/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.config;

import com.attech.adsb.client.common.Common;
import com.attech.adsb.client.common.XmlSerializer;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ANDH
 */
@XmlRootElement(name = "DataService")
@XmlAccessorType(XmlAccessType.NONE)
public class DataServiceConfig {

    @XmlElement(name = "URL")
    private String url;

    /**
     * @return the url
     */
    public String getUrl() {
	return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
	this.url = url;
    }

    public static void main(String[] args) {

	DataServiceConfig dataservice = new DataServiceConfig();
	dataservice.setUrl("http://localhost:8080/com.attech.asd.dataservice/webresources");
	XmlSerializer.serialize("dataservice.xml", dataservice);

	DataServiceConfig st = XmlSerializer.load("dataservice.xml", DataServiceConfig.class);
	System.out.println("Load completed");
	System.out.println("ITEM number: " + st.getUrl());
    }
}
