<template>
  <ContentWrap>
    <IFrame :src="src" />
  </ContentWrap>
</template>
<script lang="ts" setup>
import * as ConfigApi from '@/api/infra/config';

defineOptions({ name: 'InfraSwagger' });

const loading = ref(true); // 是否加载中
const src = ref(import.meta.env.VITE_API_BASEURL + '/doc.html'); // Knife4j UI
// const src = ref(import.meta.env.VITE_API_BASEURL + '/swagger-ui') // Swagger UI

/** 初始化 */
onMounted(async () => {
  try {
    const data = await ConfigApi.getConfigKey('url.swagger');
    if (data && data.length > 0) {
      src.value = data;
    }
  } finally {
    loading.value = false;
  }
});
</script>
