package com.express.back.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.express.back.dto.BackUserLoginData;
import com.express.back.dto.OrderOneDTO;
import com.express.back.service.OrderOneService;


@Controller
@RequestMapping(value={"/back"}, produces = "application/json;charset=UTF-8")
public class OrderOneController {
	
	private OrderOneService service=new OrderOneService();
	
	
	@RequestMapping(value={"/order_one"}, method=RequestMethod.GET)
	public String Index(){
		return "back/order_one";
	}
	
	
	@RequestMapping(value={"/OrderOne"}, method=RequestMethod.POST)
	@ResponseBody
	public OrderOneDTO OrderOne(HttpServletRequest request){
		HttpSession session=request.getSession();
		BackUserLoginData buld=(BackUserLoginData) session.getAttribute("buld");
		OrderOneDTO dto=service.OrderOne(buld);
		return dto;
	}
	
	@RequestMapping(value={"/QueryNum"}, method=RequestMethod.POST, produces = "plain/text;charset=UTF-8")
	@ResponseBody
	public String QueryNum(String cnum, HttpServletRequest request){
		HttpSession session=request.getSession();
		BackUserLoginData buld=(BackUserLoginData) session.getAttribute("buld");
		String result=service.QueryNum(buld, cnum);
		return result;
	}
	
	@RequestMapping(value={"/RecPreInputSet"}, method=RequestMethod.POST, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String RecPreInputSet(String param, HttpServletRequest request){
		HttpSession session=request.getSession();
		BackUserLoginData buld=(BackUserLoginData) session.getAttribute("buld");
		//Map<String, String[]> param=request.getParameterMap();
		String result=service.RecPreInputSet(buld, param);
		return result;
	}
	
}
