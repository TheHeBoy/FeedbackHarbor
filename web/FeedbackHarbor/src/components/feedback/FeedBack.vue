<template>
  <el-card class="box-card">
    <el-row class="w-full">
      <el-col :span="1">
        <div>
          <el-avatar :src="vModel.avatar" />
        </div>
      </el-col>
      <el-col :span="23">
        <div class="ml-7">
          <div class="flex justify-between">
            <span>{{ vModel.nickname }}</span>
            <div>
              <i-mdi-tag-multiple :color="vModel.feedbackTag.color" class="inline w-5 h-5 mr-1" />
              <span class="text-sm"> {{ vModel.feedbackTag.nameCh }}</span>
            </div>
          </div>
          <div class="mt-2">
            <u-fold unfold line="10">
              <div v-html="contents"></div>
              <div class="flex flex-wrap">
                <template v-for="(url, index) in imgList" :key="index">
                  <ElImage
                    :src="url"
                    class="w-16 h-16 mr-1 mt-1"
                    lazy
                    :preview-src-list="imgList"
                    :initial-index="index"
                  ></ElImage>
                </template>
              </div>
            </u-fold>
          </div>
          <div class="flex justify-between">
            <span class="text-sm text-slate-400 mt-1">{{ formatPast(vModel.createTime, 'YYYY-MM-DD HH:mm') }}</span>
            <div>
              <el-popover trigger="click" placement="bottom">
                <el-button link size="small" class="w-full text-center">
                  <el-icon :size="17">
                    <reportSVG />
                  </el-icon>
                  <span class="ml-1">举报</span>
                </el-button>
                <template #reference>
                  <el-button link linksize="small">
                    <i-mdi-dots-vertical />
                  </el-button>
                </template>
              </el-popover>
              <el-button link size="small" @click="commentShow = !commentShow">
                <el-icon :size="17">
                  <commentSVG />
                </el-icon>
                <span class="ml-1">{{ vModel.commentNum }}</span>
              </el-button>
              <el-button link @click="likeClick">
                <el-icon :size="17">
                  <like v-if="props.feedbackLikeIds.map(String).indexOf(str(props.vModel.id)) == -1" />
                  <liked v-else color="#1e80ff" />
                </el-icon>
                <span class="ml-1">{{ vModel.likes }}</span>
              </el-button>
            </div>
          </div>
        </div>
        <Comment v-if="commentShow" :v-model="vModel" />
      </el-col>
    </el-row>
  </el-card>
</template>

<script lang="ts" setup>
import { PropType } from 'vue';
import { FeedbackVO } from '@/api/feedback';
import { formatPast } from '@/utils/formatTime';
import liked from '@/assets/svg/LikedSVG.svg?component';
import like from '@/assets/svg/LikeSVG.svg?component';
import commentSVG from '@/assets/svg/commentSVG.svg?component';
import reportSVG from '@/assets/svg/reportSVG.svg?component';
import { str } from '~/util/basic';
import { useEmojiParse } from '~/hooks';
import emoji from '@/types/emoji';

const commentShow = ref(false);
const props = defineProps({
  vModel: {
    type: Object as PropType<FeedbackVO>,
    required: true,
  },
  feedbackLikeIds: {
    type: Array as PropType<Number[]>,
    required: true,
  },
});

const emit = defineEmits(['like']);
const likeClick = () => {
  emit('like', props.vModel);
};

const contents = computed(() => useEmojiParse(emoji.allEmoji, props.vModel.content));
const imgList = computed(() => {
  let temp = props.vModel.imgs;
  if (!temp) return [];
  return temp?.split('||');
});
</script>
