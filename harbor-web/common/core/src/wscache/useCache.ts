import WebStorageCache from 'web-storage-cache';

type CacheType = 'localStorage' | 'sessionStorage';
export const useCache = (type: CacheType = 'localStorage') => {
  const wsCache: WebStorageCache = new WebStorageCache({
    storage: type,
  });

  return {
    wsCache,
  };
};
