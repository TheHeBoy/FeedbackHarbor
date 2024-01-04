export const useLocale = () => {
  const changeLocale = async () => {
    const { availableLocales, locale } = useI18n();
    locale.value =
      availableLocales[(availableLocales.indexOf(locale.value) + 1) % availableLocales.length];
  };

  return {
    changeLocale,
  };
};
