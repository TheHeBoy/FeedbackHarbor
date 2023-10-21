import { withInstall } from "../../util";
import commentVue from "./src/comment.vue";

export * from "./interface";
export * from "./key";
export type CommentInstance = InstanceType<typeof commentVue>;
export const UComment = withInstall(commentVue);
export default UComment;
