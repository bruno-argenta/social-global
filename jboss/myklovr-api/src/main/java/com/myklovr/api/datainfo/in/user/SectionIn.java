package com.myklovr.api.datainfo.in.user;

import java.util.HashMap;

import com.myklovr.api.datainfo.in.PrivateDataInfoIn;

public class SectionIn extends PrivateDataInfoIn {

	private String sectionName;
	private HashMap<String,String> values;
	private String nextPage;
	
	
	public String getSectionName() {
		return sectionName;
	}
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}
	public HashMap<String, String> getValues() {
		return values;
	}
	public void setValues(HashMap<String, String> values) {
		this.values = values;
	}
	public String getNextPage() {
		return nextPage;
	}
	public void setNextPage(String nextPage) {
		this.nextPage = nextPage;
	}	
	
	
}
