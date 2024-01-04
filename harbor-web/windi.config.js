import {defineConfig} from 'vite-plugin-windicss';
import typography from 'windicss/plugin/typography';

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
    attributify: false,
    plugins: [
        typography(),
        ({addComponents}) => {
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
                ...obj,
            });
        },
    ],
});

