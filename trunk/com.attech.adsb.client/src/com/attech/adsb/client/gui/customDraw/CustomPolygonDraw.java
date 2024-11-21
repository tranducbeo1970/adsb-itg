/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.gui.customDraw;

import com.attech.adsb.client.common.MLogger;
import com.attech.adsb.client.common.MessageFormatFilter;
import com.attech.adsb.client.common.MouseContext;
import com.attech.adsb.client.common.NumericAmountLimitedFilter;
import com.attech.adsb.client.common.NumericFilter;
import com.attech.adsb.client.common.Validator;
import com.attech.adsb.client.common.Wgs84Coordinate;
import com.attech.adsb.client.common.enums.MouseMode;
import com.attech.adsb.client.common.enums.ShapeType;
import com.attech.adsb.client.config.CustomDrawItem;
import com.attech.adsb.client.config.Point2f;
import com.attech.adsb.client.graphic.Convertor;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.*;

/**
 *
 * @author dungnt
 */
public class CustomPolygonDraw extends javax.swing.JDialog implements ICoordinateUsage {

    private static final MLogger logger = MLogger.getLogger(CustomPolygonDraw.class);
    public static boolean isGetCordMouse = false;
    public static boolean isUseMouse = false;
    public static List<Point2f> polygonList = new ArrayList<>();
    public static boolean isShowing;
    private final ListPointModel model;

    private final javax.swing.JFrame parent;
    private String color = "#FF0000";
    private final Color errorColor = Color.decode("0xffb3b3");
    private final Color originBackgroundColor;
    private IDrawActionPerformed actionPerform;
    

    /**
     * Creates new form SelectDraw
     * @param parent
     * @param modal
     */
    public CustomPolygonDraw(javax.swing.JFrame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        logger.info("Showed");
        this.originBackgroundColor = txtName.getBackground();
        this.btnColorChooser.setBackground(Color.red);
        this.btnColorChooser.setOpaque(true);
        
        ((AbstractDocument) txtName.getDocument()).setDocumentFilter( new MessageFormatFilter(64));
        
        ((AbstractDocument) this.txtLatDeg.getDocument()).setDocumentFilter(new NumericAmountLimitedFilter(90));
        ((AbstractDocument) this.txtLatMin.getDocument()).setDocumentFilter(new NumericAmountLimitedFilter(59));
        ((AbstractDocument) this.txtLatSec.getDocument()).setDocumentFilter(new NumericAmountLimitedFilter(59));

        ((AbstractDocument) this.txtLonDeg.getDocument()).setDocumentFilter(new NumericAmountLimitedFilter(180));
        ((AbstractDocument) this.txtLonMin.getDocument()).setDocumentFilter(new NumericAmountLimitedFilter(59));
        ((AbstractDocument) this.txtLonSec.getDocument()).setDocumentFilter(new NumericAmountLimitedFilter(59));
        ((AbstractDocument) this.txtLevelFt.getDocument()).setDocumentFilter(new NumericFilter(3));
        
        this.model = new ListPointModel(tblListPoint);
	this.model.createLineNumber();
        
        this.parent = parent;
    }
    
    private CustomDrawItem parse() {
        CustomDrawItem item = new CustomDrawItem();
        item.setType(ShapeType.POLYGON);
        item.setName(this.txtName.getText());
        
        if (txtLevelFt.getText() != null && !txtLevelFt.getText().isEmpty()) {
            item.setLevel(Integer.parseInt(txtLevelFt.getText()));
        } else {
            item.setLevel(0);
        }
        
        item.setColor(Convertor.fromColorToHex(btnColorChooser.getBackground()));
        item.setPoints(model.getPoint());
        item.createLabel();
        return item;
    }
    
    private void clearPoint() {
        this.txtLatDeg.setText("");
        this.txtLatMin.setText("");
        this.txtLatSec.setText("");
        this.txtLonDeg.setText("");
        this.txtLonMin.setText("");
        this.txtLonSec.setText("");
    }

    /**
     * @param actionPerform the actionPerform to set
     */
    public void setActionPerform(IDrawActionPerformed actionPerform) {
        this.actionPerform = actionPerform;
    }

    private void clearError() {
        txtName.setBackground(originBackgroundColor);
        txtLatDeg.setBackground(originBackgroundColor);
        txtLatMin.setBackground(originBackgroundColor);
        txtLatSec.setBackground(originBackgroundColor);
        txtLonDeg.setBackground(originBackgroundColor);
        txtLonMin.setBackground(originBackgroundColor);
        txtLonSec.setBackground(originBackgroundColor);
        txtLevelFt.setBackground(originBackgroundColor);
        lblError.setText("");
    }
    
