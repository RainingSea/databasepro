package com.example.demo.repository;

import com.example.demo.entity.Mail;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MailRepository extends JpaRepository<Mail, String> {
    List<Mail> findMailByReceiver(String receiver);
    List<Mail> findMailBySender(String sender);
    List<Mail> findMailBySignIs(String sign);

}
