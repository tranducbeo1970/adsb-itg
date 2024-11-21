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
 * @author andh
 */
public class TrackStatus {
    
    private boolean monoSensorTrack;
    private boolean spiPresent;
    private boolean mostReliableHeightl;
    private int calculatedAltitudeSource;
    private boolean tentativeTrack;
            
    // Exention #1
    private Boolean simulatedTrack;
    private Boolean lastMessage;
    private Boolean firstMessage;
    private Boolean flightPlanCorrelated;
    private Boolean adsbInconsistentWithOtherSource;
    private Boolean slaveTrackPromotion;
    private Boolean backgroundService;
    
    // Exention #2
    private Boolean trackResultingOfAmalgamationProcess;
    private Integer mode4;
    private Boolean militaryEmergency;
    private Boolean militaryIdentification;
    private Integer mode5;
    
    // Exention #3
    private Boolean trackUpdatedAgeHigherSysThreshold;
    private Boolean psrUpdatedAgeHigherSysThreshold;
    private Boolean ssrUpdatedAgeHigherSysThreshold;
    private Boolean modeSUpdatedAgeHigherSysThreshold;
    private Boolean adsbUpdatedAgeHigherSysThreshold;
    private Boolean usedSpecialCode;
    private Boolean assignCodeConflict;

    public static int decode(byte[] bytes, int index, TrackStatus code) {

        if (!Byter.validateIndex(index, bytes.length, 1)) return -1;
        byte cByte = bytes[index++];
        code.setMonoSensorTrack((cByte & 0x80) > 0);
        code.setSpiPresent((cByte & 0x40) > 0);
        code.setMostReliableHeightl((cByte & 0x20) > 0);
        code.setCalculatedAltitudeSource(cByte >> 2 & 0x07);
        code.setTentativeTrack((cByte & 0x02) > 0);
        if ((cByte & 0x01) == 0 ) return 1;
        
        // Exention #1
        if (!Byter.validateIndex(index, bytes.length, 1)) return -1;
        cByte = bytes[index++];
        code.setSimulatedTrack((cByte & 0x80) > 0);
        code.setLastMessage((cByte & 0x40) > 0);
        code.setFirstMessage((cByte & 0x20) > 0);
        code.setFlightPlanCorrelated((cByte & 0x10) > 0);
        code.setAdsbInconsistentWithOtherSource((cByte & 0x08) > 0);
        code.setSlaveTrackPromotion((cByte & 0x04) > 0);
        code.setBackgroundService((cByte & 0x02) > 0);
        if ((cByte & 0x01) == 0 ) return 2;
        
        // Exention #2
        if (!Byter.validateIndex(index, bytes.length, 1)) return -1;
        cByte = bytes[index++];
        code.setTrackResultingOfAmalgamationProcess((cByte & 0x80) > 0);
        code.setMode4(cByte >> 5 & 0x03);
        code.setMilitaryEmergency((cByte & 0x10) > 0);
        code.setMilitaryIdentification((cByte & 0x08) > 0);
        code.setMode5(cByte >> 1 & 0x03);
        if ((cByte & 0x01) == 0 ) return 3;
        
        // Exention #3
        if (!Byter.validateIndex(index, bytes.length, 1)) return -1;
        cByte = bytes[index++];
        code.setTrackUpdatedAgeHigherSysThreshold((cByte & 0x80) > 0);
        code.setPsrUpdatedAgeHigherSysThreshold((cByte & 0x40) > 0);
        code.setSsrUpdatedAgeHigherSysThreshold((cByte & 0x20) > 0);
        code.setModeSUpdatedAgeHigherSysThreshold((cByte & 0x10) > 0);
        code.setAdsbUpdatedAgeHigherSysThreshold((cByte & 0x08) > 0);
        code.setUsedSpecialCode((cByte & 0x04) > 0);
        code.setAssignCodeConflict((cByte & 0x02) > 0);
        if ((cByte & 0x01) == 0 ) return 4;
        
        int count = 4;
        while ((cByte & 0x01) > 0) {
            if (!Byter.validateIndex(index, bytes.length, 1)) return -1;
            cByte = bytes[index++];
            count ++;
        }
        
        return count;
    }
    
