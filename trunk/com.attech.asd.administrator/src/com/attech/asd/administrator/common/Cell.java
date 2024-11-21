/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.administrator.common;

/**
 *
 * @author andh
 */
public class Cell {
    private int row;
    private int col;
    private String key;
    public Cell(int row, int col) {
        this.col = col;
        this.row = row;
        this.key = String.format("%s_%s", this.row, this.col);
    }

    /**
     * @return the row
     */
    public int getRow() {
        return row;
    }

    /**
     * @param row the row to set
     */
    public void setRow(int row) {
        this.row = row;
        this.key = String.format("%s_%s", this.row, this.col);
    }

    /**
     * @return the col
     */
    public int getCol() {
        return col;
    }

    /**
     * @param col the col to set
     */
    public void setCol(int col) {
        this.col = col;
        this.key = String.format("%s_%s", this.row, this.col);
    }

    /**
     * @return the key
     */
    public String getKey() {
        return key;
    }
    
    
}
