<template>
  <time class="text-sm text-slate-400">
    {{ formatPast(new Date(time), "YYYY-MM-DD HH:mm") }}
  </time>
</template>

<script lang="ts" setup>
import { onMounted } from "vue";
import { formatPast, str } from "../../util";

defineOptions({
  name: "URelativelyTime",
});
const props = defineProps({
  time: {
    type: Number,
    required: true,
  },
});

const emit = defineEmits<{}>();
onMounted(() => {});
</script>

<style lang="scss" scoped></style>
