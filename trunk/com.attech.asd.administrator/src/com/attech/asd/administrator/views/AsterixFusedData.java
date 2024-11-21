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
import com.attech.asd.daemon.RecordItem;
import com.attech.asd.database.Base;
import com.attech.asd.database.FusedFileRecordDao;
import com.attech.asd.database.common.ActionRequest;
import com.attech.asd.database.common.Command;
import com.attech.asd.database.entities.FusedFileRecord;
import com.attech.cat21.v210.Cat21Decoder;
import com.attech.cat21.v210.Cat21Message;
import java.awt.Point;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingWorker;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author AnhTH
 */
public class AsterixFusedData extends CustomDialog {

    private final FusedFileRecord record;
    //private ObjectInputStream ois; // = new ObjectInputStream(socket1.getInputStream());
    //private ObjectOutputStream oos; // = new ObjectOutputStream(socket1.getOutputStream());

    private final NormalTableModel cat21Model;
    private List<RecordItem> items;
    
    private Workbook workbook;
    
    private String PathOutput;
    
    private final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(AsterixFusedData.class);

    /**
     * Creates new form ReceiverDialog
     *
     * @param parent
     * @param modal
     * @param handle
     */
    public AsterixFusedData(java.awt.Frame parent, boolean modal, int id) {
        super(parent, modal);
        initComponents();
        this.record = new FusedFileRecordDao().get(id);
        lblFileInfo.setText(String.format("%s ", record.getFileName()));
        
        cat21Model = new NormalTableModel(this.tblCat21);
        initialCat21Table();
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
            logger.error(ex);
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
                        //System.out.println(listRecord.size());
                        requestStop = true;
                    } catch (IOException | ClassNotFoundException ex) {
                        return ex.getMessage();
                    }
                }
                if (items != null && items.size() > 0) {
                    
                        for (RecordItem item : items) {
                            List<Cat21Message> messages21 = new ArrayList<>();
                            Cat21Decoder.decode(item.getBytes(), messages21);
                            if (messages21.size() > 0) {
                                for (Cat21Message msg : messages21) {
                                    cat21Model.addRow(msg.getValueArray());
                                }
                            }
                        }
                    

                    return String.format("%s packages", items.size());
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
                }
            }
        };

        sw.execute();
    }

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
                    lblNoticeExport.setText(String.format("Please wait... Creating Asterix Cat%s Sheet: %s/%s rows)", type, rows, model.getRowCount()));
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
                        logger.error(ex);
                    } catch (IOException ex) {
                        logger.error(ex);
                    }
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
                finally{
                    btnSave.setEnabled(true);
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
        btnSave = new javax.swing.JButton();
        btnClose = new javax.swing.JButton();
        lblNoticeExport = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCat21 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("View Asterix Data -  ADSB Administrator Terminal 1.0.0");

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
                .addComponent(lblNotice, javax.swing.GroupLayout.DEFAULT_SIZE, 452, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnHeaderLayout.setVerticalGroup(
            pnHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblNotice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lblFileInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
        );

        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/save.png"))); // NOI18N
        btnSave.setText("Export");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/door_out.png"))); // NOI18N
        btnClose.setText("Close");
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        lblNoticeExport.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

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
                    .addComponent(lblNoticeExport, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnSave)
                        .addComponent(btnClose)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnBottom, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(2, 2, 2)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 925, Short.MAX_VALUE)
                    .addGap(3, 3, 3)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(pnHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 417, Short.MAX_VALUE)
                .addComponent(pnBottom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(27, 27, 27)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(52, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCloseActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        /*try {
            JFileChooser chooser = new JFileChooser();
            File file;
            file = new File(".");
            chooser.setCurrentDirectory(file);
            chooser.setDialogTitle("Select Folder To Save...");
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setAcceptAllFileFilterUsed(false);
            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                String outputFolder = chooser.getSelectedFile().getPath();
                PathOutput = outputFolder;
                ExportToExcel();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage());
        } finally {

        }
        */
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnSave;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblFileInfo;
    private javax.swing.JLabel lblNotice;
    private javax.swing.JLabel lblNoticeExport;
    private javax.swing.JPanel pnBottom;
    private javax.swing.JPanel pnHeader;
    private javax.swing.JTable tblCat21;
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
        AsterixFusedData dialog = new AsterixFusedData(null, true, 733);
        //AsterixData dialog = new AsterixData(null, true, 707);
        dialog.setVisible(true);
    }
}
