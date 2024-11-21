/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asterix.cat21.v023;

import com.attech.asterix.cat21.entities.AirSpeed;
import com.attech.asterix.cat21.entities.Altitude;
import com.attech.asterix.cat21.entities.DataSourceIdentification;
import com.attech.asterix.cat21.entities.FigureOfMerit;
import com.attech.asterix.cat21.entities.FinalStateSelectedAltitude;
import com.attech.asterix.cat21.entities.GeometricVerticalRate;
import com.attech.asterix.cat21.entities.GroundVector;
import com.attech.asterix.cat21.entities.IntermediateStateSelectedAltitude;
import com.attech.asterix.cat21.entities.LinkTechnology;
import com.attech.asterix.cat21.entities.MetInformation;
import com.attech.asterix.cat21.entities.Position;
import com.attech.asterix.cat21.entities.RateOfTurn;
import com.attech.asterix.cat21.entities.TargetReportDescriptor;
import com.attech.asterix.cat21.entities.TimeOfDayAccurary;
import com.attech.asterix.cat21.entities.TrajectoryIntent;
import com.attech.asterix.cat21.util.BitwiseUtils;
import com.attech.asterix.cat21.util.CharacterMap;
import exception.InvalidFormatException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author andh
 */
public class V023Decryptor {
    
    private static V023Decryptor instance;
    private static final char[] characters = CharacterMap.getCharacterMap();
    private static final byte [] masked = new byte[] { (byte) 0x80, (byte) 0x40, (byte) 0x20, (byte) 0x10, (byte) 0x08, (byte) 0x04, (byte) 0x02 };
    private static final short ITEM_NUMBER = 35;
    
    private boolean [] header;
    private int index;
    private int length;
    private int categoryIndex;
    
