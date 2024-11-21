/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.gui;

import com.attech.adsb.client.common.AppContext;
import com.attech.adsb.client.common.AssemblyInfo;
import com.attech.adsb.client.common.Common;
import com.attech.adsb.client.gui.mapSetup.MapBackgroundChangerDlg;
import com.attech.adsb.client.gui.filter.TargetFilterDlg;
import com.attech.adsb.client.gui.customDraw.CustomDrawDlg;
import com.attech.adsb.client.common.CustomCheckboxMenuItem;
import com.attech.adsb.client.common.ExHandler;
import com.attech.adsb.client.common.MLogger;
import com.attech.adsb.client.common.MouseContext;
import com.attech.adsb.client.common.executors.SocketReceivingHandler;
import com.attech.adsb.client.common.TargetManager;
import com.attech.adsb.client.config.AMA;
import com.attech.adsb.client.config.Airport;
import com.attech.adsb.client.config.AirportItem;
import com.attech.adsb.client.graphic.RGB;
import com.attech.adsb.client.config.Configuration;
import com.attech.adsb.client.config.CustomDrawListConfig;
import com.attech.adsb.client.config.AppConfig;
import com.attech.adsb.client.config.Point2f;
import com.attech.adsb.client.config.Polygon;
import com.attech.adsb.client.config.Preference;
import com.attech.adsb.client.config.Region;
import com.attech.adsb.client.config.RegionGroup;
import com.attech.adsb.client.config.Regions;
import com.attech.adsb.client.config.SiteMonitor;
import com.attech.adsb.client.config.TargetLabelDisplay;
import com.attech.adsb.client.config.VVD;

import com.attech.adsb.client.config.VVDs;
import com.attech.adsb.client.config.VVP;
import com.attech.adsb.client.config.VVPs;
import com.attech.adsb.client.config.VVR;
import com.attech.adsb.client.config.VVRs;
import com.attech.adsb.client.common.TmaValidator;
import com.attech.adsb.client.common.enums.MouseMode;
import com.attech.adsb.client.config.SectorTransferItem;
import com.attech.adsb.client.graphic.Convertor;
import com.attech.adsb.client.graphic.Painter;
import com.attech.adsb.client.graphic.objects.AmaGraphic;
import com.attech.adsb.client.graphic.objects.AirportGraphic;
import com.attech.adsb.client.graphic.objects.CustomDrawGraphic;
import com.attech.adsb.client.graphic.objects.FixPointGraphic;
import com.attech.adsb.client.graphic.objects.MapGraphic;
import com.attech.adsb.client.graphic.objects.MeasurementGraphic;
import com.attech.adsb.client.graphic.objects.MouseGraphic;
import com.attech.adsb.client.graphic.objects.NdbGroundStationGraphic;
import com.attech.adsb.client.graphic.objects.SectorsGraphic;
import com.attech.adsb.client.graphic.objects.RouteGraphic;
import com.attech.adsb.client.graphic.objects.SectorGraphic;
import com.attech.adsb.client.graphic.objects.TargetGraphic;
import com.attech.adsb.client.graphic.objects.TargetItem;
import com.attech.adsb.client.graphic.objects.VVDsGraphic;
import com.attech.adsb.client.graphic.objects.VVPsGraphic;
import com.attech.adsb.client.graphic.objects.VVRsGraphic;
import com.attech.adsb.client.gui.trafficList.TrafficListDlg;
import com.attech.adsb.client.gui.info.HelpDlg;
import com.attech.adsb.client.gui.mapSetup.ColorChangeNotify;
import com.attech.adsb.client.gui.playback.PlaybackDlg;
import com.attech.adsb.client.gui.stca.WarningEditorDlg;
import com.attech.adsb.client.gui.targetEditor.TargetEditor;
import com.attech.adsb.client.gui.targetList.TargetListDlg;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.util.FPSAnimator;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowAdapter;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JToggleButton;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author andh
 */
public class Main extends javax.swing.JFrame implements MouseListener, MouseMotionListener, ActionListener, MouseWheelListener, Runnable {

    private static final MLogger logger = MLogger.getLogger(Main.class);
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(5);

    public static final String WARNING_OUTSCR = "WARNING_OUTSCR";
    public static final String WARNING_AMA = "WARNING_AMA";
    public static final String WARNING_DRAW = "WARNING_DRAW";
    public static final String WARNING_VVPR = "WARNING_VVPR";
    public static final String WARNING_CLDC = "WARNING_CLDC";
    public static final String WARNING_STCA = "WARNING_STCA";
    public static final String WARNING_MTCA = "WARNING_MTCA";
    public static final String WARNING_MSA = "WARNING_MSA";
    public static final String WARNING_TRANSFER = "WARNING_TRANSFER";

    // Dialog
    private TrafficListDlg trafficListDlg;
    private TargetListDlg targetListDlg;
    private SecondaryScreenDlg secondarySreenDlg;
    private TargetFilterDlg targetFilterDlg;
    private PlaybackDlg playbackDlg;
    private HelpDlg helpDlg;
    private MapBackgroundChangerDlg mapBackgroundChangerDlg;
    private CustomDrawDlg customDrawDlg;
    private WarningEditorDlg warningEditorDlg;

    // Graphic
    private final Painter painter;
    private final FPSAnimator animator;
    private final GLJPanel gljpanel;

    // Graphic Items
    private MapGraphic mapGraphic;
    private VVPsGraphic vvpsGraphic;
    private VVDsGraphic vvdsGraphic;
    private VVRsGraphic vvrsGraphic;
    private AmaGraphic amaGraphic;
    private MouseGraphic mouseGraphic;
    private MeasurementGraphic measurementGraphic;
    private CustomDrawGraphic drawToolGraphic;
    private CustomDrawGraphic drawToolGraphicPreview;
    private SectorsGraphic sectorGraphic;
    private SectorGraphic localSectorGraphic;
    private AirportGraphic activeReportGraphic;
    private NdbGroundStationGraphic ndbGraphic;
    private NdbGroundStationGraphic vordmeGraphic;
    private FixPointGraphic fixPointGraphic;
    private RouteGraphic routeGraphic;
    private TargetGraphic primaryTargetGraphic;
    private TargetGraphic secondaryGraphic;

    // Config
    private final AppConfig appConfig = Configuration.instance().getGraphic();
    private final Preference preference = Configuration.instance().getPreference();
    private final VVDs vvds = Configuration.instance().getVVDs();
    private final VVPs vvps = Configuration.instance().getVVPs();
    private final VVRs vvrs = Configuration.instance().getVVRs();
    private final AMA ama = Configuration.instance().getAma();
    private final SiteMonitor sitemonitor = Configuration.instance().getSitemonitor();
    private final CustomDrawListConfig drawTool;

    // Other
    private final SocketReceivingHandler socketHandler;
//    private final Timer MyTimer = new Timer(1000, this);
//    private final MouseContext mouseContext;
    private final TargetManager targetManager;
//    private final TmaValidator tmaValidator;
    private TmaValidator tmaValidator;

//    public static String currentRercordFile;
    /**
     * Creates new form GUI
     */
    public Main() {
        setFullScreen(Configuration.instance().getPreference().isFullscreen());
        GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);
        capabilities.setDoubleBuffered(true);

        initComponents();

        this.painter = new Painter();
        this.tmaValidator = new TmaValidator();

        this.gljpanel = new GLJPanel(capabilities);
        this.gljpanel.addGLEventListener((GLEventListener) painter);
        this.gljpanel.setAutoSwapBufferMode(true);
        this.pnlContent.add(gljpanel);
        this.pnlContent.setViewportView(gljpanel);

        this.painter.setBgColor(new RGB(appConfig.getString(Common.BACKGROUND_COLOR)));

        // Zoom
        int zoomLevel = appConfig.getInt(Common.SELECTED_ZOOM_LEVEL);
        this.sdrRNG.setValue(zoomLevel);
        this.setZoom(zoomLevel);

        // Init graphic
        this.initMapGraphic();
        this.initTargetGraphic();

        this.activeReportGraphic = new AirportGraphic();
        this.painter.addGraphicObject(activeReportGraphic);
        this.mapGraphic = new MapGraphic(Configuration.instance().getMap());
        this.painter.addGraphicObject(mapGraphic);

        this.drawTool = Configuration.instance().getDrawTool();
        this.drawToolGraphic = new CustomDrawGraphic(drawTool);
        this.painter.addGraphicObject(drawToolGraphic);
        this.drawToolGraphicPreview = new CustomDrawGraphic();
        this.painter.addGraphicObject(drawToolGraphicPreview);

//	this.ama = Configuration.instance().getAma();
        this.amaGraphic = new AmaGraphic(ama);
        this.amaGraphic.setEnable(appConfig.getBoolean(mniAMA.getActionCommand()));
        this.mniAMA.setSelected(appConfig.getBoolean(mniAMA.getActionCommand()));
        this.painter.addGraphicObject(amaGraphic);
//	this.sitemonitor = Configuration.instance().getSitemonitor();

         this.mniTopographic.setSelected(appConfig.getBoolean(mniTopographic.getActionCommand()));

        //Menu Airport
        this.initAirportList();
        String selectedAirport = appConfig.getString(Common.SELECTED_AIRPORT);
        this.clearAirportResource();
        this.initAirport(selectedAirport);

        this.mouseGraphic = new MouseGraphic();
        this.painter.addGraphicObject(mouseGraphic);

        this.initVVDMenu();
        this.initVVPMenu();
        this.initVVRMenu();
        this.initVVXMenu();

        this.measurementGraphic = new MeasurementGraphic(Configuration.instance().getMeasurementConfig());
        this.painter.addGraphicObject(measurementGraphic);

        this.animator = new FPSAnimator(gljpanel, 4, true);
        this.animator.start();

        this.targetManager = new TargetManager();
        this.targetManager.setTargetGraphic(primaryTargetGraphic);
        this.targetManager.setSecondaryGraphic(secondaryGraphic);

        // Add validator
//	this.targetManager.addValidator(this.amaGraphic);
        this.targetManager.addValidator(this.amaGraphic);
        this.targetManager.addValidator(this.tmaValidator);
        this.targetManager.addValidator(this.vvpsGraphic);
        this.targetManager.addValidator(this.vvdsGraphic);
        this.targetManager.addValidator(this.vvrsGraphic);
        this.targetManager.addValidator(this.drawToolGraphic);

        initTransferItem();
        initHistoryLength();

        int speedVector = appConfig.getInt(Common.SPEED_VECTOR);
        AppContext.instance().setSpeedVector(speedVector);
//	Parameter.SPV = appConfig.getInt(Common.SPEED_VECTOR);
        this.scrSPV.setValue(speedVector);
        this.scrSPV.setToolTipText(String.format("%s min", speedVector));
        this.lblSPV.setText(String.format("SPV: %s min", speedVector));

        this.socketHandler = new SocketReceivingHandler(Configuration.instance().getUdpConfig());
        this.socketHandler.setTargetManager(targetManager);

        updateWarnPopupStatus();
        
        if (!preference.isDebugMode()) {
            mniAddTarget.setVisible(false);
            mniRemoveAllDummy.setVisible(false);
            jSeparator12.setVisible(false);
            
        }
//        MyTimer.start();


