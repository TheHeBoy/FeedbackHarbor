<template>
  <div v-if="data" class="comment-list">
    <ContentBox
      v-for="(comment, index) in data"
      :id="comment.id"
      :key="index"
      :data="comment"
    >
      <ReplyBox :id="comment.id" :data="comment.reply"></ReplyBox>
    </ContentBox>
  </div>
</template>

<script setup lang="ts">
import { CommentApi } from "../interface";
import ContentBox from "./content-box.vue";
import ReplyBox from "./reply-box.vue";
import { provide } from "vue";
import { InjectReplyShowAction, ReplyShowAction } from "../key";

interface Props {
  data: CommentApi[];
  total: number;
}

// 上次显示的回复框，由子组件提供
let setActiveOld: () => void;
provide(InjectReplyShowAction, {
  setActiveShow: (setActive: () => void) => {
    if (setActiveOld) {
      setActiveOld();
    }
    setActiveOld = setActive;
  },
} as ReplyShowAction);

const props = defineProps<Props>();
</script>

<style lang="scss" scoped></style>
