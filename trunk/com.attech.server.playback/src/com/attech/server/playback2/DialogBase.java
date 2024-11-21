/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DialogBase.java
 *
 * Created on Jun 19, 2012, 8:41:06 AM
 */
package com.attech.server.playback2;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;

/**
 *
 * @author Andh
 */
public class DialogBase extends javax.swing.JDialog {
    
    private DialogResult result;
   
    /** Creates new form DialogBase */
    public DialogBase(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void showDialog() {
        int x;
        int y;

        // Find out our parent 
        Container myParent = getParent();
        Point topLeft = myParent.getLocationOnScreen();
        Dimension parentSize = myParent.getSize();

        Dimension mySize = getSize();

        if (parentSize.width > mySize.width) {
            x = ((parentSize.width - mySize.width) / 2) + topLeft.x;
        } else {
            x = topLeft.x;
        }

        if (parentSize.height > mySize.height) {
            y = ((parentSize.height - mySize.height) / 2) + topLeft.y;
        } else {
            y = topLeft.y;
        }

        setLocation(x, y);
        this.setVisible(true);
        requestFocus();
    }
    
    public void showDialog(Container component) {
        int x;
        int y;

        // Find out our parent 
        Point topLeft = component.getLocationOnScreen();
        Dimension parentSize = component.getSize();

        Dimension mySize = getSize();

        if (parentSize.width > mySize.width) {
            x = ((parentSize.width - mySize.width) / 2) + topLeft.x;
        } else {
            x = topLeft.x;
        }

        if (parentSize.height > mySize.height) {
            y = ((parentSize.height - mySize.height) / 2) + topLeft.y;
        } else {
            y = topLeft.y;
        }

        setLocation(x, y);
        this.setVisible(true);
        requestFocus();
    }
    
    public DialogResult getResult() { return result; }
    protected void setResult(DialogResult result) { this.result = result; }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    
}
