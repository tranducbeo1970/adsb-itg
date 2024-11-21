/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.gui.trafficList;

import com.attech.adsb.client.common.DataWrapper;
import com.attech.adsb.client.common.MLogger;
import com.attech.adsb.client.common.executors.TaskBase;
import com.attech.adsb.client.config.Configuration;
import com.attech.adsb.client.config.DataServiceConfig;
import com.attech.adsb.client.dto.FlightPlan;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author andh
 */
public class TrafficListUpdator extends TaskBase {

    private static final MLogger logger = MLogger.getLogger(TrafficListUpdator.class);
    private final static String DATA_PATH = "entities.flightplan/findbydof";
    private final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyMMdd");

//    private String url;
    
    private String path;
    private TrafficListModel model;

    public TrafficListUpdator(Date date, TrafficListModel model) {
	this.path = String.format("%s/%s", DATA_PATH, DATE_FORMAT.format(date));
	this.model = model;
    }

    @Override
    public String getTitle() {
	return "TrafficListUpdator";
    }

    @Override
    protected MLogger getLog() {
	return logger;
    }

    @Override
    protected Integer doInBackground() throws Exception {
	try {
            
            // ----
            // NhuongND Comment
//	    Client client = ClientBuilder.newClient();
//	    WebTarget webTarget = client.target(Configuration.instance().getDataServiceCfg().getUrl());
//	    List<FlightPlan> flightPlanList = webTarget.path(path).request(MediaType.APPLICATION_XML).get(new GenericType<List<FlightPlan>>() {
//	    });
//	    this.model.update(flightPlanList);
//	    this.setMessage(String.format("%s items", flightPlanList.size()));            
            // ----
	    
//	    publish(new DataWrapper("INBOX", flightPlanList));
//	    return 0;

//	List<InboxEntity> inboxes = new ArrayList<>();
//        int lastId = 0;
//        do {
//            inboxes = inboxDao.get(this.date, lastId, 1000, filter);
//            publish(new DataWrapper("INBOX", inboxes));
//            if (!inboxes.isEmpty()) {
//                lastId = inboxes.get(inboxes.size() - 1).getId();
//            }
//        } while (!inboxes.isEmpty() && !getRequestCancel());

	} catch (Exception ex) {
	    logger.error(ex);
	    this.setMessage(String.format("Error: %s", ex.getMessage()));
	}
	return 0;
    }

}
