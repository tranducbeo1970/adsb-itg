/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.gui.playback;

import com.attech.adsb.client.common.AppContext;
import com.attech.adsb.client.common.MLogger;
import com.attech.adsb.client.common.TargetManager;
import com.attech.asterix.cat21.v21.Decryptor;
import com.attech.asterix.cat21.v21.Message;
import java.awt.Cursor;
import java.io.File;
import java.io.FilenameFilter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 *
 * @author hong
 */
public class PlaybackDlg extends javax.swing.JDialog {
    private final Decryptor decryptor = new Decryptor();
    private final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    //private final DecimalFormat formatDecimal = new DecimalFormat("###,###,###,###");
    private final MLogger logger = MLogger.getLogger(PlaybackDlg.class);
    private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    private String recordLocation = "E:\\tmp";
    private final PlayListModel model;
    private File root;
    private final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);
    private ScheduledFuture<?> futureTask;
    private Runnable myTask;
    private Player player;
    private TargetManager targetManager;

    /**
     * Creates new form PlaybackGui
     *
     * @param parent
     * @param modal
     */
    public PlaybackDlg(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        model = new PlayListModel(tblPlaylist);
        model.createLineNumber();
        //lblFileName.setText("");
    }
    
    public void changeReadInterval(long time) {
        if (time > 0) {
            if (futureTask != null) {
                futureTask.cancel(true);
            }
            futureTask = scheduledExecutorService.scheduleAtFixedRate(myTask, 0, time, TimeUnit.MILLISECONDS);
        }
    }

    private void loadPlayList(Date date) {
        this.model.clear();
        String recordFolder = format.format(date);
        File directory = new File(root, recordFolder);
        if (!directory.exists() || !directory.isDirectory()) {
            return;
        }

        File[] files = directory.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".rcd");
            }
        });

        for (File f : files) {
            this.model.add(f);
        }
    }

    private void clear() {
        lblStart.setText("00:00");
        lblEnd.setText("00:00");
        lblCurrent.setText("00:00");
        spinProgress.setValue(0);
    }
    
    public void playerStateChangedActionPerformed(PlayerState state, long progress, List<byte[]> data) {
        switch (state) {
            case START:
                btnPause.setEnabled(true);
                btnStop.setEnabled(true);
//                btnSpeed.setEnabled(true);
                btnPlay.setEnabled(false);
                break;

            case PAUSE:
                btnPause.setEnabled(false);
                btnStop.setEnabled(true);
//                btnSpeed.setEnabled(true);
                btnPlay.setEnabled(true);
                break;

            case END:
                btnPause.setEnabled(false);
                btnStop.setEnabled(false);
//                btnSpeed.setEnabled(false);
                btnPlay.setEnabled(true);
                break;

            case PLAYING:
                int value = (int) (progress - player.getStart());
                spinProgress.setValue(value);
                lblCurrent.setText(getTimeInString(progress));
                System.out.println("Play data: " + data.size() + " (packages)");
                if (data == null || data.isEmpty()) {
                    break;
                }
                for (byte[] bytes : data) {
                    List<Message> message = decryptor.decrypt(bytes);
                    if (message == null) continue;
                    this.targetManager.updateTarget(message);
                }
                
                break;

            case STOPPED:
                btnPause.setEnabled(false);
                btnStop.setEnabled(false);
//                btnSpeed.setEnabled(false);
                btnPlay.setEnabled(true);
                break;
        }
    }
    
    private String getTimeInString(long time) {
        if (time == 0) return "00:00";
        return timeFormat.format(new Date(time*1000));
    }
 
    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtDate01 = new com.toedter.calendar.JDateChooser();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPlaylist = new javax.swing.JTable();
        spinProgress = new javax.swing.JSlider();
        lblStart = new javax.swing.JLabel();
        lblCurrent = new javax.swing.JLabel();
        lblEnd = new javax.swing.JLabel();
        btnSpeed = new javax.swing.JButton();
        btnStop = new javax.swing.JButton();
        btnPause = new javax.swing.JButton();
        btnPlay = new javax.swing.JButton();
        lblFileName = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        txtDate01.setDateFormatString("dd/MM/yyyy");
        txtDate01.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                txtDate01PropertyChange(evt);
            }
        });

        tblPlaylist.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblPlaylist.setRowHeight(22);
        tblPlaylist.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblPlaylist.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPlaylistMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblPlaylist);

        spinProgress.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spinProgressStateChanged(evt);
            }
        });
        spinProgress.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                spinProgressMouseDragged(evt);
            }
        });
        spinProgress.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                spinProgressMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                spinProgressMouseReleased(evt);
            }
        });

        lblStart.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblStart.setText("start");

        lblCurrent.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCurrent.setText("current");

        lblEnd.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblEnd.setText("end");

        btnSpeed.setText("1x");
        btnSpeed.setActionCommand("1");
        btnSpeed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSpeedActionPerformed(evt);
            }
        });

        btnStop.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/adsb/client/images/stop.png"))); // NOI18N
        btnStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStopActionPerformed(evt);
            }
        });

        btnPause.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/adsb/client/images/pause.png"))); // NOI18N
        btnPause.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPauseActionPerformed(evt);
            }
        });

        btnPlay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/adsb/client/images/play.png"))); // NOI18N
        btnPlay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPlayActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnSpeed)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblFileName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnStop, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPause, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPlay, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblStart, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(60, 60, 60)
                        .addComponent(lblCurrent, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblEnd, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(spinProgress, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtDate01, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtDate01, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(345, 345, 345)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblEnd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblStart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblCurrent, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spinProgress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(btnPlay, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPause, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnStop, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSpeed)
                    .addComponent(lblFileName, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(37, 37, 37)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(78, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtDate01PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txtDate01PropertyChange
        try {

            if (!evt.getPropertyName().equals("date")) {
                return;
            }
            this.setEnabled(false);
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

            this.model.clear();
            Date date = txtDate01.getDate();
            loadPlayList(date);

        } catch (Exception ex) {

        } finally {
            this.setEnabled(true);
            setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }

    }//GEN-LAST:event_txtDate01PropertyChange

    private void spinProgressStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spinProgressStateChanged
//        if (((JSlider ) evt.getSource()).getValueIsAdjusting()) {
//            // this.playList.seek(this.spinProgress.getValue());
//        }
    }//GEN-LAST:event_spinProgressStateChanged

    private void spinProgressMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_spinProgressMouseDragged
//        System.out.println("Drag " + this.spinProgress.getValue());
    }//GEN-LAST:event_spinProgressMouseDragged

    private void spinProgressMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_spinProgressMousePressed
//        this.playList.pause();
    }//GEN-LAST:event_spinProgressMousePressed

    private void spinProgressMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_spinProgressMouseReleased

