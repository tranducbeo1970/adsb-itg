/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.config;

/**
 *
 * @author NhuongND
 */
public class Parameter {
    
    public static int SPV;  
    public static int WarningHitFuture;   
    public static float WarningHitLevel;   
    public static float WarningHitYellowDistance;  
    public static float WarningHitDistance;   
    public static float vectorAhead;   
    public static boolean heading ;   
    public static boolean assume;    
    public static boolean AcviteFilter;  
    public static boolean CheckVVPR;
    public static boolean CheckConflict;
    public static boolean CheckDRAW;   
    public static boolean CheckAMA;  
    public static boolean CheckTMA;  
    public static boolean CheckMSA;   
    public static boolean CheckOutScreen; 
    public static boolean CheckClimdDesc;  
    public static boolean CheckTransfer;   
    public static boolean Client;   
    public static boolean IsRecording;   
    public static String RecordingStorageLocation;
    public static int amaMargin;   
    public static float to_feet;
         
    public static int LowFilter = 0;   
    public static int HighFilter = 0;   
    public static String CallsignFilter;   
    public static boolean ModeHideLabel = false;  
    public static String ConditionFilter = "AND";     
    
}
