import { CACHE_KEY, useCache } from '@/hooks/web/useCache';

const { t } = useI18n(); // 国际化

/**
 * 字符权限校验
 * @param {Array} value 校验值
 * @returns {Boolean}
 */
export function checkPermi(value: string[]) {
  if (value && value instanceof Array && value.length > 0) {
    const { wsCache } = useCache();
    const permissionDatas = value;
    const all_permission = '*:*:*';
    const permissions = wsCache.get(CACHE_KEY.PERMISSION).permissions;
    const hasPermission = permissions.some((permission) => {
      return all_permission === permission || permissionDatas.includes(permission);
    });
    return !!hasPermission;
  } else {
    console.error(t('permission.hasPermission'));
    return false;
  }
}
