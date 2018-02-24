package io.github.gtf.easyShopping;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.support.v7.app.*;
import android.support.annotation.*;
import com.tencent.bugly.Bugly;
import android.content.*;
import com.tencent.bugly.crashreport.*;
import java.io.*;
import android.text.*;
import com.tencent.bugly.crashreport.CrashReport.*;

public class BaseActivity extends AppCompatActivity
{
	private LinearLayout rootLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		ActivityCollector.addActivity(this);
		Bugly.init(getApplicationContext(), "7445bf51bf", false);
		Context context = getApplicationContext();
// 获取当前包名
		String packageName = context.getPackageName();
// 获取当前进程名
		String processName = getProcessName(android.os.Process.myPid());
// 设置是否为上报进程
		UserStrategy strategy = new UserStrategy(context);
		strategy.setUploadProcess(processName == null || processName.equals(packageName));
// 初始化Bugly
		//CrashReport.initCrashReport(context, "注册时申请的APPID", false, strategy);
// 如果通过“AndroidManifest.xml”来配置APP信息，初始化方法如下
		CrashReport.initCrashReport(context, strategy);
		// 这句很关键，注意是调用父类的方法
        super.setContentView(R.layout.activity_base);
        // 经测试在代码里直接声明透明状态栏更有效
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
        initToolbar();
		//CrashReport.testJavaCrash();
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		ActivityCollector.removeActivities(this);
	}
	
	private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }

    @Override
    public void setContentView(int layoutId) {
        setContentView(View.inflate(this, layoutId, null));
    }

    @Override
    public void setContentView(View view) {
        rootLayout = (LinearLayout) findViewById(R.id.root_layout);
        if (rootLayout == null) return;
        rootLayout.addView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        initToolbar();
    }
	
	/**
	 * 获取进程号对应的进程名
	 * 
	 * @param pid 进程号
	 * @return 进程名
	 */
	private static String getProcessName(int pid) {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
			String processName = reader.readLine();
			if (!TextUtils.isEmpty(processName)) {
				processName = processName.trim();
			}
			return processName;
		} catch (Throwable throwable) {
			throwable.printStackTrace();
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException exception) {
				exception.printStackTrace();
			}
		}
		return null;
	}
	

	
}
