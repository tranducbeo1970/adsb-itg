/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.administrator.def;

import java.io.Serializable;

/**
 *
 * @author root
 */
public class Cat21Option implements Serializable{

    /*
     * Header Byte 01
     * 01 I021/010 Data Source Identification [2]
     * 02 I021/040 Target Report Descriptor [1+]
     * 03 I021/161 Track Number [2]
     * 04 I021/015 Service Identification [1]
     * 05 I021/071 Time of Applicability for Position [3]
     * 06 I021/130 Position in WGS-84 co-ordinates [6]
     * 07 I021/131 Position in WGS-84 co-ordinates, high res.  8
     */
    private boolean sic, sac;  // DataSourceIdentification
    private boolean targetDescriptor;
    private boolean trackNumber;
    private boolean serviceIdentification;
    private boolean timeOfAplicabilityPosition;
    private boolean position;
    private boolean hightResolutionPosition;

    /*
     * Header Byte 02
     * 08 I021/072 Time of Applicability for Velocity [3]
     * 09 I021/150 Air Speed [2]
     * 10 I021/151 True Air Speed [2]
     * 11 I021/080 Target Address [3]
     * 12 I021/073 Time of Message Reception of Position [3]
     * 13 I021/074 Time of Message Reception of Position-High Precision [4]
     * 14 I021/075 Time of Message Reception of Velocity [3]
     */
    private boolean timeOfAplicabilityVelocity;
    private boolean airSpeed;
    private boolean trueAirSpeed;
    private boolean targetAddress;
    private boolean timeOfMessageReceptionOfPosition;
    private boolean timeOfMessageReceptionOfPositionHightPrecisions;
    private boolean timeOfMessageReceptionOfVelocity;

    /*
     * Header Byte 03
     * 15 I021/076 Time of Message Reception of Velocity-High Precision [4]
     * 16 I021/140 Geometric Height [2]
     * 17 I021/090 Quality Indicators [1+]
     * 18 I021/210 MOPS Version [1]
     * 19 I021/070 Mode 3/A Code [2]
     * 20 I021/230 Roll Angle [2]
     * 21 I021/145 Flight Level [2]
     */
    private boolean timeOfMessageReceptionOfVelocityHightPrecision;
    private boolean geometricHeight;
    private boolean qualityIndicator;
    private boolean mopsVersion;
    private boolean mode3a;
    private boolean rollAgle;
    private boolean flightLevel;

    /*
     * Header Byte 04
     * 22 I021/152 Magnetic Heading [2]
     * 23 I021/200 Target Status [1]
     * 24 I021/155 Barometric Vertical Rate [2]
     * 25 I021/157 Geometric Vertical Rate [2]
     * 26 I021/160 Airborne Ground Vector [4]
     * 27 I021/165 Track Angle Rate [2]
     * 28 I021/077 Time of Report Transmission [3]
     */
    private boolean magneticHeading;
    private boolean targetStatus;
    private boolean barometricVerticalRate;
    private boolean geometricVerticalRate;
    private boolean airborneGroundVector;
    private boolean trackAngleRate;
    private boolean timeOfReportTranmission;

    /*
     * Header Byte 05
     * 29 I021/170 Target Identification [6]
     * 30 I021/020 Emitter Category [1]
     * 31 I021/220 Met Information [1+]
     * 32 I021/146 Selected Altitude [2]
     * 33 I021/148 Final State Selected Altitude [2]
     * 34 I021/110 Trajectory Intent [1+]
     * 35 I021/016 Service Management [1]
     */
    private boolean callSign;
    private boolean emitterCategory;
    private boolean metInformation;
    private boolean selectedAltitude;
    private boolean finalStateSelectedAltitude;
    private boolean tracjectoryIntent;
    private boolean serviceManagement;

