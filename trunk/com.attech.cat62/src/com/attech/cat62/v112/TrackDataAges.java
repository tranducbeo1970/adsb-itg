/*
 * To change this license header, choose LiceAnse Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.cat62.v112;

/**
 *
 * @author andh
 */
public class TrackDataAges {
    private boolean measureFlightLevelAgeAvailable;
    private boolean mode1AgeAvailable;
    private boolean mode2AgeAvailable;
    private boolean mode3AAgeAvailable;
    private boolean mode4AgeAvailable;
    private boolean mode5AgeAvailable;
    private boolean magneticHeadingAvailable;
    
    // #2
    private boolean indicatedAirSpeedMacNBAgeAvailable;
    private boolean trueAirSpeedAgeAvailable;
    private boolean selectedAltitudeAgeAvailable;
    private boolean finalAltitudeAgeAvailable;
    private boolean trajectoryIntentDataAgeAvailable;
    private boolean acasCapabilityFlightStatusAgeAvaileble;
    private boolean statusReportByADSBAgeAvailable;
    
    // #3
    private boolean acasResolutionAdvisoryReportAgeAvailable;
    private boolean barometricVerticalRageAgeAvailable;
    private boolean geometricVerticalRateAgeAvailable;
    private boolean rollAngleAgeAvailable;
    private boolean trackAngleRateAgeAvailable;
    private boolean trackAngleAgeAvailable;
    private boolean groundSpeedAgeAvailable;
    
    // #4
    private boolean velocityUncertainAgeAvailable;
    private boolean meteorologicalDataAgeAvailable;
    private boolean emmiterCateAgeAvaiblable;
    private boolean positionDataAgeAvailable;
    private boolean geometricAltitudeDataAgeAvailable;
    private boolean positionUncertainDataAgeAvailable;
    private boolean modeSMBDataAgeAvailable;
    
    // #5
    private boolean indicatedAirSpeedDataAgeAvailable;
    private boolean machNumberDataAgeAvailable;
    private boolean barometricPressureSettingDataAgeAvailable;
    
    // #6
    private double measureFlightLevelAge;
    private double mode1Age;
    private double mode2Age;
    private double mode3AAge;
    private double mode4Age;
    private double mode5Age;
    private double magneticHeading;
    
    // #7
    private double indicatedAirSpeedMacNBAge;
    private double trueAirSpeedAge;
    private double selectedAltitudeAge;
    private double finalAltitudeAge;
    private double trajectoryIntentDataAge;
    private double acasCapabilityFlightStatusAge;
    private double statusReportByADSBAge;
    
    // #8
    private double acasResolutionAdvisoryReportAge;
    private double barometricVerticalRageAge;
    private double geometricVerticalRateAge;
    private double rollAngleAge;
    private double trackAngleRateAge;
    private double trackAngleAge;
    private double groundSpeedAge;
    
    // #9
    private double velocityUncertainAge;
    private double meteorologicalDataAge;
    private double emmiterCateAge;
    private double positionDataAge;
    private double geometricAltitudeDataAge;
    private double positionUncertainDataAge;
    private double modeSMBDataAge;
    
    // #10
    private double indicatedAirSpeedDataAge;
    private double machNumberDataAge;
    private double barometricPressureSettingDataAge;

