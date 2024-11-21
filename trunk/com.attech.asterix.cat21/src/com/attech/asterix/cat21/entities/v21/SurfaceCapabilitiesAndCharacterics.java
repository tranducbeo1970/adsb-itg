/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asterix.cat21.entities.v21;

/**
 *
 * @author andh
 */
public class SurfaceCapabilitiesAndCharacterics {
    private boolean positionOffSetApplied;
    private boolean cockpitDisplayOfTrafficInformationSurface;
    private boolean b2low;
    private boolean receivingATCServices;
    private boolean indent;
    private Integer lengthWidth;

    /**
     * @return the positionOffSetApplied
     */
    public boolean isPositionOffSetApplied() {
        return positionOffSetApplied;
    }

    /**
     * @param positionOffSetApplied the positionOffSetApplied to set
     */
    public void setPositionOffSetApplied(boolean positionOffSetApplied) {
        this.positionOffSetApplied = positionOffSetApplied;
    }

    /**
     * @return the cockpitDisplayOfTrafficInformationSurface
     */
    public boolean isCockpitDisplayOfTrafficInformationSurface() {
        return cockpitDisplayOfTrafficInformationSurface;
    }

    /**
     * @param cockpitDisplayOfTrafficInformationSurface the cockpitDisplayOfTrafficInformationSurface to set
     */
    public void setCockpitDisplayOfTrafficInformationSurface(boolean cockpitDisplayOfTrafficInformationSurface) {
        this.cockpitDisplayOfTrafficInformationSurface = cockpitDisplayOfTrafficInformationSurface;
    }

    /**
     * @return the b2low
     */
    public boolean isB2low() {
        return b2low;
    }

    /**
     * @param b2low the b2low to set
     */
    public void setB2low(boolean b2low) {
        this.b2low = b2low;
    }

    /**
     * @return the receivingATCServices
     */
    public boolean isReceivingATCServices() {
        return receivingATCServices;
    }

    /**
     * @param receivingATCServices the receivingATCServices to set
     */
    public void setReceivingATCServices(boolean receivingATCServices) {
        this.receivingATCServices = receivingATCServices;
    }

    /**
     * @return the indent
     */
    public boolean isIndent() {
        return indent;
    }

    /**
     * @param indent the indent to set
     */
    public void setIndent(boolean indent) {
        this.indent = indent;
    }

    /**
     * @return the lengthWidth
     */
    public Integer getLengthWidth() {
        return lengthWidth;
    }

    /**
     * @param lengthWidth the lengthWidth to set
     */
    public void setLengthWidth(Integer lengthWidth) {
        this.lengthWidth = lengthWidth;
    }
}
