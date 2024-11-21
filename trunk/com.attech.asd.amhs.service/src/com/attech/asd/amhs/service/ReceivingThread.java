package com.attech.asd.amhs.service;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.attech.asd.amhs.database.dao.DBException;
import com.attech.asd.amhs.database.dao.FlightPlanDao;
import com.attech.asd.amhs.database.dao.HibernateUtil;
import com.attech.asd.amhs.database.dao.MessageAccountDao;
import com.attech.asd.amhs.database.dao.MessageDao;
import com.attech.asd.amhs.database.dao.MessageIndexDao;
import com.attech.asd.amhs.database.entities.Flightplan;
import com.attech.asd.amhs.database.entities.Message;
import com.attech.asd.amhs.database.entities.MessageAccount;
import com.attech.asd.amhs.database.entities.MessageIndex;
import com.attech.asd.amhs.service.monitor.Status;
import com.attech.asd.amhs.service.monitor.ThreadStatus;
import com.isode.x400.highlevel.X400APIException;
import com.isode.x400api.X400_att;
import java.util.List;
import java.sql.SQLException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import org.apache.log4j.xml.DOMConfigurator;

/**
 *
 * @author ANDH
 */
public class ReceivingThread extends BaseThread {

    private static final MLogger logger = MLogger.getLogger(ReceivingThread.class);
    private static final SimpleDateFormat DAY_BEGIN_FORMAT = new SimpleDateFormat("yyMMdd000000'Z'");
    private static final SimpleDateFormat DAY_END_FORMAT = new SimpleDateFormat("yyMMdd235959'Z'");
    private static final int MESSAGE_AMOUNT = 1000; // Number of message to process before retrying to get new message

    private MessageAccount account;
    private Connection connection;

    // DAO
    private final MessageAccountDao messageAccountDao = new MessageAccountDao();
    private final MessageIndexDao messageIndexDao = new MessageIndexDao();
    private final MessageDao messageDao = new MessageDao();
    private final FlightPlanDao flightPlanDao = new FlightPlanDao();

//    private long retryTimeout = 30;
    private long retryTimeout = 3000;
    private Integer waitingTimeout;
    private Integer id;
    private boolean running;
    private Date startTime;
    private final ThreadStatus status;
    private int count;
    private ThreadEventHandler eventHandler;

    public ReceivingThread(MessageAccount messageAccount) {
        super();
        this.account = messageAccount;
        this.connection = new Connection(messageAccount);
        this.running = false;
        this.status = new ThreadStatus();
        this.status.setId(this.account.getId());
        this.status.setStatus(Status.STOPPED);
        this.status.setMessageCount(this.count);
        this.count = 0;
        // NhuongND update
        this.retryTimeout = this.account.getUpdateInterval();
    }

    @Override
    public void run() {
        try {
            startTime = new Date();
//            synchronized (status) {
            this.status.setStartTime(startTime);
            this.status.setStatus(Status.RUNNING);
//            }

            this.running = true;
            logger.info("Thread %s:%s started", this.account.getId(), this.account.getName());
            while (!Thread.interrupted()) {
                updatingMessage();
            }

        } catch (InterruptedException ex) {
            logger.info(ex.getMessage());
        } catch (Exception ex) {
            logger.error(ex);
        } finally {
            this.status.setStatus(Status.STOPPED);
            this.running = false;
            logger.info("Thread %s:%s stopped", this.account.getId(), this.account.getName());
        }
    }

    @Override
    public void stopThread() {

        this.connection.cancelWaiting();
        interrupt();

        while (this.isAlive()) {
            this.connection.cancelWaiting();
            interrupt();
        }
    }

    public synchronized void refresh() {
        logger.info("Request updating manually");
        this.connection.cancelWaiting();
        this.unlock();
    }

    public synchronized void disconnect() {
        logger.info("disConnection manually");
        this.connection.disconnect();
    }

    // PRIVATE METHOD
    private void updatingMessage() throws InterruptedException {
        try {

            this.status.setStatus(Status.RUNNING);
            this.status.setRemark(null);
            
            if (Thread.interrupted()) {
                throw new InterruptedException(String.format("Thread %s:%s is requested for stopping", this.account.getId(), this.account.getName()));
            }
//            final Date date = new Date();
            this.connection.connect();

            buildIndex(Connection.MSG_TYPE_STORED_MESSAGE);

            final List<MessageIndex> messageIndexes = messageIndexDao.getUpdateList(this.account.getId());
            for (MessageIndex messageIndex : messageIndexes) {
                updateMessage(messageIndex);
                if (Thread.interrupted()) {
                    throw new InterruptedException(String.format("Thread %s:%s is requested for stopping", this.account.getId(), this.account.getName()));
                }
            }

            logger.info("Thread %s:%s sleep for %s seconds", this.account.getId(), this.account.getName(), this.retryTimeout/1000);
            this.lock(this.retryTimeout);

        } catch (Exception ex) {
            if (Thread.interrupted()) {
                throw new InterruptedException(String.format("Thread %s:%s is requested for stopping", this.account.getId(), this.account.getName()));
            }
            this.status.setStatus(Status.ERROR);
            this.status.setRemark(ex.getMessage());
            logger.error(ex);
            logger.info("Thread %s:%s sleep for %s seconds", this.account.getId(), this.account.getName(), this.retryTimeout/1000);
            this.lock(this.retryTimeout);
        } finally {

        }
    }

