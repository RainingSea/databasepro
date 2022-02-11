package com.example.demo.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Count_mail {
    @Id
    private String log_id;
    private String deliver_time;
}

