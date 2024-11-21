/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.server.playback2;

import java.io.IOException;

/**
 *
 * @author root
 */
public interface IPlayList2 {
    void build(long start, long end);
    public void selectItem(int index) throws ClassNotFoundException, IOException;
    void play();
    void pause();
    void stop();
    void addListenre(IPlayListEventListener listener);
    void seek(long value);
    int getCurrentIndex();
    boolean isAvailable();
    void increaseSpeed();
    int getSpeed();
}
