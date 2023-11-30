<template>
  <el-form
    v-show="getShow"
    ref="formLoginRef"
    :model="loginData.loginForm"
    :rules="LoginRules"
    label-position="top"
    class="w-100"
  >
    <el-form-item>
      <h2 class="w-full mb-3 text-2xl font-bold text-center xl:text-3xl enter-x xl:text-center">
        登录
      </h2>
    </el-form-item>

    <el-form-item prop="username" label="用户名">
      <el-input
        v-model="loginData.loginForm.username"
        :placeholder="t('login.usernamePlaceholder')"
      />
    </el-form-item>

    <el-form-item prop="password" label="密码">
      <el-input
        v-model="loginData.loginForm.password"
        :placeholder="t('login.passwordPlaceholder')"
        show-password
        type="password"
        @keyup.enter="getCode()"
      />
    </el-form-item>

    <el-form-item>
      <div class="w-full flex justify-between">
        <el-checkbox v-model="loginData.loginForm.rememberMe">
          {{ t('login.remember') }}
        </el-checkbox>
        <el-button link type="primary" @click="setLoginState(LoginStateEnum.FORGETP_ASSWDFORM)"
          >忘记密码?
        </el-button>
      </div>
    </el-form-item>

    <el-form-item>
      <XButton
        :loading="loginLoading"
        :title="t('login.login')"
        class="w-[100%]"
        type="primary"
        @click="getCode()"
      />
    </el-form-item>

    <el-form-item>
      <XButton
        :title="t('login.btnRegister')"
        class="w-[100%]"
        @click="setLoginState(LoginStateEnum.REGISTER)"
      />
    </el-form-item>
  </el-form>
</template>
<script lang="ts" setup>
import { ElLoading, FormInstance } from 'element-plus';
import * as authUtil from '@/utils/auth';
import * as LoginApi from '@/api/login';
import { LoginStateEnum, useLoginState } from './useLogin';
import { getTenant } from '@/utils/auth';
import router from '@/router';

defineOptions({ name: 'LoginForm' });

const { t } = useI18n();
const formLoginRef = ref<FormInstance>();
const { getLoginState, setLoginState } = useLoginState();
const loginLoading = ref(false);

const getShow = computed(() => unref(getLoginState) === LoginStateEnum.LOGIN);

const LoginRules = {
  username: [required],
  password: [required],
};

const loginData = reactive({
  isShowPassword: false,
  loginForm: {
    username: 'admin',
    password: 'admin123',
    rememberMe: false,
  },
});

// 获取验证码
const getCode = async () => {
  await handleLogin({});
};
// 记住我
const getCookie = () => {
  const loginForm = authUtil.getLoginForm();
  if (loginForm) {
    loginData.loginForm = {
      ...loginData.loginForm,
      username: loginForm.username ? loginForm.username : loginData.loginForm.username,
      password: loginForm.password ? loginForm.password : loginData.loginForm.password,
      rememberMe: loginForm.rememberMe,
    };
  }
};
// 登录
const handleLogin = async (params: any) => {
  formLoginRef.value?.validate(async (valid: any) => {
    if (valid) {
      loginLoading.value = true;
      try {
        const res = await LoginApi.login(loginData.loginForm);
        if (!res) {
          return;
        }
        if (loginData.loginForm.rememberMe) {
          authUtil.setLoginForm(loginData.loginForm);
        } else {
          authUtil.removeLoginForm();
        }
        authUtil.setToken(res);

        // 是否有链接标识,如果有说明是通过邀请链接进入到登录页面的
        const code = router.currentRoute.value.query.code as string;
        if (code) {
          router.push({ path: '/selectTenant', query: { code } });
        } else {
          // 是否有租户信息
          if (getTenant()) {
            router.push({ path: router.currentRoute.value.query.redirect as string });
          } else {
            router.push({ path: '/selectTenant' });
          }
        }
      } finally {
        loginLoading.value = false;
      }
    }
  });
};

onMounted(() => {
  getCookie();
});
</script>

<style lang="scss" scoped></style>
