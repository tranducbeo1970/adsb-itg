/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.gui.customDraw;

import com.attech.adsb.client.common.IActionPerformed;
import com.attech.adsb.client.common.MLogger;
import com.attech.adsb.client.config.CustomDrawItem;
import com.attech.adsb.client.config.Point2f;
import com.attech.adsb.client.graphic.objects.CustomDrawGraphic;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;

public class CustomDrawDlg extends javax.swing.JDialog implements ICoordinateUsage {

    private static final MLogger logger = MLogger.getLogger(CustomDrawDlg.class);
    private final DrawListModel model;
    private final CustomDrawGraphic graphic;
    private ICoordinateUsage coordinaterUser;
    private IActionPerformed actionPerformed;

    public CustomDrawDlg(javax.swing.JFrame parent, boolean modal, CustomDrawGraphic graphic) {
        super(parent, modal);
        logger.info("Custom Drawing initialize");
        initComponents();
        this.graphic = graphic;
        this.model = new DrawListModel(tblDrawList);
        this.model.createLineNumber();
        this.tblDrawList.getSelectionModel().addListSelectionListener(this::tblListSelectionChangedEventHandler);
        this.btnDelete.setEnabled(false);
    }

    private void loadData() {
        List<CustomDrawItem> items = this.graphic.getDrawList();
        for (CustomDrawItem item : items) {
            model.add(item);
        }
    }
    
    private void tblListSelectionChangedEventHandler(ListSelectionEvent e) {
        try {
            final int selectedIndex = tblDrawList.getSelectedRow();
            if (selectedIndex < 0) {
                btnDelete.setEnabled(false);
                return;
            }

            btnDelete.setEnabled(true);
        } catch (Exception ex) {
            logger.error(ex);
        }
    }
    
    @Override
    public void updateCoordinate(Point2f point) {
        try {
            if (coordinaterUser == null || !coordinaterUser.isAvailable()) return;
            coordinaterUser.updateCoordinate(point);
        } catch (Exception ex) {
            logger.error(ex);
        }
    }

    @Override
    public void captureCoordinate(Point2f point) {
        try {
            if (coordinaterUser == null || !coordinaterUser.isAvailable()) return;
            coordinaterUser.captureCoordinate(point);
            logger.info("Capture coordinate x:%s y: %s", point.getX(), point.getY());
        } catch (Exception ex) {
            logger.error(ex);
        }
    }

    @Override
    public boolean isAvailable() {
        return this.isVisible();
    }

    public void actionPerformed(Action action, CustomDrawItem item) {
        switch (action) {
            case DRAW:
                this.graphic.add(item);
                model.add(item);
                logger.info("Added item %s", item);
                break;

            case PREVIEW:
                this.graphic.preview(item);
                logger.info("Previewed item %s", item);
                break;
        }
    }