    private void updateMessage(MessageIndex messageIndex) throws X400APIException, SQLException, Exception {
        if (Thread.interrupted()) {
            throw new InterruptedException(String.format("Thread %s:%s - Request for stopping", this.account.getId(), this.account.getName()));
        }

//        connection.connect();
        ReceivedMessage receivedMessage = connection.getMessage(messageIndex.getSeq());
        if (receivedMessage.getCode() != 0) {

            messageIndex.setErrorCode(receivedMessage.getCode());
            messageIndex.increasingRetryCount();
            messageIndexDao.update(messageIndex);
            logger.warn("Thread %s:%s is getting message %s error. Code %s:%s",
                    this.account.getName(),
                    this.account.getName(),
                    messageIndex.getSeq(),
                    receivedMessage.getCode(),
                    receivedMessage.getErrorMessage());
            return;
        }

        if (receivedMessage.getIsIPN()) {
            logger.warn("Thread %s:%s - IPN message found. Skip update", this.account.getName(), this.account.getName());
            messageIndexDao.remove(messageIndex);
            return;
        }

        if (receivedMessage.getType() == X400_att.X400_MSG_PROBE) {
            logger.warn("Thread %s:%s - Probe message found. Skip update", this.account.getName(), this.account.getName());
            messageIndexDao.remove(messageIndex);
            return;
        }

        if (receivedMessage.getType() == X400_att.X400_MSG_REPORT) {
            logger.warn("Thread %s:%s - Report message found. Skip update", this.account.getName(), this.account.getName());
            messageIndexDao.remove(messageIndex);
            return;
        }

        if (receivedMessage.getType() == X400_att.X400_MSG_SUBMITTED_MESSAGE) {
            logger.warn("Thread %s:%s - Submitted message found. Skip update", this.account.getName(), this.account.getName());
            messageIndexDao.remove(messageIndex);
            return;
        }

        Message message = receivedMessage.getInbox();
        message.setAccountID(this.account.getId());
        messageDao.insertOrUpdate(message);

        increasingCount();

        String category = message.getCategory();
        switch (category) {
            case "FPL":
                processFpl(message);
                break;

            case "KHBN":
                processDailyFpl(message);
                break;

            case "ARR":
                processArr(message);
                break;

            case "DEP":
                processDep(message);
                break;

        }

        messageDao.insertOrUpdate(message);
        messageIndexDao.remove(messageIndex);
        count++;
    }

    private void processFpl(Message inbox) throws SQLException, InterruptedException {
        try {

            logger.debug("Thread %s:%s parses FPL", this.account.getId(), this.account.getName());

            FplEnt fplent = new FplEnt(inbox.getContent());
            if (Validator.isNullOrEmpty(fplent.getDof())) {
                fplent.setDof(inbox.getSubmissionTime().substring(0, 6));
            }

            if (!fplent.validate()) {
                return;
            }

            final Flightplan fpl = new Flightplan();
            fpl.setCallSign(fplent.getCallSign());
            fpl.setCraft(fplent.getAircraftType());
            fpl.setDep(fplent.getDeparture());
            fpl.setDest(fplent.getDestination());
            fpl.setDof(fplent.getDof());
            fpl.setEet(fplent.getTotalEET());
            fpl.setEtd(fplent.getDepartureTime());
            fpl.setReg(fplent.getReg());
            fpl.setRoute(fplent.getRoute());
            fpl.setRemark(fplent.getRemarks());
	    
//	    fpl.setSeq(inbox.getSeq());
            fpl.setFplID(inbox.getId());
            flightPlanDao.updateFlightPlan(fpl);
            inbox.setParsingCode(1);

            logger.debug("Thread %s:%s - Save FPL DOF: %s CALLSIGN: %s DEP: %s DEST: %s",
                    this.account.getId(),
                    this.account.getName(),
                    fpl.getDof(),
                    fpl.getCallSign(),
                    fpl.getDep(),
                    fpl.getDest());

        } catch (Exception ex) {
            inbox.setParsingCode(-1);
            logger.error("Thread %s:%s - Parsing FPL has trouble.", this.account.getId(), this.account.getName());
            logger.error(ex);
            logger.debug("Thread %s:%s - Message content\n%s", this.account.getId(), this.account.getName(), inbox.getContent());
        }
    }

