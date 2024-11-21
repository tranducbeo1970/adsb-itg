/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asterix.cat21;

import com.attech.asterix.cat21.entities.Altitude;
import com.attech.asterix.cat21.entities.DataSourceIdentification;
import com.attech.asterix.cat21.entities.Position;
import com.attech.asterix.cat21.entities.TargetAddress;
import com.attech.asterix.cat21.entities.TargetReportDescriptor;
import com.attech.asterix.cat21.entities.TimeOfDay;
import com.attech.asterix.cat21.util.BitwiseUtils;
import java.nio.ByteBuffer;

/**
 *
 * @author andh
 */
public class MessageEncryptor {
    public byte[] encrypt(Message message) {
          return null;
    }
    
    public byte[]  getDataSourceIden(DataSourceIdentification sourceInden) {
        return new byte[] { (byte) sourceInden.getSac(), (byte) sourceInden.getSic() };
    }
    
    public byte []  getTargetReportDescriptor(TargetReportDescriptor descriptor) {
       int bit1 = descriptor.isIsDifferentialCorrection() ? 1 : 0;
        bit1 = bit1 << 1 |  (descriptor.isIsGroundBitSet() ? 1 : 0);
        bit1 = bit1 << 1 | (descriptor.isIsSimulatedTargetReport() ? 1 : 0);
        bit1 = bit1 << 1 | (descriptor.isIsTestTarget() ? 1 : 0);
        bit1 = bit1 << 1 | (descriptor.isIsReportFromFieldMonitor() ? 1 : 0);
        bit1 = bit1 << 1 | (descriptor.isIsEquipementCapable() ? 1 : 0);
        bit1 = bit1 << 1 | (descriptor.isIsSpecialPositionIdentification() ? 1 : 0);
        bit1 = bit1 << 1;
        
        int bit2 = descriptor.getAtpValue();
        bit2 = bit2 << 2 | descriptor.getAltitudeReportingCapability();
        bit2 = bit2 << 3;
        
        return new byte[] { (byte)bit1, (byte)bit2 };
    }
    
    public byte[] getTimeOfDay(TimeOfDay timeOfDay) {
        int ellapseTime = Math.round(timeOfDay.getValue() * 128 / 1000);
        byte byte1 = (byte) (ellapseTime >> 16 & BitwiseUtils.maskedByte);
        byte byte2 = (byte) (ellapseTime >> 8 & BitwiseUtils.maskedByte);
        byte byte3 = (byte) (ellapseTime & BitwiseUtils.maskedByte);
        return new byte[]{byte1, byte2, byte3};
    }
    
    public byte[] getPosition(Position position) {
        byte[] latitudeInByte = extractToByte(position.getLatitude());
        byte[] longtitudeInByte = extractToByte(position.getLongtitude());
        return new byte[]{latitudeInByte[0], latitudeInByte[1], latitudeInByte[2], longtitudeInByte[0], longtitudeInByte[1], longtitudeInByte[2]};
    }
    
    public byte[] getTargetAddress(TargetAddress targetAddress) {
        return ByteBuffer.allocate(3).putInt(targetAddress.getValue()).array();
    }
    
     public byte[] getAltitude(Altitude altitude) {
        int alt = (int) Math.round(altitude.getValue() / 6.25);
        alt = alt > 0 ? alt : ~Math.abs(alt) + 0x01;
        return ByteBuffer.allocate(2).putInt(alt).array();
    }
    
    private byte[] extractToByte(double value) {
        int intValue = (int) Math.round(value / (2.145767 * 0.00001));
        intValue = intValue > 0 ? intValue : ~Math.abs(intValue) + 0x01;
        return ByteBuffer.allocate(3).putInt(intValue).array();
    }
}
