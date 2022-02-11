import Vue from 'vue'
import VueRouter from 'vue-router'
import Info from "../views/Info.vue";
import Logistics from "@/views/Logistics";
import Quk_query from "@/views/Quk_query";
import Index from "@/views/Index"
import Test from "@/views/Test";
import Receive from "@/views/Receive"
import Send from "@/views/Send";
import Admin from "@/views/Admin";
import Ready_Log from "@/views/Admin/Ready_Log";
import Sign_Log from "@/views/Admin/Sign_Log";
import Delivery_Log from "@/views/Admin/Delivery_Log";
import Notice from "@/views/Admin/Notice";
import Router from 'vue-router'
import Message from "@/views/Admin/Message";
import Reply from "@/views/Reply"
import LogIN from "@/views/LogIN";
import Statistic from "@/views/Admin/Statistic";
import Gaoyue from "@/views/Gaoyue";

// const originalPush = Router.prototype.push
// Router.prototype.push = function push(location) {
//     return originalPush.call(this, location).catch(err => err)
// }

Vue.use(VueRouter)

const routes =
    [
        {
            path: '/',
            name: '登录注册',
            show:false,
            component: LogIN,
        },
        {
            path: '/a',
            name: '物流邮寄',
            // redirect: '/Logistics',
            show:true,
            component: Index,
            children:
                [
                    {
                        path: '/Logistics',
                        name: '在线邮寄',
                        component: Logistics
                    },
                ]
        },
        {
            path: '/navigation',
            name: '追踪管理',
            show:true,
            component: Index,
            children: [
                {
                    path: '/Quk_query',
                    name: '快捷查询',
                    component: Quk_query
                },
                {
                    path: '/Send',
                    name: '寄件管理',
                    component: Send
                },
                {
                    path: '/Receive',
                    name: '收件管理',
                    component: Receive
                }
            ]
        },
        {
            path: '/navigation2',
            name: '系统服务',
            show:true,
            component: Index,
            children:
                [
                    {
                        path: '/Info',
                        name: '个人信息',
                        component: Info
                    },
                    {
                        path: '/Reply',
                        name: '系统回复',
                        component: Reply
                    }
                ]
        }
        ,
        {
            path: '/navigation3',
            name: '组件测试',
            component: Index,
            show:false,
            children:
                [
                    {
                        path: '/Com_test',
                        name: '组件测试',
                        component: Test
                    },
                ]
        }
        ,
        {
            path: '/admin',
            name: '管理端',
            component: Admin,
            show:false,
            children:
                [
                    {
                        path: '/Send_admin',
                        name: '寄件管理',
                        component: Ready_Log
                    },
                    {
                        path: '/Rece_admin',
                        name: '运输物件管理端',
                        component: Delivery_Log
                    },
                    {
                        path: '/Sign_admin',
                        name: '签收管理端',
                        component: Sign_Log
                    },
                    {
                        path: '/Notice_admin',
                        name: '用户通知端',
                        component: Notice
                    },
                    {
                        path: '/Message_admin',
                        name: '投诉消息',
                        component: Message
                    },
                    {
                        path:'/Statistic',
                        name:'数据统计',
                        component: Statistic
                    }
                ]
        },
        {
            path:'/num_search',
            name:'单页面记录',
            show: false,
            component: Index,
            children: [
                {
                    path: '/Gaoyue',
                    name:'号码查询结果',
                    component: Gaoyue
                }
            ]
        }
    ]


const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes,
})

export default router
