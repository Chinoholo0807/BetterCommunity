<!--
 * @Description: 
 * @Author: l
 * @Date: 2021-11-18 16:59:21
 * @LastEditors: l
 * @LastEditTime: 2021-11-18 18:27:39
 * @FilePath: \frontend\src\dialog\IssueRespListDialog.vue
-->
<!--
 * @Description: 
 * @Author: l
 * @Date: 2021-11-18 00:45:25
 * @LastEditors: l
 * @LastEditTime: 2021-11-18 15:10:37
 * @FilePath: \frontend\src\dialog\IssueRespDialog.vue
-->
<template>
  <el-dialog
    title="响应详细信息"
    :visible.sync="dialogVisible"
    width="60%"
    append-to-body
    :before-close="dialogBeforeClose"
  >
    <el-table :data="respList" style="width: 100%" max-height="450">
      <el-table-column type="expand">
        <template slot-scope="props">
          <el-form label-position="left" inline class="demo-table-expand">
            <el-form-item label="响应id:">
              <span>{{ props.row.id }}</span>
            </el-form-item>
            <el-form-item label="请求id:">
              <span>{{ props.row.requestId }}</span>
            </el-form-item>
            <el-form-item label="响应者账号:">
              <span>{{ props.row.respUsername }}</span>
            </el-form-item>
            <el-form-item label="响应者名称:">
              <span>{{ props.row.respName }}</span>
            </el-form-item>
            <el-form-item label="响应状态:">
              {{
                props.row.state == 0
                  ? "待接受"
                  : props.row.state == 1
                  ? "已同意"
                  : props.row.state == 2
                  ? "已拒绝"
                  : "主动取消"
              }}
            </el-form-item>
            <el-form-item label="响应创建时间:">
              <span>{{ timeFormat(props.row.createTime) }}</span>
            </el-form-item>
            <el-form-item label="响应更新时间:">
              <span>{{ timeFormat(props.row.updateTime) }}</span>
            </el-form-item>
            <el-form-item label="响应描述" style="width: 51%">
              <span>{{ props.row.description }}</span>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>

      <el-table-column label="响应id" prop="id"> </el-table-column>

      <el-table-column label="响应者名称" prop="respName"></el-table-column>

      <el-table-column
        header-align="center"
        align="center"
        prop="state"
        label="状态"
        width="0"
      >
        <template slot-scope="scope">
          <!-- <i class="el-icon-time"></i> -->
          <el-tag v-if="scope.row.state == 0">待响应</el-tag>
          <el-tag v-else-if="scope.row.state == 1" type="success"
            >已同意</el-tag
          >
          <el-tag v-else-if="scope.row.state == 2" type="danger">已拒绝</el-tag>
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
            icon="el-icon-check"
            @click="handleReply(scope.$index, scope.row, 0)"
            v-if="scope.row.state == 0"
            >同意
          </el-button>

          <el-button
            type="text"
            icon="el-icon-close"
            @click="handleReply(scope.$index, scope.row, 1)"
            v-if="scope.row.state == 0"
            >拒绝
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <div slot="footer">
      <el-button @click="dialogVisible = false">取消</el-button>
    </div>
  </el-dialog>
</template>
<script>
import { defineComponent } from "@vue/composition-api";

export default defineComponent({
  setup() {},
  data() {
    return {
      dialogVisible: false,
      readOnly: false,
      respList: [],
      rules: {},
      type: "",
    };
  },
  methods: {
    init(row, type) {
      this.respList = row;
      this.type = type;
      this.dialogVisible = true;
    },
    dialogBeforeClose() {
      this.dialogVisible = false;
    },
    async handleReply(index, row, op) {
      console.log("[ListDialog]handleReply...", row);
      const result = await this.$http.post("req/reply", {
        responseId: row.id,
        requestId: row.requestId,
        operate: op,
      });
      if (result.data.status.code == 200) {
        // 回复响应成功
        this.$message({
          message: "操作成功",
          type: "success",
        });

        this.dialogVisible = false;
      } else {
        // 响应issue失败
        this.$message({
          message: "操作失败:" + result.data.status.msg,
          type: "error",
        });
      }
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
.demo-table-expand {
  font-size: 0;
  margin-left: 30px;
}
.demo-table-expand label {
  width: 90px;
  color: #99a9bf;
}
.demo-table-expand .el-form-item {
  margin-right: 0;
  margin-bottom: 0;
  width: 50%;
}
</style>
