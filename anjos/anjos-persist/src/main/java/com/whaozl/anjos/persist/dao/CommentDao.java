package com.whaozl.anjos.persist.dao;

import java.text.MessageFormat;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.whaozl.anjos.persist.bean.Comment;
import com.whaozl.anjos.persist.datasource.DBContextHolder;
import com.whaozl.anjos.persist.datasource.DbUtilsTemplate;

@Repository
public class CommentDao{
	@Autowired
    @Qualifier("dbUtilsTemplate")
    private DbUtilsTemplate dbUtilsTemplate;
	
	/**
	 * 获取所有评论
	 */
	public List<Comment> findAllComment(){
		List<Comment> list = null;
		String sql = null;
		switch (DBContextHolder.get()) {
		case LKKData:
			sql="select FullCommentId, Content from Comments";
			break;
		default:break;
		}
		list = dbUtilsTemplate.find(Comment.class, sql);
		return list;
	}
	
	/**
	 * 分页获取评论
	 * pageNum 从0开始算
	 * @param pageIndex 当前页
	 * @param pageSize 每页多少记录
	 */
	public List<Comment> findCommentByPage(long pageIndex, long pageSize){
		List<Comment> list = null;
		String sql = "select {1},{2} from {3} where {0} between "+
						(pageIndex* pageSize + 1)+" and "+((pageIndex+1)*pageSize);
		switch (DBContextHolder.get()) {
		case LKKData:
			sql = MessageFormat.format(sql, "Seq","FullCommentId", "Content", "Comments");
			break;
		default:
			break;
		}
		list = dbUtilsTemplate.find(Comment.class, sql);
		return list;
	}
	
	/**
	 * 获取表的记录总数
	 */
	public long countAllComment(){
		String sql = null;
		switch (DBContextHolder.get()) {
		case LKKData:
			sql="select count(*) from Comments";
			break;
			default:break;
		}
		long count = (Integer)dbUtilsTemplate.findBy(sql, 1);
		return count;
	}
	
}
