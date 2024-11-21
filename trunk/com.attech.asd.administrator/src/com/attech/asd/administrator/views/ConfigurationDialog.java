/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.administrator.views;

import com.attech.asd.administrator.AppContext;
import com.attech.asd.administrator.common.CustomDialog;
import com.attech.asd.administrator.common.Validator;
import com.attech.asd.database.common.ActionRequest;
import com.attech.asd.database.common.Command;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author AnhTH
 */
public class ConfigurationDialog extends CustomDialog {

    public ConfigurationDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        txtServerAddress.setDocument(new JTextFieldLimit(15));
        txtPageSize.setDocument(new JTextFieldLimit(3));
        txtAmhsBindIp.setDocument(new JTextFieldLimit(15));
        txtAmhsPort.setDocument(new JTextFieldLimit(4));
        txtRefreshTime.setDocument(new JTextFieldLimit(5));
        txtServerPort.setDocument(new JTextFieldLimit(4));
        txtStorageWarning.setDocument(new JTextFieldLimit(3));
        txtStorageError.setDocument(new JTextFieldLimit(3));
        txtSnoozeTime.setDocument(new JTextFieldLimit(5));
        txtErrorTimeout.setDocument(new JTextFieldLimit(5));
        txtFusionTime.setDocument(new JTextFieldLimit(4));
        txtDataLimit.setDocument(new JTextFieldLimit(3));
        txtFplLimit.setDocument(new JTextFieldLimit(3));
        
        //remove Tab AMHS
        //jTabbedPane1.removeTabAt(2);
        
