/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.administrator.common;

import com.attech.asd.administrator.AppContext;
//import com.attech.asd.amhs.service.monitor.ThreadStatus;
import com.attech.asd.database.entities.MessageAccount;
//import com.attech.asd.amhs.service.monitor.Status;
import com.attech.asd.database.dao.MessageAccountDao;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

/**
 *
 * @author AnhTH
 */
public class MessageAccountTableModel extends CustomTableModel{
    private final static String COL_ID = "ID";
    private final static String COL_NO = "#";
    private final static String COL_NAME = "NAME";
    private final static String COL_STATUS = "ACTIVE";
    private final static String COL_ENABLE = "ENABLE";
    private final static String COL_MESSAGE = "MESSAGE";
    private final static String COL_REMARK = "REMARK";
    
    public MessageAccountTableModel(JTable table){
        super(table);
    }
    
    public void add(MessageAccount address) {
        this.addRow(new Object[]{
            address.getId(),
            this.getRowCount() + 1,
            address.getName(), 
//            Status.STOPPED,
	    address.getEnable(),
	    0,
	    address.getDescription()
            });
    }
    
//    public void updateAccoutMessageTable(ThreadStatus threadStatus) {
//        System.out.println(threadStatus);
//        int count = this.getRowCount();
//        for (int i =0; i< count; i++) {
//            int id = (int) this.getValueAt(i, 0);
//            if (id != threadStatus.getId()) {
//                continue;
//            }
//            this.setValueAt(threadStatus.getStatus(), i, 3);
//            this.setValueAt(threadStatus.getMessageCount(), i, 5);
//            this.setValueAt(threadStatus.getRemark(), i, 6);
//            switch (threadStatus.getStatus()){
//                case RUNNING:
//                    setCellColor(i, 3, AppContext.getNormalColor());
//                    break;
//                case STOPPED:
//                    setCellColor(i, 3, null);
//                    this.setValueAt(Status.STOPPED, i, 3);
//                    this.setValueAt(0, i, 5);
//                    break;
//                case ERROR:
//                    setCellColor(i, 3, AppContext.getErrorColor());
//                    break;
//            }
//            break;
//        }
//    }

    @Override
    protected void initialize() {
        this.addColumn(COL_ID);
        this.addColumn(COL_NO);
        this.addColumn(COL_NAME);
        this.addColumn(COL_STATUS);
        this.addColumn(COL_ENABLE);
        this.addColumn(COL_MESSAGE);
        this.addColumn(COL_REMARK);
        
        this.table.getTableHeader().setReorderingAllowed(false);
        this.table.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 13));
        this.table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.table.setGridColor(Color.gray);
        this.table.setRowHeight(30);
        
        setColumnWidth(COL_ID, 0, 0, 0);
        setColumnWidth(COL_NO, 30, 30, 30);
        setColumnWidth(COL_NAME, 90, 90, 90);
        setColumnWidth(COL_STATUS, 60, 60, 60);
        setColumnWidth(COL_ENABLE, 60, 60, 60);
        setColumnWidth(COL_MESSAGE, 70, 70, 70);
        
        this.table.setDefaultRenderer(String.class, new CellRender());
        this.table.setDefaultRenderer(Integer.class, new CellRender());
        this.table.setDefaultRenderer(Boolean.class, new CellRender());
        this.table.setDefaultRenderer(Object.class, new CellRender());
    }
    
    public void setDefault(int idAcc){
        if (!new MessageAccountDao().isExist(idAcc)){
            AppContext.getInstance().reloadListMessage = true; // do not set = !found
            System.out.printf("MsgAcc #%s has been delete -> Reload all table.\n", idAcc);
            return;
        }
        boolean found = false;
        int count = this.getRowCount();
        for (int i = 0; i < count; i++) {
            int id = (int) this.getValueAt(i, 0);
            if (id != idAcc) {
                continue;
            }
//            setValueAt(Status.STOPPED, i, 3); // status
            setCellColor(i, 3, null); // reset color
            setValueAt(0, i, 5); // msg count
            
            AppContext.getInstance().getWarnReceiver().remove(Integer.toString(idAcc));
            found = true;
            System.out.printf("Message Account #%s set default.\n", idAcc);
        }
        
        // Neu khong tim thay trong table thi reload lai toan bo table (truong hop add/ delete)
        if (!found) {
            AppContext.getInstance().reloadListMessage = true; // do not set = !found
            System.out.printf("Message Account #%s not contain in table model -> Reload all table.\n", idAcc);
        }
        
    }
}
