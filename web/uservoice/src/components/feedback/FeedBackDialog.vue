<template>
  <el-dialog v-model="isShow" width="700" title="创建反馈" :destroy-on-close="true">
    <el-form ref="ruleFormRef" hide-required-asterisk label-position="top" :rules="rules" :model="modelData">
      <el-form-item label="反馈类型" prop="feedbackType">
        <el-radio-group v-model="modelData.feedbackType">
          <el-radio :label="0" size="large" border>问题反馈</el-radio>
          <el-radio :label="1" size="large" border>产品建议</el-radio>
          <el-radio :label="2" size="large" border>添加功能</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="反馈内容" prop="content">
        <el-input ref="editorRef" v-model="modelData.content" rows="7" type="textarea" />
      </el-form-item>
    </el-form>
    <button
      class="flex w-full justify-center rounded-md bg-indigo-600 px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"
      @click="submitForm(ruleFormRef)"
    >
      提交反馈
    </button>
  </el-dialog>
</template>

<script lang="ts" setup>
import { ElInput, FormInstance } from 'element-plus';
import { FeedbackCreateVO, FeedbackVO, createFeedback } from '@/api/feedback';
import { useUserStoreWithOut } from '@/store/user';

const rules = {
  content: [
    { required: true, message: '请输入反馈内容' },
    { max: 500, message: '长度不能超过500' }],
};

const isShow = ref(false);
const editorRef = ref<InstanceType<typeof ElInput>>();
const show = () => {
  isShow.value = true;
};

defineExpose({ show });

const emit = defineEmits(['submit'])

const ruleFormRef = ref<FormInstance>();

const {user} = useUserStoreWithOut();

const modelData = ref({
  feedbackType: 0,
  content: '',
  uid: user.id,
  nickname: user.nickname,
  avatar: user.avatar,
  userType: user.userType,
});

const submitForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  await formEl.validate(async (valid, fields) => {
    if (valid) {
      createFeedback(modelData.value as FeedbackCreateVO).then((data)=>{
        if(data){
          emit('submit',data);
          isShow.value = false;
        }
      }).catch(err =>{
        console.log(err);
      });
    }
  });
};
</script>
