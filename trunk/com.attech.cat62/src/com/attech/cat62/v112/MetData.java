/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.cat62.v112;

/**
 *
 * @author andh
 */
public class MetData {
    private boolean validWindSpeed;
    private boolean validWindDirection;
    private boolean validTemperature;
    private boolean validTurbulence;
    
    private int turbulence;
    private double temperature;
    private int windDirection;
    private int windSpeed;
    
    public static int extract(byte[] bytes, int index, MetData code) {
        if (!Byter.validateIndex(index, bytes.length, 8)) return -1;
        byte currentByte = bytes[index++];
        code.setValidWindSpeed((currentByte & 0x80) > 0);
        code.setValidWindDirection((currentByte & 0x40) > 0);
        code.setValidTemperature((currentByte & 0x20) > 0);
        code.setValidTurbulence((currentByte & 0x10) > 0);

        code.setWindSpeed((bytes[index++] & 0xFF) << 8 | (bytes[index++] & 0xFF));
        code.setWindDirection((bytes[index++] & 0xFF) << 8 | (bytes[index++] & 0xFF));
        code.setTemperature(((bytes[index++] & 0xFF) << 8 | (bytes[index++] & 0xFF)) * 0.25);
        code.setTurbulence(bytes[index++] & 0xFF);
        return 8;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
//        builder.append(String.format("Temp: %s ", this.getTemperature()));
//        builder.append(String.format("Turbulence: %s ", this.getTurbulence()));
//        builder.append(String.format("WindDirection: %s ", this.getWindDirection()));
//        builder.append(String.format("WindSpeed: %s ", this.getWindSpeed()));
//        builder.append(String.format("HasTemperature: %s ", this.isIsHasTemperature()));
//        builder.append(String.format("HasTurbulence: %s ", this.isIsHasTurbulence()));
//        builder.append(String.format("HasWindDirection: %s ", this.isIsHasWindDirection()));
//        builder.append(String.format("HasWindSpeed: %s ", this.isIsHasWindSpeed()));
        return builder.toString();
    }

    /**
     * @return the validWindSpeed
     */
    public boolean isValidWindSpeed() {
        return validWindSpeed;
    }

    /**
     * @param validWindSpeed the validWindSpeed to set
     */
    public void setValidWindSpeed(boolean validWindSpeed) {
        this.validWindSpeed = validWindSpeed;
    }

    /**
     * @return the validWindDirection
     */
    public boolean isValidWindDirection() {
        return validWindDirection;
    }

    /**
     * @param validWindDirection the validWindDirection to set
     */
    public void setValidWindDirection(boolean validWindDirection) {
        this.validWindDirection = validWindDirection;
    }

    /**
     * @return the validTemperature
     */
    public boolean isValidTemperature() {
        return validTemperature;
    }

    /**
     * @param validTemperature the validTemperature to set
     */
    public void setValidTemperature(boolean validTemperature) {
        this.validTemperature = validTemperature;
    }

    /**
     * @return the validTurbulence
     */
    public boolean isValidTurbulence() {
        return validTurbulence;
    }

    /**
     * @param validTurbulence the validTurbulence to set
     */
    public void setValidTurbulence(boolean validTurbulence) {
        this.validTurbulence = validTurbulence;
    }

    /**
     * @return the turbulence <br/>
     * Integer between 0 and 15 inclusive
     */
    public int getTurbulence() {
        return turbulence;
    }

    /**
     * @param turbulence <br/>
     * Integer between 0 and 15 inclusive
     */
    public void setTurbulence(int turbulence) {
        this.turbulence = turbulence;
    }

    /**
     * @return the temperature
     */
    public double getTemperature() {
        return temperature;
    }

    /**
     * @param temperature the temperature to set
     */
    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    /**
     * @return the windDirection
     */
    public int getWindDirection() {
        return windDirection;
    }

    /**
     * @param windDirection the windDirection to set
     */
    public void setWindDirection(int windDirection) {
        this.windDirection = windDirection;
    }

    /**
     * @return the windSpeed
     */
    public int getWindSpeed() {
        return windSpeed;
    }

    /**
     * @param windSpeed the windSpeed to set
     */
    public void setWindSpeed(int windSpeed) {
        this.windSpeed = windSpeed;
    }
}
