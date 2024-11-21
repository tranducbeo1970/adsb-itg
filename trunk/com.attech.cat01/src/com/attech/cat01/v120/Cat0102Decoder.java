/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.cat01.v120;

import com.attech.cat02.v10.CollimationError;
import com.attech.cat02.v10.Decrypter02;
import static com.attech.cat02.v10.Decrypter02.NO_ERR;
import static com.attech.cat02.v10.Decrypter02.decode;
import static com.attech.cat02.v10.Decrypter02.decodeHeader;
import com.attech.cat02.v10.DynamicWindow;
import com.attech.cat02.v10.Message02;
import com.attech.cat02.v10.PlotCountValues;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author anbk
 */
public class Cat0102Decoder {

    public static final int NO_ERR = 0;
    public static final int ERR_OVER_LENGTH = 1;
    public static final int ERR_WRONG_CATEGORY = 2;

    private static final byte[] masked = new byte[]{(byte) 0x80, (byte) 0x40, (byte) 0x20, (byte) 0x10, (byte) 0x08, (byte) 0x04, (byte) 0x02};
    private static final short ITEM_NUMBER = 35;
    private static final int f = 6;

    private static boolean debug;
    public static String ERROR;
    public static String ERROR_MESSAGE;

    public static void setDebug(boolean value) {
        debug = value;
    }

    public static int decodeHeader(byte[] bytes, int index, boolean[] header) {
        // boolean [] header = new boolean[ITEM_NUMBER];
        boolean isExtend = true;
        int bit;
        int headerIndex = 0;
        while (isExtend) {
            byte currentByte = bytes[index];
            // System.out.print(" " + Integer.toHexString(bytes[index] & 0xFF) + "  [" + Integer.toBinaryString(bytes[index]  & 0xFF) + "] ");
            for (int i = 1; i < 8; i++) {
                bit = currentByte & masked[i - 1];
                if (bit == 0) {
                    continue;
                }
                int idx = headerIndex * 7 + i - 1;
                // TODO: remove later
                /*
                if (idx >= ITEM_NUMBER) {
                    if (debug) System.out.println(String.format("Error while reading header: index is out of boundary of array size. (index: %s) (size: %s)", idx, ITEM_NUMBER));
                    return -1;
                }
                 */
                header[idx] = true;
            }

            bit = currentByte & 0x01;
            isExtend = (bit != 0);
            index++;
            headerIndex++; // Increasing readed bytes
        }
        // System.out.println(" ");
        return headerIndex;
    }

