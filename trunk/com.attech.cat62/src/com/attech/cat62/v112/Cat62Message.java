/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.cat62.v112;

/**
 *
 * @author andh
 */
public class Cat62Message {
    
    // private byte [] binary;
    /*
     1 I062/010 Data Source Identifier 2
     2 - Spare -
     3 I062/015 Service Identification 1
     4 I062/070 Time Of Track Information 3
     5 I062/105 Calculated Track Position (WGS-84) 8
     6 I062/100 Calculated Track Position (Cartesian) 6
     7 I062/185 Calculated Track Velocity (Cartesian) 4
     FX - Field extension indicator -
    */
    private Integer sic, sac;
    private Integer serviceID;
    private Double timeOfTrack;
    private WGS84Coordinate posWGS84;
    private Coordinate posCartesian;
    private Coordinate veloCartesian;
    /*
     8 I062/210 Calculated Acceleration (Cartesian) 2
     9 I062/060 Track Mode 3/A Code 2
     10 I062/245 Target Identification 7
     11 I062/380 Aircraft Derived Data 1+
     12 I062/040 Track Number 2
     13 I062/080 Track Status 1+
     14 I062/290 System Track Update Ages 1+
     FX - Field extension indicator -
    */
    private Coordinate accCartesian; 
    private Mode3ACode mode3A;
    private TargetID targetID; // #10
    private AircraftDerivedData aircraftDerivedData; // #11
    private Integer trackNo;
    private TrackStatus trackStatus;
    private SystemTrackUpdateAges systemTrackUpdateAge;
    
    /*
     15 I062/200 Mode of Movement 1
     16 I062/295 Track Data Ages 1+
     17 I062/136 Measured Flight Level 2
     18 I062/130 Calculated Track Geometric Altitude 2
     19 I062/135 Calculated Track Barometric Altitude 2
     20 I062/220 Calculated Rate Of Climb/Descent 2
     21 I062/390 Flight Plan Related Data 1+
     FX - Field extension indicator -
     */
    private ModeOfMovement modeOfMovement;
    private TrackDataAges trackDataAges;
    private Double measureFlightLevel;
    private Double calculatedGeometricAltitude;
    private CalculatedBarometricAltitude calculatedBarometricAltitude;
    private Double calculatedRateOfClimb;
    private FlightPlan flightPlan; // #21
    /*
     22 I062/270 Target Size & Orientation 1+
     23 I062/300 Vehicle Fleet Identification 1
     24 I062/110 Mode 5 Data reports & Extended Mode 1 Code 1+
     25 I062/120 Track Mode 2 Code 2
     26 I062/510 Composed Track Number 3+
     27 I062/500 Estimated Accuracies 1+
     28 I062/340 Measured Information 1+
     FX - Field extension indicator -
     */
    private TargetSizeOrientation targetSizeOrienttation;
    private Integer vehicleFleetID;
    private Mode5DataReport mode5DataReport;
    private Integer mode2Code;
    private ComposedTrackNumber composedTrackNumber; // #26
    private EstimatedAccuracies estimateAccuracy;
    private MeasureInfo measureInfo; // #28
    
    /*
     29 - Spare
     30 - Spare 
     31 - Spare -
     32 - Spare -
     33 - Spare -
     34 RE Reserved Expansion Field 1+
     35 SP Reserved For Special Purpose Indicator 1+
     FX - Field extension indicator -
     */

    /**
     * @return the sic
     */
    public Integer getSic() {
        return sic;
    }

    /**
     * @param sic the sic to set
     */
    public void setSic(Integer sic) {
        this.sic = sic;
    }

    /**
     * @return the sac
     */
    public Integer getSac() {
        return sac;
    }

    /**
     * @param sac the sac to set
     */
    public void setSac(Integer sac) {
        this.sac = sac;
    }

    /**
     * @return the serviceID
     */
    public Integer getServiceID() {
        return serviceID;
    }

    /**
     * @param serviceID the serviceID to set
     */
    public void setServiceID(Integer serviceID) {
        this.serviceID = serviceID;
    }

    /**
     * @return the timeOfTrack
     */
    public Double getTimeOfTrack() {
        return timeOfTrack;
    }

    /**
     * @param timeOfTrack the timeOfTrack to set
     */
    public void setTimeOfTrack(Double timeOfTrack) {
        this.timeOfTrack = timeOfTrack;
    }

    /**
     * @return the posWGS84
     */
    public WGS84Coordinate getPosWGS84() {
        return posWGS84;
    }

    /**
     * @param posWGS84 the posWGS84 to set
     */
    public void setPosWGS84(WGS84Coordinate posWGS84) {
        this.posWGS84 = posWGS84;
    }

