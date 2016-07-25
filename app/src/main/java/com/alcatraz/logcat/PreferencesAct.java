package com.alcatraz.logcat;
import android.preference.*;
import android.os.*;
import com.alcatraz.support.v4.appcompat.*;
import android.graphics.*;
import android.support.v7.widget.*;
import android.view.View.*;
import android.view.*;
import android.content.*;
import android.app.*;

public class PreferencesAct extends PreferenceActivity
{
	Toolbar tb;
	EditTextPreference ep1;
	EditTextPreference ep2;
	CheckBoxPreference cbpf;
	ListPreference lp;
	String summary_ep1;
	String summary_ep2;
	String summary_lp;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		Bundle b=getIntent().getExtras();
		boolean per=b.getBoolean("per");
		StatusBarUtil.setColor(PreferencesAct.this,Color.parseColor("#3f51b5"));
		setContentView(R.layout.preferences_cap);
		tb=(Toolbar) findViewById(R.id.preferencescapToolbar1);
		tb.setTitle(R.string.action_pref);
		tb.setNavigationOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					finish();
					// TODO: Implement this method
				}
			});
		addPreferencesFromResource(R.layout.preferences_content);
		readSettingsSp();
		ep1=(EditTextPreference) findPreference("default_cmd");
		ep2=(EditTextPreference) findPreference("max");
		lp=(ListPreference) findPreference("startmode");
		cbpf=(CheckBoxPreference) findPreference("root");
		if(!per){
			cbpf.setEnabled(false);
		}
		ep1.setSummary(summary_ep1);
		ep2.setSummary(summary_ep2);
		if(summary_lp.equals("floatwindow")){
			lp.setSummary(R.string.pref_list_item1);
		}else{
			lp.setSummary(R.string.pref_list_item2);
		}
		ep1.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener(){

				@Override
				public boolean onPreferenceChange(Preference p1, Object p2)
				{
					if(!p2.toString().equals("")){
						ep1.setSummary(p2.toString());
						return true;
					}
					
					// TODO: Implement this method
					return false;
				}
			});
		ep2.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener(){

				@Override
				public boolean onPreferenceChange(Preference p1, Object p2)
				{
					
					if(!p2.toString().equals("")){
						ep2.setSummary(p2.toString());
						return true;
					}
					// TODO: Implement this method
					return false;
				}
			});
		lp.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener(){

				@Override
				public boolean onPreferenceChange(Preference p1, Object p2)
				{
					if(p2.toString().equals("floatwindow")){
						lp.setSummary(R.string.pref_list_item1);
					}else{
						lp.setSummary(R.string.pref_list_item2);
					}
					// TODO: Implement this method
					return true;
				}
			});
	}
	public void readSettingsSp(){
		SharedPreferences spf=getSharedPreferences(getDefaultSpf(),Activity.MODE_PRIVATE);
		summary_ep1=spf.getString("default_cmd","logcat -v threadtime");
		summary_ep2=spf.getString("max","32");
		summary_lp=spf.getString("startmode","floatwindow");
	}
	private String getDefaultSpf(){
		return getPackageName()+"_preferences";
	}
}
