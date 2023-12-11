<template>
  <div v-if="data">
    <ContentBox v-for="(comment, index) in data" :id="comment.id" :key="index" :data="comment">
      <ReplyBox
        v-if="comment.replyPage && comment.replyPage.total > 0"
        :data="comment.replyPage"
        :parentId="comment.id"
      ></ReplyBox>
    </ContentBox>
  </div>
</template>

<script setup lang="ts">
import ContentBox from './content-box.vue';
import ReplyBox from './reply-box.vue';
import { CommentVO } from '@harbor/apis/src/comment';

interface Props {
  data: CommentVO[];
}

const props = defineProps<Props>();
</script>

<style lang="scss" scoped></style>
