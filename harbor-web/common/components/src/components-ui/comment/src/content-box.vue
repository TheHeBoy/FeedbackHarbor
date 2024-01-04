<template>
  <div class="w-full flex pt-4" :class="{ reply: props.reply }">
    <UUserAvatar :size="35" :avatar="data.avatar" />
    <div class="w-full ml-2">
      <UUserNickNameInfo :nick-name="data.nickname" :type="data.userType" />
      <div @mouseenter="moreActionShow = true" @mouseleave="moreActionShow = false">
        <UImageContext :contents="data.content" :imgs="data.imgs" />
        <div class="flex w-full justify-between mt-1">
          <URelativelyTime :time="data.createTime" />
          <div class="flex">
            <el-button link @click="onLike">
              <span class="mr-1">{{ data.likes || '' }}</span>
              <likeNoSVG class="icon-btn" v-if="getCommentLikeIds().indexOf(data.id) == -1" />
              <likeSVG v-else class="icon-btn" color="#1e80ff" />
            </el-button>
            <el-button link @click="onReply(data.id)">回复</el-button>
          </div>
        </div>
      </div>
      <div v-if="state.active && getReplayBoxId() == data.id">
        <UImageInputBox
          ref="commentRef"
          :placeholder="`回复 @${data.nickname}...`"
          content-btn="发表"
          class="mt-4"
          :height="50"
          @submit="onSubmit"
          v-model="inputContent"
        />
      </div>
      <!-- 回复列表 -->
      <slot></slot>
    </div>
  </div>
</template>

<script setup lang="ts">
import { nextTick, ref, reactive } from 'vue';
import likeSVG from '../svg/likeSVG.svg?component';
import likeNoSVG from '../svg/likeNoSVG.svg?component';
import { CommentVO } from '@harbor/apis/src/comment';
import * as LikeApi from '@harbor/apis/src/like';
import { useComment } from '../useComment';
import {
  UImageInputBox,
  UUserNickNameInfo,
  UImageContext,
  URelativelyTime,
  UUserAvatar,
} from '../../index';
import { SubmitCommentProp } from '../../ImageInputBox';
import { BusTypeVO } from '@harbor/apis/src/like';

interface Props {
  reply?: boolean;
  data: CommentVO;
  id: number;
}

const props = defineProps<Props>();

const state = reactive({
  active: false,
});
const {
  setReplayBoxId,
  getReplayBoxId,
  submit,
  getCommentLikeIds,
  addCommentLikeId,
  removeByCommentId,
} = useComment();

const commentRef = ref();
const inputContent = ref('');
const moreActionShow = ref(false);

//点击回复按钮打开输入框
function onReply(id: number) {
  state.active = !state.active;
  if (state.active) {
    setReplayBoxId(id);
    nextTick(() => {
      commentRef.value?.focus();
    });
  }
}

const onSubmit = (data: SubmitCommentProp) => {
  data.content = `回复 <span style="color: #50cf97;">@${props.data.nickname}:</span> ${data.content}`;
  submit({ ...data, parentId: props.id });
  state.active = false;
};
const onLike = async () => {
  const cid = props.data.id;
  const likeFlag = await LikeApi.like({ rid: cid, busType: BusTypeVO.Comment }).then();
  if (likeFlag) {
    addCommentLikeId(cid);
    props.data.likes++;
  } else {
    removeByCommentId(props.data.id);
    props.data.likes--;
  }
};
</script>

<style lang="scss" scoped>
.reply {
  @apply pl-4 p-0
  .el-avatar {
    --el-avatar-size: 24px !important;
  }
}

.icon-btn {
  @apply w-4 h-4;
}
</style>