    public static int extract(byte[] bytes, int index, TrackDataAges code) {

        if (!Byter.validateIndex(index, bytes.length, 1)) return -1;
        byte cbyte = bytes[index++];
        code.setMeasureFlightLevelAgeAvailable((cbyte & 0x80) > 0);
        code.setMode1AgeAvailable((cbyte & 0x40) > 0);
        code.setMode2AgeAvailable((cbyte & 0x20) > 0);
        code.setMode3AAgeAvailable((cbyte & 0x10) > 0);
        code.setMode4AgeAvailable((cbyte & 0x08) > 0);
        code.setMode5AgeAvailable((cbyte & 0x04) > 0);
        code.setMagneticHeadingAvailable((cbyte & 0x02) > 0);
        if ((cbyte & 0x01) == 0) return 1;
        
        // #2
        cbyte = bytes[index++];
        code.setIndicatedAirSpeedMacNBAgeAvailable((cbyte & 0x80) > 0);
        code.setTrueAirSpeedAgeAvailable((cbyte & 0x40) > 0);
        code.setSelectedAltitudeAgeAvailable((cbyte & 0x20) > 0);
        code.setFinalAltitudeAgeAvailable((cbyte & 0x10) > 0);
        code.setTrajectoryIntentDataAgeAvailable((cbyte & 0x08) > 0);
        code.setAcasCapabilityFlightStatusAgeAvaileble((cbyte & 0x04) > 0);
        code.setStatusReportByADSBAgeAvailable((cbyte & 0x02) > 0);
        if ((cbyte & 0x01) == 0) return 2;

        // #3
        cbyte = bytes[index++];
        code.setAcasResolutionAdvisoryReportAgeAvailable((cbyte & 0x80) > 0);
        code.setBarometricVerticalRageAgeAvailable((cbyte & 0x40) > 0);
        code.setGeometricVerticalRateAgeAvailable((cbyte & 0x20) > 0);
        code.setRollAngleAgeAvailable((cbyte & 0x10) > 0);
        code.setTrackAngleRateAgeAvailable((cbyte & 0x08) > 0);
        code.setTrackAngleAgeAvailable((cbyte & 0x04) > 0);
        code.setGroundSpeedAgeAvailable((cbyte & 0x02) > 0);
        if ((cbyte & 0x01) == 0) return 3;
    
        // #4
        cbyte = bytes[index++];
        code.setVelocityUncertainAgeAvailable((cbyte & 0x80) > 0);
        code.setMeteorologicalDataAgeAvailable((cbyte & 0x40) > 0);
        code.setEmmiterCateAgeAvaiblable((cbyte & 0x20) > 0);
        code.setPositionDataAgeAvailable((cbyte & 0x10) > 0);
        code.setGeometricAltitudeDataAgeAvailable((cbyte & 0x08) > 0);
        code.setPositionUncertainDataAgeAvailable((cbyte & 0x04) > 0);
        code.setModeSMBDataAgeAvailable((cbyte & 0x02) > 0);
        if ((cbyte & 0x01) == 0) return 4;
        
        // # 5
        cbyte = bytes[index++];
        code.setIndicatedAirSpeedDataAgeAvailable((cbyte & 0x80) > 0);
        code.setMachNumberDataAgeAvailable((cbyte & 0x40) > 0);
        code.setBarometricPressureSettingDataAgeAvailable((cbyte & 0x20) > 0);
        if ((cbyte & 0x01) == 0) return 5;
        
        int count = 5;
        
        // Sub Field #1
        if (code.isMeasureFlightLevelAgeAvailable()) { 
            code.setMeasureFlightLevelAge((bytes[index++] & 0xFF) * 0.25);
            count += 1;
        }
        
        // Sub Field #2
        if (code.isMode1AgeAvailable()) {
            code.setMode1Age((bytes[index++] & 0xFF) * 0.25);
            count += 1;
        }
        
        // Sub Field #3
        if (code.isMode2AgeAvailable()) {
            code.setMode2Age((bytes[index++] & 0xFF) * 0.25);
            count += 1;
        }
        
        // Sub Field #4
        if (code.isMode3AAgeAvailable()) {
            code.setMode3AAge((bytes[index++] & 0xFF) * 0.25);
            count += 1;
        }
        
        // Sub Field #5
        if (code.isMode4AgeAvailable()) {
            code.setMode4Age((bytes[index++] & 0xFF) * 0.25);
            count += 1;
        }
        
        // Sub Field #6
        if (code.isMode5AgeAvailable()) {
            code.setMode5Age((bytes[index++] & 0xFF) * 0.25);
            count += 1;
        }
        
        // Sub Field #7
        if (code.isMagneticHeadingAvailable()) {
            code.setMagneticHeading((bytes[index++] & 0xFF) * 0.25);
            count += 1;
        }

        // Sub Field #8
        if (code.isIndicatedAirSpeedMacNBAgeAvailable()) {
            code.setIndicatedAirSpeedMacNBAge((bytes[index++] & 0xFF) * 0.25);
            count += 1;
        }
        
        // Sub Field #9
        if (code.isTrueAirSpeedAgeAvailable()) {
            code.setTrueAirSpeedAge((bytes[index++] & 0xFF) * 0.25);
            count += 1;
        }
        
        // Sub Field #10
        if (code.isSelectedAltitudeAgeAvailable()) {
            code.setSelectedAltitudeAge((bytes[index++] & 0xFF) * 0.25);
            count += 1;
        }
        
        // Sub Field #11
        if (code.isFinalAltitudeAgeAvailable()) {
            code.setFinalAltitudeAge((bytes[index++] & 0xFF) * 0.25);
            count += 1;
        }
        
        // Sub Field #12
        if (code.isTrajectoryIntentDataAgeAvailable()) {
            code.setTrajectoryIntentDataAge((bytes[index++] & 0xFF) * 0.25);
            count += 1;
        }
        
        // Sub Field #13
        if (code.isAcasCapabilityFlightStatusAgeAvaileble()) {
            code.setAcasCapabilityFlightStatusAge((bytes[index++] & 0xFF) * 0.25);
            count += 1;
        }
        
        // Sub Field #14
        if (code.isStatusReportByADSBAgeAvailable()) {
            code.setStatusReportByADSBAge((bytes[index++] & 0xFF) * 0.25);
            count += 1;
        }

        // Sub Field #15
        if (code.isAcasResolutionAdvisoryReportAgeAvailable()) {
            code.setAcasResolutionAdvisoryReportAge((bytes[index++] & 0xFF) * 0.25);
            count += 1;
        }
        
        // Sub Field #16
        if (code.isBarometricVerticalRageAgeAvailable()) {
            code.setBarometricVerticalRageAge((bytes[index++] & 0xFF) * 0.25);
            count += 1;
        }
        
        // Sub Field #17
        if (code.isGeometricVerticalRateAgeAvailable()) {
            code.setGeometricVerticalRateAge((bytes[index++] & 0xFF) * 0.25);
            count += 1;
        }
        
        // Sub Field #18
        if (code.isRollAngleAgeAvailable()) {
            code.setRollAngleAge((bytes[index++] & 0xFF) * 0.25);
            count += 1;
        }
        
        // Sub Field #19
        if (code.isTrackAngleRateAgeAvailable()) {
            code.setTrackAngleRateAge((bytes[index++] & 0xFF) * 0.25);
            count += 1;
        }
        
        // Sub Field #20
        if (code.isTrackAngleAgeAvailable()) {
            code.setTrackAngleAge((bytes[index++] & 0xFF) * 0.25);
            count += 1;
        }
        
        // Sub Field #21
        if (code.isGroundSpeedAgeAvailable()) {
            code.setGroundSpeedAge((bytes[index++] & 0xFF) * 0.25);
            count += 1;
        }

        // Sub Field #22
        if (code.isVelocityUncertainAgeAvailable()) {
            code.setVelocityUncertainAge((bytes[index++] & 0xFF) * 0.25);
            count += 1;
        }
        
        // Sub Field #23
        if (code.isMeteorologicalDataAgeAvailable()) {
            code.setMeteorologicalDataAge((bytes[index++] & 0xFF) * 0.25);
            count += 1;
        }
        
        // Sub Field #24
        if (code.isEmmiterCateAgeAvaiblable()) {
            code.setEmmiterCateAge((bytes[index++] & 0xFF) * 0.25);
            count += 1;
        }
        
        // Sub Field #25
        if (code.isPositionDataAgeAvailable()) {
            code.setPositionDataAge((bytes[index++] & 0xFF) * 0.25);
            count += 1;
        }
        
        // Sub Field #26
        if (code.isGeometricAltitudeDataAgeAvailable()) {
            code.setGeometricAltitudeDataAge((bytes[index++] & 0xFF) * 0.25);
            count += 1;
        }
        
        // Sub Field #27
        if (code.isPositionUncertainDataAgeAvailable()) {
            code.setPositionUncertainDataAge((bytes[index++] & 0xFF) * 0.25);
            count += 1;
        }
        
        // Sub Field #28
        if (code.isModeSMBDataAgeAvailable()) {
            code.setModeSMBDataAge((bytes[index++] & 0xFF) * 0.25);
            count += 1;
        }

        // Sub Field #29
        if (code.isIndicatedAirSpeedDataAgeAvailable()) {
            code.setIndicatedAirSpeedDataAge((bytes[index++] & 0xFF) * 0.25);
            count += 1;
        }
        
        // Sub Field #30
        if (code.isMachNumberDataAgeAvailable()) {
            code.setMachNumberDataAge((bytes[index++] & 0xFF) * 0.25);
            count += 1;
        }
        
        // Sub Field #31
        if (code.isBarometricPressureSettingDataAgeAvailable()) {
            code.setBarometricPressureSettingDataAge((bytes[index++] & 0xFF) * 0.25);
            count += 1;
        }
        
        return count;
    }
    
