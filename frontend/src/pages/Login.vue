<!--
 * @Description: 
 * @Author: l
 * @Date: 2021-06-01 10:29:38
 * @LastEditors: l
 * @LastEditTime: 2021-11-13 14:33:27
 * @FilePath: \frontend\src\pages\Login.vue
-->
<template>
  <div>
    <el-form
      :model='loginForm'
      ref='loginFormRef'
      label-width='60px'
      size='small'
      class='login-form'
    >
      <el-row align='middle' type='flex'>
        <el-col :span='10'> 账号: </el-col>
        <el-col :span='10'>
          <el-input
            class='inline-input'
            v-model='loginForm.username'
            placeholder='请输入账号'
          ></el-input>
        </el-col>
      </el-row>
      <el-row align='middle' type='flex' style='margin-top: 20px'>
        <el-col :span='10'> 密码: </el-col>
        <el-col :span='10'>
          <el-input
            v-model='loginForm.password'
            show-password
            placeholder='请输入密码'
          ></el-input>
        </el-col>
      </el-row>
      <el-row align='middle' type='flex' style='margin-top: 20px'>
        <el-col :span='8' :offset='0'>
          <el-button type='primary' @click='login'> 登录 </el-button>
        </el-col>
        <el-col :span='8'>
          <el-button @click='reset'>重置</el-button>
        </el-col>
        <el-col :span="8">
          <el-button type="primary" @click="register">注册</el-button>
        </el-col>
      </el-row>
    </el-form>
  </div>
</template>

<script>
import { defineComponent } from '@vue/composition-api'

export default defineComponent({
  setup() {},
  name: 'Login',
  data: function () {
    return {
      loginForm: {
        username: 'admin',
        password: '',
      },
    };
  },
  computed: {},
  methods: {
    // 重置用户名和密码
    reset: function () {
      console.log('reset')
      this.loginForm.username = ''
      this.loginForm.password = ''
    },
    // 登录函数
    login: function () {
      console.log('login')
      this.$refs.loginFormRef.validate(async (valid) => {
        if (valid) {
          const result = await this.$http.post('login', this.loginForm)
          if (result.data.status.code == 200) {
            // 登录成功
            this.$message({
              message: result.data.status.msg,
              type: 'success',
            })
            // 保存token值  
            window.sessionStorage.setItem('token',result.data.token)
            this.$router.push('/home')
          } else {
            // 登录失败
            this.$message({
              message: '登录失败:' + result.data.status.msg,
              type: 'error',
            })
          }
        }
      });
    },
    register:function(){
      console.log('go to register')
      this.$router.push('/register')
    },
  },
});
</script>
<style scoped>
.login-form {
  margin-right: 35%;
  margin-left: 35%;
  margin-top: 50px;
  border-radius: 30px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  padding-top: 30px;
  padding-left: 40px;
  padding-right: 40px;
  padding-bottom: 25px;
}
</style>