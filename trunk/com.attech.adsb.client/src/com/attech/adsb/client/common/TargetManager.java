/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.common;

import com.attech.adsb.client.common.service.AircraftService;
import com.attech.adsb.client.common.service.FlightControlService;
import com.attech.adsb.client.common.service.FlightPlanService;
import com.attech.adsb.client.config.Configuration;
import com.attech.adsb.client.config.FilterCondition;
import com.attech.adsb.client.config.SiteMonitor;
import com.attech.adsb.client.graphic.objects.TargetGraphic;
import com.attech.adsb.client.graphic.objects.TargetItem;
import com.attech.asterix.cat21.v21.Message;
import com.attech.cat01.v120.Cat01Message;
import com.attech.cat48.v121.Cat48Message;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author andh
 */
public class TargetManager implements Runnable{
    
    private static final MLogger logger = MLogger.getLogger(TargetManager.class);
    private static final SiteMonitor siteMonitor = Configuration.instance().getSitemonitor();
    private static final int TARGET_UPDATE_INTERVAL = Configuration.instance().getPreference().getTargetUpdateInterval();
    private static final int TARGET_INFO_UPDATE_INTERVAL = Configuration.instance().getPreference().getTargetInfoUpdateInterval();
   
    private final ScheduledExecutorService targetUpdateScheduler = Executors.newScheduledThreadPool(5);
    private final ScheduledExecutorService targetInfoUpdateScheduler = Executors.newScheduledThreadPool(5);
    private final HashMap<String, Target> targets = new HashMap<>();
    private final List<TargetValidator> validators = new ArrayList<>();
    private final FlightControlService flightControlService;
    private final FlightPlanService flightPlanService;
    private final AircraftService aircraftService;

    private boolean sctaWarning;
    private boolean mctaWarning;
    private TargetGraphic targetGraphic;
    private TargetGraphic secondaryGraphic;
    private FilterCondition filterCondition;
    
    public TargetManager() {
        logger.info("Set current setup position is %s", Configuration.instance().getPreference().getSector());
        flightControlService = new FlightControlService(Configuration.instance().getPreference().getSector());
        flightPlanService = new FlightPlanService();
        aircraftService = new AircraftService();
    }
    
