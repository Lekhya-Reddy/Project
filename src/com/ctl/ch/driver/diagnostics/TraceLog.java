package com.ctl.ch.driver.diagnostics;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.ctl.ch.common.coreutilities.DataTable;
import com.ctl.ch.common.databaseconnection.DatabaseConnectionException;
import com.ctl.ch.driver.diagnostics.db.ConfigableDataBase;
import com.ctl.ch.driver.diagnostics.service.CallLogFM;
import com.ctl.ch.driver.diagnostics.service.ServiceUtils;
import com.ctl.ch.driver.diagnostics.vo.CallLog;

@Path("/tracelog/")
public class TraceLog {

	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public List<CallLog> getCallLog(@Context final HttpServletRequest request,
			@QueryParam("ucid") String ucid, @QueryParam("dnis") String dnis,
			@QueryParam("language") String language,
			@QueryParam("ani") String ani,
			@QueryParam("accountid") String accountid,
			@QueryParam("date") String date) {
		ConfigableDataBase db = ConfigableDataBase.i(request);
		DataTable table;
		String whereClause = "";
		try {
			if (!ServiceUtils.isNullOrEmpty(ani)) {
			    if (ani.trim().length() == 10)
			          whereClause = " ANI = '" + ani + "'";
			    else
				whereClause = " ANI LIKE '%" + ani + "%'";
			}
			if (!ServiceUtils.isNullOrEmpty(dnis)) {
				whereClause += ((whereClause.length() > 0) ? " AND" : "");
				 if (dnis.trim().length() == 10)
			          whereClause = whereClause + " DNIS = '" + dnis + "'";
			        else
			          whereClause = whereClause + " DNIS LIKE '%" + dnis + "%'";
			}
			if (!ServiceUtils.isNullOrEmpty(language)) {
				whereClause += ((whereClause.length() > 0) ? " AND" : "")+
						" LOWER(LANGUAGE) LIKE '%" + language.toLowerCase() + "%'";
			}
			if (!ServiceUtils.isNullOrEmpty(accountid)) {
				whereClause += ((whereClause.length() > 0) ? " AND" : "")
						+ " ACCOUNT_ID LIKE '%" + accountid + "%'";
			}
			if (!ServiceUtils.isNullOrEmpty(ucid)) {
				whereClause += ((whereClause.length() > 0) ? " AND" : "");
		        if (ucid.trim().length() == 20)
		            whereClause = whereClause + " UCID ='" + ucid + "'";
		          else
		            whereClause = whereClause + " UCID LIKE '%" + ucid + "%'";
			}
			if (!ServiceUtils.isNullOrEmpty(date)) {
				whereClause += ((whereClause.length() > 0) ? " AND" : "")
						+  " TIME_STAMP >= TRUNC(TO_DATE ('" + date + "', 'mm/dd/yyyy')) and  time_stamp < trunc(TO_DATE ('" + date + "', 'mm/dd/yyyy')+1)";
			}
			

			table = db
					.runSqlQuery(
							"SELECT UCID, SESSION_CONNECTION_ID, VRU_ID, "
									+ "EXTERNAL_SYSTEM_UNIQUE_ID, DNIS, ACCOUNT_ID, ANI, "
									+ "RX_SESSION_ID, LANGUAGE, VERSION, "
									+ "TO_CHAR (TIME_STAMP, 'MM/dd/YYYY HH24:MI:SS.FF4') TIME_STAMP_STR FROM CALL_LOG"
									+ ((whereClause.length() > 0) ? " WHERE"
											+ whereClause : "")
									+ " ORDER BY TIME_STAMP DESC",
							ConfigableDataBase.DB_TIME_OUT);

			return ServiceUtils.populateList(table, CallLogFM.i);
		} catch (DatabaseConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@GET
	@Path("/retrieve/{ucid}")
	public Response getTraceLogXML(@PathParam("ucid") String pUcid,
			@Context final HttpServletRequest request) {
		System.out.println("TraceLog.getTraceLogXML()"
				+ request.getRequestURI());
		ConfigableDataBase db = ConfigableDataBase.i(request);
		try {
			if (pUcid.length() > 0) {
				byte[] result = db
						.runSqlQueryBinaryData("SELECT CALL_RECORD FROM TRACE_LOG WHERE UCID='"
								+ pUcid + "'");
				if (result != null && result.length > 0) {
					// System.out.println(new String(result));
					return Response.ok(new String(result),
							MediaType.APPLICATION_XML).build();
				}
			}
		} catch (DatabaseConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.serverError().build();
	}
}
