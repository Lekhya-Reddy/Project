package com.ctl.ch.driver.diagnostics;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.ctl.ch.common.coreutilities.DataTable;
import com.ctl.ch.common.databaseconnection.DatabaseConnectionException;
import com.ctl.ch.driver.diagnostics.db.ConfigableDataBase;
import com.ctl.ch.driver.diagnostics.service.OSDMLogFM;
import com.ctl.ch.driver.diagnostics.service.ServiceUtils;

@Path("/log/osdm")
public class OSDMLog {
	@GET
	@Path("/{ucid}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<com.ctl.ch.driver.diagnostics.vo.OSDMLog> getOSDMLog(
			@Context final HttpServletRequest request,
			@PathParam("ucid") String ucid) {
		//HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpSession session = request.getSession();
		String dbname = (String)session.getAttribute(ConfigableDataBase.ATTRIBUTE_NAME);
		ConfigableDataBase db = ConfigableDataBase.i(request);
		DataTable table;
		
		try {
			/*if(dbname!=null&&"Prod".equalsIgnoreCase(dbname)){
				table = db
						.runSqlQuery(
								"SELECT UCID, STATE, RETURN_VALUE, RETURN_CODE, NUMBER_NO_INPUTS,"
										+ " NUMBER_INVALIDS, INPUT_MODE, FAILURE_REASON, CREDIT_CARD_TYPE, CONFIDENCE_SCORE,"
										+ " NO_TO_CONFIRM, REPEAT_COUNT, HELP_COUNT, TO_CHAR (TIME_STAMP, 'MM/dd/YYYY HH24:MI:SS.FF4') TIME_STAMP_STR"
										+ " FROM OSDM_LOG WHERE UCID = '" + ucid
										+ "' ORDER BY TIME_STAMP ASC",
								ConfigableDataBase.DB_TIME_OUT);
			}
			else{*/
				table = db
						.runSqlQuery(
								"SELECT UCID, STATE, RETURN_VALUE, RETURN_CODE, NUMBER_NO_INPUTS,"
										+ " NUMBER_INVALIDS, INPUT_MODE, FAILURE_REASON, CREDIT_CARD_TYPE, CONFIDENCE_SCORE,"
										+ " NO_TO_CONFIRM, REPEAT_COUNT, HELP_COUNT, TO_CHAR (TIME_STAMP, 'MM/dd/YYYY HH24:MI:SS.FF4') TIME_STAMP_STR,SEMANTIC_ID,SSM_SCORE,UTTERANCE"
										+ " FROM OSDM_LOG WHERE UCID = '" + ucid
										+ "' ORDER BY TIME_STAMP ASC",
								ConfigableDataBase.DB_TIME_OUT);
		//	}
			

			return ServiceUtils.populateList(table, OSDMLogFM.i);
		} catch (DatabaseConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
}
