/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.cat48.v121;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hong
 */
public class Cat48Message {
    
    private byte [] binary;
    
    /*
     1 I048/010 Data Source Identifier 2
     2 I048/140 Time-of-Day 3
     3 I048/020 Target Report Descriptor 1+
     4 I048/040 Measured Position in Slant Polar Coordinates 4
     5 I048/070 Mode-3/A Code in Octal Representation 2
     6 I048/090 Flight Level in Binary Representation 2
     7 I048/130 Radar Plot Characteristics 1+1+
     */

    private Integer sic, sac;
    private Double timeOfDay;
    private TargetReportDescriptor targetReportDescriptor;
    private PolarCoordinate polarCoordinate;
    private Mode3ACode code3A;
    private FlightLevel flightLevel;
    private RadarPlotCharacteristics radarPlotCharacteristics;

    /*
     8 I048/220 Aircraft Address 3
     9 I048/240 Aircraft Identification 6
     10 I048/250 Mode S MB Data 1+8*n
     11 I048/161 Track Number 2
     12 I048/042 Calculated Position in Cartesian Coordinates 4
     13 I048/200 Calculated Track Velocity in Polar Representation 4
     14 I048/170 Track Status 1+
     */
    private Integer targetAddress;
    private String callsign;
    private ModeSMBData modeSData;
    private Integer trackNumber;
    private CartesianCoordinate cartesianCoordinate;
    private CalculatedPolarVelocity calculatedPolarVelocity;
    private TrackStatus trackstatus;

    /*
     15 I048/210 Track Quality 4
     16 I048/030 Warning/Error Conditions 1+
     17 I048/080 Mode-3/A Code Confidence Indicator 2
     18 I048/100 Mode-C Code and Confidence Indicator 4
     19 I048/110 Height Measured by 3D Radar 2
     20 I048/120 Radial Doppler Speed 1+
     21 I048/230 Communications / ACAS Capability and Flight Status 2
     */
    private TrackQuality trackQuality;
    private WarningErrorCondition warningErrorCondition;
    private Mode3ACodeConfidenceIndicator mode3AConfidenceIndicator;
    private ModeCConfidenceIndicator modeCConfidenceIndicator;
    private Height3DRadar height3DRadar;
    private RadialDroplerSpeed droplerSpeed;
    private CommunicationCapacityAndFlightStatus communicationStatus;
    
    /*
     22 I048/260 ACAS Resolution Advisory Report 7
     23 I048/055 Mode-1 Code in Octal Representation 1
     24 I048/050 Mode-2 Code in Octal Representation 2
     25 I048/065 Mode-1 Code Confidence Indicator 1
     26 I048/060 Mode-2 Code Confidence Indicator 2
     27 SP-Data Item Special Purpose Field 1+1+
     28 RE-Data Item Reserved Expansion Field 1+1+
     */
    private ACASResolutionAdvisoryReport acasResolutionReport;
    private Mode1Code mode1Code;
    private Mode2Code mode2Code;
    private Mode1CodeConfidenceIndicator mode1ConfidenceIndicator;
    private Mode2CodeConfidenceIndicator mode2ConfidenceIndicator;

    
    /**
     * @return the sic
     */
    public Integer getSic() {
        return sic;
    }

    /**
     * @param sic the sic to set
     */
    public void setSic(Integer sic) {
        this.sic = sic;
    }

    /**
     * @return the sac
     */
    public Integer getSac() {
        return sac;
    }

    /**
     * @param sac the sac to set
     */
    public void setSac(Integer sac) {
        this.sac = sac;
    }

    /**
     * @return the timeOfDay
     */
    public Double getTimeOfDay() {
        return timeOfDay;
    }

    /**
     * @param timeOfDay the timeOfDay to set
     */
    public void setTimeOfDay(Double timeOfDay) {
        this.timeOfDay = timeOfDay;
    }

    /**
     * @return the targetReportDescriptor
     */
    public TargetReportDescriptor getTargetReportDescriptor() {
        return targetReportDescriptor;
    }

