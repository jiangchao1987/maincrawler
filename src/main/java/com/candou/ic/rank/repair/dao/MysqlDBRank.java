package com.candou.ic.rank.repair.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;

public class MysqlDBRank {
	private static String linkUri;
	private static Connection connection;

	private static Logger log = Logger.getLogger(MysqlDBRank.class);

	static {
		linkUri = String.format("jdbc:mysql://%s/%s?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull", "192.168.1.101", "db_iphone_rank");
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
	}

	public static Connection getConnection() {
		do {
			try {
				connection = DriverManager.getConnection(linkUri, "java", "candou");
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
