/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.database.common;

/**
 *
 * @author NhuongND
 */
public class Constant {    

    public static final String DISK_PROPERTIES = "disk_properties";
    public static final String COMMAND_READ_FUSED_FILE = "readfusedfiletowrite"; 
    public static final String COMMAND_READ_FILE = "readfiletowrite"; 
    public static final String COMMAND_START_READ_FILE = "start_read_file";
    public static final String COMMAND_FILE_DATA = "file_data";    
    public static final String COMMAND_GET_STORAGE = "start_disk";
    public static final String COMMAND_DELETE_FILERECORD = "start_delete_filerecord";
    public static final String COMMAND_STOP_DELETE_FILERECORD = "stop_delete_filerecord";
    public static final String COMMAND_DOWNLOAD_FILE = "download_file";
    
    public static final String COMMAND_UPDATE_SERVER = "update_client";
    public static final String COMMAND_UPDATE_STORAGE = "update_storage";
    
    public static final String COMMAND_ACTIVE_RECEIVER = "active_receiver";
    public static final String COMMAND_DEACTIVE_RECEIVER = "deactive_receiver";
    public static final String COMMAND_ACTIVE_BROADCASTER = "active_broadcaster";
    public static final String COMMAND_DEACTIVE_BROADCASTER = "deactive_broadcaster";
    
    public static final String COMMAND_RELOAD_BROADCASTER = "reload_broadcaster";
    public static final String COMMAND_RELOAD_RECEIVER = "reload_receiver";
    public static final String COMMAND_RELOAD_MSG_ACC = "reload_msg_account";
    
    public static final String COMMAND_DELETE_BROADCASTER = "delete_broadcaster";
    public static final String COMMAND_DELETE_RECEIVER = "delete_receiver";
    
    public static final String NOTIFY_RELOAD_CLIENT = "notify_reload_client";
    public static final String NOTIFY_RELOAD_RECEIVER = "notify_reload_receiver";
}
