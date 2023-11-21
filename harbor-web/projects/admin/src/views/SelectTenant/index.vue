<template>
  <div class="bg-[var(--dark-bg-color)] flex h-full items-center justify-center">
    <Transition appear enter-active-class="animate__animated animate__bounceInRight">
      <el-card shadow="hover" class="w-100 !rounded-3xl">
        <div class="text-3xl font-bold text-center mb-4">请选择管理系统</div>
        <div class="overflow-y-auto max-h-100">
          <button
            v-for="tenant in model"
            :key="tenant.id"
            @click="onEntry(tenant)"
            @mouseenter="operation[tenant.id] = true"
            @mouseleave="operation[tenant.id] = false"
            :class="tenant.id == SystemTenantTypeEnum.SYSTEM ? 'bg-cyan-500' : 'bg-cyan-700'"
            class="text-white h-10 w-full px-3 py-2 block flex items-center rounded-1xl mt-2 hover:bg-cyan-800"
          >
            <img class="w-6 h-6" :src="tenant.logo" alt="" />
            <span class="ml-3 text-1xl">{{ tenant.name }}</span>
            <div class="flex-grow"></div>
            <div
              v-if="operation[tenant.id] && tenant.id != SystemTenantTypeEnum.SYSTEM"
              class="flex items-center"
            >
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
import { SelectTenantVO } from '@/api/system/selectTenant';
import router from '@/router';
import Icon from '@/components/Icon/src/Icon.vue';
import { setTenant } from '@/utils/auth';
import { ElLoading } from 'element-plus';
import { SystemTenantTypeEnum } from '@/utils/constants';

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
  ElMessage.success('删除成功');
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

onMounted(() => {
  getList();
});
</script>

<style lang="scss" scoped></style>
