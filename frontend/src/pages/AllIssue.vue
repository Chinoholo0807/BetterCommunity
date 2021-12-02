<!--
 * @Description: 
 * @Author: l
 * @Date: 2021-11-11 17:00:10
 * @LastEditors: l
 * @LastEditTime: 2021-11-20 19:54:45
 * @FilePath: \frontend\src\pages\AllIssue.vue
-->
<template>
  <div class="container">
    <el-row :gutter="0">
      <el-col>
        <el-row>
          <el-form ref="queryForm" :model="query" label-width="80px">
            <el-col :span="5">
              <el-form-item label="委托类型">
                <el-select v-model="query.type" placeholder="委托类型">
                  <el-option label="小时工" :value="0"></el-option>
                  <el-option label="搬重物" :value="1"></el-option>
                  <el-option label="上下班打车" :value="2"></el-option>
                  <el-option label="社区志愿" :value="3"></el-option>
                  <el-option label="其他" :value="4"></el-option>
                  <el-option label="所有" :value="5"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="5">
              <el-form-item label="主题名称">
                <el-input
                  v-model="query.title"
                  placeholder="主题名称"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="5">
              <el-form-item label="小区名称">
                <el-input
                  v-model="query.regCommunity"
                  placeholder="小区名称"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="4">
              <el-form-item label="委托id">
                <el-input
                  v-model="query.id"
                  placeholder="委托id"
                ></el-input>
              </el-form-item>
            </el-col>
          </el-form>


          <el-col :span="2" :offset="2"
            ><el-button
              type="primary"
              icon="el-icon-search"
              @click="handleSearch"
              >查找</el-button
            ></el-col
          >
        </el-row>

        <el-table
          :data="issues"
          border
          style="width: 100%"
          stripe
          v-if="isRouterAlive"
          :row-class-name="tableRowClassName"
        >
          <el-table-column
            prop="id"
            align="center"
            header-align="center"
            label="请求id"
            width="0"
          >
          </el-table-column>

          <el-table-column
            prop="name"
            align="center"
            header-align="center"
            label="请求者名称"
            width="0"
          >
          </el-table-column>

          <el-table-column
            prop="type"
            align="center"
            header-align="center"
            label="请求类型"
            width="0"
          >
            <template slot-scope="scope">
              <div v-if="scope.row.type == 0">
                <i class="el-icon-time"></i>
                <span style="margin-left: 10px align= 'center'">小时工</span>
              </div>
              <div v-else-if="scope.row.type == 1">
                <i class="el-icon-goods"></i>
                <span style="margin-left: 10px align= 'center'">搬重物</span>
              </div>
              <div v-else-if="scope.row.type == 2">
                <i class="el-icon-location-outline"></i>
                <span style="margin-left: 10px align= 'center'"
                  >上下班打车</span
                >
              </div>
              <div v-else-if="scope.row.type == 3">
                <i class="el-icon-service"></i>
                <span style="margin-left: 10px align= 'center'">社区志愿</span>
              </div>
              <div v-else-if="scope.row.type == 4">
                <i class="el-icon-date"></i>
                <span style="margin-left: 10px align= 'center'">其他</span>
              </div>
            </template>
          </el-table-column>

          <el-table-column
            prop="title"
            align="center"
            header-align="center"
            label="请求主题名称"
            width="0"
          >
          </el-table-column>

          <el-table-column
            header-align="center"
            align="center"
            label="创建时间"
            width="0"
          >
            <template slot-scope="scope">
              <i class="el-icon-time"></i>
              <span style="margin-left: 10px">{{
                timeFormat(scope.row.createTime)
              }}</span>
            </template>
          </el-table-column>

          <el-table-column
            header-align="center"
            align="center"
            label="结束时间"
            width="0"
          >
            <template slot-scope="scope">
              <i class="el-icon-time"></i>
              <span style="margin-left: 10px">{{
                timeFormat(scope.row.endTime)
              }}</span>
            </template>
          </el-table-column>

          <el-table-column
            header-align="center"
            align="center"
            prop="state"
            label="请求状态"
            width="0"
          >
            <template slot-scope="scope">
              <!-- <i class="el-icon-time"></i> -->
              <el-tag v-if="scope.row.state == 0" type="info">待响应</el-tag>
              <el-tag v-else-if="scope.row.state == 1" type="danger"
                >已取消</el-tag
              >
              <el-tag v-else-if="scope.row.state == 2" type="warning"
                >进行中</el-tag
              >
              <el-tag v-else-if="scope.row.state == 3" type="success"
                >已完成</el-tag
              >
            </template>
          </el-table-column>

          <el-table-column
            label="操作"
            width="200px"
            align="center"
            header-align="center"
          >
            <template #default="scope">
              {{ scope.row.state ? "" : "" }}

              <el-button
                type="text"
                icon="el-icon-search"
                @click="handleLook(scope.$index, scope.row)"
                >查看委托
              </el-button>
              <el-button
                type="text"
                icon="el-icon-search"
                @click="handleLookUser(scope.$index, scope.row)"
                v-if="isAdmin"
                >查看委托者
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-col>
    </el-row>
    <detail-issue-dialog v-if="dialogIssueOpen" ref="detailIssueDialogRef" />
    <issue-resp-dialog v-if="dialogRespOpen" ref="issueRespDialogRef" />
    <user-info-dialog v-if="userInfoOpen" ref="userInfoRef1" />
  </div>
