/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.cat48.v121;

/**
 *
 * @author hong
 */
public class TrackStatus {

    private boolean extended;
    private int cnf;
    private int rad;
    private int dou;
    private int mah;
    private int cdm;
    private int sub1tre;
    private int sub1gho;
    private int sub1sup;
    private int sub1tcc;

    /**
     * Get confirmed track
     *
     * @return 0 - Confirmed 1 - Tentative Track
     */
    public int getCnf() {
        return cnf;
    }

    /**
     * Set confirmed track
     *
     * @param cnf (0 - Confirmed 1 - Tentative Track)
     */
    public void setCnf(int cnf) {
        this.cnf = cnf;
    }

    /**
     * Get type of Sensor(s) maintaining Track
     *
     * @return 
     * 00 (0): Combined Track <br/>
     * 01 (1): PSR Track  <br/>
     * 10 (2): SSR/Mode S Track  <br/>
     * 11 (3): Invalid
     */
    public int getRad() {
        return rad;
    }

    /**
     * @param rad ( 00: Combined Track 01: PSR Track 10: SSR/Mode S Track 11: Invalid)
     */
    public void setRad(int rad) {
        this.rad = rad;
    }

    /**
     * Signals level of confidence in plot to track association process
     *
     * @return <br/>
     * 0 - Normal confidence <br/>
     * 1 - Low confidence in plot to track association.
     */
    public int getDou() {
        return dou;
    }

    /**
     * @param dou the dou to set
     */
    public void setDou(int dou) {
        this.dou = dou;
    }

    /**
     * @return the mah
     */
    public int getMah() {
        return mah;
    }

    /**
     * @param mah the mah to set
     */
    public void setMah(int mah) {
        this.mah = mah;
    }

    /**
     * @return the cdm
     */
    public int getCdm() {
        return cdm;
    }

    /**
     * @param cdm the cdm to set
     */
    public void setCdm(int cdm) {
        this.cdm = cdm;
    }

    /**
     * @return the sub1tre
     */
    public int getSub1tre() {
        return sub1tre;
    }

    /**
     * @param sub1tre the sub1tre to set
     */
    public void setSub1tre(int sub1tre) {
        this.sub1tre = sub1tre;
    }

    /**
     * @return the sub1gho
     */
    public int getSub1gho() {
        return sub1gho;
    }

    /**
     * @param sub1gho the sub1gho to set
     */
    public void setSub1gho(int sub1gho) {
        this.sub1gho = sub1gho;
    }

    /**
     * @return the sub1sup
     */
    public int getSub1sup() {
        return sub1sup;
    }

    /**
     * @param sub1sup the sub1sup to set
     */
    public void setSub1sup(int sub1sup) {
        this.sub1sup = sub1sup;
    }

    /**
     * @return the sub1tcc
     */
    public int getSub1tcc() {
        return sub1tcc;
    }

    /**
     * @param sub1tcc the sub1tcc to set
     */
    public void setSub1tcc(int sub1tcc) {
        this.sub1tcc = sub1tcc;
    }

    /**
     * @return the extended
     */
    public boolean isExtended() {
        return extended;
    }

    /**
     * @param extended the extended to set
     */
    public void setExtended(boolean extended) {
        this.extended = extended;
    }

    public static int extract(byte[] bytes, int index, TrackStatus status) {
        if (!Byter.validateIndex(index, bytes.length, 1)) {
            return -1;
        }

        int count = 1;
        byte cbyte = bytes[index++];
        // System.out.printf("Track status: %s %n", Integer.toHexString(cbyte & 0xFF));
        int ivalue = Byter.extract(cbyte, 7);
        status.setCnf(ivalue);

        ivalue = Byter.extract(cbyte, 5, 2);
        status.setRad(ivalue);

        ivalue = Byter.extract(cbyte, 4);
        status.setDou(ivalue);

        ivalue = Byter.extract(cbyte, 3);
        status.setDou(ivalue);

        ivalue = Byter.extract(cbyte, 1, 2);
        status.setCdm(ivalue);

        boolean extend = Byter.extract(cbyte, 0) > 0;
        status.setExtended(extend);
        if (!extend) {
            return 1;
        }
        if (!Byter.validateIndex(index, bytes.length, 1)) {
            return count;
        }
        cbyte = bytes[index++];

        ivalue = Byter.extract(cbyte, 7);
        status.setSub1tre(ivalue);

        ivalue = Byter.extract(cbyte, 6);
        status.setSub1gho(ivalue);

        ivalue = Byter.extract(cbyte, 5);
        status.setSub1sup(ivalue);

        ivalue = Byter.extract(cbyte, 4);
        status.setSub1tcc(ivalue);

        count++;
        extend = Byter.extract(cbyte, 0) > 0;
        if (!extend) {
            return count;
        }

        while (extend) {
            if (!Byter.validateIndex(index, bytes.length, 1)) {
                break;
            }
            cbyte = bytes[index++];
            count++;
            extend = Byter.extract(cbyte, 0) > 0;
        }

        return count;
    }

    public void print(int level) {
        String indent = Common.getLevel(level);
        System.out.println(String.format("%sTrack Status", indent));
        System.out.println(String.format("\t%sCNF: %s", indent, cnf));
        System.out.println(String.format("\t%sRAD: %s", indent, rad));
        System.out.println(String.format("\t%sDOU: %s", indent, dou));
        System.out.println(String.format("\t%sMAH: %s", indent, mah));
        System.out.println(String.format("\t%sCDM: %s", indent, cdm));

        if (extended) {
            System.out.println(String.format("\t%sExtention TRE: %s", indent, sub1tre));
            System.out.println(String.format("\t%sExtention GHO: %s", indent, sub1gho));
            System.out.println(String.format("\t%sExtention SUP: %s", indent, sub1sup));
            System.out.println(String.format("\t%sExtention TCC: %s", indent, sub1tcc));
        }
    }
    
    @Override
    public String toString() {
        String indent = Common.getLevel(1);
        //String s = "";
        String s = (String.format("%sCNF: %s", indent, cnf)) 
                + (String.format("\t%sRAD: %s", indent, rad))
                + (String.format("\t%sDOU: %s", indent, dou))
                + (String.format("\t%sMAH: %s", indent, mah))
                + (String.format("\t%sCDM: %s", indent, cdm));

        if (extended) {
            s =  s + (String.format("\t%sExtention TRE: %s", indent, sub1tre))
                +(String.format("\t%sExtention GHO: %s", indent, sub1gho))
                +(String.format("\t%sExtention SUP: %s", indent, sub1sup))
                +(String.format("\t%sExtention TCC: %s", indent, sub1tcc));
        }
        return s;
    }

}
