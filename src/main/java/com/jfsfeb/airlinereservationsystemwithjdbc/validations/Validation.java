package com.jfsfeb.airlinereservationsystemwithjdbc.validations;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jfsfeb.airlinereservationsystemwithjdbc.exceptions.AirlineSystemException;

public class Validation implements AirlineValidations{

	@Override
	public boolean validateId(int id) throws AirlineSystemException {

		String idRegEx = "[0-9]{4}";
		boolean result = false;

		if (Pattern.matches(idRegEx, String.valueOf(id))) {
			result = true;
		} else {
			throw new AirlineSystemException("Please Enter valid Id, It Should Contain Exact 4 Digits");
		}
		return result;
	}

	@Override
	public boolean validateName(String name) throws AirlineSystemException {

		String nameRegEx = "^[A-Za-z\\s]+$";
		boolean result = false;

		Pattern pattern = Pattern.compile(nameRegEx);
		Matcher matcher = pattern.matcher(name);
		if (matcher.matches()) {
			result = true;
		} else {
			throw new AirlineSystemException("Name Should Contains only Alphabets");
		}
		return result;
	}

	@Override
	public boolean validatePhone(long phoneNumber) throws AirlineSystemException {
		String phoneRegEx = "^[0-9]{10}$";
		boolean result = false;
		if (Pattern.matches(phoneRegEx, String.valueOf(phoneNumber))) {
			result = true;
		} else {
			throw new AirlineSystemException("Phone number should contain 10 numbers");
		}
		return result;
	}

	@Override
	public boolean validateEmail(String emailId) throws AirlineSystemException {

		String emailRegEx = "[a-zA-Z0-9][a-zA-Z0-9_.]*@[a-zA-Z]+([.][a-zA-Z]+)+";
		boolean result = false;

		Pattern pattern = Pattern.compile(emailRegEx);
		Matcher matcher = pattern.matcher(emailId);
		if (matcher.matches()) {
			result = true;
		} else {
//			throw new AirlineSystemException("Enter The Proper Email ID, like abc@gmail.com");
			return false;
		}
		return result;
	}

	@Override
	public boolean validatePassword(String password) throws AirlineSystemException {

		String passwordRegEx = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{6,}$";
		boolean result = false;

		Pattern pattern = Pattern.compile(passwordRegEx);
		Matcher matcher = pattern.matcher(password);

		if (matcher.matches()) {
			result = true;
		} else {
//			throw new AirlineSystemException(
//					"Enter Valid Passsword with min 6 Characters, One Uppercase and Lowercase and a Symbol ");
			return false;
		}
		return result;
	}

	@Override
	public boolean validatePrice(double price) throws AirlineSystemException {
		
		String priceRegEx = "[0-9]+([,.][0-9]{1,2})?";
		boolean result = false;
		if (Pattern.matches(priceRegEx, String.valueOf(price))) {
			result = true;
		} else {
			throw new AirlineSystemException("Price should contain only numbers");
		}
		return result;
	}

}
