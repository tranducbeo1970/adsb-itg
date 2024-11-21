/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.record.test;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.util.List;

/**
 *
 * @author root
 */
public class Deserializing<T> {
    private String fileName ;
    
    public Deserializing(String fileName) {
        this.fileName = fileName;
    }
    
    public List<T> getObject() {
        try {
            //use buffering
            InputStream file = new FileInputStream("quarks.ser");
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);
            try {
                List<T> recoveredQuarks = (List<T>) input.readObject();
                return recoveredQuarks;
            } finally {
                input.close();
            }
        } catch (ClassNotFoundException | IOException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }
}
