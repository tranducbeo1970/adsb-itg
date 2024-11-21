/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asterix.cat21.v21;

import com.attech.asterix.cat21.entities.v21.AirborneGroundVector;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author root
 */
public class Encryptor {
    
    public byte [] header = new byte [] { 0x01, 0x01, 0x01, 0x00 };
    public int length = 0;
    // private final ByteBuffer b = ByteBuffer.allocate(4);
    
    public byte[] encrypt(short sic, short sac, String callsign, String targetAddress, double longtitude, double latitude, double groundSpeed, double trackAngle, float flightLevel) {
        byte h1 = (byte) 0x83;
        List<Byte> bytes = new ArrayList<>();
        byte[] bts = encodeSicSac(sic, sac);
        bytes.add(bts[0]);
        bytes.add(bts[1]);
        
        bts = encodePosition(longtitude, latitude);
        for (int i = 0; i < bts.length; i++) {
            bytes.add(bts[i]);
        }
        
        byte h2 = (byte) 0x11;
        
        bts = encodeAddress(targetAddress);
        for (int i = 0; i < bts.length; i++) {
            bytes.add(bts[i]);
        }
        
        byte h3 = (byte) 0x03;
        bts = encodeFlightLevel(flightLevel);
        for (int i = 0; i < bts.length; i++) {
            bytes.add(bts[i]);
        }
        
        
        byte h4 = (byte) 0x09;
        AirborneGroundVector vector = new AirborneGroundVector();
        vector.setGroundSpeed(groundSpeed);
        vector.setTrackAngle(trackAngle);
        bts = encodeAirBorne(vector);
        for (int i = 0; i < bts.length; i++) {
            bytes.add(bts[i]);
        }
        
        byte h5 =  (byte) 0x80;
        bts = encodeCallSign(callsign);
        for (int i = 0; i < bts.length; i++) {
            bytes.add(bts[i]);
        }
        
        int length = bytes.size() + 5 + 2 + 1;
        byte l1 = (byte) (length >> 8 & 0XFF);
        byte l2 = (byte) (length & 0XFF);
        byte[] content = new byte[length];
        content[0] = 21;
        content[1] = l1;
        content[2] = l2;
        
        content[3] = h1;
        content[4] = h2;
        content[5] = h3;
        content[6] = h4;
        content[7] = h5;
        
        for (int i = 8; i< content.length; i++) {
            content[i] = bytes.get(i-8);
        }
        
        return content;
    }
    public byte[] encodeSicSac(short sic, short sac) {
        return new byte[]{(byte) sac, (byte) sic};
    }
    
    public byte[] encodePosition(double longtitude, double latitude) {
        byte[] byteLat = encodeLocation(latitude);
        byte[] byteLong = encodeLocation(longtitude);
        return new byte[] { byteLat[0], byteLat[1], byteLat[2], byteLat[3], byteLong[0], byteLong[1], byteLong[2], byteLong[3]} ;
    }
    
    public byte [] encodeCallSign(String callSign) {
        int [] chars = new int [8];
        for (int i = 0; i < callSign.length(); i++) {
            int index =callSign.charAt(i);
            if (index > 65) index -= 64;
            chars[i] = index;
        }
        
        byte b1 = (byte) ((chars[0] << 2) | (chars[1] >> 4 & 0x03));
        byte b2 = (byte) ((chars[1] << 4) | (chars[2] >> 2 & 0x0F));
        byte b3 = (byte) ((chars[2] << 6) | (chars[3] & 0x3F));
        byte b4 = (byte) ((chars[4] << 2) | (chars[5] >> 4 & 0x03));
        byte b5 = (byte) ((chars[1] << 5) | (chars[6] >> 2 & 0x0F));
        byte b6 = (byte) ((chars[2] << 6) | (chars[7] & 0x3F));
        return new byte[]{b1, b2, b3, b4, b5, b6};
    }
    
    public byte [] encodeAddress(String targetAddress) {
        // ByteBuffer b = ByteBuffer.allocate(3);
        BigInteger bigInt = new BigInteger(targetAddress, 16);
        int value = bigInt.intValue();
        return new byte[]{(byte) (value >> 16 & 0xFF), (byte) (value >> 8 & 0xFF), (byte) (value & 0xFF)};
    }
    
    public byte [] encodeTrueAirSpeed(int trueAirSpeed) {
        if (trueAirSpeed > 32767) return new byte[]{(byte) 0xFF, (byte) 0xFF};
        byte b1 = (byte) (trueAirSpeed >> 8 & 0x7F);
        byte b2 = (byte) (trueAirSpeed & 0xFF);
        return new byte[] {b1, b2};
    }
    
    public byte[] encodeFlightLevel(float level) {
        short value = (short) (level * 4);
        return new byte[]{(byte) ((value >> 8) & 0xff), (byte) (value & 0xff)};
    }
    
    public byte [] encodeMagneticHeading(double heading) {
        int value = (int) (heading / (360 / Math.pow(2, 16))); 
        return new byte[]{(byte) (value & 0xff), (byte) ((value >> 8) & 0xff)};
    }
        
    public byte[] encodeAirBorne(AirborneGroundVector airbone) {
        int val1 = airbone.isRangeExceeded() ? 0x80 : 0x00;
        int value = (int) (airbone.getGroundSpeed() / 0.22);
        byte b1 = (byte) (val1 | (value >> 8 & 0x7F));
        byte b2 = (byte) (value & 0xFF);
        // return new byte[] {b1, b2};
        
        int speed = (int) (airbone.getTrackAngle()/ 0.0055);
        byte b3 = (byte) (speed >> 8 & 0xFF);
        byte b4 = (byte) (speed & 0xFF);
        return new byte[] { b1, b2, b3, b4 };
    }
    
    public byte[] encodeLocation(double p) {
        ByteBuffer b = ByteBuffer.allocate(4);
        int v = (int) Math.round(p / 0.00000016764);
        b.putInt(v);
        return b.array();
    }
}
