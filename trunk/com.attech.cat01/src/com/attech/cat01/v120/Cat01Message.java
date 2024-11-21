/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.attech.cat01.v120;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author andh
 */
@XmlRootElement(name = "Cat01")
@XmlAccessorType(XmlAccessType.NONE)
public class Cat01Message {
    
    @XmlElement(name = "binary")
    private byte [] binary;
    
    /*
    1	I001/010	Data Source Identifier
    2	I001/020	Target Report Descriptor
    3	I001/161	Track/Plot Number
    4	I001/040	Measured Position in Polar Coordinates
    5	I001/042	Calculated Position in Cartesian Coordinates
    6	I001/200	Calculated Track Velocity in polar Coordinates
    7	I001/070	Mode-3/A Code in Octal Representation
    */
    @XmlElement(name = "sic")
    private Integer sic;
    
    @XmlElement(name = "sac")
    private Integer sac;
    
    @XmlElement(name = "TargetReportDescriptor")
    private TargetReportDescriptor targetDescriptor;
    
    private Integer condition;  // ???
    
    @XmlElement(name = "No")
    private Integer no;
    
    @XmlElement(name = "PolarCoordinate")
    private PolarCoordinate polarCoordinate;
    
    @XmlElement(name = "CartesianCoordinate")
    private CartesianCoordinate cartesionCoordinate;
    
    @XmlElement(name = "CalculatedPolarVelocity")
    private CalculatedPolarVelocity polarVelocity;
    
    @XmlElement(name = "Mode3ACode")
    private Mode3ACode mode3ACode;
    
    /*
    8	I001/090	Mode-C Code in Binary Representation
    9	I001/141	Truncated Time of Day
    10	I001/130	Radar Plot Characteristics
    11	I001/131	Received Power
    12	I001/120	Measured Radial Doppler Speed
    13	I001/170	Track Status
    14	I001/210	Track Quality
    */
    @XmlElement(name = "ModeC")
    private ModeC modeC;
    
    @XmlElement(name = "TimeOfDay")
    private Double timeOfDay;
    
    @XmlElement(name = "PlotCharateristic")
    private Integer plotChar;
    
    @XmlElement(name = "ReceivedPower")
    private Integer receivedPower;
    
    @XmlElement(name = "DroplerSpeed")
    private Double droplerSpeed;
    
    @XmlElement(name = "Status")
    private Status status;
    
    @XmlElement(name = "Qualitys")
    private Integer quality;
    
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
    @XmlElement(name = "Mode2")
    private Mode2Code mode2Code;
    
    @XmlElement(name = "Mode3ACodeConfidenceIndicator")
    private Mode3ACodeConfidenceIndicator mode3AConfidenceIndicator;
    
    @XmlElement(name = "ModeCConfidenceIndicator")
    private ModeCConfidenceIndicator modeCConfidenceIndicator;
    
    @XmlElement(name = "Mode2CodeConfidenceIndicator")
    private Mode2CodeConfidenceIndicator mode2CConfidenceIndicator;
    
    @XmlElement(name = "WarningErrorCondition")
    private WarningErrorCondition warningErrorCondition;
    
    @XmlElement(name = "Xpulse")
    private Xpulse xpulse;
    

