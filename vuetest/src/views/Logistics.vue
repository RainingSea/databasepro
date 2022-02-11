<template>
  <div style="display: flex;height: 1100px;margin-left: 5px" class="logistics_window">
    <div style="margin-top:25px;width:40px">
      <el-steps direction="vertical" :active="3" style="height: 1000px">
        <el-step icon="el-icon-edit"></el-step>
        <el-step icon="el-icon-edit" style="margin-top: -125px"></el-step>
        <el-step icon="el-icon-edit" style="margin-top: -25px"></el-step>
      </el-steps>
    </div>

    <div style="display: flex;flex-direction: column;">

      <!--    寄件人和收件人信息填写-->
      <div>
        <h2 style="color: rgb(64,158,255)">邮寄信息</h2>
        <div style="display: flex;flex-wrap: wrap;">
          <div style="margin-top: -20px">
            <el-form style="width: 520px;margin-top: 30px;margin-left: 0px"
                     :model="sender" :rules="rules"
                     ref="sender"
                     label-width="100px" class="demo-ruleForm">
              <el-form-item style="width: 300px" label="寄件人姓名" prop="name">
                <el-input style="height: 10px;" v-model="sender.name"></el-input>
              </el-form-item>
              <el-form-item label="寄件人地址">
                <div>
                  <v-distpicker style="width: 600px" :placeholders="placeholders"
                                @selected="onSelected_S"></v-distpicker>
                </div>
              </el-form-item>
              <el-form-item label="街道信息" prop="detail">
                <el-input type="textarea" v-model="sender.detail"></el-input>
              </el-form-item>
              <el-form-item style="width: 300px" label="联系电话" prop="phone">
                <el-input v-model="sender.phone"></el-input>
              </el-form-item>
            </el-form>
          </div>

          <div style="margin-top: -20px;">
            <el-form style="width: 500px;margin-top: 30px;margin-left: 50px" :model="receiver" :rules="rules"
                     ref="receiver"
                     label-width="100px" class="demo-ruleForm">
              <el-form-item style="width: 300px" label="收件人姓名" prop="name">
                <el-input style="height: 10px" v-model="receiver.name"></el-input>
              </el-form-item>
              <el-form-item label="收件人地址" prop="address">
                <div>
                  <v-distpicker :placeholders="placeholders" style="width: 510px"
                                @selected="onSelected_R"></v-distpicker>
                </div>
              </el-form-item>
              <el-form-item label="街道信息" prop="detail">
                <el-input type="textarea" v-model="receiver.detail" placeholder="详细街道"></el-input>
              </el-form-item>
              <el-form-item style="width: 300px" label="联系电话" prop="phone">
                <el-input v-model="receiver.phone"></el-input>
              </el-form-item>
            </el-form>
          </div>
        </div>
      </div>

      <!--    物件信息的填写-->
      <div style="margin-top: 30px">
        <h2 style="color: rgb(64,158,255)">寄托物信息</h2>
        <div style="margin-top: 20px">
          <el-form style="width: 520px;margin-top: 30px;margin-left: 40px;display: flex;flex-wrap: wrap"
                   :model="parcel"
                   :rules="rules"
                   ref="parcel"
                   label-position="top"
                   label-width="100px" class="demo-ruleForm">
            <el-form-item label="货物名称" prop="p_name">
              <el-input style="height: 10px;width: 400px" v-model="parcel.p_name"></el-input>
            </el-form-item>
            <div style="display: flex">
              <el-form-item label="货物重量" prop="p_weight" >
                <el-input style="height: 10px;width: 200px" v-model="parcel.p_weight" @input="up_price"></el-input>
              </el-form-item>
              <el-form-item style="margin-left: 80px" label="货物体积" prop="p_volume">
                <el-input style="height: 10px;width: 200px" v-model="parcel.p_volume" @input="up_price"></el-input>
              </el-form-item>
              <el-form-item style="margin-left: 80px" label="上门方式" prop="p_deliver_way">
                <el-select style="width: 200px" v-model="parcel.p_deliver_way" placeholder="请选择">
                  <el-option
                      v-for="item in options"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value">
                  </el-option>
                </el-select>
              </el-form-item>
            </div>
            <el-form-item label="描述信息" prop="p_description">
              <el-input type="textarea" style="width: 300px" v-model="parcel.p_description"></el-input>
            </el-form-item>
          </el-form>
        </div>
      </div>

      <!--        付款的界面-->
      <div style="margin-top: 40px;">
        <h2 style="color: rgb(64,158,255)">付款界面</h2>
        <div class="block" style="margin-left: 40px">
          <span class="demonstration">选择上门时间</span>
          <el-date-picker
              style="margin-left: 20px"
              v-model="parcel.p_pick_day"
              type="date"
              placeholder="选择日期"
              :picker-options="pickerOptions1"
              format="yyyy 年 MM 月 dd 日"
              value-format="yyyy-MM-dd">
          </el-date-picker>
          <el-time-select
              style="margin-left: 20px"
              v-model="parcel.p_pick_time"
              :picker-options="{
              start: '08:00',
              step: '01:00',
              end: '18:00'
              }"
              placeholder="选择时间">
          </el-time-select>
        </div>
        <div style="display: flex;flex-direction: column;margin-left: 40px">
          <div>
            <span style="margin-left: 2px">详细信息</span>
            <el-input type="textarea" style="width: 300px;margin-left: 50px;margin-top: 20px" v-model="parcel.p_demand"></el-input>
           <label style="font-size: 30px;margin-left: 200px;color: green">{{price+" 元"}}</label>
          </div>

          <el-button type="success" @click="open" style="margin-top:20px;width: 200px">完成</el-button>
        </div>
      </div>

    </div>
  </div>
