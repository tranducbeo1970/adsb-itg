/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.administrator.views;

import com.attech.asd.administrator.AppContext;
import com.attech.asd.administrator.common.CustomDialog;
import com.attech.asd.administrator.common.NormalTableModel;
import com.attech.asd.database.common.ActionRequest;
import com.attech.asd.database.common.Command;
import com.attech.asd.database.common.DBException;
import com.attech.asd.database.dao.MessageAccountDao;
import com.attech.asd.database.entities.MessageAccount;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 *
 * @author anhth
 */
public class MsgAccountManager extends CustomDialog {

    private final NormalTableModel tableModel;
    public final static String COL_ID = "ID";
    public final static String COL_NO = "#";
    public final static String COL_ACCOUNT = "ACCOUNT";
    public final static String COL_PASSWORD = "PASSWORD";
    public final static String COL_CONNECT = "CONNECTION STRING";
    public final static String COL_NAME = "NAME";
    public final static String COL_CHECKPOINT = "CHECK POINT";
    public final static String COL_ENABLE = "ENABLE";
    public final static String COL_UPDATE = "UPDATE INTERVAL";
    public final static String COL_DESCRIPTION = "DESCRIPTION";
    public final static String COL_CREATEDDATE = "CREATE AT";
    public final static String COL_UPDATEDDATE = "LAST UPDATE";


    /**
     * Creates new form PointsDialog
     *
     * @param parent
     * @param modal
     * @param points
     */
    public MsgAccountManager(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        this.tableModel = new NormalTableModel(tblAccount);
        
        this.tableModel.addColumn(COL_ID);
        this.tableModel.addColumn(COL_NO);
        this.tableModel.addColumn(COL_ACCOUNT);
        this.tableModel.addColumn(COL_PASSWORD);
        this.tableModel.addColumn(COL_CONNECT);
        this.tableModel.addColumn(COL_NAME);
        this.tableModel.addColumn(COL_CHECKPOINT);
        this.tableModel.addColumn(COL_ENABLE);
        this.tableModel.addColumn(COL_UPDATE);
        this.tableModel.addColumn(COL_DESCRIPTION);
        this.tableModel.addColumn(COL_CREATEDDATE);
        this.tableModel.addColumn(COL_UPDATEDDATE);

        
        setColumnWidth(this.tblAccount, COL_ID, 0, 0, 0);
        setColumnWidth(this.tblAccount, COL_NO, 30, 30, 30);
        setColumnWidth(this.tblAccount, COL_ACCOUNT, 150, 150, 150);
        setColumnWidth(this.tblAccount, COL_PASSWORD, 70, 70, 70);
        //setColumnWidth(this.tblAccount, COL_CONNECT, 0, 0, 0);
        setColumnWidth(this.tblAccount, COL_NAME, 70, 70, 70);
        setColumnWidth(this.tblAccount, COL_CHECKPOINT, 100, 100, 100);
        setColumnWidth(this.tblAccount, COL_ENABLE, 40, 40, 40);
        setColumnWidth(this.tblAccount, COL_UPDATE, 40, 40, 40);
        setColumnWidth(this.tblAccount, COL_DESCRIPTION, 70, 70, 70);
        setColumnWidth(this.tblAccount, COL_CREATEDDATE, 0, 0, 0);
        setColumnWidth(this.tblAccount, COL_UPDATEDDATE, 0, 0, 0);

        binding();
        btnAdd.setEnabled(AppContext.getInstance().connectToServer);
        btnRemove.setEnabled(AppContext.getInstance().connectToServer);
    }

    private void binding() {
        try{
        tableModel.setNumRows(0);
        List<MessageAccount> objs = new ArrayList<>();
        objs = new MessageAccountDao().getAll();
        if (objs != null && objs.size() > 0)
        for (int i = 0; i < objs.size(); i++) {
            MessageAccount obj = objs.get(i);
            this.tableModel.addRow(new Object[]{
                obj.getId(), 
                (i + 1), 
                obj.getAccount(), 
                obj.getPassword(), 
                obj.getConnectionString(),
                obj.getName(),
                obj.getCheckPoint(),
                obj.getEnable(),
                obj.getUpdateInterval(),
                obj.getDescription(),
                obj.getCreatedDate(),
                obj.getUpdatedDate()
            });
        }
        } catch (DBException e){
            
        }
    }

    private void setColumnWidth(JTable table, String name, int min, int max, int prefer) {
        table.getColumn(name).setMinWidth(min);
        table.getColumn(name).setMaxWidth(max);
        table.getColumn(name).setPreferredWidth(prefer);
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
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblAccount = new javax.swing.JTable();
        btnClose = new javax.swing.JButton();
        btnRemove = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Message Account -  ADSB Administrator Terminal 1.0.0");
        setResizable(false);

        tblAccount.setModel(new javax.swing.table.DefaultTableModel(
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
        tblAccount.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblAccountMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblAccount);

        btnClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/door_out.png"))); // NOI18N
        btnClose.setText("Close");
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        btnRemove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/delete1.png"))); // NOI18N
        btnRemove.setText("Remove");
        btnRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveActionPerformed(evt);
            }
        });

        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/add_16.png"))); // NOI18N
        btnAdd.setText("New");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1000, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRemove, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 298, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnClose)
                    .addComponent(btnRemove)
                    .addComponent(btnAdd))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnCloseActionPerformed

    private void btnRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveActionPerformed
        if (tblAccount.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(rootPane, "You must select one row", "Form validation", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        final int result = JOptionPane.showConfirmDialog(rootPane, "Are you sure ?");
        if (result != JOptionPane.YES_OPTION) {
            return;
        }
        int index = tblAccount.getSelectedRow();
        int id = (int) tblAccount.getValueAt(index, 0);
        MessageAccount account = new MessageAccountDao().get(id);
        new MessageAccountDao().remove(account);
        requestToServer(ActionRequest.RELOAD_MSG_ACC, account.getId());
        binding();
        //AppContext.getInstance().reloadListMessage = true;
    }//GEN-LAST:event_btnRemoveActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        AddMsgAccount dialog = new AddMsgAccount(null, true, 0);
        dialog.setVisible(true);
        binding();
    }//GEN-LAST:event_btnAddActionPerformed

    private void tblAccountMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblAccountMousePressed
        if (evt.getButton() != 1) {
            return;
        }
        JTable table = (JTable) evt.getSource();
        Point p = evt.getPoint();
        int index = table.rowAtPoint(p);
        if (evt.getClickCount() != 2) {
            return;
        }
        int id = (int) table.getValueAt(index, 0);
        AddMsgAccount dialog = new AddMsgAccount(null, true, id);
        dialog.setVisible(true);
        binding();
    }//GEN-LAST:event_tblAccountMousePressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnRemove;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblAccount;
    // End of variables declaration//GEN-END:variables
}
