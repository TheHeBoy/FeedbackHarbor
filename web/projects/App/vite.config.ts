import { defineConfig, loadEnv } from 'vite';
import { resolve } from 'path';
import build from './build/vite';
// https://vitejs.dev/config/
export default defineConfig((env) => {
  // env 环境变量
  const viteEnv = loadEnv(env.mode, process.cwd());
  return {
    base: viteEnv.VITE_BASE,
    // 插件
    plugins: [build(env)],
    // 别名设置
    resolve: {
      alias: [
        {
          find: '@',
          replacement: resolve('src'),
        },
      ],
    },
    esbuild: {
      // warning: Top-level "this" will be replaced with undefined since this file is an ECMAScript module
      logOverride: { 'this-is-undefined-in-esm': 'silent' },
    },
    // 服务设置
    server: {
      host: true, // host设置为true才可以使用network的形式，以ip访问项目
      port: 80, // 端口号
      cors: true, // 跨域设置允许
      open: '/product/feedback-harbor/home', // 自动打开浏览器
    },
    build: {
      reportCompressedSize: false,
      // 消除打包大小超过500kb警告
      chunkSizeWarningLimit: 2000,
      minify: 'esbuild',
      assetsDir: 'static/assets',
      // 静态资源打包到dist下的不同目录
      rollupOptions: {
        output: {
          chunkFileNames: 'static/js/[name]-[hash].js',
          entryFileNames: 'static/js/[name]-[hash].js',
          assetFileNames: 'static/[ext]/[name]-[hash].[ext]',
        },
      },
    },
    css: {
      preprocessorOptions: {
        // 全局引入了 scss 的文件
        scss: {
          additionalData: `@use "@/assets/styles/variables.scss" as *;`,
          javascriptEnabled: true,
        },
      },
    },
  };
});
