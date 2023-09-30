<script setup lang="ts">
import { useUserStoreWithOut } from './store/user';

const isRouterAlive = ref(true);
const userStore = useUserStoreWithOut();
const isHeadAlive = ref(true);
const headHide = () => {
  isHeadAlive.value = false;
};

const reload = () => {
  isRouterAlive.value = false;
  nextTick(() => {
    isRouterAlive.value = true;
  });
};

//刷新页面
provide("reload", reload);
//隐藏头部
provide("headHide", headHide);
//设置用户信息，避免刷新丢失
userStore.setUserInfoAction();

</script>

<template>
  <div v-if="isRouterAlive">
    <el-config-provider>
      <TheHead v-if="isHeadAlive"></TheHead>
      <div class="m-5">
        <router-view></router-view>
      </div>
      <TheFooter></TheFooter>
      <LoginDialogSocial></LoginDialogSocial>
    </el-config-provider>
  </div>
</template>
