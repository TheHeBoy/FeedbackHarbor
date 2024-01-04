<template>
  <div class="w-full text-center">{{ message }}</div>
</template>

<script lang="ts" setup>
import * as LoginApi from '@harbor/apis/src/login';
import { setToken } from '@/utils/auth';
import { SocialLoginVO } from '@harbor/apis/src/login';

const message = ref('授权中');

const getUrlValue = (key: string) => {
  const url = new URL(decodeURIComponent(location.href));
  return url.searchParams.get(key);
};
const type = getUrlValue('type');
const code = getUrlValue('code');
const state = getUrlValue('state');

if (!type || !code || !state) {
  message.value = '授权失败';
} else {
  const data: SocialLoginVO = {
    type: parseInt(type),
    code: code,
    state: state,
    redirectUri: location.href,
  };

  LoginApi.socialLogin(data)
    .then(async (data) => {
      if (data) {
        setToken(data);
        message.value = '授权成功';
        setTimeout(function () {
          window.close();
          window.opener.postMessage({ key: 'AuthPopupClosed', data: data });
        }, 1000);
      }
    })
    .catch((err) => {
      message.value = `授权失败:${err.message}`;
    });
}
</script>

<style rel="stylesheet/scss" lang="scss"></style>
