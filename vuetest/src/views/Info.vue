<template>
  <div class="nihao">
    <el-form style="width: 60%;margin-top: 30px;margin-left: 200px;display: flex;flex-wrap: wrap"
             :model="ruleForm" :rules="rules" ref="ruleForm"
             id="info-input"
             label-width="100px" label-position="top" class="demo-ruleForm">
      <el-form-item class="oneinput" label="绑定手机" prop="number">
        <el-input :readonly=true v-model="person.phone_num" style="font-size: 18px;width: 500px"></el-input>
      </el-form-item>
      <el-form-item class="oneinput" label="性别" prop="sex">
        <el-input :readonly=!change style="width: 300px" v-model="person.sex"></el-input>
      </el-form-item>
      <el-form-item class="oneinput" label="省" prop="region">
        <el-input :readonly=!change style="width: 500px" v-model="person.province"></el-input>
      </el-form-item>
      <el-form-item class="oneinput" label="市" prop="region">
        <el-input :readonly=!change style="width: 500px" v-model="person.city"></el-input>
      </el-form-item>
      <el-form-item class="oneinput" label="区/县" prop="region">
        <el-input :readonly=!change style="width: 500px" v-model="person.district"></el-input>
      </el-form-item>
      <el-form-item class="oneinput" label="详细街道" prop="region">
        <el-input :readonly=!change style="width: 500px" v-model="person.detail"></el-input>
      </el-form-item>
    </el-form>
    <div style="margin-left: 1000px;">
      <el-button :type="hhh" @click="change_info" >{{ submitB }}</el-button>
      <el-button type="danger" @click="cancel_info" :style="change==true?'{}':{display:'none'}">放弃修改</el-button>
    </div>
  </div>
</template>

<script>
import ls from '../assets/ls'

export default {
  data() {
    return {
      change: false,
      hhh: '',
      submitB: '修改信息',
      ruleForm: {
        name: '',
        region: '',
        date1: '',
        date2: '',
        delivery: false,
        type: [],
        resource: '',
        desc: ''
      },
      rules: {},
      person: null,
    };
  },
  created() {
    const _this = this;
    // console.log("session" + ls.getItem('username'))
    let cnm = ls.getItem('username')

    axios.get('http://localhost:8181/person/info/' + cnm).then(function (resp) {
      // _this.person.phone_num=resp.data[1].phone_num;
      console.log(resp)
      _this.person = resp.data;
      _this.person.province=resp.data.province;
      _this.person.city=resp.data.city;
      _this.person.district=resp.data.district;
      _this.person.detail=resp.data.detail;
      _this.person.location = resp.data.province + resp.data.city + resp.data.district + resp.data.detail;
    })
  },
  methods: {
    change_info() {
      this.change = true;
      this.hhh = 'success'
      this.submitB = '提交'
      if(this.submitB=='提交'){
        let person={
          phone_num:ls.getItem('username'),
          name:this.person.name,
          sex:this.person.sex,
          province:this.person.province,
          city:this.person.city,
          district:this.person.district,
          detail:this.person.detail,
          password:"123456"
        }
        axios.post("http://localhost:8181/person/change",person).then(function (resp){
          console.log(resp)
        })
      }
    },
    cancel_info(){
      this.change=false;
      this.hhh='';
      this.submitB='修改信息'
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          alert('submit!');
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    }
  }
}
</script>

<style>
@import "../styles/input-style.css";
</style>