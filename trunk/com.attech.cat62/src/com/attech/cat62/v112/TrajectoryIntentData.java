/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.cat62.v112;

/**
 *
 * @author andh
 */
public class TrajectoryIntentData {
    private int repetitionFactor;
    private boolean tcpNumberAvailable;
    private boolean tcpCompliance;
    private int tcpNumber;
    private int altitude;
    private double latitude;
    private double longtitude;
    private int pointType;
    private int turnDirection;

    private boolean turnRadAvailable;
    private boolean timeOverPointAvailable;
    private int timeOverPoint;
    private double tcpTurnRadius;
    
    
    // private boolean _isHasSubField1;
    // private boolean _isHasSubField2;
    // private boolean _isDataAvailable;
    // private boolean _isDataValid;
    
    
    public static int extract(byte[] bytes, int index, TrajectoryIntentData code) {

        if (!Byter.validateIndex(index, bytes.length, 16)) return -1;
        // repetition factor
        code.setRepetitionFactor(bytes[index] & 0xFF);
        
        byte currentByte = bytes[index++];
        code.setTcpNumberAvailable((currentByte & 0x80) > 0);
        code.setTcpCompliance((currentByte & 0x40) > 0);
        code.setTcpNumber(currentByte & 0x3f);
        // Altitude
        int unit = Byter.getComplementNumber(new byte[] { bytes[index++], bytes[index++] });
        code.setAltitude(unit * 10);
        // Latitude
        unit = Byter.getComplementNumber(new byte[] { bytes[index++], bytes[index++], bytes[index++] });
        code.setLatitude(unit * 180 / Math.pow(2, 23));
        // Longtitude
        unit = Byter.getComplementNumber(new byte[] { bytes[index++], bytes[index++], bytes[index++] });
        code.setLongtitude(unit * 180 / Math.pow(2, 23));
        
        currentByte = bytes[index++];
        code.setPointType(currentByte >> 4 & 0x0F);
        code.setPointType(currentByte >> 2 & 0x03);
        code.setTurnRadAvailable((currentByte >> 1 & 0x01) > 0);
        code.setTimeOverPointAvailable((currentByte & 0x01) > 0);
        
        unit = ((bytes[index++] & 0xFF) << 16) | ((bytes[index++] & 0xFF) << 8) | (bytes[index++] & 0xFF);
        code.setTimeOverPoint(unit);
        
        unit = ((bytes[index++] & 0xFF) << 8) | (bytes[index++] & 0xFF);
        code.setTcpTurnRadius(unit * 0.01);
        return 16;
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
//        builder.append("Altitude: ").append(this.getAltitude()).append(" ");
//        builder.append("Latitude: ").append(this.getLatitude()).append(" ");
//        builder.append("Longtitude: ").append(this.getLongtitle()).append(" ");
//        builder.append("Point Type: ").append(this.getPointType()).append(" ");
//        builder.append("Repetition Factor: ").append(this.getRepetitionFactor()).append(" ");
//        builder.append("Tcp Number: ").append(this.getTcpNumber()).append(" ");
//        builder.append("Tcp Turn Radius: ").append(this.getTcpTurnRadius()).append(" ");
//        builder.append("Time Over Point: ").append(this.getTimeOverPoint()).append(" ");
//        builder.append("Turn Direction: ").append(this.getTurnDirection()).append(" ");
        return builder.toString();
    }

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
     * @return the tcpNumberAvailable
     */
    public boolean isTcpNumberAvailable() {
        return tcpNumberAvailable;
    }

    /**
     * @param tcpNumberAvailable the tcpNumberAvailable to set
     */
    public void setTcpNumberAvailable(boolean tcpNumberAvailable) {
        this.tcpNumberAvailable = tcpNumberAvailable;
    }

    /**
     * @return the tcpCompliance
     */
    public boolean isTcpCompliance() {
        return tcpCompliance;
    }

    /**
     * @param tcpCompliance the tcpCompliance to set
     */
    public void setTcpCompliance(boolean tcpCompliance) {
        this.tcpCompliance = tcpCompliance;
    }

    /**
     * @return the tcpNumber
     */
    public int getTcpNumber() {
        return tcpNumber;
    }

