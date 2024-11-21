/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.amhs.service.client;

import com.attech.asd.amhs.service.monitor.ThreadStatus;
import java.util.List;

/**
 *
 * @author andh
 */
public interface ClientEventHandler {
    void updateStatus(ConnectionEventType eventType,  List<ThreadStatus> threadStatusList);
}
