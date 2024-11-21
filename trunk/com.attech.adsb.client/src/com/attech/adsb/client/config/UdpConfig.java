/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.config;

import com.attech.adsb.client.common.XmlSerializer;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author andh
 */
@XmlRootElement(name = "UDP")
@XmlAccessorType(XmlAccessType.NONE)
public class UdpConfig extends XmlSerializer {



    @XmlElement(name = "Mode")
    private String mode;

    @XmlElement(name = "BindingIP")
    private String bindingIP;

    @XmlElement(name = "Port")
    private int port;

    @XmlElement(name = "MulticastIP")
    private String multicastIP;

    @XmlElement(name = "BufferSize")
    private int bufferSize;

    @XmlElement(name = "RetryInterval")
    private int retryInterval;

    @XmlElement(name = "RecordEnable")
    private boolean record;

    @XmlElement(name = "DataLocation")
    private String recordingLocation;

    @XmlElement(name = "DataLimit")
    private int recordingLimit;

    @XmlElement(name = "IdentifyString")
    private String identfyString;

    public void UdpConfig() {
    }

    public static void main(String[] args) {
//        UdpConfig udpconfig = new UdpConfig();
//        udpconfig.setMode("UNICAST");
//        udpconfig.setBindingIP("127.0.0.1");
//        udpconfig.setPort(20550);
//        udpconfig.setRecord(true);
//        udpconfig.setRecordingLimit(30);
//        udpconfig.setRecordingLocation("D:\\Tmp\\Record\\");
//        udpconfig.setIdentfyString("sector01");
//        udpconfig.save("udp.xml");
//        System.out.println("Done");
    }

    //<editor-fold defaultstate="collapsed" desc=" Class properties ">   
    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getBindingIP() {
        return bindingIP;
    }

    public void setBindingIP(String bindingIP) {
        this.bindingIP = bindingIP;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getMulticastIP() {
        return multicastIP;
    }

    public void setMulticastIP(String multicastIP) {
        this.multicastIP = multicastIP;
    }

    public int getBufferSize() {
        return bufferSize;
    }

    public void setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    public int getRetryInterval() {
        return retryInterval;
    }

    public void setRetryInterval(int retryInterval) {
        this.retryInterval = retryInterval;
    }

    /**
     * @return the record
     */
    public boolean isRecord() {
        return record;
    }

    /**
     * @param record the record to set
     */
    public void setRecord(boolean record) {
        this.record = record;
    }

    /**
     * @return the recordingLocation
     */
    public String getRecordingLocation() {
        return recordingLocation;
    }

    /**
     * @param recordingLocation the recordingLocation to set
     */
    public void setRecordingLocation(String recordingLocation) {
        this.recordingLocation = recordingLocation;
    }

    /**
     * @return the recordingLimit
     */
    public int getRecordingLimit() {
        return recordingLimit;
    }

    /**
     * @param recordingLimit the recordingLimit to set
     */
    public void setRecordingLimit(int recordingLimit) {
        this.recordingLimit = recordingLimit;
    }
    
        /**
     * @return the identfyString
     */
    public String getIdentfyString() {
        return identfyString;
    }

    /**
     * @param identfyString the identfyString to set
     */
    public void setIdentfyString(String identfyString) {
        this.identfyString = identfyString;
    }

    //</editor-fold>
}
