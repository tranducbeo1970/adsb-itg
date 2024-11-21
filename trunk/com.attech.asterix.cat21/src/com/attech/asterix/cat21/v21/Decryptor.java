/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asterix.cat21.v21;

import com.attech.asterix.IMessageDecryptor;
import com.attech.asterix.InternalMessage;
import com.attech.asterix.cat21.entities.AirSpeed;
import com.attech.asterix.cat21.entities.DataSourceIdentification;
import com.attech.asterix.cat21.entities.FinalStateSelectedAltitude;
import com.attech.asterix.cat21.entities.GroundVector;
import com.attech.asterix.cat21.entities.MetInformation;
import com.attech.asterix.cat21.entities.Position;
import com.attech.asterix.cat21.entities.RateOfTurn;
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
import com.attech.asterix.cat21.util.BitwiseUtils;
import com.attech.asterix.cat21.util.CharacterMap;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;


public class Decryptor implements IMessageDecryptor{

    private static Decryptor instance;
    private static final char[] characters = CharacterMap.getCharacterMap();
    private static final byte[] masked = new byte[]{(byte) 0x80, (byte) 0x40, (byte) 0x20, (byte) 0x10, (byte) 0x08, (byte) 0x04, (byte) 0x02};
    private static final short ITEM_NUMBER = 49;
    // private boolean[] header;
    private int index;
    private int length;
    private int categoryIndex;
    
    public int getLen() {
        return this.index;
    }

    @Override
    public List<Message> decrypt(byte[] bytes) {
        categoryIndex = bytes[0] & 0xFF;
        if (categoryIndex != 21) return null;
        
        length = bytes[1] & 0xFF;
        length = length << 8 | bytes[2] & 0xFF;
        
        index = 0;
        index += 3;
        
        final List<Message> messages = new ArrayList<>();
        while (index < length) {

            // System.out.println("index: " + index);
            final int firstIndex = index;
            final boolean[] header = getHeader(bytes);

            // Message message = new Message(bytes);
            Message message = new Message(bytes);
            for (int i = 0; i < ITEM_NUMBER; i++) {
                if (!header[i]) continue;

                // System.out.println("index: " + index);
                switch (i) {
                    case 0: // DataSource Indentification
                        // System.out.println("DataSource Indentification");
                        DataSourceIdentification sourceIdentification = getDataSourceIden(bytes);
                        message.setSac(sourceIdentification.getSac());
                        message.setSic(sourceIdentification.getSic());
                        break;
                    case 1: // Target Report Descriptor
                        // System.out.println("Target Report Descriptor");
                        message.setTargetDescriptor(getTargetReportDescriptor(bytes));
                        break;
                    case 2: // Track Number
                        // System.out.println("Track Number");
                        message.setTrackNumber(ByteBuffer.wrap(new byte[]{0x00, 0x00, bytes[index], bytes[index + 1]}).getInt());
                        index += 2;
                        break;
                    case 3: // Service Identification
                        // System.out.println("Service Identification");
                        message.setServiceIdentification(bytes[index] & 0xFF);
                        index += 1;
                        break;
                    case 4: // Time of Applicability for Position
                        // System.out.println("Time of Applicability for Position");
                        message.setTimeOfAplicabilityPosition(getTime(bytes));
                        break;
                    case 5: // Position in WGS-84 co-ordinates
                        // System.out.println("Position in WGS-84 co-ordinates");
                        message.setPosition(getPosition(bytes));
                        break;
                    case 6: // Position in WGS-84 co-ordinates, high res.
                        // System.out.println("Position in WGS-84 co-ordinates, high res.");
                        message.setHighResolutionPosition(getHighResolutionPosition(bytes));
                        break;
                    case 7: // Time of Applicability for Velocity
                        // System.out.println("Time of Applicability for Velocity.");
                        message.setTimeOfAplicabilityVelocity(getTime(bytes));
                        break;
                    case 8: // Air Speed
                        // System.out.println("Air Speed");
                        message.setAirSpeed(getAirSpeed(bytes));
                        break;
                    case 9: // True Air Speed
                        // System.out.println("True Air Speed");
                        message.setTrueAirSpeed(getTrueAirSpeed(bytes));
                        break;
                    case 10: // Target Address
                        // System.out.println("Target Address");
                        message.setTargetAddress(getTargetAddress(bytes));
                        break;
                    case 11: // Time of Message Reception of Position
                        // System.out.println("Time of Message Reception of Position");
                        message.setTimeOfMessageReceptionOfPosition(getTime(bytes));
                        break;
                    case 12: // Time of Message Reception of Position-High Precision
                        // System.out.println("Time of Message Reception of Position-High Precision");
                        message.setTimeOfMessageReceptionOfPositionHightPrecisions(getTimeOfHighResolution(bytes));
                        break;
                    case 13: // Time of Message Reception of Velocity
                        // System.out.println("Time of Message Reception of Velocity");
                        message.setTimeOfMessageReceptionOfVelocity(getTime(bytes));
                        break;
                    case 14: // Time of Message Reception of Velocity-High Precision
                        // System.out.println("Time of Message Reception of Velocity-High Precision");
                        message.setTimeOfMessageReceptionOfVelocityHightPrecision(getTimeOfHighResolution(bytes));
                        break;
                    case 15: // Geometric Height
                        // System.out.println("Geometric Height");
                        message.setGeometricHeight(getGeometricHeight(bytes));
                        break;
                    case 16: // Quality Indicators
                        // System.out.println("Quality Indicators");
                        message.setQualityIndicator(getQualityIndicators(bytes));
                        break;
                    case 17: // MOPS Version
                        // System.out.println("MOPS Version");
                        message.setMopsVersion(getMOPSVersion(bytes));
                        break;
                    case 18: // Mode 3/A Code
                        // System.out.println("Mode 3/A Code");
                        int value = ByteBuffer.wrap(new byte[]{0x00, 0x00, bytes[index], bytes[index + 1]}).getInt();
                        String sValue = Integer.toOctalString(value);
                        value = Integer.parseInt(sValue);
                        message.setMode3a(value);
                        index += 2;
                        break;
                    case 19: // Roll Angle
                        // System.out.println("Roll Angle");
                        message.setRollAgle(getRollAngle(bytes));
                        break;
                    case 20: // Flight Level
                        // System.out.println("Flight Level");
                        message.setFlightLevel(getFlighLevel(bytes));
                        break;
                    case 21: // Magnetic Heading
                        // System.out.println("Magnetic Heading");
                        message.setMagneticHeading(getMagneticHeading(bytes));
                        break;
                    case 22: // Target Status
                        // System.out.println("Target Status");
                        message.setTargetStatus(getTargetStatus(bytes));
                        break;
                    case 23: // Barometric Vertical Rate
                        // System.out.println("Barometric Vertical Rate");
                        message.setBarometricVerticalRate(getBarometricVerticalRate(bytes));
                        break;
                    case 24: // Geometric Vertical Rate
                        // System.out.println("Geometric Vertical Rate");
                        message.setGeometricVerticalRate(getBarometricVerticalRate(bytes));
                        break;
                    case 25: // Airborne Ground Vector
                        // System.out.println("Airborne Ground Vector");
                        message.setAirborneGroundVector(getAirborneGroundVector(bytes));
                        break;
                    case 26: // Track Angle Rate
                        // System.out.println("Track Angle Rate");
                        message.setTrackAngleRate((double) (ByteBuffer.wrap(new byte[]{0x00, 0x00, bytes[index], bytes[index + 1]}).getInt() / 32));
                        index += 2;
                        break;
                    case 27: // Time of Report Transmission
                        // System.out.println("Time of Report Transmission");
                        message.setTimeOfReportTranmission(getTime(bytes));
                        break;
                    case 28: // Target Identification
                        // System.out.println("Target Identification");
                        message.setCallSign(getTargetIden(bytes));
                        break;
                    case 29: // Emitter Category
                        // System.out.println("Emitter Category");
                        message.setEmitterCategory((short) (bytes[index] & 0xFF));
                        index += 1;
                        break;
                    case 30: // Met Information
                        // System.out.println("Met Information");
                        message.setMetInformation(getMetInformation(bytes));
                        break;
                    case 31: // Selected Altitude
                        // System.out.println("Selected Altitude");
                        message.setSelectedAltitude(getSelectedAltitude(bytes));
                        break;
                    case 32: // Final State Selected Altitude
                        // System.out.println("Final State Selected Altitude");
                        message.setFinalStateSelectedAltitude(getFinalStateSeletectAltitude(bytes));
                        break;
                    case 33: // Trajectory Intent
                        // System.out.println("Trajectory Intent");
                        message.setTracjectoryIntent(getTrajectoryIntent(bytes));
                        break;
                    case 34: // Service Management
                        // System.out.println("Service Management");
                        message.setServiceManagement((bytes[index] & 0xFF) * 0.5f);
                        index += 1;
                        break;
                    case 35: // Aircraft Operational Status
                        // System.out.println("Aircraft Operational Status");
                        message.setAircraftOperationStatus(getAircraftOperationStatus(bytes));
                        break;
                    case 36: // Surface Capabilities and Characteristics
                        // System.out.println("Surface Capabilities and Characteristics");
                        message.setSurfaceCapabilitiesAndCharacterics(getSurfaceCapabilitiesAndCharacterics(bytes));
                        break;
                    case 37: // Message Amplitude
                        // System.out.println("Message Amplitude");
                        message.setMessageAmplitude(bytes[index] & 0xFF);
                        index += 1;
                        break;
                    case 38: // Mode S MB Data
                        // System.out.println("Mode S MB Data");
                        message.setModeSMBData(getModeSMDData(bytes));
                        break;
                    case 39: // ACAS Resolution Advisory Report
                        // System.out.println("ACAS Resolution Advisory Report");
                        message.setaCASResolutionAdvisoryReport(getASCASResolutionAdvisorReport(bytes));
                        break;
                    case 40: // Receiver ID
                        // System.out.println("Receiver ID");
                        message.setReceiverId((short) (bytes[index] * 0xFF));
                        index += 1;
                        break;
                    case 41: // Data Ages
                        // System.out.println("Data Ages");
                        message.setDataAges(getDataAges(bytes));
                        break;
                    case 42: // Not Used
                        break;
                    case 43: // Not Used
                        break;
                    case 44: // Not Used
                        break;
                    case 45: // Not Used
                        break;
                    case 46: // Not Used
                        break;
                    case 47: // Reserved Expansion Field
                        int reservedExpansionFieldLength = bytes[index] & 0xFF;
                        index += reservedExpansionFieldLength;
                        break;
                    case 48: // Special Purpose Field

                        int specialPurposeFieldLength = bytes[index] & 0xFF;
                        index += specialPurposeFieldLength;
                        break;
                    default:
                        break;
                }
            }

            int contentLen = index - firstIndex;
            // System.out.println("[ " + firstIndex + " - " + index + " ] length: " + contentLen);
            final byte[] contentsByte = new byte[contentLen + 3];
            System.arraycopy(bytes, firstIndex, contentsByte, 3, contentLen);
            contentsByte[0] = 21;
            contentsByte[1] = (byte) ((contentLen >> 8) & 0xFF);
            contentsByte[2] = (byte) (contentLen & 0xFF);
            message.setBytes(contentsByte);
            // message.setHeaders(header);
            // message.setFields(fields);
            // messages.add(message);
            
            messages.add(message);
        }

        // if (index != length) throw new Exception("Index is not equal length");
        return messages;
    }
    