    public List<Byte> encode() {
        final List<Byte> result = new ArrayList<>();
        final boolean ex1 = isByte1Extended();
        final boolean ex2 = isByte2Extended();
        final boolean ex3 = isByte3Extended();
        
        // First byte
        int b = 0x00;
        if (monoSensorTrack) b += 0x80;
        if (spiPresent) b += 0x40;
        if (mostReliableHeightl) b += 0x20;
        b += (calculatedAltitudeSource & 0x07) << 2;
        if (tentativeTrack) b += 0x02;
        if (ex1) b += 0x01;
        result.add((byte) b);
        if(ex1) result.add(getByte1(ex2));
        if(ex2) result.add(getByte2(ex3));
        if(ex3) result.add(getByte3());
        return result;
    }
    
    public byte [] encode2() {
        final boolean ex1 = isByte1Extended();
        final boolean ex2 = isByte2Extended();
        final boolean ex3 = isByte3Extended();
        
        // First byte
        int b = 0x00;
        if (monoSensorTrack) b += 0x80;
        if (spiPresent) b += 0x40;
        if (mostReliableHeightl) b += 0x20;
        b += (calculatedAltitudeSource & 0x07) << 2;
        if (tentativeTrack) b += 0x02;
        if (!ex1) return new byte[] {(byte)(b & 0xFF)};
        
        b += 0x01;
        byte b1 = getByte1(ex2);
        if (!ex2) return new byte[] {(byte)(b & 0xFF), b1};
        
        byte b2 = getByte2(ex3);
        if (!ex3) return new byte[] {(byte)(b & 0xFF), b1, b2};
        
        byte b3 = getByte3();
        return new byte[] {(byte)(b & 0xFF), b1, b2, b3};
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("Track Status: \n");
        builder.append(String.format("\t %s\n", monoSensorTrack ? "Multi-Sensor Track(0)": "Mono-Sensor Track(1)"));
        builder.append(String.format("\t %s\n", spiPresent ? "No-SPI(0)": "SPI(1)"));
        builder.append(String.format("\t %s\n", mostReliableHeightl ? 
                "Barometric altitude (Mode C) more reliable(0)": "Geometric altitude more reliable(1)"));
        builder.append(String.format("\t %s\n", getCalAltitudeSourceName()));
        builder.append(String.format("\t %s\n", tentativeTrack ? "Confirmed track(0)": "Tentative track(1)"));
        
        // Exention #1
        if (!isByte1Extended()) return builder.toString();
        builder.append("[Ext1]\n");
        builder.append(String.format("\t %s\n", simulatedTrack ? "Actual track(0)": "Simulated track(1)"));
        builder.append(String.format("\t %s\n", lastMessage ? 
                "Default value(0)": "last message transmitted to the user for the track(1)"));
        builder.append(String.format("\t %s\n", firstMessage ? 
                "Default value(0)": "first message transmitted to the user for the track(1)"));
        builder.append(String.format("\t %s\n", flightPlanCorrelated ? 
                "Not flight-plan correlated(0)": "Flight plan correlated(1)"));
        builder.append(String.format("\t %s\n", adsbInconsistentWithOtherSource ? 
                "Confirmed track(0)": "ADS-B data inconsistent with other surveillance information(1)"));
        builder.append(String.format("\t %s\n", slaveTrackPromotion ? 
                "Default value(0)": "Slave Track Promotion(1)"));
        builder.append(String.format("\t %s\n", backgroundService ? 
                "Complementary service used(0)": "Background service used(1)"));
       
        // Exention #2
        if (!isByte2Extended()) return builder.toString();
        builder.append("[Ext2]\n");
        builder.append(String.format("\t %s\n", trackResultingOfAmalgamationProcess
                ? "track not resulting from amalgamation process(0)" : "track resulting from amalgamation process(1)"));
        builder.append(String.format("\t %s\n", getMode4Name()));
        builder.append(String.format("\t %s\n", militaryEmergency ? "Default(0)" : "Military Emergency present(1)"));
        builder.append(String.format("\t %s\n", militaryIdentification ? "Default(0)" : "Military Identification present(1)"));
        builder.append(String.format("\t %s\n", getMode5Name()));
    

        // Exention #3
        if (!isByte3Extended()) return builder.toString();
        builder.append("[Ext3]\n");
        builder.append(String.format("\t %s\n", trackUpdatedAgeHigherSysThreshold
                ? "Default value(0)" : "Age of the last received track update is higher than system dependent threshold (coasting)(1)"));
        builder.append(String.format("\t %s\n", psrUpdatedAgeHigherSysThreshold
                ? "Default value(0)" : "Age of the last received PSR track update is higher than system dependent threshold(1)"));
        builder.append(String.format("\t %s\n", ssrUpdatedAgeHigherSysThreshold
                ? "Default value(0)" : "Age of the last received SSR track update is higher than system dependent threshold(1)"));
        builder.append(String.format("\t %s\n", modeSUpdatedAgeHigherSysThreshold
                ? "Default value(0)" : "Age of the last received ModeS track update is higher than system dependent threshold(1)"));
        builder.append(String.format("\t %s\n", adsbUpdatedAgeHigherSysThreshold
                ? "Default value(0)" : "Age of the last received ADSB track update is higher than system dependent threshold(1)"));
        builder.append(String.format("\t %s\n", usedSpecialCode
                ? "Default value(0)" : "Special Used Code (Mode A codes to be defined in the system to mark a track with special interest)(1)"));
        builder.append(String.format("\t %s\n", assignCodeConflict
                ? "Default value(0)" : "Assigned Mode A Code Conflict (same discrete Mode A Code assigned to another track)(1)"));
        return builder.toString();
    }
    
