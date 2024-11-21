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
 * @author ANDH
 */
public class MessageFormatFilter extends DocumentFilter {
    private int maxLength;
    private String characters = " -?:().,'=/+\r\n";

    public MessageFormatFilter() {
        maxLength = 0;
    }

    public MessageFormatFilter(int limit) {
        if (limit < 0) this.maxLength = 0;
        this.maxLength = limit;
    }
    
    @Override
    public void replace(FilterBypass fb, int i, int i1, String string, AttributeSet as) throws BadLocationException {
        int selected = i1;
        int length = 0;
        
        if (selected > 0) {
            super.replace(fb, i, selected, "", as);
        }
        
        int position = 0;
        for (int n = 0; n < string.length(); n++) {
            length = fb.getDocument().getLength();
            
            char c = string.charAt(n);
            if ((c != ' ' && characters.indexOf(c) < 0 && !Character.isLetterOrDigit(c)) || c > 172) {
                continue;
            }
            if (maxLength > 0 && (length + 1 - selected) > maxLength) {
                break;
            }
            if (Character.isLowerCase(c)) c = Character.toUpperCase(c);
            
            super.insertString(fb, i + position, String.valueOf(c), as);
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


    /**
     * @return the allowanceCharacter
     */
    public String getCharacter() {
        return characters;
    }

    /**
     * @param allowanceCharacter the allowanceCharacter to set
     */
    public void setCharacter(String allowanceCharacter) {
        this.characters = allowanceCharacter;
    }

    /**
     * @return the maxLength
     */
    public int getMaxLength() {
        return maxLength;
    }

    /**
     * @param maxLength the maxLength to set
     */
    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }
}
