package com.jfsfeb.airlinereservationsystemwithjdbc.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.jfsfeb.airlinereservationsystemwithjdbc.dto.FlightDetails;
import com.jfsfeb.airlinereservationsystemwithjdbc.dto.TicketsInfo;
import com.jfsfeb.airlinereservationsystemwithjdbc.dto.UserDetails;
import com.jfsfeb.airlinereservationsystemwithjdbc.exceptions.AirlineSystemException;
import com.jfsfeb.airlinereservationsystemwithjdbc.factory.UserFactory;
import com.jfsfeb.airlinereservationsystemwithjdbc.services.AdminServices;

import lombok.extern.log4j.Log4j;

@Log4j
public class AdminController {

	public static final Scanner scan = new Scanner(System.in);

	public static final UserDetails userInfo = new UserDetails();
	public static final FlightDetails flightInfo = new FlightDetails();
	public static final AdminServices adminServices = UserFactory.getAdminServicesImpleInstance();

	public static String checkRole() {
		String role = null;
		boolean flag = false;
		do {
			role = scan.next();
			if (role.equalsIgnoreCase("admin") || role.equalsIgnoreCase("user")) {
				flag = true;
			} else {
				System.out.println("Enter role value either user or admin");
				flag = false;
			}
		} while (!flag);
		return role.toLowerCase();
	}

