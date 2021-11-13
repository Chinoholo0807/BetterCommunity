<!--
 * @Description: 
 * @Author: l
 * @Date: 2021-05-31 13:38:16
 * @LastEditors: l
 * @LastEditTime: 2021-11-13 14:46:43
 * @FilePath: \frontend\src\App.vue
-->
<template>
  <div id="app">
    <router-view></router-view>
  </div>
</template>



<script>
export default {
  name: "App",
};
</script>

<style scoped>
@import "./assets/css/main.css";
#app {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  text-align: center;
  color: #2c3e50;
  height: 100%;
  width: 100%;
  position: fixed;
}
body{
  margin: 0;
}
</style>
