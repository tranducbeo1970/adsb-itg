/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asterix.cat21;

import com.attech.asterix.cat21.entities.AirSpeed;
import com.attech.asterix.cat21.entities.Altitude;
import com.attech.asterix.cat21.entities.BarometricVerticalRate;
import com.attech.asterix.cat21.entities.DataSourceIdentification;
import com.attech.asterix.cat21.entities.EmitterCategory;
import com.attech.asterix.cat21.entities.FigureOfMerit;
import com.attech.asterix.cat21.entities.FinalStateSelectedAltitude;
import com.attech.asterix.cat21.entities.FlightLevel;
import com.attech.asterix.cat21.entities.GeometricVerticalRate;
import com.attech.asterix.cat21.entities.GroundVector;
import com.attech.asterix.cat21.entities.IntermediateStateSelectedAltitude;
import com.attech.asterix.cat21.entities.LinkTechnology;
import com.attech.asterix.cat21.entities.MagneticHeading;
import com.attech.asterix.cat21.entities.MetInformation;
import com.attech.asterix.cat21.entities.Position;
import com.attech.asterix.cat21.entities.RateOfTurn;
import com.attech.asterix.cat21.entities.RollAngle;
import com.attech.asterix.cat21.entities.TargetAddress;
import com.attech.asterix.cat21.entities.TargetIdentification;
import com.attech.asterix.cat21.entities.TargetReportDescriptor;
import com.attech.asterix.cat21.entities.TargetStatus;
import com.attech.asterix.cat21.entities.TimeOfDay;
import com.attech.asterix.cat21.entities.TimeOfDayAccurary;
import com.attech.asterix.cat21.entities.TrajectoryIntent;
import com.attech.asterix.cat21.entities.TrueAirSpeed;
import com.attech.asterix.cat21.entities.VelocityAccuracy;
import com.attech.asterix.cat21.util.BitwiseUtils;
import com.attech.asterix.cat21.util.CharacterMap;
import exception.InvalidFormatException;

/**
 *
 * @author andh
 */
public class MessageDecryptor {
    
    private static MessageDecryptor instance;
    private static final char[] characters = CharacterMap.getCharacterMap();
    private static final byte [] masked = new byte[] { (byte) 0x80, (byte) 0x40, (byte) 0x20, (byte) 0x10, (byte) 0x08, (byte) 0x04, (byte) 0x02 };
    private static final short ITEM_NUMBER = 35;
    
    private boolean [] header;
    private int index;
    private int length;
    private short categoryIndex;
    
    public Message decrypt(byte[] bytes) throws InvalidFormatException {
        
        // index = 3;
        header = getHeader(bytes);
        
        Message message = new Message(bytes);
        for(int i = 0; i< ITEM_NUMBER; i++) {
            if (!header[i]) continue;
            
            switch(i) {
                case 0:
                    message.setSourceIden(getDataSourceIden(bytes));
                    break;
                case 1:
                    message.setTargetDescriptor(getTargetReportDescriptor(bytes));
                    break;
                case 2:
                    message.setTimeOfDay(getTimeOfDay(bytes));
                    break;
                case 3:
                    message.setPosition(getPosition(bytes));
                    break;
                case 4:
                    message.setTargetAddress(getTargetAddress(bytes));
                    break;
                case 5:
                    message.setAltitude(getAltitude(bytes));
                    break;
                case 6:
                    message.setFigure(getFigureOfMerit(bytes));
                    break;
                case 7:
                    message.setTechnology(getLinkTechnology(bytes));
                    break;
                case 8:
                    message.setRollAngle(getRollAngle(bytes));
                    break;
                case 9:
                    message.setFlightLevel(getFlighLevel(bytes));
                    break;
                case 10:
                    message.setAirSpeed(getAirSpeed(bytes));
                    break;
                case 11:
                    message.setTrueAirSpeed(getTrueAirSpeed(bytes));
                    break;
                case 12:
                    message.setMagneticHeading(getMagneticHeading(bytes));
                    break;
                case 13:
                    message.setBarometricVerticalRate(getBarometricVerticalRate(bytes));
                    break;
                case 14:
                    message.setGeometricVerticalRate(getGeometricVerticalRate(bytes));
                    break;
                case 15:
                    message.setGroundVector(getGroundVector(bytes));
                    break;
                case 16:
                    message.setRateOfTurn(getRateOfTurn(bytes));
                    break;
                case 17:
                    message.setTargetIdentification(getTargetIden(bytes));
                    break;
                case 18:
                    message.setVelocityAccuracy(getVelocityAccuracy(bytes));
                    break;
                case 19:
                    message.setTimeOfDayAccurary(getTimeOfDayAccurary(bytes));
                    break;
                case 20:
                    message.setTargetStatus(getTargetStatus(bytes));
                    break;
                case 21:
                    message.setEmitterCategory(getEmitterCategory(bytes));
                    break;
                case 22:
                    message.setMetInformation(getMetInformation(bytes));
                    break;
                case 23:
                    message.setIntermediateStateSelectedAltitude(getIntemediateState(bytes));
                    break;
                case 24:
                    message.setFinalStateSelectedAltitude(getFinalState(bytes));
                    break;
                case 25:
                    message.setTrajectoryIntent(getTrajectoryIntent(bytes));
                    break;
                default:
                    break;
            }
        }
        
        return message;
    }
    
