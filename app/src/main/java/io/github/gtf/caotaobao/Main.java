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
import android.content.pm.*;


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
	ClipboardManager manager;

	String updata_pan_url = "http://pan.baidu.com/s/1o8zbaFw";

	String mTaobaoUrl = "https://m.taobao.com/";
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

	int startTime = 0;
	int version;
	String toolbarTitle = "Taobao";
	boolean HideLogo = true;
	boolean IsAtHome = true;
	boolean IsTaobaoLite = false;

	String mUA ="User-Agent: MQQBrowser/26 Mozilla/5.0 (Linux; U; Android 2.3.7; zh-cn; MB200 Build/GRJ22; CyanogenMod-7) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1";

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
        //fab = (FloatingActionButton) findViewById(R.id.fab);

		//获取Preferences
		SharedPreferences settingsRead = getSharedPreferences("data", 0);
//取出数据
	    IsTaobaoLite = settingsRead.getBoolean("IsTaobaoLite" , false);
		startTime = settingsRead.getInt("startTime", 0) + 1;
		version = settingsRead.getInt("version", 0);
//打开数据库
		SharedPreferences settings = getSharedPreferences("data", 0);
//处于编辑状态
		SharedPreferences.Editor editor = settings.edit();
//存放数据
		editor.putInt("startTime", startTime);
		editor.putInt("version",5);
		editor.putBoolean("IsTaobaoLite", false);
