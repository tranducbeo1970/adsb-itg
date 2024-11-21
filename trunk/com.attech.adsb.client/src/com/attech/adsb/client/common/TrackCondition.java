/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.common;

/**
 *
 * @author ANDH
 */
public enum TrackCondition {
    NORMAL(0), ALARM(1), WARNING(2), DANGER(3);
    private final int value;

    TrackCondition(final int newValue) {
        value = newValue;
    }

    public int getValue() {
        return value;
    }
}
