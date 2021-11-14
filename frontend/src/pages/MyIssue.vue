<!--
 * @Description: 
 * @Author: l
 * @Date: 2021-11-11 17:00:10
 * @LastEditors: l
 * @LastEditTime: 2021-11-14 21:00:18
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
              <!-- </div> -->

              <el-button
                type="text"
                icon="el-icon-search"
                @click="handleLook(scope.$index, scope.row)"
                >查看
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-col>
    </el-row>
    <detail-issue-dialog v-if="dialogOpen" ref="detailIssueDialogRef" />
  </div>
</template>

<script>
import { defineComponent } from "@vue/composition-api";
import DetailIssueDialog from "@/dialog/DetailIssueDialog";
// import DetailIssueDialog from "../dialog/DetailIssueDialog.vue";
// import func from "../../vue-temp/vue-editor-bridge";
export default defineComponent({
  components: { DetailIssueDialog },
  created() {
    this.getIssues();
  },
  data() {
    return {
      issues: [],
      isRouterAlive: true,
      dialogOpen: false,
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
      console.log("[MyIssue]reload ...");
      this.isRouterAlive = false;
      this.getIssues();
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
      console.log("[MyIssue]handleEdit...", index, row);
      this.dialogOpen = true;
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
      this.dialogOpen = true;
      this.$nextTick(() => {
        this.$refs.detailIssueDialogRef.init(row, "look");
      });
    },
    handleAdd() {
      console.log("[MyIssue]handleAdd...");
      this.dialogOpen = true;
      let tmpIssue = {
        type: 4,
        title: "",
        description: "",
        headcount: 1,
        createTime: parseInt(new Date().getTime() / 1000),
        endTime: parseInt(new Date().getTime() / 1000),
        commissionFee: 0.0,

        // name: window.sessionStorage.getItem('name'),
        // username: window.sessionStorage.getItem('username')
      };
      this.$nextTick(() => {
        this.$refs.detailIssueDialogRef.init(tmpIssue, "add");
      });
    },
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