package com.candou.ic.market.pp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.candou.db.Database;
import com.candou.ic.market.pp.bean.Price;
import com.candou.util.DateTimeUtil;

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
