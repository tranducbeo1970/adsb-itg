/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.amhs.service;

import com.attech.asd.amhs.database.dao.Common;
import com.attech.asd.amhs.database.entities.Flightplan;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Saitama
 */
public class DailyFPL {

    private final static MLogger logger = MLogger.getLogger(DailyFPL.class);
    private final static DateFormat fplDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
    // private final static DateFormat fplDisplayFormat = new SimpleDateFormat("yyyy-MM-dd");
    private final static DateFormat fplDisplayFormat = new SimpleDateFormat("yyMMdd");

    private final Common cmmRegux = new Common();

    public static List<Flightplan> getDailyFlightPlanList(String message) throws Exception {

	
        // Preprocessing
        String content = message.replace("\r", "").trim();
        while (content.contains("  ")) {
            content = content.replace("  ", " ");
        }

        // DailyFlightPlan entity;
        //String[] listArr = content.replace("\r", "").split("\n");
        String[] listArr = content.split("\n");
        if (listArr == null || listArr.length <= 1) {
            return null;
        }

        // Get date
        String dateOfFlight = null;
        String currentLine;
        int currentIndex = 0;
        while (currentIndex < listArr.length) {
            currentLine = listArr[currentIndex++];
            if (!currentLine.contains("FPL ON:")) {
                continue;
            }

            Date currentDate = getDate(currentLine);
            if (currentDate == null) {
                return null;
            }
            dateOfFlight = fplDisplayFormat.format(currentDate);
//            inbox.setDateOfFlight(dateOfFlight);
            break;
        }

        if (dateOfFlight == null || dateOfFlight.isEmpty()) {
            return null;
        }

        List<Flightplan> listDeps = new ArrayList<>();
        // Parsing row
        while (currentIndex < listArr.length) {
            currentLine = listArr[currentIndex++].trim();
            if (isFullInfoLine(currentLine)) {
                Flightplan dailyFlightItem = parseFull(currentLine);
                if (dailyFlightItem == null) {
                    continue;
                }
                dailyFlightItem.setDof(dateOfFlight);
                listDeps.add(dailyFlightItem);
                continue;
            }

            if (isInfoLine(currentLine)) {

                Flightplan dailyFlightItem = parse(currentLine);
                if (dailyFlightItem == null) {
                    continue;
                }
                dailyFlightItem.setDof(dateOfFlight);
                listDeps.add(dailyFlightItem);
                continue;
            }

            if (isSeperateLine(currentLine)) {
                continue;
            }

            // It's description of last row
            if (listDeps.isEmpty()) {
                continue;
            }

            // listDeps.get(listDeps.size() - 1).setRemark(currentLine);
            listDeps.get(listDeps.size() - 1).appendRemark(currentLine);
        }
        return listDeps;
    }

//    public static Flightplan getFpl(String content) {
//        FplEnt fplent = new FplEnt(content);
//        Flightplan fpl = new Flightplan();
//        fpl.setCallSign(fplent.getCallSign());
//        fpl.setCraft(fplent.getAircraftType());
//        fpl.setDep(fplent.getDeparture());
//        fpl.setDest(fpl.getDest());
//        fpl.setDof(fplent.getDof());
//        fpl.setEet(fplent.getTotalEET());
//        // fpl.setEta(fplent.get);
//        fpl.setEtd(fplent.getDepartureTime());
//        // fpl.setFplText(content);
//        fpl.setReg(fplent.getReg());
//        return fpl;
//    }

    private static boolean isFullInfoLine(String line) {
        final String pattern = "^\\d+\\s\\w+\\s\\w+\\s\\w+\\s\\w+\\s\\d{4}\\s\\d{4,}\\s*.*$";
        return line.matches(pattern);
    }

    private static boolean isInfoLine(String line) {
        final String pattern = "^\\d+\\s\\w+\\s\\w+\\s\\w+\\s\\w+\\s\\d{4}\\s*.*";
        return line.matches(pattern);
    }

    private static boolean isSeperateLine(String line) {
        return line.contains("----");
    }

