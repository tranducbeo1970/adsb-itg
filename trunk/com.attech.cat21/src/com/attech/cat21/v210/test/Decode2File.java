/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.attech.cat21.v210.test;

import com.attech.cat21.v210.Cat21Decoder;
import com.attech.cat21.v210.Cat21Message;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author andh
 */
public class Decode2File {
    
    public void read(String path) throws IOException {
        final File file = new File(path);
        final File outputFile = new File(file.getParent(), file.getName() + ".txt");
        FileWriter fw = new FileWriter(outputFile);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write("SIC, SAC, 24Bit, CALLSIGN, LONGTITUDE, LATITUDE, TIME OF APPLICABILITY FOR POS, TRUE AIR SPEED, TIME OF RECEPTION OF POS, MODE 3A, FLIGHT LEVEL, MAGNETIC HEADING, GROUND SPEED, GROUND HEADING, TIME OF REPORT TRANSMISSION, NIC, NAC, SIL\n");
                        
        final long fileLength = file.length();
        try {
            int index = 0;
            FileInputStream inputStream = new FileInputStream(path);
            byte[] byts = new byte[3];
            int numRead = 1;
            
            int counting = 0;
            
            long time1 = System.nanoTime();
            System.out.println(Cat21Message.getHeading());
            
            do {
                
                numRead = inputStream.read(byts, 0, 3);
                if (numRead == 0) break;
                if (numRead != 3) break;
                int msgLength = (byts[1] & 0xFF) << 8 | (byts[2] & 0xFF);

                byte[] packages = new byte[msgLength];
                numRead = inputStream.read(packages, 3, msgLength - 3);
                packages[0] = byts[0];
                packages[1] = byts[1];
                packages[2] = byts[2];

                if (numRead != msgLength - 3) break;
                
                List<Cat21Message> messages = new ArrayList<>();
                Cat21Decoder.decode(packages, messages);
                counting += messages.size();
                for (Cat21Message m : messages) {
                    
                    
                    StringBuilder builder = new StringBuilder();
                    // SIC, SAC, 
                    builder.append( String.format("%s, %s, ", m.getSic(), m.getSac()));
                    
                    // 24Bit, CALLSIGN, 
                    builder.append( String.format("%s, %s, ", Integer.toHexString(m.getTargetAddress()).toUpperCase(), m.getCallSign()));
                    
                    // LONGTITUDE, LATITUDE, 
                    if (m.getHighResolutionPosition() != null) {
                        builder.append( String.format("%s, %s, ", m.getHighResolutionPosition().getLongtitude(), m.getHighResolutionPosition().getLatitude()));
                    } else { builder.append(" NULL, NULL, "); }
                    
                    // TIME OF APPLICABILITY FOR POS, 
                    if (m.getTimeOfAplicabilityPosition() != null) {
                        builder.append( String.format("%s, ", convertTimeToString(m.getTimeOfAplicabilityPosition())));
                    } else { builder.append("NULL, "); }
                    
                    // TRUE AIR SPEED, 
                    if (m.getTrueAirSpeed()!= null) {
                        builder.append( String.format("%s, ", m.getTrueAirSpeed()));
                    } else { builder.append("NULL, "); }
                    
                    // TIME OF RECEPTION OF POS, 
                    if (m.getTimeOfMessageReceptionOfPosition()!= null) {
                        builder.append(String.format("%s, ", convertTimeToString(m.getTimeOfMessageReceptionOfPosition())));
                    } else { builder.append("NULL, "); }
                    
                    // MODE 3A, 
                    if (m.getMode3a()!= null) {
                        builder.append(String.format("%s, ", m.getMode3a()));
                    } else { builder.append("NULL, "); }
                    
                    // FLIGHT LEVEL, 
                    if (m.getFlightLevel()!= null) {
                        builder.append(String.format("%s, ", m.getFlightLevel()));
                    } else { builder.append("NULL, "); }
                    
                    // MAGNETIC HEADING, 
                    if (m.getMagneticHeading()!= null) {
                        builder.append(String.format("%s, ", m.getMagneticHeading()));
                    } else { builder.append("NULL, "); }
                    
                    // GROUND SPEED, GROUND HEADING
                    if (m.getAirborneGroundVector() != null) {
                        builder.append(String.format("%s, %s, ", m.getAirborneGroundVector().getGroundSpeed(), m.getAirborneGroundVector().getTrackAngle()));
                    } else { builder.append("NULL, NULL, "); }
                    
                    // TIME OF REPORT TRANSMISSION
                    if (m.getTimeOfReportTranmission()!= null) {
                        builder.append(String.format("%s, ", convertTimeToString(m.getTimeOfReportTranmission())));
                    } else { builder.append("NULL, "); }
                    
                    // TIME OF REPORT TRANSMISSION
                    if (m.getQualityIndicator()!= null) {
                        builder.append(String.format("%s, %s, %s\n", m.getQualityIndicator().getnIC(), m.getQualityIndicator().getnACForPosition(), m.getQualityIndicator().getsIL()));
                    } else { builder.append("NULL, NULL, NULL\n"); }
                   
                    bw.write(builder.toString());
                    System.out.println(builder.toString());
                }
                counting++;
                // if (counting == 100) break;
            } while (numRead > 0);
            bw.close();
            long time2 = System.nanoTime();
            long time3 = time2 - time1;
            System.out.println("Message count: " + counting);
            System.out.println("Time count: " + time3);
        } catch (IOException ex) {
            // Logger.getLogger(CheckRawData.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }
    }
    
    public static void main (String [] args) throws IOException {
        Decode2File test = new Decode2File();
        // test.read("/media/andh/working/Projects/ADSB/2014/attech/old_adsb/16241.dat");
        // test.read("C:\\Users\\an\\Downloads\\Compressed\\SELEX\\SELEX\\data.bin");
        // test.read("D:\\temp\\Recorded\\112\\2015-06-22.15-00-00.010.0000.rcd");
//        test.read("D:\\temp\\ADS_B_Capture\\BI");
        test.read("/media/andh/working/temp/ADS_B_Capture/BI");
//        test.read("D:\\temp\\ADS_B_Capture\\BI_1");
//        test.read("D:\\temp\\ADS_B_Capture\\BI_2");
//        test.read("D:\\temp\\ADS_B_Capture\\BI_3");
//        test.read("D:\\temp\\ADS_B_Capture\\BN");
//        test.read("D:\\temp\\ADS_B_Capture\\BV");
        
    }
    
    private String convertTimeToString(double time) {
        long seconds = (long) time;
        double remain = (double) time - seconds;
        long hour = TimeUnit.SECONDS.toHours(seconds);
        long minute = TimeUnit.SECONDS.toMinutes(seconds) - TimeUnit.HOURS.toMinutes(hour);
        long second = seconds - TimeUnit.MINUTES.toSeconds(minute) - TimeUnit.HOURS.toSeconds(hour);
        return String.format("%s:%s:%s.%s", hour, minute, second, Math.round(remain * 10000));
    }
}
