package com.rgy.tool;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rgy.tlrobot.R;

public class BackInfoAdapter extends BaseAdapter {
	
	Context context=null;
	ArrayList<HashMap<String,Object>> list=null;
	
	public BackInfoAdapter(Context context,ArrayList<HashMap<String,Object>> list) {
		super();
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
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
		
		String imagePath = list.get(position).get("icon").toString();
		String text = list.get(position).get("text").toString();
		//
		convertView= LayoutInflater.from(context).inflate(R.layout.backinfo_list, null);
		ImageView imageView = (ImageView) convertView.findViewById(R.id.backinfo_image);
		TextView textView = (TextView) convertView.findViewById(R.id.backinfo_text);
		
		textView.setText(text);
		
		if(imagePath.equals("")){
			imageView.setBackgroundResource(R.drawable.none);
		}else{
			loadImage(context,imageView, imagePath);
		}
		return convertView;
	}
	
	/**
	 * 异步加载图片资源
	 * @param img
	 * @param path
	 */
	private void loadImage(Context context,ImageView img, String path) {
		//异步网络加载图片资源
	     AsyncTaskImageLoad async=new AsyncTaskImageLoad(context,img);
	     //执行异步加载，并把图片的路径传送过去
	     async.execute(path);   
	}
	
}
