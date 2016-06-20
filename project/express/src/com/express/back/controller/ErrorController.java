package com.express.back.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value={"/back"}, produces = "plain/text;charset=UTF-8")
public class ErrorController {
	
	@RequestMapping(value={"/404"}, method=RequestMethod.GET)
	public String Index(){
		return "back/404";
	}
}
