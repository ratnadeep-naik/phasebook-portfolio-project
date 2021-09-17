package com.portfolio.phasebook.backend.model;

public class Content {
	public String contentType;	//VIDEO, TEXT, IMAGE
	public String content;
	public Content() {}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

}
