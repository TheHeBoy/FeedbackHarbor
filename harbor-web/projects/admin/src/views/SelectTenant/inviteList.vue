<template>
  <div
    v-loading="loading"
    v-for="invite in inviteList"
    :key="invite.id"
    class="!text-white rounded-1xl h-15 w-full px-3 block flex items-center bg-cyan-900"
  >
    <div class="flex justify-between flex-grow">
      <div class="flex flex-col items-center">
        <el-avatar class="!w-6 !h-6" :src="invite.avatar"></el-avatar>
        <span class="text-sm">{{ invite.nickname }}</span>
      </div>
      <div class="flex items-center">
        <Icon :size="20" icon="mdi:invite" />
        <span class="text-sm ml-1">邀请加入</span>
      </div>
      <div class="flex flex-col items-center">
        <img class="w-6 h-6" :src="invite.tenantLogo" alt="" />
        <span class="text-sm">{{ invite.tenantName }}</span>
      </div>
    </div>
    <div class="mx-2 h-full w-0.1 bg-white" />
    <div class="flex justify-center h-full w-8">
      <el-dropdown trigger="click" class="dropdown">
        <el-button link color="#000000">
          <Icon :size="30" icon="ep:arrow-down" />
        </el-button>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item @click="accept(invite.id)">
              <Icon :size="20" icon="ep:select" />
              <span class="m-1">接受</span>
            </el-dropdown-item>
            <el-dropdown-item @click="refuse(invite.id)">
              <Icon :size="20" icon="ep:close-bold" />
              <span class="m-1">拒绝</span>
            </el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { onMounted } from 'vue';
import * as InviteApi from '@/api/system/invite/user';
import { InviteUserReqVO } from '@/api/system/invite/user';
import Icon from '@/components/Icon/src/Icon.vue';

const message = useMessage(); // 消息弹窗
const inviteList = ref<InviteUserReqVO[]>([]);
const loading = ref(false);
const props = defineProps({
  refresh: {
    type: Function,
    required: true,
  },
});

async function accept(id: number) {
  await InviteApi.accept(id);
  message.success('加入成功');
  refreshList();
}

async function refuse(id: number) {
  await message.delConfirm('你确定要拒绝此邀请吗？');
  await InviteApi.refuse(id);
  refreshList();
}

async function getList() {
  loading.value = true;
  try {
    inviteList.value = await InviteApi.listReplay();
  } finally {
    loading.value = false;
  }
}

function refreshList() {
  getList();
  props.refresh();
}

onMounted(() => {
  getList();
});
</script>

<style lang="scss" scoped>
.dropdown:deep(.el-tooltip__trigger:focus-visible) {
  outline: unset;
}
</style>
