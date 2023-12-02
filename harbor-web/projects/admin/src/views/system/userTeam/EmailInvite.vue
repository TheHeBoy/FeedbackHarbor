<template>
  <el-dialog
    class="!w-150"
    align-center
    v-model="dialogShow"
    title="邀请加入管理团队"
    destroy-on-close
  >
    <el-form>
      <el-form-item class="w-full">
        <div class="flex w-full">
          <el-select
            v-model="emailOrUsers"
            multiple
            filterable
            default-first-option
            remote
            :reserve-keyword="false"
            placeholder="电子邮件或用户昵称"
            class="!w-full"
            :remote-method="remoteMethod"
            :loading="loading"
          >
            <template v-if="isEmailOption && options">
              <!-- 输入的为邮箱时只显示一个-->
              <el-option :key="options" :label="options" :value="options"></el-option>
            </template>
            <template v-else>
              <el-option
                v-for="option in options as UserTeamVO[]"
                :key="option.id"
                :label="option.nickname"
                :value="option.id"
                :disabled="option.inviteStatus == SystemInviteStatusEnum.NO_REPLAY"
                class="!leading-6 !h-12"
              >
                <div class="flex items-center">
                  <el-avatar :src="option.avatar" />
                  <div class="ml-2">
                    <el-text size="large" class="block font-bold">{{ option.nickname }}</el-text>
                    <el-text size="small" class="block">@{{ option.username }}</el-text>
                  </div>
                  <el-text v-if="option.inviteStatus" size="small" type="info" class="!ml-2">
                    {{ getDictLabel(DICT_TYPE.SYSTEM_INVITE_STATUS, option.inviteStatus) }}
                  </el-text>
                </div>
              </el-option>
            </template>
          </el-select>
          <el-button class="ml-2" type="primary" @click="send">发送邀请</el-button>
        </div>
      </el-form-item>
      <el-form-item>
        <el-input v-model="emailDescribe" :autosize="{ minRows: 4 }" type="textarea" />
      </el-form-item>
    </el-form>
  </el-dialog>
</template>
<script lang="ts" setup>
import { getTenantId, getTenantName } from '@/utils/auth';
import * as UserTeamApi from '@/api/system/user/team';
import * as InviteUserApi from '@/api/system/invite/user';
import * as InviteLinkApi from '@/api/system/invite/link';
import { UserTeamVO } from '@/api/system/user/team';
import { useUserStore } from '@/store/modules/user';
import { InviteUserReqVO } from '@/api/system/invite/user';
import { DICT_TYPE, getDictLabel } from '@/utils/dict';
import { SystemInviteStatusEnum } from '@/utils/constants';
import { emailValidate } from '@/utils/validate';
import { sendInviteMail } from '@/api/system/invite/link';

const dialogShow = ref(false);
const options = ref<UserTeamVO[] | string>();
const emailOrUsers = ref([]);
const emailDescribe = ref(`加入我管理团队，一起来参与管理${getTenantName()}系统的吧！`);
const loading = ref(false);
const isEmailOption = ref(false);
const message = useMessage(); // 消息弹窗

async function remoteMethod(emailOrNickname: string) {
  if (emailOrNickname) {
    loading.value = true;
    if (emailValidate(emailOrNickname)) {
      isEmailOption.value = true;
      options.value = emailOrNickname;
    } else {
      isEmailOption.value = false;
      options.value = await UserTeamApi.queryUserListByNickName(emailOrNickname);
    }
    loading.value = false;
  } else {
    options.value = undefined;
  }
}

async function send() {
  let inviteeUserIds: number[] = [];
  let mails: string[] = [];
  for (let d of emailOrUsers.value) {
    if (emailValidate(d)) {
      mails.push(d);
    } else {
      inviteeUserIds.push(d);
    }
  }
  const userId = useUserStore().user.id;
  await InviteUserApi.inviteUser({
    inviterUserId: userId,
    inviteeUserIds: inviteeUserIds,
    tenantId: getTenantId(),
  } as InviteUserReqVO);

  await InviteLinkApi.sendInviteMail({
    mails: mails,
    tenantId: getTenantId(),
    loginUrl: import.meta.env.VITE_LOGIN_URL,
  });

  message.success('发送邀请成功');
  dialogShow.value = false;
}

/** 初始化 */
onMounted(async () => {});

defineExpose({
  open: () => {
    dialogShow.value = true;
    emailOrUsers.value = [];
  },
});
</script>
