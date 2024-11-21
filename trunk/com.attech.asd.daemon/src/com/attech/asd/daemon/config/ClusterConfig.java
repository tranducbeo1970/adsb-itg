/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.daemon.config;

import com.attech.asd.daemon.common.ExceptionHandler;
import com.sun.enterprise.ee.cms.core.GroupManagementService.MemberType;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author root
 */
public class ClusterConfig {

    // Constant
    private static final String CLIENT_NAME = "CLIENT_NAME";
    private static final String GROUP_NAME = "GROUP_NAME";
    private static final String MEMBER_TYPE = "MEMBER_TYPE";
    private static final String DISCOVERY_TIMEOUT = "DISCOVERY_TIMEOUT";
    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(ClusterConfig.class);
    private static ClusterConfig _instance;
    
    private Properties _properties;
    private String _client;
    private String _group;
    private MemberType _type;
    private int discoveryTimeout;
    
    
    public void load(String path)  {
        try {
            this._properties = new Properties();
            this._properties.load(new FileInputStream(path));
            this._client = this._properties.getProperty(CLIENT_NAME);
            this._group = this._properties.getProperty(GROUP_NAME);
            this._type = MemberType.valueOf(this.getProperties().getProperty(MEMBER_TYPE));
            this.discoveryTimeout = Integer.parseInt(this.getProperties().getProperty(DISCOVERY_TIMEOUT));
        } catch (IOException | NumberFormatException ex) {
            ExceptionHandler.handle(ex);
        }
    }

    public Properties getProperties() { return _properties; }

    public void setProperties(Properties properties) { this._properties = properties; }

    public String getClient() { return _client; }

    public void setClient(String client) { this._client = client; }

    public String getGroup() { return _group; }

    public void setGroup(String group) { this._group = group; }

    public MemberType getType() { return _type; }

    public void setType(MemberType type) { this._type = type; }
    
    public static ClusterConfig getInstance() {
        if (_instance == null) _instance= new ClusterConfig();
        return _instance;
    }

    /**
     * @return the discoveryTimeout
     */
    public int getDiscoveryTimeout() {
        return discoveryTimeout;
    }

    /**
     * @param discoveryTimeout the discoveryTimeout to set
     */
    public void setDiscoveryTimeout(int discoveryTimeout) {
        this.discoveryTimeout = discoveryTimeout;
    }
}
