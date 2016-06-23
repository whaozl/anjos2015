package com.whaozl.anjos.web.controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.whaozl.anjos.persist.bean.Comment;
import com.whaozl.anjos.persist.datasource.DBContextHolder;
import com.whaozl.anjos.persist.datasource.DataSourceType;
import com.whaozl.anjos.service.db.CommentService;

/**
 * 分词
 * @author whaozl
 * plain/text
 * text/html 页面返回,不弹出下载
 */
@Controller
@RequestMapping(value={"/"}, produces = "application/json;charset=UTF-8")
public class NLPIRController {
	
	@Autowired
	private CommentService commentService;
	
	@RequestMapping(value={"/test"}, method=RequestMethod.GET)
	@ResponseBody
	public String test( HttpServletRequest request ){
		String input = "去年开始，打开百度李毅吧，满屏的帖子大多含有“屌丝”二字，一般网友不仅不懂这词什么意思，更难理解这个词为什么会这么火。然而从下半年开始，“屌丝”已经覆盖网络各个角落，人人争说屌丝，人人争当屌丝。从遭遇恶搞到群体自嘲，“屌丝”名号横空出世";
		return input;
	}
	
	@RequestMapping(value={"/ppl"}, method=RequestMethod.POST)
	@ResponseBody
	public String ppl( HttpServletRequest request ){
		String input = "去年开始，打开百度李毅吧，满屏的帖子大多含有“屌丝”二字，一般网友不仅不懂这词什么意思，更难理解这个词为什么会这么火。然而从下半年开始，“屌丝”已经覆盖网络各个角落，人人争说屌丝，人人争当屌丝。从遭遇恶搞到群体自嘲，“屌丝”名号横空出世";
		return input;
	}
	
	/**
	 * 分析一件商品
	 */
	@RequestMapping(value={"/anal"}, method = RequestMethod.GET)
	@ResponseBody
	public List<String> analysis(HttpServletRequest request){
		DBContextHolder.set( DataSourceType.LKKData );//切换数据源
		List<Comment> list = commentService.findAllComment();
		List<String> strs = new ArrayList<String>();
		for( Comment cm : list){
			strs.add(cm.getContent());
		}
		return strs;
	}
	
	
}
