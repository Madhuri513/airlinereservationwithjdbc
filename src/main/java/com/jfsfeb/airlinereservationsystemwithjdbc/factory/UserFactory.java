package com.jfsfeb.airlinereservationsystemwithjdbc.factory;

import com.jfsfeb.airlinereservationsystemwithjdbc.dao.AdminDAO;
import com.jfsfeb.airlinereservationsystemwithjdbc.dao.AdminDAOImple;
import com.jfsfeb.airlinereservationsystemwithjdbc.dao.UserDAO;
import com.jfsfeb.airlinereservationsystemwithjdbc.dao.UserDAOImple;
import com.jfsfeb.airlinereservationsystemwithjdbc.services.AdminServices;
import com.jfsfeb.airlinereservationsystemwithjdbc.services.AdminServicesImple;
import com.jfsfeb.airlinereservationsystemwithjdbc.services.UserServices;
import com.jfsfeb.airlinereservationsystemwithjdbc.services.UserServicesImple;
import com.jfsfeb.airlinereservationsystemwithjdbc.validations.AirlineValidations;
import com.jfsfeb.airlinereservationsystemwithjdbc.validations.Validation;

public class UserFactory {
	
	private UserFactory() {
	}

	public static AdminDAO getAdminDAOImpleInstance() {
		AdminDAO dao = new AdminDAOImple();
		return dao;
	}

	public static UserDAO getUserDAOImpleInstance() {
		UserDAO userDao = new UserDAOImple();
		return userDao;
	}

	public static AdminServices getAdminServicesImpleInstance() {
		AdminServices adminServices = new AdminServicesImple();
		return adminServices;
	}

	public static UserServices getUserServicesImpleInstance() {
		UserServices userServices = new UserServicesImple();
		return userServices;
	}

	public static AirlineValidations getValidationInstance() {
		AirlineValidations validation = new Validation();
		return validation;
	}
	
	

}
