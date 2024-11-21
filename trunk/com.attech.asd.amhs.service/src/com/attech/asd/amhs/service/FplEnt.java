/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.amhs.service;


/**
 *
 * @author Saitama
 */
public class FplEnt {

    //7
    private String callSign;
    private String ssr;

    //8
    // private FlightRule flightRules;
    // private FlightType typeoFlight;
    private String flightRules;
    private String typeoFlight;

    //9
    private String aircraftNumber;
    private String aircraftType;
    // private WakeTurbulence wakeTurbulence;
    private String wakeTurbulence;

    //10
    private String equipment1;
    private String equipment2;

    //13
    private String departure;
    private String departureTime;

    //15
    private SpeedUnit speedUnit;
    private String speed;
    private LevelUnit altitudeUnit;
    private String altitude;
    private String route;

    //16
    private String destination;
    private String totalEET;
    private String altAerodrome;
    private String secondAltAerodrome;

    //18
    private String otherInformation;
    private String dof;
    private String reg;

    private String suplementaryInfomation;

    // check 19
    private boolean activateSupplementaryInformation;

    //19
    private String endurance;
    private String personsOnBoard;
    private boolean emergencyRadioU;//HF;
    private boolean emergencyRadioV;//HF;
    private boolean emergencyRadioE;//LT;
    private boolean equipmentS;//Survical;
    private boolean equipmentP;//POLAR;
    private boolean equipmentD;//DESERT;
    private boolean equipmentM;//MARITIME;
    private boolean equipmentJ;//JUNGLE;
    private boolean jacketsJ;
    private boolean jacketsL;//LIGHT;
    private boolean jacketsF;//FLOURES;
    private boolean jacketsU;//UHF;
    private boolean jacketsV;//VHF;
    private boolean dinghies;
    private String dinghiesNumber;
    private String dinghiesCapacity;
    private boolean dinghiesCover;
    private String dinghiesColor;
    private String aircraftColour;
    private boolean isRemarks;
    private String remarks;
    private String pilotInCommand;

    public FplEnt() {
    }

    public FplEnt(String message) {
        this.parse(message);
    }
    
    public boolean validate() {

        if (Validator.isNullOrEmpty(callSign)) {
            return false;
        }

        if (!Validator.validateLength(departure, 4, false)
                || !Validator.validateLength(destination, 4, false)
                || !Validator.validateLength(dof, 6, false)) {
            return false;
        }

        return true;
    }


    private void parse(String content) {
        while (content.contains("  ")) {
            content = content.replace("  ", " ");
        }
        
        String[] splits = content.split("\r\n");
        if (splits.length < 5) {
            return;
        }

        // first line: FPL-AXM529-IS
        parseFirstLine(splits[0].trim());
        parseSecondLine(splits[1].trim());
        parseThirdLine(splits[2].trim());
        parseFouthLine(splits[3].trim());
        int currentIndex = 3;
        while (currentIndex < splits.length - 1) {
            String currentLine = splits[++currentIndex];
            if (currentLine.trim().startsWith("-")) {
                break;
            }
            this.route += (" " + currentLine);
        }

        // Parse destination
        if (currentIndex == splits.length) {
            return;
        }
        parseDest(splits[currentIndex++].trim());

        // Other Info
        if (currentIndex == splits.length) {
            return;
        }
        this.otherInformation = splits[currentIndex].replace("-", "");
        while (currentIndex < splits.length - 1) {
            String currentLine = splits[++currentIndex].trim();
            if (currentLine.startsWith("-")) {
                break;
            }
            this.otherInformation += (" " + currentLine);
        }
        parseOtherInfo(this.otherInformation);

        // Parse suplement
//        if (currentIndex == splits.length - 1) {
//            return;
//        }
//        parseFirstSuplement(splits[currentIndex++]);
        String delimiter = "";
        this.suplementaryInfomation = "";
        while (currentIndex <= splits.length - 1) {
            this.suplementaryInfomation += (delimiter + splits[currentIndex++]);
            delimiter = "\r\n";
        }

    }

