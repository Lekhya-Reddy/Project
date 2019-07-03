package com.ctl.ch.driver.diagnostics.vo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonProperty;

@XmlRootElement
public class KPILog {
	private String ucid;
	private String vruId;
	private String hostName;
	private String applicationName;
	private String etnBan;
	private String menu;
	private String callReason;
	private String timeStamp;
	private String ani;
	private String company;
	private String rxSessionId;
	private String accountId;
	
	@JsonProperty("Ucid")
	@XmlElement(name = "Ucid")
	public String getUcid() {
		return ucid;
	}
	public void setUcid(String ucid) {
		this.ucid = ucid;
	}
	
	@JsonProperty("VruId")
	@XmlElement(name = "VruId")
	public String getVruId() {
		return vruId;
	}
	public void setVruId(String vruId) {
		this.vruId = vruId;
	}
	
	@JsonProperty("HostName")
	@XmlElement(name = "HostName")
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	
	@JsonProperty("ApplicationName")
	@XmlElement(name = "ApplicationName")
	public String getApplicationName() {
		return applicationName;
	}
	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}
	
	@JsonProperty("EtnBan")
	@XmlElement(name = "EtnBan")
	public String getEtnBan() {
		return etnBan;
	}
	public void setEtnBan(String etnBan) {
		this.etnBan = etnBan;
	}
	
	@JsonProperty("Menu")
	@XmlElement(name = "Menu")
	public String getMenu() {
		return menu;
	}
	public void setMenu(String menu) {
		this.menu = menu;
	}
	
	@JsonProperty("CallReason")
	@XmlElement(name = "CallReason")
	public String getCallReason() {
		return callReason;
	}
	public void setCallReason(String callReason) {
		this.callReason = callReason;
	}
	
	@JsonProperty("TimeStamp")
	@XmlElement(name = "TimeStamp")
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	@JsonProperty("Ani")
	@XmlElement(name = "Ani")
	public String getAni() {
		return ani;
	}
	public void setAni(String ani) {
		this.ani = ani;
	}
	
	@JsonProperty("Company")
	@XmlElement(name = "Company")
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	
	@JsonProperty("RxSessionId")
	@XmlElement(name = "RxSessionId")
	public String getRxSessionId() {
		return rxSessionId;
	}
	public void setRxSessionId(String rxSessionId) {
		this.rxSessionId = rxSessionId;
	}
	
	@JsonProperty("AccountId")
	@XmlElement(name = "AccountId")
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
}
