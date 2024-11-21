/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.cat62.v112;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.ByteBuffer;
import java.util.List;

/**
 *
 * @author andh
 */
public class Cat62Decryptor {
    
    public static final int NO_ERR = 0;
    public static final int ERR_OVER_LENGTH = 1;
    public static final int ERR_WRONG_CATEGORY = 2;
    private static final byte[] MASKED = new byte[]{(byte) 0x80, (byte) 0x40, (byte) 0x20, (byte) 0x10, (byte) 0x08, (byte) 0x04, (byte) 0x02};
    protected static final char[] hexArray = "0123456789ABCDEF".toCharArray();
    private static final short ITEM_NUMBER = 35;
    public static String ERROR;
    public static String ORIGIN_MESSAGE;
    
    public static int decodeHeader(byte[] bytes, int index, boolean [] header) {
        boolean isExtend = true;
        int bit;
        int headerIndex = 0;
        while (isExtend) {
            byte currentByte = bytes[index];
            for (int i = 1; i < 8; i++) {
                bit = currentByte & MASKED[i - 1];
                if (bit == 0) continue;
                int idx = headerIndex * 7 + i - 1;
                header[idx] = true;
            }

            bit = currentByte & 0x01;
            isExtend = (bit != 0);
            index++;
            headerIndex++; // Increasing readed bytes
        }
        return headerIndex;
    }
    
