/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.config;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author NhuongND
 */
@XmlRootElement(name = "Parameters")
@XmlAccessorType(XmlAccessType.NONE)
public class Parameters {
    
    @XmlElement(name = "SPV")
    public int SPV;  
    @XmlElement(name = "WarningHitFuture")
    public int WarningHitFuture;   
    @XmlElement(name = "WarningHitLevel")
    public float WarningHitLevel;   
    @XmlElement(name = "WarningHitYellowDistance")
    public float WarningHitYellowDistance;   
    @XmlElement(name = "WarningHitDistance") 
    public float WarningHitDistance;   
    @XmlElement(name = "vectorAhead")
    public float vectorAhead;   
    @XmlElement(name = "heading")
    public boolean heading ;   
    @XmlElement(name = "assume")
    public boolean assume;   
    @XmlElement(name = "AcviteFilter")    
    public boolean AcviteFilter;  
    @XmlElement(name = "CheckVVPR")
    public boolean CheckVVPR;
    @XmlElement(name = "CheckConflict")
    public boolean CheckConflict;
    @XmlElement(name = "CheckDRAW")
    public boolean CheckDRAW;       
    @XmlElement(name = "CheckAMA")
    public boolean CheckAMA;   
    @XmlElement(name = "CheckTMA")
    public boolean CheckTMA;   
    @XmlElement(name = "CheckMSA")
    public boolean CheckMSA;   
    @XmlElement(name = "CheckOutScreen")
    public boolean CheckOutScreen;   
    @XmlElement(name = "CheckClimdDesc")
    public boolean CheckClimdDesc;   
    @XmlElement(name = "CheckTransfer")
    public boolean CheckTransfer;   
    @XmlElement(name = "Client")
    public boolean Client;   
    @XmlElement(name = "IsRecording")
    public boolean IsRecording;   
    @XmlElement(name = "RecordingStorageLocation")
    public String RecordingStorageLocation; 
    @XmlElement(name = "amaMargin")
    public int amaMargin;   
    @XmlElement(name = "to_feet")
    public float to_feet;
         
    public int LowFilter = 0;   
    public int HighFilter = 0;   
    public String CallsignFilter;   
    public boolean ModeHideLabel = false;  
    public String ConditionFilter = "AND";     
    
}
