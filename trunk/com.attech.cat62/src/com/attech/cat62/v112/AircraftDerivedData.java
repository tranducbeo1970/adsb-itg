/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.cat62.v112;

/**
 *
 * @author andh
 */
public class AircraftDerivedData {
    
    private static final byte[] MASKED = new byte[]{(byte) 0x80, (byte) 0x40, (byte) 0x20, (byte) 0x10, (byte) 0x08, (byte) 0x04, (byte) 0x02};
    
    private boolean [] items ;
    private Integer address; // #1
    private String targetID; // #2
    private Double magneticHeading; // #3
    private AirSpeed airSpeed; // #4
    private Integer trueAirSpeed; // #5 knot
    private SelectedAltitude selectedAlt; // #6
    private FinalStateSelectedAltitude finalStateSelectedAltitude; // #7
    private TrajectoryIntentStatus trajectoryIntentStatus; // #8
    private TrajectoryIntentData trajectoryIntentData; // #9
    private CapacityStatusReportByModeS capacityStatusReportByModeS;
    private StatusReportedByADSB statusReportByADSB;
    private ACASResolutionAdvisoryReport resolutionAdvisoryReport;
    private Double barometricVerticalRate;
    private Double geometricverticalRate;
    private Double rollAgel;
    private TrackAngleRate trackAngleRate;
    private Double trackAngle; // #17
    private Double groundSpeed; // #18
    private Integer velociryUncertainCategory;
    private MetData metDate; // #20
    private Integer emiterCategory; // #21
    private WGS84Coordinate position; // #22
    private Double geometricAltitude; // #23
    private Integer positionUncertain; // #24
    private ModeSMBData modeSMBData; // #25
    private Integer indicatedSpeed; // #26
    private Double machNo; // #27
    private Double baroPresureSetting; // #28
    
    public AircraftDerivedData() {
        items = new boolean [28];
    }
    
