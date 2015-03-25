package com.rgy.entity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class PriceType {
	
	private int code;
	private String text;
	private List<PriceInfo> infoList = new ArrayList<PriceInfo>();
	
	public PriceType(String jsonString){
		
		try {
			JSONObject json = new JSONObject(jsonString);
			this.code = json.getInt("code");
			this.text = json.getString("text");
			
			JSONArray info = json.getJSONArray("list");
			for (int i = 0; i < info.length(); i++) {
				JSONObject result = info.getJSONObject(i);
				//
				PriceInfo priceInfo = new PriceInfo();
				priceInfo.setName(result.getString("name"));
				priceInfo.setPrice(result.getString("price"));
				priceInfo.setDetailurl(result.getString("detailurl"));
				priceInfo.setIcon(result.getString("icon"));
				infoList.add(priceInfo);
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
	
	public void setInfoList(List<PriceInfo> infoList){
		this.infoList=infoList;
	}
	
	public int getCode(){
		return code;
	}
	
	public String getText(){
		return text;
	}
	
	public List<PriceInfo> getInfoList(){
		return infoList;
	}
	
	///////////////////////////////////////
	public class PriceInfo {
		
		private String name;
		private String price;
		private String detailurl;
		private String icon;
		
		public PriceInfo(){
			
		}
		
		public void setName(String name){
			this.name=name;
		}
		
		public void setPrice(String price){
			this.price=price;
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
		
		public String getDetailurl(){
			return detailurl;
		}
		
		public String getIcon(){
			return icon;
		}
	}
}