    public void startSelfCalculation() {
        logger.info("Start target recalculate interval at %s second", TARGET_UPDATE_INTERVAL);
        targetUpdateScheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                calculateTargetStatus ();
            }
        }, TARGET_UPDATE_INTERVAL, TARGET_UPDATE_INTERVAL, TimeUnit.SECONDS);
        
        targetInfoUpdateScheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                updateTargetInfo();
            }
        }, 3, TARGET_INFO_UPDATE_INTERVAL, TimeUnit.SECONDS);
        
    }
    
    public void recalculateSpeedVector() {
	Target item;
        for (Iterator<Map.Entry<String, Target>> it = targets.entrySet().iterator(); it.hasNext();) {
            item = it.next().getValue();
	    item.calculate();
	    item.commitChange(false);
        }
    }
    
    public synchronized List<Target> getTargetList() {
	return new ArrayList<>(targets.values());
    }
    
    public void loadDummyTarget(String folderPath) {
        File targetDirectory = new File(folderPath);
        File [] files = targetDirectory.listFiles();
        for (File file : files) {
            /*
            Target target = Target.load(file.getAbsolutePath(), Target.class);
            if (target == null) continue;
            target.setDummy(true);
	    target.calculate();
	    updateTarget(target);
            */
            loadDummyTargetFromFile(file.getAbsolutePath());
        }
    }
    
    public void loadDummyTargetFromFile(String file) {
        Target target = Target.load(file, Target.class);
        if (target == null) {
            return;
        }
        target.setDummy(true);
        target.calculate();
        updateTarget(target);
    }
    
    public void assumFlight(Target target) {
	Thread thread = new Thread(() -> {
            try {
                flightControlService.assum(target);
            } catch (Exception ex) {
                logger.error(ex);
            }
        });
        thread.start();
    }
    
    public void cancelFlight(Target target) {
	Thread thread = new Thread(() -> {
            try {
                flightControlService.cancel(target);
            } catch (Exception ex) {
                logger.error(ex);
            }
        });
        thread.start();
    }
    
    public void transferFlight(Target target, String targetSector) {
	Thread thread = new Thread(() -> {
            try {
                flightControlService.transfer(target, targetSector);
            } catch (Exception ex) {
                logger.error(ex);
            }
        });
        thread.start();
    }
    
    public void removeAllDummy() {
        Iterator<String> targetIterator = targets.keySet().iterator();
        while (targetIterator.hasNext()) {
            String key = targetIterator.next();
            Target target = targets.get(key);
            
            if (!target.isDummy()) {
                continue;
            }
            
            target.setTrackAge(TrackAge.OBSOLETED);
            target.commitChange(false);
            targetIterator.remove();
        }
    }
    
    public void updateTarget(List<Message> messages) {
        try {
            for (Message message : messages) {
                if (message.getTargetAddress() == null) continue;
                
                //System.out.println(">>>> Callsign: " + message.getCallSign() + " " + (message.getAirborneGroundVector() == null ? " " :message.getAirborneGroundVector().getGroundSpeed())  );
                String address =  Integer.toHexString(message.getTargetAddress()).toUpperCase();
                if (!siteMonitor.getDisplay() && siteMonitor.contain(address)) {
                    continue;
                }

                if (targets.containsKey(address)) {
                    //System.out.println(">>>> Update target " + message.getCallSign() + " " + (message.getAirborneGroundVector() == null ? " " :message.getAirborneGroundVector().getGroundSpeed())  );
                    Target target = targets.get(address);
                    target.update(message);
                    target.commitChange(true);
                    continue;
                }

                System.out.println(">>>> Create new target: " + message.getCallSign() + " " + (message.getAirborneGroundVector() == null ? " " :message.getAirborneGroundVector().getGroundSpeed())  );
                Target target = new Target(message);
                targets.put(address, target);
                targetGraphic.addTarget(new TargetItem(target));
                secondaryGraphic.addTarget(new TargetItem(target));
                
            }
        } catch (Exception ex) {
            logger.error(ex);
        }
    }
    
    public void updateTargetCat48(List<Cat48Message> messages) {
        try {
            for (Cat48Message message : messages) {
                if (message.getTargetAddress() == null || message.getSic() == null) continue;
                
                //System.out.println(">>>> Callsign: " + message.getCallsign() + " " + (message.getCalculatedPolarVelocity() == null ? " " : (int) Math.ceil(message.getCalculatedPolarVelocity().getSpeed()))  );
                String address =  Integer.toHexString(message.getTargetAddress()).toUpperCase();
                if (!siteMonitor.getDisplay() && siteMonitor.contain(address)) {
                    continue;
                }

                if (targets.containsKey(address)) {
                    //System.out.println(">>>> Update target " + message.getCallsign() + " " + (message.getCalculatedPolarVelocity() == null ? " " : (int) Math.ceil(message.getCalculatedPolarVelocity().getSpeed()))  );
                    Target target = targets.get(address);
                    target.update(message);
                    target.commitChange(true);
                    continue;
                }

                //System.out.println(">>>> Create new target: " + message.getCallsign() + " " + (message.getCalculatedPolarVelocity() == null ? " " : (int) Math.ceil(message.getCalculatedPolarVelocity().getSpeed()))  );
                Target target = new Target(message);
                targets.put(address, target);
                targetGraphic.addTarget(new TargetItem(target));
                secondaryGraphic.addTarget(new TargetItem(target));
                
                //System.out.println("Added target " + target.getAddress());
            }
        } catch (Exception ex) {
            logger.error(ex);
        }
    }
    
    public void updateTargetCat01(List<Cat01Message> messages) {
        try {
            for (Cat01Message message : messages) {
                if (message.getNo() == null || message.getSic() == null) continue;
                
                //System.out.println(">>>> Callsign: " + message.getMode3ACode().getValue()+ " " + (message.getCalculatedPolarVelocity() == null ? " " : (int) Math.ceil(message.getCalculatedPolarVelocity().getSpeed()))  );
                String address =  Integer.toString(message.getNo());
                if (!siteMonitor.getDisplay() && siteMonitor.contain(address)) {
                    continue;
                }

                if (targets.containsKey(address)) {
                    //System.out.println(">>>> Update target " + message.getCallsign() + " " + (message.getCalculatedPolarVelocity() == null ? " " : (int) Math.ceil(message.getCalculatedPolarVelocity().getSpeed()))  );
                    Target target = targets.get(address);
                    target.update(message);
                    target.commitChange(true);
                    continue;
                }

                //System.out.println(">>>> Create new target: " + message.getCallsign() + " " + (message.getCalculatedPolarVelocity() == null ? " " : (int) Math.ceil(message.getCalculatedPolarVelocity().getSpeed()))  );
                Target target = new Target(message);
                targets.put(address, target);
                targetGraphic.addTarget(new TargetItem(target));
                secondaryGraphic.addTarget(new TargetItem(target));
                
                //System.out.println("Added target " + target.getAddress());
            }
        } catch (Exception ex) {
            logger.error(ex);
        }
    }
    
    public void applyFilter(FilterCondition filterCondition) {
        this.filterCondition = filterCondition;
    }
    
    public void apply() {
        
    }
    
    private void updateTarget(Target target) {
        if (targets.containsKey(target.getAddress())) {
            return;
        }
        targets.put(target.getAddress(), target);
        targetGraphic.addTarget(new TargetItem(target));
        secondaryGraphic.addTarget(new TargetItem(target));
    }
    
    private void updateTargetInfo() {
        logger.info("Start updating target info");
        final List<Target> targetList = new ArrayList<>(targets.values());
        try {

            for (Target target : targetList) {
                if (!target.isAvailable() || target.isObsoleted()) {
                    continue;
                }
// NhuongND comment
                flightPlanService.update(target);
                aircraftService.update(target);
            }
        } catch (Exception ex) {
            logger.error(ex);
        } finally {
            for (Target target : targetList) {
                target.commitChange(false);
            }
        }
    }
    
    private void calculateTargetStatus () {
        final List<Target> targetList = new ArrayList<>(targets.values());
        final int size = targetList.size();

        try {

            logger.info("Calculate target status (size: " + targets.size() + ")");
            Iterator<String> targetIterator = targets.keySet().iterator();
            while (targetIterator.hasNext()) {
                String key = targetIterator.next();
                Target target = targets.get(key);

//                if (!target.isDummy()) {
//                    continue;
//                }
//                target.setTrackAge(TrackAge.OBSOLETED);
//                target.commitChange(false);
//                targetIterator.remove();
                target.clearWarning();
                for (TargetValidator validator : this.validators) {
                    validator.validate(target);
                }
                target.validateTargetReportedStatus();
                target.validateTargetSignalQuality();
                target.validateTargetAge();
                if (target.isObsoleted()) {
                    targetGraphic.removeTarget(target);
                    secondaryGraphic.removeTarget(target);
                    targetIterator.remove();
                    //targetGraphic.
                    logger.info("Remove obsoleted target %s", target.getCallSign());
                }
            }

//            for (Target target : targetList) {
//                target.clearWarning();
//                for (TargetValidator validator : this.validators) {
//                    validator.validate(target);
//                }
//                target.validateTargetReportedStatus();
//                target.validateTargetSignalQuality();
//                target.validateTargetAge();
//            }
// NhuongND comment            
            flightControlService.update(targetList);
            if (this.isSctaWarning()) {
                // Collission detecting
                for (int i = 0; i < size; i++) {
                    Target testingTarget = targetList.get(i);
                    if (!testingTarget.isAvailable()) {
                        continue;
                    }

                    for (int j = i + 1; j < size; j++) {
                        Target objectTarget = targetList.get(j);
                        int hitLevel = testingTarget.validateConflict(objectTarget);
                        testingTarget.setHitLevel(hitLevel);
                        objectTarget.setHitLevel(hitLevel);
                    }
                }
            }
            // NhuongND add
            if (this.isMctaWarning()) {                
                // Collission detecting
                for (int i = 0; i < size; i++) {
                    Target testingTarget = targetList.get(i);
                    if (!testingTarget.isAvailable()) {
                        continue;
                    }

                    for (int j = i + 1; j < size; j++) {
                        Target objectTarget = targetList.get(j);
                        int hitLevel = testingTarget.validateConflictMTCS(objectTarget);                        
                        testingTarget.setHitLevelMTCS(hitLevel);
                        objectTarget.setHitLevelMTCS(hitLevel);
                    }
                }
            }

        } catch (Exception ex) {
            logger.error(ex);
        } finally {
            for (Target target : targetList) {
                target.commitChange(false);
            }
        }
    }
    
    @Override
    public void run() {
        final List<Target> targetList = new ArrayList<>(targets.values());
        final int size = targetList.size();

        try {

            logger.info("Start validation");

            for (Target target : targetList) {
                target.clearWarning();
                for (TargetValidator validator : this.validators) {
                    validator.validate(target);
                }
                target.validateTargetReportedStatus();
                target.validateTargetSignalQuality();
                target.validateTargetAge();
            }
// NhuongND comment            
            flightControlService.update(targetList);

            if (this.isSctaWarning()) {
                // Collission detecting
                for (int i = 0; i < size; i++) {
                    Target testingTarget = targetList.get(i);
                    if (!testingTarget.isAvailable()) {
                        continue;
                    }

                    for (int j = i + 1; j < size; j++) {
                        Target objectTarget = targetList.get(j);
                        int hitLevel = testingTarget.validateConflict(objectTarget);
                        testingTarget.setHitLevel(hitLevel);
                        objectTarget.setHitLevel(hitLevel);
                    }
                }
            }
            // NhuongND add
            if (this.isMctaWarning()) {                
                // Collission detecting
                for (int i = 0; i < size; i++) {
                    Target testingTarget = targetList.get(i);
                    if (!testingTarget.isAvailable()) {
                        continue;
                    }

                    for (int j = i + 1; j < size; j++) {
                        Target objectTarget = targetList.get(j);
                        int hitLevel = testingTarget.validateConflictMTCS(objectTarget);
                        testingTarget.setHitLevelMTCS(hitLevel);
                        objectTarget.setHitLevelMTCS(hitLevel);
                    }
                }
            }

            for (Target target : targetList) {
                target.commitChange(false);
            }

//	if (this.validators.isEmpty()) return;
//	Target target;
//	for (Iterator<Map.Entry<String, Target>> it = targets.entrySet().iterator(); it.hasNext();) {
//	    
//	    // Checking for expired
//	    // Checking for 
//	    target = it.next().getValue();
//	    if (!target.isAvailable()) continue;
//	    for (TargetValidator validator : this.validators) {
//		validator.validate(target);
//	    }
//	    
////	    for ()
//	}
//        if (this.amaGraphic != null) {
//	    
//	    
//	    
//            System.out.println("-- AMA --");
//            for (Iterator<Map.Entry<String, Target>> it = targets.entrySet().iterator(); it.hasNext();) {
//                target = it.next().getValue();
//                // Test AMA
//                this.amaGraphic.determineAMAViolated(target);
//            }
//        }
        } catch (Exception ex) {
            logger.error(ex);
        } finally {
//            for (Target target : targetList) {
//                target.commitChange(false);
//            }
        }
    }
    
    //<editor-fold defaultstate="collapsed" desc=" Class properties ">
    
    public void setSecondaryGraphic(TargetGraphic secondaryGraphic) {
        this.secondaryGraphic = secondaryGraphic;
    }
    
    public TargetGraphic getTargetGraphic() {
	return targetGraphic;
    }

    public void setTargetGraphic(TargetGraphic targetGraphic) {
	this.targetGraphic = targetGraphic;
    }
    
    public void addValidator(TargetValidator validator) {
	this.validators.add(validator);
    }
    
    /**
     * @return the sctaWarning
     */
    public boolean isSctaWarning() {
        return sctaWarning;
    }

    /**
     * @param sctaWarning the sctaWarning to set
     */
    public void setSctaWarning(boolean sctaWarning) {
        this.sctaWarning = sctaWarning;
    }
    
    /**
     * @return the mctaWarning
     */
    public boolean isMctaWarning() {
        return mctaWarning;
    }
    
    /**
     * @param mctaWarning the mctaWarning to set
     */
    public void setMctaWarning(boolean mctaWarning) {
        this.mctaWarning = mctaWarning;
    }
        
    //</editor-fold>
    
}
