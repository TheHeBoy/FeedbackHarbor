<template>
  <div>
    <el-avatar :size="size" :src="avatar">
      <FailImageSVG fill="#ffffff" class="p-1" v-if="uid && avatar" />
      <span v-else>注销</span>
    </el-avatar>
  </div>
</template>

<script lang="ts" setup>
import { onMounted } from 'vue';
import type { PropType } from 'vue';
import FailImageSVG from './svg/FailImageSVG.svg?component';

defineOptions({
  name: 'UUserAvatar',
});

const props = defineProps({
  avatar: {
    type: String,
    require: true,
  },
  // 用户id
  uid: {
    type: Number,
  },
  size: {
    type: [String, Number] as PropType<String | Number>,
  },
});

const emit = defineEmits<{}>();

onMounted(() => {});
</script>

<style lang="scss" scoped></style>
