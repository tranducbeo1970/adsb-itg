/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.playbackserver;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author andh
 */
public class FilesTableModel extends DefaultTableModel {

    private final List<PlaybackFile> addresses;
    private JTable table;

    public FilesTableModel() {
        addresses = new ArrayList<>();
        intialize();
    }

    public FilesTableModel(JTable table) {
        this();
        this.table = table;
        this.table.setModel(this);
        this.initColumnWidth(this.table);

    }

    public void intialize() {
        this.addColumn("#");
        this.addColumn("Name");
        this.addColumn("Status");
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    public void initColumnWidth(JTable table) {
        initColumnWidth(table, 0, 50, 50, 50);
        // initColumnWidth(table, 1, 300, 400, 350);
        initColumnWidth(table, 2, 200, 200, 200);
    }

    protected void initColumnWidth(JTable table, int colIndex, int minWidth, int maxWidth, int prefer) {
        table.getColumnModel().getColumn(colIndex).setMinWidth(minWidth);
        table.getColumnModel().getColumn(colIndex).setMaxWidth(maxWidth);
        table.getColumnModel().getColumn(colIndex).setPreferredWidth(prefer);
    }

    public void add(PlaybackFile address) {
        this.addresses.add(address);
        address.setNo(this.getRowCount() + 1);
        this.addRow(new Object[]{
            address.getNo(),
            address.getName(),
            address.getStatus()});
    }

    public PlaybackFile get(int index) {
        if (index >= this.addresses.size()) {
            return null;
        }
        return this.addresses.get(index);
    }
    
    public List<PlaybackFile> getPlaybackFiles() {
        return this.addresses;
    }

    public void clear() {
        int rowCount = getRowCount();
        for (int i = rowCount - 1; i >= 0; i--) {
            removeRow(i);
        }
    }

    public int getSelected() {
        if (this.table.getSelectedRow() < 0) {
            return 0;
        }

        return this.table.getSelectedRow();
    }

}
