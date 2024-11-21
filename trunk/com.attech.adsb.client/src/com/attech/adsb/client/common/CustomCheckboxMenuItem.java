/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.common;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.MenuSelectionManager;
import javax.swing.plaf.basic.BasicCheckBoxMenuItemUI;

/**
 *
 * @author andh
 */
public class CustomCheckboxMenuItem extends JCheckBoxMenuItem {

    public CustomCheckboxMenuItem() {
    }
    
    public CustomCheckboxMenuItem(String name) {
        super(name);
    }
    
    @Override
    public void updateUI() {
        super.updateUI();
        setUI(new BasicCheckBoxMenuItemUI() {
            @Override
            protected void doClick(MenuSelectionManager msm) {
                menuItem.doClick(0);
            }
        });
    }
}
