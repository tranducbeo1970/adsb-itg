/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.cat62.v112;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author andh
 */
public class Cat62EncryptorTest {
    
    public Cat62EncryptorTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     */
    @Test
    public void testEncrypt() {
        Cat62Message msg = new Cat62Message();
        
        // #1
        msg.setSic(150);
        msg.setSac(92);

        // #4
        msg.setTimeOfTrack(1.0);

        // #12
        msg.setTrackNo(50);

        // #13
        TrackStatus trackStatus = new TrackStatus();
        trackStatus.setMonoSensorTrack(true);
        trackStatus.setSpiPresent(true);
        trackStatus.setMostReliableHeightl(true);
        trackStatus.setCalculatedAltitudeSource(1);
        trackStatus.setTentativeTrack(false);
        msg.setTrackStatus(trackStatus);
        // Byte[] messageContent = Cat62Encryptor.encrypt(msg).toArray(new Byte[]{});
        // print(messageContent
        List<Cat62Message> messages = new ArrayList<>();
        messages.add(msg);
        
        msg = new Cat62Message();
        
        // #1
        msg.setSic(150);
        msg.setSac(92);

        // #4
        msg.setTimeOfTrack(1.0);

        // #12
        msg.setTrackNo(51);
        WGS84Coordinate wgs84Coord = new WGS84Coordinate(29.259597349104734 , 107.70803442025193);
        msg.setPosWGS84(wgs84Coord);
        msg.setMeasureFlightLevel(123.25);

        // #13
        trackStatus = new TrackStatus();
        trackStatus.setMonoSensorTrack(true);
        trackStatus.setSpiPresent(true);
        trackStatus.setMostReliableHeightl(true);
        trackStatus.setCalculatedAltitudeSource(1);
        trackStatus.setTentativeTrack(false);
        msg.setTrackStatus(trackStatus);
        messages.add(msg);
        
        
        byte [] messageContent2 = Cat62Encryptor.encode(messages);
        print(messageContent2);
        
        List<Cat62Message> decodedMsg = new ArrayList<>();
        int rsl = Cat62Decryptor.decode(messageContent2, decodedMsg);
        if ( rsl < 0) {
            System.out.println("ERR: " + Cat62Decryptor.ERROR);
            return;
        }
        
        System.out.println("Count:" + decodedMsg.size());
        for (Cat62Message msg1 : decodedMsg) {
            System.out.println("SIC: " + msg1.getSic() + " SAC: " + msg1.getSac() + " TIme: " + msg1.getTimeOfTrack() + " TrackNo: " + msg1.getTrackNo() + "\n" + msg1.getTrackStatus());
            if (msg1.getPosWGS84() != null) {
                System.out.println("POS " + msg1.getPosWGS84().lat + "  " + msg1.getPosWGS84().lng);
            }
        }
        // Cat62Message msg1 = decodedMsg.get(0);
        
        
        
    }
    
    private void print(Byte[] bytes) {
        StringBuilder builder = new StringBuilder("Mehod #1: ");
        for (Byte b : bytes) {
            builder.append(String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0')).append(' ');
        }
        System.out.println(builder.toString());
    }
    
    private void print(byte[] bytes) {
        StringBuilder builder = new StringBuilder("Mehod #2: ");
        for (byte b : bytes) {
            builder.append(String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0')).append(' ');
        }
        System.out.println(builder.toString());
    }
    
}
