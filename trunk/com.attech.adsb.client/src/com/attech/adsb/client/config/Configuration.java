/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.config;

import com.attech.adsb.client.common.Common;
import com.attech.adsb.client.common.XmlSerializer;

/**
 *
 * @author Saitama
 */
public class Configuration {



    // Graphic
    private AppConfig graphic;
    private MapConfig map;
    private Sectors sectors;
    private Preference preference;
    private GroundStationConfig ndbConfig;
    private GroundStationConfig vorDmeConfig;
    private GroundStationConfig fixPointConfig;
    private RoutesConfig routeConfig;
    private MeasureConfig measurementConfig;
    private TargetConfig targetConfig;
    private CustomDrawListConfig drawTool;
    private VVDs vvds;
    private VVPs vvps;
    private VVRs vvrs;
    private Airports airports;
    private AirportList airportList;
    private SiteMonitor sitemonitor;
    private AMA ama;
    private DataServiceConfig dataServiceCfg;
    private UdpConfig udpConfig;
    private SectorTransferConfig sectorTransferCfg;
    private ZoomLevelConfig zoomLevelConfig;
    private STCAConfig stcaConfig;
    private MTCAConfig mtcaConfig;

    public Configuration() {
    }

    public void load() {
	graphic = AppConfig.load();
	map = MapConfig.load(Common.RES_MAP);
	sectors = Sectors.load(Common.RES_SECTORS);
	airportList = AirportList.load(Common.RES_AIRPORTLIST);
	preference = XmlSerializer.load(Common.CFG_PREFERENCE, Preference.class);
	ndbConfig = XmlSerializer.load(Common.RES_NDB, GroundStationConfig.class);
	vorDmeConfig = XmlSerializer.load(Common.RES_VORDME, GroundStationConfig.class);
	fixPointConfig = XmlSerializer.load(Common.RES_FIXPOINT, GroundStationConfig.class);
	routeConfig = XmlSerializer.load(Common.RES_ROUTE, RoutesConfig.class);
	routeConfig.loadDefendency();
	measurementConfig = XmlSerializer.load(Common.CFG_MEASUREMENT, MeasureConfig.class);
	targetConfig = XmlSerializer.load(Common.CFG_TARGET, TargetConfig.class);
	drawTool = XmlSerializer.load(Common.RES_DRAW_TOOL, CustomDrawListConfig.class);
	vvds = XmlSerializer.load(Common.RES_VVD, VVDs.class);
	vvps = XmlSerializer.load(Common.RES_VVP, VVPs.class);
	vvrs = XmlSerializer.load(Common.RES_VVR, VVRs.class);
	sitemonitor = XmlSerializer.load(Common.RES_SITEMONITOR, SiteMonitor.class);
	airports = XmlSerializer.load(Common.RES_AIRPORTS, Airports.class);
	airports.loadAirport();
	ama = XmlSerializer.load(Common.RES_AMA, AMA.class);
	dataServiceCfg = XmlSerializer.load(Common.CFG_DATASERVICE, DataServiceConfig.class);
	udpConfig = XmlSerializer.load(Common.CFG_UDP, UdpConfig.class);
        sectorTransferCfg = XmlSerializer.load(Common.RES_SECTOR_TRANSFER, SectorTransferConfig.class);
        zoomLevelConfig = XmlSerializer.load(Common.CFG_ZOOM, ZoomLevelConfig.class);
        stcaConfig = XmlSerializer.load(Common.CFG_STCA, STCAConfig.class);
        mtcaConfig = XmlSerializer.load(Common.CFG_MTCA, MTCAConfig.class);
    }

    //<editor-fold defaultstate="collapsed" desc=" Class properties ">
    public AppConfig getGraphic() {
	return graphic;
    }

    public void setGraphic(AppConfig graphic) {
	this.graphic = graphic;
    }

    public MapConfig getMap() {
	return map;
    }

    public Sectors getSectors() {
	return sectors;
    }

    public void setMap(MapConfig map) {
	this.map = map;
    }

    public Preference getPreference() {
	return preference;
    }

    public void setPreference(Preference preference) {
	this.preference = preference;
    }

