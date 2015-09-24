package com.sas.coeci.rdm;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Response")
@XmlAccessorType(XmlAccessType.FIELD)
public class ServiceResponse {

	private List<ServiceParameter> parameters;
	
	private int statusCd;
	private String identifier;
	private String timezone;
	private List<String> statusMsg;
	private long requestStart;
	private long requestEnd;
	private long requestDuration;

	public String getTimezone() {
		return timezone;
	}

	private void updateDuration() {
		if (requestEnd > 0 && requestEnd > requestStart)
			requestDuration = requestEnd - requestStart;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}



	public ServiceResponse() {
		statusMsg = new ArrayList<String>();
	}

	public ServiceResponse(int cd, List<String> msg) {
		statusCd = cd;
		statusMsg = msg;
	}

	public List<ServiceParameter> getParameters() {
		return parameters;
	}

	public int getStatusCd() {
		return statusCd;
	}

	public String getIdentifier() {
		return identifier;
	}

	public List<String> getStatusMsg() {
		return statusMsg;
	}

	public long getRequestStart() {
		return requestStart;
	}

	public long getRequestEnd() {
		return requestEnd;
	}

	public void setStatusMsg(List<String> statusMsg) {
		this.statusMsg = statusMsg;
	}

	public void setParameters(List<ServiceParameter> parameters) {
		this.parameters = parameters;
	}

	public void setStatusCd(int statusCd) {
		this.statusCd = statusCd;
	}

	public void setRequestStart(long requestStart) {
		this.requestStart = requestStart;
		updateDuration();
	}

	public void setRequestEnd(long requestEnd) {
		this.requestEnd = requestEnd;
		updateDuration();
	}
	
	public long getRequestDuration() {
		return requestDuration;
	}
	
	public void setRequestDuration(long requestDuration) {
		this.requestDuration = requestDuration;
	}
	

}
