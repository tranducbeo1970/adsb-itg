/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.cat62.v112;

/**
 *
 * @author andh
 */
public class FlightPlan {
    // #1
    private boolean identificationTagAvailable;
    private boolean callsignAvailable;
    private boolean ifpsFlightIDAvailable;
    private boolean flightCategoryAvailable;
    private boolean typeOfAircraftAvailable;
    private boolean wakeTurbulenceCategoryAvailable;
    private boolean departedAirportAvailable;
    
    // #2
    private boolean destinationAirportAvailable;
    private boolean runwayDesignationAvailable;
    private boolean currentClearFlightLevelAvailable;
    private boolean currentControlPositionAvailable;
    private boolean timeOfDepArrAvailable;
    private boolean aircraftStandAvailable;
    private boolean standStatusAvailable;
    
    // #3
    private boolean standardInstrumentDepartureAvailable;
    private boolean standardInstrumentArrivalAvailable;
    private boolean preEmergencyMode3ACodeAvailable;
    private boolean preEmergencyCallsignAvailable;

    // Sub #1
    private int sic, sac; // #1
    private String callSign; // #2
    private IFPSFlightID ifpsFlightID; // #3
    private FlightCategory flightCategory; // #4
    private String aircraftType; // #5
    private String wakeTurbulenceVategory; //#6
    private String departureAirport; // #7
    private String destinationAirport; // #8
    private String runwayDesignation; // #9
    private double currentClearFlightLevel; // #10
    private CurrentControlPosition currentControlPosition; // #11
    private Time time; // #12
    private String aircraftStand; // #13
    private StandStatus standStatus; // #14
    private String standardInstrumentDeparture; // #15
    private String standardInstrumentArrival; // #16
    private PreEmergencyMode3A preEmergencyMode3A; // #17
    private String preEmergencyCallSign; // #18

