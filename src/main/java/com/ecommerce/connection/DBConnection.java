package com.ecommerce.connection;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/*
 * public class DBConnection { private static final String URL =
 * "jdbc:mysql://localhost:3306/ecommerce_jdbc"; private static final String
 * USERNAME = "root"; private static final String PASSWORD = "password";
 * 
 * public static Connection getConnection() throws SQLException { return
 * DriverManager.getConnection(URL, USERNAME, PASSWORD); } }
 */

public class DBConnection {
	private static final Properties props = new Properties();
	
	static {
		try (InputStream in = DBConnection.class.getClassLoader().getResourceAsStream("application.properties")) {
			if (in == null) {
				throw new IllegalStateException("db.properties not found on classpath");				
			}
			props.load(in);
		} catch (IOException e) {
			throw new ExceptionInInitializerError();
		}
	}
	
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(
				props.getProperty("db.url"), // -> URL
				props.getProperty("db.username"), // -> USERNAME
				props.getProperty("db.password")); // -> PASSWORD
	}
}