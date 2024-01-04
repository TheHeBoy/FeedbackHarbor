import { request } from '@harbor/apis';

export interface UserTeamVO {
  id: number;
  avatar: string;
  username: string;
  nickname: string;
  inviteStatus: number;
  roleIds: number[];
}

export const getUserList = (nickname: string): Promise<UserTeamVO[]> => {
  return request.get({ url: '/system/user/team/list', params: { nickname } });
};

export const queryUserListByNickName = (nickname: string): Promise<UserTeamVO[]> => {
  return request.get({ url: '/system/user/team/query', params: { nickname } });
};

export const quit = (userId: number): Promise<void> => {
  return request.delete({ url: '/system/user/team/quit', params: { userId } });
};
