package io.github.gtf.easyShopping;
import java.util.*;
import android.app.*;

public class ActivityCollector
{
	public static List<Activity> activities = new ArrayList<>();

	public static void addActivity(Activity activity) {
		activities.add(activity);
	}
	
	public static void removeActivities(Activity activity) {
		activities.remove(activity);
	}
	
	public static void finishAll() {
		for(Activity activity : activities) {
			if(!activity.isFinishing()){
				activity.finish();
			}
		}
		activities.clear();
		android.os.Process.killProcess(android.os.Process.myPid());
	}
}