    public boolean[] getHeader(byte[] bytes) {
        
        boolean [] header = new boolean[ITEM_NUMBER];
        boolean isExtend = true;
        int bit = 0;
        int headerIndex = 0;
        while (isExtend) {

            byte currentByte = bytes[index];
            for (int i = 1; i < 8; i++) {
                bit = currentByte & masked[i - 1];
                if (bit == 0) continue;
                header[headerIndex * 7 + i - 1] = true;
            }

            bit = currentByte & 0x01;
            isExtend = (bit != 0);
            index++;
            headerIndex++; // Increasing readed bytes
        }
        return header;
    }

    public DataSourceIdentification getDataSourceIden(byte[] bytes) {
        DataSourceIdentification source = new DataSourceIdentification();
        source.setSac((short) (bytes[index++] & 0xFF));
        //index++;
        source.setSic((short) (bytes[index++] & 0xFF));
        //index++;
        return source;
    }

    public TargetReportDescriptor getTargetReportDescriptor(byte[] bytes) {
        
        TargetReportDescriptor targerDescriptor = new TargetReportDescriptor();
        
        byte byteHigh = bytes[index++];
        // index++;
        
        int value = byteHigh >> 5 & 0x07;
        targerDescriptor.setAddressType(value);
        
        value = byteHigh >> 3 & 0x03;
        targerDescriptor.setAltitudeReportingCapability(value);
        
        boolean bValue = (byteHigh & 0x04) > 0;
        targerDescriptor.setIsRangeCheck(bValue);
        
        bValue = (byteHigh & 0x02) > 0;
        targerDescriptor.setIsReportTypeFromTarget(bValue);
        
        // Check extention
        boolean isEnd = (byteHigh & 0x01) == 0;
        if (isEnd) return targerDescriptor;
        
        // Extention 01
        byteHigh = bytes[index++];
        //index++;
        
        bValue = (byteHigh & 0x80) > 0;
        targerDescriptor.setIsDifferentialCorrection(bValue);
        
        bValue = (byteHigh & 0x40) > 0;
        targerDescriptor.setIsGroundBitSet(bValue);
        
        bValue = (byteHigh & 0x20) > 0;
        targerDescriptor.setIsSimulatedTargetReport(bValue);
        
        bValue = (byteHigh & 0x10) > 0;
        targerDescriptor.setIsTestTarget(bValue);
        
        bValue = (byteHigh & 0x08) > 0;
        targerDescriptor.setIsSelectedAltitudeAvailable(bValue);
        
        value = byteHigh >> 1 & 0x03;
        targerDescriptor.setConfidenceLevel(value);
        
        // Check extention
        isEnd = (byteHigh & 0x01) == 0;
        if (isEnd) return targerDescriptor;
        
        // Extention 02
        byteHigh = bytes[index++];
        //index++;
        
        bValue = (byteHigh & 0x20) > 0;
        targerDescriptor.setIsDifferentialCorrection(bValue);
        
        bValue = (byteHigh & 0x10) > 0;
        targerDescriptor.setIsNoGoBitStatus(bValue);
        
        bValue = (byteHigh & 0x08) > 0;
        targerDescriptor.setIsCompactPositionReporting(bValue);
        
        bValue = (byteHigh & 0x04) > 0;
        targerDescriptor.setIsLocalDecodingPositionJump(bValue);
        
        bValue = (byteHigh & 0x02) > 0;
        targerDescriptor.setIsRangeCheck(bValue);
        
        return targerDescriptor;
    }
    
