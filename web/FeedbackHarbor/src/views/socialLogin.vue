<template>
  <div class="w-full text-center">{{ message }}</div>
</template>

<script lang="ts" setup>
import { SocialLoginVO, socialLogin } from '@/api/login';
import { setToken } from '@/utils/auth';
import { useUserStoreWithOut } from '@/store/user';

const userStore = useUserStoreWithOut();
const message = ref('授权中');

const headHide = inject('headHide') as Function;
headHide();

const getUrlValue = (key: string) => {
  const url = new URL(decodeURIComponent(location.href));
  return url.searchParams.get(key);
};
const type = getUrlValue('type');
const code = getUrlValue('code');
const state = getUrlValue('state');

if (type == null || code == null || state == null) {
  message.value = '授权失败';
} else {
  const data: SocialLoginVO = {
    type: parseInt(type),
    code: code,
    state: state,
  };

  socialLogin(data)
    .then(async (data) => {
      if (data) {
        setToken(data);
        userStore.setUserInfoAction();
        message.value = '授权成功';
        setTimeout(function () {
          window.close();
          window.opener.postMessage({ key: 'AuthPopupClosed', data: data });
        }, 1000);
      }
    })
    .catch((err) => {
      console.log(err);
      message.value = '授权失败';
    });
}
</script>

<style rel="stylesheet/scss" lang="scss"></style>
