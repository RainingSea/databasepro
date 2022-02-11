package com.example.demo.repository;

import com.example.demo.entity.Direction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DirectionRepositoryTest {

    @Autowired
    private DirectionRepository directionRepository;

    @Test
    void findAll(){
        System.out.println(directionRepository.findAll());
    }

    @Test
    void findNext(){
        Direction direction=directionRepository.findById("XA6122").get();
        System.out.println(direction.getState(direction.getSign()+1));
    }

    @Test
    void signSite(){
        Direction direction=directionRepository.findById("XA6122").get();
        direction.setSign(direction.getSign()+1);
        Direction result=directionRepository.save(direction);
        System.out.println(result);
    }
}