/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asterix.cat21.v21;

import com.attech.asterix.cat21.entities.AirSpeed;
import com.attech.asterix.cat21.entities.FinalStateSelectedAltitude;
import com.attech.asterix.cat21.entities.MetInformation;
import com.attech.asterix.cat21.entities.Position;
import com.attech.asterix.cat21.entities.v21.ASCASResolutionAdvisoryReport;
import com.attech.asterix.cat21.entities.v21.AirborneGroundVector;
import com.attech.asterix.cat21.entities.v21.AircraftOperationalStatus;
import com.attech.asterix.cat21.entities.v21.DValue;
import com.attech.asterix.cat21.entities.v21.DataAges;
import com.attech.asterix.cat21.entities.v21.HighResolutionTimeSecond;
import com.attech.asterix.cat21.entities.v21.IValue;
import com.attech.asterix.cat21.entities.v21.MOPSVersion;
import com.attech.asterix.cat21.entities.v21.ModeSMBData;
import com.attech.asterix.cat21.entities.v21.QualityIndicator;
import com.attech.asterix.cat21.entities.v21.SelectedAltitude;
import com.attech.asterix.cat21.entities.v21.SurfaceCapabilitiesAndCharacterics;
import com.attech.asterix.cat21.entities.v21.TargetReportDescriptor;
import com.attech.asterix.cat21.entities.v21.TargetStatus;
import com.attech.asterix.cat21.entities.v21.TrajectoryIntent;

/**
 *
 * @author root
 */
public class Message {
    
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
    private short sic, sac;  // DataSourceIdentification
    private TargetReportDescriptor targetDescriptor;
    private Integer trackNumber;
    private Integer serviceIdentification;
    private Double timeOfAplicabilityPosition;
    private Position position;
    private Position hightResolutionPosition;
    
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
    private Double timeOfAplicabilityVelocity;
    private AirSpeed airSpeed;
    private IValue trueAirSpeed;
    private Integer targetAddress;
    private Double timeOfMessageReceptionOfPosition;
    private HighResolutionTimeSecond timeOfMessageReceptionOfPositionHightPrecisions;
    private Double timeOfMessageReceptionOfVelocity;
    
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
    private HighResolutionTimeSecond timeOfMessageReceptionOfVelocityHightPrecision;
    private Double geometricHeight;
    private QualityIndicator qualityIndicator;
    private MOPSVersion mopsVersion;
    private Integer mode3a;
    private Double rollAgle;
    private Float flightLevel;
    
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
    private Double magneticHeading;
    private TargetStatus targetStatus;
    private DValue barometricVerticalRate;
    private DValue geometricVerticalRate;
    private AirborneGroundVector airborneGroundVector;
    private Double trackAngleRate;
    private Double timeOfReportTranmission;
    
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
    private String callSign;
    private Short emitterCategory;
    private MetInformation metInformation;
    private SelectedAltitude selectedAltitude;
    private FinalStateSelectedAltitude finalStateSelectedAltitude;
    private TrajectoryIntent tracjectoryIntent;
    private Float serviceManagement;
    
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
    private AircraftOperationalStatus aircraftOperationStatus;
    private SurfaceCapabilitiesAndCharacterics surfaceCapabilitiesAndCharacterics;
    private Integer messageAmplitude;
    private ModeSMBData modeSMBData;
    private ASCASResolutionAdvisoryReport aCASResolutionAdvisoryReport;
    private Short receiverId;
    private DataAges dataAges;
    
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
        
