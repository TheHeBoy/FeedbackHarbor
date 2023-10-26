<template>
  <div>
    <UComment
      :config="config"
      upload
      page
      @submit="submit"
      @reply-page="replyPage"
      @like="likeBtn"
    >
    </UComment>
    <div class="w-full text-center">
      <p v-if="loading">加载中</p>
      <el-button v-if="!disabled" @click="more" link type="primary"
        >加载更多
      </el-button>
    </div>
  </div>
</template>

<script setup lang="ts">
import emoji from "../../types/emoji";
import { PropType, reactive, ref, computed, onMounted } from "vue";
import {
  createComment,
  createCommentVO,
  getCommentPage,
  FeedbackVO,
  like,
  getLikeList,
  uploadFiles,
  getReplyPage,
  ReplyPageParams,
} from "@harbor/apis";
import { formatPast, isNull } from "../../util";
import { ElMessage } from "element-plus";
import {
  UComment,
  CommentApi,
  ConfigApi,
  SubmitParamApi,
  UserApi,
  ReplyPageParamApi,
} from "../../components-ui";
import { UserInfo } from "../feedback";

defineOptions({
  name: "UHarborComment",
});

const props = defineProps({
  vModel: {
    type: Object as PropType<FeedbackVO>,
    required: true,
  },
  userInfo: {
    type: Object as PropType<UserInfo>,
    required: true,
  },
});

const emit = defineEmits<{}>();

const loading = ref(false);
const noMore = computed(() => config.comments.length >= config.total);
const disabled = computed(() => loading.value || noMore.value);
const config = reactive<ConfigApi>({
  user: {
    id: props.userInfo.id,
    username: props.userInfo.nickname,
    avatar: props.userInfo.avatar,
    type: props.userInfo.userType,
    likeIds: [],
  } as UserApi,
  emoji: emoji,
  comments: [],
  total: 0,
  replyShowSize: 3,
});

// 评论分页和回复分页大小都是5
let params = { pageNo: 1, pageSize: 5, feedbackId: props.vModel.id };

onMounted(() => {
  getLikeList(1).then((data) => {
    config.user.likeIds = data;
  });
  commentPage();
});

const submit = async ({ content, parentId, files, finish }: SubmitParamApi) => {
  if (content.trim().length == 0) {
    ElMessage.warning("评论不能为空");
    return;
  }

  let fileUrls: string | undefined;
  if (files && files.length > 0) {
    let formData = new FormData();
    for (let i = 0; i < files.length; i++) {
      formData.append("files", files[i]);
    }
    fileUrls = (await uploadFiles(formData)).join("||"); //格式以'||'为分割;
  }

  const createCommentVO: createCommentVO = {
    content: content,
    feedbackId: props.vModel.id,
    parentId: parentId ? Number(parentId) : undefined,
    imgs: fileUrls,
  };

  createComment(createCommentVO).then((data) => {
    const comment: CommentApi = {
      id: data.id,
      parentId: data.parentId,
      uid: config.user.id,
      content: content,
      user: {
        username: config.user.username,
        avatar: config.user.avatar,
        homeLink: "",
        type: config.user.type,
      },
      address: "",
      likes: 0,
      createTime: formatPast(data.createTime, "YYYY-MM-DD HH:mm"),
      contentImg: fileUrls,
      reply: null,
    };
    finish(comment);
    ElMessage.success("评论成功!");
    props.vModel.commentNum++;
  });
};

const likeBtn = (id: string, finish: () => void) => {
  like({ rid: parseInt(id), busType: 1 }).then(() => {
    finish();
  });
};

function convert(data: any, replyData?: any): CommentApi {
  return {
    id: data.id,
    parentId: null,
    uid: 2,
    address: "",
    content: data.content,
    likes: data.likes,
    createTime: formatPast(data.createTime, "YYYY-MM-DD HH:mm"),
    contentImg: data.imgs,
    user: {
      username: data.nickname,
      avatar: data.avatar,
      homeLink: "",
      type: data.userType,
    },
    reply:
      replyData && replyData.total > 0
        ? {
            total: replyData.total,
            list: replyData.list,
          }
        : null,
  };
}

const commentPage = async () => {
  let data = await getCommentPage(params);
  config.total = data.total;
  let comments: CommentApi[] = [];
  for (let i = 0; i < data.list.length; i++) {
    let commentVO = data.list[i];
    let replies: CommentApi[] = commentVO.replyPage.list.map((data: any) => {
      return convert(data);
    });

    comments.push(
      convert(commentVO, { list: replies, total: commentVO.replyPage.total })
    );
  }
  config.comments.push(...comments);
  loading.value = false;
};

//回复分页
const replyPage = async ({
  parentId,
  pageNum,
  pageSize,
  finish,
}: ReplyPageParamApi) => {
  console.log("why");
  let data = await getReplyPage({
    pageNo: pageNum,
    pageSize: pageSize,
    commentId: Number(parentId),
  });

  let replies: CommentApi[] = data.list.map((data: any) => {
    return convert(data);
  });
  finish({ list: replies, total: data.total });
};
const more = () => {
  params.pageNo++;
  commentPage();
};
</script>
