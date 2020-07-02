package com.jfsfeb.airlinereservationsystemwithjdbc.controller;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.jfsfeb.airlinereservationsystemwithjdbc.dto.FlightDetails;
import com.jfsfeb.airlinereservationsystemwithjdbc.dto.UserDetails;
import com.jfsfeb.airlinereservationsystemwithjdbc.exceptions.AirlineSystemException;
import com.jfsfeb.airlinereservationsystemwithjdbc.factory.UserFactory;
import com.jfsfeb.airlinereservationsystemwithjdbc.services.UserServices;

import lombok.extern.log4j.Log4j;

@Log4j
public class LoginController {

	public static final Scanner scan = new Scanner(System.in);
	public static UserServices service = UserFactory.getUserServicesImpleInstance();
	public static final UserDetails userInfo = new UserDetails();

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

	public static void loginController() {

		int select = 0;
		String email = null;
		String password = null;
		String name = null;
		long phone = 0;
		
		log.info("************************************Welcome to Airline Reservation System**********************************");
		log.info("                          --------------Available Flights details are ----------------");
		log.info(String.format("%-10s %-10s %-13s %-15s %-15s %-15s %-15s %s", "FLIGHT_ID", "FLIGHT_NAME",
				"ARRIVAL_TIME", "ARRIVAL_DATE", "DEPARTURE_TIME", "DEPARTURE_DATE", "CAPACITY",
				"TICKET_PRICE"));

		List<FlightDetails> records = service.getFlightDetails();
		for (FlightDetails details : records) {
			log.info(String.format("%-10s %-15s %-10s %-17s %-15s %-15s %-15s %s", details.getFlightId(),
					details.getFlightName(), details.getArrivalTime(), details.getArrivalDate(),
					details.getDepartureTime(), details.getDepartureDate(), details.getSeats(),
					details.getPrice()));
		}

		do {

			try {
				log.info("Press 1 to search flight according to source and destination");
				log.info("Press 2 to register");
				log.info("Press 3 if login");

				select = scan.nextInt();

				switch (select) {
				
				case 1:
					log.info("Search fligth details by source and destination");

					log.info("Enter source : ");
					String source1 = scan.next();

					log.info("Enter destination : ");
					String destination1 = scan.next();

					try {
						List<FlightDetails> search = service.searchFlightBySourceAndDestination(source1, destination1);
						if (search != null) {
							for (FlightDetails details : search) {
								log.info(details);
							}
						}
					} catch (AirlineSystemException e) {
						log.error(e.getMessage());
					}
					break;


				case 2:
					int userId = (int) (Math.random() * 10000);
					if (userId <= 1000) {
						userId = userId + 1000;
					}
					userInfo.setUserId(userId);
					log.info("Your user id: " + userId);
					
					log.info("Enter your name");
					name = scan.next();
					userInfo.setName(name);

					log.info("Enter your phone number");
					phone = scan.nextLong();
					userInfo.setPhoneNumber(phone);

					log.info("Enter your email");
					email = scan.next();
					userInfo.setMailId(email);

					log.info("Enter your password");
					password = scan.next();
					userInfo.setPassword(password);

					try {
						boolean register = service.userRegistration(userInfo);
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

				case 3:
					log.info("Enter yor emailid");
					email = scan.next();

					log.info("Enter your password");
					password = scan.next();

					try {
						UserDetails authBean = service.userLogin(email, password);
						if (authBean != null) {
							String roleAdmin = "admin";
							String roleUser = "user";
							String rol = authBean.getRole();
							if (rol.equalsIgnoreCase(roleAdmin)) {
								AdminController.adminController();
							} else if (rol.equalsIgnoreCase(roleUser)) {
								UserController.userController();
							}
						} else {
							log.info("emailid and password should not be null ");
						}
					} catch (AirlineSystemException e) {
						log.error(e.getMessage());
					}
					break;

				default:
					log.info("Invalid choice, please select from above options only");
					break;
				}

			} catch (InputMismatchException e) {
				log.error("Invalid details, please select only numbers from 1-4");
				scan.next();
			}

		} while (true);

	}
}
