<template>
  <div class="flex">
    <!--    todo 举报功能-->
    <!--    <el-button link v-if="reportShow" @click="reportDialogShow = true"-->
    <!--      >举报-->
    <!--    </el-button>-->
    <el-button link @click="$emit('onComment')">
      <span class="mr-1">{{ commentNum || '' }}</span>
      <commentSVG class="w-4 h-4" />
    </el-button>
  </div>
  <el-dialog title="举报" :width="580" v-model="reportDialogShow">
    <el-form ref="formRef" :rules="rules" label-position="top">
      <el-form-item>
        <el-radio-group size="large" v-model="reportType" @change="reportTypeChange">
          <el-radio size="large" :label="1">广告或营销信息</el-radio>
          <el-radio size="large" :label="2">恶意人身攻击</el-radio>
          <el-radio size="large" :label="3">色情或有害信息</el-radio>
          <el-radio size="large" :label="4">政治敏感言论</el-radio>
        </el-radio-group>
      </el-form-item>

      <el-radio-group size="large" v-model="reportType" @change="reportTypeChange">
        <el-radio size="large" :label="99">其他原因</el-radio>
      </el-radio-group>
      <el-form-item v-if="reportOtherShow" prop="otherReason">
        <el-input placeholder="举报原因" :rows="5" type="textarea" />
      </el-form-item>
      <el-form-item>
        <div class="w-full flex justify-center">
          <el-button class="w-30" @click="reportDialogShow = false">取消</el-button>
          <el-button type="primary" class="w-30" @submit="submit">确认</el-button>
        </div>
      </el-form-item>
    </el-form>
  </el-dialog>
</template>

<script lang="ts" setup>
import { reactive, ref } from 'vue';
import commentSVG from './svg/commentSVG.svg?component';
import { FormInstance, FormRules } from 'element-plus';

defineOptions({
  name: 'UActionBar',
});

const props = defineProps({
  type: {
    type: String, // 类型为 feedback 和 comment
    required: true,
  },
  isLike: {
    // 点赞状态
    type: Boolean,
    require: true,
  },
  likeNum: {
    // 点赞数
    type: Number,
    require: true,
  },
  commentNum: {
    // 评论数
    type: Number,
    require: true,
  },
  reportShow: {
    // 举报按钮显示
    type: Boolean,
    default: () => true,
  },
});
const emit = defineEmits<{
  (e: 'onLike'): void;
  (e: 'onComment'): void;
}>();

const reportDialogShow = ref(false);
const reportType = ref(1);
const reportOtherShow = ref(false);

const reportTypeChange = () => {
  reportOtherShow.value = reportType.value == 99;
};

const rules = reactive<FormRules>({
  otherReason: [
    {
      required: true,
      message: '请输入举报原因',
    },
  ],
});

const formRef = ref<FormInstance>();
const submit = () => {
  formRef.value?.validate(async (valid: any) => {});
};
</script>

<style lang="scss" scoped>
.icon-btn {
  @apply w-4 h-4;
}
</style>
