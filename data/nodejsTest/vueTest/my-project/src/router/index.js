import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '@/components/HelloWorld'
import testrouter from '@/components/Test1'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'HelloWorld',
      component: HelloWorld
    },
    {keyi
      path:'/test',
      name:'testname',
      component:testrouter
    }
  ]
})