    public GroundStationConfig getNdbConfig() {
	return ndbConfig;
    }

    public void setNdbConfig(GroundStationConfig ndbConfig) {
	this.ndbConfig = ndbConfig;
    }

    public GroundStationConfig getVorDmeConfig() {
	return vorDmeConfig;
    }

    public void setVorDmeConfig(GroundStationConfig vorDmeConfig) {
	this.vorDmeConfig = vorDmeConfig;
    }

    public GroundStationConfig getFixPointConfig() {
	return fixPointConfig;
    }

    public void setFixPointConfig(GroundStationConfig fixPointConfig) {
	this.fixPointConfig = fixPointConfig;
    }

    public RoutesConfig getRouteConfig() {
	return routeConfig;
    }

    public void setRouteConfig(RoutesConfig routeConfig) {
	this.routeConfig = routeConfig;
    }

    public MeasureConfig getMeasurementConfig() {
	return measurementConfig;
    }

    public void setMeasurementConfig(MeasureConfig measurementConfig) {
	this.measurementConfig = measurementConfig;
    }

    public TargetConfig getTargetConfig() {
	return targetConfig;
    }

    public void setTargetConfig(TargetConfig targetConfig) {
	this.targetConfig = targetConfig;
    }

    public void setDrawTool(CustomDrawListConfig drawTool) {
	this.drawTool = drawTool;
    }

    public CustomDrawListConfig getDrawTool() {
	return drawTool;
    }

    public VVDs getVVDs() {
	return vvds;
    }

    public void setVVDs(VVDs vvds) {
	this.vvds = vvds;
    }

    public VVPs getVVPs() {
	return vvps;
    }

    public void setVVPs(VVPs vvps) {
	this.vvps = vvps;
    }

    public VVRs getVVRs() {
	return vvrs;
    }

    public SiteMonitor getSitemonitor() {
	return sitemonitor;
    }

    public void setSitemonitor(SiteMonitor sitemonitor) {
	this.sitemonitor = sitemonitor;
    }

    public void setVVRs(VVRs vvrs) {
	this.vvrs = vvrs;
    }

    public Airports getAirports() {
	return airports;
    }

    public void setAirports(Airports airports) {
	this.airports = airports;
    }

    public AMA getAma() {
	return ama;
    }

    public void setAma(AMA ama) {
	this.ama = ama;
    }

    public DataServiceConfig getDataServiceCfg() {
	return dataServiceCfg;
    }

    public void setDataServiceCfg(DataServiceConfig dataServiceCfg) {
	this.dataServiceCfg = dataServiceCfg;
    }
    
    public AirportList getAirportList() {
	return airportList;
    }

    public void setAirportList(AirportList airportList) {
	this.airportList = airportList;
    }
    
    public UdpConfig getUdpConfig() {
	return udpConfig;
    }

    public void setUdpConfig(UdpConfig udpConfig) {
	this.udpConfig = udpConfig;
    }
    
    public SectorTransferConfig getSectorTransferCfg() {
        return sectorTransferCfg;
    }

    public void setSectorTransferCfg(SectorTransferConfig sectorTransferCfg) {
        this.sectorTransferCfg = sectorTransferCfg;
    }

    public ZoomLevelConfig getZoomLevelConfig() {
        return zoomLevelConfig;
    }

    public void setZoomLevelConfig(ZoomLevelConfig zoomLevelConfig) {
        this.zoomLevelConfig = zoomLevelConfig;
    }
    
    public STCAConfig getStcaConfig() {
        return stcaConfig;
    }

    public void setStcaConfig(STCAConfig stcaConfig) {
        this.stcaConfig = stcaConfig;
    }
    
    public MTCAConfig getMtcaConfig() {
        return mtcaConfig;
    }

    public void setMtcaConfig(MTCAConfig mtcaConfig) {
        this.mtcaConfig = mtcaConfig;
    }


    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc=" Singleton Object ">
    private static Configuration instance;

    public static Configuration instance() {
	if (instance == null) {
	    instance = new Configuration();
	}
	return instance;
    }
    //</editor-fold>

}
