/*
 * @Description: 
 * @Author: l
 * @Date: 2021-11-07 23:25:09
 * @LastEditors: l
 * @LastEditTime: 2021-11-21 15:35:09
 * @FilePath: \frontend\src\main.js
 */

// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import axios from 'axios'


axios.defaults.baseURL = 'http://127.0.0.1:5000/'
// axios.defaults.baseURL = 'http://10.128.203.95:1234/'
axios.interceptors.request.use(config=>{
  config.headers.common['Authorization'] = window.sessionStorage.getItem('token')
  // console.log(config)
  return config
})
Vue.prototype.$http = axios
Vue.config.productionTip = false;

Vue.use(ElementUI);

Date.prototype.Format = function (fmt) {undefined
  var o = {
  "M+": this.getMonth() + 1, //月份
  "d+": this.getDate(), //日
  "H+": this.getHours(), //小时
  "m+": this.getMinutes(), //分
  "s+": this.getSeconds(), //秒
  "q+": Math.floor((this.getMonth() + 3) / 3), //季度
  "S": this.getMilliseconds() //毫秒
  };
  if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
  for (var k in o)
  if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
  return fmt;
  }

new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
