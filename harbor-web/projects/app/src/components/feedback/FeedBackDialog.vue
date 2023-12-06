<template>
  <el-dialog v-model="isShow" width="700" title="反馈内容" :destroy-on-close="true" @open="open">
    <el-form ref="ruleFormRef" hide-required-asterisk label-position="top" :model="modelData">
      <el-form-item label="反馈标签" prop="feedbackType">
        <el-radio-group v-model="modelData.feedbackTagId">
          <el-radio-button v-for="tag in feedbackTags" :label="tag.id">
            <template #default>
              <i-mdi-tag-multiple :color="tag.color" class="inline w-6 h-6 mr-1" />
              {{ tag.nameCh }}
            </template>
          </el-radio-button>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="反馈内容" prop="content">
        <UImageInputBox
          ref="inputBoxRef"
          placeholder="输入反馈内容"
          content-btn="提交反馈"
          :min-height="150"
          @submit="submit"
        />
      </el-form-item>
    </el-form>
  </el-dialog>
</template>

<script lang="ts" setup>
import { FormInstance } from 'element-plus';
import { createFeedback, FeedbackCreateVO, uploadFiles } from '@harbor/apis';
import { SubmitParam2Api, UImageInputBox } from '@harbor/components';
import { FeedbackTagVO, getFeedbackTagList } from '@/api/feedback-tag';
import { onMounted } from 'vue';

const isShow = ref(false);
const inputBoxRef = ref<any>(null);
const show = () => {
  isShow.value = true;
};
defineExpose({ show });

const emit = defineEmits(['submit']);
const ruleFormRef = ref<FormInstance>();
const modelData = reactive({
  feedbackTagId: 0,
  content: '',
  imgs: [],
});

const submit = async ({ content, parentId, reply, files, clear }: SubmitParam2Api) => {
  if (content.trim().length < 5) {
    ElMessage.warning('反馈内容不能少于5字符');
    return;
  }
  modelData.content = content;
  if (files && files.length > 0) {
    let formData = new FormData();
    for (let i = 0; i < files.length; i++) {
      formData.append('files', files[i]);
    }
    modelData.imgs = await uploadFiles(formData);
  }

  createFeedback(modelData as FeedbackCreateVO).then((data) => {
    if (data) {
      if (data.sensitive) {
        ElMessage.error({
          message: `存在敏感词[${data.sensitive.join()}]`,
          showClose: true,
        });
      } else {
        emit('submit', data);
        isShow.value = false;
      }
    }
  });
};

const open = () => {
  nextTick(() => {
    inputBoxRef.value.focus();
  });
};

const feedbackTags = ref<FeedbackTagVO[]>([]);

onMounted(() => {
  getFeedbackTagList().then((data) => {
    feedbackTags.value = data;
    if (feedbackTags.value.length) {
      modelData.feedbackTagId = feedbackTags.value[0].id;
    }
  });
});
</script>
