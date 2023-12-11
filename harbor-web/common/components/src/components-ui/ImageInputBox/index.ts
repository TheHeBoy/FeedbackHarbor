import UImageInputBox from './ImageInputBox.vue';

export interface SubmitCommentProp {
  content: string;
  imgUrls: string[];
  clear: () => void;
}

export { UImageInputBox };
