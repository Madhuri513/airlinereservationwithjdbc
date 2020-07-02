package com.jfsfeb.airlinereservationsystemwithjdbc.services;

import java.util.List;

import com.jfsfeb.airlinereservationsystemwithjdbc.dto.FlightDetails;
import com.jfsfeb.airlinereservationsystemwithjdbc.dto.TicketsInfo;
import com.jfsfeb.airlinereservationsystemwithjdbc.dto.UserDetails;

public interface UserServices {
	
	public boolean userRegistration(UserDetails user);
	
	public UserDetails userLogin(String emailId,String password);
	
	public List<FlightDetails> getFlightDetails();
	
	public List<FlightDetails> searchFlightByName(String name);
	
	public List<FlightDetails> searchFlightBySourceAndDestination(String source, String destination);
	
	public TicketsInfo bookFligthTicket(TicketsInfo ticket);
	
	public boolean cancelFlightTicket(int bookingId);
	
	public List<TicketsInfo> getTicketDetails(int userId);
	

}
