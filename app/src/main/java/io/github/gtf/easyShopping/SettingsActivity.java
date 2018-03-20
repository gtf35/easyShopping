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
import android.preference.*;
import android.app.AlertDialog.*;
import android.widget.RadioGroup.*;
import com.tencent.bugly.crashreport.*;
import com.tencent.bugly.beta.*;

public class SettingsActivity extends BaseActivity
{

	private boolean xianyuOK;
	private boolean jingdongOK;
	private Toolbar toolbar;
	private AlertDialog.Builder Dialog;
	private AlertDialog.Builder Dialog2;
	private AlertDialog.Builder logInDialog;
	private AlertDialog.Builder logInDialog2;
	private String miPassword,miPassword_JD;
	private String miUsername,miUsername_JD;
	private boolean AutoLogin,AutoLogin_JD;
	private String key;
	SharedPreferences shp;
	String NewmiPassword,NewmiPassword_JD;
	String NewmiUserName,NewmiUserName_JD;
	String homePage;

	private int MODE = 1;
	private int TAOMALL = 1;
	private int JINGDONG = 2;
	
	private LinearLayout rootLayout;

	private AlertDialog.Builder SetUrlDialog;

	private AlertDialog.Builder SetUrlDialog2;


	@Override
    protected void onCreate(Bundle savedInstanceState)
	{
		MODE = PreferenceManager.getDefaultSharedPreferences(this).getInt("MODE", 1);
		if(MODE == JINGDONG){
			setTheme(R.style.myTheme_jd);
		} else {
			setTheme(R.style.myTheme_tb);
		}
		
		super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		//toolbar.setVisibility(View.VISIBLE);
		rootLayout = (LinearLayout) findViewById(R.id.root_layout);
		rootLayout.removeView(toolbar);
	    Dialog = new AlertDialog.Builder(this);
		Dialog2 = new AlertDialog.Builder(this);
        logInDialog = new AlertDialog.Builder(this);
		logInDialog2 = new AlertDialog.Builder(this);
		SetUrlDialog = new AlertDialog.Builder(this);
		SetUrlDialog2 = new AlertDialog.Builder(this);
		shp = PreferenceManager.getDefaultSharedPreferences(SettingsActivity.this);
		miUsername = shp.getString("miUsername","null");
		miPassword = shp.getString("miPassword","null");
		AutoLogin = shp.getBoolean("check_AutoLogin",true);
		miUsername_JD = shp.getString("miUsername_JD","null");
		miPassword_JD = shp.getString("miPassword_JD","null");
		AutoLogin_JD = shp.getBoolean("check_AutoLogin_JD",true);
		key = shp.getString("key",null);
		this.getFragmentManager().beginTransaction()
			.replace(android.R.id.content, new SettingsFragment())
			.commit();	
	}

//app:layout_behavior="@string/appbar_scrolling_view_behavior"
    @Override
    public void onBackPressed()
	{

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
		Toast.makeText(this,"请在群里，或酷安评论区进行反馈，谢谢.",Toast.LENGTH_LONG).show();
			}
	public void setTBHomePage(){
		String mTaobaoUrl = "https://m.taobao.com/ ";
		setHomePage("mTaobaoUrl",mTaobaoUrl);
	}
	public void setJDHomePage(){
		String mJDUrl = "https://m.jd.com";
		setHomePage("mJDUrl",mJDUrl);
	}
	public void mUpdata(){
		Beta.checkUpgrade(true,false);
	}

	public void pay(){
		Dialoginit();
		Dialog.show();
	}

	public void setAutoLogin(int type){
		LoginDialoginit(type);
		logInDialog.show();
	}

	/**
     * 支付宝支付
     * @param payCode 收款码后面的字符串；例如：收款二维码里面的字符串为 https://qr.alipay.com/stx00187oxldjvyo3ofaw60 ，则
     *                payCode = stx00187oxldjvyo3ofaw60 
     *                注：不区分大小写
     */
    private void donateAlipay(String payCode) {
        boolean hasInstalledAlipayClient = AlipayDonate.hasInstalledAlipayClient(this);
        if (hasInstalledAlipayClient) {
            AlipayDonate.startAlipayClient(this, payCode);
        }
    }

