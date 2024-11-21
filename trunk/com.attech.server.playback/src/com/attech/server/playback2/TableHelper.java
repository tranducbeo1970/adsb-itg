/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.server.playback2;

import java.awt.Color;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.RowSorterEvent;
import javax.swing.event.RowSorterListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class TableHelper<T extends Object> {
    private JTable table;
    private List<BindingObject> bindindingProperties;
    private List<T> dataObject;
    private DefaultTableModel tableModel;
    private List<TableColumn> tableColumns;
    private Class<?> cellRenderClass;
    
    public TableHelper(JTable table) {
        this(table, true);
    }
    
    public TableHelper(final JTable table, boolean isEditable) {
        this(table, isEditable, DefaultTableCellRenderer.class);
    }
    
    public TableHelper(final JTable table, boolean isEditable, Class<?> cellRenderClass) {
        this.cellRenderClass = cellRenderClass;
        this.table = table;
        this.bindindingProperties = new ArrayList<>();
        this.tableColumns = new ArrayList<>();
        this.dataObject = new ArrayList<>();
        
        this.tableModel = new AttechTableModel(isEditable);
        
        this.tableModel.addTableModelListener(new TableModelListener() {

            @Override
            public void tableChanged(TableModelEvent e) {
                try {
                    sync(e);
                } catch (SecurityException | NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                    Logger.getLogger(TableHelper.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        this.table.setModel(tableModel);
        
        if (this.table.getRowSorter() == null) return;
        this.table.getRowSorter().addRowSorterListener(new RowSorterListener() {
            @Override
            public void sorterChanged(RowSorterEvent e) {
                // ((AttechTableModel) tableModel).updateColor(table);
                table.updateUI();
            }
        });
    }
   
    private void sync(TableModelEvent e) throws SecurityException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        switch (e.getType()) {
            case -1:
                // Delete
                dataObject.remove(e.getFirstRow());
                break;
            case 0:
                // Update
                if (e.getColumn() < 0 || e.getFirstRow() < 0) break;
                    
                BindingObject object = this.bindindingProperties.get(e.getColumn());
                if (object == null || object.getSetMethod() == null || object.getSetMethod().isEmpty() || object.getDataType() == null || object.getDataType().isEmpty())  break;
                
                T currentObject = this.dataObject.get(e.getFirstRow());
                Object editValue = this.table.getValueAt(e.getFirstRow(), e.getColumn());
                // System.out.println("VALUE: " +  this.tableModel.getValueAt(e.getFirstRow(), e.getColumn()));
                
                switch(object.getDataType()) {
                    case "Integer" :
                        int value = 0;
                        try {
                            value = Integer.parseInt(editValue.toString());
                        }
                        catch (Exception ex) {
                            JOptionPane.showMessageDialog(table, "Cannot convert " + editValue + " to Integer", "Data error", JOptionPane.ERROR_MESSAGE);
                            this.table.setValueAt(0, e.getFirstRow(), e.getColumn());
                        }
                        
                        // Class[] methodParameters = new Class[]{Integer.TYPE};
                        Method m = currentObject.getClass().getDeclaredMethod(object.getSetMethod(), new Class[]{Integer.TYPE});
                        // Object [] param = new Object[] { value };
                        m.invoke(currentObject, new Object[] { value });
                        break;
                    case "Boolean":
                        boolean bValue = false;
                        try {
                            bValue = Boolean.parseBoolean(editValue.toString());
                        }
                        catch (Exception ex) {
                            JOptionPane.showMessageDialog(table, "Cannot convert " + editValue + " to Boolean", "Data error", JOptionPane.ERROR_MESSAGE);
                            this.table.setValueAt(0, e.getFirstRow(), e.getColumn());
                        }
                        
                        // Class[] methodParameters = new Class[]{Integer.TYPE};
                        Method b = currentObject.getClass().getDeclaredMethod(object.getSetMethod(), new Class[]{Boolean.TYPE});
                        // Object [] param = new Object[] { value };
                        b.invoke(currentObject, new Object[] { bValue });
                        break;
                    case "Double":
                        double dValue = 0d;
                        try {
                            dValue = Double.parseDouble(editValue.toString());
                        }
                        catch (Exception ex) {
                            JOptionPane.showMessageDialog(table, "Cannot convert " + editValue + " to Double", "Data error", JOptionPane.ERROR_MESSAGE);
                            this.table.setValueAt(0, e.getFirstRow(), e.getColumn());
                        }
                        
                        // Class[] methodParameters = new Class[]{Integer.TYPE};
                        Method d = currentObject.getClass().getDeclaredMethod(object.getSetMethod(), new Class[]{Double.TYPE});
                        // Object [] param = new Object[] { value };
                        d.invoke(currentObject, new Object[] { dValue });
                        break;
                }
                break;
            case 1:
                // Add
                break;
        }
    }
    
    public void addColumn(String name, Integer width, Integer maxWid, String field, String type) {
        this.addColumn(name, width, maxWid, field, type, null);
    }
    
    public void addColumn(String name, Integer width, Integer maxWid, String field, String type, Integer alignment) {
        try {
            // this.table.setAutoCreateColumnsFromModel(false);
            ((AttechTableModel) this.tableModel).addColumnType(type);
            TableColumn tableColumn = new TableColumn();
            tableColumn.setHeaderValue(name);

            DefaultTableCellRenderer cellRender = createCellRender(type, alignment);

            tableColumn.setCellRenderer(cellRender);

            if (width != null) {
                tableColumn.setWidth(width.intValue());
                tableColumn.setPreferredWidth(width.intValue());
            }

            if (maxWid != null) tableColumn.setMaxWidth(maxWid.intValue());

            if (field.equalsIgnoreCase("#")) {
                ((AttechTableModel) this.tableModel).addRowNumber(name);
                cellRender.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
                this.bindindingProperties.add(null);
            } else {
                this.tableModel.addColumn(name);
                BindingObject binding = field.isEmpty() ? null : new BindingObject(field, type);
                this.bindindingProperties.add(binding);
            }

            this.tableColumns.add(tableColumn);
            for (int i = 0; i<this.tableColumns.size();i++) {
                TableColumn tc = this.table.getColumnModel().getColumn(i);
                tc.setMaxWidth(this.tableColumns.get(i).getMaxWidth());
                tc.setMinWidth(this.tableColumns.get(i).getMinWidth());
                tc.setWidth(this.tableColumns.get(i).getWidth());
                tc.setPreferredWidth(this.tableColumns.get(i).getWidth());
                tc.setCellRenderer(this.tableColumns.get(i).getCellRenderer());
                tc.setCellEditor(this.tableColumns.get(i).getCellEditor());
            }
            this.table.updateUI();
        } catch (Exception ex ) {
            ex.printStackTrace();
        }
        
    }
    
    private DefaultTableCellRenderer createCellRender(String type, Integer alignment) throws NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        
        // DefaultTableCellRenderer cellRender = new ColorfulCellRenderer();
        Constructor<?> ctor = this.cellRenderClass.getConstructor();
        DefaultTableCellRenderer cellRender = (DefaultTableCellRenderer) ctor.newInstance(new Object[]{});
        
        if (alignment != null) {
            cellRender.setHorizontalAlignment(alignment);
            return cellRender;
        }
        
        switch (type) {
            case "java.lang.String":
                cellRender.setHorizontalAlignment(DefaultTableCellRenderer.LEFT);
                break;
            case "java.lang.Boolean":
                cellRender.setHorizontalAlignment(DefaultTableCellRenderer.RIGHT);
                break;
            case "java.lang.Double":
                cellRender.setHorizontalAlignment(DefaultTableCellRenderer.RIGHT);
                // tableColumn.setCellEditor(table.getDefaultEditor(Double.class));
                break;
            case "java.lang.Float":
                cellRender.setHorizontalAlignment(DefaultTableCellRenderer.RIGHT);
                break;
            case "java.lang.Integer":
                cellRender.setHorizontalAlignment(DefaultTableCellRenderer.RIGHT);
                break;
        }
        return cellRender;
    }
    
    public void add(List<T> datasource) {
        if (datasource == null) return;
        for (T t : datasource) {
            this.add(t);
        }
    }
    
    public void add(T item) {
        int length = bindindingProperties.size();
        Class<?> cls = item.getClass();
        Object[] data = new Object[length];

        try {
            Method m;

            for (int i = 0; i < length; i++) {
                BindingObject methodName = bindindingProperties.get(i);
                if (methodName == null) continue;
                m = cls.getDeclaredMethod(methodName.getGetMethod());
                data[i] = m.invoke(item);
            }

            this.tableModel.addRow(data);
            this.dataObject.add(item);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException ex) {
            Logger.getLogger(TableHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void clear() {
        this.dataObject.clear();
        this.tableModel.getDataVector().removeAllElements();
        this.tableModel.fireTableDataChanged();
    }
    
    public T getSelectedObject() {
        if (this.table.getSelectedRow() < 0) return null;
        System.out.println("Selected row : " + this.table.getSelectedRow() + " --  "+ table.convertRowIndexToModel(this.table.getSelectedRow()));
        int index = table.convertRowIndexToModel(this.table.getSelectedRow());
        if (index >= this.dataObject.size()) return null;
        return this.dataObject.get(index);
    }
    
    public void deleteRow(int index) {
        this.tableModel.removeRow(index);
    }
    
    public void deleteSelectedRow() {
        this.tableModel.removeRow(this.table.getSelectedRow());
    }
    
    public List<T> getData() {
        return this.dataObject;
    }
    
    public void replace(int index1, int index2) {
        T temp = this.dataObject.get(index1);
        this.dataObject.set(index1, this.dataObject.get(index2));
        this.dataObject.set(index2, temp);
    }
    
    public void refreshBinding() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        int length = bindindingProperties.size();
        int index  = -1;
        Method m;
        for (T t : this.dataObject) {
            index = this.dataObject.indexOf(t);
            Class<?> cls = t.getClass();
            for (int i = 0; i < length; i++) {
                BindingObject methodName = bindindingProperties.get(i);
                if (methodName == null) continue;
                m = cls.getDeclaredMethod(methodName.getGetMethod());
                Object value = m.invoke(t);
                this.tableModel.setValueAt(value, index, i);
            }
        }
    }
    
    public boolean isFirst(T t) {
        return this.dataObject.indexOf(t) == 0;
    }
    
    public boolean isLast(T t) {
        return this.dataObject.indexOf(t) == (this.dataObject.size() - 1);
    }
    
    public void update(int rowIndex, int columnIndex, Object value) {
        if (value == null) return;
        this.table.setValueAt(value, rowIndex, columnIndex);
    }
    
    public void setRowBackgroundColor(int rowIndex, Color color) {
        AttechTableModel model = (AttechTableModel) this.tableModel;
        if (model == null) return;
        model.setRowColour(rowIndex, color);
    }
    
    public void setCellBackgroundColor(int row, int col, Color color) {
        AttechTableModel model = (AttechTableModel) this.tableModel;
        if (model == null) return;
        model.setCellColor(row, col, color);
    }
}
