/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.administrator.views;

import com.attech.asd.administrator.AppContext;
import com.attech.asd.administrator.common.CustomDialog;
import com.attech.asd.administrator.common.Validator;
import com.attech.asd.database.SensorsDao;
import com.attech.asd.database.StationsDao;
import com.attech.asd.database.common.ActionRequest;
import com.attech.asd.database.common.Command;
import com.attech.asd.database.entities.SensorLogs;
import com.attech.asd.database.entities.Sensors;
import com.attech.asd.database.entities.Stations;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

/**
 *
 * @author AnhTH
 */
public class AddReceiver extends CustomDialog {

    private Sensors sensor;
    final String items1[] = {"UNICAST", "MULTICAST"};
    final private DefaultComboBoxModel receivingMode = new DefaultComboBoxModel(items1);

//    final String items2[] = {"ADS-B", "RADAR"};
    final String items2[] = {"ADS-B"}; 
    final private DefaultComboBoxModel sensorType = new DefaultComboBoxModel(items2);

    /**
     * Creates new form ReceiverDialog
     *
     * @param parent
     * @param modal
     * @param sic
     */
    public AddReceiver(java.awt.Frame parent, boolean modal, int sic) {
        super(parent, modal);
        initComponents();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        txtLongitude.setDocument(new JTextFieldLimit(9));
        txtLatitude.setDocument(new JTextFieldLimit(8));
        txtSic.setDocument(new JTextFieldLimit(4));
        txtBufferSize.setDocument(new JTextFieldLimit(4));
        txtPort.setDocument(new JTextFieldLimit(5));
        txtBindingAddress.setDocument(new JTextFieldLimit(15));
        txtMulticastAddress.setDocument(new JTextFieldLimit(15));

        cbReceivingMode.removeAllItems();
        cbReceivingMode.setModel(receivingMode);

        cbSensorType.removeAllItems();
        cbSensorType.setModel(sensorType);

        // list Station
        List<Stations> lstStations = new StationsDao().listStations();
        for (Stations station : lstStations) {
            cmbStations.addItem(station.getStationName());
        }
        this.sensor = null;
        binding(sic);
    }

