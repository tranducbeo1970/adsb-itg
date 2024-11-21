/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.cat34.v127;

import java.nio.ByteBuffer;
import java.util.List;

/**
 *
 * @author andh
 */
public class Decrypt34 {

    public static final int NO_ERR = 0;
    public static final int ERR_OVER_LENGTH = 1;
    public static final int ERR_WRONG_CATEGORY = 2;
    private static final short ITEM_NUMBER = 14;

    public static int decode(byte[] bytes, List<Message34> messages) {
        if (bytes.length <= 3) {
            return ERR_OVER_LENGTH;
        }
        int cate = bytes[0] & 0xFF;
        if (cate != 34) {
            return ERR_WRONG_CATEGORY;
        }

        int length = (((bytes[1] & 0xFF) << 8) | (bytes[2] & 0xFF));
        //System.out.println("Category: " + cate + "  Length: " + length);

        int index = 3;
        int count = 0;
        while (index < length) {
            Message34 message = new Message34();
            count = decode(bytes, index, message);
            if (count <= 0) {
                break;
            }
            index += count;
            messages.add(message);
        }
        //System.out.println(String.format("Length: %s Index: %s", length, index));
        return NO_ERR;

    }

    public static byte[] decode34(byte[] bytes, List<Message34> messages) {
        if (bytes.length <= 3) {
            return null;
        }
        int cate = bytes[0] & 0xFF;
        if (cate != 34) {
            return null;
        }

        int length = (((bytes[1] & 0xFF) << 8) | (bytes[2] & 0xFF));
        //System.out.println("Category: " + cate + "  Length: " + length);

        int index = 3;
        int count = 0;
        while (index < length) {
            Message34 message = new Message34();
            count = decode(bytes, index, message);
            if (count <= 0) {
                break;
            }
            index += count;
            messages.add(message);
        }
        //System.out.println(String.format("Length: %s Index: %s", length, index));
        if (index < bytes.length) {
            byte[] newbytes = new byte[bytes.length - index + 1];
            System.arraycopy(bytes, index - 1, newbytes, 0, bytes.length - index + 1);
            return newbytes;
        }
        return new byte[0];

    }

