import { request } from '@harbor/apis';

export interface InviteUserReqVO {
  inviterUserId: number;
  inviteeUserIds: number[];
}

export interface InviteUserReqVO {
  id: number;
  avatar: string;
  nickname: string;
  tenantLogo: string;
  tenantName: string;
}

export const inviteUser = (data: InviteUserReqVO) => {
  return request.post({ url: '/system/invite/invite-user', data });
};

export const listReplay = (): Promise<InviteUserReqVO[]> => {
  return request.get({ url: '/system/invite/list-replay' });
};

export const accept = (id: number) => {
  return request.post({ url: '/system/invite/accept', params: { id: id } });
};

export const refuse = (id: number) => {
  return request.put({ url: '/system/invite/refuse', params: { id: id } });
};
