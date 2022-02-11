<template>
  <el-collapse v-model="activeNames" @change="handleChange">
    <el-collapse-item :name="index+''" class="card" v-for="(mail,index) in all_mail">

      <template slot="title">
        <label style="font-size: 15px;">{{ mail.log_id }}</label>
        <div style="display: flex;flex-direction: column;margin-left: 100px">
          <label style="font-size: 15px;margin-left: 50px">
            {{ mail.send_city + '  --->  ' + mail.rece_city }}</label>
          <label style="font-size: 13px;margin-left: 50px;margin-top: -20px;color: grey">
            {{ mail.sender_name + '  --->  ' + mail.receiver_name }}</label>
        </div>
        <label style="font-size: 15px;margin-left: 50px;font-weight: bold">{{ mail.sign }}</label>
        <el-select v-model="value[index]" placeholder="反馈物件错误" style="margin-left: 450px"
                   @change="change(mail.log_id,index)">
          <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value">
          </el-option>
        </el-select>
      </template>

      <!--      记录物件开始和结束的信息-->

      <div style="margin-top: 20px;display: flex">
        <div>
          <el-timeline :reverse="reverse" style="width: 200px">
            <el-timeline-item
                v-for="(one_location, timeline_index) in all_mail[index].all_location"
                :key="timeline_index"
                :timestamp="one_location.locationPK.logtime">
              {{ one_location.log_location }}
            </el-timeline-item>
          </el-timeline>
          <div v-if="all_mail[index].show">
            <label>配送员</label>
            <label style="font-size: 15px;color: #03a9f4;font-weight: bold">
              {{ " " + all_mail[index].courier.courier_name }}
            </label>
            <label>正在配送</label>
            <br>
            <label>联系方式</label>
            <label v-if="all_mail[index].show" style="font-size: 15px;color: #f40303;font-weight: bold">
              {{ " " + all_mail[index].courier.courier_num }}
            </label>
          </div>
        </div>
        <!--        时间线内根据返回的列表中的每一项的location来for循环生成-->
        <!--        location记录了时间和地点以及状态位-->

        <!--        嵌入的地图-->
        <baidu-map class="testmap" center="郑州" zoom="9" :scroll-wheel-zoom="true">
          <bm-marker v-for="(lng_lat,lng_index) in all_mail[index].all_location"
                     :position="{lng:lng_lat.log_lng,lat:lng_lat.log_lat}">
            <bm-label :content=des_prefix+lng_lat.log_location+des_next_site+all_mail[index].next_site
                      :labelStyle="lng_index==(all_mail[index].all_location.length-1)?{color: 'red', fontSize : '12px'}:{display:'none'}"
                      :offset="{width: -35, height: 30}"/>
          </bm-marker>
<!--                    标记起点站的-->
          <bm-marker :position={lng:xb[index].lng,lat:xb[index].lat}
                     v-if=xb[index].show>
            <bm-label :content="xb[index].location"
                      :labelStyle="{color: 'green', fontSize : '12px',fontWeight: bold}"
                      :offset="{width: -35, height: 30}"/>
          </bm-marker>
<!--                    标记终点站的-->
          <bm-marker :position={lng:sb[index].lng,lat:sb[index].lat}>
            <bm-label :content=all_mail[index].end_place
                      :labelStyle="{color: 'blue', fontSize : '12px',fontWeight: bold}"
                      :offset="{width: -35, height: -30}"/>
          </bm-marker>
          <!--          画出路线轨迹-->
          <bm-polyline :path="all_mail[index].all_lnglat" stroke-color="blue"
                       :stroke-opacity="0.5" :stroke-weight="4"></bm-polyline>
<!--                    点和线的生成也是依赖于location的每一项-->
        </baidu-map>
      </div>

    </el-collapse-item>

    <!--    有几个对象返回，就生成几个el-collapse-item-->
  </el-collapse>
</template>

<script>
import TestMap from './Test'
import axis from "axios";
import ls from '../assets/ls'

