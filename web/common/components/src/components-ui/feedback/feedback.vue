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
        <div class="mt-2">
          <UImageContext :contents="vModel.content" :imgs="vModel.imgs" />
        </div>
        <div class="flex justify-between">
          <span class="text-sm text-slate-400 mt-1">{{
            formatPast(vModel.createTime, "YYYY-MM-DD HH:mm")
          }}</span>
          <div>
            <el-popover trigger="click" placement="bottom">
              <el-button link size="small" class="w-full text-center">
                <reportSVG class="icon-btn" />
                <span class="ml-1">举报</span>
              </el-button>
              <template #reference>
                <el-button link linksize="small">
                  <i-mdi-dots-vertical />
                </el-button>
              </template>
            </el-popover>
            <el-button
              link
              size="small"
              @click="isCommentShow = !isCommentShow"
            >
              <commentSVG class="icon-btn" />
              <span class="ml-1">{{ vModel.commentNum }}</span>
            </el-button>
            <el-button link @click="onLike(vModel)">
              <likeNoSVG
                class="icon-btn"
                v-if="feedbackLikeIds.map(String).indexOf(str(vModel.id)) == -1"
              />
              <likeSVG v-else class="icon-btn" color="#1e80ff" />
              <span class="ml-1">{{ vModel.likes }}</span>
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
import reportSVG from "./svg/reportSVG.svg?component";
import likeSVG from "./svg/likeSVG.svg?component";
import likeNoSVG from "./svg/likeNoSVG.svg?component";
import commentSVG from "./svg/commentSVG.svg?component";
import { FeedbackVO, getLikeList, like } from "@harbor/apis";
import { UHarborComment } from "../harbor-comment";
import { UserInfo } from "./index";
import { formatPast, str } from "../../util";
import { useEmojiParse } from "../../hooks";
import emoji from "../../types/emoji";

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
