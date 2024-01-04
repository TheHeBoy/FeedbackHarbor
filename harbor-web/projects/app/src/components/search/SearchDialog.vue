<template>
  <div class="root">
    <el-dialog
      destroy-on-close
      @open="open"
      v-model="isShow"
      width="600"
      :show-close="false"
      class="p-2"
    >
      <template #header>
        <div>
          <el-input ref="inputRef" v-model="searchWords" @input="onInput">
            <template #prefix>
              <i-ep-search />
            </template>
          </el-input>
        </div>
      </template>
      <div class="overflow-y-auto max-h-100">
        <div
          v-infinite-scroll="load"
          :infinite-scroll-disabled="disabled"
          infinite-scroll-distance="8"
          class="flex flex-col px-1 w-full"
        >
          <div
            v-for="vo in searchList"
            @click="onClick(vo.rid)"
            :key="vo.rid"
            class="py-2 hover:bg-gray-50 cursor-pointer break-words border-b-1"
          >
            <div v-for="(content, index) in vo.highContent" :key="index" v-html="content"></div>
          </div>
        </div>
      </div>
    </el-dialog>
    <el-dialog destroy-on-close width="800" :show-close="false" v-model="feedbackShow">
      <UFeedback
        :comment-show="true"
        @login="useLoginStoreWithOut().open()"
        :v-model="feedback"
        :userInfo="userInfo"
      />
    </el-dialog>
  </div>
</template>

<script lang="ts" setup>
import { searchPage, SearchPageRespVO } from '@/api/search';
import * as FeedbackApi from '@/api/feedback';
import { PageParam } from '@harbor/apis';
import { useLoginStoreWithOut } from '@/store/login';
import { UFeedback, UserInfo } from '@harbor/components';
import { useUserStoreWithOut } from '@/store/user';

const userInfo = useUserStoreWithOut().user as UserInfo;
const isShow = ref(false);
const feedbackShow = ref(false);
const feedback = ref();
const inputRef = ref();
const loading = ref(false);
let total = 0;
const searchList = ref<SearchPageRespVO[]>([]);
const searchWords = ref();
const noMore = computed(() => searchList.value.length >= total);
const disabled = computed(() => loading.value || noMore.value);
const pageParams = reactive<PageParam>({ pageNo: 1, pageSize: 10 });

const pageRequest = async (isPush: boolean) => {
  if (searchWords.value.length == 0) {
    return;
  }
  const data = await searchPage({ ...pageParams, searchWords: searchWords.value });
  if (isPush) {
    searchList.value.push(...data.list);
  } else {
    searchList.value = data.list;
  }
  total = data.total;
  loading.value = false;
};

const load = () => {
  console.log('test');
  loading.value = true;
  pageParams.pageNo++;
  pageRequest(true);
};

const onInput = () => {
  if (searchWords.value.length > 0) {
    pageParams.pageNo = 1;
    pageRequest(false);
  } else {
    searchList.value = [];
  }
};

const show = () => {
  isShow.value = true;
};

const open = () => {
  searchList.value = [];
  searchWords.value = '';
  nextTick(() => {
    inputRef.value.focus();
  });
};

const onClick = async (rid: number) => {
  feedback.value = await FeedbackApi.getById(rid);
  feedbackShow.value = true;
};

defineExpose({ show });
</script>

<style lang="scss" scoped>
.root:deep(.el-dialog__header) {
  @apply m-0 p-0;
}

.root:deep(.el-dialog__body) {
  @apply p-0  w-full;
}

.root:deep(em) {
  @apply text-red-500;
}
</style>
