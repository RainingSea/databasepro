<template>
  <div class="op" v-bind:style="{backgroundImage:'url(' + bg + ')',
        backgroundRepeat:'no-repeat',
        backgroundSize:'100% 100%'}">
    <div class="back">
      <!--    <img :src="imgSrc" width="100%" height="90%" alt=""/>-->
      <div class="box">
        <el-tabs v-model="activeName" @tab-click="handleClick">
          <el-tab-pane label="用户登录" name="first">
            <el-form :rules="rules" :model="form" ref="form" style="margin-bottom: 20px">
              <el-form-item label="用户名" prop="user_name">
                <el-input v-model="form.user_name" maxlength="11" v-number-only></el-input>
              </el-form-item>
              <el-form-item label="密码" prop="user_passwd">
                <el-input v-model="form.user_passwd" type="password"></el-input>
              </el-form-item>
              <el-button type="primary" @click="login">登录</el-button>
            </el-form>
          </el-tab-pane>

          <el-tab-pane label="管理员登录" name="second">
            <el-form :rules="rules" :model="admin_form" ref="admin_form" style="margin-bottom: 20px">
              <el-form-item label="管理员账号" prop="admin_name">
                <el-input v-model="admin_form.admin_name" maxlength="11" v-number-only></el-input>
              </el-form-item>
              <el-form-item label="密码" prop="admin_passwd">
                <el-input v-model="admin_form.admin_passwd"></el-input>
              </el-form-item>
              <el-button type="primary" @click="admin_login">登录</el-button>
            </el-form>
          </el-tab-pane>
        </el-tabs>
      </div>
    </div>
  </div>
</template>

<script>
import ls from '../assets/ls'

export default {
  mounted() {
    window.Vue = this;
  },
  data() {
    return {
      activeName: 'first',
      form: {
        user_name: "",
        user_passwd: "",
      },
      admin_form:{
          admin_name:"",
          admin_passwd:"",
      },
      bg: require('../assets/cat.jpg'),
      rules: {
        user_name: [
          {required: true, message: '请输入账号', trigger: 'blur'},
        ],
        user_passwd: [
          {required: true, message: '请输入密码', trigger: 'blur'},
        ],
        admin_name: [
          {required: true, message: '请输入账号', trigger: 'blur'},
        ],
        admin_passwd: [
          {required: true, message: '请输入密码', trigger: 'blur'},
        ],
        //双向绑定 用户输入成功后建立一个session来记录账号
      }
    }
  },
  methods: {
    handleClick(tab, event) {
      console.log(tab, event)
    },
    login() {
      const _this = this;
      axios.get("http://localhost:8181/person/login/" + this.form.user_name + '/' + this.form.user_passwd).then(function (resp) {
        console.log(resp)
        if (resp.data.substring(0,4) === '密码正确') {

          ls.setItem('username', _this.form.user_name);
          ls.setItem('password', _this.form.user_passwd);

          console.log(_this.form.user_name)
          console.log(ls.getItem('username'))
          _this.$router.push('/Logistics')
        } else {
          _this.$alert('密码错误', '系统提示', {
            confirmButtonText: '确定',
            callback: action => {
              this.$message({
                type: 'info',
                message: `action: ${action}`
              });
            }
          });
        }
      })
    }
    ,
    admin_login() {
      const _this = this;
      axios.get("http://localhost:8181/person/login/" + this.admin_form.admin_name + '/' + this.admin_form.admin_passwd).then(function (resp) {
        console.log(resp)
        if (resp.data.substring(0,4) === '密码正确') {
          // ls.setItem('admin_test', _this.admin_form.admin_name);
          _this.$router.push('/Send_admin')
        } else {
          _this.$alert('密码错误', '系统提示', {
            confirmButtonText: '确定',
            callback: action => {
              this.$message({
                type: 'info',
                message: `action: ${action}`
              });
            }
          });
        }
      })
    }
  }
}
</script>
<style>
html body {
  margin: 0;
  padding: 0;
}

.back {
  margin-left: 1100px;
  margin-top: 140px;
  /*padding: -10px;*/
  font-family: sans-serif;
  width: 20%;
  z-index: -2;
  background-size: cover;
  background: rgba(0, 0, 0, .1);
  box-sizing: border-box;
  box-shadow: 0 15px 25px rgba(0, 0, 0, .5);
  border-radius: 10px;
}

.box {
  margin: 30px;

}

.box h2 {
  margin: 0 0 30px;
  padding: 0;
  color: #fff;
  text-align: center;
}

.op {
  margin-top: -140px;
  height: 720px;
}


</style>