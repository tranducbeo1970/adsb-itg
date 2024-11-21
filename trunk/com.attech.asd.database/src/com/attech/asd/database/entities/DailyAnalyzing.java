/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.database.entities;

import com.attech.asd.database.AdapterObject;
import com.attech.cat01.v120.Cat01Message;
import com.attech.cat21.v210.Cat21Message;
import com.attech.cat21.v210.QualityIndicator;
import com.attech.cat48.v121.Cat48Message;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.apache.log4j.Logger;

/**
 *
 * @author anhth
 */
@Entity
@Table(name = "dailyanalyzing")
public class DailyAnalyzing {

    @Id
    @GeneratedValue
    @Column(name = "Id")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "SensorId")
    private Sensors sensor;
    @Column(name = "DateResult")
    private Date dateResult;

    @Column(name = "TotalPackage")
    private Integer totalPackage;
    @Column(name = "TotalMessage")
    private Long totalMessage;
    @Column(name = "TotalCrafts")
    private Integer totalCraft;
    @Column(name = "TotalFlights")
    private Integer totalFlight;

    @Column(name = "Status")
    private Integer status;

    @Column(name = "TotalBadMsg")
    private Integer totalBadMsg;

    @Column(name = "ListBadFlights")
    private String listBadFlights;

    @Column(name = "Do260")
    private Integer do260;
    @Column(name = "Do260A")
    private Integer do260A;
    @Column(name = "Do260B")
    private Integer do260B;
    @Column(name = "ModeS")
    private Integer modeS;

    @Transient
    private Map<String, Set> crafts;

    @Transient
    private Map<String, Set> badFlight;

    @Transient
    private static final AdapterObject dao = new AdapterObject();

    @Transient
    private static final Logger logger = Logger.getLogger(DailyAnalyzing.class);

    public DailyAnalyzing() {
        this.totalBadMsg = 0;
        this.totalCraft = 0;
        this.totalFlight = 0;
        this.totalMessage = 0l;
        this.totalPackage = 0;
        crafts = new HashMap<>();
        badFlight = new HashMap<>();
        modeS = 0;
        do260 = 0;
        do260A = 0;
        do260B = 0;
    }

    public DailyAnalyzing(String dateResult, Sensors sensor) {
        DailyAnalyzing tmp = dao.getDailyAnalyzing(dateResult, sensor);
        if (tmp != null) {
            this.id = tmp.getId();
            this.dateResult = tmp.getDateResult();
            this.crafts = tmp.getCrafts();
            this.sensor = tmp.getSensor();
            this.status = tmp.getStatus();
            this.totalCraft = (tmp.getTotalCraft() != null) ? tmp.getTotalCraft() : 0;
            this.totalFlight = (tmp.getTotalFlight() != null) ? tmp.getTotalFlight() : 0;
            this.totalMessage = (tmp.getTotalMessage() != null) ? tmp.getTotalMessage() : 0;
            this.totalPackage = (tmp.getTotalPackage() != null) ? tmp.getTotalPackage() : 0;
            this.totalBadMsg = (tmp.getTotalBadMsg() != null) ? tmp.getTotalBadMsg() : 0;
            this.listBadFlights = tmp.getListBadFlights();
            if (listBadFlights != null && !listBadFlights.isEmpty()) {
                badFlight = new HashMap<>();
                String[] arr = listBadFlights.split(",");
                for (String a : arr) {
                    badFlight.put(a, new HashSet());
                }
            }
            this.modeS = (tmp.getModeS() != null) ? tmp.getModeS() : 0;
            this.do260 = (tmp.getDo260() != null) ? tmp.getDo260() : 0;
            this.do260A = (tmp.getDo260A() != null) ? tmp.getDo260A() : 0;
            this.do260B = (tmp.getDo260B() != null) ? tmp.getDo260B() : 0;
        } else {
            this.totalBadMsg = 0;
            this.totalCraft = 0;
            this.totalFlight = 0;
            this.totalMessage = 0l;
            this.totalPackage = 0;
            crafts = new HashMap<>();
            badFlight = new HashMap<>();
            modeS = 0;
            do260 = 0;
            do260A = 0;
            do260B = 0;
            this.sensor = sensor;
            try {
                this.dateResult = new SimpleDateFormat("yyyy-MM-dd").parse(dateResult);
            } catch (ParseException ex) {
                logger.error(ex.getMessage());
            }
        }
    }

    public void update21(List<Cat21Message> messages) {
        try {
            totalPackage++;
            totalMessage = totalMessage + messages.size();
            for (Cat21Message msg : messages) {
                final String address = Integer.toHexString(msg.getTargetAddress()).toUpperCase();
                final String callsign = msg.getCallSign();
                //final QualityIndicator qualityIndicator = msg.getQualityIndicator();
                //final Integer nac = qualityIndicator.getnACForPosition() == null ? 0 : qualityIndicator.getnACForPosition().intValue();

                if (callsign == null || callsign.isEmpty()) {
                    return;
                }
                if (address == null) {
                    return;
                }
                if (getCrafts().isEmpty() || !crafts.containsKey(address)) {
                    final Set setA = new HashSet();
                    setA.add(callsign);
                    getCrafts().put(address, setA);
                    this.setTotalFlight((Integer) (this.totalFlight + 1));
                    switch (msg.getMopsVersion().getVersionNumber()) {
                        case 0:
                            do260++;
                            break;
                        case 1:
                            do260A++;
                            break;
                        case 2:
                            do260B++;
                            break;
                    }
                } else {
                    if (!crafts.get(address).contains(callsign)) {
                        this.setTotalFlight((Integer) (this.getTotalFlight() + 1));
                    }
                    getCrafts().get(address).add(callsign);
                }
                this.setTotalCraft((getCrafts() == null) ? 0 : getCrafts().size());
                /*
            if (!msg.hasPosition() && nac < 5) {
                setTotalBadMsg(getTotalBadMsg() + 1);
                putBadFlight(msg.getAddress(), msg.getCallsign());
            }
                 */
            }
        } finally {
            this.save();
        }
    }

    public void update48(List<Cat48Message> messages) {
        try {
            totalPackage++;
            totalMessage = totalMessage + messages.size();
            for (Cat48Message msg : messages) {
                final String address = Integer.toHexString(msg.getTargetAddress()).toUpperCase();
                final String callsign = msg.getCallsign();
                if (callsign == null || callsign.isEmpty()) {
                    return;
                }
                if (address == null) {
                    return;
                }
                if (getCrafts().isEmpty() || !crafts.containsKey(address)) {
                    final Set setA = new HashSet();
                    setA.add(callsign);
                    getCrafts().put(address, setA);
                    this.setTotalFlight((Integer) (this.getTotalFlight() + 1));
                    if (msg.getTargetReportDescriptor().getType() >= 4) {
                        modeS++;
                    }
                } else {
                    if (!crafts.get(address).contains(callsign)) {
                        this.setTotalFlight((Integer) (this.getTotalFlight() + 1));
                    }
                    getCrafts().get(address).add(callsign);
                }
                this.setTotalCraft((getCrafts() == null) ? 0 : getCrafts().size());
            }
        } finally {
            this.save();
        }
    }
    
    public void update01(List<Cat01Message> messages) {
        try {
            totalPackage++;
            totalMessage = totalMessage + messages.size();
        } finally {
            this.save();
        }
    }

    public void putBadFlight(String address, String callsign) {
        if (callsign == null) {
            callsign = "";
        }
        if (address == null) {
            return;
        }
        if (badFlight.isEmpty() || !badFlight.containsKey(address)) {
            Set setA = new HashSet();
            setA.add(callsign);
            badFlight.put(address, setA);
            if (listBadFlights == null) {
                listBadFlights = "";
            }
            listBadFlights = listBadFlights + address + ",";
        } else {
            badFlight.get(address).add(callsign);
        }
    }

    public void genListBadFlights() {
        if (listBadFlights == null) {
            listBadFlights = "";
        }

        if (!badFlight.isEmpty()) {
            Set set = badFlight.entrySet();
            Iterator iterator = set.iterator();
            while (iterator.hasNext()) {
                Map.Entry mentry = (Map.Entry) iterator.next();
                String address = (String) mentry.getKey();
                listBadFlights = listBadFlights + address;
                /*
                listBadFlights = listBadFlights + mentry.getKey() + "/"; 
                Set setA = (Set) mentry.getValue();
                Iterator i = setA.iterator();
                while (i.hasNext()) {
                    String element = (String) i.next();
                    if (element != null && !element.isEmpty())
                        listBadFlights = listBadFlights + element + ","; 
                }
                 */
                listBadFlights = listBadFlights + ";";
            }
        }
        //listBadFlights = listBadFlights.replaceAll(",;", ";");

        //return getListBadFlights();
    }

    public void save() {
        dao.saveDailyAnalyzing(this);
    }
    
    public void countPack(int pack){
        this.totalPackage = this.totalPackage + pack;
        this.save();
    }

    /**
     * @return the totalPackage
     */
    public Integer getTotalPackage() {
        return totalPackage;
    }

    /**
     * @param totalPackage the totalPackage to set
     */
    public void setTotalPackage(Integer totalPackage) {
        this.totalPackage = totalPackage;
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
     * @return the sensor
     */
    public Sensors getSensor() {
        return sensor;
    }

    /**
     * @param sensor the sensor to set
     */
    public void setSensor(Sensors sensor) {
        this.sensor = sensor;
    }

    /**
     * @return the DateResult
     */
    public Date getDateResult() {
        return dateResult;
    }

    /**
     * @param DateResult the DateResult to set
     */
    public void setDateResult(Date DateResult) {
        this.dateResult = DateResult;
    }

    /**
     * @return the totalMessage
     */
    public Long getTotalMessage() {
        return totalMessage;
    }

    /**
     * @param totalMessage the totalMessage to set
     */
    public void setTotalMessage(Long totalMessage) {
        this.totalMessage = totalMessage;
    }

    /**
     * @return the totalCraft
     */
    public Integer getTotalCraft() {
        return totalCraft;
    }

    /**
     * @param totalCraft the totalCraft to set
     */
    public void setTotalCraft(Integer totalCraft) {
        this.totalCraft = totalCraft;
    }

    /**
     * @return the totalFlight
     */
    public Integer getTotalFlight() {
        return totalFlight;
    }

    /**
     * @param totalFlight the totalFlight to set
     */
    public void setTotalFlight(Integer totalFlight) {
        this.totalFlight = totalFlight;
    }

    /**
     * @return the status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * @return the crafts
     */
    public Map<String, Set> getCrafts() {
        return crafts;
    }

    /**
     * @param crafts the crafts to set
     */
    public void setCrafts(Map<String, Set> crafts) {
        this.crafts = crafts;
    }

    /**
     * @return the totalBadMsg
     */
    public Integer getTotalBadMsg() {
        return totalBadMsg;
    }

    /**
     * @param totalBadMsg the totalBadMsg to set
     */
    public void setTotalBadMsg(Integer totalBadMsg) {
        this.totalBadMsg = totalBadMsg;
    }

    /**
     * @return the listBadFlights
     */
    public String getListBadFlights() {
        return listBadFlights;
    }

    /**
     * @param listBadFlights the listBadFlights to set
     */
    public void setListBadFlights(String listBadFlights) {
        this.listBadFlights = listBadFlights;
    }

    /**
     * @return the badFlight
     */
    public Map<String, Set> getBadFlight() {
        return badFlight;
    }

    /**
     * @param badFlight the badFlight to set
     */
    public void setBadFlight(Map<String, Set> badFlight) {
        this.badFlight = badFlight;
    }

    /**
     * @return the do260
     */
    public Integer getDo260() {
        return do260;
    }

    /**
     * @param do260 the do260 to set
     */
    public void setDo260(Integer do260) {
        this.do260 = do260;
    }

    /**
     * @return the do260A
     */
    public Integer getDo260A() {
        return do260A;
    }

    /**
     * @param do260A the do260A to set
     */
    public void setDo260A(Integer do260A) {
        this.do260A = do260A;
    }

    /**
     * @return the do260B
     */
    public Integer getDo260B() {
        return do260B;
    }

    /**
     * @param do260B the do260B to set
     */
    public void setDo260B(Integer do260B) {
        this.do260B = do260B;
    }

    /**
     * @return the modeS
     */
    public Integer getModeS() {
        return modeS;
    }

    /**
     * @param modeS the modeS to set
     */
    public void setModeS(Integer modeS) {
        this.modeS = modeS;
    }
}
