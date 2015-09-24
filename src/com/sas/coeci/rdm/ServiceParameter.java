package com.sas.coeci.rdm;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ServiceParameter {

	private String name;

	private String type;

	private String value;

	@XmlElement
	private List<String> valueList;

	public ServiceParameter() {
	}

	public ServiceParameter(String name, String type) {
		super();
		this.name = name;
		this.type = type;
	}

	public ServiceParameter(String name, String type, String value) {
		super();
		this.name = name;
		this.type = type;
		this.value = value;
	}

	public ServiceParameter(String name, String type, List<String> valueL) {
		super();
		this.name = name;
		this.type = type;
		this.valueList = valueL;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public String getValue() {
		return value;
	}

	public List<String> getValueList() {
		return valueList;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setValueList(List<String> valueList) {
		this.valueList = valueList;
	}

	
	public void setValueList(Object[] objects) {
		if(objects != null) {
			valueList = new ArrayList<String>();
			for(int i = 0; i < objects.length; i++) {
				valueList.add(objects[i].toString());
			}
		}
	}
	
	
	public Double[] getDoubleArray() throws NumberFormatException {
		Double[] result = null;

		if (valueList != null) {
			result = new Double[valueList.size()];
			for (int i = 0; i < valueList.size(); i++) {
				result[i] = Double.parseDouble(valueList.get(i));
			}
		}
		return result;
	}
	
	public Boolean[] getBooleanArray() throws NumberFormatException {
		Boolean[] result = null;

		if (valueList != null) {
			result = new Boolean[valueList.size()];
			for (int i = 0; i < valueList.size(); i++) {
				result[i] = Boolean.parseBoolean(valueList.get(i));
			}
		}
		return result;
	}
	
	public Long[] getLongArray() throws NumberFormatException {
		Long[] result = null;
		
		if (valueList != null) {
			result = new Long[valueList.size()];
			for (int i = 0; i < valueList.size(); i++) {
				result[i] = Long.parseLong(valueList.get(i));
			}
		}
		return result;
	}
	
	public String[] getStringArray() throws NumberFormatException {
		String[] result = null;
		
		if (valueList != null) {
			result = new String[valueList.size()];
			for (int i = 0; i < valueList.size(); i++) {
				result[i] = valueList.get(i);
			}
		}
		return result;
	}
}
