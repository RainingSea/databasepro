package com.example.demo.entity;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

public class Capital {

    public String getCapital(String pro){
        switch (pro){
            case "上海市":return "上海市";
            case "北京市":return "北京市";
            case "重庆市":return "重庆市";
            case "天津市":return "天津市";
            case "云南省":return "昆明市";
            case "吉林省":return "长春市";
            case "四川省":return "成都市";
            case "安徽省":return "合肥市";
            case "山东省":return "济南市";
            case "山西省":return "太原市";
            case "广东省":return "广州市";
            case "江苏省":return "南京市";
            case "江西省":return "南昌市";
            case "河北省":return "石家庄市";
            case "河南省":return "郑州市";
            case "浙江省":return "杭州市";
            case "海南省":return "海口市";
            case "湖北省":return "武汉市";
            case "湖南省":return "长沙市";
            case "甘肃省":return "兰州市";
            case "福建省":return "福州市";
            case "贵州省":return "贵阳市";
            case "辽宁省":return "沈阳市";
            case "陕西省":return "西安市";
            case "青海省":return "西宁市";
            case "黑龙江省":return "哈尔滨市";
            case "内蒙古自治区":return "呼和浩特市";
            case "广西壮族自治区":return "南宁市";
            case "宁夏回族自治区":return "银川市";
            case "新疆维吾尔自治区":return "乌鲁木齐市";
            case "西藏自治区":return "拉萨市";
            default: return null;
        }
    }
}