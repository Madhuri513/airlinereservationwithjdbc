package com.jfsfeb.airlinereservationsystemwithjdbc.dao;

import java.util.List;

import com.jfsfeb.airlinereservationsystemwithjdbc.dto.FlightDetails;
import com.jfsfeb.airlinereservationsystemwithjdbc.dto.TicketsInfo;
import com.jfsfeb.airlinereservationsystemwithjdbc.dto.UserDetails;

public interface AdminDAO {
			
	public boolean addFlights(FlightDetails flights);
	
	public boolean addNewAdmin(UserDetails admin);
	
	public boolean deleteFlights(int id);
	
	public List<FlightDetails> searchFlight(int id);
	
	public List<FlightDetails> viewFlightDetails();
	
	public List<UserDetails> viewAllUserDetails();
	
	public List<TicketsInfo> viewTicketsInfos();

}
