package com.augmentis.ayp.mymovie;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import java.util.UUID;

/**
 * Created by Waraporn on 9/22/2016.
 */
public class DetailMovieActivity extends AppCompatActivity{

    private static final String MOVIE_ID = "MOVIE_ID";
    private static final String TAG = "DetailMovieActivity";

    int id;

    TextView txt_director;
    TextView txt_actor;
    TextView txt_story;

    String str_director;
    String str_actor;
    String str_detail;

//    private static final String MOVIE_ID = "MOVIE_ID";
    private WebView mVideo;

    public static Intent newIntent(Context activity,int id) {
        Intent intent = new Intent(activity, DetailMovieActivity.class);
        intent.putExtra(MOVIE_ID, id);
        return intent;
    }

    @SuppressLint("NewApi")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_movie);


        mVideo = (WebView) findViewById(R.id.movie_teaser);
        mVideo.getSettings().setJavaScriptEnabled(true);
        mVideo.getSettings().setPluginState(WebSettings.PluginState.ON);
        mVideo.loadUrl("https://www.youtube.com/embed/OPp2CoLdXcc?autoplay=1&vq=small");
        mVideo.setWebChromeClient(new WebChromeClient());


        txt_director = (TextView) findViewById(R.id.director);
        txt_actor = (TextView) findViewById(R.id.actor);
        txt_story = (TextView) findViewById(R.id.story);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            id = bundle.getInt(MOVIE_ID);

            Log.d(TAG, "id : " + id);
        }


            if (android.os.Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }

            String url = "http://nearymovie.hol.es/movie.php?id=" + id;

            Log.d(TAG, "url : " + url);



            try {
                String strJson = getJSONUrl(url);
                JSONObject json1 = new JSONObject(strJson);
                String success1 = json1.getString("status");

                if (success1.equals("OK") == true) {
                    JSONArray Json_array_size = json1.getJSONArray("result");

                    Log.d(TAG, "data : " + Json_array_size);


                    for (int i = 0; i < Json_array_size.length(); i++) {
                        JSONObject object = Json_array_size.getJSONObject(i);

                        str_director = object.getString("director");
                        str_actor = object.getString("actor");
                        str_detail = object.getString("detail");

                    }
                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        txt_director.setText(str_director);
        txt_actor.setText(str_actor);
        txt_story.setText(str_detail);

        Log.d(TAG,"str_director : " + str_director);
        Log.d(TAG,"str_actor : " + str_actor);
        Log.d(TAG,"str_story : " + str_detail);

    }

    public String getJSONUrl(String url) {
        StringBuilder str = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        try {

            HttpResponse response = client.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) { // Download OK
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line;
                while ((line = reader.readLine()) != null) {
                    str.append(line);
                }
            } else {
                Log.e("Log", "Failed to download result..");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str.toString();
    }
}
