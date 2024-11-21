/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.administrator.common;

import com.attech.cat01.v120.Cat01Decoder;
import com.attech.cat01.v120.Cat01Message;
import com.attech.cat02.v10.Decrypter02;
import com.attech.cat02.v10.Message02;
import com.attech.cat34.v127.Decrypt34;
import com.attech.cat34.v127.Message34;
import com.attech.cat48.v121.Cat48Decryptor;
import com.attech.cat48.v121.Cat48Message;
import java.util.List;
/**
 *
 * @author AnhTH
 */
public class RadarDataDecrypter {
    public static final int NO_ERR = 0;
    public static final int ERR_OVER_LENGTH = 1;
    public static final int ERR_WRONG_CATEGORY = 2;

    public static String ERROR_MESSAGE;
    
    public static int decode(byte[] bytes, List<Cat01Message> messages01, List<Message02> messages02, List<Message34> messages34, List<Cat48Message> messages48) {
        ERROR_MESSAGE = null;
        try {
            if (bytes.length <= 3) {
                return ERR_OVER_LENGTH;
            }
            byte[] remaining;
            int cate = bytes[0] & 0xFF;
            switch (cate) {
                case 1:
                    // decode cat 01
                    remaining = Cat01Decoder.decode01(bytes, messages01);
                    if (remaining != null && remaining.length > 0)
                        decode(remaining, messages01, messages02, messages34, messages48);
                    break;
                case 2:
                    // decode cat 02
                    remaining = Decrypter02.decode02(bytes, messages02);
                    if (remaining != null && remaining.length > 0)
                        decode(remaining, messages01, messages02, messages34, messages48);
                    break;
                case 34:
                    // decode cat 34
                    remaining = Decrypt34.decode34(bytes, messages34);
                    if (remaining != null && remaining.length > 0)
                        decode(remaining, messages01, messages02, messages34, messages48);
                    break;
                case 48:
                    // decode cat 48
                    remaining = Cat48Decryptor.decode48(bytes, messages48);
                    if (remaining != null && remaining.length > 0)
                        decode(remaining, messages01, messages02, messages34, messages48);
                    break;
                default:
                    ERROR_MESSAGE = "Data not valid Asterix Cat 01/02/34/48";
                    return ERR_WRONG_CATEGORY;
            }
            return NO_ERR;
        } catch (Exception ex) {
            ERROR_MESSAGE = ex.getMessage();
            return -1;
        }
    }
}
