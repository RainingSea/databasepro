<template>
  <el-collapse v-model="activeNames" @change="handleChange">
    <el-collapse-item :name="index+''" class="card" v-for="(message,index) in all_message">

      <template slot="title">
        <label style="font-size: 15px;">{{ message.messagePK.phonenum }}</label>
        <label style="font-size: 15px;margin-left: 50px;color: cornflowerblue">{{ message.messagePK.log_id }}</label>
        <label style="font-size: 15px;margin-left: 50px;color: red">{{ message.messagePK.type }}</label>
        <label style="font-size: 15px;margin-left: 50px;color: red">{{message.time}}</label>
      </template>

      <!--      记录物件开始和结束的信息-->

      <div style="margin-top: 20px;display: flex;flex-direction: column">
        <div style="margin-left: 20px">{{ message.content }}</div>
        <div style="margin-top: 40px;margin-left: 20px"><el-input
            type="textarea"
            :autosize="{ minRows: 5, maxRows: 10}"
            placeholder="请输入内容"
            v-model="reply_content[index]">
        </el-input>
        </div>
        <el-button type="success" style="margin-top: 20px;margin-left: 90%"
                   @click="sub_reply(message.messagePK.phonenum,message.messagePK.log_id,message.messagePK.type,index)">回复</el-button>
      </div>
    </el-collapse-item>

    <!--    有几个对象返回，就生成几个el-collapse-item-->
  </el-collapse>
</template>
<script>
export default {
  created() {
    const _this = this;
    axios.get('http://localhost:8181/message/admin_find/').then(function (resp) {
      console.log(resp)
      _this.all_message = resp.data;
    })
  },
  data() {
    return {
      activeNames: [],
      all_message: null,
      reply_content:[],
    };
  },
  methods: {
    handleChange(val) {
      console.log(val);
    },
    sub_reply(number,id,type,index){
      const _this=this;
      console.log(number);
      console.log(id);
      console.log(type);
      console.log(this.reply_content[index]);
      let nowDate = new Date();
      console.log(nowDate.getHours());
      let date = {
        'year': nowDate.getFullYear(),
        'month': nowDate.getMonth() + 1,
        'date': nowDate.getDate(),
        'hour': nowDate.getHours(),
        'minute': nowDate.getMinutes(),
        'second': nowDate.getSeconds(),
      }
      let new_month = date.month > 10 ? date.month : '0' + date.month
      let new_date = date.date > 10 ? date.date : '0' + date.date
      let new_hour = date.hour > 10 ? date.hour : '0' + date.hour
      let new_minute = date.minute > 10 ? date.minute : '0' + date.minute
      let new_second = date.second > 10 ? date.second : '0' + date.second

      let updateTime = date.year + '-' + new_month + '-' + new_date
          + ' ' + new_hour + ':' + new_minute + ':' + new_second;
      let reply={
        'messagePK': {
          'phonenum': number,
          'log_id': id,
          'type': type+"回复",
        },
        'content': this.reply_content[index],
        'own': 0,
        'time':updateTime,
      }
      axios.put('http://localhost:8181/message/add', reply).then(function (resp) {
        console.log(resp);
        if(resp.data=="complaint success"){
          _this.$notify({
            type: 'success',
            message: id+" 回复成功",
            duration: 2000
          });
        }
      })
    }
  }
}
</script>