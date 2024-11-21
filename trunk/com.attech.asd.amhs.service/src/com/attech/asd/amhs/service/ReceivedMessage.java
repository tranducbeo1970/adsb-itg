/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.amhs.service;


import com.attech.asd.amhs.database.entities.Message;
import com.isode.x400.highlevel.Bodypart;
import com.isode.x400.highlevel.BodypartFTBP;
import com.isode.x400.highlevel.X400APIException;
import com.isode.x400api.AMHS_att;
import com.isode.x400api.BodyPart;
import com.isode.x400api.MSMessage;
import com.isode.x400api.Recip;
import com.isode.x400api.X400;
import com.isode.x400api.X400_att;
import com.isode.x400api.X400ms;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Saitama
 */
public class ReceivedMessage {

    private final SimpleDateFormat dateFormatDay = new SimpleDateFormat("yyyy-MM-dd");

    private Integer sequenceNumber;

    private String messageId;
    private String ipmId;
    private String subjectIpmId;
    private String subjectId;

    private Integer ipnRequest;

    private Priority priority;
    private String subject;
    private String content;

    private String atsPriority;
    private String atsFilingTime;
    private String atsOHI;
    private Boolean atsExtention;

    private String origin;
    private String submissionTime;
    private String deliveriedTime;

    private String ipnRecipient;
    private String ipnReceiptTime;

    private int type;
    private Boolean isIPN;

//    private List<Attachment> attachments;
//    private List<Attach> attachs;
//    private List<ReportRecipient> reportRecips;
    private List<Recipient> recipients;
    private List<Recipient> ccRecipients;
    private List<Recipient> bccRecipient;
    private List<Recipient> envelopeRecipients;
    private Integer code;
    private String errorMessage;
    private MSMessage message;
    private boolean valid = true;
    

    // CONSTRUCTORS
    public ReceivedMessage() {
	recipients = new ArrayList<>();
	code = 0;
	valid = true;
    }

    public ReceivedMessage(int code, String error) {
	this();
	this.code = code;
	this.errorMessage = error;
	this.valid = false;
    }

    public ReceivedMessage(MSMessage message) throws X400APIException {
	this();
	this.message = message;
	this.type = message.GetType();
	this.isIPN = isIPN(message);

	if (type != X400_att.X400_MSG_MESSAGE || this.isIPN) {
	    valid = false;
	    return;
	}

//	this.type = MessageType.STORED_MESSAGE;
//	this.isIPN = false;
	parseIPM(message);

    }

