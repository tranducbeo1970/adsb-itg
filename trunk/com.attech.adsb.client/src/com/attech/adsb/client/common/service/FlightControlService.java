/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.common.service;

import com.attech.adsb.client.common.MLogger;
import com.attech.adsb.client.common.Target;
import com.attech.adsb.client.common.TrackStatus;
import com.attech.adsb.client.config.Configuration;
import com.attech.adsb.client.dto.FlightControl;
import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author andh
 */
public class FlightControlService {
    private static final MLogger logger = MLogger.getLogger(FlightControlService.class);
    private final static String OWNER_PATH = "entities.flightcontrol";
    private final String serviceUrl = Configuration.instance().getDataServiceCfg().getUrl();
    private long period = 18000;

    private String sector;
    private String url;
    private Client client = ClientBuilder.newClient();
    private WebTarget webTarget;

    public FlightControlService() {
	this.url = String.format("%s%s", serviceUrl, OWNER_PATH);
	webTarget = client.target(this.url);
    }

    public FlightControlService(String sector) {
	this();
	this.sector = sector;
    }

    public void get(String id) { // DEBUG
	WebTarget wsTarget = webTarget.path(id);
	FlightControl flightControl = wsTarget.request(MediaType.APPLICATION_XML).get(FlightControl.class);
	
//	System.out.println("Get " + (flightControl != null)); p@ssw0rd2020
        
	if (flightControl != null) {
	    System.out.println(flightControl.getCallSign());
	    System.out.println(flightControl.getAddress());
	    System.out.println(flightControl.getController());
	}
    }

    public void update(Target target) {
        if (target.getCallSign() == null)
            return;
        WebTarget wsTarget = webTarget.path(target.getCallSign());
        FlightControl flightControl = wsTarget.request(MediaType.APPLICATION_XML).get(FlightControl.class);
        if (flightControl == null || !flightControl.getAddress().equalsIgnoreCase(target.getAddress()) || flightControl.isOsolete(period)) {
            target.setController(null);
            target.setTrackStatus(TrackStatus.NONE);
            return;
        }

        target.setController(flightControl.getController());
        if (flightControl.getTargetCotroller() != null && flightControl.getTargetCotroller().equalsIgnoreCase(sector)) {
            target.setTrackStatus(TrackStatus.TRANSFER_TO_THIS);
            return;
        }

        if (flightControl.getController().equalsIgnoreCase(this.sector) && flightControl.getTargetCotroller() != null && !flightControl.getTargetCotroller().isEmpty()) {
            target.setTrackStatus(TrackStatus.TRANSFER_FROM_THIS);
            return;
        }

        if (flightControl.getController().equalsIgnoreCase(this.sector)) {
            target.setTrackStatus(TrackStatus.CONTROLED);
            return;
        }

        target.setTrackStatus(TrackStatus.NONE);
    }
    
    public void update(List<Target> targets) {
        try {
            
            for (Target target : targets) {
                update(target);
            }
//            WebTarget wsTarget = webTarget.path(target.getCallSign());
//            FlightControl flightControl = wsTarget.request(MediaType.APPLICATION_XML).get(FlightControl.class);
//            if (flightControl == null || !flightControl.getAddress().equalsIgnoreCase(target.getAddress()) || flightControl.isOsolete(period)) {
//                target.setController(null);
//                target.setTrackStatus(TrackStatus.NONE);
//                return;
//            }
//
//            target.setController(flightControl.getController());
//            if (flightControl.getTargetCotroller() != null && flightControl.getTargetCotroller().equalsIgnoreCase(sector)) {
//                target.setTrackStatus(TrackStatus.TRANSFER_TO_THIS);
//                return;
//            }
//
//            if (flightControl.getController().equalsIgnoreCase(this.sector) && flightControl.getTargetCotroller() != null && !flightControl.getTargetCotroller().isEmpty()) {
//                target.setTrackStatus(TrackStatus.TRANSFER_FROM_THIS);
//                return;
//            }
//
//            if (flightControl.getController().equalsIgnoreCase(this.sector)) {
//                target.setTrackStatus(TrackStatus.CONTROLED);
//                return;
//            }
//
//            target.setTrackStatus(TrackStatus.NONE);
        } catch (Exception ex) {
            logger.error("Cannot connect to service data: %s", this.url);
            ex.printStackTrace();
        }
    }

