package com.flightApp.cts.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flightApp.cts.auth.AdminAuth;
import com.flightApp.cts.entity.Admin;
import com.flightApp.cts.entity.FlightDetails;
import com.flightApp.cts.exeption.AdminDoesnotExistException;
import com.flightApp.cts.exeption.FlightDetailsNotFoundException;
import com.flightApp.cts.exeption.NullAdminException;
import com.flightApp.cts.exeption.NullFlightDetailsException;
import com.flightApp.cts.repo.AdminDao;
import com.flightApp.cts.repo.FlightDetailsDao;
import com.flightApp.cts.service.AdminService;




@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	AdminDao adminDao;

	@Autowired
	FlightDetailsDao flightDao;
	
	

	
	/*@Override
	public Admin addAdmin(Admin admin) {
		if (admin == null)
			throw new NullAdminException("no data provided");
		Integer adminId = (int) ((Math.random() * 900) + 100);
		
		admin.setAdminId(adminId);
		Optional<Admin> checkAdmin = adminDao.findById(admin.getAdminId());
		if (checkAdmin.isPresent()) {
			throw new AdminAlreadyExistException("admin already exist exception");
		} else {
			adminDao.save(admin);
			System.out.println(adminId);
			return admin;
		}
	}*/

	@Override
	public Admin getAdmin(Integer adminId) {
		if (adminId == null)
			throw new NullAdminException("no data provided");
		Optional<Admin> admin = adminDao.findById(adminId);
		if (!admin.isPresent()) {
			throw new AdminDoesnotExistException("admin does not exist ");
		}
		return admin.get();
	}
	
	
	/*@Override
	public void deleteAdmin(Integer adminId) {
		if (adminId == null)
			throw new NullAdminException("no data provided");
		Optional<Admin> admin = adminDao.findById(adminId);
		if (!admin.isPresent()) {
			throw new AdminDoesnotExistException("admin Doesnot Exist Exception");
		}
		adminDao.deleteById(adminId);
	}*/

	@Override
	public Admin adminLogin(AdminAuth auth) {
		if (auth == null) {
			throw new NullAdminException("no data provided");
		}
		Optional<Admin> admin = adminDao.findById(auth.getAdminId());
		if (admin.isPresent()) {
			if (admin.get().getAdminId() == auth.getAdminId() && admin.get().getPassword().equals(auth.getPassword())) {
				return admin.get();
			} else {
				throw new AdminDoesnotExistException("invalid login id or password");
			}
			
		} else
			throw new AdminDoesnotExistException("admin doesnot exist");
	}
	
	@Override
	public FlightDetails getflight(int flightNumber) {
		return flightDao.findById(flightNumber).get();
	}

	@Override
	public List<FlightDetails> getAllFlightDetails() {
		return flightDao.findAll();
	}

	@Override
	public FlightDetails addFlightDetails(FlightDetails details) {
		if (details == null) {
			throw new NullFlightDetailsException("no data provided");
		}
		Integer flightNumber = (int) ((Math.random() * 9000) + 1000);
		details.setFlightNumber(flightNumber);
		details.setStatus("active");
		flightDao.save(details);
		return details;
	}

	@Override
	public void deleteFlight(Integer flightNumber) {
		if (flightNumber == null)
			throw new NullFlightDetailsException("No data recieved..");
		Optional<FlightDetails> details = flightDao.findById(flightNumber);
		if (!details.isPresent()) {
			throw new FlightDetailsNotFoundException("Flight Details not found");
		}
		flightDao.deleteById(flightNumber);
	}

	@Override
	public FlightDetails updateFlight(FlightDetails details) {
		if (details == null)
			throw new NullFlightDetailsException("No data recieved..");
		Optional<FlightDetails> flightDetails = flightDao.findById(details.getFlightNumber());
		if (!flightDetails.isPresent()) {
			throw new FlightDetailsNotFoundException("Flight with flightNumber: " + details.getFlightNumber() + " not exists..");
		}
		flightDao.save(details);
		return details;
	}

	
	/*public List<Passenger> getAllPassengers(){
		return passengerDao.findAll();
	}
	
	public List<Passenger> getPassengersByBooking(Integer id){
		if (id == null) throw new BookingDoesNotFoundException("no data provided");
		Optional<BookingDetails> details = bookingDao.findById(id);
		if (!details.isPresent())
			throw new BookingDoesNotFoundException("booking not found");
		return details.get().getPassengers();
	}*/
	
	public FlightDetails getFlightByFlightNumber(Integer flightNumber) {
		if (flightNumber == null) {
			throw new NullFlightDetailsException("no data privided");
		}
		// here should call admin endpoint
		Optional<FlightDetails> details = flightDao.findById(flightNumber);
		if (!details.isPresent()) {
			throw new FlightDetailsNotFoundException("no flight found for given number");
		}
		return details.get();
	}
	
	@Override
	public List<FlightDetails> findByRouteAndDate(String arrivalAirport, String departureAirport, String date) {
		// here should call admin endpoint
		List<FlightDetails> list = flightDao.findByRouteDate(arrivalAirport.toLowerCase(),
				departureAirport.toLowerCase());
		List<FlightDetails>  flights=new ArrayList<>();
		for (FlightDetails f : list) {
			
			if (f.getDepartureDate().equals(date)) {
				flights.add(f);
				
			}
			
		}
		if(flights.size()>0) {
			return flights;
		}
		throw new FlightDetailsNotFoundException("details not found");
	}
	
	@Override
	public List<FlightDetails> findByRouteAndDateRoundTrip(String arrivalAirport, String departureAirport,
			String departdate, String returndate) {
		// here should call admin endpoint
		List<FlightDetails> list1 = flightDao.findByRouteDate(arrivalAirport.toLowerCase(),
				departureAirport.toLowerCase());
		// here should call admin endpoint
		List<FlightDetails> list2 = flightDao.findByRouteDate(departureAirport.toLowerCase(),arrivalAirport.toLowerCase());
		// TODO Auto-generated method stub
		List<FlightDetails> list =new ArrayList<>();
		
		
		
		List<FlightDetails>  departflights=new ArrayList<>();
		for (FlightDetails f : list1) {
			
			if (f.getDepartureDate().equals(departdate)) {
				departflights.add(f);
				//System.out.println("hii");
				
			}
			
		}
		
		
		List<FlightDetails>  returnflights=new ArrayList<>();
		for (FlightDetails f : list2) {
			
			if (f.getDepartureDate().equals(returndate)) {
				returnflights.add(f);
				//System.out.println("hello");
				//System.out.println(arr);
				
			}
			
		}
		
		list.addAll(departflights);
		list.addAll(returnflights);
		
		
		
		if(departflights.size()<1) {
			throw new FlightDetailsNotFoundException("no flights for this way so choose oneway trip and book tickets");
		}
		
		else if(returnflights.size()<1) {
			throw new FlightDetailsNotFoundException("no returnflights for this way so choose oneway trip and book your tickets");
		}
		
		else if (list.size()>0) {
			return list;}
		
		
		
		throw new FlightDetailsNotFoundException("details not found");
	
	}
	

}
