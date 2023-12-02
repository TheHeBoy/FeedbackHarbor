<template>
  <el-card class="mb-5">
    <div>
      客户端：
      <el-link :href="appUrl" target="_Blank" type="primary"> {{ appUrl }}</el-link>
    </div>
  </el-card>
  <el-card>
    <div class="flex flex-col space-y-4">
      <el-radio-group v-model="duration">
        <el-radio-button :label="1">当天</el-radio-button>
        <el-radio-button :label="2">周</el-radio-button>
        <el-radio-button :label="3">月</el-radio-button>
        <el-radio-button :label="4">全部</el-radio-button>
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
                :duration="2600"
                :end-val="102400"
                :start-val="0"
                class="text-xl font-bold"
              />
            </div>
          </div>
        </el-card>
      </div>
      <div>
        <el-card shadow="hover">
          <el-skeleton :loading="loading" :rows="4" animated>
            <Echart :height="350" :options="lineOptionsData" />
          </el-skeleton>
        </el-card>
      </div>
    </div>
  </el-card>
</template>
<script lang="ts" setup>
import { set } from 'lodash-es';
import { EChartsOption } from 'echarts';

import { useDesign } from '@/hooks/web/useDesign';
import type { AnalysisTotalTypes } from './types';
import { barOptions, lineOptions, pieOptions } from './echarts-data';
import { getTenantId, getTenantRouterUri } from '@/utils/auth';

defineOptions({ name: 'Home2' });

const { t } = useI18n();
const loading = ref(true);
const duration = ref(1);
const { getPrefixCls } = useDesign();
const prefixCls = getPrefixCls('panel');
const pieOptionsData = reactive<EChartsOption>(pieOptions) as EChartsOption;

let totalState = reactive<AnalysisTotalTypes>({
  users: 0,
  messages: 0,
  moneys: 0,
  shoppings: 0,
});

const getCount = async () => {
  const data = {
    users: 102400,
    messages: 81212,
    moneys: 9280,
    shoppings: 13600,
  };
  totalState = Object.assign(totalState, data);
};

// 用户来源
const getUserAccessSource = async () => {
  const data = [
    { value: 335, name: 'analysis.directAccess' },
    { value: 310, name: 'analysis.mailMarketing' },
    { value: 234, name: 'analysis.allianceAdvertising' },
    { value: 135, name: 'analysis.videoAdvertising' },
    { value: 1548, name: 'analysis.searchEngines' },
  ];
  set(
    pieOptionsData,
    'legend.data',
    data.map((v) => t(v.name)),
  );
  set(pieOptionsData, 'series.data', data);
};
const barOptionsData = reactive<EChartsOption>(barOptions) as EChartsOption;

// 周活跃量
const getWeeklyUserActivity = async () => {
  const data = [
    { value: 13253, name: 'analysis.monday' },
    { value: 34235, name: 'analysis.tuesday' },
    { value: 26321, name: 'analysis.wednesday' },
    { value: 12340, name: 'analysis.thursday' },
    { value: 24643, name: 'analysis.friday' },
    { value: 1322, name: 'analysis.saturday' },
    { value: 1324, name: 'analysis.sunday' },
  ];
  set(
    barOptionsData,
    'xAxis.data',
    data.map((v) => t(v.name)),
  );
  set(barOptionsData, 'series', [
    {
      name: t('analysis.activeQuantity'),
      data: data.map((v) => v.value),
      type: 'bar',
    },
  ]);
};

const lineOptionsData = reactive<EChartsOption>(lineOptions) as EChartsOption;

// 每月销售总额
const getMonthlySales = async () => {
  const data = [
    { estimate: 100, actual: 120, name: 'analysis.january' },
    { estimate: 120, actual: 82, name: 'analysis.february' },
    { estimate: 161, actual: 91, name: 'analysis.march' },
    { estimate: 134, actual: 154, name: 'analysis.april' },
    { estimate: 105, actual: 162, name: 'analysis.may' },
    { estimate: 160, actual: 140, name: 'analysis.june' },
    { estimate: 165, actual: 145, name: 'analysis.july' },
    { estimate: 114, actual: 250, name: 'analysis.august' },
    { estimate: 163, actual: 134, name: 'analysis.september' },
    { estimate: 185, actual: 56, name: 'analysis.october' },
    { estimate: 118, actual: 99, name: 'analysis.november' },
    { estimate: 123, actual: 123, name: 'analysis.december' },
  ];
  set(
    lineOptionsData,
    'xAxis.data',
    data.map((v) => t(v.name)),
  );
  set(lineOptionsData, 'series', [
    {
      name: t('analysis.estimate'),
      smooth: true,
      type: 'line',
      data: data.map((v) => v.estimate),
      animationDuration: 2800,
      animationEasing: 'cubicInOut',
    },
    {
      name: t('analysis.actual'),
      smooth: true,
      type: 'line',
      itemStyle: {},
      data: data.map((v) => v.actual),
      animationDuration: 2800,
      animationEasing: 'quadraticOut',
    },
  ]);
};

const getAllApi = async () => {
  await Promise.all([
    getCount(),
    getUserAccessSource(),
    getWeeklyUserActivity(),
    getMonthlySales(),
  ]);
  loading.value = false;
};

getAllApi();

const appUrl = `${import.meta.env.VITE_APP_URL + getTenantRouterUri()}/home`;
</script>

<style lang="scss" scoped></style>
