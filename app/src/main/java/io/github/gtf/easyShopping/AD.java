package io.github.gtf.easyShopping;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import android.app.Activity;
import org.json.JSONArray;
import org.json.JSONObject;
import android.content.*;
import android.content.SharedPreferences.*;
import okhttp3.Call;
import java.io.IOException;
import android.widget.Toast;
import android.view.View;
import android.webkit.*;
import android.support.v7.app.AlertDialog;
import android.net.Uri;
import android.graphics.Bitmap;

public class AD
{
	String ADSetting = "https://gtf35.github.io/easyShopping/ADSetting.json";
	boolean haveAD = false ;

	 int id;

	 String name;

	 int version;

	 String url;
	 
	boolean on = true;
	
	public boolean initAD(final Activity activity){
		http(new okhttp3.Callback() {

				@Override
				public void onResponse(Call call, Response response) throws IOException {
					// 得到服务器返回的具体内容
					String responseData = response.body().string();
					haveAD = parseJSONWithJSONObject(responseData , activity);
					if(haveAD){
						activity. runOnUiThread(new Runnable() {  
								public void run() {  
									Toast.makeText(activity,"ad is:" + haveAD + " url is:" + url,Toast.LENGTH_LONG).show();
									showAD(activity);
								}  
							});
					}
					
					
				}

				@Override
				public void onFailure(Call call, IOException e) {
					// 在这里对异常情况进行处理
				}

			});
		
		
			return haveAD;
		}
	
	
	private void http(okhttp3.Callback callback){
		try {
			OkHttpClient client = new OkHttpClient();
			Request request = new Request.Builder()
				.url(ADSetting)
				.build();
			client.newCall(request).enqueue(callback);

		} catch (Exception e) {
			e.printStackTrace();
		}
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
	
	void showAD(final Activity activity){
		final View inputView = View.inflate(activity.getApplicationContext(), R.layout.adwebview, null);
		WebView adwebview = (WebView)inputView.findViewById(R.id.ad);
		WebSettings mWebViewSettings = adwebview.getSettings();
		mWebViewSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
		mWebViewSettings.setJavaScriptEnabled(true);  
		mWebViewSettings.setAppCacheEnabled(true);
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
		adwebview.setWebViewClient(new WebViewClient() {
				@Override
				public boolean shouldOverrideUrlLoading(WebView view, String url) {
					view.loadUrl(url);

					return true;
				}
				@Override
				public void onPageStarted(WebView view, String url, Bitmap favicon)
				{

				}
				@Override
				public void onPageFinished(WebView view, final String url)
				{
					if(on){
						new AlertDialog.Builder(activity)

							.setTitle(name)
							.setCancelable(false)	
							.setView(inputView)
							.setPositiveButton("感兴趣",  new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which)
								{
									//从其他浏览器打开
									Intent intent = new Intent();
									intent.setAction(Intent.ACTION_VIEW);
									Uri content_url = Uri.parse(url);
									intent.setData(content_url);
									activity.startActivity(Intent.createChooser(intent, "请选择浏览器"));

								}
							})
							.setNegativeButton("取消", new DialogInterface.OnClickListener(){

								@Override
								public void onClick(DialogInterface p1, int p2)
								{
									putADVersion(activity,version);
								}


							})
							.show();
					}
					on = false;
				}
			});
		adwebview.loadUrl(url);
		
		 
	}
	
	
}
