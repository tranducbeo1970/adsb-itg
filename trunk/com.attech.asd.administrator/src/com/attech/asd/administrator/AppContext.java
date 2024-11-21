/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.administrator;

import com.attech.asd.administrator.common.ExceptionHandler;
import com.attech.asd.administrator.views.AssemblyInfo;
import com.attech.asd.database.AdapterObject;
import com.attech.asd.database.SensorsDao;
import com.attech.asd.database.common.Configs;
import com.attech.asd.database.entities.Client;
import com.attech.asd.database.entities.Sensors;
import java.awt.Color;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author AnhTH
 */
public class AppContext {
    private Configs config;
    private static AppContext instance;
    private List<Sensors> sensors;
    private List<Client> clients;
    private SocketClient commandSocket;
    public boolean EnableSound;
    private SocketClient updaterSocket;
    private Set<String> warnReceiver;
    private Set<String> warnBroadcaster;
    
    public boolean reloadListClient;
    public boolean reloadListReceiver;
    public boolean reloadListMessage;
    public AssemblyInfo assemblyInfo;
    public boolean connectToServer;
    
    public AppContext() {
        this.config = new Configs();
        this.sensors = new SensorsDao().listAllSensors();
        this.clients = new AdapterObject().getAllClient();
        this.commandSocket = null;
        this.updaterSocket = null;
        this.EnableSound = true;
        this.warnReceiver = new HashSet<>();
        this.warnBroadcaster = new HashSet<>();
        this.assemblyInfo = new AssemblyInfo();
        try {
            this.assemblyInfo.load();
        } catch (IOException ex) {
            ExceptionHandler.handle(ex);
        }
        this.connectToServer = false;
        this.reloadListClient = false;
        this.reloadListMessage = false;
        this.reloadListReceiver = false;
    }
    
    public void reloadConfig(){
        config = new Configs();
    }
    
    public static synchronized void connectCommandSocket() throws IOException{
        if (instance.commandSocket != null && instance.commandSocket.isConnected())
            instance.commandSocket.close();
        
        instance.commandSocket = new SocketClient();
        instance.commandSocket.connect(getServerIp(), getServerPort());
    }
    
    public static synchronized void connectUpdaterSocket() throws IOException{
        if (instance.updaterSocket != null && instance.updaterSocket.isConnected())
            instance.updaterSocket.close();
        
        instance.updaterSocket = new SocketClient();
        instance.updaterSocket.connect(getServerIp(), getServerPort()+1);
    }
    
    public boolean isWarning(){
        return (warnReceiver.size() > 0 || warnBroadcaster.size() > 0);
    }
    
    public static AppContext getInstance() {
        if (instance == null) {
            instance = new AppContext();
        }
        return instance;
    }
    
    public void setProperty(String paramName, String paramValue){
        config.setProperty(paramName, paramValue);
    }
    
    public synchronized void saveConfig(){
        config.saveAllProperties();
    }
    
    public void reloadSensors(){
        sensors = new SensorsDao().listAllSensors();
        reloadListReceiver = true;
    }
    
    public void reloadClient(){
        clients = new AdapterObject().getAllClient();
        reloadListClient = true;
    }
    
    public String getParamValue(String paramName){
        return config.getParamValue(paramName);
    }
    
    public static Color getWarnColor(){
        return Color.decode(instance.getParamValue("WarnColor"));
    }
    
    public static Color getNormalColor(){
        return Color.decode(instance.getParamValue("NormalColor"));
    }
    
    public static Color getErrorColor(){
        return Color.decode(instance.getParamValue("ErrorColor"));
    }
    
    public static long getStorageThresshold(){
        return Long.parseLong(instance.getParamValue("DiskSpaceWarning"));
    }
    
    public static long getStorageErrorThresshold(){
        return Long.parseLong(instance.getParamValue("DiskSpaceError"));
    }
    
