package com.candou.ic.market.pp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.candou.db.Database;
import com.candou.util.DateTimeUtil;

public class AppDao {

	public static void updateAppPrice(int applicationId, String priceTrend, float lastPrice, float currentPrice) {
        try {
            Connection connection = Database.getConnection();
            PreparedStatement ps = connection.prepareStatement("update tb_application set last_price = ?, current_price = ?, price_trend = ?, updated_at = ?, expire_datetime = ? where application_id = ?");
            ps.setFloat(1, lastPrice);
            ps.setFloat(2, currentPrice);
            ps.setString(3, priceTrend);
            ps.setString(4, DateTimeUtil.nowDateTime());
            ps.setString(5, DateTimeUtil.getExpireDate());
            ps.setInt(6, applicationId);

            ps.executeUpdate();
            ps.close();
            connection.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
}
