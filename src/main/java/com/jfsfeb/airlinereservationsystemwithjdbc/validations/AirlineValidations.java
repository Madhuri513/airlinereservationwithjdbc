package com.jfsfeb.airlinereservationsystemwithjdbc.validations;

public interface AirlineValidations {
	
	public boolean validateId(int id);
	
	public boolean validateName(String name);
	
	public boolean validatePhone(long phoneNumber);
	
	public boolean validateEmail(String emailId);
	
	public boolean validatePassword(String password);
	
	public boolean validatePrice(double price);

}
