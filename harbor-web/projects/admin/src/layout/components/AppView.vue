<script lang="ts" setup>
import { useTagsViewStore } from '@/store/modules/tagsView';
import { useAppStore } from '@/store/modules/app';
import { Footer } from '@/layout/components/Footer';

defineOptions({ name: 'AppView' });

const appStore = useAppStore();

const footer = computed(() => appStore.getFooter);

const tagsViewStore = useTagsViewStore();

const getCaches = computed((): string[] => {
  return tagsViewStore.getCachedViews;
});
</script>

<template>
  <div class="flex flex-col h-full">
    <section
      class="p-[var(--app-content-padding)] bg-gray-50 flex-grow w-full bg-[var(--app-content-bg-color)]"
    >
      <router-view>
        <template #default="{ Component, route }">
          <keep-alive :include="getCaches">
            <component :is="Component" :key="route.fullPath" />
          </keep-alive>
        </template>
      </router-view>
    </section>
    <Footer class="b-0 w-full" v-if="footer" />
  </div>
</template>
