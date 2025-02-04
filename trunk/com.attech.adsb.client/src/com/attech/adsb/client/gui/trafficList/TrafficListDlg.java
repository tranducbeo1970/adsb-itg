/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.gui.trafficList;

import com.attech.adsb.client.common.MLogger;
import com.attech.adsb.client.common.MessageFormatFilter;
import com.attech.adsb.client.config.Configuration;
import com.attech.adsb.client.dto.FlightPlan;
import java.beans.PropertyChangeEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.text.AbstractDocument;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author andh
 */
public class TrafficListDlg extends javax.swing.JDialog {

    private final static MLogger logger = MLogger.getLogger(TrafficListDlg.class);
    private final static String DATA_PATH = "entities.flightplan/findbydof";
    private final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyMMdd");

    private final TrafficListModel model;
    private boolean isInitizing = false;
    private TrafficListUpdator updator;

    /**
     * Creates new form TrafficListDlg
     *
     * @param parent
     * @param modal
     */
    public TrafficListDlg(java.awt.Frame parent, boolean modal) {
	super(parent, modal);
	initComponents();

	// initialize
	isInitizing = true;
	model = new TrafficListModel(tblTraffictList);
	model.createLineNumber();
	Date date = new Date();
	dtDof.setDate(date);
	
//	MessageFormatFilter messageFormatFilter = new MessageFormatFilter(100);
//        messageFormatFilter.setCharacter(" -/=*");
        ((AbstractDocument) txtFilter.getDocument()).setDocumentFilter( new MessageFormatFilter(20));
	lblError.setText("");
	isInitizing = false;
	

    }

    private void update(String date) {

	String requestPath = String.format("%s/%s", DATA_PATH, date);
	Client client = ClientBuilder.newClient();
	WebTarget webTarget = client.target(Configuration.instance().getDataServiceCfg().getUrl());
	List<FlightPlan> flightPlanList = webTarget.path(requestPath).request(MediaType.APPLICATION_XML).get(new GenericType<List<FlightPlan>>() {
	});
	this.model.update(flightPlanList);
	this.lblError.setText(String.format("%s rows", flightPlanList.size()));

    }

    private void update(Date date) {
	updator = new TrafficListUpdator(date, model);
	updator.addPropertyChangeListener(this::taskPropertyChange);
	updator.execute();
    }

    private void taskPropertyChange(PropertyChangeEvent evt) {

	try {
	    String key = evt.getPropertyName();
	    // logger.debug("Task event %s:%s", evt.getSource().getClass(), key);

	    if ("state".equalsIgnoreCase(key)) {

		String value = evt.getNewValue().toString();
		logger.info("Property: " + value);
		if (value.equalsIgnoreCase("started")) {
		    logger.info("Start");
		} else {
		    logger.info("End");
		    TrafficListUpdator source = (TrafficListUpdator) evt.getSource();
		    if (source == null) {
			return;
		    }
		    lblError.setText(source.getMessage());
		}
	    } else if ("progress".equalsIgnoreCase(key)) {
//                this.pgTask.setValue((Integer) evt.getNewValue());
	    }
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

        jScrollPane1 = new javax.swing.JScrollPane();
        tblTraffictList = new javax.swing.JTable();
        jToolBar1 = new javax.swing.JToolBar();
        dtDof = new com.toedter.calendar.JDateChooser();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 32767));
        txtFilter = new javax.swing.JTextField();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 32767));
        btnRefresh = new javax.swing.JButton();
        filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        lblError = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Traffic List");
        setIconImage(null);
        setMinimumSize(new java.awt.Dimension(800, 400));
        setResizable(false);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        tblTraffictList.setAutoCreateRowSorter(true);
        tblTraffictList.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblTraffictList);

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        dtDof.setDateFormatString("dd.MM.yyyy");
        dtDof.setMaximumSize(new java.awt.Dimension(120, 22));
        dtDof.setMinimumSize(new java.awt.Dimension(120, 22));
        dtDof.setPreferredSize(new java.awt.Dimension(120, 22));
        dtDof.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                dtDofPropertyChange(evt);
            }
        });
        jToolBar1.add(dtDof);
        jToolBar1.add(filler1);

        txtFilter.setMaximumSize(new java.awt.Dimension(120, 2147483647));
        txtFilter.setMinimumSize(new java.awt.Dimension(120, 22));
        txtFilter.setPreferredSize(new java.awt.Dimension(120, 22));
        txtFilter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtFilterKeyReleased(evt);
            }
        });
        jToolBar1.add(txtFilter);
        jToolBar1.add(filler2);

        btnRefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/attech/adsb/client/images/refresh.png"))); // NOI18N
        btnRefresh.setToolTipText("Refresh data");
        btnRefresh.setFocusable(false);
        btnRefresh.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnRefresh.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });
        jToolBar1.add(btnRefresh);
        jToolBar1.add(filler3);

        lblError.setText("jLabel1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 788, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(lblError)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 342, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblError)
                .addGap(5, 5, 5))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
	try {
//	    update(DATE_FORMAT.format(dtDof.getDate()));
	    update(dtDof.getDate());
	} catch (Exception ex) {
	    logger.error(ex);
	    lblError.setText("Error: " + ex.getMessage());
	}
    }//GEN-LAST:event_formComponentShown

    private void dtDofPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_dtDofPropertyChange
	try {
	    if (!"date".equals(evt.getPropertyName()) || isInitizing) {
		return;
	    }

	    model.clear();
            // NhuongND Comment
	     update(DATE_FORMAT.format((Date) evt.getNewValue()));

	} catch (Exception ex) {
	    logger.error(ex);
	    lblError.setText("Error: " + ex.getMessage());
	}
    }//GEN-LAST:event_dtDofPropertyChange

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
	try {
//	    update(DATE_FORMAT.format(dtDof.getDate()));
	    update(dtDof.getDate());
	} catch (Exception ex) {
	    logger.error(ex);
	    lblError.setText("Error: " + ex.getMessage());
	}
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void txtFilterKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFilterKeyReleased
        try {
	    this.model.setFilter(txtFilter.getText());
	} catch (Exception ex) {
	    logger.error(ex);
	    lblError.setText("Error: " + ex.getMessage());
	}
    }//GEN-LAST:event_txtFilterKeyReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRefresh;
    private com.toedter.calendar.JDateChooser dtDof;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.Box.Filler filler3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel lblError;
    private javax.swing.JTable tblTraffictList;
    private javax.swing.JTextField txtFilter;
    // End of variables declaration//GEN-END:variables
}
