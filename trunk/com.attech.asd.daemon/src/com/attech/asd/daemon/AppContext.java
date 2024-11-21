/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.daemon;

import com.attech.asd.database.common.Configs;
import java.awt.Color;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TimeZone;

/**
 *
 * @author AnhTH
 */
public class AppContext {
    private Configs config;
    private static AppContext instance;
    private Map<String, String> message;
    public static final TimeZone utc = TimeZone.getTimeZone("UTC");
    
    public AppContext() {
        config = new Configs();
        message = new HashMap<>();
    }
    
    public void reloadConfig(){
        config = new Configs();
    }
    
    public static AppContext getInstance() {
        if (instance == null) {
            instance = new AppContext();
        }
        return instance;
    }
    
    public String getParamValue(String paramName){
        return config.getParamValue(paramName);
    }
    
    public static String getServerIp(){
        return instance.getParamValue("ServerIP");
    }
    
    public static String getStorageLocation(){
        return instance.getParamValue("Root");
    }
    
    public static String getFusedStorageLocation(){
        return instance.getParamValue("RootFused");
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
    
    public static int getStreamingPort(){
        return Integer.parseInt(instance.getParamValue("PortStreaming"));
    }
    
    public static int getErrorTimeout(){
        return Integer.parseInt(instance.getParamValue("ErrorTimeout"));
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
    
    public static Color getWarnColor(){
        return Color.decode(instance.getParamValue("WarnColor"));
    }
    
    public static Color getNormalColor(){
        return Color.decode(instance.getParamValue("NormalColor"));
    }
    
    public static Color getErrorColor(){
        return Color.decode(instance.getParamValue("ErrorColor"));
    }
    
    /**
     * @return the config
     */
    public Configs getConfig() {
        return config;
    }

    /**
     * @return the message
     */
    public Map<String, String> getMapMessage() {
        return message;
    }
    
    public String getMessage(String ip) {
        if (message.containsKey(ip))
            return message.get(ip);
        else
            return "";
    }

    
    /**
     * @param message the message to set
     */
    public synchronized void setMessage(String msg) {
//        message.values().forEach((e) -> {
//            e = "" + msg;
//            System.out.println(e);
//        });
        Iterator iterator = message.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry mapElement = (Map.Entry) iterator.next();
            mapElement.setValue("" + msg);
            System.out.printf("SET VALUE: %s: %s\n", mapElement.getKey(), mapElement.getValue());
        }
    }
    
    public void putMessage(String ip, String msg){
        message.put(ip, msg);
    }
    
    public void removeScreen(String ip){
        message.remove(ip);
    }
    
    public void addScreen(String ip){
        message.put(ip, "");
    }

}
