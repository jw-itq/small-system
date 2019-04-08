## SMall-Front
### 基于Vue开发的SMall商城前台页面

- 根据原作者的xmall项目二次开发学习，非常感谢作者exrick
- 原作者博客：http://blog.exrick.cn
### 所用技术

- Vue 2.x
- Vuex
- Vue Router
- [Element UI](http://element.eleme.io/#/zh-CN)
- ES6
- webpack
- axios
- Node.js
- 第三方SDK
    - [极验Test-button人机验证码](http://www.geetest.com/Test-button.html)
- 第三方插件
    - [hotjar](https://github.com/Exrick/SMall/blob/master/study/hotjar.md)：一体化分析和反馈
    - ~~[搜狐畅言评论插件](http://changyan.kuaizhan.com/)~~ 垃圾广告评论插件 现已更换 [Gitment](https://github.com/imsun/gitment)，
    这个还没做，因为畅言备案号的问题，项目部署以后会更新，原作者的gitment我就没打算用了

### 本地开发运行
- 启动后端 [SMall](https://github.com/Exrick/SMall) 项目后，在 `config/index.js` 中修改你的后端接口地址配置
- Gitment评论配置见 [Gitment](https://github.com/imsun/gitment) 使用到的页面为 `thanks.vue`
- `index.html` 中复制粘贴替换你的 [hotjar](https://github.com/Exrick/SMall/blob/master/study/hotjar.md) 代码
- 若不启动后端项目，勉强预览运行此前端项目可尝试注释掉 `src/main.js` 中第 `8、10、39-61` 行代码
- 在项目根文件夹下先后执行命令 `npm install` 、 `npm run dev`
- 商城前台端口默认9999 http://localhost:9999
## 部署
- 先后执行命令 `npm install` 、 `npm run build` 将打包生成的 `dist` 静态文件放置服务器中，若使用Nginx等涉及跨域请配置路由代理
