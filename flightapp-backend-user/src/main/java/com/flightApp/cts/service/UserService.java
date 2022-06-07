package com.flightApp.cts.service;

import java.util.List;

import com.flightApp.cts.entity.BookingDetails;
import com.flightApp.cts.entity.Passenger;
import com.flightApp.cts.entity.User;
import com.flightApp.cts.entity.auth.UserAuth;



public interface UserService {
	public User addUser(User user);

	public void updateUser(User user);

	public User getUser(Integer userId);

	//public void deleteUser(Integer userId);

	public User userLogin(UserAuth auth);

	public BookingDetails addBooking(BookingDetails booking, Integer userId, Integer flightNumber,Double cost);

	public void deleteBooking(Integer bookingId, Integer userId,String flightDepartureDate);

	public List<BookingDetails> getBookingByUserId(Integer userId);

	//public List<Object> findByRouteAndDate(String arrivalAirport, String departureAirport, String date);
	
	//public List<Object> findByRouteAndDateRoundTrip(String arrivalAirport, String departureAirport, String departdate, String returndate);
	
	//public Object getFlightByFlightNumber(Integer flightNumber);
	
	public Passenger updatePassenger(Passenger passenger);

}
