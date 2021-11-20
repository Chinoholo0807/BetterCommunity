<!--
 * @Description: 
 * @Author: l
 * @Date: 2021-11-08 10:13:44
 * @LastEditors: l
 * @LastEditTime: 2021-11-20 19:15:28
 * @FilePath: \frontend\src\pages\Home.vue
-->
<template>
  <el-container class="home-container">
    <!-- 头部 -->
    <el-header>
      <div class="logo">好社区系统</div>
    </el-header>

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
  created() {
    this.getUserInfo();
  },
  setup() {},
  name: "Home",
  data: function () {
    return {
      items: [],
    };
  },
  methods: {
    handleOpen(key, keyPath) {
      // console.log(key, keyPath);
    },
    handleClose(key, keyPath) {
      // console.log(key, keyPath);
    },
    async getUserInfo() {
      console.log("getUserInfo...");
      const result = await this.$http.get("user/info");
      // console.log(result.data)
      if (result.data.status.code == 200) {
        this.userInfo = result.data;
        // 利用sessionStorage存储用户信息
        window.sessionStorage.setItem("username", result.data.username);
        window.sessionStorage.setItem("name", result.data.name);
        window.sessionStorage.setItem("type", result.data.type);
        window.sessionStorage.setItem("userLevel", result.data.userLevel);
        window.sessionStorage.setItem("regCity", result.data.regCity);
        window.sessionStorage.setItem("regCommunity", result.data.regCommunity);
        console.log("getUserInfo success");
        //普通用户
        if (result.data.type == 0) {
          this.items = [
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
                  title: "所有委托",
                  index: "/allissue",
                },
              ],
            },
            {
              title: "我可以",
              index: "/myrecv",
              icon: "el-icon-thumb",
              subs: [
                {
                  title: "发现委托",
                  index: "/findissue",
                },
                {
                  title: "我的响应",
                  index: "/myresp",
                },
              ],
            },
          ];
        }
        //管理员用户
        else if (result.data.type == 1) {
          this.items = [
            {
              title: "我的信息",
              index: "/dashboard",
              icon: "el-icon-info",
            },
            {
              title: "所有委托",
              index: "/allissue",
              icon: "el-icon-truck",
            },
            {
              title: "所有响应",
              index: "/myresp",
              icon: "el-icon-thumb",
            },
            {
              title: "统计数据",
              index: "/census",
              icon: "el-icon-pie-chart",
            },
          ];
        }
      } else {
        this.$message({
          message: "获取用户信息失败:" + result.data.status.msg,
          type: "error",
        });
      }
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
  background-color: #dce4ec;
}
.logo {
  /* float: left; */
  width: 250px;
  line-height: 60px;
  font-size: 25px;
  color: white;
}
</style>