package com.express.back.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.express.back.dto.BackUserLoginData;
import com.express.back.service.PrintService;

@Controller
@RequestMapping(value={"/back"}, produces = "application/json;charset=UTF-8")
public class PrintController {
	
	private PrintService service=new PrintService();
	
	@RequestMapping(value={"/EmsPrintTab"}, method=RequestMethod.GET, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String EmsPrintTab(String cnum, HttpServletRequest request){
		HttpSession session=request.getSession();
		BackUserLoginData buld=(BackUserLoginData) session.getAttribute("buld");
		String result=service.EmsPrintTab(buld, cnum);
		return result;
	}

}
