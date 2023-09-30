<template>
  <div>
    <u-comment :config="config" page @submit="submit" @like="likeBtn" @show-info="">
    </u-comment>
    <div class="w-full text-center">
      <p v-if="loading">加载中</p>
      <el-button v-if="!disabled" @click="more" link type="primary" class="m-auto">加载更多</el-button>
    </div>
  </div>
</template>

<script setup lang="ts">
import '~/styles/index.scss'
import emoji from '@/types/emoji'
import { PropType, reactive } from 'vue'
import { createComment, createCommentVO, getCommentPage } from '@/api/comment'
import { CommentApi, ConfigApi, SubmitParamApi, UToast, dayjs } from '~/index'
import { useUserStoreWithOut } from '@/store/user'
import { FeedbackVO } from '@/api/feedback'
import { formatDate, formatPast } from '@/utils/formatTime'
import { like, getLikeList } from '@/api/like';

const props = defineProps({
  vModel: {
    type: Object as PropType<FeedbackVO>,
    required: true,
  },
});

const useUserStore = useUserStoreWithOut();

const { user } = storeToRefs(useUserStore);

const config = reactive<ConfigApi>({
  user: {
    id: user.value.id,
    username: user.value.nickname,
    avatar: user.value.avatar,
    likeIds: [] as string[]
  },
  emoji: emoji,
  comments: [],
  total: 0,
})

const submit = ({ content, parentId, files, finish }: SubmitParamApi) => {
  const createCommentVO: createCommentVO = {
    content: content,
    feedbackId: props.vModel.id,
    parentId: parentId == null ? null : Number(parentId),
  }
  createComment(createCommentVO).then((data) => {
    const comment: CommentApi = {
      id: data.id,
      parentId: data.parentId,
      uid: config.user.id,
      content: content,
      user: {
        username: config.user.username,
        avatar: config.user.avatar,
        homeLink: '',
      },
      address: '',
      likes: 0,
      createTime: formatPast(data.createTime),
      reply: null
    }
    finish(comment)
    UToast({ message: '评论成功!', type: 'info' })
  })
}

const likeBtn = (id: string, finish: () => void) => {
  like({ rid: parseInt(id), busType: 1 }).then(() => {
    finish();
  });
}

getLikeList(1).then((data) => {
  config.user.likeIds = data;
})

const loading = ref(false);
const noMore = computed(() => config.comments.length >= config.total);
const disabled = computed(() => loading.value || noMore.value);
let params = { pageNo: 1, pageSize: 5, feedbackId: props.vModel.id };
const commentPage = () => {
  getCommentPage(params).then((data => {
    config.total = data.total;
    let comments: CommentApi[] = [];
    for (let i = 0; i < data.list.length; i++) {
      const commentVO = data.list[i];
      const replies: CommentApi[] = commentVO.replies.map((e: any) => {
        return {
          id: e.id,
          parentId: e.parentId,
          uid: e.uid,
          address: '',
          content: e.content,
          likes: e.likes,
          createTime: formatPast(e.createTime),
          user: {
            username: e.nickname,
            avatar: e.avatar,
            homeLink: ''
          }
        }
      });
      comments.push({
        id: commentVO.id,
        parentId: null,
        uid: 2,
        address: '',
        content: commentVO.content,
        likes: commentVO.likes,
        createTime: formatPast(commentVO.createTime),
        user: {
          username: commentVO.nickname,
          avatar: commentVO.avatar,
          homeLink: ''
        },
        reply: {
          total: commentVO.replies.length,
          list: replies
        }
      });
    }
    config.comments.push(...comments);
    loading.value = false;
  }))
}
const more = () => {
  params.pageNo++;
  commentPage();
}
commentPage();

</script>
