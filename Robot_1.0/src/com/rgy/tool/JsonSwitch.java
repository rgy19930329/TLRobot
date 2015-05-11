package com.rgy.tool;

import org.json.JSONObject;

import com.rgy.entity.FlightType;
import com.rgy.entity.HotelType;
import com.rgy.entity.LinkType;
import com.rgy.entity.NewsType;
import com.rgy.entity.PriceType;
import com.rgy.entity.SoftDLType;
import com.rgy.entity.TextType;
import com.rgy.entity.TrainType;
import com.rgy.entity.VideoType;

public class JsonSwitch {
	
	public static Object getObject(String jsonString){
		
		Object target = null;
		
		try {
			JSONObject json = new JSONObject(jsonString);
			int code = json.getInt("code");
			
			TextType textType;
			switch (code) {
			case 100000://������
				textType = new TextType(jsonString);
				target = textType;
				break;
			case 200000://������
				LinkType linkType = new LinkType(jsonString);
				target = linkType;
				break;
			case 302000://������
				NewsType newsType = new NewsType(jsonString);
				target = newsType;
				break;
			case 304000://�������
				SoftDLType softDLType = new SoftDLType(jsonString);
				target = softDLType;
				break;
			case 305000://�г�
				TrainType trainType = new TrainType(jsonString);
				target = trainType;
				break;
			case 306000://����
				FlightType flightType = new FlightType(jsonString);
				target = flightType;
				break;
			case 308000://��Ӱ����Ƶ������
				VideoType videoType = new VideoType(jsonString);
				target = videoType;
				break;
			case 309000://�Ƶ�
				HotelType hotelType = new HotelType(jsonString);
				target = hotelType;
				break;
			case 311000://�۸�
				PriceType priceType = new PriceType(jsonString);
				target = priceType;
				break;
			default://Ĭ���������ദ��
				textType = new TextType(jsonString);
				target = textType;
				break;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return target;
	}
	
}
