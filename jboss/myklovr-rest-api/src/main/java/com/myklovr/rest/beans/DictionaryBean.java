package com.myklovr.rest.beans;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.myklovr.api.datainfo.dictionaries.in.DictionaryIn;
import com.myklovr.api.datainfo.dictionaries.out.DictionaryOut;
import com.myklovr.api.dictionaries.DictionariesAPI;

@Stateless
@LocalBean
public class DictionaryBean {

	

	public List<DictionaryOut> getDictionary(DictionaryIn dictionary) throws Exception {
		List<DictionaryOut> result = DictionariesAPI.getDictionary(dictionary);		
		return result;
	}


	

	

}
