<template>
  <el-row class="w-full">
    <el-col :span="1">
      <div>
        <el-avatar :src="vModel.avatar" />
      </div>
    </el-col>
    <el-col :span="23">
      <div class="ml-5">
        <div>
          <span>{{ vModel.nickname }}</span>
          <!-- <el-tag class="ml-1 mr-1 rounded-lg" type="danger">官方</el-tag> -->
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
            <el-button link type="primary" size="small">点赞</el-button>
            <el-button link type="primary" size="small" @click="commentShow = !commentShow">评论</el-button>
          </div>
        </div>
      </div>
    </el-col>
  </el-row>
  <Comment v-if="commentShow" :v-model="vModel"  class="ml-12" />
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
