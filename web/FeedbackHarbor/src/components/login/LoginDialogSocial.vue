<template>
  <el-dialog v-model="isShow" width="400" title="登陆">
    <div class="loginBtn bg-[#c71d23] hover:bg-[#c71d23]" @click="doSocialLogin(10)">
      <el-row>
        <el-col :span="2">
          <giteeLogo class="w-6 h-6"></giteeLogo>
        </el-col>
        <el-col :span="22" class="text-center">
          <span>登录 Gitee 继续</span>
        </el-col>
      </el-row>
    </div>
    <div class="loginBtn bg-[#1f2328] hover:bg-[#1f2328] mt-2" @click="doSocialLogin(30)">
      <el-row>
        <el-col :span="2">
          <githubLogo class="w-6 h-6"></githubLogo>
        </el-col>
        <el-col :span="22" class="text-center">
          <span>登录 GitHub 继续</span>
        </el-col>
      </el-row>
    </div>
    <div class="loginBtn bg-[#4285f4] hover:bg-[#4285f4] mt-2" @click="doSocialLogin(20)">
      <el-row>
        <el-col :span="2">
          <googleLogo class="w-6 h-6"></googleLogo>
        </el-col>
        <el-col :span="22" class="text-center">
          <span>登录 Google 继续</span>
        </el-col>
      </el-row>
    </div>
  </el-dialog>
</template>

<script lang="ts" setup>
import { socialAuthRedirect } from '@/api/login';
import { useloginStoreWithOut } from '@/store/login';
import giteeLogo from '@/assets/svg/giteeLogoSVG.svg?component';
import googleLogo from '@/assets/svg/googleLogoSVG.svg?component';
import githubLogo from '@/assets/svg/githubLogoSVG.svg?component';
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
  window.open(authUrl, "AuthPopup", `width=${popupWidth},height=${popupHeight},left=${left},top=${top}`);

  window.addEventListener("message", function (event) {
    if (event.data.key === "AuthPopupClosed") {
      isShow.value = false;
      reload();
    }
  });
}
</script>
<style lang="scss" scoped>
.loginBtn {
  @apply w-full rounded-md px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm cursor-pointer
}
</style>
