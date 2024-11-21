/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.lib.config;

import com.attech.adsb.lib.common.Area;
import com.attech.adsb.lib.common.XmlSerializer;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author hong
 */
@XmlRootElement(name = "configuration")
@XmlAccessorType(XmlAccessType.NONE)
public class Config {
    
    public static Config instance;
    
    @XmlElement(name = "data-folder")
    private String input;
    
    @XmlElement(name = "report-folder")
    private String output;
    
    @XmlElement(name = "build-result")
    private boolean buildResult;
    
    @XmlElement(name = "frame-time")
    private int frameTime;
    
    @XmlElement(name = "seperated-time")
    private int seperatedTime;
    
    @XmlElement(name = "print-detail-report")
    private boolean outputDetail;
    
    @XmlElement(name = "print-flight-trace")
    private boolean exportDisplayData;
    
    @XmlElement(name = "reports")
    private List<ReportCfg> reports;
    
    @XmlElement(name = "ground-station")
    private List<StationCfg> groundStations;
    
    @XmlElement(name = "sensor")
    private List<SensorCfg> sensors;
    
    @XmlElement(name = "area")
    private String areaFilterFile;
    
    @XmlElement(name = "black-list")
    private List<String> blackList;
    
    @XmlElement(name = "white-list")
    private List<String> whileList;
    
    
    
    private Area area;
    
    public Config() {
        this.reports = new ArrayList<>();
        this.sensors  = new ArrayList<>();
        this.groundStations = new ArrayList<>();
        this.blackList = new ArrayList<>();
    }
    
    public void save(String file) {
        XmlSerializer.serialize(file, this);
    }

    public static void load(String file) {
        instance = (Config) XmlSerializer.deserialize(file, Config.class);
        if (instance !=null && instance.getAreaFilterFile() != null) {
            instance.setArea((Area) XmlSerializer.deserialize(instance.getAreaFilterFile(), Area.class));
        }
        
    }
    
    public static void main (String [] args) {
        Config config = new Config();
        config.setInput("E:\\works\\projects\\ADSB\\ATM-REQUIREMENT\\svn\\test-data\\2016.02.05");
        config.setOutput("E:\\works\\temp\\output");
        config.setOutputDetail(true);
        config.setExportDisplayData(true);
        config.setFrameTime(8000);
        config.setSeperatedTime(300000);
        
        // GroundStation groundStation = new GroundStation("DIEN BIEN");
        // groundStation.addSic(166);
        // groundStation.addSic(167);
        config.addGroundStation( new StationCfg("DIEN BIEN", 166, 167));
        config.addGroundStation( new StationCfg("MOC CHAU", 159, 160, 161));
        config.addGroundStation( new StationCfg("NOI BAI", 163, 164));
        config.addGroundStation( new StationCfg("CAT BI", 169, 170));
        config.addGroundStation( new StationCfg("THO XUAN", 172, 173));
        config.addGroundStation( new StationCfg("VINH", 175, 176));
        config.addGroundStation( new StationCfg("DONG HOI", 178, 179));
        
        ReportCfg report1 = new ReportCfg();
        report1.setName("50");
        report1.setMinDistance(0.0);
        report1.setMaxDistance(50.0);
        report1.setMinFL(10f);
        config.addReport(report1);
        
        report1 = new ReportCfg();
        report1.setName("100");
        report1.setMinDistance(50.0);
        report1.setMaxDistance(100.0);
        report1.setMinFL(10f);
        config.addReport(report1);
        
        report1 = new ReportCfg();
        report1.setName("150");
        report1.setMinDistance(100.0);
        report1.setMaxDistance(150.0);
        report1.setMinFL(10f);
        config.addReport(report1);
        
        report1 = new ReportCfg();
        report1.setName("200");
        report1.setMinDistance(150.0);
        report1.setMaxDistance(200.0);
        report1.setMinFL(10f);
        config.addReport(report1);
        
        report1 = new ReportCfg();
        report1.setName("250");
        report1.setMinDistance(200.0);
        report1.setMaxDistance(250.0);
        report1.setMinFL(10f);
        config.addReport(report1);
        
        config.addSensor(159, 160, 161, 163, 164, 166, 167, 169, 170, 172, 173, 175, 176, 178, 179);
        
        config.setAreaFilterFile("hanoi_fir.xml");
        BlackList list = new BlackList();
        list.setFile("_00001_20160305014723");
        list.add("8880EEE");
        config.addBlackList("8880EEE");
        config.save("config-test.xml");
    }

