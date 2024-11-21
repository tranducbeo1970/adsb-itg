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
public interface TargetValidator {
    int validate(Target target);
    void setEnableValidator(Boolean enable);
}