    private void binding(int sic) {
        if (sic > 0) {
            this.sensor = new SensorsDao().getSensorBySic(sic);
            this.txtSic.setText(String.format("%s", sensor.getSic()));
            this.cbReceivingMode.setSelectedIndex(receivingMode.getIndexOf(sensor.getReceivingMode()));
            this.txtBindingAddress.setText(String.format("%s", sensor.getReceivingBindIp()));
            this.txtBufferSize.setText(String.format("%s", sensor.getBufferSize()));
            this.txtMulticastAddress.setText(String.format("%s", sensor.getReceivingMulticastAddress()));
            this.txtPort.setText(String.format("%s", sensor.getReceivingPort()));
            this.txtLatitude.setText(String.format("%s", sensor.getLatitude()));
            this.txtLongitude.setText(String.format("%s", sensor.getLongitude()));
            this.cbSensorType.setSelectedIndex((sensor.getSensorMode() == 1) ? 0 : 1);
            this.cmbStations.setSelectedItem(sensor.getStation().getStationName());
            this.txtDescription.setText(sensor.getDescription());
            this.txtSic.setEnabled(false);
        } else {
            this.txtSic.setText(String.format(""));
            this.cmbStations.setSelectedIndex(0);
            this.cbReceivingMode.setSelectedIndex(0);
            this.txtBindingAddress.setText(String.format(AppContext.getServerIp()));
            this.txtBufferSize.setText(String.format("5000"));
            this.txtMulticastAddress.setText(String.format(""));
            this.txtPort.setText(String.format(""));
            this.txtLatitude.setText(String.format(""));
            this.txtLongitude.setText(String.format(""));
            this.cbSensorType.setSelectedIndex(0);
            this.txtDescription.setText("");
            this.txtSic.setEnabled(true);
        }
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

    private boolean validated() {
        if (!Validator.isInteger(String.format(txtSic.getText().trim()), true)) {
            txtSic.requestFocus();
            return false;
        }
        if (!Validator.isInteger(String.format(txtPort.getText().trim()), true)) {
            txtPort.requestFocus();
            return false;
        }
        
        if (Integer.parseInt(txtPort.getText().trim()) < 1000){
            txtPort.requestFocus();
            return false;
        }
        
        if (Integer.parseInt(txtPort.getText().trim()) > 65535){
            txtPort.requestFocus();
            return false;
        }
        
        if (!txtMulticastAddress.getText().trim().isEmpty())// MULTICAST
        {
            if (!Validator.isValidInet4Address(String.format(txtMulticastAddress.getText().trim()))) {
                txtMulticastAddress.requestFocus();
                return false;
            }
        }
        if (!Validator.isValidInet4Address(String.format(txtBindingAddress.getText().trim()))) {
            txtBindingAddress.requestFocus();
            return false;
        }
        if (!Validator.isInteger(String.format(txtBufferSize.getText().trim()), true)) {
            txtBufferSize.requestFocus();
            return false;
        }
        if (!Validator.isNumberic(String.format(txtLatitude.getText().trim()))) {
            txtLatitude.requestFocus();
            return false;
        }
        float lat = Float.parseFloat(txtLatitude.getText().trim());
        if (lat > 90f || lat < -90f) {
            txtLatitude.requestFocus();
            return false;
        }
        if (!Validator.isNumberic(String.format(txtLongitude.getText().trim()))) {
            txtLongitude.requestFocus();
            return false;
        }
        float lon = Float.parseFloat(txtLongitude.getText().trim());
        if (lon > 180f || lon < -180f) {
            txtLongitude.requestFocus();
            return false;
        }
        
        if (sensor == null) { // addnew
            //validate sic unique
            final Sensors s = new SensorsDao().getSensorBySic(Integer.parseInt(txtSic.getText().trim()));
            if (s != null && s.getSic() > 0) {
                JOptionPane.showMessageDialog(rootPane, String.format("SIC Number exist. Try another!"));
                txtSic.requestFocus();
                return false;
            }
        }
        return true;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel4 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        btnClose = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        cbSensorType = new javax.swing.JComboBox<>();
        jLabel18 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        cbReceivingMode = new javax.swing.JComboBox<>();
        txtSic = new javax.swing.JTextField();
        txtPort = new javax.swing.JTextField();
        txtMulticastAddress = new javax.swing.JTextField();
        txtBindingAddress = new javax.swing.JTextField();
        txtBufferSize = new javax.swing.JTextField();
        txtLatitude = new javax.swing.JTextField();
        txtLongitude = new javax.swing.JTextField();
        cmbStations = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        txtDescription = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Add/Edit Receiver -  ADSB Administrator Terminal 1.0.0");
        setResizable(false);

        jLabel4.setText("SIC");

        jLabel9.setText("Listening port");

        jLabel11.setText("Mode");

        jLabel13.setText("Multicast Address");

        jLabel16.setText("Binding Address");

        jLabel17.setText("Buffer size");

        jLabel21.setText("Sensors Type");

        btnClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/door_out.png"))); // NOI18N
        btnClose.setText("Close");
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/save.png"))); // NOI18N
        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        cbSensorType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ADS-B", "RADAR" }));

        jLabel18.setText("Longitude");

        jLabel20.setText("Latitude");

        cbReceivingMode.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "UNICAST", "MULTICAST" }));

        txtPort.setToolTipText("Must numberic and lower than 65536");

        txtBufferSize.setToolTipText("Default value is 5000");

        jLabel5.setText("Station");

        jLabel19.setText("Description");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnSave)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnClose))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbStations, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtSic, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtLatitude)
                                    .addComponent(txtLongitude, javax.swing.GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel21, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cbSensorType, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cbReceivingMode, 0, 184, Short.MAX_VALUE)
                                    .addComponent(txtPort)
                                    .addComponent(txtMulticastAddress)
                                    .addComponent(txtBindingAddress)
                                    .addComponent(txtBufferSize)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDescription)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(6, 6, 6))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbStations, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtSic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(cbReceivingMode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtMulticastAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(txtBindingAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(txtBufferSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(cbSensorType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(txtLatitude, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(txtLongitude, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(txtDescription, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnClose)
                    .addComponent(btnSave))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCloseActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        try {
        if (!validated()) {
            JOptionPane.showMessageDialog(rootPane, String.format("Check input value!"));
            return;
        }
        
        if (sensor == null) {
            sensor = new Sensors();
        }
        String old = sensor.toString();
        sensor.setSensorMode((cbSensorType.getSelectedIndex() == 0) ? (byte) 1 : 2);
        sensor.setSic(Integer.parseInt(txtSic.getText().trim()));
        sensor.setLatitude(Float.parseFloat(txtLatitude.getText().trim()));
        sensor.setLongitude(Float.parseFloat(txtLongitude.getText().trim()));
        final Stations station = new StationsDao().getStationByName(cmbStations.getSelectedItem().toString());
        sensor.setStation(station);
        sensor.setStatus((byte) 0);
        sensor.setDescription(txtDescription.getText().trim());
        sensor.setReceivingMode(cbReceivingMode.getSelectedItem().toString());
        sensor.setReceivingMulticastAddress(txtMulticastAddress.getText().trim());
        sensor.setReceivingPort(Integer.parseInt(txtPort.getText().trim()));
        sensor.setReceivingBindIp(txtBindingAddress.getText().trim());
        sensor.setBufferSize(Integer.parseInt(txtBufferSize.getText().trim()));
        
        sensor.save();
        AppContext.getInstance().reloadListReceiver = true;
        
        new SensorLogs(
                sensor, 
                String.format("(%s) Updating sensor: (%s)", AppContext.getInstance().getCommandSocket().getCurrentIp(), old), 
                0).save();
        JOptionPane.showMessageDialog(rootPane, "Successfully!");
        requestToServer(ActionRequest.RELOAD_RECEIVER, sensor.getId());
        this.dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage());
            new SensorLogs(
                sensor, 
                String.format("(%s) Updating fail: (%s)", AppContext.getInstance().getCommandSocket().getCurrentIp(), sensor), 
                0).save();
        } 
        
    }//GEN-LAST:event_btnSaveActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox<String> cbReceivingMode;
    private javax.swing.JComboBox<String> cbSensorType;
    private javax.swing.JComboBox<String> cmbStations;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTextField txtBindingAddress;
    private javax.swing.JTextField txtBufferSize;
    private javax.swing.JTextField txtDescription;
    private javax.swing.JTextField txtLatitude;
    private javax.swing.JTextField txtLongitude;
    private javax.swing.JTextField txtMulticastAddress;
    private javax.swing.JTextField txtPort;
    private javax.swing.JTextField txtSic;
    // End of variables declaration//GEN-END:variables

}
