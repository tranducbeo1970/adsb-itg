/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.attech.cat34.v127;

/**
 *
 * @author andh
 */
public class SystemProcessMode {
    
    private boolean subfield1;
    private boolean subfield4;
    private boolean subfield5;
    private boolean subfield6;
    
    private int sub1RedRdp;
    private int sub1RedXmt;
    
    private int sub4Pol;
    private int sub4RedRad;
    private int sub4Stc;
    
    private int sub5RedRad;
    
    private int sub6RedRad;
    private int subClu;

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
     * @return the sub1RedRdp
     */
    public int getSub1RedRdp() {
        return sub1RedRdp;
    }

    /**
     * @param sub1RedRdp the sub1RedRdp to set
     */
    public void setSub1RedRdp(int sub1RedRdp) {
        this.sub1RedRdp = sub1RedRdp;
    }

    /**
     * @return the sub1RedXmt
     */
    public int getSub1RedXmt() {
        return sub1RedXmt;
    }

    /**
     * @param sub1RedXmt the sub1RedXmt to set
     */
    public void setSub1RedXmt(int sub1RedXmt) {
        this.sub1RedXmt = sub1RedXmt;
    }

    /**
     * @return the sub4Pol
     */
    public int getSub4Pol() {
        return sub4Pol;
    }

    /**
     * @param sub4Pol the sub4Pol to set
     */
    public void setSub4Pol(int sub4Pol) {
        this.sub4Pol = sub4Pol;
    }

    /**
     * @return the sub4RedRad
     */
    public int getSub4RedRad() {
        return sub4RedRad;
    }

    /**
     * @param sub4RedRad the sub4RedRad to set
     */
    public void setSub4RedRad(int sub4RedRad) {
        this.sub4RedRad = sub4RedRad;
    }

    /**
     * @return the sub4Stc
     */
    public int getSub4Stc() {
        return sub4Stc;
    }

    /**
     * @param sub4Stc the sub4Stc to set
     */
    public void setSub4Stc(int sub4Stc) {
        this.sub4Stc = sub4Stc;
    }

    /**
     * @return the sub5RedRad
     */
    public int getSub5RedRad() {
        return sub5RedRad;
    }

    /**
     * @param sub5RedRad the sub5RedRad to set
     */
    public void setSub5RedRad(int sub5RedRad) {
        this.sub5RedRad = sub5RedRad;
    }

    /**
     * @return the sub6RedRad
     */
    public int getSub6RedRad() {
        return sub6RedRad;
    }

    /**
     * @param sub6RedRad the sub6RedRad to set
     */
    public void setSub6RedRad(int sub6RedRad) {
        this.sub6RedRad = sub6RedRad;
    }

    /**
     * @return the subClu
     */
    public int getSubClu() {
        return subClu;
    }

    /**
     * @param subClu the subClu to set
     */
    public void setSubClu(int subClu) {
        this.subClu = subClu;
    }
    
    public static int extract(byte [] bytes, int index, SystemProcessMode sysProcessMode) {
        if (!Byter.validateIndex(index, bytes.length, 1)) return -1;
        int count = 1;
        byte b = bytes[index++];
        boolean sub1 = Byter.extract(b, 7) > 0;
        boolean sub4 = Byter.extract(b, 4) > 0;
        boolean sub5 = Byter.extract(b, 3) > 0;
        boolean sub6 = Byter.extract(b, 2) > 0;
        boolean extend = Byter.extract(b, 0) > 0;
        if (extend) {
            while (extend) {
                if (!Byter.validateIndex(index, bytes.length, 1)) return -1;
                b = bytes[index++];
                extend = Byter.extract(b, 0) > 0;
                count++;
            }
        }
        
        if (sub1) {
            if (!Byter.validateIndex(index, bytes.length, 1)) return -1;
            count ++;
            b = bytes[index++];
            sysProcessMode.setSubfield1(sub1);
            sysProcessMode.setSub1RedRdp(Byter.extract(b, 4, 3));
            sysProcessMode.setSub1RedXmt(Byter.extract(b, 1, 3));
        }
        
        if (sub4) {
            if (!Byter.validateIndex(index, bytes.length, 1)) return -1;
            count ++;
            b = bytes[index++];
            sysProcessMode.setSubfield4(sub4);
            sysProcessMode.setSub4Pol(Byter.extract(b, 7));
            sysProcessMode.setSub4RedRad(Byter.extract(b, 4, 3));
            sysProcessMode.setSub4Stc(Byter.extract(b, 2, 2));
        }
        
        if (sub5) {
            if (!Byter.validateIndex(index, bytes.length, 1)) return -1;
            count ++;
            b = bytes[index++];
            sysProcessMode.setSubfield5(sub5);
            sysProcessMode.setSub5RedRad(Byter.extract(b, 5, 3));
        }
        
        if (sub6) {
            if (!Byter.validateIndex(index, bytes.length, 1)) return -1;
            count ++;
            b = bytes[index++];
            sysProcessMode.setSubfield6(sub6);
            sysProcessMode.setSub6RedRad(Byter.extract(b, 5, 3));
            sysProcessMode.setSubClu(Byter.extract(b, 4));
        }
        
        return count;
    }
    
    public void print(int level) {
        String indent = Common.getLevel(level);
        System.out.println(String.format("%sSystem Process Mode", indent));
        if (subfield1) {
            System.out.println(String.format("\t%sSubfield 1", indent));
            System.out.println(String.format("\t\t%sRED-RDP: ", indent, sub1RedRdp));
            System.out.println(String.format("\t\t%sRED-XMT: ", indent, sub1RedXmt));
        }
        
        if (subfield4) {
            System.out.println(String.format("\t%sSubfield 4", indent));
            System.out.println(String.format("\t\t%sPOL: ", indent, sub4Pol));
            System.out.println(String.format("\t\t%sRED-RAD: ", indent, sub4RedRad));
            System.out.println(String.format("\t\t%sSTC: ", indent, sub4Stc));
        }

        if (subfield5) {
            System.out.println(String.format("\t%sSubfield 5", indent));
            System.out.println(String.format("\t\t%sRED-RAD: ", indent, sub5RedRad));
        }

        if (subfield6) {
            System.out.println(String.format("\t%sSubfield 6", indent));
            System.out.println(String.format("\t\t%sRED-RAD: ", indent, sub6RedRad));
            System.out.println(String.format("\t\t%sCLU: ", indent, subClu));
        }
    }
    
}
