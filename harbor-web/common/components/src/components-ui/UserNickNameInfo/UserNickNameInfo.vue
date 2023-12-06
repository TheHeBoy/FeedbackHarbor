<template>
  <div class="flex items-center">
    <el-text type="primary" size="large">{{ nickName ?? '注销用户' }}</el-text>
    <el-tag v-if="type === UserTypeEnum.ADMIN" class="ml-1" type="danger" size="small"
      >官方
    </el-tag>
  </div>
</template>

<script lang="ts" setup>
import { onMounted } from 'vue';
import { UserTypeEnum } from '@harbor/apis';

defineOptions({
  name: 'UUserNickNameInfo',
});

const props = defineProps({
  nickName: {
    type: String,
    require: true,
  },
  type: {
    // 用户类型
    type: Number,
    require: true,
  },
});
const emit = defineEmits<{}>();

onMounted(() => {});
</script>

<style lang="scss" scoped></style>