    /**
     * @return the input
     */
    public String getInput() {
        return input;
    }

    /**
     * @param input the input to set
     */
    public void setInput(String input) {
        this.input = input;
    }

    /**
     * @return the output
     */
    public String getOutput() {
        return output;
    }

    /**
     * @param output the output to set
     */
    public void setOutput(String output) {
        this.output = output;
    }

    /**
     * @return the frameTime
     */
    public int getFrameTime() {
        return frameTime;
    }

    /**
     * @param frameTime the frameTime to set
     */
    public void setFrameTime(int frameTime) {
        this.frameTime = frameTime;
    }

    /**
     * @return the seperatedTime
     */
    public int getSeperatedTime() {
        return seperatedTime;
    }

    /**
     * @param seperatedTime the seperatedTime to set
     */
    public void setSeperatedTime(int seperatedTime) {
        this.seperatedTime = seperatedTime;
    }

    /**
     * @return the outputDetail
     */
    public boolean isOutputDetail() {
        return outputDetail;
    }

    /**
     * @param outputDetail the outputDetail to set
     */
    public void setOutputDetail(boolean outputDetail) {
        this.outputDetail = outputDetail;
    }

    /**
     * @return the exportDisplayData
     */
    public boolean isExportDisplayData() {
        return exportDisplayData;
    }

    /**
     * @param exportDisplayData the exportDisplayData to set
     */
    public void setExportDisplayData(boolean exportDisplayData) {
        this.exportDisplayData = exportDisplayData;
    }

    /**
     * @return the reports
     */
    public List<ReportCfg> getReports() {
        return reports;
    }
    
    public ReportCfg getReports(int index) {
        return reports.get(index);
    }

    /**
     * @param reports the reports to set
     */
    public void setReports(List<ReportCfg> reports) {
        this.reports = reports;
    }
    
    public void addReport(ReportCfg report) {
        this.reports.add(report);
    }

    /**
     * @return the groundStations
     */
    public List<StationCfg> getGroundStations() {
        return groundStations;
    }

    /**
     * @param groundStations the groundStations to set
     */
    public void setGroundStations(List<StationCfg> groundStations) {
        this.groundStations = groundStations;
    }
    
    public void addGroundStation(StationCfg groundStation) {
        this.groundStations.add(groundStation);
    }
    
    public void addSensor(int... sic) {
        if (sic == null) return;
        for (int s : sic) {
            SensorCfg sensorcfg = new SensorCfg();
            sensorcfg.setSic(s);
            this.getSensors().add(sensorcfg);
        }
    }

//    public void addSic(int sic) {
//        this.getSensors().add(sic);
//    }

    /**
     * @return the areaFilterFile
     */
    public String getAreaFilterFile() {
        return areaFilterFile;
    }

    /**
     * @param areaFilterFile the areaFilterFile to set
     */
    public void setAreaFilterFile(String areaFilterFile) {
        this.areaFilterFile = areaFilterFile;
    }

    /**
     * @return the blackList
     */
    public List<String> getBlackList() {
        return blackList;
    }

    /**
     * @param blackList the blackList to set
     */
    public void setBlackList(List<String> blackList) {
        this.blackList = blackList;
    }
    
    public void addBlackList(String list) {
        this.blackList.add(list);
    }

    /**
     * @return the area
     */
    public Area getArea() {
        return area;
    }

    /**
     * @param area the area to set
     */
    public void setArea(Area area) {
        this.area = area;
    }

    /**
     * @return the sensors
     */
    public List<SensorCfg> getSensors() {
        return sensors;
    }

    /**
     * @param sensors the sensors to set
     */
    public void setSensors(List<SensorCfg> sensors) {
        this.sensors = sensors;
    }

    /**
     * @return the buildResult
     */
    public boolean isBuildResult() {
        return buildResult;
    }

    /**
     * @param buildResult the buildResult to set
     */
    public void setBuildResult(boolean buildResult) {
        this.buildResult = buildResult;
    }

    /**
     * @return the whileList
     */
    public List<String> getWhileList() {
        return whileList;
    }

    /**
     * @param whileList the whileList to set
     */
    public void setWhileList(List<String> whileList) {
        this.whileList = whileList;
    }
}