    private void processDailyFpl(Message inbox) throws Exception {
        try {
            final List<Flightplan> dailyFplList = DailyFPL.getDailyFlightPlanList(inbox.getContent());
            for (Flightplan dailyFpl : dailyFplList) {
                dailyFpl.setDailyFplID(inbox.getId());
                flightPlanDao.updateDailyFlightPlan(dailyFpl);
            }
        } catch (InterruptedException ex) {
            throw ex;
        } catch (Exception ex) {
            logger.error("Thread %s:%s - Parsing Daily FPL has trouble.", this.account.getId(), this.account.getName());
            logger.error(ex);
            logger.error("Thread %s:%s - Message content\n%s", this.account.getId(), this.account.getName(), inbox.getContent());
        }
    }

    private void processDep(Message inbox) throws SQLException, InterruptedException {
        try {
            String strContent = inbox.getContent();
            DepEnt dep = new DepEnt(strContent);
            if (Validator.isNullOrEmpty(dep.getDof())) {
                dep.setDof(inbox.getSubmissionTime().substring(0, 6));
            }
            if (!dep.validate()) {
                return;
            }

            Flightplan flightPlan = new Flightplan();
            flightPlan.setAtd(dep.getDepartureTime());
            flightPlan.setCallSign(dep.getCallSign());
            flightPlan.setDep(dep.getDeparture());
            flightPlan.setDest(dep.getDestination());
            flightPlan.setDof(dep.getDof());
            flightPlan.setDepID(inbox.getId());
//	    flightPlan.setDepSeq(inbox.getSeq());
            flightPlanDao.updateDeparture(flightPlan);
        } catch (Exception ex) {
            inbox.setParsingCode(-1);
            logger.error("Thread %s:%s - Parsing DEP has trouble.", this.account.getId(), this.account.getName());
            logger.error(ex);
            logger.error("Thread %s:%s - Message content\n%s", this.account.getId(), this.account.getName(), inbox.getContent());
        }
    }

    private void processArr(Message inbox) throws SQLException, InterruptedException {
        try {

            final ArrEnt arrEnt = new ArrEnt(inbox.getContent());

            if (Validator.isNullOrEmpty(arrEnt.getDof())) {
                arrEnt.setDof(inbox.getSubmissionTime().substring(0, 6));
            }

            if (!arrEnt.validate()) {
                return;
            }

            Flightplan flightPlan = new Flightplan();
            flightPlan.setAta(arrEnt.getArrivalTime());
            flightPlan.setCallSign(arrEnt.getCallsign());
            flightPlan.setDep(arrEnt.getDeparture());
            flightPlan.setDest(arrEnt.getDestination());
            flightPlan.setDof(arrEnt.getDof());
//	    flightPlan.setArrSeq(inbox.getSeq());
            flightPlan.setArrID(inbox.getId());
            flightPlanDao.updateArrival(flightPlan);

        } catch (Exception ex) {
            inbox.setParsingCode(-1);
            logger.error("Thread %s:%s - Parsing ARR has trouble.", this.account.getId(), this.account.getName());
            logger.error(ex);
            logger.error("Thread %s:%s - Message content\n%s", this.account.getId(), this.account.getName(), inbox.getContent());
        }
    }

    public void printStatus() {
        State state = super.getState();
//        long id = this.getId();
        switch (state) {
            case BLOCKED:
                logger.error("Thread %s:%s - Dead (%s)", this.account.getId(), this.account.getName(), state);
                break;
            case NEW:
                logger.warn("Thread %s:%s - Status is not running (%s)", this.account.getId(), this.account.getName(), state);
                // logger.info(String.format("%s (Thread ID: %s) IS NOT RUNNING (%s)", thread.toString(), thread.getId(), state));
                break;
            case RUNNABLE:
                logger.info("Thread %s:%s - Still alive (%s)", this.account.getId(), this.account.getName(), state);
                // logger.info(String.format("%s (Thread ID: %s) IS ALIVE (%s)", thread.toString(), thread.getId(), state));
                break;
            case TERMINATED:
                logger.error("Thread %s:%s - Dead (%s)", this.account.getId(), this.account.getName(), state);
                // logger.info(String.format("%s (Thread ID: %s) IS DEAD (%s)", thread.toString(), thread.getId(), state));
                break;
            case TIMED_WAITING:
                logger.info("Thread %s:%s - Still alive (%s)", this.account.getId(), this.account.getName(), state);
                // logger.info(String.format("%s (Thread ID: %s) IS ALIVE (%s)", thread.toString(), thread.getId(), state));
                break;
            case WAITING:
                logger.info("Thread %s:%s - Still alive (%s)", this.account.getId(), this.account.getName(), state);
                // logger.info(String.format("%s (Thread ID: %s) IS ALIVE (%s)", thread.toString(), thread.getId(), state));
                break;
            default:
                logger.warn("Thread %s:%s - Status is unknown (%s)", this.account.getId(), this.account.getName(), state);
                // logger.info(String.format("%s (Thread ID: %s) IS UNKNOWN (%s)", thread.toString(), thread.getId(), state));
                break;
        }
    }

