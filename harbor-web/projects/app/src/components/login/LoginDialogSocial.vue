<template>
  <div>
    <el-dialog v-model="isShow" :show-close="false" width="500">
      <ULogin @login="login" @login-social="doSocialLogin" />
    </el-dialog>
  </div>
</template>

<script lang="ts" setup>
import * as LoginApi from '@harbor/apis/src/login';
import { useLoginStoreWithOut } from '@/store/login';
import { ULogin } from '@harbor/components';
import { setToken } from '@/utils/auth';
import { AuthLoginRespVO } from '@harbor/apis/src/login';

const loginDialogsStore = useLoginStoreWithOut();
const { isShow } = storeToRefs(loginDialogsStore);

const login = (data: AuthLoginRespVO) => {
  setToken(data);
  location.reload();
};
const doSocialLogin = async (type: number) => {
  // 计算 redirectUri
  const redirectUri = location.origin + '/product/social-login?type=' + type;
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
      isShow.value = false;
      location.reload();
    }
  });
};
</script>
<style lang="scss" scoped>
:deep(.el-dialog) {
  background: transparent;
  box-shadow: 0 0 0 #ccc;
}
</style>
