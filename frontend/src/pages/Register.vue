<!--
 * @Description: 
 * @Author: l
 * @Date: 2021-11-11 18:32:59
 * @LastEditors: l
 * @LastEditTime: 2021-11-13 20:21:50
 * @FilePath: \frontend\src\pages\Register.vue
-->
<template>
  <div>
    <el-form
      :model="registerForm"
      ref="registerFormRef"
      label-width="80px"
      size="small"
      class="register-form"
      :rules="rules"
    >
      <el-form-item label="用户名" prop="username">
        <el-input
          v-model="registerForm.username"
          placeholder="请输入用户名"
        ></el-input>
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input
          v-model="registerForm.password"
          placeholder="请输入密码"
          show-password
        ></el-input>
      </el-form-item>
      <el-form-item label="用户姓名" prop="name">
        <el-input
          v-model="registerForm.name"
          placeholder="请输入用户姓名"
        ></el-input>
      </el-form-item>
      <el-form-item label="证件类型" prop="certificateType">
        <el-radio-group v-model="registerForm.certificateType">
          <el-radio :label="0">身份证</el-radio>
          <el-radio :label="1">护照</el-radio>
          <el-radio :label="2">港澳台居民居民身份证</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="证件号码" prop="certificateNum">
        <el-input
          v-model="registerForm.certificateNum"
          placeholder="请输入证件号码"
        ></el-input>
      </el-form-item>
      <el-form-item label="手机号码" prop="phoneNum">
        <el-input
          v-model="registerForm.phoneNum"
          placeholder="请输入手机号码"
        ></el-input>
      </el-form-item>
      <el-form-item label="注册城市" prop="selectOptions">
        <el-cascader :options="cityOptions" v-model="selectOptions" @change="handleChange">
        </el-cascader>
      </el-form-item>
      <el-form-item label="注册社区" prop="regCommunity">
        <el-input
          v-model="registerForm.regCommunity">
          placeholder="请输入小区名（例如xxxx小区）"
        ></el-input>
      </el-form-item>
      <el-form-item label="用户简介" prop="introduction">
        <el-input
          type="textarea"
          v-model="registerForm.introduction"
          placeholder="在这里输入你的简介"
        ></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="register('registerFormRef')">注册</el-button>
        <el-button type="primary" @click="resetForm()">重置</el-button
        >
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { defineComponent } from "@vue/composition-api";
import { provinceAndCityData, CodeToText } from "element-china-area-data";
// import { mixin } from "vue/types/umd";
export default defineComponent({
  setup() {},
  data: function () {
      var checkPassword = (rule,value,callback)=>{
          let regex1 = new RegExp('^[a-z]*[a-z]$')
          if(regex1.test(value)){
            return callback(new Error('密码不能全为小写'))
          }
          let regex2 = new RegExp('^[A-Z]*[A-Z]$')
          if(regex2.test(value)){
            return callback(new Error('密码不能全为大写'))
          }
          let regex3 = new RegExp('.*[0-9].*[0-9].*')
          if(!regex3.test(value)){
            return callback(new Error('密码中至少要包含2个数字'))
          }
          callback()
      }
      var checkCity = (rule,value,callback)=>{
        let opt = this.$data.selectOptions
        // console.log(opt)
        if(opt.length == 0)
          return callback(new Error('请选择城市'))
        callback()
      }
    return {
      registerForm: {
        username: "",
        password: "",
        name: "",
        certificateType: 0,
        certificateNum: "",
        phoneNum: "",
        introduction: "",
        regCity: "",
        regCommunity: "",
        regTime: "",
        updateTime: "",
      },
      cityOptions: provinceAndCityData,
      selectOptions:[ ],
      rules:{
        username:[
          {required: true, message:'请输入用户名', trigger: 'blur'},
          {min:1, max:32 , message:'用户名长度在1 - 32之间' ,trigger:'blur'},
        ],
        password:[
          {required: true, message:'请输入密码', trigger: 'blur'},
          {min:6, max:32 , message:'密码长度在6 - 32之间' ,trigger:'blur'},
          {validator: checkPassword, trigger: 'blur'}
        ],
        name:[
          {required: true, message:'请输入用户姓名', trigger: 'blur'},
        ],
        certificateNum:[
          {required: true, message:'请输入证件号码', trigger: 'blur'},
          {pattern:/^[0-9]+$/, message:'证件号码格式错误',trigger: 'blur'}
        ],
        phoneNum:[
          {required: true, message:'请输入手机号码', trigger: 'blur'},
          {pattern:/^1[3456789]\d{9}$/,message:'手机号码格式错误',trigger:"blur"}
        ],
        regCommunity:[
          {required: true, message:'请输入社区名称', trigger: 'blur'},
        ],
        selectOptions:[
          {validator: checkCity, trigger: 'blur'}
        ]
      }
    };
  },
  methods: {
    handleChange(value) {
      let province = CodeToText[value[0]];
      let city = CodeToText[value[1]];
      this.$data.registerForm.regCity = province + city;
      // console.log(this.$data.registerForm.regCity)
    },
    register(formName) {
      this.$refs[formName].validate(async (valid)=>{
        if(valid){
          //表单验证成功
          //修改创建时间和修改时间
          this.$data.registerForm.regTime = new Date().getTime()
          this.$data.registerForm.updateTime = new Date().getTime()
          const result = await this.$http.post('register', this.registerForm)
          
          if (result.data.status.code == 200) {
            // 注册成功
            this.$message({
              message: result.data.status.msg,
              type: 'success',
            })
            this.$router.push('/login')
          } else {
            // 注册失败
            this.$message({
              message: '注册失败:' + result.data.status.msg,
              type: 'error',
            })
          }
        }else{
          this.$message({
            message: "表单验证失败，请重新填写",
            type: 'warning'
          })
          return false
        }
      })
    },
    resetForm() {
      this.$data.registerForm = {
        username: "",
        password: "",
        name: "",
        certificateType: 0,
        certificateNum: "",
        phoneNum: "",
        introduction: "",
        regCity: "",
        regCommunity: "",
        regTime: "",
        updateTime: "",
      };
      this.$data.selectOptions=[]
    },
  },
});
</script>

<style scoped>
.el-form{
  margin-top: 10px;
  margin-left: 20%;
  margin-right: 20%;
  padding-left: 8%;
  padding-right: 8%;
  padding-top: 3%;
  padding-bottom: 3%;
  border-radius: 30px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

</style>
