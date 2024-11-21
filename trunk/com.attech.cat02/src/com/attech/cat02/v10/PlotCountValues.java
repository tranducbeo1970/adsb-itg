/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.cat02.v10;

/**
 *
 * @author hong
 */
public class PlotCountValues {
    private int repetitionFactor;
    private int aerialIdentification;
    private int categoryIdentification;
    private int counter;

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
     * @return the aerialIdentification
     */
    public int getAerialIdentification() {
        return aerialIdentification;
    }

    /**
     * @param aerialIdentification the aerialIdentification to set
     */
    public void setAerialIdentification(int aerialIdentification) {
        this.aerialIdentification = aerialIdentification;
    }

    /**
     * @return the categoryIdentification
     */
    public int getCategoryIdentification() {
        return categoryIdentification;
    }

    /**
     * @param categoryIdentification the categoryIdentification to set
     */
    public void setCategoryIdentification(int categoryIdentification) {
        this.categoryIdentification = categoryIdentification;
    }

    /**
     * @return the counter
     */
    public int getCounter() {
        return counter;
    }

    /**
     * @param counter the counter to set
     */
    public void setCounter(int counter) {
        this.counter = counter;
    }
    
    public void print(int level) {
        String indent = Common.getLevel(level);
        System.out.println(String.format("%sPlot Count Values", indent));
        System.out.println(String.format("\t%sRepetition Factor: %s", indent, repetitionFactor));
        System.out.println(String.format("\t%sAerial Identification: %s", indent, aerialIdentification));
        System.out.println(String.format("\t%sCategory Identification: %s", indent, categoryIdentification));
        System.out.println(String.format("\t%sCounter: %s", indent, counter));
    }
    
    public static int extract(byte[] bytes, int index, PlotCountValues plotCountValue) {
        if (!Byter.validateIndex(index, bytes.length, 3)) return -1;
        int value = bytes[index++] & 0xFF;
        plotCountValue.setRepetitionFactor(value);
        
        byte cbyte = bytes[index++];
        value = Byter.extract(cbyte, 7);
        plotCountValue.setAerialIdentification(value);
        
        value = Byter.extract(cbyte, 2, 5);
        plotCountValue.setCategoryIdentification(value);
        
        value = Byter.extract(cbyte, 0, 2);
        value = value << 8 | (bytes[index++] & 0xFF);
        plotCountValue.setCounter(value);
        return 3;
        
    }
}
