/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asterix.cat21.entities;

/**
 *
 * @author andh
 */
public class DataSourceIdentification {
    private short sac;
    private short sic;

    /** SAC **/
    public short getSac() {
        return sac;
    }

    public void setSac(short sac) {
        this.sac = sac;
    }

    /** SIC **/
    public short getSic() {
        return sic;
    }

    public void setSic(short sic) {
        this.sic = sic;
    }
    
    @Override
    public String toString() {
        return "SAC:" + this.sac + " SIC:" + this.sic;
    }
}
