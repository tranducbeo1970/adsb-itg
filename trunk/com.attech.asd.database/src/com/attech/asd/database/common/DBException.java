/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.database.common;

/**
 *
 * @author andh
 */
public class DBException extends Exception {
    public DBException(Throwable err) {
        super(err);
    }
    
     public DBException(String err) {
        super(err);
    }
    
    public DBException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
