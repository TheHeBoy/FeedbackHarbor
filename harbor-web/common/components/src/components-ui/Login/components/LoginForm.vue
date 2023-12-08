<template>
  <el-form
    ref="formLoginRef"
    :model="loginData.loginForm"
    :rules="LoginRules"
    label-position="top"
    class="w-100"
  >
    <el-form-item>
      <h2 class="w-full mb-3 text-3xl font-bold text-center">登录</h2>
    </el-form-item>

    <el-form-item prop="username" label="用户名">
      <el-input v-model="loginData.loginForm.username" placeholder="用户名" />
    </el-form-item>

    <el-form-item prop="password" label="密码">
      <el-input
        v-model="loginData.loginForm.password"
        placeholder="输入密码"
        show-password
        type="password"
        @keyup.enter="handleLogin()"
      />
    </el-form-item>

    <el-form-item>
      <el-button
        size="large"
        :loading="loginLoading"
        class="w-[100%]"
        type="primary"
        @click="handleLogin()"
        >登录
      </el-button>
      <div class="w-full flex justify-between mt-2">
        <el-button link type="primary" @click="setLoginState(LoginStateEnum.REGISTER)"
          >注册
        </el-button>

        <el-button link type="primary" @click="setLoginState(LoginStateEnum.FORGET_PASSWD)"
          >忘记密码?
        </el-button>
      </div>
    </el-form-item>
    <el-divider> 或者</el-divider>
    <div>
      <div class="loginBtn bg-[#c71d23] hover:bg-[#c71d23] mb-2" @click="doSocialLogin(10)">
        <img src="../svg/giteeLogoSVG.svg?url" class="absolute left-2 w-5 h-5" alt="" />
        <span>登录 Giteee 继续</span>
      </div>
      <div class="loginBtn bg-[#1f2328] hover:bg-[#1f2328]" @click="doSocialLogin(30)">
        <img src="../svg/githubLogoSVG.svg?url" class="absolute left-2 w-5 h-5" alt="" />
        <span>登录 GitHub 继续</span>
      </div>
    </div>
    <div class="w-full text-center mt-2">
      <el-text>隐私政策 · 用户声明</el-text>
    </div>
  </el-form>
</template>
<script lang="ts" setup>
import { reactive, ref } from 'vue';
import { FormInstance } from 'element-plus';
import * as LoginApi from '@harbor/apis/src/login';
import { LoginStateEnum, useLoginState } from './useLogin';
import { AuthLoginRespVO } from '@harbor/apis/src/login';

defineOptions({ name: 'LoginForm' });
const emit = defineEmits<{
  (e: 'login', data: AuthLoginRespVO): void;
  (e: 'loginSocial', type: number): void;
}>();
const formLoginRef = ref<FormInstance>();
const { setLoginState } = useLoginState();
const loginLoading = ref(false);

const LoginRules = {
  username: { required: true, message: '请输入用户名', trigger: 'blur' },
  password: { required: true, message: '请输入密码', trigger: 'blur' },
};

const loginData = reactive({
  loginForm: {
    username: 'admin',
    password: 'admin123',
  },
});

// 登录
const handleLogin = async () => {
  formLoginRef.value?.validate(async (valid: any) => {
    if (valid) {
      loginLoading.value = true;
      try {
        const res = await LoginApi.login(loginData.loginForm);
        emit('login', res);
      } finally {
        loginLoading.value = false;
      }
    }
  });
};

const doSocialLogin = async (type: number) => {
  emit('loginSocial', type);
};
</script>

<style lang="scss" scoped>
.loginBtn {
  @apply h-8 flex justify-center items-center relative w-full rounded-md px-3 py-1.5
  text-sm font-semibold leading-6 text-white shadow-sm cursor-pointer;
}
</style>
