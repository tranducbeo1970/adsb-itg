/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.cat62.v112;

/**
 *
 * @author andh
 */
public class Mode5Summary {
    private boolean mode5Interrogation;
    private boolean authenticatedMode5IDReply;
    private boolean authenticatedMode5DataReply;
    private boolean mode1CodeFromMode5Reply;
    private boolean mode2COdeFromMode5Reply;
    private boolean mode3CodeFromMode5Reply;
    private boolean modeCCodeFromMode5Reply;
    private boolean xPulse;
    
    public static int extract(byte[] bytes, int index, Mode5Summary code) {
        if (!Byter.validateIndex(index, bytes.length, 1)) return -1;
        byte cbyte = bytes[index++];
        code.setMode5Interrogation((cbyte & 0x80) > 0);
        code.setAuthenticatedMode5IDReply((cbyte & 0x40) > 0);
        code.setAuthenticatedMode5DataReply((cbyte & 0x20) > 0);
        code.setMode1CodeFromMode5Reply((cbyte & 0x10) > 0);
        code.setMode2COdeFromMode5Reply((cbyte & 0x08) > 0);
        code.setMode3CodeFromMode5Reply((cbyte & 0x04) > 0);
        code.setModeCCodeFromMode5Reply((cbyte & 0x02) > 0);
        code.setxPulse((cbyte & 0x01) > 0);
        return 1;
    }

    /**
     * @return the mode5Interrogation
     */
    public boolean isMode5Interrogation() {
        return mode5Interrogation;
    }

    /**
     * @param mode5Interrogation the mode5Interrogation to set
     */
    public void setMode5Interrogation(boolean mode5Interrogation) {
        this.mode5Interrogation = mode5Interrogation;
    }

    /**
     * @return the authenticatedMode5IDReply
     */
    public boolean isAuthenticatedMode5IDReply() {
        return authenticatedMode5IDReply;
    }

    /**
     * @param authenticatedMode5IDReply the authenticatedMode5IDReply to set
     */
    public void setAuthenticatedMode5IDReply(boolean authenticatedMode5IDReply) {
        this.authenticatedMode5IDReply = authenticatedMode5IDReply;
    }

    /**
     * @return the authenticatedMode5DataReply
     */
    public boolean isAuthenticatedMode5DataReply() {
        return authenticatedMode5DataReply;
    }

    /**
     * @param authenticatedMode5DataReply the authenticatedMode5DataReply to set
     */
    public void setAuthenticatedMode5DataReply(boolean authenticatedMode5DataReply) {
        this.authenticatedMode5DataReply = authenticatedMode5DataReply;
    }

    /**
     * @return the mode1CodeFromMode5Reply
     */
    public boolean isMode1CodeFromMode5Reply() {
        return mode1CodeFromMode5Reply;
    }

    /**
     * @param mode1CodeFromMode5Reply the mode1CodeFromMode5Reply to set
     */
    public void setMode1CodeFromMode5Reply(boolean mode1CodeFromMode5Reply) {
        this.mode1CodeFromMode5Reply = mode1CodeFromMode5Reply;
    }

    /**
     * @return the mode2COdeFromMode5Reply
     */
    public boolean isMode2COdeFromMode5Reply() {
        return mode2COdeFromMode5Reply;
    }

    /**
     * @param mode2COdeFromMode5Reply the mode2COdeFromMode5Reply to set
     */
    public void setMode2COdeFromMode5Reply(boolean mode2COdeFromMode5Reply) {
        this.mode2COdeFromMode5Reply = mode2COdeFromMode5Reply;
    }

    /**
     * @return the mode3CodeFromMode5Reply
     */
    public boolean isMode3CodeFromMode5Reply() {
        return mode3CodeFromMode5Reply;
    }

    /**
     * @param mode3CodeFromMode5Reply the mode3CodeFromMode5Reply to set
     */
    public void setMode3CodeFromMode5Reply(boolean mode3CodeFromMode5Reply) {
        this.mode3CodeFromMode5Reply = mode3CodeFromMode5Reply;
    }

    /**
     * @return the modeCCodeFromMode5Reply
     */
    public boolean isModeCCodeFromMode5Reply() {
        return modeCCodeFromMode5Reply;
    }

    /**
     * @param modeCCodeFromMode5Reply the modeCCodeFromMode5Reply to set
     */
    public void setModeCCodeFromMode5Reply(boolean modeCCodeFromMode5Reply) {
        this.modeCCodeFromMode5Reply = modeCCodeFromMode5Reply;
    }

    /**
     * @return the xPulse
     */
    public boolean isxPulse() {
        return xPulse;
    }

    /**
     * @param xPulse the xPulse to set
     */
    public void setxPulse(boolean xPulse) {
        this.xPulse = xPulse;
    }
    
}