    private void setError(JTextField textfield) {
        textfield.setBackground(errorColor);
        textfield.requestFocus();
    }
    
    private boolean validateForm() {
        boolean result = true;
        if (Validator.isNullOrEmpty(txtName.getText())) {
            setError(txtName);
            result = false;
        }
        
        if (Validator.isNullOrEmpty(txtLevelFt.getText())) {
            setError(txtLevelFt);
            result = false;
        }
        
        if (this.model.getRowCount() < 3) {
            lblError.setText("Polygon must has 3 points at least");
            return false;
        }
        
        return result;
    }
    
    private boolean validatePoint() {
        boolean result = true;
        if (Validator.isNullOrEmpty(txtLatDeg.getText())) {
            setError(txtLatDeg);
            result = false;
        }

        if (Validator.isNullOrEmpty(txtLatMin.getText())) {
            setError(txtLatMin);
            result = false;
        }

        if (Validator.isNullOrEmpty(txtLatSec.getText())) {
            setError(txtLatSec);
            result = false;
        }

        if (Validator.isNullOrEmpty(txtLonDeg.getText())) {
            setError(txtLonDeg);
            result = false;
        }

        if (Validator.isNullOrEmpty(txtLonMin.getText())) {
            setError(txtLonMin);
            result = false;
        }

        if (Validator.isNullOrEmpty(txtLonSec.getText())) {
            setError(txtLonSec);
            result = false;
        }
        return result;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        btnPreview = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        btnDraw = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblListPoint = new javax.swing.JTable();
        jLabel15 = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtLevelFt = new javax.swing.JTextField();
        cmbLevelUnit = new javax.swing.JComboBox<>();
        btnColorChooser = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        txtLatDeg = new javax.swing.JTextField();
        txtLatMin = new javax.swing.JTextField();
        txtLatSec = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtLonDeg = new javax.swing.JTextField();
        txtLonMin = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtLonSec = new javax.swing.JTextField();
        btnAddToList = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        chkLatChar = new javax.swing.JComboBox<>();
        chkLonChar = new javax.swing.JComboBox<>();
        btnMouseCapture = new javax.swing.JToggleButton();
        lblError = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Polygon");
        setAlwaysOnTop(true);
        setMaximumSize(new java.awt.Dimension(410, 410));
        setMinimumSize(new java.awt.Dimension(410, 410));
        setPreferredSize(new java.awt.Dimension(410, 410));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        btnPreview.setText("Preview");
        btnPreview.setToolTipText("PREVIEW");
        btnPreview.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPreviewActionPerformed(evt);
            }
        });

        btnCancel.setText("Cancel");
        btnCancel.setToolTipText("CLOSE");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        btnDraw.setText("OK");
        btnDraw.setToolTipText("DRAW POLYGON");
        btnDraw.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDrawActionPerformed(evt);
            }
        });

        tblListPoint.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
            },
            new String [] {
                "#", "Latitude", "Longitude"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblListPoint.setRowHeight(22);
        jScrollPane1.setViewportView(tblListPoint);

        jLabel15.setText("Name");

        jLabel14.setText("Level");

        txtLevelFt.setToolTipText("Minimum Altitude");

        cmbLevelUnit.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "x100 (ft)", "M" }));
        cmbLevelUnit.setEnabled(false);

        btnColorChooser.setBackground(new java.awt.Color(255, 0, 0));
        btnColorChooser.setForeground(new java.awt.Color(255, 0, 0));
        btnColorChooser.setToolTipText("CLICK TO CHOOSE COLOR");
        btnColorChooser.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnColorChooser.setContentAreaFilled(false);
        btnColorChooser.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnColorChooser.setMaximumSize(new java.awt.Dimension(26, 26));
        btnColorChooser.setMinimumSize(new java.awt.Dimension(26, 26));
        btnColorChooser.setPreferredSize(new java.awt.Dimension(26, 26));
        btnColorChooser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnColorChooserActionPerformed(evt);
            }
        });

        jLabel2.setText("DEG");

        jLabel17.setText("Point");

        jLabel3.setText("MIN");

        jLabel4.setText("SEC");

        jLabel7.setText("DEG");

        jLabel6.setText("MIN");

        jLabel5.setText("SEC");

        btnAddToList.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/adsb/client/images/add.png"))); // NOI18N
        btnAddToList.setToolTipText("Add point to list");
        btnAddToList.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnAddToList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddToListActionPerformed(evt);
            }
        });

        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/adsb/client/images/delete.png"))); // NOI18N
        btnDelete.setToolTipText("Delete selected point");
        btnDelete.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        chkLatChar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "N", "S" }));

        chkLonChar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "W", "E" }));

        btnMouseCapture.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/adsb/client/images/cursor.png"))); // NOI18N
        btnMouseCapture.setToolTipText("Using mouse to grab point");
        btnMouseCapture.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnMouseCapture.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMouseCaptureActionPerformed(evt);
            }
        });

        lblError.setMaximumSize(new java.awt.Dimension(3, 25));
        lblError.setMinimumSize(new java.awt.Dimension(3, 25));
        lblError.setPreferredSize(new java.awt.Dimension(3, 25));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(btnPreview))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15)
                            .addComponent(jLabel14)
                            .addComponent(jLabel17))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel3)
                                .addGap(14, 14, 14)
                                .addComponent(jLabel4)
                                .addGap(73, 73, 73)
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(21, 21, 21)
                                .addComponent(jLabel6)
                                .addGap(13, 13, 13)
                                .addComponent(jLabel5))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnDelete)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnMouseCapture)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnAddToList))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtName)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(btnDraw)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnCancel))
                                .addComponent(lblError, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(txtLevelFt, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cmbLevelUnit, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(txtLatDeg, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtLatMin, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtLatSec, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(chkLatChar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtLonDeg, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtLonMin, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtLonSec, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(chkLonChar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGap(155, 155, 155)
                                        .addComponent(btnColorChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel14)
                    .addComponent(txtLevelFt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbLevelUnit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnColorChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel7)
                    .addComponent(jLabel6)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel17)
                    .addComponent(txtLatDeg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtLatMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtLatSec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chkLatChar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtLonDeg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtLonMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtLonSec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chkLonChar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(btnDelete)
                    .addComponent(btnMouseCapture)
                    .addComponent(btnAddToList))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(lblError, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancel)
                    .addComponent(btnDraw)
                    .addComponent(btnPreview))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        txtLatDeg.getAccessibleContext().setAccessibleName("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnDrawActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDrawActionPerformed
        try {
            logger.info("btnDraw is clicked");
            this.clearError();
            if (!this.validateForm()) return;
            
            
            if (JOptionPane.showConfirmDialog(this, "Do you want to save?", "Confirmation", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) return;
            CustomDrawItem item = this.parse();
            this.actionPerform.actionPerformed(Action.DRAW, item);

            dispose();
        } catch (Exception e) {
             logger.error(e);
        }
//        Main.drawList.loadData();        
    }//GEN-LAST:event_btnDrawActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        try {
            logger.info("btnCancel is clicked");
            dispose();
        } catch (Exception e) {
            logger.error(e);
        }
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnPreviewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreviewActionPerformed
        try {
            logger.info("btnPreview is clicked");
            this.clearError();
            if (this.model.getRowCount() < 3) {
                lblError.setText("Polygon must has 3 points at least");
                return;
            }

            CustomDrawItem item = this.parse();
            this.actionPerform.actionPerformed(Action.PREVIEW, item);
        } catch (Exception e) {
            logger.error(e);
        }
    }//GEN-LAST:event_btnPreviewActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
    }//GEN-LAST:event_formWindowClosed

    private void btnColorChooserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnColorChooserActionPerformed
        try {
            logger.info("btnColorChooser is clicked");
            JButton colorButton = (JButton) evt.getSource();
            Color curretColor = colorButton.getBackground();
            Color selectedColor = JColorChooser.showDialog(this, "Select a color", curretColor);
            if (selectedColor == null) return;
            colorButton.setBackground(selectedColor);
            colorButton.setContentAreaFilled(false);
            colorButton.setOpaque(true);
            this.color = String.format("#%02x%02x%02x", selectedColor.getRed(), selectedColor.getGreen(), selectedColor.getBlue()).toUpperCase();
            
        } catch (Exception ex) {
            logger.error(ex);
        }
    }//GEN-LAST:event_btnColorChooserActionPerformed

    private void btnAddToListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddToListActionPerformed
        try {
            logger.info("btnAddToList is clicked");
            this.clearError();
            if (!validatePoint()) return;
            int latDec = Integer.parseInt(txtLatDeg.getText());
            int latMin = Integer.parseInt(txtLatMin.getText());
            int latSec = Integer.parseInt(txtLatSec.getText());
            String latLocate = chkLatChar.getSelectedItem().toString();
            Wgs84Coordinate latitudeCoord = new Wgs84Coordinate(latDec, latMin, latSec, latLocate);
            float latitude = Convertor.fromWgs84CoordToDecimal(latDec, latMin, latSec, latLocate);

            int lonDec = Integer.parseInt(txtLonDeg.getText());
            int lonMin = Integer.parseInt(txtLonMin.getText());
            int lonSec = Integer.parseInt(txtLonSec.getText());
            String lonLocate = chkLonChar.getSelectedItem().toString();
            Wgs84Coordinate longtitudeCoord = new Wgs84Coordinate(lonDec, lonMin, lonSec, lonLocate);
            float longtitude = Convertor.fromWgs84CoordToDecimal(lonDec, lonMin, lonSec, lonLocate);
            Point2f point = new Point2f(longtitude, latitude);
            point.setLatCoord(latitudeCoord.getLatitude());
            point.setLonCoord(longtitudeCoord.getLongtitude());
            this.model.add(point);
            clearPoint() ;
       } catch (Exception ex) {
            logger.error(ex);
        }
    }//GEN-LAST:event_btnAddToListActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        try {
            logger.info("btnDelete is clicked");
            int[] lstSlected = tblListPoint.getSelectedRows();
            for (int i = lstSlected.length - 1; i >= 0; i--) {
//                CustomPolygonDraw.polygonList.remove(lstSlected[i]);
                this.model.remove(i);
            }
            
