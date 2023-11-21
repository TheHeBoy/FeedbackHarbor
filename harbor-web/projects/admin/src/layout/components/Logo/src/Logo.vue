<script lang="ts" setup>
import { computed, onMounted, ref, unref, watch } from 'vue';
import { useAppStore } from '@/store/modules/app';
import { useDesign } from '@/hooks/web/useDesign';
import { getTenantLogo, getTenantName } from '@/utils/auth';

defineOptions({ name: 'Logo' });

const { getPrefixCls } = useDesign();

const prefixCls = getPrefixCls('logo');

const appStore = useAppStore();

const show = ref(true);

const layout = computed(() => appStore.getLayout);

const collapse = computed(() => appStore.getCollapse);

onMounted(() => {
  if (unref(collapse)) show.value = false;
});

watch(
  () => collapse.value,
  (collapse: boolean) => {
    if (unref(layout) === 'topLeft' || unref(layout) === 'cutMenu') {
      show.value = true;
      return;
    }
    if (!collapse) {
      setTimeout(() => {
        show.value = !collapse;
      }, 400);
    } else {
      show.value = !collapse;
    }
  },
);

watch(
  () => layout.value,
  (layout) => {
    if (layout === 'top' || layout === 'cutMenu') {
      show.value = true;
    } else {
      if (unref(collapse)) {
        show.value = false;
      } else {
        show.value = true;
      }
    }
  },
);
</script>

<template>
  <div>
    <div
      :class="[
        prefixCls,
        layout !== 'classic' ? `${prefixCls}__Top` : '',
        'w-full !h-[var(--logo-height)] flex items-center justify-center',
        'dark:bg-[var(--el-bg-color)]',
      ]"
    >
      <img
        class="w-[calc(var(--logo-height)-10px)] h-[calc(var(--logo-height)-10px)]"
        :src="getTenantLogo()"
        alt=""
      />
      <div v-if="show" class="flex items-center justify-center">
        <div
          :class="[
            'ml-10px grow whitespace-nowrap overflow-hidden text-16px font-700',
            {
              'text-[var(--logo-title-text-color)]': layout === 'classic',
              'text-[var(--top-header-text-color)]':
                layout === 'topLeft' || layout === 'top' || layout === 'cutMenu',
            },
          ]"
        >
          <span>{{ getTenantName() }}</span>
        </div>
        <el-tooltip v-if="show" content="全部社区">
          <router-link link class="ml-2" to="/selectTenant">
            <el-button link>
              <Icon color="#8f9dad" icon="subway:menu" class="hover:bg-gray-900" />
            </el-button>
          </router-link>
        </el-tooltip>
      </div>
    </div>
  </div>
</template>
