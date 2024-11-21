/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.server.playback2;

public class BindingObject {
    private String getMethod;
    private String setMethod;
    private String dataType;

    public BindingObject() {
    }
    
    public BindingObject(String getMethod, String setMethod, String datatype) {
        this.getMethod = getMethod;
        this.setMethod = setMethod;
        this.dataType = datatype;
    }
    
    public BindingObject(String field, String dataType) {
        this.dataType = dataType;
        String f = Character.toUpperCase(field.charAt(0)) + field.substring(1);
        this.getMethod = "get" + f;
        this.setMethod = "set" + f;
    }
    
    /**
     * @return the getMethod
     */
    public String getGetMethod() {
        return getMethod;
    }

    /**
     * @param getMethod the getMethod to set
     */
    public void setGetMethod(String getMethod) {
        this.getMethod = getMethod;
    }

    /**
     * @return the setMethod
     */
    public String getSetMethod() {
        return setMethod;
    }

    /**
     * @param setMethod the setMethod to set
     */
    public void setSetMethod(String setMethod) {
        this.setMethod = setMethod;
    }

    /**
     * @return the dataType
     */
    public String getDataType() {
        return dataType;
    }

    /**
     * @param dataType the dataType to set
     */
    public void setDataType(String dataType) {
        this.dataType = dataType;
    }
    
}
