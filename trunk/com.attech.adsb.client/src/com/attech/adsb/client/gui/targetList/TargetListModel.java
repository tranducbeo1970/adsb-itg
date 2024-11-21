/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.gui.targetList;

import com.attech.adsb.client.common.ModelBase;
import com.attech.adsb.client.common.Target;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.RowSorter.SortKey;
import javax.swing.SortOrder;

/**
 *
 * @author andh
 */
public class TargetListModel extends ModelBase {

    public TargetListModel(JTable table) {
	super(table);
    }

    @Override
    protected void initialize() {
        this.addColumn("DOF"); // 0
        this.addColumn("24BIT"); // 1
        this.addColumn("Callsign"); // 2
        this.addColumn("Reg"); // 3
        this.addColumn("Model"); // 44
        this.addColumn("Type"); //5
        this.addColumn("Operator"); //6
        this.addColumn("Dep");
        this.addColumn("Dest");
        this.addColumn("Route");
        this.addColumn("");

        setColumnWidth(0, 60, 80, 60);
        setColumnWidth(1, 60, 80, 60);
        setColumnWidth(2, 60, 80, 60);
        setColumnWidth(3, 60, 120, 80);
        setColumnWidth(4, 60, 80, 60);
        setColumnWidth(5, 60, 120, 120);
        setColumnWidth(6, 60, 100, 120);
        setColumnWidth(7, 60, 80, 60);
        setColumnWidth(8, 60, 80, 60);
        setColumnWidth(10, 0, 0, 0);

        // ANHTH removed
        // Cause: Wrong selected value
	//List<SortKey> sortkeys = new ArrayList<>();
	//sortkeys.add(new RowSorter.SortKey(3, SortOrder.DESCENDING));
	//this.setSortKey(sortkeys);

    }

    @Override
    public boolean isCellEditable(int row, int column) {
	return false;
    }

    public void update(List<Target> targets) {
	if (targets == null) {
	    return;
	}

	String address;
	Object value;
	boolean existed;       
        
        for (int rowIndex = this.getRowCount() - 1; rowIndex >= 0; rowIndex--) {
            value = this.getValueAt(rowIndex, 1);
            if (value == null) continue;
            address = (String) value;
            boolean isExisted = false;
            for (Target flightPlan : targets) {
                if (!flightPlan.getAddress().equalsIgnoreCase(address)) continue;
                isExisted = true;
                break;
            }
            
            if (!isExisted) {
                this.removeRow(rowIndex);
            }
	}

	for (Target flightPlan : targets) {
	    existed = false;
	    for (int rowIndex = this.getRowCount() - 1; rowIndex >= 0; rowIndex--) {
		value = this.getValueAt(rowIndex, 1);
		if (value == null) continue;
		address = (String) value;
		if (!flightPlan.getAddress().equalsIgnoreCase(address)) continue;
		this.udpate(rowIndex, flightPlan);
		existed = true;
		break;
	    }
	    if (existed) continue;
	    this.add(flightPlan);
	}
    }

    private void add(Target item) {
        
	this.addRow(
		new Object[]{
                    item.getDof(),
                    item.getAddress(),
		    item.getCallSign(),
		    item.getReg(),
		    item.getModel(),
		    item.getAircraftType(),
		    item.getOperator(),
                    item.getDep(),
                    item.getDest(),
		    item.getRoute(),
                    item
		});
    }

    private void udpate(int index, Target item) {
        this.setValueAt(item.getDof(), index, 0);
	this.setValueAt(item.getCallSign(), index, 2);
	this.setValueAt(item.getReg(), index, 3);
	this.setValueAt(item.getModel(), index, 4);
	this.setValueAt(item.getAircraftType(), index, 5);
        this.setValueAt(item.getOperator(), index, 6);
        this.setValueAt(item.getDep(), index, 7);
        this.setValueAt(item.getDest(), index, 8);
        this.setValueAt(item.getRoute(), index, 9);
        this.setValueAt(item, index, 10);        
    }
    
    public Target getSelectedTarget() {
        int selectedIndex = this.table.getSelectedRow();
        if (selectedIndex < 0) return null;
        return (Target) this.getValueAt(selectedIndex, 10);
    }
    
    

}
