/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asterix.cat21.entities;

/**
 *
 * @author andh
 */
public class FigureOfMerit {

    private int acas;
    private int multipleNavigational;
    private int differentialCorrection;
    private int positionAccuracy;

    public FigureOfMerit() {
    }
    
    @Override
    public String toString() {
        return "FigOfMerit ACAS:" +this.getAcas() + " MN:" + this.getMultipleNavigational() + " DC:" + this.getDifferentialCorrection() + " PA:" + this.getDifferentialCorrection();
    }

    /**
     * @return the acas
     */
    public int getAcas() {
        return acas;
    }

    /**
     * @param acas the acas to set
     */
    public void setAcas(int acas) {
        this.acas = acas;
    }

    /**
     * @return the multipleNavigational
     */
    public int getMultipleNavigational() {
        return multipleNavigational;
    }

    /**
     * @param multipleNavigational the multipleNavigational to set
     */
    public void setMultipleNavigational(int multipleNavigational) {
        this.multipleNavigational = multipleNavigational;
    }

    /**
     * @return the differentialCorrection
     */
    public int getDifferentialCorrection() {
        return differentialCorrection;
    }

    /**
     * @param differentialCorrection the differentialCorrection to set
     */
    public void setDifferentialCorrection(int differentialCorrection) {
        this.differentialCorrection = differentialCorrection;
    }

    /**
     * @return the positionAccuracy
     */
    public int getPositionAccuracy() {
        return positionAccuracy;
    }

    /**
     * @param positionAccuracy the positionAccuracy to set
     */
    public void setPositionAccuracy(int positionAccuracy) {
        this.positionAccuracy = positionAccuracy;
    }

    
}
