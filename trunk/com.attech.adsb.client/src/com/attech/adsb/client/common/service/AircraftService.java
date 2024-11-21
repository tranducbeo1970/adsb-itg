/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.common.service;

import com.attech.adsb.client.common.MLogger;
import com.attech.adsb.client.common.Target;
import com.attech.adsb.client.config.Configuration;
import com.attech.adsb.client.dto.Aircrafts;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author hong
 */
public final class AircraftService {

    private static final MLogger logger = MLogger.getLogger(FlightControlService.class);
    private final static String OWNER_PATH = "entities.aircrafts";
    private final String serviceUrl = Configuration.instance().getDataServiceCfg().getUrl();
    private final String url;
    private final Client client = ClientBuilder.newClient();
    private final WebTarget webTarget;

    
    public AircraftService() {
        this.url = String.format("%s%s", serviceUrl, OWNER_PATH);
	this.webTarget = client.target(this.url);
    }
    
    public void update(Target target) {
        if ( target == null || target.getInfo() != null) {
            return;
        }
        
        try {
            // findbyaddress
            WebTarget wsTarget = webTarget.path(String.format("findbyaddress/%s", target.getAddress()));
            Aircrafts aircraft = wsTarget.request(MediaType.APPLICATION_XML).get(Aircrafts.class);
            
            logger.info("Update info of target %s:%s", target.getCallSign(), target. getAddress());
            if (aircraft != null) {
                target.setInfo(aircraft);
            }            
        } catch (InternalServerErrorException ex) {
            logger.error("Internal Server Error");
        } catch (Exception ex) {
            logger.error(ex);
        }
    }
    
}