	/**
     * 需要提前准备好 微信收款码 照片，可通过微信客户端生成
     */
    private void donateWeixin() {
        InputStream weixinQrIs = getResources().openRawResource(R.raw.pay_weixin);
        String qrPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "AndroidDonateSample" + File.separator +
			"didikee_weixin.png";
        WeiXinDonate.saveDonateQrImage2SDCard(qrPath, BitmapFactory.decodeStream(weixinQrIs));
        WeiXinDonate.donateViaWeiXin(this, qrPath);
    }

	private void Dialoginit(){
		Dialog.setCancelable(false);
	    Dialog.setTitle("感谢有你：");
		Dialog.setMessage("很高兴你对我作品的肯定。 \n这个作品看起来很简单，但是对于我来说，我付出了大量的心血。 \n金额不限，支持微信支付宝 \n请随(duo)意(duo)捐赠。😉");
		Dialog.setPositiveButton("微信",  new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which)
				{

					Dialog2.setTitle("向导：");
					Dialog2.setCancelable(false);
					Dialog2.setMessage("受到微信API的限制，您需要在在弹出的界面点击右上角，从相册选取，然后选择第一张二维码向我捐赠。谢谢。");
					Dialog2.setPositiveButton("好的",  new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which)
							{
								donateWeixin();
							}
						});
					Dialog2.show();

				}
			});
		Dialog.setNeutralButton("算了",  new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which)
				{

				}
			});

		Dialog.setNegativeButton("支付宝",  new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					donateAlipay("FKX074315FOSAPU3BB5F7B");
				}
			});
	}

	private void LoginDialoginit(final int MODE){
		final int TB = 1;
		final int JD = 2;
		final View LoginAlertDialogView = View.inflate(getApplicationContext(), R.layout.textview_dialog, null);
		if (MODE == TB){
			logInDialog.setTitle("请输入淘宝账户(手机号/邮箱/用户名)：");
		} else if (MODE == JD){
			logInDialog.setTitle("请输入京东账户(手机号/邮箱/用户名)：");
		}
		logInDialog.setCancelable(false);
		logInDialog.setView( LoginAlertDialogView);
		logInDialog.setPositiveButton("下一步",  new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					EditText userusername = (EditText)LoginAlertDialogView.findViewById(R.id.textviewdialogEditText);
					final String username = userusername.getText().toString();
					if(MODE == TB){
						logInDialog2.setTitle("请输入淘宝账户的密码：");
					} else if(MODE == JD){
						logInDialog2.setTitle("请输入京东账户的密码：");
					}
					
					logInDialog2.setCancelable(false);
					final View LoginMimaAlertDialogView = View.inflate(getApplicationContext(), R.layout.textview_mima_dialog, null);
					logInDialog2.setView(LoginMimaAlertDialogView);
					logInDialog2.setPositiveButton("保存",  new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which)
							{
								SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext());
								EditText passwordEditView = (EditText)LoginMimaAlertDialogView.findViewById(R.id.mimaDialog);
								final String password = passwordEditView.getText().toString();
								if(key == null){
									key = getRandomString(8);
									prefs.edit().putString("key",key).commit();
								}
								if(MODE == TB){
									NewmiPassword = jiami(password,key);
									NewmiUserName = jiami(username,key);
									prefs.edit().putString("miPassword",NewmiPassword).commit();
									prefs.edit().putString("miUsername",NewmiUserName).commit();
									
								} else if(MODE == JD){
									NewmiPassword_JD = jiami(password,key);
									NewmiUserName_JD = jiami(username,key);
									prefs.edit().putString("miPassword_JD",NewmiPassword_JD).commit();
									prefs.edit().putString("miUsername_JD",NewmiUserName_JD).commit();
									
								}
								//String NewmiPassword = miPassword;
								//String NewmiUserName = miUsername;
									Toast.makeText(MyApplication.getContext(),"保存成功！",Toast.LENGTH_SHORT).show();

							}
						});
					logInDialog2.show();

				}
			});

		logInDialog.setNegativeButton("取消",  new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which)
				{

				}
			});
	}
	
	

	private String jiemi(String miwen , String key){
		String jiemihou = null;
		try {
			EncryptionDecryption des = new EncryptionDecryption(key);// 自定义密钥
			//加密后的字符
			//jiamihou = des.encrypt(mingwen);
			//解密后的字符：
			jiemihou = des.decrypt(miwen);

		} catch (Exception e) {
			Toast.makeText(SettingsActivity.this,"字符解密失败",Toast.LENGTH_SHORT).show();
		}
		return jiemihou;
	}

	private String jiami(String mingwen , String key){
		String jiamihou = null;
		try {
			EncryptionDecryption des = new EncryptionDecryption(key);// 自定义密钥
			//加密后的字符
			jiamihou = des.encrypt(mingwen);
			//解密后的字符：
			//jiemihou = des.decrypt(miwen);

		} catch (Exception e) {
			Toast.makeText(SettingsActivity.this,"字符加密失败",Toast.LENGTH_SHORT).show();
		}
		return jiamihou;
	}


	public static String getRandomString(int length) { //length表示生成字符串的长度
		String base = "abcdefghijklmnopqrstuvwxyz0123456789";   
		Random random = new Random();   
		StringBuffer sb = new StringBuffer();   
		for (int i = 0; i < length; i++) {   
			int number = random.nextInt(base.length());   
			sb.append(base.charAt(number));   
		}   
		return sb.toString();   
	}  

	private void back(){
		Intent back = new Intent(SettingsActivity.this,Main.class);
		startActivity(back);
	}

	


	public void noticeAutoWritePasswordDialog(int MODE){
		final int TB = 1;
		final int JD = 2;
		if(MODE == TB){
			new AlertDialog.Builder(SettingsActivity.this)
				.setTitle("小提示：")
				.setCancelable(false)
				.setMessage("由于天猫的安全保护，在登录天猫时可能会出现登录按钮无法点击的情况，这时点击下密码输入框再点击登录就可以了。")
				.setNegativeButton(
				"了解",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(
						DialogInterface dialog,
						int which) {

					}
				}).show();
		}else if (MODE == JD){
			new AlertDialog.Builder(SettingsActivity.this)
				.setTitle("小提示：")
				.setCancelable(false)
				.setMessage("由于京东的安全保护，在登录京东时可能会出现登录按钮无法点击的情况，这时点击下密码输入框再点击登录就可以了。")
				.setNegativeButton(
				"了解",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(
						DialogInterface dialog,
						int which) {

					}
				}).show();
		}
	}
	
	public void setLeftWebviewHomePage(){
		final View setHomeView = View.inflate(getApplicationContext(), R.layout.set_leftwebview_homepage, null);
		SetUrlDialog.setCancelable(false);
	    SetUrlDialog.setTitle("请选择主页：");
		SetUrlDialog.setView(setHomeView);
		RadioGroup rgroup = (RadioGroup)setHomeView.findViewById(R.id.rgroup);
 		rgroup.setOnCheckedChangeListener(new OnCheckedChangeListener(){
				@Override
				public void onCheckedChanged(RadioGroup rg,int checkedId)
				{
						if(checkedId == R.id.radioButton_taobao){
							homePage = "https://m.taobao.com";
						} else if(checkedId == R.id.radioButton_jingdong){
							homePage = "https://m.jd.com";
						}else if (checkedId == R.id.radioButton_google){
							homePage = "https://www.google.com";
						} else if (checkedId == R.id.radioButton_bing){
							homePage = "https://cn.bing.com";
						} else if (checkedId == R.id.radioButton_baidu){
							homePage = "https://m.baidu.com";
						} else if (checkedId == R.id.radioButton_juan){
							homePage = "http://yanshao.meizhevip.cn";
						}
					SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext());
					prefs.edit().putString("leftWebViewPage",homePage).commit();
				}
			});
		SetUrlDialog.setNeutralButton("保存", new DialogInterface.OnClickListener(){

				@Override
				public void onClick(DialogInterface p1, int p2)
				{
					SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext());
					prefs.edit().putString("leftWebViewPage",homePage).commit();
				}
		});
		SetUrlDialog.setPositiveButton("自定义",  new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					Toast.makeText(SettingsActivity .this,"请以http://或https://开头",Toast.LENGTH_LONG).show();
					SetUrlDialog2.setTitle("请输入主页地址：");
					SetUrlDialog2.setCancelable(false);
					final View inputView = View.inflate(getApplicationContext(), R.layout.textview_url, null);
					SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext());
					EditText EditView = (EditText)inputView.findViewById(R.id.editText_url);
					String temp = prefs.getString("leftWebViewPage","");
					EditView.setText(temp);
					SetUrlDialog2.setView(inputView);
					SetUrlDialog2.setPositiveButton("保存",  new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which)
							{
								SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext());
								EditText EditView = (EditText)inputView.findViewById(R.id.editText_url);
								final String homePageUrl = EditView.getText().toString();
								prefs.edit().putString("leftWebViewPage",homePageUrl).commit();
								Toast.makeText(MyApplication.getContext(),"保存成功！",Toast.LENGTH_SHORT).show();

							}
						});
				SetUrlDialog2.show();
				}
			});

		
		SetUrlDialog.show();
	}
	

	void setHomePage(final String shpurl,final String defaultshp){
		Toast.makeText(SettingsActivity .this,"请以http://或https://开头",Toast.LENGTH_LONG).show();
		final View inputView = View.inflate(getApplicationContext(), R.layout.textview_url, null);
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext());
		EditText EditView = (EditText)inputView.findViewById(R.id.editText_url);
		String temp = prefs.getString(shpurl,defaultshp);
		EditView.setText(temp);
		new AlertDialog.Builder(SettingsActivity.this)
			.setTitle("请输入主页地址：")
			.setCancelable(false)	
			.setView(inputView)
			.setPositiveButton("保存",  new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext());
					EditText EditView = (EditText)inputView.findViewById(R.id.editText_url);
					final String homePageUrl = EditView.getText().toString();
					prefs.edit().putString(shpurl,homePageUrl).commit();
					Toast.makeText(MyApplication.getContext(),"保存成功！",Toast.LENGTH_SHORT).show();

				}
			})
			.setNegativeButton("恢复默认", new DialogInterface.OnClickListener(){

				@Override
				public void onClick(DialogInterface p1, int p2)
				{
					SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext());
					prefs.edit().putString(shpurl,defaultshp).commit();
					Toast.makeText(MyApplication.getContext(),"已恢复默认！",Toast.LENGTH_SHORT).show();
				}
				
				
			})
			.show();
	}
	
	public void setLeftWebviewAbout(){
		String HELP = "试试从屏幕右侧向左侧滑动，就会划出对比窗口啦，默认是智能模式即 \n主页面为淘宝，对比窗口就是京东， \n主页面是京东，对比页面就是淘宝。\n在设置中可自定义主页。交换按钮可以将主页面和对比页面互换，主页键可以返回对比窗口的主页\n这么棒，还不捐赠一波？\n〃∀〃 ";
		Toast.makeText(SettingsActivity.this,"〃∀〃",Toast.LENGTH_SHORT).show();
		new AlertDialog.Builder(SettingsActivity.this)
			.setTitle("羞羞〃∀〃的说明：")
			.setCancelable(false)
			.setMessage(HELP)
			.setNegativeButton(
			"了解",
			new DialogInterface.OnClickListener() {

				@Override
				public void onClick(
					DialogInterface dialog,
					int which) {

				}
			}).show();
	}
}
