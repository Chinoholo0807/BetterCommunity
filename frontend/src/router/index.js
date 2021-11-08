/*
 * @Description: 
 * @Author: l
 * @Date: 2021-11-07 23:25:09
 * @LastEditors: l
 * @LastEditTime: 2021-11-08 21:54:50
 * @FilePath: \frontend\src\router\index.js
 */

import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/pages/Login'
import DashBoard from '@/pages/DashBoard'
Vue.use(Router) // 让Vue安装VueRouter组件

//指定路由
const router = new Router({
  routes: [
    {
      path: '/',
      redirect: '/login',
    },
    {
      path: '/login',
      name: 'login',
      component: Login,
    },
    {
      path: '/dashboard',
      name: 'dashboard',
      component: DashBoard,
    }
  ]
})

// 挂载路由导航守卫，防止直接页面跳转
router.beforeEach((to,from,next)=>{
  if(to.path == '/login')
    return next()
  // 获取token
  const tokenStr = window.sessionStorage.getItem('token')  
  if(!tokenStr) //直接访问，重定向到login
    return next('/login')
  // 放行
  return next()
})

export default router