    /**
     * @param targetReportDescriptor the targetReportDescriptor to set
     */
    public void setTargetReportDescriptor(TargetReportDescriptor targetReportDescriptor) {
        this.targetReportDescriptor = targetReportDescriptor;
    }

    /**
     * @return the polarCoordinate
     */
    public PolarCoordinate getPolarCoordinate() {
        return polarCoordinate;
    }

    /**
     * @param polarCoordinate the polarCoordinate to set
     */
    public void setPolarCoordinate(PolarCoordinate polarCoordinate) {
        this.polarCoordinate = polarCoordinate;
    }

    /**
     * @return the code3A
     */
    public Mode3ACode getCode3A() {
        return code3A;
    }

    /**
     * @param code3A the code3A to set
     */
    public void setCode3A(Mode3ACode code3A) {
        this.code3A = code3A;
    }

    /**
     * @return the flightLevel
     */
    public FlightLevel getFlightLevel() {
        return flightLevel;
    }

    /**
     * @param flightLevel the flightLevel to set
     */
    public void setFlightLevel(FlightLevel flightLevel) {
        this.flightLevel = flightLevel;
    }

    /**
     * @return the radarPlotCharacteristics
     */
    public RadarPlotCharacteristics getRadarPlotCharacteristics() {
        return radarPlotCharacteristics;
    }

    /**
     * @param radarPlotCharacteristics the radarPlotCharacteristics to set
     */
    public void setRadarPlotCharacteristics(RadarPlotCharacteristics radarPlotCharacteristics) {
        this.radarPlotCharacteristics = radarPlotCharacteristics;
    }

    /**
     * @return the targetAddress
     */
    public Integer getTargetAddress() {
        return targetAddress;
    }

    /**
     * @param targetAddress the targetAddress to set
     */
    public void setTargetAddress(Integer targetAddress) {
        this.targetAddress = targetAddress;
    }

    /**
     * @return the callsign
     */
    public String getCallsign() {
        return callsign;
    }

    /**
     * @param callsign the callsign to set
     */
    public void setCallsign(String callsign) {
        this.callsign = callsign;
    }

    /**
     * @return the modeSData
     */
    public ModeSMBData getModeSData() {
        return modeSData;
    }

    /**
     * @param modeSData the modeSData to set
     */
    public void setModeSData(ModeSMBData modeSData) {
        this.modeSData = modeSData;
    }

    /**
     * @return the trackNumber
     */
    public Integer getTrackNumber() {
        return trackNumber;
    }

    /**
     * @param trackNumber the trackNumber to set
     */
    public void setTrackNumber(Integer trackNumber) {
        this.trackNumber = trackNumber;
    }

    /**
     * @return the cartesianCoordinate
     */
    public CartesianCoordinate getCartesianCoordinate() {
        return cartesianCoordinate;
    }

    /**
     * @param cartesianCoordinate the cartesianCoordinate to set
     */
    public void setCartesianCoordinate(CartesianCoordinate cartesianCoordinate) {
        this.cartesianCoordinate = cartesianCoordinate;
    }

    /**
     * @return the calculatedPolarVelocity
     */
    public CalculatedPolarVelocity getCalculatedPolarVelocity() {
        return calculatedPolarVelocity;
    }

    /**
     * @param calculatedPolarVelocity the calculatedPolarVelocity to set
     */
    public void setCalculatedPolarVelocity(CalculatedPolarVelocity calculatedPolarVelocity) {
        this.calculatedPolarVelocity = calculatedPolarVelocity;
    }

    /**
     * @return the trackstatus
     */
    public TrackStatus getTrackstatus() {
        return trackstatus;
    }

    /**
     * @param trackstatus the trackstatus to set
     */
    public void setTrackstatus(TrackStatus trackstatus) {
        this.trackstatus = trackstatus;
    }

    /**
     * @return the trackQuality
     */
    public TrackQuality getTrackQuality() {
        return trackQuality;
    }

    /**
     * @param trackQuality the trackQuality to set
     */
    public void setTrackQuality(TrackQuality trackQuality) {
        this.trackQuality = trackQuality;
    }

