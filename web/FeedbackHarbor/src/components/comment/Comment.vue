<template>
  <div>
    <u-comment :config="config" page @submit="submit" @like="like" @show-info="">
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
import { formatDate } from '@/utils/formatTime'

const props = defineProps({
  vModel: {
    type: Object as PropType<FeedbackVO>,
    required: true,
  },
});

const useUserStore = useUserStoreWithOut();

const { user } = storeToRefs(useUserStore);

watch(user, () => {
  config.user.id = user.value.id;
  config.user.username = user.value.nickname;
  config.user.avatar = user.value.avatar;
});

const config = reactive<ConfigApi>({
  user: {
    id: user.value.id,
    username: user.value.nickname,
    avatar: user.value.avatar,
    // 评论id数组 建议:存储方式用户uid和评论id组成关系,根据用户uid来获取对应点赞评论id,然后加入到数组中返回
    likeIds: []
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
  if (useUserStore.isLoginAndShwolog()) {
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
        createTime: formatDate(data.createTime),
        reply: null
      }
      finish(comment)
      UToast({ message: '评论成功!', type: 'info' })
    })
  }
}

const more = () => {
  params.pageNo++;
  commentPage();
}
const loading = ref(false);
const noMore = computed(() => config.comments.length >= config.total);
const disabled = computed(() => loading.value || noMore.value);

// 点赞按钮事件 将评论id返回后端判断是否点赞，然后在处理点赞状态
const like = (id: string, finish: () => void) => {
  console.log('点赞: ' + id)
  setTimeout(() => {
    finish()
  }, 200)
}

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
          createTime: formatDate(e.createTime),
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
        createTime: formatDate(commentVO.createTime),
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
commentPage();

</script>
