/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.gui.playback;

import com.attech.adsb.client.common.ModelBase;
import java.io.File;
import javax.swing.JTable;

/**
 *
 * @author andh
 */
public class PlayListModel extends ModelBase{
    
    public PlayListModel(JTable table) {
	super(table);
    }
    
    @Override
    protected void initialize() {
	this.addColumn("Path");
	this.addColumn("");
	this.addColumn("Name");
	setColumnWidth(0, 0, 0, 0);
	setColumnWidth(1, 40, 40, 40);

    }
    
    @Override
    public boolean isCellEditable(int row, int column) {
	return false;
    }
    
    public void add(File file) {
        this.addRow(new Object[] { file.getAbsolutePath(), null, file.getName() });
    }
    

    
}
