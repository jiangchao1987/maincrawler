package com.candou.ic.navigation.jcwx.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.candou.db.Database;
import com.candou.ic.navigation.jcwx.bean.Like;

public class LikeDao {

    private static Logger log = Logger.getLogger(LikeDao.class);

    public static void addBatchLikes(List<Like> likes) {
        try {
            String sql = "INSERT IGNORE INTO jcwx_like (like_id,app_id,title,thumbnail) VALUES (?,?,?,?)";
            Connection connection = Database.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);

            for (Like Like : likes) {

                log.info(Like);

                ps.setInt(1, Like.getLikeId());
                ps.setInt(2, Like.getAppId());
                ps.setString(3, Like.getTitle());
                ps.setString(4, Like.getThumbnail());
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
