package com.example.example.utils;

public class PhoneNumberUtil {
    public static Integer toNumber(String phoneNumber) {
        final String onlyNumber = phoneNumber.replaceAll("[^0-9]", "");
        return Integer.parseInt(onlyNumber);
    }
}