    /*
     * Header Byte 06
     * 36 I021/008 Aircraft Operational Status [1]
     * 37 I021/271 Surface Capabilities and Characteristics [1+]
     * 38 I021/132 Message Amplitude [1]
     * 39 I021/250 Mode S MB Data [1+N*8]
     * 40 I021/260 ACAS Resolution Advisory Report [7]
     * 41 I021/400 Receiver ID [1]
     * 42 I021/295 Data Ages [1+]
     */
    private boolean aircraftOperationStatus;
    private boolean surfaceCapabilitiesAndCharacterics;
    private boolean messageAmplitude;
    private boolean modeSMBData;
    private boolean aCASResolutionAdvisoryReport;
    private boolean receiverId;
    private boolean dataAges;

    /*
     * Header Byte 06
     * 43 - Not Used -
     * 44 - Not Used -
     * 45 - Not Used -
     * 46 - Not Used -
     * 47 - Not Used -
     * 48 RE Reserved Expansion Field [1+]
     * 49 SP Special Purpose Field [1+]
     */
    public Cat21Option() {
    }

    public Cat21Option(boolean sic, boolean sac, boolean targetDescriptor, boolean trackNumber, boolean serviceIdentification, boolean timeOfAplicabilityPosition, boolean position, boolean hightResolutionPosition, boolean timeOfAplicabilityVelocity, boolean airSpeed, boolean trueAirSpeed, boolean targetAddress, boolean timeOfMessageReceptionOfPosition, boolean timeOfMessageReceptionOfPositionHightPrecisions, boolean timeOfMessageReceptionOfVelocity, boolean timeOfMessageReceptionOfVelocityHightPrecision, boolean geometricHeight, boolean qualityIndicator, boolean mopsVersion, boolean mode3a, boolean rollAgle, boolean flightLevel, boolean magneticHeading, boolean targetStatus, boolean barometricVerticalRate, boolean geometricVerticalRate, boolean airborneGroundVector, boolean trackAngleRate, boolean timeOfReportTranmission, boolean callSign, boolean emitterCategory, boolean metInformation, boolean selectedAltitude, boolean finalStateSelectedAltitude, boolean tracjectoryIntent, boolean serviceManagement, boolean aircraftOperationStatus, boolean surfaceCapabilitiesAndCharacterics, boolean messageAmplitude, boolean modeSMBData, boolean aCASResolutionAdvisoryReport, boolean receiverId, boolean dataAges) {
        this.sic = sic;
        this.sac = sac;
        this.targetDescriptor = targetDescriptor;
        this.trackNumber = trackNumber;
        this.serviceIdentification = serviceIdentification;
        this.timeOfAplicabilityPosition = timeOfAplicabilityPosition;
        this.position = position;
        this.hightResolutionPosition = hightResolutionPosition;
        this.timeOfAplicabilityVelocity = timeOfAplicabilityVelocity;
        this.airSpeed = airSpeed;
        this.trueAirSpeed = trueAirSpeed;
        this.targetAddress = targetAddress;
        this.timeOfMessageReceptionOfPosition = timeOfMessageReceptionOfPosition;
        this.timeOfMessageReceptionOfPositionHightPrecisions = timeOfMessageReceptionOfPositionHightPrecisions;
        this.timeOfMessageReceptionOfVelocity = timeOfMessageReceptionOfVelocity;
        this.timeOfMessageReceptionOfVelocityHightPrecision = timeOfMessageReceptionOfVelocityHightPrecision;
        this.geometricHeight = geometricHeight;
        this.qualityIndicator = qualityIndicator;
        this.mopsVersion = mopsVersion;
        this.mode3a = mode3a;
        this.rollAgle = rollAgle;
        this.flightLevel = flightLevel;
        this.magneticHeading = magneticHeading;
        this.targetStatus = targetStatus;
        this.barometricVerticalRate = barometricVerticalRate;
        this.geometricVerticalRate = geometricVerticalRate;
        this.airborneGroundVector = airborneGroundVector;
        this.trackAngleRate = trackAngleRate;
        this.timeOfReportTranmission = timeOfReportTranmission;
        this.callSign = callSign;
        this.emitterCategory = emitterCategory;
        this.metInformation = metInformation;
        this.selectedAltitude = selectedAltitude;
        this.finalStateSelectedAltitude = finalStateSelectedAltitude;
        this.tracjectoryIntent = tracjectoryIntent;
        this.serviceManagement = serviceManagement;
        this.aircraftOperationStatus = aircraftOperationStatus;
        this.surfaceCapabilitiesAndCharacterics = surfaceCapabilitiesAndCharacterics;
        this.messageAmplitude = messageAmplitude;
        this.modeSMBData = modeSMBData;
        this.aCASResolutionAdvisoryReport = aCASResolutionAdvisoryReport;
        this.receiverId = receiverId;
        this.dataAges = dataAges;
    }

