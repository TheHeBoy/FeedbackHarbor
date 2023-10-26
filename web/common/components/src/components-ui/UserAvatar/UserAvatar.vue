<template>
  <div>
    <el-avatar :src="avatar">注销</el-avatar>
  </div>
</template>

<script lang="ts" setup>
import { onMounted } from "vue";

defineOptions({
  name: "UUserAvatar",
});

const props = defineProps({
  avatar: {
    type: String,
    require: true,
  },
});

const emit = defineEmits<{}>();

onMounted(() => {});
</script>

<style lang="scss" scoped></style>