    public static int decode(byte [] bytes, int index, AircraftDerivedData coordinate) {
        int total = extractHeader(bytes, index, coordinate);
        if (total < 0) return -1;
        index += total;
        int count = 0;
        for (int i = 0; i < 28; i++) {
            if (!coordinate.getItem(i)) continue;
            switch (i) {
                case 0: // Target Address
                    IValue address = new IValue();
                    count = getTargetAddress(bytes, index, address);
                    if (count < 0) return -1;
                    coordinate.setAddress(address.getValue());
                    break;
                    
                case 1: // Subfield # 2: Target Identification
                    StringBuilder targetID = new StringBuilder();
                    /// TargetID targetID = new TargetID();
                    count = getTargetID(bytes, index, targetID);
                    if (count < 0) return -1;
                    coordinate.setTargetID(targetID.toString().trim());
                    break;
                    
                case 2: // Subfield # 3: Magnetic Heading
                    DValue magneticHeading = new DValue();
                    count = getMagneticHeading(bytes, index, magneticHeading);
                    if (count < 0) return -1;
                    coordinate.setMagneticHeading(magneticHeading.value);
                    break;
                    
                case 3: // Subfield # 4: Indicated Airspeed / Mach No
                    AirSpeed airspeed = new AirSpeed();
                    count = AirSpeed.extract(bytes, index, airspeed);
                    if (count < 0) return -1;
                    coordinate.setAirSpeed(airspeed);
                    break;
                    
                case 4: // Subfield # 5: True Airspeed
                    IValue trueAirspeed = new IValue();
                    count = getTrueAirspeed(bytes, index, trueAirspeed);
                    if (count < 0) return -1;
                    coordinate.setTrueAirSpeed(trueAirspeed.getValue());
                    break;
                    
                case 5: // Subfield # 6: Selected Altitude
                    SelectedAltitude selectedAltitude = new SelectedAltitude();
                    count = SelectedAltitude.extract(bytes, index, selectedAltitude);
                    if (count  < 0) return -1;
                    coordinate.setSelectedAlt(selectedAltitude);
                    break;
                    
                case 6: // Subfield # 7: Final State Selected Altitude
                    FinalStateSelectedAltitude finalSelectedAltitude = new FinalStateSelectedAltitude();
                    count = FinalStateSelectedAltitude.extract(bytes, index, finalSelectedAltitude);
                    if (count  < 0) return -1;
                    coordinate.setFinalStateSelectedAltitude(finalSelectedAltitude);
                    break;
                    
                case 7: // Subfield # 8 : Trajectory Intent Status
                    TrajectoryIntentStatus trajectoryIntentStatus = new TrajectoryIntentStatus();
                    count = TrajectoryIntentStatus.extract(bytes, index, trajectoryIntentStatus);
                    if (count  < 0) return -1;
                    coordinate.setTrajectoryIntentStatus(trajectoryIntentStatus);
                    break;
                    
                case 8: // Subfield # 9: Trajectory Intent Data
                    TrajectoryIntentData trajectoryIntentData = new TrajectoryIntentData();
                    count = TrajectoryIntentData.extract(bytes, index, trajectoryIntentData);
                    if (count  < 0) return -1;
                    coordinate.setTrajectoryIntentData(trajectoryIntentData);
                    break;
                    
                case 9: // Subfield # 10: Communications/ACAS Capability and Flight Status reported by Mode-S
                    CapacityStatusReportByModeS capacityStatusReportByModeS = new CapacityStatusReportByModeS();
                    count = CapacityStatusReportByModeS.extract(bytes, index, capacityStatusReportByModeS);
                    if (count  < 0) return -1;
                    coordinate.setCapacityStatusReportByModeS(capacityStatusReportByModeS);
                    break;
                    
                case 10: // Subfield # 11: Status reported by ADS-B
                    StatusReportedByADSB statusReportedByADSB = new StatusReportedByADSB();
                    count = StatusReportedByADSB.extract(bytes, index, statusReportedByADSB);
                    if (count  < 0) return -1;
                    coordinate.setStatusReportByADSB(statusReportedByADSB);
                    break;
                    
                case 11: // Subfield # 12: ACAS Resolution Advisory Report
                    ACASResolutionAdvisoryReport acasResolutionAdvisoryResport = new ACASResolutionAdvisoryReport();
                    count = ACASResolutionAdvisoryReport.extract(bytes, index, acasResolutionAdvisoryResport);
                    if (count  < 0) return -1;
                    coordinate.setResolutionAdvisoryReport(acasResolutionAdvisoryResport);
                    break;
                    
                case 12: // Subfield # 13: Barometric Vertical Rate
                    DValue barometric = new DValue();
                    count = getBarometric(bytes, index, barometric);
                    if (count  < 0) return -1;
                    coordinate.setBarometricVerticalRate(barometric.value);
                    break;
                    
                case 13: // Subfield # 14: Geometric Vertical Rate
                    DValue geometricVerticalRate = new DValue();
                    count = getBarometric(bytes, index, geometricVerticalRate);
                    if (count  < 0) return -1;
                    coordinate.setGeometricverticalRate(geometricVerticalRate.value);
                    break;
                    
                case 14: // Subfield # 15: Roll Angle
                    DValue rollangle = new DValue();
                    count = getRollAngle(bytes, index, rollangle);
                    if (count  < 0) return -1;
                    coordinate.setRollAgel(rollangle.value);
                    break;
                    
                case 15: // Subfield # 16: Track Angle Rate
                    TrackAngleRate trackAngleRate = new TrackAngleRate();
                    count = TrackAngleRate.extract(bytes, index, trackAngleRate);
                    if (count  < 0) return -1;
                    coordinate.setTrackAngleRate(trackAngleRate);
                    break;
                    
                case 16: // Subfield # 17: Track Angle
                    DValue trackAngle = new DValue();
                    count = getTrackAngle(bytes, index, trackAngle);
                    if (count  < 0) return -1;
                    coordinate.setTrackAngle(trackAngle.value);
                    break;
                    
                case 17: // Subfield # 18: Ground Speed
                    DValue groundSpeed = new DValue();
                    count = getGroundSpeed(bytes, index, groundSpeed);
                    if (count  < 0) return -1;
                    coordinate.setGroundSpeed(groundSpeed.value);
                    break;
                    
                case 18: // Subfield # 19: Velocity Uncertainty
                    if (!Byter.validateIndex(index, bytes.length, 1)) return -1;
                    coordinate.setVelociryUncertainCategory(bytes[index++] & 0xFF);
                    count = 1;
                    break;
                    
                case 19: // Subfield # 20: Met Data
                    MetData metData = new MetData();
                    count = MetData.extract(bytes, index, metData);
                    if (count  < 0) return -1;
                    coordinate.setMetDate(metData);
                    break;
                    
                case 20: // Subfield # 21: Emitter Category
                    if (!Byter.validateIndex(index, bytes.length, 1)) return -1;
                    coordinate.setEmiterCategory(bytes[index++] & 0xFF);
                    count = 1;
                    break;
                    
                case 21: // Subfield # 22: Position
                    WGS84Coordinate position = new WGS84Coordinate();
                    count = getPosition(bytes, index, position);
                    if (count  < 0) return -1;
                    coordinate.setPosition(position);
                    break;
                    
                case 22: // Subfield # 23: Geometric Altitude
                    if (!Byter.validateIndex(index, bytes.length, 2)) return -1;
                    int geometricAlt = Byter.getComplementNumber(new byte[]{bytes[index++], bytes[index++]});
                    coordinate.setGeometricAltitude(geometricAlt * 6.25);
                    count = 2;
                    break;
                    
                case 23: // Subfield # 24: Position Uncertainty
                    if (!Byter.validateIndex(index, bytes.length, 1)) return -1;
                    coordinate.setPositionUncertain(bytes[index++] & 0x0F);
                    count = 1;
                    break;
                    
                case 24: // Subfield # 25: MODE S MB DATA
                    ModeSMBData modeSMBData = new ModeSMBData();
                    count = ModeSMBData.extract(bytes, index, modeSMBData);
                    if (count < 0) return -1;
                    coordinate.setModeSMBData(modeSMBData);
                    break;
                    
                case 25: // Subfield # 26: Indicated Airspeed
                    if (!Byter.validateIndex(index, bytes.length, 2)) return -1;
                    coordinate.setIndicatedSpeed((bytes[index++] & 0xFF) << 8 | (bytes[index++] & 0xFF));
                    count = 2;
                    break;
                    
                case 26: // Subfield # 27: Mach Number
                    if (!Byter.validateIndex(index, bytes.length, 2)) return -1;
                    int machNumber =(bytes[index++] & 0xFF) << 8 | (bytes[index++] & 0xFF);
                    coordinate.setMachNo(machNumber * 0.008);
                    count = 2;
                    break;
                    
                case 27: // Subfield # 28: Barometric Pressure Setting (derived from Mode S BDS 4,0)
                    if (!Byter.validateIndex(index, bytes.length, 2)) return -1;
                    int baroPressureSetting =(bytes[index++] & 0x0F) << 8 | (bytes[index++] & 0xFF);
                    coordinate.setBaroPresureSetting(baroPressureSetting * 0.1);
                    count = 2;
                    break;
                    
                default:
                    count = 0;
                    break;
            }
            total += count;
            index += count;
        }

        return total;
    }
    
