<template>
  <el-row>
    <el-col :span="1">
      <div>
        <el-avatar src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png" />
      </div>
    </el-col>
    <el-col :span="23">
      <div class="ml-5">
        <div>
          <span>无痕</span>
          <el-tag class="ml-1 mr-1 rounded-lg" type="danger">官方</el-tag>
        </div>
        <div>
          更新日志 2023.07.18 星期二 ⚠️ 功能优化（重要） ‼️验证码域名更换
          由于验证码服务变更，验证码相关会替换为新域名：turing.captcha.qcloud.com。兔小巢预计会一周内更换成新地址，对以下相关配置有影响，需要的可以自行同步配置。
          ‼️ 影响以下两种配置 1、小程序套兔小巢webview的需要配置服务域名 2、客户端会限制网页发起请求的域名
          ℹ️任何问题可以在管理后台-用户群进行咨询
        </div>
        <div class="flex justify-between">
          <span class="text-sm text-slate-400 mt-1">07月18日 10:58</span>
          <div>
            <el-popover trigger="click" placement="bottom">
              <p>举报</p>
              <template #reference>
                <el-button link type="primary" size="small">
                  <i-mdi-dots-vertical />
                </el-button>
              </template>
            </el-popover>
            <el-button link type="primary" size="small">详情</el-button>
            <el-button link type="primary" size="small">点赞</el-button>
            <el-button link type="primary" size="small">评论</el-button>
          </div>
        </div>
      </div>
    </el-col>
  </el-row>
</template>

<script lang="ts" setup></script>