export default {

  components: {
    'testmap': TestMap,
  },
  mounted() {
    window.Vue = this;
  },
  data() {
    return {
      activeNames: [''],
      all_mail: null,
      des_prefix: '当前到达: ',
      des_next_site: '<br>下一站发往: ',
      //错误反馈用到的数据
      options: [{
        value: '投递错误',
        label: '投递错误'
      }, {
        value: '丢失',
        label: '丢失'
      }],
      value: [],
      sb: [{}, {}, {}, {},{},{}],
      xb: [{}, {}, {}, {},{},{}]
    };
  },
  created() {
    const _this = this;
    const cnm = ls.getItem('username')
    console.log(cnm);

    axios.get('http://localhost:8181/mail/findSend/' + cnm).then(function (resp) {
      console.log(resp)
      _this.all_mail = resp.data;

      //第一个axios请求对应用户的所有邮寄货件 根据手机号
      //返回的是Site_mail 附带了下一站信息的mail

      let xa, xb = null;
      for (let i = 0; i < _this.all_mail.length; i++) {
        if (_this.all_mail[i].sign === '待签收') {
          axios.get("http://localhost:8181/courier/findOne").then(function (resp) {
            console.log(resp);
            _this.all_mail[i]['show'] = true;
            _this.all_mail[i]['courier'] = resp.data
          })
        } else {
          _this.all_mail[i]['show'] = false;
        }
        if (_this.all_mail[i].end_place == null) {
          // _this.all_mail[i]['show'] = false;
          _this.xb[i]['show']=false;
          console.log(_this.all_mail[i])
          continue;
        } else {

          console.log(_this.all_mail[i])
          axis.get("/api/geocoding/v3/?address=" + _this.all_mail[i].end_place + "&output=json" +
              "&ak=GiWwDBls9BbTRYzOYKFXdOZiPMudUjVF").then(function (resp) {
            // console.log('nmsl')
            xa = resp.data.result.location.lng;
            xb = resp.data.result.location.lat;
            //标记终点位置 ajax异步请求总是出错
            _this.sb[i]['lng'] = xa;
            _this.sb[i]['lat'] = xb;
          })
          // 无需请求初始位置 因为初始位置已经在location中被初始化 而结束位置则没有在location中，而是在end_place属性中
          axios.get('http://localhost:8181/location/findBy/' + _this.all_mail[i].log_id).then(function (resp) {
            //对于每一个货件，都请求所有的位置信息
            // console.log(resp)
            _this.all_mail[i]['all_location'] = resp.data;
            _this.all_mail[i]['all_lnglat'] = new Array(resp.data.length)
            //   let test = new Array()
            for (let j = 0; j < _this.all_mail[i]['all_location'].length; j++) {
              _this.all_mail[i]['all_lnglat'][j] = {
                'lng': resp.data[j].log_lng,
                'lat': resp.data[j].log_lat
              }
            }
            _this.xb[i]['show']=true;
            _this.xb[i]['location'] = _this.all_mail[i].all_location[0].log_location;
            _this.xb[i]['lng'] = _this.all_mail[i].all_lnglat[0].lng;
            _this.xb[i]['lat'] = _this.all_mail[i].all_lnglat[0].lat;
          })
        }
      }
    })
  },
  methods: {
    handleChange(val) {
      console.log(val);
    },
    change(log_id, index) {
      console.log(this.value[index])
      console.log(log_id)
      const _this = this;
      this.$prompt('请描述反馈信息', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
      }).then(({value}) => {
        console.log(value);
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
        let complaint = {
          'messagePK': {
            'phonenum': +ls.getItem('username'),
            'log_id': log_id,
            'type': _this.value[index],
          },
          'content': value,
          'own': 1,
          'time': updateTime
        }
        axios.put('http://localhost:8181/message/add', complaint).then(function (resp) {
          console.log(resp);
          if (resp.data == 'complaint success') {
            _this.$notify({
              type: 'success',
              message: "ID: " + log_id + " 反馈了 " + _this.value[index],
              duration: 3000
            });
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '取消输入'
        });
      });
    }
  }
}
</script>

<style scoped>
.testmap {
  margin-top: -10px;
  margin-left: 120px;
  width: 65%;
  height: 550px;
}

.label_hide {
  display: none;
}

@import "../styles/input-style.css";
</style>