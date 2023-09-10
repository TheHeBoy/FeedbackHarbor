<script setup lang="ts">
import zhCN from 'element-plus/es/locale/lang/zh-cn';

const locale = zhCN;

const isRouterAlive = ref(true);
const reload = () => {
  isRouterAlive.value = false;
  nextTick(() => {
    isRouterAlive.value = true;
  });
};

provide("reload", reload);

</script>

<template>
  <TheHead></TheHead>
  <div v-if="isRouterAlive" class="m-5">
    <el-config-provider :locale="locale">
      <router-view></router-view>
    </el-config-provider>
  </div>
  <TheFooter></TheFooter>
  <LoginDialog></LoginDialog>
</template>
