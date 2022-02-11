package com.example.demo.repository;

import com.eclipsesource.json.Json;
import com.example.demo.entity.Direction;
import com.example.demo.entity.Location;
import com.example.demo.entity.LocationPK;
import net.minidev.json.JSONAware;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

@SpringBootTest
class LocationRepositoryTest {

    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private DirectionRepository directionRepository;

    @Test
    void findAll() {
        System.out.println(locationRepository.findAll());
    }

    @Test
    void findByID() {
        System.out.println(locationRepository.findLocationByLocationPK_LogidContaining("XA6122"));
    }
    @Test
    void initialize() throws IOException {
        Direction direction = directionRepository.findById("XA6146").get();
        Location location1 = new Location();
        location1.setLocationPK(new LocationPK(direction.getLog_id(), "2021-08-31 12:00:00"));
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
        String lng="", lat="";
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
//        location1.setLog_lng(Double.parseDouble(lng));
//        location1.setLog_lat(Double.parseDouble(lat));
//        System.out.println(location1);
//        Location resultL=locationRepository.save(location1);
//        if(result!=null)
//            return "location ini success";
//        else
//            return "location ini error";
    }

    @Test
    void last() throws IOException{
        URL url = new URL("https://api.map.baidu.com/reverse_geocoding/v3/?ak=GiWwDBls9BbTRYzOYKFXdOZiPMudUjVF" +
                "&output=json&coordtype=wgs84ll&location=22.639656,114.416298");
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
        int k=0;
        while(result.toString().charAt(k)!='_'){
            k++;
        }
        k=k+11;
        int k1=k;
        while(result.toString().charAt(k)!='"'){
            k++;
        }
        System.out.println(result.toString().substring(k1,k));
    }

    @Test
    public void delete(){
        List<Location> locationList=locationRepository.findLocationByLocationPK_LogidContaining("XA6146");
        for(Location location:locationList){
            locationRepository.delete(location);
        }
    }
}