/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.common;

/**
 *
 * @author andh
 */
public enum WarnLevel {
    ALARM (0),
    WARN (1),
    DANGER (2);

    private final int value;

    WarnLevel(final int newValue) {
        value = newValue;
    }

    public int getValue() {
        return value;
    }
}
