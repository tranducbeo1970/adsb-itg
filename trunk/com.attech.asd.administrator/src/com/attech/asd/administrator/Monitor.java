/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.administrator;

import com.attech.asd.administrator.common.ExceptionHandler;
import com.attech.asd.administrator.common.GroundStationConfig;
import com.attech.asd.administrator.common.MapConfig;
import com.attech.asd.administrator.common.Point;
import com.attech.asd.administrator.common.Res;
import com.attech.asd.administrator.common.RoutesConfig;
import com.attech.asd.database.AreaCoordinatesDao;
import com.attech.asd.database.AreasDao;
import com.attech.asd.database.Base;
import com.attech.asd.database.StationsDao;
import com.attech.asd.database.entities.AreaCoordinates;
import com.attech.asd.database.entities.Areas;
import com.attech.asd.database.entities.Sensors;
import com.attech.asd.database.entities.Stations;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYLineAnnotation;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.util.ShapeUtilities;

/**
 *
 * @author AnhTH
 */
public class Monitor extends javax.swing.JFrame {
    private static DecimalFormat df1 = new DecimalFormat("###.######");
    private static final float minX = 98f;
    private static final float maxX = 122f;
    private static final float minY = 3f;
    private static final float maxY = 27f;
    
    private final MapConfig map;
    private final GroundStationConfig ndbConfig;
    private final GroundStationConfig vorDmeConfig;
    private final GroundStationConfig fixPointConfig;
    private final RoutesConfig routeConfig;
    
    private final JFreeChart chart;
    private final XYSeriesCollection dataset;
    private final ChartPanel panel;
    private final XYPlot plot;
    
    private boolean showRoute;
    private boolean showGrid;
    private boolean showPoint;
    private boolean showName;
    
    private Point2D firstPoint, lastPoint, currentPoint;
    
    private boolean isDraw;
    
    private List<XYLineAnnotation> drawListLine;
    private List<Point> drawListPoint;
    
    private List<XYLineAnnotation> drawListArea;
    
    /**
     * Creates new form Map
     */
    public Monitor() throws IOException {
        initComponents();
        this.setResizable(false);
        /*
        DOMConfigurator.configure(new File(".").getCanonicalPath() + Res.CFG_LOG);
        String theme = "Windows";

        try {
            Base.configure(new File(".").getCanonicalPath() + Res.CFG_DATABASE);
            AppContext.getInstance();
            theme = AppContext.getTheme();
        } catch (Exception ex) {
            

            JOptionPane.showMessageDialog(null, "Could not open connection to database", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if (theme.equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            ExceptionHandler.handle(ex);
        }
        */
        this.setIconImage(new ImageIcon("images/logo.png").getImage());
        this.setTitle(String.format("%s v%s", AppContext.getInstance().assemblyInfo.getProduct(), AppContext.getInstance().assemblyInfo.getVersion()));
        
        showRoute = false;
        showGrid = false;
        showPoint = false;
        showName = false;
        isDraw = false;
        
        map = MapConfig.load(Res.RES_MAP);
        ndbConfig = XmlSerializer.load(Res.RES_NDB, GroundStationConfig.class);
	vorDmeConfig = XmlSerializer.load(Res.RES_VORDME, GroundStationConfig.class);
	fixPointConfig = XmlSerializer.load(Res.RES_FIXPOINT, GroundStationConfig.class);
	routeConfig = XmlSerializer.load(Res.RES_ROUTE, RoutesConfig.class);
	routeConfig.loadDefendency();
        
        lblCoordinate.setText(String.format(""));
        
        dataset  = new XYSeriesCollection();
        chart = ChartFactory.createScatterPlot("", "", "", dataset);
        
        plot = (XYPlot) chart.getPlot();
        
        plot.getDomainAxis().setLabelFont(new Font("Dialog", Font.PLAIN, 10));
        plot.getDomainAxis().setTickLabelFont(new Font("Dialog", Font.PLAIN, 8));
        plot.getRangeAxis().setLabelFont(new Font("Dialog", Font.PLAIN, 10));
        plot.getRangeAxis().setTickLabelFont(new Font("Dialog", Font.PLAIN, 8));
        plot.setDomainPannable(true);
        plot.setRangePannable(true);
        
        // Show/Hide Grid
        plot.setDomainGridlinesVisible(showGrid);
        plot.setRangeGridlinesVisible(showGrid);
        
        //Changes background color
        plot.setBackgroundPaint(Color.decode("#02273b"));
        
        panel = new ChartPanel(chart);
        initChart();
        
        drawListLine = new ArrayList<>();
        drawListPoint = new ArrayList<>();
        
        btnSavePolygon.setEnabled(isDraw);
        btnClearPolygon.setEnabled(isDraw);
        
        try {
            currentPoint = panel.translateScreenToJava2D(panel.getLocation());
            plot.zoomDomainAxes(0.5, null, currentPoint);
            plot.zoomRangeAxes(0.5, null, currentPoint);
        }catch (Exception ex){
            
        }
        
        drawListArea = new ArrayList<>();
        List<Areas> areas = new AreasDao().listAll();
        cbAreas.removeAllItems();
        cbAreas.addItem("---------- Select ----------");
        for (Areas area : areas) {
            cbAreas.addItem(area.getName());
        }
        
    }
    
