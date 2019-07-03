package com.ctl.ch.driver.diagnostics.vo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonProperty;

@XmlRootElement
public class OSDMLog {
	private String ucid;
	private String state;
	private String returnValue;
	private String returnCode;
	private String numberNoInputs;
	private String numberInvalids;
	private String inputMode;
	private String failureReason;
	private String creditCardType;
	private String confidenceScore;
	private String noToConfirm;
	private String repeatCount;
	private String helpCount;
	private String timeStamp;
	private String semanticId;
	private String ssmScore;
	private String utterance;
	
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
	
	@JsonProperty("ReturnValue")
	@XmlElement(name = "ReturnValue")
	public String getReturnValue() {
		return returnValue;
	}
	public void setReturnValue(String returnValue) {
		this.returnValue = returnValue;
	}
	
	@JsonProperty("ReturnCode")
	@XmlElement(name = "ReturnCode")
	public String getReturnCode() {
		return returnCode;
	}
	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}
	
	@JsonProperty("NumberNoInputs")
	@XmlElement(name = "NumberNoInputs")
	public String getNumberNoInputs() {
		return numberNoInputs;
	}
	public void setNumberNoInputs(String numberNoInputs) {
		this.numberNoInputs = numberNoInputs;
	}
	
	@JsonProperty("NumberInvalids")
	@XmlElement(name = "NumberInvalids")
	public String getNumberInvalids() {
		return numberInvalids;
	}
	public void setNumberInvalids(String numberInvalids) {
		this.numberInvalids = numberInvalids;
	}
	
	@JsonProperty("InputMode")
	@XmlElement(name = "InputMode")
	public String getInputMode() {
		return inputMode;
	}
	public void setInputMode(String inputMode) {
		this.inputMode = inputMode;
	}
	
	@JsonProperty("FailureReason")
	@XmlElement(name = "FailureReason")
	public String getFailureReason() {
		return failureReason;
	}
	public void setFailureReason(String failureReason) {
		this.failureReason = failureReason;
	}
	
	@JsonProperty("CreditCardType")
	@XmlElement(name = "CreditCardType")
	public String getCreditCardType() {
		return creditCardType;
	}
	public void setCreditCardType(String creditCardType) {
		this.creditCardType = creditCardType;
	}
	
	@JsonProperty("ConfidenceScore")
	@XmlElement(name = "ConfidenceScore")
	public String getConfidenceScore() {
		return confidenceScore;
	}
	public void setConfidenceScore(String confidenceScore) {
		this.confidenceScore = confidenceScore;
	}
	
	@JsonProperty("NoToConfirm")
	@XmlElement(name = "NoToConfirm")
	public String getNoToConfirm() {
		return noToConfirm;
	}
	public void setNoToConfirm(String noToConfirm) {
		this.noToConfirm = noToConfirm;
	}
	
	@JsonProperty("RepeatCount")
	@XmlElement(name = "RepeatCount")
	public String getRepeatCount() {
		return repeatCount;
	}
	public void setRepeatCount(String repeatCount) {
		this.repeatCount = repeatCount;
	}
	
	@JsonProperty("HelpCount")
	@XmlElement(name = "HelpCount")
	public String getHelpCount() {
		return helpCount;
	}
	public void setHelpCount(String helpCount) {
		this.helpCount = helpCount;
	}
	
	@JsonProperty("TimeStamp")
	@XmlElement(name = "TimeStamp")
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	@JsonProperty("SemanticId")
	@XmlElement(name = "SemanticId")
	public String getSemanticId() {
		return semanticId;
	}
	public void setSemanticId(String semanticId) {
		this.semanticId = semanticId;
	}
	
	@JsonProperty("SsmScore")
	@XmlElement(name = "SsmScore")
	public String getSsmScore() {
		return ssmScore;
	}
	public void setSsmScore(String ssmScore) {
		this.ssmScore = ssmScore;
	}
	@JsonProperty("Utterance")
	@XmlElement(name = "Utterance")
	public String getUtterance() {
		return utterance;
	}
	public void setUtterance(String utterance) {
		this.utterance = utterance;
	}
	
}
