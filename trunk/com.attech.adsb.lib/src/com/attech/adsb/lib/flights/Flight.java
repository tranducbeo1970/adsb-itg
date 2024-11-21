/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.lib.flights;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author andh
 */
@XmlRootElement(name = "Flight")
@XmlAccessorType(XmlAccessType.NONE)
public class Flight {
    
    protected final String address;
    protected final List<String> callsigns;
    protected final List<Message> messages;
    protected Long min;
    protected Long max;
    protected List<Frame> frames;
    private Boolean onL642;
    private Boolean onM771;
    private Boolean onN892;
    private Boolean onL625;
    
    public Flight(Flight flight) {
        this.address = flight.getAddress();
        this.messages = flight.getMessages();
        this.callsigns = flight.getCallsigns();
        onL625 = false;
        onL642 = false;
        onM771 = false;
        onN892 = false;
    }
    
    public Flight(String address) {
        this.address = address;
        this.messages = new ArrayList<>();
        this.callsigns = new ArrayList<>();
        onL625 = false;
        onL642 = false;
        onM771 = false;
        onN892 = false;
    }
    
    public void add(Message message) {
        final String callsign = message.getCallsign();
        if (callsign != null && !callsign.isEmpty() && !this.callsigns.contains(callsign)) this.getCallsigns().add(callsign);
        this.getMessages().add(message);
        
    }
    
    public String callsign() {
        final StringBuilder builder = new StringBuilder();
        for (String callsign : this.getCallsigns()) {
            if (callsign == null || callsign.isEmpty()) continue;
            builder.append(builder.length() == 0 ? callsign : "," + callsign);
        }
        return builder.toString().replaceAll("[^\\x00-\\x7F]", "");
    }
    
    public int size() {
        return this.getMessages().size();
    }

    public String getAddress() {
        return address;
    }
    
    public void splitFrame(long frameTime) {
        Collections.sort(this.getMessages());
        min = this.getMessages().get(0).getReceivedTime();
        max = this.getMessages().get(this.getMessages().size() - 1).getReceivedTime();
        final int size = this.getMessages().size();
        frames = new ArrayList<>();
        
        long start = ((long) min / 1000) * 1000;
        long end = start + frameTime;
        int index = 0;
        
        
        while (start < max) {
            Frame frame = new Frame(start, end);
            while (index < size) {
                Message message = this.getMessages().get(index);
                if (frame.add(message) < 0) break;
                index++;
            }
            frames.add(frame);
            start += frameTime;
            end += frameTime;
        }
    }

    /**
     * @return the callsigns
     */
    public List<String> getCallsigns() {
        return callsigns;
    }

    /**
     * @return the messages
     */
    public List<Message> getMessages() {
        return messages;
    }

    /**
     * @return the onL642
     */
    public Boolean getOnL642() {
        return onL642;
    }

    /**
     * @param onL642 the onL642 to set
     */
    public void setOnL642(Boolean onL642) {
        this.onL642 = onL642;
    }

    /**
     * @return the onM771
     */
    public Boolean getOnM771() {
        return onM771;
    }

    /**
     * @param onM771 the onM771 to set
     */
    public void setOnM771(Boolean onM771) {
        this.onM771 = onM771;
    }

    /**
     * @return the onN892
     */
    public Boolean getOnN892() {
        return onN892;
    }

    /**
     * @param onN892 the onN892 to set
     */
    public void setOnN892(Boolean onN892) {
        this.onN892 = onN892;
    }

    /**
     * @return the onL625
     */
    public Boolean getOnL625() {
        return onL625;
    }

    /**
     * @param onL625 the onL625 to set
     */
    public void setOnL625(Boolean onL625) {
        this.onL625 = onL625;
    }
    
    
}