//        this.playList.seek(this.spinProgress.getValue());
    }//GEN-LAST:event_spinProgressMouseReleased

    private void btnSpeedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSpeedActionPerformed
//        this.playList.increaseSpeed();
//        btnSpeed.setText(this.playList.getSpeed() + "x");
        try {
            int spd = Integer.parseInt(evt.getActionCommand());
            spd *=2;
            if (spd > 8) spd = 1;
            btnSpeed.setActionCommand(Integer.toString(spd));
            btnSpeed.setText(spd + "X");
            if (this.player != null && this.player.isPlaying()) {
                this.player.changeSpeed(spd);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(rootPane, ex.getMessage());
        }
    }//GEN-LAST:event_btnSpeedActionPerformed

    private void btnStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStopActionPerformed
//        this.playList.stop();
        this.player.stop();
    }//GEN-LAST:event_btnStopActionPerformed

    private void btnPauseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPauseActionPerformed
//        this.playList.pause();
        this.player.pause();
    }//GEN-LAST:event_btnPauseActionPerformed

    private void btnPlayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPlayActionPerformed
        /*
        int spd = Integer.parseInt(btnSpeed.getActionCommand());
        player.play(spd);
        */
        try {

            if (player != null && player.isPlaying()) {
                return;
            }
            int row = tblPlaylist.getSelectedRow(); // select a row
            if (row < 0){
                JOptionPane.showMessageDialog(rootPane, "Just pick only one record");
                return;
            }
            String filePath = (String) tblPlaylist.getValueAt(row, 0);
            player = new Player(new File(filePath));
            player.load();
            
            lblStart.setText(getTimeInString(player.getStart()));
            lblEnd.setText(getTimeInString(player.getEnd()));
            //lblFileName.setText("");
            
            int maximum = (int)( player.getEnd() - player.getStart());
            spinProgress.setMinimum(0);
            spinProgress.setMaximum(maximum);
            spinProgress.setValue(0);
            player.setNotify(this::playerStateChangedActionPerformed);
            int spd = Integer.parseInt(btnSpeed.getActionCommand());
            player.play(spd);
            
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(rootPane, ex.getMessage());
        }
    }//GEN-LAST:event_btnPlayActionPerformed

    private void tblPlaylistMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPlaylistMouseClicked
        try {
            if (evt.getClickCount() != 2) {
                return;
            }

            if (player != null && player.isPlaying()) {
                player.stop();
            }

            JTable target = (JTable) evt.getSource();
            int row = target.getSelectedRow(); // select a row
            String filePath = (String) target.getValueAt(row, 0);
            player = new Player(new File(filePath));
            player.load();
            
            lblStart.setText(getTimeInString(player.getStart()));
            lblEnd.setText(getTimeInString(player.getEnd()));
            
            int maximum = (int)( player.getEnd() - player.getStart());
            spinProgress.setMinimum(0);
            spinProgress.setMaximum(maximum);
            spinProgress.setValue(0);
            player.setNotify(this::playerStateChangedActionPerformed);
            int spd = Integer.parseInt(btnSpeed.getActionCommand());
            player.play(spd);
            
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(rootPane, ex.getMessage());
        }
    }//GEN-LAST:event_tblPlaylistMouseClicked

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        // TODO add your handling code here:
        try {
            root = new File(recordLocation);
        Date date = new Date();
        txtDate01.setDate(date);
        loadPlayList(date);
        clear();
        } catch (Exception ex) {
            logger.error(ex);
        }
        
    }//GEN-LAST:event_formComponentShown

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        if (player != null && player.isPlaying())
            player.stop();
        player = null;
        AppContext.instance().setPlaybackMode(false);
    }//GEN-LAST:event_formWindowClosing

    /**
     * @param targetManager the targetManager to set
     */
    public void setTargetManager(TargetManager targetManager) {
        this.targetManager = targetManager;
    }

    /**
     * @param recordLocation the recordLocation to set
     */
    public void setRecordLocation(String recordLocation) {
        this.recordLocation = recordLocation;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PlaybackDlg.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PlaybackDlg.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PlaybackDlg.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PlaybackDlg.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                PlaybackDlg dialog = new PlaybackDlg(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnPause;
    private javax.swing.JButton btnPlay;
    private javax.swing.JButton btnSpeed;
    private javax.swing.JButton btnStop;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCurrent;
    private javax.swing.JLabel lblEnd;
    private javax.swing.JLabel lblFileName;
    private javax.swing.JLabel lblStart;
    private javax.swing.JSlider spinProgress;
    private javax.swing.JTable tblPlaylist;
    private com.toedter.calendar.JDateChooser txtDate01;
    // End of variables declaration//GEN-END:variables
}
