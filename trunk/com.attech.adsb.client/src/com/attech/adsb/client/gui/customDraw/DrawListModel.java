/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.gui.customDraw;

import com.attech.adsb.client.common.ModelBase;
import com.attech.adsb.client.config.CustomDrawItem;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;

/**
 *
 * @author andh
 */
public class DrawListModel extends ModelBase {

    public DrawListModel(JTable table) {
	super(table);
    }

    @Override
    protected void initialize() {
	this.addColumn("");
	this.addColumn("Name");
	this.addColumn("Type");
	this.addColumn("Enable");
        
	setColumnWidth(0, 0, 0, 0);
        setColumnWidth(2, 80, 100, 100);
        setColumnWidth(3, 80, 80, 80);
        this.table.setRowHeight(20);

        this.table.getColumnModel().getColumn(3).setCellRenderer(this.table.getDefaultRenderer(Boolean.class));
        JCheckBox checkbox = new JCheckBox();
        checkbox.setHorizontalAlignment(JCheckBox.CENTER);
        DefaultCellEditor dce = new DefaultCellEditor(checkbox);
        table.getColumnModel().getColumn(3).setCellEditor(dce);

//	List<SortKey> sortkeys = new ArrayList<>();
//	sortkeys.add(new RowSorter.SortKey(1, SortOrder.ASCENDING));
//	this.setSortKey(sortkeys);

    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return column == 3;
    }
    
//    public void update(List<CustomDrawItem> lstDraw) {
//	if (lstDraw == null) {
//	    return;
//	}
//	int rowCount = this.getRowCount();
//	String name;
//	Object value;
//	boolean existed;
//	for (CustomDrawItem item : lstDraw) {
//	    existed = false;
//	    for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
//		value = this.getValueAt(rowIndex, 1);
//		if (value == null) {
//		    continue;
//		}
//		name = (String) value;
//		if (!item.getName().equals(name)) {
//		    continue;
//		}
//		this.udpate(rowIndex, item);
//		existed = true;
//		break;
//	    }
//	    if (existed) {
//		continue;
//	    }
//	    this.add(item);
//	}
//    }

    public void add(CustomDrawItem item) {
	this.addRow(
                new Object[]{
                    item,
                    item.getName(),
                    item.getType(),
                    item.isEnabled()
                });  
    }
    

//    private void udpate(int index, CustomDrawItem item) {
//	this.setValueAt(item.getName(), index, 1);
//	this.setValueAt(getTypeDraw(item), index, 2);
//	this.setValueAt(item.isEnabled(), index, 3);
//    }
//    
//    private String getTypeDraw(CustomDrawItem item){
//        String type = item.getType();
//        String[] st = item.getAngle().split(",");
//            int angleFrom = Integer.parseInt(st[0]);
//            int angleTo = Integer.parseInt(st[1]);
//            if(item.getType().equals("ARC")){
//                if(angleTo < angleFrom)
//                    angleTo = 360 + angleTo; 
//                if(angleTo - angleFrom >= 360)
//                    type = "CIRCLE";
//                else
//                    type = "ARC";
//            }
//        return type;    
//    }

}
