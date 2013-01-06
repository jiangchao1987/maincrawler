package com.candou.ic.rank.repair.main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.candou.ic.rank.repair.bean.AppRank;
import com.candou.ic.rank.repair.dao.MongoDBRank;
import com.candou.ic.rank.repair.dao.MysqlDBRank;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class MigrateMain {
	private static Logger log = Logger.getLogger(MigrateMain.class);
	private static String[] days = { "2013-01-01", "2013-01-02", "2013-01-03", "2013-01-04", "2013-01-05" };

	public static void main(String[] args) {
		for (String day1 : days) {
			day1 = day1 + " 00:00:00";
			String day = datestrToTimestamp(day1);
			log.info(String.format("current day: %s[%s]", day, day1));
			// all
			List<AppRank> ranksFreeAll = getRanksFreeAllFromMongo(day); // from
			saveRanksFreeAllToMysql(ranksFreeAll); // to
			List<AppRank> ranksGrossingAll = getRanksGrossingAllFromMongo(day);
			saveRanksGrossingAllToMysql(ranksGrossingAll);
			List<AppRank> ranksPaidAll = getRanksPaidAllFromMongo(day);
			saveRanksPaidAllToMysql(ranksPaidAll);

			// category
			for (int index = 0; index < 10; index++) {
				List<AppRank> ranksFreeCategory = getRanksFreeCategoryFromMongo(day, index);
				saveRanksFreeCategoryToMysql(ranksFreeCategory, index);
			}
			for (int index = 0; index < 10; index++) {
				List<AppRank> ranksGrossingCategory = getRanksGrossingCategoryFromMongo(day, index);
				saveRanksGrossingCategoryToMysql(ranksGrossingCategory, index);
			}
			for (int index = 0; index < 10; index++) {
				List<AppRank> ranksPaidCategory = getRanksPaidCategoryFromMongo(day, index);
				saveRanksPaidCategoryToMysql(ranksPaidCategory, index);
			}
		}
	}

	// --------------------------------------------get---------------------------------------------------

	private static List<AppRank> getRanksFreeAllFromMongo(String day) {
		List<AppRank> list = new ArrayList<AppRank>();
		DBCollection coll = MongoDBRank.getCollection("db_iphone_rank", "tb_rank_free_all");
		BasicDBObject query = new BasicDBObject();
		query.put("c_at", day);
		DBCursor cur = coll.find(query);

		while (cur.hasNext()) {
			DBObject obj = cur.next();
			AppRank rank = new AppRank();
			rank.setId((Integer) obj.get("a_id"));
			rank.setRank((Integer) obj.get("rk"));
			rank.setCreatedAt(timestampToDatestr((String) obj.get("c_at")));
			list.add(rank);
		}
		return list;
	}

	private static List<AppRank> getRanksGrossingAllFromMongo(String day) {
		List<AppRank> list = new ArrayList<AppRank>();
		DBCollection coll = MongoDBRank.getCollection("db_iphone_rank", "tb_rank_grossing_all");
		BasicDBObject query = new BasicDBObject();
		query.put("c_at", day);
		DBCursor cur = coll.find(query);

		while (cur.hasNext()) {
			DBObject obj = cur.next();
			AppRank rank = new AppRank();
			rank.setId((Integer) obj.get("a_id"));
			rank.setRank((Integer) obj.get("rk"));
			rank.setCreatedAt(timestampToDatestr((String) obj.get("c_at")));
			list.add(rank);
		}
		return list;
	}

	private static List<AppRank> getRanksPaidAllFromMongo(String day) {
		List<AppRank> list = new ArrayList<AppRank>();
		DBCollection coll = MongoDBRank.getCollection("db_iphone_rank", "tb_rank_paid_all");
		BasicDBObject query = new BasicDBObject();
		query.put("c_at", day);
		DBCursor cur = coll.find(query);

		while (cur.hasNext()) {
			DBObject obj = cur.next();
			AppRank rank = new AppRank();
			rank.setId((Integer) obj.get("a_id"));
			rank.setRank((Integer) obj.get("rk"));
			rank.setCreatedAt(timestampToDatestr((String) obj.get("c_at")));
			list.add(rank);
		}
		return list;
	}

	private static List<AppRank> getRanksFreeCategoryFromMongo(String day, int index) {
		List<AppRank> list = new ArrayList<AppRank>();
		DBCollection coll = MongoDBRank.getCollection("db_iphone_rank", "tb_rank_free_category_" + index);
		BasicDBObject query = new BasicDBObject();
		query.put("c_at", day);
		DBCursor cur = coll.find(query);

		while (cur.hasNext()) {
			DBObject obj = cur.next();
			AppRank rank = new AppRank();
			rank.setId((Integer) obj.get("a_id"));
			rank.setRank((Integer) obj.get("rk"));
			rank.setCategoryId((Integer) obj.get("c_id"));
			rank.setCreatedAt(timestampToDatestr((String) obj.get("c_at")));
			list.add(rank);
		}
		return list;
	}

	private static List<AppRank> getRanksGrossingCategoryFromMongo(String day, int index) {
		List<AppRank> list = new ArrayList<AppRank>();
		DBCollection coll = MongoDBRank.getCollection("db_iphone_rank", "tb_rank_grossing_category_" + index);
		BasicDBObject query = new BasicDBObject();
		query.put("c_at", day);
		DBCursor cur = coll.find(query);

		while (cur.hasNext()) {
			DBObject obj = cur.next();
			AppRank rank = new AppRank();
			rank.setId((Integer) obj.get("a_id"));
			rank.setRank((Integer) obj.get("rk"));
			rank.setCategoryId((Integer) obj.get("c_id"));
			rank.setCreatedAt(timestampToDatestr((String) obj.get("c_at")));
			list.add(rank);
		}
		return list;
	}

	private static List<AppRank> getRanksPaidCategoryFromMongo(String day, int index) {
		List<AppRank> list = new ArrayList<AppRank>();
		DBCollection coll = MongoDBRank.getCollection("db_iphone_rank", "tb_rank_paid_category_" + index);
		BasicDBObject query = new BasicDBObject();
		query.put("c_at", day);
		DBCursor cur = coll.find(query);

		while (cur.hasNext()) {
			DBObject obj = cur.next();
			AppRank rank = new AppRank();
			rank.setId((Integer) obj.get("a_id"));
			rank.setRank((Integer) obj.get("rk"));
			rank.setCategoryId((Integer) obj.get("c_id"));
			rank.setCreatedAt(timestampToDatestr((String) obj.get("c_at")));
			list.add(rank);
		}
		return list;
	}

	// --------------------------------------------------save---------------------------------------------------------

	public static void saveRanksFreeAllToMysql(List<AppRank> list) {
		try {
			Connection connection = MysqlDBRank.getConnection();
			PreparedStatement ps = connection
					.prepareStatement("INSERT IGNORE INTO tb_rank_free_all (application_id, rank, created_at) VALUES (?, ?, ?)");

			for (AppRank app : list) {
				log.info(app);

				ps.setInt(1, app.getId());
				ps.setInt(2, app.getRank());
				ps.setString(3, app.getCreatedAt());

				ps.executeUpdate();
			}

			ps.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void saveRanksGrossingAllToMysql(List<AppRank> list) {
		try {
			Connection connection = MysqlDBRank.getConnection();
			PreparedStatement ps = connection
					.prepareStatement("INSERT IGNORE INTO tb_rank_grossing_all (application_id, rank, created_at) VALUES (?, ?, ?)");

			for (AppRank app : list) {
				log.info(app);

				ps.setInt(1, app.getId());
				ps.setInt(2, app.getRank());
				ps.setString(3, app.getCreatedAt());

				ps.executeUpdate();
			}

			ps.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void saveRanksPaidAllToMysql(List<AppRank> list) {
		try {
			Connection connection = MysqlDBRank.getConnection();
			PreparedStatement ps = connection
					.prepareStatement("INSERT IGNORE INTO tb_rank_paid_all (application_id, rank, created_at) VALUES (?, ?, ?)");

			for (AppRank app : list) {
				log.info(app);

				ps.setInt(1, app.getId());
				ps.setInt(2, app.getRank());
				ps.setString(3, app.getCreatedAt());

				ps.executeUpdate();
			}

			ps.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void saveRanksFreeCategoryToMysql(List<AppRank> list, int index) {
		try {
			Connection connection = MysqlDBRank.getConnection();
			PreparedStatement ps = connection.prepareStatement("INSERT IGNORE INTO tb_rank_free_category_" + index
					+ " (application_id, rank, category_id, created_at) VALUES (?, ?, ?, ?)");

			for (AppRank app : list) {
				log.info(app);

				ps.setInt(1, app.getId());
				ps.setInt(2, app.getRank());
				ps.setInt(3, app.getCategoryId());
				ps.setString(4, app.getCreatedAt());

				ps.executeUpdate();
			}

			ps.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void saveRanksGrossingCategoryToMysql(List<AppRank> list, int index) {
		try {
			Connection connection = MysqlDBRank.getConnection();
			PreparedStatement ps = connection.prepareStatement("INSERT IGNORE INTO tb_rank_grossing_category_" + index
					+ " (application_id, rank, category_id, created_at) VALUES (?, ?, ?, ?)");

			for (AppRank app : list) {
				log.info(app);

				ps.setInt(1, app.getId());
				ps.setInt(2, app.getRank());
				ps.setInt(3, app.getCategoryId());
				ps.setString(4, app.getCreatedAt());

				ps.executeUpdate();
			}

			ps.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void saveRanksPaidCategoryToMysql(List<AppRank> list, int index) {
		try {
			Connection connection = MysqlDBRank.getConnection();
			PreparedStatement ps = connection.prepareStatement("INSERT IGNORE INTO tb_rank_paid_category_" + index
					+ " (application_id, rank, category_id, created_at) VALUES (?, ?, ?, ?)");

			for (AppRank app : list) {
				log.info(app);

				ps.setInt(1, app.getId());
				ps.setInt(2, app.getRank());
				ps.setInt(3, app.getCategoryId());
				ps.setString(4, app.getCreatedAt());

				ps.executeUpdate();
			}

			ps.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static String timestampToDatestr(String timestamp) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Timestamp now = new Timestamp(Long.valueOf(timestamp));
		return df.format(now);
	}

	public static String datestrToTimestamp(String old) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = df.parse(old);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return String.valueOf(date.getTime());
	}

}
