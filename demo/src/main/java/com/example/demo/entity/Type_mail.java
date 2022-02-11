package com.example.demo.entity;

import lombok.Data;

import javax.persistence.Column;

@Data
public class Type_mail {
    private String log_id;

    private String sender;
    private String sender_name;
    private String receiver;
    private String receiver_name;
    private String send_city;
    private String rece_city;
    private String sign;
    private String deliver_time;
    private String deliver_way;

    public void setDeliver_time(String deliver_time) {
        this.deliver_time = deliver_time;
    }

    public void setDeliver_way(String deliver_way) {
        this.deliver_way = deliver_way;
    }

    public void copy(Mail mail){
        this.log_id=mail.getLog_id();
        this.sender=mail.getSender();
        this.sender_name=mail.getSender_name();
        this.send_city=mail.getSend_city();
        this.receiver=mail.getReceiver();
        this.receiver_name=mail.getReceiver_name();
        this.rece_city=mail.getRece_city();
        this.sign=mail.getSign();
    }
}
