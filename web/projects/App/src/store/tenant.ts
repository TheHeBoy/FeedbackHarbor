import store from './index';
import { useCache } from '@/hooks/useCache';
import { checkTenantRouterUri } from '@/api/login';

const { wsCache } = useCache();

export const useTenantStore = defineStore('tenant', {
  state: () => ({
    tenant: { id: 0, name: '', logo: '' },
    isSetTenant: false,
  }),
  getters: {},
  actions: {
    async setTenantAction(tenantRouterUri: string) {
      try {
        this.tenant = await checkTenantRouterUri(tenantRouterUri);
        this.isSetTenant = true;
      } catch (e) {
        console.log(e);
      }
    },
  },
});

export const useTenantStoreWithOut = () => {
  return useTenantStore(store);
};
