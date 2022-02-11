<template>
  <el-table
      :data="tableData"
      style="width: 100%">
    <el-table-column
        prop="log_id"
        label="物件ID"
        width="80">
    </el-table-column>
    <el-table-column
        prop="sender"
        label="发件人号码"
        width="130">
    </el-table-column>
    <el-table-column
        prop="sender_name"
        label="发件人姓名"
        width="90">
    </el-table-column>
    <el-table-column
        prop="send_city"
        label="发件城市"
        width="120">
    </el-table-column>
    <el-table-column
        prop="receiver"
        label="收件人号码"
        width="130">
    </el-table-column>
    <el-table-column
        prop="receiver_name"
        label="收件人姓名"
        width="90">
    </el-table-column>
    <el-table-column
        prop="rece_city"
        label="收件城市"
        width="120">
    </el-table-column>
    <el-table-column
        prop="deliver_time"
        label="取件时间"
        width="160">
    </el-table-column>
    <el-table-column
        prop="deliver_way"
        label="取件方式"
        width="100">
    </el-table-column>
    <el-table-column
        prop="confirm"
        label="确认发送"
        width="100">
      <template slot-scope="scope">
        <el-button type="success" @click="confirmClick(scope.row)">确认</el-button>
      </template>
    </el-table-column>
    <el-table-column
        prop="cancel"
        label="取消发送"
        width="100">
      <template slot-scope="scope">
        <el-button type="danger" @click="cancelClick(scope.row)">退回</el-button>
      </template>
    </el-table-column>
  </el-table>
</template>

<script>
export default {
  inject: ['reload'],
  created() {
    const _this = this;
    axios.get('http://localhost:8181/mail/findBySign/' + '未发送').then(function (resp) {
      console.log(resp)
      _this.tableData = resp.data
    })
  },
  data() {
    return {
      tableData: []
    }
  },
  methods: {
    confirmClick(row) {
      console.log(row)
      const _this = this;
      axios.put('http://localhost:8181/mail/confirm', row).then(function (resp) {
            console.log(resp);
            if (resp.data == 'mail confirm success') {
              _this.$alert(row.log_id + '发送成功', '系统提示', {
                confirmButtonText: '确定',
                callback: action => {
                  _this.reload()
                }
              });
              axios.get('http://localhost:8181/direction/confirm/' + row.log_id).then(function (resp) {
                console.log(resp)
              })
            } else {
              _this.$alert(row.log_id + '确认失败', '系统提示', {
                confirmButtonText: '确定',
                callback: action => {
                  _this.reload()
                }
              })
            }
          }
      )
    },
    cancelClick(row) {
      console.log(row)
      const _this = this;
      this.$prompt('请输入退回理由', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
      }).then(({value}) => {
        // 从数据库删除logistics和mail
        axios.get('http://localhost:8181/logistics/delete/' + row.log_id).then(function (resp) {
          console.log(resp)
          if (resp.data == "delete success") {
            _this.$notify({
              type: 'success',
              message: "ID: " + row.log_id + " 已被退回",
              duration: 0
            })
          }
        });

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
        console.log(date);
        // 从数据库中删除Logistics和Mail中的数据 同时发一封消息给寄件人
        let reply = {
          'messagePK': {
            'phonenum': row.sender,
            'log_id': row.log_id,
            'type': "退回",
          },
          'content': value,
          'own': 0,
          'time':updateTime
        }
        console.log(reply)
        axios.put('http://localhost:8181/message/add', reply).then(function (resp) {
          console.log(resp);
          _this.reload()
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '取消输入'
        });
      });
    },
  }
}
</script>

<style scoped>

</style>