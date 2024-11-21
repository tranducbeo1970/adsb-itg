/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.gui.playback;

import java.util.List;

/**
 *
 * @author ANDH
 */
public interface IPlayerNotify {
    void playerStateChangedActionPerformed(PlayerState state, long progress, List<byte[]> data);
}
