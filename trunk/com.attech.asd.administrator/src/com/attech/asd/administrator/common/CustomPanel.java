/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.administrator.common;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JComponent;

/**
 *
 * @author AnhTH
 */
public class CustomPanel extends JComponent {
    private Image bgImage;
    
    public CustomPanel(Image bgImage) {
        this.bgImage = bgImage;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bgImage, 0, 0, this);
    }
    
    
}