    public static int decode(byte [] bytes, int index, FlightPlan code){
        
        // #1
        if (!Byter.validateIndex(index, bytes.length, 1)) return -1;
        byte cbyte = bytes[index++];
        code.setIdentificationTagAvailable((cbyte & 0x80) > 0);
        code.setCallsignAvailable((cbyte & 0x40) > 0);
        code.setIfpsFlightIDAvailable((cbyte & 0x20) > 0);
        code.setFlightCategoryAvailable((cbyte & 0x10) > 0);
        code.setTypeOfAircraftAvailable((cbyte & 0x08) > 0);
        code.setWakeTurbulenceCategoryAvailable((cbyte & 0x04) > 0);
        code.setDepartedAirportAvailable((cbyte & 0x02) > 0);
        if ((cbyte & 0x01) == 0) return 1;

        // #2
        if (!Byter.validateIndex(index, bytes.length, 1)) return -1;
        cbyte = bytes[index++];
        code.setDestinationAirportAvailable((cbyte & 0x80) > 0);
        code.setRunwayDesignationAvailable((cbyte & 0x40) > 0);
        code.setCurrentClearFlightLevelAvailable((cbyte & 0x20) > 0);
        code.setCurrentControlPositionAvailable((cbyte & 0x10) > 0);
        code.setTimeOfDepArrAvailable((cbyte & 0x08) > 0);
        code.setAircraftStandAvailable((cbyte & 0x04) > 0);
        code.setStandStatusAvailable((cbyte & 0x02) > 0);
        if ((cbyte & 0x01) == 0) return 2;

        // #3
        if (!Byter.validateIndex(index, bytes.length, 1)) return -1;
        cbyte = bytes[index++];
        code.setStandardInstrumentDepartureAvailable((cbyte & 0x80) > 0);
        code.setStandardInstrumentArrivalAvailable((cbyte & 0x40) > 0);
        code.setPreEmergencyMode3ACodeAvailable((cbyte & 0x20) > 0);
        code.setPreEmergencyCallsignAvailable((cbyte & 0x10) > 0);
        if ((cbyte & 0x01) == 0) return 3;
        
        int count = 3;
        
        // Subfield #1 
        if (code.isIdentificationTagAvailable()) {
            if (!Byter.validateIndex(index, bytes.length, 2)) return -1;
            code.setSac(bytes[index++] & 0xFF);
            code.setSic(bytes[index++] & 0xFF);
            count += 2;
        }
        
        // Subfield #2
        if (code.isCallsignAvailable()) {
            if (!Byter.validateIndex(index, bytes.length, 7)) return -1;
            byte [] content = new byte[]{
                bytes[index++], 
                bytes[index++], 
                bytes[index++], 
                bytes[index++], 
                bytes[index++], 
                bytes[index++], 
                bytes[index++]};
            code.setCallSign(new String(content));
            count += 7;
        }
        
        // Subfield #3
        int result = 0;
        if (code.isIfpsFlightIDAvailable()) {
            IFPSFlightID ifpsFlightID = new IFPSFlightID();
            result = IFPSFlightID.decode(bytes, index, ifpsFlightID);
            if ( result < 0) return -1;
            code.setIfpsFlightID(ifpsFlightID);
            count += result;
        }
        
        // Subfield #4
        if (code.isFlightCategoryAvailable()) {
            FlightCategory flightCategory = new FlightCategory();
            result = FlightCategory.decode(bytes, index, flightCategory);
            if (result < 0) return -1;
            code.setFlightCategory(flightCategory);
            count += result;
        }
        
        // Subfield #5
        if (code.isTypeOfAircraftAvailable()) {
            if (!Byter.validateIndex(index, bytes.length, 4)) return -1;
            byte [] content = new byte[]{
                bytes[index++], 
                bytes[index++], 
                bytes[index++], 
                bytes[index++]};
            code.setAircraftType(new String(content));
            count += 4;
        }
        
        // Subfield #6
        if (code.isWakeTurbulenceCategoryAvailable()) {
            if (!Byter.validateIndex(index, bytes.length, 1)) return -1;
            code.setAircraftType(new String(new byte[]{bytes[index++]}));
            count += 1;
        }
        
         // Subfield #7
        if (code.isDepartedAirportAvailable()) {
            if (!Byter.validateIndex(index, bytes.length, 4)) return -1;
            byte [] content = new byte[]{
                bytes[index++], 
                bytes[index++], 
                bytes[index++], 
                bytes[index++]};
            code.setDepartureAirport(new String(content));
            count += 4;
        }

        // Subfield #8
        if (code.isDestinationAirportAvailable()) {
            if (!Byter.validateIndex(index, bytes.length, 4)) return -1;
            byte [] content = new byte[]{
                bytes[index++], 
                bytes[index++], 
                bytes[index++], 
                bytes[index++]};
            code.setDestinationAirport(new String(content));
            count += 4;
        }
        
        // Subfield #9
        if (code.isRunwayDesignationAvailable()) {
            if (!Byter.validateIndex(index, bytes.length, 3)) return -1;
            byte [] content = new byte[]{
                bytes[index++], 
                bytes[index++], 
                bytes[index++]};
            code.setDestinationAirport(new String(content));
            count += 3;
        }
        
        // Subfield #10
        if (code.isCurrentClearFlightLevelAvailable()) {
            if (!Byter.validateIndex(index, bytes.length, 2)) return -1;
            int ival = (bytes[index++] & 0xFF) << 8 | (bytes[index++] & 0xFF);
            code.setCurrentClearFlightLevel(ival * 0.25);
            count += 2;
        }
        
        // Subfield #11
        if (code.isCurrentControlPositionAvailable()) {
            CurrentControlPosition curControlPos = new CurrentControlPosition();
            result = CurrentControlPosition.decode(bytes, index, curControlPos);
            if (result < 0) return -1;
            code.setCurrentControlPosition(curControlPos);
            count += result;
        }
        
        // Subfield #12
        if (code.isTimeOfDepArrAvailable()) {
            Time time = new Time();
            result = Time.decode(bytes, index, time);
            if (result < 0) return -1;
            code.setTime(time);
            count += result;
        }
        
        // Subfield #13
        if (code.isAircraftStandAvailable()) {
            if (!Byter.validateIndex(index, bytes.length, 6)) return -1;
            byte[] content = new byte[]{
                bytes[index++],
                bytes[index++],
                bytes[index++],
                bytes[index++],
                bytes[index++],
                bytes[index++]};
            code.setAircraftStand(new String(content));
            count += 6;
        }
        
        // Subfield #14
        if (code.isStandStatusAvailable()) {
            StandStatus standstatus = new StandStatus();
            result = StandStatus.decode(bytes, index, standstatus);
            if (result < 0) return -1;
            code.setStandStatus(standstatus);
            count += result;
        }

        // Subfield #15
        if (code.isStandardInstrumentDepartureAvailable()) {
            if (!Byter.validateIndex(index, bytes.length, 7)) return -1;
            byte[] content = new byte[]{
                bytes[index++],
                bytes[index++],
                bytes[index++],
                bytes[index++],
                bytes[index++],
                bytes[index++],
                bytes[index++]};
            code.setStandardInstrumentDeparture(new String(content));
            count += 7;
        }
        
        // Subfield #16
        if (code.isStandardInstrumentArrivalAvailable()) {
            if (!Byter.validateIndex(index, bytes.length, 7)) return -1;
            byte[] content = new byte[]{
                bytes[index++],
                bytes[index++],
                bytes[index++],
                bytes[index++],
                bytes[index++],
                bytes[index++],
                bytes[index++]};
            code.setStandardInstrumentArrival(new String(content));
            count += 7;
        }
        
        // Subfield #17
        if (code.isPreEmergencyMode3ACodeAvailable()) {
            PreEmergencyMode3A preEmergencyMode3A = new PreEmergencyMode3A();
            result = PreEmergencyMode3A.decode(bytes, index, preEmergencyMode3A);
            if (result < 0) return -1;
            code.setPreEmergencyMode3A(preEmergencyMode3A);
            count += result;
        }
        
        // Subfield #18
        if (code.isPreEmergencyCallsignAvailable()) {
            if (!Byter.validateIndex(index, bytes.length, 7)) return -1;
            byte[] content = new byte[]{
                bytes[index++],
                bytes[index++],
                bytes[index++],
                bytes[index++],
                bytes[index++],
                bytes[index++],
                bytes[index++]};
            code.setPreEmergencyCallSign(new String(content));
            count += 7;
        }
        return count;
    }
   
