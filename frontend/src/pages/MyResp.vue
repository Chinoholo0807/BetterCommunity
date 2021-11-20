<template>
  <div class="container">
    <el-row :gutter="0">
      <el-col>
        <!-- <el-row>
          <el-button type="primary" icon="el-icon-plus" @click="handleAdd"
            >添加</el-button
          >
        </el-row> -->

        <el-table
          :data="resps"
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
            label="响应id"
            width="0"
          >
          </el-table-column>

          <el-table-column
            prop="requestId"
            align="center"
            header-align="center"
            label="委托id"
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
            label="修改时间"
            width="0"
          >
            <template slot-scope="scope">
              <i class="el-icon-time"></i>
              <span style="margin-left: 10px">{{
                timeFormat(scope.row.updateTime)
              }}</span>
            </template>
          </el-table-column>

          <el-table-column
            header-align="center"
            align="center"
            prop="state"
            label="状态"
            width="0"
          >
            <template slot-scope="scope">
              <!-- <i class="el-icon-time"></i> -->
              <el-tag v-if="scope.row.state == 0" type="warning">待接受</el-tag>
              <el-tag v-else-if="scope.row.state == 1" type="success">被同意</el-tag>
              <el-tag v-else-if="scope.row.state == 2" type="danger">被拒绝</el-tag>
              <el-tag v-else-if="scope.row.state == 3" type="info">主动取消</el-tag>
            </template>
          </el-table-column>

          <el-table-column
            label="操作"
            width="200px"
            align="center"
            header-align="center"
          >
            <template #default="scope">
              <!-- <div v-if="scope.row.state == 0"> -->
              {{ scope.row.state ? "" : "" }}
              <el-button
                type="text"
                icon="el-icon-edit"
                @click="handleEdit(scope.$index, scope.row)"
                v-if="scope.row.state == 0 && !isAdmin"
                >编辑
              </el-button>

              <el-button
                type="text"
                icon="el-icon-delete"
                @click="handleDelete(scope.$index, scope.row)"
                v-if="scope.row.state == 0 && !isAdmin"
                >删除
              </el-button>

              <el-button
                type="text"
                icon="el-icon-search"
                @click="handleLook(scope.$index, scope.row)"
                >查看响应
              </el-button>

               <el-button
                type="text"
                icon="el-icon-search"
                @click="handleLookUser(scope.$index, scope.row)"
                v-if="isAdmin"
                >查看响应者
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-col>
    </el-row>
    <issue-resp-dialog v-if="dialogOpen" ref="issueRespDialogRef"/>
    <user-info-dialog  v-if="userInfoOpen" ref="userInfoRef2" />
  </div>
</template>

<script>
import { defineComponent } from "@vue/composition-api";
import DetailIssueDialog from "@/dialog/DetailIssueDialog";
import IssueRespDialog from '@/dialog/IssueRespDialog'
import UserInfoDialog from '@/dialog/UserInfoDialog'
export default defineComponent({
  components: { DetailIssueDialog ,IssueRespDialog, UserInfoDialog},
  created() {
    this.getResps();
  },
  data() {
    return {
      resps: [],
      isRouterAlive: true,
      dialogOpen: false,
      isAdmin:false,
      userInfoOpen:false,
    };
  },
  methods: {
    async getResps() {
      console.log("[MyResp]getResps...");
      let result;
      if(window.sessionStorage.getItem('type') =="0"){//普通用户
        this.isAdmin=false;
        result = await this.$http.get("resp/mine");
      }
          
      else if(window.sessionStorage.getItem('type') =="1"){//管理员
          this.isAdmin=true;
          result = await this.$http.get("resp/query",{
            params:{
              id:"",
              requestId:"",
            }})
      }
      if (result.data.status.code == 200) {
        this.resps = result.data.resps;
        console.log("[MyResp]getResps success");
        // this.reload();
      } else {
        this.$message({
          message: "获取响应信息失败:" + result.data.status.msg,
          type: "error",
        });
      }
    },
    reload() {
      console.log("[MyResp]reload ...");
      this.isRouterAlive = false;
      this.getResps();
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
    handleEdit(index, row) {
      console.log("[MyResp]handleEdit...", index, row);
      this.dialogOpen = true;
      this.$nextTick(() => {
        this.$refs.issueRespDialogRef.init(row, "edit");
      });
    },
    handleDelete(index, row) {
      console.log("[MyIssue]handleDelete...", index, row);
      this.$confirm("确定要删除这个响应吗？")
        .then(async (_) => {
          //确认删除请求
          const result = await this.$http.post("resp/delete", { id: row.id });
          if (result.data.status.code == 200) {
            // this.issues = result.data.issues;
            // console.log("[MyIssue]handleDelete success");
            this.$message({
              message: "删除响应成功",
              type: "success",
            });
            this.reload();
          } else {
            this.$message({
              message: "删除响应失败:" + result.data.status.msg,
              type: "error",
            });
          }
        })
        .catch((_) => {
          //取消删除，无事发生
        });
    },
    handleLook(index, row) {
      console.log("[MyIssue]handleLook...", index, row);
      this.dialogOpen = true;
      this.$nextTick(() => {
        this.$refs.issueRespDialogRef.init(row, "look");
      });
    },
    async handleLookUser(index,row){
      console.log("[MyResp]handleLookUser ...")
      console.log(row)
      const result = await this.$http.get("user/info",{
        params:{
          username:row.respUsername,
        }
      });
      if (result.data.status.code == 200) {
        console.log("[MyResp]handleLookUser success");
        let userInfo = result.data;
        userInfo.password="";
        this.userInfoOpen=true;
        this.$nextTick(()=>{
            this.$refs.userInfoRef2.init(userInfo,"look")
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
        let date = new Date(ts * 1000);
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