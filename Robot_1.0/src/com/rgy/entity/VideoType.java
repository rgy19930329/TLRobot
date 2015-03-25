package com.rgy.entity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class VideoType {
	
	private int code;
	private String text;
	private List<VideoInfo> infoList = new ArrayList<VideoInfo>();
	
	public VideoType(String jsonString){
		
		try {
			JSONObject json = new JSONObject(jsonString);
			this.code = json.getInt("code");
			this.text = json.getString("text");
			
			JSONArray info = json.getJSONArray("list");
			for (int i = 0; i < info.length(); i++) {
				JSONObject result = info.getJSONObject(i);
				//
				VideoInfo VideoInfo = new VideoInfo();
				VideoInfo.setName(result.getString("name"));
				VideoInfo.setInfo(result.getString("info"));
				VideoInfo.setDetailurl(result.getString("detailurl"));
				VideoInfo.setIcon(result.getString("icon"));
				infoList.add(VideoInfo);
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
	
	public void setInfoList(List<VideoInfo> infoList){
		this.infoList=infoList;
	}
	
	public int getCode(){
		return code;
	}
	
	public String getText(){
		return text;
	}
	
	public List<VideoInfo> getInfoList(){
		return infoList;
	}
	
	///////////////////////////////////////
	public class VideoInfo {
		
		private String name;
		private String info;
		private String detailurl;
		private String icon;
		
		public VideoInfo(){
			
		}
		
		public void setName(String name){
			this.name=name;
		}
		
		public void setInfo(String info){
			this.info=info;
		}
		
		public void setDetailurl(String detailurl){
			this.detailurl=detailurl;
		}
		
		public void setIcon(String icon){
			this.icon=icon;
		}
		
		/////
		public String getName(){
			return name;
		}
		
		public String getInfo(){
			return info;
		}
		
		public String getDetailurl(){
			return detailurl;
		}
		
		public String getIcon(){
			return icon;
		}
	}
}
