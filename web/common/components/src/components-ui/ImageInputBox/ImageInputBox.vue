<template>
  <div class="comment-box">
    <u-editor
      ref="editorRef"
      v-model:modelValue="content"
      v-model:imgList="imgList"
      :placeholder="props.placeholder"
      :min-height="props.minHeight"
      :maxWords="maxWords"
      @input="input"
      @submit="onSubmit"
    ></u-editor>
    <div class="flex items-center justify-between mt-1">
      <div class="flex items-center">
        <u-emoji
          :emoji="emoji"
          @add-emoji="(val: string) => editorRef?.addText(val)"
        />
        <el-button link>
          <div @click="inputRef?.click">
            <UploadSVG class="w-5 h-5" />
            <input
              class="hidden"
              ref="inputRef"
              type="file"
              multiple
              @change="change"
            />
          </div>
        </el-button>
        <div class="flex items-center ml-1">
          <el-text v-if="trimModelValueLen() <= maxWords" type="info">
            {{ maxWords - trimModelValueLen() }}
          </el-text>
          <el-text v-else type="warning">
            超过 {{ Math.abs(maxWords - trimModelValueLen()) }} 字符
          </el-text>
        </div>
      </div>
      <el-button type="primary" :disabled="disabled" @click="onSubmit">
        {{ props.contentBtn }}
      </el-button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { isEmpty, isNull, isImage, getImgTypes } from "../../util";
import { onMounted, ref } from "vue";
import UploadSVG from "./svg/UploadSVG.svg?component";
import { ElButton, ElMessage } from "element-plus";
import {
  CommentApi,
  EditorInstance,
  SubmitParam2Api,
  UEditor,
  UEmoji,
} from "../index";
import emoji from "../../types/emoji";

defineOptions({
  name: "UImageInputBox",
});

interface Props {
  placeholder: string;
  contentBtn: string;
  parentId?: number;
  reply?: CommentApi;
  minHeight?: number;
}

const props = withDefaults(defineProps<Props>(), {});

const content = ref("");
const disabled = ref(true);
const editorRef = ref<EditorInstance>();
const inputRef = ref<HTMLInputElement>();
const imgList = ref<File[]>([]);
const maxWords = ref<number>(500);

const input = (e: Event) => {
  let str = content.value.replace(/&nbsp;|<br>| /g, "");
  disabled.value = isEmpty(str) || str.length > maxWords.value;
};
const emit = defineEmits<{
  (e: "close"): void;
  (e: "submit", data: SubmitParam2Api): void;
}>();

// 提交评论的数据
const onSubmit = () => {
  emit("submit", {
    content: props.reply
      ? `回复 <span style="color: var(--u-color-success-dark-2);">@${props.reply.user.username}:</span> ${content.value}`
      : content.value,
    parentId: isNull(props.parentId, null),
    reply: props.reply,
    files: imgList.value,
    clear: () => {
      // 清理输入框提交的数据
      clearData();
      // 关闭评论框事件
      emit("close");
    },
  });
};

//清理提交后输入框和图片列表数据
const clearData = () => {
  // 清空评论框内容
  (editorRef.value as any).clear();
  imgList.value = [];
  //提交按钮禁用
  disabled.value = true;
};

defineExpose({
  focus: () => (editorRef as any).value?.focus(),
});

const change = (val: Event) => {
  const files = inputRef.value?.files as any; //获取选中的文件对象
  let sameFiles = [];
  if (!files) {
    return;
  }

  if (imgList.value.length + files.length > 6) {
    ElMessage.warning("图片个数不能超过6张!");
    return;
  }

  for (let i = 0; i < files.length; i++) {
    // 文件名
    let fileName = files[i].name;

    // 存在相同图片
    if (imgList.value.map((e) => e.name).includes(fileName)) {
      sameFiles.push(fileName);
      continue;
    }

    // 判断文件是否是图片类型
    if (isImage(fileName)) {
      imgList.value.push(files[i]);
    } else {
      ElMessage.warning(
        `请选择图片类型文件! 只支持[${getImgTypes().join(",")}]`
      );
    }
  }

  if (sameFiles.length > 0) {
    ElMessage.warning(`存在相同图片名! [ ${sameFiles.join(",")} ]`);
  }
};

// 空格和换行只占用一个字符
function trimModelValueLen(): number {
  return content.value.replaceAll(/&nbsp;|<br>| /g, " ").trim().length;
}

onMounted(() => {
  (editorRef as any).value?.focus();
});
</script>

<style lang="scss" scoped>
.comment-box {
  width: 100%;
  position: relative;
  overflow: hidden;
}

.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.2s;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
</style>