    public double getTime(byte[] bytes) {
        int value = (bytes[index] & 0xFF) << 16 | (bytes[index + 1] & 0xFF) << 8 | (bytes[index + 2] & 0xFF);
        // int value = ByteBuffer.wrap(new byte[]{0x00, bytes[index], bytes[index + 1], bytes[index + 2]}).getInt();
        index += 3;
        return (double) value/128;
    }
    
    public HighResolutionTimeSecond getTimeOfHighResolution(byte[] bytes) {
        HighResolutionTimeSecond time = new HighResolutionTimeSecond();

        byte byteHigh = bytes[index];
        index++;
        short value = (short) (byteHigh >> 6 & 0x03);
        time.setFullSecondIndication(value);

        int iValue = ByteBuffer.wrap(new byte[]{(byte) (byteHigh & 0x3f), bytes[index], bytes[index + 1], bytes[index + 2]}).getInt();
        time.setValue(iValue * 0.9313);
        index += 3;
        return time;
    }

    public Position getPosition(byte[] bytes) {
        Position position = new Position();

        byte[] latBytes = new byte[]{bytes[index], bytes[index + 1], bytes[index + 2]};
        int value = BitwiseUtils.convertFrom2sComplementNumber(latBytes);
        position.setLatitude(value * 2.145767 * 0.00001);
        index += 3;

        latBytes = new byte[]{bytes[index], bytes[index + 1], bytes[index + 2]};
        value = BitwiseUtils.convertFrom2sComplementNumber(latBytes);
        position.setLongtitude(value * 2.145767 * 0.00001);
        index += 3;
        return position;
    }
    
    public Position getHighResolutionPosition(byte[] bytes) {
        Position position = new Position();

        /*
        byte[] latBytes = new byte[]{bytes[index], bytes[index + 1], bytes[index + 2], bytes[index + 3]};
        index += 4;
        ByteBuffer bytBuffer = ByteBuffer.wrap(latBytes);
        int value = bytBuffer.getInt();
        */
        int value = ((bytes[index] & 0xFF) << 24) | ((bytes[index + 1] & 0xFF) << 16) | ((bytes[index + 2] & 0xFF) << 8) | (bytes[index + 3] & 0xFF);
        index += 4;
        position.setLatitude(value * 0.00000016764);
        
        /*
        latBytes = new byte[]{bytes[index], bytes[index + 1], bytes[index + 2], bytes[index + 3]};
        index += 4;
        bytBuffer = ByteBuffer.wrap(latBytes);
        value = bytBuffer.getInt();
        */
        
        value = ((bytes[index] & 0xFF) << 24) | ((bytes[index + 1] & 0xFF) << 16) | ((bytes[index + 2] & 0xFF) << 8) | (bytes[index + 3] & 0xFF);
        index += 4;
        position.setLongtitude(value * 0.00000016764);

        return position;
    }

    public AirSpeed getAirSpeed(byte[] bytes) {
        byte byteHight = bytes[index];
        index++;

        int speedUnit = byteHight & 0x80;
        AirSpeed airSpeed = new AirSpeed();
        airSpeed.setUnit(speedUnit > 0);

        // this.setUnit(BitwiseUtils.extractBit(byteHight, 8, 1));
        int speedValue = byteHight & 0x7F;
        speedValue = speedValue << 8 | bytes[index];
        double speed = speedValue * (speedUnit > 0 ? Math.pow(2, -14) : 0.001);
        airSpeed.setValue(speed);
        index++;
        return airSpeed;
    }
    
    public IValue getTrueAirSpeed(byte[] bytes) {
        IValue value = new IValue();
        
        byte byteHigh = bytes[index];
        index++;
        
        boolean isRangeExceeded = (byteHigh & 0x80) > 0;
        value.setIsRangeExceeded(isRangeExceeded);
        
        int speedValue = byteHigh & 0x7F;
        speedValue = (speedValue << 8 | bytes[index]) & 0xFF;
        index++;
        value.setValue(speedValue);
        
        return value;
    }
    
    public int getTargetAddress(byte[] bytes) {
        // ByteBuffer byffer = ByteBuffer.wrap(new byte[]{0x00, bytes[index], bytes[index + 1], bytes[index + 2]});
        // int value = byffer.getInt();
        int value = (bytes[index] & 0xFF) << 16 | (bytes[index + 1] & 0xFF) << 8 | (bytes[index + 2] & 0xFF);
        index+=3;
        return value;
    }    
   
    public double getGeometricHeight(byte[] bytes) {
        int value = BitwiseUtils.convertFrom2sComplementNumber(new byte[]{bytes[index], bytes[index + 1]});
        index += 2;
        return value * 6.25;
    }
    
    public MOPSVersion getMOPSVersion(byte[] bytes) {
        MOPSVersion mopsVersion = new MOPSVersion();
        int byteHigh = bytes[index];
        index++;
        
        boolean bValue = (byteHigh & 0x40) > 0;
        mopsVersion.setVersionNotSupported(bValue);
        
        short iValue = (short) (byteHigh >> 3 | 0x07);
        mopsVersion.setVersionNumber(iValue);
        
        iValue = (short) (byteHigh | 0x07);
        mopsVersion.setLinkTechnologyType(iValue);
        
        return mopsVersion;
    }
    
