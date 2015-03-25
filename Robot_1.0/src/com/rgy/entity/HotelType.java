package com.rgy.entity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class HotelType {

	private int code;
	private String text;
	private List<HotelInfo> infoList = new ArrayList<HotelInfo>();
	
	public HotelType(String jsonString){
		
		try {
			JSONObject json = new JSONObject(jsonString);
			this.code = json.getInt("code");
			this.text = json.getString("text");
			
			JSONArray info = json.getJSONArray("list");
			for (int i = 0; i < info.length(); i++) {
				JSONObject result = info.getJSONObject(i);
				//
				HotelInfo hotelInfo = new HotelInfo();
				hotelInfo.setName(result.getString("name"));
				hotelInfo.setPrice(result.getString("price"));
				hotelInfo.setSatisfaction(result.getString("satisfaction"));
				hotelInfo.setCount(result.getString("count"));
				hotelInfo.setDetailurl(result.getString("detailurl"));
				hotelInfo.setIcon(result.getString("icon"));
				infoList.add(hotelInfo);
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
	
	public void setInfoList(List<HotelInfo> infoList){
		this.infoList=infoList;
	}
	
	public int getCode(){
		return code;
	}
	
	public String getText(){
		return text;
	}
	
	public List<HotelInfo> getInfoList(){
		return infoList;
	}
	
	///////////////////////////////////////
	public class HotelInfo {
		
		private String name;
		private String price;
		private String satisfaction;
		private String count;
		private String detailurl;
		private String icon;
		
		public HotelInfo(){
			
		}
		
		public void setName(String name){
			this.name=name;
		}
		
		public void setPrice(String price){
			this.price=price;
		}
		
		public void setSatisfaction(String satisfaction){
			this.satisfaction=satisfaction;
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
		
		public String getPrice(){
			return price;
		}
		
		public String getSatisfaction(){
			return satisfaction;
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
