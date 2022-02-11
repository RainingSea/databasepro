package com.example.demo.repository;

import com.example.demo.entity.Courier;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CourierRepositoryTest {

    @Autowired
    private CourierRepository courierRepository;

    @Test
    void find(){
        List<Courier> result=courierRepository.findAll();
        System.out.println(result.get(0));
    }
}