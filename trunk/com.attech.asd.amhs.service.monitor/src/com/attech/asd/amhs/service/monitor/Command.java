/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.amhs.service.monitor;

import java.io.Serializable;

/**
 *
 * @author ANDH
 */
public class Command implements Serializable {

    private CommandType type;
    private Object argument;
    
    public Command() {
    }
    
    public Command(CommandType type, Object arg) {
	this.type = type;
	this.argument = arg;
    }

    public CommandType getType() {
	return type;
    }

    public void setType(CommandType type) {
	this.type = type;
    }

    public Object getArgument() {
	return argument;
    }

    public void setArgument(Object argument) {
	this.argument = argument;
    }
}
