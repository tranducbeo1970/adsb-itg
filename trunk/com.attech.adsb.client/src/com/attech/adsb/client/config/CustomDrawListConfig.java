/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.config;

import com.attech.adsb.client.common.enums.ShapeType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Saitama
 */
@XmlRootElement(name = "CustomDrawList")
@XmlAccessorType(XmlAccessType.NONE)
public class CustomDrawListConfig extends com.attech.adsb.client.common.XmlSerializer {
    
    @XmlAttribute(name = "z-index")
    private int zIndex;
    
    @XmlAttribute(name = "weight")
    private int weight;
    
    @XmlAttribute(name = "font-size")
    private int fontSize;
    
    @XmlAttribute(name = "padding-x")
    private int paddingX;
    
    @XmlAttribute(name = "padding-y")
    private int paddingY;
    
    @XmlElement(name = "Item")
    private List<CustomDrawItem> drawItems;    

    public CustomDrawListConfig() {
	drawItems = new ArrayList<>();
    }

    @Override
    public void save(String filename) {
	serialize(filename, this);
    }
    
    public void remvove(CustomDrawItem item) {
        this.drawItems.remove(item);
    }
    
    public static CustomDrawListConfig load(String fileName) {
	return (CustomDrawListConfig) deserialize(fileName, CustomDrawListConfig.class);
    }

    //<editor-fold defaultstate="collapsed" desc=" Class properties ">
    /**
     * @return the sectors
     */
    public List<CustomDrawItem> getDrawItems() {
	return drawItems;
    }

    public void setDrawItems(List<CustomDrawItem> drawItems) {
	this.drawItems = drawItems;
    }

    public void addDrawItem(CustomDrawItem drawItems) {
	this.drawItems.add(drawItems);
    }
    
    public void add(CustomDrawItem item) {
        update(item);
        this.drawItems.add(item);
    }
    
    public void update(CustomDrawItem item) {
        item.setWeight(this.weight);
        item.setzIndex(this.zIndex);
        item.getLabel().setFontSize(this.fontSize);
        item.getLabel().setPaddingX(paddingX);
        item.getLabel().setPaddingY(paddingY);
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
     * @return the weight
     */
    public int getWeight() {
        return weight;
    }

    /**
     * @param weight the weight to set
     */
    public void setWeight(int weight) {
        this.weight = weight;
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
    
    //</editor-fold>
    
    public static void main(String[] args) {
//        CustomDrawListConfig st = XmlSerializer.load(Common.RES_DRAW_TOOL, CustomDrawListConfig.class);
//	System.out.println("Load completed");
//	System.out.println("ITEM number: " + st.getDrawItem().size());

        CustomDrawListConfig config = new CustomDrawListConfig();
        config.setFontSize(7);
        config.setPaddingX(0);
        config.setPaddingY(0);
        config.setWeight(1);
        config.setzIndex(1);
        
//        CustomDrawItem item = new CustomDrawItem();
////        item.setAngle(angle);
//        item.setAngleEnd(360);
//        item.setAngleStart(0);
//        item.setColor("#FF0099");
//        item.setEnabled(true);
//        item.setLevel(99);
//        item.setName("Custom Draw Name");
//        item.setRadius(50);
//        item.setType(ShapeType.ARC);
//        item.setWeight(1);
//        item.setzIndex(0);
//        item.addPoint(new Point2f(108.0f, 21.0f));
//        item.setCenterPoint(new Point2f(108.0f, 21.0f));
//        
//        
//        Label label  = new Label();
//        label.setColor("#FFFFFF");
//        label.setContent("HELLO");
//        label.setFontSize(7);
//        label.setEnabled(true);
//        item.setLabel(label);
//        
//        config.add(item);
        
        config.save("custom-draw.xml");
        System.out.println("Done");



    }
    

}
