package com.ctl.ch.driver.diagnostics.service;

import com.ctl.ch.common.coreutilities.DataRow;
import com.ctl.ch.driver.diagnostics.service.ServiceUtils.RowFormater;
import com.ctl.ch.driver.diagnostics.vo.KPILog;

public class KPILogFM implements RowFormater<KPILog> {

	@Override
	public Class<KPILog> getType() {
		return KPILog.class;
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

	private final String[] fieldNames = { "Ucid", "VruId", "HostName",
			"ApplicationName", "EtnBan", "Menu", "CallReason", "TimeStamp",
			"Ani", "Company", "RxSessionId", "AccountId" };

	private final String[] tableColumns = { "UCID", "VRU_ID", "HOST_NAME",
			"APPLICATION_NAME", "ETN_BAN", "MENU", "CALL_REASON", "TIME_STAMP_STR",
			"ANI", "COMPANY", "RX_SESSION_ID", "ACCOUNT_ID" };

	public final static KPILogFM i = new KPILogFM();
}