    // PUBLIC METHODS
    public Message getInbox() {
	if (!this.valid) {
	    return null;
	}

	String category = MessageUtils.getCategory(this.content);
	
	Message msg = new Message();
	// message.setAccountID(0);
	msg.setCallsign("");
	msg.setCategory(category);
	msg.setContent(this.content);
//	message.setDof(ipmId);
	msg.setOrigin(this.origin);
	msg.setPriority(this.priority.value);
        msg.setSeq(this.sequenceNumber);
	
//	msg.setSeq(this.sequenceNumber);
//	message.setCreatedDate(createdDate);
//	message.setUpdatedDate(updatedDate);
//	message.setParsingCode(code);
	msg.setSubmissionTime(this.submissionTime);
	msg.setDate(this.submissionTime.substring(0, 6));
	msg.setDeliveryTime(this.deliveriedTime);
//	message.setDeliveryDate(this.deliveriedTime);
	
	
//	Inbox inbox = new Inbox();
//	inbox.setSeq(this.sequenceNumber);
//	inbox.setAtsPriority(this.atsPriority);
//	inbox.setAtsExtention(this.atsExtention);
//	inbox.setAtsFilingTime(this.atsFilingTime);
//	inbox.setAtsOhi(this.atsOHI);
//	inbox.setCategory(MessageUtils.getCategory(this.content));
//
//	// String timeStr = rm.getSubmitedTime();
//	String timeStr = this.submissionTime;
//	Date timeDate = MessageUtils.parseDate(timeStr);
//	inbox.setSubmissionTimeStr(timeStr);
//	inbox.setSubmissionTime(timeDate);
////        inbox.setDate(dateFormatDay.format(timeDate));
//
//	timeStr = this.deliveriedTime;
//	timeDate = MessageUtils.parseDate(timeStr);
//	inbox.setDeliveryTimeStr(timeStr);
//	inbox.setDeliveryTime(timeDate);
//	inbox.setDate(dateFormatDay.format(timeDate));
//
//	if (!validateDOF(inbox.getDate())) {
//	    return null;
//	}
//
//	inbox.setOrigin(this.origin);
//	inbox.setIpmId(this.ipmId);
//	inbox.setIpnRequest(this.ipnRequest);
//	inbox.setMessageId(this.messageId);
//	inbox.setPriority(this.priority.value);
//	inbox.setRead(false);
//
//	inbox.setSubject(this.subject);
//	inbox.setContent(this.content);
//
//	this.recipients.forEach((recipient) -> {
//	    inbox.addAddress(recipient.getAddress(), "To");
//	});
//
//	this.ccRecipients.forEach((recipient) -> {
//	    inbox.addAddress(recipient.getAddress(), "Cc");
//	});
//
//	this.bccRecipient.forEach((recipient) -> {
//	    inbox.addAddress(recipient.getAddress(), "Bcc");
//	});

//	this.attachments.forEach((attachment) -> {
//	    inbox.addAttachment(attachment.getInboxAttachment());
//	});
//
//	inbox.setAttachedFile(this.attachments != null && !this.attachments.isEmpty());
	return msg;
    }

    private boolean validateDOF(String currentDate) {
	Date date = new Date();
	Calendar calendar = Calendar.getInstance();
	calendar.setTime(date);
	calendar.add(Calendar.DAY_OF_YEAR, 30);
	String limitationDay = dateFormatDay.format(calendar.getTime());
	return currentDate.compareTo(limitationDay) <= 0;
    }

    // PRIVATE METHODS
    private boolean isIPN(MSMessage message) {
	final Integer isIpn = MSUtil.getIntParam(message, X400_att.X400_N_IS_IPN);
	return !(isIpn == null || isIpn == 0);
    }

    private void parseIPM(MSMessage message) throws X400APIException {

	// Parsing common message
	this.sequenceNumber = getIntParam(message, X400_att.X400_N_MS_SEQUENCE_NUMBER);
	this.priority = Priority.valueOf(getIntParam(message, X400_att.X400_N_PRIORITY));
	this.messageId = getStrParam(message, X400_att.X400_S_MESSAGE_IDENTIFIER);
	this.ipmId = getStrParam(message, X400_att.X400_S_IPM_IDENTIFIER);

	this.submissionTime = getStrParam(message, X400_att.X400_S_MESSAGE_SUBMISSION_TIME);
	this.deliveriedTime = getStrParam(message, X400_att.X400_S_MESSAGE_DELIVERY_TIME);
	this.origin = getStrParam(message, X400_att.X400_S_OR_ADDRESS);
	this.subject = getStrParam(message, X400_att.X400_S_SUBJECT);

	this.recipients = getRecipients(message, X400_att.X400_RECIP_PRIMARY);
	this.envelopeRecipients = getRecipients(message, X400_att.X400_RECIP_ENVELOPE);
	this.bccRecipient = getRecipients(message, X400_att.X400_RECIP_BCC);
	this.ccRecipients = getRecipients(message, X400_att.X400_RECIP_CC);
//        this.attachs = getMessageListAttachFile(this.attachments); 
	extractAttachment();
//	if (this.content == null) {
//	    extractAttachmentX400HighLevel();
//	}

//	if (!envelopeRecipients.isEmpty() && this.getType() == MessageType.STORED_MESSAGE) {
//	    String enRecipient = envelopeRecipients.get(0).getAddress();
//	    for (Recipient recip : this.recipients) {
//		if (!recip.getAddress().equalsIgnoreCase(enRecipient)) {
//		    continue;
//		}
//		this.setIpnRequest(recip.getNotificationRequest());
//		break;
//	    }
//	}
    }
    
