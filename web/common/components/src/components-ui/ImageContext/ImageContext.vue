<template>
  <div>
    <UFold :unfold="unfold" :line="line">
      <div v-html="contents"></div>
    </UFold>
    <div class="flex flex-wrap">
      <template v-for="(url, index) in imgList" :key="index">
        <ElImage
          :src="url"
          class="w-16 h-16 mr-1 mt-1"
          lazy
          preview-teleported
          :preview-src-list="imgList"
          :initial-index="index"
        ></ElImage>
      </template>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { computed, onMounted, PropType } from "vue";
import { UFold } from "../fold";

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
    type: String, // 以 || 分割
  },
  line: {
    type: Number,
    default: () => 10,
  },
});

const imgList = computed(() => {
  let temp = props.imgs;
  if (!temp) return [];
  return temp?.split("||");
});

const emit = defineEmits<{}>();

onMounted(() => {});
</script>

<style lang="scss" scoped></style>
