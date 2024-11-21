/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asterix;

import com.attech.asterix.cat21.v21.Message;
import java.util.List;

/**
 *
 * @author root
 */
public interface IMessageDecryptor {
    List<InternalMessage> extracInternalMesasge(byte[] bytes, float version);
    List<Message> decrypt(byte[] bytes);
}
