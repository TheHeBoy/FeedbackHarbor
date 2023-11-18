<template>
  <div class="u-comment">
    <div class="comment-form">
      <div class="header">
        <span class="header-title">评论</span>
      </div>
      <div class="content">
        <div class="avatar-box">
          <button @click="$emit('userAvatar')">
            <el-avatar :size="40" :src="config.user.avatar">
              <UIcon :size="25">
                <svg
                  t="1696230292343"
                  class="icon"
                  viewBox="0 0 1024 1024"
                  version="1.1"
                  xmlns="http://www.w3.org/2000/svg"
                  p-id="4050"
                  width="200"
                  height="200"
                >
                  <path
                    d="M880.182 987.568H143.818C98.705 987.568 62 952.882 62 910.245V801.52c0-122.986 110.113-223.042 245.455-223.042h409.09C851.887 578.478 962 678.533 962 801.52v108.726c0 42.636-36.704 77.322-81.818 77.322z m-736.364-82.197l736.363 0.379V801.52c0-77.873-73.408-141.224-163.637-141.224h-409.09c-90.228 0-163.636 63.351-163.636 141.224v103.851zM511.67 547.796c-140.984 0-255.682-114.697-255.682-255.682 0-140.984 114.697-255.682 255.682-255.682 140.985 0 255.682 114.697 255.682 255.682 0.001 140.985-114.697 255.682-255.682 255.682z m0-429.546c-95.871 0-173.864 77.993-173.864 173.864S415.8 465.978 511.67 465.978s173.864-77.993 173.864-173.864S607.541 118.25 511.67 118.25z"
                    fill="#ffffff"
                    p-id="4051"
                  ></path>
                </svg>
              </UIcon>
            </el-avatar>
          </button>
        </div>
        <UImageInputBox
          ref="inputBoxRef"
          placeholder="输入评论"
          content-btn="发表评论"
          @submit="submit"
        />
      </div>
    </div>
    <div v-if="comments.length != 0" class="comment-list-wrapper">
      <CommentList :data="comments" :total="total"></CommentList>
    </div>
  </div>
</template>

<script setup lang="ts">
import { provide, toRefs, useSlots, ref } from "vue";
import CommentList from "./comment-list.vue";
import { ElAvatar } from "element-plus";
import {
  UIcon,
  CommentApi,
  ConfigApi,
  SubmitParamApi,
  ReplyPageParamApi,
  UImageInputBox,
  InjectSubmit,
} from "../../index";
import {
  InjectContentBoxApi,
  InjectContentBox,
  InjectReplyBox,
  InjectSlots,
  InjectReplyBoxApi,
  SubmitParam2Api,
} from "../key";
import { isNull } from "../../../util";

interface Props {
  config: ConfigApi;
  page?: boolean;
  relativeTime?: boolean;
}

const props = withDefaults(defineProps<Props>(), {
  page: false,
});

const { user, comments, replyShowSize, total } = toRefs(props.config);
const emit = defineEmits<{
  (
    e: "submit",
    { content, parentId, files, reply, finish }: SubmitParamApi
  ): void;
  (e: "like", id: number, finish: () => void): void;
  (
    e: "replyPage",
    { parentId, pageNum, pageSize, finish }: ReplyPageParamApi
  ): void;
  (e: "showInfo", id: number, finish: Function): void;
  (e: "userAvatar"): void;
}>();
const inputBoxRef = ref();

/**
 * 提交评论
 */
const submit = ({
  content,
  parentId,
  reply,
  files,
  clear,
}: SubmitParam2Api) => {
  // 添加评论
  const finish = (comment: CommentApi) => {
    // 清空输入框内容
    clear();
    // 提交评论添加到评论列表
    if (parentId) {
      let raw_comment = comments.value.find((v) => v.id == parentId);
      if (raw_comment) {
        let replys = raw_comment.reply;
        if (replys) {
          replys.list.unshift(comment);
          replys.total++;
        } else {
          raw_comment.reply = {
            total: 1,
            list: [comment],
          };
        }
      }
    } else {
      comments.value.unshift(comment);
    }
  };
  emit("submit", { content, parentId, reply, files, finish });
};

provide(InjectSubmit, submit);

// 点赞评论数组处理
const editLikeCount = (id: number, count: number) => {
  let tar = null;
  comments.value.forEach((v) => {
    if (v.id == id) {
      tar = v;
    } else {
      tar = v.reply?.list.find((v) => v.id == id);
    }
    if (tar) {
      tar.likes += count;
    }
  });
};

/**
 * 点赞事件
 * @param id
 */
const like = (id: number) => {
  // 点赞事件处理
  const likeIds = props.config.user.likeIds;
  emit("like", id, () => {
    if (likeIds.findIndex((item) => item == id) == -1) {
      // 点赞
      likeIds.push(id);
      editLikeCount(id, 1);
    } else {
      // 取消点赞
      let index = likeIds.findIndex((item) => item == id);
      if (index != -1) {
        likeIds.splice(index, 1);
        editLikeCount(id, -1);
      }
    }
  });
};
/**
 * 评论盒子参数或方法
 */
const contentBoxParam: InjectContentBoxApi = {
  user: user,
  like: like,
  relativeTime: isNull(props.relativeTime, false),
  showInfo: (uid, finish) => emit("showInfo", uid, finish),
};
provide(InjectContentBox, contentBoxParam);

// 回复盒子
const replyBoxParam: InjectReplyBoxApi = {
  page: props.page,
  replyPage: (parentId, pageNum, pageSize, finish) => {
    emit("replyPage", { parentId, pageNum, pageSize, finish });
  },
  replyShowSize: isNull(replyShowSize?.value, 3),
  comments: comments,
};
provide(InjectReplyBox, replyBoxParam);

/**
 * 删除当前评论
 * @param comment
 */
const remove = (comment: CommentApi) => {
  // 删除评论数据操作
  const { parentId, id } = comment;
  if (parentId) {
    let comment = comments.value.find((item) => item.id == parentId);
    let reply = comment?.reply;
    if (reply) {
      let index = reply.list.findIndex((item) => item.id == id);
      if (index != -1) {
        reply.list.splice(index, 1);
        reply.total--;
      }
    }
  } else {
    let index = comments.value.findIndex((item) => item.id == id);
    if (index != -1) {
      comments.value.splice(index, 1);
    }
  }
};

// 工具卡槽
provide(InjectSlots, useSlots());

defineExpose({
  remove: remove,
  focus: () => inputBoxRef.value.focus(),
});
</script>

<style lang="scss" scoped>
@use "../style/comment.scss";
</style>
