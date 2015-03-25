package com.rgy.robot;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

public class AboutDialog extends Dialog{

	private Button btn_ok;
	
	public AboutDialog(Context context, int theme){            
		super(context, theme);        

		LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(R.layout.about, null); 
        this.setContentView(view);
        
        btn_ok=(Button) view.findViewById(R.id.ok);
        btn_ok.setOnClickListener(new android.view.View.OnClickListener() {
        	@Override
			public void onClick(View v) {
        		AboutDialog.this.hide();
			}
		});
	}     
		
}
