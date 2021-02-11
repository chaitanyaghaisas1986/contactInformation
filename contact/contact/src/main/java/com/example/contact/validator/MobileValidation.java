package com.example.contact.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MobileValidation {
	
	private static final String regex = "\\d{3}-\\d{7}";

	public static boolean isValidMobileNo(String mobileNo) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(mobileNo);
		return matcher.matches();
	}

}
