package com.whaozl.anjos.persist.bean;

import java.io.Serializable;

public class Comment implements Serializable{
	private static final long serialVersionUID = -3898502437840436057L;
	private String fullCommentId;
	private String Content;
	
	public Comment(){}
	
	public Comment(String fullCommentId, String content) {
		this.fullCommentId = fullCommentId;
		Content = content;
	}
	public String getFullCommentId() {
		return fullCommentId;
	}
	public void setFullCommentId(String fullCommentId) {
		this.fullCommentId = fullCommentId;
	}
	public String getContent() {
		return Content;
	}
	public void setContent(String content) {
		Content = content;
	}
}
