/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.administrator.common;

import java.awt.Color;
import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;

public class CellRender extends DefaultTableCellRenderer {
    Border b;
    
    public CellRender() {
        super();
        // b = BorderFactory.createCompoundBorder();
        // b = BorderFactory.createCompoundBorder(b, BorderFactory.createMatteBorder(1, 0, 0, 0, Color.WHITE));
        // b = BorderFactory.createCompoundBorder(b, BorderFactory.createMatteBorder(0, 1, 0, 0, Color.WHITE));
        // b = BorderFactory.createCompoundBorder(b, BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE));
        // b = BorderFactory.createCompoundBorder(b, BorderFactory.createMatteBorder(0, 0, 0, 1, Color.WHITE));
        b = BorderFactory.createCompoundBorder(b, BorderFactory.createMatteBorder(1, 1, 1, 1, Color.WHITE));
    }
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        
        setEnabled(table == null || table.isEnabled()); 
        CustomTableModel model = (CustomTableModel) table.getModel();
        // int index = table.convertRowIndexToModel(row);
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        Color color = model.getCellColor(row, column);

        if (color != null) {
            setBackground(color);
            if (color == Color.WHITE) {
                c.setForeground(Color.BLACK);
            } else {
                c.setForeground(Color.WHITE);
            }
        } else if (isSelected) {
            setBackground(Color.LIGHT_GRAY);
            c.setForeground(Color.BLACK);
        } else {
            setBackground(Color.WHITE);
            c.setForeground(Color.BLACK);
        }
        JComponent component = (JComponent) c;
        component.setBorder(b);
        return c;
    }
}
