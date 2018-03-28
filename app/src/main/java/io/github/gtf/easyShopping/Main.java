package io.github.gtf.easyShopping;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Build.VERSION;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SearchViewCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;
import java.util.Timer;
import android.os.Message;
import android.os.AsyncTask;
import android.widget.Toast;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.content.DialogInterface;
import android.content.Intent;
import java.util.TimerTask;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.MotionEvent;
import android.view.View.OnTouchListener;
import android.content.pm.PackageManager;
import android.os.Build;
import android.Manifest;
import android.support.v4.view.GravityCompat;
import android.view.Gravity;
import android.graphics.Bitmap;
import android.content.ClipData;
import android.os.Environment;
import java.io.File;
import java.util.Date;
import java.io.InputStream;
import java.net.URL;
import java.net.HttpURLConnection;
import java.io.FileOutputStream;
import android.content.pm.PackageInfo;
import android.view.inputmethod.InputMethodManager;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import com.tencent.bugly.beta.*;
import com.tencent.bugly.crashreport.*;
import android.widget.*;
import android.view.ViewGroup.*;
import android.util.*;
import android.support.v4.content.res.*;



public class Main extends BaseActivity
{
	// system webview
	android.webkit.WebView mWebView , mWebViewLeft , mWebViewLogin;
	// TBS webview
	com.tencent.smtt.sdk.WebView mWebView_TBS , mWebViewLeft_TBS;
	Button btn_leftWebview_back , btn_leftWebview_home , btn_leftWebview_exchange;
	Toolbar toolbar;
	FloatingActionButton fab;
	ProgressDialog mProgressDialog;
	AlertDialog.Builder Dialog;
	Handler mHandler;
	TextView Logo1;
	TextView Logo2;
	Button search_button;
	EditText search_editText , search_editText_toolbar;
	Button search_button_toolbar;
	TextView title_toolbar;
	View mainView;
	TextView nav_title;
	TextView nav_change;
	ImageView nav_btn;
	ClipboardManager manager;
	SearchView searchView;

	String HistoryMainUrl , HistoryLeftUrl;
	String HistoryMainUrl_old;
	String HistoryLeftUrl_old;
	boolean backFromSetting = false , exitByCrash = true , savePage ;
	boolean noPic;
	boolean DEBUG;
	boolean removeTab;
	boolean supportTBS;
	boolean onQuietLogin = false,onQuietLogin_JD = false;
	
	String mTaobaoUrl = "https://m.taobao.com/ ";
	String mMyTaobaoUrl = "https://h5.m.taobao.com/mlapp/mytaobao.html";
	String mTaobaoWuliuUrl = "https://h5.m.taobao.com/awp/mtb/olist.htm?sta=5#!/awp/mtb/olist.htm?sta=5";
	String mTaobaoGouwuche = "https://h5.m.taobao.com/mlapp/cart.html";
	String mTaobaoDingdan = "https://h5.m.taobao.com/mlapp/olist.html";
	String mTaobaoSoucangjia = "https://h5.m.taobao.com/fav/index.htm";
	String mTaobaoKajuanbao = "https://h5.m.taobao.com/app/hongbao/www/index/index.html";
	String mTaobaoZuji = "https://h5.m.taobao.com/footprint/homev2.html";
	String mTaobaoWW ="https://h5.m.taobao.com/ww/index.htm";

	String mTaobaoLiteUrl = "https://m.intl.taobao.com";
	String mTaobaoLiteGouwuche = "https://h5.m.taobao.com/mlapp/cart.html";
	String mTaobaoLiteDengluUrl = "https://login.m.taobao.com/login_oversea.htm";
	String mTaobaoLiteWodedingdan = "https://h5.m.taobao.com/mlapp/olist.html";
	String mTaobaoLiteSoucangjia = "https://h5.m.taobao.com/fav/index.htm";

	String mJDUrl = "https://m.jd.com";
	String mMyJD = "https://home.m.jd.com/myJd/newhome.action";
	String mJDGouwuce = "https://p.m.jd.com/cart/cart.action";
	String mJDFenlei = "https://so.m.jd.com/category/all.html";
	String mJDFaxian = "https://h5.m.jd.com/active/faxian/list/article-list.html";
	String mJDDingdan = "https://wqs.jd.com/order/orderlist_merge.shtml";
	String mJDGuanzhushangpin = "https://home.m.jd.com/myJd/myFocus/newFocusWare.actionv2";
	String mJDGuanzhudianpu = "https://wqs.jd.com/my/fav/shop_fav.shtml";
	String mJDHistory = "https://home.m.jd.com/myJd/history/wareHistory.action";

	String mXianyuUrl = "http://www.xianyuso.com/";
	String leftWebviewHomeUrl = "http://yanshao.meizhevip.cn";

	int startTime = 0;
	int loginTry = 0;
	int loginTry_JD = 0;
	int quietLoginTry = 0;
	String toolbarTitle = "Taobao";
	boolean HideLogo = true;
	boolean IsAtHome = true;
	boolean IsTaobaoLite = false;
	boolean supportZoom = false;
	boolean supportLocalAPP;
	boolean QuietLogin_JD, QuietLogin;
	private boolean AutoLogin,AutoLogin_JD;
	private boolean xianyuOK;
	private boolean jingdongOK;
	private boolean autoUpdata;
	private boolean findTaoKey;
	private boolean findUrlKey;
	private boolean AutoClick,AutoClick_JD;
	private boolean SetUserHomePage;
	private GestureDetector gestureDetector;
	private int downX, downY;
	private String imgurl = "";
	private String key;
	private String miUsername ,miUsername_JD;
	private String miPassword,miPassword_JD;
	private TaokeyTool taokey;
	SharedPreferences settingsRead;
	SharedPreferences settings;
	SharedPreferences.Editor editor;
	SharedPreferences shp;
	private String PACKAGE_NAME = "io.github.gtf.easyShopping";
	private static final int REQUEST_CODE_READ_EXTERNAL_STORAGE_PERMISSIONS = 1;
    private static final int REQUEST_CODE_WRITE_EXTERNAL_STORAGE = 2;
	private int MODE = 1;
	private int COLORMODE = 1;
	private int TAOMALL = 1;
	private int JINGDONG = 2;
	private int AUTO = 3;
	LinearLayout mainLinearLayout , leftLinearLayout;
	
	String UPDATA_LOG = "新增:主题颜色设定\n更改：启动背景 \n新增:自定义桌面图标 \n新增：临时允许缩放(在右上角菜单里) \n优化:搜索栏，粘贴网址可以直接进入 \n修复3.7.3版本长按图片全屏看图的崩溃 \n最后感谢给我无私画LOGO的小伙伴们，感谢";
	String outsideUrl;
	String mUA ="User-Agent: MQQBrowser/26 Mozilla/5.0 (Linux; U; Android 2.3.7; zh-cn; MB200 Build/GRJ22; CyanogenMod-7) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1";

	ListView lv;

	private static final String[] Taobaolist = new String[] {
		"我的淘宝",	//0
		"购物车",	//1
		"物流", 	//2
		"订单",		//3
		"收藏夹",	//4
		"足迹",		//5
		"卡券包",	//6
		"旺旺",		//7
		"设置",		//8
		"退出"		//9
    };//定义一个String数组用来显示ListView的内容private ListView lv;

	private static final String[] Jingdonglist = new String[] {
		"我的京东",	//0
		"购物车",	 	//1
		"分类", 	//2
		"发现",		//3
		"订单",		//4
		"关注的商品",	//5
		"关注的店铺",	//6
		"浏览记录",		//7
		"设置",			//8
		"退出"			//9
    };

	//定义一个String数组用来显示ListView的内容private ListView lv;

	StyleTool mStyleTool = new StyleTool();
	IconTool mIconTool = new IconTool();