    private void increasingCount() {
        if (this.count == Integer.MAX_VALUE - 1) {
            count = 0;
        } else {
            count++;
        }
    }

    // PROPERTIES
    public void setRetryTimeout(long retryTimeout) {
        this.retryTimeout = retryTimeout;
    }

    public void setWaitingTimeout(Integer waitingTimeout) {
        this.waitingTimeout = waitingTimeout;
    }

    public Integer getAccountID() {
        return this.account.getId();
    }

    public String getAccountName() {
        return this.account.getName();
    }

    public boolean isRunning() {
        return running;
    }

    public boolean isEnable() {
        return this.account.getEnable();
    }

    public synchronized ThreadStatus getStatus() {

        this.status.setMessageCount(this.count);
        return this.status;
    }

    public void setEventHandler(ThreadEventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    private void notifyStatus() {
        if (this.eventHandler == null) {
            return;
        }
        this.eventHandler.notify(Arrays.asList(status));
    }

    @Override
    protected MLogger getLogger() {
        return logger;
    }

    private void buildIndex(int messageClass) throws X400APIException, InterruptedException, DBException {

        if (Thread.interrupted()) {
            throw new InterruptedException(String.format("Thread %s:%s is requested for stopping", this.account.getId(), this.account.getName()));
        }

        String checkPoint = this.account.getCheckPoint();
        checkPoint = checkPoint == null ? DAY_BEGIN_FORMAT.format(new Date()) : checkPoint;
        logger.info("Thread %s:%s - Building index since %s", this.account.getId(), this.account.getName(), checkPoint);

        List<Index> indexList = connection.listMessages(checkPoint, messageClass);

        String submissionTime = null;

        for (Index index : indexList) {

            if (Thread.interrupted()) {
                throw new InterruptedException(String.format("Thread %s:%s is requested for stopping", this.account.getId(), this.account.getName()));
            }

            submissionTime = index.getSubmissionTime();

            // Add new message index
            MessageIndex messageIndex = new MessageIndex();
            messageIndex.setAccountID(this.account.getId());
            messageIndex.setSeq(index.getSequence());
            messageIndex.setSubmissionTime(submissionTime);
            messageIndex.setSubject(index.getSubject());
            messageIndex.setOrigin(index.getOrigin());
            messageIndex.setSubject(index.getSubject());

            //Check for existence
            if (messageIndexDao.isExisted(messageIndex)) {
                logger.info("Thread %s:%s - Seq %s of account %s is existed. Skip updating", this.account.getId(), this.account.getName(), messageIndex.getSeq(), account.getName());
                continue;
            }

            this.messageIndexDao.insert(messageIndex);
            logger.info("Thread %s:%s - Seq %s added", this.account.getId(), this.account.getName(), messageIndex.getSeq());

            if (submissionTime == null || submissionTime.isEmpty()) {
                continue;
            }

            // Update check point
            if (checkPoint == null || submissionTime.compareToIgnoreCase(checkPoint) > 0) {
                checkPoint = submissionTime;
                account.setCheckPoint(checkPoint);
                messageAccountDao.update(account);
                logger.debug("Thread %s:%s - Update checkpoint %s", this.account.getId(), this.account.getName(), checkPoint);
            }
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Main">
    public static void main(String[] args) throws SQLException, X400APIException, IOException, Exception {

        HibernateUtil.buildSessionFactory("config/database.xml");
        DOMConfigurator.configure("config/log.xml");
        MessageUtils.initialize();

        MessageAccountDao messageAccountDao = new MessageAccountDao();

        List<MessageAccount> accounts = messageAccountDao.getAll();
//	ReceivingThread thread;
        for (MessageAccount account : accounts) {
            if (!account.getEnable()) {
                continue;
            }
            ReceivingThread thread = new ReceivingThread(account);
            thread.start();

        }

//        ReceivingThread thread = new ReceivingThread();
//        // thread.setFolderManager(new FolderManager());
//        thread.setRetryTimeout(message.getRetryProcessingInterval());
//        thread.setWaitingTimeout(message.getWaitingMessageTimeout());
//        thread.setAccount(Account.context);
//
//        thread.initialize();
//
//        // boolean isUpdated = thread.process();
//        // System.out.println("Updated: " + isUpdated);
//        thread.start();
//        while (true) {
//            try {
//                Thread.sleep(3600);
//            } catch (InterruptedException ex) {
//                Logger.getLogger(ReceivingThread.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
    }
    //</editor-fold>

}
