/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.daemon.client;

import com.attech.cat21.v210.Cat21Message;

/**
 *
 * @author An
 */
public interface IValidation {
    boolean validate(Cat21Message message);
}
