<template>
  <div class="container w-full mx-auto main">
    <div class="mx-auto flex w-250">
      <div class="w-full">
        <el-card class="box-card">
          <FeedBackTags class="h-10" @change="tagChange" />
        </el-card>
        <div v-infinite-scroll="load" :infinite-scroll-disabled="disabled">
          <div v-for="feedback in feedbackList" :key="feedback.id" class="mt-5">
            <FeedBack @like="likeClick(feedback)" :feedback-like-ids="feedbackLikeIds" :v-model="feedback"
              class="w-full" />
          </div>
        </div>
        <p v-if="loading">Loading...</p>
      </div>
      <div class="ml-6">
        <el-affix position="top" :offset="20">
          <el-card class="box-card">
            <el-button class="w-50 h-10" type="primary" @click="feedbackClick()">我要反馈</el-button>
          </el-card>
        </el-affix>
      </div>
    </div>
    <FeedBackDialog ref="feedBackDialog" @submit="submitFeedback" />
  </div>

  <el-backtop :right="100" :bottom="100" />
</template>
<script lang="ts" setup>
import { FeedbackPageParams, FeedbackVO, getFeedbackPage } from '@/api/feedback';
import { getLikeList, like } from '@/api/like';

const feedBackDialog = ref<any>(null);
const feedbackList = ref<FeedbackVO[]>([]);
//已点赞集合
const feedbackLikeIds = ref<Number[]>([]);
const loading = ref(false);
const noMore = computed(() => feedbackList.value.length >= total);
const disabled = computed(() => loading.value || noMore.value);
let defaultPageParams = { pageNo: 1, pageSize: 10, order: 0 };
const pageParams = ref(defaultPageParams);
let total = -1;

const feedbackClick = () => {
  feedBackDialog.value.show()
}

const load = () => {
  loading.value = true;
  pageParams.value.pageNo++;
  pageRequest();
};

const submitFeedback = (data: any) => {
  feedbackList.value.unshift(data);
}

const pageRequest = () => {
  getFeedbackPage(pageParams.value as FeedbackPageParams).then((data) => {
    feedbackList.value.push(...data.list);
    total = data.total;
    loading.value = false;
  });
}

const likeClick = (feedback: FeedbackVO) => {
  const fid = feedback.id;
  like({ rid: fid, busType: 0 }).then((data) => {
    if (data) {
      feedbackLikeIds.value.push(fid);
      feedback.likes++;
    } else {
      feedbackLikeIds.value = feedbackLikeIds.value.filter(item => item != fid);
      feedback.likes--;
    }
  });
}

const tagChange = (val: string) => {
  if (val) {
    resetList();
    if (val == '推荐') {
      pageParams.value.order = 0;
    } else {
      pageParams.value.order = 1;
    }
    pageRequest();
  }
}

// 重置列表
const resetList = () => {
  pageParams.value = defaultPageParams;
  feedbackList.value = [];
}

pageRequest();

getLikeList(0).then((data) => {
  feedbackLikeIds.value = data;
})
</script>
<style lang="scss" scoped></style>
