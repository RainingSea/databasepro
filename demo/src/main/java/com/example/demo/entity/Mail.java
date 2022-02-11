package com.example.demo.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Mail {

    @Id
    private String log_id;

    @Column(name="sender")
    private String sender;
    @Column(name="sender_name")
    private String sender_name;
    @Column(name="receiver")
    private String receiver;
    @Column(name="receiver_name")
    private String receiver_name;
    @Column(name="send_city")
    private String send_city;
    @Column(name="rece_city")
    private String rece_city;
    @Column(name="sign")
    private String sign;
}