    private byte[] bytes;
    Message(byte[] bytes) {
        this.bytes = bytes;
    }
    
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.getSac()).append(", ").append(this.getSic());
        /*
         *  private short sic, sac;  // DataSourceIdentification
            private TargetReportDescriptor targetDescriptor;
            private Integer trackNumber;
            private Integer serviceIdentification;
            private Double timeOfAplicabilityPosition;
            private Position position;
            private Position hightResolutionPosition;
        */
        
        // builder.append(this.qualityIndicator);
        
        if (this.targetDescriptor != null) {
            builder.append(", ").append(this.targetDescriptor.getAddressType());
            builder.append(", ").append(this.targetDescriptor.getAltitudeReportingCapability());
            builder.append(", ").append(this.targetDescriptor.isIsRangeCheck());
            builder.append(", ").append(this.targetDescriptor.isIsReportTypeFromTarget());
            builder.append(", ").append(this.targetDescriptor.isIsDifferentialCorrection());
            builder.append(", ").append(this.targetDescriptor.isIsGroundBitSet());
            builder.append(", ").append(this.targetDescriptor.isIsSimulatedTargetReport());
            builder.append(", ").append(this.targetDescriptor.isIsTestTarget());
            builder.append(", ").append(this.targetDescriptor.isIsSelectedAltitudeAvailable());
            builder.append(", ").append(this.targetDescriptor.getConfidenceLevel());
            builder.append(", ").append(this.targetDescriptor.isIsIndependentPositionCheck());
            builder.append(", ").append(this.targetDescriptor.isIsNoGoBitStatus());
            builder.append(", ").append(this.targetDescriptor.isIsCompactPositionReporting());
            builder.append(", ").append(this.targetDescriptor.isIsLocalDecodingPositionJump());
            builder.append(", ").append(this.targetDescriptor.isIsRangeCheckFail());
        } else {
            builder.append(", ");
            builder.append(", ");
            builder.append(", ");
            builder.append(", ");
            builder.append(", ");
            builder.append(", ");
            builder.append(", ");
            builder.append(", ");
            builder.append(", ");
            builder.append(", ");
            builder.append(", ");
            builder.append(", ");
            builder.append(", ");
            builder.append(", ");
            builder.append(", ");
        }
        
        if (this.trackNumber != null) {
            builder.append(", ").append(this.trackNumber);
        } else {
            builder.append(", ");
        }

        if (this.serviceIdentification != null) {
            builder.append(", ").append(this.serviceIdentification);
        } else {
            builder.append(", ");
        }
        
        if (this.timeOfAplicabilityPosition != null) {
            builder.append(", ").append(this.serviceIdentification);
        } else {
            builder.append(", ");
        }
        
        if (this.position != null) {
            builder.append(", ").append(position.getLatitude());
            builder.append(", ").append(position.getLongtitude());
        } else {
            builder.append(", ");
            builder.append(", ");
        }
        
        if (this.hightResolutionPosition != null) {
            builder.append(", ").append(hightResolutionPosition.getLatitude());
            builder.append(", ").append(hightResolutionPosition.getLongtitude());
        } else {
            builder.append(", ");
            builder.append(", ");
        }
        
        /*
          * private Double timeOfAplicabilityVelocity;
            private AirSpeed airSpeed;
            private IValue trueAirSpeed;
            private Integer targetAddress;
            private Double timeOfMessageReceptionOfPosition;
            private HighResolutionTimeSecond timeOfMessageReceptionOfPositionHightPrecisions;
            private Double timeOfMessageReceptionOfVelocity;
        */
        if (this.timeOfAplicabilityVelocity != null) {
            builder.append(", ").append(this.timeOfAplicabilityVelocity);
        } else {
            builder.append(", ");
        }
        
        
        if (this.airSpeed != null) {
            builder.append(", ").append(this.airSpeed.getUnit());
            builder.append(", ").append(this.airSpeed.getValue());
        } else {
            builder.append(", ");
            builder.append(", ");
        }
        
        if (this.trueAirSpeed != null) {
            builder.append(", ").append(this.trueAirSpeed.getValue());
        } else {
            builder.append(", ");
        }
        
        if (this.targetAddress != null) {
            builder.append(", ").append(this.targetAddress);
        } else {
            builder.append(", ");
        }
        
        if (this.timeOfMessageReceptionOfPosition != null) {
            builder.append(", ").append(this.timeOfMessageReceptionOfPosition);
        } else {
            builder.append(", ");
        }
        
        if (this.timeOfMessageReceptionOfPositionHightPrecisions != null) {
            builder.append(", ").append(this.timeOfMessageReceptionOfPositionHightPrecisions.getFullSecondIndication());
            builder.append(", ").append(this.timeOfMessageReceptionOfPositionHightPrecisions.getValue());
        } else {
            builder.append(", ");
            builder.append(", ");
        }
        
        if (this.timeOfMessageReceptionOfVelocity != null) {
            builder.append(", ").append(this.timeOfMessageReceptionOfVelocity);
        } else {
            builder.append(", ");
        }
        
        /*
         * private HighResolutionTimeSecond timeOfMessageReceptionOfVelocityHightPrecision;
            private Double geometricHeight;
            private QualityIndicator qualityIndicator;
            private MOPSVersion mopsVersion;
            private Integer mode3a;
            private Double rollAgle;
            private Integer flightLevel;
         */
        
        if (this.timeOfMessageReceptionOfVelocityHightPrecision != null) {
            builder.append(", ").append(this.timeOfMessageReceptionOfVelocityHightPrecision.getFullSecondIndication());
            builder.append(", ").append(this.timeOfMessageReceptionOfVelocityHightPrecision.getValue());
        } else {
            builder.append(", ");
            builder.append(", ");
        }
        
        if (this.geometricHeight != null) {
            builder.append(", ").append(this.geometricHeight);
        } else {
            builder.append(", ");
        }
         
        if (this.qualityIndicator != null) {
            builder.append(", ").append(this.qualityIndicator.getGeometricAltAcc());
            builder.append(", ").append(this.qualityIndicator.getPositionIntegrityCategory());
            builder.append(", ").append(this.qualityIndicator.getSilSupplement());
            builder.append(", ").append(this.qualityIndicator.getSystemDesignAssuranceLevel());
            builder.append(", ").append(this.qualityIndicator.getnACForPosition());
            builder.append(", ").append(this.qualityIndicator.getnACForVelocity());
            builder.append(", ").append(this.qualityIndicator.getnIC());
            builder.append(", ").append(this.qualityIndicator.getnICForBarometricAltitude());
            builder.append(", ").append(this.qualityIndicator.getsIL());
        } else {
            builder.append(", ");
            builder.append(", ");
            builder.append(", ");
            builder.append(", ");
            builder.append(", ");
            builder.append(", ");
            builder.append(", ");
            builder.append(", ");
            builder.append(", ");
        }
        
        if (this.mopsVersion != null) {
            builder.append(", ").append(this.mopsVersion.getLinkTechnologyType());
            builder.append(", ").append(this.mopsVersion.getVersionNumber());
            builder.append(", ").append(this.mopsVersion.isVersionNotSupported());
        } else {
            builder.append(", ");
            builder.append(", ");
            builder.append(", ");
        }
        
        if (this.mode3a != null) {
            builder.append(", ").append(this.mode3a);
        } else {
            builder.append(", ");
        }
        
        if (this.rollAgle != null) {
            builder.append(", ").append(this.rollAgle);
        } else {
            builder.append(", ");
        }
        
        if (this.flightLevel != null) {
            builder.append(", ").append(this.flightLevel);
        } else {
            builder.append(", ");
        }
        
        /*
        private Double magneticHeading;
        private TargetStatus targetStatus;
        private DValue barometricVerticalRate;
        private DValue geometricVerticalRate;
        private AirborneGroundVector airborneGroundVector;
        private Double trackAngleRate;
       
        private Double timeOfReportTranmission;
        */
        
        if (this.magneticHeading != null) {
            builder.append(", ").append(this.magneticHeading);
        } else {
            builder.append(", ");
        }

        if (this.targetStatus != null) {
            builder.append(", ").append(this.targetStatus.getPriorityStatus());
            builder.append(", ").append(this.targetStatus.getSurveillanceStatus());
            builder.append(", ").append(this.targetStatus.isIntentChangeFlag());
            builder.append(", ").append(this.targetStatus.islNAVMode());
        } else {
            builder.append(", ");
            builder.append(", ");
            builder.append(", ");
        }

        if (this.barometricVerticalRate != null) {
            builder.append(", ").append(this.barometricVerticalRate);
        } else {
            builder.append(", ");
        }

        if (this.geometricVerticalRate != null) {
            builder.append(", ").append(this.geometricVerticalRate);
        } else {
            builder.append(", ");
        }

        if (this.airborneGroundVector != null) {
            builder.append(", ").append(this.airborneGroundVector.getGroundSpeed());
            builder.append(", ").append(this.airborneGroundVector.getTrackAngle());
        } else {
            builder.append(", ");
            builder.append(", ");
        }

        if (this.trackAngleRate != null) {
            builder.append(", ").append(this.trackAngleRate);
        } else {
            builder.append(", ");
        }

        if (this.timeOfReportTranmission != null) {
            builder.append(", ").append(this.timeOfReportTranmission);
        } else {
            builder.append(", ");
        }
        
        /*
         *  private TargetIdentification targetIdentification;
         *  private Short emitterCategory;
         *  private MetInformation metInformation;
         *  private SelectedAltitude selectedAltitude;
         *  private FinalStateSelectedAltitude finalStateSelectedAltitude;
         *  private TrajectoryIntent tracjectoryIntent;
         *  private Float serviceManagement;
         * 
         */
        if (this.callSign != null) {
            builder.append(", ").append(callSign);
        } else {
            builder.append(", ");
        }
        
        if (this.emitterCategory != null) {
            builder.append(", ").append(emitterCategory);
        } else {
            builder.append(", ");
        }
        
         if (this.metInformation != null) {
            builder.append(", ").append(metInformation.getTemperature());
            builder.append(", ").append(metInformation.getTurbulence());
            builder.append(", ").append(metInformation.getWindDirection());
            builder.append(", ").append(metInformation.getWindSpeed());
            builder.append(", ").append(metInformation.isIsHasTemperature());
            builder.append(", ").append(metInformation.isIsHasTurbulence());
            builder.append(", ").append(metInformation.isIsHasWindDirection());
            builder.append(", ").append(metInformation.isIsHasWindSpeed());
        } else {
            builder.append(", ");
            builder.append(", ");
            builder.append(", ");
            builder.append(", ");
            builder.append(", ");
            builder.append(", ");
            builder.append(", ");
            builder.append(", ");
        }

         
        if (this.selectedAltitude != null) {
            builder.append(", ").append(selectedAltitude.getAltitude());
            builder.append(", ").append(selectedAltitude.getSource());
            builder.append(", ").append(selectedAltitude.isIsSourceAvailability());
        } else {
            builder.append(", ");
            builder.append(", ");
            builder.append(", ");
        }
        
        if (this.finalStateSelectedAltitude != null) {
            builder.append(", ").append(finalStateSelectedAltitude.getAltitude());
            builder.append(", ").append(finalStateSelectedAltitude.getIsAltitudeHoldModeActive());
            builder.append(", ").append(finalStateSelectedAltitude.getIsApproachModeActive());
            builder.append(", ").append(finalStateSelectedAltitude.getIsManageVerticalModeActive());
        } else {
            builder.append(", ");
            builder.append(", ");
            builder.append(", ");
            builder.append(", ");
        }
        
        if (this.tracjectoryIntent != null) {
            builder.append(", ").append(tracjectoryIntent.getAltitude());
            builder.append(", ").append(tracjectoryIntent.getLatitude());
            builder.append(", ").append(tracjectoryIntent.getLongtitle());
            builder.append(", ").append(tracjectoryIntent.getPointType());
            builder.append(", ").append(tracjectoryIntent.getRepetitionFactor());
            builder.append(", ").append(tracjectoryIntent.getTcpNumber());
            builder.append(", ").append(tracjectoryIntent.getTcpTurnRadius());
            builder.append(", ").append(tracjectoryIntent.getTimeOverPoint());
            builder.append(", ").append(tracjectoryIntent.getTurnDirection());
        } else {
            builder.append(", ");
            builder.append(", ");
            builder.append(", ");
            builder.append(", ");
            builder.append(", ");
            builder.append(", ");
            builder.append(", ");
            builder.append(", ");
            builder.append(", ");
        }
        
        if (this.serviceManagement != null) {
            builder.append(", ").append(serviceManagement);
        } else {
            builder.append(", ");
        }

        /*
         private AircraftOperationalStatus aircraftOperationStatus;
         private SurfaceCapabilitiesAndCharacterics surfaceCapabilitiesAndCharacterics;
         private Integer messageAmplitude;
         private ModeSMBData modeSMBData;
         private ASCASResolutionAdvisoryReport aCASResolutionAdvisoryReport;
         private Short receiverId;
         private DataAges dataAges;
         */
        if (this.aircraftOperationStatus != null) {
            builder.append(", ").append(aircraftOperationStatus.getTargetTrajectoryChangeReportCapability());
            builder.append(", ").append(aircraftOperationStatus.isIsAirReferencedVelocityReportCapability());
            builder.append(", ").append(aircraftOperationStatus.isIsCockpitDisplayOfTrafficInformationAirborne());
            builder.append(", ").append(aircraftOperationStatus.isIsResolutionAdvisoryActive());
            builder.append(", ").append(aircraftOperationStatus.isIsSingleAntenna());
            builder.append(", ").append(aircraftOperationStatus.isIsTCASSystemStatus());
            builder.append(", ").append(aircraftOperationStatus.isIsTargetStateReportCapability());
        } else {
            builder.append(", ");
            builder.append(", ");
            builder.append(", ");
            builder.append(", ");
            builder.append(", ");
            builder.append(", ");
            builder.append(", ");
        }
        
        if (this.surfaceCapabilitiesAndCharacterics != null) {
            builder.append(", ").append(surfaceCapabilitiesAndCharacterics.getLengthWidth());
            builder.append(", ").append(surfaceCapabilitiesAndCharacterics.isB2low());
            builder.append(", ").append(surfaceCapabilitiesAndCharacterics.isCockpitDisplayOfTrafficInformationSurface());
            builder.append(", ").append(surfaceCapabilitiesAndCharacterics.isIndent());
            builder.append(", ").append(surfaceCapabilitiesAndCharacterics.isPositionOffSetApplied());
            builder.append(", ").append(surfaceCapabilitiesAndCharacterics.isReceivingATCServices());
        } else {
            builder.append(", ");
            builder.append(", ");
            builder.append(", ");
            builder.append(", ");
            builder.append(", ");
            builder.append(", ");
        }
        
        if (this.messageAmplitude != null) {
            builder.append(", ").append(messageAmplitude);
        } else {
            builder.append(", ");
        }
        if (this.modeSMBData != null) {
            builder.append(", ").append(modeSMBData.getBDS1());
            builder.append(", ").append(modeSMBData.getBDS2());
            builder.append(", ").append(modeSMBData.getMessage());
            builder.append(", ").append(modeSMBData.getRepetitionFactor());
        } else {
               builder.append(", ");
            builder.append(", ");
            builder.append(", ");
            builder.append(", ");
        }
        
        if (this.aCASResolutionAdvisoryReport != null) {
              builder.append(", ").append(aCASResolutionAdvisoryReport.getActiveResolutionAdvisories());
              builder.append(", ").append(aCASResolutionAdvisoryReport.getMessageSubType());
              builder.append(", ").append(aCASResolutionAdvisoryReport.getMultipleThreatEncounter());
              builder.append(", ").append(aCASResolutionAdvisoryReport.getThreatIdentityData());
              builder.append(", ").append(aCASResolutionAdvisoryReport.getThreatTypeIndicator());
              builder.append(", ").append(aCASResolutionAdvisoryReport.isRACRecord());
              builder.append(", ").append(aCASResolutionAdvisoryReport.isrATerminated());
        } else {
               builder.append(", ");
            builder.append(", ");
            builder.append(", ");
            builder.append(", ");
            builder.append(", ");
            builder.append(", ");
            builder.append(", ");
        }
        
        if (this.receiverId != null) {
            builder.append(", ").append(receiverId);
        } else {
            builder.append(", ");
        }
        
        /*
        if (this.dataAges != null) {
            builder.append(", ").append(dataAges.getAirSpeedAge());
            builder.append(", ").append(dataAges.getAircraftOperationalStatusAge());
            builder.append(", ").append(dataAges.getBarometricVerticalRateAge());
            builder.append(", ").append(dataAges.getFinalStateSelectedAltitudeAge());
            builder.append(", ").append(dataAges.getFlightLevelAge());
            builder.append(", ").append(dataAges.getGeometricHeightAge());
            builder.append(", ").append(dataAges.getGeometricVerticalRateAge());
            builder.append(", ").append(dataAges.getGroundVectorAge());
            builder.append(", ").append(dataAges.getIntermediateStateSelectedAltitudeAge());
            
        }
        */
        /*
        if (this.targetAddress != null) {
            builder.append(" <Target Add: ").append(getTargetAddress()).append(">");
        }

        if (this.targetIdentification != null) {
            builder.append(" <CallSign:").append(getTargetIdentification()).append(">");
        }

       

        if (this.flightLevel != null) {
            builder.append(" <Flight Level:").append(getFlightLevel()).append(">");
        }

        if (this.airSpeed != null) {
            builder.append(" <AirSpeed:").append(getAirSpeed()).append(">");
        }

        if (this.trueAirSpeed != null) {
            builder.append(" <TruAirSpeed:").append(getTrueAirSpeed()).append(">");
        }

        if (this.geometricVerticalRate != null) {
            builder.append(" <GeometricVerticalRate:").append(getGeometricVerticalRate()).append(">");
        }
        if (this.emitterCategory != null) {
            builder.append(" <EmmiterCategory:").append(getEmitterCategory()).append(">");
        }
        */
        
        return builder.toString();
    }
    

    public short getSic() { return sic; }
    public void setSic(short sic) { this.sic = sic; }

    public short getSac() { return sac; }
    public void setSac(short sac) { this.sac = sac; }

    public TargetReportDescriptor getTargetDescriptor() { return targetDescriptor; }
    public void setTargetDescriptor(TargetReportDescriptor targetDescriptor) { this.targetDescriptor = targetDescriptor; }

    public Integer getTrackNumber() { return trackNumber; }
    public void setTrackNumber(Integer trackNumber) { this.trackNumber = trackNumber; }

    public Integer getServiceIdentification() { return serviceIdentification; }
    public void setServiceIdentification(Integer serviceIdentification) { this.serviceIdentification = serviceIdentification; }

    public Double getTimeOfAplicabilityPosition() { return timeOfAplicabilityPosition; }
    public void setTimeOfAplicabilityPosition(Double timeOfAplicabilityPosition) { this.timeOfAplicabilityPosition = timeOfAplicabilityPosition; }

    public Position getPosition() { return position; }
    public void setPosition(Position position) { this.position = position; }

    public Position getHighResolutionPosition() { return hightResolutionPosition; }
    public void setHighResolutionPosition(Position hightResolutionPosition) { this.hightResolutionPosition = hightResolutionPosition;}

    public Double getTimeOfAplicabilityVelocity() { return timeOfAplicabilityVelocity; }
    public void setTimeOfAplicabilityVelocity(Double timeOfAplicabilityVelocity) { this.timeOfAplicabilityVelocity = timeOfAplicabilityVelocity; }

    public AirSpeed getAirSpeed() { return airSpeed; }
    public void setAirSpeed(AirSpeed airSpeed) { this.airSpeed = airSpeed; }

    public IValue getTrueAirSpeed() { return trueAirSpeed; }
    public void setTrueAirSpeed(IValue trueAirSpeed) { this.trueAirSpeed = trueAirSpeed; }

  
    public Integer getTargetAddress() {
        return targetAddress;
    }
    public void setTargetAddress(Integer targetAddress) {
        this.targetAddress = targetAddress;
    }

    public Double getTimeOfMessageReceptionOfPosition() {
        return timeOfMessageReceptionOfPosition;
    }
    public void setTimeOfMessageReceptionOfPosition(Double timeOfMessageReceptionOfPosition) {
        this.timeOfMessageReceptionOfPosition = timeOfMessageReceptionOfPosition;
    }

    public HighResolutionTimeSecond getTimeOfMessageReceptionOfPositionHightPrecisions() {
        return timeOfMessageReceptionOfPositionHightPrecisions;
    }
    public void setTimeOfMessageReceptionOfPositionHightPrecisions(HighResolutionTimeSecond timeOfMessageReceptionOfPositionHightPrecisions) {
        this.timeOfMessageReceptionOfPositionHightPrecisions = timeOfMessageReceptionOfPositionHightPrecisions;
    }

    public Double getTimeOfMessageReceptionOfVelocity() {
        return timeOfMessageReceptionOfVelocity;
    }
    public void setTimeOfMessageReceptionOfVelocity(Double timeOfMessageReceptionOfVelocity) {
        this.timeOfMessageReceptionOfVelocity = timeOfMessageReceptionOfVelocity;
    }

    public HighResolutionTimeSecond getTimeOfMessageReceptionOfVelocityHightPrecision() {
        return timeOfMessageReceptionOfVelocityHightPrecision;
    }
    public void setTimeOfMessageReceptionOfVelocityHightPrecision(HighResolutionTimeSecond timeOfMessageReceptionOfVelocityHightPrecision) {
        this.timeOfMessageReceptionOfVelocityHightPrecision = timeOfMessageReceptionOfVelocityHightPrecision;
    }

    public Double getGeometricHeight() {
        return geometricHeight;
    }
    public void setGeometricHeight(Double geometricHeight) {
        this.geometricHeight = geometricHeight;
    }

    public QualityIndicator getQualityIndicator() {
        return qualityIndicator;
    }
    public void setQualityIndicator(QualityIndicator qualityIndicator) {
        this.qualityIndicator = qualityIndicator;
    }

    public MOPSVersion getMopsVersion() {
        return mopsVersion;
    }
    public void setMopsVersion(MOPSVersion mopsVersion) {
        this.mopsVersion = mopsVersion;
    }

    public Integer getMode3a() {
        return mode3a;
    }
    public void setMode3a(Integer mode3a) {
        this.mode3a = mode3a;
    }

    public Double getRollAgle() {
        return rollAgle;
    }
    public void setRollAgle(Double rollAgle) {
        this.rollAgle = rollAgle;
    }

    public Float getFlightLevel() {
        return flightLevel;
    }
    public void setFlightLevel(Float flightLevel) {
        this.flightLevel = flightLevel;
    }

    public Double getMagneticHeading() {
        return magneticHeading;
    }
    public void setMagneticHeading(Double magneticHeading) {
        this.magneticHeading = magneticHeading;
    }

    public TargetStatus getTargetStatus() {
        return targetStatus;
    }
    public void setTargetStatus(TargetStatus targetStatus) {
        this.targetStatus = targetStatus;
    }

    public DValue getBarometricVerticalRate() {
        return barometricVerticalRate;
    }
    public void setBarometricVerticalRate(DValue barometricVerticalRate) {
        this.barometricVerticalRate = barometricVerticalRate;
    }

    public DValue getGeometricVerticalRate() {
        return geometricVerticalRate;
    }
    public void setGeometricVerticalRate(DValue geometricVerticalRate) {
        this.geometricVerticalRate = geometricVerticalRate;
    }

    public AirborneGroundVector getAirborneGroundVector() {
        return airborneGroundVector;
    }
    public void setAirborneGroundVector(AirborneGroundVector airborneGroundVector) {
        this.airborneGroundVector = airborneGroundVector;
    }

    public Double getTrackAngleRate() {
        return trackAngleRate;
    }
    public void setTrackAngleRate(Double trackAngleRate) {
        this.trackAngleRate = trackAngleRate;
    }

    public Double getTimeOfReportTranmission() {
        return timeOfReportTranmission;
    }
    public void setTimeOfReportTranmission(Double timeOfReportTranmission) {
        this.timeOfReportTranmission = timeOfReportTranmission;
    }

    public String getCallSign() {
        return callSign;
    }
    public void setCallSign(String callSign) {
        this.callSign = callSign;
    }

    public Short getEmitterCategory() {
        return emitterCategory;
    }
    public void setEmitterCategory(Short emitterCategory) {
        this.emitterCategory = emitterCategory;
    }

    public MetInformation getMetInformation() {
        return metInformation;
    }
    public void setMetInformation(MetInformation metInformation) {
        this.metInformation = metInformation;
    }

    public SelectedAltitude getSelectedAltitude() {
        return selectedAltitude;
    }
    public void setSelectedAltitude(SelectedAltitude selectedAltitude) {
        this.selectedAltitude = selectedAltitude;
    }

    public FinalStateSelectedAltitude getFinalStateSelectedAltitude() {
        return finalStateSelectedAltitude;
    }
    public void setFinalStateSelectedAltitude(FinalStateSelectedAltitude finalStateSelectedAltitude) {
        this.finalStateSelectedAltitude = finalStateSelectedAltitude;
    }

    public TrajectoryIntent getTracjectoryIntent() {
        return tracjectoryIntent;
    }
    public void setTracjectoryIntent(TrajectoryIntent tracjectoryIntent) {
        this.tracjectoryIntent = tracjectoryIntent;
    }

    public Float getServiceManagement() {
        return serviceManagement;
    }
    public void setServiceManagement(Float serviceManagement) {
        this.serviceManagement = serviceManagement;
    }

    public AircraftOperationalStatus getAircraftOperationStatus() {
        return aircraftOperationStatus;
    }
    public void setAircraftOperationStatus(AircraftOperationalStatus aircraftOperationStatus) {
        this.aircraftOperationStatus = aircraftOperationStatus;
    }

    public SurfaceCapabilitiesAndCharacterics getSurfaceCapabilitiesAndCharacterics() {
        return surfaceCapabilitiesAndCharacterics;
    }
    public void setSurfaceCapabilitiesAndCharacterics(SurfaceCapabilitiesAndCharacterics surfaceCapabilitiesAndCharacterics) {
        this.surfaceCapabilitiesAndCharacterics = surfaceCapabilitiesAndCharacterics;
    }

    public Integer getMessageAmplitude() {
        return messageAmplitude;
    }
    public void setMessageAmplitude(Integer messageAmplitude) {
        this.messageAmplitude = messageAmplitude;
    }

    public ModeSMBData getModeSMBData() {
        return modeSMBData;
    }
    public void setModeSMBData(ModeSMBData modeSMBData) {
        this.modeSMBData = modeSMBData;
    }

    public ASCASResolutionAdvisoryReport getaCASResolutionAdvisoryReport() {
        return aCASResolutionAdvisoryReport;
    }
    public void setaCASResolutionAdvisoryReport(ASCASResolutionAdvisoryReport aCASResolutionAdvisoryReport) {
        this.aCASResolutionAdvisoryReport = aCASResolutionAdvisoryReport;
    }

    public Short getReceiverId() {
        return receiverId;
    }
    public void setReceiverId(Short receiverId) {
        this.receiverId = receiverId;
    }

    public DataAges getDataAges() {
        return dataAges;
    }
    public void setDataAges(DataAges dataAges) {
        this.dataAges = dataAges;
    }

    public byte[] getBytes() {
        return bytes;
    }
    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }
}
