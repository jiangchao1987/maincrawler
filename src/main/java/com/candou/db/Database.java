package com.candou.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.candou.conf.Configure;

/**
 * Database Class
 *
 * @author wenmingTang
 */
public class Database {
	private static String linkUri;
	private static Connection connection;

	private static Logger log = Logger.getLogger(Database.class);

	static {
		linkUri = String.format("jdbc:mysql://%s/%s?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&autoReconnect=true", Configure.getProperty("db_host"), Configure.getProperty("db_name"));
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
	}

	/**
	 * Get connection
	 *
	 * @return
	 */
	public static Connection getConnection() {
		do {
			try {
				connection = DriverManager.getConnection(linkUri, Configure.getProperty("db_user"), Configure.getProperty("db_password"));
			} catch (SQLException e) {
				e.printStackTrace();
				log.error(e.getMessage() + ", retry after 5 seconds.");
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e1) {
					// ignore
				}
			}
		} while (connection == null);

		return connection;
	}
}
