/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.cat02.v10;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 *
 * @author hong
 */
public class Common {
    
    public static String getLevel(int level) {
        StringBuilder builder = new StringBuilder();
        for (int i =0; i < level; i++) {
            builder.append("\t");
        }
        return builder.toString();
    }
    
    public static byte[] loadMessage(String filename) {
        // Decryptor01 decrypter = new Decryptor01();
        final File file = new File(filename);
        final long fileLength = file.length();
        if (fileLength > 65000) {
            System.out.println(String.format("File is too long (%s bytes)", fileLength));
            return null;
        }
            
        byte[] bytes = new byte[(int) fileLength];
        try {
            FileInputStream inputStream = new FileInputStream(file);
            int byts = inputStream.read(bytes, 0, (int) fileLength);
            System.out.println("Read " + byts + " (bytes)");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        
        return bytes;
    }
    
}
