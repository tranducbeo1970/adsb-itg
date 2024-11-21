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
public class CustomArcDraw extends javax.swing.JDialog implements ICoordinateUsage {


    private static final MLogger logger = MLogger.getLogger(CustomArcDraw.class);
    public static boolean isGetCordMouse = false;
    public static boolean isUseMouse = false;
    public static List<Point2f> polygonList = new ArrayList<>();
    public static boolean isShowing;

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
    public CustomArcDraw(javax.swing.JFrame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        logger.info("Showed");
        originBackgroundColor = txtName.getBackground();
        btnColorChooser.setBackground(Color.red);
        btnColorChooser.setOpaque(true);

         ((AbstractDocument) txtName.getDocument()).setDocumentFilter( new MessageFormatFilter(64));
         
        ((AbstractDocument) txtLatDeg.getDocument()).setDocumentFilter(new NumericAmountLimitedFilter(90));
        ((AbstractDocument) txtLatMin.getDocument()).setDocumentFilter(new NumericAmountLimitedFilter(59));
        ((AbstractDocument) txtLatSec.getDocument()).setDocumentFilter(new NumericAmountLimitedFilter(59));

        ((AbstractDocument) txtLonDeg.getDocument()).setDocumentFilter(new NumericAmountLimitedFilter(180));
        ((AbstractDocument) txtLonMin.getDocument()).setDocumentFilter(new NumericAmountLimitedFilter(59));
        ((AbstractDocument) txtLonSec.getDocument()).setDocumentFilter(new NumericAmountLimitedFilter(59));
        
        ((AbstractDocument) txtRadialFrom.getDocument()).setDocumentFilter(new NumericFilter(3));
        ((AbstractDocument) txtRadialTo.getDocument()).setDocumentFilter(new NumericFilter(3));

        ((AbstractDocument) txtRangeNm.getDocument()).setDocumentFilter(new NumericFilter(3));
        ((AbstractDocument) txtLevelFt.getDocument()).setDocumentFilter(new NumericFilter(3));
        this.parent = parent;
    }
    
    private void clearError() {
        txtName.setBackground(originBackgroundColor);
        txtLatDeg.setBackground(originBackgroundColor);
        txtLatMin.setBackground(originBackgroundColor);
        txtLatSec.setBackground(originBackgroundColor);
        txtLonDeg.setBackground(originBackgroundColor);
        txtLonMin.setBackground(originBackgroundColor);
        txtLonSec.setBackground(originBackgroundColor);
        txtRadialFrom.setBackground(originBackgroundColor);
        txtRadialTo.setBackground(originBackgroundColor);
        txtLevelFt.setBackground(originBackgroundColor);
        txtRangeNm.setBackground(originBackgroundColor);
    }
    
    private void setError(JTextField textfield) {
        textfield.setBackground(errorColor);
        textfield.requestFocus();
    }
    
    private boolean validateData() {
        boolean result = true;
        if (Validator.isNullOrEmpty(txtName.getText())) {
            setError(txtName);
            result = false;
        }
        
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
        
        if (Validator.isNullOrEmpty(txtRadialFrom.getText())) {
            setError(txtRadialFrom);
            result = false;
        }
        
        if (Validator.isNullOrEmpty(txtRadialTo.getText())) {
            setError(txtRadialTo);
            result = false;
        }

        if (Validator.isNullOrEmpty(txtLevelFt.getText())) {
            setError(txtLevelFt);
            result = false;
        }

        if (Validator.isNullOrEmpty(txtRangeNm.getText())) {
            setError(txtRangeNm);
            result = false;
        }
        
        if (!result) return false;
        
        int radialTo = Integer.parseInt(txtRadialTo.getText());
        int radialFrom = Integer.parseInt(txtRadialFrom.getText());
        if (radialTo < radialFrom) {
            setError(txtRadialTo);
            return false;
        }
        
        return true;
    }
    