    public void setSic(boolean sic) {
        this.sic = sic;
    }

    public void setSac(boolean sac) {
        this.sac = sac;
    }

    public void setTargetDescriptor(boolean targetDescriptor) {
        this.targetDescriptor = targetDescriptor;
    }

    public void setTrackNumber(boolean trackNumber) {
        this.trackNumber = trackNumber;
    }

    public void setServiceIdentification(boolean serviceIdentification) {
        this.serviceIdentification = serviceIdentification;
    }

    public void setTimeOfAplicabilityPosition(boolean timeOfAplicabilityPosition) {
        this.timeOfAplicabilityPosition = timeOfAplicabilityPosition;
    }

    public void setPosition(boolean position) {
        this.position = position;
    }

    public void setHightResolutionPosition(boolean hightResolutionPosition) {
        this.hightResolutionPosition = hightResolutionPosition;
    }

    public void setTimeOfAplicabilityVelocity(boolean timeOfAplicabilityVelocity) {
        this.timeOfAplicabilityVelocity = timeOfAplicabilityVelocity;
    }

    public void setAirSpeed(boolean airSpeed) {
        this.airSpeed = airSpeed;
    }

    public void setTrueAirSpeed(boolean trueAirSpeed) {
        this.trueAirSpeed = trueAirSpeed;
    }

    public void setTargetAddress(boolean targetAddress) {
        this.targetAddress = targetAddress;
    }

    public void setTimeOfMessageReceptionOfPosition(boolean timeOfMessageReceptionOfPosition) {
        this.timeOfMessageReceptionOfPosition = timeOfMessageReceptionOfPosition;
    }

    public void setTimeOfMessageReceptionOfPositionHightPrecisions(boolean timeOfMessageReceptionOfPositionHightPrecisions) {
        this.timeOfMessageReceptionOfPositionHightPrecisions = timeOfMessageReceptionOfPositionHightPrecisions;
    }

    public void setTimeOfMessageReceptionOfVelocity(boolean timeOfMessageReceptionOfVelocity) {
        this.timeOfMessageReceptionOfVelocity = timeOfMessageReceptionOfVelocity;
    }

    public void setTimeOfMessageReceptionOfVelocityHightPrecision(boolean timeOfMessageReceptionOfVelocityHightPrecision) {
        this.timeOfMessageReceptionOfVelocityHightPrecision = timeOfMessageReceptionOfVelocityHightPrecision;
    }

    public void setGeometricHeight(boolean geometricHeight) {
        this.geometricHeight = geometricHeight;
    }

    public void setQualityIndicator(boolean qualityIndicator) {
        this.qualityIndicator = qualityIndicator;
    }

    public void setMopsVersion(boolean mopsVersion) {
        this.mopsVersion = mopsVersion;
    }

    public void setMode3a(boolean mode3a) {
        this.mode3a = mode3a;
    }

    public void setRollAgle(boolean rollAgle) {
        this.rollAgle = rollAgle;
    }

    public void setFlightLevel(boolean flightLevel) {
        this.flightLevel = flightLevel;
    }

    public void setMagneticHeading(boolean magneticHeading) {
        this.magneticHeading = magneticHeading;
    }

    public void setTargetStatus(boolean targetStatus) {
        this.targetStatus = targetStatus;
    }

    public void setBarometricVerticalRate(boolean barometricVerticalRate) {
        this.barometricVerticalRate = barometricVerticalRate;
    }

    public void setGeometricVerticalRate(boolean geometricVerticalRate) {
        this.geometricVerticalRate = geometricVerticalRate;
    }

    public void setAirborneGroundVector(boolean airborneGroundVector) {
        this.airborneGroundVector = airborneGroundVector;
    }

