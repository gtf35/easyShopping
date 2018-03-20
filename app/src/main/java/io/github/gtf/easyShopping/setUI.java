package io.github.gtf.easyShopping;
import android.os.Bundle;
import android.preference.PreferenceManager;
import java.util.*;
import android.view.View;
import android.app.*;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.view.View.*;
import android.widget.*;

public class setUI extends BaseActivity 
{
	Activity mActivity;
	StyleTool mStyleTool;
	IconTool mIconTool = new IconTool();
	private int MODE = 1;
	private int COLORMODE = 1;
	private int TAOMALL = 1;
	private int JINGDONG = 2;
	private int AUTO = 3;
	TextView set_style_tb,set_style_jd,set_style_auto,
	         set_icon_1,set_icon_2,set_icon_3,set_icon_4,set_icon_5,set_icon_6;
   
	@Override
    protected void onCreate(Bundle savedInstanceState)
    {
		MODE = PreferenceManager.getDefaultSharedPreferences(this).getInt("MODE", TAOMALL);
		COLORMODE = PreferenceManager.getDefaultSharedPreferences(this).getInt("STYLEMODE", AUTO);
		if(MODE == JINGDONG && COLORMODE == AUTO){
			setTheme(R.style.myTheme_jd);
		} else if (MODE == TAOMALL && COLORMODE == AUTO) {
			setTheme(R.style.myTheme_tb);
		} else if (COLORMODE == JINGDONG){
			setTheme(R.style.myTheme_jd);
		} else if (COLORMODE == TAOMALL){
			setTheme(R.style.myTheme_tb);
		}
		
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setui);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		LinearLayout rootLayout = (LinearLayout) findViewById(R.id.root_layout);
		rootLayout.removeView(toolbar);
		set_style_tb = (TextView)findViewById(R.id.set_style_tb);
		set_style_jd = (TextView)findViewById(R.id.set_style_jd);
		set_style_auto = (TextView)findViewById(R.id.set_style_auto);
		
		set_icon_1 = (TextView)findViewById(R.id.set_icon_1); 
		set_icon_2 = (TextView)findViewById(R.id.set_icon_2);
		set_icon_3 = (TextView)findViewById(R.id.set_icon_3);
		set_icon_4 = (TextView)findViewById(R.id.set_icon_4);
		set_icon_5 = (TextView)findViewById(R.id.set_icon_5);
		set_icon_6 = (TextView)findViewById(R.id.set_icon_6);
		
		initButtonTextandColor();
		mIconTool.init(this);
		Toast.makeText(setUI.this,"提示：界面可上下滑动",Toast.LENGTH_SHORT).show();
		if(COLORMODE == AUTO){
			set_style_auto.setText("使用中");
		}  else if (COLORMODE == JINGDONG){
			set_style_jd.setText("使用中");
		} else if (COLORMODE == TAOMALL){
			set_style_tb.setText("使用中");
		}
		
		int key = mIconTool.Getshp(this);
		if(key ==1){
			set_icon_1.setText("使用中");
		} else if(key == 2){
			set_icon_2.setText("使用中");
		} else if(key == 3){
			set_icon_3.setText("使用中");
		} else if(key == 4){
			set_icon_4.setText("使用中");
		} else if(key == 5){
			set_icon_5.setText("使用中");
		} else if(key == 6){
			set_icon_6.setText("使用中");
		}
		
		set_style_tb.setOnClickListener( new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					//setTBstyle(setUI.this);
					initButtonTextandColor();
					set_style_tb.setText("使用中");
				}
				
			
		});
		
		set_style_jd.setOnClickListener( new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					//setJDstyle(setUI.this);
					initButtonTextandColor();
					set_style_jd.setText("使用中");
				}


			});
		set_style_auto.setOnClickListener( new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					//setAutoStyle(setUI.this);
					initButtonTextandColor();
					set_style_auto.setText("使用中");
				}


			});
			
		set_icon_1.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					mIconTool.SetAndRememberIcon(setUI.this,1);
					initSetIconButton();
					set_icon_1.setText("使用中");
				}
			});
		
		set_icon_2.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					mIconTool.SetAndRememberIcon(setUI.this,2);
					initSetIconButton();
					set_icon_2.setText("使用中");
				}
			});
		
		set_icon_3.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					mIconTool.SetAndRememberIcon(setUI.this,3);
					initSetIconButton();
					set_icon_3.setText("使用中");
				}
			});

		set_icon_4.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					mIconTool.SetAndRememberIcon(setUI.this,4);
					initSetIconButton();
					set_icon_4.setText("使用中");
				}
			});
		set_icon_5.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					mIconTool.SetAndRememberIcon(setUI.this,5);
					initSetIconButton();
					set_icon_5.setText("使用中");
				}
			});

		set_icon_6.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					mIconTool.SetAndRememberIcon(setUI.this,6);
					initSetIconButton();
					set_icon_6.setText("使用中");
				}
			});
		
    }

	@Override
	protected void onResume()
	{
		
		super.onResume();
	}
	
	
	
	
	public void setJDstyle(Activity activity){
		activity.setTheme(R.style.myTheme_jd);
		int color_main = MyApplication.getContext(). getResources().getColor(R.color.colorJD_main);
		int color_list = MyApplication.getContext(). getResources().getColor(R.color.colorJD_list);
		int color_nav = MyApplication.getContext(). getResources().getColor(R.color.colorJD_nav);
		setToolbarColor(activity,color_main);
		
		PreferenceManager.getDefaultSharedPreferences(activity).edit().putInt("STYLEMODE",JINGDONG).commit();
	}

	public void setTBstyle(Activity activity){
		activity.setTheme(R.style.myTheme_tb);
		int color_main = MyApplication.getContext(). getResources().getColor(R.color.colorTB_main);
		int color_list = MyApplication.getContext(). getResources().getColor(R.color.colorTB_list);
		int color_nav = MyApplication.getContext(). getResources().getColor(R.color.colorTB_nav);
		setToolbarColor(activity,color_main);
		PreferenceManager.getDefaultSharedPreferences(activity).edit().putInt("STYLEMODE",TAOMALL).commit();
	}

	public void setAutoStyle(Activity activity){
		PreferenceManager.getDefaultSharedPreferences(activity).edit().putInt("STYLEMODE",AUTO).commit();
		
	}
	
	private void setToolbarColor(Activity activity,int color){
		View view = activity.getWindow().getDecorView();
		Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
		toolbar.setBackgroundColor(color);
	}
	
	void initButtonTextandColor(){
		
		set_style_jd.setText("使用");
		//set_style_jd.setBackgroundColor(MyApplication.getContext(). getResources().getColor(R.color.colorJD_main));
		set_style_tb.setText("使用");
		//set_style_tb.setBackgroundColor(MyApplication.getContext(). getResources().getColor(R.color.colorTB_main));
		set_style_auto.setText("使用");
		//set_style_auto.setBackgroundColor(R.drawable.auto_button);
		
	}
	
	void initSetIconButton(){
		set_icon_1.setText("使用");
		set_icon_2.setText("使用");
		set_icon_3.setText("使用");
		set_icon_4.setText("使用");
		set_icon_5.setText("使用");
		set_icon_6.setText("使用");
	}
}

