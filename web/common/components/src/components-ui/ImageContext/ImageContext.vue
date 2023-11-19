<template>
  <div>
    <UFold :unfold="unfold" :line="line">
      <div v-html="emojiContents"></div>
    </UFold>
    <div class="flex flex-wrap">
      <template v-for="(url, index) in imgs" :key="index">
        <ElImage
          :src="url"
          class="w-16 h-16 mr-1 mt-1"
          lazy
          preview-teleported
          :preview-src-list="imgs"
          :initial-index="index"
        ></ElImage>
      </template>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { computed, onMounted, PropType } from "vue";
import { UFold } from "../fold";
import emoji from "../../types/emoji";
import { Emoji } from "../emoji";

defineOptions({
  name: "UImageContext",
});

const props = defineProps({
  contents: {
    type: String,
    required: true,
  },
  unfold: {
    type: Boolean,
    default: () => true,
  },
  imgs: {
    type: Array as PropType<string[]>,
    default: () => [],
  },
  line: {
    type: Number,
    default: () => 10,
  },
});

const emit = defineEmits<{}>();

const useEmojiParse = (allEmoji: Emoji, val: string): string => {
  //解析表情
  const reg = /\[.+?\]/g;
  val = val.replace(reg, (str: any) => {
    const emojiPath = allEmoji[str];
    //表情库不存在的就默认返回原字符串
    if (!emojiPath) {
      return str;
    }
    return [
      '<img src="',
      emojiPath,
      '" width="20" height="20" alt="',
      str,
      '" title="',
      str,
      '" style="margin: 0 1px; vertical-align: text-bottom; display: inline"',
      "/>",
    ].join("");
  });
  return val;
};

const emojiContents = computed(() =>
  useEmojiParse(emoji.allEmoji, props.contents)
);

onMounted(() => {});
</script>

<style lang="scss" scoped></style>
