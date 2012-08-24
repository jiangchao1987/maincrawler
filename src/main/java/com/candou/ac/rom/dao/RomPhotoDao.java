package com.candou.ac.rom.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.candou.ac.rom.bean.RomPhoto;
import com.candou.util.DateTimeUtil;

@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class RomPhotoDao extends JdbcDaoSupport {
	private static Logger log = Logger.getLogger(RomPhotoDao.class);

	private static final String ROMPHOTO_INSERT = "insert ignore into tb_photo (app_id, original_url, created_at, updated_at) VALUES (?, ?, ?, ?)";

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addBatchPhotos(List<RomPhoto> photos) {
		for (RomPhoto photo : photos) {
			String now = DateTimeUtil.nowDateTime();
			log.info(photo);
			getJdbcTemplate().update(ROMPHOTO_INSERT, photo.getAppId(), photo.getOriginalUrl(), now, now);
		}
	}
	
}
