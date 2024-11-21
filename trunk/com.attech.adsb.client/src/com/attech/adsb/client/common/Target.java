/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.common;

import com.attech.adsb.client.common.enums.MeasureUnit;
import com.attech.adsb.client.common.enums.WarnType;
import com.attech.adsb.client.config.Configuration;
import com.attech.adsb.client.config.MTCAConfig;
import com.attech.adsb.client.config.Point2f;
import com.attech.adsb.client.config.STCAConfig;
import com.attech.adsb.client.config.SignalQuality;
import com.attech.adsb.client.dto.Aircrafts;
import com.attech.adsb.client.dto.FlightPlan;
import com.attech.adsb.client.graphic.Calculator;
import com.attech.asterix.cat21.entities.Position;
import com.attech.asterix.cat21.entities.v21.TargetStatus;
import com.attech.asterix.cat21.v21.Message;
import com.attech.cat01.v120.Cat01Message;
import com.attech.cat48.v121.CalculatedPolarVelocity;
import com.attech.cat48.v121.CartesianCoordinate;
import com.attech.cat48.v121.Cat48Message;
import com.attech.cat48.v121.PolarCoordinate;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author andh
 */
@XmlRootElement(name = "Target")
@XmlAccessorType(XmlAccessType.NONE)
public final class Target extends XmlSerializer {


    private static final STCAConfig sctaConfig = Configuration.instance().getStcaConfig();
    private static final MTCAConfig mtcaConfig = Configuration.instance().getMtcaConfig();
    private static final SignalQuality signalQuality = Configuration.instance().getPreference().getSignalQuality();
    private static final int tmaWarningThreshold = Configuration.instance().getPreference().getTmaWarningThreshold();
    
    private SimpleDateFormat dofFormat = new SimpleDateFormat("yyMMdd");

    private List<GraphicNotify> graphicNotifyList;

    @XmlElement(name = "Callsign")
    private String callSign;

    @XmlElement(name = "Address")
    private String address;

    @XmlElement(name = "FlightLevel")
    private float flightLevel;

    @XmlElement(name = "Speed")
    private double speed;

    @XmlElement(name = "Heading")
    private double heading;

    @XmlElement(name = "VerticalRate")
    private double verticalRate;

    @XmlElement(name = "Longtitude")
    private double longtitude;

    @XmlElement(name = "Latitude")
    private double latitude;

    @XmlElement(name = "TimeOfDay")
    private double timeOfDay;

    @XmlElement(name = "InitialTime")
    private long initialTime;

    @XmlElement(name = "NIC")
    private int nic;

    @XmlElement(name = "NAC")
    private int nac;

    @XmlElement(name = "SIL")
    private int sil;

    @XmlElement(name = "LastUpdate")
    private Date lastUpdate;

    @XmlElement(name = "LastUpdateInMil")
    private long lastUpdateInMilis;

    @XmlElement(name = "Mode3A")
    private int mode3a;

    @XmlElement(name = "TargetStatus")
    private int targetStatus = 0;               // trang thai cua may nay phat ve 0,1,2,3,4,5,6

    @XmlElement(name = "Available")
    private boolean available = false;

    @XmlElement(name = "Controller")
    private String controller;

    @XmlElement(name = "FlightPlan")
    private String flightPlan;

    @XmlElementWrapper(name = "Plots")
    @XmlElement(name = "Plots")
    private List<Plot> plots = new ArrayList<>();

    @XmlElement(name = "TrackAge")
    private TrackAge trackAge;

    @XmlElement(name = "TrackStatus")
    private TrackStatus trackStatus;

    @XmlElement(name = "Warning")
    private Map<WarnType, String> warningList = new HashMap<>();
    
    @XmlElement(name = "Note")
    private String hdgNote;

    @XmlElement(name = "Info")
    private Aircrafts info;

    @XmlElement(name = "Fpl")
    private FlightPlan fpl;
    