    private static String getDescriptionOfFullInfoLine(String line) {
        String[] splits = line.split("^\\d+\\s\\w+\\s\\w+\\s\\w+\\s\\w+\\s\\d{4}\\s\\d{4,}\\s");
        return (splits.length <= 1 ? "" : splits[1]);
    }

    private static String getDescriptionOfInfoLine(String line) {
        String[] splits = line.split("^\\d+\\s\\w+\\s\\w+\\s\\w+\\s\\w+\\s\\d{4}\\s");
        return (splits.length <= 1 ? "" : splits[1]);
    }

    private static Flightplan parseFull(String line) {
        String[] splits = line.split(" ");
        Flightplan flightList = new Flightplan();
        flightList.setCraft(splits[1]);
        flightList.setCallSign(splits[2]);
        flightList.setDep(splits[3]);
        flightList.setDest(splits[4]);
        flightList.setEtd(splits[5]);
        flightList.setEta(getEta(splits[6]));
        flightList.setRemark(getDescriptionOfFullInfoLine(line));
        return flightList;
    }

    private static String getEta(String str) {
        String out = "";
        if (str.length() >= 6) {
            if (str.substring(0, 6).matches("^(([0][1-9])|((1|2)[0-9])|([3][01]))(([01][0-9])|(2[0-3]))([0-5][0-9])$")) {
                out = str.substring(0, 6);
            } else if (str.substring(0, 4).matches("^(([01][0-9])|(2[0-3]))([0-5][0-9])$")) {
                out = str.substring(0, 4);
            }
        } else {
            if (str.length() >= 4) {
                if (str.substring(0, 4).matches("^(([01][0-9])|(2[0-3]))([0-5][0-9])$")) {
                    out = str.substring(0, 4);
                }
            }
        }
        return out;
    }

    private static Flightplan parse(String line) {
        String[] splits = line.split(" ");
        Flightplan flightList = new Flightplan();
        flightList.setCraft(splits[1]);
        flightList.setCallSign(splits[2]);
        flightList.setDep(splits[3]);
        flightList.setDest(splits[4]);
        flightList.setEtd(splits[5]);
        flightList.setRemark(getDescriptionOfInfoLine(line));
        return flightList;
    }

