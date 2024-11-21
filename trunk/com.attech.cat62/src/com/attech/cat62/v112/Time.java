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
public class Time {
    private int repetitionFactor;
    private int type;
    private int day;
    private int hour;
    private int minute;
    private boolean secondAvailable;
    private int second;

    
    public static int decode(byte [] bytes, int index, Time code){
        if (!Byter.validateIndex(index, bytes.length, 5)) return -1;
        code.setRepetitionFactor(bytes[index++] & 0xFF);
        byte cbyte = bytes[index++];
        code.setType(cbyte >> 3 & 0x1F);
        code.setDay(cbyte >> 1 & 0x03);
        code.setHour(bytes[index++] & 0x1F);
        code.setMinute(bytes[index++] & 0x3F);
        cbyte = bytes[index++];
        code.setSecondAvailable((cbyte & 0x80) > 0);
        code.setSecond(cbyte & 0x3F);
        return 5;
    }
    
    public byte[] encode() {
        byte b1 = (byte) ((byte) repetitionFactor & 0xFF);
        byte b2 = (byte) ((type & 0x1F) << 3 | (day & 0x03 << 1));
        byte b3 = (byte) ((byte) hour & 0x1F);
        byte b4 = (byte) (minute & 0x3F);
        byte b5 = (byte) ((byte) (secondAvailable ? 0x80 : 0x00) | (second & 0x3F));

        return new byte[]{b1, b2, b3, b4, b5};
    }
    
    
    /**
     * @return the repetitionFactor
     */
    public int getRepetitionFactor() {
        return repetitionFactor;
    }

    /**
     * @param repetitionFactor the repetitionFactor to set
     */
    public void setRepetitionFactor(int repetitionFactor) {
        this.repetitionFactor = repetitionFactor;
    }

    /**
     * @return the type <br/>
     * 0 Scheduled off-block time <br/>
     * 1 Estimated off-block time <br/>
     * 2 Estimated take-off time <br/>
     * 3 Actual off-block time <br/>
     * 4 Predicted time at runway hold <br/>
     * 5 Actual time at runway hold <br/>
     * 6 Actual line-up time <br/>
     * 7 Actual take-off time <br/>
     * 8 Estimated time of arrival <br/>
     * 9 Predicted landing time <br/>
     * 10 Actual landing time <br/>
     * 11 Actual time off runway <br/>
     * 12 Predicted time to gate <br/>
     * 13 Actual on-block time
     */
    public int getType() {
        return type;
    }

    /**
     * @param type <br/>
     * 0 Scheduled off-block time <br/>
     * 1 Estimated off-block time <br/>
     * 2 Estimated take-off time <br/>
     * 3 Actual off-block time <br/>
     * 4 Predicted time at runway hold <br/>
     * 5 Actual time at runway hold <br/>
     * 6 Actual line-up time <br/>
     * 7 Actual take-off time <br/>
     * 8 Estimated time of arrival <br/>
     * 9 Predicted landing time <br/>
     * 10 Actual landing time <br/>
     * 11 Actual time off runway <br/>
     * 12 Predicted time to gate <br/>
     * 13 Actual on-block time
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * @return the day <br/>
     * 00 Today <br/>
     * 01 Yesterday <br/>
     * 10 Tomorrow <br/>
     * 11 Invalid
     */
    public int getDay() {
        return day;
    }

    /**
     * @param day <br/>
     * 00 Today <br/>
     * 01 Yesterday <br/>
     * 10 Tomorrow <br/>
     * 11 Invalid
     */
    public void setDay(int day) {
        this.day = day;
    }

    /**
     * @return the hour
     */
    public int getHour() {
        return hour;
    }

    /**
     * @param hour the hour to set
     */
    public void setHour(int hour) {
        this.hour = hour;
    }

    /**
     * @return the minute
     */
    public int getMinute() {
        return minute;
    }

    /**
     * @param minute the minute to set
     */
    public void setMinute(int minute) {
        this.minute = minute;
    }

    /**
     * @return the secondAvailable
     */
    public boolean isSecondAvailable() {
        return secondAvailable;
    }

    /**
     * @param secondAvailable the secondAvailable to set
     */
    public void setSecondAvailable(boolean secondAvailable) {
        this.secondAvailable = secondAvailable;
    }

    /**
     * @return the second
     */
    public int getSecond() {
        return second;
    }

    /**
     * @param second the second to set
     */
    public void setSecond(int second) {
        this.second = second;
    }
    
    
}