    private void parseFirstLine(String line) {
        String[] splits = line.trim().split("-");
        if (splits.length > 1) {
            String[] subSplits = splits[1].split("/");
            if (subSplits.length > 0) {
                String callSign = subSplits[0];
                if (callSign.matches("([A-Z]|[0-9]){1,8}")) {
                    this.callSign = callSign;
                }
                if (subSplits.length > 1) {
                    String ssr = subSplits[1];
                    if (ssr.matches("([A])([0-9]{4})")) {
                        this.ssr = ssr;
                    }
                }
            }
//            this.callSign = splits[1];
        }

        if (splits.length <= 2 || splits[2].isEmpty()) {
            return;
        }

        String flightRule = String.valueOf(splits[2].charAt(0));
        // this.flightRules = FlightRule.valueOf(flightRule);
        this.flightRules = flightRule;

        if (splits[2].length() < 2) {
            return;
        }
        String flightType = String.valueOf(splits[2].charAt(1));
        // this.typeoFlight = FlightType.valueOf(flightType);
        this.typeoFlight = flightType;
    }

    private void parseSecondLine(String line) {
        String[] splits = line.split("-");
        if (splits.length < 3) {
            return;
        }

        // Get aircraft
        String[] subSplits = splits[1].split("/");
        if (subSplits.length > 0) {
            String aircraft = subSplits[0];
            if (aircraft.matches("^[A-Z]+.*$")) {
                this.aircraftType = aircraft;
            }
            if (aircraft.matches("^\\d{1,2}[A-Z]+.*$")) {
                this.aircraftType = aircraft.split("^\\d{1,2}")[1];
                this.aircraftNumber = aircraft.split("[A-Z]+.*$")[0];
            }
        }

        if (subSplits.length > 1) {
            // this.wakeTurbulence = WakeTurbulence.valueOf(subSplits[1]);
            this.wakeTurbulence = subSplits[1];
        }

        // Get Equip
        subSplits = splits[2].split("/");
        if (subSplits.length > 0) {
            this.equipment1 = subSplits[0];
        }

        if (subSplits.length > 1) {
            this.equipment2 = subSplits[1];
        }

    }

    private void parseThirdLine(String line) {
        line = line.trim().replace("-", "");
        if (!line.matches("^[A-Z]{4}[0-9]{4}$")) {
            return;
        }

        this.departure = line.split("[0-9]{4}$")[0];
        this.departureTime = line.split("^[A-Z]{4}")[1];
    }

    private void parseFouthLine(String line) {
        line = line.replace("-", "");
        String[] splits = line.split(" ");
        int currentIndex = 0;
        if (splits.length > 0 && splits[0].matches("^[KNM]{1}[0-9]{3,4}([FSAM]{1}[0-9]{3,4}|VFR)$")) {
            String first = splits[0].split("([FSAM]{1}[0-9]{3,4}|VFR)$")[0];
            // this.speedUnit = SpeedUnit.valueOf(first.split("[0-9]{3,4}$")[0]);
            // this.speed = first.split("^[KNM]{1}")[1];
            this.speed = first;

            String last = splits[0].split("^[KNM]{1}[0-9]{3,4}")[1];
            // if (last.equals("VFR")) {
            //    this.altitude = "VFR";
            //    this.altitudeUnit = null;
            // } else {
            //     this.altitudeUnit = LevelUnit.valueOf(last.split("[0-9]{3,4}$")[0]);
            //     this.altitude = last.split("[FSAM]{1}")[1];
            // }
            this.altitude = last;
            currentIndex++;
        }

        this.route = "";
        String seperate = "";
        for (int i = currentIndex; i < splits.length; i++) {
            this.route += (seperate + splits[i]);
            seperate = " ";
        }
    }