</template>

<script>

import VDistpicker from 'v-distpicker'


export default {
  mounted() {
    window.Vue = this;
  },
  components: {
    VDistpicker
  },
  created() {
    //页面创建时从后台读取有没有通知信息
    const _this = this;
    axios.get('http://localhost:8181/notice/findNew').then(function (resp) {
      console.log(resp)
      _this.$alert("<div v-html='data' style='white-space: pre-line;'>" + resp.data[0].content + "</div>"
          , '系统提醒', {
            confirmButtonText: '确定',
            dangerouslyUseHTMLString: true,
            callback: action => {
              this.$message({
                type: 'info',
                message: `action: ${action}`
              });
            }
          });
    })
  },
  data() {
    return {
      test: '河南省安阳市',
      pickerOptions1: {
        disabledDate(time) {
          return time.getTime() < Date.now() - 8.64e7;
        },
      },
      price:0,
      placeholders: {
        province: '---省---',
        city: '---市---',
        area: '---区/县---',
      },
      sender: {
        name: '',
        phone: '',
        district: {
          province: null,
          city: null,
          area: null,
          detail: '',
        },
      },
      receiver: {
        name: '',
        phone: '',
        district: {
          province: null,
          city: null,
          area: null,
          detail: '',
        }
      },
      parcel: {
        p_name: '',
        p_weight: '',
        p_volume: '',
        p_deliver_way: '',
        p_description: '',
        p_pick_day: '',
        p_pick_time: '',
        p_demand: ''
      },
      rules: {
        name: [
          {required: true, message: '请输入寄件人姓名', trigger: 'blur'},
          {min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur'}
        ],
        phone: [
          {required: true, message: '请输入联系方式', trigger: 'blur',},
          {min: 11, max: 11, message: '请输入正确的号码', trigger: 'blur'}
        ],
        detail: [
          {required: true, message: '请输入详细地址', trigger: 'blur'}
        ]
      },
      options: [{
        value: '送货上门',
        label: '送货上门'
      }, {
        value: '网点自取',
        label: '网点自取'
      }],
    };
  },

  methods: {
    up_price(){
      this.price=10;
      if(this.sender.district.province!=this.receiver.district.province){
        this.price=20;
      }
      //省外的起步价为20
      this.price+=Math.round(this.parcel.p_weight/5.0);
      //每5千克加一块钱
      this.price+=this.parcel.p_volume*10;
      //每1立方米加5元
    },

    // 两个地区选择器的代码 分别是寄件人和收件人
    onSelected_S(data) {
      this.sender.district.province = data.province.value;
      this.sender.district.city = data.city.value;
      this.sender.district.area = data.area.value;
      console.log("S" + this.sender.district);
    },
    onSelected_R(data) {
      this.receiver.district.province = data.province.value;
      this.receiver.district.city = data.city.value;
      this.receiver.district.area = data.area.value;
      console.log("R" + this.receiver.district);
    },
    submitForm(formName) {
      const _this = this;
      const p1 = new Promise((resolve, reject) => {
        this.$refs['sender'].validate(valid => {
          if (valid) {
            resolve()
          } else {
            alert('寄件信息有误')
          }
        })
      })
      // 检查寄件信息是否有误
      const p2 = new Promise((resolve, reject) => {
        this.$refs['receiver'].validate(valid => {
          if (valid) {
            resolve()
          } else {
            alert('收件信息有误')
          }
        })
      })
      // 检查收件信息是否有误
      Promise.all([p1, p2]).then(() => {
        let test_log = {
          log_id: "",
          log_name: _this.parcel.p_name,
          weight: _this.parcel.p_weight,
          volume: _this.parcel.p_volume,
          deliver_time: _this.parcel.p_pick_day + " " + _this.parcel.p_pick_time,
          deliver_way: _this.parcel.p_deliver_way,
          other: _this.parcel.p_demand,
          send_province: _this.sender.district.province,
          send_city: _this.sender.district.city,
          send_area: _this.sender.district.area,
          send_detail: _this.sender.detail,
          rece_province: _this.receiver.district.province,
          rece_city: _this.receiver.district.city,
          rece_area: _this.receiver.district.area,
          rece_detail: _this.receiver.detail,
        }
        // 前端创建好一个Logistics对象
        let test_mail = {
          log_id: "",
          sender: _this.sender.phone,
          sender_name: _this.sender.name,
          receiver: _this.receiver.phone,
          receiver_name: _this.receiver.name,
          send_city: _this.sender.district.city,
          rece_city: _this.receiver.district.city,
          sign: 0
        }
        // 创建好一个mail对象

        axios.post('http://localhost:8181/logistics/save', test_log).then(function (resp) {
          console.log(resp);
          // 要有先后顺序 必须保证Logistics先存储到数据库实体中
          // console.log(test_mail);
          axios.post('http://localhost:8181/mail/save', test_mail).then(function (resp) {
            console.log(resp);
          })
        })
      })
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    open() {
      const _this=this;
      this.$confirm('请确认信息填写完毕?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {

        this.$alert(this.price+'元', '请支付', {
          confirmButtonText: '支付成功',
          cancelButtonText: '太贵了',
          type: 'warning'
        })
        this.submitForm('Masijacoke850714')
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消'
        });
      });
    }
    //open
  }
  //methods结束

}
</script>
