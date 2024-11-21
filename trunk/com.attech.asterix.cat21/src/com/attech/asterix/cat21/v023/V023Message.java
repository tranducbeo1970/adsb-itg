/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asterix.cat21.v023;

import com.attech.asterix.cat21.entities.*;

/**
 *
 * @author andh
 */
public class V023Message {
    
    private DataSourceIdentification sourceIden;
    private TargetReportDescriptor targetDescriptor;
    private Double timeOfDay;
    private Position position;
    private Integer targetAddress;
    private Altitude altitude;
    private FigureOfMerit figure;
    
    private LinkTechnology technology;
    private Float rollAngle;
    private Float flightLevel;
    private AirSpeed airSpeed;
    private Integer trueAirSpeed;
    private Double magneticHeading;
    private Double barometricVerticalRate;
    
    private Double geometricVerticalRate;
    private GroundVector groundVector;
    private RateOfTurn rateOfTurn;
    private String targetIdentification;
    private Integer velocityAccuracy;
    private Double timeOfDayAccurary;
    private Integer targetStatus;
    
    private Integer emitterCategory;
    private MetInformation metInformation;
    private IntermediateStateSelectedAltitude intermediateStateSelectedAltitude;
    private FinalStateSelectedAltitude finalStateSelectedAltitude;
    private TrajectoryIntent trajectoryIntent;
    
    private byte[] bytes;
    private long time;
    
    public V023Message(byte[] bytes) {
        this.bytes = bytes;
        this.time = System.currentTimeMillis();
    }

    /**
     * @return the sourceIden
     */
    public DataSourceIdentification getSourceIden() {
        return sourceIden;
    }

    /**
     * @param sourceIden the sourceIden to set
     */
    public void setSourceIden(DataSourceIdentification sourceIden) {
        this.sourceIden = sourceIden;
    }

    /**
     * @return the targetDescriptor
     */
    public TargetReportDescriptor getTargetDescriptor() {
        return targetDescriptor;
    }

    /**
     * @param targetDescriptor the targetDescriptor to set
     */
    public void setTargetDescriptor(TargetReportDescriptor targetDescriptor) {
        this.targetDescriptor = targetDescriptor;
    }

       /**
     * @return the position
     */
    public Position getPosition() {
        return position;
    }

    /**
     * @param position the position to set
     */
    public void setPosition(Position position) {
        this.position = position;
    }

   
    /**
     * @return the altitude
     */
    public Altitude getAltitude() {
        return altitude;
    }

    /**
     * @param altitude the altitude to set
     */
    public void setAltitude(Altitude altitude) {
        this.altitude = altitude;
    }

    /**
     * @return the figure
     */
    public FigureOfMerit getFigure() {
        return figure;
    }

    /**
     * @param figure the figure to set
     */
    public void setFigure(FigureOfMerit figure) {
        this.figure = figure;
    }

    /**
     * @return the technology
     */
    public LinkTechnology getTechnology() {
        return technology;
    }

    /**
     * @param technology the technology to set
     */
    public void setTechnology(LinkTechnology technology) {
        this.technology = technology;
    }


    /**
     * @return the airSpeed
     */
    public AirSpeed getAirSpeed() {
        return airSpeed;
    }

    /**
     * @param airSpeed the airSpeed to set
     */
    public void setAirSpeed(AirSpeed airSpeed) {
        this.airSpeed = airSpeed;
    }

  
    /**
     * @return the groundVector
     */
    public GroundVector getGroundVector() {
        return groundVector;
    }

    /**
     * @param groundVector the groundVector to set
     */
    public void setGroundVector(GroundVector groundVector) {
        this.groundVector = groundVector;
    }

    /**
     * @return the rateOfTurn
     */
    public RateOfTurn getRateOfTurn() {
        return rateOfTurn;
    }

    /**
     * @param rateOfTurn the rateOfTurn to set
     */
    public void setRateOfTurn(RateOfTurn rateOfTurn) {
        this.rateOfTurn = rateOfTurn;
    }

     /**
     * @return the metInformation
     */
    public MetInformation getMetInformation() {
        return metInformation;
    }

    /**
     * @param metInformation the metInformation to set
     */
    public void setMetInformation(MetInformation metInformation) {
        this.metInformation = metInformation;
    }

    /**
     * @return the intermediateStateSelectedAltitude
     */
    public IntermediateStateSelectedAltitude getIntermediateStateSelectedAltitude() {
        return intermediateStateSelectedAltitude;
    }

    /**
     * @param intermediateStateSelectedAltitude the intermediateStateSelectedAltitude to set
     */
    public void setIntermediateStateSelectedAltitude(IntermediateStateSelectedAltitude intermediateStateSelectedAltitude) {
        this.intermediateStateSelectedAltitude = intermediateStateSelectedAltitude;
    }

    /**
     * @return the finalStateSelectedAltitude
     */
    public FinalStateSelectedAltitude getFinalStateSelectedAltitude() {
        return finalStateSelectedAltitude;
    }

    /**
     * @param finalStateSelectedAltitude the finalStateSelectedAltitude to set
     */
    public void setFinalStateSelectedAltitude(FinalStateSelectedAltitude finalStateSelectedAltitude) {
        this.finalStateSelectedAltitude = finalStateSelectedAltitude;
    }