    /**
     * @return the sic
     */
    public Integer getSic() {
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
    public Integer getSac() {
        return sac;
    }

    /**
     * @param sac the sac to set
     */
    public void setSac(int sac) {
        this.sac = sac;
    }

    /**
     * @return the targetDescriptor
     */
    public TargetReportDescriptor getTargetDescriptor() {
        return targetDescriptor;
    }

    /**
     * @param targetDescriptor the targetDescriptor to set
     */
    public void setTargetDescriptor(TargetReportDescriptor targetDescriptor) {
        this.targetDescriptor = targetDescriptor;
    }

    /**
     * @return the condition
     */
    public Integer getCondition() {
        return condition;
    }

    /**
     * @param condition the condition to set
     */
    public void setCondition(Integer condition) {
        this.condition = condition;
    }

    /**
     * @return the no
     */
    public Integer getNo() {
        return no;
    }

    /**
     * @param no the no to set
     */
    public void setNo(Integer no) {
        this.no = no;
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
     * @return the cartesionCoordinate
     */
    public CartesianCoordinate getCartesionCoordinate() {
        return cartesionCoordinate;
    }

    /**
     * @param cartesionCoordinate the cartesionCoordinate to set
     */
    public void setCartesionCoordinate(CartesianCoordinate cartesionCoordinate) {
        this.cartesionCoordinate = cartesionCoordinate;
    }

    /**
     * @return the mode3ACode
     */
    public Mode3ACode getMode3ACode() {
        return mode3ACode;
    }

    /**
     * @param mode3ACode the mode3ACode to set
     */
    public void setMode3ACode(Mode3ACode mode3ACode) {
        this.mode3ACode = mode3ACode;
    }

    /**
     * @return the modeC
     */
    public ModeC getModeC() {
        return modeC;
    }

    /**
     * @param modeC the modeC to set
     */
    public void setModeC(ModeC modeC) {
        this.modeC = modeC;
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
     * @return the plotChar
     */
    public Integer getPlotChar() {
        return plotChar;
    }

    /**
     * @param plotChar the plotChar to set
     */
    public void setPlotChar(Integer plotChar) {
        this.plotChar = plotChar;
    }

    /**
     * @return the receivedPower
     */
    public Integer getReceivedPower() {
        return receivedPower;
    }

    /**
     * @param receivedPower the receivedPower to set
     */
    public void setReceivedPower(Integer receivedPower) {
        this.receivedPower = receivedPower;
    }

    /**
     * @return the droplerSpeed
     */
    public Double getDroplerSpeed() {
        return droplerSpeed;
    }

    /**
     * @param droplerSpeed the droplerSpeed to set
     */
    public void setDroplerSpeed(Double droplerSpeed) {
        this.droplerSpeed = droplerSpeed;
    }

    /**
     * @return the status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * @return the quality
     */
    public Integer getQuality() {
        return quality;
    }

    /**
     * @param quality the quality to set
     */
    public void setQuality(Integer quality) {
        this.quality = quality;
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
     * @return the mode2CConfidenceIndicator
     */
    public Mode2CodeConfidenceIndicator getMode2CConfidenceIndicator() {
        return mode2CConfidenceIndicator;
    }

    /**
     * @param mode2CConfidenceIndicator the mode2CConfidenceIndicator to set
     */
    public void setMode2CConfidenceIndicator(Mode2CodeConfidenceIndicator mode2CConfidenceIndicator) {
        this.mode2CConfidenceIndicator = mode2CConfidenceIndicator;
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
     * @return the xpulse
     */
    public Xpulse getXpulse() {
        return xpulse;
    }

    /**
     * @param xpulse the xpulse to set
     */
    public void setXpulse(Xpulse xpulse) {
        this.xpulse = xpulse;
    }

    /**
     * @return the polarVelocity
     */
    public CalculatedPolarVelocity getPolarVelocity() {
        return polarVelocity;
    }

    /**
     * @param polarVelocity the polarVelocity to set
     */
    public void setPolarVelocity(CalculatedPolarVelocity polarVelocity) {
        this.polarVelocity = polarVelocity;
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
    
    
    /*
    1	I001/010	- Data Source Identifier
    2	I001/020	Target Report Descriptor
    3	I001/040	Measured Position in Polar Coordinates
    4	I001/070	Mode-3/A Code in Octal Representation
    5	I001/090	Mode-C Code in Binary Representation
    6	I001/130	Radar Plot Characteristics
    7	I001/141	Truncated Time of Day
    FX		Field Extension Indicator
    8	I001/050	- Mode-2 Code in Octal Representation
    9	I001/120	Measured Radial Doppler Speed
    10	I001/131	Received Power
    11	I001/080	Mode-3/A Code Confidence Indicator
    12	I001/100	Mode-C Code and Code Confidence Indicator
    13	I001/060	Mode-2 Code Confidence Indicator
    14	I001/030	Warning/Error Conditions
    FX		Field Extension Indicator
    15	I001/150	- Presence of X-Pulse
    16	-	Spare
    17	-	Spare
    18	-	Spare
    19	-	Spare
    20	-	Reserved for SP Indicator
    21		Reserved for Random Field Sequencing (RFS) Indicator (RS-bit)
    FX		Field Extension Indicator
    */
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        if (sic != null || sac != null) builder.append(String.format("[sic: %s sac: %s]", this.sic, this.sac));
        if (targetDescriptor != null) builder.append(this.targetDescriptor);
        if (condition != null) builder.append(String.format("[condition: %s]", this.condition));
        if (no != null) builder.append(String.format("[no: %s]", this.no));
        if (polarCoordinate != null) builder.append(polarCoordinate);
        if (cartesionCoordinate != null) builder.append(cartesionCoordinate);
        if (polarVelocity != null) builder.append(polarVelocity);
        if (mode3ACode != null) builder.append(mode3ACode);
        if (modeC != null) builder.append(modeC);
        if (timeOfDay != null) builder.append(String.format("[Time: %s]", this.timeOfDay));
        if (plotChar != null) builder.append(String.format("[PlotChar: %s]", this.plotChar));
        if (receivedPower != null) builder.append(String.format("[ReceivedPower: %s]", this.receivedPower));
        if (droplerSpeed != null) builder.append(String.format("[DroplerSpeed: %s]", this.droplerSpeed));
        if (status != null) builder.append(this.status);
        if (quality != null) builder.append(String.format("[Quality: %s]", this.quality));
        if (mode2Code != null) builder.append(this.mode2Code);
        if (mode3AConfidenceIndicator != null) builder.append(this.mode3AConfidenceIndicator);
        if (modeCConfidenceIndicator != null) builder.append(this.modeCConfidenceIndicator);
        if (mode2CConfidenceIndicator != null) builder.append(this.mode2CConfidenceIndicator);
        if (warningErrorCondition != null) builder.append(this.warningErrorCondition);
        if (xpulse != null) builder.append(this.xpulse);
        return builder.toString();
    }
    
    public void print() {
        System.out.println(String.format("Asterix message CAT01, length %s\n", this.binary.length));
        if (sic != null) System.out.println(String.format("\tSIC: %s", sic));
        if (sac != null) System.out.println(String.format("\tSAC: %s", sac));
        if (targetDescriptor != null) targetDescriptor.print(1);
        if (no != null) System.out.println(String.format("\tNo: %s", no));
        if (polarCoordinate != null) polarCoordinate.print(1);
        if (cartesionCoordinate != null) cartesionCoordinate.print(1);
        if (polarVelocity != null) polarVelocity.print(1);
        if (mode3ACode != null) mode3ACode.print(1);
        if (modeC != null) modeC.print(1);
        if (timeOfDay != null) System.out.println(String.format("\tTime of Day: %s", timeOfDay));
        if (plotChar != null) System.out.println(String.format("\tPlot characteristic: %s", plotChar));
        if (receivedPower != null) System.out.println(String.format("\tReceived power: %s", receivedPower));
        if (droplerSpeed != null) System.out.println(String.format("\tDropler speed: %s", droplerSpeed));
        if (status != null) status.print(1);
        if (quality != null) System.out.println(String.format("\tQuality: %s", quality));
        if (mode2Code != null) mode2Code.print(1);
        if (mode3AConfidenceIndicator != null) mode3AConfidenceIndicator.print(1);
        if (modeCConfidenceIndicator != null) modeCConfidenceIndicator.print(1);
        if (mode2CConfidenceIndicator != null) mode2CConfidenceIndicator.print(1);
        if (warningErrorCondition != null) warningErrorCondition.print(1);
        if (xpulse != null) xpulse.print(1);
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
            if (field.getName().equals("binary") || field.getName().equals("status")){
                list.add("");
                continue;
            }
            list.add(field.get(this));
        }
        return list.toArray();
    }
}
