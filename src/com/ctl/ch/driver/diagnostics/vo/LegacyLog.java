package com.ctl.ch.driver.diagnostics.vo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonProperty;

@XmlRootElement
public class LegacyLog {
	private String ucid;
	private String state;
	private String dataSource;
	private String dataAction;
	private String elapsedTime;
	private String status;
	private String errorMessage;
	private String timeStamp;
	
	@JsonProperty("Ucid")
	@XmlElement(name = "Ucid")
	public String getUcid() {
		return ucid;
	}
	public void setUcid(String ucid) {
		this.ucid = ucid;
	}
	
	@JsonProperty("State")
	@XmlElement(name = "State")
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	@JsonProperty("DataSource")
	@XmlElement(name = "DataSource")
	public String getDataSource() {
		return dataSource;
	}
	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}
	
	@JsonProperty("DataAction")
	@XmlElement(name = "DataAction")
	public String getDataAction() {
		return dataAction;
	}
	public void setDataAction(String dataAction) {
		this.dataAction = dataAction;
	}
	
	@JsonProperty("ElapsedTime")
	@XmlElement(name = "ElapsedTime")
	public String getElapsedTime() {
		return elapsedTime;
	}
	public void setElapsedTime(String elapsedTime) {
		this.elapsedTime = elapsedTime;
	}
	
	@JsonProperty("Status")
	@XmlElement(name = "Status")
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	@JsonProperty("ErrorMessage")
	@XmlElement(name = "ErrorMessage")
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	@JsonProperty("TimeStamp")
	@XmlElement(name = "TimeStamp")
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
}
