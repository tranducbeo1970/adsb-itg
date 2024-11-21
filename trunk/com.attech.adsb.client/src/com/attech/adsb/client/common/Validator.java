/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.common;

import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author SONLT
 */
public class Validator {

    public static boolean isNullOrEmpty(String value) {
        return value == null || value.isEmpty();
    }

    public static boolean isNullOrEmpty(List value) {
        return value == null || value.isEmpty();
    }

    public static boolean validateLength(String value, int length, boolean nullable) {
        if (isNullOrEmpty(value)) {
            return nullable;
        }
        return value.length() == length;
    }

    public static boolean checkNotEmpty(JTextField textfield) {
        return !(textfield.getText() == null || textfield.getText().isEmpty());
    }

    public static boolean checkNotEmpty(JTextArea textfield) {
        return !(textfield.getText() == null || textfield.getText().isEmpty());
    } 
    
    public static boolean checkLength(String str, int length) {
        return str.length() == length;
    }

    public static boolean checkLength(String str, int min, int max) {
        return str.length() >= min && str.length() <= max;
    }

//    public static boolean checkAmhsAddress(String address) {
////        AMHSAddress amhsAddress = AMHSAddress.get(address);
//        return amhsAddress.isValid();
//    }

    public static boolean validateFormat(String format, String str) {
        return str.matches(format);
    }

    public static boolean validateAmhsAddress(String address) {
        // final String cassformat = "^/CN=([A-Z]|[0-9]){8}/OU=([A-Z]|[0-9]){4}/O=([A-Z]|[0-9]){4}/PRMD=([A-Z]|[0-9]){1,128}/ADMD=ICAO/C=XX/$";
        final String cassformat = "^/CN=([A-Z]|[0-9]){8}/OU=([A-Z]|[0-9]){4}/O=([A-Z]|[0-9]){1,32}/PRMD=([A-Z]|[0-9]){1,128}/ADMD=ICAO/C=XX/$";
        final String xfFormat = "^/OU=([A-Z]|[0-9]){8}/O=AFTN/PRMD=([A-Z]|[0-9]){2,128}/ADMD=ICAO/C=XX/$";
        return validateFormat(cassformat, address) || validateFormat(xfFormat, address);
    }
//7

    public static boolean validateCallSign(String callsign) {
        final String strFormat = "([A-Z]|[0-9]){1,7}";// "^[A-Z0-9]{3}[0-9]{3,4}$";
        return validateFormat(strFormat, callsign);
    }

    public static boolean validateSSR(String callsign) {
        final String strFormat = "^([ASDC])([0-7]{4})$";
        return validateFormat(strFormat, callsign);
    }
//hhmm

    public static boolean validateHourMinute(String callsign) {
        final String strFormat = "^(0[0-9]|1[0-9]|2[0-3])[0-5][0-9]$";
        return validateFormat(strFormat, callsign);
    }

    public static boolean validateAeroDrome(String aeroDrome) {
        final String strFormat = "^[A-Z]{4}$";
        return validateFormat(strFormat, aeroDrome);
    }

    public static boolean validateDOF(String aeroDrome) {
        final String strFormat = "([0-9]{2}(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01]))|([0]{1})";
        return validateFormat(strFormat, aeroDrome.trim());
    }

    public static boolean validateTotalEET(String totalEET) {
        final String strFormat = "^[0-9]{4}$";
        return validateFormat(strFormat, totalEET);
    }

    public static boolean validateAlternte(String alternte) {
        final String strFormat = "^[A-Z]{4}$";
        return validateFormat(strFormat, alternte);
    }
//5

    public static boolean validate5Emergency1(String alternte) {
        final String strFormat = "[A-Z0-9]{8}";
        return validateFormat(strFormat, alternte);
    }
//Emergency2 only check null and max leght=180 char    

    //9
    public static boolean validate9_Number(String alternte) {
        final String strFormat = "^[0-9]{0,2}$";
        return validateFormat(strFormat, alternte);
    }

    public static boolean validate9_TypeofAricraft(String alternte) {
        final String strFormat = "^([A-Z])([0-9A-Z]{1,3})$";
        return validateFormat(strFormat, alternte);
    }
    //10

