package com.alcatraz.logcat;
import android.widget.*;
import android.view.*;
import java.util.*;
import android.content.*;
import android.graphics.*;

public class ListViewAdapter extends BaseAdapter
{
	List<String> content;
	LayoutInflater lf;
	String filter="Full";
	public ListViewAdapter(Context c, List<String> content)
	{
		this.content=content;
		lf=(LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	@Override
	public int getCount()
	{
		// TODO: Implement this method
		return content.size();
	}

	@Override
	public Object getItem(int p1)
	{
		// TODO: Implement this method
		return content.get(p1);
	}

	@Override
	public long getItemId(int p1)
	{
		// TODO: Implement this method
		return p1;
	}

	@Override
	public View getView(int p1, View p2, ViewGroup p3)
	{
		if(p2==null){
			p2=lf.inflate(R.layout.list_board,null);
		}
		String[] data=getDigestedContentline(content.get(p1));
		TextView txv=(TextView) p2.findViewById(R.id.listboardTextView_time);
		switch(filter){
			
			case "E":
				if(data[4].equals("E")){
					txv.setText(content.get(p1)+"																										");
					setColor(data,txv);
					return p2;
				}
				break;
			case "D":
				if(data[4].equals("D")){
					txv.setText(content.get(p1)+"																										");
					setColor(data,txv);
					return p2;
				}
				break;
			case "I":
				if(data[4].equals("I")){
					txv.setText(content.get(p1)+"																										");
					setColor(data,txv);
					return p2;
				}
				break;
			case "W":
				if(data[4].equals("W")){
					txv.setText(content.get(p1)+"																										");
					setColor(data,txv);
					return p2;
				}
				break;
			case "V":
				if(data[4].equals("E")){
					txv.setText(content.get(p1)+"																										");
					setColor(data,txv);
					return p2;
				}
				break;
			case "PID":

				break;
			case "Full":
				txv.setText(content.get(p1)+"																										");
				setColor(data,txv);
				return p2;
		}
		
		return p2;
		//head.setText(data[0]+" "+data[1]+" "+data[2]+" "+data[3]+" "+data[4]);
		/*from.setText(data[5]+"     ");
		 //content.setText(data[5]);
		 if(data[4].equals("E")){
		 head.setTextColor(Color.RED);
		 from.setTextColor(Color.RED);
		 content.setTextColor(Color.RED);
		 }
		 */
		// TODO: Implement this method
		
	}
	public void addItem(String item)
	{  
		content.add(item);  
	}  
	public void remItem(String item)
	{  
		content.remove(item);  
	}  
	public void remItembypos(int pos)
	{  
		content.remove(pos);  
	} 
	public String[] getDigestedContentline(String line)
	{
		return line.split(" ",8);
	}
	public void setFilter(String flag){
		this.filter=flag;
	}
	private void setColor(String[] data,TextView txv){
		try{
			switch(data[4]){
				case "E":
					txv.setTextColor(Color.RED);
					break;
				case "D":
					txv.setTextColor(Color.parseColor("#64FFDA"));
					break;
				case "I":
					txv.setTextColor(Color.parseColor("#76FF03"));
					break;
				case "W":
					txv.setTextColor(Color.parseColor("#ff9800"));
					break;
				case "V":

					break;
				default:
					switch(data[6]){
						case "E":
							txv.setTextColor(Color.RED);
							break;
						case "D":
							txv.setTextColor(Color.parseColor("#64FFDA"));
							break;
						case "I":
							txv.setTextColor(Color.parseColor("#76FF03"));
							break;
						case "W":
							txv.setTextColor(Color.parseColor("#ff9800"));
							break;
					}
					break;
			}
		}catch(Exception e){

		}
	}
}
