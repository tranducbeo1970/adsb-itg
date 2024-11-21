/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.amhs.service;

/**
 *
 * @author ANDH
 */
public enum Priority {

    NORMAL(0), LOW(1), HIGH(2);

    public final int value;

    private Priority(int value) {
        this.value = value;
    }

    public static Priority valueOf(int val) {
        switch (val) {
            case 2:
                return HIGH;
            case 1:
                return LOW;
            default:
                return NORMAL;
        }
    }

}