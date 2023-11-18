import axios, { AxiosError, AxiosInstance, AxiosResponse, InternalAxiosRequestConfig } from 'axios';

import { ElMessage, ElMessageBox, ElNotification } from 'element-plus';
import { getAccessToken, getRefreshToken, removeToken, setToken } from '@/utils/auth';
import { getTenantId } from '@/utils/auth';
import errorCode from './errorCode';

// 忽略的错误不需要提示给用户
const ignoreCode = [
  431, // 无效的刷新令牌
  401, // 刷新令牌已过期
  430, // 用户类型错误请尝试重新登录
  901, // 演示模式，禁止写操作
];
// 是否显示重新登录
export const isRelogin = { show: false };
// Axios 无感知刷新令牌，参考 https://www.dashingdog.cn/article/11 与 https://segmentfault.com/a/1190000020210980 实现
// 请求队列
let requestList: any[] = [];
// 是否正在刷新中
let isRefreshToken = false;

const result_code = 200;
const base_url = import.meta.env.VITE_BASE_URL + import.meta.env.VITE_API_URL;
// 创建axios实例
const service: AxiosInstance = axios.create({
  baseURL: base_url,
  timeout: 30000, // 请求超时时间
  withCredentials: false, // 禁用 Cookie 等信息
  headers: {
    'Content-Type': 'application/json',
  },
});

// request拦截器
service.interceptors.request.use(
  (config: InternalAxiosRequestConfig) => {
    if (getAccessToken()) {
      (config as Recordable).headers.Authorization = 'Bearer ' + getAccessToken(); // 让每个请求携带自定义token
    }

    // 设置租户
    const tenantId = getTenantId();
    if (tenantId) {
      (config as Recordable).headers['tenant-id'] = tenantId;
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
service.interceptors.response.use(
  async (response: AxiosResponse<any>) => {
    const { data } = response;
    const config = response.config;
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
    } else if (code === 401) {
      // 未登录,尝试无感知刷新token
      if (!isRefreshToken) {
        isRefreshToken = true;
        // 1. 如果获取不到刷新令牌，则只能执行登出操作
        if (!getRefreshToken()) {
          return handleAuthorized();
        }
        // 2. 进行刷新访问令牌
        try {
          const refreshTokenRes = await refreshToken();
          // 2.1 刷新成功，则回放队列的请求 + 当前请求
          setToken(refreshTokenRes.data.data);
          config.headers!.Authorization = 'Bearer ' + getAccessToken();
          requestList.forEach((cb: any) => {
            cb();
          });
          requestList = [];
          return service(config);
        } catch (e) {
          // 为什么需要 catch 异常呢？刷新失败时，请求因为 Promise.reject 触发异常。
          // 2.2 刷新失败，只回放队列的请求
          requestList.forEach((cb: any) => {
            cb();
          });
          // 提示是否要登出。即不回放当前请求！不然会形成递归
          return handleAuthorized();
        } finally {
          requestList = [];
          isRefreshToken = false;
        }
      } else {
        // 刷新token中时先添加到队列，等待刷新获取到新的令牌
        return new Promise((resolve) => {
          requestList.push(() => {
            config.headers!.Authorization = 'Bearer ' + getAccessToken(); // 让每个请求携带自定义token 请根据实际情况自行修改
            resolve(service(config));
          });
        });
      }
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

const refreshToken = async () => {
  axios.defaults.headers.common['tenant-id'] = getTenantId();
  return await axios.post(
    base_url + '/system/auth/refresh-token?refreshToken=' + getRefreshToken(),
  );
};

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
export { service };