    @XmlElement(name = "DOF")
    private String dof; 

    private boolean expired;
    private boolean isCancel;
    private boolean dummy;
    private TrackCondition trackCondition;
    
    private Point2f speedVectorPoint;
    private Point2f aheadVectorPoint;
    private Point2f conflictVertorPoint;
    private Point2f point;
    private int hitLevel;
    private int warnLevel;
    
    private boolean obsoleted;
    
    private final Map<Integer, Point2f> radar;

    public Target() {
	this.point = new Point2f();
	this.speedVectorPoint = new Point2f();
        this.trackAge = TrackAge.GOOD;
        this.trackCondition = TrackCondition.NORMAL;
	this.trackStatus = TrackStatus.NONE;
	this.graphicNotifyList = new ArrayList<>();
        this.hitLevel = 0;
        this.warnLevel = 0;
        
        this.dof = dofFormat.format(new Date());
        //NhuongND TEST
        //Date myDate = new Date(2022, 04, 10);
        //this.dof = dofFormat.format(myDate);
        
        /* TAM THOI. NEN DAT RA FILE CONFIG */
        this.radar = new HashMap<>();
        this.radar.put(1, new Point2f(105.80190f,21.19778f));
        this.radar.put(2, new Point2f(108.24470f,16.13333f));
        this.radar.put(3, new Point2f(106.65830f,10.82778f));
        this.radar.put(5, new Point2f(106.65830f,10.82778f));
        this.radar.put(6, new Point2f(108.25890f,16.13111f));
        this.radar.put(132, new Point2f(105.66830f,18.73167f));
        this.radar.put(133, new Point2f(109.19530f,13.74306f));
        this.radar.put(134, new Point2f(105.16780f,9.18111f));
    }

    public Target(Message message) {
        this();
        this.address = Integer.toHexString(message.getTargetAddress()).toUpperCase();
        this.initialTime = System.currentTimeMillis();
	this.point = new Point2f();
	this.speedVectorPoint = new Point2f();
        this.update(message);
    }
    
    public Target(Cat48Message message) {
        this();
        this.address = Integer.toHexString(message.getTargetAddress()).toUpperCase();
        this.initialTime = System.currentTimeMillis();
	this.point = new Point2f();
	this.speedVectorPoint = new Point2f();
        this.update(message);
    }
    
    public Target(Cat01Message message) {
        this();
        this.address = Integer.toString(message.getNo());
        this.initialTime = System.currentTimeMillis();
	this.point = new Point2f();
	this.speedVectorPoint = new Point2f();
        this.update(message);
    }

