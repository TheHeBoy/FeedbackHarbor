import { ReplyApi } from "./.";
import { InjectionKey, Ref } from "vue";
import { CommentApi, ReplyPageParamApi, UserApi } from "./interface";

export const InjectSlots = Symbol();

// 输入框
export interface SubmitParam2Api {
  content: string;
  parentId: number | null;
  replyId?: number | null;
  reply?: CommentApi;
  files?: any[];
  clear: () => void;
}

export const InjectSubmit: InjectionKey<
  ({ content, parentId, files, clear }: SubmitParam2Api) => void
> = Symbol();

// 内容盒子
export interface InjectContentBoxApi {
  user: Ref<UserApi>;
  relativeTime: boolean;
  like: (id: number) => void;
  showInfo: (id: number, finish: Function) => void;
}

export const InjectContentBox: InjectionKey<InjectContentBoxApi> = Symbol();

// 回复盒子
export interface InjectReplyBoxApi {
  page: boolean;
  replyShowSize: number;
  comments: Ref<CommentApi[]>;
  replyPage: (
    parentId: number,
    pageNum: number,
    pageSize: number,
    finish: (reply: ReplyApi) => void
  ) => void;
}

export const InjectReplyBox: InjectionKey<InjectReplyBoxApi> = Symbol();
