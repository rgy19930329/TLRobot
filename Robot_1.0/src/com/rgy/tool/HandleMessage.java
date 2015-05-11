package com.rgy.tool;

import java.net.URLEncoder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class HandleMessage {
	
	private String result = "";
	HttpClient httpClient;
	HttpGet httpGet;
	
	public HandleMessage(){}
	
	public void sendToMessage(String msg){
		String INFO = "";
		try {
			INFO = URLEncoder.encode(msg, "utf-8");
		} catch (Exception e) {
			System.out.println("±àÂë³ö´íÀ²£¡");
		}
		
		String path = "http://www.tuling123.com/openapi/api?key=70bc864cd329ab73a138b108f8471dc2&info="
				+ INFO;
		httpClient = new DefaultHttpClient();
		httpGet = new HttpGet(path);
	}
	
	public String getBackMessage() {
		if(httpClient!=null&&httpGet!=null){
			try {
				HttpResponse httpResponse = httpClient.execute(httpGet);
				if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					HttpEntity httpEntity = httpResponse.getEntity();
					if (httpEntity != null) {
						result = EntityUtils.toString(httpEntity);
					}
				}
			} catch (Exception e) {
				System.out.println("ÇëÇó³ö´íÀ²£¡");
				e.printStackTrace();
			} finally {
				httpClient.getConnectionManager().shutdown();
			}
		}
		
		return result;
	}
	
}
