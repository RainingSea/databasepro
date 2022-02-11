<template>
  <div style="margin-left: 50px">
    <div ref="month_chart" :style="{width: '900px', height: '300px'}"></div>
    <div style="display: flex;margin-left: 52px">
      <div ref="province_send_chart" style="width: 400px;height: 300px"></div>
      <div ref="province_receive_chart" :style="{width: '400px',height: '300px',marginLeft: '100px'}"></div>
    </div>
  </div>
</template>

<script>
import echarts from "echarts";

export default {
  name: "Statistic",
  data() {
    return {
      data11: [],
      data_mon: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12']
    }
  },
  created() {
  },
  mounted() {
    this.drawLine();
    this.drawLine2();
    this.drawLine3();
  },
  methods: {
    drawLine() {
      const _this = this;
      console.log(this.data11)
      const chart = this.$refs.month_chart
      // 基于准备好的dom，初始化echarts实例
      const myChart = echarts.init(chart)
      axios.get("http://localhost:8181/statistic/month_count").then(function (resp) {
        console.log(resp);
        let mon = resp.data;
        // 绘制图表
        myChart.setOption({
          title: {text: '各月份发送量'},
          tooltip: {},
          color: '#3183c2',
          xAxis: {
            data: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12],
            axisLabel: {
              inside: false,
              textStyle: {
                color: '#000',
                fontSize: '14',
                itemSize: ''
              },
            }
          },
          yAxis: {},
          // barCategoryGap:'20%',
          barWidth: 30,
          series: [{
            name: '销量',
            type: 'bar',
            // data: ['1','2','3','4','5','6','7','8','9','10','11','12']
            data: mon
          }]
        });
      })
    },
    drawLine2() {
      const chart = this.$refs.province_send_chart
      // 基于准备好的dom，初始化echarts实例
      const myChart = echarts.init(chart)
      // 绘制图表
      axios.get("http://localhost:8181/statistic/pro_send_count").then(function (resp) {
        console.log(resp);
        let pro=new Array();
        let fre=new Array();
        for(let i=0;i<3;i++){
          if(resp.data[i].province==null){
          }
          else{
            pro.push(resp.data[i].province)
            fre.push(resp.data[i].frequency)
          }
        }
        myChart.setOption({
          title: {text: '发件最多省份'},
          tooltip: {},
          color: '#31c288',
          xAxis: {
            data: pro,
            axisLabel: {
              inside: false,
              textStyle: {
                color: '#000',
                fontSize: '14',
                itemSize: ''
              },
            }
          },
          yAxis: {},
          // barCategoryGap:'20%',
          barWidth: 30,
          series: [{
            name: '销量',
            type: 'bar',
            data: fre
          }]
        })
      });
    },
    drawLine3() {
      const chart = this.$refs.province_receive_chart
      // 基于准备好的dom，初始化echarts实例
      const myChart = echarts.init(chart)
      // 绘制图表
      axios.get("http://localhost:8181/statistic/pro_receive_count").then(function (resp) {
        console.log(resp);
        let pro=new Array();
        let fre=new Array();
        for(let i=0;i<3;i++){
          if(resp.data[i].province==null){
          }
          else{
            pro.push(resp.data[i].province)
            fre.push(resp.data[i].frequency)
          }
        }
        myChart.setOption({
          title: {text: '收件最多省份'},
          tooltip: {},
          color: '#31c288',
          xAxis: {
            data: pro,
            axisLabel: {
              inside: false,
              textStyle: {
                color: '#000',
                fontSize: '14',
                itemSize: ''
              },
            }
          },
          yAxis: {},
          // barCategoryGap:'20%',
          barWidth: 30,
          series: [{
            name: '销量',
            type: 'bar',
            data: fre
          }]
        })
      });
    }
  }
}
</script>


<style scoped>

</style>