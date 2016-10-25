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

public class CinemaViewActivity extends AppCompatActivity {

    private static final String KEY_CINEMA = "CINEMA";
    WebView webView;

    public static Intent newIntent(Context context, String number) {
        Intent intent = new Intent(context, CinemaViewActivity.class);
        intent.putExtra(KEY_CINEMA, number);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String s = getIntent().getStringExtra(KEY_CINEMA);
        setContentView(R.layout.cinema_view_activity);
        webView = (WebView) findViewById(R.id.cinema_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webView.loadUrl("http://www.majorcineplex.com/booking2/search_showtime/cinema=" + s);
        webView.setWebChromeClient(new WebChromeClient());
    }
}
