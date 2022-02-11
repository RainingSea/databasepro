package com.example.demo.controller;

import com.example.demo.entity.Direction;
import com.example.demo.entity.Location;
import com.example.demo.entity.LocationPK;
import com.example.demo.entity.Mail;
import com.example.demo.repository.DirectionRepository;
import com.example.demo.repository.LocationRepository;
import com.example.demo.repository.MailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/location")
@CrossOrigin
public class LocationHandler {
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private DirectionRepository directionRepository;

    @GetMapping("/findBy/{log_id}")
    public List<Location> findById(@PathVariable("log_id") String log_id) throws IOException {
        List<Location> result = locationRepository.findLocationByLocationPK_LogidContaining(log_id);
        if (result == null) {
            return null;
        }
        if (directionRepository.findById(log_id).get().getSign() == 5) {
        } else {
            //理论上这里应该从GPS里获得经纬度，但现在只能这样
            Location live = new Location();
            live.setLocationPK(result.get(0).getLocationPK());
            double lng = 114.30611111;
            double lat = 30.59416667;
            live.setLog_lng(lng);
            live.setLog_lat(lat);

            URL url = new URL("https://api.map.baidu.com/reverse_geocoding/v3/?ak=GiWwDBls9BbTRYzOYKFXdOZiPMudUjVF" +
                    "&output=json&coordtype=wgs84ll&location=" + lat + "," + lng);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setDoOutput(true); // 设置该连接是可以输出的
            connection.setRequestMethod("GET"); // 设置请求方式
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
            String line = null;
            StringBuilder resultsb = new StringBuilder();
            while ((line = br.readLine()) != null) { // 读取数据
                resultsb.append(line + "\n");
            }
            connection.disconnect();
            System.out.println(resultsb.toString());
            int k = 0;
            while (resultsb.toString().charAt(k) != '_') {
                k++;
            }
            k = k + 11;
            int k1 = k;
            while (resultsb.toString().charAt(k) != '"') {
                k++;
            }
//        System.out.println(resultsb.toString().substring(k1,k));
            live.setLog_location(resultsb.toString().substring(k1, k));
            result.add(live);
        }
        return result;
    }


    @GetMapping("/findAll")
    public List<Location> findAll() {
        return locationRepository.findAll();
    }

    //    @GetMapping("/ini/{log_id}")
    public String ini(Direction direction) throws IOException {

//        Direction direction = directionRepository.findById(log_id).get();
        System.out.println(direction);
        Location location1 = new Location();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        location1.setLocationPK(new LocationPK(direction.getLog_id(), df.format(new Date())));
        location1.setLog_location(direction.getSend_place());

        URL url = new URL("https://api.map.baidu.com/geocoding/v3/?address=" +
                direction.getSend_place() + "&output=json&ak=GiWwDBls9BbTRYzOYKFXdOZiPMudUjVF");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setDoOutput(true); // 设置该连接是可以输出的
        connection.setRequestMethod("GET"); // 设置请求方式
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
        String line = null;
        StringBuilder result = new StringBuilder();
        while ((line = br.readLine()) != null) { // 读取数据
            result.append(line + "\n");
        }
        connection.disconnect();
        System.out.println(result.toString());
        String lng = "", lat = "";
        for (int i = 0; i < result.toString().length(); i++) {
            if (result.toString().charAt(i) == 'g') {
                lng = result.toString().substring(i + 3, i + 11);
                for (int j = i; j < result.toString().length(); j++) {
                    if (result.toString().charAt(j) == 't') {
                        lat = result.toString().substring(j + 3, j + 11);
                        break;
                    }
                }
                break;
            }
        }
//        System.out.println(lng);
//        System.out.println(lat);
        location1.setLog_lng(Double.parseDouble(lng));
        location1.setLog_lat(Double.parseDouble(lat));
        System.out.println(location1);
        Location resultL = locationRepository.save(location1);
        if (resultL != null)
            return "location ini success";
        else
            return "location ini error";
    }
}
