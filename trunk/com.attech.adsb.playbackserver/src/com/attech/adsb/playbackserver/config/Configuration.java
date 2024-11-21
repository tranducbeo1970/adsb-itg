/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.playbackserver.config;

import com.attech.adsb.playbackserver.XmlSerializer;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Sonlt
 */
@XmlRootElement(name = "Config")
@XmlAccessorType(XmlAccessType.NONE)
public class Configuration {

    @XmlElement(name = "Interval")
    private Integer interval;

    @XmlElement(name = "ReadingMode")
    private ReadMode readMode;

    @XmlElement(name = "BroadCasters")
    private List<BroadCasterConfig> broadCasterConfigs;

    public Configuration() {
        broadCasterConfigs = new ArrayList<>();
    }

    public void save(String file) {
        XmlSerializer.serialize(file, this);
    }

    public static Configuration load(String file) {
        return (Configuration) XmlSerializer.deserialize(file, Configuration.class);
    }

    public static void main(String[] args) throws Exception {
        Configuration config = new Configuration();
        config.setInterval(500);
        config.setReadMode(ReadMode.Raw);

        config.addBroadCasterConfig(new BroadCasterConfig("localhost", 20552));
        config.addBroadCasterConfig(new BroadCasterConfig("127.0.0.1", 20552));

        config.save("config.xml");
    }

    public void addBroadCasterConfig(BroadCasterConfig broadCasterConfig) {
        this.broadCasterConfigs.add(broadCasterConfig);
    }

    /**
     * @return the interval
     */
    public Integer getInterval() {
        return interval;
    }

    /**
     * @param interval the interval to set
     */
    public void setInterval(Integer interval) {
        this.interval = interval;
    }

    /**
     * @return the readMode
     */
    public ReadMode getReadMode() {
        return readMode;
    }

    /**
     * @param readMode the readMode to set
     */
    public void setReadMode(ReadMode readMode) {
        this.readMode = readMode;
    }

    /**
     * @return the broadCasterConfigs
     */
    public List<BroadCasterConfig> getBroadCasterConfigs() {
        return broadCasterConfigs;
    }

    /**
     * @param broadCasterConfigs the broadCasterConfigs to set
     */
    public void setBroadCasterConfigs(List<BroadCasterConfig> broadCasterConfigs) {
        this.broadCasterConfigs = broadCasterConfigs;
    }

}
