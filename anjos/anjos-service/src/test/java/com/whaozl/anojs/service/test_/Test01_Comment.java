package com.whaozl.anojs.service.test_;

import org.apache.log4j.Logger;
import java.util.List;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.whaozl.anjos.persist.bean.Comment;
import com.whaozl.anjos.persist.datasource.DBContextHolder;
import com.whaozl.anjos.persist.datasource.DataSourceType;
import com.whaozl.anjos.service.db.CommentService;
import com.whaozl.anjos.service.test.SpringTest;

public class Test01_Comment extends SpringTest{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(Test01_Comment.class);
	
	@Autowired
	private CommentService commentService;

	public @Test void test01() {
		
		DBContextHolder.set( DataSourceType.LKKData );//切换数据源
		List<Comment> list =commentService.findAllComment();
		for(Comment comment : list){
			logger.info( comment.getFullCommentId() + " : " + comment.getContent() );
		}
		
	}
	
	public @Test void test02(){
		
	}
	

}