    public byte[] encode() {
        SubByteBuilder builder = new SubByteBuilder();
        
        // Subfield #1: FPPS Identification Tag
        if (identificationTagAvailable) {
            byte[] bs = new byte[]{(byte) sac, (byte) sic};
            builder.set(0, bs);
        }
        
        // Subfield #2: Callsign
        if (callsignAvailable && callSign != null) {
            byte [] bs = new byte[] {
                (byte) getCharCode(0, callSign),
                (byte) getCharCode(1, callSign),
                (byte) getCharCode(2, callSign),
                (byte) getCharCode(3, callSign),
                (byte) getCharCode(4, callSign),
                (byte) getCharCode(5, callSign),
                (byte) getCharCode(6, callSign)
            };
            builder.set(1, bs);
        }
        
        // Subfield #3: IFPS_FLIGHT_ID
        if (ifpsFlightIDAvailable) {
            builder.set(2, this.ifpsFlightID.encode());
        }
        
        
        // Subfield #4: Flight Category
        if (flightCategoryAvailable) {
            builder.set(3, this.flightCategory.encode());
        }
        
        // Subfield #5: Type of Aircraft
        if (typeOfAircraftAvailable) {
            byte[] bs = new byte[]{
                (byte) getCharCode(0, aircraftType),
                (byte) getCharCode(1, aircraftType),
                (byte) getCharCode(2, aircraftType),
                (byte) getCharCode(3, aircraftType)
            };
            builder.set(4, bs);
        }
        
        // bit-19 (WTC) Subfield #6: Wake Turbulence Category
        if (wakeTurbulenceCategoryAvailable) {
            builder.set(5, new byte[]{(byte) getCharCode(0, wakeTurbulenceVategory)});
        }
       
        // bit-18 (DEP) Subfield #7: Departure Airport
        if (departedAirportAvailable) {
            byte[] bs = new byte[]{
                (byte) getCharCode(0, departureAirport),
                (byte) getCharCode(1, departureAirport),
                (byte) getCharCode(2, departureAirport),
                (byte) getCharCode(3, departureAirport)
            };
            builder.set(6, bs);
        }

        // bit-16 (DST) Subfield #8: Destination Airport
        if (destinationAirportAvailable) {
            byte[] bs = new byte[]{
                (byte) getCharCode(0, destinationAirport),
                (byte) getCharCode(1, destinationAirport),
                (byte) getCharCode(2, destinationAirport),
                (byte) getCharCode(3, destinationAirport)
            };
            builder.set(7, bs);
        }
        

        // bit-15 (RDS) Subfield #9: Runway Designation
        if (runwayDesignationAvailable) {
            byte[] bs = new byte[]{
                (byte) getCharCode(0, destinationAirport),
                (byte) getCharCode(1, destinationAirport),
                (byte) getCharCode(2, destinationAirport)
            };
            builder.set(8, bs);
        }

        // bit-14 (CFL) Subfield #10: Current Cleared Flight Level
        if (currentClearFlightLevelAvailable) {
            int val = (int) (this.currentClearFlightLevel * 4);
            byte[] bs = new byte[]{
                (byte) (val >> 8 & 0xFF),
                (byte) (val & 0xFF)
            };
            builder.set(9, bs);
        }

        // bit-13 (CTL) Subfield #11: Current Control Position
        if (currentControlPositionAvailable) {
            builder.set(10, this.currentControlPosition.encode());
        }

        // bit-12 (TOD) Subfield #12: Time of Departure / Arrival
        if (timeOfDepArrAvailable) {
            builder.set(11, this.time.encode());
        }

        // bit-11 (AST) Subfield #13: Aircraft Stand
        if (aircraftStandAvailable) {
            byte[] bs = new byte[]{
                (byte) getCharCode(0, aircraftStand),
                (byte) getCharCode(1, aircraftStand),
                (byte) getCharCode(2, aircraftStand),
                (byte) getCharCode(3, aircraftStand),
                (byte) getCharCode(4, aircraftStand),
                (byte) getCharCode(5, aircraftStand)
            };
            builder.set(12, bs);
        }

        // bit-10 (STS) Subfield #14: Stand Status
        if (standStatusAvailable) {
            builder.set(13, this.standStatus.encode());
        }

        // #3
        // bit-8 (STD) Subfield #15: Standard Instrument Departure
        if (standardInstrumentDepartureAvailable) {
            byte[] bs = new byte[]{
                (byte) getCharCode(0, standardInstrumentDeparture),
                (byte) getCharCode(1, standardInstrumentDeparture),
                (byte) getCharCode(2, standardInstrumentDeparture),
                (byte) getCharCode(3, standardInstrumentDeparture),
                (byte) getCharCode(4, standardInstrumentDeparture),
                (byte) getCharCode(5, standardInstrumentDeparture),
                (byte) getCharCode(6, standardInstrumentDeparture)
            };
            builder.set(14, bs);
        }

        // bit-7 (STA) Subfield #16: STandard Instrument ARrival
        if (standardInstrumentArrivalAvailable) {
            byte[] bs = new byte[]{
                (byte) getCharCode(0, standardInstrumentArrival),
                (byte) getCharCode(1, standardInstrumentArrival),
                (byte) getCharCode(2, standardInstrumentArrival),
                (byte) getCharCode(3, standardInstrumentArrival),
                (byte) getCharCode(4, standardInstrumentArrival),
                (byte) getCharCode(5, standardInstrumentArrival),
                (byte) getCharCode(6, standardInstrumentArrival)
            };
            builder.set(15, bs);
        }

        // bit-6 (PEM) Subfield #17: Pre-emergency Mode 3/A code
        if (preEmergencyMode3ACodeAvailable) {
            builder.set(16, this.preEmergencyMode3A.encode());
        }

        // bit-5 (PEC) Subfield #18: Pre-emergency Callsign
        if (preEmergencyCallsignAvailable) {
            byte[] bs = new byte[]{
                (byte) getCharCode(0, preEmergencyCallSign),
                (byte) getCharCode(1, preEmergencyCallSign),
                (byte) getCharCode(2, preEmergencyCallSign),
                (byte) getCharCode(3, preEmergencyCallSign),
                (byte) getCharCode(4, preEmergencyCallSign),
                (byte) getCharCode(5, preEmergencyCallSign),
                (byte) getCharCode(6, preEmergencyCallSign)
            };
            builder.set(17, bs);
        }

        return builder.toByteArray();
    }
    