    public byte [] encode() {
        SubByteBuilder builder = new SubByteBuilder();
        // Subfield #1:Target Address
        if (this.address != null) {
            builder.set(0, encodeTargetAddress());
        }
        
        // Subfield #2:Target Identification
        if (this.targetID != null && !this.targetID.isEmpty()) {
            builder.set(1, encodeTargetID());
        }
        
        // Subfield #3:Magnetic Heading
        if (this.magneticHeading != null) {
            builder.set(2, encodeMagneticHeading());
        }
        
        // Subfield #4:Indicated Airspeed/Mach Number
        // Subfield #5:True Airspeed
        if (this.trueAirSpeed != null) {
            builder.set(4, encodeTrueAirSpeed());
        }
        
        // Subfield #6:Selected Altitude
        // Subfield #7:Final State SelectedAltitude

        // Subfield #8: Trajectory Intent Status
        // Subfield #9: Trajectory Intent Data
        // Subfield #10: Communications / ACAS Capability and Flight Status
        // Subfield #11: Status reported by ADS-B
        // Subfield #12: ACAS Resolution Advisory Report
        // Subfield #13: Barometric Vertical Rate
        // Subfield #14: Geometric Vertical Rate

        // Subfield #15: Roll Angle
        // Subfield #16: Track Angle Rate
        // Subfield #10: Communications / ACAS Capability and Flight Status
        // Subfield #11: Status reported by ADS-B
        // Subfield #12: ACAS Resolution Advisory Report
        // Subfield #13: Barometric Vertical Rate
        // Subfield #14: Geometric Vertical Rate

        // Subfield #15: Roll Angle
        // Subfield #16: Track Angle Rate
        // Subfield #17: Track Angle
        if (this.trackAngle != null ) {
            builder.set(16, encodeTrackAngle());
        }
        
        // Subfield #18: Ground Speed
        if (this.groundSpeed != null) {
            builder.set(17, encodeGroundSpeed());
        }
        
        // Subfield #19: Velocity Uncertainty
        // Subfield #20: Meteorological Data
        // Subfield #21: Emitter Category

        // Subfield #22: Position Data
        // Subfield #23: Geometric Altitude Data
        // Subfield #24: Position Uncertainty Data
        // Subfield #25: Mode S MB Data
        // Subfield #26: Indicated Airspeed
        // Subfield #27: Mach Number
        // Subfield #28: Barometric Pressure Setting.
        
        return builder.toByteArray();
    }
    
