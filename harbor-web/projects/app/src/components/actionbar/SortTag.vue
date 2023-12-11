<template>
  <el-radio-group v-model="tag" @change="$emit('change')">
    <el-radio-button label="0">推荐</el-radio-button>
    <el-radio-button label="1">最新</el-radio-button>
  </el-radio-group>
</template>

<script lang="ts" setup>
import { ref } from 'vue';

const tag = ref('0');
</script>
