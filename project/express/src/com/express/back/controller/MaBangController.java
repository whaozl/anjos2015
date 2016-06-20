package com.express.back.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(value={"/back"}, produces = "plain/text;charset=UTF-8")
public class MaBangController {
	
	/**
	 * http://18079141326.oicp.net/express/back/receive
	 * @param buld
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"/receive"}, method=RequestMethod.POST)
	@ResponseBody
	public String login(HttpServletRequest request, HttpServletResponse response){
		String result="您好，我是郝竹林";
		//request.getAttribute("");
		//request.getParameter("");
		request.getParameterMap();//Map<String,String[]>
		return result;
	}
}
