import { EmojiApi } from "../emoji";

export interface CommentApi {
  id: number;
  parentId: number | null;
  uid: number;
  address: string;
  content: string;
  likes: number;
  contentImg?: string[];
  createTime: string;
  user: CommentUserApi;
  reply: ReplyApi | null;
}

export interface ReplyApi {
  total: number;
  list: CommentApi[];
}

export interface CommentUserApi {
  username: string;
  avatar: string;
  type: number;
  homeLink: string;
}

export interface UserApi {
  id: number;
  username: string;
  avatar: string;
  type: number;
  likeIds: number[];
}

export interface ConfigApi {
  user: UserApi;
  comments: CommentApi[];
  total: number;
  replyShowSize?: number;
}

export interface SubmitParamApi {
  content: string;
  parentId: number | null;
  files?: any[];
  reply?: CommentApi;
  finish: (comment: CommentApi) => void;
}

export interface ReplyPageParamApi {
  parentId: string;
  pageNum: number;
  pageSize: number;
  finish: (reply: ReplyApi) => void;
}
