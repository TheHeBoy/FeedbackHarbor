<template>
  <el-menu :default-active="activeIndex" mode="horizontal" class="w-200 flex items-center" @select="handleSelect">
    <div class="cursor-pointer">
      <logo class="w-10 h-10 ml-5 mr-5"></logo>
    </div>
    <span class="cursor-pointer text-xl font-bold">{{ appName }}</span>
    <div class="mr-10" />
    <el-menu-item index="1">首页</el-menu-item>
  </el-menu>
</template>

<script lang="ts" setup>
import logo from '@/assets/svg/logo.svg?component';

const appName = import.meta.env.VITE_APP_TITLE;

const activeIndex = ref('1');
const handleSelect = (key: string, keyPath: string[]) => {
  console.log(key, keyPath);
};
</script>