    /**
     * @return the measureFlightLevelAgeAvailable
     */
    public boolean isMeasureFlightLevelAgeAvailable() {
        return measureFlightLevelAgeAvailable;
    }

    /**
     * @param measureFlightLevelAgeAvailable the measureFlightLevelAgeAvailable to set
     */
    public void setMeasureFlightLevelAgeAvailable(boolean measureFlightLevelAgeAvailable) {
        this.measureFlightLevelAgeAvailable = measureFlightLevelAgeAvailable;
    }

    /**
     * @return the mode1AgeAvailable
     */
    public boolean isMode1AgeAvailable() {
        return mode1AgeAvailable;
    }

    /**
     * @param mode1AgeAvailable the mode1AgeAvailable to set
     */
    public void setMode1AgeAvailable(boolean mode1AgeAvailable) {
        this.mode1AgeAvailable = mode1AgeAvailable;
    }

    /**
     * @return the mode2AgeAvailable
     */
    public boolean isMode2AgeAvailable() {
        return mode2AgeAvailable;
    }

    /**
     * @param mode2AgeAvailable the mode2AgeAvailable to set
     */
    public void setMode2AgeAvailable(boolean mode2AgeAvailable) {
        this.mode2AgeAvailable = mode2AgeAvailable;
    }

    /**
     * @return the mode3AAgeAvailable
     */
    public boolean isMode3AAgeAvailable() {
        return mode3AAgeAvailable;
    }

    /**
     * @param mode3AAgeAvailable the mode3AAgeAvailable to set
     */
    public void setMode3AAgeAvailable(boolean mode3AAgeAvailable) {
        this.mode3AAgeAvailable = mode3AAgeAvailable;
    }

