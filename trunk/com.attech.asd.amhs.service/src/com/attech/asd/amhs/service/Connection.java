/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.amhs.service;

import com.attech.asd.amhs.database.entities.MessageAccount;
import com.isode.x400.highlevel.P3BindSession;
import com.isode.x400.highlevel.P7BindSession;
import com.isode.x400.highlevel.X400APIException;
import com.isode.x400.highlevel.X400Msg;
import com.isode.x400api.MSListResult;
import com.isode.x400api.MSMessage;
import com.isode.x400api.Session;
import com.isode.x400api.X400_att;
import com.isode.x400api.X400ms;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ANDH
 */
public class Connection {

    private MLogger logger = MLogger.getLogger("AMHSConnection");

    public static int MSG_TYPE_STORED_MESSAGE = 0;
    public static int MSG_TYPE_SUBMITTED_MESSAGE = 1;
    public static int MSG_STATUS_NEW = 0;
    public static int MSG_STATUS_LISTED = 1;
    public static int MSG_STATUS_FETCH = 2;
    public static int MSG_STATUS_ANY = -1;

    private final DateFormat filingTimeFormat = new SimpleDateFormat("ddHHmm");

    private Session session;
    private String connectionString;
    private String account;
    private String password;
    private ConnectionStatus status;

    private boolean isWaitingForNewMessage;

    // private ConnectionStatusChangedEventHandler conStatusChangedHandler;

    // CONSTRUCTORS
    public Connection() {
    }
    
    public Connection(MessageAccount account) {
        this.connectionString = account.getConnectionString();
        this.account = account.getAccount();
        this.password = account.getPassword();
    }

    public Connection(String connectionString, String account, String password) {
        this.connectionString = connectionString;
        this.account = account;
        this.password = password;
    }

    // PUBLIC METHODS
    public void waitNewMessage(int autoId, int timeout) throws X400APIException {
        int code = X400ms.x400_ms_msregisterautoaction(session, X400_att.X400_AUTO_ALERT, autoId);
        if (code != X400_att.X400_E_NOERROR) {
            throw new X400APIException(String.format("Set auto-aciton fail (id: %s)", autoId));
        }

        X400ms.x400_ms_enablewait();
        code = X400ms.x400_ms_waitnew(session, timeout);
        logger.error("End waiting (code %s)", code);
    }

    public void cancelWaiting() {
        X400ms.x400_ms_cancelwait();
        logger.info("Cancel waiting");
    }

    public void connect() throws X400APIException {

	if (session != null && status == ConnectionStatus.Connected) {
                logger.debug("Already connected");
                return;
        }

        session = new P7BindSession(connectionString, account, password, false);
        session.SetSummarizeOnBind(false);
        ((P7BindSession) session).bind();

        logger.info("Connected");
        status = ConnectionStatus.Connected;
    }

    public void disconnect() {
       try {
	    if (session == null || status == ConnectionStatus.Disconnected) {
		return;
	    }
	    ((P3BindSession) session).unbind();

	} catch (X400APIException ex) {
	    logger.error("Disconnecting fail (%s)", ex.getMessage());
	} finally {
	    status = ConnectionStatus.Disconnected;
	    logger.info("Disconnected");
	}
    }
    
    public ReceivedMessage getMessage(Integer seq) throws X400APIException, IOException {

        final MSMessage message = new MSMessage();
        int result = X400ms.x400_ms_msggetstart(this.session, seq, message);
        logger.debug(String.format("Getting message (seq: %s, code: %s)", seq, result));

        if (result == X400_att.X400_E_INT_ERROR || result == X400_att.X400_E_SYSERROR) {
            String error = X400ms.x400_ms_get_string_error(session, result);
            this.disconnect();
            throw new X400APIException(String.format("Getting message fail (seq: %s, code: %s %s)", seq, result, error));
        }

        //code 119: There is no file on server
        if (result != X400_att.X400_E_NOERROR) {
            String error = X400ms.x400_ms_get_string_error(session, result);
            // logger.error("Getting message fail (code %s)", result);
            // this.disconnect();
            // throw new X400APIException(String.format("Getting message fail (seq: %s, code: %s)", seq, result));
            return new ReceivedMessage(result, error);
        }

	
        logger.debug("Getting done");
        ReceivedMessage receivedMessage = new ReceivedMessage(message);
        receivedMessage.setSequenceNumber(seq);
        return receivedMessage;
    }