    public boolean[] getHeader(byte[] bytes) {
        index = 3;
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
    
    public TimeOfDay getTimeOfDay(byte[] bytes) {
        TimeOfDay timeOfDay = new TimeOfDay();

        int time = bytes[index] & 0xFF;
        index++;
        
        time = time << 8 | (bytes[index] & 0xFF);
        index++;
        
        time = time << 8 | (bytes[index] & 0xFF);
        index++;
        
        timeOfDay.setValue((long) (time * 1000) / 128);
        return timeOfDay;
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
    
    public TargetAddress getTargetAddress(byte[] bytes) {
        int data = bytes[index] & 0xFF;
        index++;
        data = data << 8 | (int) (bytes[index] & 0xFF);
        index++;
        data = data << 8 | (int) (bytes[index] & 0xFF);
        index++;
        TargetAddress targetAddress = new TargetAddress();
        targetAddress.setValue(data);
        return targetAddress;
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

        short value = (short) ((byteHigh | 0xC0) >> 6);
        figureOfMerit.setAcas(value);

        value = (short) ((byteHigh | 0x30) >> 4);  // get bit 6 -> 5
        figureOfMerit.setMultipleNavigational(value);

        value = (short) ((byteHigh | 0x0C) >> 2);  // get bit 6 -> 5
        figureOfMerit.setDifferentialCorrection(value);

        byteHigh = bytes[index];
        index++;
        value = (short) ((byteHigh | 0x0F));  // get bit 4 -> 1
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
    
    public RollAngle getRollAngle(byte[] bytes) throws InvalidFormatException {
        byte[] bts = new byte[] { bytes[index], bytes[index + 1] };
        index+=2;
        
        int intValue = BitwiseUtils.convertFrom2sComplementNumber(bts);
        RollAngle rollAngle = new RollAngle();
        rollAngle.setValue(intValue * 0.01f);
        return rollAngle;
    }
    
    public FlightLevel getFlighLevel(byte[] bytes) throws InvalidFormatException {
        byte[] bts = new byte[]{bytes[index], bytes[index + 1]};
        index += 2;
        int value = BitwiseUtils.convertFrom2sComplementNumber(bts);
        FlightLevel flighLevet = new FlightLevel();
        flighLevet.setValue(value / 4);
        return flighLevet;
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
    
    public TrueAirSpeed getTrueAirSpeed(byte[] bytes) {
        int airSpeed = bytes[index] & 0xFF;
        index++;
        
        airSpeed = airSpeed << 8 | (bytes[index] & 0xFF);
        index++;
        
        TrueAirSpeed trueAirSpeed = new TrueAirSpeed();
        trueAirSpeed.setValue(airSpeed);
        return trueAirSpeed;
    }
    
    public MagneticHeading getMagneticHeading(byte[] bytes) throws InvalidFormatException {
        int heading = bytes[index] & 0xFF;
        index++;
        
        heading = heading << 8 | (bytes[index] & 0xFF);
        index++;
        
        MagneticHeading magneticHeading = new MagneticHeading();
        magneticHeading.setValue((Math.round((heading*360/Math.pow(2, 16))*10000))/10000);
        return magneticHeading;
    }
    
    public BarometricVerticalRate getBarometricVerticalRate(byte[] bytes) throws InvalidFormatException {
        byte[] bts = new byte[]{bytes[index], bytes[index + 1]};
        index += 2;
        int intValue = BitwiseUtils.convertFrom2sComplementNumber(bts);
        BarometricVerticalRate barometricVerticalRate = new BarometricVerticalRate();
        barometricVerticalRate.setValue(intValue * 6.25f);
        return barometricVerticalRate;
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
    
    public TargetIdentification getTargetIden(byte[] bytes) {
        StringBuilder builder = new StringBuilder();

        byte[] byts = new byte[]{bytes[index], bytes[index + 1], bytes[index + 2]};
        index+=3;
        builder.append(getCode(byts));

        byts = new byte[]{bytes[index], bytes[index + 1], bytes[index + 2]};
        index+=3;
        builder.append(getCode(byts));

        TargetIdentification target = new TargetIdentification();
        target.setValue(builder.toString());
        return target;
    }
    
    public VelocityAccuracy getVelocityAccuracy(byte[] bytes) {
        short value = (short) (bytes[index] & 0xFF);
        index++;
        VelocityAccuracy velo = new VelocityAccuracy();
        velo.setValue(value);
        return velo;
    }
    
    public TimeOfDayAccurary getTimeOfDayAccurary(byte[] bytes) throws InvalidFormatException {
        int intValue = bytes[index] & 0xFF;
        index++;
        TimeOfDayAccurary time = new TimeOfDayAccurary();
        time.setValue(intValue/256);
        return time;
    }
    
    public TargetStatus getTargetStatus(byte[] bytes) {
        short value = (short) (bytes[index] & 0xFF);
        index++;
        TargetStatus status = new TargetStatus();
        status.setValue(value);
        return status;
    }
    
    public EmitterCategory getEmitterCategory(byte[] bytes) {
        short value = (short) (bytes[index] & 0xFF);
        index++;
        EmitterCategory status = new EmitterCategory();
        status.setValue(value);
        return status;
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
    
    public static MessageDecryptor getInstance() {
        if (instance == null) instance = new MessageDecryptor();
        return instance;
    }
}
