/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/*

DUC SUA NGAY 15/11/2024

CLASS
package com.attech.asd.daemon.receiver;
private void receivingUDPMulticast() throws UnknownHostException, IOException {

*/


/*

Phat messaages
public class SimpleTransferPackage 

*/
        
package com.attech.asd.daemon;

import com.attech.asd.daemon.client.ClientManager;
import com.attech.asd.daemon.receiver.ReceiverManager;
import com.attech.asd.daemon.recorder.CleanUpFile;
import com.attech.asd.daemon.socket.CommandManager;
import com.attech.asd.daemon.socket.UpdatingManager;
import com.attech.asd.database.Base;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import com.attech.asd.database.FileRecordDao;
import com.attech.asd.database.FusedFileRecordDao;
import com.attech.asd.database.entities.FileRecord;
import com.attech.asd.database.entities.FusedFileRecord;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;

/**
 *
 * @author AnhTH
 */
public class Run {
    private static final String CONFIG_LOG = "log.xml";
    private static final Logger logger = Logger.getLogger(Run.class);
    private static final String DATABASE_MAPPING = "database.xml";
    
    private static final ScheduledExecutorService cleanupFileScheduler = Executors.newScheduledThreadPool(5);
    
    public static void main(String[] args) throws IOException {
        /*
        if (!lockInstance("lock")) {
            JOptionPane.showMessageDialog(null, "Another instance of program has been running", "System message", JOptionPane.WARNING_MESSAGE);
            //System.out.println("Another instance of program has been running.");
            System.exit(0);
        }
        */
        
        logger.info("LOADING CONFIG");
        BasicConfigurator.configure();
        //DOMConfigurator.configure(new File(".").getCanonicalPath() + CONFIG_LOG);//
        //Base.configure(new File(".").getCanonicalPath() + DATABASE_MAPPING);//
        DOMConfigurator.configure(CONFIG_LOG);//
        Base.configure(DATABASE_MAPPING);//
        AppContext.getInstance();
        
        // ReceiverManager & ClientManager
        ReceiverManager.getInstance().launch();
        ClientManager.getInstance().launch();
        
        // Start DataFusion
        logger.info("START DATAFUSION 18/11/2024");
        //--DataFusion.getInstance().start();
        
        // UpdatingManager & CommandManager
        CommandManager.getInstance().start();
        UpdatingManager.getInstance().start();
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        //System.out.println(calendar.get(Calendar.HOUR_OF_DAY));
//        cleanupFileScheduler.scheduleAtFixedRate(new CleanUpFile(), 0, 24, TimeUnit.HOURS);
//        logger.info("CleanUp thread has been scheduled.");
        
        CorrectionAfterCrash();
    }
    
    private static void CorrectionAfterCrash(){
        List<FileRecord> listFileRecord = new FileRecordDao().listCorrectionFile();
        if (listFileRecord != null && listFileRecord.size() > 0){
            listFileRecord.forEach(record -> {
                try {
                    Path file = Paths.get(record.getAbsolutePath());
                    BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);
                    record.setTotime(new Date(attr.lastModifiedTime().toMillis()));
                    record.setStatus(1);
                    record.save();
                    System.out.printf("Corrected file %s -> %s\n", record.getFileName(), record.getTotime());
                } catch (IOException e) {
                    logger.error(e);
                }
            });
            listFileRecord.clear();
        }
        
        
        List<FusedFileRecord> listFusedFileRecord = new FusedFileRecordDao().listCorrectionFile();
        if (listFusedFileRecord != null && listFusedFileRecord.size() > 0){
            listFusedFileRecord.forEach(record -> {
                try {
                    Path file = Paths.get(record.getAbsolutePath());
                    BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);
                    record.setTotime(new Date(attr.lastModifiedTime().toMillis()));
                    record.setStatus(1);
                    record.save();
                    System.out.printf("Corrected file %s -> %s\n", record.getFileName(), record.getTotime());
                } catch (IOException e) {
                    logger.error(e);
                }
            });
            listFusedFileRecord.clear();
        }
        
    }
    
    private static boolean lockInstance(final String lockFile) {
        try {
            final File file = new File(lockFile);
            final RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
            final FileLock fileLock = randomAccessFile.getChannel().tryLock();
            if (fileLock != null) {
                Runtime.getRuntime().addShutdownHook(new Thread() {
		    @Override
                    public void run() {
                        try {
                            fileLock.release();
                            randomAccessFile.close();
                            file.delete();
                        } catch (Exception e) {
                            logger.error("Unable to remove lock file: " + lockFile, e);
                        }
                    }
                });
                return true;
            }
        } catch (Exception e) {
            logger.error("Unable to create and/or lock file: " + lockFile, e);
        }
        return false;
    }
    
    
}
