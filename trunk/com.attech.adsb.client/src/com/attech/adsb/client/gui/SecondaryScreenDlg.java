/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.gui;

import com.attech.adsb.client.common.Common;
import com.attech.adsb.client.common.CustomCheckboxMenuItem;
import com.attech.adsb.client.common.ExHandler;
import com.attech.adsb.client.common.MLogger;
import com.attech.adsb.client.common.MouseContext;
import com.attech.adsb.client.config.Airport;
import com.attech.adsb.client.config.AirportItem;
import com.attech.adsb.client.config.AppConfig;
import com.attech.adsb.client.config.Configuration;
import com.attech.adsb.client.config.Point2f;
import com.attech.adsb.client.config.Region;
import com.attech.adsb.client.config.RegionGroup;
import com.attech.adsb.client.config.Regions;
import com.attech.adsb.client.common.TmaValidator;
import com.attech.adsb.client.config.FilterCondition;
import com.attech.adsb.client.config.TargetLabelDisplay;
import com.attech.adsb.client.graphic.Convertor;
import com.attech.adsb.client.graphic.Painter;
import com.attech.adsb.client.graphic.RGB;
import com.attech.adsb.client.graphic.objects.AirportGraphic;
import com.attech.adsb.client.graphic.objects.FixPointGraphic;
import com.attech.adsb.client.graphic.objects.MapGraphic;
import com.attech.adsb.client.graphic.objects.RouteGraphic;
import com.attech.adsb.client.graphic.objects.TargetGraphic;
import com.attech.adsb.client.graphic.objects.VVDsGraphic;
import com.attech.adsb.client.graphic.objects.VVPsGraphic;
import com.attech.adsb.client.graphic.objects.VVRsGraphic;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.util.FPSAnimator;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.File;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 *
 * @author andh
 */
public class SecondaryScreenDlg extends javax.swing.JDialog implements MouseListener, MouseMotionListener, ActionListener, MouseWheelListener {
    private static final MLogger logger = MLogger.getLogger(SecondaryScreenDlg.class);
    
    private static final String SELECTED_AIRPORT = "SELECTED_AIRPORT:SECONDARY";
    private static final String SELECTED_ZOOM_LEVEL = "SELECTED_ZOOM_LEVEL:SECONDARY:";
    private static final String TARGET_LABEL_TRACKING = "TARGET/LABEL/TRACKING:SECONDARY";
    private static final String TARGET_LABEL_HEADING = "TARGET/LABEL/HEADING:SECONDARY";
    private static final String TARGET_LABEL_INFO = "TARGET/LABEL/INFO:SECONDARY";
    private static final String TARGET_LABEL_HDGNOTE = "TARGET/LABEL/HDGNOTE:SECONDARY";
    private static final String TARGET_LABEL_CTRL = "TARGET/LABEL/CTRL:SECONDARY";
    private static final String TARGET_LABEL_COALT = "TARGET/LABEL/COALT:SECONDARY";
    private static final String TARGET_LABEL_CALT = "TARGET/LABEL/CALT:SECONDARY";
    private static final String TARGET_LABEL_ALT = "TARGET/LABEL/ALT:SECONDARY";
    private static final String TARGET_LABEL_SPD = "TARGET/LABEL/SPD:SECONDARY";
    private static final String TARGET_LABEL_SSR = "TARGET/LABEL/SSR:SECONDARY";
    private static final String TARGET_LABEL_CALLSIGN = "TARGET/LABEL/CALLSIGN:SECONDARY";
    private static final String SECONDARY_SELECTED_ZOOM_LEVEL = "SELECTED_ZOOM_LEVEL:SECONDARY";
    
   
    private final TargetGraphic targetGraphic;
    private final GLJPanel gljpanel;
    private final Painter painter;
    private final MapGraphic map;
    
    private VVDsGraphic vvdsGraphic;
    private VVPsGraphic vvpsGraphic;
    private VVRsGraphic vvrsGraphic;
    private FixPointGraphic fixPointGraphic;
    private RouteGraphic routeGraphic;
    private AirportGraphic activeReportGraphic;
    private final MouseContext mouseContext;
    private final AppConfig appConfig = Configuration.instance().getGraphic();
    private Airport activeAirport;
    private final FPSAnimator animator;
    
    /**
     * Creates new form SecondScreen
     * @param parent
     * @param modal
     * @param targetGraphic
     */
    public SecondaryScreenDlg(java.awt.Frame parent, boolean modal, TargetGraphic targetGraphic ) {
        super(parent, modal);
        
        GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);
        capabilities.setDoubleBuffered(true);
        
        initComponents();
        
        this.painter = new Painter();
        this.targetGraphic = targetGraphic;
        this.gljpanel = new GLJPanel(capabilities);
        this.gljpanel.addGLEventListener((GLEventListener) painter);
        this.gljpanel.setAutoSwapBufferMode(true);
        this.painter.setBgColor(new RGB(appConfig.getString(Common.BACKGROUND_COLOR)));
        this.pnlContent.add(gljpanel);
        this.pnlContent.setViewportView(gljpanel);

        this.mouseContext = new MouseContext();
        
	// ZOOM
        int zoomLevel = appConfig.getInt(SECONDARY_SELECTED_ZOOM_LEVEL);
        this.sdrRNG.setValue(zoomLevel);
        this.setZoom(zoomLevel);
	
//        this.painter.setZoom(Configuration.instance().getZoomLevelConfig().getZoomLevels(zoomLevel - 1));
        initMapGraphic();
        initTargetGraphic();
        
