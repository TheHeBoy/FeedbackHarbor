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
                  <el-button link linksize="small">
                    <i-mdi-dots-vertical />
                  </el-button>
                </template>
              </el-popover>
              <el-button link size="small" @click="commentShow = !commentShow">评论</el-button>
              <el-button link @click="likeClick">
                <u-icon>
                  <like v-if="props.feedbackLikeIds.map(String).indexOf(str(props.vModel.id)) == -1" />
                  <liked v-else color="#1e80ff" />
                </u-icon>
                <span class="ml-1">{{ vModel.likes }}</span>
              </el-button>
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
import liked from '@/assets/svg/LikedSVG.svg?component';
import like from '@/assets/svg/LikeSVG.svg?component';
import { number } from 'vue-types';
import { str } from '~/util/basic';
const commentShow = ref(false);
const props = defineProps({
  vModel: {
    type: Object as PropType<FeedbackVO>,
    required: true,
  },
  feedbackLikeIds: {
    type: Array as PropType<Number[]>,
    required: true,
  }
});

const emit = defineEmits(['like'])
const likeClick = () => {
  emit('like', props.vModel);
}

</script>
