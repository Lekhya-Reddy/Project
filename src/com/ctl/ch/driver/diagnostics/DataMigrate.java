package com.ctl.ch.driver.diagnostics;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;

import com.ctl.ch.common.coreutilities.DataRow;
import com.ctl.ch.common.coreutilities.DataTable;
import com.ctl.ch.common.databaseconnection.DatabaseConnectionException;
import com.ctl.ch.common.databaseconnection.IDatabase;
import com.ctl.ch.driver.diagnostics.db.ConfigableDataBase;

public class DataMigrate {

	public HashMap<String, Long> compareDIALSTables(String src, String dst) {
		ConfigableDataBase srcDb = ConfigableDataBase.i(src);
		ConfigableDataBase dstDb = ConfigableDataBase.i(dst);
		HashMap<String, Long> result = new HashMap<String, Long>();
		DataTable tableNames;
		try {
			tableNames = srcDb
					.runSqlQuery(
							"SELECT TABLE_NAME FROM ALL_TABLES WHERE TABLE_NAME LIKE 'DIALS%'",
							ConfigableDataBase.DB_TIME_OUT);
			for (DataRow<?, ?> row : tableNames.getRows()) {
				String tableName = String.valueOf(row.get("TABLE_NAME"));
				long sourceCount = rowCount(tableName, srcDb);
				long destCount = rowCount(tableName, dstDb);
				System.out.println("TABLE <> " + tableName);
				if (sourceCount > -1 && destCount > -1) {
					result.put(tableName, (sourceCount - destCount));
				} else {
					result.put(tableName, -99l);
				}
			}
		} catch (DatabaseConnectionException e) {
			e.printStackTrace();
		}
		return result;
	}

	private long rowCount(String tableName, IDatabase db) {
		try {
			DataTable result = db.runSqlQuery("SELECT count(*) AS TOTAL FROM "
					+ tableName, ConfigableDataBase.DB_TIME_OUT);
			return Long.parseLong(String.valueOf(result.getRows().get(0)
					.get("TOTAL")));
		} catch (Exception e) {
			System.out.println(e.getMessage());
			// e.printStackTrace();
		}
		return -1;
	}

	public static void main(String[] args) throws UnknownHostException {
		// System.out.println((new DataMigrate()).compareDIALSTables("Test1",
		// "Test3"));
		System.out.println(InetAddress.getByName("10.1.211.108")
				.getCanonicalHostName());
	}
}
