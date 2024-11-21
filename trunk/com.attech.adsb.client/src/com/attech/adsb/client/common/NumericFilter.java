/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.common;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 *
 * @author andh
 */
public class NumericFilter extends DocumentFilter {

    private int limit;
//    private int limitAmout;
    
    public NumericFilter() {
        limit = 0;
    }
    
    public NumericFilter(int limit) {
        if (limit < 0) this.limit = 0;
        this.limit = limit;
    }
    
    @Override
    public void replace(FilterBypass fb, int i, int i1, String string, AttributeSet as) throws BadLocationException {
        if (string == null || string.isEmpty()) {
            super.replace(fb, 0, fb.getDocument().getLength(), "", as);
        }
	
	int selected = i1;
        int length = 0;
        int position = 0;
	
        for (int n = 0; n < string.length(); n++) {
            length = fb.getDocument().getLength();
            char c = string.charAt(n);
            if (!Character.isDigit(c) || c > 172) continue;
            if (limit > 0 && (length + 1 - selected) > limit) break;
            if (Character.isLowerCase(c)) c = Character.toUpperCase(c);
            super.replace(fb, i + position, selected, String.valueOf(c), as);
            selected = 0;
            position++;
        }
    }

    @Override
    public void remove(FilterBypass fb, int i, int i1) throws BadLocationException {
        super.remove(fb, i, i1);
    }

    @Override
    public void insertString(FilterBypass fb, int i, String string, AttributeSet as) throws BadLocationException {
        super.insertString(fb, i, string, as);
    }
}
