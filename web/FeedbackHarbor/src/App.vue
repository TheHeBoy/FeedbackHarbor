<script setup lang="ts">
const isRouterAlive = ref(true);
const reload = () => {
  isRouterAlive.value = false;
  nextTick(() => {
    isRouterAlive.value = true;
  });
};


const isHeadAlive = ref(true);
const headHide = () => {
  isHeadAlive.value = false;
};

provide("reload", reload);
provide("headHide", headHide);

</script>

<template>
  <div v-if="isRouterAlive">
    <el-config-provider>
      <TheHead v-if="isHeadAlive"></TheHead>
      <div class="m-5">
        <router-view></router-view>
      </div>
      <TheFooter></TheFooter>
      <LoginDialog></LoginDialog>
    </el-config-provider>
  </div>
</template>
