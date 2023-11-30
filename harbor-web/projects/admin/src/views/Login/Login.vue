<template>
  <div class="h-full bg-[var(--dark-bg-color)]">
    <div class="w-full flex justify-between p-5">
      <!-- 左上角的 logo + 系统标题 -->
      <div class="flex items-center relative text-white">
        <img alt="" class="w-48px h-48px mr-10px" src="@/assets/imgs/logo.png" />
        <span class="text-20px font-bold">{{ underlineToHump(appStore.getTitle) }}</span>
      </div>
      <div>
        <LocaleDropdown class="text-white" />
      </div>
    </div>
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
  </div>
</template>
<script lang="ts" setup>
import { underlineToHump } from '@/utils';
import { useAppStore } from '@/store/modules/app';
import { LocaleDropdown } from '@/layout/components/LocaleDropdown';

import { LoginForm, RegisterForm, ForgetPasswdForm } from './components';

defineOptions({ name: 'Login' });

const { t } = useI18n();
const appStore = useAppStore();
</script>

<style lang="scss" scoped></style>
