package com.candou.ic.navigation.wxdh.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.candou.db.Database;
import com.candou.ic.navigation.wxdh.vo.Category;

public class CategoryDao {
    private static Logger log = Logger.getLogger(CategoryDao.class);

    public static List<Category> findAllCategortys() {
        List<Category> apps = new ArrayList<Category>();
        try {
            Connection connection = Database.getConnection();
            Statement st = connection.createStatement();
            ResultSet resultSet = st.executeQuery("select *  from wxdh_category ");

            while (resultSet.next()) {
                Category cat = new Category();
                cat.setCid(resultSet.getInt("cid"));
                cat.setCname(resultSet.getString("cname"));

                apps.add(cat);
            }
            resultSet.close();
            st.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return apps;
    }

}
