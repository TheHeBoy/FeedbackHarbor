<template>
  <el-radio-group v-model="tag" size="large" @change="$emit('change')">
    <el-radio-button label="推荐" />
    <el-radio-button label="最新" />
  </el-radio-group>
</template>

<script lang="ts" setup>
import { ref } from 'vue';

const tag = ref('推荐');
</script>