    public QualityIndicator getQualityIndicators(byte[] bytes) {

        QualityIndicator indicator = new QualityIndicator();
        
        byte byte01 = bytes[index];
        index++;
        
        short value = (short) ((byte01 >> 5) & 0x07);
        indicator.setnACForVelocity(value);
        
        value = (short) ((byte01 >> 1) & 0x0F);
        indicator.setnIC(value);
        
        boolean extention = (byte01 & 0x01) > 0;
        
        if (!extention) return indicator;
        
        // Sub field 01
        byte01 = bytes[index];
        index++;
        boolean bValue = ((byte01 >> 7) & 0x01) > 0;
        indicator.setnICForBarometricAltitude(bValue);
        
        value = (short) ((byte01 >> 5) & 0x03);
        indicator.setsIL(value);
        
        value = (short) ((byte01 >> 1) & 0x0F);
        indicator.setnACForPosition(value);
        
        extention = (byte01 & 0x01) > 0;
        if (!extention) return indicator;
        
        // Sub field 02
        byte01 = bytes[index];
        index++;
        bValue = ((byte01 >> 5) & 0x01) > 0;
        indicator.setSilSupplement(bValue);
        
        value = (short) ((byte01 >> 3) & 0x03);
        indicator.setSystemDesignAssuranceLevel(value);
        
        value = (short) ((byte01 >> 1) & 0x03);
        indicator.setGeometricAltAcc(value);
        
        extention = (byte01 & 0x01) > 0;
        if (!extention) return indicator;
        
        // Sub field 03
        byte01 = bytes[index];
        index++;
        
        value = (short) ((byte01 >> 4) & 0x07);
        indicator.setPositionIntegrityCategory(value);
        
        extention = (byte01 & 0x01) > 0;
        while (extention) {
            extention = (bytes[index] & 0x01) == 0;
            index++;
        }

        return indicator;
    }
    
    public double getRollAngle(byte[] bytes) {
        byte[] bts = new byte[]{bytes[index], bytes[index + 1]};
        index += 2;
        int intValue = BitwiseUtils.convertFrom2sComplementNumber(bts);
        return intValue * 0.01f;
    }

    public float getFlighLevel(byte[] bytes)  {
        byte[] bts = new byte[]{bytes[index], bytes[index + 1]};
        index += 2;
        int value = BitwiseUtils.convertFrom2sComplementNumber(bts);
        return (float) value / 4;
    }

    public TargetStatus getTargetStatus(byte [] bytes) {
        TargetStatus targetStatus = new TargetStatus();
        byte byteHigh = bytes[index];
        index++;
        
        boolean bValue = (byteHigh & 0x80) > 0;
        targetStatus.setIntentChangeFlag(bValue);
        
        bValue = (byteHigh & 0x40) > 0;
        targetStatus.setlNAVMode(bValue);
        
        short sValue = (short) (byteHigh >> 2 & 0x07);
        targetStatus.setPriorityStatus(sValue);
        
        sValue = (short) (byteHigh >> 2 & 0x03);
        targetStatus.setSurveillanceStatus(sValue);
        
        return targetStatus;
    }
        
    public DValue getBarometricVerticalRate(byte [] bytes) {
        DValue dValue = new DValue();
        
        byte byteHigh = bytes[index];
        index++;
        boolean isRangeExceeded = (byteHigh & 0x80) > 0;
        dValue.setIsRangeExceeded(isRangeExceeded);
        
        int iValue = 0;
        //int iValue = (byteHigh & 0x7F) << 8 | bytes[index] & 0xFF;
        // int iValue = ByteBuffer.wrap(new byte[]{0x00, 0x00, (byte) (byteHigh & 0x7F), bytes[index]}).getInt();
        boolean positive = (int) (byteHigh & 0x40) > 0 ? false : true;
        // int length = bytes.length;
        
        if (positive) {
            iValue = (byteHigh & 0x7F) << 8 | bytes[index] & 0xFF;
        } else {
            iValue = (~(byteHigh & 0x7F) << 8 | ~bytes[index]) & 0xFF;
            iValue = iValue + 0x01;
            iValue = -iValue;
        }
        
        // System.out.println(">> " + Integer.toBinaryString(byteHigh & 0xFF) + " - " + Integer.toBinaryString(bytes[index] & 0xFF) + " : " + iValue);
        dValue.setValue((float) iValue* 6.25);
        index++;
        return dValue;
    }
        
    public AirborneGroundVector getAirborneGroundVector(byte[] bytes) {
        AirborneGroundVector airbone = new AirborneGroundVector();
        byte byteHigh = bytes[index];
        index++;
        
        boolean isRangeExceeded = (byteHigh & 0x80) > 0;
        airbone.setRangeExceeded(isRangeExceeded);
        
        int iValue = ByteBuffer.wrap(new byte[] { 0x00, 0x00, (byte)(byteHigh & 0x7F), bytes[index] }).getInt();
        index++;
        airbone.setGroundSpeed(iValue* 0.22);
        
        
        iValue = ByteBuffer.wrap(new byte[] { 0x00, 0x00, bytes[index], bytes[index+1] }).getInt();
        index+=2;
        airbone.setTrackAngle(iValue* 0.0055);

        return airbone;
    }
        
    public SelectedAltitude getSelectedAltitude(byte[] bytes) {
        SelectedAltitude altitude = new SelectedAltitude();
        byte byteHigh = bytes[index];
        index++;
        
        boolean bValue = (byteHigh & 0x80) > 0;
        altitude.setIsSourceAvailability(bValue);
        
        short sValue = (short) (byteHigh >> 5 & 0x03);
        altitude.setSource(sValue);
        
        int iValue = BitwiseUtils.convertFrom2sComplementNumber(new byte[] { (byte)(byteHigh & 0x1F), bytes[index]}) * 25;
        altitude.setAltitude(iValue);
        index++;
        return altitude;
    }
    
    public MetInformation getMetInformation(byte[] bytes) {
        //int counting = 1;
        byte header = bytes[index];
        index++;
        
        MetInformation metInfo = new MetInformation();
        
        // get header
        metInfo.setIsHasWindSpeed((header & 0x80)  > 0);
        metInfo.setIsHasWindDirection((header & 0x40)  > 0);
        metInfo.setIsHasTemperature((header & 0x20)  > 0);
        metInfo.setIsHasTurbulence((header & 0x10)  > 0);
        
        // check extention bit
        //int fxbit = (header & 0x01);
        //if (fxbit == 0) return metInfo;
        
        if (metInfo.isIsHasWindSpeed()) {
            int value = bytes[index] & 0xFF;
            index++;
            
            value = value << 8 | bytes[index];
            index++;
            metInfo.setWindSpeed(value);
        }
        
        if (metInfo.isIsHasWindDirection()) {
           int value = bytes[index] & 0xFF;
            index++;
            
            value = value << 8 | bytes[index];
            index++;
            metInfo.setWindDirection(value);
        }

        if (metInfo.isIsHasTemperature()) {
            byte[] tempBytes = new byte[]{bytes[index], bytes[index + 1]};
            index+=2;
            int value = BitwiseUtils.convertFrom2sComplementNumber(tempBytes);
            metInfo.setTemperature(value * 0.25f);
        }
        
        if (metInfo.isIsHasTurbulence()) {
            metInfo.setTurbulence(bytes[index] & 0xFF);
            index++;
        }
        
        return metInfo;
    }
    
