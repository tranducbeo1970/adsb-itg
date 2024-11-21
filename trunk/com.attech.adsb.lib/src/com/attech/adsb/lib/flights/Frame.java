/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.lib.flights;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.poi.ss.usermodel.Row;

/**
 *
 * @author andh
 */
@XmlRootElement(name = "Frame")
@XmlAccessorType(XmlAccessType.NONE)
public class Frame {
    private static final SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss.SSS");
    
    @XmlElement(name = "Message")
    private List<Message> messages;
    
    @XmlAttribute(name = "start-time")
    private long startTime;
    
    @XmlAttribute(name = "end-time")
    private long endTime;
    
    private double valueProbability;
    private int numMsgHasPosition;
    
    public Frame() {
        this.messages = new ArrayList<>();
    }

    public Frame(long startTime, long endTime) {
        this();
        this.startTime = startTime;
        this.endTime = endTime;
    }
    
    public int add(Message message)  {
        long time = message.getReceivedTime();
        if (time < this.startTime) return 1;
        if (time >= endTime) return -1;
        this.messages.add(message);
        if (message.hasPosition())
            this.numMsgHasPosition++;
        this.setValueProbability((this.messages.size() > 0) ? this.numMsgHasPosition * 100.0 /this.messages.size() : 0);
        return 0;
    }
    
    public int size() {
        return this.messages.size();
    }
    
    public Message get(int index) {
        return this.messages.get(index);
    }
    
    public boolean hasHorizonalPositionReport() {
        if (this.messages.isEmpty()) return false;
        for (Message mesage : getMessages()) {
            if (mesage.hasPosition()) return true;
        }
        return false;
    }
    
    public boolean hasFlightLeverReport() {
        if (this.messages.isEmpty()) return false;
        for (Message mesage : getMessages()) {
            if (mesage.hasFlightLever()) return true;
        }
        return false;
    }
    
    @Override
    public String toString() {
        return String.format("%15d ~ %15d : %15d (ms)", this.getStartTime(), this.getEndTime(), (this.getEndTime() - this.getStartTime()));
    }
    
    public void printMessage() {
        int index = 1;
        for (Message message: getMessages()) {
            System.out.printf(" %5d %s", index, message);
            index++;
        }
    }
    
    public static void exportDetailReportHeader(Row row) {
        row.createCell(0).setCellValue("FRAME #");      
        row.createCell(1).setCellValue("START");
        row.createCell(2).setCellValue("END");
        row.createCell(3).setCellValue("GOOD");
        row.createCell(4).setCellValue("MESSAGE #");
        row.createCell(5).setCellValue("SIC");
        row.createCell(6).setCellValue("ADDRESS");
        row.createCell(7).setCellValue("CALLSIGN");
        row.createCell(8).setCellValue("LONGTITUDE");
        row.createCell(9).setCellValue("LATITUDE");
        row.createCell(10).setCellValue("FLIGH LEVEL");
        row.createCell(11).setCellValue("TRANSMISS TIME");
        row.createCell(12).setCellValue("NIC");
        row.createCell(13).setCellValue("NAC");
        row.createCell(14).setCellValue("SIL");
        row.createCell(15).setCellValue("RECEIVED TIME");
        row.createCell(16).setCellValue("DISTANCE");
    }
    
    public String[][] getDetailReportData() {

        if (this.getMessages().isEmpty()) {
            String [][] values = new String [1][];
            values[0] = new String []{
                "",
                format.format(new Date(getStartTime())),
                format.format(new Date(getEndTime())),
                Boolean.toString(hasHorizonalPositionReport()),
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                ""
            };
            return values;
        } else {
            String[][] values = new String [getMessages().size()][];
            for (int i = 0; i < getMessages().size(); i++) {
                Message message = getMessages().get(i);
                if (i == 0) {
                    values[i] = new String[]{
                        "",
                        format.format(new Date(getStartTime())),
                        format.format(new Date(getEndTime())),
                        Boolean.toString(hasHorizonalPositionReport()),
                        Integer.toString(i + 1),
                        Integer.toString(message.getSic()),
                        message.getAddress(),
                        message.getCallsign(),
                        message.getLng() == null ? "" : Double.toString(message.getLng()),
                        message.getLat() == null ? "" : Double.toString(message.getLat()),
                        message.getFlightLevel() == null ? "" : Float.toString(message.getFlightLevel()),
                        Double.toString(message.getTransmissTime()),
                        Integer.toString(message.getNic()),
                        Integer.toString(message.getNac()),
                        Integer.toString(message.getSil()),
                        format.format(new Date(message.getReceivedTime())),
                        Double.toString(message.getDistance())
                    };
                } else {
                    values[i] = new String[]{
                        "",
                        "",
                        "",
                        "",
                        Integer.toString(i + 1),
                        Integer.toString(message.getSic()),
                        message.getAddress(),
                        message.getCallsign(),
                        message.getLng() == null ? "" : Double.toString(message.getLng()),
                        message.getLat() == null ? "" : Double.toString(message.getLat()),
                        message.getFlightLevel() == null ? "" : Float.toString(message.getFlightLevel()),
                        Double.toString(message.getTransmissTime()),
                        Integer.toString(message.getNic()),
                        Integer.toString(message.getNac()),
                        Integer.toString(message.getSil()),
                        format.format(new Date(message.getReceivedTime())),
                        Double.toString(message.getDistance())
                    };
                }
            }
            return values;
        }
    }
    
    public List<Message> getMessages() {
        return this.messages;
    }

    /**
     * @param messages the messages to set
     */
    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    /**
     * @return the startTime
     */
    public long getStartTime() {
        return startTime;
    }

    /**
     * @param startTime the startTime to set
     */
    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    /**
     * @return the endTime
     */
    public long getEndTime() {
        return endTime;
    }

    /**
     * @param endTime the endTime to set
     */
    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    /**
     * @return the valueProbability
     */
    public double getValueProbability() {
        return valueProbability;
    }

    /**
     * @param valueProbability the valueProbability to set
     */
    public void setValueProbability(double valueProbability) {
        this.valueProbability = valueProbability;
    }
}
