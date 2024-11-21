/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.administrator.def;

import java.io.Serializable;

/**
 *
 * @author CanhVu
 */
public class Cat48Option implements Serializable{
    /*
     1 I048/010 Data Source Identifier 2
     2 I048/140 Time-of-Day 3
     3 I048/020 Target Report Descriptor 1+
     4 I048/040 Measured Position in Slant Polar Coordinates 4
     5 I048/070 Mode-3/A Code in Octal Representation 2
     6 I048/090 Flight Level in Binary Representation 2
     7 I048/130 Radar Plot Characteristics 1+1+
     */

    private boolean sic, sac;
    private boolean timeOfDay;
    private boolean targetReportDescriptor;
    private boolean polarCoordinate;
    private boolean code3A;
    private boolean flightLevel;
    private boolean radarPlotCharacteristics;

    /*
     8 I048/220 Aircraft Address 3
     9 I048/240 Aircraft Identification 6
     10 I048/250 Mode S MB Data 1+8*n
     11 I048/161 Track Number 2
     12 I048/042 Calculated Position in Cartesian Coordinates 4
     13 I048/200 Calculated Track Velocity in Polar Representation 4
     14 I048/170 Track Status 1+
     */
    private boolean targetAddress;
    private boolean callsign;
    private boolean modeSData;
    private boolean trackNumber;
    private boolean cartesianCoordinate;
    private boolean calculatedPolarVelocity;
    private boolean trackstatus;

    /*
     15 I048/210 Track Quality 4
     16 I048/030 Warning/Error Conditions 1+
     17 I048/080 Mode-3/A Code Confidence Indicator 2
     18 I048/100 Mode-C Code and Confidence Indicator 4
     19 I048/110 Height Measured by 3D Radar 2
     20 I048/120 Radial Doppler Speed 1+
     21 I048/230 Communications / ACAS Capability and Flight Status 2
     */
    private boolean trackQuality;
    private boolean warningErrorCondition;
    private boolean mode3AConfidenceIndicator;
    private boolean modeCConfidenceIndicator;
    private boolean height3DRadar;
    private boolean droplerSpeed;
    private boolean communicationStatus;
    
    /*
     22 I048/260 ACAS Resolution Advisory Report 7
     23 I048/055 Mode-1 Code in Octal Representation 1
     24 I048/050 Mode-2 Code in Octal Representation 2
     25 I048/065 Mode-1 Code Confidence Indicator 1
     26 I048/060 Mode-2 Code Confidence Indicator 2
     27 SP-Data Item Special Purpose Field 1+1+
     28 RE-Data Item Reserved Expansion Field 1+1+
     */
    private boolean acasResolutionReport;
    private boolean mode1Code;
    private boolean mode2Code;
    private boolean mode1ConfidenceIndicator;
    private boolean mode2ConfidenceIndicator;

    public Cat48Option() {
    }

    public boolean isSic() {
        return sic;
    }

    public boolean isSac() {
        return sac;
    }

    public boolean isTimeOfDay() {
        return timeOfDay;
    }

    public boolean isTargetReportDescriptor() {
        return targetReportDescriptor;
    }

    public boolean isPolarCoordinate() {
        return polarCoordinate;
    }

    public boolean isCode3A() {
        return code3A;
    }

    public boolean isFlightLevel() {
        return flightLevel;
    }

    public boolean isRadarPlotCharacteristics() {
        return radarPlotCharacteristics;
    }

    public boolean isTargetAddress() {
        return targetAddress;
    }

    public boolean isCallsign() {
        return callsign;
    }

    public boolean isModeSData() {
        return modeSData;
    }

    public boolean isTrackNumber() {
        return trackNumber;
    }

    public boolean isCartesianCoordinate() {
        return cartesianCoordinate;
    }

    public boolean isCalculatedPolarVelocity() {
        return calculatedPolarVelocity;
    }

    public boolean isTrackstatus() {
        return trackstatus;
    }

    public boolean isTrackQuality() {
        return trackQuality;
    }

    public boolean isWarningErrorCondition() {
        return warningErrorCondition;
    }

