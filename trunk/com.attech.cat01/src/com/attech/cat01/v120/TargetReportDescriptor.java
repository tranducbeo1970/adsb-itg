/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.attech.cat01.v120;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author andh
 */
@XmlRootElement(name = "TargetReportDescriptor")
@XmlAccessorType(XmlAccessType.NONE)
public class TargetReportDescriptor {
    
    @XmlElement(name = "track")
    private boolean track;
    
    @XmlElement(name = "simulate")
    private boolean simulate;
    
    @XmlElement(name = "type")
    private int type; // 0 - Nodetection | 1 - Sole primary detection | 2 - sole secondary detection | 3 - combined
    
    @XmlElement(name = "anntena-no")
    private int antennaNo;
    
    @XmlElement(name = "special-position-id")
    private boolean specialPostionId;
    
    @XmlElement(name = "fixed-transponder")
    private boolean fixedTransponder;
    
    @XmlElement(name = "tested")
    private boolean tested;
    
    @XmlElement(name = "status")
    private int status; // 0 - default | 1 - 7500 unlawful | 2 - 7600 radio fail | 3 - 7700 emergency
    
    @XmlElement(name = "military-emergency")
    private boolean militaryEmergency;
    
    @XmlElement(name = "military-id")
    private boolean militaryId;

    /**
     * @return the track
     */
    public boolean isTrack() {
        return track;
    }

    /**
     * @param track the track to set
     */
    public void setTrack(boolean track) {
        this.track = track;
    }

    /**
     * @return the simulate
     */
    public boolean isSimulate() {
        return simulate;
    }

    /**
     * @param simulate the simulate to set
     */
    public void setSimulate(boolean simulate) {
        this.simulate = simulate;
    }

    /**
     * @return the type <br/>
     * 0 - Nodetection <br/>
     * 1 - Sole primary detection <br/>
     * 2 - sole secondary detection <br/>
     * 3 - combined
     */
    public int getType() {
        return type;
    }

    /**
     * @param type <br/>
     * 0 - Nodetection <br/>
     * 1 - Sole primary detection <br/>
     * 2 - sole secondary detection <br/>
     * 3 - combined
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * @return the antennaNo
     */
    public int getAntennaNo() {
        return antennaNo;
    }

    /**
     * @param antennaNo the antennaNo to set
     */
    public void setAntennaNo(int antennaNo) {
        this.antennaNo = antennaNo;
    }

    /**
     * @return the specialPostionId
     */
    public boolean isSpecialPostionId() {
        return specialPostionId;
    }

    /**
     * @param specialPostionId the specialPostionId to set
     */
    public void setSpecialPostionId(boolean specialPostionId) {
        this.specialPostionId = specialPostionId;
    }

    /**
     * @return the fixedTransponder
     */
    public boolean isFixedTransponder() {
        return fixedTransponder;
    }

    /**
     * @param fixedTransponder the fixedTransponder to set
     */
    public void setFixedTransponder(boolean fixedTransponder) {
        this.fixedTransponder = fixedTransponder;
    }

    /**
     * @return the tested
     */
    public boolean isTested() {
        return tested;
    }

    /**
     * @param tested the tested to set
     */
    public void setTested(boolean tested) {
        this.tested = tested;
    }

    /**
     * @return the status <br/>
     * 0 - default <br/> 
     * 1 - 7500 unlawful <br/> 
     * 2 - 7600 radio fail <br/>
     * 3 - 7700 emergency
     */
    public int getStatus() {
        return status;
    }

    /**
     * @param status the status to set<br/>
     * 0 - default <br/> 
     * 1 - 7500 unlawful <br/> 
     * 2 - 7600 radio fail <br/>
     * 3 - 7700 emergency
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * @return the militaryEmergency
     */
    public boolean isMilitaryEmergency() {
        return militaryEmergency;
    }

    /**
     * @param militaryEmergency the militaryEmergency to set
     */
    public void setMilitaryEmergency(boolean militaryEmergency) {
        this.militaryEmergency = militaryEmergency;
    }

    /**
     * @return the militaryId
     */
    public boolean isMilitaryId() {
        return militaryId;
    }

    /**
     * @param militaryId the militaryId to set
     */
    public void setMilitaryId(boolean militaryId) {
        this.militaryId = militaryId;
    }
    