    public static int decode(byte[] bytes, int index, Message34 message) {
        try {
            int start = index;
            boolean[] header = new boolean[ITEM_NUMBER];
            int count = Byter.decodeHeader(bytes, index, header);
            if (count == 0) {
                return -1;
            }
            index += count;

            for (int i = 2; i < ITEM_NUMBER; i++) {
                if (!header[i]) {
                    continue;
                }
                switch (i) {

                    case 0: // 1 I034/010 Data Source Identifier
                        DataSourceID source = new DataSourceID();
                        count = DataSourceID.extract(bytes, index, source);
                        if (count <= 0) {
                            return -1;
                        }
                        message.setSic(source.getSic());
                        message.setSac(source.getSac());
                        index += count;
                        //System.out.println(String.format("I048/010 Data Source Identifier %s(bytes)", count));
                        break;

                    case 1: // 2 I034/000 Message Type
                        IValue messagetType = new IValue();
                        count = extractMessageType(bytes, index, messagetType);
                        if (count <= 0) {
                            return -1;
                        }
                        message.setType(messagetType.val());
                        index += count;
                        //System.out.println(String.format("I034/000 Message Type %s(bytes)", count));
                        break;

                    case 2: // 3 I034/030 Time-of-Day
                        DValue timeofDay = new DValue();
                        count = extractTimeOfDay(bytes, index, timeofDay);
                        if (count <= 0) {
                            return -1;
                        }
                        message.setTimeOfDay(timeofDay.getValue());
                        index += count;
                        //System.out.println(String.format("I034/030 Time-of-Day %s(bytes)", count));
                        break;

                    case 3: // 4 I034/020 Sector Number
                        DValue sectorNumber = new DValue();
                        count = extractSectorNumber(bytes, index, sectorNumber);
                        if (count <= 0) {
                            return -1;
                        }
                        message.setTimeOfDay(sectorNumber.getValue());
                        index += count;
                        //System.out.println(String.format("I034/020 Sector Number %s(bytes)", count));
                        break;

                    case 4: // 5 I034/041 Antenna Rotation Period
                        DValue anntennaRotationSpeed = new DValue();
                        count = extractAntennaRotationSpeed(bytes, index, anntennaRotationSpeed);
                        if (count <= 0) {
                            return -1;
                        }
                        message.setAntennaRotationSpeed(anntennaRotationSpeed.getValue());
                        index += count;
                        //System.out.println(String.format("I034/041 Antenna Rotation Period %s(bytes)", count));
                        break;

                    case 5: // 6 I034/050 System Configuration and Status
                        SystemConfigurationAndStatus systemConfigStatus = new SystemConfigurationAndStatus();
                        count = SystemConfigurationAndStatus.extract(bytes, index, systemConfigStatus);
                        if (count <= 0) {
                            return -1;
                        }
                        message.setSysConfigStatus(systemConfigStatus);
                        index += count;
                        //System.out.println(String.format("I034/050 System Configuration and Status %s(bytes)", count));
                        break;

                    case 6: // 7 I034/060 System Processing Mode
                        SystemProcessMode systemProcssingMode = new SystemProcessMode();
                        count = SystemProcessMode.extract(bytes, index, systemProcssingMode);
                        if (count <= 0) {
                            return -1;
                        }
                        message.setProcessingMode(systemProcssingMode);
                        index += count;
                        //System.out.println(String.format("I034/060 System Processing Mode %s(bytes)", count));
                        break;

                    case 7: // 8 I034/070 Message Count Values
                        MessageCountValue messageCountValue = new MessageCountValue();
                        count = MessageCountValue.extract(bytes, index, messageCountValue);
                        if (count <= 0) {
                            return -1;
                        }
                        message.setMessageCountValue(messageCountValue);
                        index += count;
                        //System.out.println(String.format("I034/070 Message Count Values %s(bytes)", count));
                        break;

                    case 8: // 9 I034/100 Generic Polar Window
                        DynamicWindow dynamicWindow = new DynamicWindow();
                        count = DynamicWindow.extract(bytes, index, dynamicWindow);
                        if (count <= 0) {
                            return -1;
                        }
                        message.setWindows(dynamicWindow);
                        index += count;
                        //System.out.println(String.format("I034/100 Generic Polar Window %s(bytes)", count));
                        break;

                    case 9: // 10 I034/110 Data Filter
                        IValue dataFilter = new IValue();
                        count = extractDataFilter(bytes, index, dataFilter);
                        if (count <= 0) {
                            return -1;
                        }
                        message.setDataFilter(dataFilter.val());
                        index += count;
                        //System.out.println(String.format("I034/110 Data Filter %s(bytes)", count));
                        break;

                    case 10: // 11 I034/120 3D-Position of Data Source
                        PositionOfDataSource position = new PositionOfDataSource();
                        count = PositionOfDataSource.extract(bytes, index, position);
                        if (count <= 0) {
                            return -1;
                        }
                        message.setPositionDataSource(position);
                        index += count;
                        //System.out.println(String.format("I034/120 3D-Position of Data Source %s(bytes)", count));
                        break;

                    case 11: // 12 I034/090 Collimation Error
                        CollimationError collimationError = new CollimationError();
                        count = CollimationError.extract(bytes, index, collimationError);
                        if (count <= 0) {
                            return -1;
                        }
                        message.setCollimationError(collimationError);
                        index += count;
                        //System.out.println(String.format("I034/090 Collimation Error %s(bytes)", count));
                        break;

                    case 12: // 13 RE-Data Item Reserved Expansion Field
                        count = skip(bytes, index);
                        index += count;
                        //System.out.println(String.format("RE-Data Item Reserved Expansion Field %s(bytes)", count));
                        break;

                    case 13: // 14 SP-Data Item Special Purpose Field
                        count = skip(bytes, index);
                        index += count;
                        //System.out.println(String.format("SP-Data Item Special Purpose Field %s(bytes)", count));
                        break;
                }
            }

            if (count <= 0) {
                return -1;
            }
            index += count;
            int extractByte = index - start;
            int totalByte = extractByte + 3;

            //System.out.println("-----------------------------------------------------------------------------------------------");
            //System.out.println(String.format("index: %s - %s read: %s", start, index, extractByte));

            if (start + extractByte > bytes.length) {
                extractByte = bytes.length - start;
            }
            final byte[] contentsByte = new byte[totalByte];
            System.arraycopy(bytes, start, contentsByte, 3, extractByte);
            contentsByte[0] = 01;
            contentsByte[1] = (byte) ((totalByte >> 8) & 0xFF);
            contentsByte[2] = (byte) (totalByte & 0xFF);
            message.setBinary(contentsByte);
            return (index - start);

        } catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    private static int extractMessageType(byte[] bytes, int index, IValue value) {
        if (!Byter.validateIndex(index, bytes.length, 1)) {
            return -1;
        }
        value.setValue(bytes[index++] & 0xFF);
        return 1;
    }

    private static int extractTimeOfDay(byte[] bytes, int index, DValue dVal) {
        if (!Byter.validateIndex(index, bytes.length, 3)) {
            return -1;
        }
        int value = ByteBuffer.wrap(new byte[]{0x00, bytes[index++], bytes[index++], bytes[index++]}).getInt();
        dVal.setValue((double) value / 128);
        return 3;
    }

    private static int extractSectorNumber(byte[] bytes, int index, DValue dVal) {
        if (!Byter.validateIndex(index, bytes.length, 1)) {
            return -1;
        }
        int value = bytes[index++] & 0xFF;
        dVal.setValue((double) value * 360 / Math.pow(2, 8));
        return 1;
    }

    private static int extractAntennaRotationSpeed(byte[] bytes, int index, DValue dVal) {
        if (!Byter.validateIndex(index, bytes.length, 2)) {
            return -1;
        }
        int value = ByteBuffer.wrap(new byte[]{0x00, 0x00, bytes[index++], bytes[index++]}).getInt();
        dVal.setValue((double) value / 128);
        return 2;
    }

    private static int extractDataFilter(byte[] bytes, int index, IValue value) {
        if (!Byter.validateIndex(index, bytes.length, 1)) {
            return -1;
        }
        value.setValue(bytes[index++] & 0xFF);
        return 1;
    }

    private static int skip(byte[] bytes, Integer index) {
        int length = bytes[index] & 0xFF;
        return length;
    }
}
