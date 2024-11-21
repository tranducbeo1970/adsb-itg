/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.attech.cat48.v121;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.ByteBuffer;
import java.util.List;

/**
 *
 * @author andh
 */
public class Cat48Decryptor {
    public static final int NO_ERR = 0;
    public static final int ERR_OVER_LENGTH = 1;
    public static final int ERR_WRONG_CATEGORY = 2;
    private static final byte[] MASKED = new byte[]{(byte) 0x80, (byte) 0x40, (byte) 0x20, (byte) 0x10, (byte) 0x08, (byte) 0x04, (byte) 0x02};
    private static final short ITEM_NUMBER = 28;
    public static String ERROR;
    public static String ORIGIN_MESSAGE;
    
    public static int decodeHeader(byte[] bytes, int index, boolean [] header) {
        boolean isExtend = true;
        int bit;
        int headerIndex = 0;
        while (isExtend) {
            byte currentByte = bytes[index];
            // System.out.print(" " + Integer.toHexString(bytes[index] & 0xFF) + "  [" + Integer.toBinaryString(bytes[index]  & 0xFF) + "] ");
            for (int i = 1; i < 8; i++) {
                bit = currentByte & MASKED[i - 1];
                if (bit == 0) continue;
                int idx = headerIndex * 7 + i - 1;
                /*
                if (idx >= ITEM_NUMBER) {
                    System.out.println(String.format("Error while reading header: index is out of boundary of array size. (index: %s) (size: %s)", idx, ITEM_NUMBER));
                    return -1;
                }
                */
                header[idx] = true;
            }

            bit = currentByte & 0x01;
            isExtend = (bit != 0);
            index++;
            headerIndex++; // Increasing readed bytes
        }
        return headerIndex;
    }
    
