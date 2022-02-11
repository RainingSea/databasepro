package com.example.demo.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NoticeRepositoryTest {

    @Autowired
    private  NoticeRepository noticeRepository;

    @Test
    void findAll(){
        System.out.println(noticeRepository.findAll());
    }

    @Test
    void findNew(){
        System.out.println(noticeRepository.findNoticeByDirty("1"));
    }

}