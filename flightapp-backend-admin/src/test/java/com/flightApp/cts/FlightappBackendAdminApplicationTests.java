package com.flightApp.cts;

import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static  org.junit.jupiter.api.Assertions.*;


import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.flightApp.cts.entity.FlightDetails;
import com.flightApp.cts.exeption.FlightDetailsNotFoundException;
import com.flightApp.cts.repo.FlightDetailsDao;
import com.flightApp.cts.serviceImpl.AdminServiceImpl;

@SpringBootTest
class FlightappBackendAdminApplicationTests {

	@Test
	void contextLoads() {
	}
	
	@Autowired
	AdminServiceImpl service;
	
	@MockBean
	FlightDetailsDao flightrepo;
	
	@Test
	void testFlightDetailsNotFoundException() {

		FlightDetailsNotFoundException thrown = Assertions.assertThrows(FlightDetailsNotFoundException.class, () -> 
			service.deleteFlight(1224));
		
		Assertions.assertEquals("Flight Details not found", thrown.getMessage());
	}
	
	
	
	@Test
	public void getflightByFlightNumber() {
		Integer flightNumber=3473;
		FlightDetails flight=new FlightDetails();
		//FlightDetails flight=new FlightDetails(3473,"bengaluru","cochin",78,"2022-06-12","2022-06-18","12:53","12:54","indigo",7896.00,"active");
		flight.setArrivalAirport("cochin");
		flight.setArrivalDate("2022-06-18");
		flight.setArrivalTime("12:53");
		flight.setAvailableSeats(78);
		flight.setCost(7896d);
		flight.setDepartureAirport("bengaluru");
		flight.setDepartureDate("2022-06-12");
		flight.setDepartureTime("12:54");
		flight.setFlightNumber(3473);
		flight.setFlightVendor("indigo");
		flight.setStatus("active");
		
		
		
		when(flightrepo.findById(3473)).thenReturn(Optional.of(flight));
		FlightDetails f=service.getFlightByFlightNumber(3473);
		System.out.print(f);
		
		assertEquals(flight, f);
		
		
		
	}


	
	

}
