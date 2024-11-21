/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.administrator.views;

import com.attech.asd.administrator.AppContext;
import com.attech.asd.database.AdapterObject;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.List;
import com.attech.asd.database.Base;
import com.attech.asd.database.entities.Airports;
import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

/**
 *
 * @author AnhTH
 */
public class AirportManagement extends javax.swing.JFrame {

    private final static Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

    private int currentPage = 1;
    private final int pageSize = AppContext.getPageSize();
    private int totalPage;

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
            java.util.logging.Logger.getLogger(AirportManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        Base.configure("database.xml");
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new AirportManagement().setVisible(true);
        });
    }

    private void getAutoResizeTable(final JTable table) {

        table.getColumnModel().addColumnModelListener(new TableColumnModelListener() {
            @Override
            public void columnSelectionChanged(ListSelectionEvent lse) {
            }

            @Override
            public void columnAdded(TableColumnModelEvent tcme) {
            }

            @Override
            public void columnRemoved(TableColumnModelEvent tcme) {
            }

            @Override
            public void columnMoved(TableColumnModelEvent tcme) {
            }

            @Override
            public void columnMarginChanged(ChangeEvent ce) {
                TableColumn resizingColumn = table.getTableHeader().getResizingColumn();
                if (resizingColumn != null) {
                    resizingColumn.setPreferredWidth(resizingColumn.getWidth());
                }
                if (hasExcessWidth(table)) {
                    table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
                } else {
                    table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                }

            }

            protected boolean hasExcessWidth(JTable table) {
                return table.getPreferredSize().width < table.getParent().getWidth();
            }
        });

    }

    /**
     * Creates new form Aircraft
     */
    public AirportManagement() {
        initComponents();
        // SET SIZE FRAME
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        this.setResizable(false);
        this.getRootPane().setDefaultButton(btnSearch);
        bindTable();
        tblAirport.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table = (JTable) mouseEvent.getSource();
                Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    TableModel mt = tblAirport.getModel();
                    Airports obj = new Airports(mt.getValueAt(row, 2).toString(), mt.getValueAt(row, 3).toString(), mt.getValueAt(row, 4).toString(), mt.getValueAt(row, 5).toString(), Double.parseDouble(mt.getValueAt(row, 6).toString()), Double.parseDouble(mt.getValueAt(row, 7).toString()));
                    obj.setId(Integer.parseInt(mt.getValueAt(row, 0).toString()));
                    obj.setAddress(mt.getValueAt(row, 8).toString());
                    obj.setDescription(mt.getValueAt(row, 9).toString());
                    AirportEdit dialog = new AirportEdit(null, true);
                    dialog.setAirport(obj);
                    dialog.setVisible(true);
                    bindTable();
                }
            }
        });
    }

    private void bindTable() {
        AdapterObject dao = new AdapterObject();
        this.tblAirport.setGridColor(Color.gray);
        this.tblAirport.getTableHeader().setReorderingAllowed(false);
        this.tblAirport.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 13));
        tblAirport.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        DefaultTableModel tableModel = (DefaultTableModel) tblAirport.getModel();
        tableModel.setNumRows(0);

        totalPage = (dao.getAirportSize(txtIata.getText(), txtIcao.getText(), "") / pageSize) + 1;
        lblTotalPage.setText(Integer.toString(totalPage));
        currentPage = (currentPage > totalPage) ? totalPage : currentPage;
        int i = 0;
        txtCurrentPage.setText(Integer.toString(currentPage));
        List<Airports> list = dao.getPagingAirports(currentPage, pageSize, txtIata.getText(), txtIcao.getText(), "");
        if (list != null && list.size() > 0) {
            for (Airports obj : list) {
                i++;
                tableModel.addRow(new Object[]{
                    obj.getId(),
                    (currentPage - 1) * pageSize + i,
                    obj.getType(),
                    obj.getIata(),
                    obj.getIcao(),
                    obj.getName(),
                    obj.getLatitude(),
                    obj.getLongitude(),
                    obj.getAddress(),
                    obj.getDescription()
                });
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

        pnSearch = new javax.swing.JPanel();
        lblAddress = new javax.swing.JLabel();
        btnSearch = new javax.swing.JButton();
        txtIcao = new javax.swing.JTextField();
        txtIata = new javax.swing.JTextField();
        lblAddress1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblAirport = new javax.swing.JTable();
        pnBottom = new javax.swing.JPanel();
        btnPrevious = new javax.swing.JButton();
        txtCurrentPage = new javax.swing.JTextField();
        lblBlank = new javax.swing.JLabel();
        lblTotalPage = new javax.swing.JLabel();
        btnNext = new javax.swing.JButton();
        btnAddNew = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnClose = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Airports -  ADSB Administrator Terminal 1.0.0");
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.Y_AXIS));

        lblAddress.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblAddress.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/airplane-front-view.png"))); // NOI18N
        lblAddress.setText("IATA Code:");

        btnSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/search-16.png"))); // NOI18N
        btnSearch.setText("Search");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        txtIcao.setToolTipText("Enter operator for filter");
        txtIcao.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtIcaoKeyPressed(evt);
            }
        });

        txtIata.setToolTipText("Enter operator for filter");
        txtIata.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtIataKeyPressed(evt);
            }
        });

        lblAddress1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblAddress1.setText("ICAO Code:");

        javax.swing.GroupLayout pnSearchLayout = new javax.swing.GroupLayout(pnSearch);
        pnSearch.setLayout(pnSearchLayout);
        pnSearchLayout.setHorizontalGroup(
            pnSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnSearchLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(lblAddress)
                .addGap(199, 199, 199)
                .addComponent(lblAddress1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtIcao, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 344, Short.MAX_VALUE)
                .addComponent(btnSearch)
                .addContainerGap())
            .addGroup(pnSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnSearchLayout.createSequentialGroup()
                    .addGap(110, 110, 110)
                    .addComponent(txtIata, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(703, Short.MAX_VALUE)))
        );
        pnSearchLayout.setVerticalGroup(
            pnSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnSearchLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnSearchLayout.createSequentialGroup()
                        .addComponent(lblAddress)
                        .addGap(12, 12, 12))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnSearchLayout.createSequentialGroup()
                        .addGroup(pnSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtIcao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblAddress1)
                            .addComponent(btnSearch))
                        .addContainerGap())))
            .addGroup(pnSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnSearchLayout.createSequentialGroup()
                    .addContainerGap(9, Short.MAX_VALUE)
                    .addComponent(txtIata, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );

        getContentPane().add(pnSearch);

        tblAirport.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Id", "No", "TYPE", "IATA", "ICAO", "NAME", "LATITUDE", "LONGITUDE", "ADDRESS", "DESCRIPTION"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblAirport.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(tblAirport);
        if (tblAirport.getColumnModel().getColumnCount() > 0) {
            tblAirport.getColumnModel().getColumn(0).setMinWidth(0);
            tblAirport.getColumnModel().getColumn(0).setPreferredWidth(0);
            tblAirport.getColumnModel().getColumn(0).setMaxWidth(0);
            tblAirport.getColumnModel().getColumn(1).setMinWidth(50);
            tblAirport.getColumnModel().getColumn(1).setPreferredWidth(50);
            tblAirport.getColumnModel().getColumn(1).setMaxWidth(50);
            tblAirport.getColumnModel().getColumn(2).setMinWidth(100);
            tblAirport.getColumnModel().getColumn(2).setPreferredWidth(100);
            tblAirport.getColumnModel().getColumn(2).setMaxWidth(100);
            tblAirport.getColumnModel().getColumn(3).setMinWidth(100);
            tblAirport.getColumnModel().getColumn(3).setPreferredWidth(100);
            tblAirport.getColumnModel().getColumn(3).setMaxWidth(100);
            tblAirport.getColumnModel().getColumn(4).setMinWidth(100);
            tblAirport.getColumnModel().getColumn(4).setPreferredWidth(100);
            tblAirport.getColumnModel().getColumn(4).setMaxWidth(100);
            tblAirport.getColumnModel().getColumn(5).setMinWidth(200);
            tblAirport.getColumnModel().getColumn(5).setPreferredWidth(200);
            tblAirport.getColumnModel().getColumn(5).setMaxWidth(200);
            tblAirport.getColumnModel().getColumn(6).setMinWidth(100);
            tblAirport.getColumnModel().getColumn(6).setPreferredWidth(100);
            tblAirport.getColumnModel().getColumn(6).setMaxWidth(100);
            tblAirport.getColumnModel().getColumn(7).setMinWidth(100);
            tblAirport.getColumnModel().getColumn(7).setPreferredWidth(100);
            tblAirport.getColumnModel().getColumn(7).setMaxWidth(100);
        }

        getContentPane().add(jScrollPane1);

        btnPrevious.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/previous-16.png"))); // NOI18N
        btnPrevious.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPreviousActionPerformed(evt);
            }
        });

        txtCurrentPage.setText("1");
        txtCurrentPage.setToolTipText("Current page");
        txtCurrentPage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCurrentPageActionPerformed(evt);
            }
        });

        lblBlank.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblBlank.setText("/");

        lblTotalPage.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblTotalPage.setText("11");

        btnNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/next-16.png"))); // NOI18N
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        btnAddNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/add_1.png"))); // NOI18N
        btnAddNew.setText("Add New ");
        btnAddNew.setToolTipText("Add new an airport");
        btnAddNew.setFocusable(false);
        btnAddNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddNewActionPerformed(evt);
            }
        });

        btnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/edit_1.png"))); // NOI18N
        btnEdit.setText("Edit");
        btnEdit.setToolTipText("Edit seleted airport ");
        btnEdit.setFocusable(false);
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/delete1.png"))); // NOI18N
        btnDelete.setText("Delete");
        btnDelete.setToolTipText("Delete selected airport");
        btnDelete.setFocusable(false);
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/out_1.png"))); // NOI18N
        btnClose.setText("Close");
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnBottomLayout = new javax.swing.GroupLayout(pnBottom);
        pnBottom.setLayout(pnBottomLayout);
        pnBottomLayout.setHorizontalGroup(
            pnBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnBottomLayout.createSequentialGroup()
                .addContainerGap(615, Short.MAX_VALUE)
                .addComponent(btnAddNew)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEdit)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDelete)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnClose)
                .addContainerGap())
            .addGroup(pnBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnBottomLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(btnPrevious)
                    .addGap(12, 12, 12)
                    .addComponent(txtCurrentPage, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(6, 6, 6)
                    .addComponent(lblBlank, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(6, 6, 6)
                    .addComponent(lblTotalPage, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(6, 6, 6)
                    .addComponent(btnNext)
                    .addGap(0, 775, Short.MAX_VALUE)))
        );
        pnBottomLayout.setVerticalGroup(
            pnBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnBottomLayout.createSequentialGroup()
                .addGroup(pnBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnClose)
                    .addComponent(btnAddNew)
                    .addComponent(btnEdit)
                    .addComponent(btnDelete))
                .addGap(0, 6, Short.MAX_VALUE))
            .addGroup(pnBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnBottomLayout.createSequentialGroup()
                    .addGap(0, 3, Short.MAX_VALUE)
                    .addGroup(pnBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(btnPrevious)
                        .addGroup(pnBottomLayout.createSequentialGroup()
                            .addGap(1, 1, 1)
                            .addComponent(txtCurrentPage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(pnBottomLayout.createSequentialGroup()
                            .addGap(6, 6, 6)
                            .addComponent(lblBlank))
                        .addGroup(pnBottomLayout.createSequentialGroup()
                            .addGap(6, 6, 6)
                            .addComponent(lblTotalPage))
                        .addComponent(btnNext))
                    .addGap(0, 3, Short.MAX_VALUE)))
        );

        getContentPane().add(pnBottom);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        int viewRow = tblAirport.getSelectedRow();
        if (viewRow > -1) {
            int row = tblAirport.convertRowIndexToModel(viewRow);
            TableModel mt = tblAirport.getModel();
            int response = JOptionPane.showConfirmDialog(this, "Are you sure you want to delele this Airport ?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                new AdapterObject().removeAirport(Integer.parseInt(mt.getValueAt(row, 0).toString()));
                JOptionPane.showMessageDialog(this, "Airport is deleted!!");
                bindTable();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Must select an airport", "Info", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        int row = tblAirport.getSelectedRow();
        if (row > -1) {
            TableModel mt = tblAirport.getModel();
            Airports obj = new Airports(mt.getValueAt(row, 2).toString(), mt.getValueAt(row, 3).toString(), mt.getValueAt(row, 4).toString(), mt.getValueAt(row, 5).toString(), Double.parseDouble(mt.getValueAt(row, 6).toString()), Double.parseDouble(mt.getValueAt(row, 7).toString()));
            obj.setId(Integer.parseInt(mt.getValueAt(row, 0).toString()));
            obj.setAddress(mt.getValueAt(row, 8).toString());
            obj.setDescription(mt.getValueAt(row, 9).toString());
            AirportEdit dialog = new AirportEdit(null, true);
            dialog.setAirport(obj);
            dialog.setVisible(true);
            bindTable();
        } else {
            JOptionPane.showMessageDialog(this, "Must select an airport", "Info", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnAddNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddNewActionPerformed
        AirportEdit dialog = new AirportEdit(null, true);
        dialog.setVisible(true);
        bindTable();
    }//GEN-LAST:event_btnAddNewActionPerformed

    private void btnPreviousActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreviousActionPerformed
        if (currentPage > 1) {
            currentPage--;
            txtCurrentPage.setText(Integer.toString(currentPage));
            bindTable();
        }
    }//GEN-LAST:event_btnPreviousActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        if (currentPage < totalPage) {
            currentPage++;
            txtCurrentPage.setText(Integer.toString(currentPage));
            bindTable();
        }
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        bindTable();
    }//GEN-LAST:event_btnSearchActionPerformed

    private void txtCurrentPageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCurrentPageActionPerformed
        try {
            currentPage = Integer.parseInt(txtCurrentPage.getText().trim());
            bindTable();
        } catch (Exception ex) {
            txtCurrentPage.setText(Integer.toString(currentPage));
        }
    }//GEN-LAST:event_txtCurrentPageActionPerformed

    private void txtIataKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIataKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            bindTable();
        }
    }//GEN-LAST:event_txtIataKeyPressed

    private void txtIcaoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIcaoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            bindTable();
        }
    }//GEN-LAST:event_txtIcaoKeyPressed

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCloseActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddNew;
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrevious;
    private javax.swing.JButton btnSearch;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAddress;
    private javax.swing.JLabel lblAddress1;
    private javax.swing.JLabel lblBlank;
    private javax.swing.JLabel lblTotalPage;
    private javax.swing.JPanel pnBottom;
    private javax.swing.JPanel pnSearch;
    private javax.swing.JTable tblAirport;
    private javax.swing.JTextField txtCurrentPage;
    private javax.swing.JTextField txtIata;
    private javax.swing.JTextField txtIcao;
    // End of variables declaration//GEN-END:variables
}
