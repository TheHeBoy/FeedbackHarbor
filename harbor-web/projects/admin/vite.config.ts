import { resolve } from 'path';
import { defineConfig, loadEnv } from 'vite';
import { createVitePlugins } from './build/vite';

// 当前执行node命令时文件夹的地址(工作目录)
const root = process.cwd();

// 路径查找
function pathResolve(dir: string) {
  return resolve(root, '.', dir);
}

// https://vitejs.dev/config/
export default defineConfig((env) => {
  const viteEnv = loadEnv(env.mode, process.cwd());
  return {
    base: viteEnv.VITE_BASE,
    root: root,
    // 服务端渲染
    server: {
      // 是否开启 https
      https: false,
      // 端口号
      port: 8080,
      host: '0.0.0.0',
      open: true,
      proxy: {
        ['/admin-api']: {
          target: 'http://39.101.140.3:48080/',
          changeOrigin: true,
        },
      },
    },
    // 项目使用的vite插件。 单独提取到build/vite/plugin中管理
    plugins: [createVitePlugins()],
    css: {
      preprocessorOptions: {
        scss: {
          additionalData: `@use "./src/styles/variables.scss" as *;`,
          javascriptEnabled: true,
        },
      },
    },
    resolve: {
      extensions: ['.mjs', '.js', '.ts', '.jsx', '.tsx', '.json', '.scss', '.css'],
      alias: [
        {
          find: 'vue-i18n',
          replacement: 'vue-i18n/dist/vue-i18n.cjs.js',
        },
        {
          find: '@',
          replacement: `${pathResolve('src')}/`,
        },
      ],
    },
    build: {
      minify: 'terser',
      // brotliSize: false,
      terserOptions: {
        compress: {
          drop_debugger: viteEnv.VITE_DROP_DEBUGGER === 'true',
          drop_console: viteEnv.VITE_DROP_CONSOLE === 'true',
        },
      },
    },
  };
});
