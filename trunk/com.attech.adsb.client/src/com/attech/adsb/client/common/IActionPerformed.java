/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.common;

import com.attech.adsb.client.common.enums.ActionType;

/**
 *
 * @author ANDH
 */
public interface IActionPerformed {
    void locate(ActionType type, Object param);
}