    @Override
    protected void onCreate(Bundle savedInstanceState)
	{
		MODE = PreferenceManager.getDefaultSharedPreferences(this).getInt("MODE", TAOMALL);
		COLORMODE = PreferenceManager.getDefaultSharedPreferences(this).getInt("STYLEMODE", AUTO);
		if(COLORMODE == AUTO){
			if (MODE == TAOMALL){
				setTheme(R.style.myTheme_tb);
			} else {
				setTheme(R.style.myTheme_jd);
			}
			
		} 
		if (COLORMODE == JINGDONG){
			setTheme(R.style.myTheme_jd);
		} 
		if (COLORMODE == TAOMALL){
			setTheme(R.style.myTheme_tb);
		}
        super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setColor();
		mIconTool.init(this);
		mIconTool.GetAndSetIcon(this);
		supportTBS = PreferenceManager.getDefaultSharedPreferences(this).getBoolean("supportTBS",false);
		mainLinearLayout = (LinearLayout)findViewById(R.id.mainLinearLayout);
		LayoutParams param1 = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
		if(supportTBS){
			mWebView_TBS = new com.tencent.smtt.sdk.WebView(this);
			mWebView_TBS.setVisibility(View.GONE);
			mainLinearLayout.addView(mWebView_TBS,param1);
			initWebView_TBS(mWebView_TBS , true);
		}else{
			mWebView = new android.webkit.WebView(this);
			mWebView.setVisibility(View.GONE);
			mainLinearLayout.addView(mWebView,param1);
			initWebView(mWebView, true);
		}
		
		
		
		
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		Logo1 = (TextView) findViewById(R.id.Logo1);
		Logo2 = (TextView) findViewById(R.id.Logo2);
		search_button_toolbar = (Button)findViewById(R.id.search_toolbar_Button);
		search_editText_toolbar = (EditText)findViewById(R.id.search_toolbar_edittext);
		search_editText = (EditText)findViewById(R.id.search_editText);
		search_button = (Button)findViewById(R.id.search_button);
		title_toolbar = (TextView)findViewById(R.id.title_toolbar);
		nav_title = (TextView) findViewById(R.id.nav_title);
		nav_change = (TextView)findViewById(R.id.nav_change);
		nav_btn = (ImageView)findViewById(R.id.imageView);
		Dialog = new AlertDialog.Builder(this);
        
		mProgressDialog = new ProgressDialog(this);
		btn_leftWebview_back = (Button) findViewById(R.id.btn_leftwebview_back);
		btn_leftWebview_home = (Button) findViewById(R.id.btn_leftwebview_home);
		btn_leftWebview_exchange = (Button) findViewById(R.id.btn_leftwebview_exchange);
        //fab = (FloatingActionButton) findViewById(R.id.fab);


		//获取Preferences
		settingsRead = getSharedPreferences("data", 0);
		//取出数据
	    //IsTaobaoLite = settingsRead.getBoolean("IsTaobaoLite" , false);
		exitByCrash = settingsRead.getBoolean("exitByCrash",true);
		backFromSetting = settingsRead.getBoolean("backFromSetting",false);
		HistoryMainUrl = settingsRead.getString("HistoryMainUrl",null);
		HistoryLeftUrl = settingsRead.getString("HistoryLeftUrl",null);
		startTime = settingsRead.getInt("startTime", 0) + 1;
		//打开数据库
		settings = getSharedPreferences("data", 0);
		//处于编辑状态
		editor = settings.edit();
		//存放数据
		editor.putInt("startTime", startTime);
		editor.putBoolean("IsTaobaoLite", false);
		//完成提交
		editor.commit();
		
		shp = PreferenceManager.getDefaultSharedPreferences(this);
		IsTaobaoLite = shp.getBoolean("taobaoLite", false);
		//xianyuOK = shp.getBoolean("check_xianyu", false);
		xianyuOK = false;
		jingdongOK = shp.getBoolean("check_jingdong", false);
		autoUpdata = shp.getBoolean("autoUpdata", true);
		findTaoKey = shp.getBoolean("check_TaoKey", true);
		findUrlKey = shp.getBoolean("check_TaoUrlKey", true);
		supportLocalAPP = shp.getBoolean("supportLocalAPP",false);
		SetUserHomePage = shp.getBoolean("autoLeftWebview", false);
		key = shp.getString("key", null);
		miUsername = shp.getString("miUsername", "null");
		miPassword = shp.getString("miPassword", "null");
		miUsername_JD = shp.getString("miUsername_JD", "null");
		miPassword_JD = shp.getString("miPassword_JD", "null");
		AutoLogin = shp.getBoolean("check_AutoLogin", true);
		AutoClick = shp.getBoolean("check_AutoClick", false);
		AutoLogin_JD = shp.getBoolean("check_AutoLogin_JD", true);
		AutoClick_JD = shp.getBoolean("check_AutoClick_JD", false);
		removeTab = shp.getBoolean("removeTab", true);
		leftWebviewHomeUrl = shp.getString("leftWebViewPage", "");
		noPic = shp.getBoolean("noPic",false);
		savePage = shp.getBoolean("savePage",true);
		DEBUG = shp.getBoolean("debug",false);
		mTaobaoUrl = shp.getString("mTaobaoUrl",mTaobaoUrl);
		mJDUrl = shp.getString("mJDUrl",mJDUrl);
		QuietLogin = shp.getBoolean("QuietLogin",false);
		QuietLogin_JD = shp.getBoolean("QuietLogin_JD",false);
		debugToast("TBS:"+supportTBS);
		HistoryMainUrl_old = HistoryMainUrl;
		HistoryLeftUrl_old = HistoryLeftUrl;
		

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
			this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        //NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        //navigationView.setNavigationItemSelectedListener(this);

		//动态请求权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
		{
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
			{
                requestPermissions(
					new String[] { Manifest.permission.READ_EXTERNAL_STORAGE },
					REQUEST_CODE_READ_EXTERNAL_STORAGE_PERMISSIONS);
                requestPermissions(
					new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE },
					REQUEST_CODE_WRITE_EXTERNAL_STORAGE);
            }
        }
		
		toolbar.setTitle("");
		initLeftWebviewBtn();
		initList();
		initNavHead();
		loadHomePage();
		//loadLeftHomePage();
		initsearch();
		initsearchToolbar();
		ad();
		if (autoUpdata)
		{
			mUpdata();
		}
		final boolean theFirstStart = onFirstStart();
	
		
		SharedPreferences.Editor e = getSharedPreferences("data",0).edit().putBoolean("backFromSetting",false);
		e.commit();
		SharedPreferences.Editor f = getSharedPreferences("data",0).edit().putBoolean("exitByCrash",true);
		f.commit();
		