    /**
     * @return the posCartesian
     */
    public Coordinate getPosCartesian() {
        return posCartesian;
    }

    /**
     * @param posCartesian the posCartesian to set
     */
    public void setPosCartesian(Coordinate posCartesian) {
        this.posCartesian = posCartesian;
    }

    /**
     * @return the veloCartesian
     */
    public Coordinate getVeloCartesian() {
        return veloCartesian;
    }

    /**
     * @param veloCartesian the veloCartesian to set
     */
    public void setVeloCartesian(Coordinate veloCartesian) {
        this.veloCartesian = veloCartesian;
    }

    /**
     * @return the accCartesian
     */
    public Coordinate getAccCartesian() {
        return accCartesian;
    }

    /**
     * @param accCartesian the accCartesian to set
     */
    public void setAccCartesian(Coordinate accCartesian) {
        this.accCartesian = accCartesian;
    }

    /**
     * @return the mode3A
     */
    public Mode3ACode getMode3A() {
        return mode3A;
    }

    /**
     * @param mode3A the mode3A to set
     */
    public void setMode3A(Mode3ACode mode3A) {
        this.mode3A = mode3A;
    }

    /**
     * @return the targetID
     */
    public TargetID getTargetID() {
        return targetID;
    }

    /**
     * @param targetID the targetID to set
     */
    public void setTargetID(TargetID targetID) {
        this.targetID = targetID;
    }

    /**
     * @return the aircraftDerivedData
     */
    public AircraftDerivedData getAircraftDerivedData() {
        return aircraftDerivedData;
    }

    /**
     * @param aircraftDerivedData the aircraftDerivedData to set
     */
    public void setAircraftDerivedData(AircraftDerivedData aircraftDerivedData) {
        this.aircraftDerivedData = aircraftDerivedData;
    }

    /**
     * @return the flightPlan
     */
    public FlightPlan getFlightPlan() {
        return flightPlan;
    }

    /**
     * @param flightPlan the flightPlan to set
     */
    public void setFlightPlan(FlightPlan flightPlan) {
        this.flightPlan = flightPlan;
    }

    /**
     * @return the trackNo
     */
    public Integer getTrackNo() {
        return trackNo;
    }

    /**
     * @param trackNo the trackNo to set
     */
    public void setTrackNo(Integer trackNo) {
        this.trackNo = trackNo;
    }

    /**
     * @return the trackStatus
     */
    public TrackStatus getTrackStatus() {
        return trackStatus;
    }

    /**
     * @param trackStatus the trackStatus to set
     */
    public void setTrackStatus(TrackStatus trackStatus) {
        this.trackStatus = trackStatus;
    }

    /**
     * @return the systemTrackUpdateAge
     */
    public SystemTrackUpdateAges getSystemTrackUpdateAge() {
        return systemTrackUpdateAge;
    }

    /**
     * @param systemTrackUpdateAge the systemTrackUpdateAge to set
     */
    public void setSystemTrackUpdateAge(SystemTrackUpdateAges systemTrackUpdateAge) {
        this.systemTrackUpdateAge = systemTrackUpdateAge;
    }

    /**
     * @return the modeOfMovement
     */
    public ModeOfMovement getModeOfMovement() {
        return modeOfMovement;
    }

    /**
     * @param modeOfMovement the modeOfMovement to set
     */
    public void setModeOfMovement(ModeOfMovement modeOfMovement) {
        this.modeOfMovement = modeOfMovement;
    }

    /**
     * @return the trackDataAges
     */
    public TrackDataAges getTrackDataAges() {
        return trackDataAges;
    }

    /**
     * @param trackDataAges the trackDataAges to set
     */
    public void setTrackDataAges(TrackDataAges trackDataAges) {
        this.trackDataAges = trackDataAges;
    }

    /**
     * @return the measureFlightLevel
     */
    public Double getMeasureFlightLevel() {
        return measureFlightLevel;
    }

    /**
     * @param measureFlightLevel the measureFlightLevel to set
     */
    public void setMeasureFlightLevel(Double measureFlightLevel) {
        this.measureFlightLevel = measureFlightLevel;
    }

    /**
     * @return the calculatedGeometricAltitude
     */
    public Double getCalculatedGeometricAltitude() {
        return calculatedGeometricAltitude;
    }

    /**
     * @param calculatedGeometricAltitude the calculatedGeometricAltitude to set
     */
    public void setCalculatedGeometricAltitude(Double calculatedGeometricAltitude) {
        this.calculatedGeometricAltitude = calculatedGeometricAltitude;
    }