    /**
     * @return the mode4AgeAvailable
     */
    public boolean isMode4AgeAvailable() {
        return mode4AgeAvailable;
    }

    /**
     * @param mode4AgeAvailable the mode4AgeAvailable to set
     */
    public void setMode4AgeAvailable(boolean mode4AgeAvailable) {
        this.mode4AgeAvailable = mode4AgeAvailable;
    }

    /**
     * @return the mode5AgeAvailable
     */
    public boolean isMode5AgeAvailable() {
        return mode5AgeAvailable;
    }

    /**
     * @param mode5AgeAvailable the mode5AgeAvailable to set
     */
    public void setMode5AgeAvailable(boolean mode5AgeAvailable) {
        this.mode5AgeAvailable = mode5AgeAvailable;
    }

    /**
     * @return the magneticHeadingAvailable
     */
    public boolean isMagneticHeadingAvailable() {
        return magneticHeadingAvailable;
    }

    /**
     * @param magneticHeadingAvailable the magneticHeadingAvailable to set
     */
    public void setMagneticHeadingAvailable(boolean magneticHeadingAvailable) {
        this.magneticHeadingAvailable = magneticHeadingAvailable;
    }

    /**
     * @return the indicatedAirSpeedMacNBAgeAvailable
     */
    public boolean isIndicatedAirSpeedMacNBAgeAvailable() {
        return indicatedAirSpeedMacNBAgeAvailable;
    }

    /**
     * @param indicatedAirSpeedMacNBAgeAvailable the indicatedAirSpeedMacNBAgeAvailable to set
     */
    public void setIndicatedAirSpeedMacNBAgeAvailable(boolean indicatedAirSpeedMacNBAgeAvailable) {
        this.indicatedAirSpeedMacNBAgeAvailable = indicatedAirSpeedMacNBAgeAvailable;
    }

    /**
     * @return the trueAirSpeedAgeAvailable
     */
    public boolean isTrueAirSpeedAgeAvailable() {
        return trueAirSpeedAgeAvailable;
    }

    /**
     * @param trueAirSpeedAgeAvailable the trueAirSpeedAgeAvailable to set
     */
    public void setTrueAirSpeedAgeAvailable(boolean trueAirSpeedAgeAvailable) {
        this.trueAirSpeedAgeAvailable = trueAirSpeedAgeAvailable;
    }

    /**
     * @return the selectedAltitudeAgeAvailable
     */
    public boolean isSelectedAltitudeAgeAvailable() {
        return selectedAltitudeAgeAvailable;
    }

    /**
     * @param selectedAltitudeAgeAvailable the selectedAltitudeAgeAvailable to set
     */
    public void setSelectedAltitudeAgeAvailable(boolean selectedAltitudeAgeAvailable) {
        this.selectedAltitudeAgeAvailable = selectedAltitudeAgeAvailable;
    }

    /**
     * @return the finalAltitudeAgeAvailable
     */
    public boolean isFinalAltitudeAgeAvailable() {
        return finalAltitudeAgeAvailable;
    }

    /**
     * @param finalAltitudeAgeAvailable the finalAltitudeAgeAvailable to set
     */
    public void setFinalAltitudeAgeAvailable(boolean finalAltitudeAgeAvailable) {
        this.finalAltitudeAgeAvailable = finalAltitudeAgeAvailable;
    }

    /**
     * @return the trajectoryIntentDataAgeAvailable
     */
    public boolean isTrajectoryIntentDataAgeAvailable() {
        return trajectoryIntentDataAgeAvailable;
    }

    /**
     * @param trajectoryIntentDataAgeAvailable the trajectoryIntentDataAgeAvailable to set
     */
    public void setTrajectoryIntentDataAgeAvailable(boolean trajectoryIntentDataAgeAvailable) {
        this.trajectoryIntentDataAgeAvailable = trajectoryIntentDataAgeAvailable;
    }

    /**
     * @return the acasCapabilityFlightStatusAgeAvaileble
     */
    public boolean isAcasCapabilityFlightStatusAgeAvaileble() {
        return acasCapabilityFlightStatusAgeAvaileble;
    }

    /**
     * @param acasCapabilityFlightStatusAgeAvaileble the acasCapabilityFlightStatusAgeAvaileble to set
     */
    public void setAcasCapabilityFlightStatusAgeAvaileble(boolean acasCapabilityFlightStatusAgeAvaileble) {
        this.acasCapabilityFlightStatusAgeAvaileble = acasCapabilityFlightStatusAgeAvaileble;
    }

    /**
     * @return the statusReportByADSBAgeAvailable
     */
    public boolean isStatusReportByADSBAgeAvailable() {
        return statusReportByADSBAgeAvailable;
    }

    /**
     * @param statusReportByADSBAgeAvailable the statusReportByADSBAgeAvailable to set
     */
    public void setStatusReportByADSBAgeAvailable(boolean statusReportByADSBAgeAvailable) {
        this.statusReportByADSBAgeAvailable = statusReportByADSBAgeAvailable;
    }

