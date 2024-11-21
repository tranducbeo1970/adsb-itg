/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.server.playback.handle;

import java.util.EventObject;

/**
 *
 * @author root
 */
public class ControlCommandEvent extends EventObject  {
    
    private int command;
    
    public ControlCommandEvent(Object source, int command) {
        super(source);
        this.command = command;
    }

    public int getCommand() {
        return command;
    }

    public void setCommand(int command) {
        this.command = command;
    }
}
