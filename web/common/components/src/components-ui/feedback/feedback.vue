<template>
  <el-row class="w-full">
    <el-col :span="1">
      <UUserAvatar :avatar="vModel.avatar" :uid="vModel.uid" />
    </el-col>
    <el-col :span="23">
      <div class="ml-7">
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
          <div>
            <URelativelyTime :time="vModel.createTime" />
          </div>
          <div>
            <el-button link size="small">
              <span class="ml-1">举报</span>
            </el-button>
            <el-button link @click="onLike(vModel)">
              <likeNoSVG
                class="icon-btn"
                v-if="feedbackLikeIds.indexOf(vModel.id) == -1"
              />
              <likeSVG v-else class="icon-btn" color="#1e80ff" />
              <span class="ml-1">{{ vModel.likes }}</span>
            </el-button>
            <el-button
              link
              size="small"
              @click="isCommentShow = !isCommentShow"
            >
              <commentSVG class="icon-btn" />
              <span class="ml-1">{{ vModel.commentNum || 0 }}</span>
            </el-button>
          </div>
        </div>
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
} from "../index";
import likeSVG from "./svg/likeSVG.svg?component";
import likeNoSVG from "./svg/likeNoSVG.svg?component";
import commentSVG from "./svg/commentSVG.svg?component";
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
