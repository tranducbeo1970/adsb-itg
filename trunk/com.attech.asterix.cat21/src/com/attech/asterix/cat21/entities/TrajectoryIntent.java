/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asterix.cat21.entities;

/**
 *
 * @author andh
 */
public class TrajectoryIntent {
    private boolean _isHasSubField1,_isHasSubField2, _isDataAvailable, _isDataValid, _isTcpNumberAvailable, _isTcpCompliance, _isTurnRadiusAvailable, _isTimeOverPointAvailable;
    private int _repetitionFactor, _tcpNumber, _altitude, _pointType, _turnDirection, _timeOverPoint ;
    private float _latitude, _longtitle,_tcpTurnRadius;

    /**
     * @return the _isHasSubField1
     */
    public boolean isIsHasSubField1() {
        return _isHasSubField1;
    }

    /**
     * @param isHasSubField1 the _isHasSubField1 to set
     */
    public void setIsHasSubField1(boolean isHasSubField1) {
        this._isHasSubField1 = isHasSubField1;
    }

    /**
     * @return the _isHasSubField2
     */
    public boolean isIsHasSubField2() {
        return _isHasSubField2;
    }

    /**
     * @param isHasSubField2 the _isHasSubField2 to set
     */
    public void setIsHasSubField2(boolean isHasSubField2) {
        this._isHasSubField2 = isHasSubField2;
    }

    /**
     * @return the _isDataAvailable
     */
    public boolean isIsDataAvailable() {
        return _isDataAvailable;
    }

    /**
     * @param isDataAvailable the _isDataAvailable to set
     */
    public void setIsDataAvailable(boolean isDataAvailable) {
        this._isDataAvailable = isDataAvailable;
    }

    /**
     * @return the _isDataValid
     */
    public boolean isIsDataValid() {
        return _isDataValid;
    }

    /**
     * @param isDataValid the _isDataValid to set
     */
    public void setIsDataValid(boolean isDataValid) {
        this._isDataValid = isDataValid;
    }

    /**
     * @return the _isTcpNumberAvailable
     */
    public boolean isIsTcpNumberAvailable() {
        return _isTcpNumberAvailable;
    }

    /**
     * @param isTcpNumberAvailable the _isTcpNumberAvailable to set
     */
    public void setIsTcpNumberAvailable(boolean isTcpNumberAvailable) {
        this._isTcpNumberAvailable = isTcpNumberAvailable;
    }

    /**
     * @return the _isTcpCompliance
     */
    public boolean isIsTcpCompliance() {
        return _isTcpCompliance;
    }

    /**
     * @param isTcpCompliance the _isTcpCompliance to set
     */
    public void setIsTcpCompliance(boolean isTcpCompliance) {
        this._isTcpCompliance = isTcpCompliance;
    }

    /**
     * @return the _isTurnRadiusAvailable
     */
    public boolean isIsTurnRadiusAvailable() {
        return _isTurnRadiusAvailable;
    }

    /**
     * @param isTurnRadiusAvailable the _isTurnRadiusAvailable to set
     */
    public void setIsTurnRadiusAvailable(boolean isTurnRadiusAvailable) {
        this._isTurnRadiusAvailable = isTurnRadiusAvailable;
    }

    /**
     * @return the _isTimeOverPointAvailable
     */
    public boolean isIsTimeOverPointAvailable() {
        return _isTimeOverPointAvailable;
    }

    /**
     * @param isTimeOverPointAvailable the _isTimeOverPointAvailable to set
     */
    public void setIsTimeOverPointAvailable(boolean isTimeOverPointAvailable) {
        this._isTimeOverPointAvailable = isTimeOverPointAvailable;
    }

    /**
     * @return the _repetitionFactor
     */
    public int getRepetitionFactor() {
        return _repetitionFactor;
    }

    /**
     * @param repetitionFactor the _repetitionFactor to set
     */
    public void setRepetitionFactor(int repetitionFactor) {
        this._repetitionFactor = repetitionFactor;
    }

    /**
     * @return the _tcpNumber
     */
    public int getTcpNumber() {
        return _tcpNumber;
    }

    /**
     * @param tcpNumber the _tcpNumber to set
     */
    public void setTcpNumber(int tcpNumber) {
        this._tcpNumber = tcpNumber;
    }

    /**
     * @return the _altitude
     */
    public int getAltitude() {
        return _altitude;
    }

    /**
     * @param altitude the _altitude to set
     */
    public void setAltitude(int altitude) {
        this._altitude = altitude;
    }

    /**
     * @return the _pointType
     */
    public int getPointType() {
        return _pointType;
    }

    /**
     * @param pointType the _pointType to set
     */
    public void setPointType(int pointType) {
        this._pointType = pointType;
    }

    /**
     * @return the _turnDirection
     */
    public int getTurnDirection() {
        return _turnDirection;
    }

    /**
     * @param turnDirection the _turnDirection to set
     */
    public void setTurnDirection(int turnDirection) {
        this._turnDirection = turnDirection;
    }

    /**
     * @return the _timeOverPoint
     */
    public int getTimeOverPoint() {
        return _timeOverPoint;
    }

    /**
     * @param timeOverPoint the _timeOverPoint to set
     */
    public void setTimeOverPoint(int timeOverPoint) {
        this._timeOverPoint = timeOverPoint;
    }

    /**
     * @return the _latitude
     */
    public float getLatitude() {
        return _latitude;
    }

    /**
     * @param latitude the _latitude to set
     */
    public void setLatitude(float latitude) {
        this._latitude = latitude;
    }

    /**
     * @return the _longtitle
     */
    public float getLongtitle() {
        return _longtitle;
    }

    /**
     * @param longtitle the _longtitle to set
     */
    public void setLongtitle(float longtitle) {
        this._longtitle = longtitle;
    }

    /**
     * @return the _tcpTurnRadius
     */
    public float getTcpTurnRadius() {
        return _tcpTurnRadius;
    }

    /**
     * @param tcpTurnRadius the _tcpTurnRadius to set
     */
    public void setTcpTurnRadius(float tcpTurnRadius) {
        this._tcpTurnRadius = tcpTurnRadius;
    }
    
}
