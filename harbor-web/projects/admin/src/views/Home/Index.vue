<template>
  <el-card class="mb-5">
    <div>
      客户端：
      <el-link :href="appUrl" target="_Blank" type="primary"> {{ appUrl }}</el-link>
    </div>
  </el-card>
  <el-card>
    <div class="flex flex-col space-y-4">
      <el-radio-group v-model="duration" @change="durationChange">
        <el-radio-button :label="0">当天</el-radio-button>
        <el-radio-button :label="1">周</el-radio-button>
        <el-radio-button :label="2">月</el-radio-button>
        <el-radio-button :label="3">全部</el-radio-button>
      </el-radio-group>
      <div class="grid grid-cols-4 grid-rows-1 gap-4">
        <el-card shadow="hover">
          <div class="flex justify-between">
            <div>
              <Icon color="#40c9c6" :size="40" icon="ep:postcard" />
            </div>
            <div class="flex flex-col justify-between text-right">
              <div class="text-16px text-gray-500">新增反馈</div>
              <CountTo
                :duration="2000"
                :end-val="statisticsData.feedbackTotal"
                :start-val="0"
                class="text-xl font-bold"
              />
            </div>
          </div>
        </el-card>
      </div>
    </div>
  </el-card>
</template>
<script lang="ts" setup>
import { getTenantRouterUri } from '@/utils/auth';
import * as HomeAPi from '@/api/harbor/home';
import { formatDate } from '@/utils/formatTime';

defineOptions({ name: 'Home' });

const { t } = useI18n();
const loading = ref(true);
const statisticsData = ref({
  feedbackTotal: 0,
});
const appUrl = `${import.meta.env.VITE_APP_URL + getTenantRouterUri()}/home`;

const currentDay = () => {
  const date = new Date();
  date.setHours(0, 0, 0, 0);
  return [formatDate(date), formatDate(new Date())];
};

const week = () => {
  const date = new Date();
  date.setDate(date.getDate() - 7);
  return [formatDate(date), formatDate(new Date())];
};

const month = () => {
  const date = new Date();
  date.setDate(date.getDate() - 30);
  return [formatDate(date), formatDate(new Date())];
};

const init = async () => {
  try {
    loading.value = true;
    statisticsData.value = await HomeAPi.statistics({ createTime: durationArray[duration.value] });
  } finally {
    loading.value = false;
  }
};

const duration = ref(0);
const durationArray = [currentDay(), week(), month(), null];

const durationChange = () => {
  init();
};

onMounted(() => {
  init();
});
</script>

<style lang="scss" scoped></style>