    /**
     * @return the acasResolutionAdvisoryReportAgeAvailable
     */
    public boolean isAcasResolutionAdvisoryReportAgeAvailable() {
        return acasResolutionAdvisoryReportAgeAvailable;
    }

    /**
     * @param acasResolutionAdvisoryReportAgeAvailable the acasResolutionAdvisoryReportAgeAvailable to set
     */
    public void setAcasResolutionAdvisoryReportAgeAvailable(boolean acasResolutionAdvisoryReportAgeAvailable) {
        this.acasResolutionAdvisoryReportAgeAvailable = acasResolutionAdvisoryReportAgeAvailable;
    }

    /**
     * @return the barometricVerticalRageAgeAvailable
     */
    public boolean isBarometricVerticalRageAgeAvailable() {
        return barometricVerticalRageAgeAvailable;
    }

    /**
     * @param barometricVerticalRageAgeAvailable the barometricVerticalRageAgeAvailable to set
     */
    public void setBarometricVerticalRageAgeAvailable(boolean barometricVerticalRageAgeAvailable) {
        this.barometricVerticalRageAgeAvailable = barometricVerticalRageAgeAvailable;
    }

    /**
     * @return the geometricVerticalRateAgeAvailable
     */
    public boolean isGeometricVerticalRateAgeAvailable() {
        return geometricVerticalRateAgeAvailable;
    }

    /**
     * @param geometricVerticalRateAgeAvailable the geometricVerticalRateAgeAvailable to set
     */
    public void setGeometricVerticalRateAgeAvailable(boolean geometricVerticalRateAgeAvailable) {
        this.geometricVerticalRateAgeAvailable = geometricVerticalRateAgeAvailable;
    }

    /**
     * @return the rollAngleAgeAvailable
     */
    public boolean isRollAngleAgeAvailable() {
        return rollAngleAgeAvailable;
    }

    /**
     * @param rollAngleAgeAvailable the rollAngleAgeAvailable to set
     */
    public void setRollAngleAgeAvailable(boolean rollAngleAgeAvailable) {
        this.rollAngleAgeAvailable = rollAngleAgeAvailable;
    }

    /**
     * @return the trackAngleRateAgeAvailable
     */
    public boolean isTrackAngleRateAgeAvailable() {
        return trackAngleRateAgeAvailable;
    }

    /**
     * @param trackAngleRateAgeAvailable the trackAngleRateAgeAvailable to set
     */
    public void setTrackAngleRateAgeAvailable(boolean trackAngleRateAgeAvailable) {
        this.trackAngleRateAgeAvailable = trackAngleRateAgeAvailable;
    }

    /**
     * @return the trackAngleAgeAvailable
     */
    public boolean isTrackAngleAgeAvailable() {
        return trackAngleAgeAvailable;
    }

    /**
     * @param trackAngleAgeAvailable the trackAngleAgeAvailable to set
     */
    public void setTrackAngleAgeAvailable(boolean trackAngleAgeAvailable) {
        this.trackAngleAgeAvailable = trackAngleAgeAvailable;
    }

    /**
     * @return the groundSpeedAgeAvailable
     */
    public boolean isGroundSpeedAgeAvailable() {
        return groundSpeedAgeAvailable;
    }

    /**
     * @param groundSpeedAgeAvailable the groundSpeedAgeAvailable to set
     */
    public void setGroundSpeedAgeAvailable(boolean groundSpeedAgeAvailable) {
        this.groundSpeedAgeAvailable = groundSpeedAgeAvailable;
    }

    /**
     * @return the velocityUncertainAgeAvailable
     */
    public boolean isVelocityUncertainAgeAvailable() {
        return velocityUncertainAgeAvailable;
    }

    /**
     * @param velocityUncertainAgeAvailable the velocityUncertainAgeAvailable to set
     */
    public void setVelocityUncertainAgeAvailable(boolean velocityUncertainAgeAvailable) {
        this.velocityUncertainAgeAvailable = velocityUncertainAgeAvailable;
    }

    /**
     * @return the meteorologicalDataAgeAvailable
     */
    public boolean isMeteorologicalDataAgeAvailable() {
        return meteorologicalDataAgeAvailable;
    }

    /**
     * @param meteorologicalDataAgeAvailable the meteorologicalDataAgeAvailable to set
     */
    public void setMeteorologicalDataAgeAvailable(boolean meteorologicalDataAgeAvailable) {
        this.meteorologicalDataAgeAvailable = meteorologicalDataAgeAvailable;
    }

    /**
     * @return the emmiterCateAgeAvaiblable
     */
    public boolean isEmmiterCateAgeAvaiblable() {
        return emmiterCateAgeAvaiblable;
    }

    /**
     * @param emmiterCateAgeAvaiblable the emmiterCateAgeAvaiblable to set
     */
    public void setEmmiterCateAgeAvaiblable(boolean emmiterCateAgeAvaiblable) {
        this.emmiterCateAgeAvaiblable = emmiterCateAgeAvaiblable;
    }

