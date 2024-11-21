/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.config;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Saitama
 */
@XmlRootElement(name = "AMA")
@XmlAccessorType(XmlAccessType.NONE)
public class AMA extends com.attech.adsb.client.common.XmlSerializer {

    @XmlElement(name = "CellLineStyle")
    private String cellLineStyle;

    @XmlElement(name = "OutLineStyle")
    private String outlineStyle;

    @XmlElement(name = "LineWeight")
    private int lineWeight;

    @XmlElement(name = "Color")
    private String color;

    @XmlElement(name = "Font")
    private int font;

    @XmlElement(name = "ZIndex")
    private float zIndex;

    @XmlElement(name = "Margin")
    private int margin;

    @XmlElement(name = "CloseRangeThreshold")
    private float closeRangeThresHold;

    @XmlElement(name = "LongRangeThreshold")
    private float longRangeThresHold;

    @XmlElementWrapper(name = "Cells")
    @XmlElement(name = "Cell")
    private List<AMACell> lstCells;

    @XmlElementWrapper(name = "Points")
    @XmlElement(name = "Point")
    private List<Point2f> lstPoints;

    @XmlElement(name = "PointX")
    private PointX text;

    public AMA() {
        lstCells = new ArrayList<>();
        lstPoints = new ArrayList<>();
        text = new PointX();
    }

    @Override
    public void save(String filename) {
        serialize(filename, this);
    }

    public static AMA load(String fileName) {
        return (AMA) deserialize(fileName, AMA.class);
    }

    //<editor-fold defaultstate="collapsed" desc=" Class properties ">       
    public List<AMACell> getLstCells() {
        return lstCells;
    }

    public void setLstCells(List<AMACell> lstCells) {
        this.lstCells = lstCells;
    }

    public void addCells(AMACell Cells) {
        this.lstCells.add(Cells);
    }

    public List<Point2f> getLstPoints() {
        return lstPoints;
    }

    public void setLstPoints(List<Point2f> lstPoints) {
        this.lstPoints = lstPoints;
    }

    public void addPoints(Point2f point) {
        this.lstPoints.add(point);
    }

    public PointX getText() {
        return text;
    }

    public void setText(PointX text) {
        this.text = text;
    }

    public int getMargin() {
        return margin;
    }

    public void setMargin(int margin) {
        this.margin = margin;
    }

    public String getCellLineStyle() {
        return cellLineStyle;
    }

    public void setCellLineStyle(String cellLineStyle) {
        this.cellLineStyle = cellLineStyle;
    }

    public String getOutlineStyle() {
        return outlineStyle;
    }

    public void setOutlineStyle(String outlineStyle) {
        this.outlineStyle = outlineStyle;
    }

    public int getLineWeight() {
        return lineWeight;
    }

    public void setLineWeight(int lineWeight) {
        this.lineWeight = lineWeight;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getFont() {
        return font;
    }

    public void setFont(int font) {
        this.font = font;
    }

    public float getzIndex() {
        return zIndex;
    }

    public void setzIndex(float zIndex) {
        this.zIndex = zIndex;
    }
    
    public float getCloseRangeThresHold() {
        return closeRangeThresHold;
    }

    public void setCloseRangeThresHold(float closeRangeThresHold) {
        this.closeRangeThresHold = closeRangeThresHold;
    }

    public float getLongRangeThresHold() {
        return longRangeThresHold;
    }

    public void setLongRangeThresHold(float longRangeThresHold) {
        this.longRangeThresHold = longRangeThresHold;
    }

    //</editor-fold>
    
    
    public short getLineStyleInShort(String lineStyle) {
        String s = lineStyle.replace("0x", "").trim();
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        ByteBuffer buffer2 = ByteBuffer.wrap(data);
        return buffer2.getShort();
    }

}
