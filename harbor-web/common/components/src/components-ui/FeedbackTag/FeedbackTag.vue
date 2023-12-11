<template>
  <div>
    <i-mdi-tag-multiple :color="feedbackTag.color" class="inline mr-1" />
    <el-text type="info"> {{ feedbackTag.nameCh }}</el-text>
  </div>
</template>

<script lang="ts" setup>
import { onMounted, PropType } from 'vue';
import { FeedbackTagVO } from '@harbor/apis/src/feedback';

defineOptions({
  name: 'UFeedbackTag',
});

const props = defineProps({
  feedbackTag: {
    type: Object as PropType<FeedbackTagVO>,
    required: true,
  },
});

const emit = defineEmits<{}>();

onMounted(() => {});
</script>

<style lang="scss" scoped></style>
