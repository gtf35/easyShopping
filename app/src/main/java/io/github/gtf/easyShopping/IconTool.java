package io.github.gtf.easyShopping;
import android.content.pm.PackageManager;
import android.content.ComponentName;
import android.app.*;
import android.preference.*;
import android.widget.*;

public class IconTool
{
	private PackageManager mPackageManager;
    //默认组件
    private ComponentName componentNameDefault;
	
	private ComponentName icon_1,icon_2,icon_3,icon_4,icon_5,icon_6,icon_7;
  
	int ICONMODE;
    

    /**
     * 设置all失效
     */
    private void disableAll() {
        disableComponent(icon_1);
        disableComponent(icon_2);
		disableComponent(icon_3);
		disableComponent(icon_4);
		disableComponent(icon_5);
		disableComponent(icon_6);
		disableComponent(icon_7);
		disableComponent(componentNameDefault);
    }

	public void setEnable(int key){
		disableAll();
		if(key ==1){
			enableComponent(icon_1);
		} else if(key == 2){
			enableComponent(icon_2);
		} else if(key == 3){
			enableComponent(icon_3);
		} else if(key == 4){
			enableComponent(icon_4);
		} else if(key == 5){
			enableComponent(icon_5);
		} else if(key == 6){
			enableComponent(icon_6);
		}
	}
	
	
	
    /**
     * 启动组件
     *
     * @param componentName 组件名
     */
    private void enableComponent(ComponentName componentName) {
        //此方法用以启用和禁用组件，会覆盖Androidmanifest文件下定义的属性
        mPackageManager.setComponentEnabledSetting(componentName,
												   PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
												   PackageManager.DONT_KILL_APP);
    }

    /**
     * 禁用组件
     *
     * @param componentName 组件名
     */
    private void disableComponent(ComponentName componentName) {
        mPackageManager.setComponentEnabledSetting(componentName,
												   PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
												   PackageManager.DONT_KILL_APP);
    }

    //最后调用
    public void init(Activity activity) {
		//获取到包管理类实例
        mPackageManager = activity.getPackageManager();
        //得到此activity的全限定名
        componentNameDefault = activity.getComponentName();
        //根据全限定名创建一个组件，即activity-alias 节点下的name：HomeActivity2 对应的组件
		icon_1 = new ComponentName(activity.getBaseContext(), "io.github.gtf.easyShopping.1");
        icon_2 = new ComponentName(activity.getBaseContext(), "io.github.gtf.easyShopping.2");
		icon_3 = new ComponentName(activity.getBaseContext(), "io.github.gtf.easyShopping.3");
        icon_4 = new ComponentName(activity.getBaseContext(), "io.github.gtf.easyShopping.4");
		icon_5 = new ComponentName(activity.getBaseContext(), "io.github.gtf.easyShopping.5");
        icon_6 = new ComponentName(activity.getBaseContext(), "io.github.gtf.easyShopping.6");
		
		icon_7 = new ComponentName(activity.getBaseContext(), "io.github.gtf.easyShopping.小购物Vip");
        componentNameDefault = new ComponentName(activity.getBaseContext(), "io.github.gtf.easyShopping.小购物");
		
    }
	
	
	public void GetAndSetIcon(Activity activity){
		ICONMODE = PreferenceManager.getDefaultSharedPreferences(activity).getInt("ICONMODE",6);
		setEnable(ICONMODE);
	}
	
	public void SetAndRememberIcon(Activity activity,int mode){
		//Toast.makeText(activity,"可能需要重新启动才能被手机识别",Toast.LENGTH_LONG).show();
		PreferenceManager.getDefaultSharedPreferences(activity).edit().putInt("ICONMODE",mode).commit();
		setEnable(mode);
	}	
    
	public int Getshp(Activity activity){
		return PreferenceManager.getDefaultSharedPreferences(activity).getInt("ICONMODE",6);
	}
	
}
