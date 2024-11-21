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
public class StandStatus {
    private int emp;
    private int avl;
    
    public static int decode(byte [] bytes, int index, StandStatus code){
        if (!Byter.validateIndex(index, bytes.length, 1)) return -1;
        byte cbyte = bytes[index++];
        code.setEmp(cbyte >> 6 & 0x03);
        code.setAvl(cbyte >> 4 & 0x03);
        return 1;
    }
    
    public byte [] encode() {
        return new byte [] { (byte) (emp & 0x07 << 5 | avl & 0x07 << 4) };
    }

    /**
     * @return the emp <br/>
     * 00 Empty <br/>
     * 01 Occupied <br/>
     * 10 Unknown <br/>
     * 11 Invalid
     */
    public int getEmp() {
        return emp;
    }

    /**
     * @param emp <br/>
     * 00 Empty <br/>
     * 01 Occupied <br/>
     * 10 Unknown <br/>
     * 11 Invalid
     */
    public void setEmp(int emp) {
        this.emp = emp;
    }

    /**
     * @return the avl <br/>
     * 00 Available <br/>
     * 01 Not available <br/>
     * 10 Unknown <br/>
     * 11 Invalid
     */
    public int getAvl() {
        return avl;
    }

    /**
     * @param avl <br/>
     * 00 Available <br/>
     * 01 Not available <br/>
     * 10 Unknown <br/>
     * 11 Invalid
     */
    public void setAvl(int avl) {
        this.avl = avl;
    }
}
