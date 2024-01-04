import axios, { AxiosError, AxiosInstance, AxiosResponse, InternalAxiosRequestConfig } from 'axios';
import { ElMessage, ElMessageBox, ElNotification } from 'element-plus';
import { getAccessToken, removeToken } from '@/utils/auth';
import { getTenantId } from '@/utils/auth';
import errorCode from './errorCode';

// 忽略的错误不需要提示给用户
const ignoreCode = [
  901, // 演示模式，禁止写操作
];
// 是否显示重新登录
const isRelogin = { show: false };

const result_code = 200;
const base_url = import.meta.env.VITE_API_BASEURL + import.meta.env.VITE_API_URL;
// 创建axios实例
export const axiosInstance: AxiosInstance = axios.create({
  baseURL: base_url,
  timeout: 30000, // 请求超时时间
  withCredentials: false, // 禁用 Cookie 等信息
  headers: {
    'Content-Type': 'application/json',
  },
});

// request拦截器
axiosInstance.interceptors.request.use(
  (config: InternalAxiosRequestConfig) => {
    if (getAccessToken()) {
      (config as Recordable).headers.Authorization = 'Bearer ' + getAccessToken(); // 让每个请求携带自定义token
    }

    // 设置租户
    const tenantId = getTenantId();
    if (tenantId) {
      (config as Recordable).headers['tenant-id'] = tenantId;
    }

    // get参数编码，解决创建时间参数问题
    const params = config.params || {};
    if (config.method?.toUpperCase() === 'GET' && params) {
      let url = config.url + '?';
      for (const propName of Object.keys(params)) {
        const value = params[propName];
        if (value !== void 0 && value !== null && typeof value !== 'undefined') {
          if (typeof value === 'object') {
            for (const val of Object.keys(value)) {
              const params = propName + '[' + val + ']';
              const subPart = encodeURIComponent(params) + '=';
              url += subPart + encodeURIComponent(value[val]) + '&';
            }
          } else {
            url += `${propName}=${encodeURIComponent(value)}&`;
          }
        }
      }
      url = url.slice(0, -1);
      config.params = {};
      config.url = url;
    }

    return config;
  },
  (error: AxiosError) => {
    // Do something with request error
    console.log(error); // for debug
    Promise.reject(error);
  },
);

// response 拦截器
axiosInstance.interceptors.response.use(
  async (response: AxiosResponse<any>) => {
    const { data } = response;
    const { t } = useI18n();
    // 未设置状态码则默认成功状态
    const code = data.code || result_code;
    // 二进制数据则直接返回
    if (
      response.request.responseType === 'blob' ||
      response.request.responseType === 'arraybuffer'
    ) {
      return response.data;
    }

    // 获取错误信息
    const msg = data.msg || errorCode[code] || errorCode['default'];

    if (ignoreCode.indexOf(code) !== -1) {
      // 如果是忽略的错误码，直接返回 msg 异常
      return Promise.reject(msg);
    } else if (code == 401) {
      // 未登录
      return handleAuthorized();
    } else if (code === 500) {
      ElMessage.error(t('sys.api.errMsg500'));
      return Promise.reject(new Error(msg));
    } else if (code !== 200) {
      ElNotification.error({ title: msg });
      return Promise.reject('error');
    } else {
      return data;
    }
  },
  (error: AxiosError) => {
    console.log('err' + error); // for debug
    let { message } = error;
    const { t } = useI18n();
    if (message === 'Network Error') {
      message = t('sys.api.errorMessage');
    } else if (message.includes('timeout')) {
      message = t('sys.api.apiTimeoutMessage');
    } else if (message.includes('Request failed with status code')) {
      message = t('sys.api.apiRequestFailed') + message.substr(message.length - 3);
    }
    ElMessage.error(message);
    return Promise.reject(error);
  },
);

// 重新登录
const handleAuthorized = () => {
  const { t } = useI18n();
  if (!isRelogin.show) {
    isRelogin.show = true;
    ElMessageBox.confirm(t('sys.api.timeoutMessage'), t('common.confirmTitle'), {
      showCancelButton: false,
      closeOnClickModal: false,
      showClose: false,
      confirmButtonText: t('login.relogin'),
      type: 'warning',
    }).then(() => {
      removeToken();
      isRelogin.show = false;
      // 干掉token后再走一次路由让它过router.beforeEach的校验
      location.reload();
    });
  }
  return Promise.reject(t('sys.api.timeoutMessage'));
};
