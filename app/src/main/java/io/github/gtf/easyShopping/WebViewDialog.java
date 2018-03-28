package io.github.gtf.easyShopping;

import android.app.Dialog;  
import android.app.ActionBar.LayoutParams;  
import android.content.Context;  
import android.view.KeyEvent;  
import android.view.Window;  
import android.webkit.WebView;  
import android.webkit.WebViewClient;  

public class WebViewDialog extends Dialog {  

    WebView mWebView;  

    public WebViewDialog(Context context, String url) {  
        super(context);  

        requestWindowFeature(Window.FEATURE_NO_TITLE);  
        mWebView = new WebView(context);  
        mWebView.loadUrl(url);  
      //  mWebView.setWebViewClient(new MyClient());  
        mWebView.getSettings().setJavaScriptEnabled(true);  
        setContentView(mWebView);  

        getWindow().setLayout(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT);  
    }  

    @Override  
    public boolean onKeyDown(int keyCode, KeyEvent event) {  
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {  
            mWebView.goBack();  
            return true;  
        }  
        return super.onKeyDown(keyCode, event);  
    }  
/*
    class MyClient extends WebViewClient {  
        @Override  
        public boolean shouldOverrideUrlLoading(WebView view, String url) {  
            MainActivity.mActivity.runOnUiThread(new Runnable() {  
					@Override  
					public void run() {  
						// Toast.makeText(MainActivity.mActivity, "点击", 1).show();  
					}  
				});  
            return false;  
        }  
    }  
*/
}