    /**
     * @return the positionDataAgeAvailable
     */
    public boolean isPositionDataAgeAvailable() {
        return positionDataAgeAvailable;
    }

    /**
     * @param positionDataAgeAvailable the positionDataAgeAvailable to set
     */
    public void setPositionDataAgeAvailable(boolean positionDataAgeAvailable) {
        this.positionDataAgeAvailable = positionDataAgeAvailable;
    }

    /**
     * @return the geometricAltitudeDataAgeAvailable
     */
    public boolean isGeometricAltitudeDataAgeAvailable() {
        return geometricAltitudeDataAgeAvailable;
    }

    /**
     * @param geometricAltitudeDataAgeAvailable the geometricAltitudeDataAgeAvailable to set
     */
    public void setGeometricAltitudeDataAgeAvailable(boolean geometricAltitudeDataAgeAvailable) {
        this.geometricAltitudeDataAgeAvailable = geometricAltitudeDataAgeAvailable;
    }

    /**
     * @return the positionUncertainDataAgeAvailable
     */
    public boolean isPositionUncertainDataAgeAvailable() {
        return positionUncertainDataAgeAvailable;
    }

    /**
     * @param positionUncertainDataAgeAvailable the positionUncertainDataAgeAvailable to set
     */
    public void setPositionUncertainDataAgeAvailable(boolean positionUncertainDataAgeAvailable) {
        this.positionUncertainDataAgeAvailable = positionUncertainDataAgeAvailable;
    }

    /**
     * @return the modeSMBDataAgeAvailable
     */
    public boolean isModeSMBDataAgeAvailable() {
        return modeSMBDataAgeAvailable;
    }

    /**
     * @param modeSMBDataAgeAvailable the modeSMBDataAgeAvailable to set
     */
    public void setModeSMBDataAgeAvailable(boolean modeSMBDataAgeAvailable) {
        this.modeSMBDataAgeAvailable = modeSMBDataAgeAvailable;
    }

    /**
     * @return the indicatedAirSpeedDataAgeAvailable
     */
    public boolean isIndicatedAirSpeedDataAgeAvailable() {
        return indicatedAirSpeedDataAgeAvailable;
    }

    /**
     * @param indicatedAirSpeedDataAgeAvailable the indicatedAirSpeedDataAgeAvailable to set
     */
    public void setIndicatedAirSpeedDataAgeAvailable(boolean indicatedAirSpeedDataAgeAvailable) {
        this.indicatedAirSpeedDataAgeAvailable = indicatedAirSpeedDataAgeAvailable;
    }

    /**
     * @return the machNumberDataAgeAvailable
     */
    public boolean isMachNumberDataAgeAvailable() {
        return machNumberDataAgeAvailable;
    }

    /**
     * @param machNumberDataAgeAvailable the machNumberDataAgeAvailable to set
     */
    public void setMachNumberDataAgeAvailable(boolean machNumberDataAgeAvailable) {
        this.machNumberDataAgeAvailable = machNumberDataAgeAvailable;
    }

    /**
     * @return the barometricPressureSettingDataAgeAvailable
     */
    public boolean isBarometricPressureSettingDataAgeAvailable() {
        return barometricPressureSettingDataAgeAvailable;
    }

    /**
     * @param barometricPressureSettingDataAgeAvailable the barometricPressureSettingDataAgeAvailable to set
     */
    public void setBarometricPressureSettingDataAgeAvailable(boolean barometricPressureSettingDataAgeAvailable) {
        this.barometricPressureSettingDataAgeAvailable = barometricPressureSettingDataAgeAvailable;
    }

    /**
     * @return the measureFlightLevelAge
     */
    public double getMeasureFlightLevelAge() {
        return measureFlightLevelAge;
    }

    /**
     * @param measureFlightLevelAge the measureFlightLevelAge to set
     */
    public void setMeasureFlightLevelAge(double measureFlightLevelAge) {
        this.measureFlightLevelAge = measureFlightLevelAge;
    }

    /**
     * @return the mode1Age
     */
    public double getMode1Age() {
        return mode1Age;
    }

    /**
     * @param mode1Age the mode1Age to set
     */
    public void setMode1Age(double mode1Age) {
        this.mode1Age = mode1Age;
    }

    /**
     * @return the mode2Age
     */
    public double getMode2Age() {
        return mode2Age;
    }

    /**
     * @param mode2Age the mode2Age to set
     */
    public void setMode2Age(double mode2Age) {
        this.mode2Age = mode2Age;
    }

    /**
     * @return the mode3AAge
     */
    public double getMode3AAge() {
        return mode3AAge;
    }

    /**
     * @param mode3AAge the mode3AAge to set
     */
    public void setMode3AAge(double mode3AAge) {
        this.mode3AAge = mode3AAge;
    }

    /**
     * @return the mode4Age
     */
    public double getMode4Age() {
        return mode4Age;
    }

    /**
     * @param mode4Age the mode4Age to set
     */
    public void setMode4Age(double mode4Age) {
        this.mode4Age = mode4Age;
    }

