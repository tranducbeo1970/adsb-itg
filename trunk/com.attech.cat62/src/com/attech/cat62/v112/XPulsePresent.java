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
public class XPulsePresent {
    private boolean mode5Reply;
    private boolean modeCReply;
    private boolean mode3AReply;
    private boolean mode2Reply;
    private boolean mode1Reply;

    public static int extract(byte[] bytes, int index, XPulsePresent code) {
        if (!Byter.validateIndex(index, bytes.length, 1)) return -1;
        byte cbyte = bytes[index++];
        code.setMode5Reply((cbyte & 0x10) > 0);
        code.setModeCReply((cbyte & 0x08) > 0);
        code.setMode3AReply((cbyte & 0x04) > 0);
        code.setMode2Reply((cbyte & 0x02) > 0);
        code.setMode1Reply((cbyte & 0x01) > 0);
        return 1;
    }
    
    /**
     * @return the mode5Reply
     */
    public boolean isMode5Reply() {
        return mode5Reply;
    }

    /**
     * @param mode5Reply the mode5Reply to set
     */
    public void setMode5Reply(boolean mode5Reply) {
        this.mode5Reply = mode5Reply;
    }

    /**
     * @return the modeCReply
     */
    public boolean isModeCReply() {
        return modeCReply;
    }

    /**
     * @param modeCReply the modeCReply to set
     */
    public void setModeCReply(boolean modeCReply) {
        this.modeCReply = modeCReply;
    }

    /**
     * @return the mode3AReply
     */
    public boolean isMode3AReply() {
        return mode3AReply;
    }

    /**
     * @param mode3AReply the mode3AReply to set
     */
    public void setMode3AReply(boolean mode3AReply) {
        this.mode3AReply = mode3AReply;
    }

    /**
     * @return the mode2Reply
     */
    public boolean isMode2Reply() {
        return mode2Reply;
    }

    /**
     * @param mode2Reply the mode2Reply to set
     */
    public void setMode2Reply(boolean mode2Reply) {
        this.mode2Reply = mode2Reply;
    }

    /**
     * @return the mode1Reply
     */
    public boolean isMode1Reply() {
        return mode1Reply;
    }

    /**
     * @param mode1Reply the mode1Reply to set
     */
    public void setMode1Reply(boolean mode1Reply) {
        this.mode1Reply = mode1Reply;
    }
    
}
