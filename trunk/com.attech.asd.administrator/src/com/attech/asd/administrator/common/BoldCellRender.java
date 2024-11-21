/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.administrator.common;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;

public class BoldCellRender extends DefaultTableCellRenderer {
    Border b;
    
    public BoldCellRender() {
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
        BoldTextTableModel model = (BoldTextTableModel) table.getModel();
        int index = table.convertRowIndexToModel(row);
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (model.contain(index)) {
            c.setFont(c.getFont().deriveFont(Font.BOLD));
        } else {
            c.setFont(c.getFont().deriveFont(Font.PLAIN));
        }

//        Color color = model.getCellColor(row, column);
//        if (model.getRows().containsKey(index)) {
//            Color tmpColor = model.getRows().get(index);
//            setBackground(tmpColor);
//            if (tmpColor == Color.RED) {
//                c.setForeground(Color.WHITE);
//            } else {
//                c.setForeground(color);
//            }
//        } else {
//            setBackground(null);
//        }
        
//        if (row % 2 == 0) {
//            // setBackground(Color.RED);
//            c.setBackground(Color.GRAY);
//        } else {
//            // setBackground(Color.WHITE);
//            c.setBackground(Color.WHITE);
//        }
        
        // c.setForeground(color);
        // c.setForeground(Color.WHITE);
        JComponent component = (JComponent) c;
        component.setBorder(b);
        return c;
    }
}
