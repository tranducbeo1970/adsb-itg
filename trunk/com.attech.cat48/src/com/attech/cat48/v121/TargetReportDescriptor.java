/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.attech.cat48.v121;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author andh
 */
@XmlRootElement(name = "TargetReportDescriptor")
@XmlAccessorType(XmlAccessType.NONE)
public class TargetReportDescriptor {
    
    /*
    000 No detection
    001 Single PSR detection
    010 Single SSR detection
    011 SSR + PSR detection
    100 Single ModeS All-Call
    101 Single ModeS Roll-Call
    110 ModeS All-Call + PSR
    111 ModeS Roll-Call +PSR
    */
    @XmlAttribute(name = "type")
    private int type;
    
    @XmlAttribute(name = "simulate")
    private int simulate;
    
    @XmlAttribute(name = "rdp")
    private int rdp;
        
    @XmlAttribute(name = "spi")
    private int spi;
        
    @XmlAttribute(name = "rab")
    private int rab;
    
    /* Extention 01 */
    @XmlAttribute(name = "tst")
    private int tst;
    
    @XmlAttribute(name = "xpp")
    private int xpp;
    
    @XmlAttribute(name = "me")
    private int me;
    
    @XmlAttribute(name = "mi")
    private int mi;
    
    @XmlAttribute(name = "foefri")
    private int foefri;
        
    /**
     * @return the type <br/>
     * 0 000 No detection  <br/>
     * 1 001 Single PSR detection <br/>
     * 2 010 Single SSR detection <br/>
     * 3 011 SSR + PSR detection <br/>
     * 4 100 Single ModeS All-Call <br/>
     * 5 101 Single ModeS Roll-Call <br/>
     * 6 110 ModeS All-Call + PSR <br/>
     * 7 111 ModeS Roll-Call +PSR
     */
    public int getType() {
        return type;
    }

    /**
     * @param type <br/>
     * 0 000 No detection  <br/>
     * 1 001 Single PSR detection <br/>
     * 2 010 Single SSR detection <br/>
     * 3 011 SSR + PSR detection <br/>
     * 4 100 Single ModeS All-Call <br/>
     * 5 101 Single ModeS Roll-Call <br/>
     * 6 110 ModeS All-Call + PSR <br/>
     * 7 111 ModeS Roll-Call +PSR
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * @return the simulate
     */
    public int getSimulate() {
        return simulate;
    }

    /**
     * @param simulate the simulate to set
     */
    public void setSimulate(int simulate) {
        this.simulate = simulate;
    }

    /**
     * @return the rdp
     */
    public int getRdp() {
        return rdp;
    }

    /**
     * @param rdp the rdp to set
     */
    public void setRdp(int rdp) {
        this.rdp = rdp;
    }

    /**
     * @return the spi
     */
    public int getSpi() {
        return spi;
    }

    /**
     * @param spi the spi to set
     */
    public void setSpi(int spi) {
        this.spi = spi;
    }

    /**
     * @return the rab
     */
    public int getRab() {
        return rab;
    }

    /**
     * @param rab the rab to set
     */
    public void setRab(int rab) {
        this.rab = rab;
    }

    /**
     * @return the tst
     */
    public int getTst() {
        return tst;
    }

    /**
     * @param tst the tst to set
     */
    public void setTst(int tst) {
        this.tst = tst;
    }

    /**
     * @return the xpp
     */
    public int getXpp() {
        return xpp;
    }

    /**
     * @param xpp the xpp to set
     */
    public void setXpp(int xpp) {
        this.xpp = xpp;
    }

    /**
     * @return the me
     */
    public int getMe() {
        return me;
    }

    /**
     * @param me the me to set
     */
    public void setMe(int me) {
        this.me = me;
    }

    /**
     * @return the mi
     */
    public int getMi() {
        return mi;
    }

    /**
     * @param mi the mi to set
     */
    public void setMi(int mi) {
        this.mi = mi;
    }

    /**
     * @return the foefri
     */
    public int getFoefri() {
        return foefri;
    }