        this.map = new MapGraphic(Configuration.instance().getMap());
	this.painter.addGraphicObject(map);
        
        activeReportGraphic = new AirportGraphic();
        painter.addGraphicObject(activeReportGraphic);
        painter.addGraphicObject(this.targetGraphic);
        
        
        initAirportList();
        String selectedAirport = appConfig.getString(SELECTED_AIRPORT);
	clearAirportResource();
	initAirport(selectedAirport);
        
	animator = new FPSAnimator(gljpanel, 4, true);
	animator.start();
         
    }
    
    private void initTargetGraphic() {
        TargetLabelDisplay labelDisplay = new TargetLabelDisplay();
        labelDisplay.setTargetCallsignVisible(appConfig.getBoolean(mniCallsign.getActionCommand()));
        labelDisplay.setTargetSSRVisible(appConfig.getBoolean(mniSSR.getActionCommand()));
        labelDisplay.setTargetSPDVisible(appConfig.getBoolean(mniSpeed.getActionCommand()));
        labelDisplay.setTargetALTVisible(appConfig.getBoolean(mniAltitude.getActionCommand()));
        labelDisplay.setTargetHDGNoteVisible(appConfig.getBoolean(mniHDGNote.getActionCommand()));
        labelDisplay.setTargetInfoVisible(appConfig.getBoolean(TARGET_LABEL_INFO));
        labelDisplay.setTargetHeadingVisible(appConfig.getBoolean(TARGET_LABEL_HEADING));
        labelDisplay.setTargetTrackingVisible(appConfig.getBoolean(TARGET_LABEL_TRACKING));
        labelDisplay.setTargetCALTVisible(appConfig.getBoolean(TARGET_LABEL_CALT));
        labelDisplay.setTargetCoALTVisible(appConfig.getBoolean(TARGET_LABEL_COALT));
        labelDisplay.setTargetCTRLVisible(appConfig.getBoolean(TARGET_LABEL_CTRL));

        initTargetLabel(labelDisplay);

//        this.targetGraphic = new TargetGraphic(Configuration.instance().getTargetConfig());
        this.targetGraphic.setLabelDisplay(labelDisplay);
        FilterCondition condition = new FilterCondition();
        condition.setActive(false);
        this.targetGraphic.setFilterCondition(condition);
//        this.painter.addGraphicObject(primaryTargetGraphic);

        // Seperate the target display on secondary
//        this.secondaryGraphic = new TargetGraphic(Configuration.instance().getTargetConfig());
//        this.secondaryGraphic.setLabelDisplay(labelDisplay);
    }
    
    private void initMapGraphic() {
        Boolean enable = appConfig.getBoolean(mniWayPoint.getActionCommand());
        this.mniWayPoint.setSelected(enable);
        this.fixPointGraphic = new FixPointGraphic(Configuration.instance().getFixPointConfig());
        this.fixPointGraphic.setEnable(enable);
        this.painter.addGraphicObject(fixPointGraphic);


        enable = appConfig.getBoolean(mniName.getActionCommand());
        this.mniName.setSelected(enable);

        enable = appConfig.getBoolean(mniRoute.getActionCommand());
        this.mniRoute.setSelected(enable);
        this.routeGraphic = new RouteGraphic(Configuration.instance().getRouteConfig());
        this.routeGraphic.setEnable(enable);
        this.painter.addGraphicObject(routeGraphic);
	
    }
    
    private void initAirportList() {
	JCheckBoxMenuItem mniAirportItem;
	List<AirportItem> airportItems = Configuration.instance().getAirportList().getAirportList();
        String selectedAirport = appConfig.getString(SELECTED_AIRPORT);
	for (AirportItem airportItem : airportItems) {
            mniAirportItem = new JCheckBoxMenuItem(airportItem.getName());
            mniAirportItem.setEnabled(airportItem.getEnable());
            mniAirportItem.addActionListener(this::airportChangeActionPerform);
            mniAirportItem.setActionCommand(airportItem.getDataPath());
            if (selectedAirport.equalsIgnoreCase(airportItem.getName())) {
                mniAirportItem.setSelected(true);
            }
	    mnuAirport.add(mniAirportItem);
	}
    }
    
    private void clearAirportResource() {
//        mniTMA.setVisible(false);
//        mniAPP.setVisible(false);
//        mnuARR.setVisible(false);
//        mnuFixPoint.setVisible(false);
        mnuRUNWAY.removeAll();
	mnuRUNWAY.setEnabled(false);
        mnuPROC.removeAll();
	mnuPROC.setEnabled(false);
        mnuRUNWAY.removeAll();
	mnuRUNWAY.setEnabled(false);
        mnuARR.removeAll();
	mnuARR.setEnabled(false);
        mnuFixPoint.removeAll();
	mnuFixPoint.setEnabled(false);
	
	mniCircle.setEnabled(false);
	mniCircle.setSelected(false);
	activeReportGraphic.setDisplayCircle(false);

	mniArc.setEnabled(false);
	mniArc.setSelected(false);
	activeReportGraphic.setDisplayArc(false);
    }
    
    private void initAirport(String name) {
        
	if (name == null || name.isEmpty()) name = "none";
        
        String dataPath = Configuration.instance().getAirportList().getResourcePath(name );
        File root = new File("res");
        File rootFile = new File(root, dataPath);
        
        if (!rootFile.exists() || !rootFile.isDirectory()) return;
        
        AppConfig config = Configuration.instance().getGraphic();
	String datapath = String.format("res/%s/airport.xml", dataPath);
	Airport selectedAirport = Airport.load(datapath);

        activeAirport = selectedAirport;
        try {
	    Boolean enable ;
	    String command;
            
            activeReportGraphic.setAvailable(false);
            activeReportGraphic.update(selectedAirport);
	    
	    mniArc.setActionCommand(String.format("ARC:%s:SECONDARY", name));
	    if (selectedAirport.getDisplayArc()) {
		enable = config.getBoolean(mniArc.getActionCommand());
		mniArc.setEnabled(true);
		mniArc.setSelected(enable);
		activeReportGraphic.setDisplayArc(enable);
	    }

	    mniCircle.setActionCommand(String.format("CIRCLE:%s:SECONDARY", name));
	    if (selectedAirport.getDisplayCircle()) {
		enable = config.getBoolean(mniCircle.getActionCommand());
		mniCircle.setEnabled(true);
		mniCircle.setSelected(enable);
		activeReportGraphic.setDisplayCircle(enable);
	    } 

            painter.setPanx(Convertor.fromWGS842OpenGL(selectedAirport.getPoint().getX()));
            painter.setPany(Convertor.fromWGS842OpenGL(selectedAirport.getPoint().getY()));
            
            if (selectedAirport.getRegions() == null) {
                return;
            }

           
            for (Regions region : selectedAirport.getRegions()) {
		if ( region.getRegionGroup() == null || region.getRegionGroup().isEmpty()) break;

		switch (region.getType()) {
		    case "TMA":
			command = String.format("TMA:%s:SECONDARY", name);
			enable = config.getBoolean(command);
//			mniTMA.setVisible(true);
			mniTMA.setActionCommand(command);
			mniTMA.setSelected(enable);
			mniTMA.setEnabled(true);
			activeReportGraphic.setEnable("TMA", enable);
			File tmawarningFile = new File(rootFile, "tma-warning.xml");
			if (tmawarningFile.exists()) {
			    TmaValidator TmaWarning = TmaValidator.load(tmawarningFile.getAbsolutePath(), TmaValidator.class);
//			    Main.tmaWarningZones = TmaWarning.getLstPolygon();
			} else {
//			    Main.tmaWarningZones = new ArrayList<>();
			}
			break;

		    case "APP":
			command = String.format("APP:%s:SECONDARY", name);
			enable = config.getBoolean(command);
			mniAPP.setEnabled(true);
			mniAPP.setSelected(enable);
			mniAPP.setActionCommand(command);
			activeReportGraphic.setEnable("APP", enable);
			break;

		    case "ARR":
//			mnuARR.setVisible(true);
			if (region.getRegionGroup().get(0).getRegion() == null
				|| region.getRegionGroup().get(0).getRegion().isEmpty()) {
			    break;
			}
			mnuARR.setEnabled(true);
			List<Region> lstArr = region.getRegionGroup().get(0).getRegion();
			for (Region arr : lstArr) {
			    command = String.format("ARR:%s:%s:SECONDARY", name, arr.getName());
			    enable = config.getBoolean(command);
			    JCheckBoxMenuItem chkRegion = new CustomCheckboxMenuItem(arr.getName());
			    chkRegion.setActionCommand(command);
			    chkRegion.addActionListener(this::airportRegionDisplayChangeActionPerformed);
			    mnuARR.add(chkRegion);
			    activeReportGraphic.setEnable("ARR", arr.getName(), enable);
			}
			break;

		    case "FIX":
			if (region.getRegionGroup().get(0).getRegion() == null
				|| region.getRegionGroup().get(0).getRegion().isEmpty()) {
			    break;
			}
			mnuFixPoint.setEnabled(true);
			List<Region> lstFixPoint = region.getRegionGroup().get(0).getRegion();
			for (Region fixPoint : lstFixPoint) {
			    command = String.format("FIX:%s:%s:SECONDARY", name, fixPoint.getName());
			    enable = config.getBoolean(command);
			    JCheckBoxMenuItem chkRegion = new CustomCheckboxMenuItem(fixPoint.getName());
			    chkRegion.setActionCommand(command);
			    chkRegion.setSelected(enable);
			    chkRegion.addActionListener(this::airportRegionDisplayChangeActionPerformed);
			    mnuFixPoint.add(chkRegion);
			    activeReportGraphic.setEnable("FIX", fixPoint.getName(), enable);
			}
			break;

		    case "PROC":
			mnuPROC.setEnabled(true);
			List<RegionGroup> lstGroup = region.getRegionGroup();

			for (RegionGroup regionGroup : lstGroup) {
			    JMenu mnuGroup = new JMenu(regionGroup.getName());
			    for (Region regionItem : regionGroup.getLstRegion()) {
				command = String.format("PROC:%s:%s:%s:SECONDARY", name, regionGroup.getName(), regionItem.getName());
				enable = config.getBoolean(command);
				JCheckBoxMenuItem chkRegion = new CustomCheckboxMenuItem(regionItem.getName());
				chkRegion.setActionCommand(command);
				chkRegion.setSelected(enable);
				chkRegion.addActionListener(this::airportRegionDisplayChangeActionPerformed);
				mnuGroup.add(chkRegion);
				activeReportGraphic.setEnable("PROC", regionGroup.getName(), regionItem.getName(), enable);
//				System.out.println(String.format(""));
			    }
			    mnuPROC.add(mnuGroup);
			}
			break;
			
		    case "RWY":
			if (region.getRegionGroup().get(0).getRegion() == null
				|| region.getRegionGroup().get(0).getRegion().isEmpty()) {
			    break;
			}
			
			mnuRUNWAY.setEnabled(true);
			List<Region> lstRwy = region.getRegionGroup().get(0).getRegion();
			for (Region rwy : lstRwy) {
			    command = String.format("RWY:%s:%s:SECONDARY", name, rwy.getName());
			    enable = config.getBoolean(command);
			    JCheckBoxMenuItem chkRegion = new CustomCheckboxMenuItem(rwy.getName());
			    chkRegion.setActionCommand(command);
			    chkRegion.setSelected(enable);
			    chkRegion.addActionListener(this::airportRegionDisplayChangeActionPerformed);
			    activeReportGraphic.setEnable("RWY", rwy.getName(), enable);
			    mnuRUNWAY.add(chkRegion);
			}

			break;
		}
            }

        } finally {
            activeReportGraphic.setAvailable(true);
        }
    }
    
    public void airportChangeActionPerform(ActionEvent evt) {
       try {
	    JCheckBoxMenuItem menuItem = (JCheckBoxMenuItem) evt.getSource();
	    JMenuItem item;
	    for (int i = 0; i < mnuAirport.getItemCount(); i++) {
		item = mnuAirport.getItem(i);
		if (item.getText().equalsIgnoreCase(menuItem.getText())) {
		    continue;
		}
		mnuAirport.getItem(i).setSelected(false);
	    }

	    clearAirportResource();
	    initAirport(menuItem.getText());
	    appConfig.setParameter(SELECTED_AIRPORT, menuItem.getText());

	} catch (Exception ex) {
	    logger.error(ex);
	} finally {
	    appConfig.save();
	}
    }

    private void setAllTargetLabelAllOn(boolean display) {
        targetGraphic.getLabelDisplay().setAllDisplay(display);
        initTargetLabel(targetGraphic.getLabelDisplay());
        appConfig.setParameter(mniCallsign.getActionCommand(), targetGraphic.getLabelDisplay().isTargetCallsignVisible());
        appConfig.setParameter(mniSSR.getActionCommand(), targetGraphic.getLabelDisplay().isTargetSSRVisible());
        appConfig.setParameter(mniSpeed.getActionCommand(), targetGraphic.getLabelDisplay().isTargetSPDVisible());
        appConfig.setParameter(mniAltitude.getActionCommand(), targetGraphic.getLabelDisplay().isTargetALTVisible());
//        appConfig.setParameter(mniCAlt.getActionCommand(), targetGraphic.getLabelDisplay().isTargetCALTVisible());
//        appConfig.setParameter(mniCoAlt.getActionCommand(), targetGraphic.getLabelDisplay().isTargetCoALTVisible());
//        appConfig.setParameter(mniController.getActionCommand(), targetGraphic.getLabelDisplay().isTargetCTRLVisible());
        appConfig.setParameter(mniHDGNote.getActionCommand(), targetGraphic.getLabelDisplay().isTargetHDGNoteVisible());
//        appConfig.setParameter(mniInfo.getActionCommand(), targetGraphic.getLabelDisplay().isTargetInfoVisible());
//        appConfig.setParameter(mniHeading.getActionCommand(), targetGraphic.getLabelDisplay().isTargetHeadingVisible());
//        appConfig.setParameter(mniTracking.getActionCommand(), targetGraphic.getLabelDisplay().isTargetTrackingVisible());
    }

    private void initTargetLabel(TargetLabelDisplay labelDisplay) {
        mniCallsign.setSelected(labelDisplay.isTargetCallsignVisible());
        mniSSR.setSelected(labelDisplay.isTargetSSRVisible());
        mniSpeed.setSelected(labelDisplay.isTargetSPDVisible());
        mniAltitude.setSelected(labelDisplay.isTargetALTVisible());
//        mniCAlt.setSelected(false);
//        mniCoAlt.setSelected(labelDisplay.isTargetCoALTVisible());
//        mniController.setSelected(labelDisplay.isTargetCTRLVisible());
        mniHDGNote.setSelected(labelDisplay.isTargetHDGNoteVisible());
//        mniInfo.setSelected(labelDisplay.isTargetInfoVisible());
//        mniHeading.setSelected(labelDisplay.isTargetHeadingVisible());
//        mniTracking.setSelected(labelDisplay.isTargetTrackingVisible());
    }

    private void setZoom(int value) {
        double level = Configuration.instance().getZoomLevelConfig().getZoomLevels(value - 1);
        this.btnRGN.setText("RNG: " + value);
        this.painter.setZoom(level);
        this.gljpanel.display();
        appConfig.setParameter(Common.SELECTED_ZOOM_LEVEL, value);
        logger.info("Set zoom level to %s:%s", value, level);
    }
    
    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mnuMap = new javax.swing.JPopupMenu();
        mnuAirport = new javax.swing.JMenu();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        mnuFixPoint = new javax.swing.JMenu();
        mnuPROC = new javax.swing.JMenu();
        mnuRUNWAY = new javax.swing.JMenu();
        mnuVVP = new javax.swing.JMenu();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        mniCircle = new CustomCheckboxMenuItem();
        mniArc = new CustomCheckboxMenuItem();
        mniTMA = new CustomCheckboxMenuItem();
        mniAPP = new CustomCheckboxMenuItem();
        mnuARR = new javax.swing.JMenu();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        mnuFixNav = new javax.swing.JMenu();
        mniWayPoint = new CustomCheckboxMenuItem();
        mniName = new CustomCheckboxMenuItem();
        mniRoute = new CustomCheckboxMenuItem();
        mnuTarget = new javax.swing.JPopupMenu();
        mniCallsign = new CustomCheckboxMenuItem();
        mniSSR = new CustomCheckboxMenuItem();
        mniSpeed = new CustomCheckboxMenuItem();
        mniAltitude = new CustomCheckboxMenuItem();
        mniHDGNote = new CustomCheckboxMenuItem();
        jSeparator8 = new javax.swing.JPopupMenu.Separator();
        mniAllOn = new javax.swing.JMenuItem();
        mniAllOff = new javax.swing.JMenuItem();
        jToolBar1 = new javax.swing.JToolBar();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 32767));
        btnMap = new javax.swing.JButton();
        btnTarget = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        btnRGN = new javax.swing.JButton();
        sdrRNG = new javax.swing.JSlider();
        pnlContent = new javax.swing.JScrollPane();

        mnuAirport.setText("AIRPORTS");
        mnuMap.add(mnuAirport);
        mnuMap.add(jSeparator2);

        mnuFixPoint.setText("FIX POINTS");
        mnuMap.add(mnuFixPoint);

        mnuPROC.setText("PROC");
        mnuMap.add(mnuPROC);

        mnuRUNWAY.setText("RUNWAY");
        mnuMap.add(mnuRUNWAY);

        mnuVVP.setText("VVP");
        mnuMap.add(mnuVVP);
        mnuMap.add(jSeparator4);

        mniCircle.setSelected(true);
        mniCircle.setText("CIRCLE");
        mniCircle.setActionCommand("MAP/CIRCLE:SECONDARY");
        mniCircle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                airportRegionDisplayChangeActionPerformed(evt);
            }
        });
        mnuMap.add(mniCircle);

        mniArc.setSelected(true);
        mniArc.setText("ARC");
        mniArc.setActionCommand("MAP/ARC:SECONDARY");
        mniArc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                airportRegionDisplayChangeActionPerformed(evt);
            }
        });
        mnuMap.add(mniArc);

        mniTMA.setSelected(true);
        mniTMA.setText("TMA");
        mniTMA.setActionCommand("MAP/TMA:SECONDARY");
        mniTMA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                airportRegionDisplayChangeActionPerformed(evt);
            }
        });
        mnuMap.add(mniTMA);

        mniAPP.setSelected(true);
        mniAPP.setText("APP");
        mniAPP.setActionCommand("MAP/APP:SECONDARY");
        mniAPP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                airportRegionDisplayChangeActionPerformed(evt);
            }
        });
        mnuMap.add(mniAPP);

        mnuARR.setText("ARR");
        mnuMap.add(mnuARR);
        mnuMap.add(jSeparator5);

        mnuFixNav.setText("FIX/NAV");

        mniWayPoint.setSelected(true);
        mniWayPoint.setText("WAY POINTS");
        mniWayPoint.setActionCommand("MAP/FIXNAV/WAYPOINT:SECONDARY");
        mniWayPoint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniFixNavActionPerformed(evt);
            }
        });
        mnuFixNav.add(mniWayPoint);

        mniName.setSelected(true);
        mniName.setText("NAME");
        mniName.setActionCommand("MAP/FIXNAV/NAME:SECONDARY");
        mniName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniFixNavActionPerformed(evt);
            }
        });
        mnuFixNav.add(mniName);

        mniRoute.setSelected(true);
        mniRoute.setText("ROUTES");
        mniRoute.setActionCommand("MAP/FIXNAV/ROUTE:SECONDARY");
        mniRoute.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniFixNavActionPerformed(evt);
            }
        });
        mnuFixNav.add(mniRoute);

        mnuMap.add(mnuFixNav);

        mniCallsign.setSelected(true);
        mniCallsign.setText("CALLSIGN");
        mniCallsign.setActionCommand("TARGET/CALLSIGN:SECONDARY");
        mniCallsign.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniTargetLabelActionPerformed(evt);
            }
        });
        mnuTarget.add(mniCallsign);

        mniSSR.setSelected(true);
        mniSSR.setText("SSR/24bits");
        mniSSR.setActionCommand("TARGET/SSR:SECONDARY");
        mniSSR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniTargetLabelActionPerformed(evt);
            }
        });
        mnuTarget.add(mniSSR);

        mniSpeed.setSelected(true);
        mniSpeed.setText("SPD");
        mniSpeed.setActionCommand("TARGET/SPD:SECONDARY");
        mniSpeed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniTargetLabelActionPerformed(evt);
            }
        });
        mnuTarget.add(mniSpeed);

        mniAltitude.setSelected(true);
        mniAltitude.setText("ALT");
        mniAltitude.setActionCommand("TARGET/ALT:SECONDARY");
        mniAltitude.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniTargetLabelActionPerformed(evt);
            }
        });
        mnuTarget.add(mniAltitude);

        mniHDGNote.setSelected(true);
        mniHDGNote.setText("HDG Note");
        mniHDGNote.setActionCommand("TARGET/HDGNOTE:SECONDARY");
        mniHDGNote.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniTargetLabelActionPerformed(evt);
            }
        });
        mnuTarget.add(mniHDGNote);
        mnuTarget.add(jSeparator8);

        mniAllOn.setText("All On");
        mniAllOn.setActionCommand("ALLON");
        mniAllOn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniTargetLabelActionPerformed(evt);
            }
        });
        mnuTarget.add(mniAllOn);

        mniAllOff.setText("All Off");
        mniAllOff.setActionCommand("ALLOFF");
        mniAllOff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniTargetLabelActionPerformed(evt);
            }
        });
        mnuTarget.add(mniAllOff);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Second Screen");
        setMinimumSize(new java.awt.Dimension(640, 480));
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.setMaximumSize(new java.awt.Dimension(319, 30));
        jToolBar1.setMinimumSize(new java.awt.Dimension(312, 30));
        jToolBar1.setPreferredSize(new java.awt.Dimension(100, 30));
        jToolBar1.add(filler1);

        btnMap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/adsb/client/images/map.png"))); // NOI18N
        btnMap.setToolTipText("Map menu");
        btnMap.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnMap.setFocusable(false);
        btnMap.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnMap.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnMap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMapActionPerformed(evt);
            }
        });
        jToolBar1.add(btnMap);

        btnTarget.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/adsb/client/images/target.png"))); // NOI18N
        btnTarget.setToolTipText("Target Menu");
        btnTarget.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnTarget.setFocusable(false);
        btnTarget.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnTarget.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnTarget.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTargetActionPerformed(evt);
            }
        });
        jToolBar1.add(btnTarget);
        jToolBar1.add(jSeparator1);

        btnRGN.setText("jButton1");
        btnRGN.setFocusable(false);
        btnRGN.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnRGN.setMaximumSize(new java.awt.Dimension(80, 24));
        btnRGN.setMinimumSize(new java.awt.Dimension(80, 24));
        btnRGN.setPreferredSize(new java.awt.Dimension(80, 32));
        btnRGN.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnRGN);

        sdrRNG.setMajorTickSpacing(20);
        sdrRNG.setMaximum(300);
        sdrRNG.setMinimum(1);
        sdrRNG.setMinorTickSpacing(5);
        sdrRNG.setPaintTicks(true);
        sdrRNG.setMaximumSize(new java.awt.Dimension(200, 24));
        sdrRNG.setMinimumSize(new java.awt.Dimension(200, 24));
        sdrRNG.setPreferredSize(new java.awt.Dimension(200, 24));
        sdrRNG.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sdrRNGStateChanged(evt);
            }
        });
        jToolBar1.add(sdrRNG);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 640, Short.MAX_VALUE)
            .addComponent(pnlContent)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(pnlContent, javax.swing.GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void sdrRNGStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sdrRNGStateChanged
        try {
            int value = sdrRNG.getValue();
            setZoom(value);
           
            this.painter.setZoom(Configuration.instance().getZoomLevelConfig().getZoomLevels(value - 1));
            this.gljpanel.display();
            appConfig.setParameter(SECONDARY_SELECTED_ZOOM_LEVEL, value);
            appConfig.save();
        } catch (Exception ex) {
            logger.error(ex);
        } finally {
	    
	}
    }//GEN-LAST:event_sdrRNGStateChanged

    private void mniFixNavActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniFixNavActionPerformed
       try {
	    JCheckBoxMenuItem item = (JCheckBoxMenuItem) evt.getSource();
	    String command = evt.getActionCommand();
	    switch (command) {
		case "MAP/FIXNAV/WAYPOINT:SECONDARY":
		    this.fixPointGraphic.setEnable(item.isSelected());
		    break;

//                case "VORDME":
//                Configuration.instance().getGraphic().setVordmeDisplay(item.isSelected());
//                vordmeGraphic.setEnable(item.isSelected());
//                break;
//
//                case "NDB":
//                Configuration.instance().getGraphic().setNdbDisplay(item.isSelected());
//                ndbGraphic.setEnable(item.isSelected());
//                break;
		case "MAP/FIXNAV/NAME:SECONDARY":
//		    this.vordmeGraphic.setEnableLabel(item.isSelected());
		    this.fixPointGraphic.setEnableLabel(item.isSelected());
//		    this.ndbGraphic.setEnableLabel(item.isSelected());
		    this.routeGraphic.setEnableLabel(item.isSelected());
//		    appConfig.setLabelDisplay(item.isSelected());
//		    Painter.context.setShowLabel(item.isSelected());
		    break;

		case "MAP/FIXNAV/ROUTE:SECONDARY":
		    routeGraphic.setEnable(item.isSelected());
		    break;

	    }
	    appConfig.setParameter(command, item.isSelected());
	} catch (Exception ex) {
	    logger.error(ex);
	} finally {
	    appConfig.save();
	}
    }//GEN-LAST:event_mniFixNavActionPerformed

    private void mniTargetLabelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniTargetLabelActionPerformed
        try {
            JCheckBoxMenuItem item = null;
            String command = evt.getActionCommand();
            switch (command) {
                case "TARGET/CALLSIGN:SECONDARY":
                    item = (JCheckBoxMenuItem) evt.getSource();
                    targetGraphic.getLabelDisplay().setTargetCallsignVisible(item.isSelected());
                    appConfig.setParameter(command, item.isSelected());
                    logger.info("Set %s to %s", command, item.isSelected());
                    break;
                case "TARGET/SSR:SECONDARY":
                    item = (JCheckBoxMenuItem) evt.getSource();
                    targetGraphic.getLabelDisplay().setTargetSSRVisible(item.isSelected());
                    appConfig.setParameter(command, item.isSelected());
                    logger.info("Set %s to %s", command, item.isSelected());
                    break;
                case "TARGET/SPD:SECONDARY":
                    item = (JCheckBoxMenuItem) evt.getSource();
                    targetGraphic.getLabelDisplay().setTargetSPDVisible(item.isSelected());
                    appConfig.setParameter(command, item.isSelected());
                    logger.info("Set %s to %s", command, item.isSelected());
                    break;
                case "TARGET/ALT:SECONDARY":
                    item = (JCheckBoxMenuItem) evt.getSource();
                    targetGraphic.getLabelDisplay().setTargetALTVisible(item.isSelected());
                    appConfig.setParameter(command, item.isSelected());
                    logger.info("Set %s to %s", command, item.isSelected());
                    break;
                case "TARGET/HDGNOTE:SECONDARY":
                    item = (JCheckBoxMenuItem) evt.getSource();
                    targetGraphic.getLabelDisplay().setTargetCALTVisible(item.isSelected());
                    appConfig.setParameter(command, item.isSelected());
                    logger.info("Set %s to %s", command, item.isSelected());
                    break;
                case "ALLON":
                    setAllTargetLabelAllOn(true);
                    break;
                case "ALLOFF":
                    setAllTargetLabelAllOn(false);
                    break;
            }
            appConfig.save();
            logger.info("Configuration file is saved to file %s", Common.CFG_APP);
        } catch (Exception ex) {
            ExHandler.handle(ex);
        } finally {

        }
    }//GEN-LAST:event_mniTargetLabelActionPerformed

    private void airportRegionDisplayChangeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_airportRegionDisplayChangeActionPerformed
        // TODO add your handling code here:
        try {
	    JCheckBoxMenuItem item = (JCheckBoxMenuItem) evt.getSource();
	    if (item == null) return;
	    String command = item.getActionCommand();
	    String name = item.getText();
	    
//	    JPopupMenu container =  (JPopupMenu) item.getParent();
//	    JMenu menu = (JMenu) container.getInvoker();
	    
	    if (command.startsWith("TMA:")) {
		activeReportGraphic.setEnable("TMA", item.isSelected());
	    } else if (command.startsWith("APP:")) {
		activeReportGraphic.setEnable("APP", item.isSelected());
	    } else if (command.startsWith("ARR:")) {
		activeReportGraphic.setEnable("ARR", name, item.isSelected());
	    } else if (command.startsWith("FIX:")) {
		activeReportGraphic.setEnable("FIX", name, item.isSelected());
	    } else if (command.startsWith("PROC:")) {
		JPopupMenu container = (JPopupMenu) item.getParent();
		JMenu menu = (JMenu) container.getInvoker();
		String groupName = menu.getText();
		activeReportGraphic.setEnable("PROC", groupName, name, item.isSelected());
//		System.out.println("Parent: " + menu.getText());
	    } else if (command.startsWith("RWY:")) {
		activeReportGraphic.setEnable("RWY", name, item.isSelected());
	    } else if (command.startsWith("ARC:")) {
		activeReportGraphic.setDisplayArc(item.isSelected());
	    } else if (command.startsWith("CIRCLE:")) {
		activeReportGraphic.setDisplayCircle(item.isSelected());
	    }

//	    AppConfig config = Configuration.instance().getGraphic();
	    appConfig.setParameter(command, item.isSelected());
	    appConfig.save();
	} catch (Exception ex) {
	    logger.error(ex);
	} finally {
	    appConfig.save();
	}
    }//GEN-LAST:event_airportRegionDisplayChangeActionPerformed

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        try {
            gljpanel.addMouseListener(this);
            gljpanel.addMouseMotionListener(this);
            gljpanel.addMouseWheelListener(this);
            gljpanel.display();
        } catch (Exception ex) {
            logger.error(ex);
        }
    }//GEN-LAST:event_formComponentShown

    private void btnMapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMapActionPerformed
	try {
	    final int height = mnuMap.getPreferredSize().height;
	    final JButton button = (JButton) evt.getSource();
	    mnuMap.show(button, 0, 0 - height - 4);
	} catch (Exception ex) {
	    logger.error(ex);
	}
	
    }//GEN-LAST:event_btnMapActionPerformed

    private void btnTargetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTargetActionPerformed
       try {

	    final int height = mnuTarget.getPreferredSize().height;
	    final JButton button = (JButton) evt.getSource();
	    mnuTarget.show(button, 0, 0 - height - 4);
	} catch (Exception ex) {
	    logger.error(ex);
	}
    }//GEN-LAST:event_btnTargetActionPerformed

