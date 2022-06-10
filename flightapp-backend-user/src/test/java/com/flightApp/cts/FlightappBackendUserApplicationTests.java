package com.flightApp.cts;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.flightApp.cts.entity.Passenger;
import com.flightApp.cts.entity.User;
import com.flightApp.cts.entity.auth.UserAuth;
import com.flightApp.cts.repo.ecxeption.NullUserException;
import com.flightApp.cts.repo.ecxeption.PassengerNotFoundException;
import com.flightApp.cts.repo.ecxeption.UserDoesnotExistException;
import com.flightApp.cts.serviceImpl.UserServiceImpl;

@SpringBootTest
class FlightappBackendUserApplicationTests {

	@Autowired
	UserServiceImpl service;
	
	@Test
	void testNullUserException() {
		NullUserException thrown = Assertions.assertThrows(NullUserException.class, 
				()->service.addUser(null));
		
		Assertions.assertEquals("No data recieved", thrown.getMessage());
		
	}
	
	@Test
	void testUserDoesnotExistException() {
		UserDoesnotExistException thrown =Assertions.assertThrows(UserDoesnotExistException.class, 
				()->service.updateUser(new User(5,"xus","",1L,"")));
		Assertions.assertEquals("User not found", thrown.getMessage());
	}
	
	
	@Test
	void testUserDoesnotExistExceptionInvalidcreds() {
		UserDoesnotExistException thrown =Assertions.assertThrows(UserDoesnotExistException.class, 
				()->service.userLogin(new UserAuth(1,"cud")));
		Assertions.assertEquals("invalid login id or password", thrown.getMessage());
	}
	
	@Test
	void testPassengerNotFoundException() {
		PassengerNotFoundException  thrown=Assertions.assertThrows(PassengerNotFoundException.class, 
				()->service.updatePassenger(new Passenger(678,"",6,"")));
		Assertions.assertEquals("passenger not found", thrown.getMessage());
	}
	
	
	
	
}
