package com.company.util;

import java.util.Calendar;

public class ValidationUtil {

    private ValidationUtil() {
    }

    public static boolean isNotValidAction(int action) {
        return action < 1 || action > 2;
    }

    public static boolean isNotValidCategory(int category) {
        return category < 1 || category > 7;
    }

    public static boolean isCorrectDateFormat(String[] userDate) {
        return userDate.length == 3;
    }

    public static boolean isCorrectDate (int day, int month, int year) {

        if ((month == 4 || month == 6 || month == 9 || month == 11) && (day > 0 && day <= 30)) {
            return true;
        } else if ((month == 3 || month == 5 || month == 7) && (day > 0 && day <= 31)) {
            return true;
        } else if (month == 2 && chekLeapYear(year) && (day > 0 && day <= 29)) {
            return true;
        } else if (month == 2 && !chekLeapYear(year) && (day > 0 && day <= 28)) {
            return true;
        } else {
            return false;
        }
    }

    private static boolean chekLeapYear(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        return calendar.getActualMaximum(Calendar.DAY_OF_YEAR) > 365;
    }

}
