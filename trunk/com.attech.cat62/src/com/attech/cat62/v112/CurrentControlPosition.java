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
public class CurrentControlPosition {
    private int centre;
    private int position;

    public static int decode(byte [] bytes, int index, CurrentControlPosition code){
        if (!Byter.validateIndex(index, bytes.length, 2)) return -1;
        code.setCentre(bytes[index++] & 0xFF);
        code.setPosition(bytes[index++] & 0xFF);
        return 2;
    }
    
    public byte [] encode() {
        return new byte[]{(byte) (this.centre & 0xFF), (byte) (this.position & 0xFF)};
    }

    /**
     * @return the centre
     */
    public int getCentre() {
        return centre;
    }

    /**
     * @param centre the centre to set
     */
    public void setCentre(int centre) {
        this.centre = centre;
    }

    /**
     * @return the position
     */
    public int getPosition() {
        return position;
    }

    /**
     * @param position the position to set
     */
    public void setPosition(int position) {
        this.position = position;
    }
    
    
    
}