    public List<Index> listMessages(String since, String until) throws X400APIException {

        final List<Index> indexes = new ArrayList<>();

        MSListResult messageList = new MSListResult();
        int code = X400ms.x400_ms_listexauxpribefore(session, since, until, MSG_TYPE_STORED_MESSAGE, -1, X400_att.X400_PRIORITY_ANY, messageList); // ex(session, since, entryClass, messageList);
        if (code != X400_att.X400_E_NOERROR) {
            disconnect();
            logger.error("Fetching message fail (code %s)", code);
            throw new X400APIException(String.format("Fetching message fail (code %s)", code));
        }
        parse(messageList, indexes);
        X400ms.x400_ms_listfree(messageList);

        messageList = new MSListResult();
        code = X400ms.x400_ms_listexauxpribefore(session, since, until, MSG_TYPE_SUBMITTED_MESSAGE, -1, X400_att.X400_PRIORITY_ANY, messageList); // ex(session, since, entryClass, messageList);
        if (code != X400_att.X400_E_NOERROR) {
            disconnect();
            logger.error("Fetching message fail (code %s)", code);
            throw new X400APIException(String.format("Fetching message fail (code %s)", code));
        }
        parse(messageList, indexes);
        X400ms.x400_ms_listfree(messageList);

        return indexes;
    }

    public List<Index> listMessages(String since, int entryClass) throws X400APIException {
        final MSListResult messageList = new MSListResult();
        final int code = X400ms.x400_ms_listex(session, since, entryClass, messageList);
        if (code != X400_att.X400_E_NOERROR) {
            disconnect();
            logger.error("Fetching message fail (code %s)", code);
            throw new X400APIException(String.format("Fetching message fail (code %s)", code));
        }

        final List<Index> indexes = parse(messageList, entryClass);
        X400ms.x400_ms_listfree(messageList);
        return indexes;
    }

    public List<Index> listMessages(String since, String until, int entryClass) throws X400APIException {
        final MSListResult messageList = new MSListResult();
        final int code = X400ms.x400_ms_listexauxpribefore(session, since, until, entryClass, -1, X400_att.X400_PRIORITY_ANY, messageList); // ex(session, since, entryClass, messageList);
        if (code != X400_att.X400_E_NOERROR) {
            disconnect();
            throw new X400APIException(String.format("Fetching message fail (code %s)", code));
        }

        final List<Index> indexes = parse(messageList, entryClass);
        X400ms.x400_ms_listfree(messageList);
        return indexes;
    }

    public P7BindSession getSession() {
        return (P7BindSession) session;
    }

    public void setLogCategory(Class clzz) {
        logger = MLogger.getLogger(clzz);
    }

    // PRIVATE METHODS
    private List<Index> parse(MSListResult msListResult, int entryClass) {
        final List<Index> results = new ArrayList<>();
        int code = 0;
        int i;
        for (i = 1;; i++) {
            code = X400ms.x400_ms_listgetintparam(msListResult, X400_att.X400_N_MS_SEQUENCE_NUMBER, i);
            if (code == X400_att.X400_E_NO_MORE_RESULTS) {
                break;
            }

            if (code == X400_att.X400_E_NO_VALUE) {
                continue;
            }

            if (code != X400_att.X400_E_NOERROR) {
                break;
            }

            Index item = new Index(msListResult, i);
            item.setClazz(entryClass);

            results.add(item);
        }
        // X400ms.x400_ms_listfree(msListResult);
        return results;
    }

    private void parse(MSListResult msListResult, List<Index> indexes) {
        // final List<Index> results = new ArrayList<>();
        int code = 0;
        int i;
        for (i = 1;; i++) {
            code = X400ms.x400_ms_listgetintparam(msListResult, X400_att.X400_N_MS_SEQUENCE_NUMBER, i);
            if (code == X400_att.X400_E_NO_MORE_RESULTS) {
                break;
            }

            if (code == X400_att.X400_E_NO_VALUE) {
                continue;
            }

            if (code != X400_att.X400_E_NOERROR) {
                break;
            }

            Index item = new Index(msListResult, i);
            //item.setClazz(entryClass);
            indexes.add(item);
        }
    }

    @Override
    public String toString() {
        MString builder = new MString("Connection info : \n");
        builder.append("  > Presentation Address: %s%n", this.connectionString);
        builder.append("  > Account: %s%n", this.account);
        builder.append("  > Password: %s%n", this.password);
        builder.append("  > Connection Status: %s%n", this.status);
        return builder.toString();
    }

}
