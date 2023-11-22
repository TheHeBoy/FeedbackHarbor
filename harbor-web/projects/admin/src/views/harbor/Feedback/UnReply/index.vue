<template>
  <FeedbackAll :reply-state="HarborFeedbackReplayStateEnum.NO_REPLY" />
</template>

<script setup lang="ts">
import { FeedbackAll } from '../index';
import { HarborFeedbackReplayStateEnum } from '@/utils/constants';
</script>