    private void buildSeries(){
        List<Stations> stations = new ArrayList<>();
        stations = new StationsDao().listStations();
        int i = 0;
        Shape cross = ShapeUtilities.createDiagonalCross(0.5f, 0.5f);
        for (Stations station : stations) {
            if (station.getSensors().size() > 0) {
                XYSeries series = new XYSeries(station.getStationName());
                for (Sensors s: station.getSensors()){
                    series.add(s.getLongitude(), s.getLatitude());
                    break;
                }
                dataset.addSeries(series);
            }
            plot.getRenderer().setSeriesShape(i, cross);
            plot.getRenderer().setSeriesPaint(i, Color.BLUE);
            i++;
        }
    }
    
    
    private void initChart() {
        chart.removeLegend();
        
        buildSeries();
        
        adjustDomainAxis((NumberAxis) plot.getDomainAxis(), false);
        adjustRangeAxis((NumberAxis) plot.getRangeAxis(), false);
        
        map.getXYLines().forEach((line) -> {
            plot.addAnnotation(line);
        });
        
        panel.setPopupMenu(null);
        panel.setMouseZoomable(false);
        panel.setPreferredSize(new Dimension(800, 700));
        
        panel.setAutoscrolls(false);
        panel.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                    if (e.getWheelRotation() < 0) {
                        //System.out.println("mouse wheel Up");
                        plot.zoomDomainAxes(0.95, null, currentPoint);
                        plot.zoomRangeAxes(0.95, null, currentPoint);
                    } else {
                        //System.out.println("mouse wheel Down");
                        plot.zoomDomainAxes(1.05, null, currentPoint);
                        plot.zoomRangeAxes(1.05, null, currentPoint);
                    }
            }
        });
        
        panel.addMouseListener(new MouseListener() {
            
            private void drawLine(){
                if (drawListPoint.size() > 1) {
                    drawListLine.forEach((line) -> {
                        plot.removeAnnotation(line);
                    });
                    drawListLine.clear();
                    for (int i = 0; i< drawListPoint.size(); i ++) {
                        drawListLine.add(new XYLineAnnotation(
                            drawListPoint.get(i).lng, 
                            drawListPoint.get(i).lat,
                            (i == drawListPoint.size() - 1) ? drawListPoint.get(0).lng : drawListPoint.get(i + 1).lng, 
                            (i == drawListPoint.size() - 1) ? drawListPoint.get(0).lat : drawListPoint.get(i + 1).lat, 
                            new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10), 
                            Color.RED));
                    }
                    drawListLine.forEach((line) -> {
                        plot.addAnnotation(line);
                    });
                }
            }
            
            @Override
            public void mouseClicked(MouseEvent e) {
                Point2D p = panel.translateScreenToJava2D(e.getPoint());
                currentPoint = panel.translateScreenToJava2D(e.getPoint());
                if (isDraw) {
                    switch (e.getButton()){
                        case MouseEvent.BUTTON1: // CHUOT TRAI
                            double chartX = plot.getDomainAxis().java2DToValue(p.getX(), panel.getScreenDataArea(), plot.getDomainAxisEdge());
                            double chartY = plot.getRangeAxis().java2DToValue(p.getY(), panel.getScreenDataArea(), plot.getRangeAxisEdge());
                            drawListPoint.add(new Point(chartX, chartY));
                            drawLine();
                        }
                }
                /*
                switch (e.getButton()){
                    case MouseEvent.BUTTON1: // Trai
                        //System.out.println(MouseEvent.BUTTON1);
                        if (e.getClickCount() >= 2){
                            plot.zoomDomainAxes(1.1 - (e.getClickCount() * 0.1), null, p);
                            plot.zoomRangeAxes(1.1 - (e.getClickCount() * 0.1), null, p);
                            break;
                        }
                    case MouseEvent.BUTTON3: // Phai
                        //System.out.println(MouseEvent.BUTTON3);
                        if (e.getClickCount() >= 2){
                            plot.zoomDomainAxes(0.9 + (e.getClickCount() * 0.1), null, p);
                            plot.zoomRangeAxes(0.9 + (e.getClickCount() * 0.1), null, p);
                        }
                        break;
                }
                */
            }

            @Override
            public void mousePressed(MouseEvent e) {
                firstPoint = panel.translateScreenToJava2D(e.getPoint());
                /*
                double chartX = plot.getDomainAxis().java2DToValue(firstPoint.getX(), panel.getScreenDataArea(), plot.getDomainAxisEdge());
                double chartY = plot.getRangeAxis().java2DToValue(firstPoint.getY(), panel.getScreenDataArea(), plot.getRangeAxisEdge());
                System.out.println(String.format("Mouse Press at: %s %s", chartX, chartY));
                */
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                lastPoint = panel.translateScreenToJava2D(e.getPoint()); 
                /*
                double chartX = plot.getDomainAxis().java2DToValue(lastPoint.getX(), panel.getScreenDataArea(), plot.getDomainAxisEdge());
                double chartY = plot.getRangeAxis().java2DToValue(lastPoint.getY(), panel.getScreenDataArea(), plot.getRangeAxisEdge());
                System.out.println(String.format("Mouse Released at: %s %s", chartX, chartY));
                */
                switch (e.getButton()){
                    case MouseEvent.BUTTON3: // CHUOT PHAI
                        //System.out.println(MouseEvent.BUTTON1);
                        double dx = firstPoint.getX() - lastPoint.getX();
                        double dy = firstPoint.getY() - lastPoint.getY();
                        if (dx == 0.0 && dy == 0.0) {
                            return;
                        }
                        double wPercent =  dx / firstPoint.getX();
                        double hPercent = - dy / firstPoint.getY();
                        plot.panDomainAxes(wPercent/10, null, firstPoint);
                        plot.panRangeAxes(hPercent/10, null, firstPoint);
                    
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                
            }

            @Override
            public void mouseExited(MouseEvent e) {
                
            }
        });
        
        panel.addChartMouseListener(new ChartMouseListener() {
            @Override
            public void chartMouseClicked(ChartMouseEvent cme) {
                
            }

            @Override
            public void chartMouseMoved(ChartMouseEvent cme) {
                Point2D p = panel.translateScreenToJava2D(cme.getTrigger().getPoint());
                Rectangle2D plotArea = panel.getScreenDataArea();
                double chartX = plot.getDomainAxis().java2DToValue(p.getX(), plotArea, plot.getDomainAxisEdge());
                double chartY = plot.getRangeAxis().java2DToValue(p.getY(), plotArea, plot.getRangeAxisEdge());
                //lblCoordinate.setText(String.format("%s   %s", Convertor.fromDecimalToWgs84Coord((float) chartX), Convertor.fromDecimalToWgs84Coord((float) chartY)));
                lblCoordinate.setText(String.format("%s      %s", df1.format(chartX), df1.format(chartY)));
            }
            
        });
        
        //this.pnChart.setViewportView(panel);
        this.pnChart.add(panel);
        this.pnChart.revalidate();
        this.pnChart.repaint();
    }
    
    private void adjustDomainAxis(NumberAxis axis, boolean showTickLabel) {
        axis.setRange(minX, maxX);
        axis.setAutoRange(false);
        axis.setTickUnit(new NumberTickUnit(0.1));
        axis.setVerticalTickLabels(false);
        axis.setTickLabelsVisible(showTickLabel);
        axis.setTickMarksVisible(showTickLabel);
        axis.setVisible(showTickLabel);
    }

    private void adjustRangeAxis(NumberAxis axis, boolean showTickLabel) {
        axis.setRange(minY, maxY);
        axis.setAutoRange(false);
        axis.setTickUnit(new NumberTickUnit(0.1));
        axis.setVerticalTickLabels(false);
        axis.setTickLabelsVisible(showTickLabel);
        axis.setTickMarksVisible(showTickLabel);
        axis.setVisible(showTickLabel);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnChart = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        btnGrid = new javax.swing.JButton();
        btnRoute = new javax.swing.JButton();
        btnPoint = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        btnDraw = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        lblCoordinate = new javax.swing.JLabel();
        btnSavePolygon = new javax.swing.JButton();
        btnClearPolygon = new javax.swing.JButton();
        cbAreas = new javax.swing.JComboBox<>();
        btnName = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(822, 859));
        setPreferredSize(new java.awt.Dimension(785, 750));

        btnGrid.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/grid.png"))); // NOI18N
        btnGrid.setToolTipText("Show/Hide Grid");
        btnGrid.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnGrid.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnGrid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGridActionPerformed(evt);
            }
        });

        btnRoute.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/line.png"))); // NOI18N
        btnRoute.setToolTipText("Show/Hide Route");
        btnRoute.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnRoute.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnRoute.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRouteActionPerformed(evt);
            }
        });

        btnPoint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/cluster.png"))); // NOI18N
        btnPoint.setToolTipText("Show/Hide Fixed-Point");
        btnPoint.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnPoint.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnPoint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPointActionPerformed(evt);
            }
        });

        btnDraw.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/edit_1.png"))); // NOI18N
        btnDraw.setToolTipText("Custom Draw Area");
        btnDraw.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDraw.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnDraw.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDrawActionPerformed(evt);
            }
        });

        lblCoordinate.setText("jLabel2");
        lblCoordinate.setToolTipText("Coordinate At Cursor");
        lblCoordinate.setPreferredSize(new java.awt.Dimension(150, 16));

        btnSavePolygon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/save.png"))); // NOI18N
        btnSavePolygon.setToolTipText("Save Custom Draw");
        btnSavePolygon.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSavePolygon.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSavePolygon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSavePolygonActionPerformed(evt);
            }
        });

        btnClearPolygon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/delete1.png"))); // NOI18N
        btnClearPolygon.setToolTipText("Clear Draw Preview");
        btnClearPolygon.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnClearPolygon.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnClearPolygon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearPolygonActionPerformed(evt);
            }
        });

        cbAreas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbAreas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbAreasActionPerformed(evt);
            }
        });

        btnName.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/three_tags_black.png"))); // NOI18N
        btnName.setToolTipText("Show/Hide Name");
        btnName.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnName.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNameActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnGrid)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRoute)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPoint)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbAreas, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDraw)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSavePolygon)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnClearPolygon)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblCoordinate, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(288, 288, 288)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnClearPolygon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSavePolygon, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnDraw, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnPoint, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnRoute, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnGrid, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblCoordinate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbAreas))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pnChart, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 777, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnChart, javax.swing.GroupLayout.PREFERRED_SIZE, 700, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRouteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRouteActionPerformed
        showRoute = ! showRoute;
        if (showRoute){
            routeConfig.getXYLines().forEach((line) -> {
                plot.addAnnotation(line);
            });
            if (showName)
                    routeConfig.getXYTextName().forEach((name) -> {
                        plot.addAnnotation(name);
                    });
        } else {
            routeConfig.getXYLines().forEach((line) -> {
                plot.removeAnnotation(line);
            });
            if (showName)
                    routeConfig.getXYTextName().forEach((name) -> {
                        plot.removeAnnotation(name);
                    });
        }
        if (showRoute)
            btnRoute.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/line_split.png")));
        else
            btnRoute.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/line.png")));
    }//GEN-LAST:event_btnRouteActionPerformed

    private void btnGridActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGridActionPerformed
        showGrid = !showGrid;
        plot.setDomainGridlinesVisible(showGrid);
        plot.setRangeGridlinesVisible(showGrid);
        if (showGrid)
            btnGrid.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/grid-blue.png")));
        else
            btnGrid.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/grid.png")));
    }//GEN-LAST:event_btnGridActionPerformed

    private void btnDrawActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDrawActionPerformed
        if (isDraw && drawListLine.size() > 0)
            drawListLine.forEach((line) -> {
                plot.removeAnnotation(line);
            });
        
        drawListPoint.clear();
        drawListLine.clear();
        
        isDraw = !isDraw;
        btnSavePolygon.setEnabled(isDraw);
        btnClearPolygon.setEnabled(isDraw);
    }//GEN-LAST:event_btnDrawActionPerformed

    private void btnSavePolygonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSavePolygonActionPerformed
        if (drawListPoint.size() < 3) {
            JOptionPane.showMessageDialog(rootPane, String.format("Area must have at least 3 points"));
            return;
        }
        String name = JOptionPane.showInputDialog("Enter Area Name (Max 30 chars)");
        if (name == null || name.isEmpty()) return;
        if (name.length() > 30){
            name = name.substring(0, 29);
        }
        
        Areas area = new Areas();
        area.setName(name);
        area.setDescription("Created at ASD Administrator Application");
        area.setLastModified(new Date());
        area.setModifiedBy(1);
        area.setType(1);
        AreasDao dao = new AreasDao();
        dao.save(area);
        AreaCoordinatesDao d = new AreaCoordinatesDao();
        for (Point p : drawListPoint){
            AreaCoordinates c = new AreaCoordinates();
            c.setArea(area);
            c.setLongitude(p.lng);
            c.setLatitude(p.lat);
            d.save(c);
        }
        
        dao.save(area);
        JOptionPane.showMessageDialog(rootPane, String.format("Successfully!"));
        
        List<Areas> areas = dao.listAll();
        cbAreas.removeAllItems();
        cbAreas.addItem("---------- Select ----------");
        for (Areas a : areas) {
            cbAreas.addItem(a.getName());
        }
        
        if (isDraw && drawListPoint.size() > 2)
            drawListLine.forEach((line) -> {
                plot.removeAnnotation(line);
            });
        
        drawListPoint.clear();
        drawListLine.clear();
        
        isDraw = !isDraw;
        btnSavePolygon.setEnabled(isDraw);
        btnClearPolygon.setEnabled(isDraw);
        
    }//GEN-LAST:event_btnSavePolygonActionPerformed

    private void btnClearPolygonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearPolygonActionPerformed
        if (isDraw && drawListPoint.size() > 2)
            drawListLine.forEach((line) -> {
                plot.removeAnnotation(line);
            });
        
        drawListPoint.clear();
        drawListLine.clear();
    }//GEN-LAST:event_btnClearPolygonActionPerformed

    private void btnPointActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPointActionPerformed
        showPoint = !showPoint;
        if (showPoint){
            /*
            vorDmeConfig.getXYTextPoint().forEach((point) -> {
                plot.addAnnotation(point);
            });
            ndbConfig.getXYTextPoint().forEach((point) -> {
                plot.addAnnotation(point);
            });*/
            fixPointConfig.getXYTextPoint().forEach((point) -> {
                plot.addAnnotation(point);
            });
            if (showName)
                    fixPointConfig.getXYTextName().forEach((name) -> {
                        plot.addAnnotation(name);
                    });
        } else {
            /*
            vorDmeConfig.getXYTextPoint().forEach((point) -> {
                plot.addAnnotation(point);
            });
            ndbConfig.getXYTextPoint().forEach((point) -> {
                plot.addAnnotation(point);
            });*/
            fixPointConfig.getXYTextPoint().forEach((point) -> {
                plot.removeAnnotation(point);
            });
            if (showName)
                    fixPointConfig.getXYTextName().forEach((name) -> {
                        plot.removeAnnotation(name);
                    });
        }
        if (showPoint)
            btnPoint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/cluster_blue.png")));
        else
            btnPoint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/cluster.png")));
    }//GEN-LAST:event_btnPointActionPerformed

    private void cbAreasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbAreasActionPerformed
        drawListArea.forEach((line) -> {
                plot.removeAnnotation(line);
            });
        drawListArea.clear();
        if (cbAreas.getSelectedIndex() == 0 || cbAreas.getSelectedItem() == null) {
            return;
        }
        final Areas area = new AreasDao().getAreaByName(cbAreas.getSelectedItem().toString());
        drawListArea = area.getXYLine();
        drawListArea.forEach((line) -> {
            plot.addAnnotation(line);
        });
    }//GEN-LAST:event_cbAreasActionPerformed

    private void btnNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNameActionPerformed
        showName = !showName;
        if (showName){
            /*
            vorDmeConfig.getXYTextName().forEach((point) -> {
                plot.addAnnotation(point);
            });
            ndbConfig.getXYTextName().forEach((point) -> {
                plot.addAnnotation(point);
            });
            */
            if (showRoute)
                routeConfig.getXYTextName().forEach((name) -> {
                    plot.addAnnotation(name);
                });
            if (showPoint)
                fixPointConfig.getXYTextName().forEach((point) -> {
                    plot.addAnnotation(point);
                });
        } else {
            /*
            vorDmeConfig.getXYTextName().forEach((point) -> {
                plot.addAnnotation(point);
            });
            ndbConfig.getXYTextName().forEach((point) -> {
                plot.addAnnotation(point);
            });
            */
            fixPointConfig.getXYTextName().forEach((point) -> {
                plot.removeAnnotation(point);
            });
            routeConfig.getXYTextName().forEach((name) -> {
                plot.removeAnnotation(name);
            });
            
        }
        if (showName)
            btnName.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/three_tags.png")));
        else
            btnName.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/asd/administrator/images/three_tags_black.png")));
    }//GEN-LAST:event_btnNameActionPerformed

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
            java.util.logging.Logger.getLogger(Monitor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Monitor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Monitor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Monitor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Monitor().setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(Monitor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClearPolygon;
    private javax.swing.JButton btnDraw;
    private javax.swing.JButton btnGrid;
    private javax.swing.JButton btnName;
    private javax.swing.JButton btnPoint;
    private javax.swing.JButton btnRoute;
    private javax.swing.JButton btnSavePolygon;
    private javax.swing.JComboBox<String> cbAreas;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JLabel lblCoordinate;
    private javax.swing.JPanel pnChart;
    // End of variables declaration//GEN-END:variables
}