    /**
     * @param foefri the foefri to set
     */
    public void setFoefri(int foefri) {
        this.foefri = foefri;
    }
    
    
    public String getTypeStr() {
        // 0 - Nodetection | 1 - Sole primary detection | 2 - sole secondary detection | 3 - combined
        switch (type) {
            case 0:
                return "No detection (000) (0)";
            case 1:
                return "Single PSR detection (001) (1)";
            case 2:
                return "Single SSR detection (010) (2)";
            case 3:
                return "SSR + PSR detection (011)(3)";
            case 4:
                return "Single ModeS All-Call (100) (4)";
            case 5:
                return "Single ModeS Roll-Call (101) (5)";
            case 6:
                return "ModeS All-Call + PSR (110) (6)";
            case 7:
                return "ModeS Roll-Call +PSR (111) (7)";
            default:
                return String.format("Unknown(%s)", this.type);
        }
    }
    
    private String getFoeFriend() {
        switch(foefri) {
            case 0:
                return "No Mode 4 interrogation (0)";
            case 1:
                return "Friendly target (1)";
            case 2:
                return "Unknown target (2)";
            case 3:
                return "No reply (3)";
            default:
                return String.format("Unknown(%s)", foefri);
        }
    }
    
    public void print(int level) {
        String types = getTypeStr();
        String rdps  = rdp == 0 ? "Report from rdp Chain 1 (0)" : "Report from rdp Chain 2 (1)";
        String foefriend = getFoeFriend();
        
        String indent = Common.getLevel(level);
        System.out.println(String.format("%sTargetReportDescriptor", indent));
        System.out.println(String.format("\t%s%s", indent, types));
        System.out.println(String.format("\t%sSimulate: %s", indent, simulate));
        System.out.println(String.format("\t%s%s", indent, rdps));
        System.out.println(String.format("\t%sSPI: %s", indent, spi));
        System.out.println(String.format("\t%sRAB: %s", indent, rab));
        System.out.println(String.format("\t%sTest: %s", indent, tst));
        System.out.println(String.format("\t%sXPP: %s", indent, xpp));
        System.out.println(String.format("\t%sME: %s", indent, me));
        System.out.println(String.format("\t%sMI: %s", indent, mi));
        System.out.println(String.format("\t%s%s", indent, foefriend));
    }
    
    public static int extract(byte[] bytes, int index, TargetReportDescriptor targetReportDescriptor) {
        if (!Byter.validateIndex(index, bytes.length, 1)) return -1;
        int b = bytes[index++];
        // System.out.printf("First byte: %s%n", Integer.toBinaryString(b));
        // int v = Byter.extract(b, 5, 3);
        // System.out.printf("Type : %s%n", v);
        targetReportDescriptor.setType(Byter.extract(b, 5, 3));
        targetReportDescriptor.setSimulate(Byter.extract(b, 4));
        targetReportDescriptor.setRdp(Byter.extract(b, 3));
        targetReportDescriptor.setSpi(Byter.extract(b, 2));
        targetReportDescriptor.setRab(Byter.extract(b, 1));
        boolean extention = (Byter.extract(b, 0) == 1);
        if (!extention) return 1;
        
        if (!Byter.validateIndex(index, bytes.length, 1)) return 1;
        b = bytes[index++];
        targetReportDescriptor.setTst(Byter.extract(b, 7));
        targetReportDescriptor.setXpp(Byter.extract(b, 5));
        targetReportDescriptor.setMe(Byter.extract(b, 4));
        targetReportDescriptor.setMi(Byter.extract(b, 3));
        targetReportDescriptor.setFoefri(Byter.extract(b, 1, 2));
        extention = (Byter.extract(b, 0) == 1);
        if (!extention) return 2;
        
        int count = 2;
        while (extention) {
            if (!Byter.validateIndex(index, bytes.length, 1)) return count;
            b = bytes[index++];
            count++;
            extention = (Byter.extract(b, 0) == 1);
        }
        return count;
    }
}
