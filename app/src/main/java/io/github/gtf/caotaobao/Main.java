package io.github.gtf.caotaobao;

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


public class Main extends AppCompatActivity
implements NavigationView.OnNavigationItemSelectedListener
{
	WebView mWebView;
	Toolbar toolbar;
	FloatingActionButton fab;
	ProgressDialog mProgressDialog;
	AlertDialog.Builder Dialog;
	Handler mHandler;
	TextView Logo1;
	TextView Logo2;
	View mainView;

	String updata_pan_url = "http://pan.baidu.com/s/1o8zbaFw";
	
	String mTaobaoUrl = "https://m.taobao.com/";
	String mMyTaobaoUrl = "https://h5.m.taobao.com/mlapp/mytaobao.html";
	String mTaobaoWuliuUrl = "https://h5.m.taobao.com/awp/mtb/olist.htm?sta=5#!/awp/mtb/olist.htm?sta=5";
	String mTaobaoGouwuche = "https://h5.m.taobao.com/mlapp/cart.html";
	String mTaobaoDingdan = "https://h5.m.taobao.com/mlapp/olist.html";
	String mTaobaoSoucangjia = "https://h5.m.taobao.com/fav/index.htm";
	String mTaobaoKajuanbao = "https://h5.m.taobao.com/app/hongbao/www/index/index.html";
	String mTaobaoZuji = "https://h5.m.taobao.com/footprint/homev2.html";

	String mTaobaoLiteUrl = "https://m.intl.taobao.com";
	String mTaobaoLiteGouwuche = "https://h5.m.taobao.com/mlapp/cart.html";
	String mTaobaoLiteDengluUrl = "https://login.m.taobao.com/login_oversea.htm";
	String mTaobaoLiteWodedingdan = "https://h5.m.taobao.com/mlapp/olist.html";
	String mTaobaoLiteSoucangjia = "https://h5.m.taobao.com/fav/index.htm";

	int startTime = 0;
	String toolbarTitle = "Taobao";
	boolean HideLogo = true;
	boolean IsAtHome = true;
	boolean IsTaobaoLite = false;
    @Override
    protected void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
		Logo1 = (TextView) findViewById(R.id.Logo1);
		Logo2 = (TextView) findViewById(R.id.Logo2);
		Dialog = new AlertDialog.Builder(this);
        setSupportActionBar(toolbar);
        mWebView = (WebView)findViewById(R.id.mWebView);
		mProgressDialog = new ProgressDialog(this);
        fab = (FloatingActionButton) findViewById(R.id.fab);

		//获取Preferences
		SharedPreferences settingsRead = getSharedPreferences("data", 0);
//取出数据
	    IsTaobaoLite = settingsRead.getBoolean("IsTaobaoLite" , false);
		startTime = settingsRead.getInt("startTime", 0) + 1;	
//打开数据库
		SharedPreferences settings = getSharedPreferences("data", 0);
//处于编辑状态
		SharedPreferences.Editor editor = settings.edit();
//存放数据
		editor.putInt("startTime", startTime);
		editor.putBoolean("IsTaobaoLite", false);
//完成提交
		editor.commit();

        fab.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view)
				{
					IsAtHome = true;
					if(IsTaobaoLite == false){
						mWebView.loadUrl(mTaobaoUrl);
					}else{
						mWebView.loadUrl(mTaobaoLiteUrl);
					}
					
				}
			});

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
			this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
		LoadWebView();
		mWebView.setVisibility(View.GONE);
		//提示dialog
		Dialog.setCancelable(false);
		Dialog.setTitle("免责声明：");
		Dialog.setMessage("该项目仅限学术交流使用，一切权利归淘宝公司所有，请自觉在24小时之内删除！ \n 使用此软件造成的一切风险及后果由使用者本人承担，开发者不承担任何责任!");
		Dialog.setPositiveButton("同意",  new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
				}
			});
		if (startTime == 1){
			Dialog.show();
		}
		mHandler = new Handler(){  
			@Override  
			public void handleMessage(Message msg)
			{  
				if (msg.what == 0x123)
				{
					HideLogo = false;
					Logo1.setVisibility(View.GONE);
					Logo2.setVisibility(View.GONE);
					mWebView.setVisibility(View.VISIBLE);
				}  
			}  
		};  
    }

    @Override
    public void onBackPressed()
	{
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
		{
            drawer.closeDrawer(GravityCompat.START);
        }
		else
		{
			if (IsAtHome)
			{
				showSnackBar("退出？","确定",1);
			}
			else if (mWebView.canGoBack())
			{
				mWebView.goBack();
			}
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
	{
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
	{
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_home)
		{
			mWebView.loadUrl(mTaobaoUrl);
            return true;
        }
		else if (id == R.id.action_exit)
		{
			exitProgrames();
			return true;
		}
		else if (id == R.id.action_reload)
		{
			showSnackBar("刷新ing........"," ",0);
			mWebView.reload();
			return true;
		}

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
	{
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_myTaobao)
		{
            if (IsTaobaoLite == false)
			{
				mWebView.loadUrl(mMyTaobaoUrl);
			}
			else
			{
				showSnackBar("该选项在淘宝国际版中仅用作登录","登录",2);
			}
        }
		else if (id == R.id.nav_gouwuche)
		{
			if (IsTaobaoLite == false)
			{
				mWebView.loadUrl(mTaobaoGouwuche);
			}
			else
			{
				mWebView.loadUrl(mTaobaoLiteGouwuche);
			}
        }
		else if (id == R.id.nav_dingdan)
		{
			if (IsTaobaoLite == false)
			{
				mWebView.loadUrl(mTaobaoDingdan);
			}
			else
			{
				mWebView.loadUrl(mTaobaoLiteWodedingdan);
			}
        }
		else if (id == R.id.nav_kajuanbao)
		{
			mWebView.loadUrl(mTaobaoKajuanbao);
        }
		else if (id == R.id.nav_soucangjia)
		{
			if (IsTaobaoLite == false)
			{
				mWebView.loadUrl(mTaobaoSoucangjia);
			}
			else
			{
				mWebView.loadUrl(mTaobaoLiteSoucangjia);
			}
        }
		else if (id == R.id.nav_wuliu)
		{
			mWebView.loadUrl(mTaobaoWuliuUrl);
        }
		else if (id == R.id.nav_zuji)
		{
			mWebView.loadUrl(mTaobaoZuji);
		}
		else if (id == R.id.nav_mTabaoTypeChange)
		{
			if (IsTaobaoLite == false)
			{
				IsTaobaoLite = true;
//打开数据库
				SharedPreferences settings = getSharedPreferences("data", 0);
//处于编辑状态
				SharedPreferences.Editor editor = settings.edit();
//存放数据
				editor.putBoolean("IsTaobaoLite", true);
//完成提交
				editor.commit();
				IsAtHome = true;
				mWebView.loadUrl(mTaobaoLiteUrl);
			}
			else
			{
				IsTaobaoLite = false;
//打开数据库
				SharedPreferences settings = getSharedPreferences("data", 0);
//处于编辑状态
				SharedPreferences.Editor editor = settings.edit();
//存放数据
				editor.putBoolean("IsTaobaoLite", false);
//完成提交
				editor.commit();
				IsAtHome = true;
				mWebView.loadUrl(mTaobaoUrl);

			}
		}
		else if (id == R.id.nav_Github)
		{
			//从其他浏览器打开
			Intent intent = new Intent();
			intent.setAction(Intent.ACTION_VIEW);
			Uri content_url = Uri.parse("https://www.github.com/gtf35/caoTaobao");
			intent.setData(content_url);
			startActivity(Intent.createChooser(intent, "请选择浏览器"));

		}else if(id == R.id.nav_updata){
			//从其他浏览器打开
			Intent intent = new Intent();
			intent.setAction(Intent.ACTION_VIEW);
			Uri content_url = Uri.parse(updata_pan_url);
			intent.setData(content_url);
			startActivity(Intent.createChooser(intent, "请选择浏览器"));
			
		}

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

	public void exitProgrames()
	{
		Intent startMain = new Intent(Intent.ACTION_MAIN);
		startMain.addCategory(Intent.CATEGORY_HOME);
		startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(startMain);
		android.os.Process.killProcess(android.os.Process.myPid());
	}

	void LoadWebView()
	{
		WebSettings mWebViewSettings = mWebView.getSettings();
		mWebViewSettings.setJavaScriptEnabled(true);  
		//mWebViewSettings.setRenderPriority(RenderPriority.HIGH);
		mWebViewSettings.setAppCacheEnabled(true);
		final String cachePath = getApplicationContext().getDir("cache", Context.MODE_PRIVATE).getPath();
		mWebViewSettings.setAppCachePath(cachePath);
		mWebViewSettings.setAppCacheMaxSize(5 * 1024 * 1024);
		//设置自适应屏幕，两者合用
		mWebViewSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小 
		mWebViewSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
		//缩放操作
		mWebViewSettings.setSupportZoom(false); //支持缩放，默认为true。是下面那个的前提。
		mWebViewSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件	
		mWebViewSettings.setAllowFileAccess(true); //设置可以访问文件 
		mWebViewSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口 
		mWebViewSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
		mWebViewSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
		//优先使用缓存: 
		mWebViewSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); 
		mWebViewSettings.setAppCacheEnabled(true);
		mWebViewSettings.setDatabaseEnabled(true);
		mWebViewSettings.setDomStorageEnabled(true);//开启DOM缓存
		//mWebViewSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
		if (IsTaobaoLite == false)
		{
			mWebView.loadUrl(mTaobaoUrl);
		}
		else
		{
			mWebView.loadUrl(mTaobaoLiteUrl);
		}
		mWebView.setWebChromeClient(new WebChromeClient(){
				@Override
				public void onReceivedTitle(WebView view, String title)
				{
					toolbarTitle = title;
					toolbar.setTitle(toolbarTitle);
				}
			});
		//复写WebViewClient类的shouldOverrideUrlLoading方法
		mWebView.setWebViewClient(new WebViewClient() {
				@Override
				public void onPageStarted(WebView view, String url, Bitmap favicon)
				{
					super.onPageStarted(view, url, favicon);
					//mProgressDialog.show();
					//mProgressDialog.setMessage("加载中……😂😂😂");
					toolbar.setTitle("加载中……");
				}
				@Override
				public void onPageFinished(WebView view, String url)
				{
					super.onPageFinished(view, url);
					mProgressDialog.hide();
					toolbar.setTitle(toolbarTitle);
					if (HideLogo)
					{
						Timer timer = new Timer();
						timer.schedule(new TimerTask(){
								public void run()
								{
									mHandler.sendEmptyMessage(0x123);
								}
							}, 1000);
					}
				}
			});
	}
	/**
     * 展示一个SnackBar
     */
    public void showSnackBar(String message,String button_text,final int action_number) {
        //去掉虚拟按键
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
														 | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION //隐藏虚拟按键栏
														 | View.SYSTEM_UI_FLAG_IMMERSIVE //防止点击屏幕时,隐藏虚拟按键栏又弹了出来
														 );
        final Snackbar snackbar = Snackbar.make(getWindow().getDecorView(), message, Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction(button_text, new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					snackbar.dismiss();
					//隐藏SnackBar时记得恢复隐藏虚拟按键栏,不然屏幕底部会多出一块空白布局出来,和难看
					getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
					if(action_number == 1){
						exitProgrames();
					}else if (action_number ==2){
						mWebView.loadUrl(mTaobaoLiteDengluUrl);
					}
				}
			}).show();
    }

}
