/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.cat02.v10;

import java.nio.ByteBuffer;
import java.util.List;

/**
 *
 * @author hong
 */
public class Decrypter02 {
    public static final int NO_ERR = 0;
    public static final int ERR_OVER_LENGTH = 1;
    public static final int ERR_WRONG_CATEGORY = 2;
    private static final byte[] masked = new byte[]{(byte) 0x80, (byte) 0x40, (byte) 0x20, (byte) 0x10, (byte) 0x08, (byte) 0x04, (byte) 0x02};
    private static final short ITEM_NUMBER = 14;
    
    public static int decodeHeader(byte[] bytes, int index, boolean [] header) {
        boolean isExtend = true;
        int bit;
        int headerIndex = 0;
        while (isExtend) {
            byte currentByte = bytes[index];
            for (int i = 1; i < 8; i++) {
                bit = currentByte & masked[i - 1];
                if (bit == 0) continue;
                int idx = headerIndex * 7 + i - 1;
                if (idx >= ITEM_NUMBER) {
                    System.out.println(String.format("Error while reading header: index is out of boundary of array size. (index: %s) (size: %s)", idx, ITEM_NUMBER));
                    return -1;
                }
                header[idx] = true;
            }

            bit = currentByte & 0x01;
            isExtend = (bit != 0);
            index++;
            headerIndex++; // Increasing readed bytes
        }
        return headerIndex;
    }
    
    public static int decode(byte [] bytes, List<Message02> messages) {
        if (bytes.length <= 3) return ERR_OVER_LENGTH;
        int cate = bytes[0] & 0xFF;
        
        //System.out.println("Category: " + cate);
        
        if (cate != 2) return ERR_WRONG_CATEGORY;
        int length = (((bytes[1] & 0xFF) << 8) | (bytes[2] & 0xFF));
        //System.out.println("Length: " + length);
        int index = 3;
        int count = 0;
        while (index < length) {
            Message02 message = new Message02();
            count = decode(bytes, index, message);
            if (count <= 0) break;
            index += count;
            messages.add(message);
        }
        //System.out.println(String.format("Length: %s Index: %s", length, index));
        return NO_ERR;
    }
    
    public static byte[] decode02(byte [] bytes, List<Message02> messages) {
        if (bytes.length <= 3) 
            return null;
        int cate = bytes[0] & 0xFF;
        
        //System.out.println("Category: " + cate);
        
        if (cate != 2) 
            return null;
        int length = (((bytes[1] & 0xFF) << 8) | (bytes[2] & 0xFF));
        //System.out.println("Length: " + length);
        int index = 3;
        int count = 0;
        while (index < length) {
            Message02 message = new Message02();
            count = decode(bytes, index, message);
            if (count <= 0) break;
            index += count;
            messages.add(message);
        }
        //System.out.println(String.format("Length: %s Index: %s", length, index));
        if (index < bytes.length){
                byte [] newbytes =  new byte[bytes.length - index + 1];
                System.arraycopy(bytes, index - 1, newbytes, 0, bytes.length - index + 1);
                return newbytes;
        }
        return new byte[0];
    }
    
