/*
 * @Description: 
 * @Author: l
 * @Date: 2021-05-31 13:38:16
 * @LastEditors: l
 * @LastEditTime: 2021-11-08 09:58:37
 * @FilePath: \frontend\src\main.js
 */
// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import store from './store'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import axios from 'axios'


axios.defaults.baseURL = 'http://127.0.0.1:5000/'
Vue.prototype.$http = axios
Vue.config.productionTip = false;

Vue.use(ElementUI);


new Vue({
  el: '#app',
  router,
  store,
  components: { App },
  template: '<App/>'
})