    private String getCalAltitudeSourceName() {
        switch (calculatedAltitudeSource) {
            case 0:
                return "No source (0)";
            case 1:
                return "GNSS (1)";
            case 2:
                return "3D radar (2)";
            case 3:
                return "Triangulation (3)";
            case 4:
                return "Height from coverage (4)";
            case 5:
                return "Speed look-up table (5)";
            case 6:
                return "Default height (6)";
            case 7:
                return "Multilateration (7)";
            default:
                return "Unknown(" + calculatedAltitudeSource + ")";
        }
    }

    private String getMode4Name() {
        switch (mode4) {
            case 0:
                return "No Mode 4 interrogation (0)";
            case 1:
                return "Friendly target (1)";
            case 2:
                return "Unknown target (2)";
            case 3:
                return "No reply (3)";
            default:
                return "Unknown (" + mode4 + ")";
        }
    }

    private String getMode5Name() {
        switch (mode5) {
            case 0:
                return "No Mode 5 interrogation (0)";
            case 1:
                return "Friendly target (1)";
            case 2:
                return "Unknown target (2)";
            case 3:
                return "No reply (3)";
            default:
                return "Unknown (" + mode5 + ")";
        }
    }

    private boolean isByte1Extended() {
        return simulatedTrack != null
                && lastMessage != null
                && firstMessage != null
                && flightPlanCorrelated != null
                && adsbInconsistentWithOtherSource != null
                && slaveTrackPromotion != null
                && backgroundService != null;
    }
    
    private byte getByte1(boolean isExtended) {
        int b = 0x00;
        if (simulatedTrack) b += 0x80;
        if (lastMessage) b += 0x40;
        if (firstMessage) b += 0x20;
        if (flightPlanCorrelated) b += 0x10;
        if (adsbInconsistentWithOtherSource) b += 0x08;
        if (slaveTrackPromotion) b += 0x04;
        if (backgroundService) b += 0x02;
        if (isExtended) b += 0x01;
        return (byte) b;
    }
    
    private boolean isByte2Extended() {
        return trackResultingOfAmalgamationProcess != null
                && mode4 != null
                && militaryEmergency != null
                && militaryIdentification != null
                && mode5 != null;
    }
    
    private byte getByte2(boolean isExtended) {
        int b = 0x00;
        if (trackResultingOfAmalgamationProcess) b += 0x80;
        b += ((mode4 & 0x03) << 5);
        if (militaryEmergency) b += 0x10;
        if (militaryIdentification) b += 0x08;
        b += ((mode5 & 0x03) << 1);
        if (isExtended) b += 0x01;
        return (byte) b;
    }
    
    private boolean isByte3Extended() {
        return trackUpdatedAgeHigherSysThreshold != null
                && psrUpdatedAgeHigherSysThreshold != null
                && ssrUpdatedAgeHigherSysThreshold != null
                && modeSUpdatedAgeHigherSysThreshold != null
                && adsbUpdatedAgeHigherSysThreshold != null
                && usedSpecialCode != null
                && assignCodeConflict != null;
    }
    
