package com.candou.ic.market.pp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.candou.db.Database;
import com.candou.ic.market.pp.bean.Job;
import com.candou.ic.market.pp.bean.Price;
import com.candou.util.DateTimeUtil;

public class JobDao {
	private static Logger log = Logger.getLogger(JobDao.class);
	
	public static boolean exists(Job sourceJob) {
		Connection connection = null;
		boolean flag = false;
		try {
			connection = Database.getConnection();
			PreparedStatement ps = connection.prepareStatement("select * from tb_job where id = ?");
			ps.setInt(1, sourceJob.getId());

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
	
	public static void addBatchJobs(List<Job> jobs) {

		try {
			Connection connection = Database.getConnection();
			PreparedStatement ps = connection.prepareStatement("INSERT IGNORE INTO tb_job (id, name, url, created_at, updated_at, category_id, category_name) VALUES (?, ?, ?, ?, ?, ?, ?)");

			for (Job job : jobs) {
				log.info("batch add: " + job);

				String now = DateTimeUtil.nowDateTime();

				ps.setInt(1, job.getId());
				ps.setString(2, job.getName());
				ps.setString(3, job.getUrl());
				ps.setString(4, now);
				ps.setString(5, now);
				ps.setInt(6, job.getCategoryID());
				ps.setString(7, job.getCategoryName());

				ps.executeUpdate();
			}

			ps.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized static void batchUpdate(List<Job> jobs) {
		try {
			Connection connection = Database.getConnection();

			for (Job job : jobs) {
				PreparedStatement ps = connection.prepareStatement("select * from tb_application where application_id = ?");

				ps.setInt(1, job.getId());
				ResultSet resultSet = ps.executeQuery();

				if (resultSet.next()) {
					int applicationId = resultSet.getInt("application_id");

					String lastPriceTrend = resultSet.getString("price_trend");
					float lastPrice = resultSet.getFloat("current_price");
					float oldPrice = resultSet.getFloat("last_price");
					float currentPrice = job.getPrice();
					String version = resultSet.getString("current_version");

					log.info(String.format("app %d [original price: %.2f, price：%.2f, trend: %s], [current price: %.2f]", job.getId(), oldPrice, lastPrice, lastPriceTrend, currentPrice));

					if (currentPrice == lastPrice) {
						log.info(String.format("app %d 价格不需要更新", job.getId()));
						
						// 更新 应用 信息
						if (version.equals(job.getVersion())) {
							continue;
						}
						
						log.info(String.format("添加更新任务: [%d][%s]", job.getId(), job.getName()));
						addUpdateJob(job);
						continue;
					}

					String priceTrend = "unknow";
					if ((currentPrice == 0 && oldPrice == 0 && lastPrice == 0) || lastPriceTrend.equals("free") && currentPrice == 0) {
						priceTrend = "free";
					} else if (currentPrice < lastPrice && currentPrice == 0) {
						priceTrend = "limited";
					} else if (currentPrice < lastPrice) {
						priceTrend = "down";
					} else if (currentPrice > lastPrice) {
						priceTrend = "up";
					} else if (currentPrice == lastPrice) {
						priceTrend = "same";
					}

					if (priceTrend.equals("free")) {
						currentPrice = 0f;
						lastPrice = 0f;
					}
					
					// 记录价格变化
					TLPriceDao.addPrice(new Price(applicationId, currentPrice, lastPrice, priceTrend));

					// 更新 tb_application 表价格信息
					AppDao.updateAppPrice(applicationId, priceTrend, lastPrice, currentPrice);

					log.info(String.format("app %d price updated: [%.2f > %.2f (%s), %s > %s]", applicationId, lastPrice, currentPrice, priceTrend, lastPriceTrend, priceTrend));
				
					// 更新 应用 信息
					if (version.equals(job.getVersion())) {
						continue;
					}
					
					log.info(String.format("添加更新任务: [%d][%s]", job.getId(), job.getName()));
					addUpdateJob(job);
				}

				resultSet.close();
				ps.close();
			}

			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void addUpdateJob(Job job) {
        try {
            Connection connection = Database.getConnection();
            PreparedStatement ps =
                    connection
                            .prepareStatement("insert ignore into tb_update (id, name, url, created_at) values (?, ?, ?, UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d')))");
            ps.setInt(1, job.getId());
            ps.setString(2, job.getName());
            ps.setString(3, job.getUrl());
            ps.execute();

            ps.close();
            connection.close();
        }
        catch (Exception e) {
            log.error(e);
        }
    }

}
