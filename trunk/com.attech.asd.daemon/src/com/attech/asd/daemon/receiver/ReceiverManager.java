/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.daemon.receiver;

import com.attech.asd.daemon.AppContext;
import com.attech.asd.daemon.client.SimpleTransferPackage;
import com.attech.asd.daemon.socket.UpdatingManager;
import com.attech.asd.database.AdapterObject;
import com.attech.asd.database.SensorsDao;
import com.attech.asd.database.entities.SensorLogs;
import com.attech.asd.database.entities.Sensors;
import com.mysql.cj.MysqlType;
import java.util.HashMap;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author AnhTH
 */
public class ReceiverManager {
    
    private static final Logger logger = Logger.getLogger(ReceiverManager.class);
    private static ReceiverManager instance;
    
    private final HashMap<Integer, ReceivingHandler> receivers = new HashMap<>();
    
    public ReceiverManager(){
        try {
            List<Sensors> sensors = new SensorsDao().listAllSensors();
            if (sensors != null && sensors.size() > 0) {
                for (Sensors s : sensors) {
                    receivers.put(s.getId(), new ReceivingHandler(s));
                }
            }            
        } catch (Exception ex) {
            logger.error("Could not initial receiver handler: " + ex.getMessage());
        }
    }
    
    public void launch(){
        try {
            List<Sensors> sensors = new SensorsDao().listAllSensors();
            if (sensors != null && sensors.size() > 0) {
                for (Sensors s : sensors) {
                     //LAUNCH RECEIVER IS ACTIVE   
                    if(s.getStatus() == 1){
                        ReceiverManager.getInstance().startReceiver(s.getId());
                    }
                }
            }
        } catch (Exception ex) {
            logger.error("Could not initial receiver handler: " + ex.getMessage());
        }
    }
    
    public synchronized static ReceiverManager getInstance() {
        if (instance == null) {
            instance = new ReceiverManager();
            logger.info("ReceiverManager has been created");
        }
        return instance;
    }
    
    public void addTranferPackage(SimpleTransferPackage tranfer){
        if (!receivers.containsKey(tranfer.sensorId))
            return;
        receivers.get(tranfer.sensorId).addTranferPackage(tranfer);
    }
    
    public void addTranferPackageThenStart(SimpleTransferPackage tranfer){
        if (!receivers.containsKey(tranfer.sensorId))
            return;
        receivers.get(tranfer.sensorId).addTranferPackageThenStart(tranfer);
    }
    
    public void removeTranferPackage(SimpleTransferPackage tranfer){
        if (!receivers.containsKey(tranfer.sensorId))
            return;
        receivers.get(tranfer.sensorId).removeTranferPackage(tranfer.getNo());
    }
    
    public boolean startReceiver(int id){
        if (!getReceivers().containsKey(id)){
            // NOT FOUND
            logger.info(String.format("Could not found receiver #%s", id));
            AppContext.getInstance().setMessage(String.format("Could not found receiver #%s", id));
            return false; 
        }
        
        if (getReceivers().get(id).isRunning()) {
            logger.info(String.format("Could not start receiver #%s: (Receiver is running)", id));
            AppContext.getInstance().setMessage(String.format("Could not start receiver #%s: (Receiver is running)", id));
            return false; // DO NOTHING
        }
        // START -> NORMALLY
        getReceivers().get(id).start();
        UpdatingManager.getInstance().setNotifyReloadReceiver(id);
        // Update Status
        Sensors sensor = new SensorsDao().get(id);
        sensor.setStatus((byte) 1);
        sensor.save();
        
        return true;
    }
        
    public boolean stopReceiver(int id){
        if (!receivers.containsKey(id)){
            logger.info(String.format("Could not stop receiver #%s: (Not found/ is not running)", id));
            AppContext.getInstance().setMessage(String.format("Could not stop receiver #%s: (Not found/ is not running)", id));
            return false;
        } 
        
        if (getReceivers().get(id).isRunning())
            getReceivers().get(id).stop();
        
        UpdatingManager.getInstance().setNotifyReloadReceiver(id);
        // Update Status
        Sensors sensor = new SensorsDao().get(id);
        sensor.setStatus((byte) 0);
        sensor.save();
        
        return true;
    }
    
    public boolean reloadReceiver(int id){
        if (getReceivers().containsKey(id)){
            if (getReceivers().get(id).isRunning()){
                logger.info(String.format("Could not reload receiver #%s: (Receiver is running)", id));
                AppContext.getInstance().setMessage(String.format("Could not reload receiver #%s: (Receiver is running)", id));
                return false;
            } 
            getReceivers().remove(id);
        }
        // REMOVE
        final Sensors sensor = new SensorsDao().get(id);
        getReceivers().put(sensor.getId(), new ReceivingHandler(sensor));
        UpdatingManager.getInstance().setNotifyReloadReceiver(id);
        return true;
    }
    
    public boolean deleteReceiver(int id){
        if (getReceivers().containsKey(id)){
            if (getReceivers().get(id).isRunning()){
                logger.info(String.format("Could not delete receiver #%s: (Receiver is running)", id));
                AppContext.getInstance().setMessage(String.format("Could not delete receiver #%s: (Receiver is running)", id));
                return false;
            } 
            getReceivers().remove(id);
        }
        Sensors s = new SensorsDao().get(id);
        SensorLogs logs = new SensorLogs(s, String.format("Sensor #%s(SIC %s) has been deleted", s.getId(), s.getSic()), 1);
        logs.save();
        logger.info(String.format("Sensor #%s(SIC %s) has been deleted", s.getId(), s.getSic()));
        AppContext.getInstance().setMessage(String.format("Sensor #%s(SIC %s) has been deleted", s.getId(), s.getSic()));
        new AdapterObject().removeSensor(s);
        
        UpdatingManager.getInstance().setNotifyReloadReceiver(id);
        return true;
    }
    
    public ReceivingHandler get(int id){
        return receivers.get(id);
    }
    
    public int size(){
        return receivers.size();
    }

    /**
     * @return the receivers
     */
    public HashMap<Integer, ReceivingHandler> getReceivers() {
        return receivers;
    }

    
}
