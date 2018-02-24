package io.github.gtf.easyShopping;
import android.app.*;
import android.content.*;

public class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
		// TODO Auto-generated method stub
        context = getApplicationContext();
		super.onCreate();
    }

    public static Context getContext() {
        return context;
    }
}
