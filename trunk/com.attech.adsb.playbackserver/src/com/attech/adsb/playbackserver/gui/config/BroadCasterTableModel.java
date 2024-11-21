/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.playbackserver.gui.config;

import com.attech.adsb.playbackserver.config.BroadCasterConfig;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author andh
 */
public final class BroadCasterTableModel extends DefaultTableModel{
    public BroadCasterTableModel(JTable table) {
        initializeColumn();
        attechToTable(table);
    }
    
    private void attechToTable(JTable table) {
        table.setModel(this);
        initColumnWidth(table);
    }
    
    private void initColumnWidth(JTable table, int colIndex, int minWidth, int maxWidth, int prefer) {
	table.getColumnModel().getColumn(colIndex).setMinWidth(minWidth);
	table.getColumnModel().getColumn(colIndex).setMaxWidth(maxWidth);
	table.getColumnModel().getColumn(colIndex).setPreferredWidth(prefer);
    }
    
    private void initializeColumn() {
        this.addColumn("IP");
        this.addColumn("Port");
        
    }
    
    private void initColumnWidth(JTable table) {
	// initColumnWidth(table, 0, 30, 30, 30);
	initColumnWidth(table, 0, 100, 120, 100);
	// initColumnWidth(table, 2, 150, 200, 150);
	// initColumnWidth(table, 4, 80, 120, 120);
	// initColumnWidth(table, 5, 100, 150, 120);
    }
    
    

    
    public synchronized void addRow(BroadCasterConfig broadcasterConfig) {
	this.addRow(new Object[]{ broadcasterConfig.getIp(), broadcasterConfig.getPort() });
    }
    
    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
    
    public void clear() {
        int rowCount = this.getRowCount();
        for (int i = rowCount - 1; i >= 0; i--) {
            this.removeRow(i);
        }
    }
    
}
