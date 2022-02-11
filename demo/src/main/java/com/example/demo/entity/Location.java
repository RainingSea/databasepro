package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.extern.java.Log;

import javax.persistence.*;

@Data
@Entity
public class Location {

    @EmbeddedId
    private LocationPK locationPK;

    @Column(name="log_location")
    private String log_location;

    @Column(name="lng")
    private double log_lng;

    @Column(name="lat")
    private double log_lat;

}
