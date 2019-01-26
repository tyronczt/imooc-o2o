package com.tyron.o2o.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.tyron.o2o.dto.HeadLineExecution;
import com.tyron.o2o.entity.HeadLine;

/**
 * @Description: 首页头条业务接口
 *
 * @author: tyron
 * @date: 2018年11月26日
 */

public interface HeadLineService {
	public final static String HL_LIST_KEY = "headlinelist";

	/**
	 * 根据条件查询头条列表
	 * 
	 * @param headLineCondition
	 * @return
	 * @throws IOException
	 */
	List<HeadLine> getHeadLineList(HeadLine headLineCondition) throws IOException;

	/**
	 * 添加头条记录
	 * 
	 * @param headLine
	 * @param headLineImg
	 * @return
	 */
	HeadLineExecution addHeadLine(HeadLine headLine, MultipartFile headLineImg);

	/**
	 * 修改头条记录
	 * 
	 * @param headLine
	 * @param headLineImg
	 * @return
	 */
	HeadLineExecution modifyHeadLine(HeadLine headLine, MultipartFile headLineImg);

	/**
	 * 删除头条
	 * 
	 * @param headLineId
	 * @return
	 */
	HeadLineExecution removeHeadLine(long headLineId);

	/**
	 * 删除头条列表
	 * 
	 * @param headLineIdList
	 * @return
	 */
	HeadLineExecution removeHeadLineList(List<Long> headLineIdList);

}
