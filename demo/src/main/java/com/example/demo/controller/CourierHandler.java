package com.example.demo.controller;


import com.example.demo.entity.Courier;
import com.example.demo.repository.CourierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/courier")
@CrossOrigin
public class CourierHandler {
    @Autowired
    private CourierRepository courierRepository;

    @GetMapping("findOne")
    public Courier findOne(){
        return courierRepository.findAll().get(0);
    }
}
