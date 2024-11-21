/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.amhs.database.dao;

import java.text.DecimalFormat;

/**
 *
 * @author user
 */
public class Common {

    //Kiem tra chuoi truyen vao co phai kieu ki tu khong
    private static DecimalFormat timeformat = new DecimalFormat("00");

    public static String plusTime(String time01, String time02) {
	// validate
	if (!validateHourMinute(time01) || !validateHourMinute(time02)) {
	    return "----";
	}

	Integer hour01 = Integer.parseInt(time01.substring(0, 2));
	Integer minute01 = Integer.parseInt(time01.substring(2, 4));
	Integer hour02 = Integer.parseInt(time02.substring(0, 2));
	Integer minute02 = Integer.parseInt(time02.substring(2, 4));
	Integer hours = (hour01 + hour02);
	Integer minute = (minute01 + minute02);
	hours += minute / 60;
	minute %= 60;
	hours %= 24;
	return String.format("%s%s", timeformat.format(hours), timeformat.format(minute));
    }

    public static boolean validateHourMinute(String callsign) {
	final String strFormat = "^(0[0-9]|1[0-9]|2[0-3])[0-5][0-9]$";
	return validateFormat(strFormat, callsign);
    }

    public static boolean validateFormat(String format, String str) {
	return str.matches(format);
    }

}