    /**
     * @param tcpNumber the tcpNumber to set
     */
    public void setTcpNumber(int tcpNumber) {
        this.tcpNumber = tcpNumber;
    }

    /**
     * @return the altitude <br/>
     * -1500 ft <= altitude <= 150000 ft
     */
    public int getAltitude() {
        return altitude;
    }

    /**
     * @param altitude  <br/>
     * -1500 ft <= altitude <= 150000 ft
     */
    public void setAltitude(int altitude) {
        this.altitude = altitude;
    }

    /**
     * @return the latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * @param latitude the latitude to set
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     * @return the longtitude
     */
    public double getLongtitude() {
        return longtitude;
    }

    /**
     * @param longtitude the longtitude to set
     */
    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }

    /**
     * @return the pointType <br/>
     * 0 Unknown<br/>
     * 1 Fly by waypoint (LT)<br/>
     * 2 Fly over waypoint (LT)<br/>
     * 3 Hold pattern (LT)<br/>
     * 4 Procedure hold (LT)<br/>
     * 5 Procedure turn (LT)<br/>
     * 6 RF leg (LT)<br/>
     * 7 Top of climb (VT)<br/>
     * 8 Top of descent (VT)<br/>
     * 9 Start of level (VT)<br/>
     * 10 Cross-over altitude (VT)<br/>
     * 11 Transition altitude (VT)<br/>
     */
    public int getPointType() {
        return pointType;
    }

    /**
     * @param pointType  <br/>
     * 0 Unknown<br/>
     * 1 Fly by waypoint (LT)<br/>
     * 2 Fly over waypoint (LT)<br/>
     * 3 Hold pattern (LT)<br/>
     * 4 Procedure hold (LT)<br/>
     * 5 Procedure turn (LT)<br/>
     * 6 RF leg (LT)<br/>
     * 7 Top of climb (VT)<br/>
     * 8 Top of descent (VT)<br/>
     * 9 Start of level (VT)<br/>
     * 10 Cross-over altitude (VT)<br/>
     * 11 Transition altitude (VT)<br/>
     */
    public void setPointType(int pointType) {
        this.pointType = pointType;
    }

    /**
     * @return the turnDirection <br/>
     * 00 (0) N/A<br/>
     * 01 (1) Turn right<br/>
     * 10 (2) Turn left<br/>
     * 11 (3) No turn
     */
    public int getTurnDirection() {
        return turnDirection;
    }

    /**
     * @param turnDirection <br/>
     * 00 (0) N/A<br/>
     * 01 (1) Turn right<br/>
     * 10 (2) Turn left<br/>
     * 11 (3) No turn
     */
    public void setTurnDirection(int turnDirection) {
        this.turnDirection = turnDirection;
    }

    /**
     * @return the turnRadAvailable
     */
    public boolean isTurnRadAvailable() {
        return turnRadAvailable;
    }

    /**
     * @param turnRadAvailable the turnRadAvailable to set
     */
    public void setTurnRadAvailable(boolean turnRadAvailable) {
        this.turnRadAvailable = turnRadAvailable;
    }

    /**
     * @return the timeOverPointAvailable
     */
    public boolean isTimeOverPointAvailable() {
        return timeOverPointAvailable;
    }

    /**
     * @param timeOverPointAvailable the timeOverPointAvailable to set
     */
    public void setTimeOverPointAvailable(boolean timeOverPointAvailable) {
        this.timeOverPointAvailable = timeOverPointAvailable;
    }

    /**
     * @return the timeOverPoint
     */
    public int getTimeOverPoint() {
        return timeOverPoint;
    }

    /**
     * @param timeOverPoint (in second)
     */
    public void setTimeOverPoint(int timeOverPoint) {
        this.timeOverPoint = timeOverPoint;
    }

    /**
     * @return the tcpTurnRadius <br/>
     * 0 < TTR < 655.35 Nm
     */
    public double getTcpTurnRadius() {
        return tcpTurnRadius;
    }

    /**
     * @param tcpTurnRadius <br/>
     * 0 < TTR < 655.35 Nm
     */
    public void setTcpTurnRadius(double tcpTurnRadius) {
        this.tcpTurnRadius = tcpTurnRadius;
    }
}