    public static int decode(byte[] bytes, Integer index, Message02 message) {
        int start = index;
        boolean[] header = new boolean[ITEM_NUMBER];
        int count = decodeHeader(bytes, index, header);
        if (count == 0) return -1;
        index += count;
        
        for (int i =0;i<ITEM_NUMBER;i++){
            if (!header[i]) continue;
            switch (i) {
                case 0: // 1 I002/010 Data Source Identifier
                    DataSourceID source = new DataSourceID();
                    count = DataSourceID.extract(bytes, index, source);
                    if (count <= 0) return -1;
                    message.setSic(source.getSic());
                    message.setSac(source.getSac());
                    index += count;
                    //System.out.println(String.format("> I002/010 Data Source Identifier %s (bytes)", count));
                    break;
                    
                case 1: // 2 I002/000 Message Type
                    IVal messageType = new IVal();
                    count = extractMessageType(bytes, index, messageType);
                    if (count < 0) break;
                    index += count;
                    message.setMesageType(messageType.val());
                    //System.out.println(String.format("> I002/000 Message Type %s (bytes)", count));
                    break;
                    
                case 2: // 3 I002/020 Sector Number
                    DVal sectorNumber = new DVal();
                    count = extractSectorNumber(bytes, index, sectorNumber);
                    if (count < 0) break;
                    index += count;
                    message.setSectorNumber(sectorNumber.val());
                    //System.out.println(String.format("> I002/020 Sector Number %s (bytes)", count));
                    break;
                    
                case 3: // 4 I002/030 Time of Day
                    DVal timeofday = new DVal();
                    count = extractTimeOfDay(bytes, index, timeofday);
                    if (count < 0) break;
                    index += count;
                    message.setTimeOfDay(timeofday.val());
                    //System.out.println(String.format("> I002/030 Time of Day %s (bytes)", count));
                    break;
                    
                case 4: // 5 I002/041 Antenna Rotation Period
                    DVal rotationSpeed = new DVal();
                    count = extractAntennaRotationSpeed(bytes, index, rotationSpeed);
                    if (count < 0) break;
                    index += count;
                    message.setAntennaRotationSpeed(rotationSpeed.val());
                    //System.out.println(String.format("> I002/041 Antenna Rotation Period %s (bytes)", count));
                    break;
                    
                case 5: // 6 I002/050 Station Configuration Status
                    IVal configurationStatus = new IVal();
                    count = extractConfigurationConfig(bytes, index, configurationStatus);
                    if (count <= 0) break;
                    index += count;
                    message.setStationConfigurationStatus(configurationStatus.val());
                    //System.out.println(String.format("> I002/050 Station Configuration Status %s (bytes)", count));
                    break;
                    
                case 6: // 7 I002/060 Station Processing Mode
                    IVal configurationMode = new IVal();
                    count = extractConfigurationConfig(bytes, index, configurationMode);
                    if (count <= 0) break;
                    index += count;
                    message.setStationConfigurationStatus(configurationMode.val());
                    //System.out.println(String.format("> I002/060 Station Processing Mode %s (bytes)", count));
                    break;
                    
                case 7: // 8 I002/070 Plot Count Values
                    PlotCountValues plotCountValue = new PlotCountValues();
                    count = PlotCountValues.extract(bytes, index, plotCountValue);
                    if (count <= 0) break;
                    index += count;
                    message.setPlotcountValue(plotCountValue);
                    //System.out.println(String.format("> I002/070 Plot Count Values %s (bytes)", count));
                    break;
                    
                case 8: // 9 I002/100 Dynamic Window - Type 1
                    DynamicWindow dynamicWindow = new DynamicWindow();
                    count = DynamicWindow.extract(bytes, index, dynamicWindow);
                    if (count <= 0) break;
                    index += count;
                    message.setDynamicWindow(dynamicWindow);
                    //System.out.println(String.format("> I002/100 Dynamic Window - Type 1 %s (bytes)", count));
                    break;
                case 9: // 10 I002/090 Collimation Error
                    CollimationError collimationError = new CollimationError();
                    count = CollimationError.extract(bytes, index, collimationError);
                    if (count <= 0) break;
                    index += count;
                    message.setCollimationError(collimationError);
                    //System.out.println(String.format("> I002/090 Collimation Error %s (bytes)", count));
                    break;
                    
                case 10: // 11 I002/080 Warning/Error Conditions
                    WarningErrorCondition warmingErrorCondition = new WarningErrorCondition();
                    count = WarningErrorCondition.extract(bytes, index, warmingErrorCondition);
                    if (count <= 0) break;
                    index += count;
                    message.setWarningConditionError(warmingErrorCondition);
                    //System.out.println(String.format("> I002/080 Warning/Error Conditions %s (bytes)", count));
                    break;
                    
                case 11: // 12 - Spare
                    break;
                case 12: // 13 - Reserved for SP Indicator
                    index += skipBit13(bytes, index);
                    break;
                case 13: // 14 - Reserved for RFS Indicator (RS-bit)
                    index += skipBit14(bytes, index);
                    break;
            }
        }
        
        if (count <= 0) return -1;
        index += count;
        
        
        // System.out.println("Read byte: " + count);
        // System.out.println("Index : " + index);
        
        int extractByte = index - start;
        int totalByte = extractByte + 3;
        //System.out.println(String.format("Read : %s -> %s length: %s", start, index, extractByte));
        //System.out.println("-------------------------------------------------------------------------------------");
        // System.out.println("length: " + extractByte);
        if (start + extractByte > bytes.length) extractByte = bytes.length - start;
        final byte[] contentsByte = new byte[totalByte];
        System.arraycopy(bytes, start, contentsByte, 3, extractByte);
        contentsByte[0] = 01;
        contentsByte[1] = (byte) ((totalByte >> 8) & 0xFF);
        contentsByte[2] = (byte) (totalByte & 0xFF);
        message.setBinary(contentsByte);
        return extractByte;
    }
    
    private static int extractMessageType(byte [] bytes, int index, IVal val) {
        if (!Byter.validateIndex(index, bytes.length, 1)) return -1;
        int value = bytes[index++] & 0xFF;
        val.set(value);
        return 1;
    }
    
    private static int extractSectorNumber(byte [] bytes, int index, DVal val) {
        if (!Byter.validateIndex(index, bytes.length, 1)) return -1;
        int value = bytes[index++] & 0xFF;
        double dvalue = (double) value * 360 / Math.pow(2, 8);
        val.setValue(dvalue);
        return 1;
    }
    
    private static int extractTimeOfDay(byte [] bytes, int index, DVal val) {
        if (!Byter.validateIndex(index, bytes.length, 3)) return -1;
        int value = ByteBuffer.wrap(new byte[]{0x00, bytes[index++], bytes[index++], bytes[index++]}).getInt();
        val.setValue((double) value / 128);
        return 3;
    }
    
    private static int extractAntennaRotationSpeed(byte [] bytes, int index, DVal val) {
        if (!Byter.validateIndex(index, bytes.length, 2)) return -1;
        int value = ByteBuffer.wrap(new byte[]{0x00, 0x00, bytes[index++], bytes[index++]}).getInt();
        val.setValue((double) value / 128);
        return 2;
    }
    
    private static int extractConfigurationConfig(byte [] bytes, int index, IVal val) {
        boolean extend = true;
        int count = 0;
        int value = 0;
        while (extend) {
            if (!Byter.validateIndex(index, bytes.length, 1)) return -1;
            byte cbyte = bytes[index++];
            count++;
            value = value << 7 | ((cbyte >> 1) | 0x7F);
            extend = (cbyte & 0x01) > 0;
        }
        val.setValue(value);
        return count;
    }
    
    private static int skipBit13(byte [] bytes, Integer index) {
        int length = bytes[index] & 0xFF;
        return length;
    }
    
    private static int skipBit14(byte [] bytes, Integer index) {
        int length = bytes[index] & 0xFF;
        return length;
    }
    
    
}
