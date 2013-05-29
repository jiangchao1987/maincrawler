package com.candou.ic.navigation.jcwx.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.candou.db.Database;
import com.candou.ic.navigation.jcwx.bean.Job;

public class JobDao {

    private static Logger log = Logger.getLogger(JobDao.class);

    public static void addJob(Job job) {
        String sql = "insert into jcwx_job (job_id,title,cid,cname,views,wxh,content," +
        		"thumbnail,created_at,updated_at) values (?,?,?,?,?,?,?,?,?,?)";
        Connection conn = Database.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, job.getJob_id());
            ps.setString(2, job.getTitle());
            ps.setInt(3, job.getCategoryId());
            ps.setString(4, job.getCategoryName());
            ps.setInt(5, job.getViews());
            ps.setString(6, job.getWxh());
            ps.setString(7, job.getContent());
            ps.setString(8, job.getThumbnail());
            ps.setString(9, job.getCreatedAt());
            ps.setString(10, job.getUpdatedAt());
            ps.executeUpdate();
            ps.close();
            conn.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }



}
