<template>
  <div class="bg-[var(--dark-bg-color)] h-full">
    <div class="w-full flex justify-between p-5">
      <!-- 左上角的 logo + 系统标题 -->
      <div class="flex items-center relative text-white">
        <img alt="" class="w-48px h-48px mr-10px" src="@/assets/imgs/logo.png" />
        <span class="text-20px font-bold">{{ underlineToHump(appStore.getTitle) }}</span>
      </div>
      <div>
        <LocaleDropdown class="text-white" />
      </div>
    </div>
    <slot></slot>
  </div>
</template>
<script lang="ts" setup>
import { underlineToHump } from '@/utils';
import { LocaleDropdown } from '@/layout/components/LocaleDropdown';
import { useAppStore } from '@/store/modules/app';

const { t } = useI18n();
const appStore = useAppStore();
</script>

<style lang="scss" scoped></style>
