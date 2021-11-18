<!--
 * @Description: 
 * @Author: l
 * @Date: 2021-11-18 00:45:25
 * @LastEditors: l
 * @LastEditTime: 2021-11-18 01:24:32
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
    <el-form
      :model="respForm"
      ref="respFormRef"
      label-width="120px"
      size="small"
      class="resp-form"
      :rules="rules"
    >
      <el-form-item label="委托id">
        <el-input v-model="respForm.requestId" :disabled="true"></el-input>
      </el-form-item>
      <el-form-item label="响应描述" prop="description">
        <el-input
          type="textarea"
          rows="3"
          v-model="respForm.description"
          :disabled="readOnly"
          placeholder="在这里写下响应描述吧~"
        ></el-input>
      </el-form-item>
    </el-form>

    <div slot="footer">
      <el-button @click="dialogVisible = false">取消</el-button>
      <el-button type="primary" @click="tryRespIssue()">添加响应</el-button>
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
      respForm: {
        description: "",
        createTime: 0,
        requestId: 0,
      },
      rules: [],
    };
  },
  methods: {
    tryRespIssue() {
      //   console.log("[IssueRespDialog]tryRespIssue...");
      this.$refs.respFormRef.validate(async (valid) => {
        if (valid) {
          console.log("[IssueRespDialog]tryRespIssue ...");
          this.respForm.createTime = parseInt(new Date().getTime() / 1000);
          const result = await this.$http.post("resp/accept", this.respForm);
          if (result.data.status.code == 200) {
            // 修改issue成功
            this.$message({
              message: "响应委托帮忙成功",
              type: "success",
            });

            this.dialogVisible = false;
          } else {
            // 修改issue失败
            this.$message({
              message: "响应委托帮忙失败:" + result.data.status.msg,
              type: "error",
            });
          }
        } else {
          //表单验证失败
          this.$message({
            message: "表单验证失败，请重新填写",
            type: "warning",
          });
        }
      });
    },
    init(row) {
      this.respForm = {
        description: "",
        createTime: 0,
        requestId: 0,
      };
      this.respForm.requestId = row.id;
      this.dialogVisible = true;
    },
    dialogBeforeClose() {
      this.dialogVisible = false;
    },
  },
});
</script>
<style scoped>
</style>
