/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.config;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Saitama
 */
@XmlRootElement(name = "ListPoints")
@XmlAccessorType(XmlAccessType.NONE)
public class ListPoints extends com.attech.adsb.client.common.XmlSerializer {

    @XmlElement(name = "Point")
    private List<PointX> fixPoint;          
    
    
    public ListPoints() {
        this.fixPoint = new ArrayList<>();
    }  
    
    //<editor-fold defaultstate="collapsed" desc=" Class properties ">

    public List<PointX> getFixPoint() {
        return fixPoint;
    }

    public void setFixPoint(List<PointX> fixPoint) {
        this.fixPoint = fixPoint;
    }
    //</editor-fold>
    
    public static void main(String[] args) {
        ListPoints lstPoint = ListPoints.load("res/cra-proc.xml", ListPoints.class);
        List<PointX> fix = lstPoint.getFixPoint();
        for (PointX p : fix) {
            System.out.println("Name: " + p.getName());
        }
    }
}
