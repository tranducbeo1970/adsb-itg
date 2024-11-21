/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.daemon.client;

import com.attech.asd.daemon.AppContext;
import com.attech.asd.daemon.DataFusion;
import com.attech.asd.daemon.receiver.ReceiverManager;
import com.attech.asd.daemon.socket.UpdatingManager;
import com.attech.asd.database.AdapterObject;
import com.attech.asd.database.entities.Client;
import java.util.HashMap;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author AnhTH
 */
public class ClientManager {

    private static final Logger logger = Logger.getLogger(ClientManager.class);
    private static ClientManager instance;
    private final HashMap<Integer, ClientBroadcaster> broadcasters = new HashMap<>();
    private final HashMap<Integer, SimpleTransferPackage> tranfers = new HashMap<>();

    public ClientManager() {
        try {
            List<Client> clients = new AdapterObject().getAllClient();
            if (clients != null && clients.size() > 0) {
                for (Client c : clients) {
                    if (!c.isForwarding()) {
                        ClientBroadcaster broadcaster = new ClientBroadcaster(c);
                        broadcasters.put(c.getId(), broadcaster);
                    } else {
                        SimpleTransferPackage tranfer = new SimpleTransferPackage(c.getId(), c.getForwardAddress(), c.getForwardPort(), c.getForwardBindIp());
                        tranfer.sensorId = c.getIdSensorFwd();
                        tranfers.put(c.getId(), tranfer);
                    }
                }
            }
        } catch (Exception ex) {
            logger.error("Could not initial." + ex.getMessage());
        }
    }
    
    public void launch(){        
        try {
            List<Client> clients = new AdapterObject().getAllClient();
            if (clients != null && clients.size() > 0) {
                for (Client c : clients) {
                    //LAUNCH CLIENT IS ACTIVE   
                    if(c.getStatus() == 1){
                        ClientManager.getInstance().startClient(c.getId());
                    }
                }
            }
        } catch (Exception ex) {
            logger.error("Could not initial." + ex.getMessage());
        }
    }
    
    public synchronized static ClientManager getInstance() {
        if (instance == null) {
            instance = new ClientManager();
            logger.info("ClientManager has been created");
        }
        return instance;
    }

    public boolean startClient(int id) {
        if (broadcasters.containsKey(id)) {
            if (broadcasters.get(id).isActive()) {
                // DO NOTHING AND NOTIFY
                logger.info(String.format("Could not start client #%s: (Broadcaster is running)", id));
                AppContext.getInstance().setMessage(String.format("Could not start client #%s: (Broadcaster is running)", id));
                return false;
            }
            // START -> NORMALLY
            broadcasters.get(id).active();
            UpdatingManager.getInstance().setNotifyReloadClient(id);
            // Update Status
            Client client = new AdapterObject().getClientById(id);
            client.setStatus((byte) 1);
            client.save();
            return true;
        } else if (tranfers.containsKey(id)) {
            if (tranfers.get(id).isRunning()) {
                // DO NOTHING AND NOTIFY
                logger.info(String.format("Could not start client #%s: (TransferData is running)", id));
                AppContext.getInstance().setMessage(String.format("Could not start client #%s: (TransferData is running)", id));
                return false;
            }
            // START -> NORMALLY
            tranfers.get(id).setRunning(true);
            ReceiverManager.getInstance().addTranferPackage(tranfers.get(id));
            UpdatingManager.getInstance().setNotifyReloadClient(id);
            // Update Status
            Client client = new AdapterObject().getClientById(id);
            client.setStatus((byte) 1);
            client.save();
            
            return true;
        }
        // NOT FOUND
        logger.info(String.format("Could not found client #%s to start.", id));
        AppContext.getInstance().setMessage(String.format("Could not found client #%s to start.", id));
        return false;
    }

