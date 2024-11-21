/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.administrator.views;

import com.attech.asd.administrator.AppContext;
import com.attech.asd.administrator.common.CustomDialog;
import com.attech.asd.administrator.common.ExceptionHandler;
import com.attech.asd.administrator.common.NormalTableModel;
import com.attech.asd.administrator.common.RadarDataDecrypter;
import com.attech.asd.daemon.RecordItem;
import com.attech.asd.database.Base;
import com.attech.asd.database.FileRecordDao;
import com.attech.asd.database.common.ActionRequest;
import com.attech.asd.database.common.Command;
import com.attech.asd.database.entities.FileRecord;
import com.attech.cat01.v120.Cat01Message;
import com.attech.cat02.v10.Message02;
import com.attech.cat21.v210.Cat21Decoder;
import com.attech.cat21.v210.Cat21Message;
import com.attech.cat34.v127.Message34;
import com.attech.cat48.v121.Cat48Message;
import java.awt.Point;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingWorker;

/**
 *
 * @author AnhTH
 */
public class AsterixData extends CustomDialog {

    private final FileRecord record;
    //private ObjectInputStream ois; // = new ObjectInputStream(socket1.getInputStream());
    //private ObjectOutputStream oos; // = new ObjectOutputStream(socket1.getOutputStream());

    private final NormalTableModel cat21Model;
    private final NormalTableModel cat01Model;
    private final NormalTableModel cat02Model;
    private final NormalTableModel cat34Model;
    private final NormalTableModel cat48Model;
    private List<RecordItem> items;
    
    //private Workbook workbook;
    
    private String PathOutput;

    /**
     * Creates new form ReceiverDialog
     *
     * @param parent
     * @param modal
     * @param handle
     */
    public AsterixData(java.awt.Frame parent, boolean modal, int id) {
        super(parent, modal);
        initComponents();
        this.record = new FileRecordDao().get(id);
        lblFileInfo.setText(String.format("%s (from SIC %s)", record.getFileName(), record.getSensor().getSic()));
        
        cat21Model = new NormalTableModel(this.tblCat21);

        cat01Model = new NormalTableModel(this.tblCat01);
        cat02Model = new NormalTableModel(this.tblCat02);
        cat34Model = new NormalTableModel(this.tblCat34);
        cat48Model = new NormalTableModel(this.tblCat48);

        initialCat21Table();
        initialCat01Table();
        initialCat02Table();
        initialCat34Table();
        initialCat48Table();
        items = new ArrayList<>();

        binding();
    }