    private static int extractHeader(byte [] bytes, int index, AircraftDerivedData coordinate) {
        boolean isExtend = true;
        int bit;
        int idx;
        int count = 0;
        while (isExtend) {
            if (bytes.length <= index) return -1;
            byte bt = bytes[index];
            for (int i = 1; i < 8; i++) {
                bit = bt & MASKED[i - 1];
                if (bit == 0) continue;
                idx = count * 7 + i - 1;
                System.out.printf("idx = %s%n", idx);
                coordinate.setItem(idx);
            }

            isExtend = (( bt & 0x01) != 0);
            index++;
            count++; // Increasing readed bytes
        }
        return count;
    }
        
    private static int getTargetAddress(byte[] bytes, int index, IValue address) {
        if (!Byter.validateIndex(index, bytes.length, 3)) return -1;
        final int value = (bytes[index++] & 0xFF) << 16 | (bytes[index++] & 0xFF) << 8 | (bytes[index++] & 0xFF);
        address.setValue(value);
        return 3;
    }
    
    private static int getTargetID(byte[] bytes, int index, StringBuilder builder) {
        if (!Byter.validateIndex(index, bytes.length, 6)) return -1;
        builder.append(getCode(bytes[index++], bytes[index++], bytes[index++]));
        builder.append(getCode(bytes[index++], bytes[index++], bytes[index++]));
        return 6;
    }
    
    private static int getMagneticHeading(byte[] bytes, int index, DValue val) {
        if (!Byter.validateIndex(index, bytes.length, 2)) return -1;
        final int value = (bytes[index++] & 0xFF) << 8 | (bytes[index++] & 0xFF);
        val.setValue(value * 0.0055);
        return 2;
    }
    
    private static int getTrueAirspeed(byte[] bytes, int index, IValue val) {
        if (!Byter.validateIndex(index, bytes.length, 2)) return -1;
        final int value = (bytes[index++] & 0xFF) << 8 | (bytes[index++] & 0xFF);
        val.setValue(value);
        return 2;
    }
    
    private static int getBarometric(byte[] bytes, int index, DValue val) {
        if (!Byter.validateIndex(index, bytes.length, 2)) return -1;
        int ival = Byter.getComplementNumber(new byte[] { bytes[index++], bytes[index++] });
        val.setValue(ival * 6.25);
        return 2;
    }
    
    private static int getRollAngle(byte[] bytes, int index, DValue val) {
        if (!Byter.validateIndex(index, bytes.length, 2)) return -1;
        int ival = Byter.getComplementNumber(new byte[] { bytes[index++], bytes[index++] });
        val.setValue(ival * 0.01);
        return 2;
    }
    
    private static int getTrackAngle(byte[] bytes, int index, DValue val) {
        if (!Byter.validateIndex(index, bytes.length, 2)) return -1;
        int ival = ((bytes[index++] & 0xFF) << 8) | (bytes[index++] & 0xFF);
        val.setValue(ival * 360 / Math.pow(2, 16));
        return 2;
    }
    
