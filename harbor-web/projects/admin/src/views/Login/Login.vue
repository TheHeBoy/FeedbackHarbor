<template>
  <LayoutHead>
    <div class="flex h-full items-center justify-center">
      <Transition appear enter-active-class="animate__animated animate__bounceInRight">
        <div class="h-full flex items-center m-auto w-full">
          <!-- 账号登录 -->
          <LoginForm class="p-20px h-auto m-auto rounded-3xl light:bg-white" />
          <!-- 注册 -->
          <RegisterForm class="p-20px h-auto m-auto rounded-3xl light:bg-white" />
          <!-- 忘记密码 -->
          <ForgetPasswdForm class="p-20px h-auto m-auto rounded-3xl light:bg-white" />
        </div>
      </Transition>
    </div>
  </LayoutHead>
</template>
<script lang="ts" setup>
import { useAppStore } from '@/store/modules/app';
import { LoginForm, RegisterForm, ForgetPasswdForm } from './components';
import LayoutHead from '@/components/LayoutHead/src/LayoutHead.vue';

defineOptions({ name: 'Login' });

const { t } = useI18n();
const appStore = useAppStore();
</script>

<style lang="scss" scoped></style>
