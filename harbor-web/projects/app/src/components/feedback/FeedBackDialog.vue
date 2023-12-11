<template>
  <el-dialog v-model="isShow" width="700" title="反馈内容" @open="open">
    <el-form ref="ruleFormRef" hide-required-asterisk label-position="top" :model="modelData">
      <el-form-item label="反馈标签" prop="feedbackType">
        <el-radio-group v-model="modelData.feedbackTagId">
          <el-radio-button v-for="tag in feedbackTags" :label="tag.id">
            <template #default>
              <div class="flex items-center">
                <i-mdi-tag-multiple :color="tag.color" class="inline w-6 h-6 mr-1" />
                {{ tag.nameCh }}
              </div>
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
          v-model="inputContent"
        />
      </el-form-item>
    </el-form>
  </el-dialog>
</template>

<script lang="ts" setup>
import { FormInstance } from 'element-plus';
import { createFeedback, FeedbackCreateVO } from '@harbor/apis/src/feedback';
import { FeedbackTagVO, getFeedbackTagList } from '@/api/feedback-tag';
import { onMounted } from 'vue';
import { SubmitCommentProp, UImageInputBox } from '@harbor/components';

const isShow = ref(false);
const inputBoxRef = ref<any>(null);
const show = () => {
  isShow.value = true;
};
defineExpose({ show });

const emit = defineEmits(['submit']);
const ruleFormRef = ref<FormInstance>();
const modelData = reactive<FeedbackCreateVO>({
  feedbackTagId: 0,
  content: '',
  imgs: [],
});
const inputContent = ref('');
const submit = async ({ content, imgUrls, clear }: SubmitCommentProp) => {
  if (content.trim().length < 5) {
    ElMessage.warning('反馈内容不能少于5字符');
    return;
  }
  modelData.content = content;
  modelData.imgs = imgUrls;
  createFeedback(modelData as FeedbackCreateVO).then((data) => {
    if (data) {
      if (data.sensitive) {
        ElMessage.error({
          message: `存在敏感词[${data.sensitive.join()}]`,
          showClose: true,
        });
      } else {
        emit('submit', data);
        clear();
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
