package com.sas.coeci.rdm;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Request")
@XmlAccessorType(XmlAccessType.FIELD)
public class ServiceRequest {
 
	@XmlElement(name = "Configuration")
	private ServiceConfiguration config;

	@XmlElement(name = "InputVariables")
	private List<ServiceParameter> inParameter;
	
	@XmlElement(name = "OutputVariables")
	private List<ServiceParameter> outParameter;

	public ServiceRequest() {

	}

	public ServiceConfiguration getConfig() {
		return config;
	}

	public void setConfig(ServiceConfiguration config) {
		this.config = config;
	}

	public List<ServiceParameter> getInParameter() {
		return inParameter;
	}

	public List<ServiceParameter> getOutParameter() {
		return outParameter;
	}

	public void setInParameter(List<ServiceParameter> inParameter) {
		this.inParameter = inParameter;
	}

	public void setOutParameter(List<ServiceParameter> outParameter) {
		this.outParameter = outParameter;
	}
	
	

}