    private static int getGroundSpeed(byte[] bytes, int index, DValue val) {
        if (!Byter.validateIndex(index, bytes.length, 2)) return -1;
        int ival = Byter.getComplementNumber(new byte[] { bytes[index++], bytes[index++] });
        val.setValue(ival * 0.22);
        return 2;
    }
    
    private static int getPosition(byte[] bytes, int index, WGS84Coordinate position) {
        if (!Byter.validateIndex(index, bytes.length, 6)) return -1;
        int val = Byter.getComplementNumber(new byte[] { bytes[index++], bytes[index++], bytes[index++] });
        position.setLat(val * 180 / Math.pow(2, 23));
        val = Byter.getComplementNumber(new byte[] { bytes[index++], bytes[index++], bytes[index++] });
        position.setLng(val * 180 / Math.pow(2, 23));
        return 6;
    }
    
    private byte[] encodeTargetAddress() {
        return new byte[]{
            (byte) (this.address >> 16 & 0xFF),
            (byte) (this.address >> 8 & 0xFF),
            (byte) (this.address & 0xFF)};
    }
    
    private byte [] encodeTargetID() {
        int c0 = getCharCode(0);
        int c1 = getCharCode(1);
        int c2 = getCharCode(2);
        int c3 = getCharCode(3);
        int c4 = getCharCode(4);
        int c5 = getCharCode(5);
        int c6 = getCharCode(6);
        int c7 = getCharCode(7);
        byte b2 = (byte) ((c0 << 2) | (c1 >> 4 & 0x03));
        byte b3 = (byte) ((c1 << 4) | (c2 >> 2 & 0x0F));
        byte b4 = (byte) ((c2 << 6) | (c3 & 0x3F));
        byte b5 = (byte) ((c4 << 2) | (c5 >> 4 & 0x03));
        byte b6 = (byte) ((c5 << 4) | (c6 >> 2 & 0x0F));
        byte b7 = (byte) ((c6 << 6) | (c7 & 0x3F));
        return new byte[]{b2, b3, b4, b5, b6, b7};
    }
    
    private byte [] encodeMagneticHeading() {
        int val = (int) (this.magneticHeading / 0.0055);
        return new byte[]{(byte) (val >> 8 & 0xFF), (byte) (val & 0xFF)};
    }
    
    private byte [] encodeTrueAirSpeed() {
        return new byte[]{(byte) (this.trueAirSpeed >> 8 & 0xFF), (byte) (this.trueAirSpeed & 0xFF)};
    }
    
    private byte [] encodeTrackAngle() {
        int val = (int) (this.trackAngle * Math.pow(2, 16) / 360);
        return new byte[]{(byte) (val >> 8 & 0xFF), (byte) (val & 0xFF)};
    }
    
    private byte[] encodeGroundSpeed() {
        int ival = (int) (this.groundSpeed / 0.22);
        return encodeComplementNumberIn2Byte(ival);
    }
    
    private byte [] encodeComplementNumberIn2Byte(int val) {
        if (val > 0) {
            return new byte[]{(byte) (val >> 8 & 0xFF), (byte) (val & 0xFF)};
        }
        val = -val;
        val -= 0x01;
        return new byte[]{(byte) (~(val >> 8) & 0xFF), (byte) (~val & 0xFF)};
    }
    
    private static String getCode(byte b1, byte b2, byte b3) {
        String builder = "";
        // final StringBuilder builder = new StringBuilder();
        int code = b1 >> 2 & 0x3F;
        if (code != 0) builder += getChar(code);

        code = (b1 & 0x03) << 4 | (b2 >> 4 & 0x0F);
        if (code != 0) builder += getChar(code);
        
        code = (b2 & 0x0F) << 2 | (b3 >> 6 & 0x03);
        if (code != 0) builder += getChar(code);

        code = b3 & 0x3F;
        if (code != 0) builder += getChar(code);
        return builder;
    }
    
    private static char getChar(int i) {
        if (i <=26) return (char) (i+64);
        if (i==32) return ' ';
        return (char) i;
    }
    
