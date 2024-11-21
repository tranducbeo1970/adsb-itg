/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.database.entities;
import com.attech.adsb.lib.common.Area;
import com.attech.asd.database.AreasDao;
import java.awt.BasicStroke;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.jfree.chart.annotations.XYLineAnnotation;
/**
 *
 * @author Tang Hai Anh
 */
@Entity
@Table(name = "areas")
public class Areas{
    @Id
    @GeneratedValue
    @Column(name = "Id")
    private Integer id;
    @Column(name = "AreaName")
    private String name;
    @Column(name = "AreaDescription")
    private String description;
    @Column(name = "LastModified")
    private Date lastModified;
    @Column(name = "ModifiedBy")
    private Integer modifiedBy;
    @Column(name = "Type")
    private Integer type;
    
    @OneToMany(mappedBy = "area", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<AreaCoordinates> coordinates;
    
    
    public Areas(){
        coordinates = new ArrayList<>();
    }
    
    public void save(){
        new AreasDao().save(this);
    }
    
    public List<XYLineAnnotation> getXYLine(){
        List<XYLineAnnotation> tmp = new ArrayList<>();
        if (coordinates.size() > 2) {
            for (int i = 0; i < coordinates.size() - 1; i++) {
                tmp.add(new XYLineAnnotation(
                        coordinates.get(i).getLongitude(),
                        coordinates.get(i).getLatitude(),
                        coordinates.get(i + 1).getLongitude(),
                        coordinates.get(i + 1).getLatitude(),
                        new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f),
                        Color.RED));
            }
            tmp.add(new XYLineAnnotation(
                    coordinates.get(coordinates.size() - 1).getLongitude(),
                    coordinates.get(coordinates.size() - 1).getLatitude(),
                    coordinates.get(0).getLongitude(),
                    coordinates.get(0).getLatitude(),
                    new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f),
                    Color.RED));
        }
        return tmp;
    }
    
    public void add(AreaCoordinates coordinate){
        this.coordinates.add(coordinate);
    }
    
    public Area makeArea(){
        if (coordinates != null && coordinates.size() > 3){
            Area area = new Area();
            for (AreaCoordinates coordinates : this.coordinates){
                area.add(coordinates.getLongitude().floatValue(), coordinates.getLatitude().floatValue());
            }
            return area;
        }
        return null;
    }
    
    public Float getAreaValue(){
        float s = 0;
        if (coordinates != null && coordinates.size() > 3){
            for (int i=0; i<coordinates.size(); i++){
                if (i==coordinates.size()-1){
                    s = s +
                    (coordinates.get(i).getLongitude().floatValue()*coordinates.get(0).getLatitude().floatValue()
                    - coordinates.get(i).getLatitude().floatValue()*coordinates.get(0).getLongitude().floatValue());
                    continue;
                }  
                s = s +
                    (coordinates.get(i).getLongitude().floatValue()*coordinates.get(i+1).getLatitude().floatValue()
                    - coordinates.get(i).getLatitude().floatValue()*coordinates.get(i+1).getLongitude().floatValue());
            }
        }
        return Math.abs(s/2);
    }
    
    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the lastModified
     */
    public Date getLastModified() {
        return lastModified;
    }

    /**
     * @param lastModified the lastModified to set
     */
    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    /**
     * @return the modifiedBy
     */
    public Integer getModifiedBy() {
        return modifiedBy;
    }

    /**
     * @param modifiedBy the modifiedBy to set
     */
    public void setModifiedBy(Integer modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    /**
     * @return the coordinates
     */
    public List<AreaCoordinates> getCoordinates() {
        return coordinates;
    }

    /**
     * @param coordinates the coordinates to set
     */
    public void setCoordinates(List<AreaCoordinates> coordinates) {
        this.coordinates = coordinates;
    }

    /**
     * @return the type
     */
    public Integer getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(Integer type) {
        this.type = type;
    }
    
}
