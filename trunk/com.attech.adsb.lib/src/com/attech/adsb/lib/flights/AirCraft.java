/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.lib.flights;

import com.attech.adsb.lib.common.XmlSerializer;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/**
 *
 * @author andh
 */
@XmlRootElement(name = "AirCraft")
@XmlAccessorType(XmlAccessType.NONE)
public class AirCraft {
    
    @XmlAttribute(name = "address")
    private String address;
    
    @XmlElement(name = "messages")
    private List<Message> messages;
    
    public AirCraft() {
        this.messages = new ArrayList<>();
    }
    
    public AirCraft(String address) {
        this();
        this.address = address;
    }
    
    public AirCraft(int address) {
        this(Integer.toHexString(address).toUpperCase());
    }
    
    public void add(Message message) {
        this.getMessages().add(message);
    }
    
    public void add(List<Message> messages) {
        this.getMessages().addAll(messages);
    }
    
    public int count() { return this.getMessages().size(); }
    
    public void exportCVS(File file) throws IOException {
        BufferedWriter out = new BufferedWriter(new FileWriter(file));
        out.write("#, SIC, ADDRESS, CALLSIGN, LONGTITUDE, LATITUDE, FLIGHT-LEVEL, NIC, NAC, SIL, DISTANCE, POSITION-TIME, TRANSMISS-TIME, RECEIVED-TIME, FLIGHT-LEVEL-AGE, TIME");
        int index = 1;
        for (Message message : getMessages()) {
            out.newLine();
            final String line = String.format(" %s, %s, %s, %s,%s, %s, %s, %s,%s, %s, %s, %s,%s, %s, %s, %s", 
                    index, 
                    message.getSic(),
                    message.getAddress(),
                    message.getCallsign(),
                    message.getLng(),
                    message.getLat(),
                    message.getFlightLevel(),
                    message.getNic(),
                    message.getNac(),
                    message.getSil(),
                    message.getDistance(),
                    convertTime(message.getPositionTime()),
                    convertTime(message.getTransmissTime()),
                    message.getReceivedTime(),
                    message.getFlightLevelAge(),
                    message.getTime());
            out.write(line);
            index++;
        }
        out.close();
    }
    
    public void exportXML(File file) {
        if (file.exists()) {
            AirCraft tmp = AirCraft.load(file);
            if (tmp != null) {
                tmp.add(this.messages);
                // this.add(tmp.getMessages());
                XmlSerializer.serialize(file, tmp);
                return;
            }
        }
        XmlSerializer.serialize(file, this);
    }
    
    public static AirCraft load(File file) {
        AirCraft aircraft = (AirCraft) XmlSerializer.deserialize(file, AirCraft.class);
        if (aircraft == null) {
            System.out.printf("Unable to load file %s (%s)%n", file.getName(), file.getAbsolutePath());
        }
        return aircraft;
    }
    
    private String convertTime(Double time) {
        final int hour = (int) (time / 3600);
        double remain = time - (hour * 3600);
        final int min = (int) (remain / 60);
        remain -= (min * 60);
        final int sec = (int) remain;
        remain -= sec;
        return String.format("%s:%s:%s.%s", hour, min, sec, remain);
    }
    
    @Override
    public int hashCode() {
        return String.format("%s", this.address).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AirCraft other = (AirCraft) obj;
        return Objects.equals(this.address, other.address);
    }
    
    public List<Flight> buildUpFlight(long timeout) {
        if (this.getMessages() == null || this.getMessages().isEmpty()) return null;
        this.sort();
        
        final List<Flight> flights = new ArrayList<>();
        Flight flight = new Flight(address);
        
        final int length = this.getMessages().size();
        Message lastMessage = null;
        for (int i = 0; i < length; i++) {
            final Message message = this.getMessages().get(i);
            if (i == 0) {
                flight.add(message);
                lastMessage = message;
                continue;
            }
            
            final long time = message.getReceivedTime() - lastMessage.getReceivedTime();
            if (time > timeout) {
                flights.add(flight);
                flight = new Flight(address);
            }
            // NEU O MAT DAT MA CHUYEN CALLSIGN, BAT ADS-B LIEN TUC
            final String callsign = (message.getCallsign() != null) ? message.getCallsign() : "";
            final String lastCallsign = (lastMessage.getCallsign() != null) ? lastMessage.getCallsign() : "";
            final float fl = (message.getFlightLevel() != null) ? message.getFlightLevel() : 0;
                        
            if (fl<=0 && time <= timeout && !callsign.isEmpty() && !callsign.equalsIgnoreCase(lastCallsign))
            {
                flights.add(flight);
                flight = new Flight(address);
            }
            ///////////////////////////
            flight.add(message);
            lastMessage = message;
        }
        
        if (flight.size() > 0) flights.add(flight);
        
        return flights;
    }

    private void sort() {
        Collections.sort(getMessages());
    }
    
    // PROTERTIES

    public String getAddress() { return this.address; }
    
    public void setAddress(String address) { this.address = address; }

    public List<Message> getMessages() { return messages; }

    public void setMessages(List<Message> messages) { this.messages = messages; }
    
    
}
