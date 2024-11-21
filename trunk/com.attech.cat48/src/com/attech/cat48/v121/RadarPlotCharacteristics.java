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
public class RadarPlotCharacteristics {
    private boolean subfield1;
    private boolean subfield2;
    private boolean subfield3;
    private boolean subfield4;
    private boolean subfield5;
    private boolean subfield6;
    private boolean subfield7;
    
    private double sub1PlotRunLength;
    private int sub2NumberOfReplies;
    private int sub3Amplitude;
    private double sub4PlotRunLength;
    private int sub5Amplitude;
    private double sub6DiffInRange;
    private double sub7DiffInAzimuth;

    /**
     * @return the subfield1
     */
    public boolean isSubfield1() {
        return subfield1;
    }

    /**
     * @param subfield1 the subfield1 to set
     */
    public void setSubfield1(boolean subfield1) {
        this.subfield1 = subfield1;
    }

    /**
     * @return the subfield2
     */
    public boolean isSubfield2() {
        return subfield2;
    }

    /**
     * @param subfield2 the subfield2 to set
     */
    public void setSubfield2(boolean subfield2) {
        this.subfield2 = subfield2;
    }

    /**
     * @return the subfield3
     */
    public boolean isSubfield3() {
        return subfield3;
    }

    /**
     * @param subfield3 the subfield3 to set
     */
    public void setSubfield3(boolean subfield3) {
        this.subfield3 = subfield3;
    }

    /**
     * @return the subfield4
     */
    public boolean isSubfield4() {
        return subfield4;
    }

    /**
     * @param subfield4 the subfield4 to set
     */
    public void setSubfield4(boolean subfield4) {
        this.subfield4 = subfield4;
    }

    /**
     * @return the subfield5
     */
    public boolean isSubfield5() {
        return subfield5;
    }

    /**
     * @param subfield5 the subfield5 to set
     */
    public void setSubfield5(boolean subfield5) {
        this.subfield5 = subfield5;
    }

    /**
     * @return the subfield6
     */
    public boolean isSubfield6() {
        return subfield6;
    }

    /**
     * @param subfield6 the subfield6 to set
     */
    public void setSubfield6(boolean subfield6) {
        this.subfield6 = subfield6;
    }

    /**
     * @return the subfield7
     */
    public boolean isSubfield7() {
        return subfield7;
    }

    /**
     * @param subfield7 the subfield7 to set
     */
    public void setSubfield7(boolean subfield7) {
        this.subfield7 = subfield7;
    }

    /**
     * @return the sub1PlotRunLength
     */
    public double getSub1PlotRunLength() {
        return sub1PlotRunLength;
    }

    /**
     * @param sub1PlotRunLength the sub1PlotRunLength to set
     */
    public void setSub1PlotRunLength(double sub1PlotRunLength) {
        this.sub1PlotRunLength = sub1PlotRunLength;
    }

    /**
     * @return the sub2NumberOfReplies
     */
    public int getSub2NumberOfReplies() {
        return sub2NumberOfReplies;
    }

    /**
     * @param sub2NumberOfReplies the sub2NumberOfReplies to set
     */
    public void setSub2NumberOfReplies(int sub2NumberOfReplies) {
        this.sub2NumberOfReplies = sub2NumberOfReplies;
    }

    /**
     * @return the sub3Amplitude
     */
    public int getSub3Amplitude() {
        return sub3Amplitude;
    }

    /**
     * @param sub3Amplitude the sub3Amplitude to set
     */
    public void setSub3Amplitude(int sub3Amplitude) {
        this.sub3Amplitude = sub3Amplitude;
    }

    /**
     * @return the sub4PlotRunLength
     */
    public double getSub4PlotRunLength() {
        return sub4PlotRunLength;
    }

    /**
     * @param sub4PlotRunLength the sub4PlotRunLength to set
     */
    public void setSub4PlotRunLength(double sub4PlotRunLength) {
        this.sub4PlotRunLength = sub4PlotRunLength;
    }

    /**
     * @return the sub5Amplitude
     */
    public int getSub5Amplitude() {
        return sub5Amplitude;
    }

    /**
     * @param sub5Amplitude the sub5Amplitude to set
     */
    public void setSub5Amplitude(int sub5Amplitude) {
        this.sub5Amplitude = sub5Amplitude;
    }

    /**
     * @return the sub6DiffInRange
     */
    public double getSub6DiffInRange() {
        return sub6DiffInRange;
    }

    /**
     * @param sub6DiffInRange the sub6DiffInRange to set
     */
    public void setSub6DiffInRange(double sub6DiffInRange) {
        this.sub6DiffInRange = sub6DiffInRange;
    }

    /**
     * @return the sub7DiffInAzimuth
     */
    public double getSub7DiffInAzimuth() {
        return sub7DiffInAzimuth;
    }