    public FinalStateSelectedAltitude getFinalStateSeletectAltitude(byte[] bytes) {
        FinalStateSelectedAltitude altitude = new FinalStateSelectedAltitude();
        byte byteHigh = bytes[index];
        index++;
        
        boolean bValue = (byteHigh & 0x80) > 0;
        altitude.setIsManageVerticalModeActive(bValue);
        
        bValue = (byteHigh & 0x40) > 0;
        altitude.setIsAltitudeHoldModeActive(bValue);
        
        bValue = (byteHigh & 0x20) > 0;
        altitude.setIsApproachModeActive(bValue);
                
        int iValue = BitwiseUtils.convertFrom2sComplementNumber(new byte[] { (byte)(byteHigh & 0x1F), bytes[index]}) * 25;
        altitude.setAltitude(iValue);
        index++;
        return altitude;
    }
    
    public TrajectoryIntent getTrajectoryIntent(byte[] bytes) {
        byte data = bytes[index];
        index++;

        // int trajectoryIntentStatus = BitwiseUtils.extractBit(data, 8, 1);
        TrajectoryIntent trajectory = new TrajectoryIntent();
        trajectory.setIsHasSubField1((data & 0x80) > 0);
        trajectory.setIsHasSubField2((data & 0x40) > 0);

        //int isStillHasData = BitwiseUtils.extractBit(data, 1, 1);
        boolean extention = (data & 0x01) > 0;
        if (extention == false) {
            return trajectory;
        }

        if (trajectory.isIsHasSubField1()) {
            data = bytes[index];
            index++;
            trajectory.setIsDataAvailable((data & 0x80) > 0);
            trajectory.setIsDataValid((data & 0x40) > 0);
            extention = (data & 0x01) > 0;
        }

        if (extention) {
            trajectory.setRepetitionFactor(bytes[index] & 0xFF);
            index++;

            data = bytes[index];
            index++;
            trajectory.setIsTcpNumberAvailable((data & 0x80) > 0);
            trajectory.setIsTcpCompliance((data & 0x40) > 0);
            trajectory.setTcpNumber(data & 0x3F);

            // get Altitude 2 byte in 2 's complement
            byte[] altitudeBytes = new byte[]{bytes[index], bytes[index + 1]};
            index += 2;
            int value = BitwiseUtils.convertFrom2sComplementNumber(altitudeBytes);
            trajectory.setAltitude(value * 10);

            // latitude
            byte[] latitudeBytes = new byte[]{bytes[index], bytes[index + 1], bytes[index + 2]};
            index += 3;
            value = BitwiseUtils.convertFrom2sComplementNumber(latitudeBytes);
            trajectory.setLatitude((float) (value * 2.145767 * 0.00001));

            // longtitude
            byte[] longtitudeBytes = new byte[]{bytes[index], bytes[index + 1], bytes[index + 2]};
            index += 3;
            value = BitwiseUtils.convertFrom2sComplementNumber(longtitudeBytes);
            trajectory.setLongtitle((float) (value * 2.145767 * 0.00001));

            data = bytes[index];
            index++;
            trajectory.setPointType(data >> 4 & 0x0f);
            trajectory.setTurnDirection(data >> 2 & 0x03);
            trajectory.setIsTurnRadiusAvailable((data & 0x02) > 0);
            trajectory.setIsTimeOverPointAvailable((data & 0x01) > 0);

            value = bytes[index] & 0xFF;
            index++;
            value = value << 8 | (bytes[index] & 0xFF);
            index++;
            value = value << 8 | (bytes[index] & 0xFF);
            index++;
            trajectory.setTimeOverPoint(value);


            value = bytes[index] & 0xFF;
            index++;
            value = value << 8 | (bytes[index] & 0xFF);
            index++;
            trajectory.setTcpTurnRadius(value * 0.01f);
        }
        return trajectory;
    }
    
    public void skipTrajectoryIntent(byte[] bytes) {
        
        byte data = bytes[index++];
        
        boolean isHasSubField1 =(data & 0x80) > 0;
        boolean extention = (data & 0x01) > 0;

        if (extention == false) return ;

        if (isHasSubField1) {
            data = bytes[index++];
            extention = (data & 0x01) > 0;
        }
        
        if (extention) index+=16;
    }
    
    public AircraftOperationalStatus getAircraftOperationStatus(byte[] bytes) {
        AircraftOperationalStatus operationStatus = new AircraftOperationalStatus();
        
        byte byteHigh = bytes[index];
        index++;
        boolean bValue = (byteHigh & 0x80) > 0;
        operationStatus.setIsResolutionAdvisoryActive(bValue);
        
        int iValue = byteHigh >> 5 & 0x03;
        operationStatus.setTargetTrajectoryChangeReportCapability(iValue);
        
        bValue = (byteHigh & 0x10) > 0;
        operationStatus.setIsTargetStateReportCapability(bValue);
        
        bValue = (byteHigh & 0x08) > 0;
        operationStatus.setIsAirReferencedVelocityReportCapability(bValue);
        
        bValue = (byteHigh & 0x04) > 0;
        operationStatus.setIsCockpitDisplayOfTrafficInformationAirborne(bValue);
        
        bValue = (byteHigh & 0x02) > 0;
        operationStatus.setIsTCASSystemStatus(bValue);
        
        bValue = (byteHigh & 0x01) > 0;
        operationStatus.setIsSingleAntenna(bValue);
        
        return operationStatus;
    }
    
    public SurfaceCapabilitiesAndCharacterics getSurfaceCapabilitiesAndCharacterics(byte [] bytes) {
        SurfaceCapabilitiesAndCharacterics surfaceCapobilityAndCharaterics = new SurfaceCapabilitiesAndCharacterics();
        
        byte byteHigh= bytes[index];
        index++;
        
        boolean bValue = (byteHigh & 0x20) > 0;
        surfaceCapobilityAndCharaterics.setPositionOffSetApplied(bValue);
        
        bValue = (byteHigh & 0x10) > 0;
        surfaceCapobilityAndCharaterics.setCockpitDisplayOfTrafficInformationSurface(bValue);
        
        bValue = (byteHigh & 0x08) > 0;
        surfaceCapobilityAndCharaterics.setB2low(bValue);
        
        bValue = (byteHigh & 0x04) > 0;
        surfaceCapobilityAndCharaterics.setReceivingATCServices(bValue);
        
        bValue = (byteHigh & 0x02) > 0;
        surfaceCapobilityAndCharaterics.setIndent(bValue);
        
        bValue = (byteHigh & 0x01) > 0;
        if (!bValue) return surfaceCapobilityAndCharaterics;
        
        byteHigh = bytes[index];
        index++;
        
        int iValue = byteHigh & 0x0F;
        surfaceCapobilityAndCharaterics.setLengthWidth(iValue);
        return surfaceCapobilityAndCharaterics;
    }
    
