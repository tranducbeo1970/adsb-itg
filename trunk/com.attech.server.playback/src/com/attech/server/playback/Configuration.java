/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.server.playback;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import jcifs.smb.NtlmPasswordAuthentication;

/**
 *
 * @author root
 */
public class Configuration {
    private boolean isLocal;
    private String user;
    private String pass;
    private String location;
    private NtlmPasswordAuthentication auth;
    private String address;
    private int port;
    private static Configuration _instance;
    
    public Configuration() {
    }

    public void load() {
        try {
            Properties propLoader = new Properties();
            propLoader.load(new FileInputStream("app.properties"));

            String strValue = propLoader.getProperty("mode");
            this.isLocal = strValue.equalsIgnoreCase("local");
            
            this.user = propLoader.getProperty("user");
            this.pass = propLoader.getProperty("pass");
            this.location = propLoader.getProperty("filePath");
            this.setAddress(propLoader.getProperty("address"));
            this.setPort(Integer.parseInt(propLoader.getProperty("port")));
            
            this.setAuth(new NtlmPasswordAuthentication("", this.user, this.pass));
            
        } catch (IOException ex) {
            Logger.getLogger(Configuration.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean isIsLocal() {
        return isLocal;
    }

    public void setIsLocal(boolean isLocal) {
        this.isLocal = isLocal;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    
    public static Configuration getInstance() {
        if (_instance == null) _instance = new Configuration();
        return _instance;
    }

    /**
     * @return the auth
     */
    public NtlmPasswordAuthentication getAuth() {
        return auth;
    }

    /**
     * @param auth the auth to set
     */
    public void setAuth(NtlmPasswordAuthentication auth) {
        this.auth = auth;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the port
     */
    public int getPort() {
        return port;
    }

    /**
     * @param port the port to set
     */
    public void setPort(int port) {
        this.port = port;
    }
    
    
}
