/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.cat62.v112;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hong
 */
public class Cat62Encryptor {

    public static byte[] encode(final Cat62Message message) {
        ByteBuilder builder = new ByteBuilder();

        // Item #1
        List<Byte> bytes = DataSourceID.encode(message);
        if (!bytes.isEmpty()) {
            builder.set(0, bytes);
        }

        // Item #2
        // Item #3
        // Item #4 I062/070 Time Of Track Information
        if (message.getTimeOfTrack() != null) {
            builder.set(3, decodeTimeOfDay(message.getTimeOfTrack()));
        }

        // Item #5 Calculated Track Position (WGS-84)
        if (message.getPosWGS84() != null) {
            builder.set(4, message.getPosWGS84().encode());
        }

        // Item #6 Calculated Track Position (Cartesian)
        if (message.getPosCartesian() != null) {
            builder.set(5, PosCartesianCoordinate.encode(message.getPosCartesian()));
        }

        // Item #7 Calculated Track Velocity (Cartesian) 
        if (message.getVeloCartesian() != null) {
            builder.set(6, VeloCartesianCoordinate.encode(message.getVeloCartesian()));
        }

        // Item #8 Calculated Acceleration (Cartesian)
        // Item #9 Track Mode 3/A Code
        if (message.getMode3A() != null) {
            builder.set(8, message.getMode3A().encode());
        }

        // Item #10 Target Identification
        if (message.getTargetID() != null) {
            builder.set(9, message.getTargetID().encode());
        }

        // Item #11 Aircraft Derived Data
        if (message.getAircraftDerivedData() != null) {
            builder.set(10, message.getAircraftDerivedData().encode());
        }

        // Item #12 Track Number
        if (message.getTrackNo() != null) {
            builder.set(11, deccodeTrackNumber(message.getTrackNo()));
        }

        // Item #13 Track Status
        if (message.getTrackStatus() != null) {
            builder.set(12, message.getTrackStatus().encode());
        }

        // Item #14
        // Item #15
        // Item #16
        // Item #17 Measured Flight Level
        if (message.getMeasureFlightLevel() != null) {
            builder.set(16, encodeMeasureFlightLevel(message.getMeasureFlightLevel()));
        }

        // Item #18
        // Item #19
        // Item #20
        // Item #21 I062/390 Flight Plan Related Data 1+
        if (message.getFlightPlan() != null) {
            builder.set(17, message.getFlightPlan().encode());
        }
        // Item #22
        // Item #23
        // Item #24
        // Item #25
        // Item #26
        // Item #27
        // Item #28
        // Item #29
        // Item #30
        // Item #31
        // Item #32
        // Item #33
        // Item #34
        // Item #35
        return builder.getContentByte();
    }

    public static byte[] encode(final List<Cat62Message> messages) {
        Cat62Message msg;
        final int length = messages.size();
        byte[][] byteBlock = new byte[length][];
        int size = 3;
        for (int i = 0; i < length; i++) {
            msg = messages.get(i);
            byteBlock[i] = encode(msg);
            size += byteBlock[i].length;
        }
        if (size == 3) return null;
        byte [] content = new byte[size];
        content[0] = (byte) 0x3E;
        content[1] = (byte) (size >> 8 & 0xFF);
        content[2] = (byte) (size & 0xFF);
        // System.arraycopy(headbyte, 0, content, 3, headbyte.length);
        
        int cIndex = 3;
        for (byte[] b : byteBlock) {
            if (b == null || b.length == 0) continue;
            System.arraycopy(b, 0, content, cIndex, b.length);
            cIndex += b.length;
        }
        return content;
    }

    /**
     * Item #12
     *
     * @param trackNo
     */
    private static List<Byte> deccodeTrackNumber(Integer trackNo) {
        final List<Byte> result = new ArrayList<>();
        if (trackNo == null) {
            return result;
        }
        result.add((byte) (trackNo >> 8 & 0xFF));
        result.add((byte) (trackNo & 0xFF));
        return result;
    }

    private static List<Byte> decodeTimeOfDay(Double time) {
        final List<Byte> result = new ArrayList<>();
        if (time == null) {
            return result;
        }
        final int val = (int) (time * 128);
        result.add((byte) (val >> 16 & 0xFF));
        result.add((byte) (val >> 8 & 0xFF));
        result.add((byte) (val & 0xFF));
        return result;
    }

    private static byte[] encodeMeasureFlightLevel(double dval) {
        int val = (int) (dval / 0.25);
        if (val > 0) {
            return new byte[]{(byte) (val >> 8 & 0xFF), (byte) (val & 0xFF)};
        }
        val = -val;
        val -= 0x01;
        return new byte[]{(byte) (~(val >> 8) & 0xFF), (byte) (~val & 0xFF)};

    }
}
