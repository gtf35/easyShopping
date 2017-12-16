package io.github.gtf.easyShopping;
import android.preference.*;
import android.os.*;
import android.widget.*;
import android.content.*;
import android.net.*;
import com.pgyersdk.feedback.*;
import com.pgyersdk.activity.*;
import com.pgyersdk.update.PgyUpdateManager;

public class SettingsFragment extends PreferenceFragment
{
	private Preference pay;
	private Preference joinQQGroup;
	private Preference goGithub;
	private Preference updata;
	private Preference feedBack;
	private CheckBoxPreference xianyu;
	private CheckBoxPreference jingdong;
	private boolean xianyuOK;
	private boolean jingdongOK;
	private CheckBoxPreference autoUpdata;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.addPreferencesFromResource(R.xml.preference);
		pay = (Preference)findPreference("pay");
		joinQQGroup = (Preference)findPreference("joinQQGroup");
		xianyu = (CheckBoxPreference)findPreference("check_xianyu");
		jingdong = (CheckBoxPreference)findPreference("check_jingdong");
		goGithub = (Preference)findPreference("goGithub");
		updata = (Preference)findPreference("updata");
		feedBack = (Preference)findPreference("feedBack");
		autoUpdata = (CheckBoxPreference)findPreference("autoUpdata");
		
		SharedPreferences shp = PreferenceManager.getDefaultSharedPreferences(getActivity());
		xianyuOK = shp.getBoolean("check_xianyu", false);
		jingdongOK = shp.getBoolean("check_jingdong",false);
		
		pay.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
				@Override
				public boolean onPreferenceClick(Preference preference)
				{
					Toast.makeText(getActivity(), "谢谢", Toast.LENGTH_SHORT).show();
					Intent payIntent = new Intent(getActivity(),pay.class);
					startActivity(payIntent);
					return true;
				}
			});
		joinQQGroup.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
				@Override
				public boolean onPreferenceClick(Preference preference)
				{
					joinQQGroup("PMbwY58H1nomZYCGmBTCrVHPFHYCnCa4");
					return true;
				}
			});
		goGithub.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
				@Override
				public boolean onPreferenceClick(Preference preference)
				{
					//从其他浏览器打开
					Intent intent = new Intent();
					intent.setAction(Intent.ACTION_VIEW);
					Uri content_url = Uri.parse("https://www.github.com/gtf35/caoTaobao");
					intent.setData(content_url);
					startActivity(Intent.createChooser(intent, "请选择浏览器"));
					return true;
				}
			});
		feedBack.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
				@Override
				public boolean onPreferenceClick(Preference preference)
				{
					SettingsActivity sa = (SettingsActivity)getActivity();
					sa.mFeedBack();
					return true;
				}
			});
		updata.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
				@Override
				public boolean onPreferenceClick(Preference preference)
				{
					Toast.makeText(getActivity(), "正在检测更新，请稍后。。。。", Toast.LENGTH_SHORT).show();
					SettingsActivity sa = (SettingsActivity)getActivity();
					sa.mUpdata();
					return true;
				}
			});
		xianyu.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener(){
			@Override
			public boolean onPreferenceChange(Preference preference,Object newValue){
				if(jingdongOK){
					Toast.makeText(getActivity(),"两个选项只能选一个哟",Toast.LENGTH_SHORT).show();
				}
				return true;
			}
		});
		
		/*xianyu.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
				@Override
				public boolean onPreferenceClick(Preference preference)
				{
					if(!xianyuCanBeClick){
						Toast.makeText(getActivity(),"两个选项只能选一个哟",Toast.LENGTH_SHORT);
					}
					return true;
				}
			});*/
		
		jingdong.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener(){
				@Override
				public boolean onPreferenceChange(Preference preference,Object newValue){
					if(xianyuOK){
						Toast.makeText(getActivity(),"两个选项只能选一个哟",Toast.LENGTH_SHORT).show();
					}
					return true;
				}
			});
			
		/*jingdong.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
				@Override
				public boolean onPreferenceClick(Preference preference)
				{
					if(!jingdongCanBeClick){
						Toast.makeText(getActivity(),"两个选项只能选一个哟",Toast.LENGTH_SHORT);
					}
					return true;
				}
			});*/
			
		}
		
	/****************
	 *
	 * 发起添加群流程。群号：草宝(685830320) 的 key 为： PMbwY58H1nomZYCGmBTCrVHPFHYCnCa4
	 * 调用 joinQQGroup(PMbwY58H1nomZYCGmBTCrVHPFHYCnCa4) 即可发起手Q客户端申请加群 草宝(685830320)
	 *
	 * @param key 由官网生成的key
	 * @return 返回true表示呼起手Q成功，返回fals表示呼起失败
	 ******************/
	public boolean joinQQGroup(String key)
	{
		Intent intent = new Intent();
		intent.setData(Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3D" + key));
		// 此Flag可根据具体产品需要自定义，如设置，则在加群界面按返回，返回手Q主界面，不设置，按返回会返回到呼起产品界面    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
		try
		{
			startActivity(intent);
			return true;
		}
		catch (Exception e)
		{
			// 未安装手Q或安装的版本不支持
			Toast Toast1 = Toast.makeText(getActivity(), "启动QQ失败，请检查是否安装QQ", Toast.LENGTH_SHORT);
			Toast1.show();
			return false;
		}
	}


	

}