    /**
     * @return the warningErrorCondition
     */
    public WarningErrorCondition getWarningErrorCondition() {
        return warningErrorCondition;
    }

    /**
     * @param warningErrorCondition the warningErrorCondition to set
     */
    public void setWarningErrorCondition(WarningErrorCondition warningErrorCondition) {
        this.warningErrorCondition = warningErrorCondition;
    }

    /**
     * @return the mode3AConfidenceIndicator
     */
    public Mode3ACodeConfidenceIndicator getMode3AConfidenceIndicator() {
        return mode3AConfidenceIndicator;
    }

    /**
     * @param mode3AConfidenceIndicator the mode3AConfidenceIndicator to set
     */
    public void setMode3AConfidenceIndicator(Mode3ACodeConfidenceIndicator mode3AConfidenceIndicator) {
        this.mode3AConfidenceIndicator = mode3AConfidenceIndicator;
    }

    /**
     * @return the modeCConfidenceIndicator
     */
    public ModeCConfidenceIndicator getModeCConfidenceIndicator() {
        return modeCConfidenceIndicator;
    }

    /**
     * @param modeCConfidenceIndicator the modeCConfidenceIndicator to set
     */
    public void setModeCConfidenceIndicator(ModeCConfidenceIndicator modeCConfidenceIndicator) {
        this.modeCConfidenceIndicator = modeCConfidenceIndicator;
    }

    /**
     * @return the height3DRadar
     */
    public Height3DRadar getHeight3DRadar() {
        return height3DRadar;
    }

    /**
     * @param height3DRadar the height3DRadar to set
     */
    public void setHeight3DRadar(Height3DRadar height3DRadar) {
        this.height3DRadar = height3DRadar;
    }

    /**
     * @return the droplerSpeed
     */
    public RadialDroplerSpeed getDroplerSpeed() {
        return droplerSpeed;
    }

    /**
     * @param droplerSpeed the droplerSpeed to set
     */
    public void setDroplerSpeed(RadialDroplerSpeed droplerSpeed) {
        this.droplerSpeed = droplerSpeed;
    }

    /**
     * @return the communicationStatus
     */
    public CommunicationCapacityAndFlightStatus getCommunicationStatus() {
        return communicationStatus;
    }

    /**
     * @param communicationStatus the communicationStatus to set
     */
    public void setCommunicationStatus(CommunicationCapacityAndFlightStatus communicationStatus) {
        this.communicationStatus = communicationStatus;
    }

    /**
     * @return the acasResolutionReport
     */
    public ACASResolutionAdvisoryReport getAcasResolutionReport() {
        return acasResolutionReport;
    }

    /**
     * @param acasResolutionReport the acasResolutionReport to set
     */
    public void setAcasResolutionReport(ACASResolutionAdvisoryReport acasResolutionReport) {
        this.acasResolutionReport = acasResolutionReport;
    }

    /**
     * @return the mode1Code
     */
    public Mode1Code getMode1Code() {
        return mode1Code;
    }

    /**
     * @param mode1Code the mode1Code to set
     */
    public void setMode1Code(Mode1Code mode1Code) {
        this.mode1Code = mode1Code;
    }

    /**
     * @return the mode2Code
     */
    public Mode2Code getMode2Code() {
        return mode2Code;
    }

    /**
     * @param mode2Code the mode2Code to set
     */
    public void setMode2Code(Mode2Code mode2Code) {
        this.mode2Code = mode2Code;
    }

    /**
     * @return the mode1ConfidenceIndicator
     */
    public Mode1CodeConfidenceIndicator getMode1ConfidenceIndicator() {
        return mode1ConfidenceIndicator;
    }

    /**
     * @param mode1ConfidenceIndicator the mode1ConfidenceIndicator to set
     */
    public void setMode1ConfidenceIndicator(Mode1CodeConfidenceIndicator mode1ConfidenceIndicator) {
        this.mode1ConfidenceIndicator = mode1ConfidenceIndicator;
    }

