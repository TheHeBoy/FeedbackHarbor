import store from './index';
import { CACHE_KEY, useCache } from '@/hooks/useCache';
import { checkTenantRouterUri } from '@/api/login';
import { TenantKeyType } from '@harbor/core/src/wscache/tenant';

const { wsCache } = useCache();

export const useTenantStore = defineStore('tenant', {
  state: () => ({
    tenant: { id: 0, name: '', logo: '', routerUri: '' } as TenantKeyType,
    isSetTenant: false,
  }),
  getters: {},
  actions: {
    async setTenantAction(tenantRouterUri: string) {
      if (!tenantRouterUri) {
        return null;
      }
      let tenant = wsCache.get(CACHE_KEY.TENANT);
      if (!tenant || tenantRouterUri != tenant.routerUri) {
        tenant = await checkTenantRouterUri(tenantRouterUri);
      }
      if (tenant) {
        this.tenant = tenant;
        wsCache.set(CACHE_KEY.TENANT, tenant);
        this.isSetTenant = true;
      }
    },
  },
});

export const useTenantStoreWithOut = () => {
  return useTenantStore(store);
};
