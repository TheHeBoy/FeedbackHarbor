<template>
  <el-card class="!rounded-3xl">
    <template #header>
      <img src="./svg/wave-bg.svg?url" alt="" />
    </template>
    <div></div>
    <!-- 账号登录 -->
    <LoginForm
      v-if="checkLoginState(LoginStateEnum.LOGIN)"
      class="p-2 h-auto m-auto"
      @login="login"
      @loginSocial="loginSocial"
    />
    <!-- 注册 -->
    <RegisterForm v-else-if="checkLoginState(LoginStateEnum.REGISTER)" class="p-2 h-auto m-auto" />
    <!-- 忘记密码 -->
    <ForgetPasswdForm v-else-if="checkLoginState(LoginStateEnum.FORGET_PASSWD)" class="o" />
  </el-card>
</template>
<script lang="ts" setup>
import { ForgetPasswdForm, LoginForm, RegisterForm } from './components';
import { AuthLoginRespVO } from '@harbor/apis/src/login';
import { LoginStateEnum, useLoginState } from './components/useLogin';

defineOptions({ name: 'Login' });
const emits = defineEmits<{
  (e: 'login', data: AuthLoginRespVO): void;
  (e: 'loginSocial', type: number): void;
}>();
const { checkLoginState } = useLoginState();

function login(data: AuthLoginRespVO) {
  emits('login', data);
}

function loginSocial(type: number) {
  emits('loginSocial', type);
}
</script>

<style lang="scss" scoped>
:deep(.el-card__header) {
  @apply p-0 border-0 h-10;
}
</style>
