<template>
  <el-card class="box-card">
    <el-row class="w-full">
      <el-col :span="1">
        <el-avatar :src="vModel.avatar" />
      </el-col>
      <el-col :span="23">
        <div class="ml-7">
          <div class="flex justify-between">
            <span>{{ vModel.nickname }}</span>
            <div>
              <i-mdi-tag-multiple
                :color="vModel.feedbackTag.color"
                class="inline w-5 h-5 mr-1"
              />
              <span class="text-sm"> {{ vModel.feedbackTag.nameCh }}</span>
            </div>
          </div>
          <div class="mt-2">
            <UFold unfold line="10">
              <div v-html="contents"></div>
              <div class="flex flex-wrap">
                <template v-for="(url, index) in imgList" :key="index">
                  <ElImage
                    :src="url"
                    class="w-16 h-16 mr-1 mt-1"
                    lazy
                    :preview-src-list="imgList"
                    :initial-index="index"
                  ></ElImage>
                </template>
              </div>
            </UFold>
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
              <el-button link size="small" @click="commentShow = !commentShow">
                <commentSVG class="icon-btn" />
                <span class="ml-1">{{ vModel.commentNum }}</span>
              </el-button>
              <el-button link @click="onLike(vModel)">
                <likeNoSVG
                  class="icon-btn"
                  v-if="
                    feedbackLikeIds.map(String).indexOf(str(vModel.id)) == -1
                  "
                />
                <likeSVG v-else class="icon-btn" color="#1e80ff" />
                <span class="ml-1">{{ vModel.likes }}</span>
              </el-button>
            </div>
          </div>
        </div>
        <UHarborComment
          class="ml-8"
          v-if="commentShow"
          :user-info="userInfo"
          :v-model="vModel"
        />
      </el-col>
    </el-row>
  </el-card>
</template>

<script lang="ts" setup>
import { onMounted, PropType, ref, computed } from "vue";
import { UFold } from "../../components-ui";
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
});

const emit = defineEmits<{}>();

// 评论控件展示
const commentShow = ref(false);
// 用户已点赞反馈集合
const feedbackLikeIds = ref<Number[]>([]);

onMounted(() => {
  getLikeList(0).then((data) => {
    feedbackLikeIds.value = data;
  });
});
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

const contents = computed(() =>
  useEmojiParse(emoji.allEmoji, props.vModel.content)
);

const imgList = computed(() => {
  let temp = props.vModel.imgs;
  if (!temp) return [];
  return temp?.split("||");
});
</script>
<style lang="scss" scoped>
.icon-btn {
  @apply w-4 h-4;
}
</style>
