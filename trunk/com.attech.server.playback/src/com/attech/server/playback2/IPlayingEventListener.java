/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.server.playback2;

/**
 *
 * @author root
 */
public interface IPlayingEventListener {
    void stop();
    void start();
    void pause();
    void resume();
    void end();
    void progress(int progress, long time);
    
}
