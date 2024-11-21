/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.administrator;

import com.attech.asd.administrator.common.CustomPanel;
import com.attech.asd.administrator.common.Utils;
import com.attech.asd.database.common.DBException;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import org.apache.log4j.Logger;

/**
 *
 * @author AnhTH
 */
public class Login extends javax.swing.JFrame {

    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnLogin;
    private CustomPanel jPanel1;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblNotice;
    private javax.swing.JPasswordField txtPassword;

    private static final Logger logger = Logger.getLogger(Login.class);
    int attempLogin = 0;

    /**
     * Creates new form NewLogin
     */
    public Login() {
        this.setUndecorated(true);
        initComponents();
        this.setIconImage(new ImageIcon("images/logo.png").getImage());
        this.setTitle(String.format("%s v%s", AppContext.getInstance().assemblyInfo.getProduct(), AppContext.getInstance().assemblyInfo.getVersion()));
        init();

        this.getRootPane().setDefaultButton(btnLogin);
        setResizable(false);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        txtPassword.requestFocus();
        this.lblNotice.setText("");
    }

    private void init() {
        try {
            BufferedImage myImage = ImageIO.read(new File("images/login-bg.jpg"));
            jPanel1 = new CustomPanel(myImage);
            lblLogo = new javax.swing.JLabel();
            txtPassword = new javax.swing.JPasswordField();
            btnLogin = new javax.swing.JButton();
            lblNotice = new javax.swing.JLabel();
            btnExit = new javax.swing.JButton();

            setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
            setTitle("Aviation Surveillance Database v1.0");

            lblLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/logo-attech.png"))); // NOI18N

            txtPassword.setToolTipText("Enter valid password");
            txtPassword.setPreferredSize(new java.awt.Dimension(6, 25));

            btnLogin.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
            btnLogin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/lock_16.png"))); // NOI18N
            btnLogin.setText("AUTHENTICATE");
            btnLogin.setPreferredSize(new java.awt.Dimension(70, 25));
            btnLogin.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    btnLoginActionPerformed(evt);
                }
            });

            lblNotice.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
            lblNotice.setForeground(new java.awt.Color(255, 51, 51));
            lblNotice.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            lblNotice.setText("Incorrect password");

            btnExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/delete1.png"))); // NOI18N
            btnExit.setBorder(null);
            btnExit.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    btnExitActionPerformed(evt);
                }
            });

            javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
            jPanel1.setLayout(jPanel1Layout);
            jPanel1Layout.setHorizontalGroup(
                    jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addContainerGap()
                                    .addComponent(lblNotice, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGap(64, 64, 64)
                                    .addComponent(lblLogo)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnExit))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addGap(26, 26, 26)
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addContainerGap(26, Short.MAX_VALUE)))
            );
            jPanel1Layout.setVerticalGroup(
                    jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblLogo)
                                            .addComponent(btnExit))
                                    .addGap(113, 113, 113)
                                    .addComponent(lblNotice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addContainerGap())
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addGap(71, 71, 71)
                                            .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addContainerGap(71, Short.MAX_VALUE)))
            );

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
            getContentPane().setLayout(layout);
            layout.setHorizontalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            );
            layout.setVerticalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            );
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {
        attempLogin++;
        String authenticationCode = AppContext.getInstance().getParamValue("AuthenticationCode");
        String pass = Utils.digest(String.copyValueOf(txtPassword.getPassword()));
        if (authenticationCode.equals(pass)) {
            Main main;
            try {
                main = new Main();
                main.setVisible(true);
            } catch (DBException ex) {
                logger.error(ex);
            }
            this.dispose();
        }
        if (attempLogin > 4) {
            JOptionPane.showConfirmDialog(rootPane, "Shutdown Application ", "Login Failed 5/5 time", JOptionPane.CLOSED_OPTION);
            System.exit(0);
        } else {
            lblNotice.setText("Incorrect password. (" + attempLogin + "/5)");
            txtPassword.setText("");
        }

    }

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {
        System.exit(0);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("ADSB Administrator Terminal 1.0.0");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 234, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 198, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
