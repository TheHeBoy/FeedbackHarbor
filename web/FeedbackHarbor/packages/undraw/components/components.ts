import { Plugin } from 'vue'
import Comment from './comment'
import CommentScroll from './comment/comment-scroll'
import CommentNav from './comment/comment-nav'
import Editor from './editor'
import Fold from './fold'
import Icon from './icon'
import Emoji from './emoji'
import Toast from './toast'

export default [
  Comment,
  CommentScroll,
  CommentNav,
  Editor,
  Fold,
  Icon,
  Emoji,
  Toast
] as Plugin[]