</template>

<script>
import { defineComponent } from "@vue/composition-api";
import IssueRespDialog from '@/dialog/IssueRespDialog'
import DetailIssueDialog from '@/dialog/DetailIssueDialog'
import UserInfoDialog from '@/dialog/UserInfoDialog'
export default defineComponent({
  components: { DetailIssueDialog ,IssueRespDialog , UserInfoDialog},
  created() {
    this.handleSearch();
  },
  data() {
    return {
      issues: [],
      isRouterAlive: true,
      dialogIssueOpen: false,
      dialogRespOpen: false,
      query: {
        title: "",
        regCommunity: "",
        type: 5,
        id : "",
      },
      isAdmin:false,
      userInfoOpen:false,
    };
  },
  methods: {
    async getIssues() {
      console.log("[MyIssue]getIssues...");
      const result = await this.$http.get("req/mine");
      //   console.log(result.data);
      if (result.data.status.code == 200) {
        this.issues = result.data.issues;
        console.log("[MyIssue]getIssues success");
        // this.reload();
      } else {
        this.$message({
          message: "获取请求帮忙信息失败:" + result.data.status.msg,
          type: "error",
        });
      }
    },
    reload() {
      console.log("[FindIssue]reload ...");
      this.isRouterAlive = false;
      this.getIssues();
      this.$nextTick(() => {
        this.isRouterAlive = true;
      });
    },
    refresh() {
      console.log("[FindIssue]refresh ...");
      this.isRouterAlive = false;
      this.$nextTick(() => {
        this.isRouterAlive = true;
      });
    },
    tableRowClassName({ row, rowIndex }) {
      if (row.state == 0)
        //待响应
        return "need-resp-row";
      else if (row.state == 1)
        //已取消
        return "cancel-row";
      else if (row.state == 2)
        //被响应未完成
        return "process-row";
      else if (row.state == 3)
        //已完成
        return "finish-row";
      return "nil-row";
    },
    handleLook(index, row) {
      console.log("[AllIssue]handleLook...", index, row);
      this.dialogIssueOpen = true;
      this.$nextTick(() => {
        this.$refs.detailIssueDialogRef.init(row, "look");
      });
    },
    handleResp(index, row) {
      console.log("[AllIssue]handleResp...", index, row);
      this.dialogRespOpen = true;
      this.$nextTick(() => {
          this.$refs.issueRespDialogRef.init(row, "add");
      })
    },
    async handleSearch() {
      console.log("[AllIssue]handleSearch...");
      if(window.sessionStorage.getItem('type') =="0"){//普通用户
        this.isAdmin=false;
      }
      else if(window.sessionStorage.getItem('type') =="1"){//管理员
        this.isAdmin=true;
      }
      const result = await this.$http.get("/req/query", {
          params:this.query,
      });
      if (result.data.status.code == 200) {
        this.issues = result.data.issues;
        console.log("[AllIssue]handleSearch success");
        this.$message({
          message: "查询成功",
          type: "success",
        });
        this.issues = result.data.issues;
        this.refresh();
      } else {
        this.$message({
          message: "查询失败:" + result.data.status.msg,
          type: "error",
        });
      }
    },
    async handleLookUser(index,row){
      console.log("[AllIssue]handleLookUser ...")
      // console.log(row)
      const result = await this.$http.get("user/info",{
        params:{
          username:row.username,
        }
      });
      if (result.data.status.code == 200) {
        console.log("[AllIssue]handleLookUser success");
        let userInfo = result.data;
        userInfo.password="";
        this.userInfoOpen=true;
        this.$nextTick(()=>{
            this.$refs.userInfoRef1.init(userInfo,"look")
        })
      } else {
        this.$message({
          message: "获取用户信息失败:" + result.data.status.msg,
          type: "error",
        });
      }
    }
  },
  computed: {
    timeFormat() {
      return function (ts) {
        if (!ts) return "";
        let date = new Date(ts );
        return date.Format("yyyy-MM-dd HH:mm:ss");
      };
    },
  },
});
</script>
<style scoped>
.need-resp-row {
  background-color: snow;
}
.cancel-row {
  background-color: rgb(218, 99, 129);
}
.process-row {
  background-color: rgb(230, 220, 85);
}
.finish-row {
  background-color: greenyellow;
}
</style>