    /**
     * @return the mode2ConfidenceIndicator
     */
    public Mode2CodeConfidenceIndicator getMode2ConfidenceIndicator() {
        return mode2ConfidenceIndicator;
    }

    /**
     * @param mode2ConfidenceIndicator the mode2ConfidenceIndicator to set
     */
    public void setMode2ConfidenceIndicator(Mode2CodeConfidenceIndicator mode2ConfidenceIndicator) {
        this.mode2ConfidenceIndicator = mode2ConfidenceIndicator;
    }

    /**
     * @return the binary
     */
    public byte[] getBinary() {
        return binary;
    }

    /**
     * @param binary the binary to set
     */
    public void setBinary(byte[] binary) {
        this.binary = binary;
    }
    
    public void print() {
        System.out.printf("Asterix message, #48, length %s%n", this.binary.length);
        if (sic != null) System.out.printf("\tSIC: %s%n", sic);
        if (sac != null) System.out.printf("\tSAC: %s%n", sac);
        if (timeOfDay != null) System.out.printf("\tTime Of Day: %s%n", timeOfDay);
        if (targetReportDescriptor != null) targetReportDescriptor.print(1);
        if (polarCoordinate != null) polarCoordinate.print(1);
        if (code3A != null) code3A.print(1);
        if (flightLevel != null) flightLevel.print(1);
        if (radarPlotCharacteristics != null) radarPlotCharacteristics.print(1);
        
        if (targetAddress != null) System.out.println(String.format("\t24bit Address: %s", targetAddress));
        if (callsign != null) System.out.println(String.format("\tCallsign: %s", callsign));
        if (modeSData != null) modeSData.print(1);
        if (trackNumber != null) System.out.println(String.format("\tTrack no: %s", trackNumber));
        if (cartesianCoordinate != null) cartesianCoordinate.print(1);
        if (calculatedPolarVelocity != null) calculatedPolarVelocity.print(1);
        if (trackstatus != null) trackstatus.print(1);
        
        if (trackQuality != null) trackQuality.print(1);
        if (warningErrorCondition != null) warningErrorCondition.print(1);
        if (mode3AConfidenceIndicator != null) mode3AConfidenceIndicator.print(1);
        if (modeCConfidenceIndicator != null) modeCConfidenceIndicator.print(1);
        if (height3DRadar != null) height3DRadar.print(1);
        if (droplerSpeed != null) droplerSpeed.print(1);
        if (communicationStatus != null) communicationStatus.print(1);
        
        if (acasResolutionReport != null) acasResolutionReport.print(1);
        if (mode1Code != null) mode1Code.print(1);
        if (mode2Code != null) mode2Code.print(1);
        if (mode1ConfidenceIndicator != null) mode1ConfidenceIndicator.print(1);
        if (mode2ConfidenceIndicator != null) mode2ConfidenceIndicator.print(1);

        printBinary() ;
    }
    
    public void printBinary() {
        System.out.println("Binary: " + this.binary.length);
        for (int i=0; i<this.binary.length; i++) {
            System.out.print(" " + Integer.toHexString(this.binary[i] & 0xFF));
            if (i%8 == 0 && i != 0) {
                System.out.println("\t");
            }
        }
        System.out.println("\t");
    }
    
    public Object[] getValueArray() throws IllegalArgumentException, IllegalAccessException{
        List<Object> list = new ArrayList<>();
        for (Field field: this.getClass().getDeclaredFields()){
            if (field.getName().equals("targetAddress")){
                list.add(this.targetAddress != null ? Integer.toHexString(this.targetAddress).toUpperCase() : "");
                continue;
            }
            if (field.getName().equals("targetReportDescriptor")){
                list.add(this.targetReportDescriptor != null ? this.targetReportDescriptor.getTypeStr() : "");
                continue;
            }
            if (field.getName().equals("binary") || field.getName().equals("radarPlotCharacteristics") || field.getName().equals("modeSData")){ //
                list.add("");
                continue;
            }
            list.add(field.get(this));
        }
        return list.toArray();
    }
}
