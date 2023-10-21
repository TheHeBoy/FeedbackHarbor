// 避免vite-svg-loader错误提示, 编译行为委托给项目进行编译
declare module '*.svg?component' {
    import { FunctionalComponent, SVGAttributes } from 'vue'
    const src: FunctionalComponent<SVGAttributes>
    export default src
}

declare module '*.svg?url' {
    const src: String
    export default src
}

declare module '*.svg?raw' {
    const src: String
    export default src
}
