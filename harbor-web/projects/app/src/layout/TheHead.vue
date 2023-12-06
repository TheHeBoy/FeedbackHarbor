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
        <img
          :src="tenant.logo ? tenant.logo : '@/assets/svg/logo.svg?url'"
          class="w-10 h-10 mr-5"
          alt=""
        />
        <span class="text-xl font-bold">{{ tenant.name ? tenant.name : appName }}</span>
      </button>

      <el-menu-item ref="homeMenuItem" index="/home">{{ t('home') }}</el-menu-item>
      <el-menu-item index="/roadmap">{{ t('roadmap') }}</el-menu-item>
      <div class="flex-grow"></div>
      <div class="flex">
        <!-- 国际化待实现 -->
        <!--                <el-tooltip :content="t('change lang')" placement="top">-->
        <!--                  <button class="icon-btn mx-2" @click="toggleLocales()">-->
        <!--                    <i-la-language class="icon-footer" />-->
        <!--                  </button>-->
        <!--                </el-tooltip>-->
        <div class="ml-5">
          <div v-if="!userStore.isLogin()">
            <button @click="useLoginStoreWithOut().open()">{{ t('signIn') }}</button>
          </div>
          <div v-else>
            <el-popover trigger="click" placement="bottom">
              <el-button @click="logoutClick" link size="small" class="w-full text-center"
                >{{ t('signOut') }}
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
import { useLoginStoreWithOut } from '@/store/login';
import { useUserStoreWithOut } from '@/store/user';
import { logout } from '@/api/login';
import { useTenantStoreWithOut } from '@/store/tenant';

const appName = import.meta.env.VITE_APP_TITLE;
const activeIndex = ref('/home');
const { t, availableLocales, locale } = useI18n();
const userStore = useUserStoreWithOut();
const user = userStore.user;
const homeMenuItem = ref<InstanceType<typeof ElMenuItem>>();
const tenant = useTenantStoreWithOut().tenant;
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
