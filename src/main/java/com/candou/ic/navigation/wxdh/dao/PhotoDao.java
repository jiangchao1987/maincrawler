package com.candou.ic.navigation.wxdh.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.candou.db.Database;
import com.candou.ic.navigation.wxdh.vo.Photo;

public class PhotoDao {
    private static Logger log = Logger.getLogger(PhotoDao.class);

    public static void addBatchPhotos(List<Photo> photos) {
        Connection connection = Database.getConnection();
        try {
            // insert ignore into 自动检查珠主键是否重复
            PreparedStatement ps = connection
                .prepareStatement("insert ignore into wx_photo (wx_id, wx_screen,wx_thumb) VALUES (?, ?, ?)");

            for (Photo photo : photos) {
                log.info(photo);

                ps.setInt(1, photo.getWx_id());
                ps.setString(2, photo.getWx_screen());
                ps.setString(3, photo.getWx_thumb());
                ps.executeUpdate();
            }

            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
