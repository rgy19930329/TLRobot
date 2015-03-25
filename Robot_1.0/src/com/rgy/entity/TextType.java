package com.rgy.entity;

import org.json.JSONObject;

public class TextType {
	
	private int code;
	private String text;
	
	public TextType(String jsonString){
		
		try {
			JSONObject json = new JSONObject(jsonString);
			
			this.code = json.getInt("code");
			this.text = json.getString("text");
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
	
	public int getCode(){
		return code;
	}
	
	public String getText(){
		return text;
	}
}
















