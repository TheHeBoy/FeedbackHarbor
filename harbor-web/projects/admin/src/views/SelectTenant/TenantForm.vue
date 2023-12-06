<template>
  <LayoutHead>
    <div class="flex h-full items-center justify-center">
      <Transition appear enter-active-class="animate__animated animate__bounceInRight">
        <el-card shadow="hover" class="w-100 !rounded-3xl">
          <div class="text-3xl font-bold text-center mb-4">
            {{ formType === 'create' ? '新建' : '修改' }}反馈社区
          </div>
          <el-form label-position="top" ref="ruleFormRef" :model="formData" :rules="rules">
            <el-form-item label="Logo" prop="logo">
              <UploadImg
                v-model="formData.logo"
                :actionTool="false"
                class="flex items-end"
                height="52px"
                width="52px"
              />
            </el-form-item>
            <el-form-item label="社区名" prop="name">
              <el-input v-model="formData.name" />
            </el-form-item>
            <el-form-item label="社区路由" prop="routerUri">
              <el-input v-model="formData.routerUri">
                <template #prepend>Http://.../product/</template>
              </el-input>
            </el-form-item>
            <el-form-item>
              <el-button :disabled="formLoading" type="primary" @click="submitForm(ruleFormRef)"
                >确定
              </el-button>
            </el-form-item>
          </el-form>
        </el-card>
      </Transition>
    </div>
  </LayoutHead>
</template>
<script lang="ts" setup>
import * as SelectTenantApi from '@/api/system/selectTenant';
import router from '@/router';
import { FormInstance, FormRules } from 'element-plus';
import LayoutHead from '@/components/LayoutHead/src/LayoutHead.vue';

const { t } = useI18n(); // 国际化
const message = useMessage(); // 消息弹窗
const formLoading = ref(false); // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const ruleFormRef = ref<FormInstance>();
const formData = ref({
  id: 0,
  name: '',
  logo: '',
  routerUri: '',
});
const formType = computed(() => {
  return formData.value.id ? 'update' : 'create';
}); // 表单的类型：create - 新增；update - 修改

const rules = reactive<FormRules>({
  name: [
    {
      required: true,
      message: '请输入社区名',
      trigger: 'change',
    },
    { min: 1, max: 30, message: '社区名必须是 1 到 30 字符', trigger: 'change' },
  ],
  routerUri: [
    {
      required: true,
      message: '请输入社区路由',
      trigger: 'change',
    },
    { min: 1, max: 30, message: '社区路由必须是 1 到 30 字符', trigger: 'change' },
  ],
  logo: [
    {
      required: true,
      message: '请上传社区Logo',
      trigger: 'change',
    },
  ],
});

watch(
  () => formData.value.routerUri,
  (newValue, oldValue) => {
    formData.value.routerUri = extractAlphanumeric(newValue);
  },
  { deep: true },
);

const submitForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  await formEl.validate(async (valid, fields) => {
    if (valid) {
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
    }
  });
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

function extractAlphanumeric(inputString: string) {
  if (inputString) {
    // 使用正则表达式匹配数字和字母
    const matches = inputString.match(/[a-zA-Z0-9]+/g);

    // 如果有匹配的结果，将其连接为一个字符串
    if (matches) {
      return matches.join('');
    }
  }
  // 如果没有匹配结果，返回空字符串或其他适当的默认值
  return '';
}
</script>