    public void update(Message message) {
        try {
            lastUpdateInMilis = System.currentTimeMillis();
            this.setTargetStatus(message.getTargetStatus());
            this.setCallSign(message.getCallSign());
            this.setMode3a(message.getMode3a());

            Position pos = message.getHighResolutionPosition();
            if(pos == null) pos = message.getPosition();
            if (pos == null) return;
            this.setTargetPosition(pos);

            Double messageTime = message.getTimeOfMessageReceptionOfPosition();
            if (messageTime == null) messageTime = message.getTimeOfReportTranmission();
            
//            System.out.println("TimeOfMessageReceptionOfPosition : " + message.getTimeOfMessageReceptionOfPosition());
//            System.out.println("TimeOfAplicabilityPosition : " + message.getTimeOfAplicabilityPosition());
//            System.out.println("TimeOfMessageReceptionOfPositionHightPrecisions : " + message.getTimeOfMessageReceptionOfPositionHightPrecisions());
//            System.out.println("TimeOfReportTranmission : " + message.getTimeOfReportTranmission());
//            
//            System.out.println("TIME : " + messageTime + " TIME OF DATE: " + timeOfDay);
            /** Checking this code **/
            if (messageTime == null || messageTime < this.timeOfDay && messageTime > timeOfDay - 84600) {
                return;
            }
            
            if (messageTime != null) {
                 this.timeOfDay = messageTime;
            }
            
            this.setFlightLevel(message.getFlightLevel());
            
            if (message.getBarometricVerticalRate() != null) {
                this.verticalRate = (message.getBarometricVerticalRate().getValue() / 100);
            } else if (message.getGeometricVerticalRate() != null) {
                this.verticalRate = (message.getGeometricVerticalRate().getValue() / 100);
            }
            
            if (message.getAirborneGroundVector() != null) {
                this.speed = message.getAirborneGroundVector().getGroundSpeed();
                this.heading = (float) message.getAirborneGroundVector().getTrackAngle();
            } else {
                if (message.getAirSpeed() != null) this.speed = message.getAirSpeed().getValue();
                if (message.getMagneticHeading() != null)  this.heading = message.getMagneticHeading();
            }
            
            if (message.getQualityIndicator() != null) {
                this.nic = message.getQualityIndicator().getnIC();
                this.nac = message.getQualityIndicator().getnACForPosition() == null ? 0 : message.getQualityIndicator().getnACForPosition();
                this.sil = message.getQualityIndicator().getsIL() == null ? 0 : message.getQualityIndicator().getsIL();
                this.validateTargetSignalQuality();
                
                //System.out.println("Target " + this.callSign + " " + this.nic + " " + this.nac + " " + this.sil);
            }
            
            calculate();
            available = true;
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        lastUpdateInMilis = System.currentTimeMillis();
    }
    
    public void update(Cat48Message message) {
        try {
            
            Double messageTime = message.getTimeOfDay();
            
            //System.out.println("TIME : " + messageTime + " TIME OF DATE: " + timeOfDay);
            /** Checking this code **/
            if (messageTime == null || messageTime < this.timeOfDay && messageTime > timeOfDay - 84600) {
                return;
            }
            lastUpdateInMilis = System.currentTimeMillis();
            this.timeOfDay = messageTime;
            
            if (message.getCallsign() != null)
                this.setCallSign(message.getCallsign());
            if (message.getCode3A() != null)
                this.setMode3a(message.getCode3A().getValue());
            
            if (message.getFlightLevel() != null) {
                double fl = new BigDecimal(message.getFlightLevel().getValue()/ 100).setScale(2, RoundingMode.HALF_UP).doubleValue();
                this.setFlightLevel((float) fl);
            }
            
            CartesianCoordinate cartesionCoordinate = message.getCartesianCoordinate();
            PolarCoordinate polarCoordinate = message.getPolarCoordinate();
            if (cartesionCoordinate != null && polarCoordinate != null) {
                Position pos = Calculator.CalculateWgs84(polarCoordinate.getRho(), polarCoordinate.getTheta(), radar.get(message.getSic()).y, radar.get(message.getSic()).x);
                //System.out.println("Update target " + address + " ps: " + pos.getLatitude() + " - " + pos.getLongtitude());
                this.setTargetPosition(pos);

            }
            
            // TAM THOI DAT nIC, nAC, sIL DE KHONG CANH BAO
            this.nac = 9;
            this.nic = 7;
            this.sil = 3;
            
            CalculatedPolarVelocity vector = message.getCalculatedPolarVelocity();
            if (vector != null) {
                this.speed = new BigDecimal(vector.getSpeed()).setScale(2, RoundingMode.HALF_UP).doubleValue();
                this.heading = new BigDecimal(vector.getHeading()).setScale(2, RoundingMode.HALF_UP).doubleValue();
            }
            
            calculate();
            available = true;
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void update(Cat01Message message) {
        try {
            Double messageTime = message.getTimeOfDay();
            
            //System.out.println("TIME : " + messageTime + " TIME OF DATE: " + timeOfDay);
            /** Checking this code **/
            if (messageTime == null || messageTime < this.timeOfDay && messageTime > timeOfDay - 84600) {
                return;
            }
            lastUpdateInMilis = System.currentTimeMillis();
            this.timeOfDay = messageTime;
            
            if (message.getMode3ACode() != null){
                this.setCallSign(Integer.toString(message.getMode3ACode().getValue()));
                this.setMode3a(message.getMode3ACode().getValue());
            }
            
            if (message.getModeC()!= null) {
                double fl = new BigDecimal(message.getModeC().getValue()/ 100).setScale(2, RoundingMode.HALF_UP).doubleValue();
                this.setFlightLevel((float) fl);
            }
            
            com.attech.cat01.v120.CartesianCoordinate cartesionCoordinate = message.getCartesionCoordinate();
            com.attech.cat01.v120.PolarCoordinate polarCoordinate = message.getPolarCoordinate();
            if (cartesionCoordinate != null && polarCoordinate != null) {
                Position pos = Calculator.CalculateWgs84(polarCoordinate.getRho(), polarCoordinate.getTheta(), radar.get(message.getSic()).y, radar.get(message.getSic()).x);
                //System.out.println("Update target " + address + " ps: " + pos.getLatitude() + " - " + pos.getLongtitude());
                
                this.setTargetPosition(pos);

            }
            
            // TAM THOI DAT nIC, nAC, sIL DE KHONG CANH BAO
            this.nac = 9;
            this.nic = 7;
            this.sil = 3;
            
            com.attech.cat01.v120.CalculatedPolarVelocity vector = message.getPolarVelocity();
            if (vector != null) {
                this.speed = new BigDecimal(vector.getSpeed()).setScale(2, RoundingMode.HALF_UP).doubleValue();
                this.heading = new BigDecimal(vector.getHeading()).setScale(2, RoundingMode.HALF_UP).doubleValue();
            }
            
            calculate();
            available = true;
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    
    public void calculate() {
	if (!this.isAvailable()) return;
   
	double speedKPM = this.speed / 60;
        speedVectorPoint = Calculator.calculateLocation(this.longtitude, this.latitude, speedKPM * AppContext.instance().getSpeedVector(), this.heading, MeasureUnit.NM);
        aheadVectorPoint = Calculator.calculateLocation(this.longtitude, this.latitude, (this.speed / 3600) * tmaWarningThreshold, this.heading, MeasureUnit.NM);
        
//        float distance = (float) (((this.speed * 1.825f) / 3600) * sctaConfig.getWarningTimeInAdvance() * 1);
        float distance = (float) ((this.speed / 3600) * sctaConfig.getWarningTimeInAdvance() * 1);
        conflictVertorPoint = Calculator.calculateLocation(this.longtitude, this.latitude, distance, this.heading, MeasureUnit.NM);
        conflictVertorPoint.z = (float) (this.flightLevel + (this.verticalRate / 60) * sctaConfig.getWarningTimeInAdvance()); // convert to meter
    }
    
    public Point2f getNextPointInDistance(float distance, MeasureUnit unit) {
        return  Calculator.calculateLocation(this.longtitude, this.latitude, distance, this.heading, unit);
    }
    
    public int validateConflict(Target target) {
        if (!this.available || !target.isAvailable()) return 0;
        // Using unit in NM and Feet
        // Check for current position
        if (this.flightLevel <= 0 || target.getFlightLevel() <= 0)
            return 0;
        double deltaWidth = Calculator.getDistance( this.longtitude, this.latitude,  target.getLongtitude(), target.getLatitude(), MeasureUnit.NM); // unit is NM
        double deltaHeight = Math.abs(this.flightLevel - target.getFlightLevel()) * 100; // unit is Feet
        
//        double minimumDistanceWidth = Parameter.WarningHitDistance * 2;
//        double minimumDistanceHeigth = Parameter.WarningHitLevel * 2;
        
        if (deltaWidth < sctaConfig.getHorizonAlarmThreshold() && deltaHeight < sctaConfig.getVerticalAlarmThreshold()) {
            /*
            System.out.println(String.format("Target 1: %s/%s FL=%s->%s (ft)  |  Target 2: %s/%s FL=%s->%s (ft)",
                    this.address, this.callSign, this.flightLevel, this.flightLevel * 100,
                    target.address, target.callSign, target.flightLevel, target.flightLevel * 100
                    ));
            System.out.println(String.format("deltaHeight = %s(ft) | sctaHeightThreshold = %s", deltaHeight, sctaConfig.getVerticalWarnThreshold()));
            */
            return 2;
        }

        if (deltaWidth < sctaConfig.getHorizonWarnThreshold() && deltaHeight < sctaConfig.getVerticalWarnThreshold()) {
            /*
            System.out.println(String.format("Target 1: %s/%s FL=%s->%s (ft)  |  Target 2: %s/%s FL=%s->%s (ft)",
                    this.address, this.callSign, this.flightLevel, this.flightLevel * 100,
                    target.address, target.callSign, target.flightLevel, target.flightLevel * 100
                    ));
            System.out.println(String.format("deltaHeight = %s(ft) | sctaHeightThreshold = %s", deltaHeight, sctaConfig.getVerticalWarnThreshold()));
            */
            return 1;
        }
        if (conflictVertorPoint == null || target.getConflictVertorPoint() == null)
            return 0;
        deltaWidth = Calculator.getDistance(conflictVertorPoint, target.getConflictVertorPoint(), MeasureUnit.NM);
        deltaHeight = Math.abs(conflictVertorPoint.z - target.getConflictVertorPoint().z) * 100;
        
        if (deltaWidth < sctaConfig.getHorizonWarnThreshold() && deltaHeight < sctaConfig.getVerticalWarnThreshold()) {
            /*
            System.out.println(String.format("Target 1: %s/%s FL=%s->%s (ft)  |  Target 2: %s/%s FL=%s->%s (ft)",
                    this.address, this.callSign, this.flightLevel, this.flightLevel * 100,
                    target.address, target.callSign, target.flightLevel, target.flightLevel * 100
                    ));
            System.out.println(String.format("deltaHeight = %s(ft) | sctaHeightThreshold = %s", deltaHeight, sctaConfig.getVerticalWarnThreshold()));
            */
            return 1;
        }
        return 0;
    }
    
    // NhuongND add
    public int validateConflictMTCS(Target target) {
        if (!this.available || !target.isAvailable()) return 0;
        // Using unit in NM and Feet
        // Check for current position
        if (this.flightLevel <= 0 || target.getFlightLevel() <= 0)
            return 0;
        double deltaWidth = Calculator.getDistance( this.longtitude, this.latitude,  target.getLongtitude(), target.getLatitude(), MeasureUnit.NM); // unit is NM
        double deltaHeight = Math.abs(this.flightLevel - target.getFlightLevel()) * 100; // unit is Feet
                        
        if (deltaWidth < mtcaConfig.getHorizonAlarmThreshold() && deltaHeight < mtcaConfig.getVerticalAlarmThreshold()) {      
            return 2;
        }

        if (deltaWidth < mtcaConfig.getHorizonWarnThreshold() && deltaHeight < mtcaConfig.getVerticalWarnThreshold()) {
            return 1;
        }
        if (conflictVertorPoint == null || target.getConflictVertorPoint() == null)
            return 0;
        deltaWidth = Calculator.getDistance(conflictVertorPoint, target.getConflictVertorPoint(), MeasureUnit.NM);
        deltaHeight = Math.abs(conflictVertorPoint.z - target.getConflictVertorPoint().z) * 100;
        
        if (deltaWidth < mtcaConfig.getHorizonWarnThreshold() && deltaHeight < mtcaConfig.getVerticalWarnThreshold()) {
            return 1;
        }
        return 0;
    }
    
    public void validateTargetAge(long period) {
        if (dummy) return;
        long delta = System.currentTimeMillis() - this.lastUpdateInMilis;
        if (delta < period) {
            this.trackAge = TrackAge.GOOD;
            return;
        }
        
        if (delta < 2*period) {
            this.trackAge = TrackAge.EXPIRED;
            return;
        }
        
        this.trackAge = TrackAge.OBSOLETED;
        this.available = false;
    }
    
    public void validateTargetAge() {
        validateTargetAge(Configuration.instance().getPreference().getTargetTimeout());
    }
    
    public synchronized void commitChange(Boolean isFreshAll) {
	for (GraphicNotify graphic : this.graphicNotifyList) {
	    graphic.objectChanged(isFreshAll);
	}
    }
    
    @Override
    public int hashCode() {
        return getAddress().hashCode();
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (object instanceof String) {
            String add = (String) object;
            return !((this.address == null) || (this.address != null && !this.address.equalsIgnoreCase(add)));
        }
               
        if (!(object instanceof Target)) {
            return false;
        }
        Target other = (Target) object;
        if ((this.address == null && other.getAddress() != null) || (this.address != null && !this.address.equalsIgnoreCase(other.getAddress()))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.format("%s:%s FLVL:%s SPD:%s HEADING:%s VRATE:%s LON:%s LAT:%s QUALITY: %s %s %s",
                this.address,
                this.callSign,
                this.flightLevel,
                this.speed,
                this.heading,
                this.verticalRate,
                this.longtitude,
                this.latitude,
                this.nic,
                this.nac,
                this.sil);
    }
    
    public void validateTargetReportedStatus() {
        switch (this.targetStatus) {
            case 0:
                this.warningList.remove(WarnType.TARGET_REPORT);
                break;
//            case 1:
//                this.warningList.put(WarnType.TARGET_REPORT, String.format("Target emergency(%s)", code));
//                break;
            case 2:
                this.warningList.put(WarnType.TARGET_REPORT, "Lifeguard/medical emergency(2)");
                break;
            case 3:
                this.warningList.put(WarnType.TARGET_REPORT, "Minimum fuel(3)");
                break;
            case 4:
                this.warningList.put(WarnType.TARGET_REPORT, "No communications(4)");
                break;
            case 5:
                this.warningList.put(WarnType.TARGET_REPORT, "Unlawful interference(5)");
                break;
            case 6:
                this.warningList.put(WarnType.TARGET_REPORT, "Target Downed(6)");
                break;
            default:
                this.warningList.put(WarnType.TARGET_REPORT, String.format("Target emergency(%s)", this.targetStatus));
                break;
        }
    }
    
    public void validateTargetSignalQuality() {
        if (signalQuality.validate(this.nic, this.nac, this.sil)) {
            this.warningList.remove(WarnType.LOW_QUALITY);
        } else {
            this.warningList.put(WarnType.LOW_QUALITY, String.format("Low signal (%s-%s-%s)", this.nic, this.nac, this.sil));
        }
    }

    public void setTargetStatus(TargetStatus targetStatus) {
        if (targetStatus == null) return;
        int code = (int) targetStatus.getPriorityStatus();
        this.targetStatus = code;
        validateTargetReportedStatus();
    }
    
    private void setTargetPosition(Position pos) {
        if (pos == null) return;
        this.latitude = (float) pos.getLatitude();
        this.longtitude = pos.getLongtitude();
        this.point = new Point2f(longtitude, latitude);
        Plot plot = new Plot(longtitude, latitude);
        this.plots.add(plot);
    }

    //<editor-fold defaultstate="collapsed" desc=" Class properties ">
    public String getCallSign() {
        return callSign;
    }

    public void setCallSign(String callSign) {
        if (callSign == null || callSign.isEmpty()) return;
        this.callSign = callSign.trim().toUpperCase();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getFlightLevel() {
        return flightLevel;
    }

    public void setFlightLevel(Float flightLevel) {
        if (flightLevel == null) return;
        this.flightLevel = flightLevel;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getHeading() {
        return heading;
    }

    public void setHeading(double heading) {
        this.heading = heading;
    }

    public double getVerticalRate() {
        return verticalRate;
    }

    public void setVerticalRate(double verticalRate) {
        this.verticalRate = verticalRate;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getTimeOfDay() {
        return timeOfDay;
    }

    public void setTimeOfDay(double timeOfDay) {
        this.timeOfDay = timeOfDay;
    }

    public int getNic() {
        return nic;
    }

    public void setNic(int nic) {
        this.nic = nic;
    }

    public int getNac() {
        return nac;
    }

    public void setNac(int nac) {
        this.nac = nac;
    }

    public int getSil() {
        return sil;
    }

    public void setSil(int sil) {
        this.sil = sil;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public long getLastUpdateInMilis() {
        return lastUpdateInMilis;
    }

    public void setLastUpdateInMilis(long lastUpdateInMilis) {
        this.lastUpdateInMilis = lastUpdateInMilis;
    }

    public List<Plot> getPlots() {
        return plots;
    }

    public void setPlots(List<Plot> plots) {
        this.plots = plots;
    }

    public int getTargetStatus() {
        return targetStatus;
    }

    public void setTargetStatus(Integer targetStatus) {
        this.targetStatus = targetStatus;
    }

    public int getMode3a() {
        return mode3a;
    }

    public void setMode3a(Integer mode3a) {
        if (mode3a == null) return;
        this.mode3a = mode3a;
    }

    public long getInitialTime() {
        return initialTime;
    }

    public void setInitialTime(long initialTime) {
        this.initialTime = initialTime;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
    
    public Point2f getAHeadVectorPoint() {
        return aheadVectorPoint;
    }
    
    public Point2f getPosition() {
        return new Point2f(this.longtitude, this.latitude);
    }
    
    public Map<WarnType, String> getWarningList() {
        return warningList;
    }

    public void setWarningList(Map<WarnType, String> warningList) {
        this.warningList = warningList;
    }
    
    public void setWarning(WarnType type, String content) {
//        if (this.warningList.containsKey(type)) {
//            WarnLevel oldLevel = this.warningList.get(type);
//            if (oldLevel.getValue() >= content.getValue()) { return; }
//            this.warningList.put(type, content);
//            return;
//        }
//        
        this.warningList.put(type, content);
        System.out.println("Target "  +this.callSign + " added warning " + content) ;
    }
    
    public void clearWarning() {
        this.warningList.clear();
        hitLevel = 0;
        warnLevel = 0;
    }
    
    public void remove(WarnType type) {
        if (this.warningList.containsKey(type)) this.warningList.remove(type);
    }
    
    public boolean isWarning() {
        return this.warningList.size() > 0;
    }
    
    public Point2f getSpeedVector() {
	return speedVectorPoint;
    }

    public Point2f getPoint() {
	return point;
    }

    public Point2f getConflictVertorPoint() {
	return conflictVertorPoint;
    }
    
    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }
    
    public TrackAge getTrackAge() {
        return trackAge;
    }

    public void setTrackAge(TrackAge trackAge) {
        this.trackAge = trackAge;
    }

    public TrackCondition getTrackCondition() {
        return trackCondition;
    }

    public void setTrackCondition(TrackCondition trackCondition) {
        this.trackCondition = trackCondition;
    }

    public synchronized void setGraphicNotifyList(List<GraphicNotify> graphicNotifyList) {
	this.graphicNotifyList = graphicNotifyList;
    }
    
    public synchronized void addGraphicNotify(GraphicNotify graphicNotifyList) {
	this.graphicNotifyList.add(graphicNotifyList);
    }

    public TrackStatus getTrackStatus() {
	return trackStatus;
    }

    public void setTrackStatus(TrackStatus trackStatus) {
	this.trackStatus = trackStatus;
    }
    
    public String getController() {
	return controller;
    }

    public void setController(String controller) {
	this.controller = controller;
    }

    public String getFlightPlan() {
	return flightPlan;
    }

    public void setFlightPlan(String flightPlan) {
	this.flightPlan = flightPlan;
    }
       
    public int getHitLevel() {
        return hitLevel;
    }

    public int getWarnLevel() {
        return warnLevel;
    }

    public void setWarnLevel(int warnLevel) {
        this.warnLevel = warnLevel > this.warnLevel ? warnLevel : this.warnLevel;
    }
    
    
    /**
     * @return the obsoleted
     */
    public boolean isObsoleted() {
        return this.trackAge == TrackAge.OBSOLETED;
//        return obsoleted;
    }

    /**
     * @param obsoleted the obsoleted to set
     */
    public void setObsoleted(boolean obsoleted) {
        this.obsoleted = obsoleted;
    }
        
    public void setHitLevel(int hitLevel) {
        if (hitLevel <= this.hitLevel) return;
        this.hitLevel = hitLevel;
        switch (hitLevel) {
            case 2:
                this.warningList.put(WarnType.STCA, "STCA Danger");
                break;

            case 1:
                this.warningList.put(WarnType.STCA, "STCA Warning");
                break;
        }
        this.setWarnLevel(warnLevel);
    }
    // NhuongND add
    public void setHitLevelMTCS(int hitLevel) {
        if (hitLevel <= this.hitLevel) return;
        this.hitLevel = hitLevel;
        switch (hitLevel) {
            case 2:
                this.warningList.put(WarnType.MTCA, "MTCA Danger");
                break;

            case 1:
                this.warningList.put(WarnType.MTCA, "MTCA Warning");
                break;
        }
        this.setWarnLevel(warnLevel);
    }
    
    public boolean isIsCancel() {
        return isCancel;
    }

    /**
     * @param isCancel the isCancel to set
     */
    public void setIsCancel(boolean isCancel) {
        this.isCancel = isCancel;
    }

    /**
     * @return the dummy
     */
    public boolean isDummy() {
        return dummy;
    }

    /**
     * @param dummy the dummy to set
     */
    public void setDummy(boolean dummy) {
        this.dummy = dummy;
    }
    
    /**
     * @return the hdgNote
     */
    public String getHdgNote() {
        return hdgNote;
    }

    /**
     * @param hdgNote the hdgNote to set
     */
    public void setHdgNote(String hdgNote) {
        this.hdgNote = hdgNote;
    }

    /**
     * @return the info
     */
    public Aircrafts getInfo() {
        return info;
    }

    /**
     * @param info the info to set
     */
    public void setInfo(Aircrafts info) {
        this.info = info;
    }

    /**
     * @return the fpl
     */
    public FlightPlan getFpl() {
        return fpl;
    }

    /**
     * @param fpl the fpl to set
     */
    public void setFpl(FlightPlan fpl) {
        this.fpl = fpl;
    }
    
    /**
     * @return the dof
     */
    public String getDof() {
        return dof;
    }

    /**
     * @param dof the dof to set
     */
    public void setDof(String dof) {
        this.dof = dof;
    }
    
    public String getReg() {
        return this.info == null ? "" : info.getRegistrationNo();
    }
    
    public String getModel() {
        return this.info == null ? "" : info.getModel();
    }

    public String getAircraftType() {
        return this.info == null ? "" : info.getAircraftType();
    }

    public String getOperator() {
        return this.info == null ? "" : info.getOperator();
    }
    
    public String getDep() {
        return this.fpl == null ? "" : fpl.getDep();
    }

    public String getDest() {
        return this.fpl == null ? "" : fpl.getDest();
    }
    
    public String getRoute() {
        return this.fpl == null ? "" : fpl.getRoute();
    }
    //</editor-fold> 
}
