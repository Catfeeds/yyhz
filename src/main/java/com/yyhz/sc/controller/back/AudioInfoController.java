package com.yyhz.sc.controller.back;


import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.yyhz.sc.base.controller.BaseController;
import com.yyhz.sc.base.page.PageInfo;
import com.yyhz.sc.data.model.AudioInfo;
import com.yyhz.sc.services.AudioInfoService;
import com.yyhz.utils.UUIDUtil;
import com.yyhz.utils.stream.util.StreamVO;

/**
 * 
* @ClassName: AudioInfoController 
* @Description: app首页后台管理控制层 
* @author fengq 
* @date 2018-09-13 17:10:10 
* @Copyright：app首页后台管理
 */
@Controller
public class AudioInfoController extends BaseController {
	@Resource
	private AudioInfoService service;

	/**
	 * 列表页面跳转
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/back/audioInfoList")
	public String audioInfoList(HttpServletRequest request,
			HttpServletResponse response) {
		return "back/audio/audio_info_list";
	}

	/**
	 * 分页
	 * @param request
	 * @param response
	 * @param info
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value = "/back/audioInfoAjaxPage")
	@ResponseBody
	public Object audioInfoAjaxPage(HttpServletRequest request,
			HttpServletResponse response, AudioInfo info, Integer page,
			Integer rows) {

		PageInfo pageInfo = new PageInfo();
		pageInfo.setPage(page);
		pageInfo.setPageSize(rows);
		info.setStatus(0);
		service.selectAll(info, pageInfo);
		return JSONObject.toJSON(pageInfo);
	}

	/**
	 * 查询所有
	 * @param request
	 * @param response
	 * @param info
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value = "/back/audioInfoAjaxAll")
	@ResponseBody
	public Object audioInfoAjaxAll(HttpServletRequest request,
			HttpServletResponse response, AudioInfo info, Integer page,
			Integer rows) {
		List<AudioInfo> results= service.selectAll(info);
		return JSONObject.toJSON(results); 
	}
	
	/**
	 * 保存
	 * @param request
	 * @param response
	 * @param info
	 * @return
	 */
	@RequestMapping(value = "/back/audioInfoAjaxSave")
	@ResponseBody
	public Object audioInfoAjaxSave(HttpServletRequest request,
			HttpServletResponse response, AudioInfo info) {
		int result = 0;
		String msg = "";
		
		if (info.getId() == null || info.getId().equals("")) {
			info.setId(UUIDUtil.getUUID());
			result = service.insert(info);
			msg = "保存失败！";
		} else {
			result = service.update(info);
			msg = "修改失败！";
		}
		return getJsonResult(result, "操作成功",msg);
	}
	
	
	@RequestMapping(value = "back/audioInfoInsert")
	@ResponseBody
	public Map<String,Object> audioInfoInsert(HttpServletRequest request,
			HttpServletResponse response, AudioInfo info,StreamVO streamVO) {
		int result = 0;
		String msg = "";
		
		info.setId(UUIDUtil.getUUID());
		info.setStatus(0);
		info.setCreateTime(new Date());
		result = service.insertWithFile(info,streamVO);
		
		msg = "保存失败！";
		
		return getJsonResult(result, "操作成功",msg);
	}
	
	
	/**
	 * 删除
	 * @param request
	 * @param response
	 * @param info
	 * @return
	 */
	@RequestMapping(value = "/back/audioInfoAjaxDelete")
	@ResponseBody
	public Object audioInfoAjaxDelete(HttpServletRequest request,
			HttpServletResponse response, AudioInfo info) {
		int result = 0;
		if (info.getId() == null) {
			return getJsonResult(result,"操作成功", "操作失败,ID为空！");
		}
		try {
			info.setStatus(1);
			result = service.update(info);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return getJsonResult(result,"操作成功", "操作失败！");
	}
	@RequestMapping(value = "/back/audioInfoDetail")
	public String audioInfoDetail(HttpServletRequest request,
			HttpServletResponse response,String id) {
		AudioInfo info = service.selectById(id);
		
		return "back/audio/audio_info_detail";
	}
}
