/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.administrator.common;

import com.attech.asd.administrator.AppContext;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.DefaultRowSorter;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author AnhTH
 */
public abstract class CustomTableModel extends DefaultTableModel {
    protected JTable table;
    protected final List<String> columnIndex;
    protected final Map<String, Color> cellColors;
    protected final Map<String, Cell> alert; 
    protected final Timer alertTimer;
    protected boolean set;
    protected DefaultRowSorter sorter;
    
    public CustomTableModel() {
        super();
        this.columnIndex = new ArrayList<>();
        this.cellColors = new HashMap<>();
        this.alert = new HashMap<>();
        alertTimer = new Timer();
        alertTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                alert();
            }
        }, 0, 400);
    }
    
    public CustomTableModel(JTable table) {
        super();
        this.table = table;
        this.table.setModel(this);
        
        // sorter = ((DefaultRowSorter) table.getRowSorter());
        if (this.table.getTableHeader() != null) {
            this.table.getTableHeader().setReorderingAllowed(false);
            table.getTableHeader().setFont(new Font("Arial", Font.PLAIN, 14));
        }
        this.columnIndex = new ArrayList<>();
        this.cellColors = new HashMap<>();
        this.alert = new HashMap<>();
        alertTimer = new Timer();
        alertTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                alert();
            }
        }, 0, 400);
        this.initialize();
    }
    
    protected abstract void initialize();
    
    public int getColumnIndex(String name) {
        return columnIndex.indexOf(name);
    }
    
    @Override
    public void addColumn(Object name) {
        columnIndex.add((String) name);
        super.addColumn(name);
    }
    
    public void setValueAt(Object value, int rowIndex, String key) {
        int colIndex = this.getColumnIndex(key);
        super.setValueAt(value, rowIndex, colIndex);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
    
    public void setCellColor(int row, int col, Color color) {
        cellColors.put(row + "_" + col, color);
        fireTableRowsUpdated(row, col);
    }
    
    public Color getCellColor(int row, int col) {
        return this.cellColors.get(row + "_" + col);
    }
    
    public synchronized boolean setAlert(int row, int col) {
        Cell cell = new Cell(row, col);
        if (this.alert.containsKey(cell.getKey())) return false;
        this.alert.put(cell.getKey(), cell);
        return true;
    }
    
    public synchronized boolean isAlert(int row, int col) {
        String key = String.format("%s_%s", row, col);
        return this.alert.containsKey(key);
    }
    
    public synchronized boolean removeAlert(int row, int col) {
        String key = String.format("%s_%s", row, col);
        if (!this.alert.containsKey(key)) return false;
        this.alert.remove(key);
        return true;
    }
    
    protected synchronized void alert() {
        Color color = set ? Color.decode("#FFFFFF") : AppContext.getWarnColor();
        for (String s : alert.keySet()) {
            Cell cell = this.alert.get(s);
            cellColors.put(cell.getKey(), color);
            fireTableCellUpdated(cell.getRow(), cell.getCol());
        }
        set = !set;
    }
    
    protected void setColumnWidth(String name, int min, int max, int prefer) {
        table.getColumn(name).setMinWidth(min);
        table.getColumn(name).setMaxWidth(max);
        table.getColumn(name).setPreferredWidth(prefer);
    }
    
    public void hideHeader() {
        this.table.setTableHeader(null);

        JViewport jviewPort = (JViewport) this.table.getParent();
        if (jviewPort == null) {
            return;
        }

        JScrollPane jscrollPane = (JScrollPane) jviewPort.getParent();
        if (jscrollPane == null) {
            return;
        }
        jscrollPane.setColumnHeader(null);
        jscrollPane.setColumnHeaderView(null);
        jscrollPane.getColumnHeader().setVisible(false);
    }
    
    public void setSortKey(RowSorter.SortKey sortkey) {
        table.setAutoCreateRowSorter(true);
        final ArrayList list = new ArrayList();
        list.add(sortkey);
        sorter = ((DefaultRowSorter) table.getRowSorter());
        if (sorter == null) { return; }
        sorter.setSortKeys(list);
        sorter.sort();
    }
    
    
    public void setSortKey(List<RowSorter.SortKey> sortkey) {
        table.setAutoCreateRowSorter(true);
        sorter = ((DefaultRowSorter) table.getRowSorter());
        if (sorter == null) { return; }
        sorter.setSortKeys(sortkey);
        sorter.sort();
    }
    
    public void setSort(int column) {
        table.setAutoCreateRowSorter(true);
        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(table.getModel());
        table.setRowSorter(sorter);

        List<RowSorter.SortKey> sortKeys = new ArrayList<>(25);
        // sortKeys.add(new RowSorter.SortKey(4, SortOrder.ASCENDING));
        sortKeys.add(new RowSorter.SortKey(column, SortOrder.ASCENDING));
        sorter.setSortKeys(sortKeys);
    }

    public void setFilter(String filter) {
        sorter = ((DefaultRowSorter) table.getRowSorter());
        if (sorter == null) {
            return;
        }
//         sorter = ((DefaultRowSorter) table.getRowSorter());
        sorter.setRowFilter(RowFilter.regexFilter(filter));
        sorter.setSortsOnUpdates(true);
    }

    public void setFilter(RowFilter rowFilter) {
        sorter = ((DefaultRowSorter) table.getRowSorter());
        if (sorter == null) {
            return;
        }
        sorter.setRowFilter(rowFilter);
        sorter.setSortsOnUpdates(true);
    }
    
    public int getDisplayedRow() {
        return (this.sorter == null)
                ? this.getRowCount()
                : this.sorter.getViewRowCount();
    }

    public void refresh() {
        this.sorter.sort();
    }
    
    public void removeSort(int column){
        this.sorter.setSortable(column, false); 
    }
}
