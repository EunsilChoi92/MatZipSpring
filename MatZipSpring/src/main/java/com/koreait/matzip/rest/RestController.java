package com.koreait.matzip.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.koreait.matzip.Const;
import com.koreait.matzip.SecurityUtils;
import com.koreait.matzip.ViewRef;
import com.koreait.matzip.rest.model.RestDMI;
import com.koreait.matzip.rest.model.RestFile;
import com.koreait.matzip.rest.model.RestMenuVO;
import com.koreait.matzip.rest.model.RestPARAM;
import com.koreait.matzip.rest.model.RestRecMenuVO;
import com.koreait.matzip.rest.model.RestVO;
import com.koreait.matzip.user.model.UserPARAM;

@Controller
@RequestMapping("/rest")
public class RestController {
	
	@Autowired
	private RestService service;

	@RequestMapping("/map")
	public String restMap(Model model) {
		model.addAttribute(Const.TITLE, "지도보기");
		model.addAttribute(Const.VIEW, "rest/restMap");
		return ViewRef.TEMP_MENU_TEMP;
	}
	
	@RequestMapping(value = "/ajaxGetList", produces = {"application/json; charset=UTF-8"})
	@ResponseBody 
	public List<RestDMI> ajaxGetList(RestPARAM param) {
		System.out.println("sw_lat :" + param.getSw_lat());
		System.out.println("sw_lng :" + param.getSw_lng());
		System.out.println("ne_lat :" + param.getNe_lat());
		System.out.println("ne_lng :" + param.getNe_lng());
		
		return service.selRestList(param);
	}
	
	@RequestMapping(value="/reg", method = RequestMethod.GET)
	public String restReg(Model model) {
		model.addAttribute("categoryList", service.selCategoryList());
		model.addAttribute(Const.TITLE, "등록");
		model.addAttribute(Const.VIEW, "rest/restReg");
		return ViewRef.TEMP_MENU_TEMP;
	}
	
	@RequestMapping(value="/reg", method = RequestMethod.POST)
	public String restReg(RestPARAM param, HttpSession hs) {
		param.setI_user(SecurityUtils.getLoginUserPk(hs));
		int result = service.insRest(param);
		return "redirect:/rest/map";
	}
	
	@RequestMapping("/detail")
	public String detail(Model model, RestPARAM param) {
		RestDMI data = service.selRest(param);
		model.addAttribute(Const.TITLE, data.getNm());
		model.addAttribute(Const.VIEW, "rest/restDetail");
		model.addAttribute("data", data);
		model.addAttribute("recMenuList", service.selRestRecMenus(param));
		model.addAttribute("css", new String[]{"common", "restDetail", "swiper-bundle.min"});
		// model.addAttribute("menuList", service.selRestMenus(param));
		return ViewRef.TEMP_MENU_TEMP;
	}
	
	@RequestMapping("/ajaxSelMenuList")
	@ResponseBody
	public List<RestMenuVO> ajaxSelMenuList(RestPARAM param) {
		return service.selRestMenus(param);
	}
	
	@RequestMapping("/del")
	public String del(RestPARAM param, HttpSession hs) {
		int loginI_user = SecurityUtils.getLoginUserPk(hs);
		param.setI_user(loginI_user);
		int result = 1;
		try {
			service.delRestTran(param);
		} catch(Exception e) {
			result = 0;
		}
		System.out.println("del result : " + result);
		return "redirect:/";
	}
	
	@RequestMapping(value="/recMenus", method=RequestMethod.POST)
	public String recMenus(MultipartHttpServletRequest mReq, RedirectAttributes ra) {
		
		int i_rest = service.insRecMenus(mReq);
	
		ra.addAttribute("i_rest", i_rest);
		return "redirect:/rest/detail";
	}
	
	@RequestMapping("/ajaxDelRecMenu")
	@ResponseBody
	public int ajaxDelRecMenu(RestPARAM param, HttpSession hs) {
		String path = "/resources/img/rest/" + param.getI_rest() + "/rec_menu/";
		String realPath = hs.getServletContext().getRealPath(path);
		System.out.println("path : " + path);
		System.out.println("realPath : " + realPath);
		param.setI_user(SecurityUtils.getLoginUserPk(hs)); // 로그인한 유저의 i_user 담기
		return service.delRestRecMenu(param, realPath);
	}
	
	@RequestMapping("/ajaxDelMenu")
	@ResponseBody
	public int ajaxDelMenu(RestPARAM param) { // i_rest, seq, menu_pic 담겨 있음
		return service.delRestMenu(param);
	}
	
	@RequestMapping(value="/menus", method=RequestMethod.POST)
	public String menus(@ModelAttribute RestFile param, HttpSession hs, RedirectAttributes ra) {
		int i_user = SecurityUtils.getLoginUserPk(hs);
		int result = service.insRestMenus(param, i_user);
		
		ra.addAttribute("i_rest", param.getI_rest());
		return "redirect:/rest/detail";
	}
}
