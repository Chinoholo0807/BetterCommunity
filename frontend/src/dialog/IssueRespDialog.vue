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
      <el-button type="primary" @click="tryRespIssue()" v-if="type =='add'">添加响应</el-button>
      <el-button type="primary" @click="tryUpdateResp()" v-if="type =='edit'">修改响应</el-button>
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
      respForm:{},
      rules: {},
      type:"",
    };
  },
  methods: {
    tryRespIssue() {
      this.$refs.respFormRef.validate(async (valid) => {
        if (valid) {
          console.log("[IssueRespDialog]tryRespIssue ...");
          this.respForm.createTime = parseInt(new Date().getTime() / 1000);
          const result = await this.$http.post("resp/accept", 
          {
            requesetId:this.respForm.requestId,
            createTime:this.respForm.createTime,
            description:this.respForm.description,
          });
          if (result.data.status.code == 200) {
            // 响应issue成功
            this.$message({
              message: "响应委托帮忙成功",
              type: "success",
            });

            this.dialogVisible = false;
          } else {
            // 响应issue失败
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
    tryUpdateResp(){
      this.$refs.respFormRef.validate(async (valid)=>{
        if(valid){
          console.log("[IssueRespDialog]tryUpdateResp ...");
          this.respForm.updateTime = parseInt(new Date().getTime() / 1000);
          const result = await this.$http.post("resp/update", {
            id:this.respForm.id,
            description:this.respForm.description,
            updateTime:this.respForm.updateTime,
          });
          if (result.data.status.code == 200) {
            // 响应issue成功
            this.$message({
              message: "修改响应成功",
              type: "success",
            });

            this.dialogVisible = false;
          } else {
            // 响应issue失败
            this.$message({
              message: "修改响应失败:" + result.data.status.msg,
              type: "error",
            });
          }
        }else{
          //修改resp失败
          this.$message({
            message:"修改响应失败:"+result.data.status.msg,
            type:"error",
          })
        }
      })
    },
    init(row , type) {
      if(type == "look"){
        this.respForm = {
          requestId : row.requesetId,
          description : row.description,
        }
        this.readOnly = true;
      }else if(type == "edit"){
        this.respForm = {
          requestId: row.requestId,
          description : row.description,
          id : row.id,
          updateTime:0,
        }
        this.readOnly = false;
      }else if(type == "add"){
        this.respForm = {
          requestId : row.id,
          description : "",
          createTime : 0 ,
        }
        this.readOnly = false;
      }
      this.type = type;

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
