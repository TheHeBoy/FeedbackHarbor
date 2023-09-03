<script setup lang="ts">
import zhCN from 'element-plus/es/locale/lang/zh-cn';

const locale = zhCN;
</script>

<template>
  <TheHead></TheHead>
  <div class="m-5">
    <el-config-provider :locale="locale">
      <router-view></router-view>
    </el-config-provider>
  </div>
  <TheFooter></TheFooter>
  <LoginDialog></LoginDialog>
</template>
