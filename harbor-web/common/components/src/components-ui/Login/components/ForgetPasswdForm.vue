<template>
  <el-form
    ref="formRef"
    label-position="top"
    :model="registerForm"
    :rules="registerRules"
    class="w-100"
  >
    <el-form-item>
      <h2 class="w-full mb-3 text-3xl font-bold text-center">找回密码</h2>
    </el-form-item>
    <div v-if="!resetPasswdShow">
      <!-- 用户名 -->
      <el-form-item label="用户名" prop="username">
        <el-input v-model="registerForm.username" placeholder="请输入用户名"></el-input>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" class="w-full" @click="checkUsername">通过邮箱找回</el-button>
      </el-form-item>
    </div>
    <div v-else>
      <!-- 密码 -->
      <el-form-item label="新密码" prop="password">
        <el-input
          type="password"
          show-password
          v-model="registerForm.password"
          placeholder="请输入新密码"
        ></el-input>
      </el-form-item>

      <!-- 验证码 -->
      <el-form-item :label="`验证码 (已发到邮箱${maskEmail(registerForm.mail)})`" prop="captcha">
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
          >重置密码
        </el-button>
      </el-form-item>
    </div>
    <el-form-item>
      <el-button class="w-full" @click="handleBackLogin()">返回</el-button>
    </el-form-item>
  </el-form>
</template>
<script lang="ts" setup>
import { ref } from 'vue';
import { useLoginState } from './useLogin';
import * as LoginApi from '@harbor/apis/src/login';
import { ElMessage } from 'element-plus';

defineOptions({ name: 'RegisterForm' });

const { handleBackLogin } = useLoginState();
const formRef = ref();
const captchaTimer = ref(0);
const resetPasswdShow = ref(false);

const loading = ref(false);
// 注册表单数据
const registerForm = ref({
  userId: 0,
  username: '',
  password: '',
  mail: '',
  captcha: '',
});

// 表单验证规则
const registerRules = ref({
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    {
      validator: async (rule: any, value: any, callback: any) => {
        if (value) {
          const result = await LoginApi.checkUsername(registerForm.value.username);
          if (result && result.userId) {
            if (result.mail) {
              registerForm.value.mail = result.mail;
              registerForm.value.userId = result.userId;
              callback();
            } else {
              callback(new Error('该用户没有绑定邮箱'));
            }
          } else {
            callback(new Error('用户名不存在'));
          }
        }
      },
      trigger: 'submit',
    },
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 4, max: 16, message: '密码长度为 4-16 位', trigger: 'change' },
  ],
  captcha: [{ required: true, message: '请输入验证码', trigger: 'blur' }],
});

// 生成验证码的逻辑，此处为示例
const generateCaptcha = async () => {
  await LoginApi.sendResetPasswdMailCaptcha(registerForm.value.mail);
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
  formRef.value?.validate(async (valid: any) => {
    if (valid) {
      loading.value = true;
      try {
        await LoginApi.resetPasswd(registerForm.value);
        ElMessage.success('密码重置成功');
        handleBackLogin();
      } finally {
        loading.value = false;
      }
    }
  });
};

const checkUsername = () => {
  formRef.value?.validate(async (valid: any) => {
    if (valid) {
      generateCaptcha();
      resetPasswdShow.value = true;
    }
  });
};

const maskEmail = (mail: string) => {
  // 将邮箱地址按 @ 符号分割成用户名和域名部分
  const parts = mail.split('@');

  const [username, domain] = parts;
  // 隐藏用户名中间的字符，只显示首尾字符，保留域名部分
  const maskedUsername = `${username.charAt(0)}****${username.charAt(username.length - 1)}`;
  return `${maskedUsername}@${domain}`;
};
</script>
