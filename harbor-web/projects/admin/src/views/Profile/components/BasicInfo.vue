<template>
  <Form ref="formRef" :labelWidth="80" :rules="rules" :schema="schema"></Form>
  <XButton :title="t('common.save')" @click="submit()" />
  <XButton :title="t('common.reset')" type="danger" @click="init()" />
</template>
<script lang="ts" setup>
import type { FormRules } from 'element-plus';
import { FormSchema } from '@/types/form';
import type { FormExpose } from '@/components/Form';
import {
  getUserProfile,
  updateUserProfile,
  UserProfileUpdateReqVO,
} from '@/api/system/user/profile';

defineOptions({ name: 'BasicInfo' });

const { t } = useI18n();
const message = useMessage(); // 消息弹窗
// 表单校验
const rules = reactive<FormRules>({
  nickname: [{ required: true, message: t('profile.rules.nickname'), trigger: 'blur' }],
  email: [
    {
      type: 'email',
      message: t('profile.rules.truemail'),
      trigger: ['blur', 'change'],
    },
  ],
  mobile: [
    {
      pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
      message: t('profile.rules.truephone'),
      trigger: 'blur',
    },
  ],
});
const schema = reactive<FormSchema[]>([
  {
    field: 'nickname',
    label: t('profile.user.nickname'),
    component: 'Input',
  },
  {
    field: 'mobile',
    label: t('profile.user.mobile'),
    component: 'Input',
  },
  {
    field: 'email',
    label: t('profile.user.email'),
    component: 'Input',
  },
]);
const formRef = ref<FormExpose>(); // 表单 Ref
const submit = () => {
  const elForm = unref(formRef)?.getElFormRef();
  if (!elForm) return;
  elForm.validate(async (valid) => {
    if (valid) {
      const data = unref(formRef)?.formModel as UserProfileUpdateReqVO;
      await updateUserProfile(data);
      message.success(t('common.updateSuccess'));
      await init();
    }
  });
};
const init = async () => {
  const res = await getUserProfile();
  unref(formRef)?.setValues(res);
};
onMounted(async () => {
  await init();
});
</script>
