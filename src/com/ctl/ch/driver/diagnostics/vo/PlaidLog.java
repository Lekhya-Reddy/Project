package com.ctl.ch.driver.diagnostics.vo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonProperty;

@XmlRootElement
public class PlaidLog {
	private String timeStamp;
	private String ucid;
	private String accountId;
	private String entryType;
	private String elapsedMilliseconds;
	private String messageId;
	private String statusCode;
	private String additionalInformation;
	private String hostName;
	private String company;
	private String customerCode;
	private String rxSessionId;

	@JsonProperty("TimeStamp")
	@XmlElement(name = "TimeStamp")
	public String TimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	@JsonProperty("Ucid")
	@XmlElement(name = "Ucid")
	public String Ucid() {
		return ucid;
	}

	public void setUcid(String ucid) {
		this.ucid = ucid;
	}

	@JsonProperty("AccountId")
	@XmlElement(name = "AccountId")
	public String AccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	@JsonProperty("EntryType")
	@XmlElement(name = "EntryType")
	public String EntryType() {
		return entryType;
	}

	public void setEntryType(String entryType) {
		this.entryType = entryType;
	}

	@JsonProperty("ElapsedMilliseconds")
	@XmlElement(name = "ElapsedMilliseconds")
	public String ElapsedMilliseconds() {
		return elapsedMilliseconds;
	}

	public void setElapsedMilliseconds(String elapsedMilliseconds) {
		this.elapsedMilliseconds = elapsedMilliseconds;
	}

	@JsonProperty("MessageId")
	@XmlElement(name = "MessageId")
	public String MessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	@JsonProperty("StatusCode")
	@XmlElement(name = "StatusCode")
	public String StatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	@JsonProperty("AdditionalInformation")
	@XmlElement(name = "AdditionalInformation")
	public String AdditionalInformation() {
		return additionalInformation;
	}

	public void setAdditionalInformation(String additionalInformation) {
		this.additionalInformation = additionalInformation;
	}

	@JsonProperty("HostName")
	@XmlElement(name = "HostName")
	public String HostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	@JsonProperty("Company")
	@XmlElement(name = "Company")
	public String Company() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	@JsonProperty("CustomerCode")
	@XmlElement(name = "CustomerCode")
	public String CustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	@JsonProperty("RxSessionId")
	@XmlElement(name = "RxSessionId")
	public String RxSessionId() {
		return rxSessionId;
	}

	public void setRxSessionId(String rxSessionId) {
		this.rxSessionId = rxSessionId;
	}
}