    private static Date getDate(String line) {
        int begin = line.indexOf(":") + 1;
        int end = line.lastIndexOf(":");
        if (begin < 1 || begin >= line.length()) {
            return null;
        }
        end = end > begin ? end : line.length();

        if (end <= begin) {
            return null;
        }

        String date = line.substring(begin, end);

        try {
            return fplDateFormat.parse(date);
        } catch (ParseException ex) {
            logger.error("Cannot parse date %s", date);
            return null;
        }
    }

//    public Flightplan processFplDay(String row) {
//
//        Flightplan entity = null;
//        String[] listArr = row.split(" ");
//
//        if (listArr == null || listArr.length <= 1) {
//            return null;
//        }
//
//        int leng = listArr.length;
//
//        List<String> list = new ArrayList<>();
//        for (int i = 0; i < leng; i++) {
//            String a = checkNullStr(listArr[i]);
//            if (a.length() > 0) {
//                list.add(a);
//            }
//
//        }
//        leng = list.size();
//        if (leng > 0) {
//            if (convertString2Int(list.get(0)) == 0) {
//                return null;
//            }
//        }
//        if (leng > 1) {
//            entity = new Flightplan();
//            entity.setCraft(checkNullStr(list.get(1)));
//        }
//        if (leng > 2) {
//            String value = checkNullStr(list.get(2));
//            if (cmmRegux.checkStringWithFormat(value, "([A-Z]|[0-9]){1,7}")) {
//                entity.setCallSign(value);
//            }
//        }
//        if (leng > 3) {
//            String value = checkNullStr(list.get(3));
//            if (cmmRegux.checkStringWithFormat(value, "[A-Z]{4}")) {
//                entity.setDep(value);
//            }
//        }
//        if (leng > 4) {
//            String value = checkNullStr(list.get(4));
//            if (cmmRegux.checkStringWithFormat(value, "([A-Z]|[0-9]){1,7}")) {
//                entity.setDest(value);
//            }
//        }
//        if (leng > 5) {
//            String value = checkNullStr(list.get(5));
//            if (cmmRegux.checkStringWithDate(value, 4)) {
//                entity.setEta(value);
//            }
//        }
//        if (leng > 6) {
//            String value = checkNullStr(list.get(6));
//            if (cmmRegux.checkStringWithDate(value, 4)) {
//                entity.setEtd(value);
//                if (leng > 7) {
//                    entity.setRemark(getRemarks(list, 7));
//                }
//            } else if (cmmRegux.checkStringWithDate(value, 6)) {
//                entity.setEtd(value);
//                if (leng > 7) {
//                    entity.setRemark(getRemarks(list, 7));
//                }
//            } else {
//                entity.setRemark("" + getRemarks(list, 6));
//            }
//
//        }
//        return entity;
//    }

//    private String getRemarks(List list, int j) {
//        String out = "";
//        if (list.size() > j) {
//            for (int i = j; i < list.size(); i++) {
//                out = out + " " + checkNullStr(list.get(i));
//            }
//        }
//        return out.trim();
//    }
//
//    private int convertString2Int(Object strinput) {
//        if (strinput == null) {
//            return 0;
//        } else {
//            return Integer.parseInt(strinput.toString());
//        }
//    }
//
//    private String checkNullStr(Object strinput) {
//        if (strinput == null) {
//            return "";
//        }
//        if (strinput.toString().isEmpty()) {
//            return "";
//        }
//        return strinput.toString().trim();
//
//    }

//    public void setFont(JTextField jtext) {
//        try {
//            DocumentFilter filter = new UppercaseDocumentFilter();
////            jtext.setFont(new Font("Courier New", Font.BOLD, 11));
//            ((AbstractDocument) jtext.getDocument()).setDocumentFilter(filter);
//        } catch (Exception ex) {
//            System.err.println("setFont MS: " + ex);
//        }
//    }

//    public List<Flightplan> getDep(String content) throws Exception {
//        List<Flightplan> listDeps = new ArrayList<>();
//        Flightplan entity;
//        String[] listArr = content.replace("\r", "").split("\n");
//        if (listArr == null | listArr.length <= 1) {
//            return null;
//        }
//        for (int j = 0; j < listArr.length; j++) {
//            String row = listArr[j];
//            entity = processFplDay(row);
//            if (entity != null) {
//                listDeps.add(entity);
//            } else {
//                int i = listDeps.size();
//                if (i > 0) {
//                    Object remark = listDeps.get(i - 1).getRemark();
//                    if (remark != null) {
//                        remark = remark.toString();
//                    } else {
//                        remark = "";
//                    }
//                    listDeps.get(i - 1).setRemark(remark + row);
//                }
//            }
//        }
//        return listDeps;
//    }

//    public List<Flightplan> getArr(String content) throws Exception {
//        List<Flightplan> listArrs = new ArrayList<>();
//        Flightplan entity;
//        String[] listArr = content.split("\n");
//        if (listArr == null | listArr.length <= 1) {
//            return null;
//        }
//        for (String row : listArr) {
//            entity = processFplDay(row);
//            if (entity != null) {
//                listArrs.add(entity);
//            } else {
//                int i = listArrs.size();
//                if (i > 0) {
//                    Object remark = listArrs.get(i - 1).getRemark();
//                    if (remark != null) {
//                        remark = remark.toString();
//                    } else {
//                        remark = "";
//                    }
////                    entity= listArrs.get(i-1);
//                    listArrs.get(i - 1).setRemark(remark + row);
//                }
//            }
//        }
//        return listArrs;
//    }

//    public static String getDOFField(String dof) {
//        if (dof == null || dof.isEmpty() || dof.equalsIgnoreCase("0")) {
//            return "0";
//        }
//
//        return String.format("DOF/%s", dof);
//    }
//
//    public static String getSSR(String dof) {
//        if (dof == null || dof.isEmpty() || dof.equalsIgnoreCase("0")) {
//            return "";
//        }
//
//        return String.format("/%s", dof);
//    }
}