    private String getTypeStr() {
        // 0 - Nodetection | 1 - Sole primary detection | 2 - sole secondary detection | 3 - combined
        switch(this.type) {
            case 0:
                return "Nodetection(0)";
            case 1:
                return "Sole-primary-detection(1)";
            case 2:
                return "Sole-secondary-detection(2)";
            case 3:
                return "Combine(3)";
            default:
                return String.format("Unknown(%s)", this.type);
        }
    }
    
    private String getStatusStr() {
        // 0 - default | 1 - 7500 unlawful | 2 - 7600 radio fail | 3 - 7700 emergency
        switch(this.status) {
            case 0:
                return "Default(0)";
            case 1:
                return "7500-unlawful(1)";
            case 2:
                return "7600-radio-fail(2)";
            case 3:
                return "7700-emergency(3)";
            default:
                return String.format("Unknown(%s)", this.status);
        }
    }
    
    @Override
    public String toString() {
        String trackS = this.track ? "track (1)" : "plot(0)";
        String simulatS = this.simulate ? "simulate(1)" : "not-simulate(0)";
        String typeS = getTypeStr();
        String specialPosID = this.specialPostionId ? "special-position-id(1)" : "non-special-position-id(0)";
        String fixTransponder = this.fixedTransponder ? "Fixed-transponder(1)" : "Non-fixed-transponder(0)";
        String test = this.tested ? "Test(1)" : "Not-tested(0)";
        String statusS = getStatusStr();
        return String.format("%s | %s | %s | Antenne-no: %s | %s | %s | %s | %s | Military-emergency: %s | Military-ID: %s", trackS, simulatS, typeS, this.antennaNo, specialPosID, fixTransponder, test, statusS, this.militaryEmergency, this.militaryId);
    }
    
    public void print(int level) {
        String trackS = this.track ? "track (1)" : "plot(0)";
        String simulatS = this.simulate ? "simulate(1)" : "not-simulate(0)";
        String typeS = getTypeStr();
        String specialPosID = this.specialPostionId ? "special-position-id(1)" : "non-special-position-id(0)";
        String fixTransponder = this.fixedTransponder ? "Fixed-transponder(1)" : "Non-fixed-transponder(0)";
        String test = this.tested ? "Test(1)" : "Not-tested(0)";
        String statusS = getStatusStr();
        
        String indent = Common.getLevel(level);
        System.out.println(String.format("%sTargetReportDescriptor", indent));
        System.out.println(String.format("\t%s%s", indent, trackS));
        System.out.println(String.format("\t%s%s", indent, simulatS));
        System.out.println(String.format("\t%s%s", indent, typeS));
        System.out.println(String.format("\t%sAntenna: %s", indent, antennaNo));
        System.out.println(String.format("\t%s%s", indent, specialPosID));
        System.out.println(String.format("\t%s%s", indent, fixTransponder));
        System.out.println(String.format("\t%s%s", indent, test));
        System.out.println(String.format("\t%s%s", indent, statusS));
        
    }
    
    public static int extract(byte[] bytes, int index, TargetReportDescriptor targetReportDescriptor) {
        if (!Byter.validateIndex(index, bytes.length, 1)) return -1;
        byte b = bytes[index++];
        targetReportDescriptor.setTrack(Byter.extract(b, 7) == 1);
        targetReportDescriptor.setSimulate(Byter.extract(b, 6) == 1);
        targetReportDescriptor.setType(Byter.extract(b, 4, 2));
        targetReportDescriptor.setAntennaNo(Byter.extract(b, 3));
        targetReportDescriptor.setSpecialPostionId(Byter.extract(b, 2) == 1);
        targetReportDescriptor.setFixedTransponder(Byter.extract(b, 1) == 1);
        boolean extention = (Byter.extract(b, 0) == 1);
        if (!extention) return 1;
        
        if (!Byter.validateIndex(index, bytes.length, 1)) return -1;
        b = bytes[index++];
        targetReportDescriptor.setTested(Byter.extract(b, 7) == 1);
        targetReportDescriptor.setStatus(Byter.extract(b, 5, 2));
        targetReportDescriptor.setMilitaryEmergency(Byter.extract(b, 4) == 1);
        targetReportDescriptor.setMilitaryId(Byter.extract(b, 3) == 1);
        extention = (Byter.extract(b, 0) == 1);
        if (!extention) return 2;
        
        int count = 2;
        while (extention) {
            if (!Byter.validateIndex(index, bytes.length, 1)) return count;
            b = bytes[index++];
            count++;
            extention = (Byter.extract(b, 0) == 1);
        }
        return count;
    }
}
