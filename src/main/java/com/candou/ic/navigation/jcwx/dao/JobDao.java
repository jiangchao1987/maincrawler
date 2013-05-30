package com.candou.ic.navigation.jcwx.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.candou.db.Database;
import com.candou.ic.navigation.jcwx.bean.Job;

public class JobDao {

    private static Logger log = Logger.getLogger(JobDao.class);

    public static void addBatchJobs(List<Job> jobs) {
        try {
            Connection connection = Database.getConnection();
            PreparedStatement ps = connection
                .prepareStatement("insert into jcwx_job (job_id,title,cid,cname,views,wxh,content,thumbnail,created_at,updated_at) values (?,?,?,?,?,?,?,?,?,?)");

            for (Job job : jobs) {
                log.info(job);

                ps.setInt(1, job.getId());
                ps.setString(2, job.getTitle());
                ps.setInt(3, job.getCid());
                ps.setString(4, job.getCname());
                ps.setInt(5, job.getViews());
                ps.setString(6, job.getWxh());
                ps.setString(7, job.getContent());
                ps.setString(8, job.getThumbnail());
                ps.setString(9, job.getCreatedAt());
                ps.setString(10, job.getUpdatedAt());

                ps.executeUpdate();
            }

            ps.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }

    public static List<Job> findJobs() {
        List<Job> jobs = new ArrayList<Job>();
        try {
            Connection connection = Database.getConnection();
            Statement st = connection.createStatement();
            ResultSet resultSet = st.executeQuery("select * from jcwx_job where is_matched = 0 limit 100");

            while (resultSet.next()) {
                Job job = new Job();
                job.setId(resultSet.getInt("job_id"));
                job.setTitle(resultSet.getString("title"));
                job.setCid(resultSet.getInt("cid"));
                job.setCname(resultSet.getString("cname"));

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
            PreparedStatement ps = connection.prepareStatement("update jcwx_job set is_matched = 1 where job_id = ?");

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

    public static void batchUpdateFailedStatus(List<Job> jobs) {
        try {
            Connection connection = Database.getConnection();
            PreparedStatement ps = connection.prepareStatement("update jcwx_job set is_matched = -1 where job_id = ?");

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
