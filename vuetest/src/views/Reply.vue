<template>
  <div class="haha">
  <el-collapse v-model="activeNames" @change="handleChange">
    <el-collapse-item :name="index+''" class="card" v-for="(message,index) in all_message">

      <template slot="title">
        <label style="font-size: 15px;">{{ message.messagePK.phonenum }}</label>
        <label style="font-size: 15px;margin-left: 50px;color: cornflowerblue">{{ message.messagePK.log_id }}</label>
        <label style="font-size: 15px;margin-left: 50px;color: red">{{ message.messagePK.type }}</label>
        <label style="font-size: 15px;margin-left: 50px;color: red">{{message.time}}</label>
      </template>

      <!--      记录物件开始和结束的信息-->

      <div style="margin-top: 20px;display: flex">
        <label style="font-size: 15px;margin-left: 40px">{{ message.content }}</label>
      </div>
    </el-collapse-item>

    <!--    有几个对象返回，就生成几个el-collapse-item-->
  </el-collapse>
  </div>
</template>
<script>
export default {
  created() {
    const _this = this;
    axios.get('http://localhost:8181/message/reply/'+"15564107739").then(function (resp) {
      console.log(resp)
      _this.all_message = resp.data;
    })
  },
  data() {
    return {
      activeNames: [],
      all_message: null
    };
  },
  methods: {
    handleChange(val) {
      console.log(val);
    },
  }
}
</script>