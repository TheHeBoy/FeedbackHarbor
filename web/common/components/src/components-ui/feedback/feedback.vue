<template>
  <el-row class="w-full">
    <el-col :span="1">
      <UUserAvatar :avatar="vModel.avatar" :uid="vModel.uid" />
    </el-col>
    <el-col :span="23">
      <div class="ml-7">
        <div @mouseenter="reportShow = true" @mouseleave="reportShow = false">
          <div class="flex justify-between">
            <UUserNickNameInfo
              :nick-name="vModel.nickname"
              :type="vModel.userType"
            />
            <UFeedbackTag :feedback-tag="vModel.feedbackTag" />
          </div>
          <div>
            <UImageContext :contents="vModel.content" :imgs="vModel.imgs" />
          </div>
          <div class="flex justify-between mt-1">
            <URelativelyTime :time="vModel.createTime" />
            <UActionBar
              :reportShow="reportShow"
              :is-like="feedbackLikeIds.indexOf(vModel.id) == -1"
              :like-num="vModel.likes"
              :comment-num="vModel.commentNum"
              @onLike="onLike(vModel)"
              @onComment="isCommentShow = !isCommentShow"
            />
          </div>
        </div>
        <!-- 评论-->
        <UHarborComment
          ref="commentRef"
          v-if="isCommentShow"
          :user-info="userInfo"
          :v-model="vModel"
          @submit="$emit('submit')"
        />
      </div>
    </el-col>
  </el-row>
</template>

<script lang="ts" setup>
import { onMounted, PropType, ref, computed } from "vue";
import {
  UImageContext,
  UUserNickNameInfo,
  UUserAvatar,
  UFeedbackTag,
  UActionBar,
} from "../index";
import { FeedbackVO, getLikeList, like } from "@harbor/apis";
import { UHarborComment, URelativelyTime } from "../index";
import { UserInfo } from "./index";

defineOptions({
  name: "UFeedback",
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
  (e: "submit", content: String): void;
}>();

// 用户已点赞反馈集合
const feedbackLikeIds = ref<Number[]>([]);
const isCommentShow = ref(props.commentShow);
const commentRef = ref();
const onLike = (feedback: FeedbackVO) => {
  let fid = feedback.id;
  like({ rid: fid, busType: 0 }).then((data) => {
    if (data) {
      feedbackLikeIds.value.push(fid);
      feedback.likes++;
    } else {
      feedbackLikeIds.value = feedbackLikeIds.value.filter(
        (item) => item != fid
      );
      feedback.likes--;
    }
  });
};

const reportShow = ref(false);
onMounted(() => {
  getLikeList(0).then((data) => {
    feedbackLikeIds.value = data;
  });
});

defineExpose({
  focus: () => commentRef.value.focus(),
});
</script>
<style lang="scss" scoped>
.icon-btn {
  @apply w-4 h-4;
}
</style>
