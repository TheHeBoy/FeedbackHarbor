<template>
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
</template>

<script lang="ts" setup>
import { onMounted } from "vue";
import reportSVG from "./svg/reportSVG.svg?component";
import likeSVG from "./svg/likeSVG.svg?component";
import likeNoSVG from "./svg/likeNoSVG.svg?component";
import commentSVG from "./svg/commentSVG.svg?component";

const { t } = useI18n();

defineOptions({
  name: "UActionBar",
});

const props = defineProps({
  isLike: {
    type: Boolean,
    require: true,
  },
  type: {
    // 用户类型
    type: Number,
    require: true,
  },
});
const emit = defineEmits<{

}>();

onMounted(() => {});
</script>

<style lang="scss" scoped></style>
