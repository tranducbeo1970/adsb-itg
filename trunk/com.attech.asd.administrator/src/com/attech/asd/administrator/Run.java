/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.administrator;

import com.attech.asd.administrator.common.ExceptionHandler;
import com.attech.asd.administrator.common.Res;
import com.attech.asd.database.Base;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;
import javax.swing.JOptionPane;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

/**
 *
 * @author Le Quang Tung
 */
public class Run {
    
    private final static String DATABASE_MAPPING = "/config/database.xml";
    private static final String CONFIG_LOG = "/config/log.xml";
    private final static Logger logger = Logger.getLogger(Run.class);

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        Run run = new Run();
        BasicConfigurator.configure();
        
        if (!lockInstance("lock")) {
            JOptionPane.showMessageDialog(null, "Another instance of program has been running", "System message", JOptionPane.WARNING_MESSAGE);
            System.exit(0);
        }
        
        logger.info("LOADING CONFIG");
        run.loadConfiguration();
        try {
            run.run();
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
    }

    private void loadConfiguration() throws IOException {
        DOMConfigurator.configure(new File(".").getCanonicalPath() + Res.CFG_LOG);
        String theme = "Windows";
        logger.info("PROGRAM STARTED");

        try {
            logger.info("CONNECT TO DATABASE");
            Base.configure(new File(".").getCanonicalPath() + Res.CFG_DATABASE);
            AppContext.getInstance();
            theme = AppContext.getTheme();
        } catch (Exception ex) {
            logger.error(ex.getMessage());

            JOptionPane.showMessageDialog(null, "Could not open connection to database", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if (theme.equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            ExceptionHandler.handle(ex);
        }
    }

    private void run() {
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Login dialog = new Login();
                dialog.setVisible(true);
            }
        });
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