    public void assum(Target target) {
        FlightControl control = new FlightControl();
        control.setAddress(target.getAddress());
        control.setCallSign(target.getCallSign());
        control.setController(this.sector);

        WebTarget wsTarget = webTarget.path("assum");
        Response response = wsTarget.request().post(Entity.entity(control, MediaType.APPLICATION_XML));
        logger.info("Sector: %s assum flight %s. Return code %s.", this.sector, target.getCallSign(), response.getStatus());
         
	if (response.getStatus() != 200) {
	    return ;
	}
	
	FlightControl flightControl = response.readEntity(FlightControl.class);
	if (flightControl == null) {
	    return ;
	}
	target.setController(flightControl.getController());
        target.setTrackStatus(flightControl.getTrackStatus(this.sector));
	target.commitChange(false);
        
       
    }
    
    public void transfer(Target target, String destinationSector) {
	FlightControl control = new FlightControl();
	control.setAddress(target.getAddress());
	control.setCallSign(target.getCallSign());
	control.setController(this.sector);
        control.setTargetCotroller(destinationSector);
	
	
	WebTarget wsTarget = webTarget.path("transfer");
	Response response = wsTarget.request().post(Entity.entity(control, MediaType.APPLICATION_XML));
	 logger.info("Sector: %s transfer flight %s to %s. Return code %s.", this.sector, target.getCallSign(), destinationSector, response.getStatus());
	if (response.getStatus() != 200) {
	    return ;
	}
	
	FlightControl flightControl = response.readEntity(FlightControl.class);
	if (flightControl == null) {
	    return ;
	}
	
	target.setController(flightControl.getController());  
        target.setTrackStatus(flightControl.getTrackStatus(this.sector));
	target.commitChange(false);
        
        
    }
    
    public void cancel(Target target) {
        FlightControl control = new FlightControl();
	control.setAddress(target.getAddress());
	control.setCallSign(target.getCallSign());
	control.setController(this.sector);
	
	
	WebTarget wsTarget = webTarget.path("cancel");
	Response response = wsTarget.request().post(Entity.entity(control, MediaType.APPLICATION_XML));
	
	if (response.getStatus() != 200) {
	    return ;
	}

        FlightControl flightControl = response.readEntity(FlightControl.class);
        if (flightControl == null) {
            target.setController(null);
            target.setTrackStatus(TrackStatus.NONE);
            target.commitChange(false);
            return;
        }

        target.setController(flightControl.getController());
        target.setTrackStatus(flightControl.getTrackStatus(this.sector));
        target.commitChange(false);
        logger.info("Sector: %s cancel flight %s. Return code %s.", this.sector, target.getCallSign(), response.getStatus());
    }

//    public void assum2(Target target) {
//
//	String path = "http://localhost:8080/com.attech.asd.dataservice/webresources/entities.flightcontrol";
//	Client client = ClientBuilder.newClient();
//	WebTarget webTarget = client.target("http://localhost:8080/com.attech.asd.dataservice/webresources/entities.flightcontrol").path("assum");
////        WebTarget webTarget = client.target("http://localhost:8080/com.attech.asd.dataservice/webresources/entities.flightcontrol");
////        WebTarget webTarget = client.target(this.url);
////        WebTarget webTarget = client.target(path);
//
//	FlightControl control = new FlightControl();
//	control.setAddress(target.getAddress());
//	control.setCallSign(target.getCallSign());
//	control.setController(this.sector);
//
//	Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_XML);
//	Response res = invocationBuilder.post(Entity.entity(control, MediaType.APPLICATION_XML));
////        Response res = webTarget.request().post(Entity.entity(control, MediaType.APPLICATION_XML));
//	System.out.println("Response code: " + res.getStatus());
//	FlightControl control2 = res.readEntity(FlightControl.class);
//	System.out.println("Result: " + (control2 != null));
//
////        if (res.getStatus() != 204) {
//////            System.out.println("Error: " + res.getStatus());
////            return false;
////        } else {
////            System.out.println("Successfully ");
////             return true;
////
////        }
//    }

    public void setPeriod(long period) {
	this.period = period;
    }

    public void setSector(String sector) {
	this.sector = sector;
    }

    public void setUrl(String url) {
	this.url = url;
    }
}
