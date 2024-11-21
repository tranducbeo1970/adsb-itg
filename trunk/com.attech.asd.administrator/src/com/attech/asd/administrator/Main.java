/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.administrator;

import com.attech.asd.administrator.amhs.ClientEventHandler;
import com.attech.asd.administrator.common.ConnectionEventType;
import com.attech.asd.administrator.amhs.SocketClientThread;
import com.attech.asd.amhs.service.monitor.Command;
import com.attech.asd.amhs.service.monitor.CommandType;
import com.attech.asd.administrator.common.ClientTableModel;
import com.attech.asd.administrator.common.ExceptionHandler;
import com.attech.asd.administrator.common.MessageAccountTableModel;
import com.attech.asd.administrator.common.ReceiverTableModel;
import com.attech.asd.administrator.common.Utils;
import com.attech.asd.administrator.views.AddClient;
import com.attech.asd.administrator.views.AddMsgAccount;
import com.attech.asd.administrator.views.AddReceiver;
import com.attech.asd.administrator.views.AircraftManagement;
import com.attech.asd.administrator.views.AirportManagement;
import com.attech.asd.administrator.views.ApplicationLogs;
import com.attech.asd.administrator.views.ChangePassword;
import com.attech.asd.administrator.views.ClientDialog;
import com.attech.asd.administrator.views.ConfigurationDialog;
import com.attech.asd.administrator.views.FileManager;
import com.attech.asd.administrator.views.FlightPlanDialog;
import com.attech.asd.administrator.views.FusedFileManager;
import com.attech.asd.administrator.views.Help;
import com.attech.asd.administrator.views.MsgAccountManager;
import com.attech.asd.administrator.views.PointsDialog;
import com.attech.asd.administrator.views.ReceiverDialog;
import com.attech.asd.administrator.views.RouteDialog;
import com.attech.asd.administrator.views.MsgAccountDialog;
import com.attech.asd.administrator.views.SensorLogManager;
import com.attech.asd.amhs.service.monitor.Status;
import com.attech.asd.amhs.service.monitor.ThreadStatus;
import com.attech.asd.daemon.ServerInfo;
import com.attech.asd.daemon.client.ClientInformationItem;
import com.attech.asd.daemon.receiver.InformationItem;
import com.attech.asd.database.Base;
import com.attech.asd.database.common.Disk;
import com.attech.asd.database.common.DBException;
import com.attech.asd.database.dao.MessageAccountDao;
import com.attech.asd.database.entities.MessageAccount;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

/**
 *
 * @author Le Quang Tung
 */
public class Main extends javax.swing.JFrame {

    private final static Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    private final static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(Main.class);
    private static final TimeZone utc = TimeZone.getTimeZone("UTC");
    
    private final SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
    private final Timer timerClock;
    
    private final MessageAccountTableModel accountModel;
    private final ReceiverTableModel modelReceiver;
    private final ClientTableModel modelClient;
    private final IEventUpdater eventUpdater;
    private final EventUpdaterThread updaterThread;

    private MessageAccountDao messageAccountDao = new MessageAccountDao();
    
    private SocketClientThread clientThread;
    private final ClientEventHandler clientEventHandler;
    
    private String outputBackUpDbFolder;
    
    private JFreeChart chart;
    private TimeSeriesCollection dataset;
    private TimeSeries cat21Series;
    private TimeSeries cat21FusedSeries;
    
