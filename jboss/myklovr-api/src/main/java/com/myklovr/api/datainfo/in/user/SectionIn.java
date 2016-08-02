package com.myklovr.api.datainfo.in.user;

import java.util.Map;

import com.myklovr.api.datainfo.in.PrivateDataInfoIn;

public class SectionIn extends PrivateDataInfoIn {

	private String sectionName;
	private Map<String,String> values;
	
	
	public String getSectionName() {
		return sectionName;
	}
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}
	public Map<String, String> getValues() {
		return values;
	}
	public void setValues(Map<String, String> values) {
		this.values = values;
	}	
	
	
}