		if (startTime == 1)
		{
			noticeDialog();
		}
		if (theFirstStart)
		{
			Updata();
		}
		ToKey();
		new Timer().schedule(new TimerTask() {
				public void run()
				{	
					mHandler.sendEmptyMessage(0x131);	
				}
			}, 1000);// 这里百毫秒		
		mHandler = new Handler(){  
			@Override  
			public void handleMessage(Message msg)
			{  
				if (msg.what == 0x123)
				{
					Logo1.setVisibility(View.GONE);
					Logo2.setVisibility(View.GONE);
					if(supportTBS){
						mWebView_TBS.setVisibility(View.VISIBLE);
					} else {
						mWebView.setVisibility(View.VISIBLE);
					}
				}
				else if (msg.what == 0x124)
				{
					loadUrl("javascript: {" +

									 "document.getElementById('btn-submit').click();" +

									 " };",false);
					mProgressDialog.hide();
				}
				else if (msg.what == 0x126)
				{
					loadUrl("javascript: {" +

									 "document.getElementById('loginBtn').click();" +

									 " };",false);
					mProgressDialog.hide();
				}
				else if (msg.what == 0x127){
					mWebViewLogin.loadUrl("javascript: {" +

										  "document.getElementById('loginBtn').click();" +

										  " };");
					
					if(mWebViewLogin.getUrl().startsWith("https://m.jd.com")){
						showSnackBar("登录成功！","",0);
						
						return;
					} else {
						//showSnackBar("自动登录失败，请手动打开登录界面查看错误原因","",0);
						return;
					}
				} else if (msg.what == 0x128){
					mWebViewLogin.loadUrl("javascript: {" +

							"document.getElementById('btn-submit').click();" +

							" };");
					if(mWebViewLogin.getUrl().startsWith("https://m.taobao.com")){
						showSnackBar("登录成功！","",0);
						return;
					} else if(mWebViewLogin.getUrl().contains("安全") == true && mWebViewLogin.getUrl().contains("验证") == true){
						showSnackBar("登录失败：需要验证码，请手动打开登录界面进行登录！","",0);
						return;
					} else if(mWebViewLogin.getTitle().contains("我的淘宝")){
						showSnackBar("登录成功！","",0);
						return;
						//initquietLogin();
						//showSnackBar("自动登录失败，请手动打开登录界面查看错误原因","",0);
						
					}
				} else if (msg.what == 0x129){

					if(mWebViewLogin.getUrl().startsWith("https://m.jd.com")){
						showSnackBar("登录成功！","",0);

						return;
					} else {
						//showSnackBar("自动登录失败，请手动打开登录界面查看错误原因","",0);
						return;
					}
				} else if (msg.what == 0x130){
					
					if(mWebViewLogin.getUrl().startsWith("https://m.taobao.com")){
						showSnackBar("登录成功！","",0);
						return;
					} else if(mWebViewLogin.getUrl().contains("安全") == true && mWebViewLogin.getUrl().contains("验证") == true){
						showSnackBar("登录失败：需要验证码，请手动打开登录界面进行登录！","",0);
						return;
					} else if(mWebViewLogin.getTitle().contains("我的淘宝")){
						showSnackBar("登录成功！","",0);
						return;
						//initquietLogin();
						//showSnackBar("自动登录失败，请手动打开登录界面查看错误原因","",0);

					}
				} else if(msg.what == 0x131){
					leftLinearLayout = (LinearLayout)findViewById(R.id.leftLinearLayout);
					LayoutParams param1 = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
					if(supportTBS){
						mWebViewLeft_TBS = new com.tencent.smtt.sdk.WebView(Main.this);
						mWebViewLeft_TBS.setVisibility(View.VISIBLE);
						leftLinearLayout.addView(mWebViewLeft_TBS,param1);
						initWebView_TBS(mWebViewLeft_TBS , false);
					}else{
						mWebViewLeft = new android.webkit.WebView(Main.this);
						mWebViewLeft.setVisibility(View.VISIBLE);
						leftLinearLayout.addView(mWebViewLeft,param1);
						initWebView(mWebViewLeft, false);
					}
					mWebViewLogin = new android.webkit.WebView(Main.this);
					mWebViewLogin.setVisibility(View.GONE);
					leftLinearLayout.addView(mWebViewLogin,param1);
					initWebView(mWebViewLogin, false);
					loadLeftHomePage();
					initquietLogin();
					debugToast("main："+HistoryMainUrl+"  left：" + HistoryLeftUrl);
					debugToast("第一次启动："+onFirstStart());
					debugToast("恢复页面开关："+savePage);
					debugToast("exitbycrash: "+exitByCrash + " \n backFromSetting: " + backFromSetting); 
					if( HistoryMainUrl!= null && HistoryLeftUrl != null){
						if(theFirstStart == false){
							debugToast("不是第一次启动");
							if(exitByCrash && savePage&&backFromSetting == false){
								debugToast("因为意外退出加载");
								showSnackBar("上次启动未正常关闭，是否恢复页面？","OK",4);

							} else if(backFromSetting){
								debugToast("因为从设置返回加载");
								showSnackBar("恢复页面中。。。。。","",0);
								loadUrl(HistoryMainUrl,false);
								loadUrl(HistoryLeftUrl,true);
							}
						}	
					}
					return;
				}
			}  
	};
}
	void initquietLogin(){
		initWebViewLogin(mWebViewLogin);
		if(MODE == TAOMALL){
			mWebViewLogin.loadUrl(mMyTaobaoUrl);
		} else {
			mWebViewLogin.loadUrl(mMyJD);
		}
	}
	
	void quietLogin(int tryTime , int mMODE){
		boolean off_jd = (MODE == JINGDONG && QuietLogin_JD == false);
		boolean off = (MODE == TAOMALL && QuietLogin == false);
		if(off_jd | off){
			return;
		}
		String loginUrl = "login.m.taobao.com";
		String loginUrl_JD = "https://plogin.m.jd.com";
		if(QuietLogin_JD == false && QuietLogin == false){
			return;
		}
		if(tryTime < 3){
			showSnackBar("正在登录...","",0);
		} else if(tryTime == 3) {
			showSnackBar("登录失败.","",0);
			return;
		} else {
			return;
		}
		
		
		if (mMODE == JINGDONG){
				if (miPassword_JD.contains("null") || miUsername_JD.contains("null") || key == null)
				{
					showSnackBar("无京东用户信息","",0);
					return;
				}
				onQuietLogin_JD();
				onQuietLogin_JD = true;
		}
		if(mMODE == TAOMALL){
			if (miPassword.contains("null") || miUsername.contains("null") || key == null)
			{
				showSnackBar("无淘宝用户信息","",0);
				return;
			}
			onQuietLogin();
			onQuietLogin = true;
			
		}
	}
	
	void removeUnderTab(){
		if (removeTab){
			loadUrl("JavaScript:var el = document.getElementsByClassName(\"_1DrF-Ndoxy1b882RZcUtzX _1bKOWZpFDSZMyGm5qZHZAU\");el[0].remove();\n",false);
			loadUrl("JavaScript:setTimeout(function(){var el = document.getElementsByClassName(\"_1DrF-Ndoxy1b882RZcUtzX _1bKOWZpFDSZMyGm5qZHZAU\");el[0].remove();\n; }, 300);",false);
			loadUrl("JavaScript:var el = document.getElementsByClassName(\"app-download-popup smally show\");el[0].remove();\n",false);
			loadUrl("JavaScript:var el = document.getElementsByClassName(\"header-wrap\");el[0].remove();\n",false);
			loadUrl("JavaScript:setTimeout(function(){var el = document.getElementsByClassName(\"mui-sb-box\");el[0].remove();\n; }, 800);",false);
			loadUrl("JavaScript:var el = document.getElementsByClassName(\"buttons\");el[0].remove();\n",false);
			loadUrl("JavaScript:var el = document.getElementsByClassName(\"bottom-fxied\");el[0].remove();\n",false);
			loadUrl("JavaScript:var el = document.getElementsByClassName(\"mui-bottom-smart-banner\");el[0].remove();\n",false);
			loadUrl("JavaScript:setTimeout(function(){var el = document.getElementsByClassName(\"image-class\");el[0].remove();\n; }, 80);",false);
			loadUrl("JavaScript:var el = document.getElementsByClassName(\"e13dsk\");el[0].remove();\n",false);
			loadUrl("JavaScript:var el = document.getElementsByClassName(\"install-app\");el[0].remove();\n",false);
			loadUrl("JavaScript:var el = document.getElementsByClassName(\"_2ZhzdhjNG9KBM3ONwOuqC0 wERa5TkG4nQuWvDWt30Qs\");el[0].remove();\n",false);
			loadUrl("JavaScript:var el = document.getElementsByClassName(\"_3wPMj2-wcRhm5ZXLjBAhPm\");el[0].remove();\n",false);


		}
	}
	
	void removeClass(String Adclass){
		loadUrl("javascript: {"+
		"$(this).removeClass('"+ Adclass + "');"
		+"};"
		,false);
	}
	
	
	@Override
	public void onBackPressed()
	{
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		if (drawer.isDrawerOpen(GravityCompat.START) || drawer.isDrawerOpen(GravityCompat.END))
		{
			if (drawer.isDrawerOpen(GravityCompat.START))
			{
				drawer.closeDrawer(GravityCompat.START);
			} 
			if (drawer.isDrawerOpen(GravityCompat.END))
			{
				drawer.closeDrawer(GravityCompat.END);
			}
		}
		else
		{
			if(supportTBS){
				if (mWebView_TBS.canGoBack())
				{
					mWebView_TBS.goBack();
				}
				else
				{
					showSnackBar("退出？", "确定", 1);
				}
			}else{
				if (mWebView.canGoBack())
				{
					mWebView.goBack();
				}
				else
				{
					showSnackBar("退出？", "确定", 1);
				}
			}
		}
	}

	void onQuietLogin_JD(){
		String user=jiemi(miUsername_JD, key);
		String pwd=jiemi(miPassword_JD, key);
//把用户名密码填充到表单
		mWebViewLogin.loadUrl("javascript: {" +            

				"document.getElementById('username').value = '" + user + "';" +            

				"document.getElementById('password').value = '" + pwd + "';" +            

				"var frms = document.getElementsByName('username_login');" +            

				"frms[0].submit();" +

				" };");
		mWebViewLogin.loadUrl("javascript: {" +

				"document.getElementById('sms_login_txt').click();" +

				" };");
		mWebViewLogin.loadUrl("javascript: {" +

				"document.getElementById('account_login_txt').click();" +

				" };");
		Timer timer0 = new Timer();// 实例化Timer类
		timer0.schedule(new TimerTask() {
				public void run()
				{	
					mHandler.sendEmptyMessage(0x127);	
				}
			}, 1500);// 这里百毫秒	
		Timer timer = new Timer();// 实例化Timer类
		timer.schedule(new TimerTask() {
				public void run()
				{	
					mHandler.sendEmptyMessage(0x129);	
				}
			}, 2000);// 这里百毫秒		
	}
	
	void setColor(){
		RelativeLayout nav = (RelativeLayout) findViewById(R.id.nav_layout);
		LinearLayout list = (LinearLayout) findViewById(R.id.right_nav);
		mStyleTool.initStyle(this,nav,list);
	}
	
	void onQuietLogin(){
		String user=jiemi(miUsername, key);
		String pwd=jiemi(miPassword, key);
		mWebViewLogin.loadUrl("javascript: {" +            

				"document.getElementById('username').value = '" + user + "';" +            

				"document.getElementById('password').value = '" + pwd + "';" +            

				"var frms = document.getElementsByName('loginForm');" +            

				"frms[0].submit();" +

				" };");
		Timer timer0 = new Timer();// 实例化Timer类
		timer0.schedule(new TimerTask() {
				public void run()
				{	
					mHandler.sendEmptyMessage(0x130);	
				}
			}, 1500);// 这里百毫秒	

		Timer timer = new Timer();// 实例化Timer类
		timer.schedule(new TimerTask() {
				public void run()
				{	
					mHandler.sendEmptyMessage(0x128);	
				}
			}, 2000);// 这里百毫秒		
	}
	
	void backWebview(){
		//mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);  
		//mWebView.goBack();
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);

       /* //设置搜索输入框的步骤

        //1.查找指定的MemuItem
        MenuItem menuItem = menu.findItem(R.id.action_search);

		//2.设置SearchView v4 包方式
		 View view = SearchViewCompat.newSearchView(this);
		       menuItem.setActionView(view);
		 MenuItemCompat.setActionView(menuItem, view);

        //2.设置SearchView v7包方式
       // View view = MenuItemCompat.getActionView(menuItem);
        if (view != null) {
         	searchView = (SearchView) view;
			searchView.setBackgroundColor(R.color.WHITE);
			searchView.setOnSearchClickListener(new View.OnClickListener(){

					@Override
					public void onClick(View p1)
					{
						InputMethodManager inputMethodManager = null;
						if(inputMethodManager == null) {
							inputMethodManager = (InputMethodManager)Main.this.getSystemService(Context.INPUT_METHOD_SERVICE);
						}
						inputMethodManager.showSoftInput(searchView,0);
						searchView.setFocusable(true);
						searchView.setFocusableInTouchMode(true);
						searchView.requestFocus();
						searchView.findFocus();
					}
					
				
			});
			//searchView.setIconified(false);
            //4.设置SearchView 的查询回调接口
            searchView.setOnQueryTextListener(new OnQueryTextListener(){

					@Override
					public boolean onQueryTextSubmit(String keyword)
					{
						runsearch(keyword);
						searchView.clearFocus();
						return false;
					}

					@Override
					public boolean onQueryTextChange(String p1)
					{
						// TODO: Implement this method
						return false;
					}

				
			});

            //在搜索输入框没有显示的时候 点击Action ,回调这个接口，并且显示输入框
//            searchView.setOnSearchClickListener();
            //当自动补全的内容被选中的时候回调接口
//            searchView.setOnSuggestionListener();

            //可以设置搜索的自动补全，或者实现搜索历史
//            searchView.setSuggestionsAdapter();

        }*/
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
			loadHomePage();
			return true;
		}
		else if(id == R.id.xianyu){
			souXianYu();
			return true;
		}
		else if (id == R.id.action_settings)
		{
			SharedPreferences.Editor e = getSharedPreferences("data",0).edit().putBoolean("backFromSetting",true);
			e.commit();
			Intent intent = new Intent(Main.this, SettingsActivity.class);
			startActivity(intent);
			return true;
		}
		else if (id == R.id.action_exit)
		{
			exitProgrames();
			return true;
		}
		else if (id == R.id.share)
		{
//提示dialog
			Dialog.setCancelable(true);
			Dialog.setTitle("淘口令：");
			Dialog.setMessage("淘口令已经生成，并复制到了剪切板，去粘贴吧！");
			Dialog.setPositiveButton("OK",  new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						String thisUrl = mWebView.getUrl();
						String thisTitle = toolbarTitle;
						String thisTaokey = "【" + thisTitle + "】" + thisUrl + " 点击链接，再选择浏览器打开；或复制这条信息后打开👉手淘👈";
						copy(thisTaokey, Main.this);
					}
				});
			Dialog.show();
			return true;
		}	
		else if (id == R.id.action_reload)
		{
			showSnackBar("刷新ing........", " ", 0);
			if(supportTBS){
				mWebView_TBS.clearCache(true);
				mWebView_TBS.reload();
			}else{
				mWebView.clearCache(true);
				mWebView.reload();
			}
			
			return true;
		}
		else if (id == R.id.home)
		{
			IsAtHome = true;
			loadHomePage();
			return true;
		}
		else if (id == R.id.suofang){
			if(supportZoom == false){
				supportZoom = true;
				showSnackBar("已允许缩放","",0);
				if(supportTBS){
					// 设置可以支持缩放 
					mWebView_TBS.getSettings().setSupportZoom(true); 
					// 设置出现缩放工具 
					mWebView_TBS.getSettings().setBuiltInZoomControls(true);
					//设置可在大视野范围内上下左右拖动，并且可以任意比例缩放
					mWebView_TBS.getSettings().setUseWideViewPort(true);
					//设置默认加载的可视范围是大视野范围
					mWebView_TBS.getSettings().setLoadWithOverviewMode(true);
				} else{
					// 设置可以支持缩放 
					mWebView.getSettings().setSupportZoom(true); 
					// 设置出现缩放工具 
					mWebView.getSettings().setBuiltInZoomControls(true);
					//设置可在大视野范围内上下左右拖动，并且可以任意比例缩放
					mWebView.getSettings().setUseWideViewPort(true);
					//设置默认加载的可视范围是大视野范围
					mWebView.getSettings().setLoadWithOverviewMode(true);
				}
			}else{
				supportZoom = false;
				showSnackBar("已禁用缩放","",0);
				if(supportTBS){
					// 设置可以支持缩放 
					mWebView_TBS.getSettings().setSupportZoom(false); 
					// 设置出现缩放工具 
					mWebView_TBS.getSettings().setBuiltInZoomControls(false);
					//设置可在大视野范围内上下左右拖动，并且可以任意比例缩放
					mWebView_TBS.getSettings().setUseWideViewPort(false);
					//设置默认加载的可视范围是大视野范围
					mWebView_TBS.getSettings().setLoadWithOverviewMode(false);
				} else{
					// 设置可以支持缩放 
					mWebView.getSettings().setSupportZoom(false); 
					// 设置出现缩放工具 
					mWebView.getSettings().setBuiltInZoomControls(false);
					//设置可在大视野范围内上下左右拖动，并且可以任意比例缩放
					mWebView.getSettings().setUseWideViewPort(false);
					//设置默认加载的可视范围是大视野范围
					mWebView.getSettings().setLoadWithOverviewMode(false);
					}
			}
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
	/*
	 @SuppressWarnings("StatementWithEmptyBody")
	 @Override
	 public boolean onNavigationItemSelected(MenuItem item)
	 {
	 // Handle navigation view item clicks here.


	 }
	 */
	public void exitProgrames()
	{
		SharedPreferences.Editor e = getSharedPreferences("data",0).edit().putBoolean("exitByCrash",false);
		e.commit();
		ActivityCollector.finishAll();
	}
	
	void changeTheme(){
		/*
		TypedValue typedValue = new TypedValue(); 
		Resources.Theme theme = getTheme(); 
		try {
			theme.resolveAttribute(R.attr.theme_color, typedValue, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Resources resources = getResources();
		try {
			int color = ResourcesCompat.getColor(resources, typedValue.resourceId, null); // 获取颜色值
			//Drawable drawable = ResourcesCompat.getDrawable(resources, typedValue.resourceId, null); // 获取Drawable对象
			//String string = resources.getString(typedValue.resourceId); // 获取字符串
			toolbar.setBackgroundColor(color);
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
	}
	
	

	void souXianYu(){
		
		final View inputView = View.inflate(Main.this, R.layout.textview_url, null);
		EditText EditView = (EditText)inputView.findViewById(R.id.editText_url);
		new AlertDialog.Builder(Main.this)
			.setTitle("搜咸鱼")
			.setCancelable(true)	
			.setView(inputView)
			.setPositiveButton("走起",  new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					
					EditText EditView = (EditText)inputView.findViewById(R.id.editText_url);
					final String keyword = EditView.getText().toString();
					String url = "https://s.2.taobao.com/list/list.htm?_input_charset=utf8&q=" + keyword;
					loadUrl(url,false);
				}
			})
			.setNegativeButton("取消", new DialogInterface.OnClickListener(){

				@Override
				public void onClick(DialogInterface p1, int p2)
				{
					
				}
				
			})
			.show();
	}
	
	
	void initsearch(){
		search_button.setOnClickListener(new OnClickListener(){
			
				@Override
				public void onClick(View p1)
				{
					if(!search_editText.getText().toString().equals("")){
						runsearch(search_editText.getText().toString());
					}
					if(search_editText.getText().toString().startsWith("https:")|search_editText.getText().toString().startsWith("http:")){
						loadUrl(search_editText.getText().toString(),false);
					}
					hidesoftkey(search_editText);
					search_editText.setText("");
					DrawerLayout drawer =  (DrawerLayout)findViewById(R.id.drawer_layout);
					drawer.closeDrawer(GravityCompat.START);
				}
			
		});
		
				search_editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

				@Override
				public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
					//当actionId == XX_SEND 或者 XX_DONE时都触发
					//或者event.getKeyCode == ENTER 且 event.getAction == ACTION_DOWN时也触发
					//注意，这是一定要判断event != null。因为在某些输入法上会返回null。
					if (actionId == EditorInfo.IME_ACTION_SEND
						|| actionId == EditorInfo.IME_ACTION_DONE
						|| (event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode() && KeyEvent.ACTION_DOWN == event.getAction())) {
						if(!search_editText_toolbar.getText().toString().equals("")){
							runsearch(search_editText_toolbar.getText().toString());
						}
						runsearch(search_editText.getText().toString());
						search_editText.setText("");
						DrawerLayout drawer =  (DrawerLayout)findViewById(R.id.drawer_layout);
						drawer.closeDrawer(GravityCompat.START);
						hidesoftkey(search_editText);
						//处理事件
					}
					return false;
				}
			});
						
	}
	
	void loadUrl(String url,boolean isLeftWebview){
		if(supportTBS == true){
			if(isLeftWebview){
				mWebViewLeft_TBS.loadUrl(url);
			} else {
				mWebView_TBS.loadUrl(url);
			}
		} else {
			if(isLeftWebview){
				mWebViewLeft.loadUrl(url);
			} else {
				mWebView.loadUrl(url);
			}
		}
	}
	
	void ad(){
		AD ad = new AD();
		ad.initAD(this);
		
	}
	
	void hidesoftkey(EditText a){
		InputMethodManager inputMethodManager = null;
		if(inputMethodManager == null) {
			inputMethodManager = (InputMethodManager)Main.this.getSystemService(Context.INPUT_METHOD_SERVICE);
		}
		inputMethodManager.hideSoftInputFromWindow(a.getWindowToken(),0);
		
	}
	
	void setToolbarTitle(String title){
		search_editText_toolbar.setVisibility(View.GONE);
		title_toolbar.setVisibility(View.VISIBLE);
		title_toolbar.setText(title);
	}
	
	void initsearchToolbar(){
		title_toolbar.setVisibility(View.VISIBLE);
		search_editText_toolbar.setVisibility(View.GONE);
		search_button_toolbar.setOnClickListener(new OnClickListener(){
		int WHITE = 1 , ON = 2;
		int MODE = WHITE;
				@Override
				public void onClick(View p1)
				{
					if(MODE == WHITE){
						title_toolbar.setVisibility(View.GONE);
						search_editText_toolbar.setVisibility(View.VISIBLE);
						search_button_toolbar.setText("GO");
						search_editText_toolbar.requestFocus();
						InputMethodManager inputMethodManager = null;
						if(inputMethodManager == null) {
							inputMethodManager = (InputMethodManager)Main.this.getSystemService(Context.INPUT_METHOD_SERVICE);
						}
						inputMethodManager.showSoftInput(search_editText_toolbar,0);
						MODE = ON;
					} 
					else if(MODE == ON)
					{
						search_editText_toolbar.setOnEditorActionListener(new TextView.OnEditorActionListener() {

								@Override
								public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
									//当actionId == XX_SEND 或者 XX_DONE时都触发
									//或者event.getKeyCode == ENTER 且 event.getAction == ACTION_DOWN时也触发
									//注意，这是一定要判断event != null。因为在某些输入法上会返回null。
									if (actionId == EditorInfo.IME_ACTION_SEND
										|| actionId == EditorInfo.IME_ACTION_DONE
										|| (event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode() && KeyEvent.ACTION_DOWN == event.getAction())) {
										if(!search_editText_toolbar.getText().toString().equals("")){
											runsearch(search_editText_toolbar.getText().toString());
										}
										InputMethodManager inputMethodManager = null;
										if(inputMethodManager == null) {
											inputMethodManager = (InputMethodManager)Main.this.getSystemService(Context.INPUT_METHOD_SERVICE);
										}
										inputMethodManager.hideSoftInputFromWindow(search_editText_toolbar.getWindowToken(),0);
										search_editText_toolbar.setText("");
										title_toolbar.setVisibility(View.VISIBLE);
										search_editText_toolbar.setVisibility(View.GONE);
										search_button_toolbar.setText("搜索");
										MODE = WHITE;
										//处理事件
									}
									return false;
								}
							});
						if(!search_editText_toolbar.getText().toString().equals("")){
							runsearch(search_editText_toolbar.getText().toString());
						}
						if(search_editText_toolbar.getText().toString().startsWith("https:")|search_editText.getText().toString().startsWith("http:")){
							loadUrl(search_editText_toolbar.getText().toString(),false);
						}
						InputMethodManager inputMethodManager = null;
						if(inputMethodManager == null) {
							inputMethodManager = (InputMethodManager)Main.this.getSystemService(Context.INPUT_METHOD_SERVICE);
						}
						inputMethodManager.hideSoftInputFromWindow(search_editText_toolbar.getWindowToken(),0);
						search_editText_toolbar.setText("");
						title_toolbar.setVisibility(View.VISIBLE);
						search_editText_toolbar.setVisibility(View.GONE);
						search_button_toolbar.setText("搜索");
						MODE = WHITE;
					}
				}
				
		});
	}
	
	
	void runsearch(String keyword){
		String text = keyword;
		String url_tb = "https://s.m.taobao.com/h5?event_submit_do_new_search_auction=1&_input_charset=utf-8&topSearch=1&atype=b&searchfrom=1&action=home%3Aredirect_app_action&from=1&q="+text+"&sst=1&n=20&buying=buyitnow";
		String url_jd = "https://so.m.jd.com/ware/search.action?keyword="+text+"&searchFrom=home";
		if (xianyuOK == false && MODE == 1)
		{
			if (IsTaobaoLite)
			{
				showSnackBar("暂不支持在国际版下进行快速搜索！","",0);
			}
			else
			{
				loadUrl(url_tb,false);
			}
		}
		if (xianyuOK)
		{
			showSnackBar("暂不支持在咸鱼下进行快速搜索！","",0);
		}
		if (MODE == 2)
		{
			loadUrl(url_jd,false);
		}

		
	}
	
	void initWebViewLogin(final android.webkit.WebView initWebview){
		android.webkit.WebSettings mWebViewSettings = initWebview.getSettings();
		mWebViewSettings.setJavaScriptEnabled(true);
		mWebViewSettings.setUserAgentString(mUA);
		initWebview.setWebViewClient(new android.webkit.WebViewClient() {

				@Override
				public void onPageStarted(android.webkit.WebView view, String url, Bitmap favicon)
				{
					
				}
				@Override
				public void onPageFinished(android.webkit.WebView view, String url)
				{
					String loginUrl = "login.m.taobao.com";
					String loginUrl_JD = "https://plogin.m.jd.com";
					try
					{
						if (url.contains(loginUrl))
						{
							loginTry = loginTry + 1;
							quietLogin(loginTry,TAOMALL);
						}
						if(url.contains(loginUrl_JD) ){
							loginTry_JD = loginTry_JD + 1;
							quietLogin(loginTry_JD,JINGDONG);
						}
					}
					catch (Exception e)
					{
						showSnackBar( "判断登录界面出错", "",0);
						CrashReport.postCatchedException(e);
					}
					
				}
		});
		
		
		
	}
	
	
	void initWebView(final android.webkit.WebView initWebview , final boolean changeTitle)
	{
		android.webkit.WebSettings mWebViewSettings = initWebview.getSettings();
		
		if(noPic){
			mWebViewSettings.setLoadsImagesAutomatically(false); //支持自动加载图片
			mWebViewSettings.setBlockNetworkImage(true);
		} else {
			mWebViewSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
		}
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
		mWebViewSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
//优先使用缓存: 
		mWebViewSettings.setCacheMode(android.webkit.WebSettings.LOAD_CACHE_ELSE_NETWORK); 
		mWebViewSettings.setAppCacheEnabled(true);
		mWebViewSettings.setDatabaseEnabled(true);
		mWebViewSettings.setDomStorageEnabled(true);//开启DOM缓存
		mWebViewSettings.setUserAgentString(mUA);
//mWebViewSettings.setCacheMode(WebSettings.LOAD_DEFAULT);

		initWebview.setOnTouchListener(new View.OnTouchListener() {

				@Override
				public boolean onTouch(View arg0, MotionEvent arg1)
				{
					downX = (int) arg1.getX();
					downY = (int) arg1.getY();
					return false;
				}
			});

// 获取手指点击事件的xy坐标


		initWebview.setOnLongClickListener(new View.OnLongClickListener() {
				@Override
				public boolean onLongClick(View v)
				{
					android.webkit.WebView.HitTestResult result = ((android.webkit.WebView)v).getHitTestResult();
					if (null == result) 
						return false;
					int type = result.getType();
					if (type == android.webkit.WebView.HitTestResult.UNKNOWN_TYPE) 
						return false;
					if (type == android.webkit.WebView.HitTestResult.EDIT_TEXT_TYPE)
					{
//let TextViewhandles context menu return true;
					}
					final ItemLongClickedPopWindow itemLongClickedPopWindow = new ItemLongClickedPopWindow(Main.this, ItemLongClickedPopWindow.IMAGE_VIEW_POPUPWINDOW, (int)dip2px(120), (int)dip2px(90));
// Setup custom handlingdepending on the type
					switch (type)
					{
						case android.webkit.WebView.HitTestResult.PHONE_TYPE: // 处理拨号
							break;
						case android.webkit.WebView.HitTestResult.EMAIL_TYPE: // 处理Email
							break;
						case android.webkit.WebView.HitTestResult.GEO_TYPE: // TODO
							break;
						case android.webkit.WebView.HitTestResult.SRC_ANCHOR_TYPE: // 超链接
// Log.d(DEG_TAG, "超链接");
							break;
						case android.webkit.WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE:
							break;
						case android.webkit.WebView.HitTestResult.IMAGE_TYPE: // 处理长按图片的菜单项
							imgurl = result.getExtra();
//通过GestureDetector获取按下的位置，来定位PopWindow显示的位置
							itemLongClickedPopWindow.showAtLocation(v,        Gravity.TOP | Gravity.LEFT, downX, downY + 10);
							break;
						default:
							break;
					}

					itemLongClickedPopWindow.getView(R.id.item_longclicked_viewImage)
						.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View v)
							{
								Toast.makeText(MyApplication.getContext(), "正在加载...", Toast.LENGTH_SHORT).show();
								itemLongClickedPopWindow.dismiss();
								loadPicture(imgurl);
							}
						});
					itemLongClickedPopWindow.getView(R.id.item_longclicked_saveImage)
						.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View v)
							{
								itemLongClickedPopWindow.dismiss();
								new SaveImage().execute(); // Android 4.0以后要使用线程来访问网络
							}
						});
					return true;
				}
			});

		initWebview.setWebChromeClient(new android.webkit.WebChromeClient(){
				@Override
				public void onReceivedTitle(android.webkit.WebView view, String title)
				{
					if (changeTitle)
					{
						toolbarTitle = title;
						//toolbar.setTitle(toolbarTitle);
						setToolbarTitle(toolbarTitle);
					}
				}
			});
//复写WebViewClient类的shouldOverrideUrlLoading方法
		initWebview.setWebViewClient(new android.webkit.WebViewClient() {

				private Bitmap favicon;
				
				@Override
				public void onPageStarted(android.webkit.WebView view, String url, Bitmap favicon)
				{
					super.onPageStarted(view, url, favicon);
					if (changeTitle){
						SharedPreferences.Editor e = getSharedPreferences("data",0).edit().putString("HistoryMainUrl",url);
						e.commit();
					} else {
						SharedPreferences.Editor e = getSharedPreferences("data",0).edit().putString("HistoryLeftUrl",url);
						e.commit();
					}
					//toolbar.setTitle("加载中……");
					setToolbarTitle("加载中……");
					String loginUrl = "login.m.taobao.com";
					String loginUrl_JD = "https://plogin.m.jd.com";
					if (url.contains(loginUrl) && AutoClick)
					{
						mProgressDialog.show();
						mProgressDialog.setMessage("正在登录……");
					}
					if (url.contains(loginUrl_JD)&&AutoClick_JD){

						mProgressDialog.show();
						mProgressDialog.setMessage("正在登录……");
					}
					
					if (toolbarTitle.contains("淘宝网触屏版"))
					{
						toolbarTitle = "首页";
						removeUnderTab();
					}
				}
				@Override
				public void onPageFinished(android.webkit.WebView view, String url)
				{
					super.onPageFinished(view, url);
					mProgressDialog.hide();
					if (toolbarTitle.contains("淘宝网触屏版"))
					{
						toolbarTitle = "首页";
						
					}
					removeUnderTab();
					//toolbar.setTitle(toolbarTitle);
					setToolbarTitle(toolbarTitle);
					
					String loginUrl = "login.m.taobao.com";
					String loginUrl_JD = "https://plogin.m.jd.com";
					try
					{
						if (AutoLogin && url.contains(loginUrl) && toolbarTitle.contains("安全") == false && toolbarTitle.contains("验证") == false)
						{
							loginTry = loginTry + 1;
							AutoLogin(loginTry,TAOMALL);
						}
						if(AutoLogin_JD && url.contains(loginUrl_JD) && toolbarTitle.contains("京东登录")){
							loginTry_JD = loginTry_JD + 1;
							AutoLogin(loginTry_JD,JINGDONG);
						}
					}
					catch (Exception e)
					{
						showSnackBar( "判断登录界面出错", "",0);
						CrashReport.postCatchedException(e);
					}
					ToKey();
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

				@Override
				public boolean shouldOverrideUrlLoading(android.webkit.WebView view, String url)  
				{
					if (url == null) return false;

					try
					{
						if(url.startsWith("https://huodong.m.taobao.com")){
							return true;
						} 
						
						if (url.startsWith("http:") || url.startsWith("https:"))
						{
							initWebview.loadUrl(url);
							return true;
						}
						else
						{
							outsideUrl = url;
							if(supportLocalAPP){
								showSnackBar("页面试图打开本地APP", "允许", 3);
							}
							
//Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//startActivity(intent);
							return true;
						}
					}
					catch (Exception e)
					{ //防止crash (如果手机上没有安装处理某个scheme开头的url的APP, 会导致crash)
						return false;
					}
				}



			});
	}
	
	void initWebView_TBS(final com.tencent.smtt.sdk.WebView initWebview_TBS,final boolean changeTitle)
	{

		com.tencent.smtt.sdk.WebSettings mWebViewSettings = initWebview_TBS.getSettings();
		

		if(noPic){
			mWebViewSettings.setLoadsImagesAutomatically(false); //支持自动加载图片
			mWebViewSettings.setBlockNetworkImage(true);
		} else {
			mWebViewSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
		}
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
		mWebViewSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
//优先使用缓存: 
		mWebViewSettings.setCacheMode(com.tencent.smtt.sdk.WebSettings.LOAD_CACHE_ELSE_NETWORK); 
		mWebViewSettings.setAppCacheEnabled(true);
		mWebViewSettings.setDatabaseEnabled(true);
		mWebViewSettings.setDomStorageEnabled(true);//开启DOM缓存
		mWebViewSettings.setUserAgentString(mUA);
//mWebViewSettings.setCacheMode(WebSettings.LOAD_DEFAULT);

		initWebview_TBS.setOnTouchListener(new View.OnTouchListener() {

				@Override
				public boolean onTouch(View arg0, MotionEvent arg1)
				{
					downX = (int) arg1.getX();
					downY = (int) arg1.getY();
					return false;
				}
			});

// 获取手指点击事件的xy坐标


		initWebview_TBS.setOnLongClickListener(new View.OnLongClickListener() {
				@Override
				public boolean onLongClick(View v)
				{
					com.tencent.smtt.sdk.WebView.HitTestResult result = ((com.tencent.smtt.sdk.WebView)v).getHitTestResult();
					if (null == result) 
						return false;
					int type = result.getType();
					if (type == com.tencent.smtt.sdk.WebView.HitTestResult.UNKNOWN_TYPE) 
						return false;
					if (type == com.tencent.smtt.sdk.WebView.HitTestResult.EDIT_TEXT_TYPE)
					{
//let TextViewhandles context menu return true;
					}
					final ItemLongClickedPopWindow itemLongClickedPopWindow = new ItemLongClickedPopWindow(Main.this, ItemLongClickedPopWindow.IMAGE_VIEW_POPUPWINDOW, (int)dip2px(120), (int)dip2px(90));
// Setup custom handlingdepending on the type
					switch (type)
					{
						case com.tencent.smtt.sdk.WebView.HitTestResult.PHONE_TYPE: // 处理拨号
							break;
						case com.tencent.smtt.sdk.WebView.HitTestResult.EMAIL_TYPE: // 处理Email
							break;
						case com.tencent.smtt.sdk.WebView.HitTestResult.GEO_TYPE: // TODO
							break;
						case com.tencent.smtt.sdk.WebView.HitTestResult.SRC_ANCHOR_TYPE: // 超链接
// Log.d(DEG_TAG, "超链接");
							break;
						case com.tencent.smtt.sdk.WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE:
							break;
						case com.tencent.smtt.sdk.WebView.HitTestResult.IMAGE_TYPE: // 处理长按图片的菜单项
							imgurl = result.getExtra();
//通过GestureDetector获取按下的位置，来定位PopWindow显示的位置
							itemLongClickedPopWindow.showAtLocation(v,        Gravity.TOP | Gravity.LEFT, downX, downY + 10);
							break;
						default:
							break;
					}

					itemLongClickedPopWindow.getView(R.id.item_longclicked_viewImage)
						.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View v)
							{
								Toast.makeText(MyApplication.getContext(), "正在加载...", Toast.LENGTH_SHORT).show();
								itemLongClickedPopWindow.dismiss();
								loadPicture(imgurl);
							}
						});
					itemLongClickedPopWindow.getView(R.id.item_longclicked_saveImage)
						.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View v)
							{
								itemLongClickedPopWindow.dismiss();
								new SaveImage().execute(); // Android 4.0以后要使用线程来访问网络
							}
						});
					return true;
				}
			});

		initWebview_TBS.setWebChromeClient(new com.tencent.smtt.sdk.WebChromeClient(){
				@Override
				public void onReceivedTitle(com.tencent.smtt.sdk.WebView view, String title)
				{
					if (changeTitle)
					{
						toolbarTitle = title;
						//toolbar.setTitle(toolbarTitle);
						setToolbarTitle(toolbarTitle);
					}
				}
			});
//复写WebViewClient类的shouldOverrideUrlLoading方法
		initWebview_TBS.setWebViewClient(new com.tencent.smtt.sdk.WebViewClient() {

				private Bitmap favicon;

				@Override
				public void onPageStarted(com.tencent.smtt.sdk.WebView view, String url, Bitmap favicon)
				{
					super.onPageStarted(view, url, favicon);
					if (changeTitle){
						SharedPreferences.Editor e = getSharedPreferences("data",0).edit().putString("HistoryMainUrl",url);
						e.commit();
					} else {
						SharedPreferences.Editor e = getSharedPreferences("data",0).edit().putString("HistoryLeftUrl",url);
						e.commit();
					}
					//toolbar.setTitle("加载中……");
					setToolbarTitle("加载中……");
					String loginUrl = "login.m.taobao.com";
					String loginUrl_JD = "https://plogin.m.jd.com";
					if (url.contains(loginUrl) && AutoClick)
					{
						mProgressDialog.show();
						mProgressDialog.setMessage("正在登录……");
					}
					if (url.contains(loginUrl_JD)&&AutoClick_JD){

						mProgressDialog.show();
						mProgressDialog.setMessage("正在登录……");
					}
					
					if (toolbarTitle.contains("淘宝网触屏版"))
					{
						toolbarTitle = "首页";
						removeUnderTab();
					}

				}
				@Override
				public void onPageFinished(com.tencent.smtt.sdk.WebView view, String url)
				{
					super.onPageFinished(view, url);
					
					mProgressDialog.hide();
					if (toolbarTitle.contains("淘宝网触屏版"))
					{
						toolbarTitle = "首页";
						
					}
					removeUnderTab();
					//toolbar.setTitle(toolbarTitle);
					setToolbarTitle(toolbarTitle);
					removeUnderTab();
					String loginUrl = "login.m.taobao.com";
					String loginUrl_JD = "https://plogin.m.jd.com";
					try
					{
						if (url.contains(loginUrl) && toolbarTitle.contains("安全") == false && toolbarTitle.contains("验证") == false)
						{
							loginTry = loginTry + 1;
							AutoLogin(loginTry,TAOMALL);
						}
						if(url.contains(loginUrl_JD) && toolbarTitle.contains("京东登录")){
							loginTry_JD = loginTry_JD + 1;
							AutoLogin(loginTry_JD,JINGDONG);
						}
					}
					catch (Exception e)
					{
						showSnackBar("判断登录界面出错","",0);
						CrashReport.postCatchedException(e);
					}
					ToKey();
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

				@Override
				public boolean shouldOverrideUrlLoading(com.tencent.smtt.sdk.WebView view, String url)  
				{
					if (url == null) return false;

					try
					{
						if(url.startsWith("https://huodong.m.taobao.com")){
							return true;
						} 

						if (url.startsWith("http:") || url.startsWith("https:"))
						{
							initWebview_TBS.loadUrl(url);
							return true;
						}
						else
						{
							outsideUrl = url;
							if(supportLocalAPP){
								showSnackBar("页面试图打开本地APP", "允许", 3);
							}

//Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//startActivity(intent);
							return true;
						}
					}
					catch (Exception e)
					{ //防止crash (如果手机上没有安装处理某个scheme开头的url的APP, 会导致crash)
						return false;
					}
				}



			});
	}
	
	void debugToast(String context){
		if(DEBUG){
			Toast.makeText(MyApplication.getContext(),context,Toast.LENGTH_LONG).show();
		}
	}
	
	/**
	 * 展示一个SnackBar
	 */
	public void showSnackBar(String message, String button_text, final int action_number)
	{
//去掉虚拟按键
		/*getWindow().getDecorView().setSystemUiVisibility(
														   View.SYSTEM_UI_FLAG_HIDE_NAVIGATION //隐藏虚拟按键栏
														 | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
														 | View.SYSTEM_UI_FLAG_IMMERSIVE //防止点击屏幕时,隐藏虚拟按键栏又弹了出来
														 );*/
		final Snackbar snackbar = Snackbar.make(mainLinearLayout, message, Snackbar.LENGTH_LONG);
		snackbar.setAction(button_text, new View.OnClickListener() {
				@Override
				public void onClick(View v)
				{
					snackbar.dismiss();
//隐藏SnackBar时记得恢复隐藏虚拟按键栏,不然屏幕底部会多出一块空白布局出来,和难看
					getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
					if (action_number == 1)
					{
						exitProgrames();
					}
					else if (action_number == 2)
					{
						loadUrl(mTaobaoLiteDengluUrl,false);
					}
					else if (action_number == 3)
					{
						try
						{
							Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(outsideUrl));
							startActivity(intent);
							outsideUrl = null;
						}
						catch (Exception e)
						{
							Toast.makeText(Main.this, "启动APP失败了~你好像没有安装那个应用。", Toast.LENGTH_SHORT).show();
						}
					}
					else if(action_number == 4){
						
						loadUrl(HistoryMainUrl_old,false);
						loadUrl(HistoryLeftUrl_old,true);
					}
				}
			})
			.setCallback(new Snackbar.Callback() {
				@Override
				public void onDismissed(Snackbar snackbar, int event) {
					// Snackbar关闭时回调
					//隐藏SnackBar时记得恢复隐藏虚拟按键栏,不然屏幕底部会多出一块空白布局出来,和难看
					Main.this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
					//super.onDismissed(snackbar, event);
					//Toast.makeText(Main.this,"close",Toast.LENGTH_SHORT).show();
				}

				@Override
				public void onShown(Snackbar snackbar) {
					
					//Toast.makeText(Main.this,"open",Toast.LENGTH_SHORT).show();
					
					//super.onShown(snackbar);
					// Snackbar打开时回调
				}
			})
			.show();
	}

	String getClipbord()
	{
		String str2 = "null";
		if(findTaoKey == false && findUrlKey == false){
			return "off";
		}
		try
		{
// 获取 剪切板数据
			ClipboardManager cm = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
			if (cm != null)
			{
				ClipData cd2 = cm.getPrimaryClip();
				if (cd2 != null)
				{
					str2 = cd2.getItemAt(0).getText().toString();
				}
				else
				{
					str2 = "null";
				}
			}
		}
		catch (NullPointerException e)
		{
			showSnackBar( "哦哟，获取剪贴板出错了。 \n如果该提示频繁出现，请关闭淘口令相关的开关并等待开发者更新，抱歉。","",0);
			CrashReport.postCatchedException(e);
		}
		return str2;
	}

	@Override
	protected void onDestroy()
	{
//除指定的剪贴板数据改变监听器
// manager.removePrimaryClipChangedListener(manager.OnPrimaryClipChangedListenerwhat);
// TODO: Implement this method
		super.onDestroy();
	}


	/** 
	 * 实现文本复制功能 
	 * add by wangqianzhou 
	 * @param content 
	 */  
	public void copy(String content, Context context)  
	{  
		try
		{
// 得到剪贴板管理器  
			ClipboardManager cmb = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);  
			cmb.setText(content.trim());  
		}
		catch (NullPointerException e)
		{
			CrashReport.postCatchedException(e);
			showSnackBar("哦哟，获取剪贴板出错了。 \n如果该提示频繁出现，请关闭淘口令相关的开关并等待开发者更新，抱歉。","",0);
		}
	}  


	public void ToKey()
	{
		if(findTaoKey == false && findUrlKey == true){
			return;
		}
		final String originalClipboard = getClipbord();
		boolean IsTaoKey = originalClipboard.contains("后打开👉手淘👈");
		boolean IsUrlKey = originalClipboard.contains("手机淘宝");
//Toast.makeText(Main.this, getTaoKeyUrl(originalClipboard), Toast.LENGTH_SHORT).show();
//Toast.makeText(Main.this, getTaoKeyTitle(originalClipboard), Toast.LENGTH_SHORT).show();
//提示dialog
		Dialog.setCancelable(false);
		Dialog.setTitle("淘口令：");
		if (IsTaoKey)
		{
			Dialog.setMessage("检测到有一个淘口令:" + taokey.getTaoKeyTitle(originalClipboard) + "\n 是否马上打开？");
		}
		if (IsUrlKey)
		{
			Dialog.setMessage("检测到有一个淘宝客口令,是否马上打开？");
		}
		Dialog.setPositiveButton("打开",  new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					copy("", Main.this);
					loadUrl(taokey.getUrl(originalClipboard),false);
				}
			});
		Dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					copy("", Main.this);
				}
			});
