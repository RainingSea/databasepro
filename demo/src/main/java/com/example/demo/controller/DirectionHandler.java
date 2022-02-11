package com.example.demo.controller;


import com.example.demo.entity.*;
import com.example.demo.repository.DirectionRepository;
import com.example.demo.repository.LocationRepository;
import com.example.demo.repository.LogisticsRepository;
import com.example.demo.repository.MailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/direction")
@CrossOrigin
public class DirectionHandler {

    @Autowired
    private DirectionRepository directionRepository;
    @Autowired
    private LogisticsRepository logisticsRepository;
    @Autowired
    private LocationHandler locationHandler;
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private MailRepository mailRepository;

    @GetMapping("/findNext/{log_id}")
    public String findNext(@PathVariable("log_id") String log_id){
        Direction direction=directionRepository.findById(log_id).get();
        System.out.println(direction.getState(direction.getSign()+1));
        return direction.getState(direction.getSign()+1);
    }

    //签收一个运输网点
    @GetMapping("/signSite/{log_id}/{next_site}")
    public String signSite(@PathVariable("log_id") String log_id,@PathVariable("next_site") String next_site) throws IOException {
        System.out.println(log_id);
        System.out.println(next_site);
        //记录一个Direction对象
        Direction direction=directionRepository.findById(log_id).get();
        direction.setSign(direction.getSign()+1);
        if(direction.getSign()==4){
            Mail mail=mailRepository.findById(log_id).get();
            mail.setSign("待签收");
            mailRepository.save(mail);
        }
        if(direction.getSign()==5){
            Mail mail=mailRepository.findById(log_id).get();
            mail.setSign("已签收");
            mailRepository.save(mail);
        }

        Direction result=directionRepository.save(direction);

        //记录一个location对象
        Location location=new Location();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        location.setLocationPK(new LocationPK(log_id,df.format(new Date())));
        location.setLog_location(next_site);

        URL url = new URL("https://api.map.baidu.com/geocoding/v3/?address=" +
                next_site + "&output=json&ak=GiWwDBls9BbTRYzOYKFXdOZiPMudUjVF");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setDoOutput(true); // 设置该连接是可以输出的
        connection.setRequestMethod("GET"); // 设置请求方式
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
        String line = null;
        StringBuilder resultS = new StringBuilder();
        while ((line = br.readLine()) != null) { // 读取数据
            resultS.append(line + "\n");
        }
        connection.disconnect();
        System.out.println(resultS.toString());
        String lng="", lat="";
        for (int i = 0; i < resultS.toString().length(); i++) {
            if (resultS.toString().charAt(i) == 'g') {
                lng = resultS.toString().substring(i + 3, i + 11);
                for (int j = i; j < resultS.toString().length(); j++) {
                    if (resultS.toString().charAt(j) == 't') {
                        lat = resultS.toString().substring(j + 3, j + 11);
                        break;
                    }
                }
                break;
            }
        }
        location.setLog_lng(Double.parseDouble(lng));
        location.setLog_lat(Double.parseDouble(lat));
        Location resultL=locationRepository.save(location);

        if(result!=null&&resultL!=null)
            return "sign site success";
        else
            return "sign site error";
    }

    //mail点击后初始化一个新建一个Direction
    @GetMapping("/confirm/{log_id}")
    public String confirm(@PathVariable("log_id") String log_id) throws IOException {

        Logistics logistics=logisticsRepository.findById(log_id).get();
        Direction direction=new Direction();
        //Direction根据id的logistics里得到发货地和收货地 并生成路线
        direction.create(logistics);

        Direction result=directionRepository.save(direction);//初始化Direction
        String result1=locationHandler.ini(result);//初始化location

        if(result!=null&&result1.equals("location ini success")){
            return "direction and location confirm success";
        }
        else {
            return "direction and location confirm error";
        }
    }
}