    private byte getByte3() {
        int b = 0x00;
        if (trackUpdatedAgeHigherSysThreshold) b += 0x80;
        if (psrUpdatedAgeHigherSysThreshold) b += 0x40;
        if (ssrUpdatedAgeHigherSysThreshold) b += 0x20;
        if (modeSUpdatedAgeHigherSysThreshold) b += 0x10;
        if (adsbUpdatedAgeHigherSysThreshold) b += 0x08;
        if (usedSpecialCode) b += 0x04;
        if (assignCodeConflict) b += 0x02;
        return (byte) b;
    }
    
    /**
     * @return the multiSensorTrack <br/>
     * 0 Multisensor track <br/>
     * 1 Monosensor track
     */
    public boolean isMonoSensorTrack() {
        return monoSensorTrack;
    }

    /**
     * @param monoSensorTrack <br/>
     * 0 Multisensor track <br/>
     * 1 Monosensor track
     */
    public void setMonoSensorTrack(boolean monoSensorTrack) {
        this.monoSensorTrack = monoSensorTrack;
    }

    /**
     * @return the spiPresent
     */
    public boolean isSpiPresent() {
        return spiPresent;
    }

    /**
     * @param spiPresent the spiPresent to set
     */
    public void setSpiPresent(boolean spiPresent) {
        this.spiPresent = spiPresent;
    }

    /**
     * @return the mostReliableHeightl <br/>
     * False: Barometric altitude (Mode C) more reliable <br/>
     * True: Geometric altitude more reliable
     */
    public boolean isMostReliableHeightl() {
        return mostReliableHeightl;
    }

    /**
     * @param mostReliableHeightl <br/>
     * False: Barometric altitude (Mode C) more reliable <br/>
     * True: Geometric altitude more reliable
     */
    public void setMostReliableHeightl(boolean mostReliableHeightl) {
        this.mostReliableHeightl = mostReliableHeightl;
    }

    /**
     * @return the calculatedAltitudeSource <br/>
     * 000 no source <br/>
     * 001 GNSS <br/>
     * 010 3D radar <br/>
     * 011 triangulation <br/>
     * 100 height from coverage <br/>
     * 101 speed look-up table <br/>
     * 110 default height <br/>
     * 111 multilateration
     */
    public int getCalculatedAltitudeSource() {
        return calculatedAltitudeSource;
    }

    /**
     * @param calculatedAltitudeSource <br/>
     * 000 no source <br/>
     * 001 GNSS <br/>
     * 010 3D radar <br/>
     * 011 triangulation <br/>
     * 100 height from coverage <br/>
     * 101 speed look-up table <br/>
     * 110 default height <br/>
     * 111 multilateration
     */
    public void setCalculatedAltitudeSource(int calculatedAltitudeSource) {
        this.calculatedAltitudeSource = calculatedAltitudeSource;
    }

    /**
     * @return the tentativeTrack
     */
    public boolean isTentativeTrack() {
        return tentativeTrack;
    }

    /**
     * @param tentativeTrack the tentativeTrack to set
     */
    public void setTentativeTrack(boolean tentativeTrack) {
        this.tentativeTrack = tentativeTrack;
    }

    /**
     * Subfield #1
     * @return the simulatedTrack
     */
    public Boolean isSimulatedTrack() {
        return simulatedTrack;
    }

    /**
     * Subfield #1
     * @param simulatedTrack the simulatedTrack to set
     */
    public void setSimulatedTrack(Boolean simulatedTrack) {
        this.simulatedTrack = simulatedTrack;
    }

    /**
     * Subfield #1
     * @return the lastMessage
     */
    public Boolean isLastMessage() {
        return lastMessage;
    }

    /**
     * Subfield #1
     * @param lastMessage the lastMessage to set
     */
    public void setLastMessage(Boolean lastMessage) {
        this.lastMessage = lastMessage;
    }

    /**
     * Subfield #1
     * @return the firstMessage
     */
    public Boolean isFirstMessage() {
        return firstMessage;
    }

    /**
     * Subfield #1
     * @param firstMessage the firstMessage to set
     */
    public void setFirstMessage(Boolean firstMessage) {
        this.firstMessage = firstMessage;
    }

    /**
     * Subfield #1
     * @return the flightPlanCorrelated
     */
    public Boolean isFlightPlanCorrelated() {
        return flightPlanCorrelated;
    }

