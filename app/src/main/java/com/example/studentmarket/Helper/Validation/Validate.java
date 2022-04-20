package com.example.studentmarket.Helper.Validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validate {
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean validateEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    public static final Pattern VALID_PHONENUMBER_REGEX =
            Pattern.compile("(84|0[3|5|7|8|9])+([0-9]{8})\\b",Pattern.CASE_INSENSITIVE);

    public static boolean validatePhoneNumber(String phoneNumberStr){
        Matcher matcher = VALID_PHONENUMBER_REGEX.matcher(phoneNumberStr);
        return matcher.find();
    }
}
