import request from '@/config/axios';

export interface SelectTenantVO {
  id: number;
  name: string;
  logo: string;
}

// 查询租户详情
export const getTenant = (id: number) => {
  return request.get({ url: '/system/select-tenant/get?id=' + id });
};

// 新增租户
export const createTenant = (data: SelectTenantVO) => {
  return request.post({ url: '/system/select-tenant/create', data });
};

// 修改租户
export const updateTenant = (data: SelectTenantVO) => {
  return request.put({ url: '/system/select-tenant/update', data });
};

// 删除租户
export const deleteTenant = (id: number) => {
  return request.delete({ url: '/system/select-tenant/delete?id=' + id });
};

// 得到所有租户
export const listTenantByUser = () => {
  return request.get({ url: '/system/select-tenant/listByUser' });
};
