package com.ctl.ch.driver.diagnostics.db;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.ctl.ch.driver.diagnostics.Utils;

/**
 * Servlet Filter implementation class ConnectionManager
 */
public class ConnectionManager implements Filter {

    /**
     * Default constructor. 
     */
    public ConnectionManager() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpSession session = httpRequest.getSession();
	    String dbName = Utils.r(httpRequest, ConfigableDataBase.ATTRIBUTE_NAME);
	    if( !"".equals(dbName) ) {
		    session.setAttribute(ConfigableDataBase.ATTRIBUTE_NAME, dbName);
	    } else if ( "".equals(dbName) 
	    		&& session.getAttribute(ConfigableDataBase.ATTRIBUTE_NAME) == null ) {
	    	session.setAttribute(ConfigableDataBase.ATTRIBUTE_NAME, "Dev");
	    }
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
