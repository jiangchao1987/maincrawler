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
        try {
            Connection connection = Database.getConnection();
            //insert ignore into  自动检查珠主键是否重复
            PreparedStatement ps = connection.prepareStatement("insert ignore into wxdh_photo (appid, photo_url,type) VALUES (?, ?, ?)");

            for (Photo photo : photos) {
                log.info(photo);

                ps.setInt(1, photo.getAppid());
                ps.setString(2, photo.getPhoto_url());
                ps.setInt(3, photo.getType());
                ps.executeUpdate();
            }

            ps.close();
            connection.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void UpdatePhotoNames(Photo photo) {
        try {
            Connection connection = Database.getConnection();
            PreparedStatement preparedStatement = connection
                    .prepareStatement("update wxdh_photo set filename=? where pid=?");

            preparedStatement.setString(1, photo.getFilename());
            preparedStatement.setInt(2, photo.getId());
            preparedStatement.execute();
            connection.close();

        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }
}
