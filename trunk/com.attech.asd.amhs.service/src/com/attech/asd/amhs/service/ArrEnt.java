/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.amhs.service;

/**
 *
 * @author Saitama
 */
public class ArrEnt {



    private String callsign;
    private String ssr;
    private String departure;
    private String departureTime;
    private String destination;
    private String arrivalTime;
    private String dof;

    public ArrEnt() {
    }

    public ArrEnt(String content) {
        parseThis(content);
    }
    
    public static ArrEnt parses(String content) {
        ArrEnt arrEnt = new ArrEnt(content);
        return arrEnt;
    }
    
    public boolean validate() {
        if (Validator.isNullOrEmpty(callsign)) {
            return false;
        }

        if (!Validator.validateLength(departure, 4, false)
                || !Validator.validateLength(destination, 4, false)
                || !Validator.validateLength(arrivalTime, 4, false)
                || !Validator.validateLength(dof, 6, false)) {
            return false;
        }

        return true;
    }

    private void parseThis(String content) {
	// System.out.println(content);
        while (content.contains(" ")) {
            content=content.replace(" ", "");
        }
        content = content.replace("\r\n", "").replace(")", "");
        String[] splits = content.split("-");
        if (splits.length < 4) {
            return;
        }

        // Parse CallSign
        if (splits[1].contains("/")) {
            String[] subSplits = splits[1].split("/");
            this.callsign = subSplits[0];
            this.ssr = subSplits[1];
        } else {
            this.callsign = splits[1];
        }

        // Get dep
        if (splits[2].length() > 4) {
            this.departure = splits[2].substring(0, 4);
	    this.departureTime = splits[2].substring(4);
        } else {
            this.departure = splits[2];
        }

        // Get arr
        this.destination = splits[3].substring(0, 4);
        this.arrivalTime = splits[3].substring(4);

        int currentIndex = 4;
        while (currentIndex < splits.length) {
            String currentLine = splits[currentIndex++];
            if (!currentLine.contains("DOF/")) {
                continue;
            }
            this.setDof(currentLine.replace("DOF/", ""));
            break;
        }
    }

    /**
     * @return the callsign
     */
    public String getCallsign() {
        return callsign;
    }

    /**
     * @param callsign the callsign to set
     */
    public void setCallsign(String callsign) {
        this.callsign = callsign;
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
     * @return the depatureAerodome
     */
    public String getDeparture() {
        return departure;
    }

    /**
     * @param depatureAerodome the depatureAerodome to set
     */
    public void setDeparture(String depatureAerodome) {
        this.departure = depatureAerodome;
    }

    /**
     * @return the destinationAerodome
     */
    public String getDestination() {
        return destination;
    }

    /**
     * @param destinationAerodome the destinationAerodome to set
     */
    public void setDestination(String destinationAerodome) {
        this.destination = destinationAerodome;
    }

    /**
     * @return the arrivalTime
     */
    public String getArrivalTime() {
        return arrivalTime;
    }

    /**
     * @param arrivalTime the arrivalTime to set
     */
    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
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

}