    public void setActionPerformed(IActionPerformed actionPerformed) {
        this.actionPerformed = actionPerformed;
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblDrawList = new javax.swing.JTable();
        jToolBar2 = new javax.swing.JToolBar();
        btnDrawCircle = new javax.swing.JButton();
        btnDrawPolygon = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Draw");
        setResizable(false);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        tblDrawList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#", "NAME", "TYPE", "ENABLE"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDrawList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDrawListMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblDrawList);

        jToolBar2.setFloatable(false);
        jToolBar2.setRollover(true);

        btnDrawCircle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/adsb/client/images/circled.png"))); // NOI18N
        btnDrawCircle.setToolTipText("DRAW CIRCLE");
        btnDrawCircle.setMaximumSize(new java.awt.Dimension(28, 28));
        btnDrawCircle.setMinimumSize(new java.awt.Dimension(28, 28));
        btnDrawCircle.setPreferredSize(new java.awt.Dimension(28, 28));
        btnDrawCircle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDrawCircleActionPerformed(evt);
            }
        });
        jToolBar2.add(btnDrawCircle);

        btnDrawPolygon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/adsb/client/images/polygon.png"))); // NOI18N
        btnDrawPolygon.setToolTipText("DRAW POLYGON");
        btnDrawPolygon.setMaximumSize(new java.awt.Dimension(28, 28));
        btnDrawPolygon.setMinimumSize(new java.awt.Dimension(28, 28));
        btnDrawPolygon.setPreferredSize(new java.awt.Dimension(28, 28));
        btnDrawPolygon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDrawPolygonActionPerformed(evt);
            }
        });
        jToolBar2.add(btnDrawPolygon);

        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/adsb/client/images/delete.png"))); // NOI18N
        btnDelete.setToolTipText("DELETE");
        btnDelete.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        btnDelete.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnDelete.setMaximumSize(new java.awt.Dimension(28, 28));
        btnDelete.setMinimumSize(new java.awt.Dimension(28, 28));
        btnDelete.setPreferredSize(new java.awt.Dimension(28, 28));
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        jToolBar2.add(btnDelete);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jToolBar2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 381, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 292, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        try {
            
            int input = JOptionPane.showConfirmDialog(this, "Do you want to delete?", "Confirmation", JOptionPane.YES_NO_OPTION);
            if (input != JOptionPane.YES_OPTION) return;
            
            int[] lstSlected = tblDrawList.getSelectedRows();
            for (int i = lstSlected.length - 1; i >= 0; i--) {
                int selectedIndex = lstSlected[i];
                CustomDrawItem item = (CustomDrawItem) model.getValueAt(selectedIndex, 0);
                this.graphic.remove(item);
                model.removeRow(selectedIndex);
                logger.info("Deleted item %s", item);
            }
        } catch (Exception ex) {
            logger.error(ex);
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnDrawCircleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDrawCircleActionPerformed
        try {
            if (coordinaterUser != null && coordinaterUser.isAvailable()) {
                return;
            }
            coordinaterUser = new CustomArcDraw(null, false);
            ((CustomArcDraw) coordinaterUser).setActionPerform(this::actionPerformed);
            ((CustomArcDraw) coordinaterUser).addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent evt) {
                    graphic.turnOffPreview();
                }
            });
            ((CustomArcDraw) coordinaterUser).setAlwaysOnTop(true);
            ((CustomArcDraw) coordinaterUser).setVisible(true);
            logger.info("Button [Draw Circle] is clicked");
        } catch (Exception ex) {
            logger.error(ex);
        }
    }//GEN-LAST:event_btnDrawCircleActionPerformed

    private void tblDrawListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDrawListMouseClicked
        try {
            int selectedIndex = tblDrawList.getSelectedRow();
            boolean enable = (boolean) model.getValueAt(selectedIndex, 3);
            CustomDrawItem item = (CustomDrawItem) model.getValueAt(selectedIndex, 0);
            item.setEnabled(enable);
            this.graphic.save();
            logger.info("Set item %s display to %s", item, enable);
        } catch (Exception ex) {
            logger.error(ex);
        }
    }//GEN-LAST:event_tblDrawListMouseClicked

    private void btnDrawPolygonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDrawPolygonActionPerformed
        try {
            if (coordinaterUser != null && coordinaterUser.isAvailable()) {
                return;
            }
            coordinaterUser = new CustomPolygonDraw(null, false);
            ((CustomPolygonDraw) coordinaterUser).setActionPerform(this::actionPerformed);
            ((CustomPolygonDraw) coordinaterUser).addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent evt) {
                    graphic.turnOffPreview();
                }
            });
            ((CustomPolygonDraw) coordinaterUser).setAlwaysOnTop(true);
            ((CustomPolygonDraw) coordinaterUser).setVisible(true);
            logger.info("Button [Draw Polygon] is clicked");

        } catch (Exception ex) {
            logger.error(ex);
        }
    }//GEN-LAST:event_btnDrawPolygonActionPerformed

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        try {
            loadData();
        } catch (Exception ex) {
            logger.error(ex);
        }
    }//GEN-LAST:event_formComponentShown

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnDrawCircle;
    private javax.swing.JButton btnDrawPolygon;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JTable tblDrawList;
    // End of variables declaration//GEN-END:variables

    
}
