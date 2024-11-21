/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asterix.cat21.entities.v21;

/**
 *
 * @author root
 */
public class AircraftOperationalStatus {
    private boolean isResolutionAdvisoryActive;
    private int targetTrajectoryChangeReportCapability;
    private boolean isTargetStateReportCapability;
    private boolean isAirReferencedVelocityReportCapability;
    private boolean isCockpitDisplayOfTrafficInformationAirborne;
    private boolean isTCASSystemStatus;
    private boolean isSingleAntenna;

    /**
     * @return the isResolutionAdvisoryActive
     */
    public boolean isIsResolutionAdvisoryActive() {
        return isResolutionAdvisoryActive;
    }

    /**
     * @param isResolutionAdvisoryActive the isResolutionAdvisoryActive to set
     */
    public void setIsResolutionAdvisoryActive(boolean isResolutionAdvisoryActive) {
        this.isResolutionAdvisoryActive = isResolutionAdvisoryActive;
    }

    /**
     * @return the targetTrajectoryChangeReportCapability
     */
    public int getTargetTrajectoryChangeReportCapability() {
        return targetTrajectoryChangeReportCapability;
    }

    /**
     * @param targetTrajectoryChangeReportCapability the targetTrajectoryChangeReportCapability to set
     */
    public void setTargetTrajectoryChangeReportCapability(int targetTrajectoryChangeReportCapability) {
        this.targetTrajectoryChangeReportCapability = targetTrajectoryChangeReportCapability;
    }

    /**
     * @return the isTargetStateReportCapability
     */
    public boolean isIsTargetStateReportCapability() {
        return isTargetStateReportCapability;
    }

    /**
     * @param isTargetStateReportCapability the isTargetStateReportCapability to set
     */
    public void setIsTargetStateReportCapability(boolean isTargetStateReportCapability) {
        this.isTargetStateReportCapability = isTargetStateReportCapability;
    }

    /**
     * @return the isAirReferencedVelocityReportCapability
     */
    public boolean isIsAirReferencedVelocityReportCapability() {
        return isAirReferencedVelocityReportCapability;
    }

    /**
     * @param isAirReferencedVelocityReportCapability the isAirReferencedVelocityReportCapability to set
     */
    public void setIsAirReferencedVelocityReportCapability(boolean isAirReferencedVelocityReportCapability) {
        this.isAirReferencedVelocityReportCapability = isAirReferencedVelocityReportCapability;
    }

    /**
     * @return the isCockpitDisplayOfTrafficInformationAirborne
     */
    public boolean isIsCockpitDisplayOfTrafficInformationAirborne() {
        return isCockpitDisplayOfTrafficInformationAirborne;
    }

    /**
     * @param isCockpitDisplayOfTrafficInformationAirborne the isCockpitDisplayOfTrafficInformationAirborne to set
     */
    public void setIsCockpitDisplayOfTrafficInformationAirborne(boolean isCockpitDisplayOfTrafficInformationAirborne) {
        this.isCockpitDisplayOfTrafficInformationAirborne = isCockpitDisplayOfTrafficInformationAirborne;
    }

    /**
     * @return the isTCASSystemStatus
     */
    public boolean isIsTCASSystemStatus() {
        return isTCASSystemStatus;
    }

    /**
     * @param isTCASSystemStatus the isTCASSystemStatus to set
     */
    public void setIsTCASSystemStatus(boolean isTCASSystemStatus) {
        this.isTCASSystemStatus = isTCASSystemStatus;
    }

    /**
     * @return the isSingleAntenna
     */
    public boolean isIsSingleAntenna() {
        return isSingleAntenna;
    }

    /**
     * @param isSingleAntenna the isSingleAntenna to set
     */
    public void setIsSingleAntenna(boolean isSingleAntenna) {
        this.isSingleAntenna = isSingleAntenna;
    }

    
}
