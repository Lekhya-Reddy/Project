package com.ctl.ch.driver.diagnostics.service;

import com.ctl.ch.common.coreutilities.DataRow;
import com.ctl.ch.driver.diagnostics.service.ServiceUtils.RowFormater;
import com.ctl.ch.driver.diagnostics.vo.OSDMLog;

public class OSDMLogFM implements RowFormater<OSDMLog> {

	@Override
	public Class<OSDMLog> getType() {
		return OSDMLog.class;
	}

	@Override
	public String[] fields() {
		return fieldNames;
	}

	@Override
	public Object[] format(DataRow<?, ?> row) {
		Object[] values = new Object[tableColumns.length];
		for (int i = 0; i < tableColumns.length; i++) {
			values[i] = String.valueOf(row.get(tableColumns[i]));
		}

		return values;
	}

	private final String[] fieldNames = { "Ucid", "State", "ReturnValue",
			"ReturnCode", "NumberNoInputs", "NumberInvalids", "InputMode",
			"FailureReason", "CreditCardType", "ConfidenceScore",
			"NoToConfirm", "RepeatCount", "HelpCount", "TimeStamp","SemanticId","SsmScore","Utterance" };

	private final String[] tableColumns = { "UCID", "STATE", "RETURN_VALUE",
			"RETURN_CODE", "NUMBER_NO_INPUTS", "NUMBER_INVALIDS", "INPUT_MODE",
			"FAILURE_REASON", "CREDIT_CARD_TYPE", "CONFIDENCE_SCORE",
			"NO_TO_CONFIRM", "REPEAT_COUNT", "HELP_COUNT", "TIME_STAMP_STR","SEMANTIC_ID","SSM_SCORE","UTTERANCE" };

	public final static OSDMLogFM i = new OSDMLogFM();
}
