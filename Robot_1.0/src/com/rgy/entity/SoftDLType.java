package com.rgy.entity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class SoftDLType {
	
	private int code;
	private String text;
	private List<SoftDLInfo> infoList = new ArrayList<SoftDLInfo>();
	
	public SoftDLType(String jsonString){
		
		try {
			JSONObject json = new JSONObject(jsonString);
			this.code = json.getInt("code");
			this.text = json.getString("text");
			
			JSONArray info = json.getJSONArray("list");
			for (int i = 0; i < info.length(); i++) {
				JSONObject result = info.getJSONObject(i);
				//
				SoftDLInfo softDLInfo = new SoftDLInfo();
				softDLInfo.setName(result.getString("name"));
				softDLInfo.setCount(result.getString("count"));
				softDLInfo.setDetailurl(result.getString("detailurl"));
				softDLInfo.setIcon(result.getString("icon"));
				infoList.add(softDLInfo);
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
	
	public void setInfoList(List<SoftDLInfo> infoList){
		this.infoList=infoList;
	}
	
	public int getCode(){
		return code;
	}
	
	public String getText(){
		return text;
	}
	
	public List<SoftDLInfo> getInfoList(){
		return infoList;
	}
	
	///////////////////////////////////////
	public class SoftDLInfo {
		
		private String name;
		private String count;
		private String detailurl;
		private String icon;
		
		public SoftDLInfo(){
			
		}
		
		public void setName(String name){
			this.name=name;
		}
		
		public void setCount(String count){
			this.count=count;
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
		
		public String getCount(){
			return count;
		}
		
		public String getDetailurl(){
			return detailurl;
		}
		
		public String getIcon(){
			return icon;
		}
	}
}
