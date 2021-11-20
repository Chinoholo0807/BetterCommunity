/*
 * @Description: 
 * @Author: l
 * @Date: 2021-11-07 23:25:09
 * @LastEditors: l
 * @LastEditTime: 2021-11-17 09:52:28
 * @FilePath: \frontend\src\router\index.js
 */

import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/pages/Login'
import Home from '@/pages/Home'
import Dashboard from '@/pages/Dashboard'
import FindIssue from '@/pages/FindIssue'
import MyIssue from '@/pages/MyIssue'
import MyResp from '@/pages/MyResp'
import Register from '@/pages/Register'
import AllIssue from '@/pages/AllIssue'
import Census from '@/pages/Census'
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
      children: [{
        path: '/dashboard',
        name: 'dashboard',
        component: Dashboard,
      }, {
        path: '/myissue',
        name: 'myissue',
        component: MyIssue,
      }, {
        path: '/findissue',
        name: 'findissue',
        component: FindIssue
      }, {
        path: '/myresp',
        name: 'myresp',
        component: MyResp
      },{
        path:"/allissue",
        name:"allissue",
        component:AllIssue,
      },{
        path:"/census",
        name:"census",
        component:Census,
      }]
    }
  ]
})

// 挂载路由导航守卫，防止直接页面跳转
router.beforeEach((to, from, next) => {
  if (to.path == '/login' || to.path == '/register')
    return next()
  // 获取token
  const tokenStr = window.sessionStorage.getItem('token')
  if (!tokenStr) //直接访问，重定向到login
    return next('/login')
  // 放行
  return next()
})

export default router
