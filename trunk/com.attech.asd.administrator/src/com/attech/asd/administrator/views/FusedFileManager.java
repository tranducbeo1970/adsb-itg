/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.administrator.views;

import com.attech.asd.administrator.AppContext;
import com.attech.asd.administrator.FileReceiver;
import com.attech.asd.administrator.common.CustomDialog;
import com.attech.asd.administrator.common.ExceptionHandler;
import com.attech.asd.administrator.common.NormalTableModel;
import com.attech.asd.database.FusedFileRecordDao;
import com.attech.asd.database.common.ActionRequest;
import com.attech.asd.database.common.Command;
import com.attech.asd.database.common.Constant;
import com.attech.asd.database.entities.FusedFileRecord;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *
 * @author AnhTH
 */
public class FusedFileManager extends CustomDialog {

    private final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(FusedFileManager.class);
    private final SimpleDateFormat dor = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final TimeZone utc = TimeZone.getTimeZone("UTC");

    private final String COL_NO = "#";
    private final String COL_SIC = "SIC";
    private final String COL_FROM = "FROM";
    private final String COL_TO = "TO";
    private final String COL_PATH = "FULL PATH";
    private final String COL_FILENAME = "FILENAME";
    private final String COL_PACK = "PACKGAGE";
    private final String COL_MESSAGE = "MESSAGE";
    private final String COL_STATUS = "STATUS";
    private final String COL_ID = "ID";
    private final String COL_STATUS_NO = "STATUS_NO";

    private final NormalTableModel tableModel;

    public FusedFileManager(java.awt.Frame parent, boolean modal) throws ParseException {
        super(parent, modal);
        initComponents();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        dor.setTimeZone(utc);
        this.tableModel = new NormalTableModel(tblFileRecord);
        initialTable();
//        bindTable();
        btnDelete.setEnabled(AppContext.getInstance().connectToServer);
        btnViewData.setEnabled(AppContext.getInstance().connectToServer);
        btnDownload.setEnabled(AppContext.getInstance().connectToServer);
    }


    public void setFromDate(String date) {
        ((JTextField) txtFromDate.getDateEditor().getUiComponent()).setText(date);
    }

    public void setToDate(String date) {
        ((JTextField) txtToDate.getDateEditor().getUiComponent()).setText(date);
    }

    private void setColumnWidth(JTable table, String name, int min, int max, int prefer) {
        table.getColumn(name).setMinWidth(min);
        table.getColumn(name).setMaxWidth(max);
        table.getColumn(name).setPreferredWidth(prefer);
    }

    private void initialTable() {
        tableModel.addColumn(COL_NO);
        tableModel.addColumn(COL_SIC);
        tableModel.addColumn(COL_FROM);
        tableModel.addColumn(COL_TO);
        tableModel.addColumn(COL_PATH);
        tableModel.addColumn(COL_FILENAME);
        tableModel.addColumn(COL_PACK);
        tableModel.addColumn(COL_MESSAGE);
        tableModel.addColumn(COL_STATUS);
        tableModel.addColumn(COL_ID);
        tableModel.addColumn(COL_STATUS_NO);

        setColumnWidth(tblFileRecord, COL_NO, 30, 30, 30);
        setColumnWidth(tblFileRecord, COL_SIC, 0, 0, 0);
        setColumnWidth(tblFileRecord, COL_FROM, 180, 180, 180);
        setColumnWidth(tblFileRecord, COL_TO, 180, 180, 180);
        setColumnWidth(tblFileRecord, COL_PATH, 0, 0, 0);
        //setColumnWidth(tblFileRecord, COL_FILENAME, 200, 200, 200);
        setColumnWidth(tblFileRecord, COL_PACK, 0, 0, 0);
        setColumnWidth(tblFileRecord, COL_MESSAGE, 0, 0, 0);
        setColumnWidth(tblFileRecord, COL_STATUS, 70, 70, 70);
        setColumnWidth(tblFileRecord, COL_ID, 0, 0, 0);
        setColumnWidth(tblFileRecord, COL_STATUS_NO, 0, 0, 0);

    }

