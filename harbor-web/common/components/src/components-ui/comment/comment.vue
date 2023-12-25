<template>
  <div class="pt-2 mx-4">
    <div class="text-lg border-b-2 pb-1 mb-4">评论</div>
    <div class="pt-2 pb-4">
      <div class="pb-2">
        <UImageInputBox
          v-if="userInfo.id"
          ref="inputBoxRef"
          placeholder="输入评论"
          content-btn="发表评论"
          :height="50"
          @submit="submit"
          v-model="inputContent"
        />
        <el-button v-else class="w-full" @click="emit('login')">
          <span>登录后才能发表评论</span>
          <i-ep-right class="ml-1" />
        </el-button>
      </div>
      <div>
        <div v-if="computed.length > 0">
          <CommentList :data="comments"></CommentList>
        </div>
        <div class="w-full text-center">
          <el-text v-if="loading">loading</el-text>
          <el-button v-if="!disabled" @click="more" link type="primary">加载更多</el-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted, PropType } from 'vue';
import CommentList from './src/comment-list.vue';
import { ElMessage } from 'element-plus';
import * as CommentApi from '@harbor/apis/src/comment';
import { FeedbackVO } from '@harbor/apis/src/feedback';
import { SubmitCommentApi, useComment } from './useComment';
import { UImageInputBox, UserInfo } from '../../index';
import { CommentVO, createCommentVO } from '@harbor/apis/src/comment';

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

const emit = defineEmits<{
  (e: 'submit', content: String): void;
  (e: 'login'): void;
}>();

const inputBoxRef = ref();
let params = { pageNo: 1, pageSize: 5, feedbackId: props.vModel.id };
const inputContent = ref('');
const comments = ref<CommentVO[]>([]);
const commentTotal = ref(0);
const loading = ref(false);
const noMore = computed(() => comments.value.length >= commentTotal.value);
const disabled = computed(() => loading.value || noMore.value);
const { setCommentLikeIds, setSubmit } = useComment();

// 提交评论
const submit = async ({ content, imgUrls, clear, parentId }: SubmitCommentApi) => {
  if (content.trim().length == 0) {
    ElMessage.warning('评论不能为空');
    return;
  }

  const createCommentVO: createCommentVO = {
    content: content,
    feedbackId: props.vModel.id,
    parentId: parentId,
    imgs: imgUrls,
  };

  const data = await CommentApi.createComment(createCommentVO);
  if (data.sensitive) {
    ElMessage.error({
      message: `存在敏感词[${data.sensitive.join()}]`,
      showClose: true,
    });
    return;
  }
  // 清空输入框内容
  clear();
  finish(data);
  ElMessage.success('评论成功!');
  props.vModel.commentNum++;
  emit('submit', content);
};

// 设置提交方法
setSubmit(submit);

// 添加评论
const finish = (comment: CommentVO) => {
  // 提交评论添加到评论列表
  const parentId = comment.parentId;
  if (comment.parentId) {
    let raw_comment = comments.value.find((v) => v.id == parentId);
    if (raw_comment) {
      let replys = raw_comment.replyPage;
      if (replys) {
        replys.list.unshift(comment);
        replys.total++;
      } else {
        raw_comment.replyPage = {
          total: 1,
          list: [comment],
        };
      }
    }
  } else {
    comments.value.unshift(comment);
  }
};

// 评论分页
const commentPage = async () => {
  try {
    loading.value = true;
    let data = await CommentApi.getCommentPage(params);
    commentTotal.value = data.total;
    comments.value.push(...data.list);
  } finally {
    loading.value = false;
  }
};
const more = () => {
  params.pageNo++;
  commentPage();
};

onMounted(async () => {
  setCommentLikeIds(props.userInfo.commentLikeIds);
  await commentPage();
});

defineExpose({
  focus: () => inputBoxRef.value?.focus(),
});
</script>

<style lang="scss" scoped></style>
