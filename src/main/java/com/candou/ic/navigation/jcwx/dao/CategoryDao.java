package com.candou.ic.navigation.jcwx.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.candou.db.Database;
import com.candou.ic.navigation.jcwx.bean.Category;

public class CategoryDao {

    private static Logger log = Logger.getLogger(CategoryDao.class);

    public static void addBatchCategory(List<Category> categories) {
        try {
            Connection connection = Database.getConnection();
            PreparedStatement ps = connection.prepareStatement("insert ignore into jcwx_category (cid, cname) VALUES (?, ?)");

            for (Category category : categories) {
                ps.setInt(1, category.getCid());
                ps.setString(2, category.getCname());

                ps.addBatch();
            }
            ps.executeBatch();
            ps.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }

}
