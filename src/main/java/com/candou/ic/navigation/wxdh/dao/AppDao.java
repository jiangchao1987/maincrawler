package com.candou.ic.navigation.wxdh.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.candou.db.Database;
import com.candou.ic.navigation.wxdh.vo.App;
import com.candou.util.DateTimeUtil;

public class AppDao {
    private static String table_Name = "wx_member";

    public static void addBatchApps(List<App> apps) {
        Connection connection = Database.getConnection();
        try {

            PreparedStatement ps = connection
                .prepareStatement("insert ignore into "
                    + table_Name
                    + " (wx_displayname,wx_intro,wx_url,wx_name,wx_detail,wx_category,wx_views,wx_icon,wx_date,wx_qrcode) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

            for (App app : apps) {

                ps.setString(1, app.getWx_displayname());
                ps.setString(2, app.getWx_intro());
                ps.setString(3, app.getWx_url());
                ps.setString(4, app.getWx_name());
                ps.setString(5, app.getWx_detail());
                ps.setString(6, app.getWx_category_name());
                ps.setInt(7, app.getWx_views());
                ps.setString(8, app.getWx_icon());
                String date_dts = app.getWx_date();// 时间戳
                String date = "";
                if (date_dts != null && date_dts.length() != 0) {
                    date = DateTimeUtil.getDate(date_dts);
                    if (date != null && date.length() != 0) {
                        ps.setString(9, date);
                    }
                } else {
                    // 插入当前时间
                    ps.setString(9, DateTimeUtil.nowDateTime());
                }

                ps.setString(10, app.getWx_qrcode());

                try {
                    int result = ps.executeUpdate();
                    System.out.println(result);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 判断app是否已经存在
     *
     * @param applicationId
     *            app ID
     * @return
     */
    public static boolean exists(String displayname) {
        Connection connection = null;
        boolean flag = false;
        String sql_findApp = "select * from " + table_Name + " where wx_displayname = ?";
        try {
            connection = Database.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql_findApp);
            ps.setString(1, displayname);

            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                flag = true;
            }
            resultSet.close();
            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return flag;
    }

}
