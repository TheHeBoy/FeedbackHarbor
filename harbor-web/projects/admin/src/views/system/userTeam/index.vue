<template>
  <div class="w-full h-full flex items-center justify-center">
    <ContentWrap class="w-[50%] min-w-130">
      <div v-hasPermi="['system:invite:invite']">
        <h1 class="font-bold">邀请成员加入您的团队</h1>
        <el-row>
          <el-col :span="18">
            <el-text>
              任何拥有邀请链接的都可以加入你的管理团队。你可以停用邀请链接来将之前分享的链接失效，之后可以再重新生成新的邀请链接。
            </el-text>
          </el-col>
          <el-col :span="6" class="flex justify-center">
            <div>
              <el-button link type="primary" @click="onCopyInviteLink">
                <Icon icon="ep:link" />
                <span class="ml-1">复制邀请链接</span>
              </el-button>
              <div v-if="linkCode" class="flex" @click="onDisableInviteLink">
                <el-button link type="primary">
                  <el-text type="danger">停用链接</el-text>
                </el-button>
              </div>
            </div>
          </el-col>
        </el-row>
        <el-row class="mt-2 flex">
          <el-col :span="18">
            <el-text>通过用户邀请，也可以发送一个邀请邮件到指定邮箱地址中进行邀请。</el-text>
          </el-col>
          <el-col :span="6" class="flex justify-center">
            <el-button @click="emailInviteRef.open()" link type="primary">
              <Icon icon="mdi-light:email" />
              <span class="ml-1">用户邮件邀请</span>
            </el-button>
          </el-col>
        </el-row>
        <el-divider />
      </div>
      <el-input v-model="nicknameQuery" placeholder="搜索用户昵称" clearable class="!w-60">
        <template #prepend>
          <el-button @click="getList()">
            <Icon icon="ep:search" />
          </el-button>
        </template>
      </el-input>
      <el-divider />
      <div v-loading="loading">
        <div v-for="data in list" :key="data.id">
          <div class="flex justify-between items-center">
            <div class="flex">
              <el-avatar :src="data.avatar" />
              <div class="ml-2">
                <el-text size="large" type="" class="block font-bold">{{ data.nickname }}</el-text>
                <el-text size="small" class="block">@{{ data.username }}</el-text>
              </div>
            </div>
            <div>
              <!-- 分配角色 -->
              <el-select
                v-model="data.roleIds"
                :disabled="!checkPermi(['system:permission:assign-user-role'])"
                multiple
                @change="assignRole(data)"
                placeholder="请选择角色"
              >
                <el-option
                  v-for="item in roleList"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id"
                />
              </el-select>
              <el-button
                v-hasPermi="['system:userteam:quit']"
                class="ml-2"
                link
                type="primary"
                @click="quit(data.id)"
                >退出
              </el-button>
            </div>
          </div>
          <el-divider />
        </div>
      </div>
    </ContentWrap>
  </div>
  <EmailInvite ref="emailInviteRef" />
</template>
<script lang="ts" setup>
import * as UserTeamApi from '@/api/system/user/team';
import * as RoleApi from '@/api/system/role';
import * as InviteLinkApi from '@/api/system/invite/link';
import { UserTeamVO } from '@/api/system/user/team';
import { checkPermi } from '@/utils/permission';
import EmailInvite from './EmailInvite.vue';
import * as PermissionApi from '@/api/system/permission';
import { getTenantId } from '@/utils/auth';

defineOptions({ name: 'SystemUserTeam' });

const message = useMessage(); // 消息弹窗
const list = ref<UserTeamVO[]>([]);
const roleList = ref<any[]>([]);
const nicknameQuery = ref('');
const linkCode = ref();
const emailInviteRef = ref();
const loading = ref(true);

async function onDisableInviteLink() {
  await InviteLinkApi.deleteLink(linkCode.value);
  linkCode.value = undefined;
  message.success('停用成功');
}

async function onCopyInviteLink() {
  if (!linkCode.value) {
    linkCode.value = await InviteLinkApi.createLink(getTenantId());
  }
  const inviteLink = `${import.meta.env.VITE_LOGIN_URL}?code=${linkCode.value}`;

  //执行复制
  navigator.clipboard.writeText(inviteLink).then(
    () => {
      message.success(`复制成功：${inviteLink}`);
    },
    () => {
      message.error('复制失败');
    },
  );
}

async function getList() {
  loading.value = true;
  try {
    list.value = await UserTeamApi.getUserList(nicknameQuery.value);
  } finally {
    loading.value = false;
  }
}

async function quit(userId: number) {
  await message.confirm('确定要退出管理团队吗');
  await UserTeamApi.quit(userId);
  getList();
  message.success('成功退出');
}

async function assignRole(vo: UserTeamVO) {
  loading.value = true;
  try {
    await PermissionApi.assignUserRole({
      userId: vo.id,
      roleIds: vo.roleIds,
    });
  } finally {
    loading.value = false;
  }
}

/** 初始化 */
onMounted(async () => {
  getList();
  roleList.value = await RoleApi.getSimpleRoleList();
  if (checkPermi(['system:invite:invite'])) {
    linkCode.value = await InviteLinkApi.getLink(getTenantId());
  }
});
</script>
