/**
* 
*/
package com.tyron.o2o.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tyron.o2o.cache.JedisUtil;
import com.tyron.o2o.dao.HeadLineDao;
import com.tyron.o2o.dto.HeadLineExecution;
import com.tyron.o2o.entity.HeadLine;
import com.tyron.o2o.enums.HeadLineStateEnum;
import com.tyron.o2o.enums.OperationStatusEnum;
import com.tyron.o2o.exceptions.HeadLineOperationException;
import com.tyron.o2o.service.CacheService;
import com.tyron.o2o.service.HeadLineService;
import com.tyron.o2o.util.ImageUtil;
import com.tyron.o2o.util.PathUtil;

/**
 * @Description: 首页头条业务接口实现
 *
 * @author: tyron
 * @date: 2018年11月26日
 */
@Service
public class HeadLineServiveImpl implements HeadLineService {

	@Autowired
	private JedisUtil.Strings jedisStrings;
	@Autowired
	private JedisUtil.Keys jedisKeys;
	@Autowired
	private CacheService cacheService;
	@Autowired
	private HeadLineDao headLineDao;

	private static Logger logger = LoggerFactory.getLogger(HeadLineServiveImpl.class);

	@Override
	public List<HeadLine> getHeadLineList(HeadLine headLineCondition) {
		List<HeadLine> headLineList = null;
		ObjectMapper mapper = new ObjectMapper();
		String key = HL_LIST_KEY;
		if (headLineCondition.getEnableStatus() != null) {
			key = key + "_" + headLineCondition.getEnableStatus();
		}
		// redis中不存在key，则设值
		if (!jedisKeys.exists(key)) {
			headLineList = headLineDao.selectHeadLineList(headLineCondition);
			// 将列表转为jsonString
			String jsonString;
			try {
				jsonString = mapper.writeValueAsString(headLineList);
				jedisStrings.set(key, jsonString);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
				logger.error(e.getMessage());
				throw new HeadLineOperationException(e.getMessage());
			}
		} else {
			String jsonString = jedisStrings.get(key);
			// 将jsonString转为list
			JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, HeadLine.class);
			try {
				headLineList = mapper.readValue(jsonString, javaType);
			} catch (IOException e) {
				e.printStackTrace();
				logger.error(e.getMessage());
				throw new HeadLineOperationException(e.getMessage());
			}
		}
		return headLineList;
	}

	@Override
	@Transactional
	public HeadLineExecution addHeadLine(HeadLine headLine, MultipartFile headLineImg) {
		if (headLine != null) {
			headLine.setCreateTime(new Date());
			headLine.setLastEditTime(new Date());
			// 添加图片
			if (headLineImg != null) {
				addThumbnail(headLine, headLineImg);
			}
			try {
				// 插入图片
				int effectedNum = headLineDao.insertHeadLine(headLine);
				if (effectedNum > 0) {
					String prefix = HL_LIST_KEY;
					Set<String> keySet = jedisKeys.keys(prefix + "*");
					for (String key : keySet) {
						jedisKeys.del(key);
					}
					return new HeadLineExecution(OperationStatusEnum.SUCCESS, headLine);
				} else {
					return new HeadLineExecution(HeadLineStateEnum.EDIT_ERROR);
				}
			} catch (Exception e) {
				logger.error(e.getMessage());
				throw new HeadLineOperationException(HeadLineStateEnum.EDIT_ERROR.getStateInfo() + e.getMessage());
			}
		} else {
			return new HeadLineExecution(HeadLineStateEnum.EMPTY);
		}
	}

	@Override
	@Transactional
	public HeadLineExecution modifyHeadLine(HeadLine headLine, MultipartFile headLineImg) {
		if (headLine.getLineId() != null && headLine.getLineId() > 0) {
			headLine.setLastEditTime(new Date());
			// 图片存在
			if (headLineImg != null) {
				HeadLine tempHeadLine = headLineDao.selectHeadLineById(headLine.getLineId());
				if (tempHeadLine.getLineImg() != null) {
					ImageUtil.deleteFileOrPath(tempHeadLine.getLineImg());
				}
				addThumbnail(headLine, headLineImg);
			}
			try {
				int effectedNum = headLineDao.updateHeadLine(headLine);
				if (effectedNum > 0) {
					// 删除缓存信息
					cacheService.removeFromCache(HL_LIST_KEY);
					return new HeadLineExecution(OperationStatusEnum.SUCCESS, headLine);
				} else {
					return new HeadLineExecution(HeadLineStateEnum.EMPTY);
				}
			} catch (Exception e) {
				logger.error(e.getMessage());
				throw new RuntimeException(HeadLineStateEnum.EDIT_ERROR.getStateInfo() + e.getMessage());
			}
		} else {
			return new HeadLineExecution(HeadLineStateEnum.EMPTY);
		}
	}

	@Override
	@Transactional
	public HeadLineExecution removeHeadLine(long headLineId) {
		if (headLineId > 0) {
			try {
				HeadLine tempHeadLine = headLineDao.selectHeadLineById(headLineId);
				// 删除图片信息
				if (tempHeadLine.getLineImg() != null) {
					ImageUtil.deleteFileOrPath(tempHeadLine.getLineImg());
				}
				int effectedNum = headLineDao.deleteHeadLine(headLineId);
				if (effectedNum > 0) {
					// 删除缓存信息
					cacheService.removeFromCache(HL_LIST_KEY);
					return new HeadLineExecution(OperationStatusEnum.SUCCESS);
				} else {
					return new HeadLineExecution(HeadLineStateEnum.EMPTY);
				}
			} catch (Exception e) {
				logger.error(e.getMessage());
				throw new RuntimeException(HeadLineStateEnum.DELETE_ERROR.getStateInfo() + e.getMessage());
			}
		} else {
			return new HeadLineExecution(HeadLineStateEnum.EMPTY);
		}
	}

	@Override
	@Transactional
	public HeadLineExecution removeHeadLineList(List<Long> headLineIdList) {
		if (headLineIdList != null && headLineIdList.size() > 0) {
			try {
				List<HeadLine> headLineList = headLineDao.selectHeadLineByIds(headLineIdList);
				for (HeadLine headLine : headLineList) {
					if (headLine.getLineImg() != null) {
						ImageUtil.deleteFileOrPath(headLine.getLineImg());
					}
				}
				// 批量删除
				int effectedNum = headLineDao.batchDeleteHeadLine(headLineIdList);
				if (effectedNum > 0) {
					// 删除缓存信息
					cacheService.removeFromCache(HL_LIST_KEY);
					return new HeadLineExecution(OperationStatusEnum.SUCCESS);
				} else {
					return new HeadLineExecution(HeadLineStateEnum.EMPTY);
				}
			} catch (Exception e) {
				logger.error(e.getMessage());
				throw new RuntimeException(HeadLineStateEnum.DELETE_ERROR.getStateInfo() + e.getMessage());
			}
		} else {
			return new HeadLineExecution(HeadLineStateEnum.EMPTY);
		}
	}

	/**
	 * 添加图片信息
	 * 
	 * @param headLine
	 * @param headLineImg
	 */
	private void addThumbnail(HeadLine headLine, MultipartFile headLineImg) {
		String dest = PathUtil.getHeadLineImagePath();
		String thumbnailAddr = ImageUtil.generateHeadImg(headLineImg, dest);
		headLine.setLineImg(thumbnailAddr);
	}

}