    private int lastCountFused = 0;
    private int lastCount = 0;
    /**
     * Creates new form Main
     */
    public Main() throws DBException {
        initComponents();
        this.setIconImage(new ImageIcon("images/logo.png").getImage());
        this.setTitle(String.format("%s v%s", AppContext.getInstance().assemblyInfo.getProduct(), AppContext.getInstance().assemblyInfo.getVersion()));
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        this.setMaximumSize(dim);
        
        this.lblServerMessage.setForeground(AppContext.getNormalColor());

        try {
            AppContext.connectCommandSocket();

            this.lblCommandSocket.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/bullet_blue.png")));
            this.lblCommandSocket.setForeground(AppContext.getNormalColor());
            this.lblCommandSocket.setText(String.format("Running"));
            AppContext.getInstance().connectToServer = true;
        } catch (IOException ex) {
            logger.error("Could not connect COMMAND SOCKET with server:" + ex.getMessage());
            this.lblCommandSocket.setText(String.format("Disconnected"));
            this.lblCommandSocket.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/bullet_white.png")));
            this.lblCommandSocket.setForeground(AppContext.getErrorColor());
            AppContext.getInstance().connectToServer = false;
        }
        
        try {
            AppContext.connectUpdaterSocket();
            
            this.lblUpdaterSocket.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/bullet_blue.png")));
            this.lblUpdaterSocket.setForeground(AppContext.getNormalColor());
            this.lblUpdaterSocket.setText(String.format("Running"));
        } catch (IOException ex) {
            this.lblUpdaterSocket.setText(String.format("Disconnected"));
            this.lblUpdaterSocket.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/bullet_white.png")));
            this.lblUpdaterSocket.setForeground(AppContext.getErrorColor());
            logger.error("Could not connect UPDATER SOCKET with server:" + ex.getMessage());
        }
        
        this.scPanel.add(Box.createVerticalGlue());
        this.scPanel.add(Box.createHorizontalGlue());
        
        // set default Sound on
        AppContext.getInstance().EnableSound = chkWarningSound.isSelected();
        // show server info
        this.lblServerAddress.setText(AppContext.getServerIp());
        this.lblUpdaterPort.setText(String.format("%d", AppContext.getServerPort() + 1));
        this.lblCommandPort.setText(String.format("%d", AppContext.getServerPort()));
        this.lblAmhsPort.setText(String.format("%d", AppContext.getAmhsPort()));
        // init table to monitor
        this.modelReceiver = new ReceiverTableModel(tblReceiver);
        this.modelClient = new ClientTableModel(tblClient);
        this.accountModel = new MessageAccountTableModel(tblAccount);
        this.intializeReceiverTable();
        this.intializeClientTable();
        this.initialAccountTable();
        // create clock
        hourFormat.setTimeZone(utc);
        this.timerClock = new Timer();
        this.timerClock.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                lblClock.setText(hourFormat.format(new Date()));
            }
        }, 0, 1000);
        
        initGraph();
        chart.getPlot().setBackgroundPaint(Color.decode("#02273b"));
        this.eventUpdater = this::updateInfo;
        
        // AMHS updater
        this.clientEventHandler = this::onStatusUpdate;
        this.btnConnectAmhs.setEnabled(true);
        this.btnDisconnectAmhs.setEnabled(false);
        
        updaterThread = new EventUpdaterThread();
        updaterThread.setEventUpdater(eventUpdater);
        updaterThread.start();
        
        // AMHS Disable ----
        //this.btnConnectAmhs.setVisible(false);     
        //this.btnDisconnectAmhs.setVisible(false);
        //this.lblAmhsPort.setVisible(false);
        //this.jLabel8.setVisible(false);
        
        //-- Disable BtnMonitor
        this.btnMonitor.setVisible(false);
        
        //-- hidden btn
        //this.btnNewMessageAcc.setVisible(false);
        //this.btnMessages.setVisible(false);
        //this.mnuMsgAccount.setVisible(false);
        //this.mnuMessageAccount.setVisible(false);
        //this.jSeparator3.setVisible(false);
    }
    
    private void updateInfo(ConnectionEventType eventType, ServerInfo server){
        switch (eventType) {
            case Connected:
                lblFusedFile.setText(server.getFusionFileNameValue());
                lblTarget.setText(String.format("%s", server.getFusionTargetCountValue()));
                //System.out.println(server.getFusedMessageCountValue());
                if (cat21Series.getItemCount() > 50) {
                    cat21Series.delete(0, 1);
                }
                
                try {
                    cat21Series.add(new Second(), (cat21Series.getItemCount() == 0) ? 0 : server.getMessageCountValue() - lastCount);
                    lastCount = server.getMessageCountValue();

                    if (cat21FusedSeries.getItemCount() > 50) {
                        cat21FusedSeries.delete(0, 1);
                    }
                    cat21FusedSeries.add(new Second(), (cat21FusedSeries.getItemCount() == 0) ? 0 : server.getFusedMessageCountValue() - lastCountFused);
                    lastCountFused = server.getFusedMessageCountValue();
                    chart.setTitle(String.format("ADS-B Messages: %s", server.getFusedMessageCountValue()));
                } catch (Exception sex){
                    logger.error(sex);
                }
                
                Disk disk = server.getStorageInfoValue();
                //System.out.println(disk);
                pStorage.setToolTipText(String.format("Disk space in data directory: %s GB/%s GB avaiable", Utils.fConvertByteToGb(disk.getFree()), Utils.fConvertByteToGb(disk.getTotal())));
                try {
                    pStorage.setValue((int) ((disk.getTotal() - disk.getFree()) * 100 / disk.getTotal()));
                } catch (Exception ex) {
                    pStorage.setValue(0);
                }
                if (Utils.convertByteToGb(disk.getFree()) <= AppContext.getStorageErrorThresshold()) {
                    pStorage.setStringPainted(true);
                    pStorage.setForeground(AppContext.getErrorColor());
                    
                    if (AppContext.getInstance().EnableSound) 
                        new SoundAlert().PlayAlert();
                    
                } else if (Utils.convertByteToGb(disk.getFree()) <= AppContext.getStorageThresshold()) {
                    pStorage.setStringPainted(true);
                    pStorage.setForeground(AppContext.getWarnColor());
                }
                else {
                    pStorage.setStringPainted(true);
                    pStorage.setForeground(AppContext.getNormalColor());
                }
                pStorage.setOpaque(true);

                if (server.getReloadClientIdValue() > 0) {
                    modelClient.setDefault(server.getReloadClientIdValue());
                }

                if (server.getReloadReceiverIdValue() > 0) {
                    modelReceiver.setDefault(server.getReloadReceiverIdValue());
                }

                if (server.getReloadMsgAccIdValue() > 0) {
                    accountModel.setDefault(server.getReloadMsgAccIdValue());
                }

                Map<Integer, ClientInformationItem> mapC = (Map<Integer, ClientInformationItem>) server.getActiveClients().getData();
                mapC.values().forEach((item) -> {
                    modelClient.update(item);
                    //System.out.println(item);
                });

                Map<Integer, InformationItem> mapR = (Map<Integer, InformationItem>) server.getActiveReceivers().getData();
                mapR.values().forEach((item) -> {
                    modelReceiver.update(item);
                    //System.out.println(item);
                });
                lblServerMessage.setForeground(AppContext.getNormalColor());
                lblServerMessage.setText(server.getMessage());
                
                lblUpdaterSocket.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/bullet_blue.png")));
                lblUpdaterSocket.setForeground(AppContext.getNormalColor());
                lblUpdaterSocket.setText(String.format("Running"));

                lblCommandSocket.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/bullet_blue.png")));
                lblCommandSocket.setForeground(AppContext.getNormalColor());
                lblCommandSocket.setText(String.format("Running"));
                
                // Check to reload Client Table
                if (AppContext.getInstance().reloadListClient) {
                    AppContext.getInstance().reloadClient();
                    modelClient.setNumRows(0);
                    AppContext.getInstance().getClients().forEach(c -> {
                        modelClient.add(c);
                    });
                    modelClient.setColorDefault();
                    AppContext.getInstance().getWarnBroadcaster().clear();
                    AppContext.getInstance().reloadListClient = false;
                }

                // Check to reload Receiver Table
                if (AppContext.getInstance().reloadListReceiver) {
                    AppContext.getInstance().reloadSensors();
                    modelReceiver.setNumRows(0);
                    AppContext.getInstance().getSensors().forEach(s -> {
                        modelReceiver.add(s);
                    });
                    modelReceiver.setColorDefault();
                    AppContext.getInstance().getWarnReceiver().clear();
                    AppContext.getInstance().reloadListReceiver = false;
                }
                
                // Check to reload MsgAcc Table
                if (AppContext.getInstance().reloadListMessage){
                    try {
                        AppContext.getInstance().reloadListMessage = false;
                        initialAccountTable();
                        // reset color
                        for (int i = 0; i < accountModel.getRowCount(); i++) {
                            accountModel.setCellColor(i, 3, null);
                        }
                    } catch (DBException ex) {
                        ExceptionHandler.handle(ex);
                    }
                }
                
                break;
            case Disconnected:
                lblUpdaterSocket.setText(String.format("Disconnected"));
                lblUpdaterSocket.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/bullet_white.png")));
                lblUpdaterSocket.setForeground(AppContext.getErrorColor());

                lblCommandSocket.setText(String.format("Disconnected"));
                lblCommandSocket.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/bullet_white.png")));
                lblCommandSocket.setForeground(AppContext.getErrorColor());
                
                lblServerMessage.setForeground(AppContext.getErrorColor());
                lblServerMessage.setText("Reconnect... ");
                
                reloadAllTable();
                break;
        }
    }
    
    private void initGraph(){
        this.dataset = new TimeSeriesCollection();
        this.cat21Series = new TimeSeries("All");
        this.cat21FusedSeries = new TimeSeries("Fused");
        this.dataset.addSeries(cat21Series);
        this.dataset.addSeries(cat21FusedSeries);
        this.chart = ChartFactory.createTimeSeriesChart("ADS-B Messages",
                "Time",
                "Value",
                dataset,
                true, //legend
                false, //tooltip
                false); //url
        chart.setBackgroundPaint(null);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPopupMenu(null);
        chartPanel.setMouseZoomable(false);
        chartPanel.setOpaque(false); //this line necessary for transparency of background
        chartPanel.setBackground(new Color(0, 0, 0, 0)); //this line necessary for transparency of background
        chartPanel.setPreferredSize(new Dimension(400, 180));
        pnChart.add(chartPanel);
    }

    private void intializeReceiverTable() {
        AppContext.getInstance().getSensors().forEach(s -> {
            modelReceiver.add(s);
        });
    }

    private void intializeClientTable() {
        AppContext.getInstance().getClients().forEach(c -> {
            modelClient.add(c);
        });
    }

    private void initialAccountTable() throws DBException {
        // Initialize
        accountModel.setNumRows(0);
        List<MessageAccount> messageAccountList = messageAccountDao.getAll();
        for (MessageAccount mesageAccount : messageAccountList) {
            accountModel.add(mesageAccount);
        }
    }

    private void onStatusUpdate(ConnectionEventType type, List<ThreadStatus> param) {
        switch (type) {
            case Connected:
                this.btnConnectAmhs.setEnabled(!clientThread.isRunning());
                this.btnDisconnectAmhs.setEnabled(clientThread.isRunning());
                break;
            case Disconnected:
                this.btnConnectAmhs.setEnabled(!clientThread.isRunning());
                this.btnDisconnectAmhs.setEnabled(clientThread.isRunning());
                break;
            case Update:
                if (param == null) {
                    break;
                }
                for (ThreadStatus status : param) {
                    logger.info(String.format("Update id:%s status:%s count:%s", status.getId(), status.getStatus(), status.getMessageCount()));
//                    this.accountModel.updateAccoutMessageTable(status);
                }
                break;
        }
    }

    public void startAmhsAccount(int id) {
        try {
            Command command = new Command(CommandType.START, id);
            this.clientThread.sendCommand(command);
        } catch (IOException ex) {
            logger.error(ex);
        }
    }

    public void stopAmhsAccount(int id) {
        try {
            Command command = new Command(CommandType.STOP, id);
            this.clientThread.sendCommand(command);
        } catch (IOException ex) {
            logger.error(ex);
        }
    }
    
    private void reloadAllTable() {
        try {
        // REFRESH TABLE
        AppContext.getInstance().reloadSensors();
        modelReceiver.setNumRows(0);
        AppContext.getInstance().getSensors().forEach(s -> {
            modelReceiver.add(s);
        });
        modelReceiver.setColorDefault();
        AppContext.getInstance().getWarnReceiver().clear();
        AppContext.getInstance().reloadListReceiver = false;

        AppContext.getInstance().reloadClient();
        modelClient.setNumRows(0);
        AppContext.getInstance().getClients().forEach(c -> {
            modelClient.add(c);
        });
        modelClient.setColorDefault();
        AppContext.getInstance().getWarnBroadcaster().clear();
        AppContext.getInstance().reloadListClient = false;
      
        } catch (Exception ex) {
            
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        btnRefreshTable = new javax.swing.JButton();
        btnNewSensor = new javax.swing.JButton();
        btnNewClient = new javax.swing.JButton();
        btnNewMessageAcc = new javax.swing.JButton();
        btnFileManager = new javax.swing.JButton();
        btnFusedFileManager = new javax.swing.JButton();
        btnMessages = new javax.swing.JButton();
        btnMonitor = new javax.swing.JButton();
        btnSetting = new javax.swing.JButton();
        pnLeft = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblServerAddress = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblUpdaterPort = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lblCommandPort = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lblAmhsPort = new javax.swing.JLabel();
        btnConnectAmhs = new javax.swing.JButton();
        btnDisconnectAmhs = new javax.swing.JButton();
        lblUpdaterSocket = new javax.swing.JLabel();
        lblCommandSocket = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblAccount = new javax.swing.JTable();
        pnChart = new javax.swing.JPanel();
        scPanel = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblReceiver = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblClient = new javax.swing.JTable();
        pnBottom = new javax.swing.JPanel();
        lblTarget = new javax.swing.JLabel();
        lblFusedFile = new javax.swing.JLabel();
        lblClock = new javax.swing.JLabel();
        pStorage = new javax.swing.JProgressBar();
        lblServerMessage = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        mnuFile = new javax.swing.JMenu();
        mnuNewChannel = new javax.swing.JMenuItem();
        mnuNewBroadcaster = new javax.swing.JMenuItem();
        mnuMsgAccount = new javax.swing.JMenuItem();
        mnuLock = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        mnuExit = new javax.swing.JMenuItem();
        mnuEdit = new javax.swing.JMenu();
        mnuChangePassword = new javax.swing.JMenuItem();
        mnuBackupDb = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        chkWarningSound = new javax.swing.JCheckBoxMenuItem();
        jMenu2 = new javax.swing.JMenu();
        mnuSensorLog = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        mnuFileRecord = new javax.swing.JMenuItem();
        mnuFusedFileRecord = new javax.swing.JMenuItem();
        mnuTools = new javax.swing.JMenu();
        mnuCraft = new javax.swing.JMenuItem();
        mnuAirport = new javax.swing.JMenuItem();
        mnuPoint = new javax.swing.JMenuItem();
        mnuRoute = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        mnuMessageAccount = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        mnuLogs = new javax.swing.JMenuItem();
        mnuAbout = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("ATTECH -ASD Administrator Technical 1.0.0");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        btnRefreshTable.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/arrow_refresh_small.png"))); // NOI18N
        btnRefreshTable.setToolTipText("Refresh ");
        btnRefreshTable.setFocusable(false);
        btnRefreshTable.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnRefreshTable.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnRefreshTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshTableActionPerformed(evt);
            }
        });
        jToolBar1.add(btnRefreshTable);

        btnNewSensor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/transmit_add.png"))); // NOI18N
        btnNewSensor.setToolTipText("Add New Receiver Channel");
        btnNewSensor.setFocusable(false);
        btnNewSensor.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnNewSensor.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnNewSensor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewSensorActionPerformed(evt);
            }
        });
        jToolBar1.add(btnNewSensor);

        btnNewClient.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/monitor_add.png"))); // NOI18N
        btnNewClient.setToolTipText("Add New Client");
        btnNewClient.setFocusable(false);
        btnNewClient.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnNewClient.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnNewClient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewClientActionPerformed(evt);
            }
        });
        jToolBar1.add(btnNewClient);

        btnNewMessageAcc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/email_add.png"))); // NOI18N
        btnNewMessageAcc.setToolTipText("Add New Account ");
        btnNewMessageAcc.setFocusable(false);
        btnNewMessageAcc.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnNewMessageAcc.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnNewMessageAcc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewMessageAccActionPerformed(evt);
            }
        });
        jToolBar1.add(btnNewMessageAcc);

        btnFileManager.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/data_raw.png"))); // NOI18N
        btnFileManager.setToolTipText("File Record Manager");
        btnFileManager.setFocusable(false);
        btnFileManager.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnFileManager.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnFileManager.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFileManagerActionPerformed(evt);
            }
        });
        jToolBar1.add(btnFileManager);

        btnFusedFileManager.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/data_sort.png"))); // NOI18N
        btnFusedFileManager.setToolTipText("Fused File Manager");
        btnFusedFileManager.setFocusable(false);
        btnFusedFileManager.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnFusedFileManager.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnFusedFileManager.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFusedFileManagerActionPerformed(evt);
            }
        });
        jToolBar1.add(btnFusedFileManager);

        btnMessages.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/emails.png"))); // NOI18N
        btnMessages.setToolTipText("FPL Messages");
        btnMessages.setFocusable(false);
        btnMessages.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnMessages.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnMessages.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMessagesActionPerformed(evt);
            }
        });
        jToolBar1.add(btnMessages);

        btnMonitor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/radiolocator.png"))); // NOI18N
        btnMonitor.setToolTipText("Preview");
        btnMonitor.setFocusable(false);
        btnMonitor.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnMonitor.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnMonitor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMonitorActionPerformed(evt);
            }
        });
        jToolBar1.add(btnMonitor);

        btnSetting.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/setting_tools.png"))); // NOI18N
        btnSetting.setToolTipText("Configuration");
        btnSetting.setFocusable(false);
        btnSetting.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSetting.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSetting.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSettingActionPerformed(evt);
            }
        });
        jToolBar1.add(btnSetting);

        pnLeft.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel1.setText("Server Address:");

        lblServerAddress.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblServerAddress.setText("127.0.0.1");

        jLabel3.setText("Updater Socket Port:");

        lblUpdaterPort.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblUpdaterPort.setText("9000");

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/server-logo.png"))); // NOI18N

        jLabel6.setText("Command Socket Port:");

        lblCommandPort.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblCommandPort.setText("9001");

        jLabel8.setText("AMHS Socket Port:");

        lblAmhsPort.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblAmhsPort.setText("7749");

        btnConnectAmhs.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/connect.png"))); // NOI18N
        btnConnectAmhs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConnectAmhsActionPerformed(evt);
            }
        });

        btnDisconnectAmhs.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/disconnecte.png"))); // NOI18N
        btnDisconnectAmhs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDisconnectAmhsActionPerformed(evt);
            }
        });

        lblUpdaterSocket.setForeground(new java.awt.Color(51, 153, 0));
        lblUpdaterSocket.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/bullet_blue.png"))); // NOI18N
        lblUpdaterSocket.setToolTipText("Connect Status");
        lblUpdaterSocket.setPreferredSize(new java.awt.Dimension(200, 25));

        lblCommandSocket.setForeground(new java.awt.Color(51, 153, 0));
        lblCommandSocket.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/bullet_blue.png"))); // NOI18N
        lblCommandSocket.setToolTipText("Connect Status");
        lblCommandSocket.setPreferredSize(new java.awt.Dimension(200, 25));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblServerAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lblAmhsPort, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnConnectAmhs)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDisconnectAmhs))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCommandPort, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblUpdaterPort, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblUpdaterSocket, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE)
                            .addComponent(lblCommandSocket, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblServerAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblUpdaterSocket, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblUpdaterPort, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCommandSocket, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblCommandPort, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnConnectAmhs, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblAmhsPort, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnDisconnectAmhs, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(24, 24, 24)))
                .addContainerGap())
        );

        tblAccount.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblAccount.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblAccountMousePressed(evt);
            }
        });
        jScrollPane4.setViewportView(tblAccount);

        javax.swing.GroupLayout pnLeftLayout = new javax.swing.GroupLayout(pnLeft);
        pnLeft.setLayout(pnLeftLayout);
        pnLeftLayout.setHorizontalGroup(
            pnLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 416, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnChart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnLeftLayout.setVerticalGroup(
            pnLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnLeftLayout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnChart, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        scPanel.setBorder(null);

        jSplitPane1.setDividerLocation(500);
        jSplitPane1.setResizeWeight(1.0);

        tblReceiver.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "id", "#", "Type", "SIC", "Name", "Port", "Active", "Receiced", "Description"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblReceiver.setOpaque(false);
        tblReceiver.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblReceiverMousePressed(evt);
            }
        });
        jScrollPane3.setViewportView(tblReceiver);
        if (tblReceiver.getColumnModel().getColumnCount() > 0) {
            tblReceiver.getColumnModel().getColumn(0).setMinWidth(0);
            tblReceiver.getColumnModel().getColumn(0).setPreferredWidth(0);
            tblReceiver.getColumnModel().getColumn(0).setMaxWidth(0);
            tblReceiver.getColumnModel().getColumn(1).setMinWidth(30);
            tblReceiver.getColumnModel().getColumn(1).setPreferredWidth(30);
            tblReceiver.getColumnModel().getColumn(1).setMaxWidth(30);
            tblReceiver.getColumnModel().getColumn(2).setMinWidth(50);
            tblReceiver.getColumnModel().getColumn(2).setPreferredWidth(50);
            tblReceiver.getColumnModel().getColumn(2).setMaxWidth(50);
            tblReceiver.getColumnModel().getColumn(3).setMinWidth(50);
            tblReceiver.getColumnModel().getColumn(3).setPreferredWidth(50);
            tblReceiver.getColumnModel().getColumn(3).setMaxWidth(50);
            tblReceiver.getColumnModel().getColumn(5).setMinWidth(60);
            tblReceiver.getColumnModel().getColumn(5).setPreferredWidth(60);
            tblReceiver.getColumnModel().getColumn(5).setMaxWidth(60);
            tblReceiver.getColumnModel().getColumn(6).setMinWidth(60);
            tblReceiver.getColumnModel().getColumn(6).setPreferredWidth(60);
            tblReceiver.getColumnModel().getColumn(6).setMaxWidth(60);
            tblReceiver.getColumnModel().getColumn(7).setMinWidth(60);
            tblReceiver.getColumnModel().getColumn(7).setPreferredWidth(60);
            tblReceiver.getColumnModel().getColumn(7).setMaxWidth(60);
        }

        jSplitPane1.setLeftComponent(jScrollPane3);

        tblClient.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "#", "Name", "Port", "Active", "Package", "Description"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblClient.setOpaque(false);
        tblClient.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblClientMousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(tblClient);

        jSplitPane1.setRightComponent(jScrollPane2);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1008, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 631, Short.MAX_VALUE)
                .addContainerGap())
        );

        scPanel.setViewportView(jPanel1);

        lblTarget.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/airplane-front-view.png"))); // NOI18N
        lblTarget.setToolTipText("Current Aircraft (ADS-B)");
        lblTarget.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lblTarget.setPreferredSize(new java.awt.Dimension(200, 25));

        lblFusedFile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/save.png"))); // NOI18N
        lblFusedFile.setToolTipText("Current Fused File (ADS-B)");
        lblFusedFile.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lblFusedFile.setPreferredSize(new java.awt.Dimension(200, 25));

        lblClock.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblClock.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblClock.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/clock.png"))); // NOI18N
        lblClock.setToolTipText("Current Datetime (dd/MM/yyyy HH:mm:ss)");
        lblClock.setMaximumSize(new java.awt.Dimension(200, 28));
        lblClock.setMinimumSize(new java.awt.Dimension(120, 28));
        lblClock.setPreferredSize(new java.awt.Dimension(120, 28));

        pStorage.setToolTipText("Disk space in data directory");

        lblServerMessage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/info.png"))); // NOI18N
        lblServerMessage.setToolTipText("Lastest Message From Server");
        lblServerMessage.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lblServerMessage.setPreferredSize(new java.awt.Dimension(200, 25));

        javax.swing.GroupLayout pnBottomLayout = new javax.swing.GroupLayout(pnBottom);
        pnBottom.setLayout(pnBottomLayout);
        pnBottomLayout.setHorizontalGroup(
            pnBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnBottomLayout.createSequentialGroup()
                .addComponent(lblTarget, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblFusedFile, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pStorage, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblServerMessage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblClock, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pnBottomLayout.setVerticalGroup(
            pnBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnBottomLayout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(pnBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblClock, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(pStorage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblTarget, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblFusedFile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblServerMessage, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        mnuFile.setText("File");

        mnuNewChannel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/add_16.png"))); // NOI18N
        mnuNewChannel.setText("New Receiver");
        mnuNewChannel.setActionCommand("");
        mnuNewChannel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuNewChannelActionPerformed(evt);
            }
        });
        mnuFile.add(mnuNewChannel);

        mnuNewBroadcaster.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/add_16.png"))); // NOI18N
        mnuNewBroadcaster.setText("New Client");
        mnuNewBroadcaster.setActionCommand("");
        mnuNewBroadcaster.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuNewBroadcasterActionPerformed(evt);
            }
        });
        mnuFile.add(mnuNewBroadcaster);

        mnuMsgAccount.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/add_16.png"))); // NOI18N
        mnuMsgAccount.setText("New Message Account");
        mnuMsgAccount.setActionCommand("");
        mnuMsgAccount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuMsgAccountActionPerformed(evt);
            }
        });
        mnuFile.add(mnuMsgAccount);

        mnuLock.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/lock_16.png"))); // NOI18N
        mnuLock.setText("Lock Application");
        mnuLock.setActionCommand("");
        mnuLock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuLockActionPerformed(evt);
            }
        });
        mnuFile.add(mnuLock);
        mnuFile.add(jSeparator1);

        mnuExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/out_16.png"))); // NOI18N
        mnuExit.setText("Exit");
        mnuExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuExitActionPerformed(evt);
            }
        });
        mnuFile.add(mnuExit);

        jMenuBar1.add(mnuFile);

        mnuEdit.setText("Edit");

        mnuChangePassword.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/lock_16.png"))); // NOI18N
        mnuChangePassword.setText("Change Password");
        mnuChangePassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuChangePasswordActionPerformed(evt);
            }
        });
        mnuEdit.add(mnuChangePassword);

        mnuBackupDb.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/database_refresh_16.png"))); // NOI18N
        mnuBackupDb.setText("BackUp Database");
        mnuBackupDb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuBackupDbActionPerformed(evt);
            }
        });
        mnuEdit.add(mnuBackupDb);
        mnuEdit.add(jSeparator2);

        chkWarningSound.setSelected(true);
        chkWarningSound.setText("Warning Sound");
        chkWarningSound.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkWarningSoundActionPerformed(evt);
            }
        });
        mnuEdit.add(chkWarningSound);

        jMenuBar1.add(mnuEdit);

        jMenu2.setText("View");

        mnuSensorLog.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/sent.png"))); // NOI18N
        mnuSensorLog.setText("Sensor Log");
        mnuSensorLog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuSensorLogActionPerformed(evt);
            }
        });
        jMenu2.add(mnuSensorLog);
        jMenu2.add(jSeparator4);

        mnuFileRecord.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/data_raw_16.png"))); // NOI18N
        mnuFileRecord.setText("File Record");
        mnuFileRecord.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuFileRecordActionPerformed(evt);
            }
        });
        jMenu2.add(mnuFileRecord);

        mnuFusedFileRecord.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/data_sort_16.png"))); // NOI18N
        mnuFusedFileRecord.setText("Fused File Record");
        mnuFusedFileRecord.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuFusedFileRecordActionPerformed(evt);
            }
        });
        jMenu2.add(mnuFusedFileRecord);

        jMenuBar1.add(jMenu2);

        mnuTools.setText("Category");

        mnuCraft.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/airplane-front-view.png"))); // NOI18N
        mnuCraft.setText("Aircraft & Operator");
        mnuCraft.setActionCommand("craft-category");
        mnuCraft.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuCraftActionPerformed(evt);
            }
        });
        mnuTools.add(mnuCraft);

        mnuAirport.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/company.png"))); // NOI18N
        mnuAirport.setText("Airports");
        mnuAirport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuAirportActionPerformed(evt);
            }
        });
        mnuTools.add(mnuAirport);

        mnuPoint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/cluster.png"))); // NOI18N
        mnuPoint.setText("Points");
        mnuPoint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuPointActionPerformed(evt);
            }
        });
        mnuTools.add(mnuPoint);

        mnuRoute.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/network-pc.png"))); // NOI18N
        mnuRoute.setText("Routes");
        mnuRoute.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuRouteActionPerformed(evt);
            }
        });
        mnuTools.add(mnuRoute);
        mnuTools.add(jSeparator3);

        mnuMessageAccount.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/three_tags.png"))); // NOI18N
        mnuMessageAccount.setText("Message Account");
        mnuMessageAccount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuMessageAccountActionPerformed(evt);
            }
        });
        mnuTools.add(mnuMessageAccount);

        jMenuBar1.add(mnuTools);

        jMenu1.setText("Help");

        mnuLogs.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/file_extension_log_16.png"))); // NOI18N
        mnuLogs.setText("Logs");
        mnuLogs.setActionCommand("");
        mnuLogs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuLogsActionPerformed(evt);
            }
        });
        jMenu1.add(mnuLogs);

        mnuAbout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/help_16.png"))); // NOI18N
        mnuAbout.setText("About");
        mnuAbout.setActionCommand("");
        mnuAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuAboutActionPerformed(evt);
            }
        });
        jMenu1.add(mnuAbout);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnLeft, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnBottom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(scPanel))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnLeft, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(scPanel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnBottom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void mnuExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuExitActionPerformed
        try {
            int confirmed = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to exit the program?",
                    "Confirmation",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (confirmed == JOptionPane.YES_OPTION) {
                System.exit(0);
            } else {
            }
        } catch (HeadlessException ex) {
            logger.error(ex);
        }
    }//GEN-LAST:event_mnuExitActionPerformed

    private void chkWarningSoundActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkWarningSoundActionPerformed
        AppContext.getInstance().EnableSound = chkWarningSound.isSelected();
    }//GEN-LAST:event_chkWarningSoundActionPerformed

    private void mnuAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuAboutActionPerformed
        Help help = new Help(this, true);
        help.setVisible(true);
    }//GEN-LAST:event_mnuAboutActionPerformed

    private void btnNewClientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewClientActionPerformed
        if (!AppContext.getInstance().connectToServer){
            JOptionPane.showMessageDialog(this, "This feature temporarily block.", "Notify", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        Authentication auth = new Authentication(this, true);
        auth.setDesDialog(new AddClient(this, true, 0));
        auth.setVisible(true);
//        AddClient dialog = new AddClient(this, true, 0);
//        dialog.setVisible(true);
    }//GEN-LAST:event_btnNewClientActionPerformed

    private void btnRefreshTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshTableActionPerformed
        synchronized (this) {
            // REFRESH TABLE
            AppContext.getInstance().reloadSensors();
            modelReceiver.setNumRows(0);
            AppContext.getInstance().getSensors().forEach(s -> {modelReceiver.add(s);});
            modelReceiver.setColorDefault();
            AppContext.getInstance().getWarnReceiver().clear();
            //AppContext.getInstance().reloadListReceiver = false;

            AppContext.getInstance().reloadClient();
            modelClient.setNumRows(0);
            AppContext.getInstance().getClients().forEach(c -> {modelClient.add(c);});
            modelClient.setColorDefault();
            AppContext.getInstance().getWarnBroadcaster().clear();
            //AppContext.getInstance().reloadListClient = false;
            try {
                initialAccountTable();
                // reset color
                for (int i = 0; i < accountModel.getRowCount(); i++) {
                    accountModel.setCellColor(i, 3, null);
                }
            } catch (DBException ex) {
                ExceptionHandler.handle(ex);
            }
            
            try {
                Thread.sleep(AppContext.getRefreshTime());
            } catch (InterruptedException ex) {
                logger.info(ex);
            }
        }
    }//GEN-LAST:event_btnRefreshTableActionPerformed

    private void btnFileManagerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFileManagerActionPerformed
        try {
            Authentication auth = new Authentication(this, true);
            auth.setDesDialog(new FileManager(this, true));
            auth.setVisible(true);
        } catch (ParseException ex) {
            logger.error(ex.getMessage());
        }
    }//GEN-LAST:event_btnFileManagerActionPerformed

    private void btnNewSensorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewSensorActionPerformed
        if (!AppContext.getInstance().connectToServer){
            JOptionPane.showMessageDialog(this, "This feature temporarily block.", "Notify", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        Authentication auth = new Authentication(this, true);
        auth.setDesDialog(new AddReceiver(this, true, 0));
        auth.setVisible(true);   
//        AddReceiver dialog = new AddReceiver(this, true, 0);
//        dialog.setVisible(true);
    }//GEN-LAST:event_btnNewSensorActionPerformed

    private void btnSettingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSettingActionPerformed
        Authentication auth = new Authentication(this, true);
        auth.setDesDialog(new ConfigurationDialog(this, true));
        auth.setVisible(true);
    }//GEN-LAST:event_btnSettingActionPerformed

    private void mnuCraftActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuCraftActionPerformed
        Authentication auth = new Authentication(this, true);
        auth.setDesFrame(new AircraftManagement());
        auth.setVisible(true);
    }//GEN-LAST:event_mnuCraftActionPerformed

    private void mnuAirportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuAirportActionPerformed
        Authentication auth = new Authentication(this, true);
        auth.setDesFrame(new AirportManagement());
        auth.setVisible(true);
    }//GEN-LAST:event_mnuAirportActionPerformed

    private void mnuPointActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuPointActionPerformed
        Authentication auth = new Authentication(this, true);
        auth.setDesDialog(new PointsDialog(this, true));
        auth.setVisible(true);
    }//GEN-LAST:event_mnuPointActionPerformed

    private void tblClientMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblClientMousePressed
        try {
            if (evt.getButton() != 1) {
                return;
            }
            JTable table = (JTable) evt.getSource();
            Point p = evt.getPoint();
            int row = table.rowAtPoint(p);
            if (evt.getClickCount() != 2) {
                return;
            }
            int id = (int) table.getValueAt(row, 0);
            String status = (String) table.getValueAt(row, 4);
            Authentication auth = new Authentication(this, true);
            auth.setDesDialog(new ClientDialog(this, true, id, status.equalsIgnoreCase("Active")));
            auth.setVisible(true);   
//            ClientDialog dialog = new ClientDialog(this, true, id, status.equalsIgnoreCase("Active"));
//            dialog.setVisible(true);
        } catch (Exception ex) {
            ExceptionHandler.handle(ex);
        }
    }//GEN-LAST:event_tblClientMousePressed

    private void tblReceiverMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblReceiverMousePressed
        try {
            if (evt.getButton() != 1) {
                return;
            }
            JTable table = (JTable) evt.getSource();
            Point p = evt.getPoint();
            int row = table.rowAtPoint(p);
            if (evt.getClickCount() != 2) {
                return;
            }
            int no = (int) table.getValueAt(row, 1);
            int sic = (int) table.getValueAt(row, 3);
            String status = (String) table.getValueAt(row, 6);
            Authentication auth = new Authentication(this, true);
            auth.setDesDialog(new ReceiverDialog(this, true, status.equalsIgnoreCase("Active"), sic, no));
            auth.setVisible(true);              
//            ReceiverDialog dialog = new ReceiverDialog(this, true, status.equalsIgnoreCase("Active"), sic, no);
//            dialog.setVisible(true);
        } catch (Exception ex) {
            ExceptionHandler.handle(ex);
        }
    }//GEN-LAST:event_tblReceiverMousePressed

    private void btnMessagesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMessagesActionPerformed
        if (!AppContext.getInstance().connectToServer){
            JOptionPane.showMessageDialog(this, "This feature temporarily block.", "Notify", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        Authentication auth = new Authentication(this, true);
        auth.setDesDialog(new FlightPlanDialog(this, true));
        auth.setVisible(true);         
//        FlightPlanDialog dialog = new FlightPlanDialog(this, true);
//        dialog.setVisible(true);
    }//GEN-LAST:event_btnMessagesActionPerformed

    private void mnuRouteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuRouteActionPerformed
        Authentication auth = new Authentication(this, true);
        auth.setDesDialog(new RouteDialog(this, true));
        auth.setVisible(true);
    }//GEN-LAST:event_mnuRouteActionPerformed

    private void btnNewMessageAccActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewMessageAccActionPerformed
        if (!AppContext.getInstance().connectToServer){
            JOptionPane.showMessageDialog(this, "This feature temporarily block.", "Notify", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        Authentication auth = new Authentication(this, true);
        auth.setDesDialog(new AddMsgAccount(this, true, 0));
        auth.setVisible(true);   
//        AddMsgAccount dialog = new AddMsgAccount(this, true, 0);
//        dialog.setVisible(true);
    }//GEN-LAST:event_btnNewMessageAccActionPerformed

    private void mnuNewChannelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuNewChannelActionPerformed
        if (!AppContext.getInstance().connectToServer){
            JOptionPane.showMessageDialog(this, "This feature temporarily block.", "Notify", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        Authentication auth = new Authentication(this, true);
        auth.setDesDialog(new AddReceiver(this, true, 0));
        auth.setVisible(true);  
//        AddReceiver dialog = new AddReceiver(this, true, 0);
//        dialog.setVisible(true);
    }//GEN-LAST:event_mnuNewChannelActionPerformed

    private void mnuNewBroadcasterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuNewBroadcasterActionPerformed
        if (!AppContext.getInstance().connectToServer){
            JOptionPane.showMessageDialog(this, "This feature temporarily block.", "Notify", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        Authentication auth = new Authentication(this, true);
        auth.setDesDialog(new AddClient(this, true, 0));
        auth.setVisible(true);
//        AddClient dialog = new AddClient(this, true, 0);
//        dialog.setVisible(true);
    }//GEN-LAST:event_mnuNewBroadcasterActionPerformed

    private void mnuMessageAccountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuMessageAccountActionPerformed
        MsgAccountManager dialog = new MsgAccountManager(this, true);
        dialog.setVisible(true);
    }//GEN-LAST:event_mnuMessageAccountActionPerformed

    private void mnuLogsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuLogsActionPerformed
        ApplicationLogs dialog = new ApplicationLogs(this, true);
        dialog.setVisible(true);
    }//GEN-LAST:event_mnuLogsActionPerformed

    private void mnuChangePasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuChangePasswordActionPerformed
        ChangePassword dialog = new ChangePassword(this, true);
        dialog.setVisible(true);
    }//GEN-LAST:event_mnuChangePasswordActionPerformed

    private void btnConnectAmhsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConnectAmhsActionPerformed
        try {
            if (clientThread != null && clientThread.isRunning()) {
                JOptionPane.showMessageDialog(this, "Already connected");
                return;
            }

            String ip = AppContext.getAmhsBindIp();
            Integer port = AppContext.getAmhsPort();
            clientThread = new SocketClientThread(ip, port);
            clientThread.setClientHandler(clientEventHandler);
            clientThread.start();
        } catch (Exception ex) {
            logger.error(ex);
            clientThread.stopThread();
        }
        this.btnConnectAmhs.setEnabled(false);
        this.btnDisconnectAmhs.setEnabled(true);
    }//GEN-LAST:event_btnConnectAmhsActionPerformed

    private void btnDisconnectAmhsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDisconnectAmhsActionPerformed
        try {
            clientThread.stopThread();
            this.btnConnectAmhs.setEnabled(true);
            this.btnDisconnectAmhs.setEnabled(false);
            // re-init Account table
            initialAccountTable();
            // reset color
            for (int i = 0; i < accountModel.getRowCount(); i++) {
                accountModel.setCellColor(i, 3, null);
            }
        } catch (Exception ex) {
            logger.error(ex);
        }
    }//GEN-LAST:event_btnDisconnectAmhsActionPerformed

    private void mnuBackupDbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuBackupDbActionPerformed
        
        JFileChooser chooser = new JFileChooser();
        File file;
        file = new File(".");
        chooser.setCurrentDirectory(file);
        chooser.setDialogTitle("Select Folder To Save...");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            outputBackUpDbFolder = chooser.getSelectedFile().getPath();
        }
        if (outputBackUpDbFolder == null || outputBackUpDbFolder.isEmpty()) return;
        Thread t = new Thread(() -> {
            synchronized(this){
                logger.info("Backup Started at " + new Date());
                Base.backupDb(outputBackUpDbFolder);
                logger.info("Finished backup");
                notify();
            }
        });
        t.start();
        synchronized (t) {
            try {
                //logger.info("Waiting for Backup Thread...");
                JOptionPane.showMessageDialog(this, "Waiting for Backup Thread...");
                t.wait();
                JOptionPane.showMessageDialog(this, "Finished");
                System.gc();
            } catch (InterruptedException e) {
                logger.error(e);
            }
        }
    }//GEN-LAST:event_mnuBackupDbActionPerformed

    private void tblAccountMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblAccountMousePressed
        if (evt.getButton() != 1) {
            return;
        }
        JTable table = (JTable) evt.getSource();
        Point p = evt.getPoint();
        int index = table.rowAtPoint(p);
        if (evt.getClickCount() != 2) {
            return;
        }
        int id = (int) table.getValueAt(index, 0);
        Status status = (Status) table.getValueAt(index, 3);
        Authentication auth = new Authentication(this, true);
        auth.setDesDialog(new MsgAccountDialog(this, true, id, (status == Status.RUNNING)));
        auth.setVisible(true);
