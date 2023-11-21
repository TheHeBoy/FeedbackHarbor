<template>
  <div>
    <i-mdi-tag-multiple
      :color="feedbackTag.color"
      class="inline w-5 h-5 mr-1"
    />
    <span class="text-sm"> {{ feedbackTag.nameCh }}</span>
  </div>
</template>

<script lang="ts" setup>
import { onMounted, PropType } from "vue";
import { FeedbackTagVO } from "@harbor/apis";

defineOptions({
  name: "UFeedbackTag",
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
