/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.cat62.v112;

/**
 *
 * @author andh
 */
public class Mode5PIN {
    private int pin;
    private int nationalCode;
    private int missionCode;
    
    public static int extract(byte[] bytes, int index, Mode5PIN code) {
        if (!Byter.validateIndex(index, bytes.length, 4)) return -1;
        code.setPin((bytes[index++] & 0x3F) << 8 | (bytes[index++] & 0xFF));
        code.setNationalCode(bytes[index++] & 0x1F);
        code.setMissionCode(bytes[index++] & 0x3F);
        return 4;
    }

    /**
     * @return the pin
     */
    public int getPin() {
        return pin;
    }

    /**
     * @param pin the pin to set
     */
    public void setPin(int pin) {
        this.pin = pin;
    }

    /**
     * @return the nationalCode
     */
    public int getNationalCode() {
        return nationalCode;
    }

    /**
     * @param nationalCode the nationalCode to set
     */
    public void setNationalCode(int nationalCode) {
        this.nationalCode = nationalCode;
    }

    /**
     * @return the missionCode
     */
    public int getMissionCode() {
        return missionCode;
    }

    /**
     * @param missionCode the missionCode to set
     */
    public void setMissionCode(int missionCode) {
        this.missionCode = missionCode;
    }
    
}
