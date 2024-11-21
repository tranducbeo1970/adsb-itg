/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.cat62.v112;

/**
 *
 * @author andh
 */
public class AirSpeed {

    private int unit;
    public double value;

    public AirSpeed() {
    }
    
    public static int extract(byte [] bytes, int index, AirSpeed code){
        
        if (!Byter.validateIndex(index, bytes.length, 2)) return -1;
        byte currentByte = bytes[index++];
        final int unit = (currentByte >> 7) & 0x01;
        code.setUnit(unit);
        
        final int val = (currentByte & 0x7F) << 8 | (bytes[index++] & 0xFF);
        double dval = val * (unit == 1 ? 0.001 : Math.pow(2, -14));
        code.setValue(dval);
        return 2;
    }
    

    /**
     * @return <br/>
     * 0 (false) Air Speed = IAS, LSB (Bit-1) = 2-14 NM/s <br/>
     * 1 (true) Air Speed = Mach, LSB (Bit-1) = 0.001
     */
    public int getUnit() {
        return unit;
    }

    /**
     * @param unit 
     * 0 (false) Air Speed = IAS, LSB (Bit-1) = 2-14 NM/s <br/>
     * 1 (true) Air Speed = Mach, LSB (Bit-1) = 0.001
     */
    public void setUnit(int unit) {
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.format("%s %s", value, unit == 1 ? "Mach" : "NM/s");
    }
}
