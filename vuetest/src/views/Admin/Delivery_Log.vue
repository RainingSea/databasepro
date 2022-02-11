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
        <label style="font-size: 15px;margin-left: 50px;">
          {{ mail.sign }}</label>
        <el-select v-model="value[index]" placeholder="货站网点签收"
                   style="margin-left: 450px" @change="change(mail.log_id,mail.next_site,index,mail.sign)">
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
        <baidu-map class="testmap" center="安阳" zoom="9" :scroll-wheel-zoom="true">
          <bm-marker v-for="(lng_lat,lng_index) in all_mail[index].all_location"
                     :position="{lng:lng_lat.log_lng,lat:lng_lat.log_lat}">
            <bm-label :content=des_prefix+lng_lat.log_location+des_next_site+all_mail[index].next_site
                      :labelStyle="lng_index==(all_mail[index].all_location.length-1)?{color: 'red', fontSize : '12px'}:{display:'none'}"
                      :offset="{width: -35, height: 30}"/>
          </bm-marker>

                    标记终点站的
          <bm-marker :position={lng:sb[index].lng,lat:sb[index].lat}>
            <bm-label :content=sb[index].location
                      :labelStyle="{color: 'blue', fontSize : '12px',fontWeight: bold}"
                      :offset="{width: -35, height: -30}"/>
          </bm-marker>

          <!--          画出路线轨迹-->
          <bm-polyline :path="all_mail[index].all_lnglat" stroke-color="blue"
                       :stroke-opacity="0.5" :stroke-weight="4"></bm-polyline>
          <!--          点和线的生成也是依赖于location的每一项-->
        </baidu-map>

      </div>
    </el-collapse-item>
    <!--    有几个对象返回，就生成几个el-collapse-item-->
  </el-collapse>
</template>

<script>
import axis from "axios";

export default {
  inject: ['reload'],
  data() {
    return {
      activeNames: [''],

      all_mail: null,
      des_prefix: '当前到达: ',
      des_next_site: '<br>下一站发往: ',
      options: [{
        value: '货站中途确认',
        label: '货站中途确认'
      }, {
        value: '签收',
        label: '签收'
      }],
      value: ["", "", "", "", ""],
      sign_site: "",
      sb: [{}, {}, {}, {},{},{}],
      xb: [{}, {}, {}, {},{},{}],
      isFinished: []
    };
  },

  created() {
    const _this = this;
    axios.get('http://localhost:8181/mail/findBySign1/' + '运输中').then(function (resp) {
      console.log(resp)
      _this.all_mail = resp.data;
      console.log(_this.all_mail);
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
            console.log('nmsl')
            let xa = resp.data.result.location.lng;
            let xb = resp.data.result.location.lat;
            //标记终点位置 ajax异步请求总是出错
            _this.sb[i]['lng'] = xa;
            _this.sb[i]['lat'] = xb;
            _this.sb[i]['location']=_this.all_mail[i].end_place
          })
        }
        axios.get('http://localhost:8181/direction/findNext/' + _this.all_mail[i].log_id).then(function (resp) {
          // console.log(resp)
          _this.all_mail[i]['next_site'] = resp.data;
          // console.log(_this.all_mail[i])
        })
      }

      for (let i = 0; i < _this.all_mail.length; i++) {
        axios.get('http://localhost:8181/location/findBy/' + _this.all_mail[i].log_id).then(function (resp) {
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
      //for循环
    })
  },
  methods: {
    handleChange(val) {
      console.log(val);
    },
    change(log_id, next_site, index, sign) {
      if (sign === '待签收' && this.value[index] !== '签收') {
        this.$alert("请点击签收!")
        console.log('sbsbsb');
        return
      }
      const _this = this;
      this.$confirm('确认到达当前位置网点', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        console.log(next_site);
        axios.get('http://localhost:8181/direction/signSite/' + log_id + "/" + next_site).then(function (resp) {
          console.log(resp)
        })
        _this.$alert(log_id + '签收成功', '系统提示', {
          confirmButtonText: '确定',
          callback: action => {
            _this.reload()
          }
        });
        // _this.reload();
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消签收'
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

@import "../../styles/input-style.css";
</style>