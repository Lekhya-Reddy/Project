package com.ctl.ch.driver.diagnostics.service;

import com.ctl.ch.common.coreutilities.DataRow;
import com.ctl.ch.driver.diagnostics.service.ServiceUtils.RowFormater;
import com.ctl.ch.driver.diagnostics.vo.CallLog;

public class CallLogFM implements RowFormater<CallLog> {

	@Override
	public Class<CallLog> getType() {
		return CallLog.class;
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

	private final String[] fieldNames = { "UCID", "SessionConnectionId",
			"VruId", "ExternalSystemUniqueId", "Dnis", "AccountId", "Ani",
			"RxSessionId", "Language", "Version", "TimeStamp" };

	private final String[] tableColumns = { "UCID", "SESSION_CONNECTION_ID",
			"VRU_ID", "EXTERNAL_SYSTEM_UNIQUE_ID", "DNIS", "ACCOUNT_ID", "ANI",
			"RX_SESSION_ID", "LANGUAGE", "VERSION", "TIME_STAMP_STR" };
	
	public final static CallLogFM i = new CallLogFM();
}
