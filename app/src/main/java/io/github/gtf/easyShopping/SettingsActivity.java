package io.github.gtf.easyShopping;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.*;
import java.net.*;
import android.app.*;
import android.graphics.*;
import android.view.*;
import android.content.*;
import android.os.*;
import android.widget.*;
import java.util.*;
import android.renderscript.*;
import android.net.*;
import android.content.pm.*;
import android.view.View.*;
import java.io.*;
import com.pgyersdk.update.*;
import android.preference.*;
import com.pgyersdk.feedback.*;
import com.pgyersdk.activity.*;

public class SettingsActivity extends Activity
{

	private boolean xianyuOK;
	private boolean jingdongOK;
	private Toolbar toolbar;

	@Override
    protected void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
	
	
	
		this.getFragmentManager().beginTransaction()
			.replace(android.R.id.content, new SettingsFragment())
			.commit();	
			
	}

	
    @Override
    public void onBackPressed()
	{
		SharedPreferences shp = PreferenceManager.getDefaultSharedPreferences(SettingsActivity.this);
		xianyuOK = shp.getBoolean("check_xianyu", false);
		jingdongOK = shp.getBoolean("check_jingdong",false);
		//Toast.makeText(MyApplication.getContext(),"咸鱼："+xianyuOK+" \n京东："+jingdongOK,Toast.LENGTH_LONG).show();
		if(xianyuOK && jingdongOK){
			Toast.makeText(MyApplication.getContext(),"两个选项只能选一个哟,检查一下啦~",Toast.LENGTH_SHORT).show();
		}else{
			Toast.makeText(MyApplication.getContext(),"设置保存喽，欧耶，萌萌哒！",Toast.LENGTH_SHORT).show();
			Intent backMain = new Intent(MyApplication.getContext(),Main.class);
			startActivity(backMain);
		}
		System.out.println("按下了back键   onBackPressed()");    	
    }
	
	public void mFeedBack(){
			// 以对话框的形式弹出
			PgyFeedback.getInstance().showDialog(SettingsActivity.this);

// 以Activity的形式打开，这种情况下必须在AndroidManifest.xml配置FeedbackActivity
// 打开沉浸式,默认为false
			//FeedbackActivity.setBarImmersive(true);
			//PgyFeedback.getInstance().showActivity(SettingsActivity.this);
	}
	
	public void mUpdata(){
		PgyUpdateManager.setIsForced(false); //设置是否强制更新。true为强制更新；false为不强制更新（默认值）。
		PgyUpdateManager.register(this);
	}
	
}
