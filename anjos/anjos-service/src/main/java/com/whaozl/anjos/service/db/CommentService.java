package com.whaozl.anjos.service.db;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.whaozl.anjos.persist.bean.Comment;
import com.whaozl.anjos.persist.dao.CommentDao;

@Service
public class CommentService {
	
	@Autowired
	CommentDao commentDao;
	
	public List<Comment> findAllComment() {
		return commentDao.findAllComment();
	}
	
	/**
	 * 分页获取评论
	 * pageNum 从0开始算
	 * @param pageIndex 当前页
	 * @param pageSize 每页多少记录
	 */
	public List<Comment> findCommentByPage(long pageIndex, long pageSize){
		return commentDao.findCommentByPage(pageIndex, pageSize);
	}
	
	/**
	 * 获取表的记录总数
	 */
	public long countAllComment(){
		return commentDao.countAllComment();
	}
}
