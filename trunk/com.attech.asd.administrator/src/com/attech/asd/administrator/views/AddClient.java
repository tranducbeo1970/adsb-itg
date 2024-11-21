/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.administrator.views;

import com.attech.asd.administrator.AppContext;
import com.attech.asd.administrator.common.CustomDialog;
import com.attech.asd.administrator.common.Validator;
import com.attech.asd.database.AdapterObject;
import com.attech.asd.database.SensorsDao;
import com.attech.asd.database.StationsDao;
import com.attech.asd.database.common.ActionRequest;
import com.attech.asd.database.common.Command;
import com.attech.asd.database.entities.Client;
import com.attech.asd.database.entities.Sensors;
import com.attech.asd.database.entities.Stations;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author AnhTH
 */
public class AddClient extends CustomDialog {

    private Client client;
    //private boolean running;

    /**
     * Creates new form ClientDialog
     *
     * @param parent
     * @param modal
     * @param idClient
     */
    public AddClient(java.awt.Frame parent, boolean modal, int idClient) {
        super(parent, modal);
        initComponents();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        txtPort.setDocument(new JTextFieldLimit(5));
        txtClientLongitude.setDocument(new JTextFieldLimit(9));
        txtClientLatitude.setDocument(new JTextFieldLimit(8));
        txtAddress.setDocument(new JTextFieldLimit(15));
        txtBindAddress.setDocument(new JTextFieldLimit(15));
        txtFlMax.setDocument(new JTextFieldLimit(6));
        txtFlMin.setDocument(new JTextFieldLimit(6));
        
        if (idClient > 0)
            this.client =  new AdapterObject().getClientById(idClient);
        
        bindStation();
        binding();
        
    }
    
    private void bindStation(){
        List<Stations> lstStations = new StationsDao().listStations();
        cmbStation.addItem("-- Select Station --");
        for (Stations station : lstStations) {
            cmbStation.addItem(station.getStationName());
        }
    }
    
    public void setSensor(Stations station) {
        cmbSensors.removeAllItems();
        if (cmbStation.getSelectedIndex() == 0 || station == null){
            cmbSensors.addItem(0);
            return;
        }
        
        List<Sensors> lstSensors = new SensorsDao().listByStation(station);
        for (Sensors sensor : lstSensors) {
            cmbSensors.addItem(sensor.getSic());
        }
    }


    private void binding() {
        txtFlMin.setText(String.format("%s", 0));
        txtFlMax.setText(String.format("%s", 0));
        if (client == null){
            updatestatus();
            return;
        }
        txtName.setText(client.getName());
        txtAddress.setText(client.getForwardAddress());
        txtPort.setText(Integer.toString(client.getForwardPort()));
        if (client.getForwardBindIp() != null && !client.getForwardBindIp().isEmpty()) {
            txtBindAddress.setText(client.getForwardBindIp());
        }
        txtClientLatitude.setText(client.getLatitude().toString());
        txtClientLongitude.setText(client.getLongitude().toString());
        txtDescription.setText(client.getDescription());
        cbReceivingMode.setSelectedItem(client.getForwardMode());
        
        txtFlMin.setText(String.format("%s", client.getHeightMin()));
        txtFlMax.setText(String.format("%s", client.getHeightMax()));
                
        chkIsForwarding.setSelected(client.isForwarding());
        if (!client.isForwarding()){
            cmbStation.setSelectedIndex(0);
            this.setSensor(null);
        } else {
            final Sensors s = new SensorsDao().getSensorBySic(client.getSicFwd());
            cmbStation.setSelectedItem(s.getStation().getStationName());
            setSensor(s.getStation());
            cmbSensors.setSelectedItem(client.getSicFwd());
        }
        
        updatestatus();
    }

    private void updatestatus() {
        //jTabbedPane1.setEnabledAt(1, !chkIsForwarding.isSelected());
        if (chkIsForwarding.isSelected()){
            txtFlMin.setText("0");
            txtFlMax.setText("0");
            txtFlMin.setEnabled(false);
            txtFlMax.setEnabled(false);
        }
        
        cmbStation.setEnabled(chkIsForwarding.isSelected());
        cmbSensors.setEnabled(chkIsForwarding.isSelected());
        
        chkIsForwarding.setText(chkIsForwarding.isSelected() ? "True" : "False");
        
    }

