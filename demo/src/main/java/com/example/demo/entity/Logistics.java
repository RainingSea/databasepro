package com.example.demo.entity;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Logistics {
    @Id
    private String log_id;

    private String log_name;
    private double weight;
    private double volume;
    private String deliver_time;
    private String deliver_way;
    private String other;

    private String send_province;
    private String send_city;
    private String send_area;
    private String send_detail;
    private String rece_province;
    private String rece_city;
    private String rece_area;
    private String rece_detail;

}
