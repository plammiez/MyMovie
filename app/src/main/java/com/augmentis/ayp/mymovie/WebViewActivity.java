package com.augmentis.ayp.mymovie;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * Created by Wilailux on 10/18/2016.
 */

public class WebViewActivity extends AppCompatActivity {

    WebView webView;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, WebViewActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_activity);
        webView = (WebView) findViewById(R.id.wev_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webView.loadUrl("http://www.majorcineplex.com/movie");
        webView.setWebChromeClient(new WebChromeClient());
    }
}