    public boolean stopClient(int id) {
        if (broadcasters.containsKey(id)) {
            if (broadcasters.get(id).isActive()) {
                broadcasters.get(id).deactive();
                UpdatingManager.getInstance().setNotifyReloadClient(id);
                // Update Status
                Client client = new AdapterObject().getClientById(id);
                client.setStatus((byte) 0);
                client.save();
                return true;
            } else {
                // DO NOTHING AND NOTIFY
                logger.info(String.format("Could not stop client #%s: (Broadcaster currently stops)", id));
                AppContext.getInstance().setMessage(String.format("Could not stop client #%s: (Broadcaster currently stops)", id));
                return false;
            }
        } else if (tranfers.containsKey(id)) {
            if (tranfers.get(id).isRunning()) {
                tranfers.get(id).setRunning(false);
                ReceiverManager.getInstance().removeTranferPackage(tranfers.get(id));
                UpdatingManager.getInstance().setNotifyReloadClient(id);
                // Update Status
                Client client = new AdapterObject().getClientById(id);
                client.setStatus((byte) 0);
                client.save();
                return true;
            } else {
                // DO NOTHING AND NOTIFY
                logger.info(String.format("Could not stop client #%s: (TransferData currently stops)", id));
                AppContext.getInstance().setMessage(String.format("Could not start client #%s: (TransferData currently stops)", id));
                return false;
            }
        }
        // NOT FOUND
        logger.info(String.format("Could not found client #%s to stop", id));
        AppContext.getInstance().setMessage(String.format("Could not found client #%s to stop.", id));       
            
        return false;
    }

    public boolean deleteClient(int id) {
        if (broadcasters.containsKey(id)) {
            if (broadcasters.get(id).isActive()) {
                broadcasters.get(id).deactive();
                broadcasters.remove(id);
            }
            // DELETE
            Client client = new AdapterObject().getClientById(id);
            new AdapterObject().removeClient(client);
            UpdatingManager.getInstance().setNotifyReloadClient(id);
            return true;
        }

        if (tranfers.containsKey(id)) {
            if (tranfers.get(id).isRunning()) {
                tranfers.get(id).setRunning(false);
                tranfers.remove(id);
            }
            // DELETE
            Client client = new AdapterObject().getClientById(id);
            new AdapterObject().removeClient(client);
            UpdatingManager.getInstance().setNotifyReloadClient(id);
            return true;
        }
        
        Client client = new AdapterObject().getClientById(id);
        if (client != null) {
            new AdapterObject().removeClient(client);
            UpdatingManager.getInstance().setNotifyReloadClient(id);
            return true;
        } else {
            logger.info(String.format("Could not found client #%s to delete.", id));
            AppContext.getInstance().setMessage(String.format("Could not found client #%s to delete", id));
            return false;
        }
    }

    public boolean reloadClient(int id) {
        if (broadcasters.containsKey(id)) {
            if (broadcasters.get(id).isActive()) {
                // DO NOTHING AND NOTIFY
                logger.info(String.format("Could not reload client #%s: (Broadcaster is running)", id));
                AppContext.getInstance().setMessage(String.format("Could not reload client #%s: (Broadcaster is running)", id));
                return false;
            }
            // REMOVE
            broadcasters.remove(id);
        }

        if (tranfers.containsKey(id)) {
            if (tranfers.get(id).isRunning()) {
                // DO NOTHING AND NOTIFY
                logger.info(String.format("Could not reload client #%s: (Tranfer is running)", id));
                AppContext.getInstance().setMessage(String.format("Could not reload client #%s: (Tranfer is running)", id));
                return false;
            }
            // REMOVE
            tranfers.remove(id);
        }
        // RELOAD
        Client client = new AdapterObject().getClientById(id);
        if (client != null) {
            if (client.isForwarding()){
                SimpleTransferPackage tranfer = new SimpleTransferPackage(client.getId(), client.getForwardAddress(), client.getForwardPort(), client.getForwardBindIp());
                tranfer.sensorId = client.getIdSensorFwd();
                tranfers.put(id, tranfer);
            }
            else {
                broadcasters.put(id, new ClientBroadcaster(client));
            }
            
            UpdatingManager.getInstance().setNotifyReloadClient(id);
            return true;
        } else {
            logger.info(String.format("Could not found client #%s to reload.", id));
            AppContext.getInstance().setMessage(String.format("Could not found client #%s to reload", id));
            return false;
        }
    }

    public ClientBroadcaster getBroadcaster(int id) {
        return broadcasters.get(id);
    }

    public SimpleTransferPackage getTransfer(int id) {
        return tranfers.get(id);
    }

    public int size() {
        return broadcasters.size() + tranfers.size();
    }

    /**
     * @return the broadcasters
     */
    public HashMap<Integer, ClientBroadcaster> getBroadcasters() {
        return broadcasters;
    }

    /**
     * @return the tranfers
     */
    public HashMap<Integer, SimpleTransferPackage> getTranfers() {
        return tranfers;
    }

    
}
