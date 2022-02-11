import Vue from 'vue'
import './plugins/axios'
import App from './App.vue'
import router from './router'
import store from './store'
import './plugins/element.js'
import BaiduMap from '../node_modules/vue-baidu-map'
import echarts from 'echarts'

// Vue.prototype.$echarts = echarts
Vue.config.productionTip = false

new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app')


Vue.use(BaiduMap, {
    ak: 'bmpAARPZHGVFCosKZRvzlebFSM8cTaP4' //官方提供的ak秘钥
})
Vue.directive('numberOnly', {
    bind: function (el) {
        el.handler = function () {
            el.childNodes[1].value = el.childNodes[1].value.replace(/\D+/g, '');//因为是elementui组件里使用的el.value获取不到我们输入的内容。然后我发现他的第一个孩子才是我们输入的手机号码的内容。
        }
        el.addEventListener('keyup', el.handler); //这里使用keyup。我开始用的input发现第一次输入拼音字母竟然是可以输入的。改成keyup就都不能输入非数字了
    },
    unbind: function (el) {
        el.removeEventListener('keyup', el.handler);
    }
})
// Vue.use(vueJsonp)