    private void extractAttachment() throws X400APIException {
        BodyPart bodypart = new BodyPart();
        int index;
        for (index = 0;; index++) {
            int code = com.isode.x400api.X400ms.x400_ms_msggetbodypart(this.getMessage(), index, bodypart);
            if (code != X400_att.X400_E_NOERROR && code != X400_att.X400_E_MESSAGE_BODY) {//|| code == X400_att.X400_E_MISSING_ATTR) {
                break;
            }
            final Integer bodypartType = getIntParam(bodypart, X400_att.X400_N_BODY_TYPE);
            Attachment attachment;
            switch (bodypartType) {
                case X400_att.X400_T_IA5TEXT:
                    final String content = getStrParam(bodypart, X400_att.X400_S_BODY_DATA);
                    if (content != null && !content.isEmpty() && content.contains("\u0001")) {
                        String[] lines = content.split("\u0002");
                        if (lines.length > 1) {
                            this.setContent(lines[1]);
                        }

                        String header = lines[0].replace("\u0001", "");
                        String[] headers = header.split("\r\n");
                        for (String h : headers) {
                            if (h.startsWith("PRI:")) {
                                this.setAtsPriority(h.split(":")[1].trim());
                            }
                            if (h.startsWith("OHI:")) {
                                this.setAtsOHI(h.split(":")[1].trim());
                            }
                            if (h.startsWith("FT:")) {
                                this.setAtsFilingTime(h.split(":")[1].trim());
                            }
                        }
                    } else {
                        // this.setContent(getStrParam(bodypart, X400_att.X400_S_BODY_DATA));
                        this.setContent(content);
                        this.setAtsFilingTime(getStrParam(this.getMessage(), AMHS_att.ATS_S_FILING_TIME));
                        this.setAtsPriority(getStrParam(this.getMessage(), AMHS_att.ATS_S_PRIORITY_INDICATOR));
                        this.setAtsOHI(getStrParam(this.getMessage(), AMHS_att.ATS_S_OPTIONAL_HEADING_INFO));
                    }

                    break;

                case X400_att.X400_T_BINARY:
                case X400_att.X400_T_FTBP:
//                    attachment = getStrParam(bodypart);
//                    if (attachment == null) {
//                        break;
//                    }
//                    this.attachments.add(attachment);
                    break;
                case X400_att.X400_T_GENERAL_TEXT:
                    final String heading = getStrParam(bodypart, X400_att.X400_S_BODY_DATA);

                    if (heading == null || heading.isEmpty()) {
                        break;
                    }

                    String[] lines = heading.split("\u0002");
                    if (lines.length > 1) {
                        this.setContent(lines[1]);
                    }

                    String header = lines[0].replace("\u0001", "");
                    String[] headers = header.split("\r\n");
                    for (String h : headers) {
                        if (h.startsWith("PRI:")) {
                            this.setAtsPriority(h.split(":")[1].trim());
                        }
                        if (h.startsWith("OHI:")) {
                            this.setAtsOHI(h.split(":")[1].trim());
                        }
                        if (h.startsWith("FT:")) {
                            this.setAtsFilingTime(h.split(":")[1].trim());
                        }
                    }
                    break;

                default:
                    break;
            }
        }
    }
    
//    private void extractAttachmentX400HighLevel() throws X400APIException {
//        ReceiveMsg rm = new ReceiveMsg(p7BindSession, this.sequenceNumber);
//        int numOfBodyparts = rm.getNumberOfBodyparts();
//        for (int i = 1; i <= numOfBodyparts; i++) {
//            Bodypart bp = rm.getBodypart(i);
//            Attachment attachment;
//            if (bp instanceof BodypartIA5Text) {
//                BodypartIA5Text bpt = (BodypartIA5Text) bp;
//                final String content = bpt.getTextContent();//getStrParam(bodypart, X400_att.X400_S_BODY_DATA);
//                if (content != null && !content.isEmpty() && content.contains("\u0001")) {
//                    String[] lines = content.split("\u0002");
//                    if (lines.length > 1) {
//                        this.setContent(lines[1]);
//                    }
//
//                    String header = lines[0].replace("\u0001", "");
//                    String[] headers = header.split("\r\n");
//                    for (String h : headers) {
//                        if (h.startsWith("PRI:")) {
//                            this.setAtsPriority(h.split(":")[1].trim());
//                        }
//                        if (h.startsWith("OHI:")) {
//                            this.setAtsOHI(h.split(":")[1].trim());
//                        }
//                        if (h.startsWith("FT:")) {
//                            this.setAtsFilingTime(h.split(":")[1].trim());
//                        }
//                    }
//                } else {
//                    this.setContent(content);
//                    this.setAtsFilingTime(getStrParam(this.getMessage(), AMHS_att.ATS_S_FILING_TIME));
//                    this.setAtsPriority(getStrParam(this.getMessage(), AMHS_att.ATS_S_PRIORITY_INDICATOR));
//                    this.setAtsOHI(getStrParam(this.getMessage(), AMHS_att.ATS_S_OPTIONAL_HEADING_INFO));
//                }
//            } else if (bp instanceof BodypartGeneralText) {
//                BodypartGeneralText gtbp = (BodypartGeneralText) bp;
//                final String heading = gtbp.getStringRepresentation();//getStrParam(bodypart, X400_att.X400_S_BODY_DATA);
//                if (heading == null || heading.isEmpty()) {
//                    break;
//                }
//
//                String[] lines = heading.split("\u0002");
//                if (lines.length > 1) {
//                    this.setContent(lines[1]);
//                }
//
//                String header = lines[0].replace("\u0001", "");
//                String[] headers = header.split("\r\n");
//                for (String h : headers) {
//                    if (h.startsWith("PRI:")) {
//                        this.setAtsPriority(h.split(":")[1].trim());
//                    }
//                    if (h.startsWith("OHI:")) {
//                        this.setAtsOHI(h.split(":")[1].trim());
//                    }
//                    if (h.startsWith("FT:")) {
//                        this.setAtsFilingTime(h.split(":")[1].trim());
//                    }
//                }
//            } else if (bp instanceof BodypartFTBP) {
////                attachment = getStrParam(bp);
////                if (attachment == null) {
////                    return;
////                }
////                this.attachments.add(attachment);
//            } else if (bp.getType() == Bodypart.Bodypart_Type.BODYPART_BINARY) {
////                attachment = getStrParam(bp);
////                if (attachment == null) {
////                    return;
////                }
////                this.attachments.add(attachment);
//            } else if (bp.getType() == Bodypart.Bodypart_Type.BODYPART_MESSAGE) {
//                com.isode.x400api.Message fwdMsg = new com.isode.x400api.Message();
//                com.isode.x400api.X400ms.x400_ms_msggetmessagebody(rm, i, fwdMsg);
//                BodypartForwardedMessage fwd = new BodypartForwardedMessage(bp.getBodypartObject());
//                fwd.setFwdMessage(fwdMsg);
////                System.out.println(fwd.getStringRepresentation());
//                final String content = fwd.getStringRepresentation();
//                if (content != null && !content.isEmpty() && content.contains("\u0001")) {
//                    String[] lines = content.split("\u0002");
//                    if (lines.length > 1) {
//                        this.setContent(lines[1]);
//                    }
//
//                    String header = lines[0].replace("\u0001", "");
//                    String[] headers = header.split("\r\n");
//                    for (String h : headers) {
//                        if (h.startsWith("PRI:")) {
//                            this.setAtsPriority(h.split(":")[1].trim());
//                        }
//                        if (h.startsWith("OHI:")) {
//                            this.setAtsOHI(h.split(":")[1].trim());
//                        }
//                        if (h.startsWith("FT:")) {
//                            this.setAtsFilingTime(h.split(":")[1].trim());
//                        }
//                    }
//                } else {
//                    this.setContent(content);
//                    this.setAtsFilingTime(getStrParam(this.getMessage(), AMHS_att.ATS_S_FILING_TIME));
//                    this.setAtsPriority(getStrParam(this.getMessage(), AMHS_att.ATS_S_PRIORITY_INDICATOR));
//                    this.setAtsOHI(getStrParam(this.getMessage(), AMHS_att.ATS_S_OPTIONAL_HEADING_INFO));
//                }
//            }
//        }
////        }
//    }

