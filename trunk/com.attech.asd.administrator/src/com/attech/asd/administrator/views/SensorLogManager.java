/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.administrator.views;

import com.attech.asd.administrator.AppContext;
import com.attech.asd.administrator.common.CustomDialog;
import com.attech.asd.administrator.common.ExceptionHandler;
import com.attech.asd.administrator.common.NormalTableModel;
import com.attech.asd.database.SensorLogsDao;
import com.attech.asd.database.SensorsDao;
import com.attech.asd.database.StationsDao;
import com.attech.asd.database.entities.SensorLogs;
import com.attech.asd.database.entities.Sensors;
import com.attech.asd.database.entities.Stations;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *
 * @author AnhTH
 */
public class SensorLogManager extends CustomDialog {

    private final static String COL_NO = "#";
    private final static String COL_SIC = "SIC";
    private final static String COL_CREATEDATE = "DATE";
    private final static String COL_CONTENT = "CONTENT";
    private final static String COL_PRIORITY = "PRIORITY";
    private final static String COL_STATUS = "STATUS";
    private final static String COL_ID = "ID";
    private final static String COL_PRIORITY_NO = "PRIORITY_NO";

    private final NormalTableModel tableModel;

    public SensorLogManager(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        this.tableModel = new NormalTableModel(tblFileRecord);
        initialTable();
        // list Station
        List<Stations> lstStations = new StationsDao().listStations();
        for (Stations station : lstStations) {
            cmbStations.addItem(station.getStationName());
        }
        bindTable();
    }

    public void setSensor(Stations station) {
        cmbSensors.removeAllItems();
        List<Sensors> lstSensors = new SensorsDao().listByStation(station);
        for (Sensors sensor : lstSensors) {
            cmbSensors.addItem(sensor.getSic());
        }
    }

    public void setSensor(Sensors sensor) {
        cmbStations.setSelectedItem(sensor.getStation().getStationDescription());
        cmbSensors.setSelectedItem(sensor.getSic());
    }

    public void setFromDate(String date) {
        ((JTextField) txtFromDate.getDateEditor().getUiComponent()).setText(date);
    }

    private void setColumnWidth(JTable table, String name, int min, int max, int prefer) {
        table.getColumn(name).setMinWidth(min);
        table.getColumn(name).setMaxWidth(max);
        table.getColumn(name).setPreferredWidth(prefer);
    }

    private void initialTable() {
        tableModel.addColumn(COL_ID);
        tableModel.addColumn(COL_NO);
        tableModel.addColumn(COL_SIC);
        tableModel.addColumn(COL_CREATEDATE);
        tableModel.addColumn(COL_CONTENT);
        tableModel.addColumn(COL_PRIORITY);
        tableModel.addColumn(COL_STATUS);
        tableModel.addColumn(COL_PRIORITY_NO);

        setColumnWidth(tblFileRecord, COL_ID, 0, 0, 0);
        setColumnWidth(tblFileRecord, COL_NO, 30, 30, 30);
        setColumnWidth(tblFileRecord, COL_SIC, 40, 40, 40);
        setColumnWidth(tblFileRecord, COL_CREATEDATE, 180, 180, 180);
        //setColumnWidth(tblFileRecord, COL_CONTENT, 180, 180, 180);
        setColumnWidth(tblFileRecord, COL_PRIORITY, 80, 80, 80);
        setColumnWidth(tblFileRecord, COL_STATUS, 0, 0, 0);
        setColumnWidth(tblFileRecord, COL_PRIORITY_NO, 0, 0, 0);

    }