    public static boolean validate10_Equipment1(String alternte) {
        final String strFormat = "^([NS])([0-9A-Z\\\\s]{0,46})$";
        return validateFormat(strFormat, alternte);
    }

    public static boolean validate10_Equipment2(String alternte) {
        final String strFormat = "([0-9A-Z\\\\s]{0,46})";
        return validateFormat(strFormat, alternte);
    }

    //13
    public static boolean validate13Departure(String alternte) {
        final String strFormat = "[A-Z]{4}";
        return validateFormat(strFormat, alternte);
    }

    public static boolean validate13Time(String alternte) {
        final String strFormat = "(([01][0-9])|(2[0-3]))([0-5][0-9])";
        return validateFormat(strFormat, alternte);
    }

    //14FD 
    public static boolean validate14Boundary(String alternte) {
        final String strFormat = "([0-9]|[A-Z]){2,5}\");//([0-9]{4})(([FA]([0-9]{3}))|([SM]([0-9]{4})))([AB]{0-1})";
        return validateFormat(strFormat, alternte);
    }

    public static boolean validate14Time(String alternte) {
        final String strFormat = "(([01][0-9])|(2[0-3]))([0-5][0-9])";
        return validateFormat(strFormat, alternte);
    }

    public static boolean validate14ClearedLevel(String alternte) {
        final String strFormat = "([FA]([0-9]{3}))|([SM]([0-9]{4}))";
        return validateFormat(strFormat, alternte);
    }

    public static boolean validate14CrossingLevel1(String alternte) {
        final String strFormat = "([FA]([0-9]{3}))|([SM]([0-9]{4}))";
        return validateFormat(strFormat, alternte);
    }

    public static boolean validate14CrossingLevel2(String alternte) {
        final String strFormat = "^[AB]{1}$";
        return validateFormat(strFormat, alternte);
    }

    //15
    public static boolean validate15Speed(String alternte) {
        final String strFormat = "([NK]([0-9]{4}))|(M([0-9]{3}))";
        return validateFormat(strFormat, alternte);
    }

    public static boolean validate15AltitudeLevel(String alternte) {
        final String strFormat = "([FA]([0-9]{3}))|([SM]([0-9]{4}))";
        return validateFormat(strFormat, alternte);
    }

