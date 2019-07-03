package com.ctl.ch.driver.diagnostics;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.servlet.http.HttpServletRequest;

public final class Utils {

	public static String nullToEmpty(String value) {
		return value == null || "null".equals(value) ? EMPTY_STRING : value;
	}

	public static String r(HttpServletRequest request, String name) {
		return nullToEmpty(String.valueOf(request.getParameter(name)));
	}
	
	public static boolean isDev(HttpServletRequest request) {
		try {
			String remoteHost = InetAddress.getByName(request.getRemoteAddr())
					.getCanonicalHostName();
			String[] domain = remoteHost.split("\\.");
			if(domain.length > 0) {
				for (String devMechines : DEV_ACCESS) {
					if( devMechines.equalsIgnoreCase(domain[0]) ) {
						return true;
					}
				}
			}
			return DEV_ACCESS.contains(remoteHost);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return false;
	}

	private final static String EMPTY_STRING = "";
	private final static List<String> DEV_ACCESS;

	static {
		Scanner scanner = new Scanner(Utils.class.getResourceAsStream("/com/ctl/ch/driver/diagnostics/db/dev.access"));
		DEV_ACCESS = new ArrayList<String>();
		while (scanner.hasNext()){
			DEV_ACCESS.add(scanner.next());
		}
		scanner.close();
	}
}