    public static int decode(byte[] bytes, List<Cat62Message> messages) {
        ERROR = null;
        ORIGIN_MESSAGE = null;
        try {
            if (bytes.length <= 3) return ERR_OVER_LENGTH;
            int cate = bytes[0] & 0xFF;
            if (cate != 62) return ERR_WRONG_CATEGORY;

            int length = (((bytes[1] & 0xFF) << 8) | (bytes[2] & 0xFF));

            int index = 3;
            int count = 0;
            while (index < length) {
                Cat62Message message = new Cat62Message();
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
            ORIGIN_MESSAGE = Byter.bytesToHex(bytes);
            ERROR = sw.toString();
            return -1;
        }
        
    }
    
    public static int decode(byte[] bytes, int index, Cat62Message message) {
        try {
            int start = index;
            boolean[] header = new boolean[ITEM_NUMBER];
            int count = decodeHeader(bytes, index, header);
            if (count == 0) return -1;
            index += count;
        
            for (int i = 0; i < ITEM_NUMBER; i++) {
                if (!header[i]) continue;
                count = 0;
                switch (i) {
                    case 0: // 1 I062/010 Data Source Identifier 2
                        DataSourceID source = new DataSourceID();
                        count = DataSourceID.decode(bytes, index, source);
                        if (count <= 0) return -1;
                        message.setSic(source.getSic());
                        message.setSac(source.getSac());
                        // index += count;
                        // System.out.printf("I048/010 Data Source Identifier %s(bytes)%n", 2);
                        break;
                        
                    case 1: // 2 - Spare -
                        break;
                        
                    case 2: //  3 I062/015 Service Identification 1
                        if (!Byter.validateIndex(index, bytes.length, 3)) return -1;
                        int value = bytes[index++] &0xFF;
                        message.setServiceID(value);
                        // index += 1;
                        // count = 1;
                        break;
                        
                    case 3: // 4 I062/070 Time Of Track Information 3
                        DValue timeOfDay = new DValue();
                        count = getTimeOfDay(bytes, index, timeOfDay);
                        if (count <= 0) return -1;
                        message.setTimeOfTrack(timeOfDay.value);
                        // index += count;
                        break;
                        
                    case 4: // 5 I062/105 Calculated Track Position (WGS-84) 8
                        WGS84Coordinate wgs84 = new WGS84Coordinate();
                        count = WGS84Coordinate.decode(bytes, index, wgs84);
                        if (count <= 0) return -1;
                        message.setPosWGS84(wgs84);
                        // index += count;
                        break;
                        
                    case 5: // 6 I062/100 Calculated Track Position (Cartesian) 6
                        Coordinate posCartesian = new Coordinate();
                        count = PosCartesianCoordinate.decode(bytes, index, posCartesian);
                        if (count <= 0) return -1;
                        message.setPosCartesian(posCartesian);
                        // index += count;
                        break;
                        
                    case 6: // 7 I062/185 Calculated Track Velocity (Cartesian) 4
                        Coordinate veloCartesian = new Coordinate();
                        count = VeloCartesianCoordinate.decode(bytes, index, veloCartesian);
                        if (count <= 0) return -1;
                        message.setVeloCartesian(veloCartesian);
                        // index += count;
                        break;
                        
                    // BYTE #2
                    case 7: // 8 I062/210 Calculated Acceleration (Cartesian) 2
                        Coordinate accCartesian = new Coordinate();
                        count = AccCartesianCoordinate.extract(bytes, index, accCartesian);
                        if (count <= 0) return -1;
                        message.setAccCartesian(accCartesian);
                        break;
                        
                    case 8: // 9 I062/060 Track Mode 3/A Code 2
                        Mode3ACode code3A = new Mode3ACode();
                        count = Mode3ACode.decode(bytes, index, code3A);
                        if (count <= 0) return -1;
                        message.setMode3A(code3A);
                        break;
                        
                    case 9: // 10 I062/245 Target Identification 7
                        TargetID targetID = new TargetID();
                        count = TargetID.decode(bytes, index, targetID);
                        if (count <= 0) return -1;
                        message.setTargetID(targetID);
                        break;
                        
                    case 10: // 11 I062/380 Aircraft Derived Data 1+
                        AircraftDerivedData aircraftDerivedData = new AircraftDerivedData();
                        count = AircraftDerivedData.decode(bytes, index, aircraftDerivedData);
                        if (count < 0) return -1;
                        message.setAircraftDerivedData(aircraftDerivedData);
                        break;
                        
                    case 11: // 12 I062/040 Track Number 2
                        if (!Byter.validateIndex(index, bytes.length, 2)) return -1;
                        message.setTrackNo((bytes[index++] & 0xFF) << 8 | (bytes[index++] & 0xFF));
                        // count = 2;
                        break;
                        
                    case 12: // 13 I062/080 Track Status 1+
                        TrackStatus trackStatus = new TrackStatus();
                        count = TrackStatus.decode(bytes, index, trackStatus);
                        if (count < 0) return -1;
                        message.setTrackStatus(trackStatus);
                        break;
                        
                    case 13: // 14 I062/290 System Track Update Ages 1+
                        SystemTrackUpdateAges systemTrackUpdateAge = new SystemTrackUpdateAges();
                        count = SystemTrackUpdateAges.extract(bytes, index, systemTrackUpdateAge);
                        if (count < 0) return -1;
                        message.setSystemTrackUpdateAge(systemTrackUpdateAge);
                        break;
                        
                    case 14: //  15 I062/200 Mode of Movement 1
                        ModeOfMovement modeOfMovement = new ModeOfMovement();
                        count = ModeOfMovement.extract(bytes, index, modeOfMovement);
                        if (count < 0) return -1;
                        message.setModeOfMovement(modeOfMovement);
                        break;
                        
                    case 15: // 16 I062/295 Track Data Ages 1+
                        TrackDataAges trackDataAges = new TrackDataAges();
                        count = TrackDataAges.extract(bytes, index, trackDataAges);
                        if (count < 0) return -1;
                        message.setTrackDataAges(trackDataAges);
                        break;
                        
                    case 16: // 17 I062/136 Measured Flight Level 2
                        if (!Byter.validateIndex(index, bytes.length, 2)) return -1;
                        double measureFlightLevel = Byter.getComplementNumber(new byte[] {bytes[index++], bytes[index++]}) * 0.25;
                        message.setMeasureFlightLevel(measureFlightLevel);
                        // count = 2;
                        break;
                        
                    case 17: // 18 I062/130 Calculated Track Geometric Altitude 2
                        if (!Byter.validateIndex(index, bytes.length, 2)) return -1;
                        message.setMeasureFlightLevel(Byter.getComplementNumber(new byte[] {bytes[index++], bytes[index++]}) * 6.25);
                        // count = 2;
                        break;
                        
                    case 18: // 19 I062/135 Calculated Track Barometric Altitude 2
                        CalculatedBarometricAltitude calculatedBarometric = new CalculatedBarometricAltitude();
                        count = CalculatedBarometricAltitude.extract(bytes, index, calculatedBarometric);
                        if (count < 0) return -1;
                        message.setCalculatedBarometricAltitude(calculatedBarometric);
                        break;
                        
                    case 19: // 20 I062/220 Calculated Rate Of Climb/Descent 2
                        if (!Byter.validateIndex(index, bytes.length, 2)) return -1;
                        message.setCalculatedRateOfClimb(Byter.getComplementNumber(new byte[] {bytes[index++], bytes[index++]}) * 6.25);
                        // count = 2;
                        break;
                        
                    case 20: // 21 I062/390 Flight Plan Related Data 1+
                        FlightPlan flightPlan = new FlightPlan();
                        count = FlightPlan.decode(bytes, index, flightPlan);
                        if (count < 0) return -1;
                        message.setFlightPlan(flightPlan);
                        break;
                        
                    case 21: // 22 I062/270 Target Size & Orientation 1+
                        TargetSizeOrientation targetSizeOrient = new TargetSizeOrientation();
                        count = TargetSizeOrientation.extract(bytes, index, targetSizeOrient);
                        if (count < 0) return -1;
                        message.setTargetSizeOrienttation(targetSizeOrient);
                        break;
                        
                    case 22: // 23 I062/300 Vehicle Fleet Identification 1
                        if (!Byter.validateIndex(index, bytes.length, 1)) return -1;
                        message.setVehicleFleetID(bytes[index++] * 0xFF);
                        // count = 1;
                        break;
                        
                    case 23: // 24 I062/110 Mode 5 Data reports & Extended Mode 1 Code 1+
                        Mode5DataReport mode5DataReport = new Mode5DataReport();
                        count = Mode5DataReport.extract(bytes, index, mode5DataReport);
                        if (count <0) return -1;
                        message.setMode5DataReport(mode5DataReport);
                        break;
                        
                    case 24: // 25 I062/120 Track Mode 2 Code 2
                        if (!Byter.validateIndex(index, bytes.length, 2)) return -1;
                        int mode2Code = Integer.parseInt(Integer.toOctalString((bytes[index++] & 0x0F) << 8 | (bytes[index] & 0xFF)));
                        message.setMode2Code(mode2Code);
                        // count = 2;
                        break;
                        
                    case 25: // 26 I062/510 Composed Track Number 3+
                        ComposedTrackNumber composedTrack = new ComposedTrackNumber();
                        count = ComposedTrackNumber.extract(bytes, index, composedTrack);
                        if (count < 0) return -1;
                        message.setComposedTrackNumber(composedTrack);
                        break;
                        
                    case 26: // 27 I062/500 Estimated Accuracies 1+
                        EstimatedAccuracies estimateAccuracy = new EstimatedAccuracies();
                        count = EstimatedAccuracies.extract(bytes, index, estimateAccuracy);
                        if (count < 0) return -1;
                        message.setEstimateAccuracy(estimateAccuracy);
                        break;
                        
                    case 27: // 28 I062/340 Measured Information 1+
                        MeasureInfo measureInfo = new MeasureInfo();
                        count = MeasureInfo.extract(bytes, index, measureInfo);
                        if (count < 0) return -1;
                        message.setMeasureInfo(measureInfo);
                        break;
                        
                    case 28: // 29 - Spare
                        break;
                        
                    case 29: // 30 - Spare 
                        break;
                        
                    case 30: // 31 - Spare
                        break;
                        
                    case 31: // 32 - Spare -
                        break;
                        
                    case 32: // 33 - Spare -
                        break;
                        
                    case 33: // 34 RE Reserved Expansion Field 1+
                        count = skip(bytes, index);
                        break;
                        
                    case 34: // 35 SP Reserved For Special Purpose Indicator 1+
                        count = skip(bytes, index);
                        break;

                }
                
                index += count;
            }
        
            //if (count <= 0) return -1;
            //index += count;
            // int extractByte = index - start;
            // int totalByte = extractByte + 3;
            
            // System.out.println("-----------------------------------------------------------------------------------------------");
            // System.out.println(String.format("index: %s - %s read: %s", start, index, extractByte));
            
            // if (start + extractByte > bytes.length) extractByte = bytes.length - start;
            // final byte[] contentsByte = new byte[totalByte];
            // System.arraycopy(bytes, start, contentsByte, 3, extractByte);
            // contentsByte[0] = 01;
            // contentsByte[1] = (byte) ((totalByte >> 8) & 0xFF);
            // contentsByte[2] = (byte) (totalByte & 0xFF);
            // message.setBinary(contentsByte);
            return (index - start);
        } catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        }
    }
    
    private static int getTimeOfDay(byte[] bytes, int index, DValue dVal) {
        if (!Byter.validateIndex(index, bytes.length, 3)) {
            return -1;
        }
        int value = ByteBuffer.wrap(new byte[]{0x00, bytes[index++], bytes[index++], bytes[index++]}).getInt();
        dVal.setValue((double) value / 128);
        return 3;
    }

    private static int skip(byte[] bytes, Integer index) {
        int length = bytes[index] & 0xFF;
        return length;
    }

    
}
