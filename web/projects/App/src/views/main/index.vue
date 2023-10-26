<template>
  <div class="container w-full mx-auto">
    <div class="mx-auto flex w-[70%] min-w-200">
      <div class="w-[75%]">
        <el-card class="box-card">
          <OrderTag @change="tagChange" />
        </el-card>
        <div v-infinite-scroll="load" :infinite-scroll-disabled="disabled">
          <template v-for="feedback in feedbackList" :key="feedback.id">
            <el-card class="box-card mt-4">
              <UFeedback :v-model="feedback" :userInfo="user" />
            </el-card>
          </template>
        </div>
        <p v-if="loading">Loading...</p>
      </div>
      <div class="pl-4 w-[25%] min-w-60">
        <el-affix position="top" :offset="70">
          <el-card class="box-card">
            <el-button class="w-full h-10" type="primary" @click="feedbackClick()"
              >我要反馈
            </el-button>
          </el-card>
        </el-affix>
      </div>
    </div>
    <FeedBackDialog ref="feedBackDialog" @submit="submitFeedback" />
  </div>

  <el-backtop :right="100" :bottom="100" />
</template>
<script lang="ts" setup>
import { FeedbackPageParams, FeedbackVO, getFeedbackPage } from '@harbor/apis';
import { UFeedback } from '@harbor/components';
import { useUserStoreWithOut } from '@/store/user';
import { onMounted } from 'vue';

const userStore = useUserStoreWithOut();
const { user } = storeToRefs(userStore);

const feedBackDialog = ref<any>(null);
const feedbackList = ref<FeedbackVO[]>([]);
const loading = ref(false);
let total = -1;
const noMore = computed(() => feedbackList.value.length >= total);
const disabled = computed(() => loading.value || noMore.value);
const pageParams = reactive<FeedbackPageParams>({ pageNo: 1, pageSize: 10, order: 0 });

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
  getFeedbackPage(pageParams).then((data) => {
    feedbackList.value.push(...data.list);
    total = data.total;
    loading.value = false;
  });
};

const tagChange = (val: number) => {
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

onMounted(() => {
  pageRequest();
});
</script>
<style lang="scss" scoped></style>
