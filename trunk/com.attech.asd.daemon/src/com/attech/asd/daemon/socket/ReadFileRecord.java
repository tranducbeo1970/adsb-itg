/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.daemon.socket;

import com.attech.asd.database.FileRecordDao;
import com.attech.asd.database.entities.FileRecord;
import com.attech.asd.database.entities.RecordItem;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Date;
import static jdk.nashorn.internal.objects.NativeError.printStackTrace;

/**
 *
 * @author NhuongND
 */
public class ReadFileRecord {

    public Integer fileId;

    public void setId(Integer fileId) {
        this.fileId = fileId;
    }

    public ArrayList<ArrayList<RecordItem>> readFile() throws FileNotFoundException, IOException {

        FileRecord fileRecord = new FileRecordDao().get(fileId);
        ArrayList<ArrayList<RecordItem>> lstObject = new ArrayList<>(3600);

        FileInputStream fis = null;
        try {
            fis = new FileInputStream(fileRecord.getAbsolutePath());
            boolean cont = true;
            ObjectInputStream input = new ObjectInputStream(fis);
            int index = 0;
            Date current = new Date();
            ArrayList<RecordItem> lstRecordItem = new ArrayList<>();
            while (cont) {
                try {
                    RecordItem obj = (RecordItem) input.readObject();

                    if (obj != null) {
                        current = new Date(obj.getTime());
                        System.out.println(current);
                        int second = current.getMinutes() * 60 + current.getSeconds();

                        if (second == index) {
                            lstRecordItem.add(obj);
                        } else {
                            if ((second - index) == 1) {
                                System.out.println(index);
                                lstObject.add(index, lstRecordItem);
                                lstRecordItem = new ArrayList<>();
                                lstRecordItem.add(obj);
                                index = second;
                            } else {
                                while ((second - index) > 1) {
                                    lstObject.add(index, lstRecordItem);
                                    lstRecordItem = new ArrayList<>();
                                    index++;
                                    if (index == second) {
                                        lstRecordItem.add(obj);
                                        index = second;
                                    } else {
                                        lstObject.add(index, lstRecordItem);
                                    }
                                }
                            }
                        }
                    } else {
                        lstObject.add(index, lstRecordItem);
                        cont = false;
                    }
                } catch (Exception e) {
                    cont = false;
                }
            }
        } catch (FileNotFoundException ex) {
            printStackTrace(ex);
        } finally {
            try {
                fis.close();
            } catch (IOException ex) {
                printStackTrace(ex);
            }
        }
        return lstObject;
    }

    public ArrayList<RecordItem> readFileRecord() throws FileNotFoundException, IOException {
        
        FileRecord fileRecord = new FileRecordDao().get(fileId);        
        ArrayList<RecordItem> lstItem = new ArrayList<>();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(fileRecord.getAbsolutePath());
            boolean cont = true;
            ObjectInputStream input = new ObjectInputStream(fis);

            while (cont) {
                try {
                    RecordItem obj = (RecordItem) input.readObject();

                    if (obj != null) {
                        lstItem.add(obj);
                    } else {
                        cont = false;
                    }
                } catch (Exception e) {
                    cont = false;
                }
            }

        } catch (FileNotFoundException ex) {
            printStackTrace(ex);
        } finally {
            try {
                fis.close();
            } catch (IOException ex) {
                printStackTrace(ex);
            }
        }
        return lstItem;
    }
}
