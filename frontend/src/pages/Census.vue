<!--
 * @Description: 
 * @Author: l
 * @Date: 2021-11-20 13:41:38
 * @LastEditors: l
 * @LastEditTime: 2021-11-20 15:51:15
 * @FilePath: \frontend\src\pages\Census.vue
-->
<template>
  <div class="container">
    <el-row>
      <el-col>
        <!-- 查询栏 -->
        <el-row>
          <el-col :span="21">
            <el-form ref="queryFormRef" :model="query" label-width="80px" :rules="rules">
              <el-col :span="7">
                <el-form-item label="委托类型">
                  <el-select v-model="query.type" placeholder="委托类型">
                    <el-option label="小时工" :value="0"></el-option>
                    <el-option label="搬重物" :value="1"></el-option>
                    <el-option label="上下班打车" :value="2"></el-option>
                    <el-option label="社区志愿" :value="3"></el-option>
                    <el-option label="其他" :value="4"></el-option>
                    <el-option label="所有" :value="5"></el-option>
                  </el-select>
                </el-form-item>
              </el-col>

              <el-col :span="7">
                <el-form-item label="城市名称">
                  <el-input
                    v-model="query.regCity"
                    placeholder="城市名称"
                  ></el-input>
                </el-form-item>
              </el-col>

              <el-col :span="7">
                <el-form-item label="小区名称">
                  <el-input
                    v-model="query.regCommunity"
                    placeholder="小区名称"
                  ></el-input>
                </el-form-item>
              </el-col>

              <el-col :span="7">
                <el-form-item label="时间范围" prop="timeRange">
                  <el-date-picker
                    v-model="query.time"
                    type="monthrange"
                    range-separator="至"
                    start-placeholder="开始月份"
                    end-placeholder="结束月份"
                  >
                  </el-date-picker>
                </el-form-item>
              </el-col>
            </el-form>
          </el-col>
          <el-col :span="2">
            <el-button
              type="primary"
              icon="el-icon-search"
              @click="handleSearch"
              >查找</el-button
            >
          </el-col>
        </el-row>
        <!-- 折线图1 总交易数-->
        <el-row>
          <div class="canvas-wrapper">
           <schart 
            class="schart"
            canvasId="canvasCount"
            :options="optionsCount"
            v-if="dataReady"
            ></schart>
          </div>
        </el-row>

         <el-row>
          <div class="canvas-wrapper">
           <schart 
            class="schart"
            canvasId="canvasFee"
            :options="optionsFee"
            v-if="dataReady"
            ></schart>
          </div>
        </el-row>

      </el-col>
    </el-row>
  </div>
</template>
<script>
import { defineComponent } from "@vue/composition-api";
import Schart from "vue-schart";
export default defineComponent({
  components:{Schart},
  setup() {},
  data() {
    var checkTime = (rule,value,callback)=>{
        if(this.query.time == null || this.query.time.length == 0)
            return callback(new Error('请选择时间范围'))
        callback()
    }
    return {
      rules:{
          timeRange:[{
              validator: checkTime, trigger: 'blur'
          }]
      },
      query: {
        time: [],
        regCity: "",
        regCommunity: "",
        type: 5,
      },
      optionsCount:{
          type:"line",
          title:{
              text:"每月总交易数"
          },
          labels:[],
          legend: {
                position: "left",
            },
          datasets:[{
              label:"每月总交易数",
              fillColor: "rgba(241, 49, 74, 0.5)",
              data:[],
          }]
      },
      optionsFee:{
          type:"line",
          title:{
              text:"每月中介费"
          },
          legend: {
                position: "left",
          },
          labels:[],
          datasets:[{
              label:"每月中介费",
              fillColor: "rgba(241, 49, 74, 0.5)",
              data:[],
          }]
      },
      dataReady:false,
    };
  },
  methods: {
    handleSearch() {
      this.$refs["queryFormRef"].validate(async (valid) => {
        if (valid) {
          //表单验证成功
          console.log("[Census]handleSearch...");
          const result = await this.$http.get("census/query", {
            params: {
              startYear: new Date(this.query.time[0]).getFullYear(),
              startMonth: new Date(this.query.time[0]).getMonth() + 1,
              endYear: new Date(this.query.time[1]).getFullYear(),
              endMonth: new Date(this.query.time[1]).getMonth() + 1,
              regCity: this.query.regCity,
              regCommunity: this.query.regCommunity,
              type: this.query.type,
            },
          });
          this.dataReady=false;
          if (result.data.status.code == 200) {
            //查询成功
            this.$message({
              message: "查询成功",
              type: "success",
            });
            
            //渲染折线图，首先构造label
            let tmpLabel=[];
            let datas = result.data.datas;
            this.optionsCount.datasets[0].data = [];
            this.optionsFee.datasets[0].data = [];
            let i=0;
            for(i=0;i<datas.length;i++){
                tmpLabel.push(datas[i].year.toString()+"."+datas[i].month.toString())
                this.optionsCount.datasets[0].data.push(datas[i].count)
                this.optionsFee.datasets[0].data.push(datas[i].totalFee)
            }
            this.optionsCount.labels = tmpLabel;
            this.optionsFee.labels = tmpLabel;
            this.dataReady = true;
            //添加数据
          } else {
            // 查询失败
            this.$message({
              message: "查询失败:" + result.data.status.msg,
              type: "error",
            });
          }
        } else {
          this.$message({
            message: "表单验证失败，请重新填写",
            type: "warning",
          });
          return false;
        }
      });
    },
  },
});
</script>
<style scoped>
.canvas-wrapper{
    display: inline-block;
    margin: 20px;
}
.schart {
    width: 600px;
    height: 400px;
}
</style>
