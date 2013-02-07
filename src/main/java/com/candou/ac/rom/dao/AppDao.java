package com.candou.ac.rom.dao;

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
import com.candou.util.DateTimeUtil;

public class AppDao {
	private static Logger log = Logger.getLogger(RomAppDao.class);

	public static void addBatchApps(List<RomApp> apps) {
		try {
			Connection connection = Database.getConnection();
			PreparedStatement ps = connection
					.prepareStatement("insert ignore into tb_app (app_id, app_name, author, fit_type, size, release_date, rom_type, star, description, icon_url, url, download_url, category_id, category_name, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

			for (RomApp app : apps) {
				String now = DateTimeUtil.nowDateTime();
				log.info(app);

				ps.setInt(1, app.getAppId());
				ps.setString(2, app.getAppName());
				ps.setString(3, app.getAuthor());
				ps.setString(4, app.getFitType());
				ps.setFloat(5, app.getSize());
				ps.setString(6, app.getReleaseDate());
				ps.setString(7, app.getRomType());
				ps.setFloat(8, app.getStar());
				ps.setString(9, app.getDescription());
				ps.setString(10, app.getIconUrl());
				ps.setString(11, app.getUrl());
				ps.setString(12, app.getDownloadUrl());
				ps.setInt(13, app.getCategoryId());
				ps.setString(14, app.getCategoryName());
				ps.setString(15, now);
				ps.setString(16, now);

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
			PreparedStatement ps = connection
					.prepareStatement("select * from tb_app where app_id = ?");
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

	public static List<RomApp> findApps() {
		List<RomApp> apps = new ArrayList<RomApp>();
		try {
			Connection connection = Database.getConnection();
			Statement st = connection.createStatement();
			ResultSet resultSet = st
					.executeQuery("select app_id, download_url from tb_app where isnull(filename)");

			while (resultSet.next()) {
				RomApp app = new RomApp();
				app.setAppId(resultSet.getInt("app_id"));
				app.setDownloadUrl(resultSet.getString("download_url"));

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

	public static void updateFileName(RomApp app) {
		try {
			Connection connection = Database.getConnection();
			PreparedStatement ps = connection
					.prepareStatement("update tb_app set filename = ?, filemd5 = ?, updated_at = ? where app_id = ?");

			String now = DateTimeUtil.nowDateTime();
			log.info("RomApp [appId=" + app.getAppId() + ", fileName="
					+ app.getFilename() + ", filemd5=" + app.getFilemd5() + "]");

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
			PreparedStatement ps = connection
					.prepareStatement("update tb_app set filename = ? where filename like ?");

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
			PreparedStatement ps = connection
					.prepareStatement("update tb_app set description = ? where app_id = ?");

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
			ResultSet resultSet = st
					.executeQuery("select app_id, url from tb_app");

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
	
}
