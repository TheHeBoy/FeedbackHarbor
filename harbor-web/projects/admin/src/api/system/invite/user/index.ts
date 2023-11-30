import { request } from '@harbor/apis';

export interface InviteUserReqVO {
  inviterUserId: number;
  inviteeUserIds: number[];
  tenantId: number;
}

export interface InviteUserReqVO {
  id: number;
  avatar: string;
  nickname: string;
  tenantLogo: string;
  tenantName: string;
}

export const inviteUser = (data: InviteUserReqVO) => {
  return request.post({ url: '/system/invite/user/invite-user', data });
};

export const listReplay = (): Promise<InviteUserReqVO[]> => {
  return request.get({ url: '/system/invite/user/list-replay' });
};

export const accept = (id: number) => {
  return request.post({ url: '/system/invite/user/accept', params: { id } });
};

export const refuse = (id: number) => {
  return request.put({ url: '/system/invite/user/refuse', params: { id } });
};
