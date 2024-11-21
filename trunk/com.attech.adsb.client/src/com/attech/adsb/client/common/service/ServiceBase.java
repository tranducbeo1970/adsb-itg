/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.common.service;

import com.attech.adsb.client.config.Configuration;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

/**
 *
 * @author ANDH
 */
public abstract class ServiceBase {

    
    protected final String serviceUrl = Configuration.instance().getDataServiceCfg().getUrl();
    protected final String absoluteUrl;
    protected final Client client = ClientBuilder.newClient();
    protected final WebTarget webTarget;
    
    public ServiceBase () {
        this.absoluteUrl = String.format("%s%s", serviceUrl, getServicePath());
        this.webTarget = client.target(this.absoluteUrl);
    }
    
   public abstract String getServicePath() ;
    
}
