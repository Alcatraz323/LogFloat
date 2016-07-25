package com.alcatraz.logcat;
import android.content.*;

public class LFBroadcastReceiver extends BroadcastReceiver
{

	@Override
	public void onReceive(Context p1, Intent p2)
	{
		// TODO: Implement this method
		Intent i=new Intent();
		i.setClassName("com.alcatraz.logcat","com.alcatraz.logcat.MainActivity");
		p1.startActivity(i);
	}
	
}
