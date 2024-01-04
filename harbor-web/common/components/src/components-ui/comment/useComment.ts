import { ref } from 'vue';
import { CommentVO, ReplyVO } from '@harbor/apis/src/comment';
import { SubmitCommentProp } from '../ImageInputBox';

export interface SubmitParamApi {
  content: string;
  parentId: number | null;
  imgs?: string[];
  reply?: ReplyVO;
  finish: (comment: CommentVO) => void;
}

export interface SubmitCommentApi extends SubmitCommentProp {
  parentId?: number;
}

let commentLikeIds = ref<number[]>([]);
let submit: (data: SubmitCommentApi) => void;
// 点击回复按钮的评论id,用于只显示一个回复框
let replayBoxId = ref(0);

export function useComment() {
  function getCommentLikeIds() {
    return commentLikeIds.value;
  }

  function addCommentLikeId(id: number) {
    commentLikeIds.value.push(id);
  }

  function setCommentLikeIds(ids: number[]) {
    commentLikeIds.value = ids;
  }

  function removeByCommentId(id: number) {
    commentLikeIds.value = commentLikeIds.value.filter((item) => item != id);
  }

  function setSubmit(data: (data: SubmitCommentApi) => void) {
    submit = data;
  }

  function getReplayBoxId() {
    return replayBoxId.value;
  }

  function setReplayBoxId(id: number) {
    replayBoxId.value = id;
  }

  return {
    getCommentLikeIds,
    setCommentLikeIds,
    addCommentLikeId,
    removeByCommentId,
    setSubmit,
    submit,
    getReplayBoxId,
    setReplayBoxId,
  };
}
