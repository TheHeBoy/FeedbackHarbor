<template>
  <div class="flex">
    <el-button link v-if="reportShow">举报</el-button>
    <el-button link @click="$emit('onLike')">
      <span class="mr-1">{{ likeNum || "" }}</span>
      <likeNoSVG class="icon-btn" v-if="isLike" />
      <likeSVG v-else class="icon-btn" color="#1e80ff" />
    </el-button>
    <el-button link @click="$emit('onComment')">
      <span class="mr-1">{{ commentNum || "" }}</span>
      <commentSVG class="w-4 h-4" />
    </el-button>
  </div>
</template>

<script lang="ts" setup>
import { onMounted } from "vue";
import likeSVG from "./svg/likeSVG.svg?component";
import likeNoSVG from "./svg/likeNoSVG.svg?component";
import commentSVG from "./svg/commentSVG.svg?component";

const { t } = useI18n();

defineOptions({
  name: "UActionBar",
});

const props = defineProps({
  isLike: {
    // 点赞状态
    type: Boolean,
    require: true,
  },
  likeNum: {
    // 点赞数
    type: Number,
    require: true,
  },
  commentNum: {
    // 评论数
    type: Number,
    require: true,
  },
  reportShow: {
    // 举报按钮显示
    type: Boolean,
    default: () => true,
  },
});
const emit = defineEmits<{
  (e: "onLike"): void;
  (e: "onComment"): void;
}>();

onMounted(() => {});
</script>

<style lang="scss" scoped>
.icon-btn {
  @apply w-4 h-4;
}
</style>
