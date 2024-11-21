/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.server.playback2;

import java.util.List;

/**
 *
 * @author root
 */
public interface IPlayListEventListener {
    void buildCompleted(List<PlayItemBase> items);
    void play(int fileIndex, int max);
    void stop(int fileIndex, boolean isEnd);
    void pause(int fileIndex);
    void progress(int progress, long time);
    // void updateProgress(int value);
    // void initPlayItem(int maxValue);
}