    private List<Recipient> getRecipients(MSMessage message, int type) {
	int index;
	int code;

	final List<Recipient> addresses = new ArrayList<>();

	Recip recip = new Recip();
	Recipient recipient;
	for (index = 1;; index++) {

	    code = com.isode.x400api.X400ms.x400_ms_recipget(message, type, index, recip);
	    if (code != X400_att.X400_E_NOERROR) {
		break;
	    }
	    recipient = new Recipient(recip);
	    if (recipient.getAddress() == null) {
		break;
	    }
	    addresses.add(recipient);
	}
	return addresses;
    }

    private Integer getIntParam(BodyPart bodypart_obj, int attribute) {
	final int code = X400.x400_bodypartgetintparam(bodypart_obj, attribute);
	if (code != X400_att.X400_E_NOERROR) {
	    //System.out.printf("Fail to get attribute %s from BodyPart Object %n", attribute);
	    return null;
	}
	return bodypart_obj.GetIntValue();
    }

    private String getStrParam(BodyPart bodypart, int attribute) {
	StringBuffer value = new StringBuffer();
	byte[] bytes = new byte[32000];
	int status = com.isode.x400api.X400.x400_bodypartgetstrparam(bodypart, attribute, value, bytes);
	if (status != X400_att.X400_E_NOERROR) {
	    return null;
	}

	return value.toString();
    }

