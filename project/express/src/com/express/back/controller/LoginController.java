package com.express.back.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.express.back.dto.BackUserLoginData;
import com.express.back.service.LoginService;

@Controller
@RequestMapping(value={"/back"}, produces = "plain/text;charset=UTF-8")
public class LoginController {
	
	private LoginService service=new LoginService();

	@RequestMapping(value={"/login","/"}, method=RequestMethod.GET)
	public String Index(){
		return "back/login";
	}
	
	@RequestMapping(value={"/login"}, method=RequestMethod.POST)
	@ResponseBody
	public String login(BackUserLoginData buld, HttpServletRequest request){
		HttpSession session=request.getSession();
		String checkcode=(String)session.getAttribute("checkcode");
		if(checkcode==null){
			return "验证码过期,请刷新";
		}else if(!checkcode.equalsIgnoreCase(buld.getCheckcode())){
			return "验证码不正确";
		}
		service.login369(buld);
		if(buld.getErrorInfo()!=null){
			return buld.getErrorInfo();
		}
		//到这里将登陆成功,记住SessionId和保存buld到Session
		session.setMaxInactiveInterval(buld.getRememberMe()==1 ? 24*60*60 : 20*60);//单位秒
		session.setAttribute("buld", buld);
		System.out.println("用户名:"+buld.getUsername()+"---------369SessionId:"+buld.getSessionId());
		return "success";
	}

}
