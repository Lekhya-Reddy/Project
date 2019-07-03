package com.ctl.ch.driver.diagnostics.service;

import com.ctl.ch.common.coreutilities.DataRow;
import com.ctl.ch.driver.diagnostics.service.ServiceUtils.RowFormater;
import com.ctl.ch.driver.diagnostics.vo.PlaidLog;

public class PlaidLogFM implements RowFormater<PlaidLog> {

	@Override
	public Class<PlaidLog> getType() {
		return PlaidLog.class;
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

	private final String[] fieldNames = { "TimeStamp", "Ucid", "AccountId",
			"EntryType", "ElapsedMilliseconds", "MessageId", "StatusCode",
			"AdditionalInformation", "HostName", "Company", "CustomerCode",
			"RxSessionId" };

	private final String[] tableColumns = { "TIME_STAMP_STR", "UCID", "ACCOUNT_ID",
			"ENTRY_TYPE", "ELAPSED_MILLISECONDS", "MESSAGE_ID", "STATUS_CODE",
			"ADDITIONAL_INFORMATION", "HOST_NAME", "COMPANY", "CUSTOMER_CODE",
			"RX_SESSION_ID" };

	public final static PlaidLogFM i = new PlaidLogFM();
}
