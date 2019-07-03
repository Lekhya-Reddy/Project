package com.ctl.ch.driver.diagnostics.db;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.ctl.ch.common.databaseconnection.DatabaseConnectionException;
import com.ctl.ch.common.databaseconnection.OracleDatabase;

public class ConfigableDataBase extends OracleDatabase {
	
	public static class DBSettings implements Serializable {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1283598746205410208L;
		public DBSettings(String name, Properties properties) {
			String configName = "db." + name.toLowerCase().replace(' ', '_') + ".";
			this.name = name;
			this.uri = properties.getProperty(configName + "uri");
			this.user = properties.getProperty(configName + "user");
			this.password = properties.getProperty(configName + "password");
		}
		
		public DBSettings(String name, String uri, String user, String password) {
			this.name = name;
			this.uri = uri;
			this.user = user;
			this.password = password;
		}
		
		public String getUri() {
			return uri;
		}

		public void setUri(String uri) {
			this.uri = uri;
		}

		public String getUser() {
			return user;
		}

		public void setUser(String user) {
			this.user = user;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		@Override
		public String toString() {
			return this.name;
		}
		
		private String uri;
		private String user;
		private String password;
		private String name;
	}

	private String uri = "ocdnt16kcl-scan.test.intranet:1521/gendb01t_failover";
	private String username = "DRIVRD001";
	private String password = "ivrdev01";
	private DBSettings settings; 
	public static final String DB_TIME_OUT = "3000";
	
	public ConfigableDataBase(DBSettings dbSettings) {
		super();
		this.settings = dbSettings;
		this.setParameters(new Hashtable<String, String>());
	}
	
	@Override
	protected Connection connect() throws DatabaseConnectionException {
		// TODO Auto-generated method stub
		try {
			
	        try {
	            Class.forName("oracle.jdbc.driver.OracleDriver");
	        } catch (ClassNotFoundException e) {
				throw new DatabaseConnectionException("Unable to load Oracle Driver",
						e, 8006);			
	        }
			
	        //?username, password
	        return DriverManager.getConnection(
					"jdbc:oracle:thin:@" + this.settings.getUri(), this.settings.getUser(),
					this.settings.getPassword());
			        
		} catch (SQLException e) {
			throw new DatabaseConnectionException("Exception opening database",
					e, 8005,
					"ConnectionString=\""+getLoggingConnectionString()+"\"");
		}
	}
	
	public static ConfigableDataBase i(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		return i((String) session.getAttribute(ATTRIBUTE_NAME));
	}
	
	public static ConfigableDataBase i(String prop) {
		return new ConfigableDataBase(new DBSettings(prop, dbConfig));
	}
	
	public byte[] runSqlQueryBinaryData(String pQueryString) throws DatabaseConnectionException
	{
		StringBuilder context = new StringBuilder();
		context.append(String.format("ConnectionString=\"{0}\"\n",getLoggingConnectionString()));

		int timeout = 3000; //Integer.parseInt(pTimeoutValueSeconds);	
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection connection = null;
		try {
			context.append("RunSqlQuery: pTimeoutValueMilliseconds = "
					+ timeout
					+ " pQueryString = ["
					+ pQueryString
					+ "]");

			connection = connect();
			
			ps = connection.prepareStatement(pQueryString);
			ps.setQueryTimeout(timeout);
			rs = ps.executeQuery();
			while(rs.next()){
	
				InputStream is = rs.getBinaryStream(1);
				
				ByteArrayOutputStream os = new ByteArrayOutputStream();
				byte[] b = new byte[1024];
				
				try {
					do {
						int len = is.read(b);
						os.write(b, 0, len);
					} while(is.available() > 0);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		

				return os.toByteArray(); 
			}
			
		} catch (SQLException se) {
			DatabaseConnectionException exception =  new DatabaseConnectionException("Exception running RunSqlQueryBinaryData",
					se,
					(int)8003,
					context.toString());
				throw exception;
		}
		finally {
			disconnect(connection, ps, rs);
		}
		return null;
		
	}
	
	public static final Properties dbConfig = new Properties();
	static {
		try {
			dbConfig.load(ConfigableDataBase.class.getResourceAsStream("db.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static final String ATTRIBUTE_NAME = "DB_CONNECTION";
}
