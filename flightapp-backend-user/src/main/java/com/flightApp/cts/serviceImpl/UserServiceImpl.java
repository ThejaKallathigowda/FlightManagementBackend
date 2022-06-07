package com.flightApp.cts.serviceImpl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flightApp.cts.entity.BookingDetails;
import com.flightApp.cts.entity.Passenger;
import com.flightApp.cts.entity.User;
import com.flightApp.cts.entity.auth.UserAuth;
import com.flightApp.cts.repo.BookingDetailsDao;
import com.flightApp.cts.repo.PassengerDao;
import com.flightApp.cts.repo.UserDao;
import com.flightApp.cts.repo.ecxeption.NullUserException;
import com.flightApp.cts.repo.ecxeption.PassengerNotFoundException;
import com.flightApp.cts.repo.ecxeption.UserAlreadyExistException;
import com.flightApp.cts.repo.ecxeption.UserDoesnotExistException;
import com.flightApp.cts.service.UserService;




@Service
public class UserServiceImpl implements UserService{

	@Autowired
	UserDao userDao;

	/*@Autowired
	FlightDetailsDao flightDao;*/

	@Autowired
	BookingDetailsDao bookingDao;
	
	@Autowired
	PassengerDao passengerDao;

	@Override
	public User addUser(User user) {

		if (user == null)
			throw new NullUserException("No data recieved");
		Integer userId = (int) ((Math.random() * 900) + 100);
		user.setUserId(userId);
		Optional<User> checkUser = userDao.findById(user.getUserId());
		if (checkUser.isPresent())
			throw new UserAlreadyExistException("user already exists");

		userDao.save(user);
		System.out.println("user Added");
		return user;

	}

	
	@Override
	public void updateUser(User user) {
		if (user == null)
			throw new NullUserException("No data recieved");
		Optional<User> checkUser = userDao.findById(user.getUserId());
		if (checkUser.isPresent())
			userDao.save(user);
		else
			throw new UserDoesnotExistException("User not found");

	}

	
	@Override
	public User getUser(Integer userId) {
		if (userId == null)
			throw new NullUserException("No data recieved");
		Optional<User> user = userDao.findById(userId);
		if (!user.isPresent())
			throw new UserDoesnotExistException("User not found");
		return user.get();
	}

	
	/*@Override
	public void deleteUser(Integer userId) {
		if (userId == null)
			throw new NullUserException("No data recieved");
		Optional<User> user = userDao.findById(userId);
		if (!user.isPresent())
			throw new UserDoesnotExistException("User not found");
		userDao.deleteById(userId);
	}*/

	
	@Override
	public User userLogin(UserAuth auth) {
		if (auth == null) {
			throw new NullUserException("No data recieved");
		}
		Optional<User> user = userDao.findById(auth.getUserId());
		if (user.isPresent()) {
			if (user.get().getUserId() == auth.getUserId() && user.get().getPassword().equals(auth.getPassword())) {
				return user.get();
			} else {
				throw new UserDoesnotExistException("invalid login id or password");
			}
			
		} else {
			throw new UserDoesnotExistException("User not found");
		}
	}

	// here should call admin endpoint
	@Override
	public BookingDetails addBooking(BookingDetails booking, Integer userId, Integer flightNumber,Double cost) {
		Optional<User> user = userDao.findById(userId);
		// here should call admin endpoint
		//Optional<FlightDetails> flight = flightDao.findById(flightNumber);
		if (!user.isPresent()) {
			throw new UserDoesnotExistException("user id not found");
		}
		/*if (!flight.isPresent()) {
			throw new FlightDetailsNotFoundException("flight details not found");
		}*/
		Integer bookingId = (int) ((Math.random() * 9000) + 1000);
		booking.setBookingId(bookingId);
		//booking.setOwnerId(userId);
		booking.setFlightNumber(flightNumber);
		booking.setBookingDate(LocalDate.now().toString());
		booking.setBookingTime(LocalTime.now().toString().substring(0, 5));
		booking.setTotalCost(cost * booking.getPassengers().size());
		List<BookingDetails> bookingList = user.get().getBookingDetails();
		bookingList.add(booking);
		user.get().setBookingDetails(bookingList);
		updateUser(user.get());
		return bookingDao.getById(bookingId);
	}

