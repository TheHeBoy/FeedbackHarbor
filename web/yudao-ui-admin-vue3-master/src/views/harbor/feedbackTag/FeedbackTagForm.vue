<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      v-loading="formLoading"
    >
      <el-form-item label="标签名中文" prop="nameCh">
        <el-input v-model="formData.nameCh" placeholder="请输入标签名中文" />
      </el-form-item>
      <el-form-item label="标签名英语" prop="nameEn">
        <el-input v-model="formData.nameEn" placeholder="请输入标签名英语" />
      </el-form-item>
      <el-form-item label="标签顺序" prop="sort">
        <el-input-number
          :min="1"
          controls-position="right"
          v-model="formData.sort"
          placeholder="标签顺序"
        />
      </el-form-item>
      <el-form-item label="标签颜色" prop="color">
        <el-color-picker v-model="formData.color" size="large" :predefine="predefineColors" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="submitForm" type="primary" :disabled="formLoading">确 定</el-button>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
  </Dialog>
</template>
<script setup lang="ts">
import * as FeedbackTagApi from '@/api/harbor/feedbackTag'

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  nameCh: undefined,
  nameEn: undefined,
  sort: undefined,
  color: undefined
})
const formRules = reactive({
  nameCh: [{ required: true, message: '标签名中文不能为空', trigger: 'blur' }],
  nameEn: [{ required: true, message: '标签名英语不能为空', trigger: 'blur' }],
  sort: [{ required: true, message: '标签顺序不能为空', trigger: 'blur' }],
  color: [{ required: true, message: '标签颜色不能为空', trigger: 'blur' }]
})
const formRef = ref() // 表单 Ref

/** 打开弹窗 */
const open = async (type: string, id?: number) => {
  dialogVisible.value = true
  dialogTitle.value = t('action.' + type)
  formType.value = type
  resetForm()
  // 修改时，设置数据
  if (id) {
    formLoading.value = true
    try {
      formData.value = await FeedbackTagApi.getFeedbackTag(id)
    } finally {
      formLoading.value = false
    }
  }
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗

/** 提交表单 */
const emit = defineEmits(['success']) // 定义 success 事件，用于操作成功后的回调
const submitForm = async () => {
  // 校验表单
  if (!formRef) return
  const valid = await formRef.value.validate()
  if (!valid) return
  // 提交请求
  formLoading.value = true
  try {
    const data = formData.value as unknown as FeedbackTagApi.FeedbackTagVO
    if (formType.value === 'create') {
      await FeedbackTagApi.createFeedbackTag(data)
      message.success(t('common.createSuccess'))
    } else {
      await FeedbackTagApi.updateFeedbackTag(data)
      message.success(t('common.updateSuccess'))
    }
    dialogVisible.value = false
    // 发送操作成功的事件
    emit('success')
  } finally {
    formLoading.value = false
  }
}

/** 重置表单 */
const resetForm = () => {
  formData.value = {
    nameCh: undefined,
    nameEn: undefined,
    sort: undefined,
    color: undefined
  }
  formRef.value?.resetFields()
}

const predefineColors = ref([
  '#ff4500',
  '#ff8c00',
  '#ffd700',
  '#90ee90',
  '#00ced1',
  '#1e90ff',
  '#c7158577'
])
</script>
