package com.augmentis.ayp.mymovie;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * Created by Wilailux on 10/18/2016.
 */

public class CinemaViewActivity extends AppCompatActivity {

    WebView webView;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, CinemaViewActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.cinema_view_activity);

            webView = (WebView) findViewById(R.id.cinema_view);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setPluginState(WebSettings.PluginState.ON);
            webView.loadUrl("http://www.majorcineplex.com/cinema");
            webView.setWebChromeClient(new WebChromeClient());

    }
}