    private Attachment getStrParam(BodyPart bodypart) {
	String fileName = getStrParam(bodypart, X400_att.X400_S_FTBP_FILENAME);
	Integer length = getIntParam(bodypart, X400_att.X400_N_FTBP_OBJECT_SIZE);
	final StringBuffer value = new StringBuffer();
	final byte[] bytes = new byte[length];
	final int status = com.isode.x400api.X400.x400_bodypartgetstrparam(bodypart, X400_att.X400_S_BODY_DATA, value, bytes);
	if (status != X400_att.X400_E_NOERROR) {
	    return null;
	}

	return new Attachment(fileName, bytes);
    }

    private Attachment getStrParam(Bodypart bp) throws X400APIException {
	BodypartFTBP ftbp = (BodypartFTBP) bp;
	System.out.println(ftbp.getStringRepresentation());
	String fileName = ftbp.getFileName();;
	Integer length = ftbp.getSize();
	byte[] bytes = new byte[length];
//            fileName = ftbp.getStringRepresentation(); 
	bytes = ftbp.getBodyData();
	if (fileName == null || bytes == null) {
	    return null;
	}
	return new Attachment(fileName, bytes);
    }

    private Integer getIntParam(MSMessage ms, int attribute) {
	final int stt = X400ms.x400_ms_msggetintparam(ms, attribute);
	if (stt != X400_att.X400_E_NOERROR) {
	    // System.out.println(String.format("Fail to get int attribute from msmessage object. (Attribute: %s, Code: %s)", attribute, stt));
	    return null;
	}
	return ms.GetIntValue();
    }

