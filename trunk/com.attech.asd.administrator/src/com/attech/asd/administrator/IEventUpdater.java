/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.administrator;

import com.attech.asd.administrator.common.ConnectionEventType;
import com.attech.asd.daemon.ServerInfo;

/**
 *
 * @author AnhTH
 */
public interface IEventUpdater {
    void updateInfo(ConnectionEventType eventType, ServerInfo data);
}
