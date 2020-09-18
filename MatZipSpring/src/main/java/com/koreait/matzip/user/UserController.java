package com.koreait.matzip.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.koreait.matzip.Const;
import com.koreait.matzip.ViewRef;
import com.koreait.matzip.user.model.UserDTO;
import com.koreait.matzip.user.model.UserVO;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired // bean 등록된 객체(springContainer가 생성한 객체)가 있으면 자동으로 연결해줌
	// Bean 등록은 단 하나만 되어있어야 함
	private UserService service;
	
	@RequestMapping(value="/login", method = RequestMethod.GET)
	public String login(Model model) {
		model.addAttribute(Const.TITLE, "로그인");
		model.addAttribute(Const.VIEW, "user/login");
		return ViewRef.TEMP_DEFAULT;
	}
	
	// @ResponseBody - param을 return시킬 수 있어서 ajax 굳이 쓸 필요 없음
	@RequestMapping(value="/login", method = RequestMethod.POST)
	public String login(UserDTO param) {
//		System.out.println("id : " + param.getUser_id());
//		System.out.println("pw : " + param.getUser_pw());
		
		int result = service.login(param);
		System.out.println("result : " + result);
		if(result == 1) {
			return "redirect:/rest/map";
		}
		return "redirect:/user/login?err=" + result;
	}
	
	@RequestMapping(value="/join", method = RequestMethod.GET)
	// 절대 required에 절대 true를 주면 안 됨 - err 값이 없으면 error가 남(default : true)
	// name과 매개변수명이 같은 경우 value="err" 생략하고 int err만 적어도 됨
	// null 값을 넣을 수 있도록 매개변수 type을 Integer로 바꾸거나 defaultValue="0"으로 설정
	public String join(Model model, @RequestParam(defaultValue="0") int err) {
		System.out.println("err : " + err);
		
		if(err > 0) {
			model.addAttribute("msg", "에러가 발생했습니다.");
		}
		model.addAttribute(Const.TITLE, "회원가입");
		model.addAttribute(Const.VIEW, "user/join");
		return ViewRef.TEMP_DEFAULT;
	}
	
	@RequestMapping(value="/join", method = RequestMethod.POST)
	public String join(UserDTO param) {
		int result = service.join(param);
		
		if(result == 1) {
			return "redirect:/user/login";
		}
		return "redirect:/user/join?err=" + result;
	}
}
