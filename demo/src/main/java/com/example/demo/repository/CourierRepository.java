package com.example.demo.repository;

import com.example.demo.entity.Courier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourierRepository extends JpaRepository<Courier,String> {
}
