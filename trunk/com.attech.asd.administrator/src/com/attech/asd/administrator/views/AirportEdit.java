/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.administrator.views;

import com.attech.asd.administrator.common.CustomDialog;
import com.attech.asd.administrator.common.Validator;
import com.attech.asd.database.AdapterObject;
import com.attech.asd.database.entities.Airports;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

public class AirportEdit extends CustomDialog {

    private Airports airport;
    final String items[] = {"International", "Local", "Military"};
    final private DefaultComboBoxModel model = new DefaultComboBoxModel(items);

    public AirportEdit(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        txtLongitude.setDocument(new JTextFieldLimit(9));
        txtLatitude.setDocument(new JTextFieldLimit(8));
        txtIcao.setDocument(new JTextFieldLimit(6));
        txtIata.setDocument(new JTextFieldLimit(6));
        
        cbType.removeAllItems();
        cbType.setModel(model);
    }

    private void bindData() {
        if (airport != null) {
            this.setTitle("Edit Airport");
            this.btnSave.setText("Update");
            
            this.txtIata.setText(airport.getIata());
            this.txtIcao.setText(airport.getIcao());
            this.txtName.setText(airport.getName());
            this.txtLongitude.setText(airport.getLongitude().toString());
            this.txtLatitude.setText(airport.getLatitude().toString());
            this.txtAddress.setText(airport.getAddress());
            this.txtDescription.setText(airport.getDescription());
            this.cbType.setSelectedIndex(model.getIndexOf(airport.getType()));
        } else {
            this.setTitle("Add New Airport");
            this.btnSave.setText("Save");
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

        btnSave = new javax.swing.JButton();
        btnClose = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cbType = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        txtIata = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtIcao = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtLatitude = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtLongitude = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtAddress = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtDescription = new javax.swing.JTextField();

        setTitle("Add New Aircraft -  ADSB Administrator Terminal 1.0.0");
        setResizable(false);

        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/export.png"))); // NOI18N
        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/out_1.png"))); // NOI18N
        btnClose.setText("Close");
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        jPanel1.setLayout(new java.awt.GridLayout(8, 2, 3, 3));

        jLabel1.setText("Airport type:");
        jPanel1.add(jLabel1);

        cbType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "International", "Local", "Military" }));
        jPanel1.add(cbType);

        jLabel2.setText("IATA Code:");
        jPanel1.add(jLabel2);
        jPanel1.add(txtIata);

        jLabel3.setText("ICAO Code:");
        jPanel1.add(jLabel3);
        jPanel1.add(txtIcao);

        jLabel4.setText("Airport Name:");
        jPanel1.add(jLabel4);
        jPanel1.add(txtName);

        jLabel5.setText("Latitude:");
        jPanel1.add(jLabel5);
        jPanel1.add(txtLatitude);

        jLabel6.setText("Longitude:");
        jPanel1.add(jLabel6);
        jPanel1.add(txtLongitude);

        jLabel7.setText("Airport Address:");
        jPanel1.add(jLabel7);
        jPanel1.add(txtAddress);

        jLabel8.setText("Description:");
        jPanel1.add(jLabel8);
        jPanel1.add(txtDescription);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(89, 89, 89)
                .addComponent(btnSave)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnClose)
                .addContainerGap(73, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(250, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSave)
                    .addComponent(btnClose))
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(41, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        if (validateForm()) {
            
            AdapterObject dao = new AdapterObject();
            if (this.airport == null) {
                this.airport = new Airports();
            }
            this.airport.setIata(txtIata.getText().trim());
            this.airport.setIcao(txtIcao.getText().trim());
            this.airport.setName(txtName.getText().trim());
            this.airport.setLatitude(Double.parseDouble(txtLatitude.getText().trim()));
            this.airport.setLongitude(Double.parseDouble(txtLongitude.getText().trim()));
            this.airport.setAddress(txtAddress.getText().trim());
            this.airport.setDescription(txtDescription.getText().trim());
            this.airport.setType((String) cbType.getSelectedItem());
            try {
                new AdapterObject().saveAirport(airport);
                JOptionPane.showMessageDialog(rootPane, "Update successfully.");
                setVisible(false);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(rootPane, "Update fail");
            }
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCloseActionPerformed

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
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AirportEdit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                AirportEdit dialog = new AirportEdit(new javax.swing.JFrame(), true);
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

    public boolean validateForm() {

        if (!Validator.isEmpty(txtIata.getText())) {
            JOptionPane.showMessageDialog(rootPane, String.format("You must fill in IATA code"), "Message", JOptionPane.INFORMATION_MESSAGE);
            txtIata.requestFocus();
            return false;
        }

        if (!Validator.isEmpty(txtIcao.getText())) {
            JOptionPane.showMessageDialog(rootPane, String.format("You must fill in ICAO code"), "Message", JOptionPane.INFORMATION_MESSAGE);
            txtIcao.requestFocus();
            return false;
        }

        if (!Validator.isEmpty(txtName.getText())) {
            JOptionPane.showMessageDialog(rootPane, String.format("You must fill in Name field"), "Message", JOptionPane.INFORMATION_MESSAGE);
            txtName.requestFocus();
            return false;
        }

        if (!Validator.isEmpty(txtLatitude.getText())) {
            JOptionPane.showMessageDialog(rootPane, String.format("You must fill in Latitude field"), "Message", JOptionPane.INFORMATION_MESSAGE);
            txtLatitude.requestFocus();
            return false;
        }

        if (!Validator.isEmpty(txtLongitude.getText())) {
            JOptionPane.showMessageDialog(rootPane, String.format("You must fill in Longitude field"), "Message", JOptionPane.INFORMATION_MESSAGE);
            txtLongitude.requestFocus();
            return false;
        }

        if (!Validator.isMaxLeng(txtIcao.getText(), 6)) {
            JOptionPane.showMessageDialog(this, "ICAO code isn't more than 6 digits", "Message", JOptionPane.INFORMATION_MESSAGE);
            txtIcao.requestFocus();
            return false;
        }

        if (!Validator.isMaxLeng(txtIata.getText(), 6)) {
            JOptionPane.showMessageDialog(this, "IATA code isn't more than 6 digits", "Message", JOptionPane.INFORMATION_MESSAGE);
            txtIata.requestFocus();
            return false;
        }

        float lat = Float.parseFloat(txtLatitude.getText().trim());
        if (lat > 90f || lat < -90f) {
            txtLatitude.requestFocus();
            return false;
        }
        float lon = Float.parseFloat(txtLongitude.getText().trim());
        if (lon > 180f || lon < -180f) {
            txtLongitude.requestFocus();
            return false;
        }

        if (this.airport == null) {
            //add new
            List<Airports> tmp;
            tmp = new AdapterObject().getPagingAirports(1, 100, txtIata.getText(), "", "");
            if (tmp != null && tmp.size() > 0) {
                JOptionPane.showMessageDialog(this, "IATA code existed.");
                txtIata.requestFocus();
                return false;
            }

            tmp = new AdapterObject().getPagingAirports(1, 100, "", txtIcao.getText(), "");
            if (tmp != null && tmp.size() > 0) {
                JOptionPane.showMessageDialog(this, "ICAO code existed.");
                txtIcao.requestFocus();
                return false;
            }

        } else {
            //edit
            List<Airports> tmp;
            tmp = new AdapterObject().getPagingAirports(1, 100, txtIata.getText(), "", "");
            if (tmp != null && tmp.size() > 0) {
                for (Airports obj : tmp) {
                    if (obj.getId().intValue() != airport.getId().intValue()) {
                        JOptionPane.showMessageDialog(this, "IATA code existed.");
                        txtIata.requestFocus();
                        return false;
                    }
                }
            }

            tmp = new AdapterObject().getPagingAirports(1, 100, "", txtIcao.getText(), "");
            if (tmp != null && tmp.size() > 0) {
                for (Airports obj : tmp) {
                    if (obj.getId().intValue() != airport.getId().intValue()) {
                        JOptionPane.showMessageDialog(this, "ICAO code existed.");
                        txtIcao.requestFocus();
                        return false;
                    }
                }
            }

        }
        return true;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox cbType;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField txtAddress;
    private javax.swing.JTextField txtDescription;
    private javax.swing.JTextField txtIata;
    private javax.swing.JTextField txtIcao;
    private javax.swing.JTextField txtLatitude;
    private javax.swing.JTextField txtLongitude;
    private javax.swing.JTextField txtName;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the aircraft
     */
    public Airports getAirport() {
        return airport;
    }

    /**
     * @param aircraft the aircraft to set
     */
    public void setAirport(Airports obj) {
        this.airport = obj;
        bindData();
    }
}
