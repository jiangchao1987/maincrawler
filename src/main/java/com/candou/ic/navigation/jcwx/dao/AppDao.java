package com.candou.ic.navigation.jcwx.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.candou.db.Database;
import com.candou.ic.navigation.jcwx.bean.App;
import com.candou.util.DateTimeUtil;

public class AppDao {
    private static Logger log = Logger.getLogger(AppDao.class);

    public static void addBatchApps(List<App> apps) {
        try {
            String sql = "INSERT IGNORE INTO jcwx_app (app_id,app_name,views,likes,wxh,wxqr,content,first_category_id,first_category_name,second_category_name,thumbnail,created_at,updated_at) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
            Connection connection = Database.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);

            for (App app : apps) {
                String now = DateTimeUtil.nowDateTime();
                log.info(app);

                ps.setInt(1, app.getApp_id());
                ps.setString(2, app.getName());
                ps.setInt(3, app.getViews());
                ps.setInt(4, app.getLike());
                ps.setString(5, app.getWxh());
                ps.setString(6, app.getWxqr());
                ps.setString(7, app.getContent());
                ps.setInt(8, app.getFirstCid());
                ps.setString(9, app.getFirstCname());
                ps.setString(10, app.getSecondCname());
                ps.setString(11, app.getThumbnail());
                ps.setString(12, app.getCreatedAt());
                ps.setString(13, app.getUpdatedAt());

                ps.executeUpdate();
            }

            ps.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }
}