    public void bindTable() throws ParseException {
        final String from = ((JTextField) txtFromDate.getDateEditor().getUiComponent()).getText();
        final String to = ((JTextField) txtToDate.getDateEditor().getUiComponent()).getText();

        // validate form
        if (from.equals("") || to.equals("")) {
            JOptionPane.showMessageDialog(this.rootPane, "Please enter validated time.", "Notify", JOptionPane.INFORMATION_MESSAGE);
            if (from.equals("")) {
                txtFromDate.grabFocus();
            }
            if (to.equals("")) {
                txtToDate.grabFocus();
            }
        } else {
            tableModel.setNumRows(0);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setTimeZone(utc);
            Date date = sdf.parse(to);
            // Tang ToDate thêm 1 ngày    
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeZone(utc);
            calendar.setTime(date);
            calendar.add(Calendar.DATE, +1);
            String toPlus = sdf.format(calendar.getTime());

            //List<FileRecord> lstFileRecord = new FileRecordDao().listFileRecordBySensorAndDate(sensor, from, toPlus);
            List<FusedFileRecord> records = new FusedFileRecordDao().listFusedFileRecordByDate(from, toPlus);
            if (records == null || records.size() == 0) {
                JOptionPane.showMessageDialog(this.rootPane, "No Data.", "Notify", JOptionPane.INFORMATION_MESSAGE);
                //return;
            }
            int index = 1;
            for (FusedFileRecord record : records) {
                String stt = "Done";
                if (record.getStatus() == 0) {
                    stt = "Recording";
                } else if (record.getStatus() == 3) {
                    stt = "Deleted";
                }
                tableModel.addRow(new Object[]{
                    index,
                    0,
                    dor.format(record.getFromtime()),
                    (record.getTotime() != null ) ? dor.format(record.getTotime()) : "",
                    record.getAbsolutePath(),
                    record.getFileName(),
                    record.getCountPackage(),
                    record.getCountMessage(),
                    stt,
                    record.getId(),
                    record.getStatus()
                });
                tableModel.setCellColor(index - 1, 8, (record.getStatus() == 3) ? Color.GRAY : null);
                index++;
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblFromDate = new javax.swing.JLabel();
        lblToDate = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblFileRecord = new javax.swing.JTable();
        btnViewData = new javax.swing.JButton();
        btnClose = new javax.swing.JButton();
        txtFromDate = new com.toedter.calendar.JDateChooser();
        txtToDate = new com.toedter.calendar.JDateChooser();
        btnSearch = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnDownload = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Fused File Manager -  ADSB Administrator Terminal 1.0.0");
        setResizable(false);

        lblFromDate.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblFromDate.setText("From Date:");

        lblToDate.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblToDate.setText("To Date:");

        tblFileRecord.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tblFileRecord.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblFileRecord.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblFileRecordMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblFileRecord);

        btnViewData.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/viewstack.png"))); // NOI18N
        btnViewData.setText("View");
        btnViewData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewDataActionPerformed(evt);
            }
        });

        btnClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/door_out.png"))); // NOI18N
        btnClose.setText("Close");
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        txtFromDate.setDateFormatString("yyyy-MM-dd");
        txtFromDate.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                txtFromDatePropertyChange(evt);
            }
        });

        txtToDate.setDateFormatString("yyyy-MM-dd");
        txtToDate.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                txtToDatePropertyChange(evt);
            }
        });

        btnSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/search-16.png"))); // NOI18N
        btnSearch.setText("Search");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/delete1.png"))); // NOI18N
        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnDownload.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/download_16.png"))); // NOI18N
        btnDownload.setText("Download");
        btnDownload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDownloadActionPerformed(evt);
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
                        .addComponent(btnDelete)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnDownload)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnViewData)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnClose))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblFromDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblToDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtToDate, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                            .addComponent(txtFromDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSearch)))
                .addContainerGap())
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(btnSearch)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblFromDate, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtFromDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblToDate)
                            .addComponent(txtToDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnViewData)
                    .addComponent(btnClose)
                    .addComponent(btnDelete)
                    .addComponent(btnDownload))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCloseActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        try {
            this.bindTable();
        } catch (ParseException ex) {
            logger.error(ex.getMessage());
        }
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnViewDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewDataActionPerformed
        if (!tblFileRecord.getColumnSelectionAllowed() && tblFileRecord.getRowSelectionAllowed()) {
            try {
                int index = tblFileRecord.getSelectedRow();
                if (index < 0) {
                    JOptionPane.showMessageDialog(this, "Just pick only one record!", "Notify", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    int status = (int) tableModel.getValueAt(index, 10);
                    int id = (int) tableModel.getValueAt(index, 9);
                    //Sensors sensor = new SensorsDao().getSensorBySic(sic);
                    switch (status) {
                        case 0:
                            JOptionPane.showMessageDialog(this, "File not ready.", "Notify", JOptionPane.INFORMATION_MESSAGE);
                            return;
                        case 3:
                            JOptionPane.showMessageDialog(this, "File not found.", "Notify", JOptionPane.INFORMATION_MESSAGE);
                            return;
                        default:
                            AsterixFusedData dialog = new AsterixFusedData(null, true, id);
                            dialog.setVisible(true);
                            break;
                    }
                }
            } catch (Exception ex) {
                logger.error(ex.getMessage());
            }
        }
    }//GEN-LAST:event_btnViewDataActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        if (!tblFileRecord.getColumnSelectionAllowed() && tblFileRecord.getRowSelectionAllowed()) {
            try {
                int index = tblFileRecord.getSelectedRow();
                if (index < 0) {
                    JOptionPane.showMessageDialog(this, "Just pick only one record!", "Notify", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    int status = (int) tableModel.getValueAt(index, 10);
                    switch (status) {
                        case 0:
                            JOptionPane.showMessageDialog(this, "File not ready.", "Notify", JOptionPane.INFORMATION_MESSAGE);
                            return;
                        case 3:
                            JOptionPane.showMessageDialog(this, "File not found.", "Notify", JOptionPane.INFORMATION_MESSAGE);
                            return;
                        default:
                            Integer confirm = JOptionPane.showConfirmDialog(this.rootPane, "Are you sure want to delete?", "Notify", JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE);
                            if (confirm == JOptionPane.YES_OPTION) {
                                List<Object> objs = new ArrayList<>();
                                objs.add((Integer) tableModel.getValueAt(index, 9));
                                Command command = new Command();
                                command.setCmd(ActionRequest.DELETE_FUSED_FILE);
                                command.setParams(objs);
                                AppContext.getInstance().getCommandSocket().getOOS().writeObject(command);

                                // NEED TO REVIEW
                                Command deleteFileReceiver;
                                try {
                                    while ((deleteFileReceiver = (Command) AppContext.getInstance().getCommandSocket().getOIS().readObject()) != null) {
                                        if (deleteFileReceiver.getCmd() == ActionRequest.ACTION_COMPLETED) {
                                            try {
                                                this.bindTable();
                                            } catch (ParseException ex) {
                                                logger.error(ex.getMessage());
                                            }
                                            break;
                                        }
                                    }
                                } catch (IOException ex) {
                                    logger.error(ex.getMessage());
                                }
                            }
                            break;
                    }
                }
            } catch (ClassNotFoundException | IOException ex) {
                logger.error(ex.getMessage());
            }
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnDownloadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDownloadActionPerformed
        if (!tblFileRecord.getColumnSelectionAllowed() && tblFileRecord.getRowSelectionAllowed()) {
            //int[] lstIndex = tblFileRecord.getSelectedRows();
            int index = tblFileRecord.getSelectedRow();
            if (index < 0) {
                JOptionPane.showMessageDialog(this, "Just pick only one record!", "Notify", JOptionPane.INFORMATION_MESSAGE);
            } else {
                String pathFile = (String) tableModel.getValueAt(index, 4);
                String fileName = (String) tableModel.getValueAt(index, 5);

                int status = (int) tableModel.getValueAt(index, 10);
                switch (status) {
                    case 0:
                        JOptionPane.showMessageDialog(this, "File not ready.", "Notify", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    case 3:
                        JOptionPane.showMessageDialog(this, "File not found.", "Notify", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    default:
                        JFileChooser chooser = new JFileChooser();
                        File file;
                        file = new File(".");
                        chooser.setCurrentDirectory(file);
                        chooser.setDialogTitle("SELECT FOLDER TO SAVE...");
                        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                        chooser.setAcceptAllFileFilterUsed(false);
                        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                            String outputFolder = chooser.getSelectedFile().getPath();
                            List<Object> objs = new ArrayList<>();
                            objs.add(pathFile);
                            Thread thread = new Thread(new FileReceiver(outputFolder, fileName));
                            thread.start();
                            Command command = new Command();
                            command.setCmd(ActionRequest.DOWNLOAD_FILE);
                            command.setParams(objs);
                            try {
                                AppContext.getInstance().getCommandSocket().getOOS().writeObject(command);
                            } catch (IOException ex) {
                                logger.error(ex.getMessage());
                            }
                        }
                        break;
                }
            }
        }
    }//GEN-LAST:event_btnDownloadActionPerformed

    private void txtFromDatePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txtFromDatePropertyChange
        String getDate = ((JTextField) txtFromDate.getDateEditor().getUiComponent()).getText();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String today = sdf.format(date);
        if (getDate.equals("")) {
            ((JTextField) txtFromDate.getDateEditor().getUiComponent()).setText(today);
        }
    }//GEN-LAST:event_txtFromDatePropertyChange

    private void txtToDatePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txtToDatePropertyChange
        String getDate = ((JTextField) txtToDate.getDateEditor().getUiComponent()).getText();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String today = sdf.format(date);
        if (getDate.equals("")) {
            ((JTextField) txtToDate.getDateEditor().getUiComponent()).setText(today);
        }
    }//GEN-LAST:event_txtToDatePropertyChange

    private void tblFileRecordMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblFileRecordMousePressed
        try {
            if (evt.getButton() != 1) {
                return;
            }
            JTable table = (JTable) evt.getSource();
            Point p = evt.getPoint();
            int row = table.rowAtPoint(p);
            if (evt.getClickCount() != 2) {
                return;
            }
            ;
            JOptionPane.showMessageDialog(this.rootPane, (String) table.getValueAt(row, 4), "File Path (on Server)", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            ExceptionHandler.handle(ex);
        }
    }//GEN-LAST:event_tblFileRecordMousePressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnDownload;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnViewData;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblFromDate;
    private javax.swing.JLabel lblToDate;
    private javax.swing.JTable tblFileRecord;
    private com.toedter.calendar.JDateChooser txtFromDate;
    private com.toedter.calendar.JDateChooser txtToDate;
    // End of variables declaration//GEN-END:variables
}
