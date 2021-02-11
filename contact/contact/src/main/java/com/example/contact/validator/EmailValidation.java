package com.example.contact.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidation {

	private static final String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

	public static boolean isValidEmail(String email) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}
}
