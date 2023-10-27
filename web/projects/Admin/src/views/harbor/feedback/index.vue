<template>
  <ContentWrap>
    <!-- 搜索工作栏 -->
    <el-form
      class="-mb-15px"
      :model="queryParams"
      ref="queryFormRef"
      :inline="true"
      label-width="68px"
    >
      <el-form-item label="创建时间" prop="createTime">
        <el-date-picker
          v-model="queryParams.createTime"
          value-format="YYYY-MM-DD HH:mm:ss"
          type="daterange"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          :default-time="[new Date('1 00:00:00'), new Date('1 23:59:59')]"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="用户类型" prop="userType">
        <el-select
          v-model="queryParams.userType"
          placeholder="请选择用户类型"
          clearable
          class="!w-240px"
        >
          <el-option label="请选择字典生成" value="" />
        </el-select>
      </el-form-item>
      <el-form-item label="用户昵称" prop="nickname">
        <el-input
          v-model="queryParams.nickname"
          placeholder="请输入用户昵称"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="反馈状态" prop="state">
        <el-select
          v-model="queryParams.state"
          placeholder="请选择反馈状态"
          clearable
          class="!w-240px"
        >
          <el-option label="请选择字典生成" value="" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button @click="handleQuery">
          <Icon icon="ep:search" class="mr-5px" />
          搜索
        </el-button>
        <el-button @click="resetQuery">
          <Icon icon="ep:refresh" class="mr-5px" />
          重置
        </el-button>
        <el-button
          type="primary"
          plain
          @click="openForm('create')"
          v-hasPermi="['harbor:feedback:create']"
        >
          <Icon icon="ep:plus" class="mr-5px" />
          新增
        </el-button>
        <el-button
          type="success"
          plain
          @click="handleExport"
          :loading="exportLoading"
          v-hasPermi="['harbor:feedback:export']"
        >
          <Icon icon="ep:download" class="mr-5px" />
          导出
        </el-button>
      </el-form-item>
    </el-form>
  </ContentWrap>

  <!-- 列表 -->
  <ContentWrap>
    <el-table v-loading="loading" :data="list" @row-click="onRowClick" highlight-current-row>
      <el-table-column label="反馈信息" width="500">
        <template #default="scope">
          <div class="flex w-full">
            <UUserAvatar :avatar="scope.row.avatar" :uid="scope.row.uid" />
            <div class="ml-2">
              <div class="flex justify-between">
                <UUserNickNameInfo :nick-name="scope.row.nickname" :type="scope.row.userType" />
              </div>
              <div class="mt-2">
                <UImageContext :contents="scope.row.content" :imgs="scope.row.imgs" />
              </div>
            </div>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="反馈标签" align="center" width="150">
        <template #default="scope">
          <UFeedbackTag :feedback-tag="scope.row.feedbackTag" />
        </template>
      </el-table-column>
      <el-table-column label="反馈状态" align="center" prop="state" />
      <el-table-column
        label="反馈时间"
        align="center"
        prop="createTime"
        :formatter="dateFormatter"
      />
      <el-table-column label="操作" align="center">
        <template #default="scope">
          <el-button
            link
            type="danger"
            @click.stop="handleDelete(scope.row.id)"
            v-hasPermi="['harbor:feedback:delete']"
          >
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <div class="w-full flex justify-center">
      <Pagination
        :total="total"
        v-model:page="queryParams.pageNo"
        v-model:limit="queryParams.pageSize"
        @pagination="getList"
      />
    </div>
  </ContentWrap>

  <!-- 表单弹窗：添加/修改 -->
  <FeedbackForm ref="formRef" @success="getList" />

  <el-drawer
    modal-class="drawer-modal"
    class="min-w-150"
    :show-close="true"
    title="反馈信息"
    direction="rtl"
    destroy-on-close
    v-model="drawer"
  >
    <UFeedback :v-model="feedbackRow" commentShow :user-info="useUserStore().getUser" />
  </el-drawer>
</template>

<script setup lang="ts">
import { dateFormatter } from '@/utils/formatTime';
import download from '@/utils/download';
import * as FeedbackApi from '@/api/harbor/feedback';
import FeedbackForm from './FeedbackForm.vue';
import {
  UFeedback,
  UFeedbackTag,
  UImageContext,
  UUserAvatar,
  UUserNickNameInfo,
} from '@harbor/components';
import { useUserStore } from '@/store/modules/user';

const message = useMessage(); // 消息弹窗
const { t } = useI18n(); // 国际化

const loading = ref(true); // 列表的加载中
const total = ref(0); // 列表的总页数
const list = ref([]); // 列表的数据
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  createTime: [],
  content: undefined,
  avatar: undefined,
  userType: undefined,
  nickname: undefined,
  state: undefined,
});
const queryFormRef = ref(); // 搜索的表单
const exportLoading = ref(false); // 导出的加载中
const drawer = ref(false);
const feedbackRow = ref(); //点击的行数据

/** 查询列表 */
const getList = async () => {
  loading.value = true;
  try {
    const data = await FeedbackApi.getFeedbackPage(queryParams);
    list.value = data.list;
    total.value = data.total;
  } finally {
    loading.value = false;
  }
};

/** 搜索按钮操作 */
const handleQuery = () => {
  queryParams.pageNo = 1;
  getList();
};

/** 重置按钮操作 */
const resetQuery = () => {
  queryFormRef.value.resetFields();
  handleQuery();
};

/** 添加/修改操作 */
const formRef = ref();
const openForm = (type: string, id?: number) => {
  formRef.value.open(type, id);
};

/** 删除按钮操作 */
const handleDelete = async (id: number) => {
  try {
    // 删除的二次确认
    await message.delConfirm();
    // 发起删除
    await FeedbackApi.deleteFeedback(id);
    message.success(t('common.delSuccess'));
    // 刷新列表
    await getList();
  } catch {}
};

/** 导出按钮操作 */
const handleExport = async () => {
  try {
    // 导出的二次确认
    await message.exportConfirm();
    // 发起导出
    exportLoading.value = true;
    const data = await FeedbackApi.exportFeedback(queryParams);
    download.excel(data, '反馈.xls');
  } catch {
  } finally {
    exportLoading.value = false;
  }
};

const onRowClick = (row: any) => {
  drawer.value = true;
  feedbackRow.value = row;
};

/** 初始化 **/
onMounted(() => {
  getList();
});
</script>
<style lang="scss" scoped>
.active-row {
  --el-table-tr-bg-color: var(--el-color-info-light-9);
}
</style>
<style lang="scss">
.drawer-modal {
  background: rgba(0, 0, 0, 0);
}
</style>
