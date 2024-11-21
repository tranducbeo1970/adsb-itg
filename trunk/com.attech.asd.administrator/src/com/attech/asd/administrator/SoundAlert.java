/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.administrator;

import java.applet.Applet;
import java.applet.AudioClip;

/**
 *
 * @author AnhTH
 */
public class SoundAlert {
    
    public void PlayBeepBeep() {        
        AudioClip clip = Applet.newAudioClip(getClass().getResource("/com/attech/asd/administrator/sound/beep-beep.wav"));
        clip.play();
    }
    
    public void PlayAlert() {        
        AudioClip clip = Applet.newAudioClip(getClass().getResource("/com/attech/asd/administrator/sound/alert.wav"));
        clip.play();
    }
    
    public void PlayAircraftAlarm() {        
        AudioClip clip = Applet.newAudioClip(getClass().getResource("/com/attech/asd/administrator/sound/aircraft-alarm.wav"));
        clip.play();
    }
}