    public static boolean validateNNNN(String alternte) {
        try {
            final String strFormat = "NNN";
            String strText = alternte;
            if (strText.length() > 0) {
                int check = strText.indexOf(strFormat);
                if (check >= 0) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (Exception ex) {
            return false;
        }
    }

    //16
    public static boolean validate16Destination(String alternte) {
        final String strFormat = "([A-Z]{4})";
        return validateFormat(strFormat, alternte);
    }

    public static boolean validate16TotalEET(String alternte) {
        final String strFormat = "([0-9]{4})";
        return validateFormat(strFormat, alternte);
    }

    public static boolean validate16Alternte(String alternte) {
        final String strFormat = "([A-Z]{4})";
        return validateFormat(strFormat, alternte);
    }

    public static boolean validate162nd(String alternte) {
        final String strFormat = "([A-Z]{4})";
        return validateFormat(strFormat, alternte);
    }

    public static boolean validate16OldDestination(String alternte) {
        final String strFormat = "([A-Z]{4})";
        return validateFormat(strFormat, alternte);
    }
    //18 check value NNN and not null

    //19
    public static boolean validate19Endurance(String alternte) {
        final String strFormat = "^(0[0-9]|1[0-9]|2[0-3])[0-5][0-9]$";
        return validateFormat(strFormat, alternte);
    }

    public static boolean validate19PersonsonBoard(String alternte) {
        final String strFormat = "(TBN)|([0-9]{1,3})";
        return validateFormat(strFormat, alternte);
    }

    public static boolean validate19Number(String alternte) {
        final String strFormat = "[0-9]{2}";
        return validateFormat(strFormat, alternte);
    }

    public static boolean validate19Capacity(String alternte) {
        final String strFormat = "[0-9]{3}";
        return validateFormat(strFormat, alternte);
    }

    //20 check value NNN and not null
    //22  check value NNN and not null
    //GROUP METEO
    public static boolean validateMessageID2(String alternte) {
        final String strFormat = "[A-Z]{2}";
        return validateFormat(strFormat, alternte);
    }

    public static boolean validateMessageID3(String alternte) {
        final String strFormat = "[0-9]{2}";
        return validateFormat(strFormat, alternte);
    }

    public static boolean validateOrig(String alternte) {
        final String strFormat = "[A-Z]{4}";
        return validateFormat(strFormat, alternte);
    }

    public static boolean validateIssued(String alternte) {
        final String strFormat = "^(([0][1-9])|((1|2)[0-9])|([3][01]))(((0|1)[0-9])|(2[0-3]))([0-5][0-9])$";
        return validateFormat(strFormat, alternte);
    }

    public static boolean validateCorr(String alternte) {
        final String strFormat = "CC([0-9]|[A-Z])";
        return validateFormat(strFormat, alternte);
    }

    public static boolean validateLocation(String alternte) {
        final String strFormat = "[A-Z]{4}";
        return validateFormat(strFormat, alternte);
    }
    //GROUP METEO

    public static boolean validateSeqID(String alternte) {
        final String strFormat = "([0-9]|[A-Z]){1,3}";
        return validateFormat(strFormat, alternte);
    }

    public static boolean validateFrom(String alternte) {
        final String strFormat = "^(([0][1-9])|((1|2)[0-9])|([3][01]))(((0|1)[0-9])|(2[0-3]))([0-5][0-9])$";
        return validateFormat(strFormat, alternte);
    }

    public static boolean validateTo(String alternte) {
        final String strFormat = "^(([0][1-9])|((1|2)[0-9])|([3][01]))(((0|1)[0-9])|(2[0-3]))([0-5][0-9])$";
        return validateFormat(strFormat, alternte);
    }

    public static boolean soxanhTextDateDDhhmm(String strFrom, String strTo, int inthh) {
        try {
            boolean check = false;
            boolean checkBool = false;
            int intcheck = 0;
            if (strFrom.length() > 0) {
                if (strTo.length() > 0) {
                    checkBool = validateFrom(strFrom);
                    if (!checkBool) {
                        intcheck = intcheck + 1;
                    }
                    checkBool = validateFrom(strTo);
                    if (!checkBool) {
                        intcheck = intcheck + 1;
                    }
                    if (intcheck > 0) {
                        check = false;
                    } else if (soxanhDateDDhhmm(strFrom, strTo, inthh)) {
                        check = true;
                    } else {
                        check = false;
                    }

                } else {
                    check = false;
                }
            } else {
                check = false;
            }

            return check;
        } catch (Exception ex) {
            // Logger.getLogger(frmAIRMET.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    private static boolean soxanhDateDDhhmm(String strFrom, String strTo, int inthh) {//soxanhTextDateDDhhmm(strFrom_AIR, strTo_AIR, 4)) {//   3 - so phut chenh lech
        try {
            boolean check = false;
            if ((strFrom.trim().length() > 0) & (strTo.trim().length() > 0)) {
                if (Float.valueOf(strFrom.trim()) < Float.valueOf(strTo.trim())) {
                    int dd1 = Integer.valueOf(strTo.substring(0, 2));
                    int hh1 = Integer.valueOf(strTo.substring(2, 4)) + dd1 * 24;
                    int mm1 = Integer.valueOf(strTo.substring(4, 6)) + hh1 * 60;
                    int dd2 = Integer.valueOf(strFrom.substring(0, 2));
                    int hh2 = Integer.valueOf(strFrom.substring(2, 4)) + dd2 * 24;
                    int mm2 = Integer.valueOf(strFrom.substring(4, 6)) + hh2 * 60;
                    int a = mm1 - mm2;
                    if ((a <= inthh * 60) & (a >= 0)) {
//                    if (mm1 <= mm2) {
                        check = true;
                    } else {
                        check = false;
                        JOptionPane.showMessageDialog(null, " To date - From date");
                    }
                } else {
                    check = false;
                    JOptionPane.showMessageDialog(null, "From date < To date");

                }
            }
            return check;
        } catch (Exception ex) {
            //Logger.getLogger(frmAIRMET.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public static boolean validateObserved(String alternte) {
        final String strFormat = "^(([0][1-9])|((1|2)[0-9])|([3][01]))(((0|1)[0-9])|(2[0-3]))([0-5][0-9])$";
        return validateFormat(strFormat, alternte);
    }

    public static boolean validateForecastPeriod1(String alternte) {
        final String strFormat = "(0[1-9]|[12][0-9]|3[01])";
        return validateFormat(strFormat, alternte);
    }

    public static boolean validateForecastPeriod2(String alternte) {
        final String strFormat = "([01][1-9])|([2][0-3])";
        return validateFormat(strFormat, alternte);
    }

    //SYSNOP
    public static boolean validateWMOorShip(String alternte) {
        final String strFormat = "[0-9]{5}";
        return validateFormat(strFormat, alternte);
    }

    public static boolean validateDate(String alternte) {
        final String strFormat = "^(([0][1-9])|([12][0-9])|([3][01]))(([0-1][0-9])|(2[0-3]))$";
        return validateFormat(strFormat, alternte);
    }

    public static boolean validateLatitude(String alternte) {
        final String strFormat = "(99)([0-9]{3})";
        return validateFormat(strFormat, alternte);
    }

    public static boolean validateQuadrant(String alternte) {
        final String strFormat = "(1|3|5|7)([0-9]{4})";
        return validateFormat(strFormat, alternte);
    }

    public static boolean validatePrecipitation(String alternte) {
        final String strFormat = "(([0-4])([1-7]))(([0-9])|(/))(([0-9]{2})|(/{2}))";
        return validateFormat(strFormat, alternte);
    }

    public static boolean validateCloudCover(String alternte) {
        final String strFormat = "((/)|([0-9]))([0-9]{4})";
        return validateFormat(strFormat, alternte);
    }

    public static boolean validateOptional(String alternte) {
        final String strFormat = "(00)([0-9]{3})";
        return validateFormat(strFormat, alternte);
    }

    public static boolean validateTemperature(String alternte) {
        final String strFormat = "(1)(0|1)([0-9]{3})";
        return validateFormat(strFormat, alternte);
    }

    public static boolean validateDewpoint(String alternte) {
        final String strFormat = "(2)(0|1|9)([0-9]{3})";
        return validateFormat(strFormat, alternte);
    }

    public static boolean validateStationPressure(String alternte) {
        final String strFormat = "(3)([0-9]{4})";
        return validateFormat(strFormat, alternte);
    }

    public static boolean validateLevelPressure(String alternte) {
        final String strFormat = "(4)([0-9]{4})";
        return validateFormat(strFormat, alternte);
    }

    public static boolean validatePressureTendency(String alternte) {
        final String strFormat = "(5)([0-8])([0-9]{3})";
        return validateFormat(strFormat, alternte);
    }

    public static boolean validateLiquidPrecipitation(String alternte) {
        final String strFormat = "(6)([0-9]{3})(([0-9])|(/))";
        return validateFormat(strFormat, alternte);
    }

    public static boolean validateWeather(String alternte) {
        final String strFormat = "(7)([0-9]{4})";
        return validateFormat(strFormat, alternte);
    }

    public static boolean validateCloudType(String alternte) {
        final String strFormat = "(8)([0-9])(([0-9])|(/))(([0-9])|(/))(([0-9])|(/))";
        return validateFormat(strFormat, alternte);
    }

    public static boolean validateTime(String alternte) {
        final String strFormat = "(9)((((([0-1])([0-9]))|((2)([0-4])))([0-5])([0-9]))|(2400))";
        return validateFormat(strFormat, alternte);
    }

    public static boolean validateDirection(String alternte) {
        final String strFormat = "(222)([0-9]{2})";
        return validateFormat(strFormat, alternte);
    }

    public static boolean validateSurfaceTemperature(String alternte) {
        final String strFormat = "(0)([0-1])([0-9]{3})";
        return validateFormat(strFormat, alternte);
    }

    public static boolean validateWaveHeightsIn05m(String alternte) {
        final String strFormat = "(1)([0-9]{4})";
        return validateFormat(strFormat, alternte);
    }

    public static boolean validateWavePeriodAndHeights(String alternte) {
        final String strFormat = "(2)([0-9]{4})";
        return validateFormat(strFormat, alternte);
    }

    public static boolean validateDirectionOfSwells(String alternte) {
        final String strFormat = "(3)([0-9]{4})";
        return validateFormat(strFormat, alternte);
    }

    public static boolean validatePeriodAndDirection1(String alternte) {
        final String strFormat = "(4)([0-9]{4})";
        return validateFormat(strFormat, alternte);
    }

    public static boolean validatePeriodAndDirection2(String alternte) {
        final String strFormat = "(5)([0-9]{4})";
        return validateFormat(strFormat, alternte);
    }

    public static boolean validateIceAccretion(String alternte) {
        final String strFormat = "(6)([0-9]{4})";
        return validateFormat(strFormat, alternte);
    }

    public static boolean validateWaveHeightsTo01m(String alternte) {
        final String strFormat = "(70)([0-9]{3})";
        return validateFormat(strFormat, alternte);
    }

    public static boolean validateWetBulbTemperature(String alternte) {
        final String strFormat = "(8)([0-9]{4})";
        return validateFormat(strFormat, alternte);
    }

    public static boolean validateRegionally(String alternte) {
        final String strFormat = "(0)([0-9]{4})";
        return validateFormat(strFormat, alternte);
    }

    public static boolean validateMaximumTemperature(String alternte) {
        final String strFormat = "(1)([0-1])([0-9]{3})";
        return validateFormat(strFormat, alternte);
    }

    public static boolean validateMinimumTemperature(String alternte) {
        final String strFormat = "(2)([0-1])([0-9]{3})";
        return validateFormat(strFormat, alternte);
    }

    public static boolean validateRegionally2(String alternte) {
        final String strFormat = "(3)([0-9]{4})";
        return validateFormat(strFormat, alternte);
    }

    public static boolean validateSnowDepth(String alternte) {
        final String strFormat = "(4)([0-9]{4})";
        return validateFormat(strFormat, alternte);
    }

    public static boolean validateAdditional(String alternte) {
        final String strFormat = "(5)([0-9]{4})";
        return validateFormat(strFormat, alternte);
    }

    public static boolean validateAdditional2(String alternte) {
        final String strFormat = "([0-9]{5})";
        return validateFormat(strFormat, alternte);
    }

    public static boolean validateLiquidPrecipitation3(String alternte) {
        final String strFormat = "(6)([0-9]{3})(([1-9])|(/))";
        return validateFormat(strFormat, alternte);
    }

    public static boolean validate24HourPrecipitation(String alternte) {
        final String strFormat = "(7)([0-9]{4})";
        return validateFormat(strFormat, alternte);
    }

    public static boolean validateCloudLayerData(String alternte) {
        final String strFormat = "(8)([0-9])(([0-9])|(/))([0-9]{2})";
        return validateFormat(strFormat, alternte);
    }

    public static boolean validateSupplementary(String alternte) {
        final String strFormat = "(9)([0-9]{4})";
        return validateFormat(strFormat, alternte);
    }

    //GROUP NOTAM
    //NOTAM
    public static boolean validateNOTAMID1(String alternte) {
        final String strFormat = "[A-Z]";
        return validateFormat(strFormat, alternte);
    }

    public static boolean validateNOTAMID2(String alternte) {
        final String strFormat = "[0-9]{4}";
        return validateFormat(strFormat, alternte);
    }

    public static boolean validateNOTAMID3(String alternte) {
        final String strFormat = "([0-9]{2})";
        return validateFormat(strFormat, alternte);
    }

    public static boolean validateNOTAMFIR(String alternte) {
        final String strFormat = "([A-Z]{4)";
        return validateFormat(strFormat, alternte);
    }

    public static boolean validateNOTAMCode(String alternte) {
        final String strFormat = "([A-Z]{4})";
        return validateFormat(strFormat, alternte);
    }

    public static boolean validateNOTAMTfc(String alternte) {
        final String strFormat = "([IVK]{1,2})";
        return validateFormat(strFormat, alternte);
    }

    public static boolean validateNOTAMPurpose(String alternte) {
        final String strFormat = "([NBOMK]{1,3})";
        return validateFormat(strFormat, alternte);
    }

    public static boolean validateNOTAMScope(String alternte) {
        final String strFormat = "([AEWK]{1,2})";
        return validateFormat(strFormat, alternte);
    }

    public static boolean validateNOTAMLowerAndUpper(String alternte) {
        final String strFormat = "([0-9]{3})";
        return validateFormat(strFormat, alternte);
    }

    public static boolean validateNOTAMCoordinatesRadius(String alternte) {
        final String strFormat = "([A-Z0-9\\s]{0,14})";
        return validateFormat(strFormat, alternte);
    }

    public static boolean validateNOTAMA(String alternte) {
        final String strFormat = "(AD)|(FIR)|([A-Z]{4})|(([A-Z]{4})(\\s([A-Z]{4})){1,6})";
        return validateFormat(strFormat, alternte);
    }

    public static boolean validateNOTAMB(String alternte) {
        final String strFormat = "(([0-9]{2})((0[1-9])|([1][012]))(([0][1-9])|([12][0-9])|([3][01]))(([01][0-9])|(2[0-3]))([0-5][0-9]))";
        return validateFormat(strFormat, alternte);
    }

    public static boolean validateNOTAMC(String alternte) {
        final String strFormat = "(([0-9]{2})((0[1-9])|([1][012]))(([0][1-9])|([12][0-9])|([3][01]))(([01][0-9])|(2[0-3]))([0-5][0-9]))((\\s{0,2})(((EST)|(PERM)){0,1}))";
        return validateFormat(strFormat, alternte);
    }

    public static boolean validateNOTAMF(String alternte) {
        final String strFormat = "(SFC)|(GND)|(([0-9]{5})(FT)(\\s(AGL)){0,1})|(([0-9]{5})(FT)(\\s(AMSL)){0,1})|(([0-9]{5})(M)(\\s(AGL)){0,1})|(([0-9]{5})(M)(\\s(AMSL)){0,1})|((FL)([0-9]{3}))";
        return validateFormat(strFormat, alternte);
    }

    public static boolean validateNOTAMG(String alternte) {
        final String strFormat = "(UNL)|(([0-9]{5})(FT)(\\s(AGL)){0,1})|(([0-9]{5})(FT)(\\s(AMSL)){0,1})|(([0-9]{5})(M)(\\s(AGL)){0,1})|(([0-9]{5})(M)(\\s(AMSL)){0,1})|((FL)([0-9]{3}))";
        return validateFormat(strFormat, alternte);
    }

    //SNOWTAM
    public static boolean validateSNOWTAMState(String alternte) {
        final String strFormat = "[A-Z]{2}";
        return validateFormat(strFormat, alternte);
    }

    public static boolean validateSNOWTAMSerialNo(String alternte) {
        final String strFormat = "([0-9]){4}";
        return validateFormat(strFormat, alternte);
    }

    public static boolean validateSNOWTAMAD(String alternte) {
        final String strFormat = "[A-Z]{4}";
        return validateFormat(strFormat, alternte);
    }

    public static boolean validateSNOWTAMObservation(String alternte) {
        final String strFormat = "^((0[1-9])|([1][0-2]))(([0][1-9])|((1|2)[0-9])|([3][01]))(((0|1)[0-9])|(2[0-3]))([0-5][0-9])$";
        return validateFormat(strFormat, alternte);
    }

    public static boolean validateSNOWTAMOptGroup(String alternte) {
        final String strFormat = "(((RR)|(CC)|(AA))([A-Z0-9]))|(RTD)|(COR)|(AMD)";
        return validateFormat(strFormat, alternte);
    }

    public static boolean validateSNOWTAMC(String alternte) {
        final String strFormat = "(([0-9]{2}[LRC])|([0-9]{2}))|([0-9]{2}/[0-9]{2})";
        return validateFormat(strFormat, alternte);
    }

    public static boolean validateSNOWTAMD(String alternte) {
        final String strFormat = "[0-9]{0,4}";
        return validateFormat(strFormat, alternte);
    }

    public static boolean validateSNOWTAME(String alternte) {
        final String strFormat = "([0-9]{0,4}[LR])|([0-9]{0,4})";
        return validateFormat(strFormat, alternte);
    }

    public static boolean validateSNOWTAMF(String alternte) {
        final String strFormat = "((NIL)|([0-9]{1,3}))/((NIL)|([0-9]{1,3}))/((NIL)|([0-9]{1,3}))(.*)";
        return validateFormat(strFormat, alternte);
    }

    public static boolean validateSNOWTAMG(String alternte) {
        final String strFormat = "([A-Z0-9]{1,2})/([A-Z0-9]{1,2})/([A-Z0-9]{1,2})(.*)";
        return validateFormat(strFormat, alternte);
    }

    public static boolean validateSNOWTAMH(String alternte) {
        final String strFormat = "(((BRD)|(GRT)|(MUM)|(RFT)|(SFH)|(SFL)|(SKH)|(SKL)|(TAP)){0,1})\\s{0,2}([0-9]{1,2}/[0-9]{1,2}/[0-9]{1,2}\\s{0,2})(((BRD)|(GRT)|(MUM)|(RFT)|(SFH)|(SFL)|(SKH)|(SKL)|(TAP)){0,1})";
        return validateFormat(strFormat, alternte);
    }

    public static boolean validateSNOWTAMJ(String alternte) {
        final String strFormat = "[0-9]{1,2}/[0-9]{1,2}\\s{0,2}((L|R|(LR)|(BOTH)))";
        return validateFormat(strFormat, alternte);
    }

    public static boolean validateSNOWTAMK(String alternte) {
        final String strFormat = "((YES\\s{1,2}L)|(YES\\s{1,2}R)|(YESL)|(YESR)|(YES\\s{0,2}LR)){0,1}";
        return validateFormat(strFormat, alternte);
    }

    public static boolean validateSNOWTAML(String alternte) {
        final String strFormat = "(TOTAL)|([0-9]{1,4})|([0-9]{1,4}/[0-9]{1,4})";
        return validateFormat(strFormat, alternte);
    }

    public static boolean validateSNOWTAMM(String alternte) {
        final String strFormat = "^(([01][0-9])|(2[0-3]))([0-5][0-9])$";
        return validateFormat(strFormat, alternte);
    }

    public static boolean validateSNOWTAMN(String alternte) {
        final String strFormat = "(NO)|(.*)";
        return validateFormat(strFormat, alternte);
    }

    public static boolean validateSNOWTAMP(String alternte) {
        final String strFormat = "(YES)\\s{0,2}[0-9]{1,3}";
        return validateFormat(strFormat, alternte);
    }

    public static boolean validateSNOWTAMR(String alternte) {
        final String strFormat = "(NO)|(NIL)|([0-9A-Z]{1,108})";
        return validateFormat(strFormat, alternte);
    }

    public static boolean validateSNOWTAMS(String alternte) {
        final String strFormat = "^((0[1-9])|([1][0-2]))(([0][1-9])|((1|2)[0-9])|([3][01]))(((0|1)[0-9])|(2[0-3]))([0-5][0-9])$";
        return validateFormat(strFormat, alternte);
    }

    //ASHTAM
    public static boolean validateASHTAMState(String alternte) {
        final String strFormat = "[A-Z]{2}";
        return validateFormat(strFormat, alternte);
    }

    public static boolean validateASHTAMSerialNo(String alternte) {
        final String strFormat = "[0-9]{4}";
        return validateFormat(strFormat, alternte);
    }

    public static boolean validateASHTAMLocation(String alternte) {
        final String strFormat = "[A-Z]{4}";
        return validateFormat(strFormat, alternte);
    }

    public static boolean validateASHTAMIssued(String alternte) {
        final String strFormat = "^((0[1-9])|([1][0-2]))(([0][1-9])|((1|2)[0-9])|([3][01]))(((0|1)[0-9])|(2[0-3]))([0-5][0-9])$";
        return validateFormat(strFormat, alternte);
    }

    public static boolean validateASHTAMOpGroup(String alternte) {
        final String strFormat = "(((RR)|(CC)|(AA))([A-Z0-9]))|(RTD)|(COR)|(AMD)";
        return validateFormat(strFormat, alternte);
    }

    public static boolean validateASHTAMA(String alternte) {
        final String strFormat = "[A-Z0-9]{1,29}";
        return validateFormat(strFormat, alternte);
    }

    public static boolean validateASHTAMB(String alternte) {
        final String strFormat = "^((0[1-9])|([1][0-2]))(([0][1-9])|((1|2)[0-9])|([3][01]))(((0|1)[0-9])|(2[0-3]))([0-5][0-9])$";
        return validateFormat(strFormat, alternte);
    }

}
