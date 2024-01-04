<template>
  <el-form
    ref="registerFormRef"
    label-position="top"
    :model="registerForm"
    :rules="registerRules"
    class="w-100"
  >
    <el-form-item>
      <h2 class="w-full mb-3 text-3xl font-bold text-center">注册</h2>
    </el-form-item>
    <!-- 用户名 -->
    <el-form-item label="用户名" prop="username">
      <el-input v-model="registerForm.username" placeholder="请输入用户名"></el-input>
    </el-form-item>

    <!-- 密码 -->
    <el-form-item label="密码" prop="password">
      <el-input
        type="password"
        show-password
        v-model="registerForm.password"
        placeholder="请输入密码"
      ></el-input>
    </el-form-item>

    <!-- 邮箱 -->
    <el-form-item label="邮箱" prop="mail">
      <el-input v-model="registerForm.mail" placeholder="请输入邮箱"></el-input>
    </el-form-item>

    <!-- 验证码 -->
    <el-form-item v-if="codeShow" label="验证码" prop="captcha">
      <el-input v-model="registerForm.captcha" placeholder="请输入验证码">
        <template #append>
          <el-button
            v-if="captchaTimer <= 0"
            class="!text-white !bg-[#409eff]"
            @click="generateCaptcha"
          >
            获取验证码
          </el-button>
          <span v-else> {{ captchaTimer }}秒后可重新获取 </span>
        </template>
      </el-input>
    </el-form-item>

    <!-- 提交按钮 -->
    <el-form-item>
      <el-button :loading="loading" type="primary" class="w-full" @click="submitForm"
        >注册
      </el-button>
    </el-form-item>
    <el-form-item>
      <el-button class="w-full" @click="handleBackLogin()"> 已有账号? 返回登录</el-button>
    </el-form-item>
  </el-form>
</template>
<script lang="ts" setup>
import { ref } from 'vue';
import { useLoginState } from './useLogin';
import { emailValidate } from '@harbor/core/src/utils/validate';
import * as LoginApi from '@harbor/apis/src/login';
import { ElMessage } from 'element-plus';

defineOptions({ name: 'RegisterForm' });

const { t } = useI18n();
const { handleBackLogin } = useLoginState();
const registerFormRef = ref();
const captchaTimer = ref(0);
const codeShow = ref(false);
const loading = ref(false);
// 注册表单数据
const registerForm = ref({
  username: '',
  password: '',
  mail: '',
  captcha: '',
});

// 表单验证规则
const registerRules = ref({
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 4, max: 16, message: '账号长度为 4-16 位', trigger: 'change' },
    { pattern: '^[A-Za-z0-9]+$', message: '账号格式为数字以及字母' },
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 4, max: 16, message: '密码长度为 4-16 位', trigger: 'change' },
  ],
  mail: [
    { required: true, message: '请输入邮箱', trigger: 'blur' },
    {
      validator: (rule: any, value: any, callback: any) => {
        if (value) {
          if (emailValidate(value)) {
            codeShow.value = true;
            callback();
          } else {
            codeShow.value = false;
            callback(new Error('请输入正确的邮箱格式'));
          }
        } else {
          callback(new Error('请输入邮箱'));
        }
      },
      trigger: ['change'],
    },
  ],
  captcha: [{ required: true, message: '请输入验证码', trigger: 'blur' }],
});

// 生成验证码的逻辑，此处为示例
const generateCaptcha = async () => {
  await LoginApi.sendRegisterMailCaptcha(registerForm.value.mail);
  ElMessage.success('已发送验证码到邮箱中');
  // 设置倒计时
  captchaTimer.value = 60;
  let msgTimer = setInterval(() => {
    captchaTimer.value -= 1;
    if (captchaTimer.value <= 0) {
      clearInterval(msgTimer);
    }
  }, 1000);
};

// 提交表单的逻辑
const submitForm = () => {
  registerFormRef.value?.validate(async (valid: any) => {
    if (valid) {
      loading.value = true;
      try {
        await LoginApi.mailRegister(registerForm.value);
        ElMessage.success('注册成功');
        handleBackLogin();
      } finally {
        loading.value = false;
      }
    }
  });
};
</script>
