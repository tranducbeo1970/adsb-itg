/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.lib.flights;

import com.attech.adsb.lib.common.Calculator;
import com.attech.adsb.lib.common.Distance;
import com.attech.cat21.v210.Cat21Message;
import com.attech.cat21.v210.QualityIndicator;
import com.attech.cat21.v210.TargetStatus;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author andh
 */
@XmlRootElement(name = "Message")
@XmlAccessorType(XmlAccessType.NONE)
public class Message implements Comparable<Message> {
    
    private static final SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss.SSS");
    
    @XmlAttribute(name = "file")
    private String file;
    
    @XmlAttribute(name = "no")
    private int no;
    
    @XmlAttribute(name = "sic")
    private int sic;

    @XmlAttribute(name = "address")
    private String address;

    @XmlAttribute(name = "callsign")
    private String callsign;

    @XmlAttribute(name = "longtitude")
    private Double lng;

    @XmlAttribute(name = "latitude")
    private Double lat;

    @XmlAttribute(name = "flight-level")
    private Float flightLevel;

    @XmlAttribute(name = "nic")
    private Integer nic;

    @XmlAttribute(name = "nac")
    private Integer nac;

    @XmlAttribute(name = "sil")
    private Integer sil;

    @XmlAttribute(name = "distance")
    private double distance;

    @XmlAttribute(name = "position-time")
    private Double positionTime;

    @XmlAttribute(name = "transmiss-time")
    private Double transmissTime;
    
    @XmlAttribute(name = "received-time")
    private long receivedTime;
    
    @XmlAttribute(name = "flight-level-age")
    private Double flightLevelAge;
    
    @XmlAttribute(name = "message-amptitude")
    private Integer messageAmplitude;
    
    @XmlAttribute(name = "time")
    private String time;
    
    @XmlAttribute(name = "priority-status")
    private Integer priorityStatus;
    @XmlAttribute(name = "bearing")
    private Double bearing;
    
    public Message() {
    }
    
    public Message(Cat21Message cat21Message) {
        this.sic = cat21Message.getSic();
        this.address = Integer.toHexString(cat21Message.getTargetAddress()).toUpperCase();
        this.callsign = cat21Message.getCallSign() == null ? "" : cat21Message.getCallSign().trim();
        
        if (cat21Message.getPosition() != null) {
            this.lng = cat21Message.getPosition().getLongtitude();
            this.lat = cat21Message.getPosition().getLatitude();
            this.positionTime = cat21Message.getTimeOfMessageReceptionOfPosition();
        } else if (cat21Message.getHighResolutionPosition() != null) {
            this.lng = cat21Message.getHighResolutionPosition().getLongtitude();
            this.lat = cat21Message.getHighResolutionPosition().getLatitude();
            this.positionTime = cat21Message.getTimeOfMessageReceptionOfPosition();
        }
        
        this.transmissTime = cat21Message.getTimeOfReportTranmission();
        
        if (cat21Message.getFlightLevel() != null) {
            this.flightLevel = cat21Message.getFlightLevel();
        }
        
        this.messageAmplitude = cat21Message.getMessageAmplitude();
        
        final QualityIndicator qualityIndicator = cat21Message.getQualityIndicator();
        if (qualityIndicator != null) {
            nic = qualityIndicator.getnIC();
            nac = qualityIndicator.getnACForPosition() == null ? 0 : qualityIndicator.getnACForPosition().intValue();
            sil = qualityIndicator.getsIL() == null ? 0 : qualityIndicator.getsIL().intValue();
        }
        
        final TargetStatus status = cat21Message.getTargetStatus();
        
        if (status != null) {
            this.priorityStatus = (int) status.getPriorityStatus();
        }
        
        if (cat21Message.getDataAges() != null) {
            this.flightLevelAge = cat21Message.getDataAges().getFlightLevelAge();
        }
    }
    
    public Message(Cat21Message cat21Message, long receivedTime) {
        this(cat21Message);
        this.receivedTime = receivedTime;
        this.time = format.format(new Date(receivedTime));
    }
    
    public boolean hasPosition() {
        return !(lng == null || lat == null);
    }
    
    public boolean hasFlightLever() {
        return !(flightLevel == null);
    }
    
    public boolean valid3DPosition() {
        return !(lng == null || lat == null || flightLevel == null);
    }

    public int getSic() {
        return sic;
    }

