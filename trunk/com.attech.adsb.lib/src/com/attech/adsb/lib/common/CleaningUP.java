/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.lib.common;

import java.io.File;

/**
 *
 * @author andh
 */
public class CleaningUP {
    
    public CleaningUP() {
    }
    
    public void delete(File file) {
        File [] files = file.listFiles();
        for (File f : files) {
            if (f.isFile()) continue;
            if (f.getName().equalsIgnoreCase("50")
                    || f.getName().equalsIgnoreCase("100")
                    || f.getName().equalsIgnoreCase("100")
                    || f.getName().equalsIgnoreCase("150")
                    || f.getName().equalsIgnoreCase("200")) {
                System.out.print("> Delete " + f.getAbsolutePath() );
                deleteFolder(f);
                System.out.println("    [done]");
            } else {
                delete(f);
            }
            
        }
    }
    
    private static void deleteFolder(File folder) {
        File[] files = folder.listFiles();
        if (files != null) { //some JVMs return null for empty dirs
            for (File f : files) {
                if (f.isDirectory()) {
                    deleteFolder(f);
                } else {
                    f.delete();
                }
            }
        }
        folder.delete();
}
    
    public static void main (String[] args) {
        File f = new File("F:\\adsb\\report\\r2");
        CleaningUP cleaning = new CleaningUP();
        cleaning.delete(f);
    }
}
