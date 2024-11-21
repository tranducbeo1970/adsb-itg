/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.administrator.common;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author andh
 */
public class BoldTextTableModel extends DefaultTableModel {
    private List<Integer> rows;
    
    public BoldTextTableModel() {
        this.rows = new ArrayList<>();
    }
    
    public void set(int row ) {
        if (rows.contains(row)) return;
        this.rows.add(row);
        fireTableRowsUpdated(row, 1);
        fireTableDataChanged();
    }
    
    public void remove(int row) {
        int index = rows.indexOf(row);
        if (index < 0) return;
        rows.remove(index);
        fireTableRowsUpdated(row, 1);
        fireTableDataChanged();
    }
    
    public boolean contain(int index) {
        return this.rows.contains(index);
    }
    
    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}