    private void requestToServer(ActionRequest cmd, Integer id) {
        final List<Object> objs = new ArrayList<>();
        Command command = new Command();
        command.setCmd(cmd);
        objs.add(id);
        command.setParams(objs);
        if (AppContext.getInstance().getCommandSocket() != null && AppContext.getInstance().getCommandSocket().isConnected())
            AppContext.getInstance().getCommandSocket().setCommand(command);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        chkIsForwarding = new javax.swing.JCheckBox();
        cmbStation = new javax.swing.JComboBox<>();
        cmbSensors = new javax.swing.JComboBox<>();
        txtName = new javax.swing.JTextField();
        txtAddress = new javax.swing.JTextField();
        txtPort = new javax.swing.JTextField();
        txtBindAddress = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        txtClientLatitude = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        txtClientLongitude = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        txtDescription = new javax.swing.JTextField();
        cbReceivingMode = new javax.swing.JComboBox<>();
        jPanel4 = new javax.swing.JPanel();
        txtFlMin = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        txtFlMax = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        btnCancel = new javax.swing.JButton();
        btnSaveForward = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Add/Edit Client - ADSB Administrator Terminal 1.0.0");

        jLabel5.setText("Name");

        jLabel8.setText("Address");

        jLabel9.setText("Port");

        jLabel10.setText("Bind Address");

        jLabel12.setText("Is Forwarding");

        jLabel21.setText("Station");

        jLabel22.setText("Sensor");

        chkIsForwarding.setText("True");
        chkIsForwarding.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkIsForwardingActionPerformed(evt);
            }
        });

        cmbStation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbStationActionPerformed(evt);
            }
        });

        txtPort.setToolTipText("Must numberic and lower than 65536");

        jLabel20.setText("Forward Mode");

        jLabel23.setText("Latitude");

        jLabel24.setText("Longitude");

        jLabel25.setText("Description");

        cbReceivingMode.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "UNICAST", "MULTICAST" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)
                            .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbSensors, 0, 271, Short.MAX_VALUE)
                            .addComponent(cmbStation, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(chkIsForwarding)
                                .addGap(0, 214, Short.MAX_VALUE))
                            .addComponent(txtName)
                            .addComponent(txtAddress, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPort))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBindAddress))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtClientLatitude))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtClientLongitude))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDescription))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbReceivingMode, 0, 275, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtBindAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbReceivingMode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtClientLatitude, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtClientLongitude, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDescription, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chkIsForwarding))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbStation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbSensors, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 9, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Information", jPanel1);

        jLabel19.setText("FlightLevel MAX");

        jLabel11.setText("FlightLevel MIN");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtFlMin, javax.swing.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)
                    .addComponent(txtFlMax))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFlMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFlMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(282, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Height Filter", jPanel4);

        btnCancel.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        btnCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/door_out.png"))); // NOI18N
        btnCancel.setText("Close");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        btnSaveForward.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        btnSaveForward.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/save.png"))); // NOI18N
        btnSaveForward.setText("Save");
        btnSaveForward.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveForwardActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSaveForward, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 403, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancel)
                    .addComponent(btnSaveForward))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnSaveForwardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveForwardActionPerformed
        final int result = JOptionPane.showConfirmDialog(rootPane, "Are you sure ?");
        if (result != JOptionPane.YES_OPTION) {
            return;
        }
        
        if (!validateInput()){
            JOptionPane.showMessageDialog(rootPane, "Input data not valid!", "Form validation", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        if (!validateFl()) {
            JOptionPane.showMessageDialog(rootPane, "Flightlevel Min/Max must be not empty and is numeric", "Form validation", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        final float min = Float.parseFloat(txtFlMin.getText().trim());
        final float max = Float.parseFloat(txtFlMax.getText().trim());
        if (min > max || min < 0 || max < 0) {
            JOptionPane.showMessageDialog(rootPane, "Flightlevel Min/Max must not a negative number and min <= max", "Form validation", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        try {
            if (client == null)
                client = new Client();
            client.setName(txtName.getText().trim());
            client.setForwardAddress(txtAddress.getText().trim());
            client.setForwardPort(Integer.parseInt(txtPort.getText().trim()));
            client.setForwardBindIp(txtBindAddress.getText().trim());
            client.setForwardMode(cbReceivingMode.getSelectedItem().toString());
            client.setForwardingMultiCastTTL(3);
            client.setLatitude(Double.parseDouble(txtClientLatitude.getText().trim()));
            client.setLongitude(Double.parseDouble(txtClientLongitude.getText().trim()));
            client.setBufferSize(5000);
            
            client.setDescription(txtDescription.getText().trim());
            
            client.setHeightMin(min);
            client.setHeightMax(max);
            
            client.setSicFwd(chkIsForwarding.isSelected() ? Integer.parseInt(cmbSensors.getSelectedItem().toString()) : 0);
            client.setForwarding(client.getSicFwd() > 0);
            client.setIdSensorFwd(chkIsForwarding.isSelected() ? new SensorsDao().getSensorBySic(Integer.parseInt(cmbSensors.getSelectedItem().toString())).getId() : 0);
            

// Dung sua ngay 18/11/2024 them truong du lieu status cua client 
            client.setStatus((byte)1);

// Dung             
            
            client.save();
            AppContext.getInstance().reloadListClient = true;
            
            JOptionPane.showMessageDialog(rootPane, String.format("Successfully!"));
            requestToServer(ActionRequest.RELOAD_CLIENT, client.getId()); //Sau khi save phai yeu cau server reload lai client
            this.dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage());
        } 
    }//GEN-LAST:event_btnSaveForwardActionPerformed

    private void cmbStationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbStationActionPerformed
        this.setSensor(new StationsDao().getStationByName((String) cmbStation.getSelectedItem()));
    }//GEN-LAST:event_cmbStationActionPerformed

    private void chkIsForwardingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkIsForwardingActionPerformed
        this.updatestatus();
    }//GEN-LAST:event_chkIsForwardingActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnSaveForward;
    private javax.swing.JComboBox<String> cbReceivingMode;
    private javax.swing.JCheckBox chkIsForwarding;
    private javax.swing.JComboBox<Integer> cmbSensors;
    private javax.swing.JComboBox<String> cmbStation;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField txtAddress;
    private javax.swing.JTextField txtBindAddress;
    private javax.swing.JTextField txtClientLatitude;
    private javax.swing.JTextField txtClientLongitude;
    private javax.swing.JTextField txtDescription;
    private javax.swing.JTextField txtFlMax;
    private javax.swing.JTextField txtFlMin;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtPort;
    // End of variables declaration//GEN-END:variables

    private boolean validateInput() {
        if (!Validator.isEmpty(txtName.getText().trim(), 4)) {
            this.txtName.requestFocus();
            return false;
        }
        if (!Validator.isValidInet4Address(txtAddress.getText().trim())) {
            this.txtAddress.requestFocus();
            return false;
        }
        if (!Validator.isNumberic(txtPort.getText().trim())) {
            this.txtPort.requestFocus();
            return false;
        }
        
        if (Integer.parseInt(txtPort.getText().trim()) > 65535){
            txtPort.requestFocus();
            return false;
        }
        
        if (Integer.parseInt(txtPort.getText().trim()) < 1000){
            txtPort.requestFocus();
            return false;
        }
        
        if (!Validator.isValidInet4Address(txtBindAddress.getText().trim())) {
            this.txtBindAddress.requestFocus();
            return false;
        }
        
        if (!Validator.isFloating(txtClientLatitude.getText().trim(), true)) {
            this.txtClientLatitude.requestFocus();
            return false;
        }
        if (!Validator.isFloating(txtClientLongitude.getText().trim(), true)) {
            this.txtClientLongitude.requestFocus();
            return false;
        }
        float lat = Float.parseFloat(txtClientLatitude.getText().trim());
        if (lat > 90f || lat < -90f) {
            txtClientLatitude.requestFocus();
            return false;
        }
        float lon = Float.parseFloat(txtClientLongitude.getText().trim());
        if (lon > 180f || lon < -180f) {
            txtClientLongitude.requestFocus();
            return false;
        }
        return true;
    }

    private boolean validateFl() {
        if (!Validator.isEmpty(txtFlMin.getText().trim(), 1)) {
            txtFlMin.requestFocus();
            return false;
        }
        if (!Validator.isEmpty(txtFlMax.getText().trim(), 1)) {
            txtFlMax.requestFocus();
            return false;
        }

        if (!Validator.isFloating(txtFlMin.getText().trim(), true)) {
            txtFlMin.requestFocus();
            return false;
        }
        if (!Validator.isFloating(txtFlMax.getText().trim(), true)) {
            txtFlMax.requestFocus();
            return false;
        }
        return true;
    }
}
