package com.example.demo.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.Access;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MessageRepositoryTest {

    @Autowired
    private MessageRepository messageRepository;

    @Test
    void findall(){
//        System.out.println(messageRepository.findMessageByMessagePK_TypeIsContaining("回复"));
        System.out.println(messageRepository.findMessageByMessagePK_PhonenumAndOwn("15564107739",0));
    }
}