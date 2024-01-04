<template>
  <div class="bg-[var(--dark-bg-color)] h-full w-full relative">
    <div class="absolute top-0 w-full flex justify-between p-5">
      <!-- 左上角的 logo + 系统标题 -->
      <div class="flex items-center relative text-white">
        <img alt="" class="w-10 h-10 mr-4" src="@/assets/imgs/logo.png" />
        <span class="text-3xl font-bold">{{ underlineToHump(appStore.getTitle) }}</span>
      </div>
    </div>
    <slot></slot>
  </div>
</template>
<script lang="ts" setup>
import { underlineToHump } from '@/utils';
import { useAppStore } from '@/store/modules/app';

const { t } = useI18n();
const appStore = useAppStore();
</script>

<style lang="scss" scoped></style>
