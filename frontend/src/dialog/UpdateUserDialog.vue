<!--
 * @Description: 
 * @Author: l
 * @Date: 2021-11-13 17:40:06
 * @LastEditors: l
 * @LastEditTime: 2021-11-13 20:19:34
 * @FilePath: \frontend\src\pages\UpdateUserDialog.vue
-->
<template>
  <el-dialog
    title="修改用户信息"
    :visible.sync="dialogVisible"
    width="50%"
    append-to-body
    :before-close="dialogBeforeClose"
  >
    <el-form
      :model="userInfoForm"
      ref="userInfoFormRef"
      label-width="80px"
      size="small"
      class="user-info-form"
      :rules="rules"
    >
      <el-form-item label="用户名" prop="username">
        <el-input v-model="userInfoForm.username" :disabled="true"></el-input>
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input
          v-model="userInfoForm.password"
          placeholder="请输入密码"
          show-password
          
        ></el-input>
      </el-form-item>
      <el-form-item label="用户姓名" prop="name">
        <el-input v-model="userInfoForm.name" :disabled="true"></el-input>
      </el-form-item>

      <el-form-item label="证件类型" prop="certificateType">
        <el-radio-group v-model="userInfoForm.certificateType" :disabled="true">
          <el-radio :label="0">身份证</el-radio>
          <el-radio :label="1">护照</el-radio>
          <el-radio :label="2">港澳台居民居民身份证</el-radio>
        </el-radio-group>
      </el-form-item>

      <el-form-item label="证件号码" prop="certificateNum">
        <el-input
          v-model="userInfoForm.certificateNum"
          :disabled="true"
        ></el-input>
      </el-form-item>

      <el-form-item label="手机号码" prop="phoneNum">
        <el-input v-model="userInfoForm.phoneNum"></el-input>
      </el-form-item>

      <el-form-item label="注册城市" prop="selectOptions">
        <el-input v-model="userInfoForm.regCity" :disabled="true"></el-input>
      </el-form-item>

      <el-form-item label="注册社区" prop="regCommunity">
        <el-input v-model="userInfoForm.regCommunity" :disabled="true">
        </el-input>
      </el-form-item>

      <el-form-item label="用户简介" prop="introduction">
        <el-input
          type="textarea"
          rows="3"
          v-model="userInfoForm.introduction"
        ></el-input>
      </el-form-item>
    </el-form>

    <div slot="footer">
      <el-button @click="dialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="tryUpdate()">确 定</el-button>
    </div>
  </el-dialog>
</template>
<script>
import { defineComponent } from "@vue/composition-api";

export default defineComponent({
  setup() {},
  data() {
    var checkPassword = (rule, value, callback) => {
      let regex1 = new RegExp("^[a-z]*[a-z]$");
      if (regex1.test(value)) {
        return callback(new Error("密码不能全为小写"));
      }
      let regex2 = new RegExp("^[A-Z]*[A-Z]$");
      if (regex2.test(value)) {
        return callback(new Error("密码不能全为大写"));
      }
      let regex3 = new RegExp(".*[0-9].*[0-9].*");
      if (!regex3.test(value)) {
        return callback(new Error("密码中至少要包含2个数字"));
      }
      callback();
    };
    return {
      dialogVisible: false,
      userInfoForm: {},
      rules: {
        password: [
          { required: true, message: "请输入密码", trigger: "blur" },
          { min: 6, max: 32, message: "密码长度在6 - 32之间", trigger: "blur" },
          { validator: checkPassword, trigger: "blur" },
        ],
        phoneNum: [
          { required: true, message: "请输入手机号码", trigger: "blur" },
          {
            pattern: /^1[3456789]\d{9}$/,
            message: "手机号码格式错误",
            trigger: "blur",
          },
        ],
      },
    };
  },
  methods: {
    dialogBeforeClose() {
      // console.log('dialogBeforeClose')
      this.dialogVisible = false;
    },
    init(userInfo) {
      this.userInfoForm =Object.assign({},userInfo)
      this.dialogVisible = true;
    },
    tryUpdate() {
      this.$refs['userInfoFormRef'].validate(async (valid) => {
        if (valid) {
          //表单验证成功
          console.log('updateInfo ...')
          const result = await this.$http.post("user/update", {
            password: this.userInfoForm.password,
            phoneNum: this.userInfoForm.phoneNum,
            introduction: this.userInfoForm.introduction,
          });
          if (result.data.status.code == 200) {
            // 修改信息成功
            this.$message({
              message: "修改用户信息成功",
              type: "success",
            });
            this.dialogVisible = false;
          } else {
            // 注册失败
            this.$message({
              message: "修改信息失败:" + result.data.status.msg,
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
  },
});
</script>
<style scoped>
</style>