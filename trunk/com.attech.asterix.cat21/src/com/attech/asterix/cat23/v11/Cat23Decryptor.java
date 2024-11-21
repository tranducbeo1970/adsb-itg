/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asterix.cat23.v11;

import com.attech.asterix.cat21.entities.DataSourceIdentification;
import com.attech.asterix.cat21.v21.Message;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author andh
 */
public class Cat23Decryptor {

    private int length;
    private int index;
    private static final byte[] masked = new byte[]{(byte) 0x80, (byte) 0x40, (byte) 0x20, (byte) 0x10, (byte) 0x08, (byte) 0x04, (byte) 0x02};
    private static final short ITEM_NUMBER = 14;
    
    public Cat23Message decrypt(byte[] bytes) {
        // categoryIndex = bytes[0] & 0xFF;
        // if (categoryIndex != 21) return null;

        length = bytes[1] & 0xFF;
        length = length << 8 | bytes[2] & 0xFF;

        index = 0;
        index += 3;

        final List<Message> messages = new ArrayList<>();
        while (index < length) {

            // System.out.println("index: " + index);
            final int firstIndex = index;
            final boolean[] header = getHeader(bytes);

            // Message message = new Message(bytes);
            Cat23Message message = new Cat23Message();
            for (int i = 0; i < ITEM_NUMBER; i++) {
                if (!header[i])
                    continue;

                // System.out.println("index: " + index);
                switch (i) {
                    case 0: // DataSource Indentification
                        // System.out.println("DataSource Indentification");
                        DataSourceIdentification sourceIdentification = getDataSourceIden(bytes);
                        message.setSac(sourceIdentification.getSac());
                        message.setSic(sourceIdentification.getSic());
                        break;
                    case 1: // Target Report Descriptor
                        // System.out.println("Target Report Descriptor");
                        // message.setTargetDescriptor(getTargetReportDescriptor(bytes));
                        break;
                    case 2: // Track Number
                        // System.out.println("Track Number");
                        // message.setTrackNumber(ByteBuffer.wrap(new byte[]{0x00, 0x00, bytes[index], bytes[index + 1]}).getInt());
                        index += 2;
                        break;
                    case 3: // Service Identification
                        // System.out.println("Service Identification");
                        // message.setServiceIdentification(bytes[index] & 0xFF);
                        index += 1;
                        break;
                    case 4: // Time of Applicability for Position
                        // System.out.println("Time of Applicability for Position");
                        // message.setTimeOfAplicabilityPosition(getTime(bytes));
                        break;
                    case 5: // Position in WGS-84 co-ordinates
                        // System.out.println("Position in WGS-84 co-ordinates");
                        // message.setPosition(getPosition(bytes));
                        break;
                    case 6: // Position in WGS-84 co-ordinates, high res.
                        // System.out.println("Position in WGS-84 co-ordinates, high res.");
                        // message.setHighResolutionPosition(getHighResolutionPosition(bytes));
                        break;
                    case 7: // Time of Applicability for Velocity
                        // System.out.println("Time of Applicability for Velocity.");
                        // message.setTimeOfAplicabilityVelocity(getTime(bytes));
                        break;
                    case 8: // Air Speed
                        // System.out.println("Air Speed");
                        // message.setAirSpeed(getAirSpeed(bytes));
                        break;
                    case 9: // True Air Speed
                        // System.out.println("True Air Speed");
                        // message.setTrueAirSpeed(getTrueAirSpeed(bytes));
                        break;
                    case 10: // Target Address
                        // System.out.println("Target Address");
                        // message.setTargetAddress(getTargetAddress(bytes));
                        break;
                    case 11: // Time of Message Reception of Position
                        // System.out.println("Time of Message Reception of Position");
                        // message.setTimeOfMessageReceptionOfPosition(getTime(bytes));
                        break;
                    case 12: // Time of Message Reception of Position-High Precision
                        // System.out.println("Time of Message Reception of Position-High Precision");
                        // message.setTimeOfMessageReceptionOfPositionHightPrecisions(getTimeOfHighResolution(bytes));
                        break;
                    case 13: // Time of Message Reception of Velocity
                        // System.out.println("Time of Message Reception of Velocity");
                        // message.setTimeOfMessageReceptionOfVelocity(getTime(bytes));
                        break;
                    case 14: // Time of Message Reception of Velocity-High Precision
                        // System.out.println("Time of Message Reception of Velocity-High Precision");
                        // message.setTimeOfMessageReceptionOfVelocityHightPrecision(getTimeOfHighResolution(bytes));
                        break;
                    default:
                        break;
                }
            }

            return message;
        }
        // if (index != length) throw new Exception("Index is not equal length");
        return null;
    }
        
    public boolean[] getHeader(byte[] bytes) {

        boolean[] header = new boolean[ITEM_NUMBER];
        boolean isExtend = true;
        int bit = 0;
        int headerIndex = 0;
        while (isExtend) {

            byte currentByte = bytes[index];
            for (int i = 1; i < 8; i++) {
                bit = currentByte & masked[i - 1];
                if (bit == 0)
                    continue;
                header[headerIndex * 7 + i - 1] = true;
            }

            bit = currentByte & 0x01;
            isExtend = (bit != 0);
            index++;
            headerIndex++; // Increasing readed bytes
        }
        return header;
    }

    public DataSourceIdentification getDataSourceIden(byte[] bytes) {
        DataSourceIdentification source = new DataSourceIdentification();
        source.setSac((short) (bytes[index++] & 0xFF));
        //index++;
        source.setSic((short) (bytes[index++] & 0xFF));
        //index++;
        return source;
    }
    
}