    private void parseDest(String line) {
        line = line.replace("-", "");
        String[] splits = line.split(" ");
        if (splits.length == 0) {
            return;
        }

        if (splits[0].matches("^[A-Z]{4}[0-9]{4}$")) {
            this.destination = splits[0].split("[0-9]{4}$")[0];
            this.totalEET = splits[0].split("^[A-Z]{4}")[1];
        }

        if (splits.length > 1) {
            this.altAerodrome = splits[1];
        }

        if (splits.length > 2) {
            this.secondAltAerodrome = splits[2];
        }
    }

    private void parseOtherInfo(String line) {
        line = line.trim();
        String[] splits = line.split(" ");
        for (String str : splits) {
            if (str.isEmpty()) {
                continue;
            }
            
            if (str.startsWith("DOF/")) {
                str = str.replace("DOF/", "");
                this.dof = str.length() > 6 ? str.substring(0, 6) : str;
                continue;
//                if (str.length() >= 6) {
//                    str = str.substring(0, 6);
//                    String strs = "^([0-9]{2})((0[1-9])|([1][0-2]))(([0][1-9])|((1|2)[0-9])|([3][01]))$";// yyMMdd ^(([0][1-9])|((1|2)[0-9])|([3][01]))(((0|1)[0-9])|(2[0-3]))([0-5][0-9])$";
//                    if (str.matches(strs)) {
//                        this.dateOfFlight = str;
//                    }
//                    continue;
//                }
            }

            if (str.startsWith("REG/")) {
                this.reg = str.replace("REG/", "");
            }
        }
    }

    private void parseFirstSuplement(String line) {

        this.suplementaryInfomation = line;

        String[] splits = line.split(" ");
        for (String str : splits) {
            if (str.isEmpty()) {
                continue;
            }

            if (str.startsWith("-E/")) {
                this.endurance = str.replace("-E/", "");
                continue;
            }

            if (str.startsWith("P/")) {
                this.personsOnBoard = str.replace("P/", "");
                continue;
            }

            if (str.startsWith("R/")) {
                this.emergencyRadioU = str.contains("U");
                this.emergencyRadioV = str.contains("V");
                this.emergencyRadioE = str.contains("E");
                continue;
            }

            if (str.startsWith("S/")) {
                this.equipmentS = true;
                this.equipmentP = str.contains("P");
                this.equipmentD = str.contains("D");
                this.equipmentM = str.contains("M");
                this.equipmentJ = str.contains("J");
                continue;
            }

            if (str.startsWith("J/")) {
                this.jacketsJ = true;
                this.jacketsL = str.contains("L");
                this.jacketsF = str.contains("F");
                this.jacketsU = str.contains("U");;//UHF;
                this.jacketsV = str.contains("V");;//VHF;
                continue;
            }

            if (str.startsWith("D/")) {
                this.dinghies = true;
                this.dinghiesNumber = str.replace("D/", "");
                int begin = line.indexOf(str) + str.length();
                int end = line.indexOf(" C ");
                this.dinghiesCapacity = end > begin ? line.substring(begin, end) : line.substring(begin);
                continue;
            }

            if (str.equalsIgnoreCase("C")) {
                this.dinghiesCover = true;
                this.dinghiesColor = line.substring(line.indexOf("C") + 3);
                continue;
            }
        }

        int beginPoint = line.indexOf("D/");
        if (beginPoint < 0) {
            return;
        }
        this.dinghies = true;
        String tmp = line.substring(beginPoint);
        String[] subSplits = tmp.split(" ");
        for (String str : subSplits) {
            if (str.startsWith("D/")) {

            }
        }

    }

    private void parseSecondSuplement(String line) {

    }

    private String getFirstLine() {

        final String tmp = String.format("%s%s", this.flightRules, this.typeoFlight);
        return tmp.isEmpty()
                ? String.format("(FPL-%s\r\n", this.callSign)
                : String.format("(FPL-%s-%s\r\n", this.callSign, tmp);

    }

