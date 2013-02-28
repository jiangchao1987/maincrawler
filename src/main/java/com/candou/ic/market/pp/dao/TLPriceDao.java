package com.candou.ic.market.pp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.candou.db.Database;
import com.candou.ic.market.pp.bean.Price;
import com.candou.util.DateTimeUtil;

/*
 * CREATE TABLE `timeline_price` (
  `price_id` int(11) NOT NULL AUTO_INCREMENT,
  `application_id` int(11) NOT NULL,
  `current_price` float(6,2) NOT NULL,
  `last_price` float(6,2) NOT NULL,
  `price_trend` char(10) NOT NULL,
  `created_at` datetime NOT NULL,
  PRIMARY KEY (`price_id`),
  KEY `application_id` (`application_id`),
  KEY `price_trend` (`price_trend`),
  KEY `created_at` (`created_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 */
public class TLPriceDao {
	private static Logger log = Logger.getLogger(TLPriceDao.class);

	public static void addPrice(Price price) {
		try {
			Connection connection = Database.getConnection();
			PreparedStatement ps = connection
					.prepareStatement("INSERT IGNORE INTO timeline_price (application_id, current_price, last_price, price_trend, created_at) VALUES (?, ?, ?, ?, ?)");

			log.info("price add: " + price);

			String now = DateTimeUtil.nowDateTime();

			ps.setInt(1, price.getApplicationId());
			ps.setFloat(2, price.getCurrentPrice());
			ps.setFloat(3, price.getLastPrice());
			ps.setString(4, price.getPriceTrend());
			ps.setString(5, now);

			ps.executeUpdate();

			ps.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void addBatchPrices(List<Price> prices) {
		try {
			Connection connection = Database.getConnection();
			PreparedStatement ps = connection
					.prepareStatement("INSERT IGNORE INTO timeline_price (application_id, current_price, last_price, price_trend, created_at) VALUES (?, ?, ?, ?, ?)");

			for (Price price : prices) {
				log.info("batch price add: " + price);

				String now = DateTimeUtil.nowDateTime();

				ps.setInt(1, price.getApplicationId());
				ps.setFloat(2, price.getCurrentPrice());
				ps.setFloat(3, price.getLastPrice());
				ps.setString(4, price.getPriceTrend());
				ps.setString(5, now);

				ps.executeUpdate();
			}

			ps.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
