package com.jfsfeb.airlinereservationsystemwithjdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.jfsfeb.airlinereservationsystemwithjdbc.dto.FlightDetails;
import com.jfsfeb.airlinereservationsystemwithjdbc.dto.TicketsInfo;
import com.jfsfeb.airlinereservationsystemwithjdbc.dto.UserDetails;
import com.jfsfeb.airlinereservationsystemwithjdbc.exceptions.AirlineSystemException;
import com.jfsfeb.airlinereservationsystemwithjdbc.utility.Utility;

public class AdminDAOImple implements AdminDAO {

	ResultSet rs = null;
	UserDetails userBean = new UserDetails();
	TicketsInfo ticketBean = new TicketsInfo();
	FlightDetails flightBean = new FlightDetails();
	Properties properties = new Properties();
	Utility connector = new Utility();
	int result = 0;

	@Override
	public boolean addFlights(FlightDetails flights) {
		try(Connection connection = connector.getConnection();
				PreparedStatement myStmt = connection.prepareStatement(connector.getQuery("insertquery"));) {
	
			myStmt.setInt(1,  flights.getFlightId());
			myStmt.setString(2, flights.getFlightName());
			myStmt.setTime(3, java.sql.Time.valueOf(flights.getArrivalTime()));
			myStmt.setDate(4, java.sql.Date.valueOf(flights.getArrivalDate()));
			myStmt.setDate(5, java.sql.Date.valueOf(flights.getDepartureDate()));
			myStmt.setTime(6, java.sql.Time.valueOf(flights.getDepartureTime()));
			myStmt.setString(7, flights.getSource());
			myStmt.setString(8, flights.getDestination());
			myStmt.setInt(9, flights.getSeats());
			myStmt.setDouble(10, flights.getPrice());

			result = myStmt.executeUpdate();

			if (result != 0) {
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			e.getMessage();
		} 
		throw new AirlineSystemException("Invalid details");
	}

	@Override
	public boolean deleteFlights(int id) {
		try(Connection connection = connector.getConnection();
				PreparedStatement myStmt = connection.prepareStatement(connector.getQuery("deletequery"));) {
			myStmt.setInt(1, id);
			result = myStmt.executeUpdate();
			if (result > 0) {
				return true;
			}
		} catch (Exception e) {
			System.out.println(e);
		} 
		throw new AirlineSystemException("Flight id is not present");
	}

	@Override
	public List<FlightDetails> searchFlight(int id) {
		List<FlightDetails> searchFlight = new ArrayList<FlightDetails>();
		try(Connection connection = connector.getConnection();
				PreparedStatement myStmt = connection.prepareStatement(connector.getQuery("searchquery"));) {
			myStmt.setInt(1, id);
			rs = myStmt.executeQuery();
			if (rs.next()) {
				FlightDetails flightBean1 = new FlightDetails();
				flightBean1.setFlightId(rs.getInt("flightid"));
				flightBean1.setFlightName(rs.getString("flightname"));
				flightBean1.setArrivalTime(rs.getTime("arrivaltime").toLocalTime());
				flightBean1.setArrivalDate(rs.getDate("arrivaldate").toLocalDate());
				flightBean1.setDepartureDate(rs.getDate("departuredate").toLocalDate());
				flightBean1.setDepartureTime(rs.getTime("departuretime").toLocalTime());
				flightBean1.setSource(rs.getString("source"));
				flightBean1.setDestination(rs.getString("destination"));
				flightBean1.setSeats(rs.getInt("seats"));
				flightBean1.setPrice(rs.getDouble("price"));
				searchFlight.add(flightBean1);
			}
			return searchFlight;
		} catch (Exception e) {
			e.getMessage();
		} 
		throw new AirlineSystemException("Flight details not found");
	}

	@Override
	public List<FlightDetails> viewFlightDetails() {
		List<FlightDetails> list = new ArrayList<FlightDetails>();
		try(Connection connection = connector.getConnection();
				PreparedStatement myStmt = connection.prepareStatement(connector.getQuery("getquery"));) {

			rs = myStmt.executeQuery();
			while (rs.next()) {
				FlightDetails flightBean1 = new FlightDetails();
				flightBean1.setFlightId(rs.getInt("flightid"));
				flightBean1.setFlightName(rs.getString("flightname"));
				flightBean1.setArrivalTime(rs.getTime("arrivaltime").toLocalTime());
				flightBean1.setArrivalDate(rs.getDate("arrivaldate").toLocalDate());
				flightBean1.setDepartureDate(rs.getDate("departuredate").toLocalDate());
				flightBean1.setDepartureTime(rs.getTime("departuretime").toLocalTime());
				flightBean1.setSource(rs.getString("source"));
				flightBean1.setDestination(rs.getString("destination"));
				flightBean1.setSeats(rs.getInt("seats"));
				flightBean1.setPrice(rs.getDouble("price"));
				list.add(flightBean1);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return list;
	}

	@Override
	public List<UserDetails> viewAllUserDetails() {
		List<UserDetails> allUsers = new ArrayList<UserDetails>();
		try(Connection connection = connector.getConnection();
				PreparedStatement myStmt = connection.prepareStatement(connector.getQuery("getuser"));) {
			rs = myStmt.executeQuery();
			while (rs.next()) {
				UserDetails userBean1 = new UserDetails();
				userBean1.setUserId(rs.getInt("id"));
				userBean1.setName(rs.getString("name"));
				userBean1.setMailId(rs.getString("email"));
				userBean1.setPassword(rs.getString("password"));
				userBean1.setRole(rs.getString("role"));
				allUsers.add(userBean1);
			}
		} catch (Exception e) {
			System.out.println(e);
		} 
		return allUsers;
	}

	@Override
	public List<TicketsInfo> viewTicketsInfos() {
		List<TicketsInfo> bookings = new ArrayList<TicketsInfo>();
		try(Connection connection = connector.getConnection();
				PreparedStatement myStmt = connection.prepareStatement(connector.getQuery("getticket"));) {
			rs = myStmt.executeQuery();
			while (rs.next()) {
				TicketsInfo tickets = new TicketsInfo();
				tickets.setTicketId(rs.getInt("ticketId"));
				tickets.setUserId(rs.getInt("user_id"));
				tickets.setFlightId(rs.getInt("flight_id"));
				tickets.setNoOfSeatsBooked(rs.getInt("seats"));
				bookings.add(tickets);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return bookings;
	}

	@Override
	public boolean addNewAdmin(UserDetails admin) {
		try(Connection connection = connector.getConnection();
				PreparedStatement myStmt = connection.prepareStatement(connector.getQuery("register"));) {
	
			myStmt.setInt(1,  admin.getUserId());
			myStmt.setString(2, admin.getName());
			myStmt.setString(3, admin.getMailId());
			myStmt.setString(4, admin.getPassword());
			myStmt.setLong(5, admin.getPhoneNumber());
			myStmt.setString(6, admin.getRole());

			result = myStmt.executeUpdate();

			if (result != 0) {
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			e.getMessage();
		} 
		throw new AirlineSystemException("Invalid details");
	}
}
