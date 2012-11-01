package com.candou.ac.rom.dao;

import java.util.List;

import com.candou.ac.rom.bean.RomPhoto;

public interface IRomPhotoDao {

	void addBatchPhotos(List<RomPhoto> photos);

}
