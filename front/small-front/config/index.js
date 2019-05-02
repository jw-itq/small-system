// see http://vuejs-templates.github.io/webpack for documentation.
var path = require('path')

module.exports = {
  build: {
    env: require('./prod.env'),
    index: path.resolve(__dirname, '../dist/index.html'),
    assetsRoot: path.resolve(__dirname, '../dist'),
    assetsSubDirectory: 'static',
    assetsPublicPath: '/',
    productionSourceMap: true,
    // Gzip off by default as many popular static hosts such as
    // Surge or Netlify already gzip all static assets for you.
    // Before setting to `true`, make sure to:
    // npm install --save-dev compression-webpack-plugin
    productionGzip: false,
    productionGzipExtensions: ['js', 'css'],
    // Run the build command with an extra argument to
    // View the bundle analyzer report after build finishes:
    // `npm run build --report`
    // Set to `true` or `false` to always turn it on or off
    bundleAnalyzerReport: process.env.npm_config_report
  },
  dev: {
    env: require('./dev.env'),
    port: 9999,
    autoOpenBrowser: true,
    assetsSubDirectory: 'static',
    assetsPublicPath: '/',
    proxyTable: {
      '/member/*': {
        target: 'http://47.93.194.201:8080/small-single'  // 请求本地 需要SMall后台项目 默认127.0.0.1:7777
      },
      '/goods/*': {
        target: 'http://47.93.194.201:8080/small-single'  // 请求本地 需要SMall后台项目 默认127.0.0.1:7777
      },
      '/item/*':{
        target: 'http://47.93.194.201:8080/small-single'  // 这个是专门用来请求那个分类导航的
      }
    },
   /* proxyTable: {
      '/membertest/!*': {
        target: 'http://localhost:8080',  // 请求本地 需要SMall后台项目 默认127.0.0.1:7777
        secure: true,  // 如果是https接口，需要配置这个参数
        changeOrigin: true //是否跨域
      },
      '/member/!*': {
        target: 'https://result.eolinker.com/bjWa8Yx75c71ea7214f2339c923ae08b3d85acdbb68d312?uri=',  // 请求本地 需要SMall后台项目 默认127.0.0.1:7777
        secure: false,  // 如果是https接口，需要配置这个参数
        changeOrigin: true  //是否跨域
      },

      '/goods/!*': {
          target: 'https://result.eolinker.com/bjWa8Yx75c71ea7214f2339c923ae08b3d85acdbb68d312?uri=',  // 请求本地 需要SMall后台项目 默认127.0.0.1:7777
        secure: false,  // 如果是https接口，需要配置这个参数
        changeOrigin: true //是否跨域
      }

      },*/
    // CSS Sourcemaps off by default because relative paths are "buggy"
    // with this option, according to the CSS-Loader README
    // (https://github.com/webpack/css-loader#sourcemaps)
    // In our experience, they generally work as expected,
    // just be aware of this issue when enabling this option.
    cssSourceMap: false
  }
}
