<template>
  <div>
    <!--    <div>-->
    <!--      <v-distpicker style="" :placeholders="placeholders" @selected="onSelected"></v-distpicker>-->
    <!--      <button @click="go">点击</button>-->
    <!--    </div>-->
<!--    <baidu-map class="map" center="济南" zoom="9" :scroll-wheel-zoom="true" @ready="handler">-->
<!--      <bm-marker v-for="(item) in polylinePath" :position="{lng:item.lng,lat:item.lat}">-->
<!--      </bm-marker>-->
<!--      &lt;!&ndash;      <bm-marker :position="{lng: 116.404, lat: 39.915}" :dragging="true" >&ndash;&gt;-->
<!--      &lt;!&ndash;      </bm-marker>&ndash;&gt;-->
<!--      &lt;!&ndash;      <bm-marker :position="{lng: 116.71619325977075, lat: 39.5254432742791}" :dragging="true" >&ndash;&gt;-->
<!--      &lt;!&ndash;      </bm-marker>&ndash;&gt;-->
<!--      &lt;!&ndash;      <bm-marker :position="{lng: 117.22358632545986, lat: 39.15109633163684}" :dragging="true" >&ndash;&gt;-->
<!--      &lt;!&ndash;      </bm-marker>&ndash;&gt;-->
<!--      <bm-polyline :path="polylinePath" stroke-color="blue" :stroke-opacity="0.5" :stroke-weight="4"-->
<!--                   @lineupdate="updatePolylinePath"></bm-polyline>-->
<!--    </baidu-map>-->
    <table>
      <tr v-for="item in persons">
        <td>{{ item.phone_num }}</td>
        <td>{{ item.province }}</td>
        <td>{{ item.city }}</td>
      </tr>
    </table>
    <div ref="chart" :style="{width: '900px', height: '300px'}"></div>
  </div>

</template>

<script>

import VDistpicker from 'v-distpicker'
import mydarkstyle from '../assets/custom_map_config.json'
import  echarts from 'echarts'
export default {
  components: {
    VDistpicker,
  },
  mounted() {
    this.drawLine();
  },
  data() {
    return {
      persons: [],
      polylinePath: [
        {
          lng: 114.21, lat: 36.06
        }, {
          lng: 113.49, lat: 34.01
        }, {
          lng: 117.0, lat: 36.40
        }
      ],
      xcount: 1,
      placeholders: {
        province: '--- 省 ---',
        city: '--- 市 ---',
        area: '--- 区 ---',
      },
      center: {lng: 0, lat: 0},
      zoom: 3,
      xprovince: '',
      xcity: '安阳',
      xarea: '',
      points: [
        {lng: 116.432045, lat: 39.910683},
        {lng: 120.129721, lat: 30.314429},
        {lng: 121.491121, lat: 25.127053}
      ],
    }
  },
  methods: {
    go: function () {
      this.xcount++;
    },
    onSelected(val) {
      console.log(val);
      this.xprovince = val.province;
      this.xcity = val.city.value;
      // alert(this.xcity);
    },
    handler({BMap, map}) {
      // let point = new BMapGL.Point(116.404, 39.915);
      // let marker = new BMapGL.Marker(point);        // 创建标注
      // map.addOverlay(marker);
      map.setMapStyle({styleJson: mydarkstyle})
    },
    drawLine(){
      const chart = this.$refs.chart
      // 基于准备好的dom，初始化echarts实例
      const myChart = echarts.init(chart)
      // 绘制图表
      myChart.setOption({
        title: { text: '各月份发送量' },
        tooltip: {},
        color:'#3183c2',
        xAxis: {
          data: [1,2,3,4,5,6,7,8,9,10,11,12]
        },
        yAxis: {},
        // barCategoryGap:'20%',
        barWidth:30,
        series: [{
          name: '销量',
          type: 'bar',
          data: [20, 36, 10, 10, 20,5, 20, 36, 10, 10, 20,5]
        }]
      });
    }
  }
}
</script>
<style type="text/css">
.map {
  width: 80%;
  height: 700px;
}
</style>