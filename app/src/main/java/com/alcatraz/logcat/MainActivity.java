package com.alcatraz.logcat;

import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class MainActivity extends Activity 
{
	public static final String ACTION_START_SERVICE="service";
	public static final String ACTION_START_TACTIVITY="activity";
	String start_flag;
	View ad;
	boolean boot;
	/*
	Handler h=new Handler(){

		@Override
		public void handleMessage(Message msg)
		{
			// TODO: Implement this method
			super.handleMessage(msg);
			
			
		}
		
	};
	*/
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
		
		
		
        super.onCreate(savedInstanceState);
		try{
			
				if(boot){
					
				}else{
					finish();
				}
			
				
			
		}catch(Exception e){
			
		}
        getSettings();
		
		/*
		Thread t=new Thread(new Runnable(){

				@Override
				public void run()
				{
					h.sendMessage(h.obtainMessage());
					// TODO: Implement this method
				}
			});
		
		
			t.start();*/
		if(start_flag.equals(ACTION_START_SERVICE)){
			Intent i=new Intent(MainActivity.this,MainFloatWindow.class);
			startService(i);
		}else{
			Intent i=new Intent(MainActivity.this,TrueMActivity.class);
			startActivity(i);
		}
		finish();
    }
	private void getSettings(){
		SharedPreferences spf=getSharedPreferences(getDefaultSpf(),Activity.MODE_PRIVATE);
		start_flag=spf.getString("start_flag",ACTION_START_SERVICE);
		boot=spf.getBoolean("runaftboot",false);
	}
	private String getDefaultSpf(){
		return getPackageName()+"_preferences";
	}
	public void strToast(String content){
		Toast.makeText(MainActivity.this,content,Toast.LENGTH_SHORT).show();
	}
	
}
