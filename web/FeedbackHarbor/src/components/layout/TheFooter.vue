<template>
  <div class="text-center">
    <!-- <el-tooltip :content="t('change lang')" placement="top">
      <button class="icon-btn mx-2" @click="toggleLocales()">
        <i-la-language class="icon-footer" />
      </button>
    </el-tooltip> -->
  </div>
</template>

<script setup lang="ts">
import { isDark, toggleDark } from '@/utils/dark';

const { t, availableLocales, locale } = useI18n();
const toggleLocales = () => {
  const locales = availableLocales;
  locale.value = locales[(locales.indexOf(locale.value) + 1) % locales.length];
};
</script>

<style lang="scss">
.icon-footer {
  font-size: 1.3em;
}
</style>
