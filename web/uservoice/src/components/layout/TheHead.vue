<template>
  <el-menu
    :default-active="activeIndex"
    :active="activeIndex"
    mode="horizontal"
    :router="true"
    :ellipsis="false"
    class="flex items-center pl-10 pr-10"
    @select="handleSelect"
  >
    <button index="1" class="flex items-center mr-10" @click="homeMenuItem?.$.vnode.el?.click()">
      <logo class="w-10 h-10 mr-5" />
      <span class="text-xl font-bold">{{ appName }}</span>
    </button>

    <el-menu-item ref="homeMenuItem" index="/">首页</el-menu-item>
    <el-menu-item index="/test">功能投票</el-menu-item>
    <div class="flex-grow"></div>
    <div v-if="!userStore.isLogin()">
      <button @click="useloginStoreWithOut().open()">登录</button>
    </div>
    <div v-else>
      <el-popover trigger="click" placement="bottom">
        <el-button @click="logoutClick" link type="primary">退出登录</el-button>
        <template #reference>
          <button>
            <el-avatar :src="user.avatar" />
          </button>
        </template>
      </el-popover>
    </div>
  </el-menu>
</template>

<script lang="ts" setup>
import { ElMenuItem } from 'element-plus';
import logo from '@/assets/svg/logo.svg?component';
import { useLayoutStoreWithOut } from '@/store/layout';
import { useloginStoreWithOut } from '@/store/login';
import { useUserStoreWithOut } from '@/store/user';
import { logout } from '@/api/login';

const userStore = useUserStoreWithOut();
const layoutStore = useLayoutStoreWithOut();
const appName = import.meta.env.VITE_APP_TITLE;
const activeIndex = ref(layoutStore.activeIndex);
userStore.setUserInfoAction();
const { user } = storeToRefs(userStore);

const homeMenuItem = ref<InstanceType<typeof ElMenuItem>>();

const handleSelect = (key: string) => {
  layoutStore.setActiveIndex(key);
};

const logoutClick = () =>{
  logout().then(async (data)=>{
    await userStore.loginOut();
  })
}
</script>
