package com.example.demo.repository;

import com.example.demo.entity.Location;
import com.example.demo.entity.LocationPK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface LocationRepository extends JpaRepository<Location, LocationPK> {
    List<Location> findLocationByLocationPK_LogidContaining(String logid);
}
