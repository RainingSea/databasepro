package com.example.demo.controller;

import com.example.demo.entity.Direction;
import com.example.demo.entity.Location;
import com.example.demo.entity.Logistics;
import com.example.demo.entity.Mail;
import com.example.demo.repository.DirectionRepository;
import com.example.demo.repository.LocationRepository;
import com.example.demo.repository.LogisticsRepository;
import com.example.demo.repository.MailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/logistics")
@CrossOrigin
public class LogisticsHandler {

    @Autowired
    private LogisticsRepository logisticsRepository;
    @Autowired
    private MailRepository mailRepository;
    @Autowired
    private DirectionRepository directionRepository;
    @Autowired
    private LocationRepository locationRepository;

    @PostMapping("/save")
    public String save(@RequestBody Logistics logistics) {
        Logistics save_logistics=logistics;
        List<Logistics> resultF = logisticsRepository.findAll();
        String ID = resultF.get(resultF.size() - 1).getLog_id();
        int tempID = Integer.parseInt(ID.substring(2, 6));
        tempID++;
        String newID = "XA" + tempID;
//        System.out.println(newID);
        save_logistics.setLog_id(newID);
//        System.out.println(mail);
        Logistics result=logisticsRepository.save(save_logistics);
        if(result!=null){
            return "logistics save success";
        }
        else {
            return "logistics save error";
        }
    }

    @GetMapping("/delete/{log_id}")
    public String delete(@PathVariable String log_id){
        logisticsRepository.deleteById(log_id);
        mailRepository.deleteById(log_id);
        return "delete success";
    }
    @GetMapping("/deleteAll/{log_id}")
    public String deleteAll(@PathVariable String log_id){
        logisticsRepository.deleteById(log_id);
        mailRepository.deleteById(log_id);
        directionRepository.deleteById(log_id);
        List<Location> locationList=locationRepository.findLocationByLocationPK_LogidContaining(log_id);
        for(Location location:locationList){
            locationRepository.delete(location);
        }
        return "delete success";
    }
}
