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

public class UserDAOImple implements UserDAO {

	ResultSet rs = null;
	UserDetails userBean = new UserDetails();
	TicketsInfo ticketBean = new TicketsInfo();
	FlightDetails flightBean = new FlightDetails();
	Properties properties = new Properties();
	Utility connector = new Utility();
	int result = 0;

	@Override
	public boolean userRegistration(UserDetails user) {
		try (Connection connection = connector.getConnection();
				PreparedStatement myStmt = connection.prepareStatement(connector.getQuery("register"));) {

			myStmt.setInt(1, user.getUserId());
			myStmt.setString(2, user.getName());
			myStmt.setString(3, user.getMailId());
			myStmt.setString(4, user.getPassword());
			myStmt.setLong(5, user.getPhoneNumber());
			myStmt.setString(6, "user");
			result = myStmt.executeUpdate();

			if (result > 0) {
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			throw new AirlineSystemException("Invalid details");
		}
	}

	@Override
	public UserDetails userLogin(String emailId, String password) {
		UserDetails user = new UserDetails();
		try (Connection connection = connector.getConnection();
				PreparedStatement myStmt = connection.prepareStatement(connector.getQuery("query"));) {

			myStmt.setString(1, emailId);
			myStmt.setString(2, password);
			rs = myStmt.executeQuery();

			if (rs.next()) {
				user.setMailId(rs.getString("email"));
				user.setPassword(rs.getString("password"));
				user.setRole(rs.getString("role"));
				return user;
			}
		} catch (Exception e) {
			e.getMessage();
			throw new AirlineSystemException("Enter correct mailid and password which are given while registration");
		}
		throw new AirlineSystemException("Enter correct mailid and password which are given while registration");
	}

	@Override
	public List<FlightDetails> getFlightDetails() {
		List<FlightDetails> flights = new ArrayList<FlightDetails>();
		try (Connection connection = connector.getConnection();
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
				flights.add(flightBean1);
			}
		} catch (Exception e) {
			e.getMessage();
		}
		return flights;
	}

	@Override
	public List<FlightDetails> searchFlightByName(String name) {
		List<FlightDetails> flights1 = new ArrayList<FlightDetails>();
		try (Connection connection = connector.getConnection();
				PreparedStatement myStmt = connection.prepareStatement(connector.getQuery("searchbyname"));) {

			myStmt.setString(1, name);
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
				flights1.add(flightBean1);
			}
			return flights1;
		} catch (Exception e) {
			e.getMessage();
		}
		throw new AirlineSystemException("Fligth details not found");
	}

	@Override
	public TicketsInfo bookFligthTicket(TicketsInfo ticket) {
		try (Connection conn = connector.getConnection();
				PreparedStatement getFlightPstmt = conn.prepareStatement(connector.getQuery("searchquery"));) {

			getFlightPstmt.setInt(1, ticket.getFlightId());

			try (ResultSet getReqSet = getFlightPstmt.executeQuery();) {
				while (getReqSet.next()) {
					int bookFlightId = getReqSet.getInt("flightid");

					if (ticket.getFlightId() == bookFlightId) {

						try (Connection conne = connector.getConnection();
								PreparedStatement getUserPstmt = conne
										.prepareStatement(connector.getQuery("selectuser"));) {

							getUserPstmt.setInt(1, ticket.getUserId());
							try (ResultSet getUser = getUserPstmt.executeQuery();) {
								while (getUser.next()) {
									int user = getUser.getInt("id");

									if (ticket.getUserId() == user) {

										try (Connection conn1 = connector.getConnection();
												PreparedStatement getRequestPstmt = conn1
														.prepareStatement(connector.getQuery("ticketbooking"));) {

											getRequestPstmt.setInt(1, ticket.getTicketId());
											getRequestPstmt.setInt(2, ticket.getUserId());
											getRequestPstmt.setInt(3, ticket.getFlightId());
											getRequestPstmt.setInt(4, ticket.getNoOfSeatsBooked());

											getRequestPstmt.executeUpdate();
											return ticket;

										} catch (Exception e) {
											throw new AirlineSystemException("Can't request flight");
										}

									}
								}
							}
						} catch (Exception e) {
							throw new AirlineSystemException(e.getMessage());
						}
					}
				}
			}
		} catch (AirlineSystemException e) {
			throw new AirlineSystemException(e.getMessage());
		} catch (Exception e) {
			throw new AirlineSystemException(e.getMessage());
		}
		return null;
	}

	@Override
	public boolean cancelFlightTicket(int bookingId) {

		try (Connection connection = connector.getConnection();
				PreparedStatement myStmt = connection.prepareStatement(connector.getQuery("cancelticket"));) {
			myStmt.setInt(1, bookingId);
			result = myStmt.executeUpdate();
			if (result > 0) {
				return true;
			}
		} catch (Exception e) {
			e.getMessage();
		}
		throw new AirlineSystemException("Enter correct booking id");
	}

	@Override
	public List<TicketsInfo> getTicketDetails(int userId) {
		List<TicketsInfo> tickets = new ArrayList<TicketsInfo>();
		try (Connection connection = connector.getConnection();
				PreparedStatement myStmt = connection.prepareStatement(connector.getQuery("ticketdetails"));) {
			myStmt.setInt(1, userId);
			rs = myStmt.executeQuery();
			while (rs.next()) {
				TicketsInfo ticketBean1 = new TicketsInfo();
				ticketBean1.setTicketId(rs.getInt("ticketid"));
				ticketBean1.setUserId(rs.getInt("user_id"));
				ticketBean1.setFlightId(rs.getInt("flight_id"));
				ticketBean1.setNoOfSeatsBooked(rs.getInt("seats"));
				tickets.add(ticketBean1);
			}
			return tickets;
		} catch (Exception e) {
			e.getMessage();
			throw new AirlineSystemException("No tickets with this userid...........");
		}
	}

	@Override
	public List<FlightDetails> searchFlightBySourceAndDestination(String source, String destination) {
		List<FlightDetails> flights2 = new ArrayList<FlightDetails>();
		try (Connection connection = connector.getConnection();
				PreparedStatement myStmt = connection.prepareStatement(connector.getQuery("search"));) {

			myStmt.setString(1, source);
			myStmt.setString(2, destination);
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
				flights2.add(flightBean1);
			}
			return flights2;
		} catch (Exception e) {
			e.getMessage();
		}
		throw new AirlineSystemException("No flight found with these details");
	}

}
