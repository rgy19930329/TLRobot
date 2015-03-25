package com.rgy.entity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class TrainType {
	
	private int code;
	private String text;
	private List<TrainInfo> infoList = new ArrayList<TrainInfo>();
	
	public TrainType(String jsonString){
		
		try {
			JSONObject json = new JSONObject(jsonString);
			this.code = json.getInt("code");
			this.text = json.getString("text");
			
			JSONArray info = json.getJSONArray("list");
			for (int i = 0; i < info.length(); i++) {
				JSONObject result = info.getJSONObject(i);
				//
				TrainInfo trainInfo = new TrainInfo();
				trainInfo.setTrainnum(result.getString("trainnum"));
				trainInfo.setStart(result.getString("start"));
				trainInfo.setTerminal(result.getString("terminal"));
				trainInfo.setStarttime(result.getString("starttime"));
				trainInfo.setEndtime(result.getString("endtime"));
				trainInfo.setDetailurl(result.getString("detailurl"));
				trainInfo.setIcon(result.getString("icon"));
				infoList.add(trainInfo);
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
	
	public void setInfoList(List<TrainInfo> infoList){
		this.infoList=infoList;
	}
	
	public int getCode(){
		return code;
	}
	
	public String getText(){
		return text;
	}
	
	public List<TrainInfo> getInfoList(){
		return infoList;
	}
	
	///////////////////////////////////////
	public class TrainInfo {
		
		private String trainnum;
		private String start;
		private String terminal;
		private String starttime;
		private String endtime;
		private String detailurl;
		private String icon;
		
		public TrainInfo(){
			
		}
		
		public void setTrainnum(String trainnum){
			this.trainnum=trainnum;
		}
		
		public void setStart(String start){
			this.start=start;
		}
		
		public void setTerminal(String terminal){
			this.terminal=terminal;
		}
		
		public void setStarttime(String starttime){
			this.starttime=starttime;
		}
		
		public void setEndtime(String endtime){
			this.endtime=endtime;
		}
		
		public void setDetailurl(String detailurl){
			this.detailurl=detailurl;
		}
		
		public void setIcon(String icon){
			this.icon=icon;
		}
		
		/////
		public String getTrainnum(){
			return trainnum;
		}
		
		public String getStart(){
			return start;
		}
		
		public String getTerminal(){
			return terminal;
		}
		
		public String getStarttime(){
			return starttime;
		}
		
		public String getEndtime(){
			return endtime;
		}
		
		public String getDetailurl(){
			return detailurl;
		}
		
		public String getIcon(){
			return icon;
		}
	}
}