    private int getCharCode(int index) {
        if (targetID.isEmpty() || targetID.length() < (index + 1) || index < 0) {
            return 32;
        }
        return getCharIndex(targetID.charAt(index));
    }
    
    private static int getCharIndex(char c) {
        int val = (int) c;
        if (val >= 64) val -= 64;
        return val;
    }

    /**
     * @return the items
     */
    public boolean[] getItems() {
        return items;
    }

    /**
     * @param items the items to set
     */
    public void setItems(boolean[] items) {
        this.items = items;
    }
    
    public void setItem(int index) {
        if (this.items.length <= index) return;
        this.items[index] = true;
    }
    
    public boolean getItem(int index) {
        return this.items[index];
    }

    /**
     * @return the address
     */
    public Integer getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(Integer address) {
        this.address = address;
    }

    /**
     * @return the targetID
     */
    public String getTargetID() {
        return targetID;
    }

    /**
     * @param targetID the targetID to set
     */
    public void setTargetID(String targetID) {
        this.targetID = targetID;
    }

    /**
     * @return the magneticHeading
     */
    public Double getMagneticHeading() {
        return magneticHeading;
    }

    /**
     * @param magneticHeading the magneticHeading to set
     */
    public void setMagneticHeading(Double magneticHeading) {
        this.magneticHeading = magneticHeading;
    }

    /**
     * @return the airSpeed
     */
    public AirSpeed getAirSpeed() {
        return airSpeed;
    }

    /**
     * @param airSpeed the airSpeed to set
     */
    public void setAirSpeed(AirSpeed airSpeed) {
        this.airSpeed = airSpeed;
    }

    /**
     * @return the trueAirSpeed
     */
    public Integer getTrueAirSpeed() {
        return trueAirSpeed;
    }

    /**
     * @param trueAirSpeed the trueAirSpeed to set
     */
    public void setTrueAirSpeed(Integer trueAirSpeed) {
        this.trueAirSpeed = trueAirSpeed;
    }

    /**
     * @return the selectedAlt
     */
    public SelectedAltitude getSelectedAlt() {
        return selectedAlt;
    }

    /**
     * @param selectedAlt the selectedAlt to set
     */
    public void setSelectedAlt(SelectedAltitude selectedAlt) {
        this.selectedAlt = selectedAlt;
    }

    /**
     * @return the finalStateSelectedAltitude
     */
    public FinalStateSelectedAltitude getFinalStateSelectedAltitude() {
        return finalStateSelectedAltitude;
    }

    /**
     * @param finalStateSelectedAltitude the finalStateSelectedAltitude to set
     */
    public void setFinalStateSelectedAltitude(FinalStateSelectedAltitude finalStateSelectedAltitude) {
        this.finalStateSelectedAltitude = finalStateSelectedAltitude;
    }

    /**
     * @return the trajectoryIntentStatus
     */
    public TrajectoryIntentStatus getTrajectoryIntentStatus() {
        return trajectoryIntentStatus;
    }

    /**
     * @param trajectoryIntentStatus the trajectoryIntentStatus to set
     */
    public void setTrajectoryIntentStatus(TrajectoryIntentStatus trajectoryIntentStatus) {
        this.trajectoryIntentStatus = trajectoryIntentStatus;
    }

    /**
     * @return the trajectoryIntentData
     */
    public TrajectoryIntentData getTrajectoryIntentData() {
        return trajectoryIntentData;
    }

    /**
     * @param trajectoryIntentData the trajectoryIntentData to set
     */
    public void setTrajectoryIntentData(TrajectoryIntentData trajectoryIntentData) {
        this.trajectoryIntentData = trajectoryIntentData;
    }

    /**
     * @return the capacityStatusReportByModeS
     */
    public CapacityStatusReportByModeS getCapacityStatusReportByModeS() {
        return capacityStatusReportByModeS;
    }

    /**
     * @param capacityStatusReportByModeS the capacityStatusReportByModeS to set
     */
    public void setCapacityStatusReportByModeS(CapacityStatusReportByModeS capacityStatusReportByModeS) {
        this.capacityStatusReportByModeS = capacityStatusReportByModeS;
    }

    /**
     * @return the statusReportByADSB
     */
    public StatusReportedByADSB getStatusReportByADSB() {
        return statusReportByADSB;
    }

