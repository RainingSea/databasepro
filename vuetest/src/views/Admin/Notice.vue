<template>
  <div>
  <h2>输入通知内容</h2>
  <el-input
      type="textarea"
      :autosize="{ minRows: 5, maxRows: 10}"
      placeholder="请输入内容"
      v-model="notice_content">
  </el-input>
  <el-button type="success" style="margin-top: 20px;margin-left: 90%" @click="sub_notice">提交</el-button>
  </div>
</template>

<script>
export default {
  name: "Notice",
  data(){
    return{
      notice_content:"",
    }
  },
  methods:{
    sub_notice(){
      let notice={
        'content':this.notice_content,
        'dirty':'1'
      };
      axios.post('http://localhost:8181/notice/save',notice).then(function (resp){
        console.log(resp)
      })
    }
  }
}
</script>

<style scoped>

</style>