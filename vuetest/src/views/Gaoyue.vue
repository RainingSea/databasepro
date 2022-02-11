<template>
  <div>
    <div style="display: flex;">
      <label style="font-size: 20px;">{{ mail.log_id }}</label>
      <div style="display: flex;flex-direction: column;margin-left: 100px">
        <label style="font-size: 15px;margin-left: 50px">
          {{ mail.send_city + '  --->  ' + mail.rece_city }}</label>
        <label style="font-size: 15px;margin-left: 50px;margin-top: 20px;color: grey">
          {{ mail.sender_name + '  --->  ' + mail.receiver_name }}</label>
      </div>
      <label style="font-size: 20px;margin-left: 50px;color: red">{{ mail.sign }}</label>
    </div>
    <div style="margin-top: 80px;display: flex">
      <el-timeline :reverse="reverse" style="width: 200px">
        <el-timeline-item
            v-for="(one_location, timeline_index) in all_location"
            :key="timeline_index"
            :timestamp="one_location.locationPK.logtime">
          {{ one_location.log_location }}
        </el-timeline-item>
      </el-timeline>
      <!--        时间线内根据返回的列表中的每一项的location来for循环生成-->
      <!--        location记录了时间和地点以及状态位-->

      <!--        嵌入的地图-->
      <baidu-map class="testmap" center="安阳" zoom="9" :scroll-wheel-zoom="true">
        <bm-marker v-for="(lng_lat,lng_index) in all_location"
                   :position="{lng:lng_lat.log_lng,lat:lng_lat.log_lat}">
          <bm-label :content=des_prefix+lng_lat.log_location+des_next_site+mail.next_site
                    :labelStyle="lng_index==(all_location.length-1)?{color: 'red', fontSize : '12px'}:{display:'none'}"
                    :offset="{width: -35, height: -30}"/>
        </bm-marker>

        <!--          标记终点站的-->
        <bm-marker :position={lng:sb.lng,lat:sb.lat}>
          <bm-label :content=mail.end_place
                    :labelStyle="{color: 'blue', fontSize : '12px',fontWeight: bold}"
                    :offset="{width: -35, height: 30}"/>
        </bm-marker>

        <!--          画出路线轨迹-->
        <bm-polyline :path="all_lnglat" stroke-color="blue"
                     :stroke-opacity="0.5" :stroke-weight="4"></bm-polyline>
        <!--          点和线的生成也是依赖于location的每一项-->
      </baidu-map>
    </div>
  </div>
</template>

<script>
import ls from "@/assets/ls";
import axis from "axios";

export default {
  name: "Gaoyue",
  data() {
    return {
      mail: null,
      // mail: {
      //   'log_id': 'XA6122',
      //   'send_city': '山东省济南市',
      //   'rece_city': '河南省安阳市',
      //   'sender_name': '韩静吴',
      //   'receiver_name': '廉尚秀',
      //   'next_site':'山东省济南市',
      //   'end_place': '河南省安阳市文峰区拱辰广场',
      //   'sign':'运输中'
      // },
      all_location: [],
      all_lnglat: [],
      sb: {},
      des_prefix:'当前到达: ',
      des_next_site:'<br/>下一站发往: '
    }
  },
  created() {
    console.log(this.$route.query)
    this.mail=this.$route.query
    const _this = this;
    //第一个axios请求对应用户的所有邮寄货件 根据手机号
    //返回的是Site_mail 附带了下一站信息的mail
    if (_this.mail.end_place == null) {
    } else {
      axis.get("/api/geocoding/v3/?address=" + this.mail.end_place + "&output=json" +
          "&ak=GiWwDBls9BbTRYzOYKFXdOZiPMudUjVF").then(function (resp) {
        console.log('nmsl')
        //标记终点位置 ajax异步请求总是出错
        _this.sb['lng'] = resp.data.result.location.lng;
        _this.sb['lat'] = resp.data.result.location.lat;
      })
      axios.get('http://localhost:8181/location/findBy/' + this.mail.log_id).then(function (resp) {
        //对于每一个货件，都请求所有的位置信息
        console.log(resp)
        _this.all_location = resp.data;
        _this.all_lnglat = new Array(resp.data.length)
        //   let test = new Array()
        for (let j = 0; j < _this.all_location.length; j++) {
          _this.all_lnglat[j] = {
            'lng': resp.data[j].log_lng,
            'lat': resp.data[j].log_lat
          }
        }
      })
    }
  }
}
</script>

<style scoped>
.testmap {
  margin-left: 150px;
  margin-top: -50px;
  width: 80%;
  height: 550px;
}
</style>