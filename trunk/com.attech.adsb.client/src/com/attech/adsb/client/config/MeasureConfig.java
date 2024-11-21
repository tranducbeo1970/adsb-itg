/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.config;

import com.attech.adsb.client.common.enums.MeasureUnit;
import java.nio.ByteBuffer;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Saitama
 */
@XmlRootElement(name = "MeasurementConfig")
@XmlAccessorType(XmlAccessType.NONE)
public class MeasureConfig extends com.attech.adsb.client.common.XmlSerializer {




    @XmlElement(name = "MaximumRuler")
    private int maximumRuler;

    @XmlElement(name = "MaximumRulerPoint")
    private int maximumRulerPoint;

    @XmlElement(name = "LineWeight")
    private int lineWeight;

    @XmlElement(name = "LineStyle")
    private String lineStyle;

    @XmlElement(name = "LineColor")
    private String lineColor;

    @XmlElement(name = "PointWeight")
    private int pointWeight;

    @XmlElement(name = "ZIndex")
    private int zIndex;

    @XmlElement(name = "Unit")
    private MeasureUnit unit;
    
    @XmlElement(name = "MaxRulerNumber")
    private int maxRulerNumber;
    
    @XmlElement(name = "MaxMeasurePointPerRuler")
    private int maxMeasurePointPerRuler;
    
    @XmlElement(name = "Label")
    private Label label;
            
    //<editor-fold defaultstate="collapsed" desc=" Class properties ">


    /**
     * @return the maximumRuler
     */
    public int getMaximumRuler() {
        return maximumRuler;
    }

    /**
     * @param maximumRuler the maximumRuler to set
     */
    public void setMaximumRuler(int maximumRuler) {
        this.maximumRuler = maximumRuler;
    }

    /**
     * @return the maximumRulerPoint
     */
    public int getMaximumRulerPoint() {
        return maximumRulerPoint;
    }

    /**
     * @param maximumRulerPoint the maximumRulerPoint to set
     */
    public void setMaximumRulerPoint(int maximumRulerPoint) {
        this.maximumRulerPoint = maximumRulerPoint;
    }

    /**
     * @return the lineWeight
     */
    public int getLineWeight() {
        return lineWeight;
    }

    /**
     * @param lineWeight the lineWeight to set
     */
    public void setLineWeight(int lineWeight) {
        this.lineWeight = lineWeight;
    }

    /**
     * @return the lineStyle
     */
    public String getLineStyle() {
        return lineStyle;
    }

    /**
     * @param lineStyle the lineStyle to set
     */
    public void setLineStyle(String lineStyle) {
        this.lineStyle = lineStyle;
    }

    /**
     * @return the lineColor
     */
    public String getLineColor() {
        return lineColor;
    }

    /**
     * @param lineColor the lineColor to set
     */
    public void setLineColor(String lineColor) {
        this.lineColor = lineColor;
    }

    /**
     * @return the pointWeight
     */
    public int getPointWeight() {
        return pointWeight;
    }

    /**
     * @param pointWeight the pointWeight to set
     */
    public void setPointWeight(int pointWeight) {
        this.pointWeight = pointWeight;
    }

    /**
     * @return the label
     */
    public Label getLabel() {
        return label;
    }

    /**
     * @param label the label to set
     */
    public void setLabel(Label label) {
        this.label = label;
    }
    
    /**
     * @return the zIndex
     */
    public int getzIndex() {
        return zIndex;
    }

    /**
     * @param zIndex the zIndex to set
     */
    public void setzIndex(int zIndex) {
        this.zIndex = zIndex;
    }

    /**
     * @return the unit
     */
    public MeasureUnit getUnit() {
        return unit;
    }

    /**
     * @param unit the unit to set
     */
    public void setUnit(MeasureUnit unit) {
        this.unit = unit;
    }

    /**
     * @return the maxRulerNumber
     */
    public int getMaxRulerNumber() {
        return maxRulerNumber;
    }

    /**
     * @param maxRulerNumber the maxRulerNumber to set
     */
    public void setMaxRulerNumber(int maxRulerNumber) {
        this.maxRulerNumber = maxRulerNumber;
    }

    /**
     * @return the maxMeasurePointPerRuler
     */
    public int getMaxMeasurePointPerRuler() {
        return maxMeasurePointPerRuler;
    }

    /**
     * @param maxMeasurePointPerRuler the maxMeasurePointPerRuler to set
     */
    public void setMaxMeasurePointPerRuler(int maxMeasurePointPerRuler) {
        this.maxMeasurePointPerRuler = maxMeasurePointPerRuler;
    }


    //</editor-fold>
    
    public short getLineStyleInShort() {
	String s = this.lineStyle.replace("0x", "").trim();
	int len = s.length();
	byte[] data = new byte[len / 2];
	for (int i = 0; i < len; i += 2) {
	    data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
		    + Character.digit(s.charAt(i + 1), 16));
	}
	ByteBuffer buffer2 = ByteBuffer.wrap(data);
	return buffer2.getShort();
    }
    
    public static void main(String [] args) {
//        MeasureConfig config = new MeasureConfig();
//        config.setPointWeight(1);
//        config.setMaximumRulerPoint(20);
//        config.setMaximumRuler(10);
//        config.setLineWeight(1);
//        config.setLineStyle("0xaaa");
//        config.setLineColor("#FFFFFF");
//        config.setzIndex(1000);
//        
//        Label label = new Label();
//        label.setzIndex(1000);
//        label.setPaddingY(0);
//        label.setPaddingX(0);
//        label.setFontSize(6);
//        label.setEnabled(true);
//        label.setDrawBackground(true);
//        label.setContentUnitSize(12);
//        label.setContentHeight(20);
//        label.setColor("#FFFFFF");
//        label.setBackgroundColor("FFFFCC");
//        config.setLabel(label);
//        
//        config.save("measurement-config.xml");
//        System.out.println("Completed");

//	Integer st = Integer.parseInt("AAAA", 16);
//	System.out.println("Integer: " + st);
//	System.out.println("Integer: " + (Integer) 0xaaaa);
//	
//	ByteBuffer buffer = ByteBuffer.wrap(new byte[] {(byte) 0xaa, (byte) 0xaa});
//	System.out.println("SHort: " + buffer.getShort());
//	
//	
//	ByteBuffer buffer2 = ByteBuffer.wrap(hexStringToByteArray("aaaa"));
//	System.out.println("SHort 2: " + buffer2.getShort());
//	
////	Short.parseShort("aaaa", 16);
//	short value =  (short) 0xaaaa;
//	System.out.println("SHORT: " + value);
	
    }
    
    
//    public static byte[] hexStringToByteArray(String s) {
//	int len = s.length();
//	byte[] data = new byte[len / 2];
//	for (int i = 0; i < len; i += 2) {
//	    data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
//		    + Character.digit(s.charAt(i + 1), 16));
//	}
//	return data;
//    }
    
}