    private String getSecondLine() {
        return String.format("-%s%s/%s-%s/%s\r\n",
                this.aircraftNumber,
                this.aircraftType,
                this.wakeTurbulence,
                this.equipment1,
                this.equipment2);
    }

    private String getThirdLine() {
        return String.format("-%s%s\r\n", this.departure, this.departureTime);
    }

    private String getFouthLine() {
        final StringBuilder content = new StringBuilder();
        String[] routes = this.route.replace("\r\n", " ").split(" ");
        String line = String.format("-%s%s", this.speed, this.altitude);

        for (String route : routes) {
            if (line.length() + route.length() <= 68) {
                line += (" " + route);
                continue;
            }

            content.append(line).append("\r\n");
            line = route;
        }

        if (!line.isEmpty()) {
            content.append(line).append("\r\n");
        }
        return content.toString();
    }

    private String getFivethLine() {
        return secondAltAerodrome == null || secondAltAerodrome.isEmpty()
                ? String.format("-%s%s %s\r\n", this.destination, this.totalEET, this.altAerodrome)
                : String.format("-%s%s %s %s\r\n", this.destination, this.totalEET, this.altAerodrome, this.secondAltAerodrome);
    }

    private String getOtherInfo() {
        final StringBuilder content = new StringBuilder("");
        String[] routes = this.otherInformation.replace("\r\n", " ").split(" ");
        String line = "";
        String seperate = "-";

        for (String route : routes) {
            if (line.length() + route.length() <= 68) {
                line += (seperate + route);
                seperate = " ";
                continue;
            }

            content.append(line).append("\r\n");
            line = route;
        }

        if (!line.isEmpty()) {
            content.append(line);
        }
        return content.toString();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("");
        builder.append(getFirstLine());
        builder.append(getSecondLine());
        builder.append(getThirdLine());
        builder.append(getFouthLine());
        builder.append(getFivethLine());
        builder.append(getOtherInfo());

        if (this.suplementaryInfomation == null || this.suplementaryInfomation.length() == 0) {
            builder.append(")");
        } else {
            builder.append("\r\n").append(this.suplementaryInfomation).append(")");
        }
        return builder.toString();
    }

    //<editor-fold defaultstate="collapsed" desc="Class property methods">
    /**
     * @return the callSign
     */
    public String getCallSign() {
        return callSign;
    }

    /**
     * @param callSign the callSign to set
     */
    public void setCallSign(String callSign) {
        this.callSign = callSign;
    }

    /**
     * @return the ssr
     */
    public String getSsr() {
        return ssr;
    }

    /**
     * @param ssr the ssr to set
     */
    public void setSsr(String ssr) {
        this.ssr = ssr;
    }

    /**
     * @return the flightRules
     */
    public String getFlightRules() {
        return flightRules;
    }

    /**
     * @param flightRules the flightRules to set
     */
    public void setFlightRules(String flightRules) {
        this.flightRules = flightRules;
    }

    /**
     * @return the typeoFlight
     */
    public String getTypeoFlight() {
        return typeoFlight;
    }

    /**
     * @param typeoFlight the typeoFlight to set
     */
    public void setTypeoFlight(String typeoFlight) {
        this.typeoFlight = typeoFlight;
    }

    /**
     * @return the aircraftNumber
     */
    public String getAircraftNumber() {
        return aircraftNumber;
    }

    /**
     * @param aircraftNumber the aircraftNumber to set
     */
    public void setAircraftNumber(String aircraftNumber) {
        this.aircraftNumber = aircraftNumber;
    }

    /**
     * @return the aircraftType
     */
    public String getAircraftType() {
        return aircraftType;
    }

    /**
     * @param aircraftType the aircraftType to set
     */
    public void setAircraftType(String aircraftType) {
        this.aircraftType = aircraftType;
    }

    /**
     * @return the wakeTurbulence
     */
    public String getWakeTurbulence() {
        return wakeTurbulence;
    }

    /**
     * @param wakeTurbulence the wakeTurbulence to set
     */
    public void setWakeTurbulence(String wakeTurbulence) {
        this.wakeTurbulence = wakeTurbulence;
    }

