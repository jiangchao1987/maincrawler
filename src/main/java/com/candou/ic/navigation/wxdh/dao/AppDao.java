package com.candou.ic.navigation.wxdh.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.candou.ac.rom.bean.RomApp;
import com.candou.db.Database;
import com.candou.ic.navigation.wxdh.vo.App;
import com.candou.util.DateTimeUtil;

public class AppDao {
    private static Logger log = Logger.getLogger(AppDao.class);
    private static String table_Name = "wxdh_app";

    public static void addBatchApps(List<App> apps) {
        try {
            Connection connection = Database.getConnection();
            PreparedStatement ps = connection
                .prepareStatement("insert ignore into "+table_Name+" (id, name, intro, url, f, oc, wsu, detail, dts, cid, cname, icon, icon_name, imc,imc_name, sc,direct_number,created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

            for (App app : apps) {
                String now = DateTimeUtil.nowDateTime();
                log.info(app);

                ps.setInt(1, app.getId());
                ps.setString(2, app.getName());
                ps.setString(3, app.getIntro());
                ps.setString(4, app.getUrl());
                ps.setInt(5, app.getF());
                ps.setString(6, app.getOc());
                ps.setString(7,app.getWsu());
                ps.setString(8, app.getDetail());
                ps.setInt(9, app.getCid());
                ps.setInt(10, app.getDts());
                ps.setString(11, app.getCname());
                ps.setString(12, app.getIcon());
                ps.setString(13, app.getIcon_name());
                ps.setString(14, app.getImc());
                ps.setString(15, app.getImc_name());
                ps.setString(16, app.getSc());
                ps.setString(17, app.getDirect_number());
                ps.setString(18, now);
                ps.setString(19, now);

                ps.executeUpdate();
            }

            ps.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean exists(int appId) {
        Connection connection = null;
        boolean flag = false;
        try {
            connection = Database.getConnection();
            PreparedStatement ps = connection.prepareStatement("select * from "+table_Name+" where id = ?");
            ps.setInt(1, appId);

            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                flag = true;
            }
            resultSet.close();
            ps.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     *
     *
     * @return
     */
    public static List<App> findApps() {
        List<App> apps = new ArrayList<App>();
        try {
            Connection connection = Database.getConnection();
            Statement st = connection.createStatement();
            ResultSet resultSet = st.executeQuery("select id, download_url from "+table_Name+" where isnull(filename)");

            while (resultSet.next()) {
                App app = new App();
                app.setId(resultSet.getInt("app_id"));
                //app.setDownloadUrl(resultSet.getString("download_url"));

                apps.add(app);
            }
            resultSet.close();
            st.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return apps;
    }

    /**
     * 更新app应用
     *
     * @param app
     */
    public static void updateFileName(RomApp app) {
        try {
            Connection connection = Database.getConnection();
            PreparedStatement ps = connection
                .prepareStatement("update tb_app set filename = ?, filemd5 = ?, updated_at = ? where app_id = ?");

            String now = DateTimeUtil.nowDateTime();
            log.info("RomApp [appId=" + app.getAppId() + ", fileName=" + app.getFilename() + ", filemd5=" + app.getFilemd5()
                + "]");

            ps.setString(1, app.getFilename());
            ps.setString(2, app.getFilemd5());
            ps.setString(3, now);
            ps.setInt(4, app.getAppId());
            ps.executeUpdate();

            ps.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateFileName(String filename) {
        try {
            Connection connection = Database.getConnection();
            PreparedStatement ps = connection.prepareStatement("update tb_app set filename = ? where filename like ?");

            ps.setString(1, null);
            ps.setString(2, "%" + filename);
            ps.executeUpdate();

            ps.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<RomApp> findAvailableApps() {
        List<RomApp> apps = new ArrayList<RomApp>();
        try {
            Connection connection = Database.getConnection();
            Statement st = connection.createStatement();
            ResultSet resultSet = st
                .executeQuery("select app_id, download_url, filename, size from tb_app where !isnull(filename)");

            while (resultSet.next()) {
                RomApp app = new RomApp();
                app.setAppId(resultSet.getInt("app_id"));
                app.setDownloadUrl(resultSet.getString("download_url"));
                app.setFilename(resultSet.getString("filename"));
                app.setSize(resultSet.getFloat("size"));

                apps.add(app);
            }
            resultSet.close();
            st.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return apps;
    }

    public static void updateDescription(RomApp app) {
        try {
            Connection connection = Database.getConnection();
            PreparedStatement ps = connection.prepareStatement("update tb_app set description = ? where app_id = ?");

            ps.setString(1, app.getDescription());
            ps.setInt(2, app.getAppId());
            ps.executeUpdate();

            ps.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<RomApp> findAllApps() {
        List<RomApp> apps = new ArrayList<RomApp>();
        try {
            Connection connection = Database.getConnection();
            Statement st = connection.createStatement();
            ResultSet resultSet = st.executeQuery("select app_id, url from tb_app");

            while (resultSet.next()) {
                RomApp app = new RomApp();
                app.setAppId(resultSet.getInt("app_id"));
                app.setDownloadUrl(resultSet.getString("url"));

                apps.add(app);
            }
            resultSet.close();
            st.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return apps;
    }


    public static void updateAppIconUrl(App app){
        try {
             Connection connection = Database.getConnection();
             PreparedStatement preparedStatement = connection
                     .prepareStatement("update tb_app set icon_name=? where id=?");
             preparedStatement.setString(1, app.getIcon_name());
             preparedStatement.setInt(2, app.getId());
             System.out.println(app.getIcon_name()+"|"+app.getId());
             preparedStatement.execute();
             preparedStatement.close();
             connection.close();
         } catch (SQLException e) {
             log.error(e.getMessage());
         }
     }

}
