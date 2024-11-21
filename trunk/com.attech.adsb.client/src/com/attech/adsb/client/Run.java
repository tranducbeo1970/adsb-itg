/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client;

import com.attech.adsb.client.common.AssemblyInfo;
import com.attech.adsb.client.common.Common;
import com.attech.adsb.client.common.MLogger;
import com.attech.adsb.client.common.ShutdownHook;
import com.attech.adsb.client.config.Configuration;
import com.attech.adsb.client.gui.Main;
import java.io.File;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;
import javax.swing.JOptionPane;
import org.apache.log4j.xml.DOMConfigurator;

/**
 *
 * @author andh
 */
public class Run {

    private static final MLogger logger = MLogger.getLogger(Run.class);
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
	DOMConfigurator.configure(Common.CFG_LOG);
        Configuration.instance().load();
        
        logger.info("PROGRAM START");
        Runtime.getRuntime().addShutdownHook(new ShutdownHook());
        
        if (!lockInstance(Configuration.instance().getPreference().getLockFilePath())) {
            JOptionPane.showMessageDialog(null, "Another instance of program has been running", "System message", JOptionPane.WARNING_MESSAGE);
            System.exit(0);
        }
	
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if (Configuration.instance().getPreference().getTheme().equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.error(ex);
        }
	//</editor-fold>
	
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new Main().setVisible(true);
                } catch (Exception ex) {
                    logger.error(ex);
                    System.exit(0);
                }
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
