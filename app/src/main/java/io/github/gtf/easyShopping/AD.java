package io.github.gtf.easyShopping;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import android.app.Activity;
import org.json.JSONArray;
import org.json.JSONObject;
import android.content.*;
import android.content.SharedPreferences.*;

public class AD
{
	String ADSetting = "https://gtf35.github.io/easyShopping/ADSetting.json";
	boolean haveAD = false ;

	 int id;

	 String name;

	 int version;

	 String url;
	
	public boolean initAD(final Activity activity){
		
		new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						OkHttpClient client = new OkHttpClient();
						Request request = new Request.Builder()
							.url(ADSetting)
							.build();
						Response response = client.newCall(request).execute();
						String responseData = response.body().string();
						haveAD = parseJSONWithJSONObject(responseData , activity);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}).start();
			return haveAD;
		}
	
	private boolean parseJSONWithJSONObject(String jsonData , Activity activity) {
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                id = jsonObject.getInt("id");
                name = jsonObject.getString("name");
                version = jsonObject.getInt("version");
				url = jsonObject.getString("url");
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
		return isNew(activity);
    }
	
	private boolean isNew(Activity activity){
		if(getADVersion(activity) < version){
			return true;
		}
		return false;
	}
	
	private int getADVersion(Activity activity){
		//获取Preferences
		SharedPreferences settingsRead = activity. getSharedPreferences("AD", 0);
		//取出数据
		int ADVersion = settingsRead.getInt("version", 0);
		return ADVersion;
	}
	
	private void putADVersion(Activity activity,int  version){
		
		//打开数据库
		SharedPreferences settings = activity. getSharedPreferences("AD", 0);
		//处于编辑状态
		SharedPreferences.Editor editor = settings.edit();
		//存放数据
		editor.putInt("version", version);
		//完成提交
		editor.commit();
	}
}
