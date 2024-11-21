/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.administrator.views;

import com.attech.asd.administrator.common.CustomDialog;
import com.attech.asd.administrator.def.Log;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
/**
 *
 * @author CanhND
 */
public class ApplicationLogs extends CustomDialog {
    
    ArrayList<Log> listLog;
    TableRowSorter<DefaultTableModel> sorter;
    DefaultTableModel tbModel, tbModelFile;
    int rowSelected;
    /**
     * Creates new form ApplicationLogs
     */
    public ApplicationLogs(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        rowSelected = -2;
        listFilesForFolder(new File("./log"));
    }
    
    private void setColor() {
        tblContent.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, hasFocus, hasFocus, row, column);
                String type = tblContent.getValueAt(row, 1).toString();
                Color color = Color.WHITE;
                if (type.equals("WARN")) {
                    color = Color.YELLOW;
                } else {
                    if (type.equals("INFO")) {
                        color = Color.WHITE;
                    } else {
                        color = Color.PINK;

                    }
                }
                label.setBackground(color);
                return label;
            }
        });
    }
    
    private void TableCreate(String type, ArrayList<Log> listLog) {
        tblContent.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 12));
        tbModel = (DefaultTableModel) tblContent.getModel();
        tbModel.setRowCount(0);
        if (type.equals("ALL")) {
            for (Log log : listLog) {
                tbModel.addRow(new Object[]{log.getTime(), log.getType(), log.getContent()});
            }
        } else {
            for (Log log : listLog) {
                if (log.getType().equals(type)) {
                    tbModel.addRow(new Object[]{log.getTime(), log.getType(), log.getContent()});
                }
            }
        }
        this.setColor();
        tblContent.setModel(tbModel);
        if (tblContent.getColumnModel().getColumnCount() > 0) {
            tblContent.getColumnModel().getColumn(0).setMinWidth(120);
            tblContent.getColumnModel().getColumn(0).setPreferredWidth(120);
            tblContent.getColumnModel().getColumn(0).setMaxWidth(120);
            
            tblContent.getColumnModel().getColumn(1).setMinWidth(50);
            tblContent.getColumnModel().getColumn(1).setPreferredWidth(50);
            tblContent.getColumnModel().getColumn(1).setMaxWidth(50);
        }
    }
    
    private void ReadFile(String path) {
        FileReader fileReader = null;
        listLog = new ArrayList<>();
        try {
            File file = new File(path);
            fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.contains("INFO") || line.contains("WARN")) {
                    String time = line.substring(11, 23);
                    String type = line.substring(24, 28);
                    String content = line.substring(31, line.length());
                    Log log = new Log(time, type, content);
                    listLog.add(log);
                } else {
                    if (line.contains("ERROR")) {
                        String time = line.substring(11, 23);
                        String type = line.substring(24, 29);
                        String content = line.substring(31, line.length());
                        Log log = new Log(time, type, content);
                        listLog.add(log);
                    }
                }

            }
            fileReader.close();
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "File not found", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                fileReader.close();
            } catch (IOException ex) {
                
            }
        }
    }

    private void listFilesForFolder(final File folder) {
        tblFiles.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 12));
        tblFiles.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tbModelFile = (DefaultTableModel) tblFiles.getModel();
        Object[] row = null;
        tbModelFile.setRowCount(0);
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else {
                String [] s = fileEntry.getName().split("\\.");
                String date = s[s.length-1];
                if (date.endsWith("log"))
                    date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                row = new Object[]{fileEntry.getName(), fileEntry.getAbsolutePath(), date};
                tbModelFile.addRow(row);
            }
        }
        tblFiles.setModel(tbModelFile);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        lblFilter = new javax.swing.JLabel();
        cmbFilter = new javax.swing.JComboBox<>();
        btnDelete = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblFiles = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblContent = new javax.swing.JTable();

        setTitle("Application Logs -  ADSB Administrator Terminal 1.0.0");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        lblFilter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/screen.png"))); // NOI18N
        lblFilter.setText("Fillter :");

        cmbFilter.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ALL", "INFO", "WARN", "ERROR" }));
        cmbFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbFilterActionPerformed(evt);
            }
        });

        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/delete1.png"))); // NOI18N
        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblFilter)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cmbFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnDelete)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDelete))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tblFiles.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "File Name", "File Path", "Date"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblFiles.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblFiles.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblFilesMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblFiles);
        if (tblFiles.getColumnModel().getColumnCount() > 0) {
            tblFiles.getColumnModel().getColumn(0).setMinWidth(0);
            tblFiles.getColumnModel().getColumn(0).setPreferredWidth(0);
            tblFiles.getColumnModel().getColumn(0).setMaxWidth(0);
            tblFiles.getColumnModel().getColumn(1).setMinWidth(0);
            tblFiles.getColumnModel().getColumn(1).setPreferredWidth(0);
            tblFiles.getColumnModel().getColumn(1).setMaxWidth(0);
        }

        tblContent.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Time", "Type", "Content"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblContent);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 510, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE)))
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Closes the dialog
     */
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        setVisible(false);
        dispose();
    }//GEN-LAST:event_closeDialog

    private void cmbFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbFilterActionPerformed
        TableCreate(cmbFilter.getSelectedItem().toString(), listLog);
    }//GEN-LAST:event_cmbFilterActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        int row = tblFiles.getSelectedRow();
        if (row > -1) {
            TableModel model = tblFiles.getModel();
            int response = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete file ?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                File file = new File(model.getValueAt(row, 1).toString());
                if (file.delete()) {
                    JOptionPane.showMessageDialog(this, "File deleted successfully", "Info",  JOptionPane.INFORMATION_MESSAGE);
                    final File folder = new File("./log");
                    listFilesForFolder(folder);
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to delete the file", "Error",  JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Select file to delete","Info", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void tblFilesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblFilesMouseClicked
        int row = tblFiles.getSelectedRow();
        if (row > -1) {
            if (row != rowSelected) {
                rowSelected = row;
                TableModel model = tblFiles.getModel();
                ReadFile(model.getValueAt(row, 1).toString());
                TableCreate(cmbFilter.getSelectedItem().toString(), listLog);
            }
        }
    }//GEN-LAST:event_tblFilesMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ApplicationLogs dialog = new ApplicationLogs(new java.awt.Frame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDelete;
    private javax.swing.JComboBox<String> cmbFilter;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblFilter;
    private javax.swing.JTable tblContent;
    private javax.swing.JTable tblFiles;
    // End of variables declaration//GEN-END:variables
}