    /**
     * @return the equipment1
     */
    public String getEquipment1() {
        return equipment1;
    }

    /**
     * @param equipment1 the equipment1 to set
     */
    public void setEquipment1(String equipment1) {
        this.equipment1 = equipment1;
    }

    /**
     * @return the equipment2
     */
    public String getEquipment2() {
        return equipment2;
    }

    /**
     * @param equipment2 the equipment2 to set
     */
    public void setEquipment2(String equipment2) {
        this.equipment2 = equipment2;
    }

    /**
     * @return the departureAeroDrome
     */
    public String getDeparture() {
        return departure;
    }

    /**
     * @param departureAeroDrome the departureAeroDrome to set
     */
    public void setDeparture(String departureAeroDrome) {
        this.departure = departureAeroDrome;
    }

    /**
     * @return the departureTime
     */
    public String getDepartureTime() {
        return departureTime;
    }

    /**
     * @param departureTime the departureTime to set
     */
    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    /**
     * @return the speedUnit
     */
    public SpeedUnit getSpeedUnit() {
        return speedUnit;
    }

    /**
     * @param speedUnit the speedUnit to set
     */
    public void setSpeedUnit(SpeedUnit speedUnit) {
        this.speedUnit = speedUnit;
    }

    /**
     * @return the speed
     */
    public String getSpeed() {
        return speed;
    }

    /**
     * @param speed the speed to set
     */
    public void setSpeed(String speed) {
        this.speed = speed;
    }

    /**
     * @return the altitudeUnit
     */
    public LevelUnit getAltitudeUnit() {
        return altitudeUnit;
    }

    /**
     * @param altitudeUnit the altitudeUnit to set
     */
    public void setAltitudeUnit(LevelUnit altitudeUnit) {
        this.altitudeUnit = altitudeUnit;
    }

    /**
     * @return the altitude
     */
    public String getAltitude() {
        return altitude;
    }

    /**
     * @param altitude the altitude to set
     */
    public void setAltitude(String altitude) {
        this.altitude = altitude;
    }

    /**
     * @return the route
     */
    public String getRoute() {
        return route;
    }

    /**
     * @param route the route to set
     */
    public void setRoute(String route) {
        this.route = route;
    }

    /**
     * @return the destination
     */
    public String getDestination() {
        return destination;
    }

    /**
     * @param destination the destination to set
     */
    public void setDestination(String destination) {
        this.destination = destination;
    }

    /**
     * @return the totalEET
     */
    public String getTotalEET() {
        return totalEET;
    }

    /**
     * @param totalEET the totalEET to set
     */
    public void setTotalEET(String totalEET) {
        this.totalEET = totalEET;
    }

    /**
     * @return the altAerodrome
     */
    public String getAltAerodrome() {
        return altAerodrome;
    }

    /**
     * @param altAerodrome the altAerodrome to set
     */
    public void setAltAerodrome(String altAerodrome) {
        this.altAerodrome = altAerodrome;
    }

    /**
     * @return the secondAltAerodrome
     */
    public String getSecondAltAerodrome() {
        return secondAltAerodrome;
    }

    /**
     * @param secondAltAerodrome the secondAltAerodrome to set
     */
    public void setSecondAltAerodrome(String secondAltAerodrome) {
        this.secondAltAerodrome = secondAltAerodrome;
    }

    /**
     * @return the otherInformation
     */
    public String getOtherInformation() {
        return otherInformation;
    }

    /**
     * @param otherInformation the otherInformation to set
     */
    public void setOtherInformation(String otherInformation) {
        this.otherInformation = otherInformation;
    }

    /**
     * @return the activateSupplementaryInformation
     */
    public boolean isActivateSupplementaryInformation() {
        return activateSupplementaryInformation;
    }

