<!--
 * @Description: 
 * @Author: l
 * @Date: 2021-11-11 17:00:10
 * @LastEditors: l
 * @LastEditTime: 2021-11-21 15:46:29
 * @FilePath: \frontend\src\pages\MyIssue.vue
-->
<template>
  <div class="container">
    <el-row :gutter="0">
      <!-- <div class="handle-box">
        <el-button type="primary" icon="el-icon-search" @click="handleSearch"
          >搜索</el-button
        >
      </div> -->
      <!-- 委托具体信息表格 -->
      <el-col>
        <el-row>
          <el-button type="primary" icon="el-icon-plus" @click="handleAdd"
            >添加</el-button
          >
          <!-- <el-button type="primary" icon="el-icon-search">查找</el-button> -->
        </el-row>

        <el-table
          :data="issues"
          border
          style="width: 100%"
          stripe
          v-if="isRouterAlive"
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
            label="状态"
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
              <!-- <div v-if="scope.row.state == 0"> -->
              {{ scope.row.state ? "" : "" }}
              <el-button
                type="text"
                icon="el-icon-edit"
                @click="handleEdit(scope.$index, scope.row)"
                v-if="scope.row.state == 0"
                >编辑
              </el-button>

              <el-button
                type="text"
                icon="el-icon-delete"
                @click="handleDelete(scope.$index, scope.row)"
                v-if="scope.row.state == 0"
                >删除
              </el-button>

              <el-button
                type="text"
                icon="el-icon-finished"
                @click="handleConfirm(scope.$index, scope.row)"
                v-if="scope.row.state == 2"
                >确认完成
              </el-button>
              <el-button
                type="text"
                icon="el-icon-bell"
                @click="handleResp(scope.$index, scope.row)"
                v-if="scope.row.state != 1"
                >查看响应
              </el-button>

              <el-button
                type="text"
                icon="el-icon-search"
                @click="handleLook(scope.$index, scope.row)"
                >查看详情
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-col>
    </el-row>
    <issue-resp-list-dialog
      v-if="dialogListOpen"
      ref="issueRespListDialogRef"
    />
    <detail-issue-dialog v-if="dialogIssueOpen" ref="detailIssueDialogRef" />
  </div>
</template>

<script>
import { defineComponent } from "@vue/composition-api";
import DetailIssueDialog from "@/dialog/DetailIssueDialog";
import IssueRespListDialog from "@/dialog/IssueRespListDialog";
export default defineComponent({
  components: { DetailIssueDialog, IssueRespListDialog },
  created() {
    this.getIssues();
  },
  data() {
    return {
      issues: [],
      isRouterAlive: true,
      dialogIssueOpen: false,
      dialogListOpen: false,
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
    // 重新加载页面，会重新请求获取issues
    reload() {
      console.log("[MyIssue]reload ...");
      this.isRouterAlive = false;
      this.getIssues();
      this.$nextTick(() => {
        this.isRouterAlive = true;
      });
    },
    handleEdit(index, row) {
      console.log("[MyIssue]handleEdit...", index, row);
      this.dialogIssueOpen = true;
      this.$nextTick(() => {
        this.$refs.detailIssueDialogRef.init(row, "edit");
      });
    },
    handleDelete(index, row) {
      console.log("[MyIssue]handleDelete...", index, row);
      this.$confirm("确定要删除这个请求帮忙吗？")
        .then(async (_) => {
          //确认删除请求
          const result = await this.$http.post("req/delete", { id: row.id });
          if (result.data.status.code == 200) {
            // this.issues = result.data.issues;
            // console.log("[MyIssue]handleDelete success");
            this.$message({
              message: "删除请求帮忙成功",
              type: "success",
            });
            this.reload();
          } else {
            this.$message({
              message: "删除请求帮忙失败:" + result.data.status.msg,
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
      this.dialogIssueOpen = true;
      this.$nextTick(() => {
        this.$refs.detailIssueDialogRef.init(row, "look");
      });
    },
    handleAdd() {
      console.log("[MyIssue]handleAdd...");
      this.dialogIssueOpen = true;
      let tmpIssue = {
        type: 4,
        title: "",
        description: "",
        headcount: 1,
        createTime: parseInt(new Date().getTime()),
        endTime: parseInt(new Date().getTime()),
        commissionFee: 0.0,
      };
      this.$nextTick(() => {
        this.$refs.detailIssueDialogRef.init(tmpIssue, "add");
      });
    },
    handleConfirm(index ,row) {
      console.log("[MyIssue]handleConfirm...", index, row);
      this.$confirm("确定这个请求帮忙完成了吗？")
        .then(async (_) => {
          //确认完成请求
          const result = await this.$http.post("req/confirm", { requestId: row.id });
          if (result.data.status.code == 200) {
            this.$message({
              message: "操作成功",
              type: "success",
            });
            this.reload();
          } else {
            this.$message({
              message: "操作失败:" + result.data.status.msg,
              type: "error",
            });
          }
        })
        .catch((_) => {
          //取消，无事发生
        });
    },
    async handleResp(index, row) {
      console.log("[MyIssue]handleResp...", index, row);
      const result = await this.$http.get("resp/query", {
        params: {
          requestId: row.id,
          id: "",
        },
      });
      if (result.data.status.code == 200) {
        console.log("[MyIssue]handleResp success");
        //获取响应成功
        this.dialogListOpen = true;
        // console.log(result.data.resps)
        this.$nextTick(() => {
          this.$refs.issueRespListDialogRef.init(result.data.resps, "");
        });
      } else {
        this.$message({
          message: "获取响应信息失败:" + result.data.status.msg,
          type: "error",
        });
      }
    },
  },
  computed: {
    timeFormat() {
      return function (ts) {
        if (!ts) return "";
        let date = new Date(ts);
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