    private void initialCat21Table() {
        //tblCat21.setSize(2000, 1000);
        for (Field field : Cat21Message.class.getDeclaredFields()) {
            
            cat21Model.addColumn(field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1));
        }
        cat21Model.setNumRows(0);
    }

    private void initialCat01Table() {
        for (Field field : Cat01Message.class.getDeclaredFields()) {
            cat01Model.addColumn(field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1));
        }
        cat01Model.setNumRows(0);
    }

    private void initialCat02Table() {
        for (Field field : Message02.class.getDeclaredFields()) {
            cat02Model.addColumn(field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1));
        }
        cat02Model.setNumRows(0);
    }

    private void initialCat34Table() {
        for (Field field : Message34.class.getDeclaredFields()) {
            cat34Model.addColumn(field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1));
        }
        cat34Model.setNumRows(0);
    }

    private void initialCat48Table() {
        for (Field field : Cat48Message.class.getDeclaredFields()) {
            cat48Model.addColumn(field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1));
        }
        cat48Model.setNumRows(0);
    }

    private void binding() {
        //ois = AppContext.getInstance().getCommandSocket().getOIS();
        //oos = AppContext.getInstance().getCommandSocket().getOOS();

        getData();
        //Thread.sleep(2000);
        List<Object> objs = new ArrayList<>();
        objs.add(record.getAbsolutePath());
        Command command = new Command();
        command.setCmd(ActionRequest.READ_FILE);
        command.setParams(objs);
        try {
            AppContext.getInstance().getCommandSocket().getOOS().writeObject(command);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Connection reset");
            //logger.error(ex);
        }
        finally{
            System.gc();
        }
    }

    private void getData() {
        SwingWorker sw = new SwingWorker() {
            @Override
            protected String doInBackground() throws Exception {
                btnSave.setEnabled(false);
                boolean requestStop = false;
                Object obj = null;
                while (!requestStop) {
                    try {
                        lblNotice.setText("Getting file data from server...");
                        obj = AppContext.getInstance().getCommandSocket().getOIS().readObject();
                        items = (List<RecordItem>) obj;
                        requestStop = true;
                    } catch (IOException | ClassNotFoundException ex) {
                        return ex.getMessage();
                    }
                }
                int countMessages = 0;
                List<String> lstCallsign = new ArrayList<>();
                if (items != null && items.size() > 0) {
                    if (record.getSensor().getSensorMode().intValue() == 1) {// ADS-B
                        for (RecordItem item : items) {
                            List<Cat21Message> messages21 = new ArrayList<>();
                            Cat21Decoder.decode(item.getBytes(), messages21);
                            if (messages21.size() > 0) {
                                for (Cat21Message msg : messages21) {
                                    cat21Model.addRow(msg.getValueArray());
                                    if(!lstCallsign.contains(msg.getCallSign())){
                                        lstCallsign.add(msg.getCallSign());
                                    }                                    
                                }
                                countMessages += messages21.size();
                            }
                        }
                    } else {
                        for (RecordItem item : items) {
                            List<Cat01Message> messages01 = new ArrayList<>();
                            List<Message02> messages02 = new ArrayList<>();
                            List<Message34> messages34 = new ArrayList<>();
                            List<Cat48Message> messages48 = new ArrayList<>();
                            RadarDataDecrypter.decode(item.getBytes(), messages01, messages02, messages34, messages48);
                            if (messages01.size() > 0) {
                                for (Cat01Message msg : messages01) {
                                    cat01Model.addRow(msg.getValueArray());
                                }
                            }
                            if (messages02.size() > 0) {
                                for (Message02 msg : messages02) {
                                    cat02Model.addRow(msg.getValueArray());
                                }
                            }
                            if (messages34.size() > 0) {
                                for (Message34 msg : messages34) {
                                    cat34Model.addRow(msg.getValueArray());
                                }
                            }
                            if (messages48.size() > 0) {
                                for (Cat48Message msg : messages48) {
                                    cat48Model.addRow(msg.getValueArray());
                                }
                            }
                        }
                    }

                    return String.format("%s packages, %s messages, %s flights", items.size(), countMessages, lstCallsign.size());
                }
                return String.format("No Data or Could Not Received Any Data.");
            }

            @Override
            protected void process(List chunks) {
                // define what the event dispatch thread  
                // will do with the intermediate results received 
                // while the thread is executing 
                int val = (int) chunks.get(chunks.size() - 1);

                lblNotice.setText(String.valueOf(val));
            }

            @Override
            protected void done() {
                try {
                    String statusMsg = (String) get();
                    lblNotice.setText(statusMsg);
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                } finally {
                    btnSave.setEnabled(true);
                    jTabbedPane1.setEnabledAt(0, (cat21Model.getRowCount() > 0));
                    jTabbedPane1.setEnabledAt(1, (cat01Model.getRowCount() > 0));
                    jTabbedPane1.setEnabledAt(2, (cat02Model.getRowCount() > 0));
                    jTabbedPane1.setEnabledAt(3, (cat34Model.getRowCount() > 0));
                    jTabbedPane1.setEnabledAt(4, (cat48Model.getRowCount() > 0));
                    switch (record.getSensor().getSensorMode().intValue()){
                        case 1:
                            jTabbedPane1.setSelectedIndex(0);
                            break;
                        case 2: 
                            jTabbedPane1.setSelectedIndex(4);
                            break;
                        default:
                            jTabbedPane1.setSelectedIndex(2);
                            break;
                            
                    }
                }
            }
        };

        sw.execute();
    }
    /*
    private void ExportToExcel() {
        SwingWorker sw = new SwingWorker() {
            
            private void createSheet(NormalTableModel model, int type){
                Sheet sheet = workbook.createSheet("Asterix Cat" + type); //WorkSheet
                Row row = sheet.createRow(2); //Row created at line 3
                
                Row headerRow = sheet.createRow(0); //Create row at line 0
                for (int headings = 0; headings < model.getColumnCount(); headings++) { //For each column
                    headerRow.createCell(headings).setCellValue(model.getColumnName(headings));//Write column name
                }
                
                for (int rows = 0; rows < model.getRowCount(); rows++) { //For each table row
                    for (int cols = 0; cols < model.getColumnCount(); cols++) { //For each table column
                        row.createCell(cols).setCellValue((model.getValueAt(rows, cols) != null) ? model.getValueAt(rows, cols).toString() : ""); //Write value
                    }
                    //Set the row to the next one in the sequence 
                    row = sheet.createRow(rows + 3);
                    //lblNoticeExport.setText(String.format("Please wait... Creating Asterix Cat%s Sheet: %s/%s rows)", type, rows, model.getRowCount()));
                }
            }
            
            @Override
            protected String doInBackground() throws Exception {
                btnSave.setEnabled(false);
                new WorkbookFactory();
                workbook = new XSSFWorkbook(); //Excell workbook
                if (cat21Model.getRowCount() > 0) {
                    createSheet(cat21Model, 21);
                }
                if (cat01Model.getRowCount() > 0){
                    createSheet(cat01Model, 1);
                }
                if (cat02Model.getRowCount() > 0){
                    createSheet(cat02Model, 2);
                }
                if (cat34Model.getRowCount() > 0){
                    createSheet(cat34Model, 34);
                }
                if (cat48Model.getRowCount() > 0){
                    createSheet(cat48Model, 48);
                }
                return String.format("");
            }
            
            @Override
            protected void process(List chunks) {
                int val = (int) chunks.get(chunks.size() - 1);

                lblNotice.setText(String.valueOf(val));
            }

            @Override
            protected void done() {
                try {
                    String statusMsg = (String) get();
                    lblNoticeExport.setText(statusMsg);
                    try {
                        workbook.write(new FileOutputStream(PathOutput + "\\" + record.getFileName().replace("rcd", "xlsx")));//Save the file
                        JOptionPane.showMessageDialog(rootPane, "Successfully!");
                        
                    } catch (FileNotFoundException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                        //logger.error(ex);
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                        //logger.error(ex);
                    }
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
                finally{
                    btnSave.setEnabled(true);
                    System.gc();
                    
                }
            }
        };
        sw.execute();
        
    }
    */
    private void ExportToCsv() {
        SwingWorker sw = new SwingWorker() {
            
            private void createSheet(NormalTableModel model, int type) throws IOException{
                final File outputFile = new File(PathOutput + "\\" + "cat" + type + "_" + record.getFileName().replace("rcd", "csv"));
                final FileWriter fw = new FileWriter(outputFile);
                final BufferedWriter bw = new BufferedWriter(fw);
                StringBuilder builder = new StringBuilder();
                
                for (int headings = 0; headings < model.getColumnCount(); headings++) { //For each column
                    builder.append(String.format("%s,", model.getColumnName(headings)));
                }
                builder.append("\n");
                bw.write(builder.toString());
                
                builder = new StringBuilder();
                for (int rows = 0; rows < model.getRowCount(); rows++) { //For each table row
                    for (int cols = 0; cols < model.getColumnCount(); cols++) { //For each table column
                         builder.append(String.format("%s,", (model.getValueAt(rows, cols) != null) ? model.getValueAt(rows, cols).toString() : ""));
                    }
                    builder.append("\n");
                    //lblNoticeExport.setText(String.format("Please wait... Creating Asterix Cat%s Sheet: %s/%s rows)", type, rows, model.getRowCount()));
                }
                bw.write(builder.toString());
                bw.close();
                fw.close();
            }
            
            @Override
            protected String doInBackground() throws Exception {
                btnSave.setEnabled(false);
                
                if (cat21Model.getRowCount() > 0) {
                    createSheet(cat21Model, 21);
                }
                if (cat01Model.getRowCount() > 0){
                    createSheet(cat01Model, 1);
                }
                if (cat02Model.getRowCount() > 0){
                    createSheet(cat02Model, 2);
                }
                if (cat34Model.getRowCount() > 0){
                    createSheet(cat34Model, 34);
                }
                if (cat48Model.getRowCount() > 0){
                    createSheet(cat48Model, 48);
                }
                return String.format("");
            }
            
            @Override
            protected void process(List chunks) {
                int val = (int) chunks.get(chunks.size() - 1);

                lblNotice.setText(String.valueOf(val));
            }

            @Override
            protected void done() {
                try {
                    String statusMsg = (String) get();
                    lblNoticeExport.setText(statusMsg);
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
                finally{
                    btnSave.setEnabled(true);
                    System.gc();
                    
                }
            }
        };
        sw.execute();
        
    }
    
    private void createSheet(NormalTableModel model, int type) throws IOException {
        File outputFile = new File(PathOutput + "\\" + "cat" + type + "_" + record.getFileName().replace("rcd", "csv"));
        FileWriter fw = new FileWriter(outputFile);
        BufferedWriter bw = new BufferedWriter(fw);
        StringBuilder builder = new StringBuilder();

        for (int headings = 0; headings < model.getColumnCount(); headings++) { //For each column
            builder.append(String.format("%s,", model.getColumnName(headings)));
        }
        builder.append("\n");
        
        for (int rows = 0; rows < model.getRowCount(); rows++) { //For each table row
            for (int cols = 0; cols < model.getColumnCount(); cols++) { //For each table column
                builder.append(String.format("%s,", (model.getValueAt(rows, cols) != null) ? model.getValueAt(rows, cols).toString() : ""));
            }
            builder.append("\n");
            //lblNoticeExport.setText(String.format("Please wait... Creating Asterix Cat%s Sheet: %s/%s rows)", type, rows, model.getRowCount()));
        }
        bw.write(builder.toString());
        bw.close();
        fw.close();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnHeader = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblFileInfo = new javax.swing.JLabel();
        lblNotice = new javax.swing.JLabel();
        pnBottom = new javax.swing.JPanel();
        btnClose = new javax.swing.JButton();
        lblNoticeExport = new javax.swing.JLabel();
        btnSave = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCat21 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblCat01 = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblCat02 = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblCat34 = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblCat48 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("View Asterix Data -  ADSB Administrator Terminal 1.0.0");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jLabel1.setText("FILE:");

        lblNotice.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        javax.swing.GroupLayout pnHeaderLayout = new javax.swing.GroupLayout(pnHeader);
        pnHeader.setLayout(pnHeaderLayout);
        pnHeaderLayout.setHorizontalGroup(
            pnHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnHeaderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblFileInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 417, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblNotice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnHeaderLayout.setVerticalGroup(
            pnHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblNotice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lblFileInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
        );

        btnClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/door_out.png"))); // NOI18N
        btnClose.setText("Close");
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        lblNoticeExport.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/bullet_disk.png"))); // NOI18N
        btnSave.setText("Export CSV");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnBottomLayout = new javax.swing.GroupLayout(pnBottom);
        pnBottom.setLayout(pnBottomLayout);
        pnBottomLayout.setHorizontalGroup(
            pnBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnBottomLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblNoticeExport, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(288, 288, 288)
                .addComponent(btnSave)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnClose)
                .addContainerGap())
        );
        pnBottomLayout.setVerticalGroup(
            pnBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnBottomLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblNoticeExport, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnClose)
                        .addComponent(btnSave)))
                .addContainerGap(11, Short.MAX_VALUE))
        );

        tblCat21.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblCat21.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblCat21.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblCat21MousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblCat21);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 925, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 377, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("ADS-B: Asterix Cat 21", jPanel1);

        tblCat01.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblCat01.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblCat01.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblCat01MousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(tblCat01);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 925, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 366, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("RADAR: Asterix Cat 01", jPanel2);

        tblCat02.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblCat02.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblCat02.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblCat02MousePressed(evt);
            }
        });
        jScrollPane3.setViewportView(tblCat02);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 925, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 366, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("RADAR: Asterix Cat 02", jPanel3);

        tblCat34.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblCat34.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblCat34.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblCat34MousePressed(evt);
            }
        });
        jScrollPane4.setViewportView(tblCat34);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 925, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 366, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("RADAR: Asterix cat 34", jPanel4);

        tblCat48.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblCat48.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblCat48.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblCat48MousePressed(evt);
            }
        });
        jScrollPane5.setViewportView(tblCat48);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 925, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 366, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("RADAR: Asterix Cat 48", jPanel5);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnBottom, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(pnHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 405, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pnBottom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        System.gc();
        this.dispose();
    }//GEN-LAST:event_btnCloseActionPerformed

    private void tblCat21MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCat21MousePressed
        try {
            if (evt.getButton() != 1) {
                return;
            }
            JTable table = (JTable) evt.getSource();
            Point p = evt.getPoint();
            int row = table.rowAtPoint(p);
            int col = table.columnAtPoint(p);
            if (evt.getClickCount() != 2) {
                return;
            }
            
            JOptionPane.showMessageDialog(this.rootPane, table.getValueAt(row, col), table.getColumnName(col), JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            ExceptionHandler.handle(ex);
        }
    }//GEN-LAST:event_tblCat21MousePressed

    private void tblCat01MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCat01MousePressed
        try {
            if (evt.getButton() != 1) {
                return;
            }
            JTable table = (JTable) evt.getSource();
            Point p = evt.getPoint();
            int row = table.rowAtPoint(p);
            int col = table.columnAtPoint(p);
            if (evt.getClickCount() != 2) {
                return;
            }
            
            JOptionPane.showMessageDialog(this.rootPane, table.getValueAt(row, col), table.getColumnName(col), JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            ExceptionHandler.handle(ex);
        }
    }//GEN-LAST:event_tblCat01MousePressed

    private void tblCat02MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCat02MousePressed
        try {
            if (evt.getButton() != 1) {
                return;
            }
            JTable table = (JTable) evt.getSource();
            Point p = evt.getPoint();
            int row = table.rowAtPoint(p);
            int col = table.columnAtPoint(p);
            if (evt.getClickCount() != 2) {
                return;
            }
            
            JOptionPane.showMessageDialog(this.rootPane, table.getValueAt(row, col), table.getColumnName(col), JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            ExceptionHandler.handle(ex);
        }
    }//GEN-LAST:event_tblCat02MousePressed

    private void tblCat34MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCat34MousePressed
        try {
            if (evt.getButton() != 1) {
                return;
            }
            JTable table = (JTable) evt.getSource();
            Point p = evt.getPoint();
            int row = table.rowAtPoint(p);
            int col = table.columnAtPoint(p);
            if (evt.getClickCount() != 2) {
                return;
            }
            
            JOptionPane.showMessageDialog(this.rootPane, table.getValueAt(row, col), table.getColumnName(col), JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            ExceptionHandler.handle(ex);
        }
    }//GEN-LAST:event_tblCat34MousePressed

    private void tblCat48MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCat48MousePressed
        try {
            if (evt.getButton() != 1) {
                return;
            }
            JTable table = (JTable) evt.getSource();
            Point p = evt.getPoint();
            int row = table.rowAtPoint(p);
            int col = table.columnAtPoint(p);
            if (evt.getClickCount() != 2) {
                return;
            }
            
            JOptionPane.showMessageDialog(this.rootPane, table.getValueAt(row, col), table.getColumnName(col), JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            ExceptionHandler.handle(ex);
        }
    }//GEN-LAST:event_tblCat48MousePressed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        System.gc();
    }//GEN-LAST:event_formWindowClosing

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        JFileChooser chooser = new JFileChooser();
        File file;
        file = new File(".");
        PathOutput = "";
        chooser.setCurrentDirectory(file);
        chooser.setDialogTitle("Select Folder To Save...");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            String outputFolder = chooser.getSelectedFile().getPath();
            PathOutput = outputFolder;
        }
        if (PathOutput == null || PathOutput.isEmpty()) return;
        
        Thread t = new Thread(() -> {
            synchronized(this){
                try {
                if (cat21Model.getRowCount() > 0) {
                    createSheet(cat21Model, 21);
                }
                if (cat01Model.getRowCount() > 0){
                    createSheet(cat01Model, 1);
                }
                if (cat02Model.getRowCount() > 0){
                    createSheet(cat02Model, 2);
                }
                if (cat34Model.getRowCount() > 0){
                    createSheet(cat34Model, 34);
                }
                if (cat48Model.getRowCount() > 0){
                    createSheet(cat48Model, 48);
                }
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
                notify();
            }
        });
        t.start();
        synchronized (t) {
            try {
                JOptionPane.showMessageDialog(this, "Please wait for Exporting Thread...");
                t.wait();
                JOptionPane.showMessageDialog(this, "Finished");
                System.gc();
            } catch (InterruptedException e) {
                //logger.error(e);
            }
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnSave;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblFileInfo;
    private javax.swing.JLabel lblNotice;
    private javax.swing.JLabel lblNoticeExport;
    private javax.swing.JPanel pnBottom;
    private javax.swing.JPanel pnHeader;
    private javax.swing.JTable tblCat01;
    private javax.swing.JTable tblCat02;
    private javax.swing.JTable tblCat21;
    private javax.swing.JTable tblCat34;
    private javax.swing.JTable tblCat48;
    // End of variables declaration//GEN-END:variables

    public static void main(String[] args) {
        Base.configure("database.xml");
        AppContext.getInstance();
        try {
            AppContext.connectCommandSocket();
        } catch (Exception ex) {
            System.out.println("Could not connect updater-socket with server:" + ex.getMessage());
        }

        try {
            AppContext.connectUpdaterSocket();
        } catch (Exception ex) {
            System.out.println("Could not connect command-socket with server:" + ex.getMessage());
        }
        AsterixData dialog = new AsterixData(null, true, 733);
        //AsterixData dialog = new AsterixData(null, true, 707);
        dialog.setVisible(true);
    }
}