    public boolean isMode3AConfidenceIndicator() {
        return mode3AConfidenceIndicator;
    }

    public boolean isModeCConfidenceIndicator() {
        return modeCConfidenceIndicator;
    }

    public boolean isHeight3DRadar() {
        return height3DRadar;
    }

    public boolean isDroplerSpeed() {
        return droplerSpeed;
    }

    public boolean isCommunicationStatus() {
        return communicationStatus;
    }

    public boolean isAcasResolutionReport() {
        return acasResolutionReport;
    }

    public boolean isMode1Code() {
        return mode1Code;
    }

    public boolean isMode2Code() {
        return mode2Code;
    }

    public boolean isMode1ConfidenceIndicator() {
        return mode1ConfidenceIndicator;
    }

    public boolean isMode2ConfidenceIndicator() {
        return mode2ConfidenceIndicator;
    }

    public void setSic(boolean sic) {
        this.sic = sic;
    }

    public void setSac(boolean sac) {
        this.sac = sac;
    }

    public void setTimeOfDay(boolean timeOfDay) {
        this.timeOfDay = timeOfDay;
    }

    public void setTargetReportDescriptor(boolean targetReportDescriptor) {
        this.targetReportDescriptor = targetReportDescriptor;
    }

    public void setPolarCoordinate(boolean polarCoordinate) {
        this.polarCoordinate = polarCoordinate;
    }

    public void setCode3A(boolean code3A) {
        this.code3A = code3A;
    }

    public void setFlightLevel(boolean flightLevel) {
        this.flightLevel = flightLevel;
    }

    public void setRadarPlotCharacteristics(boolean radarPlotCharacteristics) {
        this.radarPlotCharacteristics = radarPlotCharacteristics;
    }

    public void setTargetAddress(boolean targetAddress) {
        this.targetAddress = targetAddress;
    }

    public void setCallsign(boolean callsign) {
        this.callsign = callsign;
    }

    public void setModeSData(boolean modeSData) {
        this.modeSData = modeSData;
    }

    public void setTrackNumber(boolean trackNumber) {
        this.trackNumber = trackNumber;
    }

    public void setCartesianCoordinate(boolean cartesianCoordinate) {
        this.cartesianCoordinate = cartesianCoordinate;
    }

    public void setCalculatedPolarVelocity(boolean calculatedPolarVelocity) {
        this.calculatedPolarVelocity = calculatedPolarVelocity;
    }

    public void setTrackstatus(boolean trackstatus) {
        this.trackstatus = trackstatus;
    }

    public void setTrackQuality(boolean trackQuality) {
        this.trackQuality = trackQuality;
    }

    public void setWarningErrorCondition(boolean warningErrorCondition) {
        this.warningErrorCondition = warningErrorCondition;
    }

    public void setMode3AConfidenceIndicator(boolean mode3AConfidenceIndicator) {
        this.mode3AConfidenceIndicator = mode3AConfidenceIndicator;
    }

    public void setModeCConfidenceIndicator(boolean modeCConfidenceIndicator) {
        this.modeCConfidenceIndicator = modeCConfidenceIndicator;
    }

    public void setHeight3DRadar(boolean height3DRadar) {
        this.height3DRadar = height3DRadar;
    }

    public void setDroplerSpeed(boolean droplerSpeed) {
        this.droplerSpeed = droplerSpeed;
    }

    public void setCommunicationStatus(boolean communicationStatus) {
        this.communicationStatus = communicationStatus;
    }

    public void setAcasResolutionReport(boolean acasResolutionReport) {
        this.acasResolutionReport = acasResolutionReport;
    }

    public void setMode1Code(boolean mode1Code) {
        this.mode1Code = mode1Code;
    }

    public void setMode2Code(boolean mode2Code) {
        this.mode2Code = mode2Code;
    }

    public void setMode1ConfidenceIndicator(boolean mode1ConfidenceIndicator) {
        this.mode1ConfidenceIndicator = mode1ConfidenceIndicator;
    }

    public void setMode2ConfidenceIndicator(boolean mode2ConfidenceIndicator) {
        this.mode2ConfidenceIndicator = mode2ConfidenceIndicator;
    }
    
}
