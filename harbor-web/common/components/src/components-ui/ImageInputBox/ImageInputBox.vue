<template>
  <div
    :class="{ active: active }"
    class="w-full text-sm flex flex-col root border-1 bg-[var(--u-imageInput-bg-color)] p-2 rounded-lg"
  >
    <div :style="{ height: `${height}px` }" class="overflow-auto">
      <div
        ref="editorRef"
        class="rich-input text-sm w-full h-full outline-0"
        contenteditable="true"
        :placeholder="placeholder"
        @focus="active = true"
        @blur="onBlur"
        @paste="textPlain"
        @input="input"
        autofocus
        v-html="text"
      ></div>
    </div>
    <el-upload
      :hidden="imgList.length < 1"
      v-model:file-list="imgList"
      :headers="uploadHeaders"
      :action="action"
      list-type="picture-card"
      multiple
      :on-error="uploadError"
      :before-upload="beforeUpload"
      :on-preview="onPreview"
      :on-success="uploadSuccess"
      :accept="fileType.join(',')"
      :limit="6"
    >
      <template #trigger>
        <el-button ref="inputRef" link>
          <i-ep-plus />
        </el-button>
      </template>
    </el-upload>
    <div class="flex items-center justify-between">
      <div class="flex items-center">
        <u-emoji :emoji="emoji" @add-emoji="(val: string) => addText(val)" />
        <el-button link>
          <div @click="inputRef?.$.vnode.el?.click()">
            <UploadSVG class="w-5 h-5" />
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
      <el-button type="primary" class="rounded-lg" :disabled="disabled" @click="onSubmit">
        {{ props.contentBtn }}
      </el-button>
    </div>
    <el-image-viewer
      v-if="imgViewVisible"
      @close="imgViewVisible = false"
      :initial-index="initialIndex"
      :url-list="imgList.map((e) => e.url)"
    />
  </div>
</template>

<script setup lang="ts">
import { isEmpty } from '../../util';
import { onMounted, computed, ref, nextTick } from 'vue';
import UploadSVG from './svg/UploadSVG.svg?component';
import { ElButton, ElMessage, UploadFile } from 'element-plus';
import { SubmitCommentProp, UEmoji } from '../index';
import emoji from '@harbor/core/src/types/emoji';
import { UploadUserFile } from 'element-plus/es/components/upload/src/upload';
import { getAccessToken } from '@harbor/core/src/wscache';
import { getEnv } from '@harbor/core/src/env';

const uploadHeaders = ref({
  Authorization: 'Bearer ' + getAccessToken(),
});
defineOptions({
  name: 'UImageInputBox',
});

interface Props {
  modelValue: string;
  placeholder: string;
  contentBtn: string;
  height?: number;
  maxWords?: number;
}

const props = withDefaults(defineProps<Props>(), {
  height: 200,
  maxWords: 500,
});

const emit = defineEmits<{
  (e: 'submit', data: SubmitCommentProp): void;
  (e: 'update:modelValue', val: string): void;
}>();

// v-html不能双向绑定
const text = ref('');
const content = computed({
  get() {
    return props.modelValue;
  },
  set(value: string) {
    emit('update:modelValue', value);
  },
});
const disabled = ref(true);
const editorRef = ref<HTMLDivElement>();
const inputRef = ref();
const imgList = ref<UploadUserFile[]>([]);
const initialIndex = ref(0);
const active = ref(false);
const imgViewVisible = ref(false);
const range = ref<Range>();
const fileType = ['image/jpeg', 'image/png', 'image/gif'];
const fileSize = 5; // 5M
const env = getEnv();
const action = env.VITE_UPLOAD_URL;
const input = (e: Event) => {
  let str = (e.target as HTMLDivElement).innerHTML;
  str = str.replace(/&nbsp;|<br>| /g, '');
  disabled.value = isEmpty(str) || str.length > props.maxWords;
  content.value = str;
};

// 提交评论的数据
const onSubmit = () => {
  emit('submit', {
    content: content.value,
    imgUrls: imgList.value.map((e) => e.url!),
    clear: clearData,
  });
};

//清理提交后输入框和图片列表数据
const clearData = () => {
  // 清空评论框内容
  if (editorRef.value) {
    editorRef.value.innerHTML = '';
  }
  content.value = '';
  imgList.value = [];
  //提交按钮禁用
  disabled.value = true;
  active.value = false;
};

// 空格和换行只占用一个字符
function trimModelValueLen(): number {
  return content.value.replaceAll(/&nbsp;|<br>| /g, ' ').trim().length;
}

function textPlain(e: any) {
  e.preventDefault();
  let text: string;
  let clp = (e.originalEvent || e).clipboardData;
  if (clp) {
    text = clp.getData('text/plain') || '';
    if (text !== '') {
      document.execCommand('insertText', false, text);
    }
  }
}

function addText(val: string) {
  let selection = window.getSelection();
  if (selection) {
    selection.removeAllRanges();
    // 为空初始化光标
    if (!range.value) {
      editorRef.value?.focus();
      range.value = selection.getRangeAt(0);
    }
    // 删除选中内容
    range.value.deleteContents();

    // 添加内容
    range.value.insertNode(range.value.createContextualFragment(val));

    range.value.collapse(false);
    selection.addRange(range.value);

    content.value = editorRef.value?.innerHTML || '';
  }
}

function onPreview(file: UploadUserFile) {
  imgViewVisible.value = true;
  initialIndex.value = imgList.value.findIndex((e) => e.url == file.url);
}

function beforeUpload(rawFile: File) {
  const imgSize = rawFile.size! / 1024 / 1024 < fileSize;
  if (!fileType.includes(rawFile.type)) {
    ElMessage.warning('上传图片不符合所需的格式！');
    return;
  }
  if (!imgSize) {
    ElMessage.warning(`上传图片大小不能超过${fileSize}M！`);
    return;
  }
  return rawFile;
}

function onBlur() {
  // 记录光标
  range.value = window.getSelection()?.getRangeAt(0);
  active.value = false;
}

function uploadSuccess(response: any, uploadFile: UploadFile) {
  if (!response || !response.data) {
    ElMessage.error('图片上传失败！');
    console.log(response);
    return;
  }
  uploadFile.url = response.data;
  ElMessage.success('上传成功');
}

const uploadError = () => {
  ElMessage.error('图片上传失败，请您重新上传！');
};

defineExpose({
  focus: () => {
    nextTick(() => {
      editorRef.value?.focus();
    });
  },
});

onMounted(() => {
  editorRef.value?.focus();
});
</script>

<style lang="scss" scoped>
.root {
  --u-imageInput-bg-color: #f8f9f9;
}

:deep(.el-upload--picture-card) {
  --el-upload-picture-card-size: 80px;
}

:deep(.el-upload-list) {
  --el-upload-list-picture-card-size: 80px;
}

.active {
  @apply bg-white border-blue-300;
}

.content {
  width: 380px;
  height: 50px;
  border: 1px solid #e1e1e1;
  -webkit-user-modify: read-write-plaintext-only;
}

.text:empty:before {
  content: attr(placeholder);
  color: #bbb;
}

.text:focus {
  content: none;
}

.rich-input:empty::before {
  cursor: text;
  content: attr(placeholder);
  color: #a8abb2;
}
</style>