    /**
     * Subfield #1
     * @param flightPlanCorrelated the flightPlanCorrelated to set
     */
    public void setFlightPlanCorrelated(Boolean flightPlanCorrelated) {
        this.flightPlanCorrelated = flightPlanCorrelated;
    }

    /**
     * Subfield #1
     * @return the adsbInconsistentWithOtherSource
     */
    public Boolean isAdsbInconsistentWithOtherSource() {
        return adsbInconsistentWithOtherSource;
    }

    /**
     * Subfield #1
     * @param adsbInconsistentWithOtherSource the adsbInconsistentWithOtherSource to set
     */
    public void setAdsbInconsistentWithOtherSource(Boolean adsbInconsistentWithOtherSource) {
        this.adsbInconsistentWithOtherSource = adsbInconsistentWithOtherSource;
    }

    /**
     * Subfield #1
     * @return the slaveTrackPromotion
     */
    public Boolean isSlaveTrackPromotion() {
        return slaveTrackPromotion;
    }

    /**
     * Subfield #1
     * @param slaveTrackPromotion the slaveTrackPromotion to set
     */
    public void setSlaveTrackPromotion(Boolean slaveTrackPromotion) {
        this.slaveTrackPromotion = slaveTrackPromotion;
    }

    /**
     * Subfield #1
     * @return the backgroundService
     */
    public Boolean isBackgroundService() {
        return backgroundService;
    }

    /**
     * Subfield #1
     * @param backgroundService the backgroundService to set
     */
    public void setBackgroundService(Boolean backgroundService) {
        this.backgroundService = backgroundService;
    }

    /**
     * Subfield #2
     * @return the trackResultingOfAmalgamationProcess
     */
    public Boolean isTrackResultingOfAmalgamationProcess() {
        return trackResultingOfAmalgamationProcess;
    }

    /**
     * Subfield #2
     * @param trackResultingOfAmalgamationProcess the trackResultingOfAmalgamationProcess to set
     */
    public void setTrackResultingOfAmalgamationProcess(Boolean trackResultingOfAmalgamationProcess) {
        this.trackResultingOfAmalgamationProcess = trackResultingOfAmalgamationProcess;
    }

    /**
     * Subfield #2
     * @return the mode4 <br/>
     * 00 No Mode 4 interrogation <br/>
     * 01 Friendly target <br/>
     * 10 Unknown target <br/>
     * 11 No reply
     */
    public Integer getMode4() {
        return mode4;
    }

    /**
     * Subfield #2
     * @param mode4 <br/>
     * 00 No Mode 4 interrogation <br/>
     * 01 Friendly target <br/>
     * 10 Unknown target <br/>
     * 11 No reply
     */
    public void setMode4(Integer mode4) {
        this.mode4 = mode4;
    }

    /**
     * Subfield #2
     * @return the militaryEmergency
     */
    public Boolean isMilitaryEmergency() {
        return militaryEmergency;
    }

    /**
     * Subfield #2
     * @param militaryEmergency the militaryEmergency to set
     */
    public void setMilitaryEmergency(Boolean militaryEmergency) {
        this.militaryEmergency = militaryEmergency;
    }

    /**
     * Subfield #2
     * @return the militaryIdentification
     */
    public Boolean isMilitaryIdentification() {
        return militaryIdentification;
    }

    /**
     * Subfield #2
     * @param militaryIdentification the militaryIdentification to set
     */
    public void setMilitaryIdentification(Boolean militaryIdentification) {
        this.militaryIdentification = militaryIdentification;
    }

    /**
     * Subfield #2
     * @return the mode5 <br/>
     * 00 No Mode 5 interrogation <br/>
     * 01 Friendly target <br/>
     * 10 Unknown target <br/>
     * 11 No reply
     */
    public Integer getMode5() {
        return mode5;
    }

    /**
     * Subfield #2
     * @param mode5 <br/>
     * 00 No Mode 5 interrogation <br/>
     * 01 Friendly target <br/>
     * 10 Unknown target <br/>
     * 11 No reply
     */
    public void setMode5(Integer mode5) {
        this.mode5 = mode5;
    }

    /**
     * Subfield #3
     * @return the trackUpdatedAgeHigherSysThreshold
     */
    public Boolean isTrackUpdatedAgeHigherSysThreshold() {
        return trackUpdatedAgeHigherSysThreshold;
    }