    public ModeSMBData getModeSMDData(byte [] bytes) {
        ModeSMBData modeSData = new ModeSMBData();
        modeSData.setRepetitionFactor((short) (bytes[index] & 0xFF));
        index++;
        
        modeSData.setMessage(new byte[]{
            bytes[index], 
            bytes[index + 1], 
            bytes[index + 2], 
            bytes[index + 3], 
            bytes[index + 4], 
            bytes[index + 5], 
            bytes[index + 6]});
        index += 7;
        
        byte byteHigh = bytes[index];
        index++;
        short sValue = (short) (byteHigh >> 4 & 0x0F);
        modeSData.setBDS1(sValue);
        
        sValue = (short) (byteHigh & 0x0F);
        modeSData.setBDS2(sValue);
        
        return modeSData;
    }
    
    public ASCASResolutionAdvisoryReport getASCASResolutionAdvisorReport(byte[] bytes) {
        ASCASResolutionAdvisoryReport ascasResolutionAvisoryReport = new ASCASResolutionAdvisoryReport();
        
        byte byteHigh = bytes[index];
        index++;
        short sValue = (short) (byteHigh >> 3 & 0x1F);
        ascasResolutionAvisoryReport.setMessageType(sValue);
        
        sValue = (short) (byteHigh & 0x0F);
        ascasResolutionAvisoryReport.setMessageSubType(sValue);
        
        byteHigh = bytes[index];
        index++;
        byte byteLow = bytes[index];
        index++;
        sValue = (short) ((byteHigh << 6) | (byteLow >> 2));
        ascasResolutionAvisoryReport.setActiveResolutionAdvisories(sValue);
        
        byteHigh = bytes[index];
        index++;
        
        sValue = (short) (((byteLow & 0x03) << 2) | (byteHigh >> 6));
        ascasResolutionAvisoryReport.setRACRecord(sValue);
        
        boolean bValue = (byteHigh & 20) > 0;
        ascasResolutionAvisoryReport.setrATerminated(bValue);
        
        bValue = (byteHigh & 10) > 0;
        ascasResolutionAvisoryReport.setMultipleThreatEncounter(bValue);
        
        sValue = (short) (byteHigh >> 2 & 0x03);
        ascasResolutionAvisoryReport.setThreatTypeIndicator(sValue);
        
        int iValue = ByteBuffer.wrap(new byte[]{(byte) (byteHigh & 0x03), bytes[index], bytes[index + 1], bytes[index + 2]}).getInt();
        index += 3;
        
        ascasResolutionAvisoryReport.setThreatIdentityData(iValue);
        
        return ascasResolutionAvisoryReport;
    }

    public Double getMagneticHeading(byte[] bytes) {
        int heading = bytes[index] & 0xFF;
        index++;

        heading = heading << 8 | (bytes[index] & 0xFF);
        index++;
        
        return heading * 360 / Math.pow(2, 16); 
    }

    public GroundVector getGroundVector(byte[] bytes) {
        byte[] groundSpeed = new byte[]{bytes[index], bytes[index + 1]};
        index += 2;
        int intValue = BitwiseUtils.convertFrom2sComplementNumber(groundSpeed);

        GroundVector groundVector = new GroundVector();
        groundVector.setValue((float) (intValue * Math.pow(2, -14)));

        intValue = ((bytes[index] & 0xFF) << 8) | (bytes[index + 1] & 0xFF);
        index += 2;
        groundVector.setTrackAngle(intValue * 0.0055f);

        return groundVector;
    }

    public String getTargetIden(byte[] bytes) {
        StringBuilder builder = new StringBuilder();

        byte[] byts = new byte[]{bytes[index], bytes[index + 1], bytes[index + 2]};
        index += 3;
        builder.append(getCode(byts));

        byts = new byte[]{bytes[index], bytes[index + 1], bytes[index + 2]};
        index += 3;
        builder.append(getCode(byts));

        // TargetIdentification target = new TargetIdentification();
        // target.setValue(builder.toString());
        return builder.toString();
    }

    public RateOfTurn getRateOfTurn(byte[] bytes) {
        //int readCount = 1;
        byte byte1 = bytes[index];
        index++;
        
        short turnIndicator = (short) ((byte1 >> 6) & 0x03); // 1100 0000
        
        RateOfTurn rate = new RateOfTurn();
        rate.setTurningIndicator(turnIndicator);
        
        int fxBit = byte1 & 0x01;
        if (fxBit == 0) return rate;

        int count = 0;
        while (fxBit == 1) {
            byte tempByte = bytes[index];
            fxBit = tempByte & 0x01;
            count++;
        }
        
        byte[] bts = new byte[count];
        System.arraycopy(bytes, index, bts, 0, count);
        int intValue = BitwiseUtils.convertFrom2sComplementNumber7bitPerByte(bts);
        rate.setRate(intValue/4);
        return rate;
    }

    public FinalStateSelectedAltitude getFinalState(byte[] bytes) {
        byte data = bytes[index];
        index++;

        FinalStateSelectedAltitude state = new FinalStateSelectedAltitude();

        state.setIsManageVerticalModeActive((data & 0x80) > 0);
        state.setIsAltitudeHoldModeActive((data & 0x80) > 0);
        state.setIsAltitudeHoldModeActive((data & 0x20) > 0);


        byte[] byts = new byte[]{(byte) (data & 0x1F), bytes[index + 1]};
        index++;
        state.setAltitude(BitwiseUtils.convertFrom2sComplementNumber(byts) * 25);
        return state;
    }

    private String getCode(byte[] byts) {
        /*
        if (byts.length != 3) return "";
        StringBuilder builder = new StringBuilder();
        int byt = byts[0];
        int code = byt >> 2 & 0x3F;
        if (code != 0) builder.append(characters[code]);

        code = byt & 0x03;
        code = code << 4 | (byts[1] >> 4 & 0x0F);
        if (code != 0) builder.append(characters[code]);

        code = byts[1] & 0x0F;
        // byt = byts[2];
        code = code << 2 | (byts[2] >> 6 & 0x03);
        if (code != 0) builder.append(characters[code]);

        code = byts[2] & 0x3F;
        if (code != 0) builder.append(characters[code]);
        return builder.toString();
        */
        
        // String str = "";
        // if (byts.length != 3) return "";
        StringBuilder builder = new StringBuilder();
        
        // int byt = byts[0];
        int code = byts[0] >> 2 & 0x3F;
        if (code != 0) builder.append(getChar(code));

        code = (byts[0] & 0x03) << 4 | (byts[1] >> 4 & 0x0F);
        // code = code << 4 | (byts[1] >> 4 & 0x0F);
        if (code != 0) builder.append(getChar(code));

        // code = byts[1] & 0x0F;
        // byt = byts[2];
        // code = code << 2 | (byts[2] >> 6 & 0x03);
        
        code = (byts[1] & 0x0F) << 2 | (byts[2] >> 6 & 0x03);
        if (code != 0) builder.append(getChar(code));
        //str += characters[code];

        code = byts[2] & 0x3F;
        if (code != 0) builder.append(getChar(code));
        // str += characters[code];
        return builder.toString();
    }
    
