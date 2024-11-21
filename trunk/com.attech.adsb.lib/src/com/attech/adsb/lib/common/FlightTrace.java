/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.lib.common;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author andh
 */
@XmlRootElement(name = "FlightTrace")
@XmlAccessorType(XmlAccessType.NONE)
public class FlightTrace {
    
    @XmlAttribute(name="callsign")
    private String callsign;
    
    @XmlAttribute(name="address")
    private String address;
    
    @XmlAttribute(name="station-name")
    private String stationName;
    
    @XmlElement(name="Trace")
    private List<Line> lines;
    
    public FlightTrace() {
        this.lines = new ArrayList<>();
    }
    
    public boolean valid() {
        return !this.lines.isEmpty();
    }
    
    public FlightTrace(String address) {
        this();
        this.address = address;
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
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the lines
     */
    public List<Line> getLines() {
        return lines;
    }

    /**
     * @param lines the lines to set
     */
    public void setLines(List<Line> lines) {
        this.lines = lines;
    }
    
    public void addLine(Line line) {
        if (!line.valid()) return;
        this.lines.add(line);
    }
    
    public void save(File file) {
        if (!this.valid()) return;
        XmlSerializer.serialize(file, this);
    }
    
    public static FlightTrace load(String file) {
        return (FlightTrace) XmlSerializer.deserialize(file, FlightTrace.class);
    }
    
     public static FlightTrace load(File file) {
        return (FlightTrace) XmlSerializer.deserialize(file, FlightTrace.class);
    }
    
    
    public static void main(String [] args) {
//        FlightTrace flighTrace = new FlightTrace();
//        flighTrace.setAddress("8880AA");
//        flighTrace.setCallsign("VN036");
//        
//        
//        Line line = new Line();
//        line.addPoint(new Point2f(0.0f, 0.0f));
//        line.addPoint(0.0f, 1.0f);
//        flighTrace.addLine(line);
//        flighTrace.save("flight-trace.xml");


        FlightTrace trace = FlightTrace.load("E:\\works\\temp\\adsb-7\\report\\_00059_20160205002004\\DIEN BIEN(166, 167)\\ALL\\GOOD\\7807C4.xml");
        System.out.println(trace);
        
        
    }

    /**
     * @return the stationName
     */
    public String getStationName() {
        return stationName;
    }

    /**
     * @param stationName the stationName to set
     */
    public void setStationName(String stationName) {
        this.stationName = stationName;
    }
    
    public void setLineWeight(int weight){
        for (Line line : lines){
            line.setWeight(weight);
        }
    }
}