	@Override
	public void deleteBooking(Integer bookingId, Integer userId,String flightDepatureDate) {
		Optional<User> u = userDao.findById(userId);
		Optional<BookingDetails> bd = bookingDao.findById(bookingId);
		if (!bd.isPresent()) {
			throw new UserDoesnotExistException("booking not found");
		}
		if (!u.isPresent()) {
			throw new UserDoesnotExistException("user id not found");
		}
		User user = u.get();
		List<BookingDetails> bookingList = user.getBookingDetails();
		BookingDetails deleteBooking = null;
		for (BookingDetails b : bookingList) {
			if (b.getBookingId() == bookingId) {
				System.out.println("booking id found");
				//Optional<FlightDetails> flight = flightDao.findById(b.getFlightNumber());
				LocalDate today= LocalDate.now();
				String day=today.toString();
				String depDate=flightDepatureDate;
				System.out.println(day+" "+depDate);
				//if(flight.get().getDepartureDate()) {}
				String[] s1=depDate.split("-");
				String[] s2=day.split("-");
				
				if(Integer.parseInt(s1[0])<=Integer.parseInt(s2[0]) && 
						Integer.parseInt(s1[1])<=
						Integer.parseInt(s2[1])
						&& (Integer.parseInt(s1[2])-Integer.parseInt(s2[2])<=1)) {
					System.out.println("errroe");
					throw new UserDoesnotExistException("booking cannot be cancelled");
					
				}
				
				deleteBooking = b;
			}
		}
		bookingList.remove(deleteBooking);
		user.setBookingDetails(bookingList);
		bookingDao.deleteById(bookingId);
		updateUser(user);
	}

	@Override
	public List<BookingDetails> getBookingByUserId(Integer userId) {
		Optional<User> user = userDao.findById(userId);
		if (!user.isPresent()) {
			throw new UserDoesnotExistException("user id not found");
		}
		return user.get().getBookingDetails();
	}

	// here should call admin endpoint
	/*@Override
	public List<Object> findByRouteAndDate(String arrivalAirport, String departureAirport, String date) {
		// here should call admin endpoint
		List<Object> list = flightDao.findByRouteDate(arrivalAirport.toLowerCase(),
				departureAirport.toLowerCase());
		List<Object>  flights=new ArrayList<>();
		for (Object f : list) {
			
			if (f.getDepartureDate().equals(date)) {
				flights.add(f);
				
			}
			
		}
		if(flights.size()>0) {
			return flights;
		}
		//throw new FlightDetailsNotFoundException("details not found");
	}
	
	// here should call admin endpoint
	
	/*@Override
	public List<Object> findByRouteAndDateRoundTrip(String arrivalAirport, String departureAirport,
			String departdate, String returndate) {
		// here should call admin endpoint
		List<Object> list1 = flightDao.findByRouteDate(arrivalAirport.toLowerCase(),
				departureAirport.toLowerCase());
		// here should call admin endpoint
		List<Object> list2 = flightDao.findByRouteDate(departureAirport.toLowerCase(),arrivalAirport.toLowerCase());
		// TODO Auto-generated method stub
		List<Object> list =new ArrayList<>();
		
		
		
		List<Object>  departflights=new ArrayList<>();
		for (Object f : list1) {
			
			if (f.getDepartureDate().equals(departdate)) {
				departflights.add(f);
				//System.out.println("hii");
				
			}
			
		}
		
		
		List<Object>  returnflights=new ArrayList<>();
		for (ObjectUserServiceImpl.java f : list2) {
			
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
	
	// here should call admin endpoint
	/*@Override
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
	}*/
	
	@Override
	public Passenger updatePassenger(Passenger passenger) {
		if (passenger == null) {
			throw new PassengerNotFoundException("no data provided");
		}
		Optional<Passenger> oldPassenger = passengerDao.findById(passenger.getPassengerId()); 
		if (!oldPassenger.isPresent()) {
			throw new PassengerNotFoundException("passenger not found");
		}
		passengerDao.save(passenger);
		return passenger;
	}

	public BookingDetails getBooking( Integer bookingId) {
		return bookingDao.findById(bookingId).get();
	}
	

	

}