    /**
     * @param statusReportByADSB the statusReportByADSB to set
     */
    public void setStatusReportByADSB(StatusReportedByADSB statusReportByADSB) {
        this.statusReportByADSB = statusReportByADSB;
    }

    /**
     * @return the resolutionAdvisoryReport
     */
    public ACASResolutionAdvisoryReport getResolutionAdvisoryReport() {
        return resolutionAdvisoryReport;
    }

    /**
     * @param resolutionAdvisoryReport the resolutionAdvisoryReport to set
     */
    public void setResolutionAdvisoryReport(ACASResolutionAdvisoryReport resolutionAdvisoryReport) {
        this.resolutionAdvisoryReport = resolutionAdvisoryReport;
    }

    /**
     * @return the barometricVerticalRate
     */
    public Double getBarometricVerticalRate() {
        return barometricVerticalRate;
    }

    /**
     * @param barometricVerticalRate the barometricVerticalRate to set
     */
    public void setBarometricVerticalRate(Double barometricVerticalRate) {
        this.barometricVerticalRate = barometricVerticalRate;
    }

    /**
     * @return the geometricverticalRate
     */
    public Double getGeometricverticalRate() {
        return geometricverticalRate;
    }

    /**
     * @param geometricverticalRate the geometricverticalRate to set
     */
    public void setGeometricverticalRate(Double geometricverticalRate) {
        this.geometricverticalRate = geometricverticalRate;
    }

    /**
     * @return the rollAgel <br/>
     * -180 £ Roll Angle £ 180
     */
    public Double getRollAgel() {
        return rollAgel;
    }

    /**
     * @param rollAgel <br/>
     * -180 £ Roll Angle £ 180
     */
    public void setRollAgel(Double rollAgel) {
        this.rollAgel = rollAgel;
    }

    /**
     * @return the trackAngleRate
     */
    public TrackAngleRate getTrackAngleRate() {
        return trackAngleRate;
    }

    /**
     * @param trackAngleRate the trackAngleRate to set
     */
    public void setTrackAngleRate(TrackAngleRate trackAngleRate) {
        this.trackAngleRate = trackAngleRate;
    }

    /**
     * @return the trackAngle
     */
    public Double getTrackAngle() {
        return trackAngle;
    }

    /**
     * @param trackAngle the trackAngle to set
     */
    public void setTrackAngle(Double trackAngle) {
        this.trackAngle = trackAngle;
    }

    /**
     * @return the groundSpeed <br/>
     * -2 NM/s <= Ground Speed < 2 NM/s
     */
    public Double getGroundSpeed() {
        return groundSpeed;
    }

    /**
     * @param groundSpeed  <br/>
     * -2 NM/s <= Ground Speed < 2 NM/s
     */
    public void setGroundSpeed(Double groundSpeed) {
        this.groundSpeed = groundSpeed;
    }

    /**
     * @return the velociryUncertainCategory
     */
    public Integer getVelociryUncertainCategory() {
        return velociryUncertainCategory;
    }

    /**
     * @param velociryUncertainCategory the velociryUncertainCategory to set
     */
    public void setVelociryUncertainCategory(Integer velociryUncertainCategory) {
        this.velociryUncertainCategory = velociryUncertainCategory;
    }

    /**
     * @return the metDate
     */
    public MetData getMetDate() {
        return metDate;
    }

    /**
     * @param metDate the metDate to set
     */
    public void setMetDate(MetData metDate) {
        this.metDate = metDate;
    }

    /**
     * @return the emiterCategory <br/>
     * 1 = light aircraft £ 7000 kg <br/>
     * 2 = reserved <br/>
     * 3 = 7000 kg < medium aircraft < 136000 kg <br/> 4 = reserved <br/>
     * 5 = 136000 kg £ heavy aircraft <br/>
     * 6 = highly manoeuvrable (5g acceleration capability) and high speed (>400 knots cruise) <br/>
     * 7 to 9 = reserved <br/>
     * 10 = rotocraft <br/>
     * 11 = glider / sailplane <br/>
     * 12 = lighter-than-air <br/>
     * 13 = unmanned aerial vehicle <br/>
     * 14 = space / transatmospheric vehicle <br/>
     * 15 = ultralight / handglider / paraglider <br/>
     * 16 = parachutist / skydiver <br/>
     * 17 to 19 = reserved <br/>
     * 20 = surface emergency vehicle <br/>
     * 21 = surface service vehicle <br/>
     * 22 = fixed ground or tethered obstruction <br/>
     * 23 to 24 = reserved
     */
    public Integer getEmiterCategory() {
        return emiterCategory;
    }

