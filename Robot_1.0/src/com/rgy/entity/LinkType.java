package com.rgy.entity;

import org.json.JSONObject;

public class LinkType {
	
	private int code;
	private String text;
	private String url;
	
	public LinkType(String jsonString){
		
		try {
			JSONObject json = new JSONObject(jsonString);
			
			this.code = json.getInt("code");
			this.text = json.getString("text");
			this.url = json.getString("url");
			////
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setCode(int code){
		this.code = code;
	}
	
	public void setText(String text){
		this.text = text;
	}
	
	public void setUrl(String url){
		this.url = url;
	}
	
	public int getCode(){
		return code;
	}
	
	public String getText(){
		return text;
	}
	
	public String getUrl(){
		return url;
	}
	
}
