package com.example.demo.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LogisticsRepositoryTest {

    @Autowired
    private LogisticsRepository logisticsRepository;

    @Test
    public void findAll(){
        System.out.println(logisticsRepository.findAll());
    }

    @Test
    public void delete(){
        logisticsRepository.deleteById("XA6155");
    }

}