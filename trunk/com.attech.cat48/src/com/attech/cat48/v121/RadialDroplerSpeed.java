/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.cat48.v121;

/**
 *
 * @author hong
 */
public class RadialDroplerSpeed {
    private boolean subfied1;
    private boolean subfied2;
    
    private boolean subfield1Valid;
    private int subfield1CalculatedValue;
    
    private int subfield2RepetitionFactor;
    private int subfield2DroplerSpeed;
    private int subfield2AmbiguityRange;
    private int subfield2TranmitterFrequency;
    
    public RadialDroplerSpeed() {
    }
    

    /**
     * @return the subfied1
     */
    public boolean isSubfied1() {
        return subfied1;
    }

    /**
     * @param subfied1 the subfied1 to set
     */
    public void setSubfied1(boolean subfied1) {
        this.subfied1 = subfied1;
    }

    /**
     * @return the subfied2
     */
    public boolean isSubfied2() {
        return subfied2;
    }

    /**
     * @param subfied2 the subfied2 to set
     */
    public void setSubfied2(boolean subfied2) {
        this.subfied2 = subfied2;
    }

    /**
     * @return the subfield1Valid
     */
    public boolean isSubfield1Valid() {
        return subfield1Valid;
    }

    /**
     * @param subfield1Valid the subfield1Valid to set
     */
    public void setSubfield1Valid(boolean subfield1Valid) {
        this.subfield1Valid = subfield1Valid;
    }
    
    /**
     * @return the subfield1CalculatedValue
     */
    public int getSubfield1CalculatedValue() {
        return subfield1CalculatedValue;
    }

    /**
     * @param subfield1CalculatedValue the subfield1CalculatedValue to set
     */
    public void setSubfield1CalculatedValue(int subfield1CalculatedValue) {
        this.subfield1CalculatedValue = subfield1CalculatedValue;
    }

    /**
     * @return the subfield2RepetitionFactor
     */
    public int getSubfield2RepetitionFactor() {
        return subfield2RepetitionFactor;
    }

    /**
     * @param subfield2RepetitionFactor the subfield2RepetitionFactor to set
     */
    public void setSubfield2RepetitionFactor(int subfield2RepetitionFactor) {
        this.subfield2RepetitionFactor = subfield2RepetitionFactor;
    }

    /**
     * @return the subfield2DroplerSpeed
     */
    public int getSubfield2DroplerSpeed() {
        return subfield2DroplerSpeed;
    }

    /**
     * @param subfield2DroplerSpeed the subfield2DroplerSpeed to set
     */
    public void setSubfield2DroplerSpeed(int subfield2DroplerSpeed) {
        this.subfield2DroplerSpeed = subfield2DroplerSpeed;
    }

    /**
     * @return the subfield2AmbiguityRange
     */
    public int getSubfield2AmbiguityRange() {
        return subfield2AmbiguityRange;
    }

    /**
     * @param subfield2AmbiguityRange the subfield2AmbiguityRange to set
     */
    public void setSubfield2AmbiguityRange(int subfield2AmbiguityRange) {
        this.subfield2AmbiguityRange = subfield2AmbiguityRange;
    }

    /**
     * @return the subfield2TranmitterFrequency
     */
    public int getSubfield2TranmitterFrequency() {
        return subfield2TranmitterFrequency;
    }

    /**
     * @param subfield2TranmitterFrequency the subfield2TranmitterFrequency to set
     */
    public void setSubfield2TranmitterFrequency(int subfield2TranmitterFrequency) {
        this.subfield2TranmitterFrequency = subfield2TranmitterFrequency;
    }
    
    public static int extract(byte[] bytes, int index, RadialDroplerSpeed droplerSpeed) {
        if (!Byter.validateIndex(index, bytes.length, 1)) {
            return -1;
        }
        byte cbyte = bytes[index++];
        boolean subfield1 = Byter.extract(cbyte, 7) > 0;
        boolean subfield2 = Byter.extract(cbyte, 6) > 0;
        droplerSpeed.setSubfied1(subfield1);
        droplerSpeed.setSubfied1(subfield2);
        int count = 1;

        if (subfield1) {
            if (!Byter.validateIndex(index, bytes.length, 2)) {
                return count;
            }
            cbyte = bytes[index++];
            droplerSpeed.setSubfield1Valid(Byter.extract(cbyte, 7) > 0);
            droplerSpeed.setSubfield1CalculatedValue(getCalculatedDroplerSpeed(cbyte, bytes[index++]));
            count += 2;
        }

        if (subfield2) {
            if (!Byter.validateIndex(index, bytes.length, 2)) {
                return count;
            }
            int ivalue = bytes[index++] & 0xFF;
            droplerSpeed.setSubfield2RepetitionFactor(ivalue);

            ivalue = (bytes[index++] & 0xFF) << 8 | (bytes[index++] & 0xFF);
            droplerSpeed.setSubfield2DroplerSpeed(ivalue);

            ivalue = (bytes[index++] & 0xFF) << 8 | (bytes[index++] & 0xFF);
            droplerSpeed.setSubfield2AmbiguityRange(ivalue);

            ivalue = (bytes[index++] & 0xFF) << 8 | (bytes[index++] & 0xFF);
            droplerSpeed.setSubfield2TranmitterFrequency(ivalue);
            count += 7;
        }
        return count;
    }

    private static int getCalculatedDroplerSpeed(byte byte1, byte byte2) {
        int lat = byte1 & 0x03;
        boolean positive = (int) (lat & 0x02) == 0;
        if (positive) {
            lat = lat << 8 | (byte2 & 0xFF);
        } else {
            lat = ~lat & 0xFF;
            lat = lat << 8 | (~byte2 & 0xFF);
            lat = lat + 0x01;
            lat = -lat;
        }
        return lat;
    }
    
    public void print(int level) {
        String indent = Common.getLevel(level);
        System.out.println(String.format("%sRadial Dropler Speed", indent));
        if (subfied1) {
            System.out.println(String.format("\t%sSubfield1", indent));
            System.out.println(String.format("\t\t%sValid: %s", indent, subfield1Valid));
            System.out.println(String.format("\t\t%sCalculated speed: %s", indent, subfield1CalculatedValue));
        }
        
        if (subfied2) {
            System.out.println(String.format("\t%sSubfield2", indent));
            System.out.println(String.format("\t\t%sRepetition factor: %s", indent, subfield2RepetitionFactor));
            System.out.println(String.format("\t\t%sDropler speed: %s", indent, subfield2DroplerSpeed));
            System.out.println(String.format("\t\t%sAmbiguity range: %s", indent, subfield2AmbiguityRange));
            System.out.println(String.format("\t\t%sTransmitter frequency: %s", indent, subfield2TranmitterFrequency));
        }
    }
}
