package com.candou.ic.navigation.jcwx.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.candou.db.Database;
import com.candou.ic.navigation.jcwx.bean.Article;

public class ArticleDao {

    private static Logger log = Logger.getLogger(ArticleDao.class);

    public static void addBatchArticles(List<Article> articles) {
        try {
            String sql = "INSERT IGNORE INTO jcwx_app (app_id,app_name,views,likes,wxh,wxqr,content,first_category_id,first_category_name,second_category_name,thumbnail,created_at,updated_at) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
            Connection connection = Database.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);

            for (Article article : articles) {
                log.info(article);

                ps.setInt(1, article.getAppId());
                ps.setString(2, article.getName());
                ps.setInt(3, article.getViews());
                ps.setInt(4, article.getLike());
                ps.setString(5, article.getWxh());
                ps.setString(6, article.getWxqr());
                ps.setString(7, article.getContent());
                ps.setInt(8, article.getFirstCid());
                ps.setString(9, article.getFirstCname());
                ps.setString(10, article.getSecondCname());
                ps.setString(11, article.getThumbnail());
                ps.setString(12, article.getCreatedAt());
                ps.setString(13, article.getUpdatedAt());

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
