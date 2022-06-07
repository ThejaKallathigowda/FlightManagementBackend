package com.flightApp.cts.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.flightApp.cts.auth.AdminAuth;
import com.flightApp.cts.entity.Admin;
import com.flightApp.cts.entity.FlightDetails;
import com.flightApp.cts.serviceImpl.AdminServiceImpl;


@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	AdminServiceImpl service;
	
	
	
	
	
	@PostMapping("/adminLogin")
	public ResponseEntity<Admin> loginAdmin(@RequestBody AdminAuth auth){
		Admin admin = service.adminLogin(auth);
		return ResponseEntity.ok(admin);
	}
	
	@GetMapping("/getAdmin/{id}")
	public ResponseEntity<Admin> getAdmin(@PathVariable Integer id){
		Admin admin = service.getAdmin(id);
		return ResponseEntity.ok(admin);
	}
	
	/*@DeleteMapping("/deleteAdmin/{id}")
	public ResponseEntity<Void> deleteAdmin(@PathVariable Integer id){
		service.deleteAdmin(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}*/
	
	@GetMapping("/getAllFlightDetails")
	public ResponseEntity<List<FlightDetails>> getAllFlightDetails(){
		List<FlightDetails> list = service.getAllFlightDetails();
		return ResponseEntity.ok().body(list);
	}
	
	@PostMapping("/addFlightDetails")
	public ResponseEntity<FlightDetails> addFlight(@RequestBody FlightDetails flightDetails){
		FlightDetails details = service.addFlightDetails(flightDetails);
		return ResponseEntity.ok().body(details);
	}
	
	@DeleteMapping("/deleteFlightDetails/{flightNumber}")
	public void deleteFlight(@PathVariable Integer flightNumber) {
		service.deleteFlight(flightNumber);
	}
	
	@PostMapping("/updateFlightDetails")
	public ResponseEntity<FlightDetails> updateFlight(@RequestBody FlightDetails flightDetails){
		FlightDetails details = service.updateFlight(flightDetails);
		return ResponseEntity.ok().body(details);
	}
	
	/*@GetMapping("/getAllPassengers")
	public ResponseEntity<List<Passenger>> getAllPassengers(){
		List<Passenger> passengers = service.getAllPassengers();
		return ResponseEntity.ok().body(passengers);
	}
	
	@GetMapping("/getPassengerByBooking/{id}")
	public ResponseEntity<List<Passenger>> getPassengerByBooking(@PathVariable Integer id){
		List<Passenger> passengers = service.getPassengersByBooking(id);
		return ResponseEntity.ok().body(passengers);
	}
	
	//@PostMapping("/addAdmin")
	public ResponseEntity<Admin> addAdmin(@RequestBody Admin admin){
		Admin addedAdmin = service.addAdmin(admin);
		return ResponseEntity.ok(addedAdmin);
	}*/
	
	@GetMapping("/getFlightByNumber/{flightNumber}")
	public ResponseEntity<Object> getFlightByNumber(@PathVariable Integer flightNumber){
		Object details = service.getFlightByFlightNumber(flightNumber);
		return ResponseEntity.ok().body(details);
	}
	
	@GetMapping("/flightDate/{flightNumber}")
	public String getFlightDate(@PathVariable Integer flightNumber) {
		return service.getFlightByFlightNumber(flightNumber).getDepartureDate();
		
	}
	
	
	@GetMapping("/findFlight/{arrivalAirport}/{departureAirport}/{departdate}")
	public ResponseEntity<List<FlightDetails>> findByRouteAndDate(@PathVariable String arrivalAirport,@PathVariable String departureAirport,@PathVariable String departdate){
		System.out.println("123");
		List<FlightDetails> details = service.findByRouteAndDate(arrivalAirport, departureAirport, departdate);
		return ResponseEntity.ok().body(details);
	}
	
	@GetMapping("/findFlightRoundTrip/{arrivalAirport}/{departureAirport}/{departdate}/{returndate}")
	public ResponseEntity<List<FlightDetails>> findByRouteAndDate(@PathVariable String arrivalAirport,@PathVariable String departureAirport,@PathVariable String departdate,@PathVariable String returndate){
		List<FlightDetails> details = service.findByRouteAndDateRoundTrip(arrivalAirport, departureAirport, departdate,returndate);
		return ResponseEntity.ok().body(details);
	}
	@GetMapping("/flightCost/{flightNumber}")
	public Double getFlightCost(@PathVariable Integer flightNumber) {
		return service.getFlightByFlightNumber(flightNumber).getCost();
		
	}
	
		
}