//完成提交
		editor.commit();

        /*fab.setOnClickListener(new View.OnClickListener() {
		 @Override
		 public void onClick(View view)
		 {
		 IsAtHome = true;
		 if (IsTaobaoLite == false)
		 {
		 mWebView.loadUrl(mTaobaoUrl);
		 }
		 else
		 {
		 mWebView.loadUrl(mTaobaoLiteUrl);
		 }

		 }
		 });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
			this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
		LoadWebView();
		mWebView.setVisibility(View.GONE);
		if ( version < 5){
			changeDialog();
		}else if (startTime == 1){
			noticeDialog();
		}
		IshaveTaoKey();
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
			mWebView.reload();
			return true;
		}
		else if (id == R.id.home)
		{
			IsAtHome = true;
			if (IsTaobaoLite == false)
			{
				mWebView.loadUrl(mTaobaoUrl);
			}
			else
			{
				mWebView.loadUrl(mTaobaoLiteUrl);
			}
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
				showSnackBar("该选项在淘宝国际版中仅用作登录", "登录", 2);
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
		else if (id == R.id.nav_wangwang)
		{
			mWebView.loadUrl(mTaobaoWW);
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

		}
		else if (id == R.id.nav_updata)
		{
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
		mWebViewSettings.setUserAgentString(mUA);
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
					IshaveTaoKey();
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
    public void showSnackBar(String message, String button_text, final int action_number)
	{
        //去掉虚拟按键
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
														 | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION //隐藏虚拟按键栏
														 | View.SYSTEM_UI_FLAG_IMMERSIVE //防止点击屏幕时,隐藏虚拟按键栏又弹了出来
														 );
        final Snackbar snackbar = Snackbar.make(getWindow().getDecorView(), message, Snackbar.LENGTH_LONG);
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
						mWebView.loadUrl(mTaobaoLiteDengluUrl);
					}
				}
			}).show();
    }

	String getClipbord()
	{
		// 获取 剪切板数据
		ClipboardManager cm = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
		ClipData cd2 = cm.getPrimaryClip();
		String str2 = "null";
		if (cd2 != null)
		{
			str2 = cd2.getItemAt(0).getText().toString();
		}
		else
		{
			str2 = "null";
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

	String getTaoKeyTitle(String taoKey)
	{
		//taoKey = "【美沫艾莫尔白玫瑰分体纯露免洗面膜 补水保湿提亮肤色 睡眠面贴膜】http://v.cvz5.com/h.EDtTvK 点击链接，再选择浏览器打开；或复制这条信息￥ZzGT0hLFkRC￥后打开👉手淘👈";
		double textLong1 =getLength(taoKey);
		int textLong = (int)textLong1;
		if (textLong < 1)
		{
			textLong = 1;
			taoKey = "To fix a bug";
		}
		String[] tempArray = new String[textLong];
		int i = 0;
		while (i <= textLong - 1)
		{
			tempArray[i] = taoKey.substring(i, i + 1);
			i = i + 1;
		}
		int start = 0;
		int end = 0;
		String finallyString = "";
		int time = 0 ;
		while (time < textLong)
		{
			String tempText = tempArray[time];
			if (tempText.contains("【"))
			{
				start = time + 1;
			}
			if (tempText.contains("】"))
			{
				end = time - 1;
			}
			time = time + 1;
		}

		int a = start ;

		while (a <= end)
		{
			finallyString = finallyString + tempArray[a];
			a = a + 1;
		}
		//System.out.println(finallyString);
		return finallyString;
	}

	String getTaoKeyUrl(String taoKey)
	{
		//taoKey = "【美沫艾莫尔白玫瑰分体纯露免洗面膜 补水保湿提亮肤色 睡眠面贴膜】http://v.cvz5.com/h.EDtTvK 点击链接，再选择浏览器打开；或复制这条信息￥ZzGT0hLFkRC￥后打开👉手淘👈";
		double textLong1 =getLength(taoKey);
		int textLong = (int)textLong1;
		if (textLong < 1)
		{
			textLong = 1;
			taoKey = "To fix a bug";
		}
		String[] tempArray = new String[textLong];
		int i = 0;
		while (i <= textLong - 1)
		{
			tempArray[i] = taoKey.substring(i, i + 1);
			i = i + 1;
		}
		int start = 0;
		int end = 0;
		String finallyString = "";
		int time = 0 ;
		while (time < textLong)
		{
			String tempText = tempArray[time];
			if (tempText.contains("】"))
			{
				start = time + 1;
			}
			if (tempText.contains("点"))
			{
				end = time - 2;
			}
			time = time + 1;
		}

		int a = start ;

		while (a <= end)
		{
			finallyString = finallyString + tempArray[a];
			a = a + 1;
		}
		//System.out.println(finallyString);
		return finallyString;
	}

	public static boolean isLetter(char c)
	{ 
        int k = 0x80; 
        return c / k == 0 ? true : false; 
    }

	/**
	 * 判断字符串是否为空
	 * @param str
	 * @return
	 */
	public static boolean isNull(String str)
	{
		if (str == null || str.trim().equals("") || str.trim().equalsIgnoreCase("null"))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	/** 
     * 得到一个字符串的长度,显示的长度,一个汉字或日韩文长度为2,英文字符长度为1 
     * @param String s 需要得到长度的字符串 
     * @return int 得到的字符串长度 
     */ 
    public static int length(String s)
	{
        if (s == null)
            return 0;
        char[] c = s.toCharArray();
        int len = 0;
        for (int i = 0; i < c.length; i++)
		{
            len++;
            if (!isLetter(c[i]))
			{
                len++;
            }
        }
        return len;
    }


    /** 
     * 得到一个字符串的长度,显示的长度,一个汉字或日韩文长度为1,英文字符长度为0.5 
     * @param String s 需要得到长度的字符串 
     * @return int 得到的字符串长度 
     */ 
    public static double getLength(String s)
	{
    	double valueLength = 0;  
        String chinese = "[\u4e00-\u9fa5]";  
        // 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1  
        for (int i = 0; i < s.length(); i++)
		{  
            // 获取一个字符  
            String temp = s.substring(i, i + 1);  
            // 判断是否为中文字符  
            if (temp.matches(chinese))
			{  
                // 中文字符长度为1  
                valueLength += 1;  
            }
			else
			{  
                // 其他字符长度为0.5  
                valueLength += 0.5;  
            }  
        }  
        //进位取整  
        return  Math.ceil(valueLength);  
    }

	/** 
	 * 实现文本复制功能 
	 * add by wangqianzhou 
	 * @param content 
	 */  
	public static void copy(String content, Context context)  
	{  
// 得到剪贴板管理器  
		ClipboardManager cmb = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);  
		cmb.setText(content.trim());  
	}  

	public void IshaveTaoKey()
	{
		final String originalClipboard = getClipbord();
		//Toast.makeText(Main.this, getTaoKeyUrl(originalClipboard), Toast.LENGTH_SHORT).show();
		//Toast.makeText(Main.this, getTaoKeyTitle(originalClipboard), Toast.LENGTH_SHORT).show();
		//提示dialog
		Dialog.setCancelable(false);
		Dialog.setTitle("淘口令：");
		Dialog.setMessage("检测到有一个淘口令:" + getTaoKeyTitle(originalClipboard) + "\n 是否马上打开？");
		Dialog.setPositiveButton("打开",  new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					copy("", Main.this);
					mWebView.loadUrl(getTaoKeyUrl(originalClipboard));
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
		boolean IsTaoKey = originalClipboard.contains("后打开👉手淘👈");
		if (IsTaoKey)
		{
			Toast.makeText(Main.this, "检测到有一个淘口令，是否马上打开？", Toast.LENGTH_SHORT).show();
			Dialog.show();
			copy("", Main.this);
		}
		else
		{

		}
	}
	
	void noticeDialog(){
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
	
	void changeDialog(){
		Dialog.setCancelable(false);
		Dialog.setTitle("特大喜讯：");
		Dialog.setMessage("淘宝里的条幅广告终于去掉了！！ \n 感谢酷安 @大漠飞雪 提供的UA！\n 感谢！");
		Dialog.setPositiveButton("OK",  new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					if (startTime == 1)
					{
						noticeDialog();
					}
				}
			});
		Dialog.show();
		
	}

	@Override
	protected void onRestart()
	{
		IshaveTaoKey();
		// TODO: Implement this method
		super.onRestart();
	}

	@Override
	protected void onResume()
	{
		IshaveTaoKey();
		// TODO: Implement this method
		super.onResume();
	}

	/*public String getVersion() {
		    try {
			        PackageManager manager = this.getPackageManager();
			        PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
			        String version = info.versionName;
			        return version;
			     } catch (Exception e) {
		            e.printStackTrace();
			        return this.getString(R.string.can_not_find_version_name);
			     }
		 }*/
	
}
