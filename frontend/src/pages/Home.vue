<!--
 * @Description: 
 * @Author: l
 * @Date: 2021-11-08 10:13:44
 * @LastEditors: l
 * @LastEditTime: 2021-11-14 21:02:35
 * @FilePath: \frontend\src\pages\Home.vue
-->
<template>
  <el-container class="home-container">
    <!-- 头部 -->
    <el-header>Header </el-header>

    <!-- 页面主体 -->
    <el-container>
      <!-- 侧边栏 -->
      <el-aside width="180px">
        <keep-alive>
          <!-- 菜单栏 -->
          <el-menu
            default-active="2"
            class="el-menu-vertical"
            background-color="#3d4752"
            text-color="#fff"
            active-text-color="#ffd04b"
            @open="handleOpen"
            @close="handleClose"
            router
          >
            <template v-for="item in items">
              <template v-if="item.subs">
                <el-submenu :index="item.index" :key="item.index">
                  <template #title>
                    <i :class="item.icon"></i>
                    <span>{{ item.title }}</span>
                  </template>
                  <template v-for="subItem in item.subs">
                    <el-menu-item :index="subItem.index" :key="subItem.index"
                      >{{ subItem.title }}
                    </el-menu-item>
                  </template>
                </el-submenu>
              </template>
              <template v-else>
                <el-menu-item :index="item.index" :key="item.index">
                  <i :class="item.icon"></i>
                  <template #title>{{ item.title }}</template>
                </el-menu-item>
              </template>
            </template>
          </el-menu>
        </keep-alive>
      </el-aside>

      <!-- 主体 -->
      <el-main>
        <keep-alive>
          <router-view></router-view>
        </keep-alive>
      </el-main>
    </el-container>
  </el-container>
</template>
<script>
import { defineComponent } from "@vue/composition-api";

export default defineComponent({
  setup() {},
  name: "Home",
  data: function () {
    return {
      items: [
        {
          title: "我的信息",
          index: "/dashboard",
          icon: "el-icon-info",
        },
        {
          title: "劳您驾",
          index: "1",
          icon: "el-icon-truck",
          subs: [
            {
              title: "我的委托",
              index: "/myissue",
            },
            {
              title: "发现委托",
              index: "/allissue",
            },
          ],
        },
        {
          title: "我可以",
          index: "/myrecv",
          icon: "el-icon-thumb",
        },
        {
          title: "统计数据",
          index: "/e",
          icon: "el-icon-pie-chart",
        },
      ],
    };
  },
  methods: {
    handleOpen(key, keyPath) {
      // console.log(key, keyPath);
    },
    handleClose(key, keyPath) {
      // console.log(key, keyPath);
    },
  },
});
</script>
<style scoped>
.home-container {
  height: 100%;
}
.el-header {
  background-color: #172431;
  position: relative;
  width: 100%;
  height: 60px;
}
.el-aside {
  background-color: #3d4752;
  display: block;
  left: 0;
  top: 60px;
  bottom: 0;
}
.el-main {
  position: absolute;
  left: 180px;
  right: 0;
  top: 60px;
  bottom: 0;
  overflow-y: scroll;
  background-color:#dce4ec ;
}
</style>