    private static char getChar(int i) {
        
        if (i <=26) return (char) (i+64);
        if (i==32) return ' ';
        return (char) i;
    }

    public static Decryptor getInstance() {
        if (instance == null) {
            instance = new Decryptor();
        }
        return instance;
    }
    
    public void getSurfaceCapabilitiesAndCharacteristics(byte[] bytes) {
        index+=9;
    }
    
    private DataAges getDataAges(byte[] bytes) {
        DataAges dataAges = new DataAges();
        
        final int maxValue = 28;
        int bIndex = 0;
        boolean [] bValues = new boolean[maxValue];
        boolean isEnd = false;
        
        while (!isEnd){
            byte byteHigh = bytes[index++];
            bValues[bIndex++] = (byteHigh & 0x80) > 0;
            bValues[bIndex++] = (byteHigh & 0x40) > 0;
            bValues[bIndex++] = (byteHigh & 0x20) > 0;
            bValues[bIndex++] = (byteHigh & 0x10) > 0;
            bValues[bIndex++] = (byteHigh & 0x08) > 0;
            bValues[bIndex++] = (byteHigh & 0x04) > 0;
            bValues[bIndex++] = (byteHigh & 0x02) > 0;
            isEnd = (byteHigh & 0x01) == 0;
        } 
        
        for (int i = 0; i < maxValue; i++) {
            if (!bValues[i]) continue;
            switch(i) {
                case 0: 
                    dataAges.setAircraftOperationalStatusAge((bytes[index++] & 0xFF) * 0.1);
                    break;
                case 1: 
                    dataAges.setTargetReportDescriptorAge((bytes[index++] & 0xFF) * 0.1);
                    break;
                case 2: 
                    dataAges.setMode3ACodeAge((bytes[index++] & 0xFF) * 0.1);
                    break;
                case 3: 
                    dataAges.setQualityIndicatorsAge((bytes[index++] & 0xFF) * 0.1);
                    break;
                case 4: 
                    dataAges.setTrajectoryIntentAge((bytes[index++] & 0xFF) * 0.1);
                    break;
                case 5: 
                    dataAges.setMessageAmplitudeAge((bytes[index++] & 0xFF) * 0.1);
                    break;
                case 6: 
                    dataAges.setGeometricHeightAge((bytes[index++] & 0xFF) * 0.1);
                    break;
                case 7: 
                    dataAges.setFlightLevelAge((bytes[index++] & 0xFF) * 0.1);
                    break;
                case 8: 
                    dataAges.setIntermediateStateSelectedAltitudeAge((bytes[index++] & 0xFF) * 0.1);
                    break;
                case 9: 
                    dataAges.setFinalStateSelectedAltitudeAge((bytes[index++] & 0xFF) * 0.1);
                    break;
                case 10: 
                    dataAges.setAirSpeedAge((bytes[index++] & 0xFF) * 0.1);
                    break;
                case 11: 
                    dataAges.setTrueAirSpeedAge((bytes[index++] & 0xFF) * 0.1);
                    break;
                case 12: 
                    dataAges.setMagneticHeadingAge((bytes[index++] & 0xFF) * 0.1);
                    break;
                case 13: 
                    dataAges.setBarometricVerticalRateAge((bytes[index++] & 0xFF) * 0.1);
                    break;
                case 14: 
                    dataAges.setGeometricVerticalRateAge((bytes[index++] & 0xFF) * 0.1);
                    break;
                case 15: 
                    dataAges.setGroundVectorAge((bytes[index++] & 0xFF) * 0.1);
                    break;
                case 16: 
                    dataAges.setTrackAngleRateAge((bytes[index++] & 0xFF) * 0.1);
                    break;
                case 17: 
                    dataAges.setTargetIdentificationAge((bytes[index++] & 0xFF) * 0.1);
                    break;
                case 18: 
                    dataAges.setTargetStatusAge((bytes[index++] & 0xFF) * 0.1);
                    break;
                case 19: 
                    dataAges.setMetInformationAge((bytes[index++] & 0xFF) * 0.1);
                    break;
                case 20: 
                    dataAges.setRollAngleAge((bytes[index++] & 0xFF) * 0.1);
                    break;
                case 21: 
                    dataAges.setaCASResolutionAdvisoryAge((bytes[index++] & 0xFF) * 0.1);
                    break;
                case 22: 
                    dataAges.setSurfaceCapabilitiesAndCharacteristics((bytes[index++] & 0xFF) * 0.1);
                    break;
            }
        }
        return dataAges;
    }
    
    private void skipDataAges(byte[] bytes) {
        boolean isEnd = false;
        int byteCounting = 0;
        while (!isEnd){
            byte byteHigh = bytes[index++];
            if ((byteHigh & 0x80) > 0) byteCounting++;
            if ((byteHigh & 0x40) > 0) byteCounting++;
            if ((byteHigh & 0x20) > 0) byteCounting++;
            if ((byteHigh & 0x10) > 0) byteCounting++;
            if ((byteHigh & 0x08) > 0) byteCounting++;
            if ((byteHigh & 0x04) > 0) byteCounting++;
            if ((byteHigh & 0x02) > 0) byteCounting++;
            isEnd = (byteHigh & 0x01) == 0;
        } 
        
        index += byteCounting;
    }
    
