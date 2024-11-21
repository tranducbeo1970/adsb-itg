/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.daemon.recorder;

import com.attech.asd.daemon.AppContext;
import com.attech.asd.database.AdapterObject;
import com.attech.asd.database.FileRecordDao;
import com.attech.asd.database.FusedFileRecordDao;
import com.attech.asd.database.entities.FileRecord;
import com.attech.asd.database.entities.FusedFileRecord;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;
import org.apache.log4j.Logger;

/**
 *
 * @author AnhTH
 */
public class CleanUpFile implements Runnable{
    private static final Logger logger = Logger.getLogger(CleanUpFile.class);
    private static final TimeZone utc = TimeZone.getTimeZone("UTC");
    
    private final FileRecordDao dao;
    private final FusedFileRecordDao daoF;
    private final AdapterObject ao;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat DOF_FORMAT = new SimpleDateFormat("yyMMdd");
    private List<FileRecord> listFileRecord;
    private List<FusedFileRecord> listFusedFileRecord;
    private Set<File> folderPaths;
    
    
    public CleanUpFile(){
        this.sdf.setTimeZone(utc);
        this.DOF_FORMAT.setTimeZone(utc);
        
        this.dao = new FileRecordDao();
        this.daoF = new FusedFileRecordDao();
        this.ao = new AdapterObject();
        this.listFileRecord = new ArrayList<>();
        this.listFusedFileRecord = new ArrayList<>();
        this.folderPaths = new HashSet<>();
    }
    
    private void cleanFile(){
        try {
        logger.info("Start clean up file record");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, 1 - AppContext.getDataLimit());
        String to = sdf.format(calendar.getTime());
        
        listFileRecord = dao.listByDate("", to);
        /* debug
        if (listFileRecord != null && listFileRecord.size() > 0) {
            listFileRecord.forEach((item) -> {
                System.out.println(item.getAbsolutePath());
            });
        }
        */
        
        for (FileRecord item : listFileRecord){
            File file = new File(item.getAbsolutePath());
            if (!file.exists()) {
                //logger.info(String.format("File %s not exist.", item.getAbsolutePath()));
                item.setStatus(3);
                dao.save(item);
            } else if (file.delete()) {
                logger.info(String.format("Delete file %s", item.getAbsolutePath()));
                item.setStatus(3);
                dao.save(item);
                folderPaths.add(file.getParentFile());
            }
        }
        
        folderPaths.forEach((item) -> {
            logger.info(String.format("Delete folder %s", item.getAbsolutePath()));
            item.delete();
        });
        
        folderPaths.clear();
        
        listFusedFileRecord = daoF.listByDate("", to);
        for (FusedFileRecord item : listFusedFileRecord){
            File file = new File(item.getAbsolutePath());
            if (!file.exists()) {
                //logger.info(String.format("File %s not exist.", item.getAbsolutePath()));
                item.setStatus(3);
                daoF.save(item);
            } else if (file.delete()) {
                logger.info(String.format("Delete file %s", item.getAbsolutePath()));
                item.setStatus(3);
                daoF.save(item);
                folderPaths.add(file.getParentFile());
            }
        }
        
        folderPaths.forEach((item) -> {
            logger.info(String.format("Delete folder %s", item.getAbsolutePath()));
            item.delete();
        });
        
        folderPaths.clear();
        logger.info("Clean File Finished!");
        } catch (Exception ex) {
            logger.error(ex);
        }
    }
    
    private void cleanFlightPlan(){
        try {
        logger.info("Start clean up flight plan");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, 1 - AppContext.getFlightPlanLimit());
        String to = DOF_FORMAT.format(calendar.getTime());
        logger.info("Clean up flight plan dof < " + to);
        ao.cleanFlightPlanBeforeDate(to);
        logger.info("Clean FPL Message Finished!");
        } catch (Exception ex) {
            logger.error(ex);
        }
    }
    
    @Override
    public void run() {
        cleanFile();
        cleanFlightPlan();
    }
    
}
