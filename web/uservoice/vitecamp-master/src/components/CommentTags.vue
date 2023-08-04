<template>
  <el-radio-group v-model="tag" size="large">
    <el-radio-button label="推荐" />
    <el-radio-button label="最新" />
    <el-radio-button label="好问题" />
  </el-radio-group>
</template>

<script lang="ts" setup>
import { ref } from 'vue';

const tag = ref('推荐');
</script>
