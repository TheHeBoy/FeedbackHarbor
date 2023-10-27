<template>
  <el-dialog v-model="isShow" width="700" title="反馈内容" :destroy-on-close="true">
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
        <UInputBox
          ref="commentRef"
          placeholder="输入反馈内容"
          content-btn="提交反馈"
          :min-height="150"
        />
      </el-form-item>
    </el-form>
  </el-dialog>
</template>

<script lang="ts" setup>
import { FormInstance } from 'element-plus';
import { FeedbackCreateVO, createFeedback, uploadFiles } from '@harbor/apis';
import UInputBox from '@harbor/components/src/components-ui/comment/src/tools/input-box.vue';
import {
  InjectInputBox,
  InjectInputBoxApi,
  SubmitParam2Api,
  InjectionEmojiApi,
} from '@harbor/components';
import emoji from '@harbor/components/src/types/emoji';
import { FeedbackTagVO, getFeedbackTagList } from '@/api/feedback-tag';
import { onMounted } from 'vue';

const isShow = ref(false);
const show = () => {
  isShow.value = true;
};
defineExpose({ show });

const emit = defineEmits(['submit']);
const ruleFormRef = ref<FormInstance>();
const modelData = reactive({
  feedbackTagId: 0,
  content: '',
  imgs: undefined,
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
    let fileUrls = await uploadFiles(formData);
    // 格式以'||'为分割
    modelData.imgs = fileUrls.join('||');
  }

  createFeedback(modelData as FeedbackCreateVO).then((data) => {
    if (data) {
      emit('submit', data);
      isShow.value = false;
    }
  });
};

const inputBoxParam: InjectInputBoxApi = {
  upload: true,
  submit: submit,
  focus: () => {},
};

const feedbackTags = ref<FeedbackTagVO[]>([]);

onMounted(() => {
  getFeedbackTagList().then((data) => {
    feedbackTags.value = data;
    modelData.feedbackTagId = feedbackTags.value[0].id;
  });
});

provide(InjectInputBox, inputBoxParam);
provide(InjectionEmojiApi, emoji);
</script>
