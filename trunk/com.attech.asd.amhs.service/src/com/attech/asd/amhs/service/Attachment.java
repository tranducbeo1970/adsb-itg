/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.amhs.service;



/**
 *
 * @author ANDH
 */
public class Attachment {

    private String name;
    private byte[] data;

    public Attachment() {
    }

    public Attachment(String name, byte[] bytes) {
        this.name = name;
        this.data = bytes;
    }
    

    //<editor-fold defaultstate="collapsed" desc="Class property methods">
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the data
     */
    public byte[] getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(byte[] data) {
        this.data = data;
    }

    //</editor-fold>


}
