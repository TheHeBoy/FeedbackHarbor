<template>
  <el-card class="box-card">
    <el-row class="w-full">
      <el-col :span="1">
        <div>
          <el-avatar :src="vModel.avatar" />
        </div>
      </el-col>
      <el-col :span="23">
        <div class="ml-5">
          <div class="flex">
            <span>{{ vModel.nickname }}</span>
            <!-- <el-tag class="ml-1 mr-1 rounded-lg" type="danger">官方</el-tag> -->
            <div class="flex-grow"></div>
            <div class="">
              <el-button link type="primary">
                <i-mdi-arrow-up class="w-7 h-7 font-bold" />
                <span class="font-bold text-lg">23</span>
              </el-button>
            </div>
          </div>
          <div>
            {{ vModel.content }}
          </div>
          <div class="flex justify-between">
            <span class="text-sm text-slate-400 mt-1">{{ formatDate(vModel.createTime) }}</span>
            <div>
              <el-popover trigger="click" placement="bottom">
                <p>举报</p>
                <template #reference>
                  <el-button link type="primary" size="small">
                    <i-mdi-dots-vertical />
                  </el-button>
                </template>
              </el-popover>
              <el-button link type="primary" size="small" @click="commentShow = !commentShow">评论</el-button>
            </div>
          </div>
        </div>
      </el-col>
    </el-row>
    <Comment v-if="commentShow" :v-model="vModel" class="ml-6" />
  </el-card>
</template>

<script lang="ts" setup>
import { PropType } from 'vue';
import { FeedbackVO } from '@/api/feedback';
import { formatDate } from '@/utils/formatTime';

const commentShow = ref(false);
defineProps({
  vModel: {
    type: Object as PropType<FeedbackVO>,
    required: true,
  },
});
</script>
