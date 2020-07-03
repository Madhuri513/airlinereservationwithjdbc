package com.jfsfeb.airlinereservationsystemwithjdbc.dto;

import java.io.Serializable;

import lombok.Data;
import lombok.ToString;

@Data
@SuppressWarnings("serial")
public class UserDetails implements Serializable {
	
	private int userId;
	private String name;
	private String mailId;
	@ToString.Exclude
	private String password;
	@ToString.Exclude
	private long phoneNumber;
	private String role;

}