    /**
     * @param sub7DiffInAzimuth the sub7DiffInAzimuth to set
     */
    public void setSub7DiffInAzimuth(double sub7DiffInAzimuth) {
        this.sub7DiffInAzimuth = sub7DiffInAzimuth;
    }
    
    public static int extract(byte[] bytes, int index, RadarPlotCharacteristics radarPlotCharacteristics) {
        if (!Byter.validateIndex(index, bytes.length, 1)) return -1;
        int count = 1;
        byte cbyte = bytes[index++];
        boolean sub1 = Byter.extract(cbyte, 7) > 0;
        boolean sub2 = Byter.extract(cbyte, 6) > 0;
        boolean sub3 = Byter.extract(cbyte, 5) > 0;
        boolean sub4 = Byter.extract(cbyte, 4) > 0;
        boolean sub5 = Byter.extract(cbyte, 3) > 0;
        boolean sub6 = Byter.extract(cbyte, 2) > 0;
        boolean sub7 = Byter.extract(cbyte, 1) > 0;
        boolean extend = Byter.extract(cbyte, 0) > 0;
        if (extend) {
            while (extend) {
                if (!Byter.validateIndex(index, bytes.length, 1)) return count;
                cbyte = bytes[index++];
                extend = Byter.extract(cbyte, 0) > 0;
                count++;
            }
        }
        
        double dval = 0.0;
        int ival = 0;
        
        if (sub1) {
            radarPlotCharacteristics.setSubfield1(sub1);
            if (!Byter.validateIndex(index, bytes.length, 1)) return count;
            dval = (bytes[index++] & 0xFF) * 360 / Math.pow(2, 13);
            radarPlotCharacteristics.setSub1PlotRunLength(dval);
            count++;
        }
        
        if (sub2) {
            radarPlotCharacteristics.setSubfield2(sub2);
            if (!Byter.validateIndex(index, bytes.length, 1)) return count;
            ival = (bytes[index++] & 0xFF);
            radarPlotCharacteristics.setSub2NumberOfReplies(ival);
            count++;
        }
        
        if (sub3) {
            radarPlotCharacteristics.setSubfield3(sub3);
            if (!Byter.validateIndex(index, bytes.length, 1)) return count;
            ival = Byter.getComplementNumber(new byte[]{bytes[index++]});
            // ival = (bytes[index++] & 0xFF);
            radarPlotCharacteristics.setSub3Amplitude(ival);
            count++;
        }
        
        if (sub4) {
            radarPlotCharacteristics.setSubfield4(sub4);
            if (!Byter.validateIndex(index, bytes.length, 1)) return count;
            dval = (bytes[index++] & 0xFF) * 360 / Math.pow(2, 13);
            radarPlotCharacteristics.setSub4PlotRunLength(dval);
            count++;
        }
        
        if (sub5) {
            radarPlotCharacteristics.setSubfield5(sub5);
            if (!Byter.validateIndex(index, bytes.length, 1)) return count;
            ival = (bytes[index++] & 0xFF);
            radarPlotCharacteristics.setSub5Amplitude(ival);
            count++;
        }
        
        if (sub6) {
            radarPlotCharacteristics.setSubfield6(sub6);
            if (!Byter.validateIndex(index, bytes.length, 1)) return count;
            dval = (bytes[index++] & 0xFF) / 256;
            radarPlotCharacteristics.setSub6DiffInRange(dval);
            count++;
        }
        
        if (sub7) {
            radarPlotCharacteristics.setSubfield7(sub7);
            if (!Byter.validateIndex(index, bytes.length, 1)) return count;
            dval = (bytes[index++] & 0xFF) * 360 / Math.pow(2, 14);
            radarPlotCharacteristics.setSub7DiffInAzimuth(dval);
            count++;
        }
        return count;
    }
    
    public void print(int level) {
        String indent = Common.getLevel(level);
        System.out.println(String.format("%sRadar Plot Characteristics", indent));
        if (subfield1) System.out.println(String.format("\t%sSubfield 1, SSR Plot Runlength: %s", indent, sub1PlotRunLength));
        if (subfield2) System.out.println(String.format("\t%sSubfield 2, Number of Received Replies for (M)SSR: %s", indent, sub2NumberOfReplies));
        if (subfield3) System.out.println(String.format("\t%sSubfield 3, Amplitude of (M)SSR Reply: %s", indent, sub3Amplitude));
        if (subfield4) System.out.println(String.format("\t%sSubfield 4, Primary Plot Runlength: %s", indent, sub4PlotRunLength));
        if (subfield5) System.out.println(String.format("\t%sSubfield 5, Amplitude of Primary Plot: %s", indent, sub5Amplitude));
        if (subfield6) System.out.println(String.format("\t%sSubfield 6, Difference in Range between PSR and SSR plot: %s", indent, sub6DiffInRange));
        if (subfield7) System.out.println(String.format("\t%sSubfield 7, Difference in Azimuth between PSR and SSR plot: %s", indent, sub7DiffInAzimuth));
    }
}
