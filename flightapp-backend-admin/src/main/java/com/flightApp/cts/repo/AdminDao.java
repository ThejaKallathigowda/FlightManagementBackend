package com.flightApp.cts.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flightApp.cts.entity.Admin;



public interface AdminDao extends JpaRepository<Admin, Integer> {

}
