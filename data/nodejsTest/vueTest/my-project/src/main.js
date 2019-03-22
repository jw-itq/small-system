// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'

Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  el: '#app',
  //这里传这个参数的目的是为了让这项目都有路由的功能
  router,
  components: { App },
  template: '<App/>'
})
