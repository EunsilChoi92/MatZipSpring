package com.koreait.matzip;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController {
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(HttpServletRequest request) {
		if(Const.realPath == null) {
			Const.realPath = request.getServletContext().getRealPath("");
		}
		System.out.println("root!!");
		return "redirect:/rest/map";
	}

}