    private int getCharCode(int index, String callSign) {
        if (callSign == null
                || callSign.isEmpty()
                || callSign.length() < (index + 1)
                || index < 0) {
            return 32;
        }
        return (int) callSign.charAt(index);
    }
    
    /**
     * @return the identificationTagAvailable
     */
    public boolean isIdentificationTagAvailable() {
        return identificationTagAvailable;
    }

    /**
     * @param identificationTagAvailable the identificationTagAvailable to set
     */
    public void setIdentificationTagAvailable(boolean identificationTagAvailable) {
        this.identificationTagAvailable = identificationTagAvailable;
    }

    /**
     * @return the callsignAvailable
     */
    public boolean isCallsignAvailable() {
        return callsignAvailable;
    }

    /**
     * @param callsignAvailable the callsignAvailable to set
     */
    public void setCallsignAvailable(boolean callsignAvailable) {
        this.callsignAvailable = callsignAvailable;
    }

    /**
     * @return the ifpsFlightIDAvailable
     */
    public boolean isIfpsFlightIDAvailable() {
        return ifpsFlightIDAvailable;
    }

    /**
     * @param ifpsFlightIDAvailable the ifpsFlightIDAvailable to set
     */
    public void setIfpsFlightIDAvailable(boolean ifpsFlightIDAvailable) {
        this.ifpsFlightIDAvailable = ifpsFlightIDAvailable;
    }

