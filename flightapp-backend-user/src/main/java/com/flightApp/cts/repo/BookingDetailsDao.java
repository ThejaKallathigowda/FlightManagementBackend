package com.flightApp.cts.repo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flightApp.cts.entity.BookingDetails;



@Repository
public interface BookingDetailsDao extends JpaRepository<BookingDetails, Integer> {

}
