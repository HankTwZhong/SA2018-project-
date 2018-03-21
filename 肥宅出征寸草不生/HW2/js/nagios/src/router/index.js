import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '@/components/HelloWorld'
import HostTable2 from '../components/HostTable2/HostTable2'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/nagios-hw1',
      name: 'HostTable',
      component: HostTable2
    }
  ]
})
