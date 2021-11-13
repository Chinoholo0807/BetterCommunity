<!--
 * @Description: 
 * @Author: l
 * @Date: 2021-11-11 17:00:10
 * @LastEditors: l
 * @LastEditTime: 2021-11-13 17:42:30
 * @FilePath: \frontend\src\pages\Dashboard.vue
-->
<template>
  <div>
    <el-row :gutter="20">
      <el-col :span="8">
        <el-card shadow="always" class="mgb20" style="height: 252px">
          <div class="user-info">
            <img src="../assets/img/madoka.jpg" class="user-avator" alt />
            <div class="user-info-cont">
              <div class="user-info-name">{{ userInfo.name }}</div>
              <!-- <div>用户名:{{ userInfo.username }}</div>
              <div>身份:{{ userDiscrbe }}</div> -->
            </div>
          </div>
          <!-- <div class="user-info-list">
            用户注册时间:
            <span>{{ timeFormat(userInfo.regTime) }}</span>
          </div>
          <div class="user-info-list">
            信息更新时间:
            <span>{{ timeFormat(userInfo.updateTime) }}</span>
          </div> -->
          <!-- <template style="background-color :pink"></template> -->
        </el-card>
        <el-card shadow="always" style="height: 252px">
          <template #header>
            <div class="clearfix">
              <span>TODO</span>
            </div>
          </template>
        </el-card>
      </el-col>
      <el-col :span="16">
        <el-descriptions
          class="user-detail-descrption"
          title="用户基础信息"
          :column="1"
          border
        >
          <template slot="extra">
            <el-button type="primary" size="small" @click="updateUserInfo">修改信息</el-button>
          </template>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-user"></i>
              用户名
            </template>
            {{userInfo.username}}
          </el-descriptions-item>

          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-suitcase-1"></i>
              用户类型
            </template>
            {{userType}}
          </el-descriptions-item>

          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-tickets"></i>
              证件类型
            </template>
            {{userCertificateType}}
          </el-descriptions-item>

          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-tickets"></i>
              证件号码
            </template>
            {{userInfo.certificateNum}}
          </el-descriptions-item>

          
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-mobile-phone"></i>
              手机号
            </template>
            {{userInfo.phoneNum}}
          </el-descriptions-item>
          
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-office-building"></i>
              注册城市
            </template>
            {{userInfo.regCity}}
          </el-descriptions-item>

          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-location-outline"></i>
              注册社区
            </template>
            {{userInfo.regCommunity}}
          </el-descriptions-item>

         <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-time"></i>
              注册时间
            </template>
            {{timeFormat(userInfo.regTime)}}
          </el-descriptions-item>

          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-time"></i>
              更新时间
            </template>
            {{timeFormat(userInfo.updateTime)}}
          </el-descriptions-item>

        <el-descriptions-item  >
            <template slot="label">
              <i class="el-icon-chat-round"></i>
              用户简介
            </template>
            <el-input type="textarea" rows=3 readonly v-model="userInfo.introduction" placeholder="">
            </el-input>
          </el-descriptions-item>
        </el-descriptions>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { defineComponent } from "@vue/composition-api";

export default defineComponent({
  created() {
    this.getUserInfo();
  },
  data() {
    return {
      userInfo: {},
    };
  },
  methods: {
    async getUserInfo() {
      console.log("getUserInfo");
      const result = await this.$http.get("user/info");
      // console.log(result.data)
      if (result.data.status.code == 200) {
        this.userInfo = result.data;
        console.log("getUserInfo success");
      } else {
        this.$message({
          message: "获取用户信息失败:" + result.data.status.msg,
          type: "error",
        });
      }
    },
    updateUserInfo(){
        console.log('udpateUserInfo ...')
        this.$router.push('/myrecv')
    }
  },
  computed: {
    userType() {
      let discribe = "";
      if (this.userInfo.type == 0) {
        if (this.userInfo.userLevel == 1) discribe = "VIP用户";
        else discribe = "普通用户";
      } else if (this.userInfo.type == 1) discribe = "管理员";
      return discribe;
    },
    userCertificateType(){
        let discribe =''
        if(this.userInfo.certificateType == 0)
            discribe = '身份证'
        else if(this.userInfo.certificateType == 1)
            discribe = '护照'
        else if(this.userInfo.certificateType == 2)
            discribe = '港澳台居民居民身份证'
        return discribe
    },
    timeFormat() {
      return function (ts) {
        if(!ts) return ''
        let date = new Date(ts)
        return date.Format('yyyy-MM-dd HH:mm:ss')
      };
    },
  },
});
</script>
<style scoped>
.el-card {
  margin-bottom: 20px;
}
.el-row {
  margin-bottom: 20px;
}
.user-avator {
  width: 120px;
  height: 120px;
  border-radius: 50%;
}

.user-info {
  display: flex;
  align-items: center;
  padding-bottom: 20px;
  padding-left: 10px;
  padding-right: 10px;
  border-bottom: 2px solid #ccc;
  margin-bottom: 20px;
}

.user-info-cont {
  padding-left: 50px;
  flex: 1;
  font-size: 15px;
  color: #999;
}
/* 用户姓名 */
.user-info-cont div:first-child {
  font-size: 30px;
  color: #222;
}
.el-descriptions{
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    padding-left: 30px;
    padding-right: 30px;
    padding-top: 15px;
    padding-bottom: 30px;
}
</style>