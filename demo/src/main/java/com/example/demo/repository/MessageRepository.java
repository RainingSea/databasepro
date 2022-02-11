package com.example.demo.repository;

import com.example.demo.entity.Message;
import com.example.demo.entity.MessagePK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, MessagePK> {
    List<Message> findMessageByMessagePK_TypeIsContaining(String str);
    List<Message> findMessageByMessagePK_PhonenumAndOwn(String phone_num,int own);
    List<Message> findMessageByOwn(int own);
}
