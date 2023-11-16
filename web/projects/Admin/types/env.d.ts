/// <reference types="vite/client" />

declare module '*.vue' {
  import { DefineComponent } from 'vue';
  // eslint-disable-next-line @typescript-eslint/no-explicit-any, @typescript-eslint/ban-types
  const component: DefineComponent<{}, {}, any>;
  export default component;
}

interface ImportMetaEnv {
  readonly VITE_APP_TITLE: string;
  readonly VITE_APP_CAPTCHA_ENABLE: string;
  readonly VITE_BASE_URL: string;
  readonly VITE_UPLOAD_URL: string;
  readonly VITE_API_URL: string;
  readonly VITE_DROP_DEBUGGER: string;
  readonly VITE_DROP_CONSOLE: string;
  readonly VITE_SOURCEMAP: string;
  readonly VITE_OUT_DIR: string;
  readonly VITE_BASE: string;
  readonly VITE_APP_URL: string;
}

declare global {
  interface ImportMeta {
    readonly env: ImportMetaEnv;
  }
}