    private CustomDrawItem parse() {
        CustomDrawItem item = new CustomDrawItem();
        item.setType(ShapeType.ARC);
        item.setName(txtName.getText());
        item.setAngleStart(Integer.parseInt(txtRadialFrom.getText()));
        item.setAngleEnd(Integer.parseInt(txtRadialTo.getText()));

        int latDec = Integer.parseInt(txtLatDeg.getText());
        int latMin = Integer.parseInt(txtLatMin.getText());
        int latSec = Integer.parseInt(txtLatSec.getText());
        String latLocate = chkLatChar.getSelectedItem().toString();
        int lonDec = Integer.parseInt(txtLonDeg.getText());
        int lonMin = Integer.parseInt(txtLonMin.getText());
        int lonSec = Integer.parseInt(txtLonSec.getText());
        String lonLocate = chkLonChar.getSelectedItem().toString();
        float x = Convertor.fromWgs84CoordToDecimal(lonDec, lonMin, lonSec, lonLocate);
        float y = Convertor.fromWgs84CoordToDecimal(latDec, latMin, latSec, latLocate);

        Point2f centorPoint2f = new Point2f(x, y);
        item.setCenterPoint(centorPoint2f);
        item.setColor(Convertor.fromColorToHex(btnColorChooser.getBackground()));
        item.setEnabled(true);
        item.setLevel(Integer.parseInt(txtLevelFt.getText()));
        item.setRadius(Integer.parseInt(txtRangeNm.getText()));
        item.createLabel();
//        item.set
        return item;
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
        jLabel15 = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        btnColorChooser = new javax.swing.JButton();
        txtLonDeg = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtLonSec = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtLatDeg = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtLatSec = new javax.swing.JTextField();
        txtLatMin = new javax.swing.JTextField();
        txtLonMin = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtRadialFrom = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtRadialTo = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        cmbRangeUnit = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        txtLevelFt = new javax.swing.JTextField();
        cmbLevelUnit = new javax.swing.JComboBox<>();
        txtRangeNm = new javax.swing.JTextField();
        btnMouseCapture = new javax.swing.JToggleButton();
        chkLatChar = new javax.swing.JComboBox<>();
        chkLonChar = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Circle");
        setAlwaysOnTop(true);
        setMaximumSize(new java.awt.Dimension(445, 242));
        setMinimumSize(new java.awt.Dimension(445, 242));
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
        btnDraw.setToolTipText("DRAW CIRCLE");
        btnDraw.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDrawActionPerformed(evt);
            }
        });

        jLabel15.setText("Name");

        btnColorChooser.setBackground(new java.awt.Color(255, 0, 0));
        btnColorChooser.setForeground(new java.awt.Color(255, 0, 0));
        btnColorChooser.setToolTipText("");
        btnColorChooser.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnColorChooser.setContentAreaFilled(false);
        btnColorChooser.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnColorChooser.setMaximumSize(new java.awt.Dimension(26, 26));
        btnColorChooser.setMinimumSize(new java.awt.Dimension(26, 26));
        btnColorChooser.setOpaque(true);
        btnColorChooser.setPreferredSize(new java.awt.Dimension(26, 26));
        btnColorChooser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnColorChooserActionPerformed(evt);
            }
        });

        jLabel7.setText("DEG");

        jLabel5.setText("SEC");

        jLabel6.setText("MIN");

        jLabel4.setText("SEC");

        jLabel3.setText("MIN");

        jLabel2.setText("DEG");

        jLabel17.setText("Center");

        jLabel11.setText("From");

        jLabel10.setText("Radial");

        jLabel12.setText("To");

        txtRadialTo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRadialToActionPerformed(evt);
            }
        });

        jLabel13.setText("Range");

        cmbRangeUnit.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "NM", "Km" }));
        cmbRangeUnit.setEnabled(false);

        jLabel14.setText("Level");

        txtLevelFt.setToolTipText("Minimum Altitude");

        cmbLevelUnit.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "x100 (ft)", "M" }));
        cmbLevelUnit.setEnabled(false);
        cmbLevelUnit.setMinimumSize(new java.awt.Dimension(56, 22));
        cmbLevelUnit.setPreferredSize(new java.awt.Dimension(56, 22));

        btnMouseCapture.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/adsb/client/images/cursor.png"))); // NOI18N
        btnMouseCapture.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnMouseCapture.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMouseCaptureActionPerformed(evt);
            }
        });

        chkLatChar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "N", "S" }));

        chkLonChar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "W", "E" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel10))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15)
                            .addComponent(jLabel17)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel13)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel11)
                                    .addComponent(txtRadialFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel12)
                                    .addComponent(txtRadialTo, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtRangeNm, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmbRangeUnit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtLevelFt, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmbLevelUnit, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnDraw, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnCancel))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtLatDeg, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel6)
                                            .addComponent(txtLatMin, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel5)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(txtLatSec, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(chkLatChar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(15, 15, 15)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtLonDeg, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtLonMin, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel3))
                                        .addGap(5, 5, 5)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel4)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(txtLonSec, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(chkLonChar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addComponent(txtName))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnColorChooser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnMouseCapture))))
                        .addGap(5, 5, 5))))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnPreview)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel15)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnColorChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel6)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel5)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)))
                    .addComponent(jLabel7))
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
                    .addComponent(chkLonChar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMouseCapture))
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel12)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txtRadialTo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtRadialFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel13)
                    .addComponent(txtRangeNm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbRangeUnit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(txtLevelFt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbLevelUnit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(btnCancel)
                    .addComponent(btnDraw)
                    .addComponent(btnPreview))
                .addContainerGap(8, Short.MAX_VALUE))
        );

        txtLatDeg.getAccessibleContext().setAccessibleName("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnDrawActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDrawActionPerformed
        try {
            logger.info("btnDraw is clicked");
            this.clearError();
            if (!this.validateData()) {
                return;
            }

            int confirmed = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to save?",
                    "Confirmation",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (confirmed != JOptionPane.YES_OPTION) {
                return;
            }
            
            CustomDrawItem item = this.parse();
            this.actionPerform.actionPerformed(Action.DRAW, item);
            
            dispose();
        } catch (Exception e) { 
            JOptionPane.showMessageDialog(this, e);
        }
    }//GEN-LAST:event_btnDrawActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        try {
            logger.info("btnCancel is clicked");
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnPreviewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreviewActionPerformed
        try {
            logger.info("btnPreview is clicked");
            this.clearError();
            if (!this.validateData()) {
                return;
            }

            CustomDrawItem item = this.parse();
            this.actionPerform.actionPerformed(Action.PREVIEW, item);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }//GEN-LAST:event_btnPreviewActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