    public void bindTable() {
        final Sensors sensor = new SensorsDao().getSensorBySic((int) cmbSensors.getSelectedItem());
        final String from = ((JTextField) txtFromDate.getDateEditor().getUiComponent()).getText();
        final String to = ((JTextField) txtToDate.getDateEditor().getUiComponent()).getText();
        // validate form
        if (from.equals("")) {
            JOptionPane.showMessageDialog(this.rootPane, "Please enter validated time.", "Notify", JOptionPane.INFORMATION_MESSAGE);
            if (from.equals("")) {
                txtFromDate.grabFocus();
            }
            if (to.equals("")) {
                txtToDate.grabFocus();
            }
        } else {
            try {
                tableModel.setNumRows(0);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date date = sdf.parse(to);
                // Tang ToDate thêm 1 ngày    
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                calendar.add(Calendar.DATE, +1);
                String toPlus = sdf.format(calendar.getTime());

                List<SensorLogs> logs = new SensorLogsDao().getByDateAndSensor(from, toPlus, sensor);
                /*
                if (logs == null) {
                    JOptionPane.showMessageDialog(this.rootPane, "Empty log.", "Notify", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                 */
                if (logs != null && logs.size() > 0) {
                    int index = 1;
                    for (SensorLogs log : logs) {
                        tableModel.addRow(new Object[]{
                            log.getId(),
                            index,
                            log.getSensor().getSic(),
                            log.getCreatedDate(),
                            log.getWarningContent(),
                            "", // priority color
                            log.getStatus(),
                            log.getPriority()
                        });
                        switch (log.getPriority()) {
                            case 1:// WARNING
                                tableModel.setCellColor(index - 1, 5, AppContext.getWarnColor());
                                break;
                            case 2:// ERROR
                                tableModel.setCellColor(index - 1, 5, AppContext.getErrorColor());
                                break;
                            default:// 0: NORMAL

                                break;
                        }

                        index++;
                    }
                }
            } catch (ParseException ex) {
                ExceptionHandler.handle(ex);
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

        lblStation = new javax.swing.JLabel();
        cmbStations = new javax.swing.JComboBox<>();
        lblSensor = new javax.swing.JLabel();
        cmbSensors = new javax.swing.JComboBox<>();
        lblFromDate = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblFileRecord = new javax.swing.JTable();
        btnClose = new javax.swing.JButton();
        txtFromDate = new com.toedter.calendar.JDateChooser();
        btnSearch = new javax.swing.JButton();
        lblToDate = new javax.swing.JLabel();
        txtToDate = new com.toedter.calendar.JDateChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Sensor Activities Log - ADSB Administrator Terminal 1.0.0");
        setResizable(false);

        lblStation.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblStation.setText("Station");

        cmbStations.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbStationsActionPerformed(evt);
            }
        });

        lblSensor.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblSensor.setText("Sensor");

        lblFromDate.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblFromDate.setText("From Date");

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

        btnSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/search-16.png"))); // NOI18N
        btnSearch.setText("Search");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        lblToDate.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblToDate.setText("To Date");

        txtToDate.setDateFormatString("yyyy-MM-dd");
        txtToDate.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                txtToDatePropertyChange(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnClose))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(lblSensor, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cmbSensors, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(lblStation, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cmbStations, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblToDate, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(lblFromDate, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtToDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtFromDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(177, 177, 177)
                        .addComponent(btnSearch)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblStation, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cmbStations, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblFromDate, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnSearch)
                    .addComponent(txtFromDate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblSensor, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cmbSensors, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblToDate, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtToDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnClose)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmbStationsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbStationsActionPerformed
        this.setSensor(new StationsDao().getStationByName((String) cmbStations.getSelectedItem()));
    }//GEN-LAST:event_cmbStationsActionPerformed

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCloseActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        this.bindTable();
    }//GEN-LAST:event_btnSearchActionPerformed

    private void txtFromDatePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txtFromDatePropertyChange
        String getDate = ((JTextField) txtFromDate.getDateEditor().getUiComponent()).getText();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String today = sdf.format(date);
        if (getDate.equals("")) {
            ((JTextField) txtFromDate.getDateEditor().getUiComponent()).setText(today);
        }
    }//GEN-LAST:event_txtFromDatePropertyChange

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
            JOptionPane.showMessageDialog(this.rootPane, (String) table.getValueAt(row, 4), "Log Content", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            ExceptionHandler.handle(ex);
        }
    }//GEN-LAST:event_tblFileRecordMousePressed

    private void txtToDatePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txtToDatePropertyChange
        String getDate = ((JTextField) txtToDate.getDateEditor().getUiComponent()).getText();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String today = sdf.format(date);
        if (getDate.equals("")) {
            ((JTextField) txtToDate.getDateEditor().getUiComponent()).setText(today);
        }
    }//GEN-LAST:event_txtToDatePropertyChange


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnSearch;
    private javax.swing.JComboBox<Integer> cmbSensors;
    private javax.swing.JComboBox<String> cmbStations;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblFromDate;
    private javax.swing.JLabel lblSensor;
    private javax.swing.JLabel lblStation;
    private javax.swing.JLabel lblToDate;
    private javax.swing.JTable tblFileRecord;
    private com.toedter.calendar.JDateChooser txtFromDate;
    private com.toedter.calendar.JDateChooser txtToDate;
    // End of variables declaration//GEN-END:variables
}