    public void setTrackAngleRate(boolean trackAngleRate) {
        this.trackAngleRate = trackAngleRate;
    }

    public void setTimeOfReportTranmission(boolean timeOfReportTranmission) {
        this.timeOfReportTranmission = timeOfReportTranmission;
    }

    public void setCallSign(boolean callSign) {
        this.callSign = callSign;
    }

    public void setEmitterCategory(boolean emitterCategory) {
        this.emitterCategory = emitterCategory;
    }

    public void setMetInformation(boolean metInformation) {
        this.metInformation = metInformation;
    }

    public void setSelectedAltitude(boolean selectedAltitude) {
        this.selectedAltitude = selectedAltitude;
    }

    public void setFinalStateSelectedAltitude(boolean finalStateSelectedAltitude) {
        this.finalStateSelectedAltitude = finalStateSelectedAltitude;
    }

    public void setTracjectoryIntent(boolean tracjectoryIntent) {
        this.tracjectoryIntent = tracjectoryIntent;
    }

    public void setServiceManagement(boolean serviceManagement) {
        this.serviceManagement = serviceManagement;
    }

    public void setAircraftOperationStatus(boolean aircraftOperationStatus) {
        this.aircraftOperationStatus = aircraftOperationStatus;
    }

    public void setSurfaceCapabilitiesAndCharacterics(boolean surfaceCapabilitiesAndCharacterics) {
        this.surfaceCapabilitiesAndCharacterics = surfaceCapabilitiesAndCharacterics;
    }

    public void setMessageAmplitude(boolean messageAmplitude) {
        this.messageAmplitude = messageAmplitude;
    }

    public void setModeSMBData(boolean modeSMBData) {
        this.modeSMBData = modeSMBData;
    }

    public void setaCASResolutionAdvisoryReport(boolean aCASResolutionAdvisoryReport) {
        this.aCASResolutionAdvisoryReport = aCASResolutionAdvisoryReport;
    }

    public void setReceiverId(boolean receiverId) {
        this.receiverId = receiverId;
    }

    public void setDataAges(boolean dataAges) {
        this.dataAges = dataAges;
    }

    public boolean isSic() {
        return sic;
    }

    public boolean isSac() {
        return sac;
    }

    public boolean isTargetDescriptor() {
        return targetDescriptor;
    }

    public boolean isTrackNumber() {
        return trackNumber;
    }

    public boolean isServiceIdentification() {
        return serviceIdentification;
    }

    public boolean isTimeOfAplicabilityPosition() {
        return timeOfAplicabilityPosition;
    }

    public boolean isPosition() {
        return position;
    }

    public boolean isHightResolutionPosition() {
        return hightResolutionPosition;
    }

    public boolean isTimeOfAplicabilityVelocity() {
        return timeOfAplicabilityVelocity;
    }

    public boolean isAirSpeed() {
        return airSpeed;
    }

    public boolean isTrueAirSpeed() {
        return trueAirSpeed;
    }

    public boolean isTargetAddress() {
        return targetAddress;
    }

    public boolean isTimeOfMessageReceptionOfPosition() {
        return timeOfMessageReceptionOfPosition;
    }

    public boolean isTimeOfMessageReceptionOfPositionHightPrecisions() {
        return timeOfMessageReceptionOfPositionHightPrecisions;
    }

    public boolean isTimeOfMessageReceptionOfVelocity() {
        return timeOfMessageReceptionOfVelocity;
    }

    public boolean isTimeOfMessageReceptionOfVelocityHightPrecision() {
        return timeOfMessageReceptionOfVelocityHightPrecision;
    }

    public boolean isGeometricHeight() {
        return geometricHeight;
    }

    public boolean isQualityIndicator() {
        return qualityIndicator;
    }

    public boolean isMopsVersion() {
        return mopsVersion;
    }

    public boolean isMode3a() {
        return mode3a;
    }

    public boolean isRollAgle() {
        return rollAgle;
    }

    public boolean isFlightLevel() {
        return flightLevel;
    }

    public boolean isMagneticHeading() {
        return magneticHeading;
    }

    public boolean isTargetStatus() {
        return targetStatus;
    }