    public static int decode(byte[] bytes, List<Cat48Message> messages) {
        ERROR = null;
        ORIGIN_MESSAGE = null;
        try {
            if (bytes.length <= 3) return ERR_OVER_LENGTH;
            int cate = bytes[0] & 0xFF;
            if (cate != 48) return ERR_WRONG_CATEGORY;

            int length = (((bytes[1] & 0xFF) << 8) | (bytes[2] & 0xFF));
            // System.out.println("Category: " + cate + "  Length: " + length);

            int index = 3;
            int count = 0;
            while (index < length) {
                Cat48Message message = new Cat48Message();
                count = decode(bytes, index, message);
                if (count <= 0) break;
                index += count;
                messages.add(message);
                // System.out.println(String.format("Length: %s Index: %s", length, index));
            }
            // System.out.println(String.format("Length: %s Index: %s", length, index));
            return NO_ERR;
        } catch (Exception ex) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            ORIGIN_MESSAGE = bytesToHex(bytes);
            ERROR = sw.toString();
            return -1;
        }
        
    }
    
    public static byte[] decode48(byte[] bytes, List<Cat48Message> messages) {
        ERROR = null;
        ORIGIN_MESSAGE = null;
        try {
            if (bytes.length <= 3) 
                return null;
            int cate = bytes[0] & 0xFF;
            if (cate != 48) 
                return null;

            int length = (((bytes[1] & 0xFF) << 8) | (bytes[2] & 0xFF));
            // System.out.println("Category: " + cate + "  Length: " + length);

            int index = 3;
            int count = 0;
            while (index < length) {
                Cat48Message message = new Cat48Message();
                count = decode(bytes, index, message);
                if (count <= 0) break;
                index += count;
                messages.add(message);
                // System.out.println(String.format("Length: %s Index: %s", length, index));
            }
            // System.out.println(String.format("Length: %s Index: %s", length, index));
            if (index < bytes.length){
                byte [] newbytes =  new byte[bytes.length - index + 1];
                System.arraycopy(bytes, index - 1, newbytes, 0, bytes.length - index + 1);
                return newbytes;
            }
            return new byte[0];
        } catch (Exception ex) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            ORIGIN_MESSAGE = bytesToHex(bytes);
            ERROR = sw.toString();
            return null;
        }
        
    }
    
    public static int decode(byte[] bytes, int index, Cat48Message message) {
        try {
            int start = index;
            boolean[] header = new boolean[ITEM_NUMBER];
            int count = decodeHeader(bytes, index, header);
            if (count == 0) return -1;
            index += count;
        
            for (int i = 0; i < ITEM_NUMBER; i++) {
                if (!header[i]) continue;
                switch (i) {
                    case 0: // 1 I048/010 Data Source Identifier 2
                        DataSourceID source = new DataSourceID();
                        count = DataSourceID.extract(bytes, index, source);
                        if (count <= 0) return -1;
                        message.setSic(source.getSic());
                        message.setSac(source.getSac());
                        index += count;
                        // System.out.printf("I048/010 Data Source Identifier %s(bytes)%n", 2);
                        break;
                        
                    case 1: // 2 I048/140 Time-of-Day 3
                        DValue timeOfDay = new DValue();
                        count = getTimeOfDay(bytes, index, timeOfDay);
                        if (count <= 0) return -1;
                        message.setTimeOfDay(timeOfDay.getValue());
                        index += count;
                        // System.out.printf("I048/140 Time-of-Day %s(bytes)%n", count);
                        break;
                        
                    case 2: // 3 I048/020 Target Report Descriptor 1+
                        TargetReportDescriptor targetReportDescriptor = new TargetReportDescriptor();
                        count = TargetReportDescriptor.extract(bytes, index, targetReportDescriptor);
                        if (count <= 0) return -1;
                        index += count;
                        message.setTargetReportDescriptor(targetReportDescriptor);
                        // System.out.printf("I048/020 Target Report Descriptor %s(bytes)%n", count);
                        break;
                        
                    case 3: // 4 I048/040 Measured Position in Slant Polar Coordinates 4
                        PolarCoordinate polarCoordinate = new PolarCoordinate();
                        count = PolarCoordinate.extract(bytes, index, polarCoordinate);
                        if (count <= 0) return -1;
                        index += count;
                        message.setPolarCoordinate(polarCoordinate);
                        // System.out.printf("I048/040 Measured Position in Slant Polar Coordinates %s(bytes)%n", count);
                        break;
                        
                    case 4: // 5 I048/070 Mode-3/A Code in Octal Representation 2
                        Mode3ACode mode3A = new Mode3ACode();
                        count = Mode3ACode.extract(bytes, index, mode3A);
                        if (count <= 0) return -1;
                        index += count;
                        message.setCode3A(mode3A);
                        // System.out.printf("I048/070 Mode-3/A Code in Octal Representation %s(bytes)%n", count);
                        break;
                        
                    case 5: // 6 I048/090 Flight Level in Binary Representation 2
                        FlightLevel flighLevel = new FlightLevel();
                        count = FlightLevel.extract(bytes, index, flighLevel);
                        if (count <= 0) return -1;
                        index += count;
                        message.setFlightLevel(flighLevel);
                        // System.out.printf("I048/090 Flight Level in Binary Representation %s(bytes)%n", count);
                        break;
                        
                    case 6: // 7 I048/130 Radar Plot Characteristics
                        RadarPlotCharacteristics radarPlotCharacteristics = new RadarPlotCharacteristics();
                        count = RadarPlotCharacteristics.extract(bytes, index, radarPlotCharacteristics);
                        if (count <= 0) return -1;
                        index += count;
                        message.setRadarPlotCharacteristics(radarPlotCharacteristics);
                        // System.out.printf("I048/130 Radar Plot Characteristics %s(bytes)%n", count);
                        break;
                        
                    case 7: // 8 I048/220 Aircraft Address
                        IValue address = new IValue();
                        count = getAircraftAddress(bytes, index, address);
                        if (count <= 0) return -1;
                        index += count;
                        message.setTargetAddress(address.getValue());
                        // System.out.printf("I048/220 Aircraft Address %s(bytes)%n", count);
                        break;
                        
                    case 8: // 9 I048/240 Aircraft Identification
                        SValue callsign = new SValue();
                        count = getCallsign(bytes, index, callsign);
                        if (count <= 0) return -1;
                        index += count;
                        message.setCallsign(callsign.getValue());
                        // System.out.printf("I048/240 Aircraft Identification %s(bytes)%n", count);
                        break;
                        
                    case 9: // 10 I048/250 Mode S MB Data
                        ModeSMBData modeSdata = new ModeSMBData();
                        count = ModeSMBData.extract(bytes, index, modeSdata);
                        if (count <= 0) return -1;
                        index += count;
                        message.setModeSData(modeSdata);
                        // System.out.printf("I048/250 Mode S MB Data %s(bytes)%n", count);
                        break;
                        
                    case 10: // 11 I048/161 Track Number
                        IValue trackno = new IValue();
                        count  = getTrackNumber(bytes, index, trackno);
                        if (count <= 0) return -1;
                        index += count;
                        message.setTrackNumber(trackno.val());
                        // System.out.printf("I048/161 Track Number %s(bytes)%n", count);
                        break;
                        
                    case 11: // 12 I048/042 Calculated Position in Cartesian Coordinates
                        CartesianCoordinate cartersianCoordinate = new CartesianCoordinate();
                        count = CartesianCoordinate.extract(bytes, index, cartersianCoordinate);
                        if (count <= 0) return -1;
                        index += count;
                        message.setCartesianCoordinate(cartersianCoordinate);
                        // System.out.printf("I048/042 Calculated Position in Cartesian Coordinates %s(bytes)%n", count);
                        break;
                        
                    case 12: // 13 I048/200 Calculated Track Velocity in Polar Representation 4
                        CalculatedPolarVelocity calculatePolarVelocity = new CalculatedPolarVelocity();
                        count = CalculatedPolarVelocity.extract(bytes, index, calculatePolarVelocity);
                        if (count <= 0) return -1;
                        index += count;
                        message.setCalculatedPolarVelocity(calculatePolarVelocity);
                        // System.out.printf("I048/200 Calculated Track Velocity in Polar %s(bytes)%n", count);
                        break;
                        
                    case 13: // 14 I048/170 Track Status
                        TrackStatus trackStatus = new TrackStatus();
                        count = TrackStatus.extract(bytes, index, trackStatus);
                        if (count <= 0) return -1;
                        index += count;
                        message.setTrackstatus(trackStatus);
                        // System.out.printf("I048/170 Track Status %s(bytes)%n", count);
                        break;
                        
                    case 14: // 15 I048/210 Track Quality
                        TrackQuality trackQuality = new TrackQuality();
                        count = TrackQuality.extract(bytes, index, trackQuality);
                        if (count <= 0) return -1;
                        index += count;
                        message.setTrackQuality(trackQuality);
                        // System.out.printf("I048/210 Track Quality %s(bytes)%n", count);
                        break;
                        
                    case 15: // 16 I048/030 Warning/Error Conditions
                        WarningErrorCondition warningErrorCondition = new WarningErrorCondition();
                        count = WarningErrorCondition.extract(bytes, index, warningErrorCondition);
                        if (count <= 0) return -1;
                        index += count;
                        message.setWarningErrorCondition(warningErrorCondition);
                        // System.out.printf("I048/030 Warning/Error Conditions %s(bytes)%n", count);
                        break;
                        
                    case 16: // 17 I048/080 Mode-3/A Code Confidence Indicator
                        Mode3ACodeConfidenceIndicator mode3AConfidence = new Mode3ACodeConfidenceIndicator();
                        count = Mode3ACodeConfidenceIndicator.extract(bytes, index, mode3AConfidence);
                        if (count <= 0) return -1;
                        index += count;
                        message.setMode3AConfidenceIndicator(mode3AConfidence);
                        // System.out.printf("I048/080 Mode-3/A Code Confidence Indicator %s(bytes)%n", count);
                        break;
                        
                    case 17: // 18 I048/100 Mode-C Code and Confidence Indicator
                        ModeCConfidenceIndicator moceCConfidenceIndicator = new ModeCConfidenceIndicator();
                        count = ModeCConfidenceIndicator.extract(bytes, index, moceCConfidenceIndicator);
                        if (count <= 0) return -1;
                        index += count;
                        message.setModeCConfidenceIndicator(moceCConfidenceIndicator);
                        // System.out.printf("I048/100 Mode-C Code and Confidence Indicator %s(bytes)%n", count);
                        break;
                        
                    case 18: // 19 I048/110 Height Measured by 3D Radar
                        Height3DRadar height = new Height3DRadar();
                        count = Height3DRadar.extract(bytes, index, height);
                        if (count <= 0) return -1;
                        index += count;
                        message.setHeight3DRadar(height);
                        // System.out.printf("I048/110 Height Measured by 3D Radar %s(bytes)%n", count);
                        break;
                        
                    case 19: // 20 I048/120 Radial Doppler Speed
                        RadialDroplerSpeed droplerSpeed = new RadialDroplerSpeed();
                        count = RadialDroplerSpeed.extract(bytes, index, droplerSpeed);
                        if (count <= 0) return -1;
                        index += count;
                        message.setDroplerSpeed(droplerSpeed);
                        // System.out.printf("I048/120 Radial Doppler Speed %s(bytes)%n", count);
                        break;
                        
                    case 20: // 21 I048/230 Communications / ACAS Capability and Flight Status
                        CommunicationCapacityAndFlightStatus comStatus = new CommunicationCapacityAndFlightStatus();
                        count = CommunicationCapacityAndFlightStatus.extract(bytes, index, comStatus);
                        if (count <= 0) return -1;
                        index += count;
                        message.setCommunicationStatus(comStatus);
                        // System.out.printf("I048/230 Communications / ACAS Capability and Flight Status %s(bytes)%n", count);
                        break;
                        
                    case 21: // 22 I048/260 ACAS Resolution Advisory Report
                        ACASResolutionAdvisoryReport resolutionAdvisoryReport = new ACASResolutionAdvisoryReport();
                        count = ACASResolutionAdvisoryReport.extract(bytes, index, resolutionAdvisoryReport);
                        if (count <= 0) return -1;
                        index += count;
                        message.setAcasResolutionReport(resolutionAdvisoryReport);
                        // System.out.println(String.format("I048/260 ACAS Resolution Advisory Report %s(bytes)", count));
                        break;
                        
                    case 22: // 23 I048/055 Mode-1 Code in Octal Representation
                        Mode1Code mode1 = new Mode1Code();
                        count = Mode1Code.extract(bytes, index, mode1);
                        if (count <= 0) return -1;
                        index += count;
                        message.setMode1Code(mode1);
                        // System.out.println(String.format("I048/055 Mode-1 Code in Octal Representation %s(bytes)", count));
                        break;
                        
                    case 23: // 24 I048/050 Mode-2 Code in Octal Representation
                        Mode2Code mode2 = new Mode2Code();
                        count = Mode2Code.extract(bytes, index, mode2);
                        if (count <= 0) return -1;
                        index += count;
                        message.setMode2Code(mode2);
                        // System.out.println(String.format("I048/050 Mode-2 Code in Octal Representation %s(bytes)", count));
                        break;
                        
                    case 24: // 25 I048/065 Mode-1 Code Confidence Indicator
                        Mode1CodeConfidenceIndicator mode1ConfidenceIndicator = new Mode1CodeConfidenceIndicator();
                        count = Mode1CodeConfidenceIndicator.extract(bytes, index, mode1ConfidenceIndicator);
                        if (count <= 0) return -1;
                        index += count;
                        message.setMode1ConfidenceIndicator(mode1ConfidenceIndicator);
                        // System.out.println(String.format("I048/065 Mode-1 Code Confidence Indicator %s(bytes)", count));
                        break;
                        
                    case 25: // 26 I048/060 Mode-2 Code Confidence Indicator
                        Mode2CodeConfidenceIndicator mode2ConfidenceIndicator = new Mode2CodeConfidenceIndicator();
                        count = Mode2CodeConfidenceIndicator.extract(bytes, index, mode2ConfidenceIndicator);
                        if (count <= 0) return -1;
                        index += count;
                        message.setMode2ConfidenceIndicator(mode2ConfidenceIndicator);
                        // System.out.println(String.format("I048/060 Mode-2 Code Confidence Indicator %s(bytes)", count));
                        break;
                        
                    case 26: // 27 SP-Data Item Special Purpose Field
                        count = skip(bytes, index);
                        index += count;
                        // System.out.println(String.format("SP-Data Item Special Purpose Field %s(bytes)", count));
                        break;
                        
                    case 27: // 28 RE-Data Item Reserved Expansion Field
                        count = skip(bytes, index);
                        index += count;
                        // System.out.println(String.format("RE-Data Item Reserved Expansion Field %s(bytes)", count));
                        break;
                }
            }
        
            //if (count <= 0) return -1;
            //index += count;
            int extractByte = index - start;
            int totalByte = extractByte + 3;
            
            // System.out.println("-----------------------------------------------------------------------------------------------");
            // System.out.println(String.format("index: %s - %s read: %s", start, index, extractByte));
            
            if (start + extractByte > bytes.length) extractByte = bytes.length - start;
            final byte[] contentsByte = new byte[totalByte];
            System.arraycopy(bytes, start, contentsByte, 3, extractByte);
            contentsByte[0] = 01;
            contentsByte[1] = (byte) ((totalByte >> 8) & 0xFF);
            contentsByte[2] = (byte) (totalByte & 0xFF);
            message.setBinary(contentsByte);
            return (index - start);
        } catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        }
    }
    
    private static int getTimeOfDay(byte [] bytes, int index, DValue dVal) {
        if (!Byter.validateIndex(index, bytes.length, 3)) return -1;
        int value = ByteBuffer.wrap(new byte[]{0x00, bytes[index++], bytes[index++], bytes[index++]}).getInt();
        dVal.setValue((double) value / 128);
        return 3;
    }
    
    private static int getAircraftAddress(byte [] bytes, int index, IValue sval) {
        if (!Byter.validateIndex(index, bytes.length, 3)) return -1;
        int value = ByteBuffer.wrap(new byte[]{0x00, bytes[index++], bytes[index++], bytes[index++]}).getInt();
        sval.setValue(value);
        return 3;
    }
    
    private static int getCallsign(byte[] bytes, int index, SValue callSign) {
        if (!Byter.validateIndex(index, bytes.length, 6)) return -1;
        StringBuilder builder = new StringBuilder();
        byte[] byts = new byte[]{bytes[index++], bytes[index++], bytes[index++]};
        builder.append(getCode(byts));
        byts = new byte[]{bytes[index++], bytes[index++], bytes[index++]};
        builder.append(getCode(byts));
        callSign.setValue(builder.toString());
        return 6;
    }
    
    private static int getTrackNumber(byte [] bytes, int index, IValue ivalue) {
        if (!Byter.validateIndex(index, bytes.length, 2)) return -1;
        int value = (bytes[index++] & 0x0F) << 8 | (bytes[index++] & 0xFF);
        ivalue.setValue(value);
        return 2;
    }
    
    private static String getCode(byte[] byts) {
        StringBuilder builder = new StringBuilder();
        
        int code = byts[0] >> 2 & 0x3F;
        if (code != 0) builder.append(getChar(code));

        code = (byts[0] & 0x03) << 4 | (byts[1] >> 4 & 0x0F);
        if (code != 0) builder.append(getChar(code));
        
        code = (byts[1] & 0x0F) << 2 | (byts[2] >> 6 & 0x03);
        if (code != 0) builder.append(getChar(code));

        code = byts[2] & 0x3F;
        if (code != 0) builder.append(getChar(code));
        return builder.toString();
    }
    
    private static char getChar(int i) {
        if (i <=26) return (char) (i+64);
        if (i==32) return ' ';
        return (char) i;
    }
    
    private static int skip(byte [] bytes, Integer index) {
        int length = bytes[index] & 0xFF;
        return length;
    }
    
    final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 3];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 3] = hexArray[v >>> 4];
            hexChars[j * 3 + 1] = hexArray[v & 0x0F];
            hexChars[j * 3 + 2] = ' ';
        }
        return new String(hexChars);
    }
}
