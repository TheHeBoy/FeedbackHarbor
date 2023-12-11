<template>
  <div>
    <ContentBox
      v-for="(reply, index) in list"
      :key="index"
      :data="reply"
      reply
      :id="parentId"
    ></ContentBox>
    <div v-if="data.total > pageParam.pageSize">
      <el-pagination
        small
        hide-on-single-page
        layout="total, prev, pager, next"
        :total="data.total"
        :page-size="pageParam.pageSize"
        @current-change="currentChange"
      ></el-pagination>
    </div>
  </div>
</template>

<script setup lang="ts">
import { reactive, ref } from 'vue';
import ContentBox from './content-box.vue';
import { ReplyVO } from '@harbor/apis/src/comment';
import { PageParam, PageResult } from '@harbor/apis';
import * as CommentApi from '@harbor/apis/src/comment';

interface Props {
  data: PageResult<ReplyVO>;
  parentId: number;
}

const props = defineProps<Props>();
const pageParam = reactive<PageParam>({
  pageNo: 1,
  pageSize: 5,
});

const list = ref(props.data.list);

const currentChange = async (pageNo: number) => {
  pageParam.pageNo = pageNo;

  const data = await CommentApi.getReplyPage({
    ...pageParam,
    commentId: props.parentId,
  });
  list.value = data.list;
};
</script>

<style lang="scss" scoped></style>
