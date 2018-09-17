package com.yyhz.sc.controller.back;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yyhz.sc.base.controller.BaseController;
import com.yyhz.sc.base.page.PageInfo;
import com.yyhz.sc.data.model.ActorInfo;
import com.yyhz.sc.data.model.CardInfo;
import com.yyhz.sc.services.ActorCommentService;
import com.yyhz.sc.services.ActorInfoService;
import com.yyhz.sc.services.CardInfoService;
import com.yyhz.utils.UUIDUtil;

/**
 * 
* @ClassName: CardInfoController 
* @Description: 控制层 
* @author lipeng 
* @date 2017-03-16 23:54:43 
* @Copyright：
 */
@Controller
@RequestMapping(value = "/back")
public class CardInfoController extends BaseController {
	
	@Resource
	private CardInfoService service;
	
	@Resource
	private ActorInfoService actorInfoService;
	
	@Resource
	private ActorCommentService actorCommentService;

	@RequestMapping(value = "/zcmtCardList")
	public String zcmtCardList(HttpServletRequest request,
			HttpServletResponse response) {
		return "back/card/zcmt_card_list";
	}
	@RequestMapping(value = "/sbfzCardList")
	public String sbfzCardList(HttpServletRequest request,
			HttpServletResponse response) {
		return "back/card/sbfz_card_list";
	}
	
	@RequestMapping(value = "/chcyCardList")
	public String chcyCardList(HttpServletRequest request,
			HttpServletResponse response) {
		return "back/card/chcy_card_list";
	}
	
	@RequestMapping(value = "/hlpdCardList")
	public String hlpdCardList(HttpServletRequest request,
			HttpServletResponse response) {
		return "back/card/hlpd_card_list";
	}
	
	@RequestMapping(value = "/cardListDetail")
	public String cardListDetail(HttpServletRequest request,HttpServletResponse response,String id,String type) {
		request.setAttribute("id", id);
		CardInfo info = service.selectById(id);
		String actorId = info.getCreater();
		ActorInfo actorInfo = actorInfoService.selectById(actorId);
		float avgScore = actorCommentService.getAvgScore(actorId);
		if(avgScore == 0.0f){
			actorInfo.setAvgScore(null);
		}else{
			actorInfo.setAvgScore(avgScore);
		}
		request.setAttribute("cardDetail", info);
		request.setAttribute("actorInfo", actorInfo);
		if("1".equals(type)){
			return "back/card/zcmt_card_detail";
		}else if("2".equals(type)){
			return "back/card/sbfz_card_detail";
		}else if("3".equals(type)){
			return "back/card/chcy_card_detail";
		}else if("4".equals(type)){
			return "back/card/hlpd_card_detail";
		}
		return null;
	}

	@RequestMapping(value = "/cardInfoAjaxPage")
	@ResponseBody
	public PageInfo<CardInfo> cardInfoAjaxPage(HttpServletRequest request,
			HttpServletResponse response, CardInfo info, Integer page,
			Integer rows) {
		PageInfo<CardInfo> pageInfo = new PageInfo<CardInfo>();
		pageInfo.setPage(page);
		pageInfo.setPageSize(rows);
		service.selectAll(info, pageInfo);
		return pageInfo;
	}

	@RequestMapping(value = "/cardInfoAjaxAll")
	@ResponseBody
	public List<CardInfo> cardInfoAjaxAll(HttpServletRequest request,
			HttpServletResponse response, CardInfo info, Integer page,
			Integer rows) {
		List<CardInfo> results= service.selectAll(info);
		return results; 
	}
	
	@RequestMapping(value = "/cardInfoAjaxSave")
	@ResponseBody
	public Map<String,Object> cardInfoAjaxSave(HttpServletRequest request,
			HttpServletResponse response, CardInfo info) {
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

	@RequestMapping(value = "/cardInfoAjaxDelete")
	@ResponseBody
	public Map<String,Object> cardInfoAjaxDelete(HttpServletRequest request,
			HttpServletResponse response, CardInfo info) {
		int result = 0;
		try {
			result = service.delete(info);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return getJsonResult(result,"操作成功", "删除失败！");
	}
}