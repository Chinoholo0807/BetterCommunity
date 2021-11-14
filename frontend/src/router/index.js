/*
 * @Description: 
 * @Author: l
 * @Date: 2021-11-07 23:25:09
 * @LastEditors: l
 * @LastEditTime: 2021-11-13 20:55:09
 * @FilePath: \frontend\src\router\index.js
 */

import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/pages/Login'
import Home from '@/pages/Home'
import Dashboard from '@/pages/Dashboard'
import AllHandout from '@/pages/AllHandout'
import MyIssue from '@/pages/MyIssue'
import MyRecv from '@/pages/MyRecv'
import Register from '@/pages/Register'
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
      component: Login,
    },
    {
      path: '/register',
      component: Register,
    },
    {
      path: '/home',
      component: Home,
      redirect: '/dashboard',
      children:[{
        path: '/dashboard',
        name: 'dashboard',
        component: Dashboard,
      },{
        path: '/myissue',
        name: 'myissue',
        component: MyIssue,
      },{
        path: '/allhandout',
        name: 'allhandout',
        component: AllHandout
      },{
        path :'/myrecv',
        name: 'myrecv',
        component: MyRecv,
      }]
    }
  ]
})

// 挂载路由导航守卫，防止直接页面跳转
router.beforeEach((to,from,next)=>{
  if(to.path == '/login' || to.path == '/register')
    return next()
  // 获取token
  const tokenStr = window.sessionStorage.getItem('token')  
  if(!tokenStr) //直接访问，重定向到login
    return next('/login')
  // 放行
  return next()
})

export default router
