package com.myklovr.api.dictionaries;

import java.util.ArrayList;
import java.util.List;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.myklovr.api.GenericAPI;
import com.myklovr.api.datainfo.in.dictionary.DictionaryIn;
import com.myklovr.api.datainfo.out.dictionary.DictionaryOut;
import com.myklovr.helpers.PropertiesHelper;
import com.myklovr.helpers.constants.MessagesConstants;
import com.myklovr.helpers.exception.BussinesException;;

public class DictionariesAPI extends GenericAPI {

	private static final String INDUSTRY_TYPE = "INDUSTRY_TYPE";
	private static final String COUNTRY = "COUNTRY";
	private static final String STATE = "STATE";
	private static final String SUBJECTS = "SUBJECTS";
	private static final String SCHOOL_TYPE = "SCHOOL_TYPE";

	public static List<DictionaryOut> getDictionary(DictionaryIn dictionary) throws BussinesException {
		List<DictionaryOut> resultList = new ArrayList<DictionaryOut>();
		StringBuffer statement = new StringBuffer();
		StringBuffer sb = new StringBuffer();
		List<Object> args = new ArrayList<Object>();
		sb.append("SELECT id,value FROM ");
		switch (dictionary.getDictionary()) {
		case COUNTRY:
			sb.append("country;");
			break;
		case INDUSTRY_TYPE:
			sb.append("industry_type;");
			break;
		case SUBJECTS:
			sb.append("subjects;");
			break;
		case SCHOOL_TYPE:
			sb.append("school_type;");
			break;
		case STATE:
			sb.append("state where country_code =?;");
			args.add(dictionary.getFilter());
			break;
			default: throw new BussinesException(PropertiesHelper.getStringMessageProperty(MessagesConstants.ERROR_DICTIONARY_NOT_FOUND));
		}
		statement.append(sb.toString());
		ResultSet result = executeStatement(statement.toString(), args);
		for (Row row : result) {
			resultList.add(getElement(row));
		}
		return resultList;
	}

	private static DictionaryOut getElement(Row row) {
		DictionaryOut result = new DictionaryOut();
		result.setCode(row.getString(0));
		result.setValue(row.getString(1));
		return result;
	}

}
