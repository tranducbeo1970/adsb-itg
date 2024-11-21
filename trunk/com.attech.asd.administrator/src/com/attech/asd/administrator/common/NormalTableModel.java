/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.administrator.common;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JTable;

/**
 *
 * @author AnhTH
 */
public class NormalTableModel extends CustomTableModel{

    
    public NormalTableModel(JTable table){
        super(table);
    }

    @Override
    protected void initialize() {
        this.table.getTableHeader().setReorderingAllowed(false);
        this.table.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 13));
        this.table.setGridColor(Color.gray);
        this.table.setShowGrid(true);
        this.table.setRowHeight(30);
        
        this.table.setDefaultRenderer(String.class, new CellRender());
        this.table.setDefaultRenderer(Integer.class, new CellRender());
        this.table.setDefaultRenderer(Boolean.class, new CellRender());
        this.table.setDefaultRenderer(Object.class, new CellRender());
    }
}
