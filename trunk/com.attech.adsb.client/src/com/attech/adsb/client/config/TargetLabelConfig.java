/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.config;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author andh
 */
@XmlRootElement(name = "Target-Label")
@XmlAccessorType(XmlAccessType.NONE)
public class TargetLabelConfig {



    @XmlElement(name = "TextHeight")
    private float textHeight = 25;
    
    @XmlElement(name = "CallsignWidth")
    private float callsignWidth = 150;
    
    @XmlElement(name = "AltitudeWidth")
    private float altitudeWidth = 145;
    
    @XmlElement(name = "SpeedWidth")
    private float speedWidth = 145;
    
    @XmlElement(name = "HDGNoteWidth")
    private float hdgNoteWidth = 50;
    
    @XmlElement(name = "ConnectorSize")
    private float connectorSize = 120;
    
    @XmlElement(name = "ConnectorWeight")
    private float connectorWeight;
    
    @XmlElement(name = "ConnectorStyle")
    private String connectorStyle;
    
    @XmlElement(name = "ConnectorColor")
    private String connectorColor;
    
    @XmlElement(name = "Color")
    private String color;
    
    @XmlElement(name = "ObsoletedColor")
    private String obsoletedColor;

    @XmlElement(name = "WarningColor")
    private String warningColor;
    
    @XmlElement(name = "ControlledColor")
    private String controlledColor;

    @XmlElement(name = "TransferToColor")
    private String transferToColor;

    @XmlElement(name = "TransferFromColor")
    private String transferFromColor;
    
    @XmlElement(name = "FontSize")
    private Integer fontSize;
    
    @XmlElement(name = "ConnectorDefaultAngle")
    private float connectorAngle = 30;
    
    @XmlElement(name = "ConnectorAngleStep")
    private float connectorAngleStep = 30;
    
    @XmlElement(name = "LabelHeight")
    private float labelHeight = 100;
    
    @XmlElement(name = "LabelWidth")
    private float labelWidth = 290;

     @XmlElement(name = "ZIndex")
    private float zindex;

    public TargetLabelConfig() {
    }

    //<editor-fold defaultstate="collapsed" desc=" Class properties ">
    /**
     * @return the textHeight
     */
    public float getTextHeight() {
        return textHeight;
    }

    /**
     * @param textHeight the textHeight to set
     */
    public void setTextHeight(float textHeight) {
        this.textHeight = textHeight;
    }

    /**
     * @return the callsignWidth
     */
    public float getCallsignWidth() {
        return callsignWidth;
    }

    /**
     * @param callsignWidth the callsignWidth to set
     */
    public void setCallsignWidth(float callsignWidth) {
        this.callsignWidth = callsignWidth;
    }

    /**
     * @return the altitudeWidth
     */
    public float getAltitudeWidth() {
        return altitudeWidth;
    }

    /**
     * @param altitudeWidth the altitudeWidth to set
     */
    public void setAltitudeWidth(float altitudeWidth) {
        this.altitudeWidth = altitudeWidth;
    }

    /**
     * @return the speedWidth
     */
    public float getSpeedWidth() {
        return speedWidth;
    }

    /**
     * @param speedWidth the speedWidth to set
     */
    public void setSpeedWidth(float speedWidth) {
        this.speedWidth = speedWidth;
    }

    /**
     * @return the hdgNoteWidth
     */
    public float getHdgNoteWidth() {
        return hdgNoteWidth;
    }

    /**
     * @param hdgNoteWidth the hdgNoteWidth to set
     */
    public void setHdgNoteWidth(float hdgNoteWidth) {
        this.hdgNoteWidth = hdgNoteWidth;
    }

    /**
     * @return the connectorSize
     */
    public float getConnectorSize() {
        return connectorSize;
    }

    /**
     * @param connectorSize the connectorSize to set
     */
    public void setConnectorSize(float connectorSize) {
        this.connectorSize = connectorSize;
    }

    /**
     * @return the connectorWeight
     */
    public float getConnectorWeight() {
        return connectorWeight;
    }

    /**
     * @param connectorWeight the connectorWeight to set
     */
    public void setConnectorWeight(float connectorWeight) {
        this.connectorWeight = connectorWeight;
    }

    /**
     * @return the connectorStyle
     */
    public String getConnectorStyle() {
        return connectorStyle;
    }

    /**
     * @param connectorStyle the connectorStyle to set
     */
    public void setConnectorStyle(String connectorStyle) {
        this.connectorStyle = connectorStyle;
    }

    /**
     * @return the connectorColor
     */
    public String getConnectorColor() {
        return connectorColor;
    }

    /**
     * @param connectorColor the connectorColor to set
     */
    public void setConnectorColor(String connectorColor) {
        this.connectorColor = connectorColor;
    }

    /**
     * @return the fontSize
     */
    public Integer getFontSize() {
        return fontSize;
    }

    /**
     * @param fontSize the fontSize to set
     */
    public void setFontSize(Integer fontSize) {
        this.fontSize = fontSize;
    }

    /**
     * @return the connectorAngle
     */
    public float getConnectorAngle() {
        return connectorAngle;
    }

    /**
     * @param connectorAngle the connectorAngle to set
     */
    public void setConnectorAngle(float connectorAngle) {
        this.connectorAngle = connectorAngle;
    }

    /**
     * @return the connectorAngleStep
     */
    public float getConnectorAngleStep() {
        return connectorAngleStep;
    }

    /**
     * @param connectorAngleStep the connectorAngleStep to set
     */
    public void setConnectorAngleStep(float connectorAngleStep) {
        this.connectorAngleStep = connectorAngleStep;
    }

    /**
     * @return the labelHeight
     */
    public float getLabelHeight() {
        return labelHeight;
    }

    /**
     * @param labelHeight the labelHeight to set
     */
    public void setLabelHeight(float labelHeight) {
        this.labelHeight = labelHeight;
    }

    /**
     * @return the labelWidth
     */
    public float getLabelWidth() {
        return labelWidth;
    }

    public void setLabelWidth(float labelWidth) {
        this.labelWidth = labelWidth;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getControlledColor() {
        return controlledColor;
    }

    public void setControlledColor(String controlledColor) {
        this.controlledColor = controlledColor;
    }

     public String getTransferToColor() {
        return transferToColor;
    }

    public void setTransferToColor(String transferToColor) {
        this.transferToColor = transferToColor;
    }

    public String getTransferFromColor() {
        return transferFromColor;
    }

    public void setTransferFromColor(String transferFromColor) {
        this.transferFromColor = transferFromColor;
    }
   
    /**
     * @return the warningColor
     */
    public String getWarningColor() {
        return warningColor;
    }

    /**
     * @param warningColor the warningColor to set
     */
    public void setWarningColor(String warningColor) {
        this.warningColor = warningColor;
    }
    
    
    /**
     * @return the obsoletedColor
     */
    public String getObsoletedColor() {
        return obsoletedColor;
    }

    /**
     * @param obsoletedColor the obsoletedColor to set
     */
    public void setObsoletedColor(String obsoletedColor) {
        this.obsoletedColor = obsoletedColor;
    }
    
    
    /**
     * @return the zindex
     */
    public float getZindex() {
        return zindex;
    }

    /**
     * @param zindex the zindex to set
     */
    public void setZindex(float zindex) {
        this.zindex = zindex;
    }

    //</editor-fold>


}
