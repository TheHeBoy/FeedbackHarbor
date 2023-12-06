<template>
  <div class="comment" :class="{ reply: props.reply }">
    <UUserAvatar :avatar="data.user.avatar" />
    <div class="comment-primary">
      <UUserNickNameInfo
        :nick-name="data.user.username"
        :type="data.user.type"
      />
      <div @mouseenter="reportShow = true" @mouseleave="reportShow = false">
        <UImageContext :contents="data.content" :imgs="data.contentImg" />
        <div class="flex w-full justify-between mt-1">
          <URelativelyTime :time="data.createTime" />
          <UActionBar
            :reportShow="reportShow"
            :like-num="data.likes"
            :comment-num="data.reply?.total"
            :is-like="user.likeIds.indexOf(data.id) == -1"
            @onLike="like(data.id)"
            @onComment="reply"
            type="comment"
          />
        </div>
      </div>
      <div v-if="state.active">
        <UImageInputBox
          ref="commentRef"
          :parent-id="id"
          :placeholder="`回复 @${data.user.username}...`"
          :reply="data"
          content-btn="发布"
          style="margin-top: 12px"
          @close="state.active = false"
          @submit="submit"
        />
      </div>
      <!-- 回复列表 -->
      <slot></slot>
    </div>
  </div>
</template>

<script setup lang="ts">
import { inject, nextTick, ref, reactive } from "vue";
import {
  CommentApi,
  UUserAvatar,
  InjectSubmit,
  UUserNickNameInfo,
  UImageContext,
  URelativelyTime,
  UImageInputBox,
  UActionBar,
  InjectReplyShowAction,
} from "../../../components-ui";
import { InjectContentBox, InjectContentBoxApi, ReplyShowAction } from "../key";

interface Props {
  reply?: boolean;
  data: CommentApi;
  id: number;
}

const props = defineProps<Props>();

const submit = inject(InjectSubmit);

const state = reactive({
  active: false,
});

const commentRef = ref();
const reportShow = ref(false);
const { like, user } = inject(InjectContentBox) as InjectContentBoxApi;
const { setActiveShow } = inject(InjectReplyShowAction) as ReplyShowAction;

//点击回复按钮打开输入框
function reply() {
  state.active = !state.active;
  if (state.active) {
    setActiveShow(() => {
      state.active = false;
    });
    nextTick(() => {
      commentRef.value?.focus();
    });
  }
}
</script>

<style lang="scss" scoped>
@use "../style/content-box.scss";
</style>
