/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.attech.adsb.playbackserver;

/**
 *
 * @author andh
 */
public interface IStateChangeListener {
    
    public void StateChange(int index, String state);
    
}
