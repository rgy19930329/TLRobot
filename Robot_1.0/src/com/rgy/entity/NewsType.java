package com.rgy.entity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class NewsType {
	
	private int code;
	private String text;
	private List<NewsInfo> infoList = new ArrayList<NewsInfo>();
	
	public NewsType(String jsonString){
		
		try {
			JSONObject json = new JSONObject(jsonString);
			this.code = json.getInt("code");
			this.text = json.getString("text");
			
			JSONArray info = json.getJSONArray("list");
			for (int i = 0; i < info.length(); i++) {
				JSONObject result = info.getJSONObject(i);
				//
				NewsInfo newsInfo = new NewsInfo();
				newsInfo.setArticle(result.getString("article"));
				newsInfo.setSource(result.getString("source"));
				newsInfo.setDetailurl(result.getString("detailurl"));
				newsInfo.setIcon(result.getString("icon"));
				infoList.add(newsInfo);
			}
			
			
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
	
	public void setInfoList(List<NewsInfo> infoList){
		this.infoList=infoList;
	}
	
	public int getCode(){
		return code;
	}
	
	public String getText(){
		return text;
	}
	
	public List<NewsInfo> getInfoList(){
		return infoList;
	}
	
	///////////////////////////////////////
	public class NewsInfo {
		
		private String article;
		private String source;
		private String detailurl;
		private String icon;
		
		public NewsInfo(){
			
		}
		
		public void setArticle(String article){
			this.article=article;
		}
		
		public void setSource(String source){
			this.source=source;
		}
		
		public void setDetailurl(String detailurl){
			this.detailurl=detailurl;
		}
		
		public void setIcon(String icon){
			this.icon=icon;
		}
		
		/////
		public String getArticle(){
			return article;
		}
		
		public String getSource(){
			return source;
		}
		
		public String getDetailurl(){
			return detailurl;
		}
		
		public String getIcon(){
			return icon;
		}
	}
	
}
