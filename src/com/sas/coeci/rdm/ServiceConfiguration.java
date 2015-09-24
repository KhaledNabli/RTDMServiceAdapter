package com.sas.coeci.rdm;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ServiceConfiguration")
@XmlAccessorType(XmlAccessType.FIELD)
public class ServiceConfiguration implements Serializable {

	private static final long serialVersionUID = 1L;

	private String eventName;
	private String rdmHost;
	private int rdmPort;
	private String identifier;
	private String timezone;

	public ServiceConfiguration() {
	}

	public String getEventName() {
		return eventName;
	}

	public String getRdmHost() {
		return rdmHost;
	}

	public int getRdmPort() {
		return rdmPort;
	}

	public String getIdentifier() {
		return identifier;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public void setRdmHost(String rdmHost) {
		this.rdmHost = rdmHost;
	}

	public void setRdmPort(int rdmPort) {
		this.rdmPort = rdmPort;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

}
