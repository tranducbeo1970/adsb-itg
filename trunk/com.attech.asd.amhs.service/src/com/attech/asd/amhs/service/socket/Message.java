/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.amhs.service.socket;

import java.io.Serializable;

/**
 *
 * @author ANDH
 */
public class Message implements Serializable {

    private final String text;

    public Message(String text) {
	this.text = text;
    }

    public String getText() {
	return text;
    }
}
