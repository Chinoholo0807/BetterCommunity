<!--
 * @Description: 
 * @Author: l
 * @Date: 2021-11-13 17:40:06
 * @LastEditors: l
 * @LastEditTime: 2021-11-14 21:01:24
 * @FilePath: \frontend\src\dialog\DetailIssueDialog.vue
-->
<template>
  <el-dialog
    title="委托帮忙详细信息"
    :visible.sync="dialogVisible"
    width="60%"
    append-to-body
    :before-close="dialogBeforeClose"
  >
    <el-form
      :model="issue"
      ref="issueFormRef"
      label-width="120px"
      size="small"
      class="issue-form"
      :rules="rules"
    >
      <el-form-item label="委托id" prop="id" v-if="dialogType != 'add'">
        <el-input v-model="issue.id" :disabled="true"></el-input>
      </el-form-item>

      <el-form-item label="委托者账号" prop="username" v-if="dialogType != 'add'">
        <el-input v-model="issue.username" :disabled="true"></el-input>
      </el-form-item>

      <el-form-item label="委托者用户名" prop="name" v-if="dialogType != 'add'">
        <el-input v-model="issue.name" :disabled="true"></el-input>
      </el-form-item>

      <el-form-item label="委托类型" prop="type">
        <el-radio-group v-model="issue.type" :disabled="readOnly">
          <el-radio :label="0">小时工</el-radio>
          <el-radio :label="1">搬重物</el-radio>
          <el-radio :label="2">上下班打车</el-radio>
          <el-radio :label="3">社区志愿</el-radio>
          <el-radio :label="4">其他</el-radio>
        </el-radio-group>
      </el-form-item>

      <el-form-item label="委托主题描述" prop="title">
        <el-input v-model="issue.title" :disabled="readOnly"></el-input>
      </el-form-item>

      <el-form-item label="委托费用" prop="commissionFee">
        <el-input-number v-model="issue.commissionFee" :step="0.1" type="number" :disabled="readOnly"></el-input-number>
      </el-form-item>

      <el-form-item label="委托人数需求" prop="headcount">
        <el-input-number  v-model="issue.headcount"   :disabled="readOnly"></el-input-number>
      </el-form-item>

      <el-form-item label="委托结束时间" prop="endTime">
        <el-date-picker
          v-model="endTimeStr"
          type="datetime"
          placeholder="选择委托结束时间"
          :disabled="readOnly"
        >
        </el-date-picker>
      </el-form-item>

      <el-form-item label="委托请求描述" prop="description">
        <el-input
          type="textarea"
          rows="3"
          v-model="issue.description"
          :disabled="readOnly"
        ></el-input>
      </el-form-item>
    </el-form>

    <div slot="footer">
      <el-button @click="dialogVisible = false">取消</el-button>
      <el-button type="primary" @click="tryUpdateIssue()" v-if="dialogType == 'edit'">更新委托</el-button>
      <el-button type="primary" @click="tryAddIssue()" v-if="dialogType == 'add'">添加委托</el-button>

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
      issue: {},
      endTimeStr:'',
      dialogType:'',
      rules: {
          commissionFee:[
               { required: true, message: "请输入费用", trigger: "blur" },
            //    { type: 'float', message: '费用必须为数字',trigger: "blur"}
          ],
          headcount:[
               { required: true, message: "请输入委托所需人数", trigger: "blur" },
            //    { type: 'integer', message: '人数必须为整数',trigger: "blur"}
          ],
          title:[
             { required: true, message: "请输入主题描述", trigger: "blur" },
          ],
      },
    };
  },
  watch:{
      endTimeStr(newStr,oldStr){
          this.issue.endTime = new Date(newStr).getTime()/1000
      }
  },
  methods: {
    dialogBeforeClose() {
      this.dialogVisible = false;
    },
    init(issue ,  dialogType) {
      if(dialogType == 'look')
        this.readOnly=true
      else
        this.readOnly=false
      this.issue = Object.assign({}, issue);
      this.dialogType = dialogType;
      this.endTimeStr = new Date(this.issue.endTime*1000);
      this.dialogVisible = true;
    },
    tryUpdateIssue() {
      this.$refs["issueFormRef"].validate(async (valid) => {
        if (valid) {
          //表单验证成功
          console.log("[DetailIssueDialog]tryUpdateIssue ...");
          const result = await this.$http.post("req/update", this.issue);
          if (result.data.status.code == 200) {
            // 修改issue成功
            this.$message({
              message: "修改委托帮忙信息成功",
              type: "success",
            });
            this.dialogVisible = false;
          } else {
            // 修改issue失败
            this.$message({
              message: "修改委托信息失败:" + result.data.status.msg,
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
    tryAddIssue(){
        this.$refs["issueFormRef"].validate(async (valid) => {
        if (valid) {
          //表单验证成功
          console.log("[DetailIssueDialog]tryAddIssue ...");
          const result = await this.$http.post("req/add", this.issue);
          if (result.data.status.code == 200) {
            // 添加issue成功
            this.$message({
              message: "新建委托帮忙成功",
              type: "success",
            });
            this.dialogVisible = false;
          } else {
            // 修改issue失败
            this.$message({
              message: "新建委托帮忙失败:" + result.data.status.msg,
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
    }
  },
});
</script>
<style scoped>
</style>