<template>
  <div class="flex items-center">
    <el-text type="info" class="text-18">{{ nickName ?? '注销用户' }}</el-text>
    <el-tag v-if="type === UserTypeEnum.ADMIN" class="ml-1" type="danger" size="small">
      {{ t('com.official') }}
    </el-tag>
  </div>
</template>

<script lang="ts" setup>
import { onMounted } from 'vue';
import { UserTypeEnum } from '@harbor/apis/src/user';
import { useI18n } from 'vue-i18n';

defineOptions({
  name: 'UUserNickNameInfo',
});

const { t } = useI18n();

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
