package com.example.demo.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Direction {

    @Id
    private String log_id;

    private String send_place;
    private String send_city;
    @Column(name = "Scapital")
    private String s_capital;

    @Column(name = "Rcapital")
    private String r_capital;
    private String rece_city;
    private String rece_place;
    private int sign;


    public String getState(int sign) {
        switch (sign) {
            case 0: {
                return this.send_place;
            }
            case 1: {
                return this.send_city;
            }
            case 2: {
                return this.s_capital;
            }
            case 3: {
                return this.r_capital;
            }
            case 4: {
                return this.rece_city;
            }
            case 5: {
                return this.rece_place;
            }
            default:{
                return "已签收";
            }
        }
    }

    public void create(Logistics logistics){
        Capital capital=new Capital();
        this.setLog_id(logistics.getLog_id());
        this.setSend_place(logistics.getSend_province()+logistics.getSend_city()
                +logistics.getSend_area()+logistics.getSend_detail());
        this.setSend_city(logistics.getSend_province()+logistics.getSend_city());
        this.setS_capital(logistics.getSend_province()+capital.getCapital(logistics.getSend_province()));
        this.setR_capital(logistics.getRece_province()+capital.getCapital(logistics.getRece_province()));
        this.setRece_city(logistics.getRece_province()+logistics.getRece_city());
        this.setRece_place(logistics.getRece_province()+logistics.getRece_city()
                +logistics.getRece_area()+logistics.getRece_detail());
        this.setSign(0);
    }
}