    /**
     * @param emiterCategory  <br/>
     * 1 = light aircraft £ 7000 kg <br/>
     * 2 = reserved <br/>
     * 3 = 7000 kg < medium aircraft < 136000 kg <br/> 4 = reserved <br/>
     * 5 = 136000 kg £ heavy aircraft <br/>
     * 6 = highly manoeuvrable (5g acceleration capability) and high speed (>400 knots cruise) <br/>
     * 7 to 9 = reserved <br/>
     * 10 = rotocraft <br/>
     * 11 = glider / sailplane <br/>
     * 12 = lighter-than-air <br/>
     * 13 = unmanned aerial vehicle <br/>
     * 14 = space / transatmospheric vehicle <br/>
     * 15 = ultralight / handglider / paraglider <br/>
     * 16 = parachutist / skydiver <br/>
     * 17 to 19 = reserved <br/>
     * 20 = surface emergency vehicle <br/>
     * 21 = surface service vehicle <br/>
     * 22 = fixed ground or tethered obstruction <br/>
     * 23 to 24 = reserved
     */
    public void setEmiterCategory(Integer emiterCategory) {
        this.emiterCategory = emiterCategory;
    }

    /**
     * @return the position
     */
    public WGS84Coordinate getPosition() {
        return position;
    }

    /**
     * @param position the position to set
     */
    public void setPosition(WGS84Coordinate position) {
        this.position = position;
    }

    /**
     * @return the geometricAltitude <br/>
     * -1500 ft <= Altitude <= 150000 ft
     */
    public Double getGeometricAltitude() {
        return geometricAltitude;
    }

    /**
     * @param geometricAltitude <br/>
     * -1500 ft <= Altitude <= 150000 ft
     */
    public void setGeometricAltitude(Double geometricAltitude) {
        this.geometricAltitude = geometricAltitude;
    }

    /**
     * @return the positionUncertain
     */
    public Integer getPositionUncertain() {
        return positionUncertain;
    }

    /**
     * @param positionUncertain the positionUncertain to set
     */
    public void setPositionUncertain(Integer positionUncertain) {
        this.positionUncertain = positionUncertain;
    }

    /**
     * @return the modeSMBData
     */
    public ModeSMBData getModeSMBData() {
        return modeSMBData;
    }

    /**
     * @param modeSMBData the modeSMBData to set
     */
    public void setModeSMBData(ModeSMBData modeSMBData) {
        this.modeSMBData = modeSMBData;
    }

    /**
     * @return the indicatedSpeed <br/>
     * 0 Kt <= Indicated Airspeed <= 1100 Kt
     */
    public Integer getIndicatedSpeed() {
        return indicatedSpeed;
    }

    /**
     * @param indicatedSpeed <br/>
     * 0 Kt <= Indicated Airspeed <= 1100 Kt
     */
    public void setIndicatedSpeed(Integer indicatedSpeed) {
        this.indicatedSpeed = indicatedSpeed;
    }

    /**
     * @return the machNo <br/>
     * 0 <= Mach Number <= 4.096
     */
    public Double getMachNo() {
        return machNo;
    }

    /**
     * @param machNo <br/>
     * 0 <= Mach Number <= 4.096
     */
    public void setMachNo(Double machNo) {
        this.machNo = machNo;
    }

    /**
     * @return the baroPresureSetting <br/>
     *  -0mb <= BPS <= 409.5 mb   
     */
    public Double getBaroPresureSetting() {
        return baroPresureSetting;
    }

    /**
     * @param baroPresureSetting <br/>
     *  -0mb <= BPS <= 409.5 mb
     */
    public void setBaroPresureSetting(Double baroPresureSetting) {
        this.baroPresureSetting = baroPresureSetting;
    }
}
