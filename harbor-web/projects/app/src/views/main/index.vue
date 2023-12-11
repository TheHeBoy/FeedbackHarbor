<template>
  <div class="w-full min-h-full !bg-gray-50 flex justify-center p-10">
    <div class="flex">
      <div class="w-150">
        <el-card>
          <div class="w-full flex justify-between">
            <SortTag @change="sortChange" />
            <div class="flex space-x-4">
              <el-button>
                <i-ep-search />
                <span class="ml-1">搜索</span>
              </el-button>
              <div class="lg:hidden">
                <el-button type="primary" @click="feedbackClick()">我要反馈</el-button>
              </div>
            </div>
          </div>
        </el-card>
        <div v-infinite-scroll="load" :infinite-scroll-disabled="disabled">
          <template v-for="feedback in feedbackList" :key="feedback.id">
            <el-card class="feedback-card w-full mt-4">
              <UFeedback
                @login="useLoginStoreWithOut().open()"
                :v-model="feedback"
                :userInfo="userInfo"
              />
            </el-card>
          </template>
        </div>
        <p v-if="loading">Loading...</p>
      </div>
      <div class="w-80 <lg:hidden">
        <el-affix position="top" :offset="70">
          <div class="pl-4 w-full">
            <el-card class="w-full box-card">
              <el-button class="w-full" type="primary" @click="feedbackClick()">
                我要反馈
              </el-button>
            </el-card>
          </div>
        </el-affix>
      </div>
    </div>
    <FeedBackDialog ref="feedBackDialog" @submit="submitFeedback" />
    <el-backtop :right="50" :bottom="50" />
  </div>
</template>
<script lang="ts" setup>
import { FeedbackPageParams, FeedbackVO, getFeedbackPage } from '@harbor/apis/src/feedback';
import { UFeedback, UserInfo } from '@harbor/components';
import { useUserStoreWithOut } from '@/store/user';
import { onMounted } from 'vue';
import * as LikeApi from '@harbor/apis/src/like';
import { BusTypeVO } from '@harbor/apis/src/like';
import { useLoginStoreWithOut } from '@/store/login';

const userStore = useUserStoreWithOut();
const userInfo = { ...userStore.user } as UserInfo;

const feedBackDialog = ref<any>(null);
const feedbackList = ref<FeedbackVO[]>([]);
const loading = ref(false);
let total = -1;
const noMore = computed(() => feedbackList.value.length >= total);
const disabled = computed(() => loading.value || noMore.value);
const pageParams = reactive<FeedbackPageParams>({ pageNo: 1, pageSize: 10, order: 0 });

const feedbackClick = () => {
  if (userStore.isSetUser) {
    feedBackDialog.value.show();
  } else {
    useLoginStoreWithOut().open();
  }
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
  getFeedbackPage(pageParams).then((data) => {
    feedbackList.value.push(...data.list);
    total = data.total;
    loading.value = false;
  });
};

const sortChange = (val: number) => {
  if (val) {
    resetList();
    pageParams.order = val;
    pageRequest();
  }
};

// 重置列表
const resetList = () => {
  pageParams.pageNo = 1;
  feedbackList.value = [];
};

onMounted(async () => {
  pageRequest();
  userInfo.feedbackLikeIds = await LikeApi.getLikeList(BusTypeVO.Feedback);
  userInfo.commentLikeIds = await LikeApi.getLikeList(BusTypeVO.Comment);
});
</script>
<style lang="scss" scoped>
.feedback-card :deep(.el-card__body) {
  padding: 0;
}

:deep(.el-card) {
  @apply rounded-lg;
}
</style>
