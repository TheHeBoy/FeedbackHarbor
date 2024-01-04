<script lang="ts" setup>
import { useAppStore } from '@/store/modules/app';
import { useDesign } from '@/hooks/web/useDesign';

// eslint-disable-next-line vue/no-reserved-component-names
defineOptions({ name: 'Footer' });

const { getPrefixCls } = useDesign();

const prefixCls = getPrefixCls('footer');

const appStore = useAppStore();

const title = computed(() => appStore.getTitle);
</script>

<template>
  <div
    :class="prefixCls"
    class="text-center text-[var(--el-text-color-placeholder)] !bg-gray-100 h-[var(--app-footer-height)] leading-[var(--app-footer-height)]"
  >
    <p style="font-size: 14px">Copyright ©2022-{{ title }}</p>
  </div>
</template>
