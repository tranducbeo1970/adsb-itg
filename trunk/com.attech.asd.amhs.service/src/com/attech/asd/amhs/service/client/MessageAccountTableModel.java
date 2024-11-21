/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.amhs.service.client;

import com.attech.asd.amhs.database.entities.MessageAccount;
import com.attech.asd.amhs.service.monitor.ThreadStatus;
import javax.swing.JTable;

/**
 *
 * @author ANDH
 */
public class MessageAccountTableModel extends CustomTableModel {

    public MessageAccountTableModel(JTable jtable) {
	super(jtable);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
	return false;
    }

    @Override
    protected void initialize() {
	this.addColumn("ID");
	this.addColumn("Name");
	this.addColumn("Status");
	this.addColumn("Enabled");
	this.addColumn("Message Count");
	this.addColumn("Remark");
	
	setColumnWidth(0, 0, 0, 0);
	setColumnWidth(1, 90, 130, 90);
	setColumnWidth(2, 70, 70, 70);
	setColumnWidth(3, 60, 60, 60);
	setColumnWidth(4, 90, 130, 130);
    }

    public void add(MessageAccount address) {
        this.addRow(new Object[]{
            address.getId(),
            address.getName(), 
            "Stop",
	    address.getEnable(),
	    0,
	    address.getDescription()
            });
    }
    
    public void update(ThreadStatus threadStatus) {
        int count = this.getRowCount();
        for (int i =0; i< count; i++) {
            int id = (int) this.getValueAt(i, 0);
            if (id != threadStatus.getId()) {
                continue;
            }
            this.setValueAt(threadStatus.getStatus(), i, 2);
            this.setValueAt(threadStatus.getMessageCount(), i, 4);
            this.setValueAt(threadStatus.getRemark(), i, 5);
            break;
        }
    }
    
//    
//    public void add(List<AddressBook> addresses) {
//        for (AddressBook address : addresses) {
//            this.add(address);
//        }
//    }
}
