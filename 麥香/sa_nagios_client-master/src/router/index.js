import Vue from 'vue'
import Router from 'vue-router'
import mainPage from '@/components/mainPage'

Vue.use(Router)
Vue.use(require('vue-moment'))

export default new Router({
  routes: [
    {
      path: '/',
      name: 'mainPage',
      component: mainPage
    }
  ]
})
