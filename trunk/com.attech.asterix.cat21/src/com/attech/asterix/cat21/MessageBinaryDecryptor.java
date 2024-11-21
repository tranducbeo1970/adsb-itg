/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asterix.cat21;

import com.attech.asterix.cat21.util.CharacterMap;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author andh
 */
public class MessageBinaryDecryptor {
    private static MessageBinaryDecryptor instance;
    private static final char[] characters = CharacterMap.getCharacterMap();
    private static final byte [] masked = new byte[] { (byte) 0x80, (byte) 0x40, (byte) 0x20, (byte) 0x10, (byte) 0x08, (byte) 0x04, (byte) 0x02 };
    private static final short ITEM_NUMBER = 35;
    
    private boolean [] header;
    private int index;
    private int length;
    private short categoryIndex;
    
    public BinaryMessage decrypt(byte[] bytes) {
        
        // index = 3;
        header = getHeader(bytes);
        byte[][] bts = new byte[header.length][];
        
        for(int i = 0; i< ITEM_NUMBER; i++) {
            if (!header[i]) continue;
            
            switch(i) {
                case 0:
                    bts[0] = getDataSourceIden(bytes);
                    break;
                case 1:
                    bts[1] = getTargetReportDescriptor(bytes);
                    break;
                case 2:
                    bts[2] = getTimeOfDay(bytes);
                    break;
                case 3:
                    bts[3] = getPosition(bytes);
                    break;
                case 4:
                    bts[4] = getTargetAddress(bytes);
                    break;
                case 5:
                    bts[5] = getAltitude(bytes);
                    break;
                case 6:
                    bts[6] = getFigureOfMerit(bytes);
                    break;
                case 7:
                    bts[7] = new byte[] { getLinkTechnology(bytes) };
                    break;
                case 8:
                    bts[8] = getRollAngle(bytes);
                    break;
                case 9:
                    bts[9] = getFlighLevel(bytes);
                    break;
                case 10:
                    bts[10] = getAirSpeed(bytes);
                    break;
                case 11:
                    bts[11] = getTrueAirSpeed(bytes);
                    break;
                case 12:
                    bts[12] = getMagneticHeading(bytes);
                    break;
                case 13:
                    bts[13] = getBarometricVerticalRate(bytes);
                    break;
                case 14:
                    bts[14] = getGeometricVerticalRate(bytes);
                    break;
                case 15:
                    bts[15] = getGroundVector(bytes);
                    break;
                case 16:
                    bts[16] = new byte[] {getRateOfTurn(bytes)};
                    break;
                case 17:
                    bts[17] = getTargetIden(bytes);
                    break;
                case 18:
                    bts[18] = new byte[] { getVelocityAccuracy(bytes) };
                    break;
                case 19:
                    bts[19] = new byte[] {getTimeOfDayAccurary(bytes)};
                    break;
                case 20:
                    bts[20] = new byte[] {getTimeOfDayAccurary(bytes)};
                    break;
                case 21:
                    bts[21] = new byte[] {getEmitterCategory(bytes)};
                    break;
                case 22:
                    bts[22] = getMetInformation(bytes);
                    break;
                case 23:
                    bts[23] = getIntemediateState(bytes);
                    break;
                case 24:
                    bts[24] = getFinalState(bytes);
                    break;
                case 25:
                    bts[25] = getTrajectoryIntent(bytes);
                    break;
                default:
                    break;
            }
        }
        BinaryMessage message = new BinaryMessage(bts, header);
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
    
    public byte[] getDataSourceIden(byte[] bytes) {
        byte[] temp = new byte[]{bytes[index], bytes[index + 1]};
        index += 2;
        return temp;
    }

    public byte[] getTargetReportDescriptor(byte[] bytes) {
        byte[] temp = new byte[]{bytes[index], bytes[index + 1]};
        index += 2;
        return temp;
    }
    
    public byte[] getTimeOfDay(byte[] bytes) {
        byte[] temp = new byte[]{bytes[index], bytes[index + 1], bytes[index + 2]};
        index+=3;
        return temp;
    }
    
    public byte[] getPosition(byte[] bytes) {
        byte[] temp = new byte[]{bytes[index], bytes[index + 1], bytes[index + 2], bytes[index + 3], bytes[index + 4], bytes[index + 5]};
        index+=6;
        return temp;
    }
    
    public byte[] getTargetAddress(byte[] bytes) {
        byte[] temp = new byte[]{bytes[index], bytes[index + 1], bytes[index + 2]};
        index+=3;
        return temp;
    }
    
    public byte[] getAltitude(byte[] bytes) {
        byte[] temp = new byte[]{bytes[index], bytes[index + 1]};
        index += 2;
        return temp;
    }

    public byte[] getFigureOfMerit(byte[] bytes) {
        byte[] temp = new byte[]{bytes[index], bytes[index + 1]};
        index += 2;
        return temp;
    }

    public byte getLinkTechnology(byte[] bytes) {
        index++;
        return bytes[index];
    }
    
    public byte[] getRollAngle(byte[] bytes)  {
        byte[] temp = new byte[]{bytes[index], bytes[index + 1]};
        index += 2;
        return temp;
    }
    
    public byte[] getFlighLevel(byte[] bytes) {
        byte[] temp = new byte[]{bytes[index], bytes[index + 1]};
        index += 2;
        return temp;
    }
    
    public byte[] getAirSpeed(byte[] bytes) {
        byte[] temp = new byte[]{bytes[index], bytes[index + 1]};
        index += 2;
        return temp;
    }
    
    public byte[] getTrueAirSpeed(byte[] bytes) {
        byte[] temp = new byte[]{bytes[index], bytes[index + 1]};
        index += 2;
        return temp;
    }
    
    public byte[] getMagneticHeading(byte[] bytes) {
        byte[] temp = new byte[]{bytes[index], bytes[index + 1]};
        index += 2;
        return temp;
    }
    
    public byte[] getBarometricVerticalRate(byte[] bytes) {
        byte[] temp = new byte[]{bytes[index], bytes[index + 1]};
        index += 2;
        return temp;
    }
    
    public byte[] getGeometricVerticalRate(byte[] bytes) {
        byte[] temp = new byte[]{bytes[index], bytes[index + 1]};
        index += 2;
        return temp;
    }
    
    public byte[] getGroundVector(byte[] bytes) {
        byte[] temp = new byte[]{bytes[index], bytes[index + 1], bytes[index + 2], bytes[index + 3]};
        index += 4;
        return temp;
    }
    
    public byte getRateOfTurn(byte[] bytes) {
        //int readCount = 1;
        byte byte1 = bytes[index];
        index++;
        return byte1;
    }
    
    public byte[] getTargetIden(byte[] bytes) {
        byte[] byts = new byte[]{bytes[index], bytes[index + 1], bytes[index + 2], bytes[index+3], bytes[index + 4], bytes[index + 5]};
        index+=6;
        return byts;
    }
    
    public byte getVelocityAccuracy(byte[] bytes) {
        byte byte1 = bytes[index];
        index++;
        return byte1;
    }
    
    public byte getTimeOfDayAccurary(byte[] bytes) {
        byte byte1 = bytes[index];
        index++;
        return byte1;
    }
    
    public byte getTargetStatus(byte[] bytes) {
        byte byte1 = bytes[index];
        index++;
        return byte1;
    }

    public byte getEmitterCategory(byte[] bytes) {
        byte byte1 = bytes[index];
        index++;
        return byte1;
    }
    
    public byte[] getMetInformation(byte[] bytes) {
        int counting = 1;
        byte firstByte = bytes[index];
        // index++;
        
        // get header
        boolean isHasWindSpeed = (firstByte & 0x80) > 0;
        boolean isHasWindDirection = (firstByte & 0x40) > 0;
        boolean isHasTemperature = (firstByte & 0x20) > 0;
        boolean isHasTurbulence = (firstByte & 0x10) > 0;
        
        // check extention bit
        int fxbit = (firstByte & 0x01);
        if (fxbit == 0) { 
            index++;
            return new byte[]{firstByte};
        }
        
        List<Byte> list = new ArrayList<Byte>();
        list.add(firstByte);
        
        if (isHasWindSpeed) counting += 2;
        if (isHasWindDirection) counting += 2;
        if (isHasTemperature) counting += 2;
        if (isHasTurbulence) counting++;

        byte [] bts = new byte[counting];
        System.arraycopy(bytes, index, bts, 0, counting);
        index += counting;
        return bts;
    }
    
    public byte[] getIntemediateState(byte[] bytes) {
        byte[] bts = new byte[]{bytes[index], bytes[index + 1]};
        index += 2;
        return bts;
    }
    
    public byte[] getFinalState(byte[] bytes) {
        byte[] bts = new byte[]{bytes[index], bytes[index + 1]};
        index += 2;
        return bts;
    }
    
    public byte[] getTrajectoryIntent(byte[] bytes) {
        byte data = bytes[index];
        int counting = 1;
        
        // int trajectoryIntentStatus = BitwiseUtils.extractBit(data, 8, 1);
        boolean isHasSubField1 = (data & 0x80) > 0;
        boolean isHasSubField2 =(data & 0x40) > 0;
        
        //int isStillHasData = BitwiseUtils.extractBit(data, 1, 1);
        boolean extention = (data & 0x01) > 0;
        if (!extention) {
            index++;
            return new byte[] { data };
        }
     
        if (isHasSubField1) {
            data = bytes[index + counting];
            counting++;
            extention = (data & 0x01) > 0;
        }
        
        if (extention && isHasSubField2) counting+=15;
        byte[] bts = new byte[counting];
        System.arraycopy(bytes, index, bts, 0, counting);
        index += counting;
        return bts;
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
    
    public static MessageBinaryDecryptor getInstance() {
        if (instance == null) instance = new MessageBinaryDecryptor();
        return instance;
    }
}