    /**
     * @return the flightCategoryAvailable
     */
    public boolean isFlightCategoryAvailable() {
        return flightCategoryAvailable;
    }

    /**
     * @param flightCategoryAvailable the flightCategoryAvailable to set
     */
    public void setFlightCategoryAvailable(boolean flightCategoryAvailable) {
        this.flightCategoryAvailable = flightCategoryAvailable;
    }

    /**
     * @return the typeOfAircraftAvailable
     */
    public boolean isTypeOfAircraftAvailable() {
        return typeOfAircraftAvailable;
    }

    /**
     * @param typeOfAircraftAvailable the typeOfAircraftAvailable to set
     */
    public void setTypeOfAircraftAvailable(boolean typeOfAircraftAvailable) {
        this.typeOfAircraftAvailable = typeOfAircraftAvailable;
    }

    /**
     * @return the wakeTurbulenceCategoryAvailable
     */
    public boolean isWakeTurbulenceCategoryAvailable() {
        return wakeTurbulenceCategoryAvailable;
    }

    /**
     * @param wakeTurbulenceCategoryAvailable the wakeTurbulenceCategoryAvailable to set
     */
    public void setWakeTurbulenceCategoryAvailable(boolean wakeTurbulenceCategoryAvailable) {
        this.wakeTurbulenceCategoryAvailable = wakeTurbulenceCategoryAvailable;
    }

    /**
     * @return the departedAirportAvailable
     */
    public boolean isDepartedAirportAvailable() {
        return departedAirportAvailable;
    }

    /**
     * @param departedAirportAvailable the departedAirportAvailable to set
     */
    public void setDepartedAirportAvailable(boolean departedAirportAvailable) {
        this.departedAirportAvailable = departedAirportAvailable;
    }

    /**
     * @return the destinationAirportAvailable
     */
    public boolean isDestinationAirportAvailable() {
        return destinationAirportAvailable;
    }

    /**
     * @param destinationAirportAvailable the destinationAirportAvailable to set
     */
    public void setDestinationAirportAvailable(boolean destinationAirportAvailable) {
        this.destinationAirportAvailable = destinationAirportAvailable;
    }

    /**
     * @return the runwayDesignationAvailable
     */
    public boolean isRunwayDesignationAvailable() {
        return runwayDesignationAvailable;
    }

    /**
     * @param runwayDesignationAvailable the runwayDesignationAvailable to set
     */
    public void setRunwayDesignationAvailable(boolean runwayDesignationAvailable) {
        this.runwayDesignationAvailable = runwayDesignationAvailable;
    }

    /**
     * @return the currentClearFlightLevelAvailable
     */
    public boolean isCurrentClearFlightLevelAvailable() {
        return currentClearFlightLevelAvailable;
    }

    /**
     * @param currentClearFlightLevelAvailable the currentClearFlightLevelAvailable to set
     */
    public void setCurrentClearFlightLevelAvailable(boolean currentClearFlightLevelAvailable) {
        this.currentClearFlightLevelAvailable = currentClearFlightLevelAvailable;
    }

    /**
     * @return the currentControlPositionAvailable
     */
    public boolean isCurrentControlPositionAvailable() {
        return currentControlPositionAvailable;
    }

    /**
     * @param currentControlPositionAvailable the currentControlPositionAvailable to set
     */
    public void setCurrentControlPositionAvailable(boolean currentControlPositionAvailable) {
        this.currentControlPositionAvailable = currentControlPositionAvailable;
    }

