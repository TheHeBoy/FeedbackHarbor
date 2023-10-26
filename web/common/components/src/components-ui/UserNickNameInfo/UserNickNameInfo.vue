<template>
  <div class="flex">
    <span>{{ nickName ? nickName : '注销用户' }}</span>
    <span v-if="type === UserTypeEnum.ADMIN">官方</span>
  </div>
</template>

<script lang="ts" setup>
import { onMounted } from "vue";
import { UserTypeEnum } from "@harbor/apis";

defineOptions({
  name: "UUserNickNameInfo",
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
