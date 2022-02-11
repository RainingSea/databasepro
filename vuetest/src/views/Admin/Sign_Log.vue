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
        width="130">
    </el-table-column>
    <el-table-column
        prop="send_city"
        label="发件地址"
        width="185">
    </el-table-column>
    <el-table-column
        prop="receiver"
        label="收件人号码"
        width="130">
    </el-table-column>
    <el-table-column
        prop="receiver_name"
        label="收件人姓名"
        width="130">
    </el-table-column>
    <el-table-column
        prop="rece_city"
        label="收件地址"
        width="100">
    </el-table-column>
    <el-table-column
        prop="sign"
        label=""
        width="120">
    </el-table-column>
    <el-table-column
        prop="cancel"
        label="取消发送"
        width="120">
      <template slot-scope="scope">
        <el-button type="danger" @click="cancelClick(scope.row)">删除记录</el-button>
      </template>
    </el-table-column>
  </el-table>
</template>

<script>
export default {
  created() {
    const _this = this;
    axios.get('http://localhost:8181/mail/findBySign/' + '已签收').then(function (resp) {
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
    cancelClick(row) {
      const _this = this;
      this.$confirm('确认删除?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        axios.get('http://localhost:8181/logistics/deleteAll/' + row.log_id).then(function (resp) {
          console.log(resp)
          if (resp.data == "delete success") {
            _this.$notify({
              type: 'success',
              message: "ID: " + row.log_id + " 已被删除",
              duration: 0
            })
          }
          _this.reload()
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });

    }
  }
}
</script>

<style scoped>

</style>