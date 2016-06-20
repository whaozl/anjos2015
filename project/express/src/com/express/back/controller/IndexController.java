package com.express.back.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.express.back.dto.BackUserLoginData;
import com.express.back.dto.WelcomeDTO;
import com.express.back.service.IndexService;


@Controller
@RequestMapping(value={"/back"}, produces = "application/json;charset=UTF-8")
public class IndexController {
	private IndexService service=new IndexService();
	
	
	
	
	@RequestMapping(value={"/index"}, method=RequestMethod.GET)
	public String Index(HttpServletRequest request){
		BackUserLoginData buld=(BackUserLoginData) request.getSession().getAttribute("buld");
		request.setAttribute("username", buld.getUsername());
		return "back/index";
	}
	
	@RequestMapping(value={"/exit"}, method=RequestMethod.GET)
	public String exit(HttpServletRequest requeset){
		HttpSession session=requeset.getSession();
		BackUserLoginData buld=(BackUserLoginData) session.getAttribute("buld");
		if(buld!=null && buld.getRememberMe()!=1){//不记住则清空
			session.setAttribute("buld", null);//不是""
		}
		return "back/login";
	}
	
	@RequestMapping(value={"/welcome"}, method=RequestMethod.GET)
	public String Welcome(){
		return "back/welcome";
	}
	
	@RequestMapping(value={"/welcome"}, method=RequestMethod.POST)
	@ResponseBody
	public WelcomeDTO Welcome(HttpServletRequest request){
		HttpSession session=request.getSession();
		BackUserLoginData buld=(BackUserLoginData) session.getAttribute("buld");
		WelcomeDTO dto=service.Welcome369(buld);
		return dto;
	}
	
}
