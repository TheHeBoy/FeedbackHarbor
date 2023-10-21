import request from '@/config/axios';

export interface GroupVO {
  id: number;
  name: string;
  description: string;
  status: byte;
}

// 查询用户组列表
export const getGroupPage = async (params) => {
  return await request.get({ url: `/system/group/page`, params });
};

// 查询用户组详情
export const getGroup = async (id: number) => {
  return await request.get({ url: `/system/group/get?id=` + id });
};

// 新增用户组
export const createGroup = async (data: GroupVO) => {
  return await request.post({ url: `/system/group/create`, data });
};

// 修改用户组
export const updateGroup = async (data: GroupVO) => {
  return await request.put({ url: `/system/group/update`, data });
};

// 删除用户组
export const deleteGroup = async (id: number) => {
  return await request.delete({ url: `/system/group/delete?id=` + id });
};

// 导出用户组 Excel
export const exportGroup = async (params) => {
  return await request.download({ url: `/system/group/export-excel`, params });
};
