package io.github.gtf.easyShopping;
import android.preference.*;
import android.app.*;
import android.support.v7.widget.*;
import android.support.v4.content.*;
import android.content.*;
import android.view.*;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import java.security.acl.*;
import android.support.design.widget.*;

public class StyleTool
{
	private int MODE = 1;
	private int COLORMODE = 1;
	private int TAOMALL = 1;
	private int JINGDONG = 2;
	private int AUTO = 3;
	
	
	
	
	
	public void initStyle(Activity activity,RelativeLayout nav ,LinearLayout list){
		MODE = PreferenceManager.getDefaultSharedPreferences(activity).getInt("MODE",TAOMALL);
		COLORMODE = PreferenceManager.getDefaultSharedPreferences(activity).getInt("STYLEMODE", AUTO);
		if(COLORMODE == AUTO){
			if(MODE == TAOMALL){
				setTBstyle(activity,nav,list);
			} else {
				setJDstyle(activity,nav,list);
			}
			
		} 
		if (COLORMODE == JINGDONG){
			setJDstyle(activity,nav,list);
		} 
		if (COLORMODE == TAOMALL){
			setTBstyle(activity,nav,list);
		}
	}

	public void setJDstyle(Activity activity,RelativeLayout nav ,LinearLayout list){
		activity.setTheme(R.style.myTheme_jd);
		int color_main = MyApplication.getContext(). getResources().getColor(R.color.colorJD_main);
		int color_list = MyApplication.getContext(). getResources().getColor(R.color.colorJD_list);
		int color_nav = MyApplication.getContext(). getResources().getColor(R.color.colorJD_nav);
		setToolbarColor(activity, color_main);
		setNavColor(activity, color_nav,nav);
		setListColor(activity, color_list,list);
		//PreferenceManager.getDefaultSharedPreferences(activity).edit().putInt("STYLEMODE",JINGDONG).commit();
	}

	public void setTBstyle(Activity activity,RelativeLayout nav ,LinearLayout list){
		activity.setTheme(R.style.myTheme_tb);
		int color_main = MyApplication.getContext(). getResources().getColor(R.color.colorTB_main);
		int color_list = MyApplication.getContext(). getResources().getColor(R.color.colorTB_list);
		int color_nav = MyApplication.getContext(). getResources().getColor(R.color.colorTB_nav);
		setToolbarColor(activity,color_main);
		setNavColor(activity, color_nav,nav);
		setListColor(activity, color_list,list);
		//PreferenceManager.getDefaultSharedPreferences(activity).edit().putInt("STYLEMODE",TAOMALL).commit();
	}
	
	public void setAutoStyle(Activity activity,RelativeLayout nav ,LinearLayout list){
		//PreferenceManager.getDefaultSharedPreferences(activity).edit().putInt("STYLEMODE",AUTO).commit();
		this.initStyle(activity,nav,list);
	}
	
	private void setToolbarColor(Activity activity,int color){
		View view = activity.getWindow().getDecorView();
		Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
		toolbar.setBackgroundColor(color);
	}
	
	private void setNavColor(Activity activity,int color,RelativeLayout nav){
		nav.setBackgroundColor(color);
	}
	
	private void setListColor(Activity activity,int color,LinearLayout list){
		list.setBackgroundColor(color);
	}
}
