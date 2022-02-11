package com.example.demo.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class LocationPK implements Serializable {

//    private static final long serialVersionUID = -7523633817377892758L;
//    public LocationPK(String logidid,String logtime){
//        this.logid=logidid;
//        this.logtime=logtime;
//    }

    @Column(name = "log_id")
    private String logid;

    @Column(name = "log_time")
    private String logtime;

}