    public static int decodeCat02Cat01(byte[] bytes, List<Cat01Message> messages, List<Message02> messages02) {
        ERROR = null;
        ERROR_MESSAGE = null;
        try {
            if (bytes.length <= 3) {
                return ERR_OVER_LENGTH;
            }
            int cate = bytes[0] & 0xFF;
            switch (cate) {
                case 1:
                    int length = (((bytes[1] & 0xFF) << 8) | (bytes[2] & 0xFF));
                    int index = 3;
                    int count = 0;
                    int defaultSic = 0;
                    while (index < length) {
                        if (debug) {
                            System.out.println(String.format("Length: %s Index: %s", length, index));
                        }
                        Cat01Message message = new Cat01Message();
                        count = decode(bytes, index, message);
                        if (count <= 0) {
                            break;
                        }
                        index += count;
                        if (message.getSic() != null) {
                            defaultSic = message.getSic();
                        } else {
                            message.setSic(defaultSic);
                        }
                        messages.add(message);

                    }
                    if (debug) {
                        System.out.println(String.format("Length: %s Index: %s", length, index));
                    }
                    return NO_ERR;

                case 2:
                    //List<Message02> msgs02 = new ArrayList<>();
                    //Decrypter02.decode(bytes, msgs02);
                    length = (((bytes[1] & 0xFF) << 8) | (bytes[2] & 0xFF));
                    System.out.println("Length: " + length);
                    index = 3;
                    count = 0;
                    while (index < length) {
                        Message02 msg = new Message02();
                        count = decode(bytes, index, msg);
                        if (count <= 0) break;
                        index += count;
                        messages02.add(msg);
                    }
                    System.out.println(String.format("Length: %s Index: %s", length, index));
                    //int firstLength = length + 3;
                    if (index < bytes.length){ // van con package 01
                        byte [] newbytes =  new byte[bytes.length - length];
                        System.arraycopy(bytes, length, newbytes, 0, bytes.length - length);
                        decode(newbytes, messages);
                    }
                    return NO_ERR;
                default:
                    ERROR_MESSAGE = bytesToHex(bytes);
                    ERROR = "This message category is not Asterix Cat 01";
                    return -1;
            }

        } catch (Exception ex) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            ERROR_MESSAGE = bytesToHex(bytes);
            ERROR = sw.toString();
            return -1;
        }
    }

    public static int decode(byte[] bytes, List<Cat01Message> messages) {
        ERROR = null;
        ERROR_MESSAGE = null;
        try {
            if (bytes.length <= 3) {
                return ERR_OVER_LENGTH;
            }
            int cate = bytes[0] & 0xFF;
            if (cate != 1) {
                ERROR_MESSAGE = bytesToHex(bytes);
                ERROR = "This message category is not Asterix Cat 01";
                return -1;
            }
            int length = (((bytes[1] & 0xFF) << 8) | (bytes[2] & 0xFF));
            int index = 3;
            int count = 0;
            int defaultSic = 0;
            while (index < length) {
                if (debug) {
                    System.out.println(String.format("Length: %s Index: %s", length, index));
                }
                Cat01Message message = new Cat01Message();
                count = decode(bytes, index, message);
                if (count <= 0) {
                    break;
                }
                index += count;
                if (message.getSic() != null) {
                    defaultSic = message.getSic();
                } else {
                    message.setSic(defaultSic);
                }
                messages.add(message);

            }
            if (debug) {
                System.out.println(String.format("Length: %s Index: %s", length, index));
            }
            return NO_ERR;
        } catch (Exception ex) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            ERROR_MESSAGE = bytesToHex(bytes);
            ERROR = sw.toString();
            return -1;
        }
    }

    public static int decode(byte[] bytes, Integer index, Cat01Message message) {
        int start = index;
        boolean[] header = new boolean[ITEM_NUMBER];
        int count = decodeHeader(bytes, index, header);
        if (count == 0) {
            return -1;
        }
        index += count;
        // 1 I001/010 Data Source Identifier
        if (header[0]) {
            DataSourceID source = new DataSourceID();
            count = DataSourceID.extract(bytes, index, source);
            if (count <= 0) {
                return -1;
            }
            message.setSic(source.getSic());
            message.setSac(source.getSac());
            index += count;
            if (debug) {
                System.out.println(String.format("Data Source Identifier %s(bytes)", 2));
            }
        }

        boolean isTrack = false;
        // 2 I001/020 Target Report Descriptor
        if (header[1]) {
            TargetReportDescriptor targetDescriptor = new TargetReportDescriptor();
            count = TargetReportDescriptor.extract(bytes, index, targetDescriptor);
            if (count <= 0) {
                return -1;
            }
            message.setTargetDescriptor(targetDescriptor);
            index += count;
            if (debug) {
                System.out.println(String.format("Target Report Descriptor %s(bytes)", count));
            }
            isTrack = targetDescriptor.isTrack();
        }

        // System.out.println(isTrack ? "[TRACK]" : "[PLOT]");
        count = isTrack ? decodeTrack(bytes, index, message, header) : decodePlot(bytes, index, message, header);

        if (count <= 0) {
            return -1;
        }
        index += count;
        if (debug) {
            System.out.println("-------------------------------------------------------------------------------------");
        }
        if (debug) {
            System.out.println("Start : " + start);
        }
        if (debug) {
            System.out.println("Read byte: " + count);
        }
        if (debug) {
            System.out.println("Index : " + index);
        }

        int extractByte = index - start;
        int totalByte = extractByte + 3;
        // System.out.println("Subtotal : " + count);
        if (debug) {
            System.out.println("length: " + extractByte);
        }
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
    }

    public static int decodeTrack(byte[] bytes, Integer index, Cat01Message message, boolean[] header) {
        // try {
        int count = 0;
        int start = index;
        for (int i = 2; i < ITEM_NUMBER; i++) {
            if (!header[i]) {
                continue;
            }

            switch (i) {
                case 2:  // 3 I001/161 Track/Plot Number
                    int number = ByteBuffer.wrap(new byte[]{0x00, 0x00, bytes[index++], bytes[index++]}).getInt();
                    message.setNo(number);
                    if (debug) {
                        System.out.println(String.format("Track/Plot Number %s(bytes)", 2));
                    }
                    break;

                case 3:  // 4 I001/040 Measured Position in Polar Coordinates
                    PolarCoordinate polarCoord = new PolarCoordinate();
                    count = PolarCoordinate.extract(bytes, index, polarCoord);
                    if (count <= 0) {
                        break;
                    }
                    index += count;
                    message.setPolarCoordinate(polarCoord);
                    if (debug) {
                        System.out.println(String.format("Measured Position in Polar Coordinates %s(bytes)", count));
                    }
                    break;

                case 4:  // 5 I001/042 Calculated Position in Cartesian Coordinates
                    CartesianCoordinate coordinate = new CartesianCoordinate();
                    count = CartesianCoordinate.extract(bytes, index, coordinate);
                    if (count <= 0) {
                        break;
                    }
                    index += count;
                    message.setCartesionCoordinate(coordinate);
                    if (debug) {
                        System.out.println(String.format("Calculated Position in Cartesian Coordinates %s(bytes)", count));
                    }
                    break;

                case 5:  // 6 I001/200 Calculated Track Velocity in polar Coordinates
                    CalculatedPolarVelocity calculatedVelocity = new CalculatedPolarVelocity();
                    count = CalculatedPolarVelocity.extract(bytes, index, calculatedVelocity);
                    if (count <= 0) {
                        break;
                    }
                    index += count;
                    message.setPolarVelocity(calculatedVelocity);
                    if (debug) {
                        System.out.println(String.format("Calculated Track Velocity in polar Coordinates %s(bytes)", count));
                    }
                    break;

                case 6:  // 7 I001/070 Mode-3/A Code in Octal Representation
                    Mode3ACode code3A = new Mode3ACode();
                    count = Mode3ACode.extract(bytes, index, code3A);
                    if (count <= 0) {
                        break;
                    }
                    index += count;
                    message.setMode3ACode(code3A);
                    if (debug) {
                        System.out.println(String.format("Mode-3/A Code in Octal Representation %s(bytes)", count));
                    }
                    break;

                case 7:  // 8 I001/090 Mode-C Code in Binary Representation
                    ModeC modeC = new ModeC();
                    count = ModeC.extract(bytes, index, modeC);
                    if (count <= 0) {
                        break;
                    }
                    index += count;
                    message.setModeC(modeC);
                    if (debug) {
                        System.out.println(String.format("Mode-C Code in Binary Representation %s(bytes)", count));
                    }
                    break;

                case 8:  // 9 I001/141 Truncated Time of Day
                    DVal timeOfDay = new DVal();
                    count = getTimeOfDay(bytes, index, timeOfDay);
                    if (count <= 0) {
                        break;
                    }
                    index += count;
                    message.setTimeOfDay(timeOfDay.val());
                    if (debug) {
                        System.out.println(String.format("Truncated Time of Day %s(bytes)", count));
                    }
                    break;

                case 9:  // 10 I001/130 Radar Plot Characteristics
                    IVal plotChar = new IVal();
                    count = getRadarPlotCharactics(bytes, index, plotChar);
                    if (count <= 0) {
                        break;
                    }
                    index += count;
                    message.setPlotChar(plotChar.val());
                    if (debug) {
                        System.out.println(String.format("Radar Plot Characteristics %s(bytes)", count));
                    }
                    break;

                case 10:  // 11 I001/131 Received Power
                    int power = (int) (bytes[index++] & 0XFF);
                    message.setReceivedPower(power);
                    // index += 2;
                    if (debug) {
                        System.out.println(String.format("Received Power %s(bytes)", 1));
                    }
                    break;

                case 11:  // 12 I001/120 Measured Radial Doppler Speed
                    int dropplerSpd = (int) (bytes[index++] & 0XFF);
                    double pow = (double) (-14 + f);
                    double spd = (double) dropplerSpd * Math.pow(2, pow);
                    message.setDroplerSpeed(spd);
                    if (debug) {
                        System.out.println(String.format("Measured Radial Doppler Speed %s(bytes)", 2));
                    }
                    break;

                case 12:  // 13 I001/170 Track Status
                    Status status = new Status();
                    count = Status.extract(bytes, index, status);
                    if (count <= 0) {
                        break;
                    }
                    index += count;
                    message.setStatus(status);
                    if (debug) {
                        System.out.println(String.format("Track Status %s(bytes)", count));
                    }
                    break;

                case 13:  // 14 I001/210 Track Quality
                    IVal trackQuality = new IVal();
                    count = getRadarPlotCharactics(bytes, index, trackQuality);
                    if (count <= 0) {
                        break;
                    }
                    index += count;
                    message.setQuality(trackQuality.val());
                    if (debug) {
                        System.out.println(String.format("Track Quality %s(bytes)", count));
                    }
                    break;

                case 14:  // 15 I001/050 Mode-2 Code in Octal Representation
                    Mode2Code mode2Code = new Mode2Code();
                    count = Mode2Code.extract(bytes, index, mode2Code);
                    if (count <= 0) {
                        break;
                    }
                    index += count;
                    message.setMode2Code(mode2Code);
                    if (debug) {
                        System.out.println(String.format("Mode-2 Code in Octal Representation %s(bytes)", count));
                    }
                    break;

                case 15:  // 16 I001/080 Mode-3/A Code Confidence Indicator
                    Mode3ACodeConfidenceIndicator mode3AConfidence = new Mode3ACodeConfidenceIndicator();
                    count = Mode3ACodeConfidenceIndicator.extract(bytes, index, mode3AConfidence);
                    if (count <= 0) {
                        break;
                    }
                    index += count;
                    message.setMode3AConfidenceIndicator(mode3AConfidence);
                    if (debug) {
                        System.out.println(String.format("Mode-3/A Code Confidence Indicator %s(bytes)", count));
                    }
                    break;

                case 16:  // 17 I001/100 Mode-C Code and Code Confidence Indicator
                    ModeCConfidenceIndicator modeCconfidence = new ModeCConfidenceIndicator();
                    count = ModeCConfidenceIndicator.extract(bytes, index, modeCconfidence);
                    if (count <= 0) {
                        break;
                    }
                    index += count;
                    message.setModeCConfidenceIndicator(modeCconfidence);
                    if (debug) {
                        System.out.println(String.format("Mode-C Code and Code Confidence Indicator %s(bytes)", count));
                    }
                    break;

                case 17:  // 18 I001/060 Mode-2 Code Confidence Indicator
                    Mode2CodeConfidenceIndicator mode2Confidence = new Mode2CodeConfidenceIndicator();
                    count = ModeCConfidenceIndicator.extract(bytes, index, mode2Confidence);
                    if (count <= 0) {
                        break;
                    }
                    index += count;
                    message.setMode2CConfidenceIndicator(mode2Confidence);
                    if (debug) {
                        System.out.println(String.format("Mode-2 Code Confidence Indicator %s(bytes)", count));
                    }
                    break;

                case 18:  // 19 I001/030 Warning/Error Conditions
                    WarningErrorCondition warningCodition = new WarningErrorCondition();
                    count = WarningErrorCondition.extract(bytes, index, warningCodition);
                    if (count <= 0) {
                        break;
                    }
                    index += count;
                    message.setWarningErrorCondition(warningCodition);
                    if (debug) {
                        System.out.println(String.format("Warning/Error Conditions %s(bytes)", count));
                    }
                    break;

                case 19:  // 20 - Reserved for Special Purpose Indicator (SP)
                    index += skipBit20(bytes, index);
                    break;

                case 20:  // 21 - Reserved for RFS Indicator (RS-bit)
                    index += skipBit21(bytes, index);
                    break;

                case 21:  // 22 I001/150  Presence of X-Pulse 1
                    Xpulse pulse = new Xpulse();
                    count = Xpulse.extract(bytes, index, pulse);
                    if (count <= 0) {
                        break;
                    }
                    index += count;
                    message.setXpulse(pulse);
                    if (debug) {
                        System.out.println(String.format("Presence of X-Pulse 1 %s(bytes)", count));
                    }
                    break;

                default:
                    break;
            }
        }

        int length = index - start;
//            final byte[] contentsByte = new byte[length + 1];
//            System.arraycopy(bytes, start, contentsByte, 1, length);
//            contentsByte[0] = 01;
//            message.setBinary(bytes);
        return length;

        // } catch (Exception ex) {
        // ex.printStackTrace();
        // String content = bytesToHex(bytes);
        // System.out.println("Err: " + content);
        // return -1;
        // }
    }

    public static int decodePlot(byte[] bytes, Integer index, Cat01Message message, boolean[] header) {
        int count = 0;
        int start = index;

        try {
            for (int i = 2; i < ITEM_NUMBER; i++) {
                if (!header[i]) {
                    continue;
                }
                switch (i) {
                    // 1 I001/010 Data Source Identifier
                    // 2 I001/020 Target Report Descriptor
                    case 2: // 3 I001/040 Measured Position in Polar Coordinates
                        PolarCoordinate polarCoord = new PolarCoordinate();
                        count = PolarCoordinate.extract(bytes, index, polarCoord);
                        if (count <= 0) {
                            break;
                        }
                        index += count;
                        message.setPolarCoordinate(polarCoord);
                        if (debug) {
                            System.out.println(String.format("Measured Position in Polar Coordinates %s(bytes)", count));
                        }
                        break;

                    case 3: // 4 I001/070 Mode-3/A Code in Octal Representation
                        Mode3ACode code3A = new Mode3ACode();
                        count = Mode3ACode.extract(bytes, index, code3A);
                        if (count <= 0) {
                            break;
                        }
                        index += count;
                        message.setMode3ACode(code3A);
                        if (debug) {
                            System.out.println(String.format("Mode-3/A %s(bytes)", count));
                        }
                        break;

                    case 4: // 5 I001/090 Mode-C Code in Binary Representation
                        ModeC modeC = new ModeC();
                        count = ModeC.extract(bytes, index, modeC);
                        if (count <= 0) {
                            break;
                        }
                        index += count;
                        message.setModeC(modeC);
                        if (debug) {
                            System.out.println(String.format("Mode-C %s(bytes)", count));
                        }
                        break;

                    case 5: // 6 I001/130 Radar Plot Characteristics
                        IVal plotChar = new IVal();
                        count = getRadarPlotCharactics(bytes, index, plotChar);
                        if (count <= 0) {
                            break;
                        }
                        index += count;
                        message.setPlotChar(plotChar.val());
                        if (debug) {
                            System.out.println(String.format("Plot Characteristics %s(bytes)", count));
                        }
                        break;

                    case 6: // 7 I001/141 Truncated Time of Day
                        DVal timeOfDay = new DVal();
                        count = getTimeOfDay(bytes, index, timeOfDay);
                        if (count <= 0) {
                            break;
                        }
                        index += count;
                        message.setTimeOfDay(timeOfDay.val());
                        if (debug) {
                            System.out.println(String.format("Time of Day %s(bytes)", count));
                        }
                        break;

                    case 7: // 8 I001/050 Mode-2 Code in Octal Representation
                        Mode2Code mode2Code = new Mode2Code();
                        count = Mode2Code.extract(bytes, index, mode2Code);
                        if (count <= 0) {
                            break;
                        }
                        index += count;
                        message.setMode2Code(mode2Code);
                        if (debug) {
                            System.out.println(String.format("Mode 2 %s(bytes)", count));
                        }
                        break;

                    case 8: // 9 I001/120 Measured Radial Doppler Speed
                        if (!validateIndex(index, bytes.length, 1)) {
                            return -1;
                        }
                        int dropplerSpd = (int) (bytes[index++] & 0XFF);
                        double pow = (double) (-14 + f);
                        double spd = (double) dropplerSpd * Math.pow(2, pow);
                        message.setDroplerSpeed(spd);
                        if (debug) {
                            System.out.println(String.format("Doppler Speed %s(bytes)", 1));
                        }
                        break;

                    case 9: // 10 I001/131 Received Power
                        if (!validateIndex(index, bytes.length, 1)) {
                            return -1;
                        }
                        int power = (int) (bytes[index++] & 0XFF);
                        message.setReceivedPower(power);
                        // index += 2;
                        if (debug) {
                            System.out.println(String.format("Received Power %s(bytes)", 1));
                        }
                        break;

                    case 10: // 11 I001/080 Mode-3/A Code Confidence Indicator
                        Mode3ACodeConfidenceIndicator mode3AConfidence = new Mode3ACodeConfidenceIndicator();
                        count = Mode3ACodeConfidenceIndicator.extract(bytes, index, mode3AConfidence);
                        if (count <= 0) {
                            break;
                        }
                        index += count;
                        message.setMode3AConfidenceIndicator(mode3AConfidence);
                        if (debug) {
                            System.out.println(String.format("Mode-3/A Code Confidence Indicator %s(bytes)", 1));
                        }
                        break;

                    case 11: // 12 I001/100 Mode-C Code and Code Confidence Indicator
                        ModeCConfidenceIndicator modeCconfidence = new ModeCConfidenceIndicator();
                        count = ModeCConfidenceIndicator.extract(bytes, index, modeCconfidence);
                        if (count <= 0) {
                            break;
                        }
                        index += count;
                        message.setModeCConfidenceIndicator(modeCconfidence);
                        if (debug) {
                            System.out.println(String.format("Mode-C Code and Code Confidence Indicator %s(bytes)", 1));
                        }
                        break;

                    case 12: // 13 I001/060 Mode-2 Code Confidence Indicator
                        Mode2CodeConfidenceIndicator mode2Confidence = new Mode2CodeConfidenceIndicator();
                        count = ModeCConfidenceIndicator.extract(bytes, index, mode2Confidence);
                        if (count <= 0) {
                            break;
                        }
                        index += count;
                        message.setMode2CConfidenceIndicator(mode2Confidence);
                        if (debug) {
                            System.out.println(String.format("Mode-2 Code Confidence Indicator %s(bytes)", 1));
                        }
                        break;

                    case 13: // 14 I001/030 Warning/Error Conditions
                        WarningErrorCondition warningCodition = new WarningErrorCondition();
                        count = WarningErrorCondition.extract(bytes, index, warningCodition);
                        if (count <= 0) {
                            break;
                        }
                        index += count;
                        message.setWarningErrorCondition(warningCodition);
                        if (debug) {
                            System.out.println(String.format("Warning/Error Conditions %s(bytes)", 1));
                        }
                        break;

                    case 14: // 15 I001/150 Presence of X-Pulse
                        Xpulse pulse = new Xpulse();
                        count = Xpulse.extract(bytes, index, pulse);
                        if (count <= 0) {
                            break;
                        }
                        index += count;
                        message.setXpulse(pulse);
                        if (debug) {
                            System.out.println(String.format("Presence of X-Pulse %s(bytes)", 1));
                        }
                        break;

                    case 15: // 16 -	Spare
                        break;
                    case 16: // 17 -	Spare
                        break;
                    case 17: // 18 -	Spare
                        break;
                    case 18: // 19 -	Spare
                        break;
                    case 19: // 20 -	Reserved for SP Indicator
                        index += skipBit20(bytes, index);
                        break;
                    case 20: // 21 Reserved for Random Field Sequencing (RFS) Indicator (RS-bit)
                        index += skipBit21(bytes, index);
                        break;

                }
            }
            int length = index - start;
//            final byte[] contentsByte = new byte[length + 1];
//            System.arraycopy(bytes, start, contentsByte, 1, length);
//            contentsByte[0] = 01;
//            message.setBinary(bytes);
            return length;

        } catch (Exception ex) {
            ex.printStackTrace();
            String content = bytesToHex(bytes);
            System.out.println(ex.getMessage());
            System.out.println("Err::[" + index + "] " + content);
            return -1;
        }
    }

    private static int skipBit20(byte[] bytes, Integer index) {
        if (!validateIndex(index, bytes.length, 1)) {
            return 0;
        }
        int length = bytes[index] & 0xFF;
        return length;
    }

    private static int skipBit21(byte[] bytes, Integer index) {
        if (!validateIndex(index, bytes.length, 1)) {
            return 0;
        }
        int length = bytes[index] & 0xFF;
        return length;
    }

    private static boolean validateIndex(int current, int length, int numOfByte) {
        if (current + numOfByte >= length) {
            System.out.println(String.format("Error while reading message: Index is out of boundary of array size (index: %s)(size: %s)", (current + numOfByte), length));
            return false;
        }
        return true;
    }

    public static int getComplementNumber(byte[] bytes) {
        int lat = bytes[0] & 0xFF;
        boolean positive = (int) (lat & 0x80) <= 0;
        int length = bytes.length;

        if (positive) {
            for (int i = 1; i < length; i++) {
                lat = lat << 8 | (bytes[i] & 0xFF);
            }
        } else {
            lat = ~lat & 0xFF;
            for (int i = 1; i < length; i++) {
                lat = lat << 8 | (~bytes[i] & 0xFF);
            }
            lat = lat + 0x01;
            lat = -lat;
        }
        return lat;
    }

    private static int getTimeOfDay(byte[] bytes, int index, DVal dVal) {
        if (!validateIndex(index, bytes.length, 2)) {

            return -1;
        }
        int value = ByteBuffer.wrap(new byte[]{0x00, 0x00, bytes[index++], bytes[index++]}).getInt();
        dVal.setValue((double) value / 128);
        return 2;
    }

    private static int getRadarPlotCharactics(byte[] bytes, int index, IVal ival) {
        int count = 0;
        int value = 0;
        while (true) {
            if (!validateIndex(index, bytes.length, 1)) {
                break;
            }
            byte cByte = bytes[index++];
            value = value << 7 | (cByte >> 1 & 0x7F);
            boolean extended = (cByte & 0X01) > 0;
            count++;
            if (!extended) {
                break;
            }
        }
        ival.setValue(value);
        return count;
    }

    final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 3];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 3] = hexArray[v >>> 4];
            hexChars[j * 3 + 1] = hexArray[v & 0x0F];
            hexChars[j * 3 + 2] = ' ';
        }
        return new String(hexChars);
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
                    com.attech.cat02.v10.DataSourceID source = new com.attech.cat02.v10.DataSourceID();
                    count = com.attech.cat02.v10.DataSourceID.extract(bytes, index, source);
                    if (count <= 0) return -1;
                    message.setSic(source.getSic());
                    message.setSac(source.getSac());
                    index += count;
                    System.out.println(String.format("> I002/010 Data Source Identifier %s (bytes)", count));
                    break;
                    
                case 1: // 2 I002/000 Message Type
                    com.attech.cat02.v10.IVal messageType = new com.attech.cat02.v10.IVal();
                    count = extractMessageType(bytes, index, messageType);
                    if (count < 0) break;
                    index += count;
                    message.setMesageType(messageType.val());
                    System.out.println(String.format("> I002/000 Message Type %s (bytes)", count));
                    break;
                    
                case 2: // 3 I002/020 Sector Number
                    com.attech.cat02.v10.DVal sectorNumber = new com.attech.cat02.v10.DVal();
                    count = extractSectorNumber(bytes, index, sectorNumber);
                    if (count < 0) break;
                    index += count;
                    message.setSectorNumber(sectorNumber.val());
                    System.out.println(String.format("> I002/020 Sector Number %s (bytes)", count));
                    break;
                    
                case 3: // 4 I002/030 Time of Day
                    com.attech.cat02.v10.DVal timeofday = new com.attech.cat02.v10.DVal();
                    count = extractTimeOfDay(bytes, index, timeofday);
                    if (count < 0) break;
                    index += count;
                    message.setTimeOfDay(timeofday.val());
                    System.out.println(String.format("> I002/030 Time of Day %s (bytes)", count));
                    break;
                    
                case 4: // 5 I002/041 Antenna Rotation Period
                    com.attech.cat02.v10.DVal rotationSpeed = new com.attech.cat02.v10.DVal();
                    count = extractAntennaRotationSpeed(bytes, index, rotationSpeed);
                    if (count < 0) break;
                    index += count;
                    message.setAntennaRotationSpeed(rotationSpeed.val());
                    System.out.println(String.format("> I002/041 Antenna Rotation Period %s (bytes)", count));
                    break;
                    
                case 5: // 6 I002/050 Station Configuration Status
                    com.attech.cat02.v10.IVal configurationStatus = new com.attech.cat02.v10.IVal();
                    count = extractConfigurationConfig(bytes, index, configurationStatus);
                    if (count <= 0) break;
                    index += count;
                    message.setStationConfigurationStatus(configurationStatus.val());
                    System.out.println(String.format("> I002/050 Station Configuration Status %s (bytes)", count));
                    break;
                    
                case 6: // 7 I002/060 Station Processing Mode
                    com.attech.cat02.v10.IVal configurationMode = new com.attech.cat02.v10.IVal();
                    count = extractConfigurationConfig(bytes, index, configurationMode);
                    if (count <= 0) break;
                    index += count;
                    message.setStationConfigurationStatus(configurationMode.val());
                    System.out.println(String.format("> I002/060 Station Processing Mode %s (bytes)", count));
                    break;
                    
                case 7: // 8 I002/070 Plot Count Values
                    PlotCountValues plotCountValue = new PlotCountValues();
                    count = PlotCountValues.extract(bytes, index, plotCountValue);
                    if (count <= 0) break;
                    index += count;
                    message.setPlotcountValue(plotCountValue);
                    System.out.println(String.format("> I002/070 Plot Count Values %s (bytes)", count));
                    break;
                    
                case 8: // 9 I002/100 Dynamic Window - Type 1
                    DynamicWindow dynamicWindow = new DynamicWindow();
                    count = DynamicWindow.extract(bytes, index, dynamicWindow);
                    if (count <= 0) break;
                    index += count;
                    message.setDynamicWindow(dynamicWindow);
                    System.out.println(String.format("> I002/100 Dynamic Window - Type 1 %s (bytes)", count));
                    break;
                case 9: // 10 I002/090 Collimation Error
                    CollimationError collimationError = new CollimationError();
                    count = CollimationError.extract(bytes, index, collimationError);
                    if (count <= 0) break;
                    index += count;
                    message.setCollimationError(collimationError);
                    System.out.println(String.format("> I002/090 Collimation Error %s (bytes)", count));
                    break;
                    
                case 10: // 11 I002/080 Warning/Error Conditions
                    com.attech.cat02.v10.WarningErrorCondition warmingErrorCondition = new com.attech.cat02.v10.WarningErrorCondition();
                    count = com.attech.cat02.v10.WarningErrorCondition.extract(bytes, index, warmingErrorCondition);
                    if (count <= 0) break;
                    index += count;
                    message.setWarningConditionError(warmingErrorCondition);
                    System.out.println(String.format("> I002/080 Warning/Error Conditions %s (bytes)", count));
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
        System.out.println(String.format("Read : %s -> %s length: %s", start, index, extractByte));
        System.out.println("-------------------------------------------------------------------------------------");
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
    
    private static int extractMessageType(byte [] bytes, int index, com.attech.cat02.v10.IVal val) {
        if (!com.attech.cat02.v10.Byter.validateIndex(index, bytes.length, 1)) return -1;
        int value = bytes[index++] & 0xFF;
        val.set(value);
        return 1;
    }
    
    private static int extractSectorNumber(byte [] bytes, int index, com.attech.cat02.v10.DVal val) {
        if (!com.attech.cat02.v10.Byter.validateIndex(index, bytes.length, 1)) return -1;
        int value = bytes[index++] & 0xFF;
        double dvalue = (double) value * 360 / Math.pow(2, 8);
        val.setValue(dvalue);
        return 1;
    }
    
    private static int extractTimeOfDay(byte [] bytes, int index, com.attech.cat02.v10.DVal val) {
        if (!com.attech.cat02.v10.Byter.validateIndex(index, bytes.length, 3)) return -1;
        int value = ByteBuffer.wrap(new byte[]{0x00, bytes[index++], bytes[index++], bytes[index++]}).getInt();
        val.setValue((double) value / 128);
        return 3;
    }
    
    private static int extractAntennaRotationSpeed(byte [] bytes, int index, com.attech.cat02.v10.DVal val) {
        if (!com.attech.cat02.v10.Byter.validateIndex(index, bytes.length, 2)) return -1;
        int value = ByteBuffer.wrap(new byte[]{0x00, 0x00, bytes[index++], bytes[index++]}).getInt();
        val.setValue((double) value / 128);
        return 2;
    }
    
    private static int extractConfigurationConfig(byte [] bytes, int index, com.attech.cat02.v10.IVal val) {
        boolean extend = true;
        int count = 0;
        int value = 0;
        while (extend) {
            if (!com.attech.cat02.v10.Byter.validateIndex(index, bytes.length, 1)) return -1;
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
