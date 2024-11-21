/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.administrator.common;

import java.util.regex.Pattern;

/**
 *
 * @author AnhTH
 */
public class Validator {

    public static boolean isEmpty(String value, Integer minLength) {
        if (minLength <= 0) {
            return true;
        }

        if (value == null || value.isEmpty()) {
            return false;
        }

        return (value.length() >= minLength);
    }
    
    public static boolean isFloating(String value, boolean isMandatory) {
        if (value == null || value.isEmpty()) {
            return (!isMandatory);
        }

        try {
            Float.parseFloat(value);
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }

    public static boolean isInRange(String value, Integer minValue, Integer maxValue) {
        if ((value == null || value.isEmpty()) && minValue == null && maxValue == null) {
            return true;
        }

        try {
            int intValue = Integer.parseInt(value);
            
            if (minValue == null && maxValue == null) {
                return true;
            }
            
            if (minValue == null) {
                return intValue <= maxValue;
            }
            
            if (maxValue == null) {
                return intValue >= minValue;
            }
            
            return intValue >= minValue && intValue <= maxValue;
        } catch (NumberFormatException ex) {
            return false;
        }
    }
    
    private static final String IPV4_REGEX
            = "^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\."
            + "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\."
            + "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\."
            + "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
    private static final Pattern IPv4_PATTERN = Pattern.compile(IPV4_REGEX);

    public static boolean isValidInet4Address(String ip) {
        if (ip == null) {
            return false;
        }
        return IPv4_PATTERN.matcher(ip).matches();
    }

    public static boolean isEmpty(String value) {
        return !(value == null || value.isEmpty());
    }

    public static boolean isMinLeng(String value, Integer minLength) {
        if (minLength <= 0) {
            return true;
        }
        if (value == null || value.isEmpty()) {
            return false;
        }
        return (value.length() >= minLength);
    }

    public static boolean isMaxLeng(String value, Integer maxLength) {
        if (maxLength <= 0) {
            return true;
        }
        if (value == null || value.isEmpty()) {
            return false;
        }
        return (value.length() <= maxLength);
    }

    public static boolean isInteger(String value, boolean isMandatory) {
        if (value == null || value.isEmpty()) {
            return (!isMandatory);
        }
        try {
            Integer.parseInt(value);
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }

    
    public static boolean isChar(String value) {
        return value.matches("[ a-zA-Z0-9-/]+");
    }

    public static boolean isNumberic(String value) {
        return value.matches("[ 0-9-.]+");
    }
}
