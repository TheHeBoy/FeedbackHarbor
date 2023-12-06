import { defineConfig } from 'vite-plugin-windicss';
import typography from 'windicss/plugin/typography';
import colors from 'windicss/colors';
import path from 'path';

function range(size, startAt = 1) {
  return Array.from(Array(size).keys()).map((i) => i + startAt);
}

export default defineConfig({
  extract: {
    include: [
      '**/index.html',
      '**/*.{js,ts,jsx,tsx,vue}',
      '../../common/components/**/*.{js,ts,jsx,tsx,vue}',
    ],
    exclude: ['node_modules', '../../common/components/**/node_modules'],
  },
  safelist: 'prose prose-sm m-auto text-left',
  darkMode: 'class',
  attributify: false,
  plugins: [
    typography(),
    ({ addComponents }) => {
      const obj = {};
      range(50).map((i) => {
        obj[`.border-top-${i}`] = {
          borderTopWidth: `${i}px`,
        };
        obj[`.border-left-${i}`] = {
          borderLeftWidth: `${i}px`,
        };
        obj[`.border-right-${i}`] = {
          borderRightWidth: `${i}px`,
        };
        obj[`.border-bottom-${i}`] = {
          borderBottomWidth: `${i}px`,
        };
      });
      addComponents({
        '.hover-trigger': {
          display: 'flex',
          height: '100%',
          padding: '1px 10px 0',
          cursor: 'pointer',
          alignItems: 'center',
          transition: 'background var(--transition-time-02)',
          '&:hover': {
            backgroundColor: 'var(--top-header-hover-color)',
          },
        },
        '.dark .hover-trigger': {
          '&:hover': {
            backgroundColor: 'var(--el-bg-color-overlay)',
          },
        },
        ...obj,
      });
    },
  ],
  theme: {
    fontFamily: {
      sans: ['Open Sans', 'ui-sans-serif', 'system-ui'],
      serif: ['Montserrat', 'ui-serif', 'Georgia'],
      mono: ['Fira Sans', 'ui-monospace', 'SFMono-Regular'],
    },
    extend: {
      typography: {
        DEFAULT: {
          css: {
            maxWidth: '65ch',
            color: 'inherit',
            a: {
              color: 'inherit',
              opacity: 0.75,
              fontWeight: '500',
              textDecoration: 'underline',
              '&:hover': {
                opacity: 1,
                color: colors.teal[600],
              },
            },
            b: { color: 'inherit' },
            strong: { color: 'inherit' },
            em: { color: 'inherit' },
            h1: { color: 'inherit' },
            h2: { color: 'inherit' },
            h3: { color: 'inherit' },
            h4: { color: 'inherit' },
            code: { color: 'inherit' },
          },
        },
      },
      backgroundColor: {
        // 暗黑背景色
        'v-dark': 'var(--dark-bg-color)',
      },
    },
  },
});
