package com.nisal.beMax.domain.validation;

public class Util {

    public static boolean isMobileValid(String mobile) {
        return mobile.matches("^07[0145678][0-9]{7}$");
    }

}
