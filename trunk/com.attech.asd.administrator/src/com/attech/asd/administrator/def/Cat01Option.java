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
public class Cat01Option implements Serializable{
        /*
    1	I001/010	Data Source Identifier
    2	I001/020	Target Report Descriptor
    3	I001/161	Track/Plot Number
    4	I001/040	Measured Position in Polar Coordinates
    5	I001/042	Calculated Position in Cartesian Coordinates
    6	I001/200	Calculated Track Velocity in polar Coordinates
    7	I001/070	Mode-3/A Code in Octal Representation
    */
    private boolean sic;
    private boolean sac;
    private boolean targetDescriptor;  
    private boolean condition;  // ???
    private boolean no;
    private boolean polarCoordinate;
    private boolean cartesionCoordinate;
    private boolean polarVelocity;
    private boolean mode3ACode;
    /*
    8	I001/090	Mode-C Code in Binary Representation
    9	I001/141	Truncated Time of Day
    10	I001/130	Radar Plot Characteristics
    11	I001/131	Received Power
    12	I001/120	Measured Radial Doppler Speed
    13	I001/170	Track Status
    14	I001/210	Track Quality
    */
    private boolean modeC;
    private boolean timeOfDay;
    private boolean plotChar;
    private boolean receivedPower;
    private boolean droplerSpeed;
    private boolean status;
    private boolean quality;
    
    /*
    15	I001/050	Mode-2 Code in Octal Representation
    16	I001/080	Mode-3/A Code Confidence Indicator
    17	I001/100	Mode-C Code and Code Confidence Indicator
    18	I001/060	Mode-2 Code Confidence Indicator
    19	I001/030	Warning/Error Conditions
    20	-	Reserved for Special Purpose Indicator (SP)
    21	-	Reserved for RFS Indicator (RS-bit)
                    
    22 I001/150 Presence of X-Pulse 1	
    */
    private boolean mode2Code;
    private boolean mode3AConfidenceIndicator;
    private boolean modeCConfidenceIndicator;
    private boolean mode2CConfidenceIndicator;
    private boolean warningErrorCondition;
    private boolean xpulse;

    public Cat01Option() {
    }

    public void setSic(boolean sic) {
        this.sic = sic;
    }

    public void setSac(boolean sac) {
        this.sac = sac;
    }

    public void setTargetDescriptor(boolean targetDescriptor) {
        this.targetDescriptor = targetDescriptor;
    }

    public void setCondition(boolean condition) {
        this.condition = condition;
    }

    public void setNo(boolean no) {
        this.no = no;
    }

    public void setPolarCoordinate(boolean polarCoordinate) {
        this.polarCoordinate = polarCoordinate;
    }

    public void setCartesionCoordinate(boolean cartesionCoordinate) {
        this.cartesionCoordinate = cartesionCoordinate;
    }

    public void setPolarVelocity(boolean polarVelocity) {
        this.polarVelocity = polarVelocity;
    }

    public void setMode3ACode(boolean mode3ACode) {
        this.mode3ACode = mode3ACode;
    }

    public void setModeC(boolean modeC) {
        this.modeC = modeC;
    }

    public void setTimeOfDay(boolean timeOfDay) {
        this.timeOfDay = timeOfDay;
    }

    public void setPlotChar(boolean plotChar) {
        this.plotChar = plotChar;
    }

    public void setReceivedPower(boolean receivedPower) {
        this.receivedPower = receivedPower;
    }

    public void setDroplerSpeed(boolean droplerSpeed) {
        this.droplerSpeed = droplerSpeed;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setQuality(boolean quality) {
        this.quality = quality;
    }

    public void setMode2Code(boolean mode2Code) {
        this.mode2Code = mode2Code;
    }

    public void setMode3AConfidenceIndicator(boolean mode3AConfidenceIndicator) {
        this.mode3AConfidenceIndicator = mode3AConfidenceIndicator;
    }

    public void setModeCConfidenceIndicator(boolean modeCConfidenceIndicator) {
        this.modeCConfidenceIndicator = modeCConfidenceIndicator;
    }

    public void setMode2CConfidenceIndicator(boolean mode2CConfidenceIndicator) {
        this.mode2CConfidenceIndicator = mode2CConfidenceIndicator;
    }

    public void setWarningErrorCondition(boolean warningErrorCondition) {
        this.warningErrorCondition = warningErrorCondition;
    }

    public void setXpulse(boolean xpulse) {
        this.xpulse = xpulse;
    }

    public boolean isSic() {
        return sic;
    }

    public boolean isSac() {
        return sac;
    }

    public boolean isTargetDescriptor() {
        return targetDescriptor;
    }

    public boolean isCondition() {
        return condition;
    }

    public boolean isNo() {
        return no;
    }

    public boolean isPolarCoordinate() {
        return polarCoordinate;
    }

    public boolean isCartesionCoordinate() {
        return cartesionCoordinate;
    }

    public boolean isPolarVelocity() {
        return polarVelocity;
    }

    public boolean isMode3ACode() {
        return mode3ACode;
    }

    public boolean isModeC() {
        return modeC;
    }

    public boolean isTimeOfDay() {
        return timeOfDay;
    }

    public boolean isPlotChar() {
        return plotChar;
    }

    public boolean isReceivedPower() {
        return receivedPower;
    }

    public boolean isDroplerSpeed() {
        return droplerSpeed;
    }

    public boolean isStatus() {
        return status;
    }

    public boolean isQuality() {
        return quality;
    }

    public boolean isMode2Code() {
        return mode2Code;
    }

    public boolean isMode3AConfidenceIndicator() {
        return mode3AConfidenceIndicator;
    }

    public boolean isModeCConfidenceIndicator() {
        return modeCConfidenceIndicator;
    }

    public boolean isMode2CConfidenceIndicator() {
        return mode2CConfidenceIndicator;
    }

    public boolean isWarningErrorCondition() {
        return warningErrorCondition;
    }

    public boolean isXpulse() {
        return xpulse;
    }
    
    
}
