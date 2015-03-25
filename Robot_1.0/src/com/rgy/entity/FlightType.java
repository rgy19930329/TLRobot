package com.rgy.entity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class FlightType {
	
	private int code;
	private String text;
	private List<FlightInfo> infoList = new ArrayList<FlightInfo>();
	
	public FlightType(String jsonString){
		
		try {
			JSONObject json = new JSONObject(jsonString);
			this.code = json.getInt("code");
			this.text = json.getString("text");
			
			JSONArray info = json.getJSONArray("list");
			for (int i = 0; i < info.length(); i++) {
				JSONObject result = info.getJSONObject(i);
				//
				FlightInfo flightInfo = new FlightInfo();
				flightInfo.setFlight(result.getString("flight"));
				flightInfo.setRoute(result.getString("route"));
				flightInfo.setStarttime(result.getString("starttime"));
				flightInfo.setEndtime(result.getString("endtime"));
				//flightInfo.setState(result.getString("state"));
				flightInfo.setDetailurl(result.getString("detailurl"));
				flightInfo.setIcon(result.getString("icon"));
				infoList.add(flightInfo);
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
	
	public void setInfoList(List<FlightInfo> infoList){
		this.infoList=infoList;
	}
	
	public int getCode(){
		return code;
	}
	
	public String getText(){
		return text;
	}
	
	public List<FlightInfo> getInfoList(){
		return infoList;
	}
	
	///////////////////////////////////////
	public class FlightInfo {
		
		private String flight;
		private String route;
		private String starttime;
		private String endtime;
		//private String state;
		private String detailurl;
		private String icon;
		
		public FlightInfo(){
			
		}
		
		public void setFlight(String flight){
			this.flight=flight;
		}
		
		public void setRoute(String route){
			this.route=route;
		}
		
		public void setStarttime(String starttime){
			this.starttime=starttime;
		}
		
		public void setEndtime(String endtime){
			this.endtime=endtime;
		}
		
//		public void setState(String state){
//			this.state=state;
//		}
		
		public void setDetailurl(String detailurl){
			this.detailurl=detailurl;
		}
		
		public void setIcon(String icon){
			this.icon=icon;
		}
		
		/////
		public String getFlight(){
			return flight;
		}
		
		public String getRoute(){
			return route;
		}
		
		public String getStarttime(){
			return starttime;
		}
		
		public String getEndtime(){
			return endtime;
		}
		
//		public String getState(){
//			return state;
//		}
		
		public String getDetailurl(){
			return detailurl;
		}
		
		public String getIcon(){
			return icon;
		}
	}
}
