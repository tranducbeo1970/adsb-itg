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
public class ModeACode {
    private boolean notValidated;
    private boolean garbled;
    private int source;
    private int value;

    public static int extract(byte [] bytes, int index, ModeACode code){
        if (!Byter.validateIndex(index, bytes.length, 2)) return -1;
        byte cbyte = bytes[index++];
        code.setNotValidated((cbyte & 0x80) > 0);
        code.setGarbled((cbyte & 0x40) > 0);
        code.setSource((cbyte >> 5 & 0x01 ));
        int val = (cbyte & 0x0F << 8) | (bytes[index++] & 0xFF);
        code.setValue(Integer.parseInt(Integer.toOctalString(val)));
        return 2;
    }
    
    
    /**
     * @return the notValidated
     */
    public boolean isNotValidated() {
        return notValidated;
    }

    /**
     * @param notValidated the notValidated to set
     */
    public void setNotValidated(boolean notValidated) {
        this.notValidated = notValidated;
    }

    /**
     * @return the garbled
     */
    public boolean isGarbled() {
        return garbled;
    }

    /**
     * @param garbled the garbled to set
     */
    public void setGarbled(boolean garbled) {
        this.garbled = garbled;
    }

    /**
     * @return the source <br/>
     * 0 MODE 3/A code as derived from the reply of the transponder, <br/>
     * 1 Smoothed MODE 3/A code as provided by a sensor local tracker.
     */
    public int getSource() {
        return source;
    }

    /**
     * @param source <br/>
     * 0 MODE 3/A code as derived from the reply of the transponder, <br/>
     * 1 Smoothed MODE 3/A code as provided by a sensor local tracker.
     */
    public void setSource(int source) {
        this.source = source;
    }

    /**
     * @return the value
     */
    public int getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(int value) {
        this.value = value;
    }
    
    
}
