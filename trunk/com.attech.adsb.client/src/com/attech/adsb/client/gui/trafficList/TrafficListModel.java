/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.gui.trafficList;

import com.attech.adsb.client.common.ModelBase;
import com.attech.adsb.client.dto.FlightPlan;
import java.util.List;
import javax.swing.JTable;

/**
 *
 * @author andh
 */
public class TrafficListModel extends ModelBase {

    public TrafficListModel(JTable table) {
	super(table);
    }

    @Override
    protected void initialize() {
        this.addColumn("ID");
        this.addColumn("DOF");
        this.addColumn("CALLSIGN");
        this.addColumn("DEP");
        this.addColumn("ETD");
        this.addColumn("DEST");
        this.addColumn("ETA");
        this.addColumn("ATD");
        this.addColumn("ATA");
        this.addColumn("ROUTE");

        setColumnWidth(0, 0, 0, 0);
        setColumnWidth(1, 60, 80, 60);
        setColumnWidth(2, 60, 80, 60);
        setColumnWidth(3, 60, 80, 60);
        setColumnWidth(4, 60, 80, 60);
        setColumnWidth(5, 60, 80, 60);
        setColumnWidth(6, 60, 80, 60);
        setColumnWidth(7, 60, 80, 60);
        setColumnWidth(8, 60, 80, 60);

        /*
	List<SortKey> sortkeys = new ArrayList<>();
	sortkeys.add(new RowSorter.SortKey(3, SortOrder.DESCENDING));
	this.setSortKey(sortkeys);
        */
    }

    @Override
    public boolean isCellEditable(int row, int column) {
	return false;
    }

    public void update(List<FlightPlan> flightPlanList) {
	if (flightPlanList == null) {
	    return;
	}

	int rowCount = this.getRowCount();
	int id;
	Object value;
	boolean existed;

	for (FlightPlan flightPlan : flightPlanList) {
	    existed = false;
	    for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
		value = this.getValueAt(rowIndex, 0);
		if (value == null) {
		    continue;
		}
		id = (int) value;
		if (flightPlan.getId() != id) {
		    continue;
		}
		this.udpate(rowIndex, flightPlan);
		existed = true;
		break;
	    }

	    if (existed) {
		continue;
	    }
	    this.add(flightPlan);
	}
    }

    private void add(FlightPlan item) {
	this.addRow(
		new Object[]{
		    item.getId(),
		    item.getDof(),
		    item.getCallSign(),
		    item.getDep(),
                    item.getEtd(),
		    item.getDest(),
                    item.getEta(),
		    item.getAtd(),
		    item.getAta(),
		    item.getRoute()
		});
    }

    private void udpate(int index, FlightPlan item) {
	this.setValueAt(item.getDof(), index, 1);
	this.setValueAt(item.getCallSign(), index, 2);
	this.setValueAt(item.getDep(), index, 3);
        this.setValueAt(item.getEtd(), index, 4);
	this.setValueAt(item.getDest(), index, 5);
        this.setValueAt(item.getEta(), index, 6);
	this.setValueAt(item.getAtd(), index, 7);
	this.setValueAt(item.getAta(), index, 8);
	this.setValueAt(item.getRoute(), index, 9);
    }

//    public void add 
}
