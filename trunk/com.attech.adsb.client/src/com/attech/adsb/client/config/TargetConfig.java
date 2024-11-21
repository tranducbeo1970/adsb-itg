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
 * @author Saitama
 */
@XmlRootElement(name = "Target")
@XmlAccessorType(XmlAccessType.NONE)
public class TargetConfig extends com.attech.adsb.client.common.XmlSerializer {

    @XmlElement(name = "TargetSize")
    private float targetSize;

    @XmlElement(name = "TargetRadius")
    private float targetRadius;

    @XmlElement(name = "TargetInnerSize")
    private float targetInnerSize;

    @XmlElement(name = "TargetZIndex")
    private float targetZIndex;

    @XmlElement(name = "TargetZIndex2")
    private float targetZIndex2;

    @XmlElement(name = "NormalColor1")
    private String normalColor1;

    @XmlElement(name = "NormalColor2")
    private String normalColor2;

    @XmlElement(name = "WarningColor")
    private String warningColor;

    @XmlElement(name = "ControlledColor")
    private String controlledColor;

    @XmlElement(name = "TransferToColor")
    private String transferToColor;

    @XmlElement(name = "TransferFromColor")
    private String transferFromColor;

    @XmlElement(name = "Weight")
    private float weight;

    @XmlElement(name = "TargetLabel")
    private TargetLabelConfig targetLabelConfig;

    @XmlElement(name = "CircleWarnColor")
    private String circleWarnColor;

    @XmlElement(name = "CircleAlarmColor")
    private String circleAlarmColor;
    
    @XmlElement(name = "TraceColor")
    private String traceColor;
    
    @XmlElement(name = "TraceWeight")
    private float traceWeight;

    @XmlElement(name = "ObsoletedColor")
    private String obsoletedTargetColor;

    public TargetConfig() {
    }

    //<editor-fold defaultstate="collapsed" desc=" Class properties ">
    public float getTargetSize() {
        return targetSize;
    }

    public void setTargetSize(float targetSize) {
        this.targetSize = targetSize;
    }

    public float getTargetRadius() {
        return targetRadius;
    }

    public void setTargetRadius(float targetRadius) {
        this.targetRadius = targetRadius;
    }

    public float getTargetInnerSize() {
        return targetInnerSize;
    }

    public void setTargetInnerSize(float targetInnerSize) {
        this.targetInnerSize = targetInnerSize;
    }

    public float getTargetZIndex() {
        return targetZIndex;
    }

    public void setTargetZIndex(float targetIndex) {
        this.targetZIndex = targetIndex;
    }

    public String getNormalColor1() {
        return normalColor1;
    }

    public void setNormalColor1(String normalColor1) {
        this.normalColor1 = normalColor1;
    }

    public String getNormalColor2() {
        return normalColor2;
    }

    public void setNormalColor2(String normalColor2) {
        this.normalColor2 = normalColor2;
    }

    public String getWarningColor() {
        return warningColor;
    }

    public void setWarningColor(String warningColor) {
        this.warningColor = warningColor;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public TargetLabelConfig getTargetLabelConfig() {
        return targetLabelConfig;
    }

    public void setTargetLabelConfig(TargetLabelConfig targetLabelConfig) {
        this.targetLabelConfig = targetLabelConfig;
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
     * @return the circleWarnColor
     */
    public String getCircleWarnColor() {
        return circleWarnColor;
    }

    /**
     * @param circleWarnColor the circleWarnColor to set
     */
    public void setCircleWarnColor(String circleWarnColor) {
        this.circleWarnColor = circleWarnColor;
    }

    /**
     * @return the circleAlarmColor
     */
    public String getCircleAlarmColor() {
        return circleAlarmColor;
    }

    /**
     * @param circleAlarmColor the circleAlarmColor to set
     */
    public void setCircleAlarmColor(String circleAlarmColor) {
        this.circleAlarmColor = circleAlarmColor;
    }

    /**
     * @return the obsoletedTargetColor
     */
    public String getObsoletedTargetColor() {
        return obsoletedTargetColor;
    }

    /**
     * @param obsoletedTargetColor the obsoletedTargetColor to set
     */
    public void setObsoletedTargetColor(String obsoletedTargetColor) {
        this.obsoletedTargetColor = obsoletedTargetColor;
    }
    
    
    /**
     * @return the targetZIndex2
     */
    public float getTargetZIndex2() {
        return targetZIndex2;
    }

    /**
     * @param targetZIndex2 the targetZIndex2 to set
     */
    public void setTargetZIndex2(float targetZIndex2) {
        this.targetZIndex2 = targetZIndex2;
    }
    
    /**
     * @return the traceColor
     */
    public String getTraceColor() {
        return traceColor;
    }

    /**
     * @param traceColor the traceColor to set
     */
    public void setTraceColor(String traceColor) {
        this.traceColor = traceColor;
    }

    /**
     * @return the traceWeight
     */
    public float getTraceWeight() {
        return traceWeight;
    }

    /**
     * @param traceWeight the traceWeight to set
     */
    public void setTraceWeight(float traceWeight) {
        this.traceWeight = traceWeight;
    }

    //</editor-fold>
    
    
    public static void main(String[] args) {
        TargetLabelConfig label = new TargetLabelConfig();
        label.setAltitudeWidth(185);
        label.setCallsignWidth(145);
        label.setColor("#FFFFFF");
        label.setConnectorAngle(30.0f);
        label.setConnectorAngleStep(10.0f);
        label.setConnectorColor("#FFFFFF");
        label.setConnectorSize(60.0f);
        // label.setConnectorStyle(connectorStyle);
        label.setConnectorWeight(1.0f);
        label.setFontSize(7);
        label.setHdgNoteWidth(145);
        label.setLabelHeight(100);
        label.setLabelWidth(290);
        label.setSpeedWidth(145);
        label.setTextHeight(25.0f);

        TargetConfig config = new TargetConfig();
        config.setNormalColor1("#FFFFFF");
        config.setNormalColor2("#000000");
        config.setTargetZIndex(1000);
        config.setWarningColor("#FF00FF");
        config.setWeight(1.0f);
        config.setTargetLabelConfig(label);
        config.save("target.xml");
        System.out.println("Completed");

    }
}