    private String getStrParam(MSMessage ms, int attribute) {
	StringBuffer value = new StringBuffer();
	final int stt = X400ms.x400_ms_msggetstrparam(ms, attribute, value);
	if (stt != X400_att.X400_E_NOERROR) {
	    return null;
	}
	return value.toString();
    }

    /* PROPERTIES */
    //<editor-fold defaultstate="collapsed" desc="Class properties">
    /**
     * @return the sequenceNumber
     */
    public Integer getSequenceNumber() {
	return sequenceNumber;
    }

    /**
     * @param sequenceNumber the sequenceNumber to set
     */
    public void setSequenceNumber(Integer sequenceNumber) {
	this.sequenceNumber = sequenceNumber;
    }

    /**
     * @return the messageId
     */
    public String getMessageId() {
	return messageId;
    }

    /**
     * @param messageId the messageId to set
     */
    public void setMessageId(String messageId) {
	this.messageId = messageId;
    }

    /**
     * @return the ipmId
     */
    public String getIpmId() {
	return ipmId;
    }

    /**
     * @param ipmId the ipmId to set
     */
    public void setIpmId(String ipmId) {
	this.ipmId = ipmId;
    }

    /**
     * @return the subjectIpmId
     */
    public String getSubjectIpmId() {
	return subjectIpmId;
    }

    /**
     * @param subjectIpmId the subjectIpmId to set
     */
    public void setSubjectIpmId(String subjectIpmId) {
	this.subjectIpmId = subjectIpmId;
    }

    /**
     * @return the subjectId
     */
    public String getSubjectId() {
	return subjectId;
    }

    /**
     * @param subjectId the subjectId to set
     */
    public void setSubjectId(String subjectId) {
	this.subjectId = subjectId;
    }

    /**
     * @return the ipnRequest
     */
    public Integer getIpnRequest() {
	return ipnRequest;
    }

    /**
     * @param ipnRequest the ipnRequest to set
     */
    public void setIpnRequest(Integer ipnRequest) {
	this.ipnRequest = ipnRequest;
    }

    /**
     * @return the priority
     */
    public Priority getPriority() {
	return priority;
    }

    /**
     * @param priority the priority to set
     */
    public void setPriority(Priority priority) {
	this.priority = priority;
    }

    /**
     * @return the subject
     */
    public String getSubject() {
	return subject;
    }

    /**
     * @param subject the subject to set
     */
    public void setSubject(String subject) {
	this.subject = subject;
    }

