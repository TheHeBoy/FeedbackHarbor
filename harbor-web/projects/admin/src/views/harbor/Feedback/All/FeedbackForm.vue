<template>
  <div class="root">
    <Dialog :title="dialogTitle" v-model="dialogVisible">
      <el-form
        ref="formRef"
        :model="formData"
        :rules="formRules"
        label-width="100px"
        v-loading="formLoading"
      >
        <el-form-item label="内容">
          <Editor v-model="formData.content" height="150px" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="submitForm" type="primary" :disabled="formLoading">确 定</el-button>
        <el-button @click="dialogVisible = false">取 消</el-button>
      </template>
    </Dialog>
  </div>
</template>
<script setup lang="ts">
import * as FeedbackApi from '@/api/harbor/feedback';

const { t } = useI18n(); // 国际化
const message = useMessage(); // 消息弹窗

const dialogVisible = ref(false); // 弹窗的是否展示
const dialogTitle = ref(''); // 弹窗的标题
const formLoading = ref(false); // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref(''); // 表单的类型：create - 新增；
const formData = ref({
  content: undefined,
});
const formRules = reactive({});
const formRef = ref(); // 表单 Ref

/** 提交表单 */
const emit = defineEmits(['success']); // 定义 success 事件，用于操作成功后的回调
const submitForm = async () => {
  // 校验表单
  if (!formRef) return;
  const valid = await formRef.value.validate();
  if (!valid) return;
  // 提交请求
  formLoading.value = true;
  try {
    const data = formData.value as unknown as FeedbackApi.FeedbackVO;
    if (formType.value === 'create') {
      await FeedbackApi.createFeedback(data);
      message.success(t('common.createSuccess'));
    }
    dialogVisible.value = false;
    // 发送操作成功的事件
    emit('success');
  } finally {
    formLoading.value = false;
  }
};
</script>