    /**
     * @return the calculatedBarometricAltitude
     */
    public CalculatedBarometricAltitude getCalculatedBarometricAltitude() {
        return calculatedBarometricAltitude;
    }

    /**
     * @param calculatedBarometricAltitude the calculatedBarometricAltitude to set
     */
    public void setCalculatedBarometricAltitude(CalculatedBarometricAltitude calculatedBarometricAltitude) {
        this.calculatedBarometricAltitude = calculatedBarometricAltitude;
    }

    /**
     * @return the calculatedRateOfClimb
     */
    public Double getCalculatedRateOfClimb() {
        return calculatedRateOfClimb;
    }

    /**
     * @param calculatedRateOfClimb the calculatedRateOfClimb to set
     */
    public void setCalculatedRateOfClimb(Double calculatedRateOfClimb) {
        this.calculatedRateOfClimb = calculatedRateOfClimb;
    }

    /**
     * @return the targetSizeOrienttation
     */
    public TargetSizeOrientation getTargetSizeOrienttation() {
        return targetSizeOrienttation;
    }

    /**
     * @param targetSizeOrienttation the targetSizeOrienttation to set
     */
    public void setTargetSizeOrienttation(TargetSizeOrientation targetSizeOrienttation) {
        this.targetSizeOrienttation = targetSizeOrienttation;
    }

    /**
     * @return the vehicleFleetID <br/>
     * 0 Unknown <br/>
     * 1 ATC equipment maintenance <br/>
     * 2 Airport maintenance <br/>
     * 3 Fire <br/>
     * 4 Bird scarer <br/>
     * 5 Snow plough <br/>
     * 6 Runway sweeper <br/>
     * 7 Emergency <br/>
     * 8 Police <br/>
     * 9 Bus <br/>
     * 10 Tug (push/tow) <br/>
     * 11 Grass cutter <br/>
     * 12 Fuel <br/>
     * 13 Baggage <br/>
     * 14 Catering <br/>
     * 15 Aircraft maintenance <br/>
     * 16 Flyco (follow me)
     */
    public int getVehicleFleetID() {
        return vehicleFleetID;
    }

    /**
     * @param vehicleFleetID <br/>
     * 0 Unknown <br/>
     * 1 ATC equipment maintenance <br/>
     * 2 Airport maintenance <br/>
     * 3 Fire <br/>
     * 4 Bird scarer <br/>
     * 5 Snow plough <br/>
     * 6 Runway sweeper <br/>
     * 7 Emergency <br/>
     * 8 Police <br/>
     * 9 Bus <br/>
     * 10 Tug (push/tow) <br/>
     * 11 Grass cutter <br/>
     * 12 Fuel <br/>
     * 13 Baggage <br/>
     * 14 Catering <br/>
     * 15 Aircraft maintenance <br/>
     * 16 Flyco (follow me)
     */
    public void setVehicleFleetID(int vehicleFleetID) {
        this.vehicleFleetID = vehicleFleetID;
    }

    /**
     * @return the mode5DataReport
     */
    public Mode5DataReport getMode5DataReport() {
        return mode5DataReport;
    }

    /**
     * @param mode5DataReport the mode5DataReport to set
     */
    public void setMode5DataReport(Mode5DataReport mode5DataReport) {
        this.mode5DataReport = mode5DataReport;
    }

    /**
     * @return the mode2Code
     */
    public int getMode2Code() {
        return mode2Code;
    }

    /**
     * @param mode2Code the mode2Code to set
     */
    public void setMode2Code(int mode2Code) {
        this.mode2Code = mode2Code;
    }

    /**
     * @return the composedTrackNumber
     */
    public ComposedTrackNumber getComposedTrackNumber() {
        return composedTrackNumber;
    }

    /**
     * @param composedTrackNumber the composedTrackNumber to set
     */
    public void setComposedTrackNumber(ComposedTrackNumber composedTrackNumber) {
        this.composedTrackNumber = composedTrackNumber;
    }

    /**
     * @return the estimateAccuracy
     */
    public EstimatedAccuracies getEstimateAccuracy() {
        return estimateAccuracy;
    }

    /**
     * @param estimateAccuracy the estimateAccuracy to set
     */
    public void setEstimateAccuracy(EstimatedAccuracies estimateAccuracy) {
        this.estimateAccuracy = estimateAccuracy;
    }

    /**
     * @return the measureInfo
     */
    public MeasureInfo getMeasureInfo() {
        return measureInfo;
    }

    /**
     * @param measureInfo the measureInfo to set
     */
    public void setMeasureInfo(MeasureInfo measureInfo) {
        this.measureInfo = measureInfo;
    }


}
