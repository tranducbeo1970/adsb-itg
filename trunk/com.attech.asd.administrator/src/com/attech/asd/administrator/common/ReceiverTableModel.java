/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.administrator.common;

import com.attech.asd.administrator.AppContext;
import com.attech.asd.daemon.receiver.InformationItem;
import com.attech.asd.database.SensorsDao;
import com.attech.asd.database.entities.Sensors;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.ListSelectionModel;

/**
 *
 * @author AnhTH
 */
public class ReceiverTableModel extends CustomTableModel {

    private final static String COL_ID = "ID";
    private final static String COL_NO = "#";
    private final static String COL_NAME = "NAME";
    private final static String COL_STATUS = "ACTIVE";
    private final static String COL_TYPE = "TYPE";
    private final static String COL_SIC = "SIC";
    private final static String COL_PORT = "PORT";
    private final static String COL_DATA = "RECEIVED";
    private final static String COL_DESCRIPTION = "DESCRIPTION";

    public ReceiverTableModel(JTable table) {
        super(table);
    }

    public void add(Sensors s) {
        this.addRow(new Object[]{
            s.getId(),
            this.getRowCount() + 1,
            (s.getSensorMode() == 1) ? "ADS-B" : "RADAR",
            s.getSic(),
            s.getStation().getStationName(),
            s.getReceivingPort(),
            "Inactive",
            0,
            ""
        });
    }
    
    public void setColorDefault(){
        for (int i = 0; i < this.getRowCount(); i++) {
            setCellColor(i, 6, null);
            setCellColor(i, 7, null);
        }
    }
    
    public void setDefault(int idSensor){
        if (!new SensorsDao().isExist(idSensor)){
            AppContext.getInstance().reloadListReceiver = true;
            System.out.printf("Receiver #%s has been delete -> Reload all table.\n", idSensor);
            return;
        }
        boolean found = false;
        int count = this.getRowCount();
        for (int i = 0; i < count; i++) {
            int id = (int) this.getValueAt(i, 0);
            if (id != idSensor) {
                continue;
            }
            setValueAt("Inactive", i, 6);
            setCellColor(i, 6, null);
            
            removeAlert(i, 7);
            setValueAt(0, i, 7);
            setCellColor(i, 7, null);
            setValueAt("", i, 8);
            AppContext.getInstance().getWarnReceiver().remove(Integer.toString(idSensor));
            found = true;
            System.out.printf("Receiver #%s set default.\n", idSensor);
        }
        
        // Neu khong tim thay trong table thi reload lai toan bo table (truong hop add/ delete)
        if (!found)
            AppContext.getInstance().reloadListReceiver = true; // do not set = !found
    }

    public void update(InformationItem item) {
        //System.out.println(item);
        //int count = this.getRowCount();
        int count = this.getDisplayedRow();
        for (int i = 0; i < count; i++) {
            int id = (int) this.getValueAt(i, 0);
            if (id != item.getNo()) {
                continue;
            }
            this.setValueAt((item.isStatus() ? "Active" : "Inactive"), i, 6);
            this.setValueAt(item.getReceived(), i, 7);
            this.setValueAt(item.getDes(), i, 8);
            
            setCellColor(i, 6, null);
            setCellColor(i, 7, null);
            //ACTIVE COLUMN
            if (item.isStatus())
                setCellColor(i, 6, AppContext.getNormalColor());
            
            //PACKAGE VA DESCRIPTION COLUMN
            if (item.getReceivingStatus()) {// NEU CO DU LIEU
                setCellColor(i, 7, AppContext.getNormalColor());
                if ( AppContext.getInstance().getWarnReceiver().contains(Integer.toString(id))){
                    AppContext.getInstance().getWarnReceiver().remove(Integer.toString(id));
                    System.out.println(String.format("#%s set normal", item.getNo()));
                }
                    
            } else {
                if (item.isData()) {
                    setCellColor(i, 7, AppContext.getWarnColor());
                    setValueAt("Cannot receive any data...", i, 8);
                    AppContext.getInstance().getWarnReceiver().add(Integer.toString(id));
                    System.out.println(String.format("#%s can not receive any data", item.getNo()));
                } else {
                    setCellColor(i, 7, null);
                    setValueAt("", i, 8);
                    AppContext.getInstance().getWarnReceiver().remove(Integer.toString(id));
                    System.out.println(String.format("#%s set normal", item.getNo()));
                }
            }

            break;
        }
    }

    @Override
    protected void initialize() {
        this.addColumn(COL_ID);
        this.addColumn(COL_NO);
        this.addColumn(COL_TYPE);
        this.addColumn(COL_SIC);
        this.addColumn(COL_NAME);
        this.addColumn(COL_PORT);
        this.addColumn(COL_STATUS);
        this.addColumn(COL_DATA);
        this.addColumn(COL_DESCRIPTION);

        this.table.getTableHeader().setReorderingAllowed(false);
        this.table.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 13));
        this.table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.table.setGridColor(Color.gray);
        this.table.setShowGrid(true);
        this.table.setRowHeight(30);

        setColumnWidth(COL_ID, 0, 0, 0);
        setColumnWidth(COL_NO, 30, 30, 30);
        setColumnWidth(COL_TYPE, 60, 60, 60);
        setColumnWidth(COL_SIC, 45, 45, 45);
        setColumnWidth(COL_NAME, 150, 150, 150);
        setColumnWidth(COL_STATUS, 70, 70, 70);
        setColumnWidth(COL_DATA, 80, 80, 80);
        setColumnWidth(COL_PORT, 50, 50, 50);
        
        this.table.setDefaultRenderer(String.class, new CellRender());
        this.table.setDefaultRenderer(Integer.class, new CellRender());
        this.table.setDefaultRenderer(Boolean.class, new CellRender());
        this.table.setDefaultRenderer(Object.class, new CellRender());
    }
    
    public void createLineNumber() {

        JViewport jviewPort = (JViewport) this.table.getParent();
        if (jviewPort == null) {
            return;
        }

        JScrollPane jscrollPane = (JScrollPane) jviewPort.getParent();
        if (jscrollPane == null) {
            return;
        }

        TableRowNumber tableLineNumber = new TableRowNumber(jscrollPane, table);
        tableLineNumber.setBackground(Color.LIGHT_GRAY);
        jscrollPane.setRowHeaderView(tableLineNumber);

    }
}
