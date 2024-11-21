/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.administrator.views;

import com.attech.asd.administrator.AppContext;
import com.attech.asd.administrator.NewMonitor;
import com.attech.asd.administrator.common.CustomDialog;
import com.attech.asd.administrator.common.Validator;
import com.attech.asd.database.AdapterObject;
import com.attech.asd.database.AreasDao;
import com.attech.asd.database.SensorsDao;
import com.attech.asd.database.StationsDao;
import com.attech.asd.database.common.ActionRequest;
import com.attech.asd.database.common.Command;
import com.attech.asd.database.entities.Areas;
import com.attech.asd.database.entities.Circulars;
import com.attech.asd.database.entities.Client;
import com.attech.asd.database.entities.Sensors;
import com.attech.asd.database.entities.Stations;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author AnhTH
 */
public class ClientDialog extends CustomDialog {

    private Client client;
    private boolean running;
    
    

    /**
     * Creates new form ClientDialog
     *
     * @param parent
     * @param modal
     * @param broadcaster
     */
    public ClientDialog(java.awt.Frame parent, boolean modal, int idClient, boolean running) {
        super(parent, modal);
        initComponents();
//        try {
//            Thread.sleep(AppContext.getRefreshTime());
//        } catch (InterruptedException ex) {
//            ExceptionHandler.handle(ex);
//        }
        
        this.client = new AdapterObject().getClientById(idClient);
        this.running = running;
        
        bindStation();
        List<Areas> areas = new AreasDao().listAll();
        cbAreas.removeAllItems();
        for (Areas area : areas) {
            cbAreas.addItem(area.getName());
        }
        binding();
        this.setLocationRelativeTo(parent);
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

    private void bindAreaTable() {
        tblArea.setGridColor(Color.gray);
        if (client.getAreas() == null || client.getAreas().isEmpty()) {
            btnRemoveArea.setEnabled(false);
            //btnPoint.setEnabled(false);
            //return;
        }
        final DefaultTableModel model = (DefaultTableModel) tblArea.getModel();
        model.setNumRows(0);
        int i = 0;
        for (Areas area : client.getAreas()) {
            model.addRow(new Object[]{
                area.getId(),
                ++i,
                area.getName()
            });
        }
    }

    private void bindCircularTable() {
        tblCircular.setGridColor(Color.gray);
        if (client.getCirculars() == null || client.getCirculars().isEmpty()) {
            btnRemoveCircular.setEnabled(false);
            //return;
        }
        final DefaultTableModel model = (DefaultTableModel) tblCircular.getModel();
        model.setNumRows(0);
        int i = 0;
        for (Circulars obj : client.getCirculars()) {
            model.addRow(new Object[]{
                obj.getId(),
                ++i,
                obj.getRadius(),
                obj.getLatitude(),
                obj.getLongitude()
            });
        }
    }

    private void binding() {
        lblNo.setText(Integer.toString(client.getId()));
        lblName.setText(client.getName());
        lblAddress.setText(client.getForwardAddress());
        lblPort.setText(Integer.toString(client.getForwardPort()));
        if (client.getForwardBindIp() != null && !client.getForwardBindIp().isEmpty()) {
            lblBindingAddress.setText(client.getForwardBindIp());
        }
        txtFlMin.setText(String.format("%s", client.getHeightMin()));
        txtFlMax.setText(String.format("%s", client.getHeightMax()));
        
        bindAreaTable();
        bindCircularTable();
        
        chkIsForwarding.setSelected(client.isForwarding());
        if (!client.isForwarding()){
            cmbStation.setSelectedIndex(0);
            this.setSensor(null);
        } else {
            final Sensors s = new SensorsDao().getSensorBySic(client.getSicFwd());
            if(s != null){
                cmbStation.setSelectedItem(s.getStation().getStationName()); 
                setSensor(s.getStation());
            cmbSensors.setSelectedItem(client.getSicFwd());
            }     
        }
        
        updatestatus();
    }

    private void updatestatus() {
        if (running) {
            this.lblStatus.setText("Active");

        } else {
            this.lblStatus.setText("Inactive");
        }
        this.btnActive.setEnabled(!running);
        this.btnDeactive.setEnabled(running);
        this.btnEdit.setEnabled(!running);
        this.btnDelete.setEnabled(!running);

        this.txtFlMin.setEnabled(!running);
        this.txtFlMax.setEnabled(!running);
        this.btnSaveHeight.setEnabled(!running);
        this.btnAddArea.setEnabled(!running);
        this.btnRemoveArea.setEnabled(!running);
        this.btnAddCircular.setEnabled(!running);
        this.btnRemoveCircular.setEnabled(!running);
        
        jTabbedPane1.setEnabledAt(1, !chkIsForwarding.isSelected());
        jTabbedPane1.setEnabledAt(2, !chkIsForwarding.isSelected());
        jTabbedPane1.setEnabledAt(3, !chkIsForwarding.isSelected());
        
        cmbStation.setEnabled(!running && chkIsForwarding.isSelected());
        cmbSensors.setEnabled(!running && chkIsForwarding.isSelected());
        btnSaveForward.setEnabled(!running);
        chkIsForwarding.setEnabled(!running);
        
        chkIsForwarding.setText(chkIsForwarding.isSelected() ? "True" : "False");
        
        // NEU KHONG KET NOI VOI SERVER THI DISABLE HET
        if (!AppContext.getInstance().connectToServer){
            btnActive.setEnabled(false);
            btnDeactive.setEnabled(false);
            btnDelete.setEnabled(false);
            btnAddArea.setEnabled(false);
            btnAddCircular.setEnabled(false);
            btnRemoveArea.setEnabled(false);
            btnRemoveCircular.setEnabled(false);
            btnEdit.setEnabled(false);
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
        jLabel1 = new javax.swing.JLabel();
        lblNo = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblName = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lblStatus = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lblAddress = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lblPort = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        lblBindingAddress = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        chkIsForwarding = new javax.swing.JCheckBox();
        cmbStation = new javax.swing.JComboBox<>();
        btnSaveForward = new javax.swing.JButton();
        cmbSensors = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        cbAreas = new javax.swing.JComboBox<>();
        btnAddArea = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblArea = new javax.swing.JTable();
        jLabel14 = new javax.swing.JLabel();
        btnRemoveArea = new javax.swing.JButton();
        btnSelect1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        txtRadius = new javax.swing.JTextField();
        txtLatitude = new javax.swing.JTextField();
        txtLongitude = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblCircular = new javax.swing.JTable();
        btnAddCircular = new javax.swing.JButton();
        btnRemoveCircular = new javax.swing.JButton();
        btnSelect = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        txtFlMin = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        txtFlMax = new javax.swing.JTextField();
        btnSaveHeight = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        btnCancel = new javax.swing.JButton();
        btnDeactive = new javax.swing.JButton();
        btnActive = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Client Dialog -  ADSB Administrator Terminal 1.0.0");

        jLabel1.setText("#");

        lblNo.setText("10");
        lblNo.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lblNo.setMaximumSize(new java.awt.Dimension(50, 20));
        lblNo.setMinimumSize(new java.awt.Dimension(50, 20));
        lblNo.setPreferredSize(new java.awt.Dimension(16, 25));

        jLabel5.setText("Name");

        lblName.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lblName.setPreferredSize(new java.awt.Dimension(200, 25));

        jLabel7.setText("Status");

        lblStatus.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lblStatus.setPreferredSize(new java.awt.Dimension(200, 25));

        jLabel8.setText("Address");

        lblAddress.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lblAddress.setPreferredSize(new java.awt.Dimension(200, 25));

        jLabel9.setText("Port");

        lblPort.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lblPort.setPreferredSize(new java.awt.Dimension(200, 25));

        jLabel10.setText("Bind Address");

        lblBindingAddress.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lblBindingAddress.setPreferredSize(new java.awt.Dimension(200, 25));

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

        btnSaveForward.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/save.png"))); // NOI18N
        btnSaveForward.setText("Save");
        btnSaveForward.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveForwardActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(106, 106, 106)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
                            .addComponent(jLabel21, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
                            .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblAddress, javax.swing.GroupLayout.DEFAULT_SIZE, 289, Short.MAX_VALUE)
                            .addComponent(lblPort, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(lblBindingAddress, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(chkIsForwarding)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(cmbStation, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cmbSensors, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblNo, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnSaveForward)))
                .addContainerGap())
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(104, 104, 104)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGap(48, 48, 48)))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblName, javax.swing.GroupLayout.DEFAULT_SIZE, 289, Short.MAX_VALUE)
                        .addComponent(lblStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addContainerGap()))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblAddress, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblBindingAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSaveForward)
                .addContainerGap(9, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addContainerGap(240, Short.MAX_VALUE)))
        );

        jTabbedPane1.addTab("Information", jPanel1);

        jLabel13.setText("Area");

        cbAreas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnAddArea.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/add_16.png"))); // NOI18N
        btnAddArea.setText("Add");
        btnAddArea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddAreaActionPerformed(evt);
            }
        });

        tblArea.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Id", "NO", "AREA NAME"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblArea.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblAreaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblArea);
        if (tblArea.getColumnModel().getColumnCount() > 0) {
            tblArea.getColumnModel().getColumn(0).setMinWidth(0);
            tblArea.getColumnModel().getColumn(0).setPreferredWidth(0);
            tblArea.getColumnModel().getColumn(0).setMaxWidth(0);
            tblArea.getColumnModel().getColumn(1).setMinWidth(50);
            tblArea.getColumnModel().getColumn(1).setPreferredWidth(50);
            tblArea.getColumnModel().getColumn(1).setMaxWidth(50);
        }

        jLabel14.setText("Area Filtered");

        btnRemoveArea.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/delete1.png"))); // NOI18N
        btnRemoveArea.setText("Remove");
        btnRemoveArea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveAreaActionPerformed(evt);
            }
        });

        btnSelect1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/cursor.png"))); // NOI18N
        btnSelect1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelect1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 85, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(cbAreas, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(12, 12, 12)
                                .addComponent(btnAddArea)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnSelect1))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnRemoveArea, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbAreas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnAddArea))
                    .addComponent(btnSelect1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRemoveArea)
                .addContainerGap(97, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Area Filter", jPanel2);

        jLabel15.setText("Radius(NM)");

        jLabel16.setText("Latitude");

        jLabel17.setText("Longitude");

        jLabel18.setText("Circular Filtered");

        tblCircular.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Id", "NO", "RADIUS", "LAT", "LONG"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblCircular.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblCircularMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblCircular);
        if (tblCircular.getColumnModel().getColumnCount() > 0) {
            tblCircular.getColumnModel().getColumn(0).setMinWidth(0);
            tblCircular.getColumnModel().getColumn(0).setPreferredWidth(0);
            tblCircular.getColumnModel().getColumn(0).setMaxWidth(0);
            tblCircular.getColumnModel().getColumn(1).setMinWidth(30);
            tblCircular.getColumnModel().getColumn(1).setPreferredWidth(30);
            tblCircular.getColumnModel().getColumn(1).setMaxWidth(30);
            tblCircular.getColumnModel().getColumn(2).setMinWidth(50);
            tblCircular.getColumnModel().getColumn(2).setPreferredWidth(50);
            tblCircular.getColumnModel().getColumn(2).setMaxWidth(70);
        }

        btnAddCircular.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/add_16.png"))); // NOI18N
        btnAddCircular.setText("Add");
        btnAddCircular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddCircularActionPerformed(evt);
            }
        });

        btnRemoveCircular.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/delete1.png"))); // NOI18N
        btnRemoveCircular.setText("Remove");
        btnRemoveCircular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveCircularActionPerformed(evt);
            }
        });

        btnSelect.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/cursor.png"))); // NOI18N
        btnSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelectActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(53, 53, 53))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(21, 21, 21)))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE)
                            .addComponent(txtLatitude)
                            .addComponent(txtLongitude, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE)
                            .addComponent(txtRadius, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnAddCircular, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSelect)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnRemoveCircular, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtRadius, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtLatitude, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSelect))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtLongitude, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAddCircular))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRemoveCircular)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Circular Filter", jPanel3);

        jLabel19.setText("FlightLevel MAX");

        btnSaveHeight.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/add_16.png"))); // NOI18N
        btnSaveHeight.setText("Add");
        btnSaveHeight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveHeightActionPerformed(evt);
            }
        });

        jLabel11.setText("FlightLevel MIN");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtFlMin, javax.swing.GroupLayout.DEFAULT_SIZE, 323, Short.MAX_VALUE)
                            .addComponent(txtFlMax)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnSaveHeight)))
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
                .addGap(12, 12, 12)
                .addComponent(btnSaveHeight)
                .addContainerGap(187, Short.MAX_VALUE))
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

        btnDeactive.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        btnDeactive.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/bullet_lightning_black.png"))); // NOI18N
        btnDeactive.setText("Deactive");
        btnDeactive.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeactiveActionPerformed(evt);
            }
        });

        btnActive.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        btnActive.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/bullet_lightning.png"))); // NOI18N
        btnActive.setText("Active");
        btnActive.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActiveActionPerformed(evt);
            }
        });

        btnEdit.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        btnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/edit_1.png"))); // NOI18N
        btnEdit.setText("Edit");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnDelete.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/delete1.png"))); // NOI18N
        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnActive, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDeactive)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jTabbedPane1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancel)
                    .addComponent(btnDeactive)
                    .addComponent(btnActive)
                    .addComponent(btnEdit)
                    .addComponent(btnDelete))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnActiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActiveActionPerformed
        final int result = JOptionPane.showConfirmDialog(rootPane, "Are you sure ?");
        if (result != JOptionPane.YES_OPTION) {
            return;
        }
        try {
            requestToServer(ActionRequest.ACTIVE_CLIENT, client.getId());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage());
        } finally {
            this.updatestatus();
            this.dispose();
        }
    }//GEN-LAST:event_btnActiveActionPerformed

    private void btnDeactiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeactiveActionPerformed
        final int result = JOptionPane.showConfirmDialog(rootPane, "Are you sure ?");
        if (result != JOptionPane.YES_OPTION) {
            return;
        }
        try {
            requestToServer(ActionRequest.DEACTIVE_CLIENT, client.getId());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage());
        } finally {
            this.updatestatus();
            this.dispose();
        }
    }//GEN-LAST:event_btnDeactiveActionPerformed

    private void btnAddAreaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddAreaActionPerformed
        final int result = JOptionPane.showConfirmDialog(rootPane, "Are you sure ?");
        if (result != JOptionPane.YES_OPTION) {
            return;
        }
        try {
            final Areas area = new AreasDao().getAreaByName(cbAreas.getSelectedItem().toString());
            if (area != null) {
                final boolean check = client.addArea(area);
                if (check && client.save()) {
                    bindAreaTable();
                }
            }
            AppContext.getInstance().reloadListClient = true;
            
            JOptionPane.showMessageDialog(rootPane, String.format("Successfully!"));
            requestToServer(ActionRequest.RELOAD_CLIENT, client.getId());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage());
        } finally {
            this.updatestatus();
            //AppContext.getInstance().reloadListClient = true;
        }
    }//GEN-LAST:event_btnAddAreaActionPerformed

    private void btnRemoveAreaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveAreaActionPerformed
        if (tblArea.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(rootPane, "You must select one area", "Form validation", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        final int result = JOptionPane.showConfirmDialog(rootPane, "Are you sure ?");
        if (result != JOptionPane.YES_OPTION) {
            return;
        }

        final int index = tblArea.getSelectedRow();
        final DefaultTableModel model = (DefaultTableModel) tblArea.getModel();
        final int id = (int) model.getValueAt(index, 0);
        client.removeArea(id);
        if (client.save()) {
            bindAreaTable();
            AppContext.getInstance().reloadListClient = true;
            
            JOptionPane.showMessageDialog(rootPane, String.format("Successfully!"));
            requestToServer(ActionRequest.RELOAD_CLIENT, client.getId());
        }
    }//GEN-LAST:event_btnRemoveAreaActionPerformed

    private void btnRemoveCircularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveCircularActionPerformed
        if (tblCircular.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(rootPane, "You must select one circular", "Form validation", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        final int result = JOptionPane.showConfirmDialog(rootPane, "Are you sure ?");
        if (result != JOptionPane.YES_OPTION) {
            return;
        }
        final int index = tblCircular.getSelectedRow();
        final DefaultTableModel model = (DefaultTableModel) tblCircular.getModel();
        final int id = (int) model.getValueAt(index, 0);
        client.removeCircular(id);
        if (client.save()) {
            bindCircularTable();
            AppContext.getInstance().reloadListClient = true;
            
            JOptionPane.showMessageDialog(rootPane, String.format("Successfully!"));
            requestToServer(ActionRequest.RELOAD_CLIENT, client.getId());
        }
    }//GEN-LAST:event_btnRemoveCircularActionPerformed

    private void tblAreaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblAreaMouseClicked
        btnRemoveArea.setEnabled(!running && tblArea.getSelectedRow() >= 0);
        //btnPoint.setEnabled(tblArea.getSelectedRow() >= 0);
    }//GEN-LAST:event_tblAreaMouseClicked

    private void btnAddCircularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddCircularActionPerformed
        if (!validateCircular()) {
            JOptionPane.showMessageDialog(rootPane, "Radius/ Coordinates must be not empty and is numeric", "Form validation", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        final int result = JOptionPane.showConfirmDialog(rootPane, "Are you sure ?");
        if (result != JOptionPane.YES_OPTION) {
            return;
        }
        try {
            final Circulars area = new Circulars(
                    Float.parseFloat(txtRadius.getText().trim()),
                    Float.parseFloat(txtLatitude.getText().trim()),
                    Float.parseFloat(txtLongitude.getText().trim()));
            //area.save();
            final boolean check = client.addCircular(area);
            if (check && client.save()) {
                bindCircularTable();
                AppContext.getInstance().reloadListClient = true;

                JOptionPane.showMessageDialog(rootPane, String.format("Successfully!"));
                requestToServer(ActionRequest.RELOAD_CLIENT, client.getId());
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage());
        } finally {
            this.updatestatus();
        }
    }//GEN-LAST:event_btnAddCircularActionPerformed

    private void tblCircularMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCircularMouseClicked
        btnRemoveCircular.setEnabled(!running && tblCircular.getSelectedRow() >= 0);
    }//GEN-LAST:event_tblCircularMouseClicked

    private void btnSaveHeightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveHeightActionPerformed
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

        final int result = JOptionPane.showConfirmDialog(rootPane, "Are you sure ?");
        if (result != JOptionPane.YES_OPTION) {
            return;
        }
        try {
            client.setHeightMin(min);
            client.setHeightMax(max);
            client.save();
            binding();
            AppContext.getInstance().reloadListClient = true;
            
            JOptionPane.showMessageDialog(rootPane, String.format("Successfully!"));
            requestToServer(ActionRequest.RELOAD_CLIENT, client.getId());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage());
        } 
    }//GEN-LAST:event_btnSaveHeightActionPerformed

    private void btnSaveForwardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveForwardActionPerformed
        final int result = JOptionPane.showConfirmDialog(rootPane, "Are you sure ?");
        if (result != JOptionPane.YES_OPTION) {
            return;
        }
        try {
            client.setSicFwd(chkIsForwarding.isSelected() ? Integer.parseInt(cmbSensors.getSelectedItem().toString()) : 0);
            client.setForwarding(client.getSicFwd() > 0);
            client.setIdSensorFwd(chkIsForwarding.isSelected() 
                    ? new SensorsDao().getSensorBySic(Integer.parseInt(cmbSensors.getSelectedItem().toString())).getId() 
                    : 0);
            client.save();
            
            AppContext.getInstance().reloadListClient = true;
            
            JOptionPane.showMessageDialog(rootPane, String.format("Successfully!"));
            requestToServer(ActionRequest.RELOAD_CLIENT, client.getId());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage());
        } finally {
            binding();
            this.updatestatus();
        }
    }//GEN-LAST:event_btnSaveForwardActionPerformed

    private void chkIsForwardingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkIsForwardingActionPerformed
        this.updatestatus();
    }//GEN-LAST:event_chkIsForwardingActionPerformed

    private void cmbStationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbStationActionPerformed
        this.setSensor(new StationsDao().getStationByName((String) cmbStation.getSelectedItem()));
    }//GEN-LAST:event_cmbStationActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        this.dispose();
        AddClient dialog = new AddClient(null, true, client.getId());
        dialog.setVisible(true);
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        final int result = JOptionPane.showConfirmDialog(rootPane, "Are you sure ?");
        if (result != JOptionPane.YES_OPTION) {
            return;
        }
        requestToServer(ActionRequest.DELETE_CLIENT, client.getId());
        this.dispose();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnSelectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelectActionPerformed
        NewMonitor dialog = new NewMonitor(null, true);
        dialog.txtLatitude = txtLatitude;
        dialog.txtLongitude = txtLongitude;
        dialog.setVisible(true);
    }//GEN-LAST:event_btnSelectActionPerformed

    private void btnSelect1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelect1ActionPerformed
        NewMonitor dialog = new NewMonitor(null, true);
        dialog.txtLatitude = txtLatitude;
        dialog.txtLongitude = txtLongitude;
        dialog.setVisible(true);
    }//GEN-LAST:event_btnSelect1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActive;
    private javax.swing.JButton btnAddArea;
    private javax.swing.JButton btnAddCircular;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnDeactive;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnRemoveArea;
    private javax.swing.JButton btnRemoveCircular;
    private javax.swing.JButton btnSaveForward;
    private javax.swing.JButton btnSaveHeight;
    private javax.swing.JButton btnSelect;
    private javax.swing.JButton btnSelect1;
    private javax.swing.JComboBox<String> cbAreas;
    private javax.swing.JCheckBox chkIsForwarding;
    private javax.swing.JComboBox<Integer> cmbSensors;
    private javax.swing.JComboBox<String> cmbStation;
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
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblAddress;
    private javax.swing.JLabel lblBindingAddress;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblNo;
    private javax.swing.JLabel lblPort;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JTable tblArea;
    private javax.swing.JTable tblCircular;
    private javax.swing.JTextField txtFlMax;
    private javax.swing.JTextField txtFlMin;
    private javax.swing.JTextField txtLatitude;
    private javax.swing.JTextField txtLongitude;
    private javax.swing.JTextField txtRadius;
    // End of variables declaration//GEN-END:variables

    private boolean validateCircular() {
        if (!Validator.isEmpty(txtRadius.getText().trim(), 1)) {
            return false;
        }
        if (!Validator.isEmpty(txtLatitude.getText().trim(), 1)) {
            return false;
        }
        if (!Validator.isEmpty(txtLongitude.getText().trim(), 1)) {
            return false;
        }
        if (!Validator.isFloating(txtRadius.getText().trim(), true)) {
            return false;
        }
        if (!Validator.isFloating(txtLatitude.getText().trim(), true)) {
            return false;
        }
        if (!Validator.isFloating(txtLongitude.getText().trim(), true)) {
            return false;
        }
        return true;
    }

    private boolean validateFl() {
        if (!Validator.isEmpty(txtFlMin.getText().trim(), 1)) {
            return false;
        }
        if (!Validator.isEmpty(txtFlMax.getText().trim(), 1)) {
            return false;
        }

        if (!Validator.isFloating(txtFlMin.getText().trim(), true)) {
            return false;
        }
        if (!Validator.isFloating(txtFlMax.getText().trim(), true)) {
            return false;
        }
        return true;
    }
}