    /**
     * @return the content
     */
    public String getContent() {
	return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(String content) {
	this.content = content;
    }

    /**
     * @return the atsPriority
     */
    public String getAtsPriority() {
	return atsPriority;
    }

    /**
     * @param atsPriority the atsPriority to set
     */
    public void setAtsPriority(String atsPriority) {
	this.atsPriority = atsPriority;
    }

    /**
     * @return the atsFilingTime
     */
    public String getAtsFilingTime() {
	return atsFilingTime;
    }

    /**
     * @param atsFilingTime the atsFilingTime to set
     */
    public void setAtsFilingTime(String atsFilingTime) {
	this.atsFilingTime = atsFilingTime;
    }

    /**
     * @return the atsOHI
     */
    public String getAtsOHI() {
	return atsOHI;
    }

    /**
     * @param atsOHI the atsOHI to set
     */
    public void setAtsOHI(String atsOHI) {
	this.atsOHI = atsOHI;
    }

    /**
     * @return the atsExtention
     */
    public Boolean getAtsExtention() {
	return atsExtention;
    }

    /**
     * @param atsExtention the atsExtention to set
     */
    public void setAtsExtention(Boolean atsExtention) {
	this.atsExtention = atsExtention;
    }

    /**
     * @return the origin
     */
    public String getOrigin() {
	return origin;
    }

    /**
     * @param origin the origin to set
     */
    public void setOrigin(String origin) {
	this.origin = origin;
    }

    /**
     * @return the submissionTime
     */
    public String getSubmissionTime() {
	return submissionTime;
    }

    /**
     * @param submissionTime the submissionTime to set
     */
    public void setSubmissionTime(String submissionTime) {
	this.submissionTime = submissionTime;
    }

    /**
     * @return the deliveriedTime
     */
    public String getDeliveriedTime() {
	return deliveriedTime;
    }

    /**
     * @param deliveriedTime the deliveriedTime to set
     */
    public void setDeliveriedTime(String deliveriedTime) {
	this.deliveriedTime = deliveriedTime;
    }

    /**
     * @return the ipnRecipient
     */
    public String getIpnRecipient() {
	return ipnRecipient;
    }

    /**
     * @param ipnRecipient the ipnRecipient to set
     */
    public void setIpnRecipient(String ipnRecipient) {
	this.ipnRecipient = ipnRecipient;
    }

    /**
     * @return the ipnReceiptTime
     */
    public String getIpnReceiptTime() {
	return ipnReceiptTime;
    }

    /**
     * @param ipnReceiptTime the ipnReceiptTime to set
     */
    public void setIpnReceiptTime(String ipnReceiptTime) {
	this.ipnReceiptTime = ipnReceiptTime;
    }

    /**
     * @return the type
     */
    public int getType() {
	return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(int type) {
	this.type = type;
    }

    /**
     * @return the isIPN
     */
    public Boolean getIsIPN() {
	return isIPN;
    }

    /**
     * @param isIPN the isIPN to set
     */
    public void setIsIPN(Boolean isIPN) {
	this.isIPN = isIPN;
    }

    /**
     * @return the recipients
     */
    public List<Recipient> getRecipients() {
	return recipients;
    }

    /**
     * @param recipients the recipients to set
     */
    public void setRecipients(List<Recipient> recipients) {
	this.recipients = recipients;
    }

    /**
     * @return the ccRecipients
     */
    public List<Recipient> getCcRecipients() {
	return ccRecipients;
    }

    /**
     * @param ccRecipients the ccRecipients to set
     */
    public void setCcRecipients(List<Recipient> ccRecipients) {
	this.ccRecipients = ccRecipients;
    }

    /**
     * @return the bccRecipient
     */
    public List<Recipient> getBccRecipient() {
	return bccRecipient;
    }

    /**
     * @param bccRecipient the bccRecipient to set
     */
    public void setBccRecipient(List<Recipient> bccRecipient) {
	this.bccRecipient = bccRecipient;
    }

    /**
     * @return the envelopeRecipients
     */
    public List<Recipient> getEnvelopeRecipients() {
	return envelopeRecipients;
    }

    /**
     * @param envelopeRecipients the envelopeRecipients to set
     */
    public void setEnvelopeRecipients(List<Recipient> envelopeRecipients) {
	this.envelopeRecipients = envelopeRecipients;
    }

    /**
     * @return the code
     */
    public Integer getCode() {
	return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(Integer code) {
	this.code = code;
    }

    /**
     * @return the message
     */
    public MSMessage getMessage() {
	return message;
    }

    /**
     * @return the errorMessage
     */
    public String getErrorMessage() {
	return errorMessage;
    }

    /**
     * @param errorMessage the errorMessage to set
     */
    public void setErrorMessage(String errorMessage) {
	this.errorMessage = errorMessage;
    }
//
//    public List<Attach> getAttachs() {
//        return attachs;
//    }
//
//    public void setAttachs(List<Attach> attachs) {
//        this.attachs = attachs;
//    }

    /**
     * @return the valid
     */
    public boolean isValid() {
	return valid;
    }

    /**
     * @param valid the valid to set
     */
    public void setValid(boolean valid) {
	this.valid = valid;
    }

    //</editor-fold>
}
