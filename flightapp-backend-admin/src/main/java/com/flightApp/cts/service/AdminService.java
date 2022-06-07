package com.flightApp.cts.service;

import java.util.List;

import com.flightApp.cts.auth.AdminAuth;
import com.flightApp.cts.entity.Admin;
import com.flightApp.cts.entity.FlightDetails;



public interface AdminService {
	//public Admin addAdmin(Admin admin);

	public Admin getAdmin(Integer adminId);

	//public void deleteAdmin(Integer adminId);*/

	public Admin adminLogin(AdminAuth auth);
	
	public FlightDetails getflight(int flightNumber);

	public List<FlightDetails> getAllFlightDetails();

	public FlightDetails addFlightDetails(FlightDetails details);

	public void deleteFlight(Integer flightNumber);

	public FlightDetails updateFlight(FlightDetails details);
	
	/*public List<Passenger> getAllPassengers();
	
	public List<Passenger> getPassengersByBooking(Integer id);*/
	
	public List<FlightDetails> findByRouteAndDate(String arrivalAirport, String departureAirport, String date);
	
	public List<FlightDetails> findByRouteAndDateRoundTrip(String arrivalAirport, String departureAirport,
			String departdate, String returndate);

}
