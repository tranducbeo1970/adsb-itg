/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.database.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Command implements Serializable{
    //private String cmd;
    private ActionRequest cmd;
    private List<Object> params;
    
    public Command(){
        this.params = new ArrayList<>();
    }
    
    public Command(ActionRequest command){
        this.params = new ArrayList<>();
        this.cmd = command;
    }
    
    public Command(ActionRequest command, List<Object> objs) {
        this.cmd = command;
        this.params = objs;
    }
    
    public void addParam(Object obj){
        this.params.add(obj);
    }

    /**
     * @return the cmd
     */
    public ActionRequest getCmd() {
        return cmd;
    }

    /**
     * @param cmd the cmd to set
     */
    public void setCmd(ActionRequest cmd) {
        this.cmd = cmd;
    }

    /**
     * @return the params
     */
    public List<Object> getParams() {
        return params;
    }
    
    public Object getParam(int index) {
        return params.get(index);
    }

    /**
     * @param params the params to set
     */
    public void setParams(List<Object> params) {
        this.params = params;
    }
    
    
    
}
