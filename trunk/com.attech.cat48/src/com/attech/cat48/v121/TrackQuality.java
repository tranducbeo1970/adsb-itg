/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.attech.cat48.v121;

/**
 *
 * @author an
 */
public class TrackQuality {
    private double sigmaX;
    private double sigmaY;
    private double sigmaV;
    private double sigmaH;

    /**
     * @return the sigmaX
     */
    public double getSigmaX() {
        return sigmaX;
    }

    /**
     * @param sigmaX the sigmaX to set
     */
    public void setSigmaX(double sigmaX) {
        this.sigmaX = sigmaX;
    }

    /**
     * @return the sigmaY
     */
    public double getSigmaY() {
        return sigmaY;
    }

    /**
     * @param sigmaY the sigmaY to set
     */
    public void setSigmaY(double sigmaY) {
        this.sigmaY = sigmaY;
    }

    /**
     * @return the sigmaV
     */
    public double getSigmaV() {
        return sigmaV;
    }

    /**
     * @param sigmaV the sigmaV to set
     */
    public void setSigmaV(double sigmaV) {
        this.sigmaV = sigmaV;
    }

    /**
     * @return the sigmaH
     */
    public double getSigmaH() {
        return sigmaH;
    }

    /**
     * @param sigmaH the sigmaH to set
     */
    public void setSigmaH(double sigmaH) {
        this.sigmaH = sigmaH;
    }
    
    public static int extract(byte[] bytes, int index, TrackQuality trackQuality) {
        if (!Byter.validateIndex(index, bytes.length, 4)) return -1;
        trackQuality.setSigmaX((double) (bytes[index++] & 0xFF) / 128);
        trackQuality.setSigmaY((double) (bytes[index++] & 0xFF) / 128);
        trackQuality.setSigmaV((double) (bytes[index++] & 0xFF) * Math.pow(2, -14));
        trackQuality.setSigmaH((double) (bytes[index++] & 0xFF) * 360 / Math.pow(2, 12));
        return 4;
    }
    
    public void print(int level) {
        String indent = Common.getLevel(level);
        System.out.println(String.format("%sTrack quality", indent));
        System.out.println(String.format("\t%sSigma(X): %s", indent, sigmaX));
        System.out.println(String.format("\t%sSigma(Y): %s", indent, sigmaY));
        System.out.println(String.format("\t%sSigma(V): %s", indent, sigmaV));
        System.out.println(String.format("\t%sSigma(H): %s", indent, sigmaH));
    }
}