//Toast.makeText(Main.this,originalClipboard,Toast.LENGTH_SHORT).show();
		if (IsTaoKey && findTaoKey)
		{
			copy("", Main.this);
			Toast.makeText(Main.this, "检测到有一个淘口令，是否马上打开？", Toast.LENGTH_SHORT).show();
			Dialog.show();
		}
		else if (IsUrlKey && findUrlKey)
		{
			copy("", Main.this);
			Toast.makeText(Main.this, "检测到有一个淘宝客口令，是否马上打开？", Toast.LENGTH_SHORT).show();
			Dialog.show();
		}
	}

	void noticeDialog()
	{
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
		Dialog.show();
	}


	@Override
	protected void onRestart()
	{
		ToKey();
// TODO: Implement this method
		super.onRestart();
	}

	@Override
	protected void onResume()
	{
		ToKey();
// TODO: Implement this method
		super.onResume();
	}

	void loadHomePage()
	{
		if (xianyuOK == false && MODE == 1)
		{
			if (IsTaobaoLite)
			{
				loadUrl(mTaobaoLiteUrl,false);
			}
			else
			{
				loadUrl(mTaobaoUrl,false);
			}
		}
		if (xianyuOK)
		{
			loadUrl(mXianyuUrl,false);
		}
		if (MODE == 2)
		{
			loadUrl(mJDUrl,false);
		}
	}

	private void loadPicture(String url)
	{
		try
		{
			Intent intent = new Intent(Main.this, PhotoView.class);
			intent.putExtra("URL", url);
			startActivity(intent);
		}
		catch (Exception e)
		{
			CrashReport.postCatchedException(e);
			Toast.makeText(MyApplication.getContext(), "加载PhotoView Activity出错，请等待开发者修复，抱歉。", Toast.LENGTH_SHORT).show();
		}
	}

	private void AutoLogin(int loginTime, int logoinType)
	{
		if (AutoLogin == true && logoinType == TAOMALL)
		{
			taoMaillogin(loginTime);
		}

		if (AutoLogin_JD == true && logoinType == JINGDONG){
			JDlogin(loginTime);
		}
	}

	private String jiemi(String miwen , String key)
	{
		String jiemihou = null;
		try
		{
			EncryptionDecryption des = new EncryptionDecryption(key);// 自定义密钥
//加密后的字符
//jiamihou = des.encrypt(mingwen);
//解密后的字符：
			jiemihou = des.decrypt(miwen);

		}
		catch (Exception e)
		{
			Toast.makeText(Main.this, "字符解密失败", Toast.LENGTH_SHORT).show();
		}
		return jiemihou;
	}

	private String jiami(String mingwen , String key)
	{
		String jiamihou = null;
		try
		{
			EncryptionDecryption des = new EncryptionDecryption(key);// 自定义密钥
//加密后的字符
			jiamihou = des.encrypt(mingwen);
//解密后的字符：
//jiemihou = des.decrypt(miwen);

		}
		catch (Exception e)
		{
			Toast.makeText(Main.this, "字符加密失败", Toast.LENGTH_SHORT).show();
		}
		return jiamihou;
	}

	void taoMaillogin(int loginTime){
		if (miPassword.contains("null") || miUsername.contains("null") || key == null)
		{
			new AlertDialog.Builder(Main.this)
				.setTitle("无淘宝用户信息")
				.setCancelable(false)
				.setMessage("您已经开启淘宝自动登录但尚未配置登录信息，请到设置里配置淘宝登录信息或取消自动登录。")
				.setNegativeButton(
				"去设置",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(
						DialogInterface dialog,
						int which)
					{
						SharedPreferences.Editor e = getSharedPreferences("data",0).edit().putBoolean("backFromSetting",true);
						e.commit();
						Intent intent = new Intent(Main.this, SettingsActivity.class);
						startActivity(intent);
					}
				}).show();
		}
		else if (loginTime <= 4)
		{
//用户名
			String user=jiemi(miUsername, key);
//String user = miUsername;
//密码
			String pwd=jiemi(miPassword, key);
//String pwd = miPassword;
//把用户名密码填充到表单
			loadUrl("javascript: {" +            

							 "document.getElementById('username').value = '" + user + "';" +            

							 "document.getElementById('password').value = '" + pwd + "';" +            

							 "var frms = document.getElementsByName('loginForm');" +            

							 "frms[0].submit();" +

							 " };",false);
			if (AutoClick)
			{
				Timer timer = new Timer();// 实例化Timer类
				timer.schedule(new TimerTask() {
						public void run()
						{	
							mHandler.sendEmptyMessage(0x124);	
						}
					}, 1500);// 这里百毫秒		
			}



		}
		else if (loginTime > 4)
		{
			new AlertDialog.Builder(Main.this)
				.setTitle("登录错误次数过多")
				.setCancelable(false)
				.setMessage("自动登录多次失败，可能是账户或密码错误，去设置重新配置一下吧。")
				.setNegativeButton(
				"去设置",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(
						DialogInterface dialog,
						int which)
					{
						SharedPreferences.Editor e = getSharedPreferences("data",0).edit().putBoolean("backFromSetting",true);
						e.commit();
						Intent intent = new Intent(Main.this, SettingsActivity.class);
						startActivity(intent);
					}
				}).show();
		}
	
	}
	
	

	void JDlogin(int loginTime){
		if (miPassword_JD.contains("null") || miUsername_JD.contains("null") || key == null)
		{
			new AlertDialog.Builder(Main.this)
				.setTitle("无京东用户信息")
				.setCancelable(false)
				.setMessage("您已经开启自动登录但尚未配置登录信息，请到设置里配置京东用户信息或取消自动登录。")
				.setNegativeButton(
				"去设置",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(
						DialogInterface dialog,
						int which)
					{
						SharedPreferences.Editor e = getSharedPreferences("data",0).edit().putBoolean("backFromSetting",true);
						e.commit();
						Intent intent = new Intent(Main.this, SettingsActivity.class);
						startActivity(intent);
					}
				}).show();
		}
		else if (loginTime <= 4)
		{
//用户名
			String user=jiemi(miUsername_JD, key);
			//String user = "";
//密码
			String pwd=jiemi(miPassword_JD, key);
			//String pwd = "";
//把用户名密码填充到表单
			loadUrl("javascript: {" +            

							 "document.getElementById('username').value = '" + user + "';" +            

							 "document.getElementById('password').value = '" + pwd + "';" +            

							 "var frms = document.getElementsByName('username_login');" +            

							 "frms[0].submit();" +

							 " };",false);
			loadUrl("javascript: {" +

							 "document.getElementById('sms_login_txt').click();" +

							 " };",false);
			loadUrl("javascript: {" +

							 "document.getElementById('account_login_txt').click();" +

							 " };",false);
			
			if (AutoClick_JD)
			{
				Timer timer = new Timer();// 实例化Timer类
				timer.schedule(new TimerTask() {
						public void run()
						{	
							mHandler.sendEmptyMessage(0x126);	
						}
					}, 1500);// 这里百毫秒		
			}



		}
		else if (loginTime > 4)
		{
			new AlertDialog.Builder(Main.this)
				.setTitle("登录错误次数过多")
				.setCancelable(false)
				.setMessage("自动登录多次失败，可能是账户或密码错误，去设置重新配置一下吧。")
				.setNegativeButton(
				"去设置",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(
						DialogInterface dialog,
						int which)
					{
						SharedPreferences.Editor e = getSharedPreferences("data",0).edit().putBoolean("backFromSetting",true);
						e.commit();
						Intent intent = new Intent(Main.this, SettingsActivity.class);
						startActivity(intent);
					}
				}).show();
		}
	}
	
	
	
	/***
	 * 功能：用线程保存图片
	 *
	 * @author wangyp
	 */
	private class SaveImage extends AsyncTask<String, Void, String>
	{
		@Override
		protected String doInBackground(String... params)
		{
			String result = "";
			try
			{
				String sdcard = Environment.getExternalStorageDirectory().toString();
				File file = new File(sdcard + "/Download");
				if (!file.exists())
				{
					file.mkdirs();
				}
				int idx = imgurl.lastIndexOf(".");
				String ext = imgurl.substring(idx);
				file = new File(sdcard + "/Download/" + new Date().getTime() + ext);
				InputStream inputStream = null;
				URL url = new URL(imgurl);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setConnectTimeout(20000);
				if (conn.getResponseCode() == 200)
				{
					inputStream = conn.getInputStream();
				}
				byte[] buffer = new byte[4096];
				int len = 0;
				FileOutputStream outStream = new FileOutputStream(file);
				while ((len = inputStream.read(buffer)) != -1)
				{
					outStream.write(buffer, 0, len);
				}
				outStream.close();
				result = "图片已保存至：" + file.getAbsolutePath();
			}
			catch (Exception e)
			{
				result = "保存失败！" + e.getLocalizedMessage();
			}
			return result;
		}

		@Override
		protected void onPostExecute(String result)
		{
			showSnackBar(result,"",0);
		}

		void showToast(String result)
		{
			Toast.makeText(MyApplication.getContext(), result, Toast.LENGTH_SHORT).show();
		}
    }

	public static String getRandomString(int length)
	{ //length表示生成字符串的长度
		String base = "abcdefghijklmnopqrstuvwxyz0123456789";   
		Random random = new Random();   
		StringBuffer sb = new StringBuffer();   
		for (int i = 0; i < length; i++)
		{   
			int number = random.nextInt(base.length());   
			sb.append(base.charAt(number));   
		}   
		return sb.toString();   
	}  

	public static int px2dip(int pxValue)
	{
		final float scale = Resources.getSystem().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}


	public static float dip2px(float dipValue)
	{
		final float scale = Resources.getSystem().getDisplayMetrics().density;
		return  (dipValue * scale + 0.5f);
	}


	public boolean onFirstStart()
	{
		boolean firstTime = false;
		try
		{   PackageInfo info = getPackageManager().getPackageInfo(PACKAGE_NAME, 0);
			int currentVersion = info.versionCode;
			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
			int lastVersion = prefs.getInt("VERSION_KEY", 0);
			if (currentVersion > lastVersion)
			{
				firstTime = true;
				//如果当前版本大于上次版本，该版本属于第一次启动
				//将当前版本写入preference中，则下次启动的时候，据此判断，不再为首次启动
				prefs.edit().putInt("VERSION_KEY", currentVersion).commit();
			}
		}
		catch (PackageManager.NameNotFoundException e)
		{
			showSnackBar( "抱歉啦~获取版本信息失败，请等待更新修复，大人原谅呢~", "",0);
		}
		return firstTime;
	}


	public void mUpdata()
	{
		Beta.checkUpgrade(false,true);
	}

	public void Updata()
	{
		new AlertDialog.Builder(Main.this)
			.setTitle("欢迎使用，这个版本有以下特性！")
			.setMessage(UPDATA_LOG)
			.setNegativeButton(
			"知道了",
			new DialogInterface.OnClickListener() {

				@Override
				public void onClick(
					DialogInterface dialog,
					int which)
				{

				}
			}).show();
	}
	

	public void initList()
	{
		String[] list = Taobaolist;
		if (MODE == 1)
		{
			list = Taobaolist;
		}
		else if (MODE == 2)
		{
			list = Jingdonglist;
		}
		lv = (ListView) findViewById(R.id.lv);//得到ListView对象的引用 /*为ListView设置Adapter来绑定数据*/ 
		final ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
		lv.setAdapter(mAdapter);
		lv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
										long arg3)
				{
                    String a = ("你点击了第" + arg2 + "行");
					//mAdapter.add("第"+arg2);
					//Toast.makeText(Main.this,a,Toast.LENGTH_SHORT).show();
					int id = arg2;
					if (MODE == 1)
					{
						if (id == 0)
						{
							if (IsTaobaoLite == false)
							{
								loadUrl(mMyTaobaoUrl,false);
							}
							else
							{
								showSnackBar("该选项在淘宝国际版中仅用作登录", "登录", 2);
							}
						}
						else if (id == 1)
						{
							if (IsTaobaoLite == false)
							{
								loadUrl(mTaobaoGouwuche,false);
							}
							else
							{
								loadUrl(mTaobaoLiteGouwuche,false);
							}
						}
						else if (id == 3)
						{
							if (IsTaobaoLite == false)
							{
								loadUrl(mTaobaoDingdan,false);
							}
							else
							{
								loadUrl(mTaobaoLiteWodedingdan,false);
							}
						}
						else if (id == 6)
						{
							loadUrl(mTaobaoKajuanbao,false);
						}
						else if (id == 4)
						{
							if (IsTaobaoLite == false)
							{
								loadUrl(mTaobaoSoucangjia,false);
							}
							else
							{
								loadUrl(mTaobaoLiteSoucangjia,false);
							}
						}
						else if (id == 2)
						{
							loadUrl(mTaobaoWuliuUrl,false);
						}
						else if (id == 5)
						{
							loadUrl(mTaobaoZuji,false);
						}
						else if (id == 7)
						{
							loadUrl(mTaobaoWW,false);
						}
						else if (id == 8)
						{
							SharedPreferences.Editor e = getSharedPreferences("data",0).edit().putBoolean("backFromSetting",true);
							e.commit();
							Intent intent = new Intent(Main.this, SettingsActivity.class);
							startActivity(intent);
						}
						else if (id == 9)
						{
							exitProgrames();
						}
					}

					if (MODE == 2)
					{
						if (id == 0)
						{
							loadUrl(mMyJD,false);
						}
						else if (id == 1)
						{
							loadUrl(mJDGouwuce,false);
						}
						else if (id == 2)
						{
							loadUrl(mJDFenlei,false);
						}
						else if (id == 3)
						{
							loadUrl(mJDFaxian,false);
						}
						else if (id == 4)
						{

							loadUrl(mJDDingdan,false);
						}
						else if (id == 5)
						{
							loadUrl(mJDGuanzhushangpin,false);
						}
						else if (id == 5)
						{
							loadUrl(mJDGuanzhudianpu,false);
						}
						else if (id == 7)
						{
							loadUrl(mJDHistory,false);
						}
						else if (id == 8)
						{
							SharedPreferences.Editor e = getSharedPreferences("data",0).edit().putBoolean("backFromSetting",true);
							e.commit();
							Intent intent = new Intent(Main.this, SettingsActivity.class);
							startActivity(intent);
						}
						else if (id == 9)
						{
							exitProgrames();
						}
					}


					DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
					drawer.closeDrawer(GravityCompat.START);

				}
			});
	}

	public void initNavHead()
	{
		
		if (MODE == 1)
		{
			nav_title.setText("淘宝");
			nav_change.setText("   点击切换京东");
			nav_btn.setImageResource(R.drawable.tb_icon);
		}
		else if (MODE == 2)
		{
			nav_title.setText("京东");
	   		nav_change.setText("   点击切换淘宝");
			nav_btn.setImageResource(R.drawable.jd_icon);
		}

		nav_btn.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					change_nav_mode();
					mWebViewLogin.reload();
					setColor();
				}
			});

		nav_title.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					change_nav_mode();
					mWebViewLogin.reload();
					setColor();
				}
			});
		nav_change.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					change_nav_mode();
					mWebViewLogin.reload();
					setColor();
				}
			});
	}

	public void loadLeftHomePage()
	{
		//Toast.makeText(Main.this,leftWebviewHomeUrl,Toast.LENGTH_SHORT).show();
		boolean haveUserHomePage = "".equals(leftWebviewHomeUrl.trim());
		//Toast.makeText(Main.this,haveUserHomePage + "",Toast.LENGTH_SHORT).show();
		if (SetUserHomePage == false)
		{
			if (MODE == TAOMALL)
			{
				loadUrl(mJDUrl,true);
			}
			else if (MODE == JINGDONG)
			{
				loadUrl(mTaobaoUrl,true);
			}
		}
		else if (SetUserHomePage == true && haveUserHomePage == false)
		{
			loadUrl(leftWebviewHomeUrl,true);
		}
		else
		{
			showSnackBar("自定义网址为空！！","",0);
			
		}

	}

	public void initLeftWebviewBtn()
	{
		btn_leftWebview_back.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					if(supportTBS){
						mWebViewLeft_TBS.goBack();
					} else {
						mWebViewLeft.goBack();
					}
					
				}

			});

		btn_leftWebview_home.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					loadLeftHomePage();
				}

			});

		btn_leftWebview_exchange.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					if(supportTBS){
						String tempUrl1 , tempUrl2;
						tempUrl1 = mWebViewLeft_TBS.getUrl();
						tempUrl2 = mWebView_TBS.getUrl();
						mWebViewLeft_TBS.loadUrl(tempUrl2);
						mWebView_TBS.loadUrl(tempUrl1);
						tempUrl1 = null;
						tempUrl2 = null;
					}else{
						String tempUrl1 , tempUrl2;
						tempUrl1 = mWebViewLeft.getUrl();
						tempUrl2 = mWebView.getUrl();
						mWebViewLeft.loadUrl(tempUrl2);
						mWebView.loadUrl(tempUrl1);
						tempUrl1 = null;
						tempUrl2 = null;
					}
				}

			});
	}

	public void change_nav_mode()
	{
		if (MODE == TAOMALL)
		{
			MODE = JINGDONG;
		}
		else
		{
			MODE = TAOMALL;
		}
		shp.edit().putInt("MODE", MODE).commit();
		initNavHead();
		initList();
		loadHomePage();
		loadLeftHomePage();
	}

	@SuppressLint("NewApi")
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults)
	{
        switch (requestCode)
		{
            case REQUEST_CODE_WRITE_EXTERNAL_STORAGE: {
					for (int i = 0; i < permissions.length; i++)
					{
						if (grantResults[i] == PackageManager.PERMISSION_GRANTED)
						{


						}
						else
						{
						}

					}
				}
            case REQUEST_CODE_READ_EXTERNAL_STORAGE_PERMISSIONS: {
					for (int i = 0; i < permissions.length; i++)
					{
						if (grantResults[i] == PackageManager.PERMISSION_GRANTED)
						{
							Toast.makeText(this, "允许读写存储！", Toast.LENGTH_SHORT).show();

						}
						else
						{
							Toast.makeText(this, "未允许读写存储！", Toast.LENGTH_SHORT).show();
						}

					}
				}
				break;
            default: {
					super.onRequestPermissionsResult(requestCode, permissions,
													 grantResults);
				}
        }
    }



}