	public static void adminController() {

		int flightId = 0;
		String flightName = null;
		LocalTime arrivalTime = null;
		LocalTime departureTime = null;
		LocalDate arrivalDate = null;
		LocalDate departureDate = null;
		boolean flag = false;

		int choice = 0;

		do {

			try {
				log.info("Which operations you want to perform?????");
				log.info("*****************************************");
				log.info("1.View all flights");
				log.info("2.Add new Flights");
				log.info("3.Add new Admin");
				log.info("4.Search flight by id");
				log.info("5.Delete Flights");
				log.info("6.View All User details");
				log.info("7.View Ticket Bookings");
				log.info("8.Logout");

				choice = scan.nextInt();

				switch (choice) {

				case 1:

					log.info("Flights details are:");

					List<FlightDetails> records = adminServices.viewFlightDetails();
					log.info(String.format("%-10s %-10s %-13s %-15s %-15s %-15s %-15s %s", "FLIGHT_ID", "FLIGHT_NAME",
							"ARRIVAL_TIME", "ARRIVAL_DATE", "DEPARTURE_TIME", "DEPARTURE_DATE", "CAPACITY",
							"TICKET_PRICE"));
					for (FlightDetails details : records) {
						log.info(String.format("%-10s %-15s %-10s %-17s %-15s %-15s %-15s %s", details.getFlightId(),
								details.getFlightName(), details.getArrivalTime(), details.getArrivalDate(),
								details.getDepartureTime(), details.getDepartureDate(), details.getSeats(),
								details.getPrice()));
					}

					break;

				case 2:

					log.info("Add new Flight");

					flightId = (int) (Math.random() * 10000);
					if (flightId <= 1000) {
						flightId = flightId + 1000;
					}
					flightInfo.setFlightId(flightId);
					log.info("Flight id is: " + flightId);

					log.info("Enter Flight name : ");
					flightName = scan.next();
					flightInfo.setFlightName(flightName);

					log.info("Enter Arrival time : ");

					do {
						try {
							flag = false;
							arrivalTime = LocalTime.parse(scan.next());
							break;
						} catch (DateTimeParseException e) {
							log.error(
									"Time should be in HH:MM:SS format only! hours from 0 to 23, minutes from 0 to 59 and seconds from 0 to 59!!!");
						}
					} while (!flag);
					flightInfo.setArrivalTime(arrivalTime);

					log.info("Enter Arrival date : ");

					do {
						try {
							flag = false;
							arrivalDate = LocalDate.parse(scan.next());
							break;
						} catch (DateTimeParseException e) {
							log.error(
									"Date should be in YYYY-MM-DD format only! month from 1 to 12 and date from 1 to 31 only!!!");
						}
					} while (!flag);
					flightInfo.setArrivalDate(arrivalDate);

					log.info("Enter Departure Date : ");
					do {
						try {
							flag = false;
							departureDate = LocalDate.parse(scan.next());
							break;
						} catch (DateTimeParseException e) {
							log.error(
									"Date should be in YYYY-MM-DD format only! month from 1 to 12 and date from 1 to 31 only!!!");
						}
					} while (!flag);
					flightInfo.setDepartureDate(departureDate);

					log.info("Enter Departure time : ");
					do {
						try {
							flag = false;
							departureTime = LocalTime.parse(scan.next());
							break;
						} catch (DateTimeParseException e) {
							log.error(
									"Time should be in HH:MM:SS format only! hours from 0 to 23, minutes from 0 to 59 and seconds from 0 to 59!!!");
						}
					} while (!flag);

					flightInfo.setDepartureTime(departureTime);

					log.info("Enter Source : ");
					String source = scan.next();
					flightInfo.setSource(source);

					log.info("Enter Destination : ");
					String destination = scan.next();
					flightInfo.setDestination(destination);

					log.info("Enter number of seats : ");
					int seats = scan.nextInt();
					flightInfo.setSeats(seats);

					log.info("Enter ticket price : ");
					double price = scan.nextDouble();
					flightInfo.setPrice(price);

					try {
						boolean adding = adminServices.addFlights(flightInfo);

						if (adding) {
							log.info("Flight added successfully ..........");
						}
					} catch (AirlineSystemException e) {
						log.error(e.getMessage());
					}

					break;

				case 3:
					int userId = (int) (Math.random() * 10000);
					if (userId <= 1000) {
						userId = userId + 1000;
					}
					userInfo.setUserId(userId);
					log.info("Your User id is: " + userId);

					log.info("Enter your name");
					String name = scan.next();
					userInfo.setName(name);

					log.info("Enter your phone number");
					long phone = scan.nextLong();
					userInfo.setPhoneNumber(phone);

					log.info("Enter your email");
					String email = scan.next();
					userInfo.setMailId(email);

					log.info("Enter your password");
					String password = scan.next();
					userInfo.setPassword(password);

					log.info("Enter your role");
					String role = checkRole();
					userInfo.setRole(role);

					try {
						boolean register = adminServices.addNewAdmin(userInfo);
						if (register) {
							log.info("Registered successfully");
							break;
						}
					} catch (InputMismatchException e) {
						log.error("Invalid details, please enter correct values");
						scan.next();
						break;
					} catch (AirlineSystemException e) {
						log.error(e.getMessage());
					}
					break;

				case 4:

					log.info("Search flight by id");

					try {

						flightId = scan.nextInt();
						List<FlightDetails> search = adminServices.searchFlight(flightId);
						log.info(String.format("%-10s %-10s %-13s %-15s %-15s %-15s %-15s %s", "FLIGHT_ID",
								"FLIGHT_NAME", "ARRIVAL_TIME", "ARRIVAL_DATE", "DEPARTURE_TIME", "DEPARTURE_DATE",
								"CAPACITY", "TICKET_PRICE"));
						if (search != null) {
							for (FlightDetails details : search) {
								log.info(String.format("%-10s %-15s %-10s %-17s %-15s %-15s %-15s %s",
										details.getFlightId(), details.getFlightName(), details.getArrivalTime(),
										details.getArrivalDate(), details.getDepartureTime(),
										details.getDepartureDate(), details.getSeats(), details.getPrice()));
							}
						}

					} catch (InputMismatchException e) {
						log.error("Id should be in numbers only");
						scan.nextLine();
					} catch (AirlineSystemException e) {
						log.error(e.getMessage());
						break;
					}

					break;

				case 5:

					log.info("Enter id of the fligth to delete : ");

					try {
						flightId = scan.nextInt();
						boolean deleted = adminServices.deleteFlights(flightId);
						if (deleted) {
							log.info("Deleted flight successfully");
						}
					} catch (InputMismatchException e) {
						log.error("Id should be in numbers only");
						scan.nextLine();
					} catch (AirlineSystemException e) {
						log.error(e.getMessage());
					}

					break;

				case 6:

					log.info("User details are:");
					List<UserDetails> users = adminServices.viewAllUserDetails();
					log.info(String.format("%-10s %-25s %-25s %s", "USER_ID", "USER_NAME", "EMAIL", "ROLE"));
					for (UserDetails details : users) {
						log.info(String.format("%-10s %-25s %-25s %s", details.getUserId(), details.getName(),
								details.getMailId(), details.getRole()));
					}
					break;

				case 7:

					log.info("Ticket Booking details are:");
					List<TicketsInfo> bookings = adminServices.viewTicketsInfos();
					log.info(String.format("%-10s %-15s %-15s %s", "TICKET_ID", "USER_ID", "FLIGHT_ID",
							"NUMBER_OF_SEATS"));
					for (TicketsInfo details : bookings) {
						log.info(String.format("%-10s %-15s %-15s %s", details.getTicketId(), details.getUserId(),
								details.getFlightId(), details.getNoOfSeatsBooked()));
					}
					break;

				case 8:

					log.info("Logged out successfully!!!!!!!!!!!");
					LoginController.loginController();
					break;

				default:
					log.error("Invalid choice , please select above options only");
					break;
				}
			} catch (InputMismatchException e) {
				log.error("Invalid Choice, Choice Contain Only values, select 1 to 8 only");
				scan.nextLine();
			} catch (AirlineSystemException e) {
				log.info(e.getMessage());
				break;
			}

		} while (true);

	}

}
