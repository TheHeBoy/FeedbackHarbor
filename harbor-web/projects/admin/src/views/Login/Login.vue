<template>
  <LayoutHead>
    <div class="flex h-full items-center justify-center">
      <Transition appear enter-active-class="animate__animated animate__bounceInRight">
        <ULogin @login="login" @loginSocial="loginSocial"></ULogin>
      </Transition>
    </div>
  </LayoutHead>
</template>
<script lang="ts" setup>
import { ULogin } from '@harbor/components';
import LayoutHead from '@/components/LayoutHead/src/LayoutHead.vue';
import { AuthLoginRespVO } from '@harbor/apis/src/login';
import router from '@/router';
import { getTenant } from '@/utils/auth';
import * as authUtil from '@/utils/auth';
import * as LoginApi from '@harbor/apis/src/login';

defineOptions({ name: 'Login' });

const login = (res: AuthLoginRespVO) => {
  authUtil.setToken(res);
  routerSkip();
};

const loginSocial = async (type: number) => {
  // 计算 redirectUri
  const redirectUri = location.origin + '/dashboard/social-login?type=' + type;
  // 进行跳转
  const authUrl = await LoginApi.socialAuthRedirect(type, encodeURIComponent(redirectUri));
  const popupWidth = 800;
  const popupHeight = 800;

  // 计算窗口居中位置
  const left = (window.screen.width - popupWidth) / 2;
  const top = (window.screen.height - popupHeight) / 2;

  // 打开授权小窗口
  window.open(
    authUrl,
    'AuthPopup',
    `width=${popupWidth},height=${popupHeight},left=${left},top=${top}`,
  );

  window.addEventListener('message', function (event) {
    if (event.data.key === 'AuthPopupClosed') {
      routerSkip();
    }
  });
};

const routerSkip = () => {
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
};
</script>

<style lang="scss" scoped></style>
