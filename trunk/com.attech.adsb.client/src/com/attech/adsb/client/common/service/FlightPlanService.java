/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.common.service;

import com.attech.adsb.client.common.MLogger;
import com.attech.adsb.client.common.Target;
import com.attech.adsb.client.dto.FlightPlan;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author ANDH
 */
public class FlightPlanService extends ServiceBase{

    private static final MLogger logger = MLogger.getLogger(FlightPlanService.class);

    public FlightPlanService() {
        super();
    }
    
    public void update(Target target) {
        if ( target == null || target.getInfo() != null) {
            return;
        }
        
        try {            
            
            // findbyaddress            
            WebTarget wsTarget = webTarget.path(String.format("findbytarget/%s/%s", target.getDof(), target.getCallSign()));            
            FlightPlan aircraft = wsTarget.request(MediaType.APPLICATION_XML).get(FlightPlan.class);    
            
            logger.info("Update flight plan of target %s:%s", target.getCallSign(), target. getAddress());
            if (aircraft != null) {
                target.setFpl(aircraft);
            }            
        } catch (InternalServerErrorException ex) {
            logger.error("Internal Server Error");
        } catch (Exception ex) {
            logger.error(ex);
        }
    }

    @Override
    public String getServicePath() {
        return "entities.flightplan";
    }
}
