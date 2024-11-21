/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.server.playback2;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class AttechTableModel extends DefaultTableModel {

    private int rowNo = -1;
    private boolean isEditable = true;
    private Map<Integer, Color> list = new HashMap<>();
    private Map<String, Color> cells = new HashMap<>();
    private List<Class<?>> classes;
    
    public AttechTableModel(boolean isEditable) {
        this.isEditable = isEditable;
        this.classes = new ArrayList<>();
    }
    
    public void addRowNumber(String name) {
        this.addColumn(name);
        this.rowNo = this.getColumnCount() -1;
    }
    
    public void updateColor(JTable table) {
        // for (Integer key : list.keySet()) {
        //    System.out.println(key + " -> " + table.convertRowIndexToView(key));
        // }
        // System.out.println(table.convertRowIndexToModel(0));
        fireTableDataChanged();
    }
    
    @Override
    public boolean isCellEditable(int row, int column) {
        return isEditable;
    }
    
    @Override
    public Object getValueAt(int row, int col) {
        if (col == this.rowNo) {
            return new Integer(row + 1);
        } else {
            return super.getValueAt(row, col);
        }
    }
    
    @Override
    public Class getColumnClass(int col) {  
        if( classes == null || classes.isEmpty() || classes.size() < col) return String.class;
        return classes.get(col);
    }
    
    public void setRowColour(int row, Color c) {
        list.put(row, c);
        fireTableRowsUpdated(row, row);
    }
    
    public void remove(int i) {
        if (list.containsKey(i))  list.remove(i);
        fireTableRowsUpdated(i, i);
    }
    
    public void setCellColor(int row, int col, Color c) {
        cells.put(row + "_" + col, c);
        fireTableRowsUpdated(row, col);
    }
    
    public void removw(int row, int col) {
        cells.remove(row + "_" + col);
        fireTableRowsUpdated(row, col);
    }

    public Map<Integer, Color> getRows() {
        return this.list;
    }
    
    public Map<String, Color> getCells() {
        return this.cells;
    }
    
    public void addColumnType(String type) {
        try {
            this.classes.add(Class.forName(type));
        } catch (ClassNotFoundException ex) {
            this.classes.add(String.class);
        }
    }
}
