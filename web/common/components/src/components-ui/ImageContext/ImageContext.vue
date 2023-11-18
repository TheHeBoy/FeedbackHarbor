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
import { useEmojiParse } from "../../hooks";
import emoji from "../../types/emoji";

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

const emojiContents = computed(() =>
  useEmojiParse(emoji.allEmoji, props.contents)
);

onMounted(() => {});
</script>

<style lang="scss" scoped></style>