    /**
     * @param activateSupplementaryInformation the
     * activateSupplementaryInformation to set
     */
    public void setActivateSupplementaryInformation(boolean activateSupplementaryInformation) {
        this.activateSupplementaryInformation = activateSupplementaryInformation;
    }

    /**
     * @return the endurance
     */
    public String getEndurance() {
        return endurance;
    }

    /**
     * @param endurance the endurance to set
     */
    public void setEndurance(String endurance) {
        this.endurance = endurance;
    }

    /**
     * @return the personsOnBoard
     */
    public String getPersonsOnBoard() {
        return personsOnBoard;
    }

    /**
     * @param personsOnBoard the personsOnBoard to set
     */
    public void setPersonsOnBoard(String personsOnBoard) {
        this.personsOnBoard = personsOnBoard;
    }

    /**
     * @return the emergencyRadioU
     */
    public boolean isEmergencyRadioU() {
        return emergencyRadioU;
    }

    /**
     * @param emergencyRadioU the emergencyRadioU to set
     */
    public void setEmergencyRadioU(boolean emergencyRadioU) {
        this.emergencyRadioU = emergencyRadioU;
    }

    /**
     * @return the emergencyRadioV
     */
    public boolean isEmergencyRadioV() {
        return emergencyRadioV;
    }

    /**
     * @param emergencyRadioV the emergencyRadioV to set
     */
    public void setEmergencyRadioV(boolean emergencyRadioV) {
        this.emergencyRadioV = emergencyRadioV;
    }

    /**
     * @return the emergencyRadioE
     */
    public boolean isEmergencyRadioE() {
        return emergencyRadioE;
    }

    /**
     * @param emergencyRadioE the emergencyRadioE to set
     */
    public void setEmergencyRadioE(boolean emergencyRadioE) {
        this.emergencyRadioE = emergencyRadioE;
    }

    /**
     * @return the equipmentS
     */
    public boolean isEquipmentS() {
        return equipmentS;
    }

    /**
     * @param equipmentS the equipmentS to set
     */
    public void setEquipmentS(boolean equipmentS) {
        this.equipmentS = equipmentS;
    }

    /**
     * @return the equipmentP
     */
    public boolean isEquipmentP() {
        return equipmentP;
    }

    /**
     * @param equipmentP the equipmentP to set
     */
    public void setEquipmentP(boolean equipmentP) {
        this.equipmentP = equipmentP;
    }

    /**
     * @return the equipmentD
     */
    public boolean isEquipmentD() {
        return equipmentD;
    }

    /**
     * @param equipmentD the equipmentD to set
     */
    public void setEquipmentD(boolean equipmentD) {
        this.equipmentD = equipmentD;
    }

    /**
     * @return the equipmentM
     */
    public boolean isEquipmentM() {
        return equipmentM;
    }

    /**
     * @param equipmentM the equipmentM to set
     */
    public void setEquipmentM(boolean equipmentM) {
        this.equipmentM = equipmentM;
    }

    /**
     * @return the equipmentJ
     */
    public boolean isEquipmentJ() {
        return equipmentJ;
    }

    /**
     * @param equipmentJ the equipmentJ to set
     */
    public void setEquipmentJ(boolean equipmentJ) {
        this.equipmentJ = equipmentJ;
    }

    /**
     * @return the jacketsJ
     */
    public boolean isJacketsJ() {
        return jacketsJ;
    }

    /**
     * @param jacketsJ the jacketsJ to set
     */
    public void setJacketsJ(boolean jacketsJ) {
        this.jacketsJ = jacketsJ;
    }

    /**
     * @return the jacketsL
     */
    public boolean isJacketsL() {
        return jacketsL;
    }

    /**
     * @param jacketsL the jacketsL to set
     */
    public void setJacketsL(boolean jacketsL) {
        this.jacketsL = jacketsL;
    }

    /**
     * @return the jacketsF
     */
    public boolean isJacketsF() {
        return jacketsF;
    }

    /**
     * @param jacketsF the jacketsF to set
     */
    public void setJacketsF(boolean jacketsF) {
        this.jacketsF = jacketsF;
    }