    @Override
    public List<InternalMessage> extracInternalMesasge(byte[] bytes, float version){
        
        index = 0;
        categoryIndex = bytes[0] & 0xFF;
        if (categoryIndex != 21) return null;
        
        length = bytes[1];
        length = length << 8 | bytes[2] & 0xFF;
        index += 3;

        List<InternalMessage> messages = new ArrayList<>();
        
        while (index < length) {
            
            int firstIndex = index;
            boolean [] header = getHeader(bytes);
            int [] fields = new int[header.length];
            
            InternalMessage message = new InternalMessage(version);
            message.setHeaderLength(index - firstIndex);
            
            for (int i = 0; i < ITEM_NUMBER; i++) {
                if (!header[i]) continue;
                switch (i) {
                    case 0: // DataSource Indentification
                        DataSourceIdentification sourceIdentification = getDataSourceIden(bytes);
                        message.setSac(sourceIdentification.getSac());
                        message.setSic(sourceIdentification.getSic());
                        fields[0] = 2;
                        break;
                    case 1: // Target Report Descriptor
                        fields[1] = index;
                        message.setTargetDescriptor(getTargetReportDescriptor(bytes));
                        fields[1] = index - fields[1];
                        break;
                    case 2: // Track Number
                        index += 2;
                        fields[2] = 2;
                        break;
                    case 3: // Service Identification
                        fields[3] = 1;
                        index += 1;
                        break;
                    case 4: // Time of Applicability for Position
                        fields[4] = 3;
                        index += 3;
                        break;
                    case 5: // Position in WGS-84 co-ordinates
                        fields[5] = index;
                        message.setPosition(getPosition(bytes));
                        fields[5] = index - fields[5];
                        break;
                    case 6: // Position in WGS-84 co-ordinates, high res.
                        // index += 8;
                        fields[6] = index;
                        message.setPosition(getHighResolutionPosition(bytes));
                        fields[6] = index - fields[6];
                        break;
                    case 7: // Time of Applicability for Velocity
                        fields[7] = 3;
                        index += 3;
                        break;
                    case 8: // Air Speed
                        fields[8] = 2;
                        index += 2;
                        break;
                    case 9: // True Air Speed
                        fields[9] = 2;
                        index += 2;
                        break;
                    case 10: // Target Address
                        fields[10] = index;
                        message.setTargetAddress((Integer) getTargetAddress(bytes));
                        fields[10] = index - fields[10];
                        break;
                    case 11: // Time of Message Reception of Position
                        message.setTimeOfMessageReceptionOfPosition(getTime(bytes));
                        fields[11] = 3;
                        // index += 3;
                        break;
                    case 12: // Time of Message Reception of Position-High Precision
                        fields[12] = 4;
                        index += 4;
                        break;
                    case 13: // Time of Message Reception of Velocity
                        fields[13] = 3;
                        index += 3;
                        break;
                    case 14: // Time of Message Reception of Velocity-High Precision
                        fields[14] = 4;
                        index += 4;
                        break;
                    case 15: // Geometric Height
                        fields[15] = 2;
                        index += 2;
                        break;
                    case 16: // Quality Indicators
                        fields[16] = index;
                        message.setQualityIndicator(getQualityIndicators(bytes));
                        fields[16] = index - fields[16];
                        break;
                    case 17: // MOPS Version
                        fields[17] = 1;
                        index += 1;
                        break;
                    case 18: // Mode 3/A Code
                        fields[18] = 2;
                        index += 2;
                        break;
                    case 19: // Roll Angle
                        fields[19] = 2;
                        index += 2;
                        // message.setRollAngle(getRollAngle(bytes));
                        break;
                    case 20: // Flight Level
                        fields[20] = index;
                        //index += 2;
                        message.setFlightLevel(getFlighLevel(bytes));
                        fields[20] = index-fields[20];
                        break;
                    case 21: // Magnetic Heading
                        fields[21] = 2;
                        index += 2;
                        break;
                    case 22: // Target Status
                        fields[22] = 1;
                        index += 1;
                        break;
                    case 23: // Barometric Vertical Rate
                        fields[23] = 2;
                        index += 2;
                        break;
                    case 24: // Geometric Vertical Rate
                        fields[24] = 2;
                        index += 2;
                        break;
                    case 25: // Airborne Ground Vector
                        fields[25] = 4;
                        index += 4;
                        break;
                    case 26: // Track Angle Rate
                        fields[26] = 2;
                        index += 2;
                        break;
                    case 27: // Time of Report Transmission
                        fields[27] = 3;
                        index += 3;
                        break;
                    case 28: // Target Identification
                        fields[28] = 6;
                        message.setTargetIdentification(getTargetIden(bytes));
                        break;
                    case 29: // Emitter Category
                        fields[29] = 1;
                        index += 1;
                        break;
                    case 30: // Met Information
                        // System.out.println("Met Information");
                        fields[30] = index;
                        byte hightByte = bytes[index];
                        index++;
                        // get header
                        if ((hightByte & 0x80) > 0) {
                            index++;
                        }
                        if ((hightByte & 0x40) > 0) {
                            index++;
                        }
                        if ((hightByte & 0x20) > 0) {
                            index++;
                        }
                        if ((hightByte & 0x10) > 0) {
                            index++;
                        }
                        fields[30] = index- fields[30];
                        break;
                    case 31: // Selected Altitude
                        // System.out.println("Selected Altitude");
                        fields[31] = 2;
                        index += 2;
                        break;
                    case 32: // Final State Selected Altitude
                        // System.out.println("Final State Selected Altitude");
                        fields[32] = 2;
                        index += 2;
                        break;
                    case 33: // Trajectory Intent
                        // System.out.println("Trajectory Intent");
                        fields[33] = index;
                        this.skipTrajectoryIntent(bytes);
                        fields[33] = index - fields[33];
                        break;
                    case 34: // Service Management
                        // System.out.println("Service Management");
                        fields[34] = 1;
                        index += 1;
                        break;
                    case 35: // Aircraft Operational Status
                        // System.out.println("Aircraft Operational Status");
                        fields[35] = 1;
                        index += 1;
                        break;
                    case 36: // Surface Capabilities and Characteristics
                        // System.out.println("Surface Capabilities and Characteristics");
                        fields[36] = index;
                        byte byteHigh = bytes[index++];
                        boolean bValue = (byteHigh & 0x01) > 0;
                        if (bValue) {
                            index++;
                        }
                        fields[36] = index - fields[36];
                        break;
                    case 37: // Message Amplitude
                        // System.out.println("Message Amplitude");
                        fields[37] = 1;
                        index += 1;
                        break;
                    case 38: // Mode S MB Data
                        // System.out.println("Mode S MB Data");
                        fields[38] = 9;
                        index += 9;
                        break;
                    case 39: // ACAS Resolution Advisory Report
                        // System.out.println("ACAS Resolution Advisory Report");
                        fields[39] = 7;
                        index += 7;
                        break;
                    case 40: // Receiver ID
                        // System.out.println("Receiver ID");
                        fields[40] = 1;
                        index += 1;
                        break;
                    case 41: // Data Ages
                        // System.out.println("Data Ages");
                        fields[41] = index;
                        skipDataAges(bytes);
                        fields[41] = index - fields[41];
                        break;
                    case 42: // Not Used
                        break;
                    case 43: // Not Used
                        break;
                    case 44: // Not Used
                        break;
                    case 45: // Not Used
                        break;
                    case 46: // Not Used
                        break;
                    case 47: // Reserved Expansion Field
                        int reservedExpansionFieldLength = bytes[index] & 0xFF;
                        index += reservedExpansionFieldLength;
                        fields[47] = reservedExpansionFieldLength;
                        break;
                    case 48: // Special Purpose Field

                        int specialPurposeFieldLength = bytes[index] & 0xFF;
                        index += specialPurposeFieldLength;
                        fields[48] = specialPurposeFieldLength;
                        break;
                    default:
                        break;
                }
            }
            int contentLen = index - firstIndex;
            // System.out.println("[ " + firstIndex + " - " + index + " ] length: " + contentLen);
            byte[] contentsByte = new byte[contentLen + 3];
            System.arraycopy(bytes, firstIndex, contentsByte, 3, contentLen);
            contentsByte[0] = 21;
            contentsByte[1] = (byte) (contentLen >> 8);
            contentsByte[2] = (byte) (contentLen & 0xFF);
            message.setBytes(contentsByte);
            message.setHeaders(header);
            message.setFields(fields);
            messages.add(message);
        }

        return messages;
    }
}