    /**
     * @return the mode5Age
     */
    public double getMode5Age() {
        return mode5Age;
    }

    /**
     * @param mode5Age the mode5Age to set
     */
    public void setMode5Age(double mode5Age) {
        this.mode5Age = mode5Age;
    }

    /**
     * @return the magneticHeading
     */
    public double getMagneticHeading() {
        return magneticHeading;
    }

    /**
     * @param magneticHeading the magneticHeading to set
     */
    public void setMagneticHeading(double magneticHeading) {
        this.magneticHeading = magneticHeading;
    }

    /**
     * @return the indicatedAirSpeedMacNBAge
     */
    public double getIndicatedAirSpeedMacNBAge() {
        return indicatedAirSpeedMacNBAge;
    }

    /**
     * @param indicatedAirSpeedMacNBAge the indicatedAirSpeedMacNBAge to set
     */
    public void setIndicatedAirSpeedMacNBAge(double indicatedAirSpeedMacNBAge) {
        this.indicatedAirSpeedMacNBAge = indicatedAirSpeedMacNBAge;
    }

    /**
     * @return the trueAirSpeedAge
     */
    public double getTrueAirSpeedAge() {
        return trueAirSpeedAge;
    }

    /**
     * @param trueAirSpeedAge the trueAirSpeedAge to set
     */
    public void setTrueAirSpeedAge(double trueAirSpeedAge) {
        this.trueAirSpeedAge = trueAirSpeedAge;
    }

    /**
     * @return the selectedAltitudeAge
     */
    public double getSelectedAltitudeAge() {
        return selectedAltitudeAge;
    }

    /**
     * @param selectedAltitudeAge the selectedAltitudeAge to set
     */
    public void setSelectedAltitudeAge(double selectedAltitudeAge) {
        this.selectedAltitudeAge = selectedAltitudeAge;
    }

    /**
     * @return the finalAltitudeAge
     */
    public double getFinalAltitudeAge() {
        return finalAltitudeAge;
    }

    /**
     * @param finalAltitudeAge the finalAltitudeAge to set
     */
    public void setFinalAltitudeAge(double finalAltitudeAge) {
        this.finalAltitudeAge = finalAltitudeAge;
    }

    /**
     * @return the trajectoryIntentDataAge
     */
    public double getTrajectoryIntentDataAge() {
        return trajectoryIntentDataAge;
    }

    /**
     * @param trajectoryIntentDataAge the trajectoryIntentDataAge to set
     */
    public void setTrajectoryIntentDataAge(double trajectoryIntentDataAge) {
        this.trajectoryIntentDataAge = trajectoryIntentDataAge;
    }

    /**
     * @return the acasCapabilityFlightStatusAge
     */
    public double getAcasCapabilityFlightStatusAge() {
        return acasCapabilityFlightStatusAge;
    }

    /**
     * @param acasCapabilityFlightStatusAge the acasCapabilityFlightStatusAge to set
     */
    public void setAcasCapabilityFlightStatusAge(double acasCapabilityFlightStatusAge) {
        this.acasCapabilityFlightStatusAge = acasCapabilityFlightStatusAge;
    }

    /**
     * @return the statusReportByADSBAge
     */
    public double getStatusReportByADSBAge() {
        return statusReportByADSBAge;
    }

    /**
     * @param statusReportByADSBAge the statusReportByADSBAge to set
     */
    public void setStatusReportByADSBAge(double statusReportByADSBAge) {
        this.statusReportByADSBAge = statusReportByADSBAge;
    }

    /**
     * @return the acasResolutionAdvisoryReportAge
     */
    public double getAcasResolutionAdvisoryReportAge() {
        return acasResolutionAdvisoryReportAge;
    }

    /**
     * @param acasResolutionAdvisoryReportAge the acasResolutionAdvisoryReportAge to set
     */
    public void setAcasResolutionAdvisoryReportAge(double acasResolutionAdvisoryReportAge) {
        this.acasResolutionAdvisoryReportAge = acasResolutionAdvisoryReportAge;
    }

    /**
     * @return the barometricVerticalRageAge
     */
    public double getBarometricVerticalRageAge() {
        return barometricVerticalRageAge;
    }

    /**
     * @param barometricVerticalRageAge the barometricVerticalRageAge to set
     */
    public void setBarometricVerticalRageAge(double barometricVerticalRageAge) {
        this.barometricVerticalRageAge = barometricVerticalRageAge;
    }

    /**
     * @return the geometricVerticalRateAge
     */
    public double getGeometricVerticalRateAge() {
        return geometricVerticalRateAge;
    }

    /**
     * @param geometricVerticalRateAge the geometricVerticalRateAge to set
     */
    public void setGeometricVerticalRateAge(double geometricVerticalRateAge) {
        this.geometricVerticalRateAge = geometricVerticalRateAge;
    }

    /**
     * @return the rollAngleAge
     */
    public double getRollAngleAge() {
        return rollAngleAge;
    }