//            if (lstSlected.length == CustomPolygonDraw.polygonList.size()) {
//                CustomPolygonDraw.polygonList.clear();
//            } else {
//                for (int i = 0; i < lstSlected.length; i++) {
//                    CustomPolygonDraw.polygonList.remove(lstSlected[i]);
//                }
//            } 
        } catch (Exception ex) {
            logger.error(ex);
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnMouseCaptureActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMouseCaptureActionPerformed
        try {
            logger.info("btnMouseCapture is clicked");
            if (btnMouseCapture.isSelected()) {
                MouseContext.instance().setMode(MouseMode.Capture);
            } else if (MouseContext.instance().getMode() == MouseMode.Capture) {
                MouseContext.instance().setMode(MouseMode.Normal);
            }
        } catch (Exception ex) {
            logger.error(ex);
        }
    }//GEN-LAST:event_btnMouseCaptureActionPerformed

    private void bind(Point2f point) {
        Point2f wgs84Point = Convertor.fromOpenglToWgs84(point);
        Wgs84Coordinate longtitude = Convertor.fromDecimalToWgs84Coord(wgs84Point.getLongtitude());
        txtLonDeg.setText(Integer.toString(longtitude.getDeg()));
        txtLonMin.setText(Integer.toString(longtitude.getMin()));
        txtLonSec.setText(Integer.toString(longtitude.getSec()));
        chkLonChar.setSelectedItem(longtitude.getLontitudeCharacter());

        Wgs84Coordinate latitude = Convertor.fromDecimalToWgs84Coord(wgs84Point.getLatitude());
        txtLatDeg.setText(Integer.toString(latitude.getDeg()));
        txtLatMin.setText(Integer.toString(latitude.getMin()));
        txtLatSec.setText(Integer.toString(latitude.getSec()));
        chkLatChar.setSelectedItem(latitude.getLatitdeCharacter());
    }
    
    @Override
    public void setVisible(boolean value) {
        isShowing = value;
        super.setVisible(value);
    }

    @Override
    public void updateCoordinate(Point2f point) {
        bind(point);
    }

    @Override
    public void captureCoordinate(Point2f point) {
        bind(point);
        MouseContext.instance().setMode(MouseMode.Normal);
        btnMouseCapture.setSelected(false);
    }

    @Override
    public boolean isAvailable() {
        return this.isVisible();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddToList;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnColorChooser;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnDraw;
    private javax.swing.JToggleButton btnMouseCapture;
    private javax.swing.JButton btnPreview;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JComboBox<String> chkLatChar;
    private javax.swing.JComboBox<String> chkLonChar;
    private javax.swing.JComboBox<String> cmbLevelUnit;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblError;
    private javax.swing.JTable tblListPoint;
    private javax.swing.JTextField txtLatDeg;
    private javax.swing.JTextField txtLatMin;
    private javax.swing.JTextField txtLatSec;
    private javax.swing.JTextField txtLevelFt;
    private javax.swing.JTextField txtLonDeg;
    private javax.swing.JTextField txtLonMin;
    private javax.swing.JTextField txtLonSec;
    private javax.swing.JTextField txtName;
    // End of variables declaration//GEN-END:variables
}
