/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.administrator.common;

import com.attech.asd.administrator.AppContext;
import com.attech.asd.daemon.client.ClientInformationItem;
import com.attech.asd.database.AdapterObject;
import com.attech.asd.database.entities.Client;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JTable;

/**
 *
 * @author AnhTH
 */
public class ClientTableModel extends CustomTableModel {

    private final static String COL_ID = "ID";
    private final static String COL_NO = "#";
    private final static String COL_NAME = "ADDRESS";
    private final static String COL_PORT = "PORT";
    private final static String COL_DESCRIPTION = "DESCRIPTION";
    private final static String COL_FILTER = "ACTIVE";
    private final static String COL_PACKAGES = "PACKAGE";

    public ClientTableModel(JTable table) {
        super(table);
    }

    public void add(Client c) {
        this.addRow(new Object[]{
            c.getId(),
            this.getRowCount() + 1,
            c.getForwardAddress(),
            c.getForwardPort(),
            "Inactive",
            0,
            c.getDescription()
        });
    }
    
    public void setColorDefault(){
        for (int i = 0; i < this.getRowCount(); i++) {
            setCellColor(i, 4, null);
        }
    }
    
    public void setDefault(int idClient){
        if (!new AdapterObject().isExistClient(idClient)) {
            AppContext.getInstance().reloadListClient = true; // do not set = !found
            System.out.printf("Client #%s has been delete -> Reload all table.\n", idClient);
            return;
        }
        boolean found = false;
        int count = this.getRowCount();
        for (int i = 0; i < count; i++) {
            int id = (int) this.getValueAt(i, 0);
            if (id != idClient) {
                continue;
            }
            setValueAt("Inactive", i, 4);
            setCellColor(i, 4, null);
            setValueAt(0, i, 5);
            AppContext.getInstance().getWarnBroadcaster().remove(Integer.toString(idClient));
            found = true;
            System.out.printf("Client #%s set default.\n", idClient);
        }
        // Neu khong tim thay trong table thi reload lai toan bo table (truong hop add/ delete)
        if (!found)
            AppContext.getInstance().reloadListClient = true; // do not set = !found
    }

    public void update(ClientInformationItem item) {
        //System.out.println(item);
        int count = this.getRowCount();
        for (int i = 0; i < count; i++) {
            /*
            if (!this.getValueAt(i, 4).toString().equals("Active")){
                setCellColor(i, 4, null);
                setValueAt(0, i, 5);
            }
            */
            int id = (int) this.getValueAt(i, 0);
            if (id != item.getNo()) {
                continue;
            }
            this.setValueAt(item.isActive() ? "Active" : "Inactive", i, 4);
            this.setValueAt(item.getCount(), i, 5);
            final Color color = item.isActive() ? AppContext.getNormalColor() : null;
            setCellColor(i, 4, color);
            break;
        }
    }

    @Override
    protected void initialize() {
        this.addColumn(COL_ID);
        this.addColumn(COL_NO);
        this.addColumn(COL_NAME);
        this.addColumn(COL_PORT);
        this.addColumn(COL_FILTER);
        this.addColumn(COL_PACKAGES);
        this.addColumn(COL_DESCRIPTION);

        this.table.getTableHeader().setReorderingAllowed(false);
        this.table.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 13));
        this.table.setGridColor(Color.gray);
        this.table.setShowGrid(true);
        this.table.setRowHeight(30);

        setColumnWidth(COL_ID, 0, 0, 0);
        setColumnWidth(COL_NO, 30, 30, 30);
        setColumnWidth(COL_NAME, 130, 130, 130);
        setColumnWidth(COL_PORT, 50, 50, 50);
        setColumnWidth(COL_FILTER, 70, 70, 70);
        setColumnWidth(COL_PACKAGES, 80, 80, 80);
        
        this.table.setDefaultRenderer(String.class, new CellRender());
        this.table.setDefaultRenderer(Integer.class, new CellRender());
        this.table.setDefaultRenderer(Boolean.class, new CellRender());
        this.table.setDefaultRenderer(Object.class, new CellRender());
    }
}
