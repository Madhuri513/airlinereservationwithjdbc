package com.jfsfeb.airlinereservationsystemwithjdbc.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Data;

@Data
@SuppressWarnings("serial")
public class FlightDetails implements Serializable {
	
	private int flightId;
	private String flightName;
	private LocalTime arrivalTime;
	private LocalDate arrivalDate;
	private LocalTime departureTime;
	private LocalDate departureDate;
	private String source;
	private String destination;
	private int seats;
	private double price;

}
