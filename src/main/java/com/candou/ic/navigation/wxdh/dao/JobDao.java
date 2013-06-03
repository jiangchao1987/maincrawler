package com.candou.ic.navigation.wxdh.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.candou.db.Database;
import com.candou.ic.navigation.wxdh.vo.Job;
import com.candou.util.DateTimeUtil;

public class JobDao {
    private static Logger log = Logger.getLogger(JobDao.class);
    private static String table_Name = "wxdh_job";

    public static void addJob(List<Job> apps) {
        try {
            Connection connection = Database.getConnection();
            PreparedStatement ps = connection
                .prepareStatement("insert ignore into "
                    + table_Name
                    + " (id, name, intro, url, f, oc, cid, cname,  direct_number,created_at, updated_at) VALUES (?, ?,  ?, ?, ?, ?, ?, ?, ?,?,?)");

            for (Job app : apps) {
                String now = DateTimeUtil.nowDateTime();
                // log.info(app);

                ps.setInt(1, app.getId());
                ps.setString(2, app.getName());
                ps.setString(3, app.getIntro());
                ps.setString(4, app.getUrl());
                ps.setFloat(5, app.getF());
                ps.setString(6, app.getOc());
                ps.setFloat(7, app.getCid());
                ps.setString(8, app.getCname());
                ps.setString(9, app.getDirect_number());
                ps.setString(10, now);
                ps.setString(11, now);
                ps.executeUpdate();
            }

            ps.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Job> findJobs() {
        List<Job> jobs = new ArrayList<Job>();
        try {
            Connection connection = Database.getConnection();
            Statement st = connection.createStatement();
            ResultSet resultSet = st.executeQuery("select * from wxdh_job where is_matched = 0 limit 100");

            while (resultSet.next()) {
                Job job = new Job();
                job.setId(resultSet.getInt("id"));
                job.setCid(resultSet.getInt("cid"));
                job.setCname(resultSet.getString("cname"));
                job.setName(resultSet.getString("name"));
                jobs.add(job);
            }
            resultSet.close();
            st.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return jobs;
    }

    public static void batchUpdateMatchedStatus(List<Job> jobs) {
        try {
            Connection connection = Database.getConnection();
            PreparedStatement ps = connection.prepareStatement("update wxdh_job set is_matched = 1 where id = ?");

            for (Job job : jobs) {
                ps.setInt(1, job.getId());
                ps.executeUpdate();
            }

            ps.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }

    // 失败 job
    public static void batchUpdateFailedStatus(List<Job> jobs) {
        try {
            Connection connection = Database.getConnection();
            PreparedStatement ps = connection.prepareStatement("update wxdh_job set is_matched = -1 where id = ?");

            for (Job job : jobs) {
                ps.setInt(1, job.getId());
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
