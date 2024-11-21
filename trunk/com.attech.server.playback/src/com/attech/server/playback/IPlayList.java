/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.server.playback;

import java.awt.event.ActionListener;

public interface IPlayList {

    public void build(long start, long end);

    public long getFoundStart();

    public void setFoundStart(long foundStart);

    public long getFoundEnd();

    public void setFoundEnd(long foundEnd);

    public void start();

    public void stop();

    public void pause();

    public void addUpdateListener(ActionListener l);

    public int getCurrentValue();

    public long getCurrentTime();
}
