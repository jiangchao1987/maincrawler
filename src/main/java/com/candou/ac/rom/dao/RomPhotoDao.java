package com.candou.ac.rom.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.candou.ac.rom.bean.RomPhoto;
import com.candou.db.Database;
import com.candou.util.DateTimeUtil;

public class RomPhotoDao {
    private static Logger log = Logger.getLogger(RomPhotoDao.class);

    public static void addBatchPhotos(List<RomPhoto> photos) {
        try {
            Connection connection = Database.getConnection();
            PreparedStatement ps = connection.prepareStatement("insert ignore into tb_photo (app_id, original_url, created_at, updated_at) VALUES (?, ?, ?, ?)");

            for (RomPhoto photo : photos) {
                String now = DateTimeUtil.nowDateTime();
                log.info(photo);

                ps.setInt(1, photo.getAppId());
                ps.setString(2, photo.getOriginalUrl());
                ps.setString(3, now);
                ps.setString(4, now);

                ps.executeUpdate();
            }

            ps.close();
            connection.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }
}
