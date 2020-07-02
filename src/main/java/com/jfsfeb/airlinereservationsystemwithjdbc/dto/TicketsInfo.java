package com.jfsfeb.airlinereservationsystemwithjdbc.dto;

import java.io.Serializable;

import lombok.Data;


@Data
@SuppressWarnings("serial")
public class TicketsInfo implements Serializable {
	
	private int ticketId; 
	private int userId;
	private int flightId;
	private int noOfSeatsBooked;

}
