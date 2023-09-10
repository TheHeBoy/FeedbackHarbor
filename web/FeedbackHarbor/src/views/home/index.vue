<template>
  <div class="container w-full mx-auto main">
    <div class="mx-auto flex w-250">
      <div class="w-full">
        <el-card class="box-card">
          <FeedBackTags class="h-10" />
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
            <el-button class="w-40 h-10" type="primary" @click="feedbackClick()">我要反馈</el-button>
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
import { getFeedbackLikeList, feedbackLike, FeedbackLikeVO } from '@/api/feedback/like';
import { useUserStoreWithOut } from '@/store/user';

const feedBackDialog = ref<any>(null);
const feedbackList = ref<FeedbackVO[]>([]);
const userStore = useUserStoreWithOut();

const feedbackClick = () => {
  feedBackDialog.value.show()
}
let pageParams = { pageNo: 1, pageSize: 5 };
let total = -1;
const loading = ref(false);
const noMore = computed(() => feedbackList.value.length >= total);
const disabled = computed(() => loading.value || noMore.value);
const load = () => {
  loading.value = true;
  pageParams.pageNo++;
  pageRequest();
};

const submitFeedback = (data: any) => {
  feedbackList.value.unshift(data);
}

const pageRequest = () => {
  getFeedbackPage(pageParams as FeedbackPageParams).then((data) => {
    feedbackList.value.push(...data.list);
    total = data.total;
    loading.value = false;
  });
}

pageRequest();

//点赞
const feedbackLikeIds = ref<Number[]>([]);

getFeedbackLikeList().then((data) => {
  feedbackLikeIds.value = data;
})

const likeClick = (feedback: FeedbackVO) => {
  const fid = feedback.id;
  feedbackLike({ feedbackId: fid }).then((data) => {
    if (data) {
      feedbackLikeIds.value.push(fid);
      feedback.likes++;
    } else {
      feedbackLikeIds.value = feedbackLikeIds.value.filter(item => item != fid);
      feedback.likes--;
    }
  });
}

</script>
<style lang="scss" scoped></style>
