/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.administrator.common;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Saitama
 */
@XmlRootElement(name = "Label")
@XmlAccessorType(XmlAccessType.NONE)
public class Label {


    @XmlAttribute(name = "Enabled")
    private Boolean enabled;

    @XmlAttribute(name = "Padding-X")
    private int paddingX;

    @XmlAttribute(name = "Padding-Y")
    private int paddingY;

    @XmlElement(name = "X")
    private Float x;

    @XmlElement(name = "Y")
    private Float y;

    @XmlElement(name = "Z-Index")
    private int zIndex;

    @XmlElement(name = "Font-Size")
    private int fontSize;

    @XmlElement(name = "Color")
    private String color;

    @XmlElement(name = "Content")
    private String content;
    
    @XmlElement(name = "Draw-Background")
    private Boolean drawBackground;
    
    @XmlElement(name = "Background-Color")
    private String backgroundColor;
    
    @XmlElement(name = "Content-Unit-Size")
    private Integer contentUnitSize;
    
    @XmlElement(name = "Content-Height")
    private Integer contentHeight;
    
    public Label() {       
    }
    
    public Label(Label obj) {
        this.enabled = obj.getEnabled();
        this.color = obj.getColor();
        this.content = obj.getContent();
        this.fontSize = obj.getFontSize();
        this.zIndex = obj.getzIndex();
        this.paddingX = obj.getPaddingX();
        this.paddingY = obj.getPaddingY();
        this.x = obj.getX();
        this.y = obj.getY();
        this.drawBackground = obj.getDrawBackground();
        this.backgroundColor = obj.getBackgroundColor();
        this.contentHeight = obj.getContentHeight();
        this.contentUnitSize = obj.getContentUnitSize();
    }
    
    public Label(String content) {       
        this.content = content;
        this.color = "#FFFFFF";
        this.enabled = true;
        this.fontSize = 7;
        this.zIndex = 101;
    }

    //<editor-fold defaultstate="collapsed" desc=" Class properties ">
    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return the color
     */
    public String getColor() {
        return color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * @return the paddingX
     */
    public int getPaddingX() {
        return paddingX;
    }

    /**
     * @param paddingX the paddingX to set
     */
    public void setPaddingX(int paddingX) {
        this.paddingX = paddingX;
    }

    /**
     * @return the paddingY
     */
    public int getPaddingY() {
        return paddingY;
    }

    /**
     * @param paddingY the paddingY to set
     */
    public void setPaddingY(int paddingY) {
        this.paddingY = paddingY;
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
     * @return the enabled
     */
    public Boolean getEnabled() {
        return enabled;
    }

    /**
     * @param enabled the enabled to set
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * @return the fontSize
     */
    public int getFontSize() {
        return fontSize;
    }

    /**
     * @param fontSize the fontSize to set
     */
    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    /**
     * @return the x
     */
    public Float getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(Float x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public Float getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(Float y) {
        this.y = y;
    }

    
    /**
     * @return the drawBackground
     */
    public Boolean getDrawBackground() {
        return drawBackground;
    }

    /**
     * @param drawBackground the drawBackground to set
     */
    public void setDrawBackground(Boolean drawBackground) {
        this.drawBackground = drawBackground;
    }

    /**
     * @return the backgroundColor
     */
    public String getBackgroundColor() {
        return backgroundColor;
    }

    /**
     * @param backgroundColor the backgroundColor to set
     */
    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    /**
     * @return the contentUnitSize
     */
    public Integer getContentUnitSize() {
        return contentUnitSize;
    }

    /**
     * @param contentUnitSize the contentUnitSize to set
     */
    public void setContentUnitSize(Integer contentUnitSize) {
        this.contentUnitSize = contentUnitSize;
    }

    /**
     * @return the contentHeight
     */
    public Integer getContentHeight() {
        return contentHeight;
    }

    /**
     * @param contentHeight the contentHeight to set
     */
    public void setContentHeight(Integer contentHeight) {
        this.contentHeight = contentHeight;
    }
    
    //</editor-fold>
}
