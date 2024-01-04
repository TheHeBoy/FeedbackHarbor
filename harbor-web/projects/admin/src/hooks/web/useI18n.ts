import i18n from '@/plugins/vueI18n';

// 判断字符串是否包含中文
function hasChinese(str) {
  return /[\u4E00-\u9FA5]+/g.test(str);
}

export const useI18n = () => {
  const { t, ...methods } = i18n.global;

  const tFn = (key: string, ...arg: any[]) => {
    if (hasChinese(key)) return key;
    //@ts-ignore
    return t(key, ...arg);
  };

  return {
    ...methods,
    t: tFn,
  };
};

export const t = (key: string) => key;