//        isShowing = false;
//        isUseMouse = false;
//        CustomArcDraw.polygonList.clear();
//        Main.clearPreviewDrawTool();
//        Main.enableDrawList();
    }//GEN-LAST:event_formWindowClosed

    private void txtRadialToActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRadialToActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRadialToActionPerformed

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
    
    /**
     * @return the actionPerform
     */
    public IDrawActionPerformed getActionPerform() {
        return actionPerform;
    }

    /**
     * @param actionPerform the actionPerform to set
     */
    public void setActionPerform(IDrawActionPerformed actionPerform) {
        this.actionPerform = actionPerform;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnColorChooser;
    private javax.swing.JButton btnDraw;
    private javax.swing.JToggleButton btnMouseCapture;
    private javax.swing.JButton btnPreview;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JComboBox<String> chkLatChar;
    private javax.swing.JComboBox<String> chkLonChar;
    private javax.swing.JComboBox<String> cmbLevelUnit;
    private javax.swing.JComboBox<String> cmbRangeUnit;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    public static javax.swing.JTextField txtLatDeg;
    public static javax.swing.JTextField txtLatMin;
    public static javax.swing.JTextField txtLatSec;
    private javax.swing.JTextField txtLevelFt;
    public static javax.swing.JTextField txtLonDeg;
    public static javax.swing.JTextField txtLonMin;
    public static javax.swing.JTextField txtLonSec;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtRadialFrom;
    private javax.swing.JTextField txtRadialTo;
    private javax.swing.JTextField txtRangeNm;
    // End of variables declaration//GEN-END:variables
}
