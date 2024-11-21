/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.lib.flights;

import com.attech.adsb.lib.common.Area;
import com.attech.adsb.lib.common.XmlSerializer;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class OldAirCraft {
    
    @XmlAttribute(name="address")
    private String address;
    
    @XmlElement(name="CallSigns")
    private List<String> callsigns;
    
    @XmlElement(name="Messages")
    private List<Message> messages;
    
    private int index;
    private String cs;
    
    public OldAirCraft(){
        this.index = 0;
        this.messages = new ArrayList<>();
        this.callsigns = new ArrayList<>();
    }
    
    public OldAirCraft(String address) {
        this();
        this.address = address;
        
    }
    
    public void add(Message message) {
        message.setNo(this.messages.size() + 1);
        this.messages.add(message);
        cs = message.getCallsign() == null ? "" : message.getCallsign();
        if (!callsigns.contains(cs)) this.callsigns.add(cs);
    }
    
    public void add(List<Message> msgs) {
        // this.messages.addAll(msgs);
        for (Message message : msgs) {
            this.add(message);
        }
    }
    
    public void add(OldAirCraft aircraft) {
        this.messages.addAll(aircraft.getMessages());
        for (String callsign : aircraft.getCallsigns()) {
            if (this.callsigns.contains(callsign)) continue;
            this.callsigns.add(callsign);
        }
    }
    
    public void sort() throws Exception {
        try {
            Collections.sort(messages);
        } catch (Exception ex) {
            String error = String.format("Error on aircraft ID: %s, callsign: %s, message count: %s",
                    this.address,
                    this.callsigns,
                    this.messages.size());
            throw new Exception(error, ex);
        }
    }

    public long getStartTime() {
        if (messages.isEmpty()) return 0;
        return messages.get(0).getReceivedTime();
    }

    public long getEndTime() {
        if (messages.isEmpty()) return 0;
        return messages.get(messages.size() - 1).getReceivedTime();
    }
    
    public void resetIndex() { index = 0; }
    
    public Message get() {
        if (index >= messages.size()) return null;
        return this.messages.get(index);
    }
    
    public void next() {
        if (index >= messages.size()) return;
        index++;
    }
    
    public boolean hasNext() {
        return index < messages.size() ;
    }
    
    public String key() {
        return String.format("%s.%s", this.address, this.getCallsign());
    }
    
    @Override
    public String toString() {
        // return String.format("ADD: %-10s MESSAGE-COUNT: %-10d START: %-15d END: %-15d FLIGHT-ID: %s", address, messages.size(), getStartTime(), getEndTime(), getCallsign());
        return String.format("KEY: [%s]", this.hashCode(), this.key());
    }
    
    @Override
    public int hashCode() {
        return String.format("%s.%s", this.address, this.getCallsign()).hashCode();
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
        final OldAirCraft other = (OldAirCraft) obj;
        if (!Objects.equals(this.address, other.address)) {
            return false;
        }
        if (!Objects.equals(this.getCallsign(), other.getCallsign())) {
            return false;
        }
        return true;
    }
    
    public void printMessage() {
        for (Message message : messages) System.out.println(message);
    }
    
    public int getMessageCount() {
        return this.messages.size();
    }

    public String getCallsign() {
        if (this.getCallsigns().isEmpty()) return "";
        StringBuilder builder = new StringBuilder();
        for (String callsign : this.callsigns) {
            if (callsign == null || callsign.isEmpty()) continue;
            builder.append(builder.length() == 0 ? callsign : ", " + callsign);
        }
        return builder.toString();
    }
    
    public OldAirCraft filter(Float minFL, Float maxFL) {
        OldAirCraft aircraft = new OldAirCraft(this.address);
        for (Message m : messages) {
            if (!validateFlightLevel(m, minFL, maxFL)) continue;
            aircraft.add(m);
        }
        return aircraft;
    }
    
    public OldAirCraft filter(double minDistance, double maxDistance, Float minFL, Float maxFL) {
        OldAirCraft aircraft = new OldAirCraft(this.address);
        for (Message m : messages) {
            if (!validateFlightLevel(m, minFL, maxFL)) continue;
            if (!validateDistance(m, minDistance, maxDistance)) continue;
            aircraft.add(m);
        }
        return aircraft;
    }
    
    public OldAirCraft filter(double minDistance, double maxDistance, Float minFL, Float maxFL, Area area) {
        OldAirCraft aircraft = new OldAirCraft(this.address);
        for (Message m : messages) {
            if (!validateFlightLevel(m, minFL, maxFL)) continue;
            if (!validateDistance(m, minDistance, maxDistance)) continue;
            if (area != null && !area.validate(m.getLng().floatValue(), m.getLat().floatValue())) continue;
            aircraft.add(m);
        }
        return aircraft;
    }
    
    public Map<String, OldAirCraft> buildupFlight2() {
        
        Map<String, OldAirCraft> flights = new HashMap<>();
        final List<Message> tmp = new ArrayList<>();
        for (Message m : this.messages) {
            String callsign = m.getCallsign();
            if (callsign == null || callsign.isEmpty()) {
                tmp.add(m);
                continue;
            }

            OldAirCraft flight = flights.get(callsign);
            if (flight == null) {
                flight = new OldAirCraft(this.address);
                flights.put(callsign, flight);
            }
            
            if (!tmp.isEmpty()) {
                flight.add(tmp);
                tmp.clear();
            }
            flight.add(m);
        }
        return flights;
    }
        
    public Map<String, OldAirCraft> buildupFlight() throws Exception {
        this.sort();
        final Map<String, OldAirCraft> flights = new HashMap<>();
        Message lastMessage = null;
        Message curMessage = null;
        OldAirCraft aircraft = null;
        String lastKnownCallsign = "";
        String tmpCallSign = null;
        String currentCS;
        long delta = 0;
        
        for (int i = 0; i < this.messages.size(); i++) {
            
            if (aircraft == null) {
                lastMessage = this.messages.get(i);
                aircraft = new OldAirCraft(lastMessage.getAddress());
                // aircraft = flights.containsKey(lastMessage.key()) ? flights.get(lastMessage.key()) : new AirCraft(lastMessage.getAddress());
                aircraft.add(lastMessage);
                
                /*
                if (flights.containsKey(lastMessage.key())) {
                    aircraft = flights.get(aircraft.key());
                    aircraft.add(lastMessage);
                } else {
                    aircraft = new AirCraft(lastMessage.getAddress());
                    aircraft.add(lastMessage);
                }
                */
                if (lastMessage.getCallsign() !=null && !lastMessage.getCallsign().isEmpty()) lastKnownCallsign = lastMessage.getCallsign();
                continue;
            } 
            
            curMessage = this.messages.get(i);
            delta = curMessage.getReceivedTime() - lastMessage.getReceivedTime();
            
            currentCS = curMessage.getCallsign() == null ? "" : curMessage.getCallsign().trim();
            if ((!currentCS.isEmpty() && !currentCS.equalsIgnoreCase(lastKnownCallsign) && !lastKnownCallsign.isEmpty()) || (delta >= 900000)) {
                // System.out.println(" > Break File: lastCS " + lastKnownCallsign + " currentCS " + currentCS);
                flights.put(aircraft.key(), aircraft);
                aircraft = null;
                lastKnownCallsign = "";
                continue;
            }
            
            aircraft.add(curMessage);
            lastMessage = curMessage;
            if (lastMessage.getCallsign() !=null && !lastMessage.getCallsign().isEmpty()) lastKnownCallsign = lastMessage.getCallsign();
            
        }
        
        if (aircraft != null && aircraft.getMessageCount() > 0) flights.put(aircraft.key(), aircraft);
        return flights;
    }
    
    public boolean isEmpty() {
        return this.messages.isEmpty();
    }
    
    private boolean validateFlightLevel(Message message, Float minFL, Float maxFL) {
        if (minFL == null && maxFL == null) return true;
        if (message.getFlightLevel() == null) return false;
        if (minFL != null && message.getFlightLevel() < minFL) return false;
        if (maxFL != null && message.getFlightLevel() >= maxFL) return false;
        return true;
    }
    
    private boolean validateDistance(Message message, Double minDistance, Double maxDistance) {
        if (minDistance == null && maxDistance == null) return true;
        if (message.getDistance() < minDistance || message.getDistance() > maxDistance) return false;
        return true;
    }
    
    public void save(String file) {
        XmlSerializer.serialize(file, this);
    }
    
    public void save(File file) {
        XmlSerializer.serialize(file, this);
    }
    
    public static OldAirCraft load(String file) {
        return (OldAirCraft) XmlSerializer.deserialize(file, OldAirCraft.class);
    }
    
    public static OldAirCraft load(File file) {
        return (OldAirCraft) XmlSerializer.deserialize(file, OldAirCraft.class);
    }

    // Properties
    
    public void setMessages(List<Message> messages) { this.messages = messages; }
    
    public List<Message> getMessages() { return this.messages; }

    public void setAddress(String address) { this.address = address; }
    
    public String getAddress() { return this.address; }
    
    public void setCallsigns(List<String> callsigns) { this.callsigns = callsigns; }

    public List<String> getCallsigns() { return callsigns; }
    
}
