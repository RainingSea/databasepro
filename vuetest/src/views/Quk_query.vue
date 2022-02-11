<template>
  <div style="display: flex;flex-direction: column">
    <h2>快捷查询</h2>
    <label-wrap style="font-size: 10px">输入信件号即可快捷查询</label-wrap>
    <el-tabs v-model="activeName" @tab-click="handleClick">
      <el-tab-pane label="订单分号" name="first">
        <el-input style="width: 30%;margin-left: 10px" v-model="log_id"></el-input>
        <el-button type="primary" style="margin-left: 20px;margin-top: 20px" @click="searchByID">点击查询</el-button>
        <div v-if="!search_bit" style="display: flex">
          <div style="width: 400px">
            <el-collapse @change="handleChange"
                         style="font-size: 20px;margin-top: 30px;margin-left:20px;width: 400px">
              <el-collapse-item name="1" class="card">
                <template slot="title">
                  <label style="font-size: 14px;">{{ mail.log_id }}</label>
                  <div style="display: flex;flex-direction: column;margin-left: 100px">
                    <label style="font-size: 14px;margin-left:-20px;">
                      {{ mail.send_city + '  --->  ' + mail.rece_city }}</label>
                    <label style="font-size: 13px;margin-left:-20px;margin-top: -10px;color: grey">
                      {{ mail.sender_name + '  --->  ' + mail.receiver_name }}</label>
                  </div>
                  <label style="font-size: 15px;margin-left: 40px;">{{ mail.sign }}</label>
                </template>
                <div>
                  <el-timeline :reverse="reverse" style="width: 200px">
                    <el-timeline-item
                        v-for="(one_location, timeline_index) in all_location"
                        :key="timeline_index"
                        :timestamp="one_location.locationPK.logtime">
                      {{ one_location.log_location }}
                    </el-timeline-item>
                  </el-timeline>
                </div>
              </el-collapse-item>
            </el-collapse>
          </div>
          <baidu-map class="testmap" center="安阳" zoom="9" :scroll-wheel-zoom="true">
            <bm-marker v-for="(lng_lat,lng_index) in all_location"
                       :position="{lng:lng_lat.log_lng,lat:lng_lat.log_lat}">
              <bm-label :content=des_prefix+lng_lat.log_location+des_next_site+mail.next_site
                        :labelStyle="lng_index==(all_location.length-1)?{color: 'red', fontSize : '12px'}:{display:'none'}"
                        :offset="{width: -35, height: -30}"/>
            </bm-marker>

            <bm-marker :position={lng:xb.lng,lat:xb.lat}
                       v-if=true>
              <bm-label :content="xb.location"
                        :labelStyle="{color: 'green', fontSize : '12px',fontWeight: bold}"
                        :offset="{width: -35, height: 30}"/>
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
      </el-tab-pane>

      <!--      使用手机号进行查询的面板-->
      <el-tab-pane label="手机号" name="second">
        <el-input style="width: 50%;margin-left: 10px" v-model="log_id"></el-input>
        <el-button type="primary" style="margin-left: 20px;margin-top: 20px" @click="searchByNum">点击查询</el-button>
        <el-table
            :data="tableData"
            style="width: 100%"
            stripe
            @row-click="go">
          <el-table-column
              prop="log_id"
              label="物件ID"
              width="80">
          </el-table-column>
          <el-table-column
              prop="sign"
              label="状态"
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
        </el-table>
      </el-tab-pane>
    </el-tabs>

    <!--    显示物流件信息的面板-->

  </div>
</template>

<script>

import axis from "axios";

export default {
  data() {
    return {
      activeName: 'first',
      search_bit: true,
      log_id: '',
      mail: null,
      all_location: null,
      all_lnglat: null,
      sb: {},
      xb:{},
      //根据件id查询用的的信息
      tableData: [],
      des_prefix:'当前到达: ',
      des_next_site:'<br/>下一站发往: '
    };
  },
  methods: {
    handleClick(tab, event) {
      console.log(tab, event);
    },
    searchByID(log_id) {
      const _this = this;
      axios.get('http://localhost:8181/mail/findById/' + this.log_id).then(function (resp) {
        if (resp.data.log_id == '000000') {
          _this.search_bit = true
        }
        // 如果没有数据返回一个id为000000的默认数据 不要显示
        console.log(resp)
        _this.mail = resp.data;
        console.log(_this.mail.end_place);
        if (_this.mail.end_place == null) {
        } else {
          axis.get("/api/geocoding/v3/?address=" + _this.mail.end_place + "&output=json" +
              "&ak=GiWwDBls9BbTRYzOYKFXdOZiPMudUjVF").then(function (resp) {
            console.log('nmsl')
            //标记终点位置 ajax异步请求总是出错
            _this.sb['lng'] = resp.data.result.location.lng;
            _this.sb['lat'] = resp.data.result.location.lat;
          })
        }
        axios.get('http://localhost:8181/location/findBy/' + _this.log_id).then(function (resp) {
          //对于每一个货件，都请求所有的位置信息
          // console.log(resp)
          _this.all_location = resp.data;
          _this.all_lnglat = new Array(resp.data.length)
          //   let test = new Array()
          for (let j = 0; j < _this.all_location.length; j++) {
            _this.all_lnglat[j] = {
              'lng': resp.data[j].log_lng,
              'lat': resp.data[j].log_lat
            }
          }
          _this.xb['show']=true;
          _this.xb['lng']=resp.data[0].log_lng;
          _this.xb['lat']=resp.data[0].log_lat;
          _this.xb['location']=_this.all_location[0].log_location;
          _this.search_bit = false;
        })
      })
    },
    searchByNum() {
      const _this = this;
      axios.get('http://localhost:8181/mail/findSend/' + '15564107739').then(function (resp) {
        // console.log(resp)
        _this.tableData = resp.data
      })
    },
    go(row, event, column) {
      console.log('row')
      console.log(row)
      let routeData=this.$router.resolve({
        //要跳转的页面的名称
        path: '/Gaoyue',
        // name: "数据统计",
        //传参
        query:row
      })
      window.open(routeData.href, '_blank');
      //以新窗口打开
      // window.open('http://localhost:8080/Logistics', '_blank')
    }
  }
};
</script>

<style scoped>
.testmap {
  margin-left: 60px;
  margin-top: 30px;
  width: 60%;
  height: 550px;
}
</style>