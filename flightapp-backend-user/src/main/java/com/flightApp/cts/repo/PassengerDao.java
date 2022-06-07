package com.flightApp.cts.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flightApp.cts.entity.Passenger;



public interface PassengerDao extends JpaRepository<Passenger, Integer> {

}