    /**
     * @param rollAngleAge the rollAngleAge to set
     */
    public void setRollAngleAge(double rollAngleAge) {
        this.rollAngleAge = rollAngleAge;
    }

    /**
     * @return the trackAngleRateAge
     */
    public double getTrackAngleRateAge() {
        return trackAngleRateAge;
    }

    /**
     * @param trackAngleRateAge the trackAngleRateAge to set
     */
    public void setTrackAngleRateAge(double trackAngleRateAge) {
        this.trackAngleRateAge = trackAngleRateAge;
    }

    /**
     * @return the trackAngleAge
     */
    public double getTrackAngleAge() {
        return trackAngleAge;
    }

    /**
     * @param trackAngleAge the trackAngleAge to set
     */
    public void setTrackAngleAge(double trackAngleAge) {
        this.trackAngleAge = trackAngleAge;
    }

    /**
     * @return the groundSpeedAge
     */
    public double getGroundSpeedAge() {
        return groundSpeedAge;
    }

    /**
     * @param groundSpeedAge the groundSpeedAge to set
     */
    public void setGroundSpeedAge(double groundSpeedAge) {
        this.groundSpeedAge = groundSpeedAge;
    }

    /**
     * @return the velocityUncertainAge
     */
    public double getVelocityUncertainAge() {
        return velocityUncertainAge;
    }

    /**
     * @param velocityUncertainAge the velocityUncertainAge to set
     */
    public void setVelocityUncertainAge(double velocityUncertainAge) {
        this.velocityUncertainAge = velocityUncertainAge;
    }

    /**
     * @return the meteorologicalDataAge
     */
    public double getMeteorologicalDataAge() {
        return meteorologicalDataAge;
    }

    /**
     * @param meteorologicalDataAge the meteorologicalDataAge to set
     */
    public void setMeteorologicalDataAge(double meteorologicalDataAge) {
        this.meteorologicalDataAge = meteorologicalDataAge;
    }

    /**
     * @return the emmiterCateAge
     */
    public double getEmmiterCateAge() {
        return emmiterCateAge;
    }

    /**
     * @param emmiterCateAge the emmiterCateAge to set
     */
    public void setEmmiterCateAge(double emmiterCateAge) {
        this.emmiterCateAge = emmiterCateAge;
    }

    /**
     * @return the positionDataAge
     */
    public double getPositionDataAge() {
        return positionDataAge;
    }

    /**
     * @param positionDataAge the positionDataAge to set
     */
    public void setPositionDataAge(double positionDataAge) {
        this.positionDataAge = positionDataAge;
    }

    /**
     * @return the geometricAltitudeDataAge
     */
    public double getGeometricAltitudeDataAge() {
        return geometricAltitudeDataAge;
    }

    /**
     * @param geometricAltitudeDataAge the geometricAltitudeDataAge to set
     */
    public void setGeometricAltitudeDataAge(double geometricAltitudeDataAge) {
        this.geometricAltitudeDataAge = geometricAltitudeDataAge;
    }

    /**
     * @return the positionUncertainDataAge
     */
    public double getPositionUncertainDataAge() {
        return positionUncertainDataAge;
    }

    /**
     * @param positionUncertainDataAge the positionUncertainDataAge to set
     */
    public void setPositionUncertainDataAge(double positionUncertainDataAge) {
        this.positionUncertainDataAge = positionUncertainDataAge;
    }

    /**
     * @return the modeSMBDataAge
     */
    public double getModeSMBDataAge() {
        return modeSMBDataAge;
    }

    /**
     * @param modeSMBDataAge the modeSMBDataAge to set
     */
    public void setModeSMBDataAge(double modeSMBDataAge) {
        this.modeSMBDataAge = modeSMBDataAge;
    }

    /**
     * @return the indicatedAirSpeedDataAge
     */
    public double getIndicatedAirSpeedDataAge() {
        return indicatedAirSpeedDataAge;
    }

    /**
     * @param indicatedAirSpeedDataAge the indicatedAirSpeedDataAge to set
     */
    public void setIndicatedAirSpeedDataAge(double indicatedAirSpeedDataAge) {
        this.indicatedAirSpeedDataAge = indicatedAirSpeedDataAge;
    }

    /**
     * @return the machNumberDataAge
     */
    public double getMachNumberDataAge() {
        return machNumberDataAge;
    }

    /**
     * @param machNumberDataAge the machNumberDataAge to set
     */
    public void setMachNumberDataAge(double machNumberDataAge) {
        this.machNumberDataAge = machNumberDataAge;
    }

    /**
     * @return the barometricPressureSettingDataAge
     */
    public double getBarometricPressureSettingDataAge() {
        return barometricPressureSettingDataAge;
    }

    /**
     * @param barometricPressureSettingDataAge the barometricPressureSettingDataAge to set
     */
    public void setBarometricPressureSettingDataAge(double barometricPressureSettingDataAge) {
        this.barometricPressureSettingDataAge = barometricPressureSettingDataAge;
    }
    
    
    
}
