package com.rgy.tool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.rgy.entity.FlightType;
import com.rgy.entity.FlightType.FlightInfo;
import com.rgy.entity.HotelType;
import com.rgy.entity.HotelType.HotelInfo;
import com.rgy.entity.LinkType;
import com.rgy.entity.NewsType;
import com.rgy.entity.NewsType.NewsInfo;
import com.rgy.entity.PriceType;
import com.rgy.entity.PriceType.PriceInfo;
import com.rgy.entity.SoftDLType;
import com.rgy.entity.SoftDLType.SoftDLInfo;
import com.rgy.entity.TextType;
import com.rgy.entity.TrainType;
import com.rgy.entity.TrainType.TrainInfo;
import com.rgy.entity.VideoType;
import com.rgy.entity.VideoType.VideoInfo;
import com.rgy.robot.R;

public class MainListAdapter extends BaseAdapter {

	Context context=null;
	ArrayList<HashMap<String,Object>> chatList=null;
	
	public MainListAdapter(Context context,ArrayList<HashMap<String,Object>> chatList) {
		super();
		this.context = context;
		this.chatList = chatList;
	}
	
	@Override
	public int getCount() {
		return chatList.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		int typeFlag = (Integer)chatList.get(position).get("typeFlag");
		int image_src = (Integer)chatList.get(position).get("image");
		Object obj = chatList.get(position).get("obj");
		
		if(typeFlag == 0){//me,�ұ�,obj�϶���String���͵�
			convertView= LayoutInflater.from(context).inflate(R.layout.chat_listitem_me, null);
			ImageView imageView = (ImageView) convertView.findViewById(R.id.chatlist_image_me);
			TextView textView = (TextView) convertView.findViewById(R.id.chatlist_text_me);
			imageView.setBackgroundResource(image_src);
			//obj�϶���String���͵�
			String result = (String) obj;
			textView.setText(result);
		}else if(typeFlag == 1){//robot,���,Ҫ�ж�obj������
			convertView= LayoutInflater.from(context).inflate(R.layout.chat_listitem_robot, null);
			ImageView imageView = (ImageView) convertView.findViewById(R.id.chatlist_image_robot);
			TextView textView = (TextView) convertView.findViewById(R.id.chatlist_text_robot);
			ListView listView = (ListView) convertView.findViewById(R.id.backinfo_list);
			imageView.setBackgroundResource(image_src);
			//Ҫ�ж�obj������
			if(obj instanceof String){
				String text = (String) obj;
				textView.setText(text);
			}else if(obj instanceof TextType){//������
				TextType textType = (TextType)obj;
				String text = textType.getText();
				textView.setText(text);
			}else if(obj instanceof LinkType){//������
				LinkType linkType = (LinkType)obj;
				String text = linkType.getText();
				String link = linkType.getUrl();
				String result = text +"\n"+ link;
				textView.setText(result);
			}else if(obj instanceof NewsType){//������
				NewsType newsType = (NewsType)obj;
				String text = newsType.getText();
				textView.setText(text);
				
				final ArrayList<HashMap<String, Object>> list=new ArrayList<HashMap<String, Object>>(); 
				
				List<NewsInfo> infoList = newsType.getInfoList();
				for(int i=0;i<infoList.size();i++){
					NewsInfo newsInfo = infoList.get(i);
					String article = newsInfo.getArticle();
					String source = newsInfo.getSource();
					String right_text = article+"\n"+source; 
					
					String detailurl = newsInfo.getDetailurl();
					String icon = newsInfo.getIcon();
					
					addBackInfoListItem(list,icon,right_text,detailurl);
				}
				
				BackInfoAdapter backInfoAdapter=new BackInfoAdapter(context, list);
				
				listView.setAdapter(backInfoAdapter);
				setListViewHeight(listView);
				/**
				 * Ϊbackinfo_list��Ӽ����¼�
				 */
				listView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						String detailurl = (String) list.get(arg2).get("detailurl");
						Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(detailurl));
						context.startActivity(intent);
					}
				});
			}else if(obj instanceof SoftDLType){//���������
				SoftDLType softDLType = (SoftDLType)obj;
				String text = softDLType.getText();
				textView.setText(text);
				
				final ArrayList<HashMap<String, Object>> list=new ArrayList<HashMap<String, Object>>();
				
				List<SoftDLInfo> infoList = softDLType.getInfoList();
				for(int i=0;i<infoList.size();i++){
					SoftDLInfo softDLInfo = infoList.get(i);
					String name = softDLInfo.getName();
					String count = softDLInfo.getCount();
					String right_text = name+"\n"+count; 
					
					String detailurl = softDLInfo.getDetailurl();
					String icon = softDLInfo.getIcon();
					
					addBackInfoListItem(list,icon,right_text,detailurl);
				}
				
				BackInfoAdapter backInfoAdapter=new BackInfoAdapter(context, list);
				
				listView.setAdapter(backInfoAdapter);
				setListViewHeight(listView);
				/**
				 * Ϊbackinfo_list��Ӽ����¼�
				 */
				listView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						String detailurl = (String) list.get(arg2).get("detailurl");
						Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(detailurl));
						context.startActivity(intent);
					}
				});
			}else if(obj instanceof TrainType){//�г���
				TrainType trainType = (TrainType)obj;
				String text = trainType.getText();
				textView.setText(text);
				
				final ArrayList<HashMap<String, Object>> list=new ArrayList<HashMap<String, Object>>();
				
				List<TrainInfo> infoList = trainType.getInfoList();
				for(int i=0;i<infoList.size();i++){
					TrainInfo trainInfo = infoList.get(i);
					String trainnum = trainInfo.getTrainnum();
					String start = trainInfo.getStart();
					String terminal = trainInfo.getTerminal();
					String starttime = trainInfo.getStarttime();
					String endtime = trainInfo.getEndtime();
					String right_text = trainnum +"\n"+start+"-"+terminal+"\n"+starttime+"-"+endtime;
					//
					String detailurl = trainInfo.getDetailurl();
					String icon = trainInfo.getIcon();
					//
					addBackInfoListItem(list,icon,right_text,detailurl);
				}
				
				BackInfoAdapter backInfoAdapter=new BackInfoAdapter(context, list);
				
				listView.setAdapter(backInfoAdapter);
				setListViewHeight(listView);
				
				/**
				 * Ϊbackinfo_list��Ӽ����¼�
				 */
				listView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						String detailurl = (String) list.get(arg2).get("detailurl");
						Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(detailurl));
						context.startActivity(intent);
					}
				});
			}else if(obj instanceof FlightType){//������
				FlightType flightType = (FlightType)obj;
				String text = flightType.getText();
				textView.setText(text);
				
				final ArrayList<HashMap<String, Object>> list=new ArrayList<HashMap<String, Object>>();
				
				List<FlightInfo> infoList = flightType.getInfoList();
				for(int i=0;i<infoList.size();i++){
					FlightInfo flightInfo = infoList.get(i);
					String flight = flightInfo.getFlight();
					String route = flightInfo.getRoute();
					String starttime = flightInfo.getStarttime();
					String endtime = flightInfo.getEndtime();
					String right_text = flight+"/"+route+"\n"+starttime+"-"+endtime;
					//
					String detailurl = flightInfo.getDetailurl();
					String icon = flightInfo.getIcon();
					//
					addBackInfoListItem(list,icon,right_text,detailurl);
				}

				BackInfoAdapter backInfoAdapter=new BackInfoAdapter(context, list);
				
				listView.setAdapter(backInfoAdapter);
				setListViewHeight(listView);
				
				/**
				 * Ϊbackinfo_list��Ӽ����¼�
				 */
				listView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						String detailurl = (String) list.get(arg2).get("detailurl");
						Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(detailurl));
						context.startActivity(intent);
					}
				});
			}else if(obj instanceof VideoType){//��Ƶ��
				VideoType videoType = (VideoType)obj;
				String text = videoType.getText();
				textView.setText(text);
				
				final ArrayList<HashMap<String, Object>> list=new ArrayList<HashMap<String, Object>>();
				
				List<VideoInfo> infoList = videoType.getInfoList();
				for(int i=0;i<infoList.size();i++){
					VideoInfo softDLInfo = infoList.get(i);
					String name = softDLInfo.getName();
					String info = softDLInfo.getInfo();
					String right_text = name+"\n"+info;
					//
					String detailurl = softDLInfo.getDetailurl();
					String icon = softDLInfo.getIcon();
					//
					addBackInfoListItem(list,icon,right_text,detailurl);
				}

				BackInfoAdapter backInfoAdapter=new BackInfoAdapter(context, list);
				
				listView.setAdapter(backInfoAdapter);
				setListViewHeight(listView);
				
				/**
				 * Ϊbackinfo_list��Ӽ����¼�
				 */
				listView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						String detailurl = (String) list.get(arg2).get("detailurl");
						Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(detailurl));
						context.startActivity(intent);
					}
				});
			}else if(obj instanceof HotelType){//�Ƶ���
				HotelType hotelType = (HotelType)obj;
				String text = hotelType.getText();
				textView.setText(text);
				
				final ArrayList<HashMap<String, Object>> list=new ArrayList<HashMap<String, Object>>();
				
				List<HotelInfo> infoList = hotelType.getInfoList();
				for(int i=0;i<infoList.size();i++){
					HotelInfo softDLInfo = infoList.get(i);
					String name = softDLInfo.getName();
					String price = softDLInfo.getPrice();
					String satisfaction = softDLInfo.getSatisfaction();
					String count = softDLInfo.getCount();
					String right_text = name+"-"+price+"\n"+satisfaction+"-"+count;
					//
					String detailurl = softDLInfo.getDetailurl();
					String icon = softDLInfo.getIcon();
					//
					addBackInfoListItem(list,icon,right_text,detailurl);
				}

				BackInfoAdapter backInfoAdapter=new BackInfoAdapter(context, list);
				
				listView.setAdapter(backInfoAdapter);
				setListViewHeight(listView);
				
				/**
				 * Ϊbackinfo_list��Ӽ����¼�
				 */
				listView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						String detailurl = (String) list.get(arg2).get("detailurl");
						Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(detailurl));
						context.startActivity(intent);
					}
				});
			}else if(obj instanceof PriceType){//�۸���
				PriceType priceType = (PriceType)obj;
				String text = priceType.getText();
				textView.setText(text);
				
				final ArrayList<HashMap<String, Object>> list=new ArrayList<HashMap<String, Object>>();
				
				List<PriceInfo> infoList = priceType.getInfoList();
				for(int i=0;i<infoList.size();i++){
					PriceInfo softDLInfo = infoList.get(i);
					String name = softDLInfo.getName();
					String price = softDLInfo.getPrice();
					String right_text = name+"\n"+price;
					//
					String detailurl = softDLInfo.getDetailurl();
					String icon = softDLInfo.getIcon();
					//
					addBackInfoListItem(list,icon,right_text,detailurl);
				}
				
				BackInfoAdapter backInfoAdapter=new BackInfoAdapter(context, list);
				
				listView.setAdapter(backInfoAdapter);
				setListViewHeight(listView);
				
				/**
				 * Ϊbackinfo_list��Ӽ����¼�
				 */
				listView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						String detailurl = (String) list.get(arg2).get("detailurl");
						Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(detailurl));
						context.startActivity(intent);
					}
				});
			}
			
		}//end if(typeFlag == 1)
		
		return convertView;
	}
	
	/**
	 * Ϊlist�������
	 * @param list
	 * @param left_image
	 * @param right_text
	 */
	private void addBackInfoListItem(ArrayList<HashMap<String, Object>> list,String icon,String right_text,String detailurl) {  
		HashMap<String, Object> map = new HashMap<String, Object>();   
		map.put("icon", icon);
		map.put("text", right_text); 
		map.put("detailurl",detailurl);
		list.add(map);  
	}
	
	/**
	 * ����ListView�ĸ߶�
	 * @param listView
	 */
	private void setListViewHeight(ListView listView) {

		ListAdapter listAdapter = listView.getAdapter();
		
		if (listAdapter == null) {
			return;
		}
		
		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}
		
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}

}
















