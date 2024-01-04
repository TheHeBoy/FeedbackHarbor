<template>
  <div class="u-emoji">
    <el-popover
      :placement="placement"
      popper-class="emoji-popover"
      :width="250"
      trigger="click"
      @before-enter="onBefore"
    >
      <div class="face-tooltip-head">
        <label
          v-for="(item, index) in faceList"
          :key="index"
          :class="activeIndex == index ? 'active' : ''"
          @click="change(index)"
        >
          <img :src="item" alt="" />
        </label>
      </div>

      <div class="emoji-body">
        <div
          class="emjio-container"
          :style="{ transform: `translateX(${offsetX}%)` }"
        >
          <div
            v-for="(list, index) in emojis"
            :key="index"
            class="emoji-wrapper"
          >
            <el-scrollbar>
              <div style="padding: 0 5px">
                <span
                  v-for="(value, key) in list"
                  :key="key"
                  class="emoji-item"
                  @click="$emit('addEmoji', key as unknown as string)"
                >
                  <el-image
                    :src="value"
                    :title="String(key)"
                    class="emoji"
                    style="width: 24px; height: 24px; margin: 5px"
                    lazy
                  ></el-image>
                </span>
              </div>
            </el-scrollbar>
          </div>
        </div>
      </div>

      <template #reference>
        <el-button link>
          <EmojiSVG  class="w-5 h-5"/>
        </el-button>
      </template>
    </el-popover>
  </div>
</template>

<script setup lang="ts">
import { ref } from "vue";
import { EmojiApi } from "./interface";
import EmojiSVG from "./svg/EmojiSVG.svg?component";
import { ElPopover, ElImage, ElScrollbar } from "element-plus";

defineOptions({
  name: "UEmoji",
});

interface Props {
  emoji: EmojiApi;
  placement?: any;
}

const props = withDefaults(defineProps<Props>(), {
  placement: "bottom",
});

const activeIndex = ref(0);
const offsetX = ref(0);
const emojis = ref(new Array(2));
const { emojiList, faceList } = props.emoji as EmojiApi;

const emit = defineEmits<{
  (e: "addEmoji", key: string): void;
}>();

function change(val: number) {
  activeIndex.value = val;
  switch (val) {
    case 0:
      offsetX.value = 0;
      break;
    case 1:
      offsetX.value = -50;
      emojis.value[1] = emojiList[1];
      break;
  }
}

function onBefore() {
  emojis.value[0] = emojiList[0];
}
</script>

<style lang="scss">
.emoji-popover {
  padding: 12px 0 !important;
}
</style>

<style lang="scss" scoped>
@use "./style/index.scss";
</style>
