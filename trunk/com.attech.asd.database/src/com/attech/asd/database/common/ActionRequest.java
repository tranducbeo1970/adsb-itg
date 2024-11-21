/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.database.common;

import java.io.Serializable;

/**
 *
 * @author AnhTH
 */
public enum ActionRequest implements Serializable{
    ACTIVE_RECEIVER,// v
    DEACTIVE_RECEIVER,// v
    RELOAD_RECEIVER,// v
    DELETE_RECEIVER,// v
    ACTIVE_CLIENT,// v
    DEACTIVE_CLIENT,// v
    RELOAD_CLIENT,// v
    DELETE_CLIENT,// v 
    RELOAD_MSG_ACC, 
    DELETE_MSG_ACC, 
    GET_STORAGE,
    READ_FILE, // v
    DELETE_FILE, // v
    DELETE_FUSED_FILE, // v
    DOWNLOAD_FILE, // v
    UPDATE_SERVER, // v
    ACTION_COMPLETED,
    RELOAD_CONFIGURATION,
    DO_NOTHING
}