    public List<V023Message> decrypt(byte[] bytes) throws InvalidFormatException {
        index = 0;
        categoryIndex = bytes[0] & 0xFF;
        if (categoryIndex != 21) {
            return null;
        }
        length = bytes[1] & 0xFF;
        length = length << 8 | (bytes[2] & 0xFF);
        index += 3;
        List<V023Message> messages = new ArrayList<>();

        while (index < length) {
            // index = 3;
            header = getHeader(bytes);

            V023Message message = new V023Message(bytes);
            
            for (int i = 0; i < ITEM_NUMBER; i++) {
                if (!header[i]) {
                    continue;
                }

                // System.out.println("Index: " + index);
                
                switch (i) {
                    case 0:
                        // System.out.println("DataSource Indentification");
                        message.setSourceIden(getDataSourceIden(bytes));
                        break;
                    case 1:
                        // System.out.println("TargetDescriptor");
                        message.setTargetDescriptor(getTargetReportDescriptor(bytes));
                        break;
                    case 2:
                        // System.out.println("TimeOfDa");
                        message.setTimeOfDay(getTimeOfDay(bytes));
                        break;
                    case 3:
                        // System.out.println("Position");
                        message.setPosition(getPosition(bytes));
                        break;
                    case 4:
                        // System.out.println("TargetAddress");
                        message.setTargetAddress(getTargetAddress(bytes));
                        break;
                    case 5:
                        // System.out.println("Altitude");
                        message.setAltitude(getAltitude(bytes));
                        break;
                    case 6:
                        // System.out.println("FigureOfMerit");
                        message.setFigure(getFigureOfMerit(bytes));
                        break;
                    case 7:
                        // System.out.println("Technology");
                        message.setTechnology(getLinkTechnology(bytes));
                        break;
                    case 8:
                        // System.out.println("RollAngle");
                        message.setRollAngle(getRollAngle(bytes));
                        break;
                    case 9:
                        // System.out.println("FlightLevel");
                        message.setFlightLevel(getFlighLevel(bytes));
                        break;
                    case 10:
                        // System.out.println("AirSpeed");
                        message.setAirSpeed(getAirSpeed(bytes));
                        break;
                    case 11:
                        // System.out.println("TrueAirSpeed");
                        message.setTrueAirSpeed(getTrueAirSpeed(bytes));
                        break;
                    case 12:
                        // System.out.println("MagneticHeading");
                        message.setMagneticHeading(getMagneticHeading(bytes));
                        break;
                    case 13:
                        // System.out.println("BarometricVerticalRate");
                        message.setBarometricVerticalRate(getBarometricVerticalRate(bytes));
                        break;
                    case 14:
                        // System.out.println("GeometricVerticalRate");
                        message.setGeometricVerticalRate(getBarometricVerticalRate(bytes));
                        break;
                    case 15:
                        // System.out.println("GroundVector");
                        message.setGroundVector(getGroundVector(bytes));
                        break;
                    case 16:
                        // System.out.println("RateOfTurn");
                        message.setRateOfTurn(getRateOfTurn(bytes));
                        break;
                    case 17:
                        // System.out.println("TargetIdentification");
                        message.setTargetIdentification(getTargetIden(bytes));
                        break;
                    case 18:
                        // System.out.println("VelocityAccuracy");
                        message.setVelocityAccuracy(getVelocityAccuracy(bytes));
                        break;
                    case 19:
                        // System.out.println("TimeOfDayAccurary");
                        message.setTimeOfDayAccurary(getTimeOfDayAccurary(bytes));
                        break;
                    case 20:
                        // System.out.println("TargetStatus");
                        message.setTargetStatus(getTargetStatus(bytes));
                        break;
                    case 21:
                        // System.out.println("EmitterCategory");
                        message.setEmitterCategory(getEmitterCategory(bytes));
                        break;
                    case 22:
                        // System.out.println("MetInformation");
                        message.setMetInformation(getMetInformation(bytes));
                        break;
                    case 23:
                        // System.out.println("IntermediateStateSelectedAltitude");
                        message.setIntermediateStateSelectedAltitude(getIntemediateState(bytes));
                        break;
                    case 24:
                        // System.out.println("FinalStateSelectedAltitude");
                        message.setFinalStateSelectedAltitude(getFinalState(bytes));
                        break;
                    case 25:
                        // System.out.println("TrajectoryIntent");
                        message.setTrajectoryIntent(getTrajectoryIntent(bytes));
                        break;
                    case 26:
                        break;
                    case 27:
                        break;
                    case 28:
                        break;
                    case 29:
                        break;
                    case 30:
                        break;
                    case 31:
                        break;
                    case 32:
                        break;
                    case 33:
                        // System.out.println("TrajectoryIntent");
                        int reservedExpansionFieldLength = bytes[index] & 0xFF;
                        index += reservedExpansionFieldLength;
                        break;
                    case 34:
                        // System.out.println("TrajectoryIntent");
                        int specialPurposeFieldLength = bytes[index] & 0xFF;
                        index += specialPurposeFieldLength;
                        break;
                    default:
                        break;
                }
            }
            messages.add(message);
            // System.out.println("Length: " + index);
        }
        return messages;
    }
    
