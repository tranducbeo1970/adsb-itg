/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.gui.filter;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 *
 * @author ANDH
 */
public class DocumentInputFilter extends DocumentFilter{
    @Override
        public void insertString(DocumentFilter.FilterBypass fb, int offset, String text, AttributeSet as) throws BadLocationException {
            int len = text.length();
            if (len > 0) {
                /*
                 * Here you can place your other checks that you need to perform
                 * and do add the same checks for replace method as well.
                 */
                if (Character.isDigit(text.charAt(len - 1))) {
                    super.insertString(fb, offset, text, as);
                }

            }
        }

        @Override
        public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet as) throws BadLocationException {
            int len = text.length();
            if (len > 0) {
                if (Character.isDigit(text.charAt(len - 1))) {
                    super.replace(fb, offset, length, text, as);
                }

            }
        }
}
