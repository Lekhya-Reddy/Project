package com.ctl.ch.driver.diagnostics.vo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonProperty;

@XmlRootElement
public class CallLog {

	private String ucid;
	private String sessionConnectionId;
	private String vruId;
	private String externalSystemUniqueId;
	private String dnis;
	private String accountId;
	private String ani;
	private String rxSessionId;
	private String language;
	private String version;
	private String timeStamp;
	
	@JsonProperty("UCID")
	@XmlElement(name = "UCID")
	public String getUCID() {
		return ucid;
	}
	public void setUCID(String uCID) {
		ucid = uCID;
	}
	
	@JsonProperty("SessionConnectionId")
	@XmlElement(name = "SessionConnectionId")
	public String getSessionConnectionId() {
		return sessionConnectionId;
	}
	public void setSessionConnectionId(String sessionConnectionId) {
		this.sessionConnectionId = sessionConnectionId;
	}
	
	@JsonProperty("VruId")
	@XmlElement(name = "VruId")
	public String getVruId() {
		return vruId;
	}
	public void setVruId(String vruId) {
		this.vruId = vruId;
	}
	
	@JsonProperty("ExternalSystemUniqueId")
	@XmlElement(name = "ExternalSystemUniqueId")
	public String getExternalSystemUniqueId() {
		return externalSystemUniqueId;
	}
	public void setExternalSystemUniqueId(String externalSystemUniqueId) {
		this.externalSystemUniqueId = externalSystemUniqueId;
	}
	
	@JsonProperty("Dnis")
	@XmlElement(name = "Dnis")
	public String getDnis() {
		return dnis;
	}
	public void setDnis(String dnis) {
		this.dnis = dnis;
	}
	
	@JsonProperty("AccountId")
	@XmlElement(name = "AccountId")
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	
	@JsonProperty("Ani")
	@XmlElement(name = "Ani")
	public String getAni() {
		return ani;
	}
	public void setAni(String ani) {
		this.ani = ani;
	}
	
	@JsonProperty("RxSessionId")
	@XmlElement(name = "RxSessionId")
	public String getRxSessionId() {
		return rxSessionId;
	}
	public void setRxSessionId(String rxSessionId) {
		this.rxSessionId = rxSessionId;
	}
	
	@JsonProperty("Language")
	@XmlElement(name = "Language")
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	
	@JsonProperty("Version")
	@XmlElement(name = "Version")
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	
	@JsonProperty("TimeStamp")
	@XmlElement(name = "TimeStamp")
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timestamp) {
		this.timeStamp = timestamp;
	}
}
