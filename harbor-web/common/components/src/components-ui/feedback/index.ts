import UFeedback from './feedback.vue';

export interface UserInfo {
  id: number;
  avatar: string;
  nickname: string;
  userType: number;
  feedbackLikeIds: number[];
  commentLikeIds: number[];
}

export { UFeedback };
