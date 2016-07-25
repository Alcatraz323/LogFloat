package com.alcatraz.logcat;
import android.animation.*;
import android.app.*;
import android.content.*;
import android.graphics.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.view.WindowManager.*;
import android.view.animation.*;
import android.widget.*;
import android.support.v4.widget.*;
import android.support.v7.app.*;
import android.support.v7.widget.*;
import java.io.*;
import com.alcatraz.support.v4.appcompat.*;
import java.util.*;
import android.util.*;
import android.widget.SeekBar.*;
import android.widget.AdapterView.*;

public class MainFloatWindow extends Service implements OnClickListener
{


	@Override
	public void onClick(View p1)
	{
		switch(p1.getId()){
			case R.id.floatbarImageButton1:
				dl.openDrawer(Gravity.LEFT);
				break;
			case R.id.floatbarImageButton2:

				break;
			case R.id.floatbarImageButton3:
				View v=LayoutInflater.from(this).inflate(R.layout.ad_filter,null);
				ListView lw=(ListView) v.findViewById(R.id.adfilterListView1);
				String[] s={"Full","E","D","W","V","I"};
				ListAdapter g=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,s);
				lw.setAdapter(g);
				final android.app.AlertDialog fil=new android.app.AlertDialog.Builder(MainFloatWindow.this)
					.setTitle(R.string.ad_title_2)
					.setView(v)
					.create();
				fil.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
				lw.setOnItemClickListener(new OnItemClickListener(){

						@Override
						public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4)
						{
							switch(p1.getItemAtPosition(p3).toString()){
								case "E":
									lad.setFilter("E");
									filtitle.setText("E");
									fil.dismiss();
									break;
								case "D":
									lad.setFilter("D");
									filtitle.setText("D");
									fil.dismiss();
									break;
								case "I":
									lad.setFilter("I");
									filtitle.setText("I");
									fil.dismiss();
									break;
								case "W":
									lad.setFilter("W");
									filtitle.setText("W");
									fil.dismiss();
									break;
								case "V":
									lad.setFilter("V");
									filtitle.setText("V");
									fil.dismiss();
									break;
								case "PID":

									break;
								case "Full":
									lad.setFilter("Full");
									filtitle.setText("Full");
									fil.dismiss();
									break;
								default:

									break;
							}
							// TODO: Implement this method
						}
					});
				
				
				fil.show();
				break;
			case R.id.floatbarImageButton4:
				if(dab==0){
					isPaused=true;
					
					dab=1;
					ibtn_4.setImageResource(R.drawable.ic_play);
				}else{
					isPaused=false;
					dab=0;
					ibtn_4.setImageResource(R.drawable.ic_pause);
					
				}
				break;
			case R.id.floatterminalImageButton1:
				/*if(!t.isAlive()){
				 cmd=tet.getText().toString();
				 if(cmd==""){

				 }else{
				 t.start();
				 }
				 }else{
				 */
				cmd=tet.getText().toString();
				try{
					dos1.writeBytes(cmd+"\n");
					dos1.flush();
				}catch(IOException e){
					strToast(e.toString());
				}
				//}
				break;
			case R.id.floatterminalEditText1:
				View f=LayoutInflater.from(this).inflate(R.layout.ad_input,null);
				final EditText et=(EditText) f.findViewById(R.id.adinputEditText1);
				et.setText(tet.getText());
				android.app.AlertDialog input=new android.app.AlertDialog.Builder(MainFloatWindow.this)
					.setTitle(R.string.ad_title)
					.setView(f)
					.setPositiveButton(R.string.ad_pb,new DialogInterface.OnClickListener(){

						@Override
						public void onClick(DialogInterface p1, int p2)
						{
							tet.setText(et.getText());
							// TODO: Implement this method
						}
					})
					.create();
				input.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
				input.show();
				break;
		}
		// TODO: Implement this method
	}

	WindowManager wm;
	WindowManager.LayoutParams parmas;
	View rootv;
	RelativeLayout rl;
	ImageButton btn;
	DrawerLayout dl;
	LinearLayout ll;
	LinearLayout ll1;
	LinearLayout llt;
	boolean hasRoot=false;
	TextView txv;
	ListView lw;
	ListView lw1;
	TextView filtitle;
	ImageButton ibtn_1;
	ImageButton ibtn_2;
	ImageButton ibtn_3;
	ImageButton ibtn_4;
	ImageButton tbtn_1;
	EditText tet;
	SeekBar sb;
	Handler h;
	int dab=0;
	boolean flag=true;
	boolean isPaused=false;
	ListViewAdapter lad;
	boolean runForLogCat=true;
	ListAdapter lad1;
	LinkedList logs=new LinkedList();
	LinkedList logs_b=new LinkedList();
	String action[];
	Thread t;
	String cacheline;
	DataOutputStream dos1;
	/*
	 ARGS_________________*/
	String cmd;
	boolean useroot;
	int max;

	/*__________________::*/

	@Override
	public IBinder onBind(Intent p1)
	{
		// TODO: Implement this method
		return null;
	}

	@Override
	public void onStart(Intent intent, int startId)
	{
		try{
			runForLogCat=intent.getExtras().getBoolean("run");
		}catch(Exception e){

		}


		h=new Handler(){

			@Override
			public void handleMessage(Message msg)
			{
				if(runForLogCat){
					refresh();
				}else{
					terminalRef(cacheline);
				}

				// TODO: Implement this method
				super.handleMessage(msg);
			}

		};
		// TODO: Implement this method

		readSettings();
		if(runForLogCat){
		action=new String[]{getString(R.string.action_pref),getString(R.string.action_shutdown),getString(R.string.action_terminal),getString(R.string.action_about)};
		}else{
			action=new String[]{getString(R.string.action_pref),getString(R.string.action_shutdown),getString(R.string.action_home),getString(R.string.action_about)};
		}
		createview();
		if(!upgradeRootPermission(getPackageCodePath())){
			android.app.AlertDialog a=new android.app.AlertDialog.Builder(this)
				.setTitle(R.string.root_fail_t)
				.setMessage(R.string.root_fail_m)
				.setPositiveButton(R.string.ad_pb,null).create();
			a.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
			a.show();
		}else{
			hasRoot=true;
		} 
		t=new Thread(new Runnable(){

				@Override
				public void run()
				{
					if(runForLogCat){
						if(hasRoot&&useroot){
							try{
								DataOutputStream dos;
								java.lang.Process process = Runtime.getRuntime().exec("su");
								dos=new DataOutputStream(process.getOutputStream());
								dos.writeBytes(cmd+"\n");
								dos.flush();
								BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
								String line;
								int i=0;
								while((line=bufferedReader.readLine())!=null&&flag){
									if(!isPaused){
									i++;
									if(i>=max){
										logs.add(line);
										h.sendMessage(h.obtainMessage());
										logs.removeFirst();
									}else{

										logs.add(line);
										h.sendMessage(h.obtainMessage());
									}
									/*}else{
										i++;
										if(i>=max){
											logs_b.add(line);
											h.sendMessage(h.obtainMessage());
											logs.removeFirst();
										}else{

											logs_b.add(line);
											h.sendMessage(h.obtainMessage());
										}*/
									}
								}
								if(dos!=null){
									dos.close();
								}

								//try{
								//process.waitFor();
								//}catch(InterruptedException e){}





							}catch(IOException e){
								e.printStackTrace();
							}
						}else{
							try{
								java.lang.Process process = Runtime.getRuntime().exec(cmd);
								BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
								String line;
								int i=0;
								while((line=bufferedReader.readLine())!=null&&flag){
									if(!isPaused){
									i++;
									if(i>=max){
										logs.add(line);
										h.sendMessage(h.obtainMessage());
										logs.removeFirst();
									}else{
										logs.add(line);
										h.sendMessage(h.obtainMessage());
									}
									/*}else{
										i++;
										if(i>=max){
											logs_b.add(line);
											h.sendMessage(h.obtainMessage());
											logs.removeFirst();
										}else{

											logs_b.add(line);
											h.sendMessage(h.obtainMessage());
										}*/
									}
								}



							}catch(IOException e){
								e.printStackTrace();
							}
						}
						// TODO: Implement this method
					}else{
						try{

							java.lang.Process process = Runtime.getRuntime().exec("su");
							dos1=new DataOutputStream(process.getOutputStream());

							BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
							String line;
							while((line=bufferedReader.readLine())!=null&&flag){
								cacheline=line;
								h.sendMessage(h.obtainMessage());
							}

							//try{
							//process.waitFor();
							//}catch(InterruptedException e){}





						}catch(IOException e){
							e.printStackTrace();
						}
					}
				}

			});
		//if(runForLogCat){
		t.start();
		//}


		// TODO: Implement this method
		super.onStart(intent,startId);
	}

	@Override
	public void onCreate()
	{
		/*
		 h=new Handler(){

		 @Override
		 public void handleMessage(Message msg)
		 {
		 if(runForLogCat){
		 refresh();
		 }else{
		 terminalRef(cacheline);
		 }

		 // TODO: Implement this method
		 super.handleMessage(msg);
		 }

		 };
		 // TODO: Implement this method
		 */

		super.onCreate();
		/*
		 readSettings();
		 action=new String[]{getString(R.string.action_pref),getString(R.string.action_shutdown),getString(R.string.action_terminal),getString(R.string.action_about)};
		 createview();
		 if(!upgradeRootPermission(getPackageCodePath())){
		 android.app.AlertDialog a=new android.app.AlertDialog.Builder(this)
		 .setTitle(R.string.root_fail_t)
		 .setMessage(R.string.root_fail_m)
		 .setPositiveButton(R.string.ad_pb,null).create();
		 a.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
		 a.show();
		 }else{
		 hasRoot=true;
		 } 

		 if(runForLogCat){
		 t.start();
		 }
		 */
	}
	public void createview()
	{
		parmas=new WindowManager.LayoutParams();
		wm=(WindowManager)getApplication().getSystemService(getApplication().WINDOW_SERVICE);
		parmas.type=LayoutParams.TYPE_SYSTEM_ALERT; 
        parmas.format=PixelFormat.RGBA_8888;
        parmas.flags=LayoutParams.FLAG_NOT_FOCUSABLE;
        parmas.gravity=Gravity.LEFT|Gravity.TOP;       
        parmas.x=0;
        parmas.y=0;
        parmas.width=WindowManager.LayoutParams.WRAP_CONTENT;
        parmas.height=WindowManager.LayoutParams.WRAP_CONTENT;
        LayoutInflater inflater = LayoutInflater.from(getApplication());
		rl=(RelativeLayout) inflater.inflate(R.layout.float_window,null);
		dl=(DrawerLayout) rl.findViewById(R.id.floatwindowDrawerLayout1);
        ll=(LinearLayout) rl.findViewById(R.id.floatwindowLinearLayout1);
		ll1=(LinearLayout) rl.findViewById(R.id.floatwindowLinearLayout);
		llt=(LinearLayout) rl.findViewById(R.id.floatterminalLinearLayout);
		if(!runForLogCat){
			ll1.setVisibility(View.GONE);
			llt.setVisibility(View.VISIBLE);
		}
		txv=(TextView) rl.findViewById(R.id.floatterminalTextView1);
		tet=(EditText) rl.findViewById(R.id.floatterminalEditText1);
		tet.setOnClickListener(this);
		ibtn_1=(ImageButton) rl.findViewById(R.id.floatbarImageButton1);
		ibtn_2=(ImageButton) rl.findViewById(R.id.floatbarImageButton2);
		ibtn_3=(ImageButton) rl.findViewById(R.id.floatbarImageButton3);
		ibtn_4=(ImageButton) rl.findViewById(R.id.floatbarImageButton4);
		tbtn_1=(ImageButton) rl.findViewById(R.id.floatterminalImageButton1);
		filtitle=(TextView) rl.findViewById(R.id.floatbarTextView);
		ibtn_2.setVisibility(View.GONE);
		ibtn_1.setOnClickListener(this);
		ibtn_2.setOnClickListener(this);
		ibtn_3.setOnClickListener(this);
		ibtn_4.setOnClickListener(this);
		tbtn_1.setOnClickListener(this);
		lw=(ListView) rl.findViewById(R.id.floatwindowListView1);
		lw1=(ListView) rl.findViewById(R.id.floatwindowListView2);
		sb=(SeekBar) rl.findViewById(R.id.floatwindowSeekBar1);
		sb.setProgress((int)ll.getAlpha()*100);
		sb.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){

				@Override
				public void onProgressChanged(SeekBar p1, int p2, boolean p3)
				{

					if(p2!=0){
						ll.setAlpha(p2/100f);
					}else{
						ll.setAlpha(1);
						p1.setProgress(100);
					}

					// TODO: Implement this method
				}

				@Override
				public void onStartTrackingTouch(SeekBar p1)
				{
					// TODO: Implement this method
				}

				@Override
				public void onStopTrackingTouch(SeekBar p1)
				{
					// TODO: Implement this method
				}
			});
		lad=new ListViewAdapter(this,logs);
		lad1=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,action);
		lw1.setAdapter(lad1);
		lw1.setOnItemClickListener(new OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4)
				{
					String act=p1.getItemAtPosition(p3).toString();
					if(act.equals(action[0])){
						Intent i=new Intent(MainFloatWindow.this,PreferencesAct.class);
						i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						i.putExtra("per",hasRoot);
						startActivity(i);
					}else if(act.equals(action[1])){
						stopSelf();
					}else if(act.equals(action[2])){
						if(runForLogCat){
						stopSelf();
						Intent in=new Intent(MainFloatWindow.this,MainFloatWindow.class);
						in.putExtra("run",false);
						startService(in);
						}else{
							stopSelf();
							Intent in=new Intent(MainFloatWindow.this,MainFloatWindow.class);
							in.putExtra("run",true);
							startService(in);
						}
					}else{
						android.app.AlertDialog a=new android.app.AlertDialog.Builder(MainFloatWindow.this)
							.setTitle(R.string.action_about)
							.setMessage("Copyright© Alcatraz"+"\n"+"Personal.Creation(个人作品)")
							.create();
						a.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
						a.show();
					}
					// TODO: Implement this method
				}
			});
		lw.setAdapter(lad);
        wm.addView(rl,parmas);
        btn=(ImageButton)rl.findViewById(R.id.floatwindowImageButton1);
		dl.measure(View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.EXACTLY),View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.EXACTLY));
        rl.measure(View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED),View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED));
        btn.setOnTouchListener(new OnTouchListener() 
			{

				@Override
				public boolean onTouch(View v, MotionEvent event) 
				{
					if(ll.getVisibility()==View.VISIBLE){
						parmas.x=(int) event.getRawX()+btn.getMeasuredWidth()/2-100-dl.getWidth();
						parmas.y=(int) event.getRawY()-btn.getMeasuredHeight()/2-60;
						wm.updateViewLayout(rl,parmas);
					}else{
						parmas.x=(int) event.getRawX()+btn.getMeasuredWidth()/2-100;
						parmas.y=(int) event.getRawY()-btn.getMeasuredHeight()/2-60;
						wm.updateViewLayout(rl,parmas);
					}
					return false;
				}
			});	

        btn.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					if(ll.getVisibility()==View.GONE){
						ll.setVisibility(View.VISIBLE);
					}else{
						ll.setVisibility(View.GONE);
					}
					// TODO: Implement this method
				}
			});



	}
	public static boolean upgradeRootPermission(String pkgCodePath)
	{
		java.lang.Process process = null;
		DataOutputStream os = null;
		try{
			String cmd="chmod 777 "+pkgCodePath;
			process=Runtime.getRuntime().exec("su");
			os=new DataOutputStream(process.getOutputStream());
			os.writeBytes(cmd+"\n");
			os.writeBytes("exit\n");
			os.flush();
			process.waitFor();
		}catch(Exception e){
			return false;
		}
		finally{
			try{
				if(os!=null){
					os.close();
				}
				process.destroy();
			}catch(Exception e){
			}
		}
		try{
			return process.waitFor()==0;
		}catch(InterruptedException e){}
		return true;
	}
	@Override
	public void onDestroy()
	{
		// TODO: Implement this method
		super.onDestroy();
		if(rl!=null){
			wm.removeView(rl);
		}
	}
	public void refresh()
	{
		lad.notifyDataSetChanged();

	}
	public void strToast(String content)
	{
		Toast.makeText(MainFloatWindow.this,content,Toast.LENGTH_SHORT).show();
	}
	public void readSettings()
	{
		SharedPreferences spf=getSharedPreferences(getDefaultSpf(),Activity.MODE_PRIVATE);
		cmd=spf.getString("default_cmd","logcat -v threadtime");
		useroot=spf.getBoolean("root",true);
		max=Integer.valueOf(spf.getString("max","32"));

	}
	private String getDefaultSpf()
	{
		return getPackageName()+"_preferences";
	}
	private void terminalRef(String line)
	{
		txv.append("\n"+line);
	}
}
