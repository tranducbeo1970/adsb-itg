/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.amhs.service;

import com.attech.asd.amhs.database.entities.Flightplan;

/**
 *
 * @author Saitama
 */
public class DepEnt {


    private String callsign;
    private String ssr;
    private String departure;
    private String departureTime;
    private String destination;
    private String dof;

    public DepEnt() {
    }
    
    public DepEnt(String content) {
        parseThis(content);
    }
    
    public static DepEnt parse(String content) {
        return new DepEnt(content);
    }
    
    private void parseThis(String content) {
        while (content.contains(" ")) {
            content=content.replace(" ", "");
        }
        content = content.replace("\r", "").replace("\n", "").replace(")", "").trim();
        
        String[] splits = content.split("-");
        if (splits.length < 4) {
            return;
        }

        // Parse CallSign
	String[] subSplits = splits[1].split("/");
	this.callsign = subSplits[0];
	if (subSplits.length > 1) { this.ssr = subSplits[1]; }
//        if (splits[1].contains("/")) {
//            String[] subSplits = splits[1].split("/");
//            this.callsign = subSplits[0];
//            this.ssr = subSplits[1];
//        } else {
//            this.callsign = splits[1];
//        }

        // Dep & Time
        this.departure = splits[2].substring(0, 4);
        this.departureTime = splits[2].substring(4);

        // Get Dest
        this.destination = splits[3];

        if (!content.contains("DOF/")) {
            return;
        }

        int currentIndex = 4;
        while (currentIndex < splits.length) {
            String currentLine = splits[currentIndex++];
            if (!currentLine.contains("DOF/")) {
                continue;
            }
            this.dof = currentLine.replace("DOF/", "");
            break;
        }
    }
    
    public boolean validate() {
        if (Validator.isNullOrEmpty(callsign)) {
            return false;
        }

        if (!Validator.validateLength(departure, 4, false)
                || !Validator.validateLength(destination, 4, false)
                || !Validator.validateLength(departureTime, 4, false)
                || !Validator.validateLength(dof, 6, false)) {
            return false;
        }

        return true;
    }

    //<editor-fold defaultstate="collapsed" desc="Class property methods">
    /**
     * @return the callSign
     */
    public String getCallSign() {
        return callsign;
    }

    /**
     * @param callSign the callSign to set
     */
    public void setCallSign(String callSign) {
        this.callsign = callSign;
    }

    /**
     * @return the ssr
     */
    public String getSsr() {
        return ssr;
    }

    /**
     * @param ssr the ssr to set
     */
    public void setSsr(String ssr) {
        this.ssr = ssr;
    }

    /**
     * @return the departureAerodome
     */
    public String getDeparture() {
        return departure;
    }

    /**
     * @param departureAerodome the departureAerodome to set
     */
    public void setDeparture(String departureAerodome) {
        this.departure = departureAerodome;
    }

    /**
     * @return the departureTime
     */
    public String getDepartureTime() {
        return departureTime;
    }

    /**
     * @param departureTime the departureTime to set
     */
    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    /**
     * @return the destination
     */
    public String getDestination() {
        return destination;
    }

    /**
     * @param destination the destination to set
     */
    public void setDestination(String destination) {
        this.destination = destination;
    }

    /**
     * @return the date
     */
    public String getDof() {
        return dof;
    }
    
    /**
     * @param date the date to set
     */
    public void setDof(String date) {
        this.dof = date;
    }
    //</editor-fold>
}
