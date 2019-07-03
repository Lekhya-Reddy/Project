package com.ctl.ch.driver.diagnostics.service;

import com.ctl.ch.common.coreutilities.DataRow;
import com.ctl.ch.driver.diagnostics.service.ServiceUtils.RowFormater;
import com.ctl.ch.driver.diagnostics.vo.LegacyLog;

public class LegacyLogFM implements RowFormater<LegacyLog> {

	@Override
	public Class<LegacyLog> getType() {
		return LegacyLog.class;
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

	private final String[] fieldNames = { "Ucid", "State", "DataSource",
			"DataAction", "ElapsedTime", "Status", "ErrorMessage", "TimeStamp" };

	private final String[] tableColumns = { "UCID", "STATE", "DATA_SOURCE",
			"DATA_ACTION", "ELAPSED_TIME", "STATUS", "ERROR_MESSAGE",
			"TIME_STAMP_STR" };

	public final static LegacyLogFM i = new LegacyLogFM();
}
