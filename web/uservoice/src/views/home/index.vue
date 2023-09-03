<template>
  <div class="container w-full mx-auto main">
    <div class="mx-auto w-250 flex">
      <div class="w-full">
        <el-card class="box-card">
          <FeedBackTags />
        </el-card>
        <div v-infinite-scroll="load" :infinite-scroll-disabled="disabled">
          <el-card v-for="feedback in feedbackList" :key="feedback.uid" class="box-card mt-5 w-200">
            <FeedBack :v-model="feedback"/>
          </el-card>
        </div>
      </div>
      <div class="ml-6">
        <el-affix position="top" :offset="20">
          <el-card class="box-card">
            <el-button class="w-40" type="primary" @click="feedbackClick()">我要反馈</el-button>
          </el-card>
        </el-affix>
      </div>
    </div>
    <p v-if="loading">Loading...</p>
    <FeedBackDialog ref="feedBackDialog" @submit="submitFeedback"/>
  </div>

  <el-backtop :right="100" :bottom="100" />
</template>
<script lang="ts" setup>
import { FeedbackPageParams, FeedbackVO, getFeedbackPage } from '@/api/feedback';
import { useUserStoreWithOut } from '@/store/user';


const feedBackDialog = ref<any>(null);
const feedbackList = ref<FeedbackVO[]>([]);
const userStore = useUserStoreWithOut();

const feedbackClick = ()=>{
  if(userStore.isLoginAndShwolog()){
    feedBackDialog.value.show()
  }
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

const submitFeedback = (data:any) => {
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
</script>
<style lang="scss" scoped></style>