    /**
     * @return the timeOfDepArrAvailable
     */
    public boolean isTimeOfDepArrAvailable() {
        return timeOfDepArrAvailable;
    }

    /**
     * @param timeOfDepArrAvailable the timeOfDepArrAvailable to set
     */
    public void setTimeOfDepArrAvailable(boolean timeOfDepArrAvailable) {
        this.timeOfDepArrAvailable = timeOfDepArrAvailable;
    }

    /**
     * @return the aircraftStandAvailable
     */
    public boolean isAircraftStandAvailable() {
        return aircraftStandAvailable;
    }

    /**
     * @param aircraftStandAvailable the aircraftStandAvailable to set
     */
    public void setAircraftStandAvailable(boolean aircraftStandAvailable) {
        this.aircraftStandAvailable = aircraftStandAvailable;
    }

    /**
     * @return the standStatusAvailable
     */
    public boolean isStandStatusAvailable() {
        return standStatusAvailable;
    }

    /**
     * @param standStatusAvailable the standStatusAvailable to set
     */
    public void setStandStatusAvailable(boolean standStatusAvailable) {
        this.standStatusAvailable = standStatusAvailable;
    }

    /**
     * @return the standardInstrumentDepartureAvailable
     */
    public boolean isStandardInstrumentDepartureAvailable() {
        return standardInstrumentDepartureAvailable;
    }

    /**
     * @param standardInstrumentDepartureAvailable the standardInstrumentDepartureAvailable to set
     */
    public void setStandardInstrumentDepartureAvailable(boolean standardInstrumentDepartureAvailable) {
        this.standardInstrumentDepartureAvailable = standardInstrumentDepartureAvailable;
    }

    /**
     * @return the standardInstrumentArrivalAvailable
     */
    public boolean isStandardInstrumentArrivalAvailable() {
        return standardInstrumentArrivalAvailable;
    }

    /**
     * @param standardInstrumentArrivalAvailable the standardInstrumentArrivalAvailable to set
     */
    public void setStandardInstrumentArrivalAvailable(boolean standardInstrumentArrivalAvailable) {
        this.standardInstrumentArrivalAvailable = standardInstrumentArrivalAvailable;
    }

    /**
     * @return the preEmergencyMode3ACodeAvailable
     */
    public boolean isPreEmergencyMode3ACodeAvailable() {
        return preEmergencyMode3ACodeAvailable;
    }

    /**
     * @param preEmergencyMode3ACodeAvailable the preEmergencyMode3ACodeAvailable to set
     */
    public void setPreEmergencyMode3ACodeAvailable(boolean preEmergencyMode3ACodeAvailable) {
        this.preEmergencyMode3ACodeAvailable = preEmergencyMode3ACodeAvailable;
    }

    /**
     * @return the preEmergencyCallsignAvailable
     */
    public boolean isPreEmergencyCallsignAvailable() {
        return preEmergencyCallsignAvailable;
    }

    /**
     * @param preEmergencyCallsignAvailable the preEmergencyCallsignAvailable to set
     */
    public void setPreEmergencyCallsignAvailable(boolean preEmergencyCallsignAvailable) {
        this.preEmergencyCallsignAvailable = preEmergencyCallsignAvailable;
    }

    /**
     * @return the sic
     */
    public int getSic() {
        return sic;
    }

    /**
     * @param sic the sic to set
     */
    public void setSic(int sic) {
        this.sic = sic;
    }

    /**
     * @return the sac
     */
    public int getSac() {
        return sac;
    }

    /**
     * @param sac the sac to set
     */
    public void setSac(int sac) {
        this.sac = sac;
    }

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
     * @return the ifpsFlightID
     */
    public IFPSFlightID getIfpsFlightID() {
        return ifpsFlightID;
    }

    /**
     * @param ifpsFlightID the ifpsFlightID to set
     */
    public void setIfpsFlightID(IFPSFlightID ifpsFlightID) {
        this.ifpsFlightID = ifpsFlightID;
    }

    /**
     * @return the flightCategory
     */
    public FlightCategory getFlightCategory() {
        return flightCategory;
    }

