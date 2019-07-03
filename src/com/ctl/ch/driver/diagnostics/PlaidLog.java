package com.ctl.ch.driver.diagnostics;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.ctl.ch.common.coreutilities.DataTable;
import com.ctl.ch.common.databaseconnection.DatabaseConnectionException;
import com.ctl.ch.driver.diagnostics.db.ConfigableDataBase;
import com.ctl.ch.driver.diagnostics.service.PlaidLogFM;
import com.ctl.ch.driver.diagnostics.service.ServiceUtils;

@Path("/log/plaid")
public class PlaidLog {
	@GET
	@Path("/{ucid}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<com.ctl.ch.driver.diagnostics.vo.PlaidLog> getOSDMLog(
			@Context final HttpServletRequest request,
			@PathParam("ucid") String ucid) {

		ConfigableDataBase db = ConfigableDataBase.i(request);
		DataTable table;
		try {
			table = db
					.runSqlQuery(
							"SELECT TO_CHAR (TIME_STAMP, 'MM/dd/YYYY HH24:MI:SS.FF4') TIME_STAMP_STR, UCID, ACCOUNT_ID,"
									+ " ENTRY_TYPE, ELAPSED_MILLISECONDS, MESSAGE_ID, STATUS_CODE, ADDITIONAL_INFORMATION,"
									+ " HOST_NAME, COMPANY, CUSTOMER_CODE, RX_SESSION_ID"
									+ " FROM PLAID_LOG WHERE UCID = '"
									+ ucid
									+ "' ORDER BY TIME_STAMP ASC",
							ConfigableDataBase.DB_TIME_OUT);

			return ServiceUtils.populateList(table, PlaidLogFM.i);
		} catch (DatabaseConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
}
