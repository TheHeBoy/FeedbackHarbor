<template>
  <div class="container w-full mx-auto">
    <div class="mx-auto flex w-[70%] min-w-200">
      <div class="w-[75%]">
        <el-card class="box-card">
          <OrderTag class="h-10" @change="tagChange" />
        </el-card>
        <div v-infinite-scroll="load" :infinite-scroll-disabled="disabled">
          <div v-for="feedback in feedbackList" :key="feedback.id" class="mt-4">
            <FeedBack @like="likeClick(feedback)" :feedback-like-ids="feedbackLikeIds" :v-model="feedback" />
          </div>
        </div>
        <p v-if="loading">Loading...</p>
      </div>
      <div class="pl-4 w-[25%] min-w-60">
        <el-affix position="top" :offset="70">
          <el-card class="box-card">
            <el-button class="w-full h-10" type="primary" @click="feedbackClick()">我要反馈</el-button>
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
const pageParams = reactive({ pageNo: 1, pageSize: 10, order: 0 });
let total = -1;

const feedbackClick = () => {
  feedBackDialog.value.show();
};

const load = () => {
  loading.value = true;
  pageParams.pageNo++;
  pageRequest();
};

const submitFeedback = (data: FeedbackVO) => {
  feedbackList.value.unshift(data);
};

const pageRequest = () => {
  getFeedbackPage(pageParams as FeedbackPageParams).then((data) => {
    feedbackList.value.push(...data.list);
    total = data.total;
    loading.value = false;
  });
};

const likeClick = (feedback: FeedbackVO) => {
  const fid = feedback.id;
  like({ rid: fid, busType: 0 }).then((data) => {
    if (data) {
      feedbackLikeIds.value.push(fid);
      feedback.likes++;
    } else {
      feedbackLikeIds.value = feedbackLikeIds.value.filter((item) => item != fid);
      feedback.likes--;
    }
  });
};

const tagChange = (val: string) => {
  if (val) {
    resetList();
    if (val == '推荐') {
      pageParams.order = 0;
    } else {
      pageParams.order = 1;
    }
    pageRequest();
  }
};

// 重置列表
const resetList = () => {
  pageParams.pageNo = 1;
  feedbackList.value = [];
};

pageRequest();

getLikeList(0).then((data) => {
  feedbackLikeIds.value = data;
});

// const { params } = useRoute(); //2.在跳转页面定义router变量，解构得到指定的query和params传参的参数
// console.log(params);
</script>
<style lang="scss" scoped></style>
