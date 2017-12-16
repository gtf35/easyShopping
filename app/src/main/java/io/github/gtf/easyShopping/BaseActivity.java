package io.github.gtf.easyShopping;
import android.support.v7.app.*;
import android.os.*;

public class BaseActivity extends AppCompatActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		ActivityCollector.addActivity(this);
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		ActivityCollector.removeActivities(this);
	}
	
	
}
