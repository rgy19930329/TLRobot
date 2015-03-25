package com.rgy.robot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.rgy.entity.FlightType;
import com.rgy.entity.FlightType.FlightInfo;
import com.rgy.entity.HotelType;
import com.rgy.entity.HotelType.HotelInfo;
import com.rgy.entity.LinkType;
import com.rgy.entity.NewsType;
import com.rgy.entity.PriceType;
import com.rgy.entity.NewsType.NewsInfo;
import com.rgy.entity.PriceType.PriceInfo;
import com.rgy.entity.SoftDLType;
import com.rgy.entity.SoftDLType.SoftDLInfo;
import com.rgy.entity.TextType;
import com.rgy.entity.TrainType;
import com.rgy.entity.TrainType.TrainInfo;
import com.rgy.entity.VideoType;
import com.rgy.entity.VideoType.VideoInfo;
import com.rgy.tool.HandleMessage;
import com.rgy.tool.JsonSwitch;
import com.rgy.tool.MainListAdapter;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.R.integer;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi") public class MainActivity extends Activity {

	EditText edit;
	Button send;
	
	ListView chatListView;
	MainListAdapter mainListAdapter;
	ArrayList<HashMap<String,Object>> chatList=new ArrayList<HashMap<String, Object>>();
	
	ListView backinfoListView;
	
	HandleMessage handleMessage;
	AboutDialog aboutDialog;
	ProgressDialog pd;
	
	@SuppressLint("NewApi") @Override
	protected void onCreate(Bundle savedInstanceState) {
		//
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
				.detectDiskReads().detectDiskWrites().detectNetwork()												// problems
				.penaltyLog().build());
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
				.detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
				.penaltyLog().penaltyDeath().build());
		/////
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        /////
		aboutDialog=new AboutDialog(this,R.style.about_dialog);
		pd = new ProgressDialog(MainActivity.this);
		
		edit = (EditText) findViewById(R.id.edit);
		send = (Button) findViewById(R.id.send);

		//-----------------------------------------------------------------
		chatListView = (ListView) findViewById(R.id.chat_list);
		mainListAdapter = new MainListAdapter(this,chatList);
		chatListView.setAdapter(mainListAdapter);
		
		addItemToList("亲，我们来聊点什么呢",1);
		
		//-----------------------------------------------------------------
		
		handleMessage = new HandleMessage();
		/**
		 * 发送按钮点击事件
		 */
		send.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String msg = edit.getText().toString();
				if(msg.equals("")){
					Toast.makeText(MainActivity.this, "发送内容不能为空", Toast.LENGTH_LONG).show();
					return;
				}
				addItemToList(msg, 0);
				//
				new AsyncTaskInfoLoad().execute(msg);
				
				edit.setText("");
			}
		});
	}// end onCreate
	
	/**
	 * 为MainList添加item
	 * @param obj
	 * @param typeFlag
	 */
	public void addItemToList(Object obj,int typeFlag){
		HashMap<String,Object> map=new HashMap<String,Object>();
		if(typeFlag == 0){
			map.put("image", R.drawable.me);
		}else if(typeFlag == 1){
			map.put("image", R.drawable.robot);
		}
		map.put("obj", obj);
		map.put("typeFlag",typeFlag);
		chatList.add(map);
		mainListAdapter.notifyDataSetChanged();
		chatListView.setSelection(chatList.size()-1);
	}
	
	/**
	 * 异步消息处理
	 * @author hhtx
	 *
	 */
	private class AsyncTaskInfoLoad extends AsyncTask<String, Integer, String>{
		
		@Override
		protected String doInBackground(String... params) {
			String msg = params[0];
			handleMessage.sendToMessage(msg);
			String jsonString = handleMessage.getBackMessage();
			return jsonString;
		}
		
		@Override
		protected void onPostExecute(String jsonString) {
			if(jsonString.equals("")){
				Toast.makeText(MainActivity.this, "亲，麻烦检查下你的网络吧^_^!", Toast.LENGTH_SHORT).show();
				return;
			}
			////
			Object obj = JsonSwitch.getObject(jsonString);
			addItemToList(obj, 1);
			super.onPostExecute(jsonString);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		// 通过id来辨别被点击的菜单选项
		case R.id.exit:
			finish();
//			pd = new ProgressDialog(MainActivity.this);
//			pd.setMessage("拼命加载中……");
//			pd.show();
			break;
			
		case R.id.about:
			aboutDialog.show();
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		switch (keyCode) {
		//------------------------------------------------------------
		case KeyEvent.KEYCODE_BACK://返回键
			
			AlertDialog.Builder build = new AlertDialog.Builder(this);
			build.setMessage("确定要离开我吗？")
					.setPositiveButton("离开",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
									finish();
								}
							})
					.setNegativeButton("取消",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,int which) {
									
								}
							}).show();
			break;
		//-----------------------------------------------------------------------	
			
			
		//-----------------------------------------------------------------------
		default:
			break;
		}
		return false;
		
	}

}
