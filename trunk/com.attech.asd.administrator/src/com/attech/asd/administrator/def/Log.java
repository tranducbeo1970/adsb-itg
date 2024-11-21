/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.administrator.def;

/**
 *
 * @author CanhVu
 */
public class Log {
    private String time ;
    private String type;
    private String content;

    public Log() {
    }

    public Log(String time, String type, String content) {
        this.time = time;
        this.type = type;
        this.content = content;
    }
    
    public String getTime() {
        return time;
    }

    public String getType() {
        return type;
    }

    public String getContent() {
        return content;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setContent(String content) {
        this.content = content;
    }
    
}