    public boolean[] getHeader(byte[] bytes) {
        // index = 3;
        header = new boolean[ITEM_NUMBER];
        boolean isExtend = true;
        int bit = 0;
        int headerIndex = 0;
        while (isExtend) {
            
            byte currentByte = bytes[index];
            for (int i = 1; i < 8; i++) {
                bit = currentByte & masked[i - 1];
                if (bit == 0) {
                    continue;
                }
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
        source.setSac((short)(bytes[index] & 0xFF));
        index++;
        source.setSic((short)(bytes[index] & 0xFF));
        index++;
        return source;
    }
    
    public TargetReportDescriptor getTargetReportDescriptor(byte[] bytes) {
        TargetReportDescriptor targerDescriptor = new TargetReportDescriptor();
        byte byteHigh = bytes[index];
        targerDescriptor.setIsDifferentialCorrection((byteHigh & 0x80) > 0); // _isDifferentialCorrection = (byteHigh & 0x80) > 0; // Get bit [8]
        targerDescriptor.setIsGroundBitSet((byteHigh & 0x40) > 0); // this._isGroundBitSet = (byteHigh & 0x40) > 0; // Get bit [7]
        targerDescriptor.setIsSimulatedTargetReport((byteHigh & 0x20) > 0);  //this._isSimulatedTargetReport = (byteHigh & 0x20) > 0; // Get bit [6]
        targerDescriptor.setIsTestTarget((byteHigh & 0x10) > 0); // this._isTestTarget = (byteHigh & 0x10) > 0; // Get bit [5]
        targerDescriptor.setIsReportFromFieldMonitor((byteHigh & 0x08) > 0); // this._isReportFromFieldMonitor = (byteHigh & 0x08) > 0; // Get bit [4]
        targerDescriptor.setIsEquipementCapable((byteHigh & 0x04) > 0); // this._isEquipementCapable = (byteHigh & 0x04) > 0; // Get bit [3]
        targerDescriptor.setIsSpecialPositionIdentification((byteHigh & 0x02) > 0); // this._isSpecialPositionIdentification = (byteHigh & 0x02) > 0; // Get bit [2]

        index++;
        byteHigh = bytes[index];
        targerDescriptor.setAtpValue((short) ((byteHigh & 0xE0) >> 5)); //this._atpValue = (byteHigh & 0xE0) >> 5; // Get bit 5 -> 8
        targerDescriptor.setAltitudeReportingCapability((short) ((byteHigh & 0x18) >> 3)); // this._altitudeReportingCapability = (byteHigh & 0x18) >> 3; // Get bit 5 -> 4

        index++;

        return targerDescriptor;
    }
    
    public Double getTimeOfDay(byte[] bytes) {
       int value = ByteBuffer.wrap(new byte[]{0x00, bytes[index], bytes[index + 1], bytes[index + 2]}).getInt();
        index += 3;
        return (double) value/128;
    }
    
    public Position getPosition(byte[] bytes) {
        Position position = new Position();
        
        byte[] latBytes = new byte[] {bytes[index], bytes[index+1], bytes[index+2]};
        int value = BitwiseUtils.convertFrom2sComplementNumber(latBytes);
        position.setLatitude(value * 2.145767 * 0.00001);
        index+=3;
        
        latBytes = new byte[] {bytes[index], bytes[index+1], bytes[index+2]};
        value = BitwiseUtils.convertFrom2sComplementNumber(latBytes);
        position.setLongtitude(value * 2.145767 * 0.00001);
        index+=3;
        
        return position;
    }
    
    public Integer getTargetAddress(byte[] bytes) {
         ByteBuffer byffer = ByteBuffer.wrap(new byte[]{0x00, bytes[index], bytes[index + 1], bytes[index + 2]});
        index+=3;
        int value = byffer.getInt();
        return value;
    }
    
    public Altitude getAltitude(byte[] bytes) throws InvalidFormatException {
        byte[] byts = new byte[]{bytes[index], bytes[index + 1]};
        index+=2;
        
        int value = BitwiseUtils.convertFrom2sComplementNumber(byts);
        Altitude altitude = new Altitude();
        altitude.setValue(value * 6.25f);
        
        return altitude;
    }
    
    public FigureOfMerit getFigureOfMerit(byte[] bytes) {
        byte byteHigh = bytes[index];
        index++;

        FigureOfMerit figureOfMerit = new FigureOfMerit();

        int value = ((byteHigh | 0xC0) >> 6) & 0xFF;
        figureOfMerit.setAcas(value);

        value =  ((byteHigh | 0x30) >> 4);  // get bit 6 -> 5
        figureOfMerit.setMultipleNavigational(value);

        value = ((byteHigh | 0x0C) >> 2);  // get bit 6 -> 5
        figureOfMerit.setDifferentialCorrection(value);

        byteHigh = bytes[index];
        index++;
        value = ((byteHigh | 0x0F));  // get bit 4 -> 1
        figureOfMerit.setPositionAccuracy(value);
        return figureOfMerit;
    }
    
    public LinkTechnology getLinkTechnology(byte[] bytes) {
        byte byteHigh = bytes[index];
        index++;

        LinkTechnology linkTechnology = new LinkTechnology();

        boolean value = (byteHigh & 0x10) > 0;
        linkTechnology.setCockpitDisplayDti(value);

        value = (byteHigh & 0x08) > 0;
        linkTechnology.setModeSExtendSquitter(value);

        value = (byteHigh & 0x04) > 0;
        linkTechnology.setUat(value);

        value = (byteHigh & 0x02) > 0;
        linkTechnology.setVdlMode4(value);

        value = (byteHigh & 0x01) > 0;
        linkTechnology.setOtherTechnology(value);

        return linkTechnology;

    }
    
    public float getRollAngle(byte[] bytes) throws InvalidFormatException {
        byte[] bts = new byte[]{bytes[index], bytes[index + 1]};
        index += 2;
        int intValue = BitwiseUtils.convertFrom2sComplementNumber(bts);
        return intValue * 0.01f;
    }
    
    public Float getFlighLevel(byte[] bytes) throws InvalidFormatException {
        byte[] bts = new byte[]{bytes[index], bytes[index + 1]};
        index += 2;
        int value = BitwiseUtils.convertFrom2sComplementNumber(bts);
        return (float) value / 4;
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
        double speed = speedValue * ( speedUnit > 0 ? Math.pow(2, -14) : 0.001 );
        airSpeed.setValue(speed);
        
        return airSpeed;
    }
    
    public int getTrueAirSpeed(byte[] bytes) {
        int airSpeed = bytes[index] & 0xFF;
        index++;
        
        airSpeed = airSpeed << 8 | (bytes[index] & 0xFF);
        index++;
        return airSpeed;
    }
    
    public double getMagneticHeading(byte[] bytes) throws InvalidFormatException {
        int heading = bytes[index] & 0xFF;
        index++;

        heading = heading << 8 | (bytes[index] & 0xFF);
        index++;
        
        return heading * 360 / Math.pow(2, 16); 
    }
    
    public double getBarometricVerticalRate(byte[] bytes) throws InvalidFormatException {
        byte[] bts = new byte[]{bytes[index], bytes[index + 1]};
        index += 2;
        int intValue = BitwiseUtils.convertFrom2sComplementNumber(bts);
        return intValue * 6.25f;
    }
    
    public GeometricVerticalRate getGeometricVerticalRate(byte[] bytes) throws InvalidFormatException {
        byte[] bts = new byte[]{bytes[index], bytes[index + 1]};
        index+=2;
        int intValue = BitwiseUtils.convertFrom2sComplementNumber(bts);

        GeometricVerticalRate geometricVerticalRate = new GeometricVerticalRate();
        geometricVerticalRate.setValue(intValue * 6.25f);
        return geometricVerticalRate;
    }
    
    public GroundVector getGroundVector(byte[] bytes) {
        byte [] groundSpeed = new byte[] { bytes[index], bytes[index + 1] };
        index+=2;
        int intValue = BitwiseUtils.convertFrom2sComplementNumber(groundSpeed);
        
        GroundVector groundVector = new GroundVector();
        groundVector.setValue((float) (intValue * Math.pow(2, -14)));
        
        intValue = ((bytes[index] & 0xFF) << 8) | (bytes[index + 1] & 0xFF);
        index+=2;
        groundVector.setTrackAngle(intValue * 0.0055f);
        
        return groundVector;
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
    
    public String getTargetIden(byte[] bytes) {
        StringBuilder builder = new StringBuilder();

        byte[] byts = new byte[]{bytes[index], bytes[index + 1], bytes[index + 2]};
        index+=3;
        builder.append(getCode(byts));

        byts = new byte[]{bytes[index], bytes[index + 1], bytes[index + 2]};
        index+=3;
        builder.append(getCode(byts));
        return builder.toString();
    }
    
    public int getVelocityAccuracy(byte[] bytes) {
        short value = (short) (bytes[index] & 0xFF);
        index++;
        return value;
    }
    
    public double getTimeOfDayAccurary(byte[] bytes) throws InvalidFormatException {
        int intValue = bytes[index] & 0xFF;
        index++;
        TimeOfDayAccurary time = new TimeOfDayAccurary();
        return (double) intValue/256;
    }
    
    public int getTargetStatus(byte[] bytes) {
        short value = (short) (bytes[index] & 0xFF);
        index++;
        return value;
    }
    
    public int getEmitterCategory(byte[] bytes) {
        short value = (short) (bytes[index] & 0xFF);
        index++;
        return value;
    }
    
    public MetInformation getMetInformation(byte[] bytes) {
        int counting = 1;
        byte header = bytes[index];
        index++;
        
        MetInformation metInfo = new MetInformation();
        
        // get header
        metInfo.setIsHasWindSpeed((header & 0x80)  > 0);
        metInfo.setIsHasWindDirection((header & 0x40)  > 0);
        metInfo.setIsHasTemperature((header & 0x20)  > 0);
        metInfo.setIsHasTurbulence((header & 0x10)  > 0);
        
        // check extention bit
        int fxbit = (header & 0x01);
        if (fxbit == 0) return metInfo;
        
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
    
    public IntermediateStateSelectedAltitude getIntemediateState(byte[] bytes) {
        byte byt = bytes[index];
        index++;

        IntermediateStateSelectedAltitude state = new IntermediateStateSelectedAltitude();

        state.setIsSourceInformationProvide((byt & 0x80) > 0);
        state.setSource((short) (byt & 0x60));


        byte[] byts = new byte[]{(byte) (byt & 0x1F), bytes[index]};
        index++;
        state.setAltitude(BitwiseUtils.convertFrom2sComplementNumber(byts) * 25);
        return state;
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
    
    public TrajectoryIntent getTrajectoryIntent(byte[] bytes) {
        byte data = bytes[index];
        index++;
        
        // int trajectoryIntentStatus = BitwiseUtils.extractBit(data, 8, 1);
        TrajectoryIntent trajectory = new TrajectoryIntent();
        trajectory.setIsHasSubField1((data & 0x80) > 0);
        trajectory.setIsHasSubField2((data & 0x40) > 0);
        
        //int isStillHasData = BitwiseUtils.extractBit(data, 1, 1);
        boolean extention = (data & 0x01) > 0;
        if (extention == false) return trajectory;
     
        if (trajectory.isIsHasSubField1()) {
            data = bytes[index];
            index++;
            trajectory.setIsDataAvailable((data & 0x80) > 0);
            trajectory.setIsDataValid((data & 0x40) > 0);
            extention = (data & 0x01) > 0;
        }
        
        if (extention) {
            trajectory.setRepetitionFactor(bytes[index]&0xFF);
            index++;
            
            data = bytes[index];
            index++;
            trajectory.setIsTcpNumberAvailable((data & 0x80) > 0);
            trajectory.setIsTcpCompliance((data & 0x40) > 0);
            trajectory.setTcpNumber(data & 0x3F);
            
            // get Altitude 2 byte in 2 's complement
            byte[] altitudeBytes = new byte[]{bytes[index], bytes[index+1]};
            index+=2;
            int value = BitwiseUtils.convertFrom2sComplementNumber(altitudeBytes);
            trajectory.setAltitude(value*10);
            
            // latitude
            byte[] latitudeBytes = new byte[]{bytes[index], bytes[index + 1], bytes[index + 2]};
            index+=3;
            value = BitwiseUtils.convertFrom2sComplementNumber(latitudeBytes);
            trajectory.setLatitude((float)(value * 2.145767 * 0.00001));
            
            // longtitude
            byte[] longtitudeBytes = new byte[]{bytes[index], bytes[index + 1], bytes[index + 2]};
            index+=3;
            value = BitwiseUtils.convertFrom2sComplementNumber(longtitudeBytes);
            trajectory.setLongtitle((float)(value * 2.145767 * 0.00001));
         
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
            value = value << 8 | (bytes[index]& 0xFF);
            index++;
            trajectory.setTimeOverPoint(value);
            
            
            value = bytes[index] & 0xFF;
            index++;
            value = value << 8 | (bytes[index] & 0xFF);
            index++;
            trajectory.setTcpTurnRadius(value*0.01f);
        }
        return trajectory;
    }
    
    private String getCode(byte[] byts) {

        if (byts.length != 3) return "";
        
        StringBuilder builder = new StringBuilder();
        
        int byt = byts[0];
        int code = byt >> 2 & 0x3F;
        if (code != 0) builder.append(characters[code]);
        
        code = byt & 0x03;
        code = code << 4 | (byts[1] >> 4 & 0x0F);
        if (code != 0) builder.append(characters[code]);
        
        code = byts[1] & 0x0F;
        byt = byts[2];
        code = code << 2 | (byts[2] >> 6 & 0x03);
        if (code != 0) builder.append(characters[code]);
        
        code = byts[2] & 0x3F;
        if (code != 0) builder.append(characters[code]);
        
        return builder.toString();
    }
    
    public static V023Decryptor getInstance() {
        if (instance == null) instance = new V023Decryptor();
        return instance;
    }
}
