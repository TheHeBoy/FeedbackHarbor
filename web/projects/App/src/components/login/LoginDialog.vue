<template>
  <el-dialog v-model="isShow" width="350" title="账号登陆">
    <el-form
      ref="ruleFormRef"
      hide-required-asterisk
      label-position="top"
      :rules="LoginRules"
      :model="loginData.loginForm"
    >
      <el-form-item label="账号" prop="username">
        <el-input v-model="loginData.loginForm.username" />
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input v-model="loginData.loginForm.password" type="password" autocomplete="off" />
      </el-form-item>
    </el-form>
    <el-form-item>
      <button
        type="submit"
        class="flex w-full justify-center rounded-md bg-indigo-600 px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"
        @click="submitForm(ruleFormRef)"
      >
        登录
      </button>
    </el-form-item>
  </el-dialog>
</template>

<script lang="ts" setup>
import { FormInstance } from 'element-plus';
import { useloginStoreWithOut } from '@/store/login';
import { login } from '@/api/login';
import { setToken } from '@/utils/auth';
import { useUserStoreWithOut } from '@/store/user';

const loginDialogsStore = useloginStoreWithOut();
const userStore = useUserStoreWithOut();

const { isShow } = storeToRefs(loginDialogsStore);

const ruleFormRef = ref<FormInstance>();

const loginData = reactive({
  loginForm: {
    username: 'test',
    password: '123456',
  },
});

const LoginRules = {
  username: [{ required: true, message: '请输入用户名' }],
  password: [{ required: true, message: '请输入密码' }],
};
const reload = inject('reload') as Function;

const submitForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  await formEl.validate((valid, fields) => {
    if (valid) {
      login({
        username: loginData.loginForm.username,
        password: loginData.loginForm.password,
      }).then((data) => {
        setToken(data);
        userStore.setUserInfoAction();
        isShow.value = false;
        reload();
      });
    }
  });
};
</script>
