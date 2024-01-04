import { request } from '@harbor/apis';

export interface InviteSendMailReqVO {
  mails: string[];
  tenantId: number;
  loginUrl: string;
}

export const createLink = (tenantId: number) => {
  return request.post({ url: '/system/invite/link/create', params: { tenantId } });
};

export const join = (code: string) => {
  return request.post({ url: '/system/invite/link/join', params: { code } });
};

export const getLink = (tenantId: number) => {
  return request.get({ url: '/system/invite/link/get', params: { tenantId } });
};

export const deleteLink = (code: number) => {
  return request.delete({ url: '/system/invite/link/delete', params: { code } });
};

export const sendInviteMail = (data: InviteSendMailReqVO) => {
  return request.post({ url: '/system/invite/link/send-invite-mail', data });
};
