package com.example.demo.entity;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Courier {

    @Id
    private String courier_num;

    private String courier_name;
}