        // hidden Menu TRAFFIC LIST
        mniTrafficList.setVisible(false);
//        mniAddTarget.setVisible(false);
//        mniRemoveAllDummy.setVisible(false);
//        jSeparator12.setVisible(false);

    }

    private void setFullScreen(boolean isShowFullScreen) {

        if (isShowFullScreen) {
            Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
            this.setUndecorated(true);
            this.setAlwaysOnTop(true);
            this.setSize(screensize.width, screensize.height);
            this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        } else {
            this.setUndecorated(false);
            this.setAlwaysOnTop(false);
        }
    }

    private void initTransferItem() {
        final String currentSector = Configuration.instance().getPreference().getSector();
        mniLocalSector.setText(String.format("Local Sector (%s)", currentSector));
        mnuTargetTransfer.removeAll();
        List<SectorTransferItem> sectorTransferList = Configuration.instance().getSectorTransferCfg().getSectorTransferList();
        for (SectorTransferItem sectorTransferItem : sectorTransferList) {          
            if (!sectorTransferItem.getName().equalsIgnoreCase(currentSector)) {
                continue;
            }            
            for (String sector : sectorTransferItem.getSectors()) {
                JMenuItem menuItem = new JMenuItem(sector);
                menuItem.setActionCommand("TRANSFER_FLIGHT");
                menuItem.addActionListener(this::mniSelectedTargetActionPerformed);
                mnuTargetTransfer.add(menuItem);
            }
            break;
        }
        // Hidden LocalSector
        mniLocalSector.setVisible(false);
    }

    private void initHistoryLength() {
        String command = appConfig.getString(Common.HISTORY_LENGTH);
        switch (command) {
            case "0":
                mniZero.setSelected(true);
                primaryTargetGraphic.getLabelDisplay().setHistoryLength(0);
                btnHistoryLength.setText(String.format("HL: %s", mniZero.getText()));
                break;
            case "0.25":
                mni1P4Min.setSelected(true);
                primaryTargetGraphic.getLabelDisplay().setHistoryLength(0.25f);
                btnHistoryLength.setText(String.format("HL: %s", mni1P4Min.getText()));
                break;
            case "0.5":
                mni1P2Min.setSelected(true);
                primaryTargetGraphic.getLabelDisplay().setHistoryLength(0.5f);
                btnHistoryLength.setText(String.format("HL: %s", mni1P2Min.getText()));
                break;
            case "1":
                mni1Min.setSelected(true);
                primaryTargetGraphic.getLabelDisplay().setHistoryLength(1.0f);
                btnHistoryLength.setText(String.format("HL: %s", mni1Min.getText()));
                break;
            case "2":
                mni2Min.setSelected(true);
                primaryTargetGraphic.getLabelDisplay().setHistoryLength(2.0f);
                btnHistoryLength.setText(String.format("HL: %s", mni2Min.getText()));
                break;
            case "3":
                mni3Min.setSelected(true);
                primaryTargetGraphic.getLabelDisplay().setHistoryLength(3.0f);
                btnHistoryLength.setText(String.format("HL: %s", mni3Min.getText()));
                break;
            case "4":
                mni4Min.setSelected(true);
                primaryTargetGraphic.getLabelDisplay().setHistoryLength(4.0f);
                btnHistoryLength.setText(String.format("HL: %s", mni4Min.getText()));
                break;
            case "5":
                mni5Min.setSelected(true);
                primaryTargetGraphic.getLabelDisplay().setHistoryLength(5.0f);
                btnHistoryLength.setText(String.format("HL: %s", mni5Min.getText()));
                break;
            case "6":
                mni6Min.setSelected(true);
                primaryTargetGraphic.getLabelDisplay().setHistoryLength(6.0f);
                btnHistoryLength.setText(String.format("HL: %s", mni6Min.getText()));
                break;
            case "7":
                mni7Min.setSelected(true);
                primaryTargetGraphic.getLabelDisplay().setHistoryLength(7.0f);
                btnHistoryLength.setText(String.format("HL: %s", mni7Min.getText()));
                break;
            case "8":
                mni8Min.setSelected(true);
                primaryTargetGraphic.getLabelDisplay().setHistoryLength(8.0f);
                btnHistoryLength.setText(String.format("HL: %s", mni8Min.getText()));
                break;
            case "9":
                mni9Min.setSelected(true);
                primaryTargetGraphic.getLabelDisplay().setHistoryLength(9.0f);
                btnHistoryLength.setText(String.format("HL: %s", mni9Min.getText()));
                break;
            case "10":
                mni10Min.setSelected(true);
                primaryTargetGraphic.getLabelDisplay().setHistoryLength(10.0f);
                btnHistoryLength.setText(String.format("HL: %s", mni10Min.getText()));
                break;
        }
    }

    private void initMapGraphic() {
        Boolean labelEnable = appConfig.getBoolean(mniName.getActionCommand());
        Boolean enable = appConfig.getBoolean(mniWayPoint.getActionCommand());

        this.mniWayPoint.setSelected(enable);
        this.fixPointGraphic = new FixPointGraphic(Configuration.instance().getFixPointConfig());
        this.fixPointGraphic.setEnable(enable);
        this.fixPointGraphic.setEnableLabel(labelEnable);
        this.painter.addGraphicObject(fixPointGraphic);

        enable = appConfig.getBoolean(mniVorDme.getActionCommand());
        this.mniVorDme.setSelected(enable);
        this.vordmeGraphic = new NdbGroundStationGraphic(Configuration.instance().getVorDmeConfig());
        this.vordmeGraphic.setEnable(enable);
        this.vordmeGraphic.setEnableLabel(labelEnable);
        this.painter.addGraphicObject(vordmeGraphic);

        enable = appConfig.getBoolean(mniNdb.getActionCommand());
        this.mniNdb.setSelected(enable);
        this.ndbGraphic = new NdbGroundStationGraphic(Configuration.instance().getNdbConfig());
        this.ndbGraphic.setEnable(enable);
        this.ndbGraphic.setEnableLabel(labelEnable);
        this.painter.addGraphicObject(ndbGraphic);

        enable = appConfig.getBoolean(mniName.getActionCommand());
        this.mniName.setSelected(enable);

        enable = appConfig.getBoolean(mniRoute.getActionCommand());
        this.mniRoute.setSelected(enable);
        this.routeGraphic = new RouteGraphic(Configuration.instance().getRouteConfig());
        this.routeGraphic.setEnable(enable);
        this.routeGraphic.setEnableLabel(labelEnable);
        this.painter.addGraphicObject(routeGraphic);

        enable = appConfig.getBoolean(mniSectors.getActionCommand());
        this.mniSectors.setSelected(enable);
        this.sectorGraphic = new SectorsGraphic(Configuration.instance().getSectors());
        this.sectorGraphic.setEnable(enable);
        this.painter.addGraphicObject(sectorGraphic);

        enable = appConfig.getBoolean(mniLocalSector.getActionCommand());
        this.mniLocalSector.setSelected(enable);
        this.localSectorGraphic = this.sectorGraphic.getLocalSector();
        this.localSectorGraphic.setEnable(enable);
        this.painter.addGraphicObject(localSectorGraphic);
        
        enable = appConfig.getBoolean(mniTopographic.getActionCommand());
        this.mniTopographic.setSelected(enable);
    }

    private void initTargetGraphic() {
        TargetLabelDisplay labelDisplay = new TargetLabelDisplay();
        labelDisplay.setTargetCallsignVisible(appConfig.getBoolean(mniCallsign.getActionCommand()));
        labelDisplay.setTargetSSRVisible(appConfig.getBoolean(mniSSR.getActionCommand()));
        labelDisplay.setTargetSPDVisible(appConfig.getBoolean(mniSpeed.getActionCommand()));
        labelDisplay.setTargetALTVisible(appConfig.getBoolean(mniAltitude.getActionCommand()));
        labelDisplay.setTargetCALTVisible(appConfig.getBoolean(mniCAlt.getActionCommand()));
        labelDisplay.setTargetCoALTVisible(appConfig.getBoolean(mniCoAlt.getActionCommand()));
        labelDisplay.setTargetCTRLVisible(appConfig.getBoolean(mniController.getActionCommand()));
        labelDisplay.setTargetHDGNoteVisible(appConfig.getBoolean(mniHDGNote.getActionCommand()));
        labelDisplay.setTargetInfoVisible(appConfig.getBoolean(mniInfo.getActionCommand()));
        labelDisplay.setTargetHeadingVisible(appConfig.getBoolean(mniHeading.getActionCommand()));
        labelDisplay.setTargetTrackingVisible(appConfig.getBoolean(mniTracking.getActionCommand()));

        initTargetLabel(labelDisplay);

        this.primaryTargetGraphic = new TargetGraphic(Configuration.instance().getTargetConfig());
        this.primaryTargetGraphic.setLabelDisplay(labelDisplay);
        this.primaryTargetGraphic.setFilterCondition(appConfig.getFilterCondition());
        this.btnFilter.setSelected(appConfig.getFilterCondition().isActive());
        this.painter.addGraphicObject(primaryTargetGraphic);

        // Seperate the target display on secondary
        this.secondaryGraphic = new TargetGraphic(Configuration.instance().getTargetConfig());
        this.secondaryGraphic.setLabelDisplay(labelDisplay);
    }

    private void initTargetLabel(TargetLabelDisplay labelDisplay) {
        mniCallsign.setSelected(labelDisplay.isTargetCallsignVisible());
        mniSSR.setSelected(labelDisplay.isTargetSSRVisible());
        mniSpeed.setSelected(labelDisplay.isTargetSPDVisible());
        mniAltitude.setSelected(labelDisplay.isTargetALTVisible());
        mniCAlt.setSelected(labelDisplay.isTargetCALTVisible());
        mniCoAlt.setSelected(labelDisplay.isTargetCoALTVisible());
        mniController.setSelected(labelDisplay.isTargetCTRLVisible());
        mniHDGNote.setSelected(labelDisplay.isTargetHDGNoteVisible());
        mniInfo.setSelected(labelDisplay.isTargetInfoVisible());
        mniHeading.setSelected(labelDisplay.isTargetHeadingVisible());
        mniTracking.setSelected(labelDisplay.isTargetTrackingVisible());
    }

    private void initAirportList() {
        JCheckBoxMenuItem mniAirportItem;
        List<AirportItem> airportItems = Configuration.instance().getAirportList().getAirportList();
        String selectedAirport = Configuration.instance().getGraphic().getString(Common.SELECTED_AIRPORT);
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

    private void initVVDMenu() {
        List<VVD> vvdList = Configuration.instance().getVVDs().getVVD();
        if (vvdList == null || vvdList.isEmpty()) {
            mnuVVD.setEnabled(false);
            return;
        }

        this.mnuVVD.setEnabled(true);
//        this.vvds = Configuration.instance().getVVDs();
        this.vvdsGraphic = new VVDsGraphic(vvds);
        this.painter.addGraphicObject(vvdsGraphic);
        AppConfig config = Configuration.instance().getGraphic();
        for (VVD vvd : vvdList) {
            JCheckBoxMenuItem chkVVD = new CustomCheckboxMenuItem(vvd.getName());
            String command = String.format("VVD/%s", vvd.getName());
            Boolean enabled = config.getBoolean(command);
            chkVVD.setActionCommand(command);
            chkVVD.setSelected(enabled);
            chkVVD.addActionListener(this::vvdMenuActionPerformed);
            vvdsGraphic.setEnable(vvd.getName(), enabled);
            mnuVVD.add(chkVVD);
        }
    }

    private void initVVPMenu() {
        List<VVP> lstVVP = vvps.getVVP();
        if (lstVVP == null || lstVVP.isEmpty()) {
            mnuVVP.setEnabled(false);
            return;
        }

        this.vvpsGraphic = new VVPsGraphic(vvps);
        this.painter.addGraphicObject(vvpsGraphic);
        for (VVP vvp : lstVVP) {
            JCheckBoxMenuItem chkVVP = new CustomCheckboxMenuItem(vvp.getName());
            String command = String.format("VVP/%s", vvp.getName());
            Boolean enabled = appConfig.getBoolean(command);
            chkVVP.setActionCommand(command);
            chkVVP.setSelected(enabled);
            chkVVP.addActionListener(this::vvdMenuActionPerformed);
            vvpsGraphic.setEnable(vvp.getName(), enabled);
            mnuVVP.add(chkVVP);
        }
    }

    private void initVVRMenu() {
//        List<VVR> lstVVR = Configuration.instance().getVVRs().getVVR();
        List<VVR> lstVVR = vvrs.getVVR();
        if (lstVVR == null || lstVVR.isEmpty()) {
            mnuVVP.setEnabled(false);
            return;
        }

//        this.vvrs = Configuration.instance().getVVRs();
        this.vvrsGraphic = new VVRsGraphic(vvrs);
        this.painter.addGraphicObject(vvrsGraphic);
        AppConfig config = Configuration.instance().getGraphic();
        for (VVR vvp : lstVVR) {
            JCheckBoxMenuItem chkVVP = new CustomCheckboxMenuItem(vvp.getName());
            String command = String.format("VVR/%s", vvp.getName());
            Boolean enabled = config.getBoolean(command);
            chkVVP.setActionCommand(command);
            chkVVP.setSelected(enabled);
            chkVVP.addActionListener(this::vvdMenuActionPerformed);
            vvrsGraphic.setEnable(vvp.getName(), enabled);
            mnuVVR.add(chkVVP);
        }

    }

    private void initVVXMenu() {
        mnuVVX.setEnabled(false);
    }

    private void setAllTargetLabelAllOn(boolean display) {
        primaryTargetGraphic.getLabelDisplay().setAllDisplay(display);
        initTargetLabel(primaryTargetGraphic.getLabelDisplay());
        appConfig.setParameter(mniCallsign.getActionCommand(), primaryTargetGraphic.getLabelDisplay().isTargetCallsignVisible());
        appConfig.setParameter(mniSSR.getActionCommand(), primaryTargetGraphic.getLabelDisplay().isTargetSSRVisible());
        appConfig.setParameter(mniSpeed.getActionCommand(), primaryTargetGraphic.getLabelDisplay().isTargetSPDVisible());
        appConfig.setParameter(mniAltitude.getActionCommand(), primaryTargetGraphic.getLabelDisplay().isTargetALTVisible());
        appConfig.setParameter(mniCAlt.getActionCommand(), primaryTargetGraphic.getLabelDisplay().isTargetCALTVisible());
        appConfig.setParameter(mniCoAlt.getActionCommand(), primaryTargetGraphic.getLabelDisplay().isTargetCoALTVisible());
        appConfig.setParameter(mniController.getActionCommand(), primaryTargetGraphic.getLabelDisplay().isTargetCTRLVisible());
        appConfig.setParameter(mniHDGNote.getActionCommand(), primaryTargetGraphic.getLabelDisplay().isTargetHDGNoteVisible());
        appConfig.setParameter(mniInfo.getActionCommand(), primaryTargetGraphic.getLabelDisplay().isTargetInfoVisible());
        appConfig.setParameter(mniHeading.getActionCommand(), primaryTargetGraphic.getLabelDisplay().isTargetHeadingVisible());
        appConfig.setParameter(mniTracking.getActionCommand(), primaryTargetGraphic.getLabelDisplay().isTargetTrackingVisible());
    }

    private void updateWarnPopupStatus() {
        Boolean enable = appConfig.getBoolean(WARNING_OUTSCR);
        mniWarningOutSrc.setSelected(enable);
        primaryTargetGraphic.setOutOfScreenWarning(enable);

        enable = appConfig.getBoolean(WARNING_OUTSCR);
        mniWarningOutSrc.setSelected(enable);
        primaryTargetGraphic.setOutOfScreenWarning(enable);

        enable = appConfig.getBoolean(WARNING_AMA);
        mniWarningAMA.setSelected(enable);
        amaGraphic.setEnableValidator(enable);

        enable = appConfig.getBoolean(WARNING_DRAW);
        mniWarningDraw.setSelected(enable);
        drawToolGraphic.setEnableValidator(enable);
        
        enable = appConfig.getBoolean(WARNING_VVPR);
        vvdsGraphic.setEnableValidator(enable);
        vvpsGraphic.setEnableValidator(enable);
        vvrsGraphic.setEnableValidator(enable);
        mniWarningVVPR.setSelected(enable);

        enable = appConfig.getBoolean(WARNING_CLDC);
        AppContext.instance().setCldsWarning(enable);
        mniWarningCLDC.setSelected(enable);

        enable = appConfig.getBoolean(WARNING_STCA);
        this.targetManager.setSctaWarning(enable);
        mniWarningSTCA.setSelected(enable);
        
        enable = appConfig.getBoolean(WARNING_MTCA);
        this.targetManager.setMctaWarning(enable);
        mniWarningMTCA.setSelected(enable);

        enable = appConfig.getBoolean(WARNING_MSA);
        this.tmaValidator.setEnableValidator(enable);
        mniWarningMSA.setSelected(enable);
    }

    private void initAirport(String name) {

        if (name == null || name.isEmpty()) {
            name = "none";
        }

        String dataPath = Configuration.instance().getAirportList().getResourcePath(name);
        File root = new File("res");
        File rootFile = new File(root, dataPath);

//        String dataRoot = "res/" + name;
//        File rootFile = new File(dataRoot);
        if (!rootFile.exists() || !rootFile.isDirectory()) {
            return;
        }

        AppConfig config = Configuration.instance().getGraphic();
        String datapath = String.format("res/%s/airport.xml", dataPath);
        Airport selectedAirport = Airport.load(datapath);

//        activeAirport = selectedAirport;
//	activeReportGraphic = new AirportGraphic(activeAirport);
//	activeReportGraphic.setEnable(true);
//      painter.addGraphicObject(activeReportGraphic);
        try {
            boolean enable;
            String command;

            activeReportGraphic.setAvailable(false);
            activeReportGraphic.update(selectedAirport);

            mniArc.setActionCommand(String.format("ARC:%s", name));
            if (selectedAirport.getDisplayArc()) {
                enable = config.getBoolean(mniArc.getActionCommand());
                mniArc.setEnabled(true);
                mniArc.setSelected(enable);
                activeReportGraphic.setDisplayArc(enable);
            }

            mniCircle.setActionCommand(String.format("CIRCLE:%s", name));
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
                if (region.getRegionGroup() == null || region.getRegionGroup().isEmpty()) {
                    break;
                }

                switch (region.getType()) {
                    case "TMA":
                        command = String.format("TMA:%s", name);
                        enable = config.getBoolean(command);
//			mniTMA.setVisible(true);
                        mniTMA.setActionCommand(command);
                        mniTMA.setSelected(enable);
                        mniTMA.setEnabled(true);
                        activeReportGraphic.setEnable("TMA", enable);
                        File tmawarningFile = new File(rootFile, "tma-warning.xml");                        
                        if (tmawarningFile.exists()) {
                            this.tmaValidator = TmaValidator.load(tmawarningFile.getAbsolutePath(), TmaValidator.class);  
                        } 
                        break;

                    case "APP":
                        command = String.format("APP:%s", name);
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
                            command = String.format("ARR:%s:%s", name, arr.getName());
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
                            command = String.format("FIX:%s:%s", name, fixPoint.getName());
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
                                command = String.format("PROC:%s:%s:%s", name, regionGroup.getName(), regionItem.getName());
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
                            command = String.format("RWY:%s:%s", name, rwy.getName());
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

    public void proChangeActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            mnuMap.setVisible(true);
        } catch (Exception ex) {
            logger.error(ex);
        }
    }
    
    private void closeDialog(JDialog dialog) {
        if (dialog == null || !dialog.isVisible()) return;
        dialog.dispose();
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
            appConfig.setParameter(Common.SELECTED_AIRPORT, menuItem.getText());
            appConfig.save();
        } catch (Exception ex) {
            logger.error(ex);
        } finally {
            
        }
        mnuMap.setVisible(true);
    }

    public void vvdMenuActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            JCheckBoxMenuItem item = (JCheckBoxMenuItem) evt.getSource();
            AppConfig config = Configuration.instance().getGraphic();
            String name = item.getText();

            if (item.getActionCommand().startsWith("VVD/")) {
                vvdsGraphic.setEnable(name, item.isSelected());
            } else if (item.getActionCommand().startsWith("VVP/")) {
                vvpsGraphic.setEnable(name, item.isSelected());
            } else if (item.getActionCommand().startsWith("VVR/")) {
                vvrsGraphic.setEnable(name, item.isSelected());
            }

            config.setParameter(item.getActionCommand(), item.isSelected());
            config.save();
        } catch (Exception ex) {
            logger.error(ex);
        }
    }

    private void setZoom(int value) {
        double level = Configuration.instance().getZoomLevelConfig().getZoomLevels(value - 1);
        this.btnRGN.setText("RNG: " + value);
        this.painter.setZoom(level);
        this.gljpanel.display();
        appConfig.setParameter(Common.SELECTED_ZOOM_LEVEL, value);
        logger.info("Set zoom level to %s:%s", value, level);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        try {
//	    if (ae.getSource() == MyTimer) {
////                if (Common.Parameter.Client) {
////                    timestr = cl.getDate();
////                }
//
//		TargetTimeOut();
//		TargetOutScreen();
//	    }
        } catch (Exception ex) {
            logger.error(ex);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        try {
            Point2f endPoint = painter.convertFromScreenToOpenGL(e.getPoint());
            this.primaryTargetGraphic.handeMouseClick(e);
            this.gljpanel.display();
            TargetItem selectedItem = primaryTargetGraphic.getSelectedTarget();

            switch (e.getButton()) {
                case 1:
                    switch (MouseContext.instance().getMode()) {
                        case Measure:
                            if (selectedItem != null) {
                                this.measurementGraphic.setPoint(selectedItem.getTarget());
                                break;
                            }
                            this.measurementGraphic.setPoint(endPoint);
                            break;

                        case Capture:
                            // Do update coordinate
                            
                            if (MouseContext.instance().getCoordinateCapptureRequester() != null) {
                                MouseContext.instance().getCoordinateCapptureRequester().captureCoordinate(endPoint);
                                MouseContext.instance().setMode(MouseMode.Normal);
                                MouseContext.instance().setCoordinateCapptureRequester(null);
                            }
                            
                            if (customDrawDlg == null || !customDrawDlg.isVisible()) {
                                MouseContext.instance().setMode(MouseMode.Normal);
                                break;
                            }
                            customDrawDlg.captureCoordinate(endPoint);
                            break;

                        default:
                            if (selectedItem != null) {
                                selectedItem.roteLable();
                            }
                            break;
                    }
                    break;
                    
                case 2:
                    break;
                    
                case 3:
                    switch (MouseContext.instance().getMode()) {
                        case Measure:
                            this.measurementGraphic.finishRuler();
                            MouseContext.instance().setMeasuring(false);
                            this.gljpanel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                            btnMeasure.setSelected(false);
                            break;
                        case Capture:
                            // Do update coordinate
                            break;
                        default:
                            if (selectedItem != null) {
                                switch (selectedItem.getTarget().getTrackStatus()) {
                                    case NONE:
                                        mniTargetAssum.setEnabled(true);
                                        mnuTargetTransfer.setEnabled(false);
                                        break;
                                    case CONTROLED:
                                        mniTargetAssum.setEnabled(false);
                                        mnuTargetTransfer.setEnabled(true);
                                        break;
                                    case TRANSFER_FROM_THIS:
                                        mniTargetAssum.setEnabled(true);
                                        mnuTargetTransfer.setEnabled(false);
                                        break;
                                    case TRANSFER_TO_THIS:
                                        mniTargetAssum.setEnabled(true);
                                        mnuTargetTransfer.setEnabled(false);
                                        break;
                                }
                                mnuSelectedTarget.show(e.getComponent(), e.getX(), e.getY());
                            }
                            break;
                    }
                    break;
            }
        } catch (Exception ex) {
            logger.error(ex);
        } finally {
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        try {
            switch (e.getButton()) {
                case 1:
                    break;

                case 2:
                    break;

                case 3:
                    MouseContext.instance().setDraggingMap(true);
                    MouseContext.instance().setSelectedPoint(e.getPoint());
                    break;
                default:
                    break;
            }

        } catch (Exception ex) {
            logger.error(ex);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        try {
            MouseContext.instance().setDraggingMap(false);
        } catch (Exception ex) {
            logger.error(ex);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        try {
            if (!MouseContext.instance().isDraggingMap()) {
                return;
            }
            Point2f endPoint = painter.convertFromScreenToOpenGL(e.getPoint());
            Point2f startPoint = painter.convertFromScreenToOpenGL(MouseContext.instance().getSelectedPoint());
            float dx = endPoint.getX() - startPoint.getX();
            float dy = endPoint.getY() - startPoint.getY();
            this.painter.updatePan(dx, dy);
            this.gljpanel.display();
            MouseContext.instance().setSelectedPoint(e.getPoint());
        } catch (Exception ex) {
            logger.error(ex);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        try {
            Point2f pan = painter.convertFromScreenToOpenGL(e.getPoint());
            MouseContext.instance().setCurrentPoint(pan);
            switch (MouseContext.instance().getMode()) {
                case Measure:
                    this.measurementGraphic.updateCandidate(pan);
                    break;
                case Capture:
                    if (customDrawDlg == null || !customDrawDlg.isVisible()) {
                        break;
                    }
                    customDrawDlg.updateCoordinate(pan);
                    break;
            }
            this.gljpanel.display();
            Point2f wgs84Point = Convertor.fromOpenglToWgs84(pan);
            this.lblLong.setText(Convertor.fromDecimalToWgs84Coord(wgs84Point.getLongtitude()).getLongtitude());
            this.lblLat.setText(Convertor.fromDecimalToWgs84Coord(wgs84Point.getLatitude()).getLatitude());
            this.amaGraphic.onMouseHover(pan);
        } catch (Exception ex) {
            logger.error(ex);
        }
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

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mnuMap = new javax.swing.JPopupMenu();
        mnuAirport = new javax.swing.JMenu();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        mnuFixPoint = new javax.swing.JMenu();
        mnuPROC = new javax.swing.JMenu();
        mnuRUNWAY = new javax.swing.JMenu();
        mniCircle = new CustomCheckboxMenuItem();
        mniArc = new CustomCheckboxMenuItem();
        mniTMA = new CustomCheckboxMenuItem();
        mniAPP = new CustomCheckboxMenuItem();
        mnuARR = new javax.swing.JMenu();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        mniAMA = new javax.swing.JCheckBoxMenuItem();
        mniSectors = new javax.swing.JCheckBoxMenuItem();
        mniLocalSector = new javax.swing.JCheckBoxMenuItem();
        mniTopographic = new javax.swing.JCheckBoxMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        mnuVVP = new javax.swing.JMenu();
        mnuVVR = new javax.swing.JMenu();
        mnuVVD = new javax.swing.JMenu();
        mnuVVX = new javax.swing.JMenu();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        mnuFixNav = new javax.swing.JMenu();
        mniWayPoint = new CustomCheckboxMenuItem();
        mniVorDme = new CustomCheckboxMenuItem();
        mniNdb = new CustomCheckboxMenuItem();
        mniName = new CustomCheckboxMenuItem();
        mniRoute = new CustomCheckboxMenuItem();
        mniDraw = new javax.swing.JMenuItem();
        mniSecondDisplay = new javax.swing.JMenuItem();
        mniSetup = new javax.swing.JMenuItem();
        mnuTarget = new javax.swing.JPopupMenu();
        mnuLabel = new javax.swing.JMenu();
        mniCallsign = new CustomCheckboxMenuItem();
        mniSSR = new CustomCheckboxMenuItem();
        mniSpeed = new CustomCheckboxMenuItem();
        mniAltitude = new CustomCheckboxMenuItem();
        mniCAlt = new CustomCheckboxMenuItem();
        mniCoAlt = new CustomCheckboxMenuItem();
        mniController = new CustomCheckboxMenuItem();
        mniHDGNote = new CustomCheckboxMenuItem();
        mniInfo = new CustomCheckboxMenuItem();
        mniHeading = new CustomCheckboxMenuItem();
        mniTracking = new CustomCheckboxMenuItem();
        jSeparator8 = new javax.swing.JPopupMenu.Separator();
        mniAllOn = new javax.swing.JMenuItem();
        mniAllOff = new javax.swing.JMenuItem();
        mniTrafficList = new javax.swing.JMenuItem();
        mniFilter = new javax.swing.JMenuItem();
        mniTargetList = new javax.swing.JMenuItem();
        jSeparator12 = new javax.swing.JPopupMenu.Separator();
        mniAddTarget = new javax.swing.JMenuItem();
        mniRemoveAllDummy = new javax.swing.JMenuItem();
        mnuWarning = new javax.swing.JPopupMenu();
        mnuWarningSetup = new javax.swing.JMenuItem();
        jSeparator6 = new javax.swing.JPopupMenu.Separator();
        mniWarningAMA = new CustomCheckboxMenuItem();
        mniWarningDraw = new CustomCheckboxMenuItem();
        mniWarningCLDC = new CustomCheckboxMenuItem();
        mniWarningVVPR = new CustomCheckboxMenuItem();
        mniWarningOutSrc = new CustomCheckboxMenuItem();
        mniWarningSTCA = new CustomCheckboxMenuItem();
        mniWarningMTCA = new CustomCheckboxMenuItem();
        mniWarningMSA = new CustomCheckboxMenuItem();
        mnuSelectedTarget = new javax.swing.JPopupMenu();
        mniTargetAssum = new javax.swing.JMenuItem();
        mnuTargetTransfer = new javax.swing.JMenu();
        mniTargetCancel = new javax.swing.JMenuItem();
        mniTargetSaving = new javax.swing.JMenuItem();
        mniTargetEdit = new javax.swing.JMenuItem();
        mnuZoomLevel = new javax.swing.JPopupMenu();
        mni50 = new javax.swing.JMenuItem();
        mni100 = new javax.swing.JMenuItem();
        mni150 = new javax.swing.JMenuItem();
        mni200 = new javax.swing.JMenuItem();
        mni250 = new javax.swing.JMenuItem();
        mnuHistoryLength = new javax.swing.JPopupMenu();
        mniZero = new javax.swing.JRadioButtonMenuItem();
        mni1P4Min = new javax.swing.JRadioButtonMenuItem();
        mni1P2Min = new javax.swing.JRadioButtonMenuItem();
        mni1Min = new javax.swing.JRadioButtonMenuItem();
        mni2Min = new javax.swing.JRadioButtonMenuItem();
        mni3Min = new javax.swing.JRadioButtonMenuItem();
        mni4Min = new javax.swing.JRadioButtonMenuItem();
        mni5Min = new javax.swing.JRadioButtonMenuItem();
        mni6Min = new javax.swing.JRadioButtonMenuItem();
        mni7Min = new javax.swing.JRadioButtonMenuItem();
        mni8Min = new javax.swing.JRadioButtonMenuItem();
        mni9Min = new javax.swing.JRadioButtonMenuItem();
        mni10Min = new javax.swing.JRadioButtonMenuItem();
        historyLengthGroup = new javax.swing.ButtonGroup();
        tbrMain = new javax.swing.JToolBar();
        btnMap = new javax.swing.JButton();
        btnTarget = new javax.swing.JButton();
        btnWarn = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        btnRGN = new javax.swing.JButton();
        sdrRNG = new javax.swing.JSlider();
        jSeparator7 = new javax.swing.JToolBar.Separator();
        btnFilter = new javax.swing.JToggleButton();
        jButton8 = new javax.swing.JButton();
        btnMeasure = new javax.swing.JToggleButton();
        btnMeasureClear = new javax.swing.JButton();
        jSeparator9 = new javax.swing.JToolBar.Separator();
        lblSPV = new javax.swing.JButton();
        scrSPV = new javax.swing.JSlider();
        jSeparator10 = new javax.swing.JToolBar.Separator();
        btnHistoryLength = new javax.swing.JButton();
        jSeparator11 = new javax.swing.JToolBar.Separator();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        lblLong = new javax.swing.JLabel();
        lblLat = new javax.swing.JLabel();
        btnPlayback = new javax.swing.JButton();
        btnHelp = new javax.swing.JButton();
        pnlContent = new javax.swing.JScrollPane();

        mnuAirport.setText("AIRPORTS");
        mnuMap.add(mnuAirport);
        mnuMap.add(jSeparator3);

        mnuFixPoint.setText("FIX POINTS");
        mnuFixPoint.setToolTipText("Fix Points");
        mnuFixPoint.setActionCommand("FIX_POINTS");
        mnuMap.add(mnuFixPoint);

        mnuPROC.setText("PROC");
        mnuMap.add(mnuPROC);

        mnuRUNWAY.setText("RUNWAY");
        mnuMap.add(mnuRUNWAY);

        mniCircle.setText("CIRCLE");
        mniCircle.setActionCommand("MAP/CIRCLE");
        mniCircle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                airportRegionDisplayChangeActionPerformed(evt);
            }
        });
        mnuMap.add(mniCircle);

        mniArc.setSelected(true);
        mniArc.setText("ARC");
        mniArc.setActionCommand("MAP/ARC");
        mniArc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                airportRegionDisplayChangeActionPerformed(evt);
            }
        });
        mnuMap.add(mniArc);

        mniTMA.setText("TMA");
        mniTMA.setActionCommand("MAP/TMA");
        mniTMA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                airportRegionDisplayChangeActionPerformed(evt);
            }
        });
        mnuMap.add(mniTMA);

        mniAPP.setText("APP");
        mniAPP.setActionCommand("MAP/APP");
        mniAPP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                airportRegionDisplayChangeActionPerformed(evt);
            }
        });
        mnuMap.add(mniAPP);

        mnuARR.setText("ARR");
        mnuMap.add(mnuARR);
        mnuMap.add(jSeparator2);

        mniAMA.setSelected(true);
        mniAMA.setText("AMA");
        mniAMA.setActionCommand("MAP/AMA");
        mniAMA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniAMAActionPerformed(evt);
            }
        });
        mnuMap.add(mniAMA);

        mniSectors.setSelected(true);
        mniSectors.setText("Sectors");
        mniSectors.setActionCommand("SECTORS");
        mniSectors.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniSectorsActionPerformed(evt);
            }
        });
        mnuMap.add(mniSectors);

        mniLocalSector.setSelected(true);
        mniLocalSector.setText("Local Sector");
        mniLocalSector.setActionCommand("LOCAL_SECTOR");
        mniLocalSector.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniSectorsActionPerformed(evt);
            }
        });
        mnuMap.add(mniLocalSector);

        mniTopographic.setSelected(true);
        mniTopographic.setText("Topographic");
        mniTopographic.setActionCommand("TOPOGRAPHIC");
        mniTopographic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniTopographicActionPerformed(evt);
            }
        });
        mnuMap.add(mniTopographic);
        mnuMap.add(jSeparator4);

        mnuVVP.setText("VVP");
        mnuMap.add(mnuVVP);

        mnuVVR.setText("VVR");
        mnuMap.add(mnuVVR);

        mnuVVD.setText("VVD");
        mnuMap.add(mnuVVD);

        mnuVVX.setText("WX");
        mnuMap.add(mnuVVX);
        mnuMap.add(jSeparator5);

        mnuFixNav.setText("FIX/NAV");

        mniWayPoint.setSelected(true);
        mniWayPoint.setText("WAY POINTS");
        mniWayPoint.setActionCommand("MAP/FIXNAV/WAYPOINT");
        mniWayPoint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniFixNavActionPerformed(evt);
            }
        });
        mnuFixNav.add(mniWayPoint);

        mniVorDme.setSelected(true);
        mniVorDme.setText("VOR/DME");
        mniVorDme.setActionCommand("MAP/FIXNAV/VORDME");
        mniVorDme.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniFixNavActionPerformed(evt);
            }
        });
        mnuFixNav.add(mniVorDme);

        mniNdb.setSelected(true);
        mniNdb.setText("NDB");
        mniNdb.setActionCommand("MAP/FIXNAV/NDB");
        mniNdb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniFixNavActionPerformed(evt);
            }
        });
        mnuFixNav.add(mniNdb);

        mniName.setSelected(true);
        mniName.setText("NAME");
        mniName.setActionCommand("MAP/FIXNAV/NAME");
        mniName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniFixNavActionPerformed(evt);
            }
        });
        mnuFixNav.add(mniName);

        mniRoute.setSelected(true);
        mniRoute.setText("ROUTES");
        mniRoute.setActionCommand("MAP/FIXNAV/ROUTE");
        mniRoute.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniFixNavActionPerformed(evt);
            }
        });
        mnuFixNav.add(mniRoute);

        mnuMap.add(mnuFixNav);

        mniDraw.setText("CUSTOM DRAW");
        mniDraw.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniDrawActionPerformed(evt);
            }
        });
        mnuMap.add(mniDraw);

        mniSecondDisplay.setText("SECONDARY SCREEN");
        mniSecondDisplay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniSecondDisplayActionPerformed(evt);
            }
        });
        mnuMap.add(mniSecondDisplay);

        mniSetup.setText("BACKGROUND COLOR");
        mniSetup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniSetupActionPerformed(evt);
            }
        });
        mnuMap.add(mniSetup);

        mnuLabel.setText("LABEL");

        mniCallsign.setSelected(true);
        mniCallsign.setText("CALLSIGN");
        mniCallsign.setActionCommand("TARGET/LABEL/CALLSIGN");
        mniCallsign.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniTargetLabelActionPerformed(evt);
            }
        });
        mnuLabel.add(mniCallsign);

        mniSSR.setSelected(true);
        mniSSR.setText("SSR/24bits");
        mniSSR.setActionCommand("TARGET/LABEL/SSR");
        mniSSR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniTargetLabelActionPerformed(evt);
            }
        });
        mnuLabel.add(mniSSR);

        mniSpeed.setSelected(true);
        mniSpeed.setText("SPD");
        mniSpeed.setActionCommand("TARGET/LABEL/SPD");
        mniSpeed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniTargetLabelActionPerformed(evt);
            }
        });
        mnuLabel.add(mniSpeed);

        mniAltitude.setSelected(true);
        mniAltitude.setText("ALT");
        mniAltitude.setActionCommand("TARGET/LABEL/ALT");
        mniAltitude.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniTargetLabelActionPerformed(evt);
            }
        });
        mnuLabel.add(mniAltitude);

        mniCAlt.setSelected(true);
        mniCAlt.setText("C.ALT");
        mniCAlt.setActionCommand("TARGET/LABEL/CALT");
        mniCAlt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniTargetLabelActionPerformed(evt);
            }
        });
        mnuLabel.add(mniCAlt);

        mniCoAlt.setSelected(true);
        mniCoAlt.setText("Co.ALT");
        mniCoAlt.setActionCommand("TARGET/LABEL/COALT");
        mniCoAlt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniTargetLabelActionPerformed(evt);
            }
        });
        mnuLabel.add(mniCoAlt);

        mniController.setSelected(true);
        mniController.setText("CTRL");
        mniController.setActionCommand("TARGET/LABEL/CTRL");
        mniController.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniTargetLabelActionPerformed(evt);
            }
        });
        mnuLabel.add(mniController);

        mniHDGNote.setSelected(true);
        mniHDGNote.setText("HDG NOTE");
        mniHDGNote.setActionCommand("TARGET/LABEL/HDGNOTE");
        mniHDGNote.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniTargetLabelActionPerformed(evt);
            }
        });
        mnuLabel.add(mniHDGNote);

        mniInfo.setSelected(true);
        mniInfo.setText("INFO");
        mniInfo.setActionCommand("TARGET/LABEL/INFO");
        mniInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniTargetLabelActionPerformed(evt);
            }
        });
        mnuLabel.add(mniInfo);

        mniHeading.setSelected(true);
        mniHeading.setText("HEADING");
        mniHeading.setActionCommand("TARGET/LABEL/HEADING");
        mniHeading.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniTargetLabelActionPerformed(evt);
            }
        });
        mnuLabel.add(mniHeading);

        mniTracking.setSelected(true);
        mniTracking.setText("TRACKING");
        mniTracking.setActionCommand("TARGET/LABEL/TRACKING");
        mniTracking.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniTargetLabelActionPerformed(evt);
            }
        });
        mnuLabel.add(mniTracking);
        mnuLabel.add(jSeparator8);

        mniAllOn.setText("All On");
        mniAllOn.setActionCommand("ALLON");
        mniAllOn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniTargetLabelActionPerformed(evt);
            }
        });
        mnuLabel.add(mniAllOn);

        mniAllOff.setText("All Off");
        mniAllOff.setActionCommand("ALLOFF");
        mniAllOff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniTargetLabelActionPerformed(evt);
            }
        });
        mnuLabel.add(mniAllOff);

        mnuTarget.add(mnuLabel);

        mniTrafficList.setText("TRAFFIC LIST");
        mniTrafficList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniTrafficListActionPerformed(evt);
            }
        });
        mnuTarget.add(mniTrafficList);

        mniFilter.setText("FITLER");
        mniFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniFilterActionPerformed(evt);
            }
        });
        mnuTarget.add(mniFilter);

        mniTargetList.setText("TARGET LIST");
        mniTargetList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniTargetListActionPerformed(evt);
            }
        });
        mnuTarget.add(mniTargetList);
        mnuTarget.add(jSeparator12);

        mniAddTarget.setText("Open Target");
        mniAddTarget.setActionCommand("TARGET_ADDING");
        mniAddTarget.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniTargetActionPerformed(evt);
            }
        });
        mnuTarget.add(mniAddTarget);

        mniRemoveAllDummy.setText("Remove dummy");
        mniRemoveAllDummy.setToolTipText("Remove all dummy target");
        mniRemoveAllDummy.setActionCommand("TARGET_REMOVING");
        mniRemoveAllDummy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniTargetActionPerformed(evt);
            }
        });
        mnuTarget.add(mniRemoveAllDummy);

        mnuWarningSetup.setText("SETUP");
        mnuWarningSetup.setToolTipText("");
        mnuWarningSetup.setActionCommand("WARNING_SETUP");
        mnuWarningSetup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuWarningSetupActionPerformed(evt);
            }
        });
        mnuWarning.add(mnuWarningSetup);
        mnuWarning.add(jSeparator6);

        mniWarningAMA.setSelected(true);
        mniWarningAMA.setText("AMA WRN");
        mniWarningAMA.setActionCommand("WARNING_AMA");
        mniWarningAMA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniWarningActionPerformed(evt);
            }
        });
        mnuWarning.add(mniWarningAMA);

        mniWarningDraw.setSelected(true);
        mniWarningDraw.setText("DRAW");
        mniWarningDraw.setActionCommand("WARNING_DRAW");
        mniWarningDraw.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniWarningActionPerformed(evt);
            }
        });
        mnuWarning.add(mniWarningDraw);

        mniWarningCLDC.setSelected(true);
        mniWarningCLDC.setText("CL/DC");
        mniWarningCLDC.setActionCommand("WARNING_CLDC");
        mniWarningCLDC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniWarningActionPerformed(evt);
            }
        });
        mnuWarning.add(mniWarningCLDC);

        mniWarningVVPR.setSelected(true);
        mniWarningVVPR.setText("VVP/R");
        mniWarningVVPR.setActionCommand("WARNING_VVPR");
        mniWarningVVPR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniWarningActionPerformed(evt);
            }
        });
        mnuWarning.add(mniWarningVVPR);

        mniWarningOutSrc.setSelected(true);
        mniWarningOutSrc.setText("OUT SCREEN");
        mniWarningOutSrc.setActionCommand("WARNING_OUTSCR");
        mniWarningOutSrc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniWarningActionPerformed(evt);
            }
        });
        mnuWarning.add(mniWarningOutSrc);

        mniWarningSTCA.setSelected(true);
        mniWarningSTCA.setText("STCA");
        mniWarningSTCA.setActionCommand("WARNING_STCA");
        mniWarningSTCA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniWarningActionPerformed(evt);
            }
        });
        mnuWarning.add(mniWarningSTCA);

        mniWarningMTCA.setSelected(true);
        mniWarningMTCA.setText("MTCA");
        mniWarningMTCA.setActionCommand("WARNING_MTCA");
        mniWarningMTCA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniWarningActionPerformed(evt);
            }
        });
        mnuWarning.add(mniWarningMTCA);

        mniWarningMSA.setSelected(true);
        mniWarningMSA.setText("MSA");
        mniWarningMSA.setActionCommand("WARNING_MSA");
        mniWarningMSA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniWarningActionPerformed(evt);
            }
        });
        mnuWarning.add(mniWarningMSA);

        mniTargetAssum.setText("Assum");
        mniTargetAssum.setActionCommand("ASSUM_FLIGHT");
        mniTargetAssum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniSelectedTargetActionPerformed(evt);
            }
        });
        mnuSelectedTarget.add(mniTargetAssum);

        mnuTargetTransfer.setText("Transfer");
        mnuTargetTransfer.setActionCommand("TRANSFER_FLIGHT");
        mnuTargetTransfer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniSelectedTargetActionPerformed(evt);
            }
        });
        mnuSelectedTarget.add(mnuTargetTransfer);

        mniTargetCancel.setText("Cancel");
        mniTargetCancel.setActionCommand("CANCEL_FLIGHT");
        mniTargetCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniSelectedTargetActionPerformed(evt);
            }
        });
        mnuSelectedTarget.add(mniTargetCancel);

        mniTargetSaving.setText("Save");
        mniTargetSaving.setActionCommand("SAVE_FLIGHT");
        mniTargetSaving.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniSelectedTargetActionPerformed(evt);
            }
        });
        mnuSelectedTarget.add(mniTargetSaving);

        mniTargetEdit.setText("Edit");
        mniTargetEdit.setActionCommand("TARGET_EDIT");
        mniTargetEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniSelectedTargetActionPerformed(evt);
            }
        });
        mnuSelectedTarget.add(mniTargetEdit);

        mni50.setText("50");
        mni50.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniZoomLevelActionPerformed(evt);
            }
        });
        mnuZoomLevel.add(mni50);

        mni100.setText("100");
        mni100.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniZoomLevelActionPerformed(evt);
            }
        });
        mnuZoomLevel.add(mni100);

        mni150.setText("150");
        mni150.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniZoomLevelActionPerformed(evt);
            }
        });
        mnuZoomLevel.add(mni150);

        mni200.setText("200");
        mni200.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniZoomLevelActionPerformed(evt);
            }
        });
        mnuZoomLevel.add(mni200);

        mni250.setText("250");
        mni250.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniZoomLevelActionPerformed(evt);
            }
        });
        mnuZoomLevel.add(mni250);

        historyLengthGroup.add(mniZero);
        mniZero.setSelected(true);
        mniZero.setText("0 min");
        mniZero.setActionCommand("0");
        mniZero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniHistoryLengthActionPerformed(evt);
            }
        });
        mnuHistoryLength.add(mniZero);

        historyLengthGroup.add(mni1P4Min);
        mni1P4Min.setText("1/4 min");
        mni1P4Min.setActionCommand("0.25");
        mni1P4Min.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniHistoryLengthActionPerformed(evt);
            }
        });
        mnuHistoryLength.add(mni1P4Min);

        historyLengthGroup.add(mni1P2Min);
        mni1P2Min.setText("1/2 min");
        mni1P2Min.setActionCommand("0.5");
        mni1P2Min.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniHistoryLengthActionPerformed(evt);
            }
        });
        mnuHistoryLength.add(mni1P2Min);

        historyLengthGroup.add(mni1Min);
        mni1Min.setText("1 min");
        mni1Min.setActionCommand("1");
        mni1Min.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniHistoryLengthActionPerformed(evt);
            }
        });
        mnuHistoryLength.add(mni1Min);

        historyLengthGroup.add(mni2Min);
        mni2Min.setText("2 min");
        mni2Min.setActionCommand("2");
        mni2Min.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniHistoryLengthActionPerformed(evt);
            }
        });
        mnuHistoryLength.add(mni2Min);

        historyLengthGroup.add(mni3Min);
        mni3Min.setText("3 min");
        mni3Min.setActionCommand("3");
        mni3Min.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniHistoryLengthActionPerformed(evt);
            }
        });
        mnuHistoryLength.add(mni3Min);

        historyLengthGroup.add(mni4Min);
        mni4Min.setText("4 min");
        mni4Min.setActionCommand("4");
        mni4Min.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniHistoryLengthActionPerformed(evt);
            }
        });
        mnuHistoryLength.add(mni4Min);

        historyLengthGroup.add(mni5Min);
        mni5Min.setText("5 min");
        mni5Min.setActionCommand("5");
        mni5Min.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniHistoryLengthActionPerformed(evt);
            }
        });
        mnuHistoryLength.add(mni5Min);

        historyLengthGroup.add(mni6Min);
        mni6Min.setText("6 min");
        mni6Min.setActionCommand("6");
        mni6Min.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniHistoryLengthActionPerformed(evt);
            }
        });
        mnuHistoryLength.add(mni6Min);

        historyLengthGroup.add(mni7Min);
        mni7Min.setText("7 min");
        mni7Min.setActionCommand("7");
        mni7Min.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniHistoryLengthActionPerformed(evt);
            }
        });
        mnuHistoryLength.add(mni7Min);

        historyLengthGroup.add(mni8Min);
        mni8Min.setText("8 min");
        mni8Min.setActionCommand("8");
        mni8Min.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniHistoryLengthActionPerformed(evt);
            }
        });
        mnuHistoryLength.add(mni8Min);

        historyLengthGroup.add(mni9Min);
        mni9Min.setText("9 min");
        mni9Min.setActionCommand("9");
        mni9Min.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniHistoryLengthActionPerformed(evt);
            }
        });
        mnuHistoryLength.add(mni9Min);

        historyLengthGroup.add(mni10Min);
        mni10Min.setText("10 min");
        mni10Min.setActionCommand("10");
        mni10Min.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniHistoryLengthActionPerformed(evt);
            }
        });
        mnuHistoryLength.add(mni10Min);

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1280, 800));
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        tbrMain.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        tbrMain.setFloatable(false);
        tbrMain.setRollover(true);

        btnMap.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnMap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/adsb/client/images/map.png"))); // NOI18N
        btnMap.setToolTipText("Map config");
        btnMap.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnMap.setFocusable(false);
        btnMap.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnMap.setMaximumSize(new java.awt.Dimension(32, 32));
        btnMap.setMinimumSize(new java.awt.Dimension(32, 32));
        btnMap.setPreferredSize(new java.awt.Dimension(32, 32));
        btnMap.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnMap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMapActionPerformed(evt);
            }
        });
        tbrMain.add(btnMap);

        btnTarget.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnTarget.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/adsb/client/images/target.png"))); // NOI18N
        btnTarget.setToolTipText("TARGET");
        btnTarget.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnTarget.setFocusable(false);
        btnTarget.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnTarget.setMaximumSize(new java.awt.Dimension(32, 32));
        btnTarget.setMinimumSize(new java.awt.Dimension(32, 32));
        btnTarget.setPreferredSize(new java.awt.Dimension(32, 32));
        btnTarget.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnTarget.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTargetActionPerformed(evt);
            }
        });
        tbrMain.add(btnTarget);

        btnWarn.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnWarn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/adsb/client/images/warning.png"))); // NOI18N
        btnWarn.setToolTipText("WARN");
        btnWarn.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnWarn.setFocusable(false);
        btnWarn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnWarn.setMaximumSize(new java.awt.Dimension(32, 32));
        btnWarn.setMinimumSize(new java.awt.Dimension(32, 32));
        btnWarn.setPreferredSize(new java.awt.Dimension(32, 32));
        btnWarn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnWarn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnWarnActionPerformed(evt);
            }
        });
        tbrMain.add(btnWarn);
        tbrMain.add(jSeparator1);

        btnRGN.setText("jButton1");
        btnRGN.setFocusable(false);
        btnRGN.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnRGN.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnRGN.setMaximumSize(new java.awt.Dimension(80, 24));
        btnRGN.setMinimumSize(new java.awt.Dimension(80, 24));
        btnRGN.setPreferredSize(new java.awt.Dimension(80, 32));
        btnRGN.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnRGN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRGNActionPerformed(evt);
            }
        });
        tbrMain.add(btnRGN);

        sdrRNG.setMajorTickSpacing(20);
        sdrRNG.setMaximum(300);
        sdrRNG.setMinimum(1);
        sdrRNG.setMinorTickSpacing(5);
        sdrRNG.setPaintTicks(true);
        sdrRNG.setMaximumSize(new java.awt.Dimension(200, 30));
        sdrRNG.setMinimumSize(new java.awt.Dimension(200, 20));
        sdrRNG.setPreferredSize(new java.awt.Dimension(200, 20));
        sdrRNG.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sdrRNGStateChanged(evt);
            }
        });
        tbrMain.add(sdrRNG);
        tbrMain.add(jSeparator7);

        btnFilter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/adsb/client/images/filter.png"))); // NOI18N
        btnFilter.setToolTipText("Filter target");
        btnFilter.setFocusable(false);
        btnFilter.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnFilter.setMaximumSize(new java.awt.Dimension(30, 28));
        btnFilter.setMinimumSize(new java.awt.Dimension(30, 28));
        btnFilter.setPreferredSize(new java.awt.Dimension(30, 28));
        btnFilter.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFilterActionPerformed(evt);
            }
        });
        tbrMain.add(btnFilter);

        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/adsb/client/images/home.png"))); // NOI18N
        jButton8.setToolTipText("Home");
        jButton8.setFocusable(false);
        jButton8.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton8.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tbrMain.add(jButton8);

        btnMeasure.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/adsb/client/images/ruler.png"))); // NOI18N
        btnMeasure.setToolTipText("Measure");
        btnMeasure.setFocusable(false);
        btnMeasure.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnMeasure.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnMeasure.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMeasureActionPerformed(evt);
            }
        });
        tbrMain.add(btnMeasure);

        btnMeasureClear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/adsb/client/images/delete.png"))); // NOI18N
        btnMeasureClear.setToolTipText("Clear");
        btnMeasureClear.setFocusable(false);
        btnMeasureClear.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnMeasureClear.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnMeasureClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMeasureClearActionPerformed(evt);
            }
        });
        tbrMain.add(btnMeasureClear);
        tbrMain.add(jSeparator9);

        lblSPV.setText("SPV");
        lblSPV.setFocusable(false);
        lblSPV.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblSPV.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblSPV.setMaximumSize(new java.awt.Dimension(80, 24));
        lblSPV.setMinimumSize(new java.awt.Dimension(80, 24));
        lblSPV.setPreferredSize(new java.awt.Dimension(80, 24));
        lblSPV.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tbrMain.add(lblSPV);

        scrSPV.setMajorTickSpacing(5);
        scrSPV.setMaximum(10);
        scrSPV.setMinorTickSpacing(1);
        scrSPV.setPaintTicks(true);
        scrSPV.setToolTipText("");
        scrSPV.setMaximumSize(new java.awt.Dimension(120, 30));
        scrSPV.setMinimumSize(new java.awt.Dimension(120, 30));
        scrSPV.setPreferredSize(new java.awt.Dimension(120, 30));
        scrSPV.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                scrSPVStateChanged(evt);
            }
        });
        tbrMain.add(scrSPV);
        tbrMain.add(jSeparator10);

        btnHistoryLength.setText("HL 10 min");
        btnHistoryLength.setToolTipText("History length");
        btnHistoryLength.setFocusable(false);
        btnHistoryLength.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnHistoryLength.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnHistoryLength.setMaximumSize(new java.awt.Dimension(90, 32));
        btnHistoryLength.setMinimumSize(new java.awt.Dimension(90, 32));
        btnHistoryLength.setPreferredSize(new java.awt.Dimension(90, 32));
        btnHistoryLength.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnHistoryLength.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHistoryLengthActionPerformed(evt);
            }
        });
        tbrMain.add(btnHistoryLength);
        tbrMain.add(jSeparator11);
        tbrMain.add(filler1);

        lblLong.setText("jLabel1");
        lblLong.setMaximumSize(new java.awt.Dimension(100, 25));
        lblLong.setMinimumSize(new java.awt.Dimension(100, 25));
        lblLong.setPreferredSize(new java.awt.Dimension(100, 25));
        tbrMain.add(lblLong);

        lblLat.setText("jLabel2");
        lblLat.setMaximumSize(new java.awt.Dimension(100, 25));
        lblLat.setMinimumSize(new java.awt.Dimension(100, 25));
        lblLat.setPreferredSize(new java.awt.Dimension(100, 25));
        tbrMain.add(lblLat);

        btnPlayback.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/adsb/client/images/play.png"))); // NOI18N
        btnPlayback.setFocusable(false);
        btnPlayback.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnPlayback.setMaximumSize(new java.awt.Dimension(30, 28));
        btnPlayback.setMinimumSize(new java.awt.Dimension(30, 28));
        btnPlayback.setPreferredSize(new java.awt.Dimension(30, 28));
        btnPlayback.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnPlayback.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPlaybackActionPerformed(evt);
            }
        });
        tbrMain.add(btnPlayback);

        btnHelp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/adsb/client/images/help.png"))); // NOI18N
        btnHelp.setToolTipText("ABOUT");
        btnHelp.setFocusable(false);
        btnHelp.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnHelp.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnHelp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHelpActionPerformed(evt);
            }
        });
        tbrMain.add(btnHelp);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tbrMain, javax.swing.GroupLayout.DEFAULT_SIZE, 1116, Short.MAX_VALUE)
            .addComponent(pnlContent)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(pnlContent, javax.swing.GroupLayout.DEFAULT_SIZE, 764, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(tbrMain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnMapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMapActionPerformed
        try {
            final int height = mnuMap.getPreferredSize().height;
            final JButton button = (JButton) evt.getSource();
            mnuMap.show(button, 0, 0 - height - 4);
        } catch (Exception ex) {
            logger.error(ex);
        }
    }//GEN-LAST:event_btnMapActionPerformed

    private void sdrRNGStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sdrRNGStateChanged
        try {
            this.setZoom(sdrRNG.getValue());
            appConfig.save();
        } catch (Exception ex) {
            logger.error(ex);
        }
    }//GEN-LAST:event_sdrRNGStateChanged

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        try {
            this.setIconImage(Common.ICON.getImage());

            this.gljpanel.addMouseListener(this);
            this.gljpanel.addMouseMotionListener(this);
            this.gljpanel.addMouseWheelListener(this);
            this.gljpanel.display();

            this.socketHandler.start();
            this.targetManager.startSelfCalculation();
            this.scheduler.scheduleAtFixedRate(this, 1, 1, TimeUnit.SECONDS);

            try {
                String jarFile = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getPath();
                AssemblyInfo assemblyInfo = new AssemblyInfo(jarFile);
                this.setTitle(String.format("%s %s", assemblyInfo.getProduct(), assemblyInfo.getVersion()));
            } catch (Exception ex) {
                this.setTitle("ADSB - Client");
                logger.error("Cannot load program built info");
            }

        } catch (Exception ex) {
            logger.error(ex);
        }
    }//GEN-LAST:event_formComponentShown

    private void btnTargetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTargetActionPerformed
        try {

            final int height = mnuTarget.getPreferredSize().height;
            final JButton button = (JButton) evt.getSource();
            mnuTarget.show(button, 0, 0 - height - 4);
        } catch (Exception ex) {
            logger.error(ex);
        }
    }//GEN-LAST:event_btnTargetActionPerformed

    private void btnWarnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnWarnActionPerformed
        try {

            final int height = mnuWarning.getPreferredSize().height;
            final JButton button = (JButton) evt.getSource();
            mnuWarning.show(button, 0, 0 - height - 4);

        } catch (Exception ex) {
            logger.error(ex);
        }
    }//GEN-LAST:event_btnWarnActionPerformed

    private void mniFixNavActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniFixNavActionPerformed
        try {
            JCheckBoxMenuItem item = (JCheckBoxMenuItem) evt.getSource();
            String command = evt.getActionCommand();
            switch (command) {
                case "MAP/FIXNAV/WAYPOINT":
                    this.fixPointGraphic.setEnable(item.isSelected());

                    break;
                case "MAP/FIXNAV/VORDME":
                    vordmeGraphic.setEnable(item.isSelected());
                    break;

                case "MAP/FIXNAV/NDB":
                    ndbGraphic.setEnable(item.isSelected());
                    break;

                case "MAP/FIXNAV/NAME":
                    this.vordmeGraphic.setEnableLabel(item.isSelected());
                    this.fixPointGraphic.setEnableLabel(item.isSelected());
                    this.ndbGraphic.setEnableLabel(item.isSelected());
                    this.routeGraphic.setEnableLabel(item.isSelected());
                    break;

                case "MAP/FIXNAV/ROUTE":
                    routeGraphic.setEnable(item.isSelected());
                    break;

            }
            appConfig.setParameter(command, item.isSelected());
            appConfig.save();
        } catch (Exception ex) {
            logger.error(ex);
        } finally {
        }
    }//GEN-LAST:event_mniFixNavActionPerformed

    private void btnMeasureClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMeasureClearActionPerformed
        try {
            this.measurementGraphic.finishRuler();
            this.measurementGraphic.clearRuler();
            if (MouseContext.instance().getMode() == MouseMode.Measure) {
                MouseContext.instance().setMode(MouseMode.Normal);
                this.gljpanel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
            btnMeasure.setSelected(false);
        } catch (Exception ex) {
            logger.error(ex);
        }
    }//GEN-LAST:event_btnMeasureClearActionPerformed

    private void mniSectorsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniSectorsActionPerformed
        try {
            JCheckBoxMenuItem item = (JCheckBoxMenuItem) evt.getSource();
            if (item == null) {
                return;
            }
            final String command = item.getActionCommand();
            switch (command) {
                case "SECTORS":
                    this.sectorGraphic.setEnable(item.isSelected());
                    break;

                case "LOCAL_SECTOR":
                    this.localSectorGraphic.setEnable(item.isSelected());
                    break;

            }
            appConfig.setParameter(command, item.isSelected());
            appConfig.save();
        } catch (Exception ex) {
            logger.error(ex);
        }
        mnuMap.setVisible(true);
    }//GEN-LAST:event_mniSectorsActionPerformed

    private void mniDrawActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniDrawActionPerformed
        try {
            if (customDrawDlg != null && customDrawDlg.isVisible()) {
                customDrawDlg.requestFocus();
                return;
            }
            customDrawDlg = new CustomDrawDlg(this, false, this.drawToolGraphic);
            customDrawDlg.setAlwaysOnTop(true);
            customDrawDlg.setVisible(true);
        } catch (Exception ex) {
            logger.error(ex);
        }
    }//GEN-LAST:event_mniDrawActionPerformed

    private void mniTargetLabelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniTargetLabelActionPerformed
        try {
            JCheckBoxMenuItem item = null;
            String command = evt.getActionCommand();
            switch (command) {
                case "TARGET/LABEL/CALLSIGN":
                    item = (JCheckBoxMenuItem) evt.getSource();
                    primaryTargetGraphic.getLabelDisplay().setTargetCallsignVisible(item.isSelected());
                    appConfig.setParameter(command, item.isSelected());
                    logger.info("Set %s to %s", command, item.isSelected());
                    break;
                case "TARGET/LABEL/SSR":
                    item = (JCheckBoxMenuItem) evt.getSource();
                    primaryTargetGraphic.getLabelDisplay().setTargetSSRVisible(item.isSelected());
                    appConfig.setParameter(command, item.isSelected());
                    logger.info("Set %s to %s", command, item.isSelected());
                    break;
                case "TARGET/LABEL/SPD":
                    item = (JCheckBoxMenuItem) evt.getSource();
                    primaryTargetGraphic.getLabelDisplay().setTargetSPDVisible(item.isSelected());
                    appConfig.setParameter(command, item.isSelected());
                    logger.info("Set %s to %s", command, item.isSelected());
                    break;
                case "TARGET/LABEL/ALT":
                    item = (JCheckBoxMenuItem) evt.getSource();
                    primaryTargetGraphic.getLabelDisplay().setTargetALTVisible(item.isSelected());
                    appConfig.setParameter(command, item.isSelected());
                    logger.info("Set %s to %s", command, item.isSelected());
                    break;
                case "TARGET/LABEL/CALT":
                    item = (JCheckBoxMenuItem) evt.getSource();
                    primaryTargetGraphic.getLabelDisplay().setTargetCALTVisible(item.isSelected());
                    appConfig.setParameter(command, item.isSelected());
                    logger.info("Set %s to %s", command, item.isSelected());
                    break;
                case "TARGET/LABEL/COALT":
                    item = (JCheckBoxMenuItem) evt.getSource();
                    primaryTargetGraphic.getLabelDisplay().setTargetCoALTVisible(item.isSelected());
                    appConfig.setParameter(command, item.isSelected());
                    logger.info("Set %s to %s", command, item.isSelected());
                    break;
                case "TARGET/LABEL/CTRL":
                    item = (JCheckBoxMenuItem) evt.getSource();
                    primaryTargetGraphic.getLabelDisplay().setTargetCTRLVisible(item.isSelected());
                    appConfig.setParameter(command, item.isSelected());
                    logger.info("Set %s to %s", command, item.isSelected());
                    break;
                case "TARGET/LABEL/HDGNOTE":
                    item = (JCheckBoxMenuItem) evt.getSource();
                    primaryTargetGraphic.getLabelDisplay().setTargetHDGNoteVisible(item.isSelected());
                    appConfig.setParameter(command, item.isSelected());
                    logger.info("Set %s to %s", command, item.isSelected());
                    break;
                case "TARGET/LABEL/INFO":
                    item = (JCheckBoxMenuItem) evt.getSource();
                    primaryTargetGraphic.getLabelDisplay().setTargetInfoVisible(item.isSelected());
                    appConfig.setParameter(command, item.isSelected());
                    logger.info("Set %s to %s", command, item.isSelected());
                    break;
                case "TARGET/LABEL/HEADING":
                    item = (JCheckBoxMenuItem) evt.getSource();
                    primaryTargetGraphic.getLabelDisplay().setTargetHeadingVisible(item.isSelected());
                    appConfig.setParameter(command, item.isSelected());
                    logger.info("Set %s to %s", command, item.isSelected());
                    break;
                case "TARGET/LABEL/TRACKING":
                    item = (JCheckBoxMenuItem) evt.getSource();
                    primaryTargetGraphic.getLabelDisplay().setTargetTrackingVisible(item.isSelected());
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
        try {
            JCheckBoxMenuItem item = (JCheckBoxMenuItem) evt.getSource();
            if (item == null) {
                return;
            }
            final String command = item.getActionCommand();
            final String name = item.getText();

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
            } else if (command.startsWith("RWY:")) {
                activeReportGraphic.setEnable("RWY", name, item.isSelected());
            } else if (command.startsWith("ARC:")) {
                activeReportGraphic.setDisplayArc(item.isSelected());
            } else if (command.startsWith("CIRCLE:")) {
                activeReportGraphic.setDisplayCircle(item.isSelected());
            }

            appConfig.setParameter(command, item.isSelected());
            appConfig.save();
            logger.info("Configuration file is saved to file %s", Common.CFG_APP);
        } catch (Exception ex) {
            logger.error(ex);
        }
    }//GEN-LAST:event_airportRegionDisplayChangeActionPerformed

    private void mniAMAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniAMAActionPerformed
        try {
            JCheckBoxMenuItem item = (JCheckBoxMenuItem) evt.getSource();
            this.amaGraphic.setEnable(item.isSelected());
            appConfig.setParameter(evt.getActionCommand(), item.isSelected());
            appConfig.save();
            logger.info("Configuration file is saved to file %s", Common.CFG_APP);
        } catch (Exception ex) {
            logger.error(ex);
        }
        mnuMap.setVisible(true);
    }//GEN-LAST:event_mniAMAActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        try {
            
            this.closeDialog(this.trafficListDlg);
            this.closeDialog(this.targetListDlg);
            this.closeDialog(this.secondarySreenDlg);
            this.closeDialog(this.targetFilterDlg);
            this.closeDialog(this.playbackDlg);
            this.closeDialog(this.helpDlg);
            this.closeDialog(this.mapBackgroundChangerDlg);
            this.closeDialog(this.customDrawDlg);
            this.closeDialog(this.warningEditorDlg);
            
            int confirmed = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to exit the program?",
                    "Confirmation",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (confirmed == JOptionPane.YES_OPTION) {
                System.exit(0);
            } else {
            }
        } catch (Exception ex) {
            logger.error(ex);
        }
    }//GEN-LAST:event_formWindowClosing

    private void scrSPVStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_scrSPVStateChanged
        try {
//	    Parameter.SPV = scrSPV.getValue();
            AppContext.instance().setSpeedVector(scrSPV.getValue());
            this.targetManager.recalculateSpeedVector();
            this.scrSPV.setToolTipText(String.format("%s min", scrSPV.getValue()));
            this.lblSPV.setText(String.format("SPV: %s min", scrSPV.getValue()));
            appConfig.setParameter(Common.SPEED_VECTOR, scrSPV.getValue());
            appConfig.save();
        } catch (Exception ex) {
            logger.error(ex);
        }
    }//GEN-LAST:event_scrSPVStateChanged

    private void mniWarningActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniWarningActionPerformed
        try {
            JCheckBoxMenuItem item = (JCheckBoxMenuItem) evt.getSource();
            if (item == null) {
                return;
            }
            String command = item.getActionCommand();
            Boolean enable = item.isSelected();
            switch (command) {
                case WARNING_OUTSCR:
                    primaryTargetGraphic.setOutOfScreenWarning(enable);
                    break;

                case WARNING_AMA:
                    amaGraphic.setEnableValidator(enable);
                    break;
                case "WARNING_SETUP":
                    if (warningEditorDlg !=null && warningEditorDlg.isVisible()) {
                        warningEditorDlg.requestFocus();
                        break;
                    }
                    warningEditorDlg = new WarningEditorDlg(this, false, Configuration.instance().getStcaConfig());
                    warningEditorDlg.setAlwaysOnTop(true);
                    warningEditorDlg.setVisible(true);
                    break;
                    
//                case "WARNING_TRANSFER":
//                    break;
                case WARNING_DRAW:
                    // 
                    drawToolGraphic.setEnableValidator(enable);
                    break;

                case WARNING_VVPR:
                    vvdsGraphic.setEnableValidator(enable);
                    vvpsGraphic.setEnableValidator(enable);
                    vvrsGraphic.setEnableValidator(enable);
                    break;

                case WARNING_CLDC:
                    AppContext.instance().setCldsWarning(enable);
                    break;

                case WARNING_STCA:
                    this.targetManager.setSctaWarning(enable);
                    break;
                    
                case WARNING_MTCA:
                    this.targetManager.setMctaWarning(enable);
                    break;

                case WARNING_MSA:
                    this.tmaValidator.setEnableValidator(enable);
                    break;
            }

            appConfig.setParameter(command, enable);
            appConfig.save();
        } catch (Exception ex) {
            logger.error(ex);
        }
    }//GEN-LAST:event_mniWarningActionPerformed

    private void mniSetupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniSetupActionPerformed
        try {
            if (mapBackgroundChangerDlg != null && mapBackgroundChangerDlg.isVisible()) {
                mapBackgroundChangerDlg.requestFocus();
                return;
            }

            mapBackgroundChangerDlg = new MapBackgroundChangerDlg(this, false);
            mapBackgroundChangerDlg.setColor(appConfig.getString(Common.BACKGROUND_COLOR));
            mapBackgroundChangerDlg.setColorChangeNotify(new ColorChangeNotify() {
                @Override
                public void colorChanged(int action, String color) {
                    switch (action) {
                        case JOptionPane.OK_OPTION:
                            appConfig.setParameter(Common.BACKGROUND_COLOR, color);
                            appConfig.save();
                            painter.setBgColor(new RGB(color));
                            break;
                        case JOptionPane.CANCEL_OPTION:
                            painter.setBgColor(new RGB(appConfig.getString(Common.BACKGROUND_COLOR)));
                            break;
                        default:
                            painter.setBgColor(new RGB(color));
                            break;
                    }
                }
            });
            mapBackgroundChangerDlg.setAlwaysOnTop(true);
            mapBackgroundChangerDlg.setVisible(true);

        } catch (Exception ex) {
            logger.error(ex);
        }
    }//GEN-LAST:event_mniSetupActionPerformed

    private void mniFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniFilterActionPerformed
        try {
            if (targetFilterDlg != null && targetFilterDlg.isVisible()) {
                targetFilterDlg.requestFocus();
                return;
            }

            targetFilterDlg = new TargetFilterDlg(this, false, this.primaryTargetGraphic);
            targetFilterDlg.setLocationRelativeTo(this);
            targetFilterDlg.setFocusable(false);
            targetFilterDlg.setAlwaysOnTop(true);
            targetFilterDlg.setVisible(true);
            targetFilterDlg.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent evt) {
                    primaryTargetGraphic.applyFilter(appConfig.getFilterCondition());
                    btnFilter.setSelected(appConfig.getFilterCondition().isActive());
                }
            });

        } catch (Exception ex) {
            logger.error(ex);
        }
    }//GEN-LAST:event_mniFilterActionPerformed

    private void mniTrafficListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniTrafficListActionPerformed
        try {
            if (trafficListDlg != null && trafficListDlg.isVisible()) {
                trafficListDlg.requestFocus();
                return;
            }

            trafficListDlg = new TrafficListDlg(this, false);
            trafficListDlg.setVisible(true);
            trafficListDlg.setAlwaysOnTop(true);
        } catch (Exception ex) {
            logger.error(ex);
        }
    }//GEN-LAST:event_mniTrafficListActionPerformed

    private void mniSecondDisplayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniSecondDisplayActionPerformed
        try {
            if (secondarySreenDlg != null && secondarySreenDlg.isVisible()) {
                secondarySreenDlg.requestFocus();
                return;
            }

            
            secondarySreenDlg = new SecondaryScreenDlg(this, false, this.secondaryGraphic);
            secondarySreenDlg.setVisible(true);
            secondarySreenDlg.setAlwaysOnTop(true);

        } catch (Exception ex) {
            logger.error(ex);
        }


    }//GEN-LAST:event_mniSecondDisplayActionPerformed

    private void mniTargetListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniTargetListActionPerformed
        try {
            if (targetListDlg != null && targetListDlg.isVisible()) {
                targetListDlg.requestFocus();
                return;
            }

            targetListDlg = new TargetListDlg(this, false, this.targetManager);
            targetListDlg.setLocatedNotify((x, y) -> {
                this.painter.setPanx(x);
                this.painter.setPany(y);
            });
            targetListDlg.setVisible(true);
            targetListDlg.setAlwaysOnTop(true);

        } catch (Exception ex) {
            logger.error(ex);
        }
    }//GEN-LAST:event_mniTargetListActionPerformed

    private void btnMeasureActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMeasureActionPerformed
        try {

            JToggleButton button = (JToggleButton) evt.getSource();
            boolean enable = button.isSelected();
            if (enable) {
                this.mouseGraphic.setEnable(true);
                MouseContext.instance().setMeasuring(true);
                MouseContext.instance().setMode(MouseMode.Measure);
                this.measurementGraphic.createRuler();
                this.gljpanel.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
            } else {
                if (MouseContext.instance().getMode() == MouseMode.Measure) {
                    this.measurementGraphic.finishRuler();
                    MouseContext.instance().setMode(MouseMode.Normal);
                }
            }
        } catch (Exception ex) {
            logger.error(ex);
        }
    }//GEN-LAST:event_btnMeasureActionPerformed

    private void mniSelectedTargetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniSelectedTargetActionPerformed
        try {
            TargetItem selectedItem = primaryTargetGraphic.getSelectedTarget();
            if (selectedItem == null) {
                return;
            }
            
            JMenuItem menuItem = (JMenuItem) evt.getSource();
            String actionCommand = evt.getActionCommand();
            switch (actionCommand) {
                case "CANCEL_FLIGHT":
                    this.targetManager.cancelFlight(selectedItem.getTarget());
                    break;

                case "ASSUM_FLIGHT":
                    this.targetManager.assumFlight(selectedItem.getTarget());
                    break;

                case "TRANSFER_FLIGHT":
                    this.targetManager.transferFlight(selectedItem.getTarget(), menuItem.getText());
                    break;

                case "SAVE_FLIGHT":
                    final JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY);
                    if (fileChooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) {
                        return;
                    }

                    File destination = fileChooser.getSelectedFile();
                    File savedFile = new File (destination, selectedItem.getCallSign() + ".xml");
                    String filepath = savedFile.getAbsolutePath();
                    selectedItem.getTarget().save(filepath);
                    break;
                    
                case "TARGET_EDIT" :
                    TargetEditor editor = new TargetEditor(this, false, selectedItem.getTarget());
                    editor.setAlwaysOnTop(true);
                    editor.setVisible(true);
                    
                    break;
            }

        } catch (Exception ex) {
            logger.error(ex);
        }
    }//GEN-LAST:event_mniSelectedTargetActionPerformed

    private void mniTargetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniTargetActionPerformed
        try {
            JMenuItem item = (JMenuItem) evt.getSource();
            if (item == null) {
                return;
            }
            String command = item.getActionCommand();
            switch (command) {
                case "TARGET_ADDING":
                    JFileChooser jfc = new JFileChooser(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());

                    jfc.setDialogTitle("Select an image");
                    jfc.setMultiSelectionEnabled(true);
                    jfc.setAcceptAllFileFilterUsed(false);
                    FileNameExtensionFilter filter = new FileNameExtensionFilter("Target", "xml");
                    jfc.addChoosableFileFilter(filter);

                    int returnValue = jfc.showOpenDialog(null);
                    if (returnValue != JFileChooser.APPROVE_OPTION) {
                        return;
                    }
                    File[] files = jfc.getSelectedFiles();
                    for (File file : files) {
                        if (file.isDirectory()) {
                            continue;
                        }
                        this.targetManager.loadDummyTargetFromFile(file.getAbsolutePath());
                    }

                    break;

                case "TARGET_REMOVING":
                    this.targetManager.removeAllDummy();
                    this.gljpanel.display();
                    break;
            }

        } catch (Exception ex) {
            logger.error(ex);
        }
    }//GEN-LAST:event_mniTargetActionPerformed

    private void mniZoomLevelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniZoomLevelActionPerformed
        try {
            Integer value = Integer.parseInt(evt.getActionCommand());
            this.setZoom(value);
            this.sdrRNG.setValue(value);
        } catch (Exception ex) {
            logger.error(ex);
        } finally {
            appConfig.save();
        }
    }//GEN-LAST:event_mniZoomLevelActionPerformed

    private void btnRGNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRGNActionPerformed
        try {
            final int height = mnuZoomLevel.getPreferredSize().height;
            final JButton button = (JButton) evt.getSource();
            mnuZoomLevel.show(button, 0, 0 - height - 4);
        } catch (Exception ex) {
            logger.error(ex);
        }
    }//GEN-LAST:event_btnRGNActionPerformed

    private void btnHistoryLengthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHistoryLengthActionPerformed
        try {
            final int height = mnuHistoryLength.getPreferredSize().height;
            final JButton button = (JButton) evt.getSource();
            mnuHistoryLength.show(button, 0, 0 - height - 4);
        } catch (Exception ex) {
            logger.error(ex);
        }
    }//GEN-LAST:event_btnHistoryLengthActionPerformed

    private void mniHistoryLengthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniHistoryLengthActionPerformed
        try {
            String actionCommand = evt.getActionCommand();
            JRadioButtonMenuItem item = (JRadioButtonMenuItem) evt.getSource();
            Float amount = Float.parseFloat(actionCommand);
            this.primaryTargetGraphic.getLabelDisplay().setHistoryLength(amount);
            btnHistoryLength.setText(String.format("HL: %s", item.getText()));
            appConfig.setParameter(Common.HISTORY_LENGTH, actionCommand);
            appConfig.save();
        } catch (Exception ex) {
            logger.error(ex);
        }
    }//GEN-LAST:event_mniHistoryLengthActionPerformed

    private void btnFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFilterActionPerformed
        try {
            appConfig.getFilterCondition().setActive(btnFilter.isSelected());
            this.primaryTargetGraphic.applyFilter(appConfig.getFilterCondition());
            appConfig.save();
        } catch (Exception ex) {
            logger.error(ex);
        }
    }//GEN-LAST:event_btnFilterActionPerformed

    private void btnPlaybackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPlaybackActionPerformed
        try {
            AppContext.instance().setPlaybackMode(true);
            if (playbackDlg != null && playbackDlg.isVisible()) {
                playbackDlg.requestFocus();
                return;
            }

            playbackDlg = new PlaybackDlg(this, false);
            playbackDlg.setTargetManager(targetManager);
            playbackDlg.setRecordLocation(Configuration.instance().getUdpConfig().getRecordingLocation());
            playbackDlg.setVisible(true);
            playbackDlg.setAlwaysOnTop(true);
        } catch (Exception ex) {
            logger.error(ex);
        }
    }//GEN-LAST:event_btnPlaybackActionPerformed

    private void btnHelpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHelpActionPerformed
        try {
            if (helpDlg != null && helpDlg.isVisible()) {
                helpDlg.requestFocus();
                return;
            }
            helpDlg = new HelpDlg(this, false);
            helpDlg.setAlwaysOnTop(true);
            helpDlg.setVisible(true);

        } catch (Exception ex) {
            logger.error(ex);
        }
    }//GEN-LAST:event_btnHelpActionPerformed

    private void mnuWarningSetupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuWarningSetupActionPerformed
        try {
            if (warningEditorDlg != null && warningEditorDlg.isVisible()) {
                warningEditorDlg.requestFocus();
                return;
            }
            warningEditorDlg = new WarningEditorDlg(this, false, Configuration.instance().getStcaConfig());
            warningEditorDlg.setAlwaysOnTop(true);
            warningEditorDlg.setVisible(true);
        } catch (Exception ex) {
            logger.error(ex);
        }
    }//GEN-LAST:event_mnuWarningSetupActionPerformed

    private void mniTopographicActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniTopographicActionPerformed
        try {
            JCheckBoxMenuItem item = (JCheckBoxMenuItem) evt.getSource();
            appConfig.setParameter(evt.getActionCommand(), item.isSelected());
            appConfig.save();
            logger.info("Configuration file is saved to file %s", Common.CFG_APP);
        } catch (Exception ex) {
            logger.error(ex);
        }
        mnuMap.setVisible(true);
    }//GEN-LAST:event_mniTopographicActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton btnFilter;
    private javax.swing.JButton btnHelp;
    private javax.swing.JButton btnHistoryLength;
    private javax.swing.JButton btnMap;
    private javax.swing.JToggleButton btnMeasure;
    private javax.swing.JButton btnMeasureClear;
    private javax.swing.JButton btnPlayback;
    private javax.swing.JButton btnRGN;
    private javax.swing.JButton btnTarget;
    private javax.swing.JButton btnWarn;
    private javax.swing.Box.Filler filler1;
    private javax.swing.ButtonGroup historyLengthGroup;
    private javax.swing.JButton jButton8;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator10;
    private javax.swing.JToolBar.Separator jSeparator11;
    private javax.swing.JPopupMenu.Separator jSeparator12;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JPopupMenu.Separator jSeparator6;
    private javax.swing.JToolBar.Separator jSeparator7;
    private javax.swing.JPopupMenu.Separator jSeparator8;
    private javax.swing.JToolBar.Separator jSeparator9;
    private javax.swing.JLabel lblLat;
    private javax.swing.JLabel lblLong;
    private javax.swing.JButton lblSPV;
    private javax.swing.JMenuItem mni100;
    private javax.swing.JRadioButtonMenuItem mni10Min;
    private javax.swing.JMenuItem mni150;
    private javax.swing.JRadioButtonMenuItem mni1Min;
    private javax.swing.JRadioButtonMenuItem mni1P2Min;
    private javax.swing.JRadioButtonMenuItem mni1P4Min;
    private javax.swing.JMenuItem mni200;
    private javax.swing.JMenuItem mni250;
    private javax.swing.JRadioButtonMenuItem mni2Min;
    private javax.swing.JRadioButtonMenuItem mni3Min;
    private javax.swing.JRadioButtonMenuItem mni4Min;
    private javax.swing.JMenuItem mni50;
    private javax.swing.JRadioButtonMenuItem mni5Min;
    private javax.swing.JRadioButtonMenuItem mni6Min;
    private javax.swing.JRadioButtonMenuItem mni7Min;
    private javax.swing.JRadioButtonMenuItem mni8Min;
    private javax.swing.JRadioButtonMenuItem mni9Min;
    private javax.swing.JCheckBoxMenuItem mniAMA;
    private javax.swing.JCheckBoxMenuItem mniAPP;
    private javax.swing.JMenuItem mniAddTarget;
    private javax.swing.JMenuItem mniAllOff;
    private javax.swing.JMenuItem mniAllOn;
    private javax.swing.JCheckBoxMenuItem mniAltitude;
    private javax.swing.JCheckBoxMenuItem mniArc;
    private javax.swing.JCheckBoxMenuItem mniCAlt;
    private javax.swing.JCheckBoxMenuItem mniCallsign;
    private javax.swing.JCheckBoxMenuItem mniCircle;
    private javax.swing.JCheckBoxMenuItem mniCoAlt;
    private javax.swing.JCheckBoxMenuItem mniController;
    private javax.swing.JMenuItem mniDraw;
    private javax.swing.JMenuItem mniFilter;
    private javax.swing.JCheckBoxMenuItem mniHDGNote;
    private javax.swing.JCheckBoxMenuItem mniHeading;
    private javax.swing.JCheckBoxMenuItem mniInfo;
    private javax.swing.JCheckBoxMenuItem mniLocalSector;
    private javax.swing.JCheckBoxMenuItem mniName;
    private javax.swing.JCheckBoxMenuItem mniNdb;
    private javax.swing.JMenuItem mniRemoveAllDummy;
    private javax.swing.JCheckBoxMenuItem mniRoute;
    private javax.swing.JCheckBoxMenuItem mniSSR;
    private javax.swing.JMenuItem mniSecondDisplay;
    private javax.swing.JCheckBoxMenuItem mniSectors;
    private javax.swing.JMenuItem mniSetup;
    private javax.swing.JCheckBoxMenuItem mniSpeed;
    private javax.swing.JCheckBoxMenuItem mniTMA;
    private javax.swing.JMenuItem mniTargetAssum;
    private javax.swing.JMenuItem mniTargetCancel;
    private javax.swing.JMenuItem mniTargetEdit;
    private javax.swing.JMenuItem mniTargetList;
    private javax.swing.JMenuItem mniTargetSaving;
    private javax.swing.JCheckBoxMenuItem mniTopographic;
    private javax.swing.JCheckBoxMenuItem mniTracking;
    private javax.swing.JMenuItem mniTrafficList;
    private javax.swing.JCheckBoxMenuItem mniVorDme;
    private javax.swing.JCheckBoxMenuItem mniWarningAMA;
    private javax.swing.JCheckBoxMenuItem mniWarningCLDC;
    private javax.swing.JCheckBoxMenuItem mniWarningDraw;
    private javax.swing.JCheckBoxMenuItem mniWarningMSA;
    private javax.swing.JCheckBoxMenuItem mniWarningMTCA;
    private javax.swing.JCheckBoxMenuItem mniWarningOutSrc;
    private javax.swing.JCheckBoxMenuItem mniWarningSTCA;
    private javax.swing.JCheckBoxMenuItem mniWarningVVPR;
    private javax.swing.JCheckBoxMenuItem mniWayPoint;
    private javax.swing.JRadioButtonMenuItem mniZero;
    private javax.swing.JMenu mnuARR;
    private javax.swing.JMenu mnuAirport;
    private javax.swing.JMenu mnuFixNav;
    private javax.swing.JMenu mnuFixPoint;
    private javax.swing.JPopupMenu mnuHistoryLength;
    private javax.swing.JMenu mnuLabel;
    private javax.swing.JPopupMenu mnuMap;
    private javax.swing.JMenu mnuPROC;
    private javax.swing.JMenu mnuRUNWAY;
    private javax.swing.JPopupMenu mnuSelectedTarget;
    private javax.swing.JPopupMenu mnuTarget;
    private javax.swing.JMenu mnuTargetTransfer;
    private javax.swing.JMenu mnuVVD;
    private javax.swing.JMenu mnuVVP;
    private javax.swing.JMenu mnuVVR;
    private javax.swing.JMenu mnuVVX;
    private javax.swing.JPopupMenu mnuWarning;
    private javax.swing.JMenuItem mnuWarningSetup;
    private javax.swing.JPopupMenu mnuZoomLevel;
    private javax.swing.JScrollPane pnlContent;
    private javax.swing.JSlider scrSPV;
    private javax.swing.JSlider sdrRNG;
    private javax.swing.JToolBar tbrMain;
    // End of variables declaration//GEN-END:variables

    @Override
    public void run() {
        this.painter.getContext().flip();
    }
}
