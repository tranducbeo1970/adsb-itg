/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.administrator;

import com.attech.asd.administrator.common.ExceptionHandler;
import com.attech.asd.daemon.RecordItem;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author haianh
 */
public class FileReceiver implements Runnable{
    private String fileName;
    private String location;
    
    public FileReceiver(String location, String fileName){
        this.fileName = fileName;
        this.location = location;
    }
    
    @Override
    public void run() {
        List<RecordItem> listRecord = new ArrayList<>();
        boolean requestStop = false;
        Object obj = null;
        while (!requestStop) {
            try {
                    obj = AppContext.getInstance().getCommandSocket().getOIS().readObject();
                    listRecord = (List<RecordItem>) obj;
                    //System.out.println(listRecord.size());
                    requestStop = true;
            } catch (IOException | ClassNotFoundException ex) {
                System.out.println(String.format("%s : %s", obj, ex.getMessage()));
            }
        }
        if (listRecord.size() > 0) {
            try {
                File outputFile = new File(location, fileName);
                FileOutputStream fos = new FileOutputStream(outputFile);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                for (RecordItem item : listRecord){
                    oos.writeObject(item);
                }
                fos.close();
                oos.close();
            } catch (IOException ex){
                ExceptionHandler.handle(ex);
            }
        }
        JOptionPane.showMessageDialog(null, "Save File Suscessfully");
    }
    
}
