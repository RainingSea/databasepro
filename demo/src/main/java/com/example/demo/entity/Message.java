package com.example.demo.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Data
@Entity
public class Message {

    @EmbeddedId
    private MessagePK messagePK;

    @Column(name="content")
    private String content;

    @Column(name="own")
    private int own;

    @Column(name="time")
    private String time;
}