    /**
     * @param flightCategory the flightCategory to set
     */
    public void setFlightCategory(FlightCategory flightCategory) {
        this.flightCategory = flightCategory;
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
     * @return the wakeTurbulenceVategory
     */
    public String getWakeTurbulenceVategory() {
        return wakeTurbulenceVategory;
    }

    /**
     * @param wakeTurbulenceVategory the wakeTurbulenceVategory to set
     */
    public void setWakeTurbulenceVategory(String wakeTurbulenceVategory) {
        this.wakeTurbulenceVategory = wakeTurbulenceVategory;
    }

    /**
     * @return the departureAirport
     */
    public String getDepartureAirport() {
        return departureAirport;
    }

    /**
     * @param departureAirport the departureAirport to set
     */
    public void setDepartureAirport(String departureAirport) {
        this.departureAirport = departureAirport;
    }

    /**
     * @return the destinationAirport
     */
    public String getDestinationAirport() {
        return destinationAirport;
    }

    /**
     * @param destinationAirport the destinationAirport to set
     */
    public void setDestinationAirport(String destinationAirport) {
        this.destinationAirport = destinationAirport;
    }

    /**
     * @return the runwayDesignation
     */
    public String getRunwayDesignation() {
        return runwayDesignation;
    }

    /**
     * @param runwayDesignation the runwayDesignation to set
     */
    public void setRunwayDesignation(String runwayDesignation) {
        this.runwayDesignation = runwayDesignation;
    }

    /**
     * @return the currentClearFlightLevel
     */
    public double getCurrentClearFlightLevel() {
        return currentClearFlightLevel;
    }

    /**
     * @param currentClearFlightLevel the currentClearFlightLevel to set
     */
    public void setCurrentClearFlightLevel(double currentClearFlightLevel) {
        this.currentClearFlightLevel = currentClearFlightLevel;
    }

    /**
     * @return the currentControlPosition
     */
    public CurrentControlPosition getCurrentControlPosition() {
        return currentControlPosition;
    }

    /**
     * @param currentControlPosition the currentControlPosition to set
     */
    public void setCurrentControlPosition(CurrentControlPosition currentControlPosition) {
        this.currentControlPosition = currentControlPosition;
    }

    /**
     * @return the time
     */
    public Time getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(Time time) {
        this.time = time;
    }

    /**
     * @return the aircraftStand
     */
    public String getAircraftStand() {
        return aircraftStand;
    }

    /**
     * @param aircraftStand the aircraftStand to set
     */
    public void setAircraftStand(String aircraftStand) {
        this.aircraftStand = aircraftStand;
    }

    /**
     * @return the standStatus
     */
    public StandStatus getStandStatus() {
        return standStatus;
    }

    /**
     * @param standStatus the standStatus to set
     */
    public void setStandStatus(StandStatus standStatus) {
        this.standStatus = standStatus;
    }

    /**
     * @return the standardInstrumentDeparture
     */
    public String getStandardInstrumentDeparture() {
        return standardInstrumentDeparture;
    }

    /**
     * @param standardInstrumentDeparture the standardInstrumentDeparture to set
     */
    public void setStandardInstrumentDeparture(String standardInstrumentDeparture) {
        this.standardInstrumentDeparture = standardInstrumentDeparture;
    }

    /**
     * @return the standardInstrumentArrival
     */
    public String getStandardInstrumentArrival() {
        return standardInstrumentArrival;
    }

    /**
     * @param standardInstrumentArrival the standardInstrumentArrival to set
     */
    public void setStandardInstrumentArrival(String standardInstrumentArrival) {
        this.standardInstrumentArrival = standardInstrumentArrival;
    }

    /**
     * @return the preEmergencyMode3A
     */
    public PreEmergencyMode3A getPreEmergencyMode3A() {
        return preEmergencyMode3A;
    }

    /**
     * @param preEmergencyMode3A the preEmergencyMode3A to set
     */
    public void setPreEmergencyMode3A(PreEmergencyMode3A preEmergencyMode3A) {
        this.preEmergencyMode3A = preEmergencyMode3A;
    }

    /**
     * @return the preEmergencyCallSign
     */
    public String getPreEmergencyCallSign() {
        return preEmergencyCallSign;
    }

    /**
     * @param preEmergencyCallSign the preEmergencyCallSign to set
     */
    public void setPreEmergencyCallSign(String preEmergencyCallSign) {
        this.preEmergencyCallSign = preEmergencyCallSign;
    }
    
}