    /**
     * @return the trajectoryIntent
     */
    public TrajectoryIntent getTrajectoryIntent() {
        return trajectoryIntent;
    }

    /**
     * @param trajectoryIntent the trajectoryIntent to set
     */
    public void setTrajectoryIntent(TrajectoryIntent trajectoryIntent) {
        this.trajectoryIntent = trajectoryIntent;
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("Msg>");
        builder.append(getSourceIden()).append(getTargetDescriptor()).append(getTimeOfDay()).append(getPosition()).append(getTargetAddress()).append(getAltitude()).append(getFigure());
        builder.append(getTechnology()).append(getRollAngle()).append(getFlightLevel()).append(getAirSpeed()).append(getTrueAirSpeed()).append(getMagneticHeading()).append(getBarometricVerticalRate());
        builder.append(getGeometricVerticalRate()).append(getGroundVector()).append(getRateOfTurn()).append(getTargetIdentification()).append(getVelocityAccuracy()).append(getTimeOfDayAccurary()).append(getTargetStatus());
        builder.append(getEmitterCategory()).append(getMetInformation()).append(getIntermediateStateSelectedAltitude()).append(getFinalStateSelectedAltitude()).append(getTrajectoryIntent());
        return builder.toString().replace("null", "-");
    }
    
    public byte[] getByte() {
        return this.getBytes();
    }
    
    public long getTime() {
        return this.time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(long time) {
        this.time = time;
    }

    /**
     * @return the timeOfDay
     */
    public Double getTimeOfDay() {
        return timeOfDay;
    }

    /**
     * @param timeOfDay the timeOfDay to set
     */
    public void setTimeOfDay(Double timeOfDay) {
        this.timeOfDay = timeOfDay;
    }

    /**
     * @return the targetAddress
     */
    public Integer getTargetAddress() {
        return targetAddress;
    }

    /**
     * @param targetAddress the targetAddress to set
     */
    public void setTargetAddress(Integer targetAddress) {
        this.targetAddress = targetAddress;
    }

    /**
     * @return the rollAngle
     */
    public Float getRollAngle() {
        return rollAngle;
    }

    /**
     * @param rollAngle the rollAngle to set
     */
    public void setRollAngle(Float rollAngle) {
        this.rollAngle = rollAngle;
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
     * @return the trueAirSpeed
     */
    public Integer getTrueAirSpeed() {
        return trueAirSpeed;
    }

    /**
     * @param trueAirSpeed the trueAirSpeed to set
     */
    public void setTrueAirSpeed(Integer trueAirSpeed) {
        this.trueAirSpeed = trueAirSpeed;
    }

    /**
     * @return the magneticHeading
     */
    public Double getMagneticHeading() {
        return magneticHeading;
    }

    /**
     * @param magneticHeading the magneticHeading to set
     */
    public void setMagneticHeading(Double magneticHeading) {
        this.magneticHeading = magneticHeading;
    }

    /**
     * @return the barometricVerticalRate
     */
    public Double getBarometricVerticalRate() {
        return barometricVerticalRate;
    }

    /**
     * @param barometricVerticalRate the barometricVerticalRate to set
     */
    public void setBarometricVerticalRate(Double barometricVerticalRate) {
        this.barometricVerticalRate = barometricVerticalRate;
    }

    /**
     * @return the geometricVerticalRate
     */
    public Double getGeometricVerticalRate() {
        return geometricVerticalRate;
    }

    /**
     * @param geometricVerticalRate the geometricVerticalRate to set
     */
    public void setGeometricVerticalRate(Double geometricVerticalRate) {
        this.geometricVerticalRate = geometricVerticalRate;
    }

    /**
     * @return the targetIdentification
     */
    public String getTargetIdentification() {
        return targetIdentification;
    }

    /**
     * @param targetIdentification the targetIdentification to set
     */
    public void setTargetIdentification(String targetIdentification) {
        this.targetIdentification = targetIdentification;
    }

    /**
     * @return the velocityAccuracy
     */
    public Integer getVelocityAccuracy() {
        return velocityAccuracy;
    }

    /**
     * @param velocityAccuracy the velocityAccuracy to set
     */
    public void setVelocityAccuracy(Integer velocityAccuracy) {
        this.velocityAccuracy = velocityAccuracy;
    }

    /**
     * @return the timeOfDayAccurary
     */
    public Double getTimeOfDayAccurary() {
        return timeOfDayAccurary;
    }

    /**
     * @param timeOfDayAccurary the timeOfDayAccurary to set
     */
    public void setTimeOfDayAccurary(Double timeOfDayAccurary) {
        this.timeOfDayAccurary = timeOfDayAccurary;
    }

    /**
     * @return the targetStatus
     */
    public Integer getTargetStatus() {
        return targetStatus;
    }

    /**
     * @param targetStatus the targetStatus to set
     */
    public void setTargetStatus(Integer targetStatus) {
        this.targetStatus = targetStatus;
    }

    /**
     * @return the emitterCategory
     */
    public Integer getEmitterCategory() {
        return emitterCategory;
    }

    /**
     * @param emitterCategory the emitterCategory to set
     */
    public void setEmitterCategory(Integer emitterCategory) {
        this.emitterCategory = emitterCategory;
    }

    /**
     * @return the bytes
     */
    public byte[] getBytes() {
        return bytes;
    }

    /**
     * @param bytes the bytes to set
     */
    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }
}
