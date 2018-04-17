import Vue from 'vue'
import Router from 'vue-router'
import HostTable from '../components/HostTable/HostTable'
import Contact from '../components/Contact/Contact'


Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/nagios-hw3',
      name: 'HostTable',
      component: HostTable
    },
    {
      path: '/conTact',
      name: 'Contact',
      component: Contact
    }
  ]
})
