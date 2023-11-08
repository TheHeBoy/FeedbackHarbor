<template>
  <div class="bg-[var(--dark-bg-color)] flex h-full items-center justify-center">
    <Transition appear enter-active-class="animate__animated animate__bounceInRight">
      <el-card shadow="hover" class="w-100 !rounded-3xl">
        <div class="text-3xl font-bold text-center mb-4">
          {{ formType === 'create' ? '新建' : '修改' }}反馈社区
        </div>
        <el-row class="my-4 items-end justify-center">
          <el-col :span="4">
            <el-tooltip content="选择Logo">
              <UploadImg
                v-model="formData.logo"
                :actionTool="false"
                class="flex items-end"
                height="52px"
                width="52px"
              />
            </el-tooltip>
          </el-col>
          <el-col :span="20">
            <div class="text-xs mb-1">
              <el-text type="info">社区名</el-text>
            </div>
            <el-input
              class="!h-full"
              :autofocus="true"
              v-model="formData.name"
              placeholder="反馈港"
            ></el-input>
          </el-col>
        </el-row>
        <div class="text-center">
          <el-button :disabled="formLoading" type="primary" @click="onSubmit">确定</el-button>
        </div>
      </el-card>
    </Transition>
  </div>
</template>
<script lang="ts" setup>
import * as SelectTenantApi from '@/api/system/selectTenant';
import router from '@/router';

const { t } = useI18n(); // 国际化
const message = useMessage(); // 消息弹窗
const formLoading = ref(false); // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formData = ref({
  id: 0,
  name: '',
  logo: '',
});
const formType = computed(() => {
  return formData.value.id ? 'update' : 'create';
}); // 表单的类型：create - 新增；update - 修改

const onSubmit = async () => {
  // 校验表单
  if (formData.value.name.length >= 30 || formData.value.name.length < 1) {
    message.error('社区名只能是1到30字符');
    return;
  }
  if (!formData.value.logo) {
    message.error('请上传Logo');
    return;
  }
  // 提交请求
  formLoading.value = true;
  try {
    const data = formData.value as SelectTenantApi.SelectTenantVO;
    if (formType.value === 'create') {
      await SelectTenantApi.createTenant(data);
      message.success(t('common.createSuccess'));
    } else {
      await SelectTenantApi.updateTenant(data);
      message.success(t('common.updateSuccess'));
    }
    router.back();
  } finally {
    formLoading.value = false;
  }
};

onMounted(() => {
  const route = useRoute();
  if (route.query.tenantId) {
    formData.value.id = route.query.tenantId as unknown as number;
    SelectTenantApi.getTenant(formData.value.id).then((data) => {
      formData.value = data;
    });
  }
});
</script>