    /**
     * @return the jacketsU
     */
    public boolean isJacketsU() {
        return jacketsU;
    }

    /**
     * @param jacketsU the jacketsU to set
     */
    public void setJacketsU(boolean jacketsU) {
        this.jacketsU = jacketsU;
    }

    /**
     * @return the jacketsV
     */
    public boolean isJacketsV() {
        return jacketsV;
    }

    /**
     * @param jacketsV the jacketsV to set
     */
    public void setJacketsV(boolean jacketsV) {
        this.jacketsV = jacketsV;
    }

    /**
     * @return the dinghies
     */
    public boolean isDinghies() {
        return dinghies;
    }

    /**
     * @param dinghies the dinghies to set
     */
    public void setDinghies(boolean dinghies) {
        this.dinghies = dinghies;
    }

    /**
     * @return the dinghiesNumber
     */
    public String getDinghiesNumber() {
        return dinghiesNumber;
    }

    /**
     * @param dinghiesNumber the dinghiesNumber to set
     */
    public void setDinghiesNumber(String dinghiesNumber) {
        this.dinghiesNumber = dinghiesNumber;
    }

    /**
     * @return the dinghiesCapacity
     */
    public String getDinghiesCapacity() {
        return dinghiesCapacity;
    }

    /**
     * @param dinghiesCapacity the dinghiesCapacity to set
     */
    public void setDinghiesCapacity(String dinghiesCapacity) {
        this.dinghiesCapacity = dinghiesCapacity;
    }

    /**
     * @return the dinghiesCover
     */
    public boolean isDinghiesCover() {
        return dinghiesCover;
    }

    /**
     * @param dinghiesCover the dinghiesCover to set
     */
    public void setDinghiesCover(boolean dinghiesCover) {
        this.dinghiesCover = dinghiesCover;
    }

    /**
     * @return the dinghiesColor
     */
    public String getDinghiesColor() {
        return dinghiesColor;
    }

    /**
     * @param dinghiesColor the dinghiesColor to set
     */
    public void setDinghiesColor(String dinghiesColor) {
        this.dinghiesColor = dinghiesColor;
    }

    /**
     * @return the aircraftColour
     */
    public String getAircraftColour() {
        return aircraftColour;
    }

    /**
     * @param aircraftColour the aircraftColour to set
     */
    public void setAircraftColour(String aircraftColour) {
        this.aircraftColour = aircraftColour;
    }

    /**
     * @return the isRemarks
     */
    public boolean isIsRemarks() {
        return isRemarks;
    }

    /**
     * @param isRemarks the isRemarks to set
     */
    public void setIsRemarks(boolean isRemarks) {
        this.isRemarks = isRemarks;
    }

    /**
     * @return the remarks
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * @param remarks the remarks to set
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /**
     * @return the pilotInCommand
     */
    public String getPilotInCommand() {
        return pilotInCommand;
    }

    /**
     * @param pilotInCommand the pilotInCommand to set
     */
    public void setPilotInCommand(String pilotInCommand) {
        this.pilotInCommand = pilotInCommand;
    }

    /**
     * @return the dateOfFlight
     */
    public String getDof() {
        return dof;
    }

    /**
     * @param dateOfFlight the dateOfFlight to set
     */
    public void setDof(String dateOfFlight) {
        this.dof = dateOfFlight;
    }

    /**
     * @return the reg
     */
    public String getReg() {
        return reg;
    }

    /**
     * @param reg the reg to set
     */
    public void setReg(String reg) {
        this.reg = reg;
    }

    /**
     * @return the suplementaryInfomation
     */
    public String getSuplementaryInfomation() {
        return suplementaryInfomation;
    }

    /**
     * @param suplementaryInfomation the suplementaryInfomation to set
     */
    public void setSuplementaryInfomation(String suplementaryInfomation) {
        this.suplementaryInfomation = suplementaryInfomation;
    }
    //</editor-fold>

}
