package com.example.demo.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Person {
    @Id
        private String phone_num;
        private String name;
        private String sex;
        private String province;
        private String city;
        private String district;
        private String detail;
        private String password;
}
