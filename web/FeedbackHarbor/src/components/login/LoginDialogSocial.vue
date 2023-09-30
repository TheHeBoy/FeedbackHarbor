<template>
  <el-dialog v-model="isShow" width="550" title="账号登陆">
    <div class="w-full flex justify-center">
      <button class="">
        <giteeLogo class="w-10 h-10" @click="doSocialLogin(10)"></giteeLogo>
      </button>
    </div>
  </el-dialog>
</template>

<script lang="ts" setup>
import { socialAuthRedirect } from '@/api/login';
import { useloginStoreWithOut } from '@/store/login';
import giteeLogo from '@/assets/svg/giteeLogo.svg?component';
import { useUserStoreWithOut } from '@/store/user';

const loginDialogsStore = useloginStoreWithOut();
const reload = inject("reload") as Function;
const { isShow } = storeToRefs(loginDialogsStore);
const userStore = useUserStoreWithOut();

const doSocialLogin = async (type: number) => {
  // 计算 redirectUri
  const redirectUri = location.origin + '/product/social-login?type=' + type
  // 进行跳转
  const authUrl = await socialAuthRedirect(type, encodeURIComponent(redirectUri))
  const popupWidth = 800;
  const popupHeight = 800;

  // 计算窗口居中位置
  const left = (window.screen.width - popupWidth) / 2;
  const top = (window.screen.height - popupHeight) / 2;

  // 打开授权小窗口
  const authPopup = window.open(authUrl, "AuthPopup", `width=${popupWidth},height=${popupHeight},left=${left},top=${top}`);

  window.addEventListener("message", function (event) {
    if (event.data.key === "AuthPopupClosed" && authPopup && authPopup.closed) {
      userStore.setUserInfoAction()
      isShow.value = false;
      reload();
    }
  });
}
</script>