//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(SecondaryScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(SecondaryScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(SecondaryScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(SecondaryScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//        //</editor-fold>
//
//        /* Create and display the dialog */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                SecondaryScreen dialog = new SecondaryScreen(new javax.swing.JFrame(), true);
//                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
//                    @Override
//                    public void windowClosing(java.awt.event.WindowEvent e) {
//                        System.exit(0);
//                    }
//                });
//                dialog.setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnMap;
    private javax.swing.JButton btnRGN;
    private javax.swing.JButton btnTarget;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JPopupMenu.Separator jSeparator8;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JCheckBoxMenuItem mniAPP;
    private javax.swing.JMenuItem mniAllOff;
    private javax.swing.JMenuItem mniAllOn;
    private javax.swing.JCheckBoxMenuItem mniAltitude;
    private javax.swing.JCheckBoxMenuItem mniArc;
    private javax.swing.JCheckBoxMenuItem mniCallsign;
    private javax.swing.JCheckBoxMenuItem mniCircle;
    private javax.swing.JCheckBoxMenuItem mniHDGNote;
    private javax.swing.JCheckBoxMenuItem mniName;
    private javax.swing.JCheckBoxMenuItem mniRoute;
    private javax.swing.JCheckBoxMenuItem mniSSR;
    private javax.swing.JCheckBoxMenuItem mniSpeed;
    private javax.swing.JCheckBoxMenuItem mniTMA;
    private javax.swing.JCheckBoxMenuItem mniWayPoint;
    private javax.swing.JMenu mnuARR;
    private javax.swing.JMenu mnuAirport;
    private javax.swing.JMenu mnuFixNav;
    private javax.swing.JMenu mnuFixPoint;
    private javax.swing.JPopupMenu mnuMap;
    private javax.swing.JMenu mnuPROC;
    private javax.swing.JMenu mnuRUNWAY;
    private javax.swing.JPopupMenu mnuTarget;
    private javax.swing.JMenu mnuVVP;
    private javax.swing.JScrollPane pnlContent;
    private javax.swing.JSlider sdrRNG;
    // End of variables declaration//GEN-END:variables

    @Override
    public void mouseClicked(MouseEvent e) {
//        Point2f endPoint = Painter.context.convertFromScreenToOpenGL(e.getPoint());
	switch (e.getButton()) {
//	    case 1:
//		if (this.mouseContext.isMeasuring()) {
//		    this.measurementGraphic.setPoint(Painter.context.convertFromScreenToOpenGL(e.getPoint()));
//		} else {
//		    this.targetGraphic.handeMouseClick(e);
//		    gljpanel.display();
//		}
//
//		if (SelectDraw.isGetCordMouse) {
//		    String Lat = ConvertCordinate.convertLatDecimalToDegHMS(endPoint.getY());
//		    String Lon = ConvertCordinate.convertLongDecimalToDegHMS(endPoint.getX());
//		    SelectDraw.txtDegN.setText(Lat.split(" ")[0]);
//		    SelectDraw.txtMinN.setText(Lat.split(" ")[1]);
//		    SelectDraw.txtSecN.setText(Lat.split(" ")[2]);
//		    SelectDraw.txtDegE.setText(Lon.split(" ")[0]);
//		    SelectDraw.txtMinE.setText(Lon.split(" ")[1]);
//		    SelectDraw.txtSecE.setText(Lon.split(" ")[2]);
//		    SelectDraw.PolygonList.clear();
//		    SelectDraw.PolygonList.add(endPoint);
//		}
//		if (SelectDraw.isUseMouse && !SelectDraw.isStopUseMouse) {
//		    SelectDraw.PolygonList.add(endPoint);
//		}
//
//		break;
//	    case 2:
//		break;
//	    case 3:
//		if (this.mouseContext.isMeasuring()) {
//		    this.measurementGraphic.finishRuler();
//		    this.mouseContext.setMeasuring(false);
//		    gljpanel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
//		    System.out.println("Finish ruler");
//		}
//
//		break;
	}
    }

    @Override
    public void mousePressed(MouseEvent e) {
	switch (e.getButton()) {
	    case 1:
		break;

	    case 2:
		break;

	    case 3:
		System.out.println("Begin dragging map at x = " + e.getPoint().getX() + " y = " + e.getPoint().getY());
		mouseContext.setDraggingMap(true);
		mouseContext.setSelectedPoint(e.getPoint());

//		if (SelectDraw.isUseMouse) {
//		    SelectDraw.isStopUseMouse = true;
//		}
		break;
	    default:
		break;
	}
	System.out.println("Press button:" + e.getButton());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println("End dragging");
	mouseContext.setDraggingMap(false);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        System.out.println("Mouse enter" + e.getButton());
    }

    @Override
    public void mouseExited(MouseEvent e) {
        System.out.println("Mouse out" + e.getButton());
    }

    @Override
    public void mouseDragged(MouseEvent e) {
	if (!mouseContext.isDraggingMap()) {
	    return;
	}
	Point2f endPoint = painter.convertFromScreenToOpenGL(e.getPoint());
	Point2f startPoint = painter.convertFromScreenToOpenGL(mouseContext.getSelectedPoint());
	float dx = endPoint.getX() - startPoint.getX();
	float dy = endPoint.getY() - startPoint.getY();
	this.painter.updatePan(dx, dy);
	this.gljpanel.display();
	mouseContext.setSelectedPoint(e.getPoint());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
	Point2f pan = painter.convertFromScreenToOpenGL(e.getPoint());
	this.gljpanel.display();
	Point2f p = Convertor.fromOpenglToWgs84(pan);

	if (!mouseContext.isDraggingMap()) {
	    return;
	}
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
	try {
	    int zoomLevel = sdrRNG.getValue();
	    int nextZoomLevel = zoomLevel + e.getWheelRotation();
	    if (nextZoomLevel > sdrRNG.getMaximum() || nextZoomLevel < sdrRNG.getMinimum()) {
		return;
	    }
	    sdrRNG.setValue(nextZoomLevel);
	} catch (Exception ex) {
	    logger.error(ex);
	}
    }
}
