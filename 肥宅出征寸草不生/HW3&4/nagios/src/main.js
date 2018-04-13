// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import 'bootstrap/dist/js/bootstrap.min.js'
import 'bootstrap/dist/css/bootstrap.css'
import Vuetable from '../node_modules/vuetable-2/src/components/Vuetable'
import VueTablePagination from '../node_modules/vuetable-2/src/components/VueTablePagination'
import store from './store'

Vue.config.productionTip = false
Vue.component('vuetable', Vuetable)
Vue.component('vuetable-pagination', VueTablePagination)
/* eslint-disable no-new */

new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>',
  store,
  comments: {
    VueTable: Vuetable,
    VueTablePagination: VueTablePagination
    }
  }
)