    public void setSic(int sic) {
        this.sic = sic;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCallsign() {
        return callsign;
    }

    public void setCallsign(String callsign) {
        this.callsign = callsign;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getPositionTime() {
        return positionTime;
    }

    /**
     * @param positionTime the positionTime to set
     */
    public void setPositionTime(Double positionTime) {
        this.positionTime = positionTime;
    }

    /**
     * @return the transmissTime
     */
    public Double getTransmissTime() {
        return transmissTime;
    }

    /**
     * @param transmissTime the transmissTime to set
     */
    public void setTransmissTime(Double transmissTime) {
        this.transmissTime = transmissTime;
    }

    /**
     * @return the flightLevel
     */
    public Float getFlightLevel() {
        return flightLevel;
    }

    /**
     * @param flightLevel the flightLevel to set
     */
    public void setFlightLevel(Float flightLevel) {
        this.flightLevel = flightLevel;
    }

    /**
     * @return the nic
     */
    public Integer getNic() {
        return nic;
    }

    /**
     * @param nic the nic to set
     */
    public void setNic(Integer nic) {
        this.nic = nic;
    }

    /**
     * @return the nac
     */
    public Integer getNac() {
        return nac;
    }

    /**
     * @param nac the nac to set
     */
    public void setNac(Integer nac) {
        this.nac = nac;
    }

    /**
     * @return the sil
     */
    public Integer getSil() {
        return sil;
    }

    /**
     * @param sil the sil to set
     */
    public void setSil(Integer sil) {
        this.sil = sil;
    }

    /**
     * @return the receivedTime
     */
    public long getReceivedTime() {
        return receivedTime;
    }

    /**
     * @param receivedTime the receivedTime to set
     */
    public void setReceivedTime(long receivedTime) {
        this.receivedTime = receivedTime;
        this.time = format.format(new Date(receivedTime));
    }

    @Override
    public int compareTo(Message o) {
        if (this.receivedTime == o.getReceivedTime()) return 0;
        return this.receivedTime < o.getReceivedTime() ? -1 : 1;
        // if (Objects.equals(this.positionTime, o.getPositionTime())) return 0;
        // return this.positionTime < o.getPositionTime()? -1 : 1;
    }
    
    @Override
    public String toString() {
        return String.format("SIC: %3d ADD: %8s CALLSIGN: %8s LNG: %3.5f LAT: %3.5f FL: %4.3f P-TIME: %5.8f T-TIME: %5.8f NIC: %2d NAC: %2d SIL: %2d R-TIME: %20d DISTANCE: %5.8f",
                sic, 
                address, 
                callsign, 
                lng, 
                lat, 
                flightLevel,
                positionTime, 
                transmissTime, 
                nic, 
                nac, 
                sil, 
                receivedTime, getDistance());
    }
    
    public String key() {
        return String.format("%s.%s", this.address, this.callsign);
    }
    
    public void distance2Point(double longtitude, double latitude) {
        if (this.lat == null || this.lng == null) return;
        this.setDistance(Calculator.distanceBetween(this.lng, this.lat, longtitude, latitude));
    }
    
    public double getDistance2Point(double longtitude, double latitude) {
        //if (this.lat == null || this.lng == null) return;
        return Calculator.distanceBetween(this.lng, this.lat, longtitude, latitude);
    }
    
    public void Bearing2Point(double longtitude, double latitude) {
        if (this.lat == null || this.lng == null) return;
        this.bearing = Distance.Bearing( longtitude, latitude, this.lng, this.lat);
    }

    /**
     * @return the distance
     */
    public double getDistance() {
        return distance;
    }

    /**
     * @return the no
     */
    public int getNo() {
        return no;
    }

    /**
     * @param no the no to set
     */
    public void setNo(int no) {
        this.no = no;
    }

    /**
     * @return the time
     */
    public String getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * @return the priorityStatus
     */
    public Integer getPriorityStatus() {
        return priorityStatus;
    }

    /**
     * @param priorityStatus the priorityStatus to set
     */
    public void setPriorityStatus(Integer priorityStatus) {
        this.priorityStatus = priorityStatus;
    }

    /**
     * @return the file
     */
    public String getFile() {
        return file;
    }

    /**
     * @param file the file to set
     */
    public void setFile(String file) {
        this.file = file;
    }

    /**
     * @return the flightLevelAge
     */
    public Double getFlightLevelAge() {
        return flightLevelAge;
    }

    /**
     * @param flightLevelAge the flightLevelAge to set
     */
    public void setFlightLevelAge(Double flightLevelAge) {
        this.flightLevelAge = flightLevelAge;
    }

    /**
     * @return the bearing
     */
    public Double getBearing() {
        return bearing;
    }

    /**
     * @param bearing the bearing to set
     */
    public void setBearing(Double bearing) {
        this.bearing = bearing;
    }

    /**
     * @param distance the distance to set
     */
    public void setDistance(double distance) {
        this.distance = distance;
    }

    /**
     * @return the messageAmplitude
     */
    public Integer getMessageAmplitude() {
        return messageAmplitude;
    }

    /**
     * @param messageAmplitude the messageAmplitude to set
     */
    public void setMessageAmplitude(Integer messageAmplitude) {
        this.messageAmplitude = messageAmplitude;
    }
   
}
