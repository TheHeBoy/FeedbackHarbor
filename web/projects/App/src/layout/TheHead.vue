<template>
  <el-affix position="top" :offset="0">
    <el-menu
      :default-active="activeIndex"
      :active="activeIndex"
      mode="horizontal"
      :router="true"
      :ellipsis="false"
      class="flex items-center pl-20 pr-20"
    >
      <button class="flex items-center mr-10" @click="homeMenuItem?.$.vnode.el?.click()">
        <logo :color="isDark ? '#ffffff' : '#13227a'" class="w-10 h-10 mr-5" />
        <span class="text-xl font-bold">{{ appName }}</span>
      </button>

      <el-menu-item ref="homeMenuItem" index="/home">首页</el-menu-item>
      <el-menu-item index="/roadmap">产品计划</el-menu-item>
      <div class="flex-grow"></div>
      <div class="flex">
        <!-- <el-tooltip :content="t('change lang')" placement="top">
          <button class="icon-btn mx-2" @click="toggleLocales()">
            <i-la-language class="icon-footer" />
          </button>
        </el-tooltip> -->
        <el-tooltip
          class="ml-2"
          :content="isDark ? t('change light') : t('change dark')"
          placement="top"
        >
          <button class="mx-3 !outline-none" @click="toggleDark()">
            <i-ph-cloud-moon-bold v-if="isDark" class="w-6 h-6" />
            <i-ph-sun-horizon-bold v-else class="w-6 h-6" />
          </button>
        </el-tooltip>
        <div class="ml-5">
          <div v-if="!userStore.isLogin()">
            <button @click="useLoginStoreWithOut().open()">登录</button>
          </div>
          <div v-else>
            <el-popover trigger="click" placement="bottom">
              <el-button @click="logoutClick" link size="small" class="w-full text-center"
                >退出登录
              </el-button>
              <template #reference>
                <button>
                  <el-avatar :src="user.avatar" />
                </button>
              </template>
            </el-popover>
          </div>
        </div>
      </div>
    </el-menu>
  </el-affix>

  <div class="m-5">
    <router-view></router-view>
  </div>
</template>

<script lang="ts" setup>
import { ElMenuItem } from 'element-plus';
import logo from '@/assets/svg/logo.svg?component';
import { useLoginStoreWithOut } from '@/store/login';
import { useUserStoreWithOut } from '@/store/user';
import { logout } from '@/api/login';
import { isDark, toggleDark } from '@/utils/dark';

const { t, availableLocales, locale } = useI18n();
const userStore = useUserStoreWithOut();
const appName = import.meta.env.VITE_APP_TITLE;
const activeIndex = ref('/home');
const { user } = storeToRefs(userStore);

const homeMenuItem = ref<InstanceType<typeof ElMenuItem>>();

const logoutClick = () => {
  logout().then(async (data) => {
    await userStore.loginOut();
    location.reload();
  });
};

const toggleLocales = () => {
  const locales = availableLocales;
  locale.value = locales[(locales.indexOf(locale.value) + 1) % locales.length];
};
</script>
