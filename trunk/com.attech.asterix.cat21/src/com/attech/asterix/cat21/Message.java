/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asterix.cat21;

import com.attech.asterix.cat21.entities.*;

/**
 *
 * @author andh
 */
public class Message {
    private DataSourceIdentification sourceIden;
    private TargetReportDescriptor targetDescriptor;
    private TimeOfDay timeOfDay;
    private Position position;
    private TargetAddress targetAddress;
    private Altitude altitude;
    private FigureOfMerit figure;
    
    private LinkTechnology technology;
    private RollAngle rollAngle;
    private FlightLevel flightLevel;
    private AirSpeed airSpeed;
    private TrueAirSpeed trueAirSpeed;
    private MagneticHeading magneticHeading;
    private BarometricVerticalRate barometricVerticalRate;
    
    private GeometricVerticalRate geometricVerticalRate;
    private GroundVector groundVector;
    private RateOfTurn rateOfTurn;
    private TargetIdentification targetIdentification;
    private VelocityAccuracy velocityAccuracy;
    private TimeOfDayAccurary timeOfDayAccurary;
    private TargetStatus targetStatus;
    
    private EmitterCategory emitterCategory;
    private MetInformation metInformation;
    private IntermediateStateSelectedAltitude intermediateStateSelectedAltitude;
    private FinalStateSelectedAltitude finalStateSelectedAltitude;
    private TrajectoryIntent trajectoryIntent;
    
    private byte[] bytes;
    private long time;
    
    public Message(byte[] bytes) {
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
     * @return the timeOfDay
     */
    public TimeOfDay getTimeOfDay() {
        return timeOfDay;
    }

    /**
     * @param timeOfDay the timeOfDay to set
     */
    public void setTimeOfDay(TimeOfDay timeOfDay) {
        this.timeOfDay = timeOfDay;
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
     * @return the targetAddress
     */
    public TargetAddress getTargetAddress() {
        return targetAddress;
    }

    /**
     * @param targetAddress the targetAddress to set
     */
    public void setTargetAddress(TargetAddress targetAddress) {
        this.targetAddress = targetAddress;
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
     * @return the rollAngle
     */
    public RollAngle getRollAngle() {
        return rollAngle;
    }

    /**
     * @param rollAngle the rollAngle to set
     */
    public void setRollAngle(RollAngle rollAngle) {
        this.rollAngle = rollAngle;
    }

    /**
     * @return the flightLevel
     */
    public FlightLevel getFlightLevel() {
        return flightLevel;
    }

    /**
     * @param flightLevel the flightLevel to set
     */
    public void setFlightLevel(FlightLevel flightLevel) {
        this.flightLevel = flightLevel;
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
     * @return the trueAirSpeed
     */
    public TrueAirSpeed getTrueAirSpeed() {
        return trueAirSpeed;
    }

    /**
     * @param trueAirSpeed the trueAirSpeed to set
     */
    public void setTrueAirSpeed(TrueAirSpeed trueAirSpeed) {
        this.trueAirSpeed = trueAirSpeed;
    }

    /**
     * @return the magneticHeading
     */
    public MagneticHeading getMagneticHeading() {
        return magneticHeading;
    }

    /**
     * @param magneticHeading the magneticHeading to set
     */
    public void setMagneticHeading(MagneticHeading magneticHeading) {
        this.magneticHeading = magneticHeading;
    }

    /**
     * @return the barometricVerticalRate
     */
    public BarometricVerticalRate getBarometricVerticalRate() {
        return barometricVerticalRate;
    }

    /**
     * @param barometricVerticalRate the barometricVerticalRate to set
     */
    public void setBarometricVerticalRate(BarometricVerticalRate barometricVerticalRate) {
        this.barometricVerticalRate = barometricVerticalRate;
    }

    /**
     * @return the geometricVerticalRate
     */
    public GeometricVerticalRate getGeometricVerticalRate() {
        return geometricVerticalRate;
    }

    /**
     * @param geometricVerticalRate the geometricVerticalRate to set
     */
    public void setGeometricVerticalRate(GeometricVerticalRate geometricVerticalRate) {
        this.geometricVerticalRate = geometricVerticalRate;
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
     * @return the targetIdentification
     */
    public TargetIdentification getTargetIdentification() {
        return targetIdentification;
    }

    /**
     * @param targetIdentification the targetIdentification to set
     */
    public void setTargetIdentification(TargetIdentification targetIdentification) {
        this.targetIdentification = targetIdentification;
    }

    /**
     * @return the velocityAccuracy
     */
    public VelocityAccuracy getVelocityAccuracy() {
        return velocityAccuracy;
    }

    /**
     * @param velocityAccuracy the velocityAccuracy to set
     */
    public void setVelocityAccuracy(VelocityAccuracy velocityAccuracy) {
        this.velocityAccuracy = velocityAccuracy;
    }

    /**
     * @return the timeOfDayAccurary
     */
    public TimeOfDayAccurary getTimeOfDayAccurary() {
        return timeOfDayAccurary;
    }

    /**
     * @param timeOfDayAccurary the timeOfDayAccurary to set
     */
    public void setTimeOfDayAccurary(TimeOfDayAccurary timeOfDayAccurary) {
        this.timeOfDayAccurary = timeOfDayAccurary;
    }

    /**
     * @return the targetStatus
     */
    public TargetStatus getTargetStatus() {
        return targetStatus;
    }

    /**
     * @param targetStatus the targetStatus to set
     */
    public void setTargetStatus(TargetStatus targetStatus) {
        this.targetStatus = targetStatus;
    }

    /**
     * @return the emitterCategory
     */
    public EmitterCategory getEmitterCategory() {
        return emitterCategory;
    }

    /**
     * @param emitterCategory the emitterCategory to set
     */
    public void setEmitterCategory(EmitterCategory emitterCategory) {
        this.emitterCategory = emitterCategory;
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
        builder.append(sourceIden).append(targetDescriptor).append(timeOfDay).append(position).append(targetAddress).append(altitude).append(figure);
        builder.append(technology).append(rollAngle).append(flightLevel).append(airSpeed).append(trueAirSpeed).append(magneticHeading).append(barometricVerticalRate);
        builder.append(geometricVerticalRate).append(groundVector).append(rateOfTurn).append(targetIdentification).append(velocityAccuracy).append(timeOfDayAccurary).append(targetStatus);
        builder.append(emitterCategory).append(metInformation).append(intermediateStateSelectedAltitude).append(finalStateSelectedAltitude).append(trajectoryIntent);
        return builder.toString().replace("null", "-");
    }
    
    public byte[] getByte() {
        return this.bytes;
    }
    
    public long getTime() {
        return this.time;
    }
}