    public static String getTheme(){
        return instance.getParamValue("Theme");
    }
    
    public static String getSplitMode(){
        return instance.getParamValue("SplitMode");
    }
    
    public static String getServerIp(){
        return instance.getParamValue("ServerIP");
    }
    
    public static String getAmhsBindIp(){
        return instance.getParamValue("AmhsMonitorBindIP1");
    }
    
    public static int getAmhsPort(){
        return Integer.parseInt(instance.getParamValue("AmhsMonitorPort1"));
    }
    
    public static int getServerPort(){
        return Integer.parseInt(instance.getParamValue("ServerPort"));
    }
    
    public static int getDataLimit(){
        return Integer.parseInt(instance.getParamValue("DataLimit"));
    }
    
    public static int getFlightPlanLimit(){
        return Integer.parseInt(instance.getParamValue("FlightPlanLimit"));
    }
    
    public static int getRefreshTime(){
        return Integer.parseInt(instance.getParamValue("RefreshTime"));
    }
    
    public static String getStorageLocation(){
        return instance.getParamValue("Root");
    }
    
    public static String getFusedStorageLocation(){
        return instance.getParamValue("RootFused");
    }
    
    public static int getStreamingPort(){
        return Integer.parseInt(instance.getParamValue("PortStreaming"));
    }
    
    public static int getErrorTimeout(){
        return Integer.parseInt(instance.getParamValue("ErrorTimeout"));
    }
    
    public static int getSnoozeTime(){
        return Integer.parseInt(instance.getParamValue("SnoozeTime"));
    }
    
    public static int getWarningTimeout(){
        return Integer.parseInt(instance.getParamValue("WarningTimeout"));
    }
    
    public static int getBroadcastTime(){
        return Integer.parseInt(instance.getParamValue("BroadcastTime"));
    }
    
    public static int getFusePeriodTime(){
        return Integer.parseInt(instance.getParamValue("FusePeriodTime"));
    }
    
    public static int getFusionCleanupPeriodTime(){
        return Integer.parseInt(instance.getParamValue("FusionCleanupPeriodTime"));
    }
    
    public static int getFusionItemTimeout(){
        return Integer.parseInt(instance.getParamValue("FusionItemTimeout"));
    }
    
    public static int getBroadcastPackagetLimit(){
        return Integer.parseInt(instance.getParamValue("BroadcastPackagetLimit"));
    }
    
    public static int getPageSize(){
        return Integer.parseInt(instance.getParamValue("PageSize"));
    }
    /**
     * @return the socket
     */
    public SocketClient getCommandSocket() {
        return commandSocket;
    }

    /**
     * @param socket the socket to set
     */
    public void setCommandSocket(SocketClient socket) {
        this.commandSocket = socket;
    }
    
    /**
     * @return the socket
     */
    public SocketClient getUpdaterSocket() {
        return updaterSocket;
    }

    /**
     * @param socket the socket to set
     */
    public void setUpdaterSocket(SocketClient socket) {
        this.updaterSocket = socket;
    }

    /**
     * @return the sensors
     */
    public List<Sensors> getSensors() {
        return sensors;
    }

    /**
     * @return the clients
     */
    public List<Client> getClients() {
        return clients;
    }

    /**
     * @return the warnReceiver
     */
    public Set<String> getWarnReceiver() {
        return warnReceiver;
    }

    /**
     * @param warnReceiver the warnReceiver to set
     */
    public void setWarnReceiver(Set<String> warnReceiver) {
        this.warnReceiver = warnReceiver;
    }

    /**
     * @return the warnBroadcaster
     */
    public Set<String> getWarnBroadcaster() {
        return warnBroadcaster;
    }

    /**
     * @param warnBroadcaster the warnBroadcaster to set
     */
    public void setWarnBroadcaster(Set<String> warnBroadcaster) {
        this.warnBroadcaster = warnBroadcaster;
    }


}