        binding();
        this.getRootPane().setDefaultButton(btnSave);
    }

    private void binding() {
        this.txtServerAddress.setText(String.format("%s", AppContext.getServerIp()));
        this.txtServerPort.setText(String.format("%s", AppContext.getServerPort()));
        this.txtRefreshTime.setText(String.format("%s", AppContext.getRefreshTime()));
        this.txtErrorTimeout.setText(String.format("%s", AppContext.getErrorTimeout()));
        this.txtSnoozeTime.setText(String.format("%s", AppContext.getSnoozeTime()));
        this.txtFusionTime.setText(String.format("%s", AppContext.getFusePeriodTime()));
        this.txtFusionCleanupTime.setText(String.format("%s", AppContext.getFusionCleanupPeriodTime()));
        this.txtPageSize.setText(String.format("%s", AppContext.getPageSize()));

        this.btnWarningColor.setBackground(AppContext.getWarnColor());
        this.btnWarningColor.setContentAreaFilled(false);
        this.btnWarningColor.setOpaque(true);
        
        this.btnNomalColor.setBackground(AppContext.getNormalColor());
        this.btnNomalColor.setContentAreaFilled(false);
        this.btnNomalColor.setOpaque(true);
                
        this.btnErrorColor.setBackground(AppContext.getErrorColor());
        this.btnErrorColor.setContentAreaFilled(false);
        this.btnErrorColor.setOpaque(true);
        
        
        this.cmbTheme.setSelectedItem(AppContext.getTheme());

        this.txtDataLocation.setText(String.format("%s", AppContext.getStorageLocation()));
        this.txtFusedDataLocation.setText(String.format("%s", AppContext.getFusedStorageLocation()));
        this.txtStorageWarning.setText(String.format("%s", AppContext.getStorageThresshold()));
        this.txtStorageError.setText(String.format("%s", AppContext.getStorageErrorThresshold()));
        this.cmbSplitMode.setSelectedItem(String.format("%s", AppContext.getSplitMode()));
        
        this.txtAmhsBindIp.setText(String.format("%s", AppContext.getAmhsBindIp()));
        this.txtAmhsPort.setText(String.format("%s", AppContext.getAmhsPort()));
        
        this.cmbSplitMode.setEnabled(false);
        
        this.txtDataLimit.setText(String.format("%s", AppContext.getDataLimit()));
        this.txtFplLimit.setText(String.format("%s", AppContext.getFlightPlanLimit()));
    }
    
    private boolean validateData() {
        if (!Validator.isValidInet4Address(String.format(txtServerAddress.getText()))){
            txtServerAddress.requestFocus();
            return false;
        }
        
        if (!Validator.isNumberic(String.format(txtServerPort.getText()))){
            txtServerPort.requestFocus();
            return false;
        }
        
        if (!Validator.isNumberic(String.format(txtRefreshTime.getText()))){
            txtRefreshTime.requestFocus();
            return false;
        }
        if (!Validator.isNumberic(String.format(txtErrorTimeout.getText()))){
            txtErrorTimeout.requestFocus();
            return false;
        }
        if (!Validator.isNumberic(String.format(txtSnoozeTime.getText()))){
            txtSnoozeTime.requestFocus();
            return false;
        }
        if (!Validator.isNumberic(String.format(txtFusionTime.getText()))){
            txtFusionTime.requestFocus();
            return false;
        }
        if (!Validator.isNumberic(String.format(txtFusionCleanupTime.getText()))){
            txtFusionCleanupTime.requestFocus();
            return false;
        }
        if (!Validator.isNumberic(String.format(txtPageSize.getText()))){
            txtPageSize.requestFocus();
            return false;
        }
        if (!Validator.isNumberic(String.format(txtStorageWarning.getText()))){
            txtStorageWarning.requestFocus();
            return false;
        }
        
        if (!Validator.isNumberic(String.format(txtStorageError.getText()))){
            txtStorageError.requestFocus();
            return false;
        }
        
        if (!Validator.isValidInet4Address(String.format(txtAmhsBindIp.getText()))){
            txtServerAddress.requestFocus();
            return false;
        }
        
        if (!Validator.isNumberic(String.format(txtAmhsPort.getText()))){
            txtServerPort.requestFocus();
            return false;
        }
        
        if (Integer.parseInt(txtAmhsPort.getText().trim()) > 65535){
            txtAmhsPort.requestFocus();
            return false;
        }
        
        if (!Validator.isNumberic(String.format(txtDataLimit.getText()))){
            txtDataLimit.requestFocus();
            return false;
        }
        
        if (Integer.parseInt(txtDataLimit.getText().trim()) < 10){
            txtDataLimit.requestFocus();
            return false;
        }
        
        if (!Validator.isNumberic(String.format(txtFplLimit.getText()))){
            txtFplLimit.requestFocus();
            return false;
        }
        
        if (Integer.parseInt(txtFplLimit.getText().trim()) < 10){
            txtFplLimit.requestFocus();
            return false;
        }
        
        return true;
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

        btnClose = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        pnGeneral = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtServerAddress = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtServerPort = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtRefreshTime = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtErrorTimeout = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtSnoozeTime = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtFusionTime = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtFusionCleanupTime = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtPageSize = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        btnNomalColor = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        btnWarningColor = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        btnErrorColor = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        cmbTheme = new javax.swing.JComboBox<>();
        pnRecorder = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        txtDataLocation = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtFusedDataLocation = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        cmbSplitMode = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        txtStorageWarning = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        txtDataLimit = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        txtStorageError = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txtAmhsBindIp = new javax.swing.JTextField();
        txtAmhsPort = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        txtFplLimit = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Configuration -  ADSB Administrator Terminal 1.0.0");
        setResizable(false);

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

        jLabel1.setText("Server Address (IP)");

        txtServerAddress.setToolTipText("This setting will not take effect until the server application is restart");

        jLabel2.setText("Port Socket");

        txtServerPort.setToolTipText("This setting will not take effect until the server application is restart");

        jLabel3.setText("Refresh Time (ms)");

        jLabel4.setText("Error Timeout (ms)");

        jLabel5.setText("SnoozeTime (ms)");

        jLabel6.setText("Fusion Period Time (ms)");

        jLabel7.setText("Fusion Cleanup Period Time (ms)");

        jLabel8.setText("Page Size");

        jLabel9.setText("Normal Color");

        btnNomalColor.setPreferredSize(new java.awt.Dimension(39, 32));
        btnNomalColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNomalColorActionPerformed(evt);
            }
        });

        jLabel10.setText("Warning Color");

        btnWarningColor.setPreferredSize(new java.awt.Dimension(39, 32));
        btnWarningColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnWarningColorActionPerformed(evt);
            }
        });

        jLabel11.setText("Error Color");

        btnErrorColor.setPreferredSize(new java.awt.Dimension(39, 32));
        btnErrorColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnErrorColorActionPerformed(evt);
            }
        });

        jLabel16.setText("Theme");

        cmbTheme.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        cmbTheme.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Metal", "Nimbus", "Windows", "Windows Classic" }));

        javax.swing.GroupLayout pnGeneralLayout = new javax.swing.GroupLayout(pnGeneral);
        pnGeneral.setLayout(pnGeneralLayout);
        pnGeneralLayout.setHorizontalGroup(
            pnGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnGeneralLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnGeneralLayout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtServerAddress))
                    .addGroup(pnGeneralLayout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtServerPort, javax.swing.GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnGeneralLayout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtRefreshTime, javax.swing.GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE))
                    .addGroup(pnGeneralLayout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtErrorTimeout, javax.swing.GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE))
                    .addGroup(pnGeneralLayout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSnoozeTime, javax.swing.GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnGeneralLayout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtFusionTime, javax.swing.GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE))
                    .addGroup(pnGeneralLayout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtFusionCleanupTime, javax.swing.GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE))
                    .addGroup(pnGeneralLayout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPageSize, javax.swing.GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE))
                    .addGroup(pnGeneralLayout.createSequentialGroup()
                        .addGroup(pnGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbTheme, 0, 295, Short.MAX_VALUE)
                            .addGroup(pnGeneralLayout.createSequentialGroup()
                                .addGroup(pnGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(btnWarningColor, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE)
                                        .addComponent(btnErrorColor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(btnNomalColor, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        pnGeneralLayout.setVerticalGroup(
            pnGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnGeneralLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnGeneralLayout.createSequentialGroup()
                        .addGroup(pnGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtServerAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtServerPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtRefreshTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtErrorTimeout, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSnoozeTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtFusionTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtFusionCleanupTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPageSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnNomalColor, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnWarningColor, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(pnGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnErrorColor, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbTheme, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("General", pnGeneral);

        jLabel12.setText("Data Location");

        txtDataLocation.setToolTipText("The path to store file record from sperated sensor. This setting will not take effect until the server application is restart");

        jLabel13.setText("Fused Data Location");

        txtFusedDataLocation.setToolTipText("The path to store fused file record. This setting will not take effect until the server application is restart");

        jLabel14.setText("Split Mode");

        cmbSplitMode.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        cmbSplitMode.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "BY_MINUTE", "BY_HOUR", "BY_DAY", "NO_SPLIT" }));
        cmbSplitMode.setToolTipText("This setting will not take effect until the server application is restart");
        cmbSplitMode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbSplitModeActionPerformed(evt);
            }
        });

        jLabel15.setText("Storage Warning");

        txtStorageWarning.setToolTipText("If free space in data directory smaller than this value, the alert will be set.");

        jLabel19.setText("Data Limit Threshold");

        txtDataLimit.setToolTipText("By DAY and must be greater than 10");

        jLabel21.setText("Storage Error");

        txtStorageError.setToolTipText("If free space in data directory smaller than this value, the alert will be set.");

        javax.swing.GroupLayout pnRecorderLayout = new javax.swing.GroupLayout(pnRecorder);
        pnRecorder.setLayout(pnRecorderLayout);
        pnRecorderLayout.setHorizontalGroup(
            pnRecorderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnRecorderLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnRecorderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnRecorderLayout.createSequentialGroup()
                        .addGroup(pnRecorderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnRecorderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtFusedDataLocation)
                            .addComponent(txtDataLocation, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtStorageWarning)
                            .addComponent(cmbSplitMode, javax.swing.GroupLayout.Alignment.LEADING, 0, 353, Short.MAX_VALUE)
                            .addComponent(txtDataLimit)))
                    .addGroup(pnRecorderLayout.createSequentialGroup()
                        .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtStorageError, javax.swing.GroupLayout.PREFERRED_SIZE, 353, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        pnRecorderLayout.setVerticalGroup(
            pnRecorderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnRecorderLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnRecorderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDataLocation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnRecorderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFusedDataLocation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnRecorderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbSplitMode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnRecorderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtStorageWarning, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnRecorderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtStorageError, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnRecorderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtDataLimit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(211, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Recorder", pnRecorder);

        jLabel17.setText("AMHS Monitor Bind IP");

        jLabel18.setText("AMHS Monitor Port");

        txtAmhsBindIp.setToolTipText("This setting will not take effect until the server application is restart");

        txtAmhsPort.setToolTipText("This setting will not take effect until the server application is restart");

        jLabel20.setText("FlightPlan Limit Threshold");

        txtFplLimit.setToolTipText("By DAY and must be greater than 10");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtAmhsBindIp))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtAmhsPort, javax.swing.GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE)
                            .addComponent(txtFplLimit))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtAmhsBindIp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtAmhsPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFplLimit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(316, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("AMHS", jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jTabbedPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
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
        final int result = JOptionPane.showConfirmDialog(rootPane, "Are you sure ?");
        if (result != JOptionPane.YES_OPTION) {
            return;
        }
        try {
            if (!validateData()) {
                JOptionPane.showMessageDialog(rootPane, String.format("Check input value!"));
                return;
            }
            AppContext.getInstance().setProperty("ServerIP", txtServerAddress.getText().trim());
            AppContext.getInstance().setProperty("ServerPort", txtServerPort.getText().trim());
            AppContext.getInstance().setProperty("RefreshTime", txtRefreshTime.getText().trim());
            AppContext.getInstance().setProperty("ErrorTimeout", txtErrorTimeout.getText().trim());
            AppContext.getInstance().setProperty("SnoozeTime", txtSnoozeTime.getText().trim());
            AppContext.getInstance().setProperty("FusePeriodTime", txtFusionTime.getText().trim());
            AppContext.getInstance().setProperty("FusionCleanupPeriodTime", txtFusionCleanupTime.getText().trim());
            AppContext.getInstance().setProperty("PageSize", txtPageSize.getText().trim());
            AppContext.getInstance().setProperty("WarnColor", "#" + Integer.toHexString(btnWarningColor.getBackground().getRGB() & 0xffffff).toUpperCase());
            AppContext.getInstance().setProperty("NormalColor", "#" + Integer.toHexString(btnNomalColor.getBackground().getRGB() & 0xffffff).toUpperCase());
            AppContext.getInstance().setProperty("ErrorColor", "#" + Integer.toHexString(btnErrorColor.getBackground().getRGB() & 0xffffff).toUpperCase());
            AppContext.getInstance().setProperty("Theme", cmbTheme.getSelectedItem().toString());
            AppContext.getInstance().setProperty("Root", txtDataLocation.getText().trim());
            AppContext.getInstance().setProperty("RootFused", txtFusedDataLocation.getText().trim());
            AppContext.getInstance().setProperty("SplitMode", cmbSplitMode.getSelectedItem().toString());
            AppContext.getInstance().setProperty("DiskSpaceWarning", txtStorageWarning.getText().trim());
            AppContext.getInstance().setProperty("DiskSpaceError", txtStorageError.getText().trim());
            AppContext.getInstance().setProperty("AmhsMonitorBindIP1", txtAmhsBindIp.getText().trim());
            AppContext.getInstance().setProperty("AmhsMonitorPort1", txtAmhsPort.getText().trim());
            AppContext.getInstance().setProperty("DataLimit", txtDataLimit.getText().trim());
            AppContext.getInstance().setProperty("FlightPlanLimit", txtFplLimit.getText().trim());
            
            AppContext.getInstance().saveConfig();
            requestToServer(ActionRequest.RELOAD_CONFIGURATION, 0);
            JOptionPane.showMessageDialog(rootPane, String.format("Successfully! User need to restart software to confirm new configuration!"));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage());
        } finally {
            binding();            
            //this.dispose();
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnWarningColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnWarningColorActionPerformed
        Color newColor = JColorChooser.showDialog(null, "Choose a color", ((JButton) evt.getSource()).getBackground());
        if (newColor == null) {
            return;
        }
        ((JButton) evt.getSource()).setBackground(newColor);
        ((JButton) evt.getSource()).setContentAreaFilled(false);
        ((JButton) evt.getSource()).setOpaque(true);
    }//GEN-LAST:event_btnWarningColorActionPerformed

    private void btnNomalColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNomalColorActionPerformed
        Color newColor = JColorChooser.showDialog(null, "Choose a color", ((JButton) evt.getSource()).getBackground());
        if (newColor == null) {
            return;
        }
        ((JButton) evt.getSource()).setBackground(newColor);
        ((JButton) evt.getSource()).setContentAreaFilled(false);
        ((JButton) evt.getSource()).setOpaque(true);
    }//GEN-LAST:event_btnNomalColorActionPerformed

    private void btnErrorColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnErrorColorActionPerformed
        Color newColor = JColorChooser.showDialog(null, "Choose a color", ((JButton) evt.getSource()).getBackground());
        if (newColor == null) {
            return;
        }
        ((JButton) evt.getSource()).setBackground(newColor);
        ((JButton) evt.getSource()).setContentAreaFilled(false);
        ((JButton) evt.getSource()).setOpaque(true);
    }//GEN-LAST:event_btnErrorColorActionPerformed

    private void cmbSplitModeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbSplitModeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbSplitModeActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnErrorColor;
    private javax.swing.JButton btnNomalColor;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnWarningColor;
    private javax.swing.JComboBox<String> cmbSplitMode;
    private javax.swing.JComboBox<String> cmbTheme;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JPanel pnGeneral;
    private javax.swing.JPanel pnRecorder;
    private javax.swing.JTextField txtAmhsBindIp;
    private javax.swing.JTextField txtAmhsPort;
    private javax.swing.JTextField txtDataLimit;
    private javax.swing.JTextField txtDataLocation;
    private javax.swing.JTextField txtErrorTimeout;
    private javax.swing.JTextField txtFplLimit;
    private javax.swing.JTextField txtFusedDataLocation;
    private javax.swing.JTextField txtFusionCleanupTime;
    private javax.swing.JTextField txtFusionTime;
    private javax.swing.JTextField txtPageSize;
    private javax.swing.JTextField txtRefreshTime;
    private javax.swing.JTextField txtServerAddress;
    private javax.swing.JTextField txtServerPort;
    private javax.swing.JTextField txtSnoozeTime;
    private javax.swing.JTextField txtStorageError;
    private javax.swing.JTextField txtStorageWarning;
    // End of variables declaration//GEN-END:variables

    

}