    /**
     * Subfield #3
     * @param trackUpdatedAgeHigherSysThreshold the trackUpdatedAgeHigherSysThreshold to set
     */
    public void setTrackUpdatedAgeHigherSysThreshold(Boolean trackUpdatedAgeHigherSysThreshold) {
        this.trackUpdatedAgeHigherSysThreshold = trackUpdatedAgeHigherSysThreshold;
    }

    /**
     * Subfield #3
     * @return the psrUpdatedAgeHigherSysThreshold
     */
    public Boolean isPsrUpdatedAgeHigherSysThreshold() {
        return psrUpdatedAgeHigherSysThreshold;
    }

    /**
     * Subfield #3
     * @param psrUpdatedAgeHigherSysThreshold the psrUpdatedAgeHigherSysThreshold to set
     */
    public void setPsrUpdatedAgeHigherSysThreshold(Boolean psrUpdatedAgeHigherSysThreshold) {
        this.psrUpdatedAgeHigherSysThreshold = psrUpdatedAgeHigherSysThreshold;
    }

    /**
     * Subfield #3
     * @return the ssrUpdatedAgeHigherSysThreshold
     */
    public Boolean isSsrUpdatedAgeHigherSysThreshold() {
        return ssrUpdatedAgeHigherSysThreshold;
    }

    /**
     * Subfield #3
     * @param ssrUpdatedAgeHigherSysThreshold the ssrUpdatedAgeHigherSysThreshold to set
     */
    public void setSsrUpdatedAgeHigherSysThreshold(Boolean ssrUpdatedAgeHigherSysThreshold) {
        this.ssrUpdatedAgeHigherSysThreshold = ssrUpdatedAgeHigherSysThreshold;
    }

    /**
     * Subfield #3
     * @return the modeSUpdatedAgeHigherSysThreshold
     */
    public Boolean isModeSUpdatedAgeHigherSysThreshold() {
        return modeSUpdatedAgeHigherSysThreshold;
    }

    /**
     * Subfield #3
     * @param modeSUpdatedAgeHigherSysThreshold the modeSUpdatedAgeHigherSysThreshold to set
     */
    public void setModeSUpdatedAgeHigherSysThreshold(Boolean modeSUpdatedAgeHigherSysThreshold) {
        this.modeSUpdatedAgeHigherSysThreshold = modeSUpdatedAgeHigherSysThreshold;
    }

    /**
     * Subfield #3
     * @return the adsbUpdatedAgeHigherSysThreshold
     */
    public Boolean isAdsbUpdatedAgeHigherSysThreshold() {
        return adsbUpdatedAgeHigherSysThreshold;
    }

    /**
     * Subfield #3
     * @param adsbUpdatedAgeHigherSysThreshold the adsbUpdatedAgeHigherSysThreshold to set
     */
    public void setAdsbUpdatedAgeHigherSysThreshold(Boolean adsbUpdatedAgeHigherSysThreshold) {
        this.adsbUpdatedAgeHigherSysThreshold = adsbUpdatedAgeHigherSysThreshold;
    }

    /**
     * Subfield #3
     * @return the usedSpecialCode <br/>
     * 0 Default value <br/>
     * 1 Special Used Code (Mode A codes to be defined in the system to mark a track with special interest)
     */
    public Boolean isUsedSpecialCode() {
        return usedSpecialCode;
    }

    /**
     * Subfield #3
     * @param usedSpecialCode <br/>
     * 0 Default value <br/>
     * 1 Special Used Code (Mode A codes to be defined in the system to mark a track with special interest)
     */
    public void setUsedSpecialCode(Boolean usedSpecialCode) {
        this.usedSpecialCode = usedSpecialCode;
    }

    /**
     * Subfield #3
     * @return the assignCodeConflict <br/>
     * False: default <br/>
     * True: Assigned Mode A Code Conflict (same discrete Mode A Code assigned to another track)
     */
    public Boolean isAssignCodeConflict() {
        return assignCodeConflict;
    }

    /**
     * Subfield #3
     * @param assignCodeConflict <br/>
     * False: default <br/>
     * True: Assigned Mode A Code Conflict (same discrete Mode A Code assigned to another track)
     */
    public void setAssignCodeConflict(Boolean assignCodeConflict) {
        this.assignCodeConflict = assignCodeConflict;
    }
    
}
