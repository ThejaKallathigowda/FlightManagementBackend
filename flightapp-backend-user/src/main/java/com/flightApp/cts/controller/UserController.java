package com.flightApp.cts.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.flightApp.cts.entity.BookingDetails;
import com.flightApp.cts.entity.Passenger;
import com.flightApp.cts.entity.User;
import com.flightApp.cts.entity.auth.UserAuth;
import com.flightApp.cts.repo.ecxeption.UserValidationException;
import com.flightApp.cts.serviceImpl.UserServiceImpl;



@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/user")
public class UserController {
	
	RestTemplate restTemplate=new RestTemplate();
	String uri="http://localhost:8087/admin";
	

	@Autowired
	private UserServiceImpl service;
	

	@PostMapping("/addUser")
	public ResponseEntity<User> addUser(@Valid @RequestBody User user, Errors error) {
		if (error.hasErrors()) {
			throw new UserValidationException("invalid data provided");
		}
		User addedUser =  service.addUser(user);
		return ResponseEntity.ok().body(addedUser);
	}

	@PostMapping("/userLogin")
	public ResponseEntity<User> loginUser(@RequestBody UserAuth auth) {
		User user = service.userLogin(auth);
		return ResponseEntity.ok().body(user);
		
	}

	@GetMapping("/getUser/{id}")
	public ResponseEntity<User> getUser(@PathVariable Integer id) {
		
		User user = service.getUser(id);
		return ResponseEntity.ok(user);
	}

	@PostMapping("/updateUser")
	public ResponseEntity<Void> updateUser(@Valid @RequestBody User user) {
		service.updateUser(user);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	/*@DeleteMapping("/deleteUser/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
		service.deleteUser(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}*/

 	
	@PostMapping("/addBooking/{userId}/{flightNumber}")
	public ResponseEntity<BookingDetails> addBooking(@RequestBody BookingDetails booking,@PathVariable Integer userId,@PathVariable Integer flightNumber){
		
	    String url=uri+"/flightCost/"+flightNumber;
		Double flightCost =restTemplate.getForObject(url, Double.class);
		System.out.println(url);
		System.out.println(flightCost);
		BookingDetails details = service.addBooking(booking, userId, flightNumber,flightCost);
		return ResponseEntity.ok().body(details);
	}
	
	@DeleteMapping("/deleteBooking/{bookingId}/{userId}")
	public void deleteBooking(@PathVariable Integer bookingId,@PathVariable Integer userId) {
		BookingDetails b= service.getBooking(bookingId);
		Integer flightNumber=b.getFlightNumber();
		String url=uri+"/flightDate/"+flightNumber;
		String date =restTemplate.getForObject(url, String.class);
		System.out.println(url);
		System.out.println(date);
		service.deleteBooking(bookingId, userId,date); 
	}
	
	@GetMapping("/findFlightRoundTrip/{arrivalAirport}/{departureAirport}/{departdate}/{returndate}")
	public ResponseEntity<List<Object>> findByRouteAndDate(@PathVariable String arrivalAirport,@PathVariable String departureAirport,@PathVariable String departdate,@PathVariable String returndate){
		String url=uri+"/findFlightRoundTrip/"+arrivalAirport+"/"+departureAirport+"/"+departdate+"/"+returndate;
		System.out.println(url);
		List<Object> flightList =restTemplate.getForObject(url,List.class);
		return ResponseEntity.ok().body(flightList);
	}
	
	@GetMapping("/findFlight/{arrivalAirport}/{departureAirport}/{departdate}")
	public ResponseEntity<List<Object>> findByRouteAndDate(@PathVariable String arrivalAirport,@PathVariable String departureAirport,@PathVariable String departdate){
		String url=uri+"/findFlight/"+arrivalAirport+"/"+departureAirport+"/"+departdate;
		System.out.println(url);
		List<Object> flightList =restTemplate.getForObject(url,List.class);
		return ResponseEntity.ok().body(flightList);
	}
	
	
	@GetMapping("/getBookingByUser/{userId}")
	public ResponseEntity<List<BookingDetails>> getBookingByUser(@PathVariable Integer userId){
		List<BookingDetails> list = service.getBookingByUserId(userId);
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping("/getFlightByNumber/{flightNumber}")
	public ResponseEntity<Object> getFlightByNumber(@PathVariable Integer flightNumber){
		 
		
	    System.out.println(flightNumber);
		String url=uri+"/getFlightByNumber/"+flightNumber;
		System.out.println(url);
		
		
		
		Object details=restTemplate.getForObject(url, Object.class);
		System.out.println(details);
		//return "hi"+flightNumber;
		//Object details = service.getFlightByFlightNumber(flightNumber);
		return ResponseEntity.ok().body(details);
	}
	
	@PostMapping("/updatePassenger")
	public ResponseEntity<Passenger> updatePassenger(@RequestBody Passenger passenger){
		Passenger p = service.updatePassenger(passenger);
		return ResponseEntity.ok().body(p);
	}
}