    public boolean isBarometricVerticalRate() {
        return barometricVerticalRate;
    }

    public boolean isGeometricVerticalRate() {
        return geometricVerticalRate;
    }

    public boolean isAirborneGroundVector() {
        return airborneGroundVector;
    }

    public boolean isTrackAngleRate() {
        return trackAngleRate;
    }

    public boolean isTimeOfReportTranmission() {
        return timeOfReportTranmission;
    }

    public boolean isCallSign() {
        return callSign;
    }

    public boolean isEmitterCategory() {
        return emitterCategory;
    }

    public boolean isMetInformation() {
        return metInformation;
    }

    public boolean isSelectedAltitude() {
        return selectedAltitude;
    }

    public boolean isFinalStateSelectedAltitude() {
        return finalStateSelectedAltitude;
    }

    public boolean isTracjectoryIntent() {
        return tracjectoryIntent;
    }

    public boolean isServiceManagement() {
        return serviceManagement;
    }

    public boolean isAircraftOperationStatus() {
        return aircraftOperationStatus;
    }

    public boolean isSurfaceCapabilitiesAndCharacterics() {
        return surfaceCapabilitiesAndCharacterics;
    }

    public boolean isMessageAmplitude() {
        return messageAmplitude;
    }

    public boolean isModeSMBData() {
        return modeSMBData;
    }

    public boolean isaCASResolutionAdvisoryReport() {
        return aCASResolutionAdvisoryReport;
    }

    public boolean isReceiverId() {
        return receiverId;
    }

    public boolean isDataAges() {
        return dataAges;
    }

    @Override
    public String toString() {
        return "Cat21Option{" + "sic=" + sic + ", sac=" + sac + ", targetDescriptor=" + targetDescriptor + ", trackNumber=" + trackNumber + ", serviceIdentification=" + serviceIdentification + ", timeOfAplicabilityPosition=" + timeOfAplicabilityPosition + ", position=" + position + ", hightResolutionPosition=" + hightResolutionPosition + ", timeOfAplicabilityVelocity=" + timeOfAplicabilityVelocity + ", airSpeed=" + airSpeed + ", trueAirSpeed=" + trueAirSpeed + ", targetAddress=" + targetAddress + ", timeOfMessageReceptionOfPosition=" + timeOfMessageReceptionOfPosition + ", timeOfMessageReceptionOfPositionHightPrecisions=" + timeOfMessageReceptionOfPositionHightPrecisions + ", timeOfMessageReceptionOfVelocity=" + timeOfMessageReceptionOfVelocity + ", timeOfMessageReceptionOfVelocityHightPrecision=" + timeOfMessageReceptionOfVelocityHightPrecision + ", geometricHeight=" + geometricHeight + ", qualityIndicator=" + qualityIndicator + ", mopsVersion=" + mopsVersion + ", mode3a=" + mode3a + ", rollAgle=" + rollAgle + ", flightLevel=" + flightLevel + ", magneticHeading=" + magneticHeading + ", targetStatus=" + targetStatus + ", barometricVerticalRate=" + barometricVerticalRate + ", geometricVerticalRate=" + geometricVerticalRate + ", airborneGroundVector=" + airborneGroundVector + ", trackAngleRate=" + trackAngleRate + ", timeOfReportTranmission=" + timeOfReportTranmission + ", callSign=" + callSign + ", emitterCategory=" + emitterCategory + ", metInformation=" + metInformation + ", selectedAltitude=" + selectedAltitude + ", finalStateSelectedAltitude=" + finalStateSelectedAltitude + ", tracjectoryIntent=" + tracjectoryIntent + ", serviceManagement=" + serviceManagement + ", aircraftOperationStatus=" + aircraftOperationStatus + ", surfaceCapabilitiesAndCharacterics=" + surfaceCapabilitiesAndCharacterics + ", messageAmplitude=" + messageAmplitude + ", modeSMBData=" + modeSMBData + ", aCASResolutionAdvisoryReport=" + aCASResolutionAdvisoryReport + ", receiverId=" + receiverId + ", dataAges=" + dataAges + '}';
    }

}
