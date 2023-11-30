<template>
  <div class="bg-[var(--dark-bg-color)] flex h-full items-center justify-center">
    <Transition appear enter-active-class="animate__animated animate__bounceInRight">
      <el-card shadow="hover" class="w-100 !rounded-3xl">
        <div class="flex relative justify-center mb-4">
          <el-dropdown placement="top" class="!absolute !left-0">
            <el-avatar :src="useUserStore().user.avatar" />
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item @click="logout" :icon="Back">登出</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
          <div class="text-3xl font-bold text-center">请选择管理系统</div>
        </div>
        <div class="overflow-y-auto max-h-100">
          <InviteList :refresh="getList" />
          <button
            v-for="tenant in model"
            :key="tenant.id"
            @click="onEntry(tenant)"
            @mouseenter="operation[tenant.id] = true"
            @mouseleave="operation[tenant.id] = false"
            :class="tenant.id == SystemIdEnum ? 'bg-cyan-500' : 'bg-cyan-700'"
            class="text-white h-10 w-full px-3 py-2 block flex items-center rounded-1xl mt-2 hover:bg-cyan-800"
          >
            <img class="w-6 h-6" :src="tenant.logo" alt="" />
            <span class="ml-3 text-1xl">{{ tenant.name }}</span>
            <div class="flex-grow"></div>
            <div v-if="operation[tenant.id] && tenant.id != SystemIdEnum" class="flex items-center">
              <Icon @click.stop="onUpdate(tenant.id)" class="mr-1" icon="ep:edit" />
              <Icon @click.stop="onDelete(tenant)" icon="ep:delete" />
            </div>
          </button>
        </div>
        <el-button link type="primary" class="w-full mt-4" @click="onCreate"
          >新建社区租户
        </el-button>
      </el-card>
    </Transition>
    <el-dialog v-model="confirmDialog" align-center width="400">
      <template #header>
        <div>你确定要删除 {{ waitDeleteTenant!.name }} 反馈社区吗?</div>
        <el-text type="danger">删除后将不能恢复！</el-text>
      </template>
      <div class="flex justify-end">
        <el-button @click="confirmDialog = false">取消</el-button>
        <el-button type="primary" @click="dialogDelete">确定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script lang="ts" setup>
import * as SelectTenantApi from '@/api/system/selectTenant';
import * as InviteLinkApi from '@/api/system/invite/link';
import { SelectTenantVO } from '@/api/system/selectTenant';
import router from '@/router';
import Icon from '@/components/Icon/src/Icon.vue';
import { setTenant } from '@/utils/auth';
import { ElLoading } from 'element-plus';
import { SystemIdEnum } from '@/utils/constants';
import InviteList from './inviteList.vue';
import { useUserStore } from '@/store/modules/user';
import { Back } from '@element-plus/icons-vue';

const message = useMessage(); // 消息弹窗

const model = ref<SelectTenantVO[]>();
const operation = ref({});
const confirmDialog = ref(false);
const onCreate = () => {
  router.push({ path: 'createTenant' });
};
const waitDeleteTenant = ref<SelectTenantVO>();

const onDelete = (tenantVO: SelectTenantVO) => {
  waitDeleteTenant.value = tenantVO;
  confirmDialog.value = true;
};

const dialogDelete = async () => {
  await SelectTenantApi.deleteTenant(waitDeleteTenant.value!.id);
  message.success('删除成功');
  getList();
  confirmDialog.value = false;
};

const getList = () => {
  SelectTenantApi.listTenantByUser().then((data: SelectTenantVO[]) => {
    model.value = data;
  });
};

const onEntry = async (tenantVO: SelectTenantVO) => {
  setTenant(tenantVO);
  const loading = ElLoading.service({
    lock: true,
    text: '正在进入系统中...',
  });
  await router.push({ path: '/' });
  loading.close();
};

const onUpdate = (tenantId: number) => {
  router.push({ path: 'createTenant', query: { tenantId } });
};

const logout = async () => {
  await useUserStore().loginOut();
  location.reload();
};

onMounted(async () => {
  const code = router.currentRoute.value.query.code as string;
  if (code) {
    try {
      await InviteLinkApi.join(code);
    } catch (ex) {
      console.error(ex);
    }
  }
  getList();
});
</script>

<style lang="scss" scoped>
:deep(.el-tooltip__trigger:focus-visible) {
  outline: unset;
}
</style>
