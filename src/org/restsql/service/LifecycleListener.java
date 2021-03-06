/* Copyright (c) restSQL Project Contributors. Licensed under MIT. */
package org.restsql.service;

import java.sql.SQLException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.restsql.core.Config;
import org.restsql.core.Factory;

/**
 * Loads properties file from servlet context.
 */
public class LifecycleListener implements ServletContextListener {

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent event) {
    	String value = event.getServletContext().getInitParameter(Config.KEY_RESTSQL_PROPERTIES);
    	if (value == null) {
    		value = System.getProperty(Config.KEY_RESTSQL_PROPERTIES, Config.DEFAULT_RESTSQL_PROPERTIES);
    	}
		System.out.println("Loading restsql properties from " + value);
    	System.setProperty(Config.KEY_RESTSQL_PROPERTIES, value);
    	Config.loadAllProperties();
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0) {
    	try {
			Factory.getConnectionFactory().destroy();
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
    }
	
}
