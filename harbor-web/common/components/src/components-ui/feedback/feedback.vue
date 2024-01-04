<template>
  <div class="root flex flex-row">
    <el-row class="w-full">
      <el-col :span="22">
        <div class="feedback-border">
          <div class="p-4" @mouseenter="reportShow = true" @mouseleave="reportShow = false">
            <div class="flex justify-between items-center">
              <div class="flex items-center justify-center">
                <UUserAvatar :size="35" :avatar="vModel.avatar" :uid="vModel.uid" />
                <UUserNickNameInfo
                  class="ml-1"
                  :nick-name="vModel.nickname"
                  :type="vModel.userType"
                />
              </div>
              <UFeedbackTag :feedback-tag="vModel.feedbackTag" />
            </div>
            <div class="mt-1">
              <UImageContext :contents="vModel.content" :imgs="vModel.imgs" />
            </div>
            <div class="flex justify-between mt-1">
              <URelativelyTime :time="vModel.createTime" />
              <div class="flex space-x-4">
                <UActionBar
                  :reportShow="reportShow"
                  :is-like="feedbackLikeIds.indexOf(vModel.id) == -1"
                  :like-num="vModel.likes"
                  :comment-num="vModel.commentNum"
                  @onLike="onLike(vModel)"
                  @onComment="isCommentShow = !isCommentShow"
                  type="feedback"
                />
              </div>
            </div>
          </div>
          <div v-if="isCommentShow">
            <!-- 评论-->
            <UComment
              ref="commentRef"
              :user-info="userInfo"
              :v-model="vModel"
              @login="emit('login')"
              @submit="$emit('submit')"
            />
          </div>
        </div>
      </el-col>
      <el-col :span="2">
        <div class="h-full">
          <button
            class="feedback-btn"
            @click="onLike(vModel)"
            :class="feedbackLikeIds.indexOf(vModel.id) == -1 ? '' : 'like'"
          >
            <div class="flex flex-col">
              <i-ep-arrowUpBold class="m-auto" />
              <span>{{ vModel.likes }}</span>
            </div>
          </button>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script lang="ts" setup>
import { onMounted, PropType, ref } from 'vue';
import {
  UActionBar,
  UComment,
  UFeedbackTag,
  UImageContext,
  UUserAvatar,
  UUserNickNameInfo,
  URelativelyTime,
  UserInfo,
} from '../index';
import { FeedbackVO } from '@harbor/apis/src/feedback';
import * as LikeApi from '@harbor/apis/src/like';

defineOptions({
  name: 'UFeedback',
});

const props = defineProps({
  vModel: {
    type: Object as PropType<FeedbackVO>,
    required: true,
  },
  userInfo: {
    type: Object as PropType<UserInfo>,
    required: true,
  },
  // 评论控件默认是否展示
  commentShow: {
    type: Boolean,
    default: false,
  },
});

const emit = defineEmits<{
  (e: 'submit', content: String): void;
  (e: 'login'): void;
}>();

// 用户已点赞反馈集合
const feedbackLikeIds = ref<Number[]>(props.userInfo.feedbackLikeIds);
const isCommentShow = ref(props.commentShow);
const commentRef = ref();
const reportShow = ref(false);
const onLike = (feedback: FeedbackVO) => {
  let fid = feedback.id;
  LikeApi.like({ rid: fid, busType: 0 }).then((data) => {
    if (data) {
      feedbackLikeIds.value.push(fid);
      feedback.likes++;
    } else {
      feedbackLikeIds.value = feedbackLikeIds.value.filter((item) => item != fid);
      feedback.likes--;
    }
  });
};

onMounted(() => {});

defineExpose({
  focus: () => commentRef.value?.focus(),
});
</script>
<style scoped lang="scss">
.root {
  --feedback-like-color: #409eff;
  --feedback-hoverLike-color: #6eb0f5;
  --feedback-border-color: #dcdfe6;
}

.like {
  @apply bg-[var(--feedback-like-color)] text-white #{!important};
}

.feedback-border {
  @apply border-r-1 border-[var(--feedback-border-color)];
}

.feedback-btn {
  @apply h-full text-xl w-full text-[#44cef6];
}

.feedback-btn:hover {
  @apply bg-[var(--feedback-hoverLike-color)] text-white #{!important};
}
</style>
