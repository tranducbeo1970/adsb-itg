/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asterix;

import com.attech.asterix.cat21.entities.Position;
import com.attech.asterix.cat21.entities.v21.QualityIndicator;
import com.attech.asterix.cat21.entities.v21.TargetReportDescriptor;
import java.io.Serializable;

/**
 *
 * @author root
 */
public class InternalMessage implements Serializable{
    
    private short sic, sac;
    private Integer targetAddress;
    private int headerLength;
    private float version;
    private long receivedTime;
    
    private Double timeOfMessageReceptionOfPosition;
    private String targetIdentification;
    private TargetReportDescriptor targetDescriptor;
    private Position position;
    private QualityIndicator qualityIndicator;
    private Float flightLevel;
    private byte[] bytes;
    private int [] fields;
    private boolean [] headers;
    
    public InternalMessage(float version) {
        this.version = version;
        receivedTime = System.currentTimeMillis();
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(" ");
        builder.append("SAC: ").append(sac).append(" SIC: ").append(sic).append(" ");
        builder.append("ADD: ").append(getTargetAddress()).append(" ");
        builder.append("CALLSIGN: ").append(getTargetIdentification()).append(" ");
        builder.append("FLIGH LEVEL: ").append(getFlightLevel()).append(" ");
        builder.append("POSITION: ").append(getPosition()).append(" ");
        builder.append("TIME: ").append(getTimeOfMessageReceptionOfPosition()).append(" ");
        return builder.toString().replace("null", " ").replace("  ", " ");
    }
    
    public long getCode() {
        long code = targetAddress * 1000000 + sac * 1000 + sic;
        // System.out.println("get code " + targetAddress + " + " + sac + " + " + sic + " " + code + " Hash Code: " + this.hashCode());
        return code;
    }
    
    @Override
    public  int hashCode() {
        int value = sac * 1000 + sic;
        return value;
    }
    
    @Override
   public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof InternalMessage)) return false;
        return obj == this;
    }

    public byte[] getBytes() { return bytes; }
    
    public float getVersion() { return this.version; }

    public short getSic() { return sic; }
 
    public void setSic(short sic) { this.sic = sic; }

    public short getSac() { return sac; }

    public void setSac(short sac) { this.sac = sac; }

    public Integer getTargetAddress() { return targetAddress; }

    public void setTargetAddress(Integer targetAddress) { this.targetAddress = targetAddress; }

    public String getTargetIdentification() { return targetIdentification; }

    public void setTargetIdentification(String targetIdentification) { this.targetIdentification = targetIdentification; }

    public TargetReportDescriptor getTargetDescriptor() { return targetDescriptor; }

    public void setTargetDescriptor(TargetReportDescriptor targetDescriptor) { this.targetDescriptor = targetDescriptor; }

    public Position getPosition() { return position; }

    public void setPosition(Position position) { this.position = position; }

    public QualityIndicator getQualityIndicator() { return qualityIndicator; }

    public void setQualityIndicator(QualityIndicator qualityIndicator) { this.qualityIndicator = qualityIndicator; }

    public Float getFlightLevel() { return flightLevel; }

    public void setFlightLevel(Float flightLevel) { this.flightLevel = flightLevel; }

    public void setBytes(byte[] bytes) { this.bytes = bytes; }
    
    public void setVersion(float version) { this.version = version; }

    public long getReceivedTime() { return receivedTime; }

    public int[] getFields() { return fields; }

    public void setFields(int[] fields) { this.fields = fields; }

    public boolean[] getHeaders() { return headers; }

    public void setHeaders(boolean[] headers) { this.headers = headers; }

    public int getHeaderLength() { return headerLength; }

    public void setHeaderLength(int headerLength) { this.headerLength = headerLength; }

    public Double getTimeOfMessageReceptionOfPosition() { return timeOfMessageReceptionOfPosition; }

    public void setTimeOfMessageReceptionOfPosition(Double timeOfMessageReceptionOfPosition) { this.timeOfMessageReceptionOfPosition = timeOfMessageReceptionOfPosition; }
}
