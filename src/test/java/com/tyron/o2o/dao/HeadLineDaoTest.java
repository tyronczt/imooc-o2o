package com.tyron.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.tyron.o2o.BaseTest;
import com.tyron.o2o.entity.HeadLine;
import com.tyron.o2o.enums.EnableStatusEnum;
import com.tyron.o2o.util.ImageUtil;
import com.tyron.o2o.util.PathUtil;

/**
 * @Description: 首页头条数据接口测试
 *
 * @author: tyron
 * @date: 2018年11月25日
 */

public class HeadLineDaoTest extends BaseTest {

	@Autowired
	private HeadLineDao headLineDao;

	@Test
	public void testInsertHeadLine() throws Exception {
		HeadLine headLine = new HeadLine();
		headLine.setCreateTime(new Date());
		headLine.setEnableStatus(EnableStatusEnum.AVAILABLE.getState());
		String filePath = "D:\\eclipse\\pic\\头图4.jpg";
		MultipartFile multipartFile = ImageUtil.path2MultipartFile(filePath);
		String dest = PathUtil.getHeadLineImagePath();
		String generateHeadImg = ImageUtil.generateHeadImg(multipartFile, dest);
		headLine.setLineImg(generateHeadImg);
		headLine.setLineLink("链接4");
		headLine.setLineName("头图4");
		headLine.setPriority(4);
		int effectNum = headLineDao.insertHeadLine(headLine);
		System.out.println("insertNum:" + effectNum);
	}

	@Test
	@Ignore
	public void testModifyHeadLine() throws Exception {
		HeadLine currHeadLine = new HeadLine();
		currHeadLine.setLastEditTime(new Date());
		currHeadLine.setLineId(1L);
		// 删除原有图片
		HeadLine origHeadLine = headLineDao.selectHeadLineById(1L);
		ImageUtil.deleteFileOrPath(origHeadLine.getLineImg());
		currHeadLine.setLineLink("链接2");
		currHeadLine.setLineName("头图2");
		String filePath = "D:\\eclipse\\pic\\头图2.jpg";
		MultipartFile multipartFile = ImageUtil.path2MultipartFile(filePath);
		String dest = PathUtil.getHeadLineImagePath();
		String generateHeadImg = ImageUtil.generateHeadImg(multipartFile, dest);
		currHeadLine.setLineImg(generateHeadImg);
		currHeadLine.setPriority(2);
		int effectNum = headLineDao.updateHeadLine(currHeadLine);
		System.out.println("insertNum:" + effectNum);
	}

	@Test
	@Ignore
	public void testDeleteHeadLine() throws Exception {
		HeadLine headLine = new HeadLine();
		headLine.setLineId(1L);
		headLine.setEnableStatus(EnableStatusEnum.UNAVAILABLE.getState());
		headLine.setLastEditTime(new Date());
		int effectNum = headLineDao.updateHeadLine(headLine);
		System.out.println("insertNum:" + effectNum);
	}

	@Test
	@Ignore
	public void testSelectHeadLine() throws Exception {
		List<HeadLine> headLineList = headLineDao.selectHeadLineList(new HeadLine());
		assertEquals(1, headLineList.size());
	}

}
