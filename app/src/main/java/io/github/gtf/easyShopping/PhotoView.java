package io.github.gtf.easyShopping;
import android.os.*;
import com.bm.library.PhotoView;
import android.content.*;
import java.net.*;
import com.pgyersdk.crash.*;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.*;
import com.bumptech.glide.*;
import com.bumptech.glide.load.engine.*;
import com.bumptech.glide.request.*;
import com.bumptech.glide.load.resource.drawable.*;
import com.bumptech.glide.request.target.*;
import android.view.*;
import java.io.*;
import java.util.*;


public class PhotoView extends BaseActivity
{
	
	private Toolbar toolbar;
	private PhotoView photoView;
	ProgressBar mProgressBar;
	String URL = null;
	
	@Override
    protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
        setContentView(R.layout.photoview);
		setSupportActionBar(toolbar);
		mProgressBar = (ProgressBar)findViewById(R.id.photoview_progressbar);
		mProgressBar.setVisibility(View.VISIBLE);
		initPhotoView();
		loadPhoto();
		photoView.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					finish();
				}
		});
		photoView.setOnLongClickListener(new View.OnLongClickListener(){

				@Override
				public boolean onLongClick(View p1)
				{
					new SaveImage().execute(); // Android 4.0以后要使用线程来访问网络
					return false;
				}
				
			
		});
	}
	
	public String getUrl(){
		try{
			Intent intent = getIntent();
			if (intent != null) {
				URL = intent.getStringExtra("URL");
			}		
		}catch(Exception e){
			RepotrCrash(e);
		}
		return URL;
	}
	
	public void loadPhoto(){
		Glide.with(this)
			.load(getUrl())
			.diskCacheStrategy(DiskCacheStrategy.NONE)
		    .listener(new RequestListener<String, GlideDrawable>() {
                @Override
                public boolean onException(Exception e, String model, Target<GlideDrawable> target,
										   boolean isFirstResource) {
					RepotrCrash(e);
                    return false;
                }
				

                @Override
                public boolean onResourceReady(GlideDrawable resource, String model,
											   Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
					mProgressBar.setVisibility(View.GONE);
                    return false;
                }
            })
			.into(photoView);
	}
	
	public void initPhotoView(){
		photoView = (PhotoView) findViewById(R.id.img);
// 启用图片缩放功能
		photoView.enable();
// 禁用图片缩放功能 (默认为禁用，会跟普通的ImageView一样，缩放功能需手动调用enable()启用)
//		photoView.disenable();
	}
	
	public static void RepotrCrash(Exception e){
		PgyCrashManager.reportCaughtException(MyApplication.getContext(), e); 
		Toast.makeText(MyApplication.getContext(),"哦哟，出错了,抱歉。",Toast.LENGTH_SHORT);
	}
	
	/***
     * 功能：用线程保存图片
     *
     * @author wangyp
     */
    private class SaveImage extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String result = "";
            try {
                String sdcard = Environment.getExternalStorageDirectory().toString();
                File file = new File(sdcard + "/Download");
                if (!file.exists()) {
                    file.mkdirs();
                }
                int idx = URL.lastIndexOf(".");
                String ext = URL.substring(idx);
                file = new File(sdcard + "/Download/" + new Date().getTime() + ext);
                InputStream inputStream = null;
                URL url = new URL(URL);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(20000);
                if (conn.getResponseCode() == 200) {
                    inputStream = conn.getInputStream();
                }
                byte[] buffer = new byte[4096];
                int len = 0;
                FileOutputStream outStream = new FileOutputStream(file);
                while ((len = inputStream.read(buffer)) != -1) {
                    outStream.write(buffer, 0, len);
                }
                outStream.close();
                result = "图片已保存至：" + file.getAbsolutePath();
            } catch (Exception e) {
                result = "保存失败！" + e.getLocalizedMessage();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            showToast(result);
        }

		void showToast(String result){
			Toast.makeText(MyApplication.getContext(),result,Toast.LENGTH_SHORT).show();
		}
    }
	
}