//        MsgAccountDialog dialog = new MsgAccountDialog(this, true, id, (status == Status.RUNNING));
//        dialog.setVisible(true);
    }//GEN-LAST:event_tblAccountMousePressed

    private void btnMonitorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMonitorActionPerformed
        NewMonitor dialog = new NewMonitor(this, true);
        dialog.setVisible(true);
        
    }//GEN-LAST:event_btnMonitorActionPerformed

    private void mnuSensorLogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuSensorLogActionPerformed
        SensorLogManager dialog = new SensorLogManager(this, true);
        dialog.setVisible(true);
    }//GEN-LAST:event_mnuSensorLogActionPerformed

    private void mnuFileRecordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuFileRecordActionPerformed
        try {
            Authentication auth = new Authentication(this, true);
            auth.setDesDialog(new FileManager(this, true));
            auth.setVisible(true);
        } catch (ParseException ex) {
            logger.error(ex.getMessage());
        }
    }//GEN-LAST:event_mnuFileRecordActionPerformed

    private void mnuFusedFileRecordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuFusedFileRecordActionPerformed
        try {
            Authentication auth = new Authentication(this, true);
            auth.setDesDialog(new FusedFileManager(this, true));
            auth.setVisible(true);
        } catch (ParseException ex) {
            logger.error(ex.getMessage());
        }
    }//GEN-LAST:event_mnuFusedFileRecordActionPerformed

    private void btnFusedFileManagerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFusedFileManagerActionPerformed
        try {
            Authentication auth = new Authentication(this, true);
            auth.setDesDialog(new FusedFileManager(this, true));
            auth.setVisible(true);
        } catch (ParseException ex) {
            logger.error(ex.getMessage());
        }
    }//GEN-LAST:event_btnFusedFileManagerActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        try {
            int confirmed = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to exit the program?",
                    "Confirmation",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (confirmed == JOptionPane.YES_OPTION) {
                System.exit(0);
            } else {
            }
        } catch (HeadlessException ex) {
            logger.error(ex);
        }
    }//GEN-LAST:event_formWindowClosing

    private void mnuMsgAccountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuMsgAccountActionPerformed
        if (!AppContext.getInstance().connectToServer){
            JOptionPane.showMessageDialog(this, "This feature temporarily block.", "Notify", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        AddMsgAccount dialog = new AddMsgAccount(this, true, 0);
        dialog.setVisible(true);
    }//GEN-LAST:event_mnuMsgAccountActionPerformed

    private void mnuLockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuLockActionPerformed
        Lock auth = new Lock(this, true);
        auth.setVisible(true);
    }//GEN-LAST:event_mnuLockActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Main().setVisible(true);
                } catch (DBException ex) {
                    logger.error(ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConnectAmhs;
    private javax.swing.JButton btnDisconnectAmhs;
    private javax.swing.JButton btnFileManager;
    private javax.swing.JButton btnFusedFileManager;
    private javax.swing.JButton btnMessages;
    private javax.swing.JButton btnMonitor;
    private javax.swing.JButton btnNewClient;
    private javax.swing.JButton btnNewMessageAcc;
    private javax.swing.JButton btnNewSensor;
    private javax.swing.JButton btnRefreshTable;
    private javax.swing.JButton btnSetting;
    private javax.swing.JCheckBoxMenuItem chkWarningSound;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel lblAmhsPort;
    private javax.swing.JLabel lblClock;
    private javax.swing.JLabel lblCommandPort;
    private javax.swing.JLabel lblCommandSocket;
    private javax.swing.JLabel lblFusedFile;
    private javax.swing.JLabel lblServerAddress;
    private javax.swing.JLabel lblServerMessage;
    private javax.swing.JLabel lblTarget;
    private javax.swing.JLabel lblUpdaterPort;
    private javax.swing.JLabel lblUpdaterSocket;
    private javax.swing.JMenuItem mnuAbout;
    private javax.swing.JMenuItem mnuAirport;
    private javax.swing.JMenuItem mnuBackupDb;
    private javax.swing.JMenuItem mnuChangePassword;
    private javax.swing.JMenuItem mnuCraft;
    private javax.swing.JMenu mnuEdit;
    private javax.swing.JMenuItem mnuExit;
    private javax.swing.JMenu mnuFile;
    private javax.swing.JMenuItem mnuFileRecord;
    private javax.swing.JMenuItem mnuFusedFileRecord;
    private javax.swing.JMenuItem mnuLock;
    private javax.swing.JMenuItem mnuLogs;
    private javax.swing.JMenuItem mnuMessageAccount;
    private javax.swing.JMenuItem mnuMsgAccount;
    private javax.swing.JMenuItem mnuNewBroadcaster;
    private javax.swing.JMenuItem mnuNewChannel;
    private javax.swing.JMenuItem mnuPoint;
    private javax.swing.JMenuItem mnuRoute;
    private javax.swing.JMenuItem mnuSensorLog;
    private javax.swing.JMenu mnuTools;
    private javax.swing.JProgressBar pStorage;
    private javax.swing.JPanel pnBottom;
    private javax.swing.JPanel pnChart;
    private javax.swing.JPanel pnLeft;
    private javax.swing.JScrollPane scPanel;
    private javax.swing.JTable tblAccount;
    private javax.swing.JTable tblClient;
    private javax.swing.JTable tblReceiver;
    // End of variables declaration//GEN-END:variables

}
