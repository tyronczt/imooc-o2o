package com.tyron.o2o.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tyron.o2o.entity.Area;
import com.tyron.o2o.service.AreaService;

/**
 * @Description: 区域控制器
 *
 * @author tyronchen
 * @date 2018年3月24日
 */
@Controller
@RequestMapping("/superadmin")
public class AreaController {
	Logger logger = LoggerFactory.getLogger(AreaController.class);

	@Autowired
	private AreaService areaService;

	/**
	 * 区域列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/listarea", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> listArea() {
		Map<String, Object> modelMap = new HashMap<>();
		List<Area> areaList = new ArrayList<>();
		try {
			areaList = areaService.getAreaList();
			modelMap.put("rows", areaList);
			modelMap.put("total", areaList.size());
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
		}
		return modelMap;
	}

}
