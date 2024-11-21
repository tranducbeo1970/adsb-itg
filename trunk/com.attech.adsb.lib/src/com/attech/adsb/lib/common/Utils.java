/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.lib.common;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Workbook;

/**
 *
 * @author andh
 */
public class Utils {
    public static void save(File file, Workbook workbook) {
        try {
            // System.out.println("Append to File");
            // File f = new File("F:\\Projects\\ADSB\\ATM\\test-data\\BH.xlsx");
            
            // final File file = new File (this.config.getOutput(), name + ".xlsx");
            if (file.exists()) file.delete();
            // System.out.printf("Saving report %s ", file.getName());
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
            // FileOutputStream stream = new FileOutputStream(file);
            workbook.write(bufferedOutputStream);
            bufferedOutputStream.close();
            // System.out.println(" [done]");
            
            /*
            if (this.config.isOutputDetail()) {
                File outputDetail = new File(this.config.getOutput(), name);
                if (outputDetail.exists()) outputDetail.delete();
                outputDetail.mkdir();
                for (Report report : this.getReports()) {
                    System.out.printf("Export detail of report %s %n", report.getName());
                    report.exportDetailData(outputDetail);
                }
            }
            